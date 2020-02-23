package com.google.android.gms.location.places;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbay;
import com.google.android.gms.internal.zzcdz;
import com.google.android.gms.location.places.internal.zzw;

public class zzm extends zzw {
    private static final String TAG = zzm.class.getSimpleName();
    private final zzd zzbjN;
    private final zza zzbjO;
    private final zze zzbjP;
    private final zzf zzbjQ;
    private final zzc zzbjR;

    public static abstract class zza<A extends Api.zze> extends zzb<AutocompletePredictionBuffer, A> {
        public zza(Api api, GoogleApiClient googleApiClient) {
            super(api, googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new AutocompletePredictionBuffer(DataHolder.zzau(status.getStatusCode()));
        }
    }

    public static abstract class zzb<R extends Result, A extends Api.zze> extends zzbay<R, A> {
        public zzb(Api api, GoogleApiClient googleApiClient) {
            super((Api<?>) api, googleApiClient);
        }

        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Result) obj);
        }
    }

    public static abstract class zzc<A extends Api.zze> extends zzb<PlaceBuffer, A> {
        public zzc(Api api, GoogleApiClient googleApiClient) {
            super(api, googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new PlaceBuffer(DataHolder.zzau(status.getStatusCode()));
        }
    }

    public static abstract class zzd<A extends Api.zze> extends zzb<PlaceLikelihoodBuffer, A> {
        public zzd(Api api, GoogleApiClient googleApiClient) {
            super(api, googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new PlaceLikelihoodBuffer(DataHolder.zzau(status.getStatusCode()), 100);
        }
    }

    public static abstract class zzf<A extends Api.zze> extends zzb<Status, A> {
        public zzf(Api api, GoogleApiClient googleApiClient) {
            super(api, googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return status;
        }
    }

    @Deprecated
    public static abstract class zze<A extends Api.zze> extends zzb<zzcdz, A> {
    }

    public zzm(zza zza2) {
        this.zzbjN = null;
        this.zzbjO = zza2;
        this.zzbjP = null;
        this.zzbjQ = null;
        this.zzbjR = null;
    }

    public zzm(zzc zzc2) {
        this.zzbjN = null;
        this.zzbjO = null;
        this.zzbjP = null;
        this.zzbjQ = null;
        this.zzbjR = zzc2;
    }

    public zzm(zzd zzd2) {
        this.zzbjN = zzd2;
        this.zzbjO = null;
        this.zzbjP = null;
        this.zzbjQ = null;
        this.zzbjR = null;
    }

    public zzm(zzf zzf2) {
        this.zzbjN = null;
        this.zzbjO = null;
        this.zzbjP = null;
        this.zzbjQ = zzf2;
        this.zzbjR = null;
    }

    public final void zzF(Status status) throws RemoteException {
        this.zzbjQ.setResult(status);
    }

    public final void zzO(DataHolder dataHolder) throws RemoteException {
        zzbo.zza(this.zzbjN != null, (Object) "placeEstimator cannot be null");
        if (dataHolder == null) {
            if (Log.isLoggable(TAG, 6)) {
                Log.e(TAG, "onPlaceEstimated received null DataHolder", new Throwable());
            }
            this.zzbjN.zzr(Status.zzaBo);
            return;
        }
        Bundle zzqN = dataHolder.zzqN();
        this.zzbjN.setResult(new PlaceLikelihoodBuffer(dataHolder, zzqN == null ? 100 : PlaceLikelihoodBuffer.zzz(zzqN)));
    }

    public final void zzP(DataHolder dataHolder) throws RemoteException {
        if (dataHolder == null) {
            if (Log.isLoggable(TAG, 6)) {
                Log.e(TAG, "onAutocompletePrediction received null DataHolder", new Throwable());
            }
            this.zzbjO.zzr(Status.zzaBo);
            return;
        }
        this.zzbjO.setResult(new AutocompletePredictionBuffer(dataHolder));
    }

    public final void zzQ(DataHolder dataHolder) throws RemoteException {
        zze zze2 = null;
        if (dataHolder == null) {
            if (Log.isLoggable(TAG, 6)) {
                Log.e(TAG, "onPlaceUserDataFetched received null DataHolder", new Throwable());
            }
            zze2.zzr(Status.zzaBo);
            return;
        }
        zze2.setResult(new zzcdz(dataHolder));
    }

    public final void zzR(DataHolder dataHolder) throws RemoteException {
        this.zzbjR.setResult(new PlaceBuffer(dataHolder));
    }
}
