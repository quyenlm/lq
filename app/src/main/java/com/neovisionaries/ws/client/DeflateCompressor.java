package com.neovisionaries.ws.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

class DeflateCompressor {
    DeflateCompressor() {
    }

    public static byte[] compress(byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Deflater deflater = createDeflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater);
        dos.write(input, 0, input.length);
        dos.close();
        deflater.end();
        return baos.toByteArray();
    }

    private static Deflater createDeflater() {
        return new Deflater(-1, true);
    }
}
