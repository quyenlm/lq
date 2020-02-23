package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class nx<A, B, C> {
    private final Map<B, C> values;
    private final List<A> zzcaf;
    private final nk<A, B> zzcag;
    private nu<A, C> zzcah;
    private nu<A, C> zzcai;

    private nx(List<A> list, Map<B, C> map, nk<A, B> nkVar) {
        this.zzcaf = list;
        this.values = map;
        this.zzcag = nkVar;
    }

    private final C zzag(A a) {
        return this.values.get(this.zzcag.zzab(a));
    }

    public static <A, B, C> nv<A, C> zzc(List<A> list, Map<B, C> map, nk<A, B> nkVar, Comparator<A> comparator) {
        nx nxVar = new nx(list, map, nkVar);
        Collections.sort(list, comparator);
        Iterator<oa> it = new ny(list.size()).iterator();
        int size = list.size();
        while (it.hasNext()) {
            oa next = it.next();
            size -= next.zzcam;
            if (next.zzcal) {
                nxVar.zze(nr.zzbZZ, next.zzcam, size);
            } else {
                nxVar.zze(nr.zzbZZ, next.zzcam, size);
                size -= next.zzcam;
                nxVar.zze(nr.zzbZY, next.zzcam, size);
            }
        }
        return new nv<>(nxVar.zzcah == null ? np.zzFF() : nxVar.zzcah, comparator);
    }

    private final void zze(int i, int i2, int i3) {
        nq zzn = zzn(i3 + 1, i2 - 1);
        A a = this.zzcaf.get(i3);
        nu<A, C> ntVar = i == nr.zzbZY ? new nt<>(a, zzag(a), (nq) null, zzn) : new no<>(a, zzag(a), (nq) null, zzn);
        if (this.zzcah == null) {
            this.zzcah = ntVar;
            this.zzcai = ntVar;
            return;
        }
        this.zzcai.zzb(ntVar);
        this.zzcai = ntVar;
    }

    private final nq<A, C> zzn(int i, int i2) {
        if (i2 == 0) {
            return np.zzFF();
        }
        if (i2 == 1) {
            A a = this.zzcaf.get(i);
            return new no(a, zzag(a), (nq) null, (nq) null);
        }
        int i3 = i2 / 2;
        int i4 = i + i3;
        nq zzn = zzn(i, i3);
        nq zzn2 = zzn(i4 + 1, i3);
        A a2 = this.zzcaf.get(i4);
        return new no(a2, zzag(a2), zzn, zzn2);
    }
}
