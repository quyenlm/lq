package com.google.android.gms.internal;

import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.Map;

final class os implements oy {
    private /* synthetic */ pf zzcbi;

    os(op opVar, pf pfVar) {
        this.zzcbi = pfVar;
    }

    public final void zzC(Map<String, Object> map) {
        String str;
        String str2 = (String) map.get("s");
        if (!str2.equals("ok")) {
            str = (String) map.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
        } else {
            str2 = null;
            str = null;
        }
        if (this.zzcbi != null) {
            this.zzcbi.zzaa(str2, str);
        }
    }
}
