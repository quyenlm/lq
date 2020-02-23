package com.google.android.gms.internal;

import android.text.TextUtils;
import com.garena.overlay.RecordingService;
import java.util.Map;

@zzzn
public final class zzru implements zzrd {
    private final zzrv zzJJ;

    public zzru(zzrv zzrv) {
        this.zzJJ = zzrv;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        zzaee zzaee;
        String str = map.get("action");
        if ("grant".equals(str)) {
            try {
                int parseInt = Integer.parseInt(map.get("amount"));
                String str2 = map.get("type");
                if (!TextUtils.isEmpty(str2)) {
                    zzaee = new zzaee(str2, parseInt);
                    this.zzJJ.zzb(zzaee);
                }
            } catch (NumberFormatException e) {
                zzafr.zzc("Unable to parse reward amount.", e);
            }
            zzaee = null;
            this.zzJJ.zzb(zzaee);
        } else if (RecordingService.SERVICE_ACTION_VIDEO_START.equals(str)) {
            this.zzJJ.zzbc();
        }
    }
}
