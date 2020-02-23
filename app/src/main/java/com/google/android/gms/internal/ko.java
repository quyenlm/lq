package com.google.android.gms.internal;

import com.google.firebase.auth.PhoneAuthProvider;

final class ko implements kr {
    private /* synthetic */ String zzbXe;

    ko(kl klVar, String str) {
        this.zzbXe = str;
    }

    public final void zza(PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onCodeAutoRetrievalTimeOut(this.zzbXe);
    }
}
