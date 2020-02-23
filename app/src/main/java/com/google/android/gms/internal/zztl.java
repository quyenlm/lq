package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Base64;
import com.appsflyer.share.Constants;
import com.google.android.gms.ads.internal.zzbs;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@zzzn
public final class zztl {
    private final Map<zztm, zztn> zzKo = new HashMap();
    private final LinkedList<zztm> zzKp = new LinkedList<>();
    @Nullable
    private zzsi zzKq;

    private static String[] zzY(String str) {
        try {
            String[] split = str.split("\u0000");
            for (int i = 0; i < split.length; i++) {
                split[i] = new String(Base64.decode(split[i], 0), "UTF-8");
            }
            return split;
        } catch (UnsupportedEncodingException e) {
            return new String[0];
        }
    }

    private static boolean zzZ(String str) {
        try {
            return Pattern.matches((String) zzbs.zzbL().zzd(zzmo.zzEf), str);
        } catch (RuntimeException e) {
            zzbs.zzbD().zza((Throwable) e, "InterstitialAdPool.isExcludedAdUnit");
            return false;
        }
    }

    private static void zza(String str, zztm zztm) {
        if (zzafr.zzz(2)) {
            zzafr.v(String.format(str, new Object[]{zztm}));
        }
    }

    private static String zzaa(String str) {
        try {
            Matcher matcher = Pattern.compile("([^/]+/[0-9]+).*").matcher(str);
            return matcher.matches() ? matcher.group(1) : str;
        } catch (RuntimeException e) {
            return str;
        }
    }

