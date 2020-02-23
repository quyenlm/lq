package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.internal.adp;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.er;
import com.google.android.gms.internal.es;
import com.google.android.gms.internal.et;
import com.google.android.gms.internal.zzbq;
import com.google.android.gms.tagmanager.zzei;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

final class zzes implements Runnable {
    private final Context mContext;
    private volatile String zzbDU;
    private final String zzbDw;
    private final es zzbFT;
    private final String zzbFU;
    private zzdi<zzbq> zzbFV;
    private volatile zzal zzbFW;
    private volatile String zzbFX;

    private zzes(Context context, String str, es esVar, zzal zzal) {
        this.mContext = context;
        this.zzbFT = esVar;
        this.zzbDw = str;
        this.zzbFW = zzal;
        String valueOf = String.valueOf("/r?id=");
        String valueOf2 = String.valueOf(str);
        this.zzbFU = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        this.zzbDU = this.zzbFU;
        this.zzbFX = null;
    }

    public zzes(Context context, String str, zzal zzal) {
        this(context, str, new es(), zzal);
    }

    public final void run() {
        boolean z;
        String str;
        if (this.zzbFV == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            zzdj.v("...no network connectivity");
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            this.zzbFV.zzbw(zzda.zzbFh);
            return;
        }
        zzdj.v("Start loading resource from network ...");
        String valueOf = String.valueOf(this.zzbFW.zzAX());
        String str2 = this.zzbDU;
        String valueOf2 = String.valueOf("&v=a65833898");
        String sb = new StringBuilder(String.valueOf(valueOf).length() + String.valueOf(str2).length() + String.valueOf(valueOf2).length()).append(valueOf).append(str2).append(valueOf2).toString();
        if (this.zzbFX != null && !this.zzbFX.trim().equals("")) {
            String valueOf3 = String.valueOf(sb);
            String valueOf4 = String.valueOf("&pv=");
            String str3 = this.zzbFX;
            sb = new StringBuilder(String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(str3).length()).append(valueOf3).append(valueOf4).append(str3).toString();
        }
        if (zzei.zzBD().zzBE().equals(zzei.zza.CONTAINER_DEBUG)) {
            String valueOf5 = String.valueOf(sb);
            String valueOf6 = String.valueOf("&gtm_debug=x");
            str = valueOf6.length() != 0 ? valueOf5.concat(valueOf6) : new String(valueOf5);
        } else {
            str = sb;
        }
        er zzDF = es.zzDF();
        InputStream inputStream = null;
        try {
            inputStream = zzDF.zzfU(str);
        } catch (FileNotFoundException e) {
            String str4 = this.zzbDw;
            zzdj.zzaT(new StringBuilder(String.valueOf(str).length() + 79 + String.valueOf(str4).length()).append("No data is retrieved from the given url: ").append(str).append(". Make sure container_id: ").append(str4).append(" is correct.").toString());
            this.zzbFV.zzbw(zzda.zzbFj);
            zzDF.close();
            return;
        } catch (et e2) {
            String valueOf7 = String.valueOf(str);
            zzdj.zzaT(valueOf7.length() != 0 ? "Error when loading resource for url: ".concat(valueOf7) : new String("Error when loading resource for url: "));
            this.zzbFV.zzbw(zzda.zzbFk);
        } catch (IOException e3) {
            String valueOf8 = String.valueOf(e3.getMessage());
            zzdj.zzc(new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(valueOf8).length()).append("Error when loading resources from url: ").append(str).append(" ").append(valueOf8).toString(), e3);
            this.zzbFV.zzbw(zzda.zzbFi);
            zzDF.close();
            return;
        } catch (Throwable th) {
            zzDF.close();
            throw th;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            eg.zzb(inputStream, byteArrayOutputStream);
            zzbq zzbq = (zzbq) adp.zza(new zzbq(), byteArrayOutputStream.toByteArray());
            String valueOf9 = String.valueOf(zzbq);
            zzdj.v(new StringBuilder(String.valueOf(valueOf9).length() + 43).append("Successfully loaded supplemented resource: ").append(valueOf9).toString());
            if (zzbq.zzlB == null && zzbq.zzlA.length == 0) {
                String valueOf10 = String.valueOf(this.zzbDw);
                zzdj.v(valueOf10.length() != 0 ? "No change for container: ".concat(valueOf10) : new String("No change for container: "));
            }
            this.zzbFV.onSuccess(zzbq);
            zzDF.close();
            zzdj.v("Load resource from network finished.");
        } catch (IOException e4) {
            String valueOf11 = String.valueOf(e4.getMessage());
            zzdj.zzc(new StringBuilder(String.valueOf(str).length() + 51 + String.valueOf(valueOf11).length()).append("Error when parsing downloaded resources from url: ").append(str).append(" ").append(valueOf11).toString(), e4);
            this.zzbFV.zzbw(zzda.zzbFj);
            zzDF.close();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzdi<zzbq> zzdi) {
        this.zzbFV = zzdi;
    }

    /* access modifiers changed from: package-private */
    public final void zzfb(String str) {
        if (str == null) {
            this.zzbDU = this.zzbFU;
            return;
        }
        String valueOf = String.valueOf(str);
        zzdj.zzaC(valueOf.length() != 0 ? "Setting CTFE URL path: ".concat(valueOf) : new String("Setting CTFE URL path: "));
        this.zzbDU = str;
    }

    /* access modifiers changed from: package-private */
    public final void zzfr(String str) {
        String valueOf = String.valueOf(str);
        zzdj.zzaC(valueOf.length() != 0 ? "Setting previous container version: ".concat(valueOf) : new String("Setting previous container version: "));
        this.zzbFX = str;
    }
}
