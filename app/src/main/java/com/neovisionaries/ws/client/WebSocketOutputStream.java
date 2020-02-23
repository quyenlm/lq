package com.neovisionaries.ws.client;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class WebSocketOutputStream extends FilterOutputStream {
    public WebSocketOutputStream(OutputStream out) {
        super(out);
    }

    public void write(String string) throws IOException {
        write(Misc.getBytesUTF8(string));
    }

    public void write(WebSocketFrame frame) throws IOException {
        writeFrame0(frame);
        writeFrame1(frame);
        writeFrameExtendedPayloadLength(frame);
        byte[] maskingKey = Misc.nextBytes(4);
        write(maskingKey);
        writeFramePayload(frame, maskingKey);
    }

    private void writeFrame0(WebSocketFrame frame) throws IOException {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        if (frame.getFin()) {
            i = 128;
        } else {
            i = 0;
        }
        if (frame.getRsv1()) {
            i2 = 64;
        } else {
            i2 = 0;
        }
        int i5 = i2 | i;
        if (frame.getRsv2()) {
            i3 = 32;
        } else {
            i3 = 0;
        }
        int i6 = i3 | i5;
        if (frame.getRsv3()) {
            i4 = 16;
        }
        write(i6 | i4 | (frame.getOpcode() & 15));
    }

    private void writeFrame1(WebSocketFrame frame) throws IOException {
        int b;
        int len = frame.getPayloadLength();
        if (len <= 125) {
            b = 128 | len;
        } else if (len <= 65535) {
            b = 128 | 126;
        } else {
            b = 128 | 127;
        }
        write(b);
    }

    private void writeFrameExtendedPayloadLength(WebSocketFrame frame) throws IOException {
        int len = frame.getPayloadLength();
        if (len > 125) {
            if (len <= 65535) {
                write((len >> 8) & 255);
                write(len & 255);
                return;
            }
            write(0);
            write(0);
            write(0);
            write(0);
            write((len >> 24) & 255);
            write((len >> 16) & 255);
            write((len >> 8) & 255);
            write(len & 255);
        }
    }

    private void writeFramePayload(WebSocketFrame frame, byte[] maskingKey) throws IOException {
        byte[] payload = frame.getPayload();
        if (payload != null) {
            for (int i = 0; i < payload.length; i++) {
                write((payload[i] ^ maskingKey[i % 4]) & 255);
            }
        }
    }
}
