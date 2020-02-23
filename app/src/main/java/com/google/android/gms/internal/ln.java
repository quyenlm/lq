package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.firebase.auth.ActionCodeResult;

public final class ln implements ActionCodeResult {
    private final String zzalO;
    private final String zzbXD;
    private final int zzbja;

    public ln(@NonNull ld ldVar) {
        if (TextUtils.isEmpty(ldVar.zzEU())) {
            this.zzalO = ldVar.getEmail();
        } else {
            this.zzalO = ldVar.zzEU();
        }
        this.zzbXD = ldVar.getEmail();
        if (TextUtils.isEmpty(ldVar.zzEV())) {
            this.zzbja = 3;
        } else if (ldVar.zzEV().equals("PASSWORD_RESET")) {
            this.zzbja = 0;
        } else if (ldVar.zzEV().equals("VERIFY_EMAIL")) {
            this.zzbja = 1;
        } else if (ldVar.zzEV().equals("RECOVER_EMAIL")) {
            this.zzbja = 2;
        } else {
            this.zzbja = 3;
        }
    }

    @Nullable
    public final String getData(int i) {
        switch (i) {
            case 0:
                return this.zzalO;
            case 1:
                return this.zzbXD;
            default:
                return null;
        }
    }

    public final int getOperation() {
        return this.zzbja;
    }
}
