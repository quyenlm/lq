package com.google.android.gms.tagmanager;

import android.content.Context;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.ei;
import com.google.android.gms.internal.ek;
import com.google.android.gms.internal.em;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbp;
import com.google.android.gms.internal.zzbr;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzfc {
    private static final zzea<zzbr> zzbGe = new zzea<>(zzgk.zzCh(), true);
    private final DataLayer zzbDx;
    private final ek zzbGf;
    private final zzbo zzbGg;
    private final Map<String, zzbr> zzbGh;
    private final Map<String, zzbr> zzbGi;
    private final Map<String, zzbr> zzbGj;
    private final zzp<ei, zzea<zzbr>> zzbGk;
    private final zzp<String, zzfi> zzbGl;
    private final Set<em> zzbGm;
    private final Map<String, zzfj> zzbGn;
    private volatile String zzbGo;
    private int zzbGp;

    public zzfc(Context context, ek ekVar, DataLayer dataLayer, zzan zzan, zzan zzan2, zzbo zzbo) {
        if (ekVar == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.zzbGf = ekVar;
        this.zzbGm = new HashSet(ekVar.zzCX());
        this.zzbDx = dataLayer;
        this.zzbGg = zzbo;
        zzfd zzfd = new zzfd(this);
        new zzq();
        this.zzbGk = zzq.zza(1048576, zzfd);
        zzfe zzfe = new zzfe(this);
        new zzq();
        this.zzbGl = zzq.zza(1048576, zzfe);
        this.zzbGh = new HashMap();
        zzb(new zzm(context));
        zzb(new zzam(zzan2));
        zzb(new zzaz(dataLayer));
        zzb(new zzgl(context, dataLayer));
        this.zzbGi = new HashMap();
        zzc(new zzak());
        zzc(new zzbl());
        zzc(new zzbm());
        zzc(new zzbt());
        zzc(new zzbu());
        zzc(new zzdf());
        zzc(new zzdg());
        zzc(new zzem());
        zzc(new zzfz());
        this.zzbGj = new HashMap();
        zza((zzbr) new zze(context));
        zza((zzbr) new zzf(context));
        zza((zzbr) new zzh(context));
        zza((zzbr) new zzi(context));
        zza((zzbr) new zzj(context));
        zza((zzbr) new zzk(context));
        zza((zzbr) new zzl(context));
        zza((zzbr) new zzt());
        zza((zzbr) new zzaj(this.zzbGf.getVersion()));
        zza((zzbr) new zzam(zzan));
        zza((zzbr) new zzas(dataLayer));
        zza((zzbr) new zzbc(context));
        zza((zzbr) new zzbd());
        zza((zzbr) new zzbk());
        zza((zzbr) new zzbp(this));
        zza((zzbr) new zzbv());
        zza((zzbr) new zzbw());
        zza((zzbr) new zzcw(context));
        zza((zzbr) new zzcy());
        zza((zzbr) new zzde());
        zza((zzbr) new zzdl());
        zza((zzbr) new zzdn(context));
        zza((zzbr) new zzeb());
        zza((zzbr) new zzef());
        zza((zzbr) new zzej());
        zza((zzbr) new zzel());
        zza((zzbr) new zzen(context));
        zza((zzbr) new zzfk());
        zza((zzbr) new zzfl());
        zza((zzbr) new zzgf());
        zza((zzbr) new zzgm());
        this.zzbGn = new HashMap();
        for (em next : this.zzbGm) {
            for (int i = 0; i < next.zzDC().size(); i++) {
                ei eiVar = next.zzDC().get(i);
                zzfj zzf = zzf(this.zzbGn, zza(eiVar));
                zzf.zza(next);
                zzf.zza(next, eiVar);
                zzf.zza(next, AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
            }
            for (int i2 = 0; i2 < next.zzDD().size(); i2++) {
                ei eiVar2 = next.zzDD().get(i2);
                zzfj zzf2 = zzf(this.zzbGn, zza(eiVar2));
                zzf2.zza(next);
                zzf2.zzb(next, eiVar2);
                zzf2.zzb(next, AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
            }
        }
        for (Map.Entry next2 : this.zzbGf.zzDA().entrySet()) {
            for (ei eiVar3 : (List) next2.getValue()) {
                if (!zzgk.zzf(eiVar3.zzCZ().get(zzbg.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    zzf(this.zzbGn, (String) next2.getKey()).zzb(eiVar3);
                }
            }
        }
    }

    private final String zzBL() {
        if (this.zzbGp <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.zzbGp));
        for (int i = 2; i < this.zzbGp; i++) {
            sb.append(' ');
        }
        sb.append(": ");
        return sb.toString();
    }

    private final zzea<Boolean> zza(ei eiVar, Set<String> set, zzeo zzeo) {
        zzea<zzbr> zza = zza(this.zzbGi, eiVar, set, zzeo);
        Boolean zzf = zzgk.zzf(zza.getObject());
        zzgk.zzI(zzf);
        return new zzea<>(zzf, zza.zzBz());
    }

    private final zzea<Boolean> zza(em emVar, Set<String> set, zzer zzer) {
        boolean z;
        boolean z2 = true;
        for (ei zza : emVar.zzDc()) {
            zzea<Boolean> zza2 = zza(zza, set, zzer.zzBr());
            if (zza2.getObject().booleanValue()) {
                zzgk.zzI(false);
                return new zzea<>(false, zza2.zzBz());
            }
            z2 = z && zza2.zzBz();
        }
        for (ei zza3 : emVar.zzDb()) {
            zzea<Boolean> zza4 = zza(zza3, set, zzer.zzBs());
            if (!zza4.getObject().booleanValue()) {
                zzgk.zzI(false);
                return new zzea<>(false, zza4.zzBz());
            }
            z = z && zza4.zzBz();
        }
        zzgk.zzI(true);
        return new zzea<>(true, z);
    }

    private final zzea<zzbr> zza(zzbr zzbr, Set<String> set, zzgn zzgn) {
        if (!zzbr.zzlN) {
            return new zzea<>(zzbr, true);
        }
        switch (zzbr.type) {
            case 2:
                zzbr zzj = eg.zzj(zzbr);
                zzj.zzlE = new zzbr[zzbr.zzlE.length];
                for (int i = 0; i < zzbr.zzlE.length; i++) {
                    zzea<zzbr> zza = zza(zzbr.zzlE[i], set, zzgn.zzbz(i));
                    if (zza == zzbGe) {
                        return zzbGe;
                    }
                    zzj.zzlE[i] = zza.getObject();
                }
                return new zzea<>(zzj, false);
            case 3:
                zzbr zzj2 = eg.zzj(zzbr);
                if (zzbr.zzlF.length != zzbr.zzlG.length) {
                    String valueOf = String.valueOf(zzbr.toString());
                    zzdj.e(valueOf.length() != 0 ? "Invalid serving value: ".concat(valueOf) : new String("Invalid serving value: "));
                    return zzbGe;
                }
                zzj2.zzlF = new zzbr[zzbr.zzlF.length];
                zzj2.zzlG = new zzbr[zzbr.zzlF.length];
                for (int i2 = 0; i2 < zzbr.zzlF.length; i2++) {
                    zzea<zzbr> zza2 = zza(zzbr.zzlF[i2], set, zzgn.zzbA(i2));
                    zzea<zzbr> zza3 = zza(zzbr.zzlG[i2], set, zzgn.zzbB(i2));
                    if (zza2 == zzbGe || zza3 == zzbGe) {
                        return zzbGe;
                    }
                    zzj2.zzlF[i2] = zza2.getObject();
                    zzj2.zzlG[i2] = zza3.getObject();
                }
                return new zzea<>(zzj2, false);
            case 4:
                if (set.contains(zzbr.zzlH)) {
                    String valueOf2 = String.valueOf(zzbr.zzlH);
                    String valueOf3 = String.valueOf(set.toString());
                    zzdj.e(new StringBuilder(String.valueOf(valueOf2).length() + 79 + String.valueOf(valueOf3).length()).append("Macro cycle detected.  Current macro reference: ").append(valueOf2).append(".  Previous macro references: ").append(valueOf3).append(".").toString());
                    return zzbGe;
                }
                set.add(zzbr.zzlH);
                zzea<zzbr> zza4 = zzgo.zza(zza(zzbr.zzlH, set, zzgn.zzBy()), zzbr.zzlM);
                set.remove(zzbr.zzlH);
                return zza4;
            case 7:
                zzbr zzj3 = eg.zzj(zzbr);
                zzj3.zzlL = new zzbr[zzbr.zzlL.length];
                for (int i3 = 0; i3 < zzbr.zzlL.length; i3++) {
                    zzea<zzbr> zza5 = zza(zzbr.zzlL[i3], set, zzgn.zzbC(i3));
                    if (zza5 == zzbGe) {
                        return zzbGe;
                    }
                    zzj3.zzlL[i3] = zza5.getObject();
                }
                return new zzea<>(zzj3, false);
            default:
                zzdj.e(new StringBuilder(25).append("Unknown type: ").append(zzbr.type).toString());
                return zzbGe;
        }
    }

    private final zzea<zzbr> zza(String str, Set<String> set, zzdm zzdm) {
        ei eiVar;
        this.zzbGp++;
        zzfi zzfi = this.zzbGl.get(str);
        if (zzfi != null) {
            zza(zzfi.zzBN(), set);
            this.zzbGp--;
            return zzfi.zzBM();
        }
        zzfj zzfj = this.zzbGn.get(str);
        if (zzfj == null) {
            String valueOf = String.valueOf(zzBL());
            zzdj.e(new StringBuilder(String.valueOf(valueOf).length() + 15 + String.valueOf(str).length()).append(valueOf).append("Invalid macro: ").append(str).toString());
            this.zzbGp--;
            return zzbGe;
        }
        zzea<Set<ei>> zza = zza(str, zzfj.zzBO(), zzfj.zzBP(), zzfj.zzBQ(), zzfj.zzBS(), zzfj.zzBR(), set, zzdm.zzAZ());
        if (zza.getObject().isEmpty()) {
            eiVar = zzfj.zzBT();
        } else {
            if (zza.getObject().size() > 1) {
                String valueOf2 = String.valueOf(zzBL());
                zzdj.zzaT(new StringBuilder(String.valueOf(valueOf2).length() + 37 + String.valueOf(str).length()).append(valueOf2).append("Multiple macros active for macroName ").append(str).toString());
            }
            eiVar = (ei) zza.getObject().iterator().next();
        }
        if (eiVar == null) {
            this.zzbGp--;
            return zzbGe;
        }
        zzea<zzbr> zza2 = zza(this.zzbGj, eiVar, set, zzdm.zzBq());
        zzea<zzbr> zzea = zza2 == zzbGe ? zzbGe : new zzea<>(zza2.getObject(), zza.zzBz() && zza2.zzBz());
        zzbr zzBN = eiVar.zzBN();
        if (zzea.zzBz()) {
            this.zzbGl.zzf(str, new zzfi(zzea, zzBN));
        }
        zza(zzBN, set);
        this.zzbGp--;
        return zzea;
    }

    private final zzea<Set<ei>> zza(String str, Set<em> set, Map<em, List<ei>> map, Map<em, List<String>> map2, Map<em, List<ei>> map3, Map<em, List<String>> map4, Set<String> set2, zzfb zzfb) {
        return zza(set, set2, (zzfh) new zzff(this, map, map2, map3, map4), zzfb);
    }

    private final zzea<zzbr> zza(Map<String, zzbr> map, ei eiVar, Set<String> set, zzeo zzeo) {
        boolean z;
        boolean z2 = true;
        zzbr zzbr = eiVar.zzCZ().get(zzbg.FUNCTION.toString());
        if (zzbr == null) {
            zzdj.e("No function id in properties");
            return zzbGe;
        }
        String str = zzbr.zzlI;
        zzbr zzbr2 = map.get(str);
        if (zzbr2 == null) {
            zzdj.e(String.valueOf(str).concat(" has no backing implementation."));
            return zzbGe;
        }
        zzea<zzbr> zzea = this.zzbGk.get(eiVar);
        if (zzea != null) {
            return zzea;
        }
        HashMap hashMap = new HashMap();
        boolean z3 = true;
        for (Map.Entry next : eiVar.zzCZ().entrySet()) {
            zzea<zzbr> zza = zza((zzbr) next.getValue(), set, zzeo.zzfp((String) next.getKey()).zza((zzbr) next.getValue()));
            if (zza == zzbGe) {
                return zzbGe;
            }
            if (zza.zzBz()) {
                eiVar.zza((String) next.getKey(), zza.getObject());
                z = z3;
            } else {
                z = false;
            }
            hashMap.put((String) next.getKey(), zza.getObject());
            z3 = z;
        }
        if (!zzbr2.zzd(hashMap.keySet())) {
            String valueOf = String.valueOf(zzbr2.zzBl());
            String valueOf2 = String.valueOf(hashMap.keySet());
            zzdj.e(new StringBuilder(String.valueOf(str).length() + 43 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length()).append("Incorrect keys for function ").append(str).append(" required ").append(valueOf).append(" had ").append(valueOf2).toString());
            return zzbGe;
        }
        if (!z3 || !zzbr2.zzAE()) {
            z2 = false;
        }
        zzea<zzbr> zzea2 = new zzea<>(zzbr2.zzo(hashMap), z2);
        if (!z2) {
            return zzea2;
        }
        this.zzbGk.zzf(eiVar, zzea2);
        return zzea2;
    }

    private final zzea<Set<ei>> zza(Set<em> set, Set<String> set2, zzfh zzfh, zzfb zzfb) {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        boolean z = true;
        for (em next : set) {
            zzer zzBx = zzfb.zzBx();
            zzea<Boolean> zza = zza(next, set2, zzBx);
            if (zza.getObject().booleanValue()) {
                zzfh.zza(next, hashSet, hashSet2, zzBx);
            }
            z = z && zza.zzBz();
        }
        hashSet.removeAll(hashSet2);
        return new zzea<>(hashSet, z);
    }

    private static String zza(ei eiVar) {
        return zzgk.zzb(eiVar.zzCZ().get(zzbg.INSTANCE_NAME.toString()));
    }

    private final void zza(zzbr zzbr, Set<String> set) {
        zzea<zzbr> zza;
        if (zzbr != null && (zza = zza(zzbr, set, (zzgn) new zzdy())) != zzbGe) {
            Object zzg = zzgk.zzg(zza.getObject());
            if (zzg instanceof Map) {
                this.zzbDx.push((Map) zzg);
            } else if (zzg instanceof List) {
                for (Object next : (List) zzg) {
                    if (next instanceof Map) {
                        this.zzbDx.push((Map) next);
                    } else {
                        zzdj.zzaT("pushAfterEvaluate: value not a Map");
                    }
                }
            } else {
                zzdj.zzaT("pushAfterEvaluate: value not a Map or List");
            }
        }
    }

    private final void zza(zzbr zzbr) {
        zza(this.zzbGj, zzbr);
    }

    private static void zza(Map<String, zzbr> map, zzbr zzbr) {
        if (map.containsKey(zzbr.zzBk())) {
            String valueOf = String.valueOf(zzbr.zzBk());
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Duplicate function type name: ".concat(valueOf) : new String("Duplicate function type name: "));
        } else {
            map.put(zzbr.zzBk(), zzbr);
        }
    }

    private final void zzb(zzbr zzbr) {
        zza(this.zzbGh, zzbr);
    }

    private final void zzc(zzbr zzbr) {
        zza(this.zzbGi, zzbr);
    }

    private static zzfj zzf(Map<String, zzfj> map, String str) {
        zzfj zzfj = map.get(str);
        if (zzfj != null) {
            return zzfj;
        }
        zzfj zzfj2 = new zzfj();
        map.put(str, zzfj2);
        return zzfj2;
    }

    private final synchronized void zzft(String str) {
        this.zzbGo = str;
    }

    /* access modifiers changed from: package-private */
    public final synchronized String zzBK() {
        return this.zzbGo;
    }

    public final synchronized void zzL(List<zzbp> list) {
        for (zzbp next : list) {
            if (next.name == null || !next.name.startsWith("gaExperiment:")) {
                String valueOf = String.valueOf(next);
                zzdj.v(new StringBuilder(String.valueOf(valueOf).length() + 22).append("Ignored supplemental: ").append(valueOf).toString());
            } else {
                zzbq.zza(this.zzbDx, next);
            }
        }
    }

    public final synchronized void zzeZ(String str) {
        zzft(str);
        zzar zzBj = this.zzbGg.zzfj(str).zzBj();
        for (ei zza : zza(this.zzbGm, (Set<String>) new HashSet(), (zzfh) new zzfg(this), zzBj.zzAZ()).getObject()) {
            zza(this.zzbGh, zza, (Set<String>) new HashSet(), zzBj.zzAY());
        }
        zzft((String) null);
    }

    public final zzea<zzbr> zzfs(String str) {
        this.zzbGp = 0;
        return zza(str, (Set<String>) new HashSet(), this.zzbGg.zzfi(str).zzBi());
    }
}
