package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbbe;
import com.google.android.gms.internal.zzbec;
import com.google.android.gms.internal.zzben;

public final class PendingResults {

    static final class zza<R extends Result> extends zzbbe<R> {
        private final R zzaBi;

        public zza(R r) {
            super(Looper.getMainLooper());
            this.zzaBi = r;
        }

        /* access modifiers changed from: protected */
        public final R zzb(Status status) {
            if (status.getStatusCode() == this.zzaBi.getStatus().getStatusCode()) {
                return this.zzaBi;
            }
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    static final class zzb<R extends Result> extends zzbbe<R> {
        private final R zzaBj;

        public zzb(GoogleApiClient googleApiClient, R r) {
            super(googleApiClient);
            this.zzaBj = r;
        }

        /* access modifiers changed from: protected */
        public final R zzb(Status status) {
            return this.zzaBj;
        }
    }

    static final class zzc<R extends Result> extends zzbbe<R> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final R zzb(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    private PendingResults() {
    }

    public static PendingResult<Status> canceledPendingResult() {
        zzben zzben = new zzben(Looper.getMainLooper());
        zzben.cancel();
        return zzben;
    }

    public static <R extends Result> PendingResult<R> canceledPendingResult(R r) {
        zzbo.zzb(r, (Object) "Result must not be null");
        zzbo.zzb(r.getStatus().getStatusCode() == 16, (Object) "Status code must be CommonStatusCodes.CANCELED");
        zza zza2 = new zza(r);
        zza2.cancel();
        return zza2;
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r) {
        zzbo.zzb(r, (Object) "Result must not be null");
        zzc zzc2 = new zzc((GoogleApiClient) null);
        zzc2.setResult(r);
        return new zzbec(zzc2);
    }

    public static PendingResult<Status> immediatePendingResult(Status status) {
        zzbo.zzb(status, (Object) "Result must not be null");
        zzben zzben = new zzben(Looper.getMainLooper());
        zzben.setResult(status);
        return zzben;
    }

    public static <R extends Result> PendingResult<R> zza(R r, GoogleApiClient googleApiClient) {
        zzbo.zzb(r, (Object) "Result must not be null");
        zzbo.zzb(!r.getStatus().isSuccess(), (Object) "Status code must not be SUCCESS");
        zzb zzb2 = new zzb(googleApiClient, r);
        zzb2.setResult(r);
        return zzb2;
    }

    public static PendingResult<Status> zza(Status status, GoogleApiClient googleApiClient) {
        zzbo.zzb(status, (Object) "Result must not be null");
        zzben zzben = new zzben(googleApiClient);
        zzben.setResult(status);
        return zzben;
    }

    public static <R extends Result> OptionalPendingResult<R> zzb(R r, GoogleApiClient googleApiClient) {
        zzbo.zzb(r, (Object) "Result must not be null");
        zzc zzc2 = new zzc(googleApiClient);
        zzc2.setResult(r);
        return new zzbec(zzc2);
    }
}
