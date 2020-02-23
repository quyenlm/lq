package com.google.firebase.appindexing.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzn implements OnCompleteListener<Status> {
    final /* synthetic */ TaskCompletionSource zzaCV;
    final /* synthetic */ zzk zzbVS;
    final /* synthetic */ zzm zzbVT;
    final /* synthetic */ int zzqX;

    zzn(zzm zzm, int i, zzk zzk, TaskCompletionSource taskCompletionSource) {
        this.zzbVT = zzm;
        this.zzqX = i;
        this.zzbVS = zzk;
        this.zzaCV = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task<Status> task) {
        if (this.zzqX < zzm.MAX_RETRIES && zzm.zzc(task)) {
            zzo zzo = new zzo(this);
            long pow = (long) (((double) zzm.zzbVO) * Math.pow(zzm.zzbVP, (double) this.zzqX) * ((((Math.random() * 2.0d) - 1.0d) * zzm.zzbVQ) + 1.0d));
            if (this.zzbVT.mHandler.postDelayed(zzo, pow)) {
                zzw.zzgo(new StringBuilder(47).append("Task will be retried in ").append(pow).append(" ms").toString());
                return;
            }
            zzw.zzgo("Failed to schedule retry -- failing immediately!");
        }
        if (task.isSuccessful()) {
            Status result = task.getResult();
            if (result.isSuccess()) {
                this.zzaCV.setResult(null);
            } else {
                this.zzaCV.setException(zzaa.zzb(result, "Indexing error, please try again."));
            }
        } else {
            this.zzaCV.setException(task.getException());
        }
    }
}
