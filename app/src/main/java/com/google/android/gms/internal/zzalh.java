package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

@zzzn
@TargetApi(21)
public final class zzalh extends zzalg {
    public zzalh(zzaka zzaka, boolean z) {
        super(zzaka, z);
    }

    @Nullable
    public final WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        if (webResourceRequest == null || webResourceRequest.getUrl() == null) {
            return null;
        }
        return zza(webView, webResourceRequest.getUrl().toString(), webResourceRequest.getRequestHeaders());
    }
}
