package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public final class w extends zzcxq {
    static String encode(String str, String str2) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        Charset forName = Charset.forName("UTF-8");
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (str2.indexOf(charAt) != -1) {
                sb.append((char) charAt);
                i++;
            } else {
                int i2 = 1;
                if (Character.isHighSurrogate((char) charAt)) {
                    if (i + 1 >= str.length()) {
                        throw new UnsupportedEncodingException();
                    } else if (Character.isLowSurrogate(str.charAt(i + 1))) {
                        i2 = 2;
                    } else {
                        throw new UnsupportedEncodingException();
                    }
                }
                byte[] bytes = str.substring(i, i + i2).getBytes(forName);
                for (int i3 = 0; i3 < bytes.length; i3++) {
                    sb.append("%");
                    sb.append(Character.toUpperCase(Character.forDigit((bytes[i3] >> 4) & 15, 16)));
                    sb.append(Character.toUpperCase(Character.forDigit(bytes[i3] & 15, 16)));
                }
                i += i2;
            }
        }
        return sb.toString().replaceAll(" ", "%20");
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        try {
            return new eb(encode(zzcxp.zzd(dpVarArr.length > 0 ? (dp) zzbo.zzu(dpVarArr[0]) : dv.zzbLu), "#;/?:@&=+$,abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_.!~*'()0123456789"));
        } catch (UnsupportedEncodingException e) {
            return dv.zzbLu;
        }
    }
}
