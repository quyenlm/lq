package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.google.android.gms.ads.internal.zzbs;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzzn
@TargetApi(11)
public class zzalg extends zzakb {
    public zzalg(zzaka zzaka, boolean z) {
        super(zzaka, z);
    }

    /* access modifiers changed from: protected */
    public final WebResourceResponse zza(WebView webView, String str, @Nullable Map<String, String> map) {
        if (!(webView instanceof zzaka)) {
            zzafr.zzaT("Tried to intercept request from a WebView that wasn't an AdWebView.");
            return null;
        }
        zzaka zzaka = (zzaka) webView;
        if (this.zztr != null) {
            this.zztr.zza(str, map, 1);
        }
        if (!"mraid.js".equalsIgnoreCase(new File(str).getName())) {
            return super.shouldInterceptRequest(webView, str);
        }
        if (zzaka.zziw() != null) {
            zzaka.zziw().zzfL();
        }
        String str2 = zzaka.zzam().zzAt ? (String) zzbs.zzbL().zzd(zzmo.zzCP) : zzaka.zziA() ? (String) zzbs.zzbL().zzd(zzmo.zzCO) : (String) zzbs.zzbL().zzd(zzmo.zzCN);
        try {
            Context context = zzaka.getContext();
            String str3 = zzaka.zziz().zzaP;
            HashMap hashMap = new HashMap();
            hashMap.put("User-Agent", zzbs.zzbz().zzs(context, str3));
            hashMap.put("Cache-Control", "max-stale=3600");
            String str4 = (String) new zzaie(context).zzb(str2, hashMap).get(60, TimeUnit.SECONDS);
            if (str4 == null) {
                return null;
            }
            return new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream(str4.getBytes("UTF-8")));
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            String valueOf = String.valueOf(e.getMessage());
            zzafr.zzaT(valueOf.length() != 0 ? "Could not fetch MRAID JS. ".concat(valueOf) : new String("Could not fetch MRAID JS. "));
            return null;
        }
    }
}
