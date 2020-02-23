package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.zze;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

public final class zzbcp extends GoogleApiClient implements zzbdq {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final int zzaBb;
    private final GoogleApiAvailability zzaBd;
    private Api.zza<? extends zzctk, zzctl> zzaBe;
    private boolean zzaBh;
    private zzq zzaCA;
    private Map<Api<?>, Boolean> zzaCD;
    final Queue<zzbay<?, ?>> zzaCJ = new LinkedList();
    private final Lock zzaCv;
    private volatile boolean zzaDA;
    private long zzaDB = 120000;
    private long zzaDC = Constants.ACTIVE_THREAD_WATCHDOG;
    private final zzbcu zzaDD;
    private zzbdk zzaDE;
    final Map<Api.zzc<?>, Api.zze> zzaDF;
    Set<Scope> zzaDG = new HashSet();
    private final zzbea zzaDH = new zzbea();
    private final ArrayList<zzbbi> zzaDI;
    private Integer zzaDJ = null;
    Set<zzbes> zzaDK = null;
    final zzbev zzaDL;
    private final zzad zzaDM = new zzbcq(this);
    private final zzac zzaDy;
    private zzbdp zzaDz = null;
    private final Looper zzrM;

    public zzbcp(Context context, Lock lock, Looper looper, zzq zzq, GoogleApiAvailability googleApiAvailability, Api.zza<? extends zzctk, zzctl> zza, Map<Api<?>, Boolean> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.zzc<?>, Api.zze> map2, int i, int i2, ArrayList<zzbbi> arrayList, boolean z) {
        this.mContext = context;
        this.zzaCv = lock;
        this.zzaBh = false;
        this.zzaDy = new zzac(looper, this.zzaDM);
        this.zzrM = looper;
        this.zzaDD = new zzbcu(this, looper);
        this.zzaBd = googleApiAvailability;
        this.zzaBb = i;
        if (this.zzaBb >= 0) {
            this.zzaDJ = Integer.valueOf(i2);
        }
        this.zzaCD = map;
        this.zzaDF = map2;
        this.zzaDI = arrayList;
        this.zzaDL = new zzbev(this.zzaDF);
        for (GoogleApiClient.ConnectionCallbacks registerConnectionCallbacks : list) {
            this.zzaDy.registerConnectionCallbacks(registerConnectionCallbacks);
        }
        for (GoogleApiClient.OnConnectionFailedListener registerConnectionFailedListener : list2) {
            this.zzaDy.registerConnectionFailedListener(registerConnectionFailedListener);
        }
        this.zzaCA = zzq;
        this.zzaBe = zza;
    }

    /* access modifiers changed from: private */
    public final void resume() {
        this.zzaCv.lock();
        try {
            if (this.zzaDA) {
                zzqc();
            }
        } finally {
            this.zzaCv.unlock();
        }
    }

    public static int zza(Iterable<Api.zze> iterable, boolean z) {
        boolean z2 = false;
        boolean z3 = false;
        for (Api.zze next : iterable) {
            if (next.zzmv()) {
                z3 = true;
            }
            z2 = next.zzmG() ? true : z2;
        }
        if (z3) {
            return (!z2 || !z) ? 1 : 2;
        }
        return 3;
    }

    /* access modifiers changed from: private */
    public final void zza(GoogleApiClient googleApiClient, zzben zzben, boolean z) {
        zzbfo.zzaIy.zzd(googleApiClient).setResultCallback(new zzbct(this, zzben, z, googleApiClient));
    }

