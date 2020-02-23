package com.neovisionaries.ws.client;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class WebSocketInputStream extends FilterInputStream {
    public WebSocketInputStream(InputStream in) {
        super(in);
    }

    public String readLine() throws IOException {
        return Misc.readLine(this, "UTF-8");
    }

    public WebSocketFrame readFrame() throws IOException, WebSocketException {
        byte[] buffer = new byte[8];
        try {
            readBytes(buffer, 2);
            boolean fin = (buffer[0] & 128) != 0;
            boolean rsv1 = (buffer[0] & 64) != 0;
            boolean rsv2 = (buffer[0] & 32) != 0;
            boolean rsv3 = (buffer[0] & 16) != 0;
            int opcode = buffer[0] & 15;
            boolean mask = (buffer[1] & 128) != 0;
            long payloadLength = (long) (buffer[1] & Byte.MAX_VALUE);
            if (payloadLength == 126) {
                readBytes(buffer, 2);
                payloadLength = (long) (((buffer[0] & 255) << 8) | (buffer[1] & 255));
            } else if (payloadLength == 127) {
                readBytes(buffer, 8);
                if ((buffer[0] & 128) != 0) {
                    throw new WebSocketException(WebSocketError.INVALID_PAYLOAD_LENGTH, "The payload length of a frame is invalid.");
                }
                payloadLength = (long) (((buffer[0] & 255) << 56) | ((buffer[1] & 255) << 48) | ((buffer[2] & 255) << 40) | ((buffer[3] & 255) << 32) | ((buffer[4] & 255) << 24) | ((buffer[5] & 255) << 16) | ((buffer[6] & 255) << 8) | (buffer[7] & 255));
            }
            byte[] maskingKey = null;
            if (mask) {
                maskingKey = new byte[4];
                readBytes(maskingKey, 4);
            }
            if (2147483647L < payloadLength) {
                skipQuietly(payloadLength);
                throw new WebSocketException(WebSocketError.TOO_LONG_PAYLOAD, "The payload length of a frame exceeds the maximum array size in Java.");
            }
            return new WebSocketFrame().setFin(fin).setRsv1(rsv1).setRsv2(rsv2).setRsv3(rsv3).setOpcode(opcode).setMask(mask).setPayload(readPayload(payloadLength, mask, maskingKey));
        } catch (InsufficientDataException e) {
            if (e.getReadByteCount() == 0) {
                throw new NoMoreFrameException();
            }
            throw e;
        }
    }

    /* access modifiers changed from: package-private */
    public void readBytes(byte[] buffer, int length) throws IOException, WebSocketException {
        int total = 0;
        while (total < length) {
            int count = read(buffer, total, length - total);
            if (count <= 0) {
                throw new InsufficientDataException(length, total);
            }
            total += count;
        }
    }

    private void skipQuietly(long length) {
        try {
            skip(length);
        } catch (IOException e) {
        }
    }

    private byte[] readPayload(long payloadLength, boolean mask, byte[] maskingKey) throws IOException, WebSocketException {
        if (payloadLength == 0) {
            return null;
        }
        try {
            byte[] payload = new byte[((int) payloadLength)];
            readBytes(payload, payload.length);
            if (!mask) {
                return payload;
            }
            WebSocketFrame.mask(maskingKey, payload);
            return payload;
        } catch (OutOfMemoryError e) {
            skipQuietly(payloadLength);
            throw new WebSocketException(WebSocketError.INSUFFICIENT_MEMORY_FOR_PAYLOAD, "OutOfMemoryError occurred during a trial to allocate a memory area for a frame's payload: " + e.getMessage(), e);
        }
    }
}