    private static void zzc(Bundle bundle, String str) {
        while (true) {
            String[] split = str.split(Constants.URL_PATH_DELIMITER, 2);
            if (split.length != 0) {
                String str2 = split[0];
                if (split.length == 1) {
                    bundle.remove(str2);
                    return;
                }
                bundle = bundle.getBundle(str2);
                if (bundle != null) {
                    str = split[1];
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private final String zzeH() {
        try {
            StringBuilder sb = new StringBuilder();
            Iterator it = this.zzKp.iterator();
            while (it.hasNext()) {
                sb.append(Base64.encodeToString(((zztm) it.next()).toString().getBytes("UTF-8"), 0));
                if (it.hasNext()) {
                    sb.append("\u0000");
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    static Set<String> zzi(zzir zzir) {
        HashSet hashSet = new HashSet();
        hashSet.addAll(zzir.extras.keySet());
        Bundle bundle = zzir.zzzX.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        if (bundle != null) {
            hashSet.addAll(bundle.keySet());
        }
        return hashSet;
    }

    static zzir zzj(zzir zzir) {
        zzir zzl = zzl(zzir);
        Bundle bundle = zzl.zzzX.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        if (bundle != null) {
            bundle.putBoolean("_skipMediation", true);
        }
        zzl.extras.putBoolean("_skipMediation", true);
        return zzl;
    }

    private static zzir zzk(zzir zzir) {
        zzir zzl = zzl(zzir);
        for (String str : ((String) zzbs.zzbL().zzd(zzmo.zzEb)).split(",")) {
            zzc(zzl.zzzX, str);
            if (str.startsWith("com.google.ads.mediation.admob.AdMobAdapter/")) {
                zzc(zzl.extras, str.replaceFirst("com.google.ads.mediation.admob.AdMobAdapter/", ""));
            }
        }
        return zzl;
    }

    private static zzir zzl(zzir zzir) {
        Parcel obtain = Parcel.obtain();
        zzir.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        zzir zzir2 = (zzir) zzir.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDR)).booleanValue()) {
            zzir.zzh(zzir2);
        }
        return zzir2;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzto zza(zzir zzir, String str) {
        zztn zztn;
        if (zzZ(str)) {
            return null;
        }
        int i = new zzacc(this.zzKq.getApplicationContext()).zzgM().zzVS;
        zzir zzk = zzk(zzir);
        String zzaa = zzaa(str);
        zztm zztm = new zztm(zzk, zzaa, i);
        zztn zztn2 = this.zzKo.get(zztm);
        if (zztn2 == null) {
            zza("Interstitial pool created at %s.", zztm);
            zztn zztn3 = new zztn(zzk, zzaa, i);
            this.zzKo.put(zztm, zztn3);
            zztn = zztn3;
        } else {
            zztn = zztn2;
        }
        this.zzKp.remove(zztm);
        this.zzKp.add(zztm);
        zztn.zzeL();
        while (true) {
            if (this.zzKp.size() <= ((Integer) zzbs.zzbL().zzd(zzmo.zzEc)).intValue()) {
                break;
            }
            zztm remove = this.zzKp.remove();
            zztn zztn4 = this.zzKo.get(remove);
            zza("Evicting interstitial queue for %s.", remove);
            while (zztn4.size() > 0) {
                zzto zzm = zztn4.zzm((zzir) null);
                if (zzm.zzKz) {
                    zztp.zzeN().zzeP();
                }
                zzm.zzKv.zzbb();
            }
            this.zzKo.remove(remove);
        }
        while (zztn.size() > 0) {
            zzto zzm2 = zztn.zzm(zzk);
            if (zzm2.zzKz) {
                if (zzbs.zzbF().currentTimeMillis() - zzm2.zzKy > 1000 * ((long) ((Integer) zzbs.zzbL().zzd(zzmo.zzEe)).intValue())) {
                    zza("Expired interstitial at %s.", zztm);
                    zztp.zzeN().zzeO();
                }
            }
            String str2 = zzm2.zzKw != null ? " (inline) " : " ";
            zza(new StringBuilder(String.valueOf(str2).length() + 34).append("Pooled interstitial").append(str2).append("returned at %s.").toString(), zztm);
            return zzm2;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzsi zzsi) {
        if (this.zzKq == null) {
            this.zzKq = zzsi.zzeF();
            if (this.zzKq != null) {
                SharedPreferences sharedPreferences = this.zzKq.getApplicationContext().getSharedPreferences("com.google.android.gms.ads.internal.interstitial.InterstitialAdPool", 0);
                while (this.zzKp.size() > 0) {
                    zztm remove = this.zzKp.remove();
                    zztn zztn = this.zzKo.get(remove);
                    zza("Flushing interstitial queue for %s.", remove);
                    while (zztn.size() > 0) {
                        zztn.zzm((zzir) null).zzKv.zzbb();
                    }
                    this.zzKo.remove(remove);
                }
                try {
                    HashMap hashMap = new HashMap();
                    for (Map.Entry next : sharedPreferences.getAll().entrySet()) {
                        if (!((String) next.getKey()).equals("PoolKeys")) {
                            zztr zzab = zztr.zzab((String) next.getValue());
                            zztm zztm = new zztm(zzab.zzuT, zzab.zztV, zzab.zzKt);
                            if (!this.zzKo.containsKey(zztm)) {
                                this.zzKo.put(zztm, new zztn(zzab.zzuT, zzab.zztV, zzab.zzKt));
                                hashMap.put(zztm.toString(), zztm);
                                zza("Restored interstitial queue for %s.", zztm);
                            }
                        }
                    }
                    for (String str : zzY(sharedPreferences.getString("PoolKeys", ""))) {
                        zztm zztm2 = (zztm) hashMap.get(str);
                        if (this.zzKo.containsKey(zztm2)) {
                            this.zzKp.add(zztm2);
                        }
                    }
                } catch (IOException | RuntimeException e) {
                    zzbs.zzbD().zza(e, "InterstitialAdPool.restore");
                    zzafr.zzc("Malformed preferences value for InterstitialAdPool.", e);
                    this.zzKo.clear();
                    this.zzKp.clear();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzir zzir, String str) {
        if (this.zzKq != null) {
            int i = new zzacc(this.zzKq.getApplicationContext()).zzgM().zzVS;
            zzir zzk = zzk(zzir);
            String zzaa = zzaa(str);
            zztm zztm = new zztm(zzk, zzaa, i);
            zztn zztn = this.zzKo.get(zztm);
            if (zztn == null) {
                zza("Interstitial pool created at %s.", zztm);
                zztn = new zztn(zzk, zzaa, i);
                this.zzKo.put(zztm, zztn);
            }
            zztn.zza(this.zzKq, zzir);
            zztn.zzeL();
            zza("Inline entry added to the queue at %s.", zztm);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002f, code lost:
        r2 = r0.size();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzeG() {
        /*
            r9 = this;
            r8 = 2
            r7 = 0
            com.google.android.gms.internal.zzsi r0 = r9.zzKq
            if (r0 != 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            java.util.Map<com.google.android.gms.internal.zztm, com.google.android.gms.internal.zztn> r0 = r9.zzKo
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r4 = r0.iterator()
        L_0x0011:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x008c
            java.lang.Object r0 = r4.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r0.getKey()
            com.google.android.gms.internal.zztm r1 = (com.google.android.gms.internal.zztm) r1
            java.lang.Object r0 = r0.getValue()
            com.google.android.gms.internal.zztn r0 = (com.google.android.gms.internal.zztn) r0
            boolean r2 = com.google.android.gms.internal.zzafr.zzz(r8)
            if (r2 == 0) goto L_0x0056
            int r2 = r0.size()
            int r3 = r0.zzeJ()
            if (r3 >= r2) goto L_0x0056
            java.lang.String r5 = "Loading %s/%s pooled interstitials for %s."
            r6 = 3
            java.lang.Object[] r6 = new java.lang.Object[r6]
            int r3 = r2 - r3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r6[r7] = r3
            r3 = 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r6[r3] = r2
            r6[r8] = r1
            java.lang.String r2 = java.lang.String.format(r5, r6)
            com.google.android.gms.internal.zzafr.v(r2)
        L_0x0056:
            int r2 = r0.zzeK()
            int r2 = r2 + 0
            r3 = r2
        L_0x005d:
            int r5 = r0.size()
            com.google.android.gms.internal.zzme<java.lang.Integer> r2 = com.google.android.gms.internal.zzmo.zzEd
            com.google.android.gms.internal.zzmm r6 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r2 = r6.zzd(r2)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r5 >= r2) goto L_0x0084
            java.lang.String r2 = "Pooling and loading one new interstitial for %s."
            zza((java.lang.String) r2, (com.google.android.gms.internal.zztm) r1)
            com.google.android.gms.internal.zzsi r2 = r9.zzKq
            boolean r2 = r0.zzb((com.google.android.gms.internal.zzsi) r2)
            if (r2 == 0) goto L_0x005d
            int r2 = r3 + 1
            r3 = r2
            goto L_0x005d
        L_0x0084:
            com.google.android.gms.internal.zztp r0 = com.google.android.gms.internal.zztp.zzeN()
            r0.zzn(r3)
            goto L_0x0011
        L_0x008c:
            com.google.android.gms.internal.zzsi r0 = r9.zzKq
            if (r0 == 0) goto L_0x0006
            com.google.android.gms.internal.zzsi r0 = r9.zzKq
            android.content.Context r0 = r0.getApplicationContext()
            java.lang.String r1 = "com.google.android.gms.ads.internal.interstitial.InterstitialAdPool"
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r7)
            android.content.SharedPreferences$Editor r2 = r0.edit()
            r2.clear()
            java.util.Map<com.google.android.gms.internal.zztm, com.google.android.gms.internal.zztn> r0 = r9.zzKo
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r3 = r0.iterator()
        L_0x00ad:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x00e1
            java.lang.Object r0 = r3.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r0.getKey()
            com.google.android.gms.internal.zztm r1 = (com.google.android.gms.internal.zztm) r1
            java.lang.Object r0 = r0.getValue()
            com.google.android.gms.internal.zztn r0 = (com.google.android.gms.internal.zztn) r0
            boolean r4 = r0.zzeM()
            if (r4 == 0) goto L_0x00ad
            com.google.android.gms.internal.zztr r4 = new com.google.android.gms.internal.zztr
            r4.<init>(r0)
            java.lang.String r0 = r4.zzeW()
            java.lang.String r4 = r1.toString()
            r2.putString(r4, r0)
            java.lang.String r0 = "Saved interstitial queue for %s."
            zza((java.lang.String) r0, (com.google.android.gms.internal.zztm) r1)
            goto L_0x00ad
        L_0x00e1:
            java.lang.String r0 = "PoolKeys"
            java.lang.String r1 = r9.zzeH()
            r2.putString(r0, r1)
            r2.apply()
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zztl.zzeG():void");
    }
}
