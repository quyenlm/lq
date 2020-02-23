package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.text.TextUtils;

@TargetApi(14)
@MainThread
final class zzchy implements Application.ActivityLifecycleCallbacks {
    private /* synthetic */ zzchl zzbtt;

    private zzchy(zzchl zzchl) {
        this.zzbtt = zzchl;
    }

    /* synthetic */ zzchy(zzchl zzchl, zzchm zzchm) {
        this(zzchl);
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        Bundle bundle2;
        Uri data;
        try {
            this.zzbtt.zzwF().zzyD().log("onActivityCreated");
            Intent intent = activity.getIntent();
            if (!(intent == null || (data = intent.getData()) == null || !data.isHierarchical())) {
                if (bundle == null) {
                    Bundle zzq = this.zzbtt.zzwB().zzq(data);
                    this.zzbtt.zzwB();
                    String str = zzcjl.zzl(intent) ? "gs" : "auto";
                    if (zzq != null) {
                        this.zzbtt.zzd(str, "_cmp", zzq);
                    }
                }
                String queryParameter = data.getQueryParameter("referrer");
                if (!TextUtils.isEmpty(queryParameter)) {
                    if (!(queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content")))) {
                        this.zzbtt.zzwF().zzyC().log("Activity created with data 'referrer' param without gclid and at least one utm field");
                        return;
                    }
                    this.zzbtt.zzwF().zzyC().zzj("Activity created with referrer", queryParameter);
                    if (!TextUtils.isEmpty(queryParameter)) {
                        this.zzbtt.zzb("auto", "_ldl", queryParameter);
                    }
                } else {
                    return;
                }
            }
        } catch (Throwable th) {
            this.zzbtt.zzwF().zzyx().zzj("Throwable caught in onActivityCreated", th);
        }
        zzchz zzwx = this.zzbtt.zzwx();
        if (bundle != null && (bundle2 = bundle.getBundle("com.google.firebase.analytics.screen_service")) != null) {
            zzcic zzq2 = zzwx.zzq(activity);
            zzq2.zzbol = bundle2.getLong("id");
            zzq2.zzboj = bundle2.getString("name");
            zzq2.zzbok = bundle2.getString("referrer_name");
        }
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzbtt.zzwx().onActivityDestroyed(activity);
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        this.zzbtt.zzwx().onActivityPaused(activity);
        zzcja zzwD = this.zzbtt.zzwD();
        zzwD.zzwE().zzj(new zzcje(zzwD, zzwD.zzkq().elapsedRealtime()));
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        this.zzbtt.zzwx().onActivityResumed(activity);
        zzcja zzwD = this.zzbtt.zzwD();
        zzwD.zzwE().zzj(new zzcjd(zzwD, zzwD.zzkq().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzbtt.zzwx().onActivitySaveInstanceState(activity, bundle);
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }
}
