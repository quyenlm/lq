package com.google.android.gms.internal;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.internal.zzbo;
import java.io.UnsupportedEncodingException;

public final class abv {
    @NonNull
    public static String zzhI(@Nullable String str) throws UnsupportedEncodingException {
        return TextUtils.isEmpty(str) ? "" : zzhJ(Uri.encode(str));
    }

    @NonNull
    public static String zzhJ(@NonNull String str) {
        zzbo.zzu(str);
        return str.replace("%2F", Constants.URL_PATH_DELIMITER);
    }

    @NonNull
    public static String zzhK(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!str.startsWith(Constants.URL_PATH_DELIMITER) && !str.endsWith(Constants.URL_PATH_DELIMITER) && !str.contains("//")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : str.split(Constants.URL_PATH_DELIMITER)) {
            if (!TextUtils.isEmpty(str2)) {
                if (sb.length() > 0) {
                    sb.append(Constants.URL_PATH_DELIMITER).append(str2);
                } else {
                    sb.append(str2);
                }
            }
        }
        return sb.toString();
    }
}
