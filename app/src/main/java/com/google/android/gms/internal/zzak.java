package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class zzak implements zzan {
    private HttpClient zzaA;

    public zzak(HttpClient httpClient) {
        this.zzaA = httpClient;
    }

    private static void zza(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, zzp<?> zzp) throws zza {
        byte[] zzg = zzp.zzg();
        if (zzg != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(zzg));
        }
    }

    private static void zza(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String next : map.keySet()) {
            httpUriRequest.setHeader(next, map.get(next));
        }
    }

    public final HttpResponse zza(zzp<?> zzp, Map<String, String> map) throws IOException, zza {
        HttpPost httpPost;
        switch (zzp.getMethod()) {
            case -1:
                httpPost = new HttpGet(zzp.getUrl());
                break;
            case 0:
                httpPost = new HttpGet(zzp.getUrl());
                break;
            case 1:
                HttpPost httpPost2 = new HttpPost(zzp.getUrl());
                httpPost2.addHeader("Content-Type", zzp.zzf());
                zza((HttpEntityEnclosingRequestBase) httpPost2, zzp);
                httpPost = httpPost2;
                break;
            case 2:
                HttpPut httpPut = new HttpPut(zzp.getUrl());
                httpPut.addHeader("Content-Type", zzp.zzf());
                zza((HttpEntityEnclosingRequestBase) httpPut, zzp);
                httpPost = httpPut;
                break;
            case 3:
                httpPost = new HttpDelete(zzp.getUrl());
                break;
            case 4:
                httpPost = new HttpHead(zzp.getUrl());
                break;
            case 5:
                httpPost = new HttpOptions(zzp.getUrl());
                break;
            case 6:
                httpPost = new HttpTrace(zzp.getUrl());
                break;
            case 7:
                zzal zzal = new zzal(zzp.getUrl());
                zzal.addHeader("Content-Type", zzp.zzf());
                zza((HttpEntityEnclosingRequestBase) zzal, zzp);
                httpPost = zzal;
                break;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
        zza((HttpUriRequest) httpPost, map);
        zza((HttpUriRequest) httpPost, zzp.getHeaders());
        HttpParams params = httpPost.getParams();
        int zzi = zzp.zzi();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, zzi);
        return this.zzaA.execute(httpPost);
    }
}
