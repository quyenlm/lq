package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.android.gms.ads.internal.overlay.zzaa;
import com.google.android.gms.ads.internal.overlay.zzaq;
import com.google.android.gms.ads.internal.zzbs;
import com.tencent.component.plugin.PluginReporter;
import com.tencent.imsdk.expansion.downloader.DownloaderServiceMarshaller;
import com.vk.sdk.api.VKApiConst;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

@zzzn
public final class zzrw implements zzrd {
    private boolean zzJK;

    private static int zza(Context context, Map<String, String> map, String str, int i) {
        String str2 = map.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            zzji.zzds();
            return zzaiy.zzc(context, Integer.parseInt(str2));
        } catch (NumberFormatException e) {
            zzafr.zzaT(new StringBuilder(String.valueOf(str).length() + 34 + String.valueOf(str2).length()).append("Could not parse ").append(str).append(" in a video GMSG: ").append(str2).toString());
            return i;
        }
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        int i;
        int i2;
        String str = map.get("action");
        if (str == null) {
            zzafr.zzaT("Action missing from video GMSG.");
            return;
        }
        if (zzafr.zzz(3)) {
            JSONObject jSONObject = new JSONObject(map);
            jSONObject.remove("google.afma.Notify_dt");
            String valueOf = String.valueOf(jSONObject.toString());
            zzafr.zzaC(new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(valueOf).length()).append("Video GMSG: ").append(str).append(" ").append(valueOf).toString());
        }
        if ("background".equals(str)) {
            String str2 = map.get("color");
            if (TextUtils.isEmpty(str2)) {
                zzafr.zzaT("Color parameter missing from color video GMSG.");
                return;
            }
            try {
                zzaka.setBackgroundColor(Color.parseColor(str2));
            } catch (IllegalArgumentException e) {
                zzafr.zzaT("Invalid color parameter in video GMSG.");
            }
        } else if ("decoderProps".equals(str)) {
            String str3 = map.get("mimeTypes");
            if (str3 == null) {
                zzafr.zzaT("No MIME types specified for decoder properties inspection.");
                zzaa.zza(zzaka, "missingMimeTypes");
            } else if (Build.VERSION.SDK_INT < 16) {
                zzafr.zzaT("Video decoder properties available on API versions >= 16.");
                zzaa.zza(zzaka, "deficientApiVersion");
            } else {
                HashMap hashMap = new HashMap();
                for (String str4 : str3.split(",")) {
                    hashMap.put(str4, zzaiw.zzaQ(str4.trim()));
                }
                zzaa.zzc(zzaka, hashMap);
            }
        } else {
            zzajz zziE = zzaka.zziE();
            if (zziE == null) {
                zzafr.zzaT("Could not get underlay container for a video GMSG.");
                return;
            }
            boolean equals = "new".equals(str);
            boolean equals2 = VKApiConst.POSITION.equals(str);
            if (equals || equals2) {
                Context context = zzaka.getContext();
                int zza = zza(context, map, "x", 0);
                int zza2 = zza(context, map, "y", 0);
                int zza3 = zza(context, map, "w", -1);
                int zza4 = zza(context, map, "h", -1);
                if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFB)).booleanValue()) {
                    i = Math.min(zza3, zzaka.getMeasuredWidth() - zza);
                    zza4 = Math.min(zza4, zzaka.getMeasuredHeight() - zza2);
                } else {
                    i = zza3;
                }
                try {
                    i2 = Integer.parseInt(map.get("player"));
                } catch (NumberFormatException e2) {
                    i2 = 0;
                }
                boolean parseBoolean = Boolean.parseBoolean(map.get("spherical"));
                if (!equals || zziE.zzip() != null) {
                    zziE.zze(zza, zza2, i, zza4);
                } else {
                    zziE.zza(zza, zza2, i, zza4, i2, parseBoolean, new zzaq(map.get(DownloaderServiceMarshaller.PARAMS_FLAGS)));
                }
            } else {
                zzaa zzip = zziE.zzip();
                if (zzip == null) {
                    zzaa.zzh(zzaka);
                } else if ("click".equals(str)) {
                    Context context2 = zzaka.getContext();
                    int zza5 = zza(context2, map, "x", 0);
                    int zza6 = zza(context2, map, "y", 0);
                    long uptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, (float) zza5, (float) zza6, 0);
                    zzip.zze(obtain);
                    obtain.recycle();
                } else if ("currentTime".equals(str)) {
                    String str5 = map.get("time");
                    if (str5 == null) {
                        zzafr.zzaT("Time parameter missing from currentTime video GMSG.");
                        return;
                    }
                    try {
                        zzip.seekTo((int) (Float.parseFloat(str5) * 1000.0f));
                    } catch (NumberFormatException e3) {
                        String valueOf2 = String.valueOf(str5);
                        zzafr.zzaT(valueOf2.length() != 0 ? "Could not parse time parameter from currentTime video GMSG: ".concat(valueOf2) : new String("Could not parse time parameter from currentTime video GMSG: "));
                    }
                } else if (MessengerShareContentUtility.SHARE_BUTTON_HIDE.equals(str)) {
                    zzip.setVisibility(4);
                } else if (PluginReporter.EVENT_LOAD.equals(str)) {
                    zzip.zzfY();
                } else if ("muted".equals(str)) {
                    if (Boolean.parseBoolean(map.get("muted"))) {
                        zzip.zzfZ();
                    } else {
                        zzip.zzga();
                    }
                } else if ("pause".equals(str)) {
                    zzip.pause();
                } else if ("play".equals(str)) {
                    zzip.play();
                } else if ("show".equals(str)) {
                    zzip.setVisibility(0);
                } else if ("src".equals(str)) {
                    zzip.zzaq(map.get("src"));
                } else if ("touchMove".equals(str)) {
                    Context context3 = zzaka.getContext();
                    zzip.zza((float) zza(context3, map, "dx", 0), (float) zza(context3, map, "dy", 0));
                    if (!this.zzJK) {
                        zzaka.zziu().zzfQ();
                        this.zzJK = true;
                    }
                } else if (MediaRouteProviderProtocol.CLIENT_DATA_VOLUME.equals(str)) {
                    String str6 = map.get(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME);
                    if (str6 == null) {
                        zzafr.zzaT("Level parameter missing from volume video GMSG.");
                        return;
                    }
                    try {
                        zzip.zzb(Float.parseFloat(str6));
                    } catch (NumberFormatException e4) {
                        String valueOf3 = String.valueOf(str6);
                        zzafr.zzaT(valueOf3.length() != 0 ? "Could not parse volume parameter from volume video GMSG: ".concat(valueOf3) : new String("Could not parse volume parameter from volume video GMSG: "));
                    }
                } else if ("watermark".equals(str)) {
                    zzip.zzgb();
                } else {
                    String valueOf4 = String.valueOf(str);
                    zzafr.zzaT(valueOf4.length() != 0 ? "Unknown video action: ".concat(valueOf4) : new String("Unknown video action: "));
                }
            }
        }
    }
}
