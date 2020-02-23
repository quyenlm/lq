package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

final class rr implements Runnable {
    private /* synthetic */ DatabaseReference.CompletionListener zzcdD;
    private /* synthetic */ DatabaseError zzcdV;
    private /* synthetic */ DatabaseReference zzcdW;

    rr(qu quVar, DatabaseReference.CompletionListener completionListener, DatabaseError databaseError, DatabaseReference databaseReference) {
        this.zzcdD = completionListener;
        this.zzcdV = databaseError;
        this.zzcdW = databaseReference;
    }

    public final void run() {
        this.zzcdD.onComplete(this.zzcdV, this.zzcdW);
    }
}
