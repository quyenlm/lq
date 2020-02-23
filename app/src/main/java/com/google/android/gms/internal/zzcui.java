package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.WorkerThread;
import com.vk.sdk.api.model.VKAttachments;
import java.util.List;

final class zzcui implements Runnable {
    private /* synthetic */ zzcuf zzbHS;

    private zzcui(zzcuf zzcuf) {
        this.zzbHS = zzcuf;
    }

    /* synthetic */ zzcui(zzcuf zzcuf, zzcug zzcug) {
        this(zzcuf);
    }

    @WorkerThread
    public final void run() {
        int unused = this.zzbHS.mState = 3;
        String zzd = this.zzbHS.zzbDw;
        zzcvk.zzaT(new StringBuilder(String.valueOf(zzd).length() + 26).append("Container ").append(zzd).append(" loading failed.").toString());
        if (this.zzbHS.zzbHQ != null) {
            for (zzcut zzcut : this.zzbHS.zzbHQ) {
                if (zzcut.zzCn()) {
                    try {
                        this.zzbHS.zzbHN.logEventInternalNoInterceptor(VKAttachments.TYPE_APP, zzcut.zzCk(), zzcut.zzCl(), zzcut.currentTimeMillis());
                        String valueOf = String.valueOf(zzcut.zzCk());
                        zzcvk.v(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Logged event ").append(valueOf).append(" to Firebase (marked as passthrough).").toString());
                    } catch (RemoteException e) {
                        zzcup.zza("Error logging event with measurement proxy:", e, this.zzbHS.mContext);
                    }
                } else {
                    String valueOf2 = String.valueOf(zzcut.zzCk());
                    zzcvk.v(new StringBuilder(String.valueOf(valueOf2).length() + 45).append("Discarded event ").append(valueOf2).append(" (marked as non-passthrough).").toString());
                }
            }
            List unused2 = this.zzbHS.zzbHQ = null;
        }
    }
}
