package com.google.android.gms.internal;

import java.nio.charset.Charset;
import java.security.MessageDigest;

@zzzn
public final class zzho extends zzhf {
    private MessageDigest zzzd;
    private final int zzzg;
    private final int zzzh;

    public zzho(int i) {
        int i2 = i / 8;
        this.zzzg = i % 8 > 0 ? i2 + 1 : i2;
        this.zzzh = i;
    }

    public final byte[] zzy(String str) {
        byte[] bArr;
        synchronized (this.mLock) {
            this.zzzd = zzcW();
            if (this.zzzd == null) {
                bArr = new byte[0];
            } else {
                this.zzzd.reset();
                this.zzzd.update(str.getBytes(Charset.forName("UTF-8")));
                byte[] digest = this.zzzd.digest();
                bArr = new byte[(digest.length > this.zzzg ? this.zzzg : digest.length)];
                System.arraycopy(digest, 0, bArr, 0, bArr.length);
                if (this.zzzh % 8 > 0) {
                    long j = 0;
                    for (int i = 0; i < bArr.length; i++) {
                        if (i > 0) {
                            j <<= 8;
                        }
                        j += (long) (bArr[i] & 255);
                    }
                    long j2 = j >>> (8 - (this.zzzh % 8));
                    for (int i2 = this.zzzg - 1; i2 >= 0; i2--) {
                        bArr[i2] = (byte) ((int) (255 & j2));
                        j2 >>>= 8;
                    }
                }
            }
        }
        return bArr;
    }
}
