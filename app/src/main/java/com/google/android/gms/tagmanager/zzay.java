package com.google.android.gms.tagmanager;

import java.util.Arrays;

final class zzay {
    final String zzBN;
    final byte[] zzbEw;

    zzay(String str, byte[] bArr) {
        this.zzBN = str;
        this.zzbEw = bArr;
    }

    public final String toString() {
        String str = this.zzBN;
        return new StringBuilder(String.valueOf(str).length() + 54).append("KeyAndSerialized: key = ").append(str).append(" serialized hash = ").append(Arrays.hashCode(this.zzbEw)).toString();
    }
}
