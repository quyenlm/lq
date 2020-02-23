package com.google.android.gms.internal;

import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class uv<T> implements Iterable<Map.Entry<qr, T>> {
    private static final nh zzcgt = ni.zza(ob.zze(wp.class));
    private static final uv zzcgu = new uv((Object) null, zzcgt);
    private final T value;
    private final nh<wp, uv<T>> zzcgs;

    public uv(T t) {
        this(t, zzcgt);
    }

    private uv(T t, nh<wp, uv<T>> nhVar) {
        this.value = t;
        this.zzcgs = nhVar;
    }

    public static <V> uv<V> zzHR() {
        return zzcgu;
    }

    private final <R> R zza(qr qrVar, uy<? super T, R> uyVar, R r) {
        Iterator<Map.Entry<wp, uv<T>>> it = this.zzcgs.iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            r = ((uv) next.getValue()).zza(qrVar.zza((wp) next.getKey()), uyVar, r);
        }
        return this.value != null ? uyVar.zza(qrVar, this.value, r) : r;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        uv uvVar = (uv) obj;
        if (this.zzcgs == null ? uvVar.zzcgs != null : !this.zzcgs.equals(uvVar.zzcgs)) {
            return false;
        }
        if (this.value != null) {
            if (this.value.equals(uvVar.value)) {
                return true;
            }
        } else if (uvVar.value == null) {
            return true;
        }
        return false;
    }

    public final T getValue() {
        return this.value;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.value != null ? this.value.hashCode() : 0) * 31;
        if (this.zzcgs != null) {
            i = this.zzcgs.hashCode();
        }
        return hashCode + i;
    }

    public final boolean isEmpty() {
        return this.value == null && this.zzcgs.isEmpty();
    }

    public final Iterator<Map.Entry<qr, T>> iterator() {
        ArrayList arrayList = new ArrayList();
        zza(new ux(this, arrayList));
        return arrayList.iterator();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ImmutableTree { value=");
        sb.append(this.value);
        sb.append(", children={");
        Iterator<Map.Entry<wp, uv<T>>> it = this.zzcgs.iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            sb.append(((wp) next.getKey()).asString());
            sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            sb.append(next.getValue());
        }
        sb.append("} }");
        return sb.toString();
    }

    public final Collection<T> values() {
        ArrayList arrayList = new ArrayList();
        zza(new uw(this, arrayList));
        return arrayList;
    }

    public final qr zzF(qr qrVar) {
        return zza(qrVar, uz.zzcgx);
    }

    public final T zzG(qr qrVar) {
        uv uvVar;
        uz<Object> uzVar = uz.zzcgx;
        T t = (this.value == null || !uzVar.zzaj(this.value)) ? null : this.value;
        Iterator<wp> it = qrVar.iterator();
        T t2 = t;
        while (it.hasNext() && (uvVar = this.zzcgs.get(it.next())) != null) {
            if (uvVar.value != null && uzVar.zzaj(uvVar.value)) {
                t2 = uvVar.value;
            }
            this = uvVar;
        }
        return t2;
    }

    public final uv<T> zzH(qr qrVar) {
        while (!qrVar.isEmpty()) {
            uv<T> uvVar = this.zzcgs.get(qrVar.zzHc());
            if (uvVar == null) {
                return zzcgu;
            }
            qrVar = qrVar.zzHd();
            this = uvVar;
        }
        return this;
    }

    public final nh<wp, uv<T>> zzHS() {
        return this.zzcgs;
    }

    public final uv<T> zzI(qr qrVar) {
        if (qrVar.isEmpty()) {
            return this.zzcgs.isEmpty() ? zzcgu : new uv<>((Object) null, this.zzcgs);
        }
        wp zzHc = qrVar.zzHc();
        uv uvVar = this.zzcgs.get(zzHc);
        if (uvVar == null) {
            return this;
        }
        uv zzI = uvVar.zzI(qrVar.zzHd());
        nh<wp, uv<T>> zzX = zzI.isEmpty() ? this.zzcgs.zzX(zzHc) : this.zzcgs.zzg(zzHc, zzI);
        return (this.value != null || !zzX.isEmpty()) ? new uv<>(this.value, zzX) : zzcgu;
    }

    public final T zzJ(qr qrVar) {
        while (!qrVar.isEmpty()) {
            uv uvVar = this.zzcgs.get(qrVar.zzHc());
            if (uvVar == null) {
                return null;
            }
            qrVar = qrVar.zzHd();
            this = uvVar;
        }
        return this.value;
    }

    public final qr zza(qr qrVar, uz<? super T> uzVar) {
        if (this.value != null && uzVar.zzaj(this.value)) {
            return qr.zzGZ();
        }
        if (qrVar.isEmpty()) {
            return null;
        }
        wp zzHc = qrVar.zzHc();
        uv uvVar = this.zzcgs.get(zzHc);
        if (uvVar == null) {
            return null;
        }
        qr zza = uvVar.zza(qrVar.zzHd(), uzVar);
        if (zza == null) {
            return null;
        }
        return new qr(zzHc).zzh(zza);
    }

    public final uv<T> zza(qr qrVar, uv<T> uvVar) {
        if (qrVar.isEmpty()) {
            return uvVar;
        }
        wp zzHc = qrVar.zzHc();
        uv uvVar2 = this.zzcgs.get(zzHc);
        if (uvVar2 == null) {
            uvVar2 = zzcgu;
        }
        uv<T> zza = uvVar2.zza(qrVar.zzHd(), uvVar);
        return new uv<>(this.value, zza.isEmpty() ? this.zzcgs.zzX(zzHc) : this.zzcgs.zzg(zzHc, zza));
    }

    public final void zza(uy<T, Void> uyVar) {
        zza(qr.zzGZ(), uyVar, (Object) null);
    }

    public final uv<T> zzb(qr qrVar, T t) {
        if (qrVar.isEmpty()) {
            return new uv<>(t, this.zzcgs);
        }
        wp zzHc = qrVar.zzHc();
        uv uvVar = this.zzcgs.get(zzHc);
        if (uvVar == null) {
            uvVar = zzcgu;
        }
        return new uv<>(this.value, this.zzcgs.zzg(zzHc, uvVar.zzb(qrVar.zzHd(), t)));
    }

    public final T zzb(qr qrVar, uz<? super T> uzVar) {
        if (this.value != null && uzVar.zzaj(this.value)) {
            return this.value;
        }
        Iterator<wp> it = qrVar.iterator();
        while (it.hasNext()) {
            uv uvVar = this.zzcgs.get(it.next());
            if (uvVar == null) {
                return null;
            }
            if (uvVar.value != null && uzVar.zzaj(uvVar.value)) {
                return uvVar.value;
            }
            this = uvVar;
        }
        return null;
    }

    public final <R> R zzb(R r, uy<? super T, R> uyVar) {
        return zza(qr.zzGZ(), uyVar, r);
    }

    public final boolean zzb(uz<? super T> uzVar) {
        if (this.value != null && uzVar.zzaj(this.value)) {
            return true;
        }
        Iterator<Map.Entry<wp, uv<T>>> it = this.zzcgs.iterator();
        while (it.hasNext()) {
            if (((uv) it.next().getValue()).zzb(uzVar)) {
                return true;
            }
        }
        return false;
    }

    public final uv<T> zze(wp wpVar) {
        uv<T> uvVar = this.zzcgs.get(wpVar);
        return uvVar != null ? uvVar : zzcgu;
    }
}
