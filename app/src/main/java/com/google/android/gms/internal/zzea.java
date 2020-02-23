package com.google.android.gms.internal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class zzea {
    private static String TAG = zzea.class.getSimpleName();
    private final String className;
    private final zzdb zzpJ;
    private final String zzrv;
    private final int zzrw = 2;
    private volatile Method zzrx = null;
    private final Class<?>[] zzry;
    private CountDownLatch zzrz = new CountDownLatch(1);

    public zzea(zzdb zzdb, String str, String str2, Class<?>... clsArr) {
        this.zzpJ = zzdb;
        this.className = str;
        this.zzrv = str2;
        this.zzry = clsArr;
        this.zzpJ.zzC().submit(new zzeb(this));
    }

    /* access modifiers changed from: private */
    public final void zzX() {
        try {
            Class loadClass = this.zzpJ.zzD().loadClass(zzb(this.zzpJ.zzF(), this.className));
            if (loadClass != null) {
                this.zzrx = loadClass.getMethod(zzb(this.zzpJ.zzF(), this.zzrv), this.zzry);
                if (this.zzrx == null) {
                    this.zzrz.countDown();
                } else {
                    this.zzrz.countDown();
                }
            }
        } catch (zzcx e) {
        } catch (UnsupportedEncodingException e2) {
        } catch (ClassNotFoundException e3) {
        } catch (NoSuchMethodException e4) {
        } catch (NullPointerException e5) {
        } finally {
            this.zzrz.countDown();
        }
    }

    private final String zzb(byte[] bArr, String str) throws zzcx, UnsupportedEncodingException {
        return new String(this.zzpJ.zzE().zza(bArr, str), "UTF-8");
    }

    public final Method zzY() {
        if (this.zzrx != null) {
            return this.zzrx;
        }
        try {
            if (this.zzrz.await(2, TimeUnit.SECONDS)) {
                return this.zzrx;
            }
            return null;
        } catch (InterruptedException e) {
            return null;
        }
    }
}
