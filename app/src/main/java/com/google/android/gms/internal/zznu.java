package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@zzzn
public final class zznu extends zzpk implements zzoa {
    private final Object mLock = new Object();
    private final zznn zzHJ;
    @Nullable
    private zzks zzHK;
    @Nullable
    private View zzHL;
    private zzny zzHM;
    private final String zzHR;
    private final SimpleArrayMap<String, zznp> zzHS;
    private final SimpleArrayMap<String, String> zzHT;

    public zznu(String str, SimpleArrayMap<String, zznp> simpleArrayMap, SimpleArrayMap<String, String> simpleArrayMap2, zznn zznn, zzks zzks, View view) {
        this.zzHR = str;
        this.zzHS = simpleArrayMap;
        this.zzHT = simpleArrayMap2;
        this.zzHJ = zznn;
        this.zzHK = zzks;
        this.zzHL = view;
    }

    public final void destroy() {
        this.zzHM = null;
        this.zzHK = null;
        this.zzHL = null;
    }

    public final List<String> getAvailableAssetNames() {
        int i = 0;
        String[] strArr = new String[(this.zzHS.size() + this.zzHT.size())];
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzHS.size(); i3++) {
            strArr[i2] = this.zzHS.keyAt(i3);
            i2++;
        }
        while (i < this.zzHT.size()) {
            strArr[i2] = this.zzHT.keyAt(i);
            i++;
            i2++;
        }
        return Arrays.asList(strArr);
    }

    public final String getCustomTemplateId() {
        return this.zzHR;
    }

    public final zzks getVideoController() {
        return this.zzHK;
    }

    public final void performClick(String str) {
        synchronized (this.mLock) {
            if (this.zzHM == null) {
                zzajc.e("Attempt to call performClick before ad initialized.");
            } else {
                this.zzHM.zza((View) null, str, (Bundle) null, (Map<String, WeakReference<View>>) null, (View) null);
            }
        }
    }

    public final void recordImpression() {
        synchronized (this.mLock) {
            if (this.zzHM == null) {
                zzajc.e("Attempt to perform recordImpression before ad initialized.");
            } else {
                this.zzHM.zza((View) null, (Map<String, WeakReference<View>>) null);
            }
        }
    }

    public final String zzP(String str) {
        return this.zzHT.get(str);
    }

    public final zzos zzQ(String str) {
        return this.zzHS.get(str);
    }

    public final void zzb(zzny zzny) {
        synchronized (this.mLock) {
            this.zzHM = zzny;
        }
    }

    public final IObjectWrapper zzei() {
        return zzn.zzw(this.zzHM);
    }

    public final String zzej() {
        return "3";
    }

    public final zznn zzek() {
        return this.zzHJ;
    }

    public final View zzel() {
        return this.zzHL;
    }

    public final IObjectWrapper zzen() {
        return zzn.zzw(this.zzHM.getContext().getApplicationContext());
    }

    public final boolean zzj(IObjectWrapper iObjectWrapper) {
        if (this.zzHM == null) {
            zzajc.e("Attempt to call renderVideoInMediaView before ad initialized.");
            return false;
        } else if (this.zzHL == null) {
            return false;
        } else {
            zznv zznv = new zznv(this);
            this.zzHM.zza((View) (FrameLayout) zzn.zzE(iObjectWrapper), (zznw) zznv);
            return true;
        }
    }
}
