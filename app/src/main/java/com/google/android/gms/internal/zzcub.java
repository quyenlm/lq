package com.google.android.gms.internal;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

final class zzcub implements zzcud {
    private /* synthetic */ zzcua zzbHG;

    zzcub(zzcua zzcua) {
        this.zzbHG = zzcua;
    }

    public final AdvertisingIdClient.Info zzAD() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(this.zzbHG.mContext);
        } catch (IllegalStateException e) {
            zzcvk.zzc("IllegalStateException getting Advertising Id Info", e);
            return null;
        } catch (GooglePlayServicesRepairableException e2) {
            zzcvk.zzc("GooglePlayServicesRepairableException getting Advertising Id Info", e2);
            return null;
        } catch (IOException e3) {
            zzcvk.zzc("IOException getting Ad Id Info", e3);
            return null;
        } catch (GooglePlayServicesNotAvailableException e4) {
            boolean unused = this.zzbHG.zzbHD = false;
            zzcvk.zzc("GooglePlayServicesNotAvailableException getting Advertising Id Info", e4);
            return null;
        } catch (Exception e5) {
            zzcvk.zzc("Unknown exception. Could not get the Advertising Id Info.", e5);
            return null;
        }
    }
}
