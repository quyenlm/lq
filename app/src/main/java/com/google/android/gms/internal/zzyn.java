package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbb;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.dynamic.zzn;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzyn implements Callable<zzaff> {
    private static long zzRj = TimeUnit.SECONDS.toMillis(60);
    private final Context mContext;
    private int mErrorCode;
    private final Object mLock = new Object();
    private final zzcu zzIc;
    /* access modifiers changed from: private */
    public final zzafg zzQQ;
    private String zzRA;
    private boolean zzRB;
    private final zzaie zzRv;
    /* access modifiers changed from: private */
    public final zzbb zzRw;
    private boolean zzRx;
    private List<String> zzRy;
    private JSONObject zzRz;
    private final zznb zzsK;
    private zzyh zzuP;

    public zzyn(Context context, zzbb zzbb, zzaie zzaie, zzcu zzcu, zzafg zzafg, zznb zznb) {
        this.mContext = context;
        this.zzRw = zzbb;
        this.zzRv = zzaie;
        this.zzQQ = zzafg;
        this.zzIc = zzcu;
        this.zzsK = zznb;
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFu)).booleanValue()) {
            this.zzuP = zzbb.zzbi();
        }
        if (this.zzuP == null) {
            this.zzuP = new zzyh(context, zzafg, zzbb, zzcu);
            this.zzuP.zzgs();
            this.zzRB = true;
        }
        this.zzRx = false;
        this.mErrorCode = -2;
        this.zzRy = null;
        this.zzRA = null;
    }

    private final zzaff zza(zzoa zzoa) {
        int i;
        synchronized (this.mLock) {
            i = this.mErrorCode;
            if (zzoa == null && this.mErrorCode == -2) {
                i = 0;
            }
        }
        return new zzaff(this.zzQQ.zzUj.zzSz, (zzaka) null, this.zzQQ.zzXY.zzMa, i, this.zzQQ.zzXY.zzMb, this.zzRy, this.zzQQ.zzXY.orientation, this.zzQQ.zzXY.zzMg, this.zzQQ.zzUj.zzSC, false, (zzua) null, (zzut) null, (String) null, (zzub) null, (zzud) null, 0, this.zzQQ.zzvX, this.zzQQ.zzXY.zzTn, this.zzQQ.zzXR, this.zzQQ.zzXS, this.zzQQ.zzXY.zzTt, this.zzRz, i != -2 ? null : zzoa, (zzaee) null, (List<String>) null, (List<String>) null, this.zzQQ.zzXY.zzTG, this.zzQQ.zzXY.zzTH, (String) null, this.zzQQ.zzXY.zzMd, this.zzRA, this.zzQQ.zzXX);
    }

    private final zzajm<zznp> zza(JSONObject jSONObject, boolean z, boolean z2) throws JSONException {
        String string = z ? jSONObject.getString("url") : jSONObject.optString("url");
        double optDouble = jSONObject.optDouble("scale", 1.0d);
        boolean optBoolean = jSONObject.optBoolean("is_transparent", true);
        if (!TextUtils.isEmpty(string)) {
            return z2 ? new zzajh(new zznp((Drawable) null, Uri.parse(string), optDouble)) : this.zzRv.zza(string, new zzyu(this, z, optDouble, optBoolean, string));
        }
        zzc(0, z);
        return new zzajh(null);
    }

    static zzaka zzb(zzajm<zzaka> zzajm) {
        try {
            return (zzaka) zzajm.get((long) ((Integer) zzbs.zzbL().zzd(zzmo.zzFz)).intValue(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            zzafr.zzc("InterruptedException occurred while waiting for video to load", e);
            Thread.currentThread().interrupt();
        } catch (CancellationException | ExecutionException | TimeoutException e2) {
            zzafr.zzc("Exception occurred while waiting for video to load", e2);
        }
        return null;
    }

    private static Integer zzb(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            return Integer.valueOf(Color.rgb(jSONObject2.getInt("r"), jSONObject2.getInt("g"), jSONObject2.getInt(APDataReportManager.GAMEANDMONTHSINPUT_PRE)));
        } catch (JSONException e) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public final void zzc(zzpj zzpj, String str) {
        try {
            zzpt zzs = this.zzRw.zzs(zzpj.getCustomTemplateId());
            if (zzs != null) {
                zzs.zzb(zzpj, str);
            }
        } catch (RemoteException e) {
            zzafr.zzc(new StringBuilder(String.valueOf(str).length() + 40).append("Failed to call onCustomClick for asset ").append(str).append(".").toString(), e);
        }
    }

    private static String[] zzd(JSONObject jSONObject, String str) throws JSONException {
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        String[] strArr = new String[optJSONArray.length()];
        for (int i = 0; i < optJSONArray.length(); i++) {
            strArr[i] = optJSONArray.getString(i);
        }
        return strArr;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0113  */
    /* renamed from: zzgw */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzaff call() {
        /*
            r13 = this;
            r12 = 0
            r11 = 0
            boolean r2 = r13.zzRB     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x000b
            com.google.android.gms.internal.zzyh r2 = r13.zzuP     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2.zzgt()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
        L_0x000b:
            java.util.UUID r2 = java.util.UUID.randomUUID()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.lang.String r10 = r2.toString()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            boolean r2 = r13.zzgx()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x0051
            r7 = r11
        L_0x001a:
            boolean r2 = r13.zzgx()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 != 0) goto L_0x0022
            if (r7 != 0) goto L_0x0071
        L_0x0022:
            r3 = r11
        L_0x0023:
            boolean r2 = r13.zzgx()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 != 0) goto L_0x002d
            if (r3 == 0) goto L_0x002d
            if (r7 != 0) goto L_0x0123
        L_0x002d:
            r3 = r11
        L_0x002e:
            boolean r2 = r3 instanceof com.google.android.gms.internal.zznu     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x004c
            r0 = r3
            com.google.android.gms.internal.zznu r0 = (com.google.android.gms.internal.zznu) r0     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2 = r0
            com.google.android.gms.internal.zzyw r4 = new com.google.android.gms.internal.zzyw     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r4.<init>(r13)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzyr r5 = new com.google.android.gms.internal.zzyr     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r5.<init>(r13, r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r4.zzRW = r5     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzyh r2 = r13.zzuP     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzys r4 = new com.google.android.gms.internal.zzys     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r4.<init>(r13, r5)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2.zza((com.google.android.gms.internal.zzym) r4)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
        L_0x004c:
            com.google.android.gms.internal.zzaff r2 = r13.zza((com.google.android.gms.internal.zzoa) r3)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
        L_0x0050:
            return r2
        L_0x0051:
            com.google.android.gms.internal.zzajg r2 = new com.google.android.gms.internal.zzajg     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2.<init>()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzyw r3 = new com.google.android.gms.internal.zzyw     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r3.<init>(r13)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzyo r4 = new com.google.android.gms.internal.zzyo     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r4.<init>(r13, r10, r3, r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzyh r3 = r13.zzuP     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r3.zza((com.google.android.gms.internal.zzym) r4)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            long r4 = zzRj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.lang.Object r2 = r2.get(r4, r3)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            org.json.JSONObject r2 = (org.json.JSONObject) r2     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r7 = r2
            goto L_0x001a
        L_0x0071:
            java.lang.String r2 = "template_id"
            java.lang.String r5 = r7.getString(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzafg r2 = r13.zzQQ     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzaae r2 = r2.zzUj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzon r2 = r2.zzwj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x00a9
            com.google.android.gms.internal.zzafg r2 = r13.zzQQ     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzaae r2 = r2.zzUj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzon r2 = r2.zzwj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            boolean r2 = r2.zzIn     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r4 = r2
        L_0x0088:
            com.google.android.gms.internal.zzafg r2 = r13.zzQQ     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzaae r2 = r2.zzUj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzon r2 = r2.zzwj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x00ab
            com.google.android.gms.internal.zzafg r2 = r13.zzQQ     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzaae r2 = r2.zzUj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzon r2 = r2.zzwj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            boolean r2 = r2.zzIp     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r3 = r2
        L_0x0099:
            java.lang.String r2 = "2"
            boolean r2 = r2.equals(r5)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x00ad
            com.google.android.gms.internal.zzzf r2 = new com.google.android.gms.internal.zzzf     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2.<init>(r4, r3)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r3 = r2
            goto L_0x0023
        L_0x00a9:
            r4 = r12
            goto L_0x0088
        L_0x00ab:
            r3 = r12
            goto L_0x0099
        L_0x00ad:
            java.lang.String r2 = "1"
            boolean r2 = r2.equals(r5)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x00bd
            com.google.android.gms.internal.zzzg r2 = new com.google.android.gms.internal.zzzg     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2.<init>(r4, r3)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r3 = r2
            goto L_0x0023
        L_0x00bd:
            java.lang.String r2 = "3"
            boolean r2 = r2.equals(r5)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x011c
            java.lang.String r2 = "custom_template_id"
            java.lang.String r2 = r7.getString(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzajg r3 = new com.google.android.gms.internal.zzajg     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r3.<init>()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            android.os.Handler r5 = com.google.android.gms.internal.zzagz.zzZr     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzyq r6 = new com.google.android.gms.internal.zzyq     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r6.<init>(r13, r3, r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r5.post(r6)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            long r8 = zzRj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.lang.Object r2 = r3.get(r8, r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 == 0) goto L_0x00ec
            com.google.android.gms.internal.zzzh r2 = new com.google.android.gms.internal.zzzh     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2.<init>(r4)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r3 = r2
            goto L_0x0023
        L_0x00ec:
            java.lang.String r3 = "No handler for custom template: "
            java.lang.String r2 = "custom_template_id"
            java.lang.String r2 = r7.getString(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            int r4 = r2.length()     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r4 == 0) goto L_0x0108
            java.lang.String r2 = r3.concat(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
        L_0x0102:
            com.google.android.gms.internal.zzafr.e(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
        L_0x0105:
            r3 = r11
            goto L_0x0023
        L_0x0108:
            java.lang.String r2 = new java.lang.String     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2.<init>(r3)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            goto L_0x0102
        L_0x010e:
            r2 = move-exception
        L_0x010f:
            boolean r2 = r13.zzRx
            if (r2 != 0) goto L_0x0116
            r13.zzt(r12)
        L_0x0116:
            com.google.android.gms.internal.zzaff r2 = r13.zza((com.google.android.gms.internal.zzoa) r11)
            goto L_0x0050
        L_0x011c:
            r2 = 0
            r13.zzt(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            goto L_0x0105
        L_0x0121:
            r2 = move-exception
            goto L_0x010f
        L_0x0123:
            java.lang.String r2 = "tracking_urls_and_actions"
            org.json.JSONObject r4 = r7.getJSONObject(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.lang.String r2 = "impression_tracking_urls"
            java.lang.String[] r2 = zzd(r4, r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            if (r2 != 0) goto L_0x0161
            r2 = r11
        L_0x0132:
            r13.zzRy = r2     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.lang.String r2 = "active_view"
            org.json.JSONObject r2 = r4.optJSONObject(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r13.zzRz = r2     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            java.lang.String r2 = "debug_signals"
            java.lang.String r2 = r7.optString(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r13.zzRA = r2     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzoa r8 = r3.zza(r13, r7)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzoc r2 = new com.google.android.gms.internal.zzoc     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            android.content.Context r3 = r13.mContext     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.ads.internal.zzbb r4 = r13.zzRw     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzyh r5 = r13.zzuP     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzcu r6 = r13.zzIc     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzafg r9 = r13.zzQQ     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzaae r9 = r9.zzUj     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            com.google.android.gms.internal.zzaje r9 = r9.zzvT     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r8.zzb(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            r3 = r8
            goto L_0x002e
        L_0x0161:
            java.util.List r2 = java.util.Arrays.asList(r2)     // Catch:{ CancellationException -> 0x010e, ExecutionException -> 0x0121, InterruptedException -> 0x017b, JSONException -> 0x0166, TimeoutException -> 0x016d, Exception -> 0x0174 }
            goto L_0x0132
        L_0x0166:
            r2 = move-exception
            java.lang.String r3 = "Malformed native JSON response."
            com.google.android.gms.internal.zzafr.zzc(r3, r2)
            goto L_0x010f
        L_0x016d:
            r2 = move-exception
            java.lang.String r3 = "Timeout when loading native ad."
            com.google.android.gms.internal.zzafr.zzc(r3, r2)
            goto L_0x010f
        L_0x0174:
            r2 = move-exception
            java.lang.String r3 = "Error occured while doing native ads initialization."
            com.google.android.gms.internal.zzafr.zzc(r3, r2)
            goto L_0x010f
        L_0x017b:
            r2 = move-exception
            goto L_0x010f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzyn.call():com.google.android.gms.internal.zzaff");
    }

    /* access modifiers changed from: private */
    public static List<Drawable> zzj(List<zznp> list) throws RemoteException {
        ArrayList arrayList = new ArrayList();
        for (zznp zzeg : list) {
            arrayList.add((Drawable) zzn.zzE(zzeg.zzeg()));
        }
        return arrayList;
    }

    public final zzajm<zznp> zza(JSONObject jSONObject, String str, boolean z, boolean z2) throws JSONException {
        JSONObject jSONObject2 = z ? jSONObject.getJSONObject(str) : jSONObject.optJSONObject(str);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, z, z2);
    }

    public final List<zzajm<zznp>> zza(JSONObject jSONObject, String str, boolean z, boolean z2, boolean z3) throws JSONException {
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        ArrayList arrayList = new ArrayList();
        if (optJSONArray == null || optJSONArray.length() == 0) {
            zzc(0, false);
            return arrayList;
        }
        int length = z3 ? optJSONArray.length() : 1;
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            arrayList.add(zza(jSONObject2, false, z2));
        }
        return arrayList;
    }

    public final Future<zznp> zza(JSONObject jSONObject, String str, boolean z) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject(str);
        boolean optBoolean = jSONObject2.optBoolean("require", true);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, optBoolean, z);
    }

    public final zzajm<zzaka> zzc(JSONObject jSONObject, String str) throws JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject == null) {
            return new zzajh(null);
        }
        if (TextUtils.isEmpty(optJSONObject.optString("vast_xml"))) {
            zzafr.zzaT("Required field 'vast_xml' is missing");
            return new zzajh(null);
        }
        zzyx zzyx = new zzyx(this.mContext, this.zzIc, this.zzQQ, this.zzsK, this.zzRw);
        zzajg zzajg = new zzajg();
        zzbs.zzbz();
        zzagz.runOnUiThread(new zzyy(zzyx, optJSONObject, zzajg));
        return zzajg;
    }

    public final void zzc(int i, boolean z) {
        if (z) {
            zzt(i);
        }
    }

    public final zzajm<zznn> zzd(JSONObject jSONObject) throws JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject("attribution");
        if (optJSONObject == null) {
            return new zzajh(null);
        }
        String optString = optJSONObject.optString(ContentType.TYPE_TEXT);
        int optInt = optJSONObject.optInt("text_size", -1);
        Integer zzb = zzb(optJSONObject, "text_color");
        Integer zzb2 = zzb(optJSONObject, "bg_color");
        int optInt2 = optJSONObject.optInt("animation_ms", 1000);
        int optInt3 = optJSONObject.optInt("presentation_ms", 4000);
        int i = (this.zzQQ.zzUj.zzwj == null || this.zzQQ.zzUj.zzwj.versionCode < 2) ? 1 : this.zzQQ.zzUj.zzwj.zzIq;
        boolean optBoolean = optJSONObject.optBoolean("allow_pub_rendering");
        List arrayList = new ArrayList();
        if (optJSONObject.optJSONArray("images") != null) {
            arrayList = zza(optJSONObject, "images", false, false, true);
        } else {
            arrayList.add(zza(optJSONObject, "image", false, false));
        }
        return zzaji.zza(zzaji.zzp(arrayList), new zzyt(this, optString, zzb2, zzb, optInt, optInt3, optInt2, i, optBoolean));
    }

    public final boolean zzgx() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzRx;
        }
        return z;
    }

    public final void zzt(int i) {
        synchronized (this.mLock) {
            this.zzRx = true;
            this.mErrorCode = i;
        }
    }
}
