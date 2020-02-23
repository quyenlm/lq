package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.tagmanager.zzcn;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class zzcuf {
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public volatile int mState = 1;
    /* access modifiers changed from: private */
    public final String zzbDw;
    private ScheduledFuture<?> zzbGa = null;
    /* access modifiers changed from: private */
    public final String zzbHH;
    /* access modifiers changed from: private */
    public final String zzbHI;
    /* access modifiers changed from: private */
    public final zzcvz zzbHJ;
    /* access modifiers changed from: private */
    public final cd zzbHK;
    /* access modifiers changed from: private */
    public final ExecutorService zzbHL;
    private final ScheduledExecutorService zzbHM;
    /* access modifiers changed from: private */
    public final zzcn zzbHN;
    /* access modifiers changed from: private */
    public final zzcuo zzbHO;
    /* access modifiers changed from: private */
    public zzcvu zzbHP;
    /* access modifiers changed from: private */
    public List<zzcut> zzbHQ = new ArrayList();
    /* access modifiers changed from: private */
    public boolean zzbHR = false;
    /* access modifiers changed from: private */
    public final zze zzvw;

    zzcuf(Context context, String str, @Nullable String str2, @Nullable String str3, zzcvz zzcvz, cd cdVar, ExecutorService executorService, ScheduledExecutorService scheduledExecutorService, zzcn zzcn, zze zze, zzcuo zzcuo) {
        this.mContext = context;
        this.zzbDw = (String) zzbo.zzu(str);
        this.zzbHJ = (zzcvz) zzbo.zzu(zzcvz);
        this.zzbHK = (cd) zzbo.zzu(cdVar);
        this.zzbHL = (ExecutorService) zzbo.zzu(executorService);
        this.zzbHM = (ScheduledExecutorService) zzbo.zzu(scheduledExecutorService);
        this.zzbHN = (zzcn) zzbo.zzu(zzcn);
        this.zzvw = (zze) zzbo.zzu(zze);
        this.zzbHO = (zzcuo) zzbo.zzu(zzcuo);
        this.zzbHH = str3;
        this.zzbHI = str2;
        this.zzbHQ.add(new zzcut("gtm.load", new Bundle(), "gtm", new Date(), false, this.zzbHN));
        String str4 = this.zzbDw;
        zzcvk.v(new StringBuilder(String.valueOf(str4).length() + 35).append("Container ").append(str4).append("is scheduled for loading.").toString());
        this.zzbHL.execute(new zzcuj(this, (zzcug) null));
    }

    /* access modifiers changed from: private */
    public final void zzaj(long j) {
        if (this.zzbGa != null) {
            this.zzbGa.cancel(false);
        }
        String str = this.zzbDw;
        zzcvk.v(new StringBuilder(String.valueOf(str).length() + 45).append("Refresh container ").append(str).append(" in ").append(j).append("ms.").toString());
        this.zzbGa = this.zzbHM.schedule(new zzcuh(this), j, TimeUnit.MILLISECONDS);
    }

    public final void dispatch() {
        this.zzbHL.execute(new zzcug(this));
    }

    public final void zza(zzcut zzcut) {
        this.zzbHL.execute(new zzcuk(this, zzcut));
    }
}
