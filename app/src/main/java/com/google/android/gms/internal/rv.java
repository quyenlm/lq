package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

final class rv implements Comparable<rv> {
    /* access modifiers changed from: private */
    public int retryCount;
    /* access modifiers changed from: private */
    public qr zzbZf;
    /* access modifiers changed from: private */
    public Transaction.Handler zzcdZ;
    /* access modifiers changed from: private */
    public ValueEventListener zzcea;
    /* access modifiers changed from: private */
    public int zzceb;
    private long zzcec;
    /* access modifiers changed from: private */
    public boolean zzced;
    /* access modifiers changed from: private */
    public DatabaseError zzcee;
    /* access modifiers changed from: private */
    public long zzcef;
    /* access modifiers changed from: private */
    public xm zzceg;
    /* access modifiers changed from: private */
    public xm zzceh;
    /* access modifiers changed from: private */
    public xm zzcei;

    private rv(qr qrVar, Transaction.Handler handler, ValueEventListener valueEventListener, int i, boolean z, long j) {
        this.zzbZf = qrVar;
        this.zzcdZ = handler;
        this.zzcea = valueEventListener;
        this.zzceb = i;
        this.retryCount = 0;
        this.zzced = z;
        this.zzcec = j;
        this.zzcee = null;
        this.zzceg = null;
        this.zzceh = null;
        this.zzcei = null;
    }

    /* synthetic */ rv(qr qrVar, Transaction.Handler handler, ValueEventListener valueEventListener, int i, boolean z, long j, qv qvVar) {
        this(qrVar, handler, valueEventListener, i, z, j);
    }

    static /* synthetic */ int zzd(rv rvVar) {
        int i = rvVar.retryCount;
        rvVar.retryCount = i + 1;
        return i;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        rv rvVar = (rv) obj;
        if (this.zzcec < rvVar.zzcec) {
            return -1;
        }
        return this.zzcec == rvVar.zzcec ? 0 : 1;
    }
}
