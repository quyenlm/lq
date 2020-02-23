package com.google.android.gms.ads.internal;

import android.os.AsyncTask;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzmo;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class zzbq extends AsyncTask<Void, Void, String> {
    private /* synthetic */ zzbm zzvf;

    private zzbq(zzbm zzbm) {
        this.zzvf = zzbm;
    }

    /* synthetic */ zzbq(zzbm zzbm, zzbn zzbn) {
        this(zzbm);
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final String doInBackground(Void... voidArr) {
        try {
            zzeu unused = this.zzvf.zzvd = (zzeu) this.zzvf.zzva.get(((Long) zzbs.zzbL().zzd(zzmo.zzFX)).longValue(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            zzafr.zzc("Failed to load ad data", e);
        } catch (TimeoutException e2) {
            zzafr.zzaT("Timed out waiting for ad data");
        }
        return this.zzvf.zzbp();
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        String str = (String) obj;
        if (this.zzvf.zzvc != null && str != null) {
            this.zzvf.zzvc.loadUrl(str);
        }
    }
}
