package com.google.android.gms.internal;

import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.List;
import java.util.Map;

final class ov implements oy {
    private /* synthetic */ op zzcbf;
    private /* synthetic */ pc zzcbm;

    ov(op opVar, pc pcVar) {
        this.zzcbf = opVar;
        this.zzcbm = pcVar;
    }

    public final void zzC(Map<String, Object> map) {
        String str = (String) map.get("s");
        if (str.equals("ok")) {
            Map map2 = (Map) map.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
            if (map2.containsKey("w")) {
                this.zzcbf.zza((List<String>) (List) map2.get("w"), this.zzcbm.zzcby);
            }
        }
        if (((pc) this.zzcbf.zzcaT.get(this.zzcbm.zzGm())) != this.zzcbm) {
            return;
        }
        if (!str.equals("ok")) {
            pc unused = this.zzcbf.zza(this.zzcbm.zzGm());
            this.zzcbm.zzcbx.zzaa(str, (String) map.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE));
            return;
        }
        this.zzcbm.zzcbx.zzaa((String) null, (String) null);
    }
}
