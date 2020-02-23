package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.tencent.component.net.download.multiplex.http.ContentType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class ax extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        byte[] decode;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length > 0);
        if (dpVarArr[0] == dv.zzbLu) {
            return dv.zzbLu;
        }
        String zzd = zzcxp.zzd(dpVarArr[0]);
        String str = "MD5";
        if (dpVarArr.length > 1) {
            str = dpVarArr[1] == dv.zzbLu ? "MD5" : zzcxp.zzd(dpVarArr[1]);
        }
        String str2 = ContentType.TYPE_TEXT;
        if (dpVarArr.length > 2) {
            str2 = dpVarArr[2] == dv.zzbLu ? ContentType.TYPE_TEXT : zzcxp.zzd(dpVarArr[2]);
        }
        if (ContentType.TYPE_TEXT.equals(str2)) {
            decode = zzd.getBytes();
        } else if ("base16".equals(str2)) {
            decode = zzcue.decode(zzd);
        } else {
            String valueOf = String.valueOf(str2);
            throw new RuntimeException(valueOf.length() != 0 ? "Hash: Unknown input format: ".concat(valueOf) : new String("Hash: Unknown input format: "));
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(decode);
            return new eb(zzcue.encode(instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            String valueOf2 = String.valueOf(str);
            throw new RuntimeException(valueOf2.length() != 0 ? "Hash: Unknown algorithm: ".concat(valueOf2) : new String("Hash: Unknown algorithm: "), e);
        }
    }
}
