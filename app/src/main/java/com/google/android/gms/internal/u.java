package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public final class u extends zzcxq {
    static String decode(String str, String str2) throws UnsupportedEncodingException {
        Charset forName = Charset.forName("UTF-8");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt != '%') {
                sb.append((char) charAt);
                i++;
            } else {
                byte zzv = zzv(str, i);
                int i2 = i + 3;
                if ((zzv & 128) != 0) {
                    int i3 = 0;
                    while (((zzv << i3) & 128) != 0) {
                        i3++;
                    }
                    if (i3 < 2 || i3 > 4) {
                        throw new UnsupportedEncodingException();
                    }
                    byte[] bArr = new byte[i3];
                    bArr[0] = zzv;
                    for (int i4 = 1; i4 < i3; i4++) {
                        byte zzv2 = zzv(str, i2);
                        i2 += 3;
                        if ((zzv2 & 192) != 128) {
                            throw new UnsupportedEncodingException();
                        }
                        bArr[i4] = zzv2;
                    }
                    CharBuffer decode = forName.decode(ByteBuffer.wrap(bArr));
                    if (decode.length() != 1 || str2.indexOf(decode.charAt(0)) == -1) {
                        sb.append(decode);
                        i = i2;
                    } else {
                        sb.append(str.substring(i, i2));
                        i = i2;
                    }
                } else if (str2.indexOf(zzv) == -1) {
                    sb.append((char) zzv);
                    i = i2;
                } else {
                    sb.append(str.substring(i2 - 3, i2));
                    i = i2;
                }
            }
        }
        return sb.toString();
    }

    private static byte zzv(String str, int i) throws UnsupportedEncodingException {
        if (i + 3 > str.length() || str.charAt(i) != '%') {
            throw new UnsupportedEncodingException();
        }
        String substring = str.substring(i + 1, i + 3);
        if (substring.charAt(0) == '+' || substring.charAt(0) == '-') {
            throw new UnsupportedEncodingException();
        }
        try {
            return (byte) Integer.parseInt(substring, 16);
        } catch (NumberFormatException e) {
            throw new UnsupportedEncodingException();
        }
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        try {
            return new eb(decode(zzcxp.zzd(dpVarArr.length > 0 ? (dp) zzbo.zzu(dpVarArr[0]) : dv.zzbLu), "#;/?:@&=+$,"));
        } catch (UnsupportedEncodingException e) {
            return dv.zzbLu;
        }
    }
}
