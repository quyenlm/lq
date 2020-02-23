package com.neovisionaries.ws.client;

class DeflateDecompressor {
    DeflateDecompressor() {
    }

    public static void decompress(ByteArray input, ByteArray output) throws FormatException {
        decompress(input, 0, output);
    }

    private static void decompress(ByteArray input, int index, ByteArray output) throws FormatException {
        do {
        } while (inflateBlock(input, new int[]{index * 8}, output));
    }

    private static boolean inflateBlock(ByteArray input, int[] bitIndex, ByteArray output) throws FormatException {
        boolean last = input.readBit(bitIndex);
        switch (input.readBits(bitIndex, 2)) {
            case 0:
                inflatePlainBlock(input, bitIndex, output);
                break;
            case 1:
                inflateFixedBlock(input, bitIndex, output);
                break;
            case 2:
                inflateDynamicBlock(input, bitIndex, output);
                break;
            default:
                throw new FormatException(String.format("[%s] Bad compression type '11' at the bit index '%d'.", new Object[]{DeflateDecompressor.class.getSimpleName(), Integer.valueOf(bitIndex[0])}));
        }
        if (input.length() <= bitIndex[0] / 8) {
            last = true;
        }
        if (!last) {
            return true;
        }
        return false;
    }

    private static void inflatePlainBlock(ByteArray input, int[] bitIndex, ByteArray output) {
        int index = ((bitIndex[0] + 7) & -8) / 8;
        int len = (input.get(index) & 255) + ((input.get(index + 1) & 255) * 256);
        int index2 = index + 4;
        output.put(input, index2, len);
        bitIndex[0] = (index2 + len) * 8;
    }

    private static void inflateFixedBlock(ByteArray input, int[] bitIndex, ByteArray output) throws FormatException {
        inflateData(input, bitIndex, output, FixedLiteralLengthHuffman.getInstance(), FixedDistanceHuffman.getInstance());
    }

    private static void inflateDynamicBlock(ByteArray input, int[] bitIndex, ByteArray output) throws FormatException {
        Huffman[] tables = new Huffman[2];
        DeflateUtil.readDynamicTables(input, bitIndex, tables);
        inflateData(input, bitIndex, output, tables[0], tables[1]);
    }

    private static void inflateData(ByteArray input, int[] bitIndex, ByteArray output, Huffman literalLengthHuffman, Huffman distanceHuffman) throws FormatException {
        while (true) {
            int literalLength = literalLengthHuffman.readSym(input, bitIndex);
            if (literalLength != 256) {
                if (literalLength < 0 || literalLength > 255) {
                    duplicate(DeflateUtil.readLength(input, bitIndex, literalLength), DeflateUtil.readDistance(input, bitIndex, distanceHuffman), output);
                } else {
                    output.put(literalLength);
                }
            } else {
                return;
            }
        }
    }

    private static void duplicate(int length, int distance, ByteArray output) {
        int sourceLength = output.length();
        byte[] target = new byte[length];
        int initialPosition = sourceLength - distance;
        int sourceIndex = initialPosition;
        int targetIndex = 0;
        while (targetIndex < length) {
            if (sourceLength <= sourceIndex) {
                sourceIndex = initialPosition;
            }
            target[targetIndex] = output.get(sourceIndex);
            targetIndex++;
            sourceIndex++;
        }
        output.put(target);
    }
}
