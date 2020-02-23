package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class tt implements qj {
    private static tt zzcfC = new tt();
    private HashMap<qi, List<qi>> zzcfB = new HashMap<>();

    private tt() {
    }

    public static tt zzHA() {
        return zzcfC;
    }

    public final void zzd(qi qiVar) {
        qi zza;
        List list;
        int i = 0;
        synchronized (this.zzcfB) {
            List list2 = this.zzcfB.get(qiVar);
            if (list2 != null) {
                int i2 = 0;
                while (true) {
                    if (i2 >= list2.size()) {
                        break;
                    } else if (list2.get(i2) == qiVar) {
                        list2.remove(i2);
                        break;
                    } else {
                        i2++;
                    }
                }
                if (list2.isEmpty()) {
                    this.zzcfB.remove(qiVar);
                }
            }
            if (!qiVar.zzGH().isDefault() && (list = this.zzcfB.get(zza)) != null) {
                while (true) {
                    if (i >= list.size()) {
                        break;
                    } else if (list.get(i) == qiVar) {
                        list.remove(i);
                        break;
                    } else {
                        i++;
                    }
                }
                if (list.isEmpty()) {
                    this.zzcfB.remove((zza = qiVar.zza(vt.zzM(qiVar.zzGH().zzFq()))));
                }
            }
        }
    }

    public final void zzi(qi qiVar) {
        synchronized (this.zzcfB) {
            List list = this.zzcfB.get(qiVar);
            if (list == null) {
                list = new ArrayList();
                this.zzcfB.put(qiVar, list);
            }
            list.add(qiVar);
            if (!qiVar.zzGH().isDefault()) {
                qi zza = qiVar.zza(vt.zzM(qiVar.zzGH().zzFq()));
                List list2 = this.zzcfB.get(zza);
                if (list2 == null) {
                    list2 = new ArrayList();
                    this.zzcfB.put(zza, list2);
                }
                list2.add(qiVar);
            }
            qiVar.zzaE(true);
            qiVar.zza((qj) this);
        }
    }

    public final void zzj(qi qiVar) {
        synchronized (this.zzcfB) {
            List list = this.zzcfB.get(qiVar);
            if (list != null && !list.isEmpty()) {
                if (qiVar.zzGH().isDefault()) {
                    HashSet hashSet = new HashSet();
                    for (int size = list.size() - 1; size >= 0; size--) {
                        qi qiVar2 = (qi) list.get(size);
                        if (!hashSet.contains(qiVar2.zzGH())) {
                            hashSet.add(qiVar2.zzGH());
                            qiVar2.zzGV();
                        }
                    }
                } else {
                    ((qi) list.get(0)).zzGV();
                }
            }
        }
    }
}
