package com.google.android.gms.internal;

import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class cu {
    private String zzbEa = "https://www.google-analytics.com";

    private static String zzfC(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            String valueOf = String.valueOf(str);
            zzcvk.e(valueOf.length() != 0 ? "Cannot encode the string: ".concat(valueOf) : new String("Cannot encode the string: "));
            return "";
        }
    }

    public final String zzb(bz bzVar) {
        String sb;
        String str = this.zzbEa;
        String valueOf = String.valueOf("/gtm/android?");
        if (bzVar.zzCL()) {
            sb = bzVar.zzCM();
        } else if (bzVar == null) {
            sb = "";
        } else {
            String trim = !bzVar.zzCN().trim().equals("") ? bzVar.zzCN().trim() : UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
            StringBuilder sb2 = new StringBuilder();
            if (bzVar.zzCJ() != null) {
                sb2.append(bzVar.zzCJ());
            } else {
                sb2.append("id");
            }
            sb2.append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(zzfC(bzVar.getContainerId())).append("&pv=").append(zzfC(trim)).append("&rv=5.0");
            if (bzVar.zzCL()) {
                sb2.append("&gtm_debug=x");
            }
            sb = sb2.toString();
        }
        return new StringBuilder(String.valueOf(str).length() + String.valueOf(valueOf).length() + String.valueOf(sb).length()).append(str).append(valueOf).append(sb).toString();
    }
}
