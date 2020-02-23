package com.google.android.gms.internal;

import java.util.concurrent.Callable;

final class zzul implements Callable<zzuh> {
    private /* synthetic */ zzue zzMT;
    private /* synthetic */ zzuk zzMU;

    zzul(zzuk zzuk, zzue zzue) {
        this.zzMU = zzuk;
        this.zzMT = zzue;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzfp */
    public final zzuh call() throws Exception {
        synchronized (this.zzMU.mLock) {
            if (this.zzMU.zzMP) {
                return null;
            }
            return this.zzMT.zza(this.zzMU.mStartTime, this.zzMU.zzMN);
        }
    }
}
