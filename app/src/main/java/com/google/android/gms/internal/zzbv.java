package com.google.android.gms.internal;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.android.gms.ads.internal.zzbs;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

final class zzbv {
    private static boolean zzlP = false;
    /* access modifiers changed from: private */
    public static MessageDigest zzlQ = null;
    private static final Object zzlR = new Object();
    private static final Object zzlS = new Object();
    static CountDownLatch zzlT = new CountDownLatch(1);

    static String zza(zzax zzax, String str, boolean z) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] zza;
        byte[] zzc = adp.zzc(zzax);
        if (z) {
            zza = zza(zzc, str, true);
        } else {
            Vector<byte[]> zza2 = zza(zzc, 255);
            if (zza2 == null || zza2.size() == 0) {
                zza = zza(adp.zzc(zzb((long) PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)), str, true);
            } else {
                zzbe zzbe = new zzbe();
                zzbe.zzcJ = new byte[zza2.size()][];
                Iterator<byte[]> it = zza2.iterator();
                int i = 0;
                while (it.hasNext()) {
                    zzbe.zzcJ[i] = zza(it.next(), str, false);
                    i++;
                }
                zzbe.zzcE = zzb(zzc);
                zza = adp.zzc(zzbe);
            }
        }
        return zzbt.zza(zza, true);
    }

    static String zza(String str, String str2, boolean z) {
        byte[] zza = zza(str, str2, true, ((Boolean) zzbs.zzbL().zzd(zzmo.zzEO)).booleanValue());
        return zza != null ? zzbt.zza(zza, true) : Integer.toString(7);
    }

    private static Vector<byte[]> zza(byte[] bArr, int i) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        int length = ((bArr.length + 255) - 1) / 255;
        Vector<byte[]> vector = new Vector<>();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 * 255;
            try {
                vector.add(Arrays.copyOfRange(bArr, i3, bArr.length - i3 > 255 ? i3 + 255 : bArr.length));
                i2++;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        return vector;
    }

    private static byte[] zza(String str, String str2, boolean z, boolean z2) {
        zzbb zzbb = new zzbb();
        try {
            zzbb.zzcC = str.length() < 3 ? str.getBytes("ISO-8859-1") : zzbt.zza(str, true);
            zzbb.zzcD = str2.length() < 3 ? str2.getBytes("ISO-8859-1") : zzbt.zza(str2, true);
            return adp.zzc(zzbb);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static byte[] zza(byte[] bArr, String str, boolean z) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] array;
        int i = z ? 239 : 255;
        if (bArr.length > i) {
            bArr = adp.zzc(zzb((long) PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM));
        }
        if (bArr.length < i) {
            byte[] bArr2 = new byte[(i - bArr.length)];
            new SecureRandom().nextBytes(bArr2);
            array = ByteBuffer.allocate(i + 1).put((byte) bArr.length).put(bArr).put(bArr2).array();
        } else {
            array = ByteBuffer.allocate(i + 1).put((byte) bArr.length).put(bArr).array();
        }
        if (z) {
            array = ByteBuffer.allocate(256).put(zzb(array)).put(array).array();
        }
        byte[] bArr3 = new byte[256];
        new zzby().zza(array, bArr3);
        if (str != null && str.length() > 0) {
            if (str.length() > 32) {
                str = str.substring(0, 32);
            }
            new acg(str.getBytes("UTF-8")).zzG(bArr3);
        }
        return bArr3;
    }

    private static zzax zzb(long j) {
        zzax zzax = new zzax();
        zzax.zzbq = Long.valueOf(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
        return zzax;
    }

    public static byte[] zzb(byte[] bArr) throws NoSuchAlgorithmException {
        byte[] digest;
        synchronized (zzlR) {
            MessageDigest zzx = zzx();
            if (zzx == null) {
                throw new NoSuchAlgorithmException("Cannot compute hash");
            }
            zzx.reset();
            zzx.update(bArr);
            digest = zzlQ.digest();
        }
        return digest;
    }

    static void zzw() {
        synchronized (zzlS) {
            if (!zzlP) {
                zzlP = true;
                new Thread(new zzbx()).start();
            }
        }
    }

    private static MessageDigest zzx() {
        zzw();
        boolean z = false;
        try {
            z = zzlT.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        if (z && zzlQ != null) {
            return zzlQ;
        }
        return null;
    }
}
