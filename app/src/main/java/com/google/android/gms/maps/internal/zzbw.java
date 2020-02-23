package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.vk.sdk.api.VKApiConst;

public final class zzbw {
    private zzbw() {
    }

    public static void zza(Bundle bundle, String str, Parcelable parcelable) {
        bundle.setClassLoader(zzbw.class.getClassLoader());
        Bundle bundle2 = bundle.getBundle("map_state");
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        bundle2.setClassLoader(zzbw.class.getClassLoader());
        bundle2.putParcelable(str, parcelable);
        bundle.putBundle("map_state", bundle2);
    }

    public static void zzd(Bundle bundle, Bundle bundle2) {
        if (bundle != null && bundle2 != null) {
            Parcelable zzg = zzg(bundle, "MapOptions");
            if (zzg != null) {
                zza(bundle2, "MapOptions", zzg);
            }
            Parcelable zzg2 = zzg(bundle, "StreetViewPanoramaOptions");
            if (zzg2 != null) {
                zza(bundle2, "StreetViewPanoramaOptions", zzg2);
            }
            Parcelable zzg3 = zzg(bundle, "camera");
            if (zzg3 != null) {
                zza(bundle2, "camera", zzg3);
            }
            if (bundle.containsKey(VKApiConst.POSITION)) {
                bundle2.putString(VKApiConst.POSITION, bundle.getString(VKApiConst.POSITION));
            }
            if (bundle.containsKey("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT")) {
                bundle2.putBoolean("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT", bundle.getBoolean("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT", false));
            }
        }
    }

    private static <T extends Parcelable> T zzg(Bundle bundle, String str) {
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(zzbw.class.getClassLoader());
        Bundle bundle2 = bundle.getBundle("map_state");
        if (bundle2 == null) {
            return null;
        }
        bundle2.setClassLoader(zzbw.class.getClassLoader());
        return bundle2.getParcelable(str);
    }
}
