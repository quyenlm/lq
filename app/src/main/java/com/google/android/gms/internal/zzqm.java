package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.plus.PlusShare;
import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.Map;

@zzzn
public final class zzqm implements zzrd {
    public final void zza(zzaka zzaka, Map<String, String> map) {
        String str = map.get("action");
        if ("tick".equals(str)) {
            String str2 = map.get(PlusShare.KEY_CALL_TO_ACTION_LABEL);
            String str3 = map.get("start_label");
            String str4 = map.get("timestamp");
            if (TextUtils.isEmpty(str2)) {
                zzafr.zzaT("No label given for CSI tick.");
            } else if (TextUtils.isEmpty(str4)) {
                zzafr.zzaT("No timestamp given for CSI tick.");
            } else {
                try {
                    long parseLong = (Long.parseLong(str4) - zzbs.zzbF().currentTimeMillis()) + zzbs.zzbF().elapsedRealtime();
                    if (TextUtils.isEmpty(str3)) {
                        str3 = "native:view_load";
                    }
                    zzaka.zziG().zza(str2, str3, parseLong);
                } catch (NumberFormatException e) {
                    zzafr.zzc("Malformed timestamp for CSI tick.", e);
                }
            }
        } else if ("experiment".equals(str)) {
            String str5 = map.get("value");
            if (TextUtils.isEmpty(str5)) {
                zzafr.zzaT("No value given for CSI experiment.");
                return;
            }
            zznb zzdR = zzaka.zziG().zzdR();
            if (zzdR == null) {
                zzafr.zzaT("No ticker for WebView, dropping experiment ID.");
            } else {
                zzdR.zzh(APDataReportManager.ACCOUNTINPUT_PRE, str5);
            }
        } else if ("extra".equals(str)) {
            String str6 = map.get("name");
            String str7 = map.get("value");
            if (TextUtils.isEmpty(str7)) {
                zzafr.zzaT("No value given for CSI extra.");
            } else if (TextUtils.isEmpty(str6)) {
                zzafr.zzaT("No name given for CSI extra.");
            } else {
                zznb zzdR2 = zzaka.zziG().zzdR();
                if (zzdR2 == null) {
                    zzafr.zzaT("No ticker for WebView, dropping extra parameter.");
                } else {
                    zzdR2.zzh(str6, str7);
                }
            }
        }
    }
}
