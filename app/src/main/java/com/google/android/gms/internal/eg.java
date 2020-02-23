package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzgk;
import com.tencent.tp.a.h;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class eg {
    private static ei zza(zzbj zzbj, zzbn zzbn, zzbr[] zzbrArr, int i) throws eo {
        ej zzDx = ei.zzDx();
        for (int valueOf : zzbj.zzkA) {
            zzbm zzbm = (zzbm) zza(zzbn.zzkQ, Integer.valueOf(valueOf).intValue(), "properties");
            String str = (String) zza(zzbn.zzkO, zzbm.key, "keys");
            zzbr zzbr = (zzbr) zza(zzbrArr, zzbm.value, "values");
            if (zzbg.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzDx.zzl(zzbr);
            } else {
                zzDx.zzb(str, zzbr);
            }
        }
        return zzDx.zzDy();
    }

    public static ek zza(zzbn zzbn) throws eo {
        zzbr[] zzbrArr = new zzbr[zzbn.zzkP.length];
        for (int i = 0; i < zzbn.zzkP.length; i++) {
            zza(i, zzbn, zzbrArr, (Set<Integer>) new HashSet(0));
        }
        el zzDz = ek.zzDz();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < zzbn.zzkS.length; i2++) {
            arrayList.add(zza(zzbn.zzkS[i2], zzbn, zzbrArr, i2));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < zzbn.zzkT.length; i3++) {
            arrayList2.add(zza(zzbn.zzkT[i3], zzbn, zzbrArr, i3));
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < zzbn.zzkR.length; i4++) {
            ei zza = zza(zzbn.zzkR[i4], zzbn, zzbrArr, i4);
            zzDz.zzc(zza);
            arrayList3.add(zza);
        }
        for (zzbo zza2 : zzbn.zzkU) {
            zzDz.zzb(zza(zza2, arrayList, arrayList3, arrayList2, zzbn));
        }
        zzDz.zzgd(zzbn.version);
        zzDz.zzbJ(zzbn.zzlc);
        return zzDz.zzDB();
    }

    private static em zza(zzbo zzbo, List<ei> list, List<ei> list2, List<ei> list3, zzbn zzbn) {
        en enVar = new en();
        for (int valueOf : zzbo.zzle) {
            enVar.zzd(list3.get(Integer.valueOf(valueOf).intValue()));
        }
        for (int valueOf2 : zzbo.zzlf) {
            enVar.zze(list3.get(Integer.valueOf(valueOf2).intValue()));
        }
        for (int valueOf3 : zzbo.zzlg) {
            enVar.zzf(list.get(Integer.valueOf(valueOf3).intValue()));
        }
        for (int valueOf4 : zzbo.zzli) {
            enVar.zzge(zzbn.zzkP[Integer.valueOf(valueOf4).intValue()].string);
        }
        for (int valueOf5 : zzbo.zzlh) {
            enVar.zzg(list.get(Integer.valueOf(valueOf5).intValue()));
        }
        for (int valueOf6 : zzbo.zzlj) {
            enVar.zzgf(zzbn.zzkP[Integer.valueOf(valueOf6).intValue()].string);
        }
        for (int valueOf7 : zzbo.zzlk) {
            enVar.zzh(list2.get(Integer.valueOf(valueOf7).intValue()));
        }
        for (int valueOf8 : zzbo.zzlm) {
            enVar.zzgg(zzbn.zzkP[Integer.valueOf(valueOf8).intValue()].string);
        }
        for (int valueOf9 : zzbo.zzll) {
            enVar.zzi(list2.get(Integer.valueOf(valueOf9).intValue()));
        }
        for (int valueOf10 : zzbo.zzln) {
            enVar.zzgh(zzbn.zzkP[Integer.valueOf(valueOf10).intValue()].string);
        }
        return enVar.zzDE();
    }

    private static zzbr zza(int i, zzbn zzbn, zzbr[] zzbrArr, Set<Integer> set) throws eo {
        if (set.contains(Integer.valueOf(i))) {
            String valueOf = String.valueOf(set);
            zzfQ(new StringBuilder(String.valueOf(valueOf).length() + 90).append("Value cycle detected.  Current value reference: ").append(i).append(".  Previous value references: ").append(valueOf).append(".").toString());
        }
        zzbr zzbr = (zzbr) zza(zzbn.zzkP, i, "values");
        if (zzbrArr[i] != null) {
            return zzbrArr[i];
        }
        zzbr zzbr2 = null;
        set.add(Integer.valueOf(i));
        switch (zzbr.type) {
            case 1:
            case 5:
            case 6:
            case 8:
                zzbr2 = zzbr;
                break;
            case 2:
                zzbh.zza zzk = zzk(zzbr);
                zzbr2 = zzj(zzbr);
                zzbr2.zzlE = new zzbr[zzk.zzlq.length];
                int[] iArr = zzk.zzlq;
                int length = iArr.length;
                int i2 = 0;
                int i3 = 0;
                while (i2 < length) {
                    zzbr2.zzlE[i3] = zza(iArr[i2], zzbn, zzbrArr, set);
                    i2++;
                    i3++;
                }
                break;
            case 3:
                zzbr2 = zzj(zzbr);
                zzbh.zza zzk2 = zzk(zzbr);
                if (zzk2.zzlr.length != zzk2.zzls.length) {
                    zzfQ(new StringBuilder(58).append("Uneven map keys (").append(zzk2.zzlr.length).append(") and map values (").append(zzk2.zzls.length).append(h.b).toString());
                }
                zzbr2.zzlF = new zzbr[zzk2.zzlr.length];
                zzbr2.zzlG = new zzbr[zzk2.zzlr.length];
                int[] iArr2 = zzk2.zzlr;
                int length2 = iArr2.length;
                int i4 = 0;
                int i5 = 0;
                while (i4 < length2) {
                    zzbr2.zzlF[i5] = zza(iArr2[i4], zzbn, zzbrArr, set);
                    i4++;
                    i5++;
                }
                int[] iArr3 = zzk2.zzls;
                int length3 = iArr3.length;
                int i6 = 0;
                int i7 = 0;
                while (i6 < length3) {
                    zzbr2.zzlG[i7] = zza(iArr3[i6], zzbn, zzbrArr, set);
                    i6++;
                    i7++;
                }
                break;
            case 4:
                zzbr2 = zzj(zzbr);
                zzbr2.zzlH = zzgk.zzb(zza(zzk(zzbr).zzlv, zzbn, zzbrArr, set));
                break;
            case 7:
                zzbr2 = zzj(zzbr);
                zzbh.zza zzk3 = zzk(zzbr);
                zzbr2.zzlL = new zzbr[zzk3.zzlu.length];
                int[] iArr4 = zzk3.zzlu;
                int length4 = iArr4.length;
                int i8 = 0;
                int i9 = 0;
                while (i8 < length4) {
                    zzbr2.zzlL[i9] = zza(iArr4[i8], zzbn, zzbrArr, set);
                    i8++;
                    i9++;
                }
                break;
        }
        if (zzbr2 == null) {
            String valueOf2 = String.valueOf(zzbr);
            zzfQ(new StringBuilder(String.valueOf(valueOf2).length() + 15).append("Invalid value: ").append(valueOf2).toString());
        }
        zzbrArr[i] = zzbr2;
        set.remove(Integer.valueOf(i));
        return zzbr2;
    }

    private static <T> T zza(T[] tArr, int i, String str) throws eo {
        if (i < 0 || i >= tArr.length) {
            zzfQ(new StringBuilder(String.valueOf(str).length() + 45).append("Index out of bounds detected: ").append(i).append(" in ").append(str).toString());
        }
        return tArr[i];
    }

    public static void zzb(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    private static void zzfQ(String str) throws eo {
        zzdj.e(str);
        throw new eo(str);
    }

    public static zzbr zzj(zzbr zzbr) {
        zzbr zzbr2 = new zzbr();
        zzbr2.type = zzbr.type;
        zzbr2.zzlM = (int[]) zzbr.zzlM.clone();
        if (zzbr.zzlN) {
            zzbr2.zzlN = zzbr.zzlN;
        }
        return zzbr2;
    }

    private static zzbh.zza zzk(zzbr zzbr) throws eo {
        if (((zzbh.zza) zzbr.zza(zzbh.zza.zzlo)) == null) {
            String valueOf = String.valueOf(zzbr);
            zzfQ(new StringBuilder(String.valueOf(valueOf).length() + 54).append("Expected a ServingValue and didn't get one. Value is: ").append(valueOf).toString());
        }
        return (zzbh.zza) zzbr.zza(zzbh.zza.zzlo);
    }
}
