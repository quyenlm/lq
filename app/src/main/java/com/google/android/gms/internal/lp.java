package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

public final class lp implements AuthResult {
    private ls zzbXF;
    private lo zzbXG = null;

    public lp(@NonNull ls lsVar) {
        this.zzbXF = (ls) zzbo.zzu(lsVar);
        List<lq> zzEY = this.zzbXF.zzEY();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < zzEY.size()) {
                if (!TextUtils.isEmpty(zzEY.get(i2).getRawUserInfo())) {
                    this.zzbXG = new lo(zzEY.get(i2).getProviderId(), zzEY.get(i2).getRawUserInfo());
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    @Nullable
    public final AdditionalUserInfo getAdditionalUserInfo() {
        return this.zzbXG;
    }

    @Nullable
    public final FirebaseUser getUser() {
        return this.zzbXF;
    }
}
