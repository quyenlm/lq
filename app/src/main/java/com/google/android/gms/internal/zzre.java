package com.google.android.gms.internal;

import android.content.Context;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzre implements zzrd {
    private final Context mContext;
    private final zzaje zztW;

    public zzre(Context context, zzaje zzaje) {
        this.mContext = context;
        this.zztW = zzaje;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.zzrj zza(com.google.android.gms.internal.zzri r9) {
        /*
            r8 = this;
            r2 = 0
            r3 = 0
            java.net.URL r0 = r9.zzez()     // Catch:{ Exception -> 0x00f5 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x00f5 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x00f5 }
            com.google.android.gms.internal.zzagz r1 = com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            android.content.Context r2 = r8.mContext     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            com.google.android.gms.internal.zzaje r4 = r8.zztW     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.String r4 = r4.zzaP     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r5 = 0
            r1.zza((android.content.Context) r2, (java.lang.String) r4, (boolean) r5, (java.net.HttpURLConnection) r0)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.util.ArrayList r1 = r9.zzeA()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            int r4 = r1.size()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
        L_0x0024:
            if (r3 >= r4) goto L_0x004d
            java.lang.Object r2 = r1.get(r3)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            int r3 = r3 + 1
            com.google.android.gms.internal.zzrh r2 = (com.google.android.gms.internal.zzrh) r2     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.String r5 = r2.getKey()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.String r2 = r2.getValue()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r0.addRequestProperty(r5, r2)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            goto L_0x0024
        L_0x003a:
            r1 = move-exception
            r2 = r0
        L_0x003c:
            com.google.android.gms.internal.zzrj r0 = new com.google.android.gms.internal.zzrj     // Catch:{ all -> 0x00f2 }
            r3 = 0
            r4 = 0
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00f2 }
            r0.<init>(r8, r3, r4, r1)     // Catch:{ all -> 0x00f2 }
            if (r2 == 0) goto L_0x004c
            r2.disconnect()
        L_0x004c:
            return r0
        L_0x004d:
            java.lang.String r1 = r9.zzeB()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            if (r1 != 0) goto L_0x0076
            r1 = 1
            r0.setDoOutput(r1)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.String r1 = r9.zzeB()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            byte[] r1 = r1.getBytes()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            int r2 = r1.length     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r0.setFixedLengthStreamingMode(r2)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.io.OutputStream r3 = r0.getOutputStream()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r2.<init>(r3)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r2.write(r1)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r2.close()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
        L_0x0076:
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r4.<init>()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.util.Map r1 = r0.getHeaderFields()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            if (r1 == 0) goto L_0x00c6
            java.util.Map r1 = r0.getHeaderFields()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.util.Set r1 = r1.entrySet()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.util.Iterator r5 = r1.iterator()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
        L_0x008d:
            boolean r1 = r5.hasNext()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            if (r1 == 0) goto L_0x00c6
            java.lang.Object r1 = r5.next()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.Object r2 = r1.getValue()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.util.List r2 = (java.util.List) r2     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.util.Iterator r6 = r2.iterator()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
        L_0x00a3:
            boolean r2 = r6.hasNext()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            if (r2 == 0) goto L_0x008d
            java.lang.Object r2 = r6.next()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            com.google.android.gms.internal.zzrh r7 = new com.google.android.gms.internal.zzrh     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.Object r3 = r1.getKey()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r7.<init>(r3, r2)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r4.add(r7)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            goto L_0x00a3
        L_0x00be:
            r1 = move-exception
            r2 = r0
        L_0x00c0:
            if (r2 == 0) goto L_0x00c5
            r2.disconnect()
        L_0x00c5:
            throw r1
        L_0x00c6:
            com.google.android.gms.internal.zzrk r2 = new com.google.android.gms.internal.zzrk     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.String r1 = r9.zzey()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            int r3 = r0.getResponseCode()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.io.InputStream r6 = r0.getInputStream()     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r5.<init>(r6)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            java.lang.String r5 = com.google.android.gms.internal.zzagz.zza((java.io.InputStreamReader) r5)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r2.<init>(r1, r3, r4, r5)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            com.google.android.gms.internal.zzrj r1 = new com.google.android.gms.internal.zzrj     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            r3 = 1
            r4 = 0
            r1.<init>(r8, r3, r2, r4)     // Catch:{ Exception -> 0x003a, all -> 0x00be }
            if (r0 == 0) goto L_0x00ef
            r0.disconnect()
        L_0x00ef:
            r0 = r1
            goto L_0x004c
        L_0x00f2:
            r0 = move-exception
            r1 = r0
            goto L_0x00c0
        L_0x00f5:
            r0 = move-exception
            r1 = r0
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzre.zza(com.google.android.gms.internal.zzri):com.google.android.gms.internal.zzrj");
    }

    private static JSONObject zza(zzrk zzrk) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("http_request_id", zzrk.zzey());
            if (zzrk.getBody() != null) {
                jSONObject.put("body", zzrk.getBody());
            }
            JSONArray jSONArray = new JSONArray();
            for (zzrh next : zzrk.zzeD()) {
                jSONArray.put(new JSONObject().put("key", next.getKey()).put("value", next.getValue()));
            }
            jSONObject.put("headers", jSONArray);
            jSONObject.put("response_code", zzrk.getResponseCode());
        } catch (JSONException e) {
            zzafr.zzb("Error constructing JSON for http response.", e);
        }
        return jSONObject;
    }

    private static zzri zzc(JSONObject jSONObject) {
        URL url;
        String optString = jSONObject.optString("http_request_id");
        String optString2 = jSONObject.optString("url");
        String optString3 = jSONObject.optString("post_body", (String) null);
        try {
            url = new URL(optString2);
        } catch (MalformedURLException e) {
            zzafr.zzb("Error constructing http request.", e);
            url = null;
        }
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("headers");
        if (optJSONArray == null) {
            optJSONArray = new JSONArray();
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(new zzrh(optJSONObject.optString("key"), optJSONObject.optString("value")));
            }
        }
        return new zzri(optString, url, arrayList, optString3);
    }

    public final JSONObject zzR(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = new JSONObject();
            String str2 = "";
            try {
                str2 = jSONObject.optString("http_request_id");
                zzrj zza = zza(zzc(jSONObject));
                if (zza.isSuccess()) {
                    jSONObject2.put("response", zza(zza.zzeC()));
                    jSONObject2.put("success", true);
                    return jSONObject2;
                }
                jSONObject2.put("response", new JSONObject().put("http_request_id", str2));
                jSONObject2.put("success", false);
                jSONObject2.put("reason", zza.getReason());
                return jSONObject2;
            } catch (Exception e) {
                try {
                    jSONObject2.put("response", new JSONObject().put("http_request_id", str2));
                    jSONObject2.put("success", false);
                    jSONObject2.put("reason", e.toString());
                    return jSONObject2;
                } catch (JSONException e2) {
                    return jSONObject2;
                }
            }
        } catch (JSONException e3) {
            zzafr.e("The request is not a valid JSON.");
            try {
                return new JSONObject().put("success", false);
            } catch (JSONException e4) {
                return new JSONObject();
            }
        }
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        zzagt.zza((Runnable) new zzrf(this, map, zzaka));
    }
}
