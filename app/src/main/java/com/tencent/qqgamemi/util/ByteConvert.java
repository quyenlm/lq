package com.tencent.qqgamemi.util;

public class ByteConvert {
    public static byte[] longToBytes(long n) {
        byte[] b = new byte[8];
        b[7] = (byte) ((int) (n & 255));
        b[6] = (byte) ((int) ((n >> 8) & 255));
        b[5] = (byte) ((int) ((n >> 16) & 255));
        b[4] = (byte) ((int) ((n >> 24) & 255));
        b[3] = (byte) ((int) ((n >> 32) & 255));
        b[2] = (byte) ((int) ((n >> 40) & 255));
        b[1] = (byte) ((int) ((n >> 48) & 255));
        b[0] = (byte) ((int) ((n >> 56) & 255));
        return b;
    }

    public static void longToBytes(long n, byte[] array, int offset) {
        array[offset + 7] = (byte) ((int) (n & 255));
        array[offset + 6] = (byte) ((int) ((n >> 8) & 255));
        array[offset + 5] = (byte) ((int) ((n >> 16) & 255));
        array[offset + 4] = (byte) ((int) ((n >> 24) & 255));
        array[offset + 3] = (byte) ((int) ((n >> 32) & 255));
        array[offset + 2] = (byte) ((int) ((n >> 40) & 255));
        array[offset + 1] = (byte) ((int) ((n >> 48) & 255));
        array[offset + 0] = (byte) ((int) ((n >> 56) & 255));
    }

    public static long bytesToLong(byte[] array) {
        return ((((long) array[0]) & 255) << 56) | ((((long) array[1]) & 255) << 48) | ((((long) array[2]) & 255) << 40) | ((((long) array[3]) & 255) << 32) | ((((long) array[4]) & 255) << 24) | ((((long) array[5]) & 255) << 16) | ((((long) array[6]) & 255) << 8) | ((((long) array[7]) & 255) << 0);
    }

    public static long bytesToLong(byte[] array, int offset) {
        return ((((long) array[offset + 0]) & 255) << 56) | ((((long) array[offset + 1]) & 255) << 48) | ((((long) array[offset + 2]) & 255) << 40) | ((((long) array[offset + 3]) & 255) << 32) | ((((long) array[offset + 4]) & 255) << 24) | ((((long) array[offset + 5]) & 255) << 16) | ((((long) array[offset + 6]) & 255) << 8) | ((((long) array[offset + 7]) & 255) << 0);
    }

    public static byte[] intToBytes(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 255);
        b[2] = (byte) ((n >> 8) & 255);
        b[1] = (byte) ((n >> 16) & 255);
        b[0] = (byte) ((n >> 24) & 255);
        return b;
    }

    public static void intToBytes(int n, byte[] array, int offset) {
        array[offset + 3] = (byte) (n & 255);
        array[offset + 2] = (byte) ((n >> 8) & 255);
        array[offset + 1] = (byte) ((n >> 16) & 255);
        array[offset] = (byte) ((n >> 24) & 255);
    }

    public static int bytesToInt(byte[] b) {
        return (b[3] & 255) | ((b[2] & 255) << 8) | ((b[1] & 255) << 16) | ((b[0] & 255) << 24);
    }

    public static int bytesToInt(byte[] b, int offset) {
        return (b[offset + 3] & 255) | ((b[offset + 2] & 255) << 8) | ((b[offset + 1] & 255) << 16) | ((b[offset] & 255) << 24);
    }

    public static byte[] uintToBytes(long n) {
        byte[] b = new byte[4];
        b[3] = (byte) ((int) (n & 255));
        b[2] = (byte) ((int) ((n >> 8) & 255));
        b[1] = (byte) ((int) ((n >> 16) & 255));
        b[0] = (byte) ((int) ((n >> 24) & 255));
        return b;
    }

    public static void uintToBytes(long n, byte[] array, int offset) {
        array[offset + 3] = (byte) ((int) n);
        array[offset + 2] = (byte) ((int) ((n >> 8) & 255));
        array[offset + 1] = (byte) ((int) ((n >> 16) & 255));
        array[offset] = (byte) ((int) ((n >> 24) & 255));
    }

    public static long bytesToUint(byte[] array) {
        return ((long) (array[3] & 255)) | (((long) (array[2] & 255)) << 8) | (((long) (array[1] & 255)) << 16) | (((long) (array[0] & 255)) << 24);
    }

    public static long bytesToUint(byte[] array, int offset) {
        return ((long) (array[offset + 3] & 255)) | (((long) (array[offset + 2] & 255)) << 8) | (((long) (array[offset + 1] & 255)) << 16) | (((long) (array[offset] & 255)) << 24);
    }

    public static byte[] shortToBytes(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 255);
        b[0] = (byte) ((n >> 8) & 255);
        return b;
    }

    public static void shortToBytes(short n, byte[] array, int offset) {
        array[offset + 1] = (byte) (n & 255);
        array[offset] = (byte) ((n >> 8) & 255);
    }

    public static short bytesToShort(byte[] b) {
        return (short) ((b[1] & 255) | ((b[0] & 255) << 8));
    }

    public static short bytesToShort(byte[] b, int offset) {
        return (short) ((b[offset + 1] & 255) | ((b[offset] & 255) << 8));
    }

    public static byte[] ushortToBytes(int n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 255);
        b[0] = (byte) ((n >> 8) & 255);
        return b;
    }

    public static void ushortToBytes(int n, byte[] array, int offset) {
        array[offset + 1] = (byte) (n & 255);
        array[offset] = (byte) ((n >> 8) & 255);
    }

    public static int bytesToUshort(byte[] b) {
        return (b[1] & 255) | ((b[0] & 255) << 8);
    }

    public static int bytesToUshort(byte[] b, int offset) {
        return (b[offset + 1] & 255) | ((b[offset] & 255) << 8);
    }

    public static byte[] ubyteToBytes(int n) {
        return new byte[]{(byte) (n & 255)};
    }

    public static void ubyteToBytes(int n, byte[] array, int offset) {
        array[0] = (byte) (n & 255);
    }

    public static int bytesToUbyte(byte[] array) {
        return array[0] & 255;
    }

    public static int bytesToUbyte(byte[] array, int offset) {
        return array[offset] & 255;
    }
}
