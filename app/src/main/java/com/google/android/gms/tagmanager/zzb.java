package com.google.android.gms.tagmanager;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

final class zzb implements zzd {
    private /* synthetic */ zza zzbDm;

    zzb(zza zza) {
        this.zzbDm = zza;
    }

    public final AdvertisingIdClient.Info zzAD() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(this.zzbDm.mContext);
        } catch (IllegalStateException e) {
            zzdj.zzc("IllegalStateException getting Advertising Id Info", e);
            return null;
        } catch (GooglePlayServicesRepairableException e2) {
            zzdj.zzc("GooglePlayServicesRepairableException getting Advertising Id Info", e2);
            return null;
        } catch (IOException e3) {
            zzdj.zzc("IOException getting Ad Id Info", e3);
            return null;
        } catch (GooglePlayServicesNotAvailableException e4) {
            zzdj.zzc("GooglePlayServicesNotAvailableException getting Advertising Id Info", e4);
            return null;
        } catch (Exception e5) {
            zzdj.zzc("Unknown exception. Could not get the Advertising Id Info.", e5);
            return null;
        }
    }
}
