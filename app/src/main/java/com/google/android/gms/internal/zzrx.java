package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbs;
import java.util.Map;

@zzzn
final class zzrx implements zzrd {
    zzrx() {
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        zzaks zzaks;
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzEC)).booleanValue()) {
            zzaks zziH = zzaka.zziH();
            if (zziH == null) {
                try {
                    zzaks zzaks2 = new zzaks(zzaka, Float.parseFloat(map.get("duration")), "1".equals(map.get("customControlsAllowed")));
                    zzaka.zza(zzaks2);
                    zzaks = zzaks2;
                } catch (NullPointerException | NumberFormatException e) {
                    zzafr.zzb("Unable to parse videoMeta message.", e);
                    zzbs.zzbD().zza(e, "VideoMetaGmsgHandler.onGmsg");
                    return;
                }
            } else {
                zzaks = zziH;
            }
            boolean equals = "1".equals(map.get("muted"));
            float parseFloat = Float.parseFloat(map.get("currentTime"));
            int parseInt = Integer.parseInt(map.get("playbackState"));
            int i = (parseInt < 0 || 3 < parseInt) ? 0 : parseInt;
            String str = map.get("aspectRatio");
            float parseFloat2 = TextUtils.isEmpty(str) ? 0.0f : Float.parseFloat(str);
            if (zzafr.zzz(3)) {
                zzafr.zzaC(new StringBuilder(String.valueOf(str).length() + 79).append("Video Meta GMSG: isMuted : ").append(equals).append(" , playbackState : ").append(i).append(" , aspectRatio : ").append(str).toString());
            }
            zzaks.zza(parseFloat, i, equals, parseFloat2);
        }
    }
}
