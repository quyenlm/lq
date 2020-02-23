package com.google.android.gms.internal;

import android.content.Context;
import android.os.Build;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.ads.internal.zzbs;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;

@zzzn
public final class zzmq {
    private Context mContext = null;
    private boolean zzGI;
    private String zzGJ;
    private Map<String, String> zzGK;
    private String zzwH = null;

    public zzmq(Context context, String str) {
        this.mContext = context;
        this.zzwH = str;
        this.zzGI = ((Boolean) zzbs.zzbL().zzd(zzmo.zzCQ)).booleanValue();
        this.zzGJ = (String) zzbs.zzbL().zzd(zzmo.zzCR);
        this.zzGK = new LinkedHashMap();
        this.zzGK.put("s", "gmob_sdk");
        this.zzGK.put(VKApiConst.VERSION, "3");
        this.zzGK.put(HttpRequestParams.OS, Build.VERSION.RELEASE);
        this.zzGK.put(ServerProtocol.DIALOG_PARAM_SDK_VERSION, Build.VERSION.SDK);
        Map<String, String> map = this.zzGK;
        zzbs.zzbz();
        map.put("device", zzagz.zzhQ());
        this.zzGK.put(VKAttachments.TYPE_APP, context.getApplicationContext() != null ? context.getApplicationContext().getPackageName() : context.getPackageName());
        Map<String, String> map2 = this.zzGK;
        zzbs.zzbz();
        map2.put("is_lite_sdk", zzagz.zzO(context) ? "1" : "0");
        Future<zzacb> zzn = zzbs.zzbI().zzn(this.mContext);
        try {
            zzn.get();
            this.zzGK.put("network_coarse", Integer.toString(zzn.get().zzVS));
            this.zzGK.put("network_fine", Integer.toString(zzn.get().zzVT));
        } catch (Exception e) {
            zzbs.zzbD().zza((Throwable) e, "CsiConfiguration.CsiConfiguration");
        }
    }

    /* access modifiers changed from: package-private */
    public final Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: package-private */
    public final String zzck() {
        return this.zzwH;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzdL() {
        return this.zzGI;
    }

    /* access modifiers changed from: package-private */
    public final String zzdM() {
        return this.zzGJ;
    }

    /* access modifiers changed from: package-private */
    public final Map<String, String> zzdN() {
        return this.zzGK;
    }
}
