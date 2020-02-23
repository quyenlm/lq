package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;

final class zzg implements Runnable {
    private /* synthetic */ FirebaseAuth zzbWh;
    private /* synthetic */ FirebaseAuth.AuthStateListener zzbWi;

    zzg(FirebaseAuth firebaseAuth, FirebaseAuth.AuthStateListener authStateListener) {
        this.zzbWh = firebaseAuth;
        this.zzbWi = authStateListener;
    }

    public final void run() {
        this.zzbWi.onAuthStateChanged(this.zzbWh);
    }
}
