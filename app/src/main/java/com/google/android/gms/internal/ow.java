package com.google.android.gms.internal;

import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.tp.a.h;
import java.util.Map;

final class ow implements oy {
    private /* synthetic */ op zzcbf;

    ow(op opVar) {
        this.zzcbf = opVar;
    }

    public final void zzC(Map<String, Object> map) {
        String str = (String) map.get("s");
        if (!str.equals("ok")) {
            String str2 = (String) map.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
            if (this.zzcbf.zzbZE.zzIH()) {
                this.zzcbf.zzbZE.zzb(new StringBuilder(String.valueOf(str).length() + 34 + String.valueOf(str2).length()).append("Failed to send stats: ").append(str).append(" (message: ").append(str2).append(h.b).toString(), (Throwable) null, new Object[0]);
            }
        }
    }
}
