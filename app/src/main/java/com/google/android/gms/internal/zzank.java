package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzr;
import java.util.HashSet;
import java.util.Set;

public final class zzank {
    private final zzamj zzadj;
    private volatile Boolean zzagU;
    private String zzagV;
    private Set<Integer> zzagW;

    protected zzank(zzamj zzamj) {
        zzbo.zzu(zzamj);
        this.zzadj = zzamj;
    }

    public static boolean zzlo() {
        return zzans.zzahf.get().booleanValue();
    }

    public static int zzlp() {
        return zzans.zzahC.get().intValue();
    }

    public static long zzlq() {
        return zzans.zzahn.get().longValue();
    }

    public static long zzlr() {
        return zzans.zzahq.get().longValue();
    }

    public static int zzls() {
        return zzans.zzahs.get().intValue();
    }

    public static int zzlt() {
        return zzans.zzaht.get().intValue();
    }

    public static String zzlu() {
        return zzans.zzahv.get();
    }

    public static String zzlv() {
        return zzans.zzahu.get();
    }

    public static String zzlw() {
        return zzans.zzahw.get();
    }

    public static long zzly() {
        return zzans.zzahK.get().longValue();
    }

    public final boolean zzln() {
        if (this.zzagU == null) {
            synchronized (this) {
                if (this.zzagU == null) {
                    ApplicationInfo applicationInfo = this.zzadj.getContext().getApplicationInfo();
                    String zzsf = zzr.zzsf();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzagU = Boolean.valueOf(str != null && str.equals(zzsf));
                    }
                    if ((this.zzagU == null || !this.zzagU.booleanValue()) && "com.google.android.gms.analytics".equals(zzsf)) {
                        this.zzagU = Boolean.TRUE;
                    }
                    if (this.zzagU == null) {
                        this.zzagU = Boolean.TRUE;
                        this.zzadj.zzkr().zzbs("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzagU.booleanValue();
    }

    public final Set<Integer> zzlx() {
        String str = zzans.zzahF.get();
        if (this.zzagW == null || this.zzagV == null || !this.zzagV.equals(str)) {
            String[] split = TextUtils.split(str, ",");
            HashSet hashSet = new HashSet();
            for (String parseInt : split) {
                try {
                    hashSet.add(Integer.valueOf(Integer.parseInt(parseInt)));
                } catch (NumberFormatException e) {
                }
            }
            this.zzagV = str;
            this.zzagW = hashSet;
        }
        return this.zzagW;
    }
}
