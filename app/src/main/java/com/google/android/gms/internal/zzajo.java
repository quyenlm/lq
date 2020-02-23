package com.google.android.gms.internal;

import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.HttpHost;

@zzzn
public final class zzajo {
    public static HttpURLConnection zzb(String str, int i) throws IOException {
        URL url = new URL(str);
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (i3 <= 20) {
                URLConnection openConnection = url.openConnection();
                openConnection.setConnectTimeout(i);
                openConnection.setReadTimeout(i);
                if (!(openConnection instanceof HttpURLConnection)) {
                    throw new IOException("Invalid protocol.");
                }
                HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                httpURLConnection.setInstanceFollowRedirects(false);
                if (httpURLConnection.getResponseCode() / 100 != 3) {
                    return httpURLConnection;
                }
                String headerField = httpURLConnection.getHeaderField("Location");
                if (headerField == null) {
                    throw new IOException("Missing Location header in redirect");
                }
                URL url2 = new URL(url, headerField);
                String protocol = url2.getProtocol();
                if (protocol == null) {
                    throw new IOException("Protocol is null");
                } else if (protocol.equals(HttpHost.DEFAULT_SCHEME_NAME) || protocol.equals(VKApiConst.HTTPS)) {
                    String valueOf = String.valueOf(headerField);
                    zzafr.zzaC(valueOf.length() != 0 ? "Redirecting to ".concat(valueOf) : new String("Redirecting to "));
                    httpURLConnection.disconnect();
                    i2 = i3;
                    url = url2;
                } else {
                    String valueOf2 = String.valueOf(protocol);
                    throw new IOException(valueOf2.length() != 0 ? "Unsupported scheme: ".concat(valueOf2) : new String("Unsupported scheme: "));
                }
            } else {
                throw new IOException("Too many redirects (20)");
            }
        }
    }
}
