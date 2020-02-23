package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.internal.js.zza;
import com.google.android.gms.ads.internal.js.zzl;
import com.google.android.gms.ads.internal.js.zzy;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzaaz extends zzafp {
    /* access modifiers changed from: private */
    public static zzl zzRl = null;
    private static long zzTW = TimeUnit.SECONDS.toMillis(10);
    private static boolean zzTX = false;
    private static zzre zzTY = null;
    /* access modifiers changed from: private */
    public static zzrn zzTZ = null;
    private static zzrd zzUa = null;
    private static final Object zzuF = new Object();
    private final Context mContext;
    private final Object zzQT = new Object();
    /* access modifiers changed from: private */
    public final zzzp zzSm;
    private final zzaaf zzSn;
    private zzij zzSo;
    /* access modifiers changed from: private */
    public zzy zzUb;

    public zzaaz(Context context, zzaaf zzaaf, zzzp zzzp, zzij zzij) {
        super(true);
        this.zzSm = zzzp;
        this.mContext = context;
        this.zzSn = zzaaf;
        this.zzSo = zzij;
        synchronized (zzuF) {
            if (!zzTX) {
                zzTZ = new zzrn();
                zzTY = new zzre(context.getApplicationContext(), zzaaf.zzvT);
                zzUa = new zzabh();
                zzRl = new zzl(this.mContext.getApplicationContext(), this.zzSn.zzvT, (String) zzbs.zzbL().zzd(zzmo.zzBX), new zzabg(), new zzabf());
                zzTX = true;
            }
        }
    }

    private final JSONObject zza(zzaae zzaae, String str) {
        zzacb zzacb;
        AdvertisingIdClient.Info info;
        Bundle bundle = zzaae.zzSz.extras.getBundle("sdk_less_server_data");
        if (bundle == null) {
            return null;
        }
        try {
            zzacb = zzbs.zzbI().zzn(this.mContext).get();
        } catch (Exception e) {
            zzafr.zzc("Error grabbing device info: ", e);
            zzacb = null;
        }
        Context context = this.mContext;
        zzabk zzabk = new zzabk();
        zzabk.zzUj = zzaae;
        zzabk.zzUk = zzacb;
        JSONObject zza = zzabt.zza(context, zzabk);
        if (zza == null) {
            return null;
        }
        try {
            info = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException | IllegalStateException e2) {
            zzafr.zzc("Cannot get advertising id info", e2);
            info = null;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("request_id", str);
        hashMap.put("request_param", zza);
        hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bundle);
        if (info != null) {
            hashMap.put(HttpRequestParams.AD_ID, info.getId());
            hashMap.put(VKApiConst.LAT, Integer.valueOf(info.isLimitAdTrackingEnabled() ? 1 : 0));
        }
        try {
            return zzbs.zzbz().zzj(hashMap);
        } catch (JSONException e3) {
            return null;
        }
    }

    protected static void zza(zza zza) {
        zza.zza("/loadAd", (zzrd) zzTZ);
        zza.zza("/fetchHttpRequest", (zzrd) zzTY);
        zza.zza("/invalidRequest", zzUa);
    }

    protected static void zzb(zza zza) {
        zza.zzb("/loadAd", (zzrd) zzTZ);
        zza.zzb("/fetchHttpRequest", (zzrd) zzTY);
        zza.zzb("/invalidRequest", zzUa);
    }

    private final zzaai zzd(zzaae zzaae) {
        zzbs.zzbz();
        String zzhO = zzagz.zzhO();
        JSONObject zza = zza(zzaae, zzhO);
        if (zza == null) {
            return new zzaai(0);
        }
        long elapsedRealtime = zzbs.zzbF().elapsedRealtime();
        Future<JSONObject> zzS = zzTZ.zzS(zzhO);
        zzaiy.zzaaH.post(new zzabb(this, zza, zzhO));
        try {
            JSONObject jSONObject = zzS.get(zzTW - (zzbs.zzbF().elapsedRealtime() - elapsedRealtime), TimeUnit.MILLISECONDS);
            if (jSONObject == null) {
                return new zzaai(-1);
            }
            zzaai zza2 = zzabt.zza(this.mContext, zzaae, jSONObject.toString());
            return (zza2.errorCode == -3 || !TextUtils.isEmpty(zza2.body)) ? zza2 : new zzaai(3);
        } catch (InterruptedException | CancellationException e) {
            return new zzaai(-1);
        } catch (TimeoutException e2) {
            return new zzaai(2);
        } catch (ExecutionException e3) {
            return new zzaai(0);
        }
    }

    public final void onStop() {
        synchronized (this.zzQT) {
            zzaiy.zzaaH.post(new zzabe(this));
        }
    }

    public final void zzbd() {
        zzafr.zzaC("SdkLessAdLoaderBackgroundTask started.");
        String zzw = zzbs.zzbY().zzw(this.mContext);
        zzaae zzaae = new zzaae(this.zzSn, -1, zzbs.zzbY().zzu(this.mContext), zzbs.zzbY().zzv(this.mContext), zzw);
        zzbs.zzbY().zzh(this.mContext, zzw);
        zzaai zzd = zzd(zzaae);
        zzaae zzaae2 = zzaae;
        zzaiy.zzaaH.post(new zzaba(this, new zzafg(zzaae2, zzd, (zzub) null, (zziv) null, zzd.errorCode, zzbs.zzbF().elapsedRealtime(), zzd.zzTs, (JSONObject) null, this.zzSo)));
    }
}
