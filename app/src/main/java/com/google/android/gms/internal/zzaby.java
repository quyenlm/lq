package com.google.android.gms.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.ads.internal.zzbs;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.http.cookie.SM;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzaby {
    private int mOrientation = -1;
    private String zzHD;
    private final zzaae zzMM;
    private boolean zzMy = false;
    private List<String> zzRy;
    private String zzUS;
    private String zzUT;
    private List<String> zzUU;
    private String zzUV;
    private String zzUW;
    private String zzUX;
    private List<String> zzUY;
    private long zzUZ = -1;
    private boolean zzVa = false;
    private final long zzVb = -1;
    private long zzVc = -1;
    private boolean zzVd = false;
    private boolean zzVe = false;
    private boolean zzVf = false;
    private boolean zzVg = true;
    private boolean zzVh = true;
    private String zzVi = "";
    private boolean zzVj = false;
    private zzaee zzVk;
    private List<String> zzVl;
    private List<String> zzVm;
    private boolean zzVn = false;
    private zzaak zzVo;
    private boolean zzVp = false;
    private String zzVq;
    private List<String> zzVr;
    private boolean zzVs;
    private String zzVt;
    private zzaeq zzVu;
    private boolean zzVv;

    public zzaby(zzaae zzaae, String str) {
        this.zzUT = str;
        this.zzMM = zzaae;
    }

    private static String zza(Map<String, List<String>> map, String str) {
        List list = map.get(str);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (String) list.get(0);
    }

    private static long zzb(Map<String, List<String>> map, String str) {
        List list = map.get(str);
        if (list != null && !list.isEmpty()) {
            String str2 = (String) list.get(0);
            try {
                return (long) (Float.parseFloat(str2) * 1000.0f);
            } catch (NumberFormatException e) {
                zzafr.zzaT(new StringBuilder(String.valueOf(str).length() + 36 + String.valueOf(str2).length()).append("Could not parse float from ").append(str).append(" header: ").append(str2).toString());
            }
        }
        return -1;
    }

    private static List<String> zzc(Map<String, List<String>> map, String str) {
        String str2;
        List list = map.get(str);
        if (list == null || list.isEmpty() || (str2 = (String) list.get(0)) == null) {
            return null;
        }
        return Arrays.asList(str2.trim().split("\\s+"));
    }

    private static boolean zzd(Map<String, List<String>> map, String str) {
        List list = map.get(str);
        return list != null && !list.isEmpty() && Boolean.valueOf((String) list.get(0)).booleanValue();
    }

    public final void zza(String str, Map<String, List<String>> map, String str2) {
        this.zzHD = str2;
        zzh(map);
    }

    public final zzaai zze(long j) {
        return new zzaai(this.zzMM, this.zzUT, this.zzHD, this.zzUU, this.zzUY, this.zzUZ, this.zzVa, -1, this.zzRy, this.zzVc, this.mOrientation, this.zzUS, j, this.zzUW, this.zzUX, this.zzVd, this.zzVe, this.zzVf, this.zzVg, false, this.zzVi, this.zzVj, this.zzMy, this.zzVk, this.zzVl, this.zzVm, this.zzVn, this.zzVo, this.zzVp, this.zzVq, this.zzVr, this.zzVs, this.zzVt, this.zzVu, this.zzUV, this.zzVh, this.zzVv);
    }

    public final void zzh(Map<String, List<String>> map) {
        this.zzUS = zza(map, "X-Afma-Ad-Size");
        this.zzVt = zza(map, "X-Afma-Ad-Slot-Size");
        List<String> zzc = zzc(map, "X-Afma-Click-Tracking-Urls");
        if (zzc != null) {
            this.zzUU = zzc;
        }
        this.zzUV = zza(map, "X-Afma-Debug-Signals");
        List list = map.get("X-Afma-Debug-Dialog");
        if (list != null && !list.isEmpty()) {
            this.zzUW = (String) list.get(0);
        }
        List<String> zzc2 = zzc(map, "X-Afma-Tracking-Urls");
        if (zzc2 != null) {
            this.zzUY = zzc2;
        }
        long zzb = zzb(map, "X-Afma-Interstitial-Timeout");
        if (zzb != -1) {
            this.zzUZ = zzb;
        }
        this.zzVa |= zzd(map, "X-Afma-Mediation");
        List<String> zzc3 = zzc(map, "X-Afma-Manual-Tracking-Urls");
        if (zzc3 != null) {
            this.zzRy = zzc3;
        }
        long zzb2 = zzb(map, "X-Afma-Refresh-Rate");
        if (zzb2 != -1) {
            this.zzVc = zzb2;
        }
        List list2 = map.get("X-Afma-Orientation");
        if (list2 != null && !list2.isEmpty()) {
            String str = (String) list2.get(0);
            if ("portrait".equalsIgnoreCase(str)) {
                this.mOrientation = zzbs.zzbB().zzhU();
            } else if ("landscape".equalsIgnoreCase(str)) {
                this.mOrientation = zzbs.zzbB().zzhT();
            }
        }
        this.zzUX = zza(map, "X-Afma-ActiveView");
        List list3 = map.get("X-Afma-Use-HTTPS");
        if (list3 != null && !list3.isEmpty()) {
            this.zzVf = Boolean.valueOf((String) list3.get(0)).booleanValue();
        }
        this.zzVd |= zzd(map, "X-Afma-Custom-Rendering-Allowed");
        this.zzVe = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE.equals(zza(map, "X-Afma-Ad-Format"));
        List list4 = map.get("X-Afma-Content-Url-Opted-Out");
        if (list4 != null && !list4.isEmpty()) {
            this.zzVg = Boolean.valueOf((String) list4.get(0)).booleanValue();
        }
        List list5 = map.get("X-Afma-Content-Vertical-Opted-Out");
        if (list5 != null && !list5.isEmpty()) {
            this.zzVh = Boolean.valueOf((String) list5.get(0)).booleanValue();
        }
        List list6 = map.get("X-Afma-Gws-Query-Id");
        if (list6 != null && !list6.isEmpty()) {
            this.zzVi = (String) list6.get(0);
        }
        String zza = zza(map, "X-Afma-Fluid");
        if (zza != null && zza.equals("height")) {
            this.zzVj = true;
        }
        this.zzMy = "native_express".equals(zza(map, "X-Afma-Ad-Format"));
        this.zzVk = zzaee.zzaz(zza(map, "X-Afma-Rewards"));
        if (this.zzVl == null) {
            this.zzVl = zzc(map, "X-Afma-Reward-Video-Start-Urls");
        }
        if (this.zzVm == null) {
            this.zzVm = zzc(map, "X-Afma-Reward-Video-Complete-Urls");
        }
        this.zzVn |= zzd(map, "X-Afma-Use-Displayed-Impression");
        this.zzVp |= zzd(map, "X-Afma-Auto-Collect-Location");
        this.zzVq = zza(map, SM.SET_COOKIE);
        String zza2 = zza(map, "X-Afma-Auto-Protection-Configuration");
        if (zza2 == null || TextUtils.isEmpty(zza2)) {
            Uri.Builder buildUpon = Uri.parse("https://pagead2.googlesyndication.com/pagead/gen_204").buildUpon();
            buildUpon.appendQueryParameter("id", "gmob-apps-blocked-navigation");
            if (!TextUtils.isEmpty(this.zzUW)) {
                buildUpon.appendQueryParameter("debugDialog", this.zzUW);
            }
            boolean booleanValue = ((Boolean) zzbs.zzbL().zzd(zzmo.zzCf)).booleanValue();
            String valueOf = String.valueOf(buildUpon.toString());
            String valueOf2 = String.valueOf("navigationURL");
            this.zzVo = new zzaak(booleanValue, Arrays.asList(new String[]{new StringBuilder(String.valueOf(valueOf).length() + 18 + String.valueOf(valueOf2).length()).append(valueOf).append(HttpRequest.HTTP_REQ_ENTITY_JOIN).append(valueOf2).append("={NAVIGATION_URL}").toString()}));
        } else {
            try {
                this.zzVo = zzaak.zze(new JSONObject(zza2));
            } catch (JSONException e) {
                zzafr.zzc("Error parsing configuration JSON", e);
                this.zzVo = new zzaak();
            }
        }
        List<String> zzc4 = zzc(map, "X-Afma-Remote-Ping-Urls");
        if (zzc4 != null) {
            this.zzVr = zzc4;
        }
        String zza3 = zza(map, "X-Afma-Safe-Browsing");
        if (!TextUtils.isEmpty(zza3)) {
            try {
                this.zzVu = zzaeq.zzf(new JSONObject(zza3));
            } catch (JSONException e2) {
                zzafr.zzc("Error parsing safe browsing header", e2);
            }
        }
        this.zzVs |= zzd(map, "X-Afma-Render-In-Browser");
        String zza4 = zza(map, "X-Afma-Pool");
        if (!TextUtils.isEmpty(zza4)) {
            try {
                this.zzVv = new JSONObject(zza4).getBoolean("never_pool");
            } catch (JSONException e3) {
                zzafr.zzc("Error parsing interstitial pool header", e3);
            }
        }
    }
}
