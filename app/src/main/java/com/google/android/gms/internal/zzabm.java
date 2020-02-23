package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.internal.js.zzl;
import com.google.android.gms.ads.internal.zzbs;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.imsdk.framework.consts.InnerErrorCode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzabm extends zzaan {
    private static zzabm zzUx;
    private static final Object zzuF = new Object();
    private final Context mContext;
    private final zzl zzLG;
    private final zzabl zzUy;
    private final zzmb zzUz;

    private zzabm(Context context, zzmb zzmb, zzabl zzabl) {
        this(context, zzmb, zzabl, zzbs.zzbO().zzb(context, new zzaje(11020208, 11020208, true)).zzff());
    }

    private zzabm(Context context, zzmb zzmb, zzabl zzabl, zzl zzl) {
        this.mContext = context;
        this.zzUy = zzabl;
        this.zzUz = zzmb;
        this.zzLG = zzl;
    }

    private static zzaai zza(Context context, zzl zzl, zzmb zzmb, zzabl zzabl, zzaae zzaae) {
        String string;
        zzafr.zzaC("Starting ad request from service using: AFMA_getAd");
        zznb zznb = new zznb(((Boolean) zzbs.zzbL().zzd(zzmo.zzCQ)).booleanValue(), "load_ad", zzaae.zzvX.zzAs);
        if (zzaae.versionCode > 10 && zzaae.zzSP != -1) {
            zznb.zza(zznb.zzc(zzaae.zzSP), "cts");
        }
        zzmz zzdS = zznb.zzdS();
        zzajm<Bundle> zzj = zzabl.zzUv.zzj(context);
        Future<zzacn> zzo = zzabl.zzUu.zzo(context);
        zzajm<String> zzaD = zzabl.zzUp.zzaD(zzaae.zzSA.packageName);
        zzajm<String> zze = zzabl.zzUw.zze(zzaae);
        Future<zzacb> zzn = zzbs.zzbI().zzn(context);
        zzajm zzajh = new zzajh(null);
        Bundle bundle = zzaae.zzSz.extras;
        zzajm zza = (!zzaae.zzSV || (bundle != null && bundle.getString("_ad") != null)) ? zzajh : zzabl.zzUs.zza(zzaae.applicationInfo);
        zzajm zzz = ((Boolean) zzbs.zzbL().zzd(zzmo.zzDM)).booleanValue() ? zzabl.zzUw.zzz(context) : new zzajh(null);
        Bundle bundle2 = (zzaae.versionCode < 4 || zzaae.zzSG == null) ? null : zzaae.zzSG;
        ((Boolean) zzbs.zzbL().zzd(zzmo.zzDg)).booleanValue();
        zzbs.zzbz();
        if (zzagz.zzc(context, context.getPackageName(), "android.permission.ACCESS_NETWORK_STATE") && ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo() == null) {
            zzafr.zzaC("Device is offline.");
        }
        String uuid = zzaae.versionCode >= 7 ? zzaae.zzSM : UUID.randomUUID().toString();
        zzabu zzabu = new zzabu(uuid, zzaae.applicationInfo.packageName);
        if (zzaae.zzSz.extras != null && (string = zzaae.zzSz.extras.getString("_ad")) != null) {
            return zzabt.zza(context, zzaae, string);
        }
        List<String> zza2 = zzabl.zzUq.zza(zzaae);
        Bundle bundle3 = (Bundle) zzaji.zza(zzj, null, ((Long) zzbs.zzbL().zzd(zzmo.zzGb)).longValue(), TimeUnit.MILLISECONDS);
        zzacn zzacn = (zzacn) zzaji.zza(zzo, null, ((Long) zzbs.zzbL().zzd(zzmo.zzEF)).longValue(), TimeUnit.MILLISECONDS);
        Location location = (Location) zzaji.zza(zza, null, ((Long) zzbs.zzbL().zzd(zzmo.zzFK)).longValue(), TimeUnit.MILLISECONDS);
        AdvertisingIdClient.Info info = (AdvertisingIdClient.Info) zzaji.zza(zzz, null, ((Long) zzbs.zzbL().zzd(zzmo.zzDN)).longValue(), TimeUnit.MILLISECONDS);
        String str = (String) zzaji.zza(zze, null);
        String str2 = (String) zzaji.zza(zzaD, null);
        zzacb zzacb = (zzacb) zzaji.zza(zzn, null);
        if (zzacb == null) {
            zzafr.zzaT("Error fetching device info. This is not recoverable.");
            return new zzaai(0);
        }
        zzabk zzabk = new zzabk();
        zzabk.zzUj = zzaae;
        zzabk.zzUk = zzacb;
        zzabk.zzUh = zzacn;
        zzabk.zzzV = location;
        zzabk.zzUg = bundle3;
        zzabk.zzSB = str;
        zzabk.zzqi = info;
        if (zza2 == null) {
            zzabk.zzSN.clear();
        }
        zzabk.zzSN = zza2;
        zzabk.zzSG = bundle2;
        zzabk.zzUi = str2;
        zzabk.zzUl = zzabl.zzUo.zzf(context);
        zzabk.zzUm = zzabl.zzUm;
        JSONObject zza3 = zzabt.zza(context, zzabk);
        if (zza3 == null) {
            return new zzaai(0);
        }
        if (zzaae.versionCode < 7) {
            try {
                zza3.put("request_id", uuid);
            } catch (JSONException e) {
            }
        }
        String jSONObject = zza3.toString();
        zznb.zza(zzdS, "arc");
        zzagz.zzZr.post(new zzabn(zzl, zzabu, zznb, zznb.zzdS(), jSONObject));
        try {
            zzaca zzaca = zzabu.zzgG().get(10, TimeUnit.SECONDS);
            if (zzaca == null) {
                return new zzaai(0);
            }
            if (zzaca.getErrorCode() != -2) {
                zzaai zzaai = new zzaai(zzaca.getErrorCode());
                zzagz.zzZr.post(new zzabq(zzabl, context, zzabu, zzaae));
                return zzaai;
            }
            if (zznb.zzdW() != null) {
                zznb.zza(zznb.zzdW(), "rur");
            }
            zzaai zzaai2 = null;
            if (!TextUtils.isEmpty(zzaca.zzgL())) {
                zzaai2 = zzabt.zza(context, zzaae, zzaca.zzgL());
            }
            if (zzaai2 == null && !TextUtils.isEmpty(zzaca.getUrl())) {
                zzaai2 = zza(zzaae, context, zzaae.zzvT.zzaP, zzaca.getUrl(), str2, zzaca, zznb, zzabl);
            }
            if (zzaai2 == null) {
                zzaai2 = new zzaai(0);
            }
            zznb.zza(zzdS, "tts");
            zzaai2.zzTB = zznb.zzdU();
            zzagz.zzZr.post(new zzabq(zzabl, context, zzabu, zzaae));
            return zzaai2;
        } catch (Exception e2) {
            return new zzaai(0);
        } finally {
            zzagz.zzZr.post(new zzabq(zzabl, context, zzabu, zzaae));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:109:?, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a4, code lost:
        r3 = r7.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r5 = new java.io.InputStreamReader(r2.getInputStream());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        com.google.android.gms.ads.internal.zzbs.zzbz();
        r6 = com.google.android.gms.internal.zzagz.zza(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        com.google.android.gms.common.util.zzn.closeQuietly(r5);
        zza(r3, r12, r6, r9);
        r8.zza(r3, r12, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c2, code lost:
        if (r19 == null) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c4, code lost:
        r19.zza(r4, "ufe");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d1, code lost:
        r3 = r8.zze(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x010f, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0110, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        com.google.android.gms.common.util.zzn.closeQuietly(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0114, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01a6, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01a7, code lost:
        r4 = r5;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:63:0x0111=Splitter:B:63:0x0111, B:54:0x0106=Splitter:B:54:0x0106} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.zzaai zza(com.google.android.gms.internal.zzaae r13, android.content.Context r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, com.google.android.gms.internal.zzaca r18, com.google.android.gms.internal.zznb r19, com.google.android.gms.internal.zzabl r20) {
        /*
            if (r19 == 0) goto L_0x00da
            com.google.android.gms.internal.zzmz r2 = r19.zzdS()
            r4 = r2
        L_0x0007:
            com.google.android.gms.internal.zzaby r8 = new com.google.android.gms.internal.zzaby     // Catch:{ IOException -> 0x00e5 }
            java.lang.String r2 = r18.zzgI()     // Catch:{ IOException -> 0x00e5 }
            r8.<init>(r13, r2)     // Catch:{ IOException -> 0x00e5 }
            java.lang.String r3 = "AdRequestServiceImpl: Sending request: "
            java.lang.String r2 = java.lang.String.valueOf(r16)     // Catch:{ IOException -> 0x00e5 }
            int r5 = r2.length()     // Catch:{ IOException -> 0x00e5 }
            if (r5 == 0) goto L_0x00de
            java.lang.String r2 = r3.concat(r2)     // Catch:{ IOException -> 0x00e5 }
        L_0x0020:
            com.google.android.gms.internal.zzafr.zzaC(r2)     // Catch:{ IOException -> 0x00e5 }
            java.net.URL r5 = new java.net.URL     // Catch:{ IOException -> 0x00e5 }
            r0 = r16
            r5.<init>(r0)     // Catch:{ IOException -> 0x00e5 }
            r2 = 0
            com.google.android.gms.common.util.zze r3 = com.google.android.gms.ads.internal.zzbs.zzbF()     // Catch:{ IOException -> 0x00e5 }
            long r10 = r3.elapsedRealtime()     // Catch:{ IOException -> 0x00e5 }
            r3 = r2
            r7 = r5
        L_0x0035:
            java.net.URLConnection r2 = r7.openConnection()     // Catch:{ IOException -> 0x00e5 }
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch:{ IOException -> 0x00e5 }
            com.google.android.gms.internal.zzagz r5 = com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x010a }
            r6 = 0
            r5.zza((android.content.Context) r14, (java.lang.String) r15, (boolean) r6, (java.net.HttpURLConnection) r2)     // Catch:{ all -> 0x010a }
            boolean r5 = android.text.TextUtils.isEmpty(r17)     // Catch:{ all -> 0x010a }
            if (r5 != 0) goto L_0x0056
            boolean r5 = r18.zzgK()     // Catch:{ all -> 0x010a }
            if (r5 == 0) goto L_0x0056
            java.lang.String r5 = "x-afma-drt-cookie"
            r0 = r17
            r2.addRequestProperty(r5, r0)     // Catch:{ all -> 0x010a }
        L_0x0056:
            java.lang.String r5 = r13.zzSW     // Catch:{ all -> 0x010a }
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x010a }
            if (r6 != 0) goto L_0x0068
            java.lang.String r6 = "Sending webview cookie in ad request header."
            com.google.android.gms.internal.zzafr.zzaC(r6)     // Catch:{ all -> 0x010a }
            java.lang.String r6 = "Cookie"
            r2.addRequestProperty(r6, r5)     // Catch:{ all -> 0x010a }
        L_0x0068:
            if (r18 == 0) goto L_0x0094
            java.lang.String r5 = r18.zzgJ()     // Catch:{ all -> 0x010a }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x010a }
            if (r5 != 0) goto L_0x0094
            r5 = 1
            r2.setDoOutput(r5)     // Catch:{ all -> 0x010a }
            java.lang.String r5 = r18.zzgJ()     // Catch:{ all -> 0x010a }
            byte[] r9 = r5.getBytes()     // Catch:{ all -> 0x010a }
            int r5 = r9.length     // Catch:{ all -> 0x010a }
            r2.setFixedLengthStreamingMode(r5)     // Catch:{ all -> 0x010a }
            r6 = 0
            java.io.BufferedOutputStream r5 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0104 }
            java.io.OutputStream r12 = r2.getOutputStream()     // Catch:{ all -> 0x0104 }
            r5.<init>(r12)     // Catch:{ all -> 0x0104 }
            r5.write(r9)     // Catch:{ all -> 0x01aa }
            com.google.android.gms.common.util.zzn.closeQuietly(r5)     // Catch:{ all -> 0x010a }
        L_0x0094:
            int r9 = r2.getResponseCode()     // Catch:{ all -> 0x010a }
            java.util.Map r12 = r2.getHeaderFields()     // Catch:{ all -> 0x010a }
            r5 = 200(0xc8, float:2.8E-43)
            if (r9 < r5) goto L_0x0115
            r5 = 300(0x12c, float:4.2E-43)
            if (r9 >= r5) goto L_0x0115
            java.lang.String r3 = r7.toString()     // Catch:{ all -> 0x010a }
            r6 = 0
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ all -> 0x010f }
            java.io.InputStream r7 = r2.getInputStream()     // Catch:{ all -> 0x010f }
            r5.<init>(r7)     // Catch:{ all -> 0x010f }
            com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x01a6 }
            java.lang.String r6 = com.google.android.gms.internal.zzagz.zza((java.io.InputStreamReader) r5)     // Catch:{ all -> 0x01a6 }
            com.google.android.gms.common.util.zzn.closeQuietly(r5)     // Catch:{ all -> 0x010a }
            zza(r3, r12, r6, r9)     // Catch:{ all -> 0x010a }
            r8.zza(r3, r12, r6)     // Catch:{ all -> 0x010a }
            if (r19 == 0) goto L_0x00d1
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ all -> 0x010a }
            r5 = 0
            java.lang.String r6 = "ufe"
            r3[r5] = r6     // Catch:{ all -> 0x010a }
            r0 = r19
            r0.zza(r4, r3)     // Catch:{ all -> 0x010a }
        L_0x00d1:
            com.google.android.gms.internal.zzaai r3 = r8.zze(r10)     // Catch:{ all -> 0x010a }
            r2.disconnect()     // Catch:{ IOException -> 0x00e5 }
            r2 = r3
        L_0x00d9:
            return r2
        L_0x00da:
            r2 = 0
            r4 = r2
            goto L_0x0007
        L_0x00de:
            java.lang.String r2 = new java.lang.String     // Catch:{ IOException -> 0x00e5 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x00e5 }
            goto L_0x0020
        L_0x00e5:
            r2 = move-exception
            java.lang.String r3 = "Error while connecting to ad server: "
            java.lang.String r2 = r2.getMessage()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r4 = r2.length()
            if (r4 == 0) goto L_0x019f
            java.lang.String r2 = r3.concat(r2)
        L_0x00fa:
            com.google.android.gms.internal.zzafr.zzaT(r2)
            com.google.android.gms.internal.zzaai r2 = new com.google.android.gms.internal.zzaai
            r3 = 2
            r2.<init>(r3)
            goto L_0x00d9
        L_0x0104:
            r3 = move-exception
            r4 = r6
        L_0x0106:
            com.google.android.gms.common.util.zzn.closeQuietly(r4)     // Catch:{ all -> 0x010a }
            throw r3     // Catch:{ all -> 0x010a }
        L_0x010a:
            r3 = move-exception
            r2.disconnect()     // Catch:{ IOException -> 0x00e5 }
            throw r3     // Catch:{ IOException -> 0x00e5 }
        L_0x010f:
            r3 = move-exception
            r4 = r6
        L_0x0111:
            com.google.android.gms.common.util.zzn.closeQuietly(r4)     // Catch:{ all -> 0x010a }
            throw r3     // Catch:{ all -> 0x010a }
        L_0x0115:
            java.lang.String r5 = r7.toString()     // Catch:{ all -> 0x010a }
            r6 = 0
            zza(r5, r12, r6, r9)     // Catch:{ all -> 0x010a }
            r5 = 300(0x12c, float:4.2E-43)
            if (r9 < r5) goto L_0x016b
            r5 = 400(0x190, float:5.6E-43)
            if (r9 >= r5) goto L_0x016b
            java.lang.String r5 = "Location"
            java.lang.String r5 = r2.getHeaderField(r5)     // Catch:{ all -> 0x010a }
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x010a }
            if (r6 == 0) goto L_0x0141
            java.lang.String r3 = "No location header to follow redirect."
            com.google.android.gms.internal.zzafr.zzaT(r3)     // Catch:{ all -> 0x010a }
            com.google.android.gms.internal.zzaai r3 = new com.google.android.gms.internal.zzaai     // Catch:{ all -> 0x010a }
            r4 = 0
            r3.<init>(r4)     // Catch:{ all -> 0x010a }
            r2.disconnect()     // Catch:{ IOException -> 0x00e5 }
            r2 = r3
            goto L_0x00d9
        L_0x0141:
            java.net.URL r6 = new java.net.URL     // Catch:{ all -> 0x010a }
            r6.<init>(r5)     // Catch:{ all -> 0x010a }
            int r5 = r3 + 1
            com.google.android.gms.internal.zzme<java.lang.Integer> r3 = com.google.android.gms.internal.zzmo.zzGG     // Catch:{ all -> 0x010a }
            com.google.android.gms.internal.zzmm r7 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ all -> 0x010a }
            java.lang.Object r3 = r7.zzd(r3)     // Catch:{ all -> 0x010a }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x010a }
            int r3 = r3.intValue()     // Catch:{ all -> 0x010a }
            if (r5 <= r3) goto L_0x018f
            java.lang.String r3 = "Too many redirects."
            com.google.android.gms.internal.zzafr.zzaT(r3)     // Catch:{ all -> 0x010a }
            com.google.android.gms.internal.zzaai r3 = new com.google.android.gms.internal.zzaai     // Catch:{ all -> 0x010a }
            r4 = 0
            r3.<init>(r4)     // Catch:{ all -> 0x010a }
            r2.disconnect()     // Catch:{ IOException -> 0x00e5 }
            r2 = r3
            goto L_0x00d9
        L_0x016b:
            r3 = 46
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x010a }
            r4.<init>(r3)     // Catch:{ all -> 0x010a }
            java.lang.String r3 = "Received error HTTP response code: "
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x010a }
            java.lang.StringBuilder r3 = r3.append(r9)     // Catch:{ all -> 0x010a }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x010a }
            com.google.android.gms.internal.zzafr.zzaT(r3)     // Catch:{ all -> 0x010a }
            com.google.android.gms.internal.zzaai r3 = new com.google.android.gms.internal.zzaai     // Catch:{ all -> 0x010a }
            r4 = 0
            r3.<init>(r4)     // Catch:{ all -> 0x010a }
            r2.disconnect()     // Catch:{ IOException -> 0x00e5 }
            r2 = r3
            goto L_0x00d9
        L_0x018f:
            r8.zzh(r12)     // Catch:{ all -> 0x010a }
            r2.disconnect()     // Catch:{ IOException -> 0x00e5 }
            if (r20 == 0) goto L_0x019b
            r3 = r5
            r7 = r6
            goto L_0x0035
        L_0x019b:
            r3 = r5
            r7 = r6
            goto L_0x0035
        L_0x019f:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r3)
            goto L_0x00fa
        L_0x01a6:
            r3 = move-exception
            r4 = r5
            goto L_0x0111
        L_0x01aa:
            r3 = move-exception
            r4 = r5
            goto L_0x0106
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzabm.zza(com.google.android.gms.internal.zzaae, android.content.Context, java.lang.String, java.lang.String, java.lang.String, com.google.android.gms.internal.zzaca, com.google.android.gms.internal.zznb, com.google.android.gms.internal.zzabl):com.google.android.gms.internal.zzaai");
    }

    public static zzabm zza(Context context, zzmb zzmb, zzabl zzabl) {
        zzabm zzabm;
        synchronized (zzuF) {
            if (zzUx == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                zzmo.initialize(context);
                zzUx = new zzabm(context, zzmb, zzabl);
            }
            zzabm = zzUx;
        }
        return zzabm;
    }

    private static void zza(String str, Map<String, List<String>> map, String str2, int i) {
        if (zzafr.zzz(2)) {
            zzafr.v(new StringBuilder(String.valueOf(str).length() + 39).append("Http Response: {\n  URL:\n    ").append(str).append("\n  Headers:").toString());
            if (map != null) {
                for (String next : map.keySet()) {
                    zzafr.v(new StringBuilder(String.valueOf(next).length() + 5).append("    ").append(next).append(":").toString());
                    for (String valueOf : map.get(next)) {
                        String valueOf2 = String.valueOf(valueOf);
                        zzafr.v(valueOf2.length() != 0 ? "      ".concat(valueOf2) : new String("      "));
                    }
                }
            }
            zzafr.v("  Body:");
            if (str2 != null) {
                for (int i2 = 0; i2 < Math.min(str2.length(), InnerErrorCode.SDK_ERROR_BASIC_XG); i2 += 1000) {
                    zzafr.v(str2.substring(i2, Math.min(str2.length(), i2 + 1000)));
                }
            } else {
                zzafr.v("    null");
            }
            zzafr.v(new StringBuilder(34).append("  Response Code:\n    ").append(i).append("\n}").toString());
        }
    }

    public final void zza(zzaae zzaae, zzaap zzaap) {
        zzbs.zzbD().zzd(this.mContext, zzaae.zzvT);
        zzajm<Void> zza = zzagt.zza((Runnable) new zzabr(this, zzaae, zzaap));
        zzbs.zzbP().zzie();
        zzbs.zzbP().getHandler().postDelayed(new zzabs(this, zza), Constants.WATCHDOG_WAKE_TIMER);
    }

    public final void zza(zzaax zzaax, zzaas zzaas) {
        zzafr.v("Nonagon code path entered in octagon");
        throw new IllegalArgumentException();
    }

    public final zzaai zzc(zzaae zzaae) {
        return zza(this.mContext, this.zzLG, this.zzUz, this.zzUy, zzaae);
    }
}
