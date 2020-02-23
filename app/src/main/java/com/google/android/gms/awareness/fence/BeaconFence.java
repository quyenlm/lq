package com.google.android.gms.awareness.fence;

import android.support.annotation.RequiresPermission;
import com.google.android.gms.awareness.state.BeaconState;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbis;
import com.google.android.gms.internal.zzbiy;
import java.util.Collection;

public final class BeaconFence {
    private BeaconFence() {
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static AwarenessFence found(Collection<BeaconState.TypeFilter> collection) {
        zzbo.zzaf(collection != null && !collection.isEmpty());
        return found((BeaconState.TypeFilter[]) collection.toArray(new BeaconState.TypeFilter[collection.size()]));
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static AwarenessFence found(BeaconState.TypeFilter... typeFilterArr) {
        zzbo.zzaf(typeFilterArr != null && typeFilterArr.length > 0);
        return zzbiy.zza(zzbis.zza(typeFilterArr));
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static AwarenessFence lost(Collection<BeaconState.TypeFilter> collection) {
        zzbo.zzaf(collection != null && !collection.isEmpty());
        return lost((BeaconState.TypeFilter[]) collection.toArray(new BeaconState.TypeFilter[collection.size()]));
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static AwarenessFence lost(BeaconState.TypeFilter... typeFilterArr) {
        zzbo.zzaf(typeFilterArr != null && typeFilterArr.length > 0);
        return zzbiy.zza(zzbis.zzb(typeFilterArr));
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static AwarenessFence near(Collection<BeaconState.TypeFilter> collection) {
        zzbo.zzaf(collection != null && !collection.isEmpty());
        return near((BeaconState.TypeFilter[]) collection.toArray(new BeaconState.TypeFilter[collection.size()]));
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static AwarenessFence near(BeaconState.TypeFilter... typeFilterArr) {
        zzbo.zzaf(typeFilterArr != null && typeFilterArr.length > 0);
        return zzbiy.zza(zzbis.zzc(typeFilterArr));
    }
}
