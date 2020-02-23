package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;

final class zzf implements Runnable {
    private /* synthetic */ FirebaseAuth.IdTokenListener zzbWg;
    private /* synthetic */ FirebaseAuth zzbWh;

    zzf(FirebaseAuth firebaseAuth, FirebaseAuth.IdTokenListener idTokenListener) {
        this.zzbWh = firebaseAuth;
        this.zzbWg = idTokenListener;
    }

    public final void run() {
        this.zzbWg.onIdTokenChanged(this.zzbWh);
    }
}
