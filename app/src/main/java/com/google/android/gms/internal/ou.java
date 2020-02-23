package com.google.android.gms.internal;

import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.Map;

final class ou implements oy {
    private /* synthetic */ String zzbST;
    private /* synthetic */ op zzcbf;
    private /* synthetic */ pf zzcbi;
    private /* synthetic */ long zzcbk;
    private /* synthetic */ pd zzcbl;

    ou(op opVar, String str, long j, pd pdVar, pf pfVar) {
        this.zzcbf = opVar;
        this.zzbST = str;
        this.zzcbk = j;
        this.zzcbl = pdVar;
        this.zzcbi = pfVar;
    }

    public final void zzC(Map<String, Object> map) {
        if (this.zzcbf.zzbZE.zzIH()) {
            wl zza = this.zzcbf.zzbZE;
            String str = this.zzbST;
            String valueOf = String.valueOf(map);
            zza.zzb(new StringBuilder(String.valueOf(str).length() + 11 + String.valueOf(valueOf).length()).append(str).append(" response: ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        if (((pd) this.zzcbf.zzcaS.get(Long.valueOf(this.zzcbk))) == this.zzcbl) {
            this.zzcbf.zzcaS.remove(Long.valueOf(this.zzcbk));
            if (this.zzcbi != null) {
                String str2 = (String) map.get("s");
                if (str2.equals("ok")) {
                    this.zzcbi.zzaa((String) null, (String) null);
                } else {
                    this.zzcbi.zzaa(str2, (String) map.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE));
                }
            }
        } else if (this.zzcbf.zzbZE.zzIH()) {
            this.zzcbf.zzbZE.zzb(new StringBuilder(81).append("Ignoring on complete for put ").append(this.zzcbk).append(" because it was removed already.").toString(), (Throwable) null, new Object[0]);
        }
        this.zzcbf.zzGi();
    }
}
