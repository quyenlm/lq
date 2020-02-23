package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import com.google.android.gms.ads.internal.js.zzai;
import com.google.android.gms.ads.internal.js.zzl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

@zzzn
public final class zzfh implements zzfp {
    private final Context mApplicationContext;
    private final Object mLock = new Object();
    private final zzaje zztW;
    private final WeakHashMap<zzaff, zzfi> zzwK = new WeakHashMap<>();
    private final ArrayList<zzfi> zzwL = new ArrayList<>();
    private final zzl zzwM;

    public zzfh(Context context, zzaje zzaje, zzl zzl) {
        this.mApplicationContext = context.getApplicationContext();
        this.zztW = zzaje;
        this.zzwM = zzl;
    }

    private final boolean zzf(zzaff zzaff) {
        boolean z;
        synchronized (this.mLock) {
            zzfi zzfi = this.zzwK.get(zzaff);
            z = zzfi != null && zzfi.zzcs();
        }
        return z;
    }

    public final void zza(zzfi zzfi) {
        synchronized (this.mLock) {
            if (!zzfi.zzcs()) {
                this.zzwL.remove(zzfi);
                Iterator<Map.Entry<zzaff, zzfi>> it = this.zzwK.entrySet().iterator();
                while (it.hasNext()) {
                    if (it.next().getValue() == zzfi) {
                        it.remove();
                    }
                }
            }
        }
    }

    public final void zza(zziv zziv, zzaff zzaff) {
        zza(zziv, zzaff, zzaff.zzPg.getView());
    }

    public final void zza(zziv zziv, zzaff zzaff, View view) {
        zza(zziv, zzaff, (zzgs) new zzfo(view, zzaff), (zzai) null);
    }

    public final void zza(zziv zziv, zzaff zzaff, View view, zzai zzai) {
        zza(zziv, zzaff, (zzgs) new zzfo(view, zzaff), zzai);
    }

    public final void zza(zziv zziv, zzaff zzaff, zzgs zzgs, @Nullable zzai zzai) {
        zzfi zzfi;
        synchronized (this.mLock) {
            if (zzf(zzaff)) {
                zzfi = this.zzwK.get(zzaff);
            } else {
                zzfi = new zzfi(this.mApplicationContext, zziv, zzaff, this.zztW, zzgs);
                zzfi.zza((zzfp) this);
                this.zzwK.put(zzaff, zzfi);
                this.zzwL.add(zzfi);
            }
            if (zzai != null) {
                zzfi.zza((zzgd) new zzfq(zzfi, zzai));
            } else {
                zzfi.zza((zzgd) new zzfu(zzfi, this.zzwM, this.mApplicationContext));
            }
        }
    }

    public final void zzg(zzaff zzaff) {
        synchronized (this.mLock) {
            zzfi zzfi = this.zzwK.get(zzaff);
            if (zzfi != null) {
                zzfi.zzcq();
            }
        }
    }

    public final void zzh(zzaff zzaff) {
        synchronized (this.mLock) {
            zzfi zzfi = this.zzwK.get(zzaff);
            if (zzfi != null) {
                zzfi.stop();
            }
        }
    }

    public final void zzi(zzaff zzaff) {
        synchronized (this.mLock) {
            zzfi zzfi = this.zzwK.get(zzaff);
            if (zzfi != null) {
                zzfi.pause();
            }
        }
    }

    public final void zzj(zzaff zzaff) {
        synchronized (this.mLock) {
            zzfi zzfi = this.zzwK.get(zzaff);
            if (zzfi != null) {
                zzfi.resume();
            }
        }
    }
}
