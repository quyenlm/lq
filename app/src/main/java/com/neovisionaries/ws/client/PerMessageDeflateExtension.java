package com.neovisionaries.ws.client;

import java.util.Map;

class PerMessageDeflateExtension extends PerMessageCompressionExtension {
    private static final String CLIENT_MAX_WINDOW_BITS = "client_max_window_bits";
    private static final String CLIENT_NO_CONTEXT_TAKEOVER = "client_no_context_takeover";
    private static final byte[] COMPRESSION_TERMINATOR = {0, 0, -1, -1};
    private static final int INCOMING_SLIDING_WINDOW_MARGIN = 1024;
    private static final int MAX_BITS = 15;
    private static final int MAX_WINDOW_SIZE = 32768;
    private static final int MIN_BITS = 8;
    private static final int MIN_WINDOW_SIZE = 256;
    private static final String SERVER_MAX_WINDOW_BITS = "server_max_window_bits";
    private static final String SERVER_NO_CONTEXT_TAKEOVER = "server_no_context_takeover";
    private boolean mClientNoContextTakeover;
    private int mClientWindowSize = 32768;
    private ByteArray mIncomingSlidingWindow;
    private int mIncomingSlidingWindowBufferSize;
    private boolean mServerNoContextTakeover;
    private int mServerWindowSize = 32768;

    public PerMessageDeflateExtension() {
        super(WebSocketExtension.PERMESSAGE_DEFLATE);
    }

    public PerMessageDeflateExtension(String name) {
        super(name);
    }

    /* access modifiers changed from: package-private */
    public void validate() throws WebSocketException {
        for (Map.Entry<String, String> entry : getParameters().entrySet()) {
            validateParameter(entry.getKey(), entry.getValue());
        }
        this.mIncomingSlidingWindowBufferSize = this.mServerWindowSize + 1024;
    }

    public boolean isServerNoContextTakeover() {
        return this.mServerNoContextTakeover;
    }

    public boolean isClientNoContextTakeover() {
        return this.mClientNoContextTakeover;
    }

    public int getServerWindowSize() {
        return this.mServerWindowSize;
    }

    public int getClientWindowSize() {
        return this.mClientWindowSize;
    }

    private void validateParameter(String key, String value) throws WebSocketException {
        if (SERVER_NO_CONTEXT_TAKEOVER.equals(key)) {
            this.mServerNoContextTakeover = true;
        } else if (CLIENT_NO_CONTEXT_TAKEOVER.equals(key)) {
            this.mClientNoContextTakeover = true;
        } else if (SERVER_MAX_WINDOW_BITS.equals(key)) {
            this.mServerWindowSize = computeWindowSize(key, value);
        } else if (CLIENT_MAX_WINDOW_BITS.equals(key)) {
            this.mClientWindowSize = computeWindowSize(key, value);
        } else {
            throw new WebSocketException(WebSocketError.PERMESSAGE_DEFLATE_UNSUPPORTED_PARAMETER, "permessage-deflate extension contains an unsupported parameter: " + key);
        }
    }

    private int computeWindowSize(String key, String value) throws WebSocketException {
        int size = 256;
        for (int i = 8; i < extractMaxWindowBits(key, value); i++) {
            size *= 2;
        }
        return size;
    }

    private int extractMaxWindowBits(String key, String value) throws WebSocketException {
        int bits = parseMaxWindowBits(value);
        if (bits >= 0) {
            return bits;
        }
        throw new WebSocketException(WebSocketError.PERMESSAGE_DEFLATE_INVALID_MAX_WINDOW_BITS, String.format("The value of %s parameter of permessage-deflate extension is invalid: %s", new Object[]{key, value}));
    }

