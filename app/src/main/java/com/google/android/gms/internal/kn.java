package com.google.android.gms.internal;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

final class kn implements kr {
    private /* synthetic */ PhoneAuthCredential zzbXf;

    kn(kl klVar, PhoneAuthCredential phoneAuthCredential) {
        this.zzbXf = phoneAuthCredential;
    }

    public final void zza(PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onVerificationCompleted(this.zzbXf);
    }
}