    private final void zzap(int i) {
        if (this.zzaDJ == null) {
            this.zzaDJ = Integer.valueOf(i);
        } else if (this.zzaDJ.intValue() != i) {
            String valueOf = String.valueOf(zzaq(i));
            String valueOf2 = String.valueOf(zzaq(this.zzaDJ.intValue()));
            throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 51 + String.valueOf(valueOf2).length()).append("Cannot use sign-in mode: ").append(valueOf).append(". Mode was already set to ").append(valueOf2).toString());
        }
        if (this.zzaDz == null) {
            boolean z = false;
            boolean z2 = false;
            for (Api.zze next : this.zzaDF.values()) {
                if (next.zzmv()) {
                    z2 = true;
                }
                z = next.zzmG() ? true : z;
            }
            switch (this.zzaDJ.intValue()) {
                case 1:
                    if (!z2) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    } else if (z) {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                    break;
                case 2:
                    if (z2) {
                        if (this.zzaBh) {
                            this.zzaDz = new zzbbp(this.mContext, this.zzaCv, this.zzrM, this.zzaBd, this.zzaDF, this.zzaCA, this.zzaCD, this.zzaBe, this.zzaDI, this, true);
                            return;
                        } else {
                            this.zzaDz = zzbbk.zza(this.mContext, this, this.zzaCv, this.zzrM, this.zzaBd, this.zzaDF, this.zzaCA, this.zzaCD, this.zzaBe, this.zzaDI);
                            return;
                        }
                    }
                    break;
            }
            if (!this.zzaBh || z) {
                this.zzaDz = new zzbcx(this.mContext, this, this.zzaCv, this.zzrM, this.zzaBd, this.zzaDF, this.zzaCA, this.zzaCD, this.zzaBe, this.zzaDI, this);
            } else {
                this.zzaDz = new zzbbp(this.mContext, this.zzaCv, this.zzrM, this.zzaBd, this.zzaDF, this.zzaCA, this.zzaCD, this.zzaBe, this.zzaDI, this, false);
            }
        }
    }

    private static String zzaq(int i) {
        switch (i) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    private final void zzqc() {
        this.zzaDy.zzrA();
        this.zzaDz.connect();
    }

    /* access modifiers changed from: private */
    public final void zzqd() {
        this.zzaCv.lock();
        try {
            if (zzqe()) {
                zzqc();
            }
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final ConnectionResult blockingConnect() {
        boolean z = true;
        zzbo.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        this.zzaCv.lock();
        try {
            if (this.zzaBb >= 0) {
                if (this.zzaDJ == null) {
                    z = false;
                }
                zzbo.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzaDJ == null) {
                this.zzaDJ = Integer.valueOf(zza(this.zzaDF.values(), false));
            } else if (this.zzaDJ.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zzap(this.zzaDJ.intValue());
            this.zzaDy.zzrA();
            return this.zzaDz.blockingConnect();
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        boolean z = false;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            z = true;
        }
        zzbo.zza(z, (Object) "blockingConnect must not be called on the UI thread");
        zzbo.zzb(timeUnit, (Object) "TimeUnit must not be null");
        this.zzaCv.lock();
        try {
            if (this.zzaDJ == null) {
                this.zzaDJ = Integer.valueOf(zza(this.zzaDF.values(), false));
            } else if (this.zzaDJ.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zzap(this.zzaDJ.intValue());
            this.zzaDy.zzrA();
            return this.zzaDz.blockingConnect(j, timeUnit);
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        zzbo.zza(isConnected(), (Object) "GoogleApiClient is not connected yet.");
        zzbo.zza(this.zzaDJ.intValue() != 2, (Object) "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        zzben zzben = new zzben((GoogleApiClient) this);
        if (this.zzaDF.containsKey(zzbfo.zzajR)) {
            zza(this, zzben, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient build = new GoogleApiClient.Builder(this.mContext).addApi(zzbfo.API).addConnectionCallbacks(new zzbcr(this, atomicReference, zzben)).addOnConnectionFailedListener(new zzbcs(this, zzben)).setHandler(this.zzaDD).build();
            atomicReference.set(build);
            build.connect();
        }
        return zzben;
    }

    public final void connect() {
        boolean z = false;
        this.zzaCv.lock();
        try {
            if (this.zzaBb >= 0) {
                if (this.zzaDJ != null) {
                    z = true;
                }
                zzbo.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzaDJ == null) {
                this.zzaDJ = Integer.valueOf(zza(this.zzaDF.values(), false));
            } else if (this.zzaDJ.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.zzaDJ.intValue());
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void connect(int i) {
        boolean z = true;
        this.zzaCv.lock();
        if (!(i == 3 || i == 1 || i == 2)) {
            z = false;
        }
        try {
            zzbo.zzb(z, (Object) new StringBuilder(33).append("Illegal sign-in mode: ").append(i).toString());
            zzap(i);
            zzqc();
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void disconnect() {
        this.zzaCv.lock();
        try {
            this.zzaDL.release();
            if (this.zzaDz != null) {
                this.zzaDz.disconnect();
            }
            this.zzaDH.release();
            for (zzbay zzbay : this.zzaCJ) {
                zzbay.zza((zzbex) null);
                zzbay.cancel();
            }
            this.zzaCJ.clear();
            if (this.zzaDz != null) {
                zzqe();
                this.zzaDy.zzrz();
                this.zzaCv.unlock();
            }
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.mContext);
        printWriter.append(str).append("mResuming=").print(this.zzaDA);
        printWriter.append(" mWorkQueue.size()=").print(this.zzaCJ.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zzaDL.zzaFl.size());
        if (this.zzaDz != null) {
            this.zzaDz.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.zzaCv.lock();
        try {
            if (!isConnected() && !this.zzaDA) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            } else if (this.zzaDF.containsKey(api.zzpd())) {
                ConnectionResult connectionResult = this.zzaDz.getConnectionResult(api);
                if (connectionResult != null) {
                    this.zzaCv.unlock();
                } else if (this.zzaDA) {
                    connectionResult = ConnectionResult.zzazX;
                } else {
                    Log.w("GoogleApiClientImpl", zzqg());
                    Log.wtf("GoogleApiClientImpl", String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                    connectionResult = new ConnectionResult(8, (PendingIntent) null);
                    this.zzaCv.unlock();
                }
                return connectionResult;
            } else {
                throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
            }
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzrM;
    }

    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        if (!isConnected()) {
            return false;
        }
        Api.zze zze = this.zzaDF.get(api.zzpd());
        return zze != null && zze.isConnected();
    }

    public final boolean isConnected() {
        return this.zzaDz != null && this.zzaDz.isConnected();
    }

    public final boolean isConnecting() {
        return this.zzaDz != null && this.zzaDz.isConnecting();
    }

    public final boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zzaDy.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public final boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zzaDy.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public final void reconnect() {
        disconnect();
        connect();
    }

    public final void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzaDy.registerConnectionCallbacks(connectionCallbacks);
    }

    public final void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaDy.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        zzbdr zzbdr = new zzbdr(fragmentActivity);
        if (this.zzaBb >= 0) {
            zzbau.zza(zzbdr).zzal(this.zzaBb);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    public final void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzaDy.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public final void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaDy.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @NonNull
    public final <C extends Api.zze> C zza(@NonNull Api.zzc<C> zzc) {
        C c = (Api.zze) this.zzaDF.get(zzc);
        zzbo.zzb(c, (Object) "Appropriate Api was not requested.");
        return c;
    }

    public final void zza(zzbes zzbes) {
        this.zzaCv.lock();
        try {
            if (this.zzaDK == null) {
                this.zzaDK = new HashSet();
            }
            this.zzaDK.add(zzbes);
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final boolean zza(@NonNull Api<?> api) {
        return this.zzaDF.containsKey(api.zzpd());
    }

    public final boolean zza(zzbei zzbei) {
        return this.zzaDz != null && this.zzaDz.zza(zzbei);
    }

    public final void zzb(zzbes zzbes) {
        this.zzaCv.lock();
        try {
            if (this.zzaDK == null) {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (!this.zzaDK.remove(zzbes)) {
                Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (!zzqf()) {
                this.zzaDz.zzpE();
            }
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void zzc(ConnectionResult connectionResult) {
        if (!zze.zze(this.mContext, connectionResult.getErrorCode())) {
            zzqe();
        }
        if (!this.zzaDA) {
            this.zzaDy.zzk(connectionResult);
            this.zzaDy.zzrz();
        }
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbay<R, A>> T zzd(@NonNull T t) {
        zzbo.zzb(t.zzpd() != null, (Object) "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.zzaDF.containsKey(t.zzpd());
        String name = t.zzpg() != null ? t.zzpg().getName() : "the API";
        zzbo.zzb(containsKey, (Object) new StringBuilder(String.valueOf(name).length() + 65).append("GoogleApiClient is not configured to use ").append(name).append(" required for this call.").toString());
        this.zzaCv.lock();
        try {
            if (this.zzaDz == null) {
                this.zzaCJ.add(t);
            } else {
                t = this.zzaDz.zzd(t);
                this.zzaCv.unlock();
            }
            return t;
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final <A extends Api.zzb, T extends zzbay<? extends Result, A>> T zze(@NonNull T t) {
        zzbo.zzb(t.zzpd() != null, (Object) "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.zzaDF.containsKey(t.zzpd());
        String name = t.zzpg() != null ? t.zzpg().getName() : "the API";
        zzbo.zzb(containsKey, (Object) new StringBuilder(String.valueOf(name).length() + 65).append("GoogleApiClient is not configured to use ").append(name).append(" required for this call.").toString());
        this.zzaCv.lock();
        try {
            if (this.zzaDz == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (this.zzaDA) {
                this.zzaCJ.add(t);
                while (!this.zzaCJ.isEmpty()) {
                    zzbay remove = this.zzaCJ.remove();
                    this.zzaDL.zzb(remove);
                    remove.zzr(Status.zzaBo);
                }
            } else {
                t = this.zzaDz.zze(t);
                this.zzaCv.unlock();
            }
            return t;
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void zze(int i, boolean z) {
        if (i == 1 && !z && !this.zzaDA) {
            this.zzaDA = true;
            if (this.zzaDE == null) {
                this.zzaDE = GoogleApiAvailability.zza(this.mContext.getApplicationContext(), (zzbdl) new zzbcv(this));
            }
            this.zzaDD.sendMessageDelayed(this.zzaDD.obtainMessage(1), this.zzaDB);
            this.zzaDD.sendMessageDelayed(this.zzaDD.obtainMessage(2), this.zzaDC);
        }
        this.zzaDL.zzqM();
        this.zzaDy.zzaA(i);
        this.zzaDy.zzrz();
        if (i == 2) {
            zzqc();
        }
    }

    public final void zzm(Bundle bundle) {
        while (!this.zzaCJ.isEmpty()) {
            zze(this.zzaCJ.remove());
        }
        this.zzaDy.zzn(bundle);
    }

    public final <L> zzbdw<L> zzp(@NonNull L l) {
        this.zzaCv.lock();
        try {
            return this.zzaDH.zza(l, this.zzrM, "NO_TYPE");
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void zzpl() {
        if (this.zzaDz != null) {
            this.zzaDz.zzpl();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzqe() {
        if (!this.zzaDA) {
            return false;
        }
        this.zzaDA = false;
        this.zzaDD.removeMessages(2);
        this.zzaDD.removeMessages(1);
        if (this.zzaDE != null) {
            this.zzaDE.unregister();
            this.zzaDE = null;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzqf() {
        boolean z = false;
        this.zzaCv.lock();
        try {
            if (this.zzaDK != null) {
                if (!this.zzaDK.isEmpty()) {
                    z = true;
                }
                this.zzaCv.unlock();
            }
            return z;
        } finally {
            this.zzaCv.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final String zzqg() {
        StringWriter stringWriter = new StringWriter();
        dump("", (FileDescriptor) null, new PrintWriter(stringWriter), (String[]) null);
        return stringWriter.toString();
    }
}
