package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.WorkerThread;
import com.vk.sdk.api.model.VKAttachments;

final class zzcuk implements Runnable {
    private /* synthetic */ zzcuf zzbHS;
    private final zzcut zzbHT;

    public zzcuk(zzcuf zzcuf, zzcut zzcut) {
        this.zzbHS = zzcuf;
        this.zzbHT = zzcut;
    }

    @WorkerThread
    public final void run() {
        if (this.zzbHS.mState == 2) {
            String valueOf = String.valueOf(this.zzbHT.zzCk());
            zzcvk.v(valueOf.length() != 0 ? "Evaluating tags for event ".concat(valueOf) : new String("Evaluating tags for event "));
            this.zzbHS.zzbHP.zzb(this.zzbHT);
        } else if (this.zzbHS.mState == 1) {
            this.zzbHS.zzbHQ.add(this.zzbHT);
            String valueOf2 = String.valueOf(this.zzbHT.zzCk());
            zzcvk.v(new StringBuilder(String.valueOf(valueOf2).length() + 30).append("Added event ").append(valueOf2).append(" to pending queue.").toString());
        } else if (this.zzbHS.mState == 3) {
            String valueOf3 = String.valueOf(this.zzbHT.zzCk());
            zzcvk.v(new StringBuilder(String.valueOf(valueOf3).length() + 61).append("Failed to evaluate tags for event ").append(valueOf3).append(" (container failed to load)").toString());
            if (this.zzbHT.zzCn()) {
                try {
                    this.zzbHS.zzbHN.logEventInternalNoInterceptor(VKAttachments.TYPE_APP, this.zzbHT.zzCk(), this.zzbHT.zzCl(), this.zzbHT.currentTimeMillis());
                    String valueOf4 = String.valueOf(this.zzbHT.zzCk());
                    zzcvk.v(new StringBuilder(String.valueOf(valueOf4).length() + 38).append("Logged passthrough event ").append(valueOf4).append(" to Firebase.").toString());
                } catch (RemoteException e) {
                    zzcup.zza("Error logging event with measurement proxy:", e, this.zzbHS.mContext);
                }
            } else {
                String valueOf5 = String.valueOf(this.zzbHT.zzCk());
                zzcvk.v(valueOf5.length() != 0 ? "Discarded non-passthrough event ".concat(valueOf5) : new String("Discarded non-passthrough event "));
            }
        }
    }
}
