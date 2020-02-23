package com.google.android.gms.internal;

import android.content.ComponentName;
import android.os.RemoteException;
import com.google.android.gms.analytics.zzl;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.stats.zza;
import java.util.Collections;

public final class zzamn extends zzamh {
    /* access modifiers changed from: private */
    public final zzamp zzagg = new zzamp(this);
    private zzany zzagh;
    private final zzanm zzagi;
    private zzaoo zzagj;

    protected zzamn(zzamj zzamj) {
        super(zzamj);
        this.zzagj = new zzaoo(zzamj.zzkq());
        this.zzagi = new zzamo(this, zzamj);
    }

    /* access modifiers changed from: private */
    public final void onServiceDisconnected(ComponentName componentName) {
        zzl.zzjC();
        if (this.zzagh != null) {
            this.zzagh = null;
            zza("Disconnected from device AnalyticsService", componentName);
            zzkv().zzkn();
        }
    }

    /* access modifiers changed from: private */
    public final void zza(zzany zzany) {
        zzl.zzjC();
        this.zzagh = zzany;
        zzkP();
        zzkv().onServiceConnected();
    }

    private final void zzkP() {
        this.zzagj.start();
        this.zzagi.zzs(zzans.zzahO.get().longValue());
    }

    /* access modifiers changed from: private */
    public final void zzkQ() {
        zzl.zzjC();
        if (isConnected()) {
            zzbo("Inactivity, disconnecting from device AnalyticsService");
            disconnect();
        }
    }

    public final boolean connect() {
        zzl.zzjC();
        zzkD();
        if (this.zzagh != null) {
            return true;
        }
        zzany zzkR = this.zzagg.zzkR();
        if (zzkR == null) {
            return false;
        }
        this.zzagh = zzkR;
        zzkP();
        return true;
    }

    public final void disconnect() {
        zzl.zzjC();
        zzkD();
        try {
            zza.zzrU();
            getContext().unbindService(this.zzagg);
        } catch (IllegalArgumentException | IllegalStateException e) {
        }
        if (this.zzagh != null) {
            this.zzagh = null;
            zzkv().zzkn();
        }
    }

    public final boolean isConnected() {
        zzl.zzjC();
        zzkD();
        return this.zzagh != null;
    }

    public final boolean zzb(zzanx zzanx) {
        zzbo.zzu(zzanx);
        zzl.zzjC();
        zzkD();
        zzany zzany = this.zzagh;
        if (zzany == null) {
            return false;
        }
        try {
            zzany.zza(zzanx.zzdV(), zzanx.zzlG(), zzanx.zzlI() ? zzank.zzlu() : zzank.zzlv(), Collections.emptyList());
            zzkP();
            return true;
        } catch (RemoteException e) {
            zzbo("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }

    public final boolean zzkO() {
        zzl.zzjC();
        zzkD();
        zzany zzany = this.zzagh;
        if (zzany == null) {
            return false;
        }
        try {
            zzany.zzkk();
            zzkP();
            return true;
        } catch (RemoteException e) {
            zzbo("Failed to clear hits from AnalyticsService");
            return false;
        }
    }
}
