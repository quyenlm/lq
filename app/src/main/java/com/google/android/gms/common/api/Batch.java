package com.google.android.gms.common.api;

import com.google.android.gms.internal.zzbbe;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends zzbbe<BatchResult> {
    /* access modifiers changed from: private */
    public final Object mLock;
    /* access modifiers changed from: private */
    public int zzaAC;
    /* access modifiers changed from: private */
    public boolean zzaAD;
    /* access modifiers changed from: private */
    public boolean zzaAE;
    /* access modifiers changed from: private */
    public final PendingResult<?>[] zzaAF;

    public static final class Builder {
        private List<PendingResult<?>> zzaAH = new ArrayList();
        private GoogleApiClient zzapu;

        public Builder(GoogleApiClient googleApiClient) {
            this.zzapu = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken<>(this.zzaAH.size());
            this.zzaAH.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zzaAH, this.zzapu, (zzb) null);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.mLock = new Object();
        this.zzaAC = list.size();
        this.zzaAF = new PendingResult[this.zzaAC];
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.zzaBm, this.zzaAF));
            return;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < list.size()) {
                PendingResult<?> pendingResult = list.get(i2);
                this.zzaAF[i2] = pendingResult;
                pendingResult.zza(new zzb(this));
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    /* synthetic */ Batch(List list, GoogleApiClient googleApiClient, zzb zzb) {
        this(list, googleApiClient);
    }

    static /* synthetic */ int zzb(Batch batch) {
        int i = batch.zzaAC;
        batch.zzaAC = i - 1;
        return i;
    }

    public final void cancel() {
        super.cancel();
        for (PendingResult<?> cancel : this.zzaAF) {
            cancel.cancel();
        }
    }

    /* renamed from: createFailedResult */
    public final BatchResult zzb(Status status) {
        return new BatchResult(status, this.zzaAF);
    }
}
