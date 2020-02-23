package com.google.android.gms.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.vk.sdk.api.VKApiConst;
import org.apache.http.HttpHost;

public final class zzare {
    public static String zzbN(String str) {
        boolean z = false;
        zzbo.zzb(!TextUtils.isEmpty(str), (Object) "account type cannot be empty");
        String scheme = Uri.parse(str).getScheme();
        if (HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(scheme) || VKApiConst.HTTPS.equalsIgnoreCase(scheme)) {
            z = true;
        }
        zzbo.zzb(z, (Object) "Account type must be an http or https URI");
        return str;
    }
}
