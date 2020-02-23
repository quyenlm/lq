package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@zzzn
public abstract class zzhf {
    @Nullable
    private static MessageDigest zzyW = null;
    protected Object mLock = new Object();

    /* access modifiers changed from: protected */
    @Nullable
    public final MessageDigest zzcW() {
        MessageDigest messageDigest;
        synchronized (this.mLock) {
            if (zzyW != null) {
                messageDigest = zzyW;
            } else {
                for (int i = 0; i < 2; i++) {
                    try {
                        zzyW = MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException e) {
                    }
                }
                messageDigest = zzyW;
            }
        }
        return messageDigest;
    }

    /* access modifiers changed from: package-private */
    public abstract byte[] zzy(String str);
}
