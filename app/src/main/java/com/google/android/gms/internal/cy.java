package com.google.android.gms.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzn;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public final class cy implements Runnable {
    private final Context mContext;
    private final cc zzbKT;
    private final cx zzbKU;
    private final cu zzbKV;
    private final cl zzbKt;

    public cy(Context context, cl clVar, cc ccVar) {
        this(context, clVar, ccVar, new cx(), new cu());
    }

    private cy(Context context, cl clVar, cc ccVar, cx cxVar, cu cuVar) {
        this.mContext = (Context) zzbo.zzu(context);
        this.zzbKT = (cc) zzbo.zzu(ccVar);
        this.zzbKt = clVar;
        this.zzbKU = cxVar;
        this.zzbKV = cuVar;
    }

    private final boolean zzbv(String str) {
        return this.mContext.getPackageManager().checkPermission(str, this.mContext.getPackageName()) == 0;
    }

    public final void run() {
        boolean z;
        String zzb;
        if (!zzbv("android.permission.INTERNET")) {
            zzcvk.e("Missing android.permission.INTERNET. Please add the following declaration to your AndroidManifest.xml: <uses-permission android:name=\"android.permission.INTERNET\" />");
            z = false;
        } else if (!zzbv("android.permission.ACCESS_NETWORK_STATE")) {
            zzcvk.e("Missing android.permission.ACCESS_NETWORK_STATE. Please add the following declaration to your AndroidManifest.xml: <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />");
            z = false;
        } else {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                zzcvk.zzaT("No network connectivity - Offline");
                z = false;
            } else {
                z = true;
            }
        }
        if (!z) {
            this.zzbKT.zzk(0, 0);
            return;
        }
        zzcvk.v("Starting to load resource from Network.");
        cv cvVar = new cv();
        InputStream inputStream = null;
        try {
            zzb = this.zzbKV.zzb(this.zzbKt.zzCP());
            String valueOf = String.valueOf(zzb);
            zzcvk.v(valueOf.length() != 0 ? "Loading resource from ".concat(valueOf) : new String("Loading resource from "));
            inputStream = cvVar.zzfU(zzb);
        } catch (FileNotFoundException e) {
            String valueOf2 = String.valueOf(zzb);
            zzcvk.e(valueOf2.length() != 0 ? "NetworkLoader: No data was retrieved from the given url: ".concat(valueOf2) : new String("NetworkLoader: No data was retrieved from the given url: "));
            this.zzbKT.zzk(2, 0);
            cvVar.close();
            return;
        } catch (da e2) {
            String valueOf3 = String.valueOf(zzb);
            zzcvk.e(valueOf3.length() != 0 ? "NetworkLoader: Error when loading resource for url: ".concat(valueOf3) : new String("NetworkLoader: Error when loading resource for url: "));
            this.zzbKT.zzk(3, 0);
        } catch (IOException e3) {
            String valueOf4 = String.valueOf(e3.getMessage());
            zzcvk.zzb(new StringBuilder(String.valueOf(zzb).length() + 54 + String.valueOf(valueOf4).length()).append("NetworkLoader: Error when loading resource from url: ").append(zzb).append(" ").append(valueOf4).toString(), e3);
            this.zzbKT.zzk(1, 0);
            cvVar.close();
            return;
        } catch (Throwable th) {
            cvVar.close();
            throw th;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            zzn.zza(inputStream, byteArrayOutputStream, false);
            this.zzbKT.zzv(byteArrayOutputStream.toByteArray());
            cvVar.close();
        } catch (IOException e4) {
            String valueOf5 = String.valueOf(e4.getMessage());
            zzcvk.zzb(new StringBuilder(String.valueOf(zzb).length() + 66 + String.valueOf(valueOf5).length()).append("NetworkLoader: Error when parsing downloaded resources from url: ").append(zzb).append(" ").append(valueOf5).toString(), e4);
            this.zzbKT.zzk(2, 0);
            cvVar.close();
        }
    }
}
