package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbs;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@zzzn
public final class zznb {
    private final Object mLock = new Object();
    private boolean zzGI;
    private final List<zzmz> zzGZ = new LinkedList();
    private final Map<String, String> zzHa = new LinkedHashMap();
    private String zzHb;
    private zzmz zzHc;
    @Nullable
    private zznb zzHd;

    public zznb(boolean z, String str, String str2) {
        this.zzGI = z;
        this.zzHa.put("action", str);
        this.zzHa.put("ad_format", str2);
    }

    public final void zzO(String str) {
        if (this.zzGI) {
            synchronized (this.mLock) {
                this.zzHb = str;
            }
        }
    }

    public final boolean zza(zzmz zzmz, long j, String... strArr) {
        synchronized (this.mLock) {
            for (String zzmz2 : strArr) {
                this.zzGZ.add(new zzmz(j, zzmz2, zzmz));
            }
        }
        return true;
    }

    public final boolean zza(@Nullable zzmz zzmz, String... strArr) {
        if (!this.zzGI || zzmz == null) {
            return false;
        }
        return zza(zzmz, zzbs.zzbF().elapsedRealtime(), strArr);
    }

    @Nullable
    public final zzmz zzc(long j) {
        if (!this.zzGI) {
            return null;
        }
        return new zzmz(j, (String) null, (zzmz) null);
    }

    public final void zzc(@Nullable zznb zznb) {
        synchronized (this.mLock) {
            this.zzHd = zznb;
        }
    }

    public final zzmz zzdS() {
        return zzc(zzbs.zzbF().elapsedRealtime());
    }

    public final void zzdT() {
        synchronized (this.mLock) {
            this.zzHc = zzdS();
        }
    }

    public final String zzdU() {
        String sb;
        StringBuilder sb2 = new StringBuilder();
        synchronized (this.mLock) {
            for (zzmz next : this.zzGZ) {
                long time = next.getTime();
                String zzdP = next.zzdP();
                zzmz zzdQ = next.zzdQ();
                if (zzdQ != null && time > 0) {
                    sb2.append(zzdP).append('.').append(time - zzdQ.getTime()).append(',');
                }
            }
            this.zzGZ.clear();
            if (!TextUtils.isEmpty(this.zzHb)) {
                sb2.append(this.zzHb);
            } else if (sb2.length() > 0) {
                sb2.setLength(sb2.length() - 1);
            }
            sb = sb2.toString();
        }
        return sb;
    }

    /* access modifiers changed from: package-private */
    public final Map<String, String> zzdV() {
        Map<String, String> zza;
        synchronized (this.mLock) {
            zzmr zzhr = zzbs.zzbD().zzhr();
            zza = (zzhr == null || this.zzHd == null) ? this.zzHa : zzhr.zza(this.zzHa, this.zzHd.zzdV());
        }
        return zza;
    }

    public final zzmz zzdW() {
        zzmz zzmz;
        synchronized (this.mLock) {
            zzmz = this.zzHc;
        }
        return zzmz;
    }

    public final void zzh(String str, String str2) {
        zzmr zzhr;
        if (this.zzGI && !TextUtils.isEmpty(str2) && (zzhr = zzbs.zzbD().zzhr()) != null) {
            synchronized (this.mLock) {
                zzmv zzM = zzhr.zzM(str);
                Map<String, String> map = this.zzHa;
                map.put(str, zzM.zzg(map.get(str), str2));
            }
        }
    }
}
