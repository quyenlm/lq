package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbo;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzbau extends zzbba {
    private final SparseArray<zza> zzaBB = new SparseArray<>();

    class zza implements GoogleApiClient.OnConnectionFailedListener {
        public final int zzaBC;
        public final GoogleApiClient zzaBD;
        public final GoogleApiClient.OnConnectionFailedListener zzaBE;

        public zza(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zzaBC = i;
            this.zzaBD = googleApiClient;
            this.zzaBE = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 27).append("beginFailureResolution for ").append(valueOf).toString());
            zzbau.this.zzb(connectionResult, this.zzaBC);
        }
    }

    private zzbau(zzbdt zzbdt) {
        super(zzbdt);
        this.zzaEG.zza("AutoManageHelper", (zzbds) this);
    }

    public static zzbau zza(zzbdr zzbdr) {
        zzbdt zzb = zzb(zzbdr);
        zzbau zzbau = (zzbau) zzb.zza("AutoManageHelper", zzbau.class);
        return zzbau != null ? zzbau : new zzbau(zzb);
    }

    @Nullable
    private final zza zzam(int i) {
        if (this.zzaBB.size() <= i) {
            return null;
        }
        return this.zzaBB.get(this.zzaBB.keyAt(i));
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zzaBB.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                printWriter.append(str).append("GoogleApiClient #").print(zzam.zzaBC);
                printWriter.println(":");
                zzam.zzaBD.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(this.zzaBB);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 14).append("onStart ").append(z).append(" ").append(valueOf).toString());
        if (this.zzaBN.get() == null) {
            for (int i = 0; i < this.zzaBB.size(); i++) {
                zza zzam = zzam(i);
                if (zzam != null) {
                    zzam.zzaBD.connect();
                }
            }
        }
    }

    public final void onStop() {
        super.onStop();
        for (int i = 0; i < this.zzaBB.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                zzam.zzaBD.disconnect();
            }
        }
    }

    public final void zza(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzbo.zzb(googleApiClient, (Object) "GoogleApiClient instance cannot be null");
        zzbo.zza(this.zzaBB.indexOfKey(i) < 0, (Object) new StringBuilder(54).append("Already managing a GoogleApiClient with id ").append(i).toString());
        zzbbb zzbbb = (zzbbb) this.zzaBN.get();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(zzbbb);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 49).append("starting AutoManage for client ").append(i).append(" ").append(z).append(" ").append(valueOf).toString());
        this.zzaBB.put(i, new zza(i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && zzbbb == null) {
            String valueOf2 = String.valueOf(googleApiClient);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf2).length() + 11).append("connecting ").append(valueOf2).toString());
            googleApiClient.connect();
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza zza2 = this.zzaBB.get(i);
        if (zza2 != null) {
            zzal(i);
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = zza2.zzaBE;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    public final void zzal(int i) {
        zza zza2 = this.zzaBB.get(i);
        this.zzaBB.remove(i);
        if (zza2 != null) {
            zza2.zzaBD.unregisterConnectionFailedListener(zza2);
            zza2.zzaBD.disconnect();
        }
    }

    /* access modifiers changed from: protected */
    public final void zzps() {
        for (int i = 0; i < this.zzaBB.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                zzam.zzaBD.connect();
            }
        }
    }
}
