package com.google.firebase.database;

import com.google.android.gms.internal.qr;
import com.google.android.gms.internal.qu;
import com.google.android.gms.internal.tn;
import com.google.android.gms.internal.xd;
import com.google.android.gms.internal.xm;
import com.google.android.gms.internal.xp;
import com.google.android.gms.internal.xs;
import com.google.android.gms.internal.za;
import com.google.android.gms.internal.zd;
import com.google.android.gms.internal.zf;
import com.google.android.gms.internal.zg;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import java.util.Map;

public class OnDisconnect {
    /* access modifiers changed from: private */
    public qu zzbYY;
    /* access modifiers changed from: private */
    public qr zzbZf;

    OnDisconnect(qu quVar, qr qrVar) {
        this.zzbYY = quVar;
        this.zzbZf = qrVar;
    }

    private final Task<Void> zza(DatabaseReference.CompletionListener completionListener) {
        za<Task<Void>, DatabaseReference.CompletionListener> zzb = zd.zzb(completionListener);
        this.zzbYY.zzq(new zzo(this, zzb));
        return zzb.getFirst();
    }

    private final Task<Void> zza(Map<String, Object> map, DatabaseReference.CompletionListener completionListener) {
        Map<qr, xm> zzb = zf.zzb(this.zzbZf, map);
        za<Task<Void>, DatabaseReference.CompletionListener> zzb2 = zd.zzb(completionListener);
        this.zzbYY.zzq(new zzn(this, zzb, zzb2, map));
        return zzb2.getFirst();
    }

    private final Task<Void> zzb(Object obj, xm xmVar, DatabaseReference.CompletionListener completionListener) {
        zf.zzO(this.zzbZf);
        tn.zza(this.zzbZf, obj);
        Object zzan = zg.zzan(obj);
        zf.zzam(zzan);
        xm zza = xp.zza(zzan, xmVar);
        za<Task<Void>, DatabaseReference.CompletionListener> zzb = zd.zzb(completionListener);
        this.zzbYY.zzq(new zzm(this, zza, zzb));
        return zzb.getFirst();
    }

    public Task<Void> cancel() {
        return zza((DatabaseReference.CompletionListener) null);
    }

    public void cancel(DatabaseReference.CompletionListener completionListener) {
        zza(completionListener);
    }

    public Task<Void> removeValue() {
        return setValue((Object) null);
    }

    public void removeValue(DatabaseReference.CompletionListener completionListener) {
        setValue((Object) null, completionListener);
    }

    public Task<Void> setValue(Object obj) {
        return zzb(obj, xd.zzJb(), (DatabaseReference.CompletionListener) null);
    }

    public Task<Void> setValue(Object obj, double d) {
        return zzb(obj, xs.zzc(this.zzbZf, Double.valueOf(d)), (DatabaseReference.CompletionListener) null);
    }

    public Task<Void> setValue(Object obj, String str) {
        return zzb(obj, xs.zzc(this.zzbZf, str), (DatabaseReference.CompletionListener) null);
    }

    public void setValue(Object obj, double d, DatabaseReference.CompletionListener completionListener) {
        zzb(obj, xs.zzc(this.zzbZf, Double.valueOf(d)), completionListener);
    }

    public void setValue(Object obj, DatabaseReference.CompletionListener completionListener) {
        zzb(obj, xd.zzJb(), completionListener);
    }

    public void setValue(Object obj, String str, DatabaseReference.CompletionListener completionListener) {
        zzb(obj, xs.zzc(this.zzbZf, str), completionListener);
    }

    public void setValue(Object obj, Map map, DatabaseReference.CompletionListener completionListener) {
        zzb(obj, xs.zzc(this.zzbZf, map), completionListener);
    }

    public Task<Void> updateChildren(Map<String, Object> map) {
        return zza(map, (DatabaseReference.CompletionListener) null);
    }

    public void updateChildren(Map<String, Object> map, DatabaseReference.CompletionListener completionListener) {
        zza(map, completionListener);
    }
}
