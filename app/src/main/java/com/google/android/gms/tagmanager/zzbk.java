package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbr;
import com.tencent.component.net.download.multiplex.http.ContentType;
import java.util.Map;

final class zzbk extends zzbr {
    private static final String ID = zzbf.ENCODE.toString();
    private static final String zzbEH = zzbg.ARG0.toString();
    private static final String zzbEI = zzbg.NO_PADDING.toString();
    private static final String zzbEJ = zzbg.INPUT_FORMAT.toString();
    private static final String zzbEK = zzbg.OUTPUT_FORMAT.toString();

    public zzbk() {
        super(ID, zzbEH);
    }

    public final boolean zzAE() {
        return true;
    }

    public final zzbr zzo(Map<String, zzbr> map) {
        byte[] decode;
        String encodeToString;
        zzbr zzbr = map.get(zzbEH);
        if (zzbr == null || zzbr == zzgk.zzCh()) {
            return zzgk.zzCh();
        }
        String zzb = zzgk.zzb(zzbr);
        zzbr zzbr2 = map.get(zzbEJ);
        String zzb2 = zzbr2 == null ? ContentType.TYPE_TEXT : zzgk.zzb(zzbr2);
        zzbr zzbr3 = map.get(zzbEK);
        String zzb3 = zzbr3 == null ? "base16" : zzgk.zzb(zzbr3);
        int i = 2;
        zzbr zzbr4 = map.get(zzbEI);
        if (zzbr4 != null && zzgk.zzf(zzbr4).booleanValue()) {
            i = 3;
        }
        try {
            if (ContentType.TYPE_TEXT.equals(zzb2)) {
                decode = zzb.getBytes();
            } else if ("base16".equals(zzb2)) {
                decode = zzo.decode(zzb);
            } else if ("base64".equals(zzb2)) {
                decode = Base64.decode(zzb, i);
            } else if ("base64url".equals(zzb2)) {
                decode = Base64.decode(zzb, i | 8);
            } else {
                String valueOf = String.valueOf(zzb2);
                zzdj.e(valueOf.length() != 0 ? "Encode: unknown input format: ".concat(valueOf) : new String("Encode: unknown input format: "));
                return zzgk.zzCh();
            }
            if ("base16".equals(zzb3)) {
                encodeToString = zzo.encode(decode);
            } else if ("base64".equals(zzb3)) {
                encodeToString = Base64.encodeToString(decode, i);
            } else if ("base64url".equals(zzb3)) {
                encodeToString = Base64.encodeToString(decode, i | 8);
            } else {
                String valueOf2 = String.valueOf(zzb3);
                zzdj.e(valueOf2.length() != 0 ? "Encode: unknown output format: ".concat(valueOf2) : new String("Encode: unknown output format: "));
                return zzgk.zzCh();
            }
            return zzgk.zzI(encodeToString);
        } catch (IllegalArgumentException e) {
            zzdj.e("Encode: invalid input:");
            return zzgk.zzCh();
        }
    }
}
