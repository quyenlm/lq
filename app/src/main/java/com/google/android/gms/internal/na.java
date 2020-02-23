package com.google.android.gms.internal;

import com.google.firebase.FirebaseApp;

final class na implements FirebaseApp.zzc {
    private /* synthetic */ on zzbZC;

    na(mx mxVar, on onVar) {
        this.zzbZC = onVar;
    }

    public final void zzac(boolean z) {
        if (z) {
            this.zzbZC.interrupt("app_in_background");
        } else {
            this.zzbZC.resume("app_in_background");
        }
    }
}
