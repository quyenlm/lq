package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.json.JSONObject;

@zzzn
public final class zznx extends zzoc {
    private Object mLock;
    @Nullable
    private zzvc zzHV;
    @Nullable
    private zzvf zzHW;
    private final zznz zzHX;
    @Nullable
    private zzny zzHY;
    private boolean zzHZ;

    private zznx(Context context, zznz zznz, zzcu zzcu, zzoa zzoa) {
        super(context, zznz, (zzyh) null, zzcu, (JSONObject) null, zzoa, (zzaje) null, (String) null);
        this.zzHZ = false;
        this.mLock = new Object();
        this.zzHX = zznz;
    }

    public zznx(Context context, zznz zznz, zzcu zzcu, zzvc zzvc, zzoa zzoa) {
        this(context, zznz, zzcu, zzoa);
        this.zzHV = zzvc;
    }

    public zznx(Context context, zznz zznz, zzcu zzcu, zzvf zzvf, zzoa zzoa) {
        this(context, zznz, zzcu, zzoa);
        this.zzHW = zzvf;
    }

    @Nullable
    public final View zza(View.OnClickListener onClickListener, boolean z) {
        IObjectWrapper iObjectWrapper;
        synchronized (this.mLock) {
            if (this.zzHY != null) {
                View zza = this.zzHY.zza(onClickListener, z);
                return zza;
            }
            try {
                if (this.zzHV != null) {
                    iObjectWrapper = this.zzHV.zzfw();
                } else {
                    if (this.zzHW != null) {
                        iObjectWrapper = this.zzHW.zzfw();
                    }
                    iObjectWrapper = null;
                }
            } catch (RemoteException e) {
                zzafr.zzc("Failed to call getAdChoicesContent", e);
            }
            if (iObjectWrapper == null) {
                return null;
            }
            View view = (View) zzn.zzE(iObjectWrapper);
            return view;
        }
    }

    public final void zza(View view, Map<String, WeakReference<View>> map) {
        zzbo.zzcz("recordImpression must be called on the main UI thread.");
        synchronized (this.mLock) {
            this.zzId = true;
            if (this.zzHY != null) {
                this.zzHY.zza(view, map);
                this.zzHX.recordImpression();
            } else {
                try {
                    if (this.zzHV != null && !this.zzHV.getOverrideImpressionRecording()) {
                        this.zzHV.recordImpression();
                        this.zzHX.recordImpression();
                    } else if (this.zzHW != null && !this.zzHW.getOverrideImpressionRecording()) {
                        this.zzHW.recordImpression();
                        this.zzHX.recordImpression();
                    }
                } catch (RemoteException e) {
                    zzafr.zzc("Failed to call recordImpression", e);
                }
            }
        }
    }

    public final void zza(View view, Map<String, WeakReference<View>> map, Bundle bundle, View view2) {
        zzbo.zzcz("performClick must be called on the main UI thread.");
        synchronized (this.mLock) {
            if (this.zzHY != null) {
                this.zzHY.zza(view, map, bundle, view2);
                this.zzHX.onAdClicked();
            } else {
                try {
                    if (this.zzHV != null && !this.zzHV.getOverrideClickHandling()) {
                        this.zzHV.zzl(zzn.zzw(view));
                        this.zzHX.onAdClicked();
                    }
                    if (this.zzHW != null && !this.zzHW.getOverrideClickHandling()) {
                        this.zzHW.zzl(zzn.zzw(view));
                        this.zzHX.onAdClicked();
                    }
                } catch (RemoteException e) {
                    zzafr.zzc("Failed to call performClick", e);
                }
            }
        }
    }

    public final void zza(View view, Map<String, WeakReference<View>> map, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        synchronized (this.mLock) {
            this.zzHZ = true;
            try {
                if (this.zzHV != null) {
                    this.zzHV.zzm(zzn.zzw(view));
                } else if (this.zzHW != null) {
                    this.zzHW.zzm(zzn.zzw(view));
                }
            } catch (RemoteException e) {
                zzafr.zzc("Failed to call prepareAd", e);
            }
            this.zzHZ = false;
        }
    }

    public final void zzb(View view, Map<String, WeakReference<View>> map) {
        synchronized (this.mLock) {
            try {
                if (this.zzHV != null) {
                    this.zzHV.zzn(zzn.zzw(view));
                } else if (this.zzHW != null) {
                    this.zzHW.zzn(zzn.zzw(view));
                }
            } catch (RemoteException e) {
                zzafr.zzc("Failed to call untrackView", e);
            }
        }
    }

    public final void zzc(@Nullable zzny zzny) {
        synchronized (this.mLock) {
            this.zzHY = zzny;
        }
    }

    public final boolean zzep() {
        boolean zzep;
        synchronized (this.mLock) {
            zzep = this.zzHY != null ? this.zzHY.zzep() : this.zzHX.zzaP();
        }
        return zzep;
    }

    public final boolean zzeq() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzHZ;
        }
        return z;
    }

    public final zzny zzer() {
        zzny zzny;
        synchronized (this.mLock) {
            zzny = this.zzHY;
        }
        return zzny;
    }

    @Nullable
    public final zzaka zzes() {
        return null;
    }

    public final void zzet() {
    }
}
