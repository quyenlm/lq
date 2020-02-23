package com.google.android.gms.internal;

import android.util.Base64;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.component.net.download.multiplex.http.ContentType;

public final class at extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        byte[] decode;
        String encodeToString;
        boolean z = true;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length > 0);
        String zzd = zzcxp.zzd(dpVarArr[0]);
        String str = ContentType.TYPE_TEXT;
        if (dpVarArr.length > 1) {
            str = zzcxp.zzd(dpVarArr[1]);
        }
        String str2 = "base16";
        if (dpVarArr.length > 2) {
            str2 = zzcxp.zzd(dpVarArr[2]);
        }
        if (dpVarArr.length <= 3 || !zzcxp.zza(dpVarArr[3])) {
            z = false;
        }
        int i = z ? 3 : 2;
        try {
            if (ContentType.TYPE_TEXT.equals(str)) {
                decode = zzd.getBytes();
            } else if ("base16".equals(str)) {
                decode = zzcue.decode(zzd);
            } else if ("base64".equals(str)) {
                decode = Base64.decode(zzd, i);
            } else if ("base64url".equals(str)) {
                decode = Base64.decode(zzd, i | 8);
            } else {
                String valueOf = String.valueOf(str);
                throw new UnsupportedOperationException(valueOf.length() != 0 ? "Encode: unknown input format: ".concat(valueOf) : new String("Encode: unknown input format: "));
            }
            if ("base16".equals(str2)) {
                encodeToString = zzcue.encode(decode);
            } else if ("base64".equals(str2)) {
                encodeToString = Base64.encodeToString(decode, i);
            } else if ("base64url".equals(str2)) {
                encodeToString = Base64.encodeToString(decode, i | 8);
            } else {
                String valueOf2 = String.valueOf(str2);
                throw new RuntimeException(valueOf2.length() != 0 ? "Encode: unknown output format: ".concat(valueOf2) : new String("Encode: unknown output format: "));
            }
            return new eb(encodeToString);
        } catch (IllegalArgumentException e) {
            String valueOf3 = String.valueOf(str);
            throw new RuntimeException(valueOf3.length() != 0 ? "Encode: invalid input:".concat(valueOf3) : new String("Encode: invalid input:"));
        }
    }
}
