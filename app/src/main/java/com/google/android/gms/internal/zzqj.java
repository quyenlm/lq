package com.google.android.gms.internal;

import com.tencent.midas.oversea.network.http.APNetworkManager2;
import java.util.Map;

@zzzn
public final class zzqj implements zzrd {
    private final zzqk zzIT;

    public zzqj(zzqk zzqk) {
        this.zzIT = zzqk;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        String str = map.get("name");
        if (str == null) {
            zzafr.zzaT("App event with no name parameter.");
        } else {
            this.zzIT.onAppEvent(str, map.get(APNetworkManager2.HTTP_KEY_OVERSEAINFO));
        }
    }
}
