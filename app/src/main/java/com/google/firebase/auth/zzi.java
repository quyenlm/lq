package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;

final class zzi implements Runnable {
    private /* synthetic */ FirebaseAuth zzbWh;

    zzi(FirebaseAuth firebaseAuth) {
        this.zzbWh = firebaseAuth;
    }

    public final void run() {
        for (FirebaseAuth.AuthStateListener onAuthStateChanged : this.zzbWh.zzbVd) {
            onAuthStateChanged.onAuthStateChanged(this.zzbWh);
        }
    }
}
