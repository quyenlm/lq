package com.google.android.gms.internal;

import com.appsflyer.share.Constants;
import java.util.ArrayList;
import java.util.List;

public final class ok {
    public static String zzR(List<String> list) {
        if (list.isEmpty()) {
            return Constants.URL_PATH_DELIMITER;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String next : list) {
            if (!z) {
                sb.append(Constants.URL_PATH_DELIMITER);
            }
            z = false;
            sb.append(next);
        }
        return sb.toString();
    }

    public static Long zzah(Object obj) {
        if (obj instanceof Integer) {
            return Long.valueOf((long) ((Integer) obj).intValue());
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        return null;
    }

    public static void zzc(boolean z, String str, Object... objArr) {
        if (!z) {
            String valueOf = String.valueOf(String.format(str, objArr));
            throw new AssertionError(valueOf.length() != 0 ? "hardAssert failed: ".concat(valueOf) : new String("hardAssert failed: "));
        }
    }

    public static List<String> zzgG(String str) {
        ArrayList arrayList = new ArrayList();
        String[] split = str.split(Constants.URL_PATH_DELIMITER);
        for (int i = 0; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                arrayList.add(split[i]);
            }
        }
        return arrayList;
    }
}
