package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbr;
import com.tencent.component.net.download.multiplex.http.ContentType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

final class zzbw extends zzbr {
    private static final String ID = zzbf.HASH.toString();
    private static final String zzbEH = zzbg.ARG0.toString();
    private static final String zzbEJ = zzbg.INPUT_FORMAT.toString();
    private static final String zzbEN = zzbg.ALGORITHM.toString();

    public zzbw() {
        super(ID, zzbEH);
    }

    public final boolean zzAE() {
        return true;
    }

    public final zzbr zzo(Map<String, zzbr> map) {
        byte[] decode;
        zzbr zzbr = map.get(zzbEH);
        if (zzbr == null || zzbr == zzgk.zzCh()) {
            return zzgk.zzCh();
        }
        String zzb = zzgk.zzb(zzbr);
        zzbr zzbr2 = map.get(zzbEN);
        String zzb2 = zzbr2 == null ? "MD5" : zzgk.zzb(zzbr2);
        zzbr zzbr3 = map.get(zzbEJ);
        String zzb3 = zzbr3 == null ? ContentType.TYPE_TEXT : zzgk.zzb(zzbr3);
        if (ContentType.TYPE_TEXT.equals(zzb3)) {
            decode = zzb.getBytes();
        } else if ("base16".equals(zzb3)) {
            decode = zzo.decode(zzb);
        } else {
            String valueOf = String.valueOf(zzb3);
            zzdj.e(valueOf.length() != 0 ? "Hash: unknown input format: ".concat(valueOf) : new String("Hash: unknown input format: "));
            return zzgk.zzCh();
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(zzb2);
            instance.update(decode);
            return zzgk.zzI(zzo.encode(instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            String valueOf2 = String.valueOf(zzb2);
            zzdj.e(valueOf2.length() != 0 ? "Hash: unknown algorithm: ".concat(valueOf2) : new String("Hash: unknown algorithm: "));
            return zzgk.zzCh();
        }
    }
}
