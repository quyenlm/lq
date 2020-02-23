package com.google.android.gms.internal;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.zzh;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class qu implements oo {
    private final rx zzbYW;
    /* access modifiers changed from: private */
    public final on zzccd;
    /* access modifiers changed from: private */
    public final yz zzcdj = new yz(new yt(), 0);
    /* access modifiers changed from: private */
    public sh zzcdk;
    /* access modifiers changed from: private */
    public si zzcdl;
    /* access modifiers changed from: private */
    public vb<List<rv>> zzcdm;
    private boolean zzcdn = false;
    private final vo zzcdo;
    private final qd zzcdp;
    /* access modifiers changed from: private */
    public final wl zzcdq;
    private final wl zzcdr;
    private final wl zzcds;
    private long zzcdt = 0;
    private long zzcdu = 1;
    /* access modifiers changed from: private */
    public so zzcdv;
    /* access modifiers changed from: private */
    public so zzcdw;
    private FirebaseDatabase zzcdx;
    private boolean zzcdy = false;
    private long zzcdz = 0;

    qu(rx rxVar, qd qdVar, FirebaseDatabase firebaseDatabase) {
        this.zzbYW = rxVar;
        this.zzcdp = qdVar;
        this.zzcdx = firebaseDatabase;
        this.zzcdq = this.zzcdp.zzgP("RepoOperation");
        this.zzcdr = this.zzcdp.zzgP("Transaction");
        this.zzcds = this.zzcdp.zzgP("DataOperation");
        this.zzcdo = new vo(this.zzcdp);
        this.zzccd = qdVar.zza(new ol(rxVar.host, rxVar.zzbxU, rxVar.secure), this);
        zzq(new qv(this));
    }

    /* access modifiers changed from: private */
    public final void zzHg() {
        this.zzcdp.zzccP.zza(new rh(this));
        this.zzccd.initialize();
        uh zzgQ = this.zzcdp.zzgQ(this.zzbYW.host);
        this.zzcdk = new sh();
        this.zzcdl = new si();
        this.zzcdm = new vb<>();
        this.zzcdv = new so(this.zzcdp, new ug(), new rm(this));
        this.zzcdw = new so(this.zzcdp, zzgQ, new ro(this));
        List<tm> zzFs = zzgQ.zzFs();
        Map<String, Object> zza = se.zza(this.zzcdj);
        long j = Long.MIN_VALUE;
        for (tm next : zzFs) {
            rq rqVar = new rq(this, next);
            if (j >= next.zzHt()) {
                throw new IllegalStateException("Write ids were not in order.");
            }
            long zzHt = next.zzHt();
            this.zzcdu = next.zzHt() + 1;
            if (next.zzHw()) {
                if (this.zzcdq.zzIH()) {
                    this.zzcdq.zzb(new StringBuilder(48).append("Restoring overwrite with id ").append(next.zzHt()).toString(), (Throwable) null, new Object[0]);
                }
                this.zzccd.zza(next.zzFq().zzHb(), next.zzHu().getValue(true), (pf) rqVar);
                this.zzcdw.zza(next.zzFq(), next.zzHu(), se.zza(next.zzHu(), zza), next.zzHt(), true, false);
                j = zzHt;
            } else {
                if (this.zzcdq.zzIH()) {
                    this.zzcdq.zzb(new StringBuilder(44).append("Restoring merge with id ").append(next.zzHt()).toString(), (Throwable) null, new Object[0]);
                }
                this.zzccd.zza(next.zzFq().zzHb(), next.zzHv().zzaD(true), (pf) rqVar);
                this.zzcdw.zza(next.zzFq(), next.zzHv(), se.zza(next.zzHv(), zza), next.zzHt(), false);
                j = zzHt;
            }
        }
        zza(qc.zzccM, (Object) false);
        zza(qc.zzccN, (Object) false);
    }

    private final long zzHk() {
        long j = this.zzcdu;
        this.zzcdu = 1 + j;
        return j;
    }

    /* access modifiers changed from: private */
    public final void zzHl() {
        vb<List<rv>> vbVar = this.zzcdm;
        zzb(vbVar);
        zza(vbVar);
    }

    /* access modifiers changed from: private */
    public final void zzT(List<? extends vk> list) {
        if (!list.isEmpty()) {
            this.zzcdo.zzV(list);
        }
    }

    private final xm zza(qr qrVar, List<Long> list) {
        xm zzc = this.zzcdw.zzc(qrVar, list);
        return zzc == null ? xd.zzJb() : zzc;
    }

    /* access modifiers changed from: private */
    public final void zza(long j, qr qrVar, DatabaseError databaseError) {
        if (databaseError == null || databaseError.getCode() != -25) {
            List<? extends vk> zza = this.zzcdw.zza(j, !(databaseError == null), true, (ys) this.zzcdj);
            if (zza.size() > 0) {
                zzn(qrVar);
            }
            zzT(zza);
        }
    }

    /* access modifiers changed from: private */
    public final void zza(vb<List<rv>> vbVar) {
        Boolean bool;
        if (vbVar.getValue() != null) {
            List<rv> zzc = zzc(vbVar);
            Iterator<rv> it = zzc.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().zzceb != rw.zzcek) {
                        bool = false;
                        break;
                    }
                } else {
                    bool = true;
                    break;
                }
            }
            if (bool.booleanValue()) {
                qr zzFq = vbVar.zzFq();
                ArrayList arrayList = new ArrayList();
                for (rv zzb : zzc) {
                    arrayList.add(Long.valueOf(zzb.zzcef));
                }
                xm zza = zza(zzFq, (List<Long>) arrayList);
                String zzIP = zza.zzIP();
                Iterator<rv> it2 = zzc.iterator();
                while (true) {
                    xm xmVar = zza;
                    if (it2.hasNext()) {
                        rv next = it2.next();
                        int unused = next.zzceb = rw.zzcel;
                        rv.zzd(next);
                        zza = xmVar.zzl(qr.zza(zzFq, next.zzbZf), next.zzceh);
                    } else {
                        this.zzccd.zza(zzFq.zzHb(), xmVar.getValue(true), zzIP, new rc(this, zzFq, zzc, this));
                        return;
                    }
                }
            }
        } else if (vbVar.hasChildren()) {
            vbVar.zza(new rb(this));
        }
    }

    /* access modifiers changed from: private */
    public final void zza(vb<List<rv>> vbVar, int i) {
        DatabaseError zzbU;
        int i2;
        List value = vbVar.getValue();
        ArrayList arrayList = new ArrayList();
        if (value != null) {
            ArrayList arrayList2 = new ArrayList();
            if (i == -9) {
                zzbU = DatabaseError.zzgA("overriddenBySet");
            } else {
                zd.zzb(i == -25, new StringBuilder(45).append("Unknown transaction abort reason: ").append(i).toString());
                zzbU = DatabaseError.zzbU(-25);
            }
            int i3 = -1;
            int i4 = 0;
            while (true) {
                int i5 = i4;
                i2 = i3;
                if (i5 >= value.size()) {
                    break;
                }
                rv rvVar = (rv) value.get(i5);
                if (rvVar.zzceb != rw.zzcen) {
                    if (rvVar.zzceb == rw.zzcel) {
                        int unused = rvVar.zzceb = rw.zzcen;
                        DatabaseError unused2 = rvVar.zzcee = zzbU;
                        i3 = i5;
                        i4 = i5 + 1;
                    } else {
                        zze((qi) new to(this, rvVar.zzcea, vt.zzM(rvVar.zzbZf)));
                        if (i == -9) {
                            arrayList.addAll(this.zzcdw.zza(rvVar.zzcef, true, false, (ys) this.zzcdj));
                        } else {
                            zd.zzb(i == -25, new StringBuilder(45).append("Unknown transaction abort reason: ").append(i).toString());
                        }
                        arrayList2.add(new rl(this, rvVar, zzbU));
                    }
                }
                i3 = i2;
                i4 = i5 + 1;
            }
            if (i2 == -1) {
                vbVar.setValue(null);
            } else {
                vbVar.setValue(value.subList(0, i2 + 1));
            }
            zzT(arrayList);
            ArrayList arrayList3 = arrayList2;
            int size = arrayList3.size();
            int i6 = 0;
            while (i6 < size) {
                Object obj = arrayList3.get(i6);
                i6++;
                zzo((Runnable) obj);
            }
        }
    }

    private final void zza(wp wpVar, Object obj) {
        if (wpVar.equals(qc.zzccL)) {
            this.zzcdj.zzay(((Long) obj).longValue());
        }
        qr qrVar = new qr(qc.zzccK, wpVar);
        try {
            xm zza = xp.zza(obj, xd.zzJb());
            this.zzcdk.zzg(qrVar, zza);
            zzT(this.zzcdv.zzi(qrVar, zza));
        } catch (DatabaseException e) {
            this.zzcdq.zzd("Failed to parse info update", e);
        }
    }

    /* access modifiers changed from: private */
    public final void zza(String str, qr qrVar, DatabaseError databaseError) {
        if (databaseError != null && databaseError.getCode() != -1 && databaseError.getCode() != -25) {
            wl wlVar = this.zzcdq;
            String valueOf = String.valueOf(qrVar.toString());
            String valueOf2 = String.valueOf(databaseError.toString());
            wlVar.zze(new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length()).append(str).append(" at ").append(valueOf).append(" failed: ").append(valueOf2).toString(), (Throwable) null);
        }
    }

    /* access modifiers changed from: private */
    public final void zza(List<rv> list, vb<List<rv>> vbVar) {
        List value = vbVar.getValue();
        if (value != null) {
            list.addAll(value);
        }
        vbVar.zza(new ri(this, list));
    }

    /* access modifiers changed from: private */
    public static DatabaseError zzab(String str, String str2) {
        if (str != null) {
            return DatabaseError.zzZ(str, str2);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public final qr zzb(qr qrVar, int i) {
        qr zzFq = zzo(qrVar).zzFq();
        if (this.zzcdr.zzIH()) {
            wl wlVar = this.zzcdq;
            String valueOf = String.valueOf(qrVar);
            String valueOf2 = String.valueOf(zzFq);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 44 + String.valueOf(valueOf2).length()).append("Aborting transactions for path: ").append(valueOf).append(". Affected: ").append(valueOf2).toString(), (Throwable) null, new Object[0]);
        }
        vb<List<rv>> zzK = this.zzcdm.zzK(qrVar);
        zzK.zza(new rj(this, i), false);
        zza(zzK, i);
        zzK.zza(new rk(this, i), false, false);
        return zzFq;
    }

    /* access modifiers changed from: private */
    public final void zzb(vb<List<rv>> vbVar) {
        List value = vbVar.getValue();
        if (value != null) {
            int i = 0;
            while (i < value.size()) {
                if (((rv) value.get(i)).zzceb == rw.zzcem) {
                    value.remove(i);
                } else {
                    i++;
                }
            }
            if (value.size() > 0) {
                vbVar.setValue(value);
            } else {
                vbVar.setValue(null);
            }
        }
        vbVar.zza(new re(this));
    }

    private final List<rv> zzc(vb<List<rv>> vbVar) {
        ArrayList arrayList = new ArrayList();
        zza((List<rv>) arrayList, vbVar);
        Collections.sort(arrayList);
        return arrayList;
    }

    /* access modifiers changed from: private */
    public final qr zzn(qr qrVar) {
        DatabaseError databaseError;
        boolean z;
        Transaction.Result abort;
        vb<List<rv>> zzo = zzo(qrVar);
        qr zzFq = zzo.zzFq();
        List<rv> zzc = zzc(zzo);
        if (!zzc.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (rv zzb : zzc) {
                arrayList2.add(Long.valueOf(zzb.zzcef));
            }
            for (rv next : zzc) {
                qr.zza(zzFq, next.zzbZf);
                ArrayList arrayList3 = new ArrayList();
                if (next.zzceb == rw.zzceo) {
                    z = true;
                    databaseError = next.zzcee;
                    if (databaseError.getCode() != -25) {
                        arrayList3.addAll(this.zzcdw.zza(next.zzcef, true, false, (ys) this.zzcdj));
                    }
                } else if (next.zzceb != rw.zzcek) {
                    databaseError = null;
                    z = false;
                } else if (next.retryCount >= 25) {
                    z = true;
                    databaseError = DatabaseError.zzgA("maxretries");
                    arrayList3.addAll(this.zzcdw.zza(next.zzcef, true, false, (ys) this.zzcdj));
                } else {
                    xm zza = zza(next.zzbZf, (List<Long>) arrayList2);
                    xm unused = next.zzceg = zza;
                    try {
                        abort = next.zzcdZ.doTransaction(zzh.zza(zza));
                        databaseError = null;
                    } catch (Throwable th) {
                        DatabaseError fromException = DatabaseError.fromException(th);
                        abort = Transaction.abort();
                        databaseError = fromException;
                    }
                    if (abort.isSuccess()) {
                        Long valueOf = Long.valueOf(next.zzcef);
                        Map<String, Object> zza2 = se.zza(this.zzcdj);
                        xm zzFn = abort.zzFn();
                        xm zza3 = se.zza(zzFn, zza2);
                        xm unused2 = next.zzceh = zzFn;
                        xm unused3 = next.zzcei = zza3;
                        long unused4 = next.zzcef = zzHk();
                        arrayList2.remove(valueOf);
                        arrayList3.addAll(this.zzcdw.zza(next.zzbZf, zzFn, zza3, next.zzcef, next.zzced, false));
                        arrayList3.addAll(this.zzcdw.zza(valueOf.longValue(), true, false, (ys) this.zzcdj));
                        databaseError = null;
                        z = false;
                    } else {
                        z = true;
                        arrayList3.addAll(this.zzcdw.zza(next.zzcef, true, false, (ys) this.zzcdj));
                    }
                }
                zzT(arrayList3);
                if (z) {
                    int unused5 = next.zzceb = rw.zzcem;
                    DataSnapshot zza4 = zzh.zza(zzh.zza(this, next.zzbZf), xf.zzj(next.zzceg));
                    zzq(new rf(this, next));
                    arrayList.add(new rg(this, next, databaseError, zza4));
                }
            }
            zzb(this.zzcdm);
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= arrayList.size()) {
                    break;
                }
                zzo((Runnable) arrayList.get(i2));
                i = i2 + 1;
            }
            zzHl();
        }
        return zzFq;
    }

    private final vb<List<rv>> zzo(qr qrVar) {
        vb<List<rv>> vbVar = this.zzcdm;
        while (!qrVar.isEmpty() && vbVar.getValue() == null) {
            vbVar = vbVar.zzK(new qr(qrVar.zzHc()));
            qrVar = qrVar.zzHd();
        }
        return vbVar;
    }

    public final FirebaseDatabase getDatabase() {
        return this.zzcdx;
    }

    /* access modifiers changed from: package-private */
    public final void interrupt() {
        this.zzccd.interrupt("repo_interrupt");
    }

    public final void onDisconnect() {
        zza(qc.zzccN, (Object) false);
        Map<String, Object> zza = se.zza(this.zzcdj);
        si siVar = this.zzcdl;
        si siVar2 = new si();
        siVar.zza(new qr(""), new sf(siVar2, zza));
        ArrayList arrayList = new ArrayList();
        siVar2.zza(qr.zzGZ(), new qy(this, arrayList));
        this.zzcdl = new si();
        zzT(arrayList);
    }

    public final void purgeOutstandingWrites() {
        if (this.zzcdq.zzIH()) {
            this.zzcdq.zzb("Purging writes", (Throwable) null, new Object[0]);
        }
        zzT(this.zzcdw.zzHq());
        zzb(qr.zzGZ(), -25);
        this.zzccd.purgeOutstandingWrites();
    }

    /* access modifiers changed from: package-private */
    public final void resume() {
        this.zzccd.resume("repo_interrupt");
    }

    public final String toString() {
        return this.zzbYW.toString();
    }

    public final void zzB(Map<String, Object> map) {
        for (Map.Entry next : map.entrySet()) {
            zza(wp.zzgT((String) next.getKey()), next.getValue());
        }
    }

    public final void zzGb() {
        zza(qc.zzccN, (Object) true);
    }

    public final rx zzHh() {
        return this.zzbYW;
    }

    public final long zzHi() {
        return this.zzcdj.zzJF();
    }

    /* access modifiers changed from: package-private */
    public final boolean zzHj() {
        return !this.zzcdv.isEmpty() || !this.zzcdw.isEmpty();
    }

    public final void zza(qr qrVar, pz pzVar, DatabaseReference.CompletionListener completionListener, Map<String, Object> map) {
        if (this.zzcdq.zzIH()) {
            wl wlVar = this.zzcdq;
            String valueOf = String.valueOf(qrVar);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 8).append("update: ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        if (this.zzcds.zzIH()) {
            wl wlVar2 = this.zzcds;
            String valueOf2 = String.valueOf(qrVar);
            String valueOf3 = String.valueOf(map);
            wlVar2.zzb(new StringBuilder(String.valueOf(valueOf2).length() + 9 + String.valueOf(valueOf3).length()).append("update: ").append(valueOf2).append(" ").append(valueOf3).toString(), (Throwable) null, new Object[0]);
        }
        if (pzVar.isEmpty()) {
            if (this.zzcdq.zzIH()) {
                this.zzcdq.zzb("update called with no changes. No-op", (Throwable) null, new Object[0]);
            }
            zza(completionListener, (DatabaseError) null, qrVar);
            return;
        }
        pz zza = se.zza(pzVar, se.zza(this.zzcdj));
        long zzHk = zzHk();
        zzT(this.zzcdw.zza(qrVar, pzVar, zza, zzHk, true));
        this.zzccd.zza(qrVar.zzHb(), map, (pf) new rt(this, qrVar, zzHk, completionListener));
        Iterator<Map.Entry<qr, xm>> it = pzVar.iterator();
        while (it.hasNext()) {
            zzn(zzb(qrVar.zzh((qr) it.next().getKey()), -9));
        }
    }

    public final void zza(qr qrVar, xm xmVar, DatabaseReference.CompletionListener completionListener) {
        if (this.zzcdq.zzIH()) {
            wl wlVar = this.zzcdq;
            String valueOf = String.valueOf(qrVar);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 5).append("set: ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        if (this.zzcds.zzIH()) {
            wl wlVar2 = this.zzcds;
            String valueOf2 = String.valueOf(qrVar);
            String valueOf3 = String.valueOf(xmVar);
            wlVar2.zzb(new StringBuilder(String.valueOf(valueOf2).length() + 6 + String.valueOf(valueOf3).length()).append("set: ").append(valueOf2).append(" ").append(valueOf3).toString(), (Throwable) null, new Object[0]);
        }
        xm zza = se.zza(xmVar, se.zza(this.zzcdj));
        long zzHk = zzHk();
        zzT(this.zzcdw.zza(qrVar, xmVar, zza, zzHk, true, true));
        this.zzccd.zza(qrVar.zzHb(), xmVar.getValue(true), (pf) new rs(this, qrVar, zzHk, completionListener));
        zzn(zzb(qrVar, -9));
    }

    public final void zza(qr qrVar, DatabaseReference.CompletionListener completionListener) {
        this.zzccd.zza(qrVar.zzHb(), (pf) new qx(this, qrVar, completionListener));
    }

    public final void zza(qr qrVar, Transaction.Handler handler, boolean z) {
        Transaction.Result result;
        if (this.zzcdq.zzIH()) {
            wl wlVar = this.zzcdq;
            String valueOf = String.valueOf(qrVar);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 13).append("transaction: ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        if (this.zzcds.zzIH()) {
            wl wlVar2 = this.zzcdq;
            String valueOf2 = String.valueOf(qrVar);
            wlVar2.zzb(new StringBuilder(String.valueOf(valueOf2).length() + 13).append("transaction: ").append(valueOf2).toString(), (Throwable) null, new Object[0]);
        }
        if (this.zzcdp.zzcaE && !this.zzcdy) {
            this.zzcdy = true;
            this.zzcdr.info("runTransaction() usage detected while persistence is enabled. Please be aware that transactions *will not* be persisted across database restarts.  See https://www.firebase.com/docs/android/guide/offline-capabilities.html#section-handling-transactions-offline for more details.");
        }
        DatabaseReference zza = zzh.zza(this, qrVar);
        qz qzVar = new qz(this);
        zzf((qi) new to(this, qzVar, zza.zzFr()));
        int i = rw.zzcej;
        long j = this.zzcdz;
        this.zzcdz = 1 + j;
        rv rvVar = new rv(qrVar, handler, qzVar, i, z, j, (qv) null);
        xm zza2 = zza(qrVar, (List<Long>) new ArrayList());
        xm unused = rvVar.zzceg = zza2;
        DatabaseError databaseError = null;
        try {
            Transaction.Result doTransaction = handler.doTransaction(zzh.zza(zza2));
            if (doTransaction == null) {
                throw new NullPointerException("Transaction returned null as result");
            }
            result = doTransaction;
            if (!result.isSuccess()) {
                xm unused2 = rvVar.zzceh = null;
                xm unused3 = rvVar.zzcei = null;
                zzo((Runnable) new ra(this, handler, databaseError, zzh.zza(zza, xf.zzj(rvVar.zzceg))));
                return;
            }
            int unused4 = rvVar.zzceb = rw.zzcek;
            vb<List<rv>> zzK = this.zzcdm.zzK(qrVar);
            List value = zzK.getValue();
            if (value == null) {
                value = new ArrayList();
            }
            value.add(rvVar);
            zzK.setValue(value);
            Map<String, Object> zza3 = se.zza(this.zzcdj);
            xm zzFn = result.zzFn();
            xm zza4 = se.zza(zzFn, zza3);
            xm unused5 = rvVar.zzceh = zzFn;
            xm unused6 = rvVar.zzcei = zza4;
            long unused7 = rvVar.zzcef = zzHk();
            zzT(this.zzcdw.zza(qrVar, zzFn, zza4, rvVar.zzcef, z, false));
            zzHl();
        } catch (Throwable th) {
            databaseError = DatabaseError.fromException(th);
            result = Transaction.abort();
        }
    }

    public final void zza(qr qrVar, Map<qr, xm> map, DatabaseReference.CompletionListener completionListener, Map<String, Object> map2) {
        this.zzccd.zzb(qrVar.zzHb(), map2, (pf) new qw(this, qrVar, map, completionListener));
    }

    public final void zza(vt vtVar, boolean z) {
        this.zzcdw.zza(vtVar, z);
    }

    /* access modifiers changed from: package-private */
    public final void zza(DatabaseReference.CompletionListener completionListener, DatabaseError databaseError, qr qrVar) {
        if (completionListener != null) {
            wp zzHf = qrVar.zzHf();
            zzo((Runnable) new rr(this, completionListener, databaseError, (zzHf == null || !zzHf.zzIN()) ? zzh.zza(this, qrVar) : zzh.zza(this, qrVar.zzHe())));
        }
    }

    public final void zza(List<String> list, Object obj, boolean z, Long l) {
        List<? extends vk> zzi;
        qr qrVar = new qr(list);
        if (this.zzcdq.zzIH()) {
            wl wlVar = this.zzcdq;
            String valueOf = String.valueOf(qrVar);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 14).append("onDataUpdate: ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        if (this.zzcds.zzIH()) {
            wl wlVar2 = this.zzcdq;
            String valueOf2 = String.valueOf(qrVar);
            String valueOf3 = String.valueOf(obj);
            wlVar2.zzb(new StringBuilder(String.valueOf(valueOf2).length() + 15 + String.valueOf(valueOf3).length()).append("onDataUpdate: ").append(valueOf2).append(" ").append(valueOf3).toString(), (Throwable) null, new Object[0]);
        }
        this.zzcdt++;
        if (l != null) {
            try {
                th thVar = new th(l.longValue());
                if (z) {
                    HashMap hashMap = new HashMap();
                    for (Map.Entry entry : ((Map) obj).entrySet()) {
                        hashMap.put(new qr((String) entry.getKey()), xp.zza(entry.getValue(), xd.zzJb()));
                    }
                    zzi = this.zzcdw.zza(qrVar, (Map<qr, xm>) hashMap, thVar);
                } else {
                    zzi = this.zzcdw.zza(qrVar, xp.zza(obj, xd.zzJb()), thVar);
                }
            } catch (DatabaseException e) {
                this.zzcdq.zzd("FIREBASE INTERNAL ERROR", e);
                return;
            }
        } else if (z) {
            HashMap hashMap2 = new HashMap();
            for (Map.Entry entry2 : ((Map) obj).entrySet()) {
                hashMap2.put(new qr((String) entry2.getKey()), xp.zza(entry2.getValue(), xd.zzJb()));
            }
            zzi = this.zzcdw.zza(qrVar, (Map<qr, xm>) hashMap2);
        } else {
            zzi = this.zzcdw.zzi(qrVar, xp.zza(obj, xd.zzJb()));
        }
        if (zzi.size() > 0) {
            zzn(qrVar);
        }
        zzT(zzi);
    }

    public final void zza(List<String> list, List<pe> list2, Long l) {
        qr qrVar = new qr(list);
        if (this.zzcdq.zzIH()) {
            wl wlVar = this.zzcdq;
            String valueOf = String.valueOf(qrVar);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 20).append("onRangeMergeUpdate: ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        if (this.zzcds.zzIH()) {
            wl wlVar2 = this.zzcdq;
            String valueOf2 = String.valueOf(qrVar);
            String valueOf3 = String.valueOf(list2);
            wlVar2.zzb(new StringBuilder(String.valueOf(valueOf2).length() + 21 + String.valueOf(valueOf3).length()).append("onRangeMergeUpdate: ").append(valueOf2).append(" ").append(valueOf3).toString(), (Throwable) null, new Object[0]);
        }
        this.zzcdt++;
        ArrayList arrayList = new ArrayList(list2.size());
        for (pe xtVar : list2) {
            arrayList.add(new xt(xtVar));
        }
        List<? extends vk> zza = l != null ? this.zzcdw.zza(qrVar, (List<xt>) arrayList, new th(l.longValue())) : this.zzcdw.zzb(qrVar, (List<xt>) arrayList);
        if (zza.size() > 0) {
            zzn(qrVar);
        }
        zzT(zza);
    }

    public final void zzaB(boolean z) {
        zza(qc.zzccM, (Object) Boolean.valueOf(z));
    }

    public final void zzb(qr qrVar, xm xmVar, DatabaseReference.CompletionListener completionListener) {
        this.zzccd.zzb(qrVar.zzHb(), xmVar.getValue(true), (pf) new ru(this, qrVar, xmVar, completionListener));
    }

    public final void zze(qi qiVar) {
        zzT(qc.zzccK.equals(qiVar.zzGH().zzFq().zzHc()) ? this.zzcdv.zzh(qiVar) : this.zzcdw.zzh(qiVar));
    }

    public final void zzf(qi qiVar) {
        wp zzHc = qiVar.zzGH().zzFq().zzHc();
        zzT((zzHc == null || !zzHc.equals(qc.zzccK)) ? this.zzcdw.zzg(qiVar) : this.zzcdv.zzg(qiVar));
    }

    public final void zzo(Runnable runnable) {
        this.zzcdp.zzGO();
        this.zzcdp.zzccO.zzo(runnable);
    }

    public final void zzq(Runnable runnable) {
        this.zzcdp.zzGO();
        this.zzcdp.zzccQ.zzq(runnable);
    }
}