    private int parseMaxWindowBits(String value) {
        if (value == null) {
            return -1;
        }
        try {
            int bits = Integer.parseInt(value);
            if (bits < 8 || 15 < bits) {
                return -1;
            }
            return bits;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* access modifiers changed from: protected */
    public byte[] decompress(byte[] compressed) throws WebSocketException {
        ByteArray input = new ByteArray(compressed.length + COMPRESSION_TERMINATOR.length);
        input.put(compressed);
        input.put(COMPRESSION_TERMINATOR);
        if (this.mIncomingSlidingWindow == null) {
            this.mIncomingSlidingWindow = new ByteArray(this.mIncomingSlidingWindowBufferSize);
        }
        int outPos = this.mIncomingSlidingWindow.length();
        try {
            DeflateDecompressor.decompress(input, this.mIncomingSlidingWindow);
            byte[] output = this.mIncomingSlidingWindow.toBytes(outPos);
            this.mIncomingSlidingWindow.shrink(this.mIncomingSlidingWindowBufferSize);
            if (this.mServerNoContextTakeover) {
                this.mIncomingSlidingWindow.clear();
            }
            return output;
        } catch (Exception e) {
            throw new WebSocketException(WebSocketError.DECOMPRESSION_ERROR, String.format("Failed to decompress the message: %s", new Object[]{e.getMessage()}), e);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] compress(byte[] plain) throws WebSocketException {
        if (!canCompress(plain)) {
            return plain;
        }
        try {
            return adjustCompressedData(DeflateCompressor.compress(plain));
        } catch (Exception e) {
            throw new WebSocketException(WebSocketError.COMPRESSION_ERROR, String.format("Failed to compress the message: %s", new Object[]{e.getMessage()}), e);
        }
    }

    private boolean canCompress(byte[] plain) {
        if (this.mClientWindowSize != 32768 && plain.length >= this.mClientWindowSize) {
            return false;
        }
        return true;
    }

    private static byte[] adjustCompressedData(byte[] compressed) throws FormatException {
        ByteArray data = new ByteArray(compressed.length + 1);
        data.put(compressed);
        int[] bitIndex = new int[1];
        boolean[] hasEmptyBlock = new boolean[1];
        do {
        } while (skipBlock(data, bitIndex, hasEmptyBlock));
        if (hasEmptyBlock[0]) {
            return data.toBytes(0, (((bitIndex[0] - 1) / 8) + 1) - 4);
        }
        appendEmptyBlock(data, bitIndex);
        return data.toBytes(0, ((bitIndex[0] - 1) / 8) + 1);
    }

    private static void appendEmptyBlock(ByteArray data, int[] bitIndex) {
        switch (bitIndex[0] % 8) {
            case 0:
            case 6:
            case 7:
                data.put(0);
                break;
        }
        bitIndex[0] = bitIndex[0] + 3;
    }

    private static boolean skipBlock(ByteArray input, int[] bitIndex, boolean[] hasEmptyBlock) throws FormatException {
        boolean last = input.readBit(bitIndex);
        if (last) {
            input.clearBit(bitIndex[0] - 1);
        }
        boolean plain0 = false;
        switch (input.readBits(bitIndex, 2)) {
            case 0:
                if (skipPlainBlock(input, bitIndex) != 0) {
                    plain0 = false;
                    break;
                } else {
                    plain0 = true;
                    break;
                }
            case 1:
                skipFixedBlock(input, bitIndex);
                break;
            case 2:
                skipDynamicBlock(input, bitIndex);
                break;
            default:
                throw new FormatException(String.format("[%s] Bad compression type '11' at the bit index '%d'.", new Object[]{PerMessageDeflateExtension.class.getSimpleName(), Integer.valueOf(bitIndex[0])}));
        }
        if (input.length() <= bitIndex[0] / 8) {
            last = true;
        }
        if (last && plain0) {
            hasEmptyBlock[0] = true;
        }
        if (!last) {
            return true;
        }
        return false;
    }

    private static int skipPlainBlock(ByteArray input, int[] bitIndex) {
        int index = ((bitIndex[0] + 7) & -8) / 8;
        int len = (input.get(index) & 255) + ((input.get(index + 1) & 255) * 256);
        bitIndex[0] = (index + 4 + len) * 8;
        return len;
    }

    private static void skipFixedBlock(ByteArray input, int[] bitIndex) throws FormatException {
        skipData(input, bitIndex, FixedLiteralLengthHuffman.getInstance(), FixedDistanceHuffman.getInstance());
    }

    private static void skipDynamicBlock(ByteArray input, int[] bitIndex) throws FormatException {
        Huffman[] tables = new Huffman[2];
        DeflateUtil.readDynamicTables(input, bitIndex, tables);
        skipData(input, bitIndex, tables[0], tables[1]);
    }

    private static void skipData(ByteArray input, int[] bitIndex, Huffman literalLengthHuffman, Huffman distanceHuffman) throws FormatException {
        while (true) {
            int literalLength = literalLengthHuffman.readSym(input, bitIndex);
            if (literalLength != 256) {
                if (literalLength < 0 || literalLength > 255) {
                    DeflateUtil.readLength(input, bitIndex, literalLength);
                    DeflateUtil.readDistance(input, bitIndex, distanceHuffman);
                }
            } else {
                return;
            }
        }
    }
}
