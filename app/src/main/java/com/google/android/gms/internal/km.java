package com.google.android.gms.internal;

import com.google.firebase.auth.PhoneAuthProvider;

final class km implements kr {
    private /* synthetic */ String zzbXe;

    km(kl klVar, String str) {
        this.zzbXe = str;
    }

    public final void zza(PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onCodeSent(this.zzbXe, PhoneAuthProvider.ForceResendingToken.zzEK());
    }
}
