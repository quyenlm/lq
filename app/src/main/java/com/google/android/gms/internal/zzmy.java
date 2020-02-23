package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.text.TextUtils;

final class zzmy extends zzmv {
    zzmy() {
    }

    @Nullable
    private static String zzN(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int i = 0;
        int length = str.length();
        while (i < str.length() && str.charAt(i) == ',') {
            i++;
        }
        while (length > 0 && str.charAt(length - 1) == ',') {
            length--;
        }
        return (i == 0 && length == str.length()) ? str : str.substring(i, length);
    }

    public final String zzg(@Nullable String str, String str2) {
        String zzN = zzN(str);
        String zzN2 = zzN(str2);
        return TextUtils.isEmpty(zzN) ? zzN2 : TextUtils.isEmpty(zzN2) ? zzN : new StringBuilder(String.valueOf(zzN).length() + 1 + String.valueOf(zzN2).length()).append(zzN).append(",").append(zzN2).toString();
    }
}
