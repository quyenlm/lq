package com.google.android.gms.tagmanager;

import android.os.Looper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.ContainerHolder;

final class zzv implements ContainerHolder {
    private Status mStatus;
    private boolean zzaNd;
    private Container zzbDE;
    private Container zzbDF;
    private zzx zzbDG;
    private zzw zzbDH;
    private TagManager zzbDI;
    private final Looper zzrM;

    public zzv(Status status) {
        this.mStatus = status;
        this.zzrM = null;
    }

    public zzv(TagManager tagManager, Looper looper, Container container, zzw zzw) {
        this.zzbDI = tagManager;
        this.zzrM = looper == null ? Looper.getMainLooper() : looper;
        this.zzbDE = container;
        this.zzbDH = zzw;
        this.mStatus = Status.zzaBm;
        tagManager.zza(this);
    }

    private final void zzAL() {
        if (this.zzbDG != null) {
            zzx zzx = this.zzbDG;
            zzx.sendMessage(zzx.obtainMessage(1, this.zzbDF.zzAI()));
        }
    }

    public final synchronized Container getContainer() {
        Container container = null;
        synchronized (this) {
            if (this.zzaNd) {
                zzdj.e("ContainerHolder is released.");
            } else {
                if (this.zzbDF != null) {
                    this.zzbDE = this.zzbDF;
                    this.zzbDF = null;
                }
                container = this.zzbDE;
            }
        }
        return container;
    }

    /* access modifiers changed from: package-private */
    public final String getContainerId() {
        if (!this.zzaNd) {
            return this.zzbDE.getContainerId();
        }
        zzdj.e("getContainerId called on a released ContainerHolder.");
        return "";
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final synchronized void refresh() {
        if (this.zzaNd) {
            zzdj.e("Refreshing a released ContainerHolder.");
        } else {
            this.zzbDH.zzAM();
        }
    }

    public final synchronized void release() {
        if (this.zzaNd) {
            zzdj.e("Releasing a released ContainerHolder.");
        } else {
            this.zzaNd = true;
            this.zzbDI.zzb(this);
            this.zzbDE.release();
            this.zzbDE = null;
            this.zzbDF = null;
            this.zzbDH = null;
            this.zzbDG = null;
        }
    }

    public final synchronized void setContainerAvailableListener(ContainerHolder.ContainerAvailableListener containerAvailableListener) {
        if (this.zzaNd) {
            zzdj.e("ContainerHolder is released.");
        } else if (containerAvailableListener == null) {
            this.zzbDG = null;
        } else {
            this.zzbDG = new zzx(this, containerAvailableListener, this.zzrM);
            if (this.zzbDF != null) {
                zzAL();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final String zzAK() {
        if (!this.zzaNd) {
            return this.zzbDH.zzAK();
        }
        zzdj.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        return "";
    }

    public final synchronized void zza(Container container) {
        if (!this.zzaNd) {
            this.zzbDF = container;
            zzAL();
        }
    }

    public final synchronized void zzeZ(String str) {
        if (!this.zzaNd) {
            this.zzbDE.zzeZ(str);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzfa(String str) {
        if (this.zzaNd) {
            zzdj.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.zzbDH.zzfa(str);
        }
    }
}
