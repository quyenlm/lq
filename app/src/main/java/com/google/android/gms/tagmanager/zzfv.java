package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

final class zzfv implements zzbe {
    private final Context mContext;
    private final String zzJP;
    private final zzfy zzbGR;
    private final zzfx zzbGS;

    zzfv(Context context, zzfx zzfx) {
        this(new zzfw(), context, zzfx);
    }

    private zzfv(zzfy zzfy, Context context, zzfx zzfx) {
        String str = null;
        this.zzbGR = zzfy;
        this.mContext = context.getApplicationContext();
        this.zzbGS = zzfx;
        String str2 = Build.VERSION.RELEASE;
        Locale locale = Locale.getDefault();
        if (!(locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0)) {
            StringBuilder sb = new StringBuilder();
            sb.append(locale.getLanguage().toLowerCase());
            if (!(locale.getCountry() == null || locale.getCountry().length() == 0)) {
                sb.append("-").append(locale.getCountry().toLowerCase());
            }
            str = sb.toString();
        }
        this.zzJP = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{"GoogleTagManager", "4.00", str2, str, Build.MODEL, Build.ID});
    }

    private static URL zzd(zzbx zzbx) {
        try {
            return new URL(zzbx.zzBo());
        } catch (MalformedURLException e) {
            zzdj.e("Error trying to parse the GTM url.");
            return null;
        }
    }

    public final boolean zzBf() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzdj.v("...no network connectivity");
        return false;
    }

    public final void zzK(List<zzbx> list) {
        boolean z;
        HttpURLConnection zzc;
        int min = Math.min(list.size(), 40);
        boolean z2 = true;
        int i = 0;
        while (i < min) {
            zzbx zzbx = list.get(i);
            URL zzd = zzd(zzbx);
            if (zzd == null) {
                zzdj.zzaT("No destination: discarding hit.");
                this.zzbGS.zzb(zzbx);
                z = z2;
            } else {
                InputStream inputStream = null;
                try {
                    zzc = this.zzbGR.zzc(zzd);
                    if (z2) {
                        zzdo.zzbt(this.mContext);
                        z2 = false;
                    }
                    zzc.setRequestProperty("User-Agent", this.zzJP);
                    int responseCode = zzc.getResponseCode();
                    inputStream = zzc.getInputStream();
                    if (responseCode != 200) {
                        zzdj.zzaT(new StringBuilder(25).append("Bad response: ").append(responseCode).toString());
                        this.zzbGS.zzc(zzbx);
                    } else {
                        this.zzbGS.zza(zzbx);
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    zzc.disconnect();
                    z = z2;
                } catch (IOException e) {
                    boolean z3 = z2;
                    String valueOf = String.valueOf(e.getClass().getSimpleName());
                    zzdj.zzaT(valueOf.length() != 0 ? "Exception sending hit: ".concat(valueOf) : new String("Exception sending hit: "));
                    zzdj.zzaT(e.getMessage());
                    this.zzbGS.zzc(zzbx);
                    z = z3;
                } catch (Throwable th) {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    zzc.disconnect();
                    throw th;
                }
            }
            i++;
            z2 = z;
        }
    }
}
