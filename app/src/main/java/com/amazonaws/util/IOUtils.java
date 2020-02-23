package com.amazonaws.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public enum IOUtils {
    ;
    
    private static final int BUFFER_SIZE = 4096;

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[4096];
            while (true) {
                int n = is.read(b);
                if (n == -1) {
                    return output.toByteArray();
                }
                output.write(b, 0, n);
            }
        } finally {
            output.close();
        }
    }

    public static String toString(InputStream is) throws IOException {
        return new String(toByteArray(is), StringUtils.UTF8);
    }
}
