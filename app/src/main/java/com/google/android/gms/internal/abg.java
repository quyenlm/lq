package com.google.android.gms.internal;

import android.content.Context;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class abg implements Runnable {
    private Context mContext;
    private abh zzcnE;
    private abh zzcnF;
    private abh zzcnG;
    private abk zzcnw;

    public abg(Context context, abh abh, abh abh2, abh abh3, abk abk) {
        this.mContext = context;
        this.zzcnE = abh;
        this.zzcnF = abh2;
        this.zzcnG = abh3;
        this.zzcnw = abk;
    }

    private static abl zza(abh abh) {
        abl abl = new abl();
        if (abh.zzKE() != null) {
            Map<String, Map<String, byte[]>> zzKE = abh.zzKE();
            ArrayList arrayList = new ArrayList();
            for (String next : zzKE.keySet()) {
                ArrayList arrayList2 = new ArrayList();
                Map map = zzKE.get(next);
                for (String str : map.keySet()) {
                    abm abm = new abm();
                    abm.key = str;
                    abm.zzcnR = (byte[]) map.get(str);
                    arrayList2.add(abm);
                }
                abo abo = new abo();
                abo.zzbxU = next;
                abo.zzcnW = (abm[]) arrayList2.toArray(new abm[arrayList2.size()]);
                arrayList.add(abo);
            }
            abl.zzcnO = (abo[]) arrayList.toArray(new abo[arrayList.size()]);
        }
        if (abh.zzss() != null) {
            List<byte[]> zzss = abh.zzss();
            abl.zzcnP = (byte[][]) zzss.toArray(new byte[zzss.size()][]);
        }
        abl.timestamp = abh.getTimestamp();
        return abl;
    }

    public final void run() {
        abp abp = new abp();
        if (this.zzcnE != null) {
            abp.zzcnX = zza(this.zzcnE);
        }
        if (this.zzcnF != null) {
            abp.zzcnY = zza(this.zzcnF);
        }
        if (this.zzcnG != null) {
            abp.zzcnZ = zza(this.zzcnG);
        }
        if (this.zzcnw != null) {
            abn abn = new abn();
            abn.zzcnS = this.zzcnw.getLastFetchStatus();
            abn.zzcnT = this.zzcnw.isDeveloperModeEnabled();
            abn.zzcnU = this.zzcnw.zzKI();
            abp.zzcoa = abn;
        }
        if (!(this.zzcnw == null || this.zzcnw.zzKG() == null)) {
            ArrayList arrayList = new ArrayList();
            Map<String, abe> zzKG = this.zzcnw.zzKG();
            for (String next : zzKG.keySet()) {
                if (zzKG.get(next) != null) {
                    abq abq = new abq();
                    abq.zzbxU = next;
                    abq.zzcod = zzKG.get(next).zzKD();
                    abq.resourceId = zzKG.get(next).zzKC();
                    arrayList.add(abq);
                }
            }
            abp.zzcob = (abq[]) arrayList.toArray(new abq[arrayList.size()]);
        }
        byte[] zzc = adp.zzc(abp);
        try {
            FileOutputStream openFileOutput = this.mContext.openFileOutput("persisted_config", 0);
            openFileOutput.write(zzc);
            openFileOutput.close();
        } catch (IOException e) {
            Log.e("AsyncPersisterTask", "Could not persist config.", e);
        }
    }
}
