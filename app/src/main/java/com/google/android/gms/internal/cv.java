package com.google.android.gms.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

final class cv implements cw {
    private HttpURLConnection zzbKR;
    private InputStream zzbKS = null;

    cv() {
    }

    public final void close() {
        HttpURLConnection httpURLConnection = this.zzbKR;
        try {
            if (this.zzbKS != null) {
                this.zzbKS.close();
            }
        } catch (IOException e) {
            IOException iOException = e;
            String valueOf = String.valueOf(iOException.getMessage());
            zzcvk.zzb(valueOf.length() != 0 ? "HttpUrlConnectionNetworkClient: Error when closing http input stream: ".concat(valueOf) : new String("HttpUrlConnectionNetworkClient: Error when closing http input stream: "), iOException);
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    public final InputStream zzfU(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setConnectTimeout(20000);
        this.zzbKR = httpURLConnection;
        HttpURLConnection httpURLConnection2 = this.zzbKR;
        int responseCode = httpURLConnection2.getResponseCode();
        if (responseCode == 200) {
            this.zzbKS = httpURLConnection2.getInputStream();
            return this.zzbKS;
        }
        String sb = new StringBuilder(25).append("Bad response: ").append(responseCode).toString();
        if (responseCode == 404) {
            throw new FileNotFoundException(sb);
        } else if (responseCode == 503) {
            throw new da(sb);
        } else {
            throw new IOException(sb);
        }
    }
}
