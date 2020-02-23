package com.google.android.gms.nearby.messages.internal;

import java.util.Arrays;

public class zzc {
    private static final char[] zzbyU = "0123456789abcdef".toCharArray();
    private final byte[] zzbyV;

    protected zzc(byte[] bArr) {
        this.zzbyV = bArr;
    }

    public static byte[] zzeE(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) ((Character.digit(str.charAt(i * 2), 16) << 4) + Character.digit(str.charAt((i * 2) + 1), 16));
        }
        return bArr;
    }

    public static String zzo(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(zzbyU[(b >> 4) & 15]).append(zzbyU[b & 15]);
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!obj.getClass().isAssignableFrom(getClass())) {
            return false;
        }
        return Arrays.equals(this.zzbyV, ((zzc) obj).zzbyV);
    }

    public final byte[] getBytes() {
        return this.zzbyV;
    }

    public final String getHex() {
        return zzo(this.zzbyV);
    }

    public int hashCode() {
        return Arrays.hashCode(this.zzbyV);
    }

    public String toString() {
        return zzo(this.zzbyV);
    }
}
