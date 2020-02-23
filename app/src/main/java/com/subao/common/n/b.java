package com.subao.common.n;

import java.security.MessageDigest;

/* compiled from: AuthUtils */
public class b {
    public static byte[] a(String str, byte[] bArr) {
        return MessageDigest.getInstance(str).digest(bArr);
    }

    public static byte[] a(byte[] bArr) {
        return a("MD5", bArr);
    }
}
