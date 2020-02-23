package com.tencent.msdk.stat;

import com.google.android.gms.cast.framework.media.NotificationOptions;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

class l extends DefaultConnectionKeepAliveStrategy {
    final /* synthetic */ k a;

    l(k kVar) {
        this.a = kVar;
    }

    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        long keepAliveDuration = super.getKeepAliveDuration(httpResponse, httpContext);
        return keepAliveDuration == -1 ? NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS : keepAliveDuration;
    }
}
