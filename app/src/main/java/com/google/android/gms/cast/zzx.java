package com.google.android.gms.cast;

import android.view.Display;
import com.google.android.gms.cast.CastRemoteDisplay;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;

final class zzx implements ResultCallback<CastRemoteDisplay.CastRemoteDisplaySessionResult> {
    private /* synthetic */ CastRemoteDisplayLocalService zzapJ;

    zzx(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zzapJ = castRemoteDisplayLocalService;
    }

    public final /* synthetic */ void onResult(Result result) {
        if (!((CastRemoteDisplay.CastRemoteDisplaySessionResult) result).getStatus().isSuccess()) {
            this.zzapJ.zzbp("Unable to stop the remote display, result unsuccessful");
        } else {
            this.zzapJ.zzbp("remote display stopped");
        }
        Display unused = this.zzapJ.zzPO = null;
    }
}
