package com.google.android.gms.internal;

import java.util.Comparator;

public abstract class nu<K, V> implements nq<K, V> {
    private final V value;
    private final K zzcab;
    private nq<K, V> zzcac;
    private final nq<K, V> zzcad;

    nu(K k, V v, nq<K, V> nqVar, nq<K, V> nqVar2) {
        this.zzcab = k;
        this.value = v;
        this.zzcac = nqVar == null ? np.zzFF() : nqVar;
        this.zzcad = nqVar2 == null ? np.zzFF() : nqVar2;
    }

    private final nq<K, V> zzFL() {
        if (this.zzcac.isEmpty()) {
            return np.zzFF();
        }
        if (!this.zzcac.zzFE() && !this.zzcac.zzFG().zzFE()) {
            this = zzFM();
        }
        return this.zza((Object) null, (Object) null, ((nu) this.zzcac).zzFL(), (nq) null).zzFN();
    }

    private final nu<K, V> zzFM() {
        nu<K, V> zzFQ = zzFQ();
        return zzFQ.zzcad.zzFG().zzFE() ? zzFQ.zza((K) null, (V) null, (nq<K, V>) null, ((nu) zzFQ.zzcad).zzFP()).zzFO().zzFQ() : zzFQ;
    }

    private final nu<K, V> zzFN() {
        if (this.zzcad.zzFE() && !this.zzcac.zzFE()) {
            this = zzFO();
        }
        if (this.zzcac.zzFE() && ((nu) this.zzcac).zzcac.zzFE()) {
            this = this.zzFP();
        }
        return (!this.zzcac.zzFE() || !this.zzcad.zzFE()) ? this : this.zzFQ();
    }

    private final nu<K, V> zzFO() {
        return (nu) this.zzcad.zza(null, null, zzFD(), zzb((Object) null, (Object) null, nr.zzbZY, (nq) null, ((nu) this.zzcad).zzcac), (nu<K, V>) null);
    }

    private final nu<K, V> zzFP() {
        return (nu) this.zzcac.zza(null, null, zzFD(), (nq) null, zzb((Object) null, (Object) null, nr.zzbZY, ((nu) this.zzcac).zzcad, (nq<K, V>) null));
    }

    private final nu<K, V> zzFQ() {
        return zzb((Object) null, (Object) null, zza((nq) this), this.zzcac.zza(null, null, zza((nq) this.zzcac), (nq) null, (nq) null), this.zzcad.zza(null, null, zza((nq) this.zzcad), (nq) null, (nq) null));
    }

    private static int zza(nq nqVar) {
        return nqVar.zzFE() ? nr.zzbZZ : nr.zzbZY;
    }

    private final nu<K, V> zzb(K k, V v, Integer num, nq<K, V> nqVar, nq<K, V> nqVar2) {
        K k2 = this.zzcab;
        V v2 = this.value;
        if (nqVar == null) {
            nqVar = this.zzcac;
        }
        if (nqVar2 == null) {
            nqVar2 = this.zzcad;
        }
        return num == nr.zzbZY ? new nt(k2, v2, nqVar, nqVar2) : new no(k2, v2, nqVar, nqVar2);
    }

    public final K getKey() {
        return this.zzcab;
    }

    public final V getValue() {
        return this.value;
    }

    public final boolean isEmpty() {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract int zzFD();

    public final nq<K, V> zzFG() {
        return this.zzcac;
    }

    public final nq<K, V> zzFH() {
        return this.zzcad;
    }

    public final nq<K, V> zzFI() {
        return this.zzcac.isEmpty() ? this : this.zzcac.zzFI();
    }

    public final nq<K, V> zzFJ() {
        return this.zzcad.isEmpty() ? this : this.zzcad.zzFJ();
    }

    public final int zzFK() {
        return this.zzcac.zzFK() + 1 + this.zzcad.zzFK();
    }

    public final /* synthetic */ nq zza(Object obj, Object obj2, int i, nq nqVar, nq nqVar2) {
        return zzb((Object) null, (Object) null, i, nqVar, nqVar2);
    }

    public final nq<K, V> zza(K k, V v, Comparator<K> comparator) {
        int compare = comparator.compare(k, this.zzcab);
        return (compare < 0 ? zza((Object) null, (Object) null, this.zzcac.zza(k, v, comparator), (nq<K, V>) null) : compare == 0 ? zza(k, v, (nq) null, (nq) null) : zza((Object) null, (Object) null, (nq) null, this.zzcad.zza(k, v, comparator))).zzFN();
    }

    public final nq<K, V> zza(K k, Comparator<K> comparator) {
        nu<K, V> zza;
        if (comparator.compare(k, this.zzcab) < 0) {
            if (!this.zzcac.isEmpty() && !this.zzcac.zzFE() && !((nu) this.zzcac).zzcac.zzFE()) {
                this = zzFM();
            }
            zza = this.zza((Object) null, (Object) null, this.zzcac.zza(k, comparator), (nq<K, V>) null);
        } else {
            if (this.zzcac.zzFE()) {
                this = zzFP();
            }
            if (!this.zzcad.isEmpty() && !this.zzcad.zzFE() && !((nu) this.zzcad).zzcac.zzFE()) {
                nu zzFQ = this.zzFQ();
                if (zzFQ.zzcac.zzFG().zzFE()) {
                    zzFQ = zzFQ.zzFP().zzFQ();
                }
                this = zzFQ;
            }
            if (comparator.compare(k, this.zzcab) == 0) {
                if (this.zzcad.isEmpty()) {
                    return np.zzFF();
                }
                nq<K, V> zzFI = this.zzcad.zzFI();
                this = this.zza(zzFI.getKey(), zzFI.getValue(), (nq) null, ((nu) this.zzcad).zzFL());
            }
            zza = this.zza((Object) null, (Object) null, (nq) null, this.zzcad.zza(k, comparator));
        }
        return zza.zzFN();
    }

    /* access modifiers changed from: protected */
    public abstract nu<K, V> zza(K k, V v, nq<K, V> nqVar, nq<K, V> nqVar2);

    public final void zza(ns<K, V> nsVar) {
        this.zzcac.zza(nsVar);
        nsVar.zzh(this.zzcab, this.value);
        this.zzcad.zza(nsVar);
    }

    /* access modifiers changed from: package-private */
    public final void zzb(nq<K, V> nqVar) {
        this.zzcac = nqVar;
    }
}
