package com.google.android.gms.internal;

import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.tp.a.h;
import java.util.Map;

final class ot implements oy {
    private /* synthetic */ op zzcbf;
    private /* synthetic */ boolean zzcbj;

    ot(op opVar, boolean z) {
        this.zzcbf = opVar;
        this.zzcbj = z;
    }

    public final void zzC(Map<String, Object> map) {
        oz unused = this.zzcbf.zzcaN = oz.Connected;
        String str = (String) map.get("s");
        if (str.equals("ok")) {
            int unused2 = this.zzcbf.zzcba = 0;
            this.zzcbf.zzcaH.zzaB(true);
            if (this.zzcbj) {
                this.zzcbf.zzGh();
                return;
            }
            return;
        }
        String unused3 = this.zzcbf.zzcaU = null;
        boolean unused4 = this.zzcbf.zzcaV = true;
        this.zzcbf.zzcaH.zzaB(false);
        String str2 = (String) map.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
        this.zzcbf.zzbZE.zzb(new StringBuilder(String.valueOf(str).length() + 26 + String.valueOf(str2).length()).append("Authentication failed: ").append(str).append(" (").append(str2).append(h.b).toString(), (Throwable) null, new Object[0]);
        this.zzcbf.zzcaM.close();
        if (str.equals("invalid_token")) {
            op.zzj(this.zzcbf);
            if (((long) this.zzcbf.zzcba) >= 3) {
                this.zzcbf.zzcaX.zzGB();
                this.zzcbf.zzbZE.zze("Provided authentication credentials are invalid. This usually indicates your FirebaseApp instance was not initialized correctly. Make sure your google-services.json file has the correct firebase_url and api_key. You can re-download google-services.json from https://console.firebase.google.com/.", (Throwable) null);
            }
        }
    }
}
