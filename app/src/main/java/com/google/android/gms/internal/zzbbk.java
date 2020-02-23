package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zzbbk implements zzbdp {
    private final Context mContext;
    private final zzbcp zzaCl;
    /* access modifiers changed from: private */
    public final zzbcx zzaCm;
    /* access modifiers changed from: private */
    public final zzbcx zzaCn;
    private final Map<Api.zzc<?>, zzbcx> zzaCo;
    private final Set<zzbei> zzaCp = Collections.newSetFromMap(new WeakHashMap());
    private final Api.zze zzaCq;
    private Bundle zzaCr;
    /* access modifiers changed from: private */
    public ConnectionResult zzaCs = null;
    /* access modifiers changed from: private */
    public ConnectionResult zzaCt = null;
    /* access modifiers changed from: private */
    public boolean zzaCu = false;
    /* access modifiers changed from: private */
    public final Lock zzaCv;
    private int zzaCw = 0;
    private final Looper zzrM;

    private zzbbk(Context context, zzbcp zzbcp, Lock lock, Looper looper, zze zze, Map<Api.zzc<?>, Api.zze> map, Map<Api.zzc<?>, Api.zze> map2, zzq zzq, Api.zza<? extends zzctk, zzctl> zza, Api.zze zze2, ArrayList<zzbbi> arrayList, ArrayList<zzbbi> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.mContext = context;
        this.zzaCl = zzbcp;
        this.zzaCv = lock;
        this.zzrM = looper;
        this.zzaCq = zze2;
        this.zzaCm = new zzbcx(context, this.zzaCl, lock, looper, zze, map2, (zzq) null, map4, (Api.zza<? extends zzctk, zzctl>) null, arrayList2, new zzbbm(this, (zzbbl) null));
        this.zzaCn = new zzbcx(context, this.zzaCl, lock, looper, zze, map, zzq, map3, zza, arrayList, new zzbbn(this, (zzbbl) null));
        ArrayMap arrayMap = new ArrayMap();
        for (Api.zzc<?> put : map2.keySet()) {
            arrayMap.put(put, this.zzaCm);
        }
        for (Api.zzc<?> put2 : map.keySet()) {
            arrayMap.put(put2, this.zzaCn);
        }
        this.zzaCo = Collections.unmodifiableMap(arrayMap);
    }

    public static zzbbk zza(Context context, zzbcp zzbcp, Lock lock, Looper looper, zze zze, Map<Api.zzc<?>, Api.zze> map, zzq zzq, Map<Api<?>, Boolean> map2, Api.zza<? extends zzctk, zzctl> zza, ArrayList<zzbbi> arrayList) {
        Api.zze zze2 = null;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        for (Map.Entry next : map.entrySet()) {
            Api.zze zze3 = (Api.zze) next.getValue();
            if (zze3.zzmG()) {
                zze2 = zze3;
            }
            if (zze3.zzmv()) {
                arrayMap.put((Api.zzc) next.getKey(), zze3);
            } else {
                arrayMap2.put((Api.zzc) next.getKey(), zze3);
            }
        }
        zzbo.zza(!arrayMap.isEmpty(), (Object) "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        for (Api next2 : map2.keySet()) {
            Api.zzc<?> zzpd = next2.zzpd();
            if (arrayMap.containsKey(zzpd)) {
                arrayMap3.put(next2, map2.get(next2));
            } else if (arrayMap2.containsKey(zzpd)) {
                arrayMap4.put(next2, map2.get(next2));
            } else {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList4.get(i);
            i++;
            zzbbi zzbbi = (zzbbi) obj;
            if (arrayMap3.containsKey(zzbbi.zzayW)) {
                arrayList2.add(zzbbi);
            } else if (arrayMap4.containsKey(zzbbi.zzayW)) {
                arrayList3.add(zzbbi);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zzbbk(context, zzbcp, lock, looper, zze, arrayMap, arrayMap2, zzq, zza, zze2, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private final void zza(ConnectionResult connectionResult) {
        switch (this.zzaCw) {
            case 1:
                break;
            case 2:
                this.zzaCl.zzc(connectionResult);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        zzpG();
        this.zzaCw = 0;
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    /* access modifiers changed from: private */
    public final void zzd(int i, boolean z) {
        this.zzaCl.zze(i, z);
        this.zzaCt = null;
        this.zzaCs = null;
    }

    private final boolean zzf(zzbay<? extends Result, ? extends Api.zzb> zzbay) {
        Api.zzc<? extends Api.zzb> zzpd = zzbay.zzpd();
        zzbo.zzb(this.zzaCo.containsKey(zzpd), (Object) "GoogleApiClient is not configured to use the API required for this call.");
        return this.zzaCo.get(zzpd).equals(this.zzaCn);
    }

    /* access modifiers changed from: private */
    public final void zzl(Bundle bundle) {
        if (this.zzaCr == null) {
            this.zzaCr = bundle;
        } else if (bundle != null) {
            this.zzaCr.putAll(bundle);
        }
    }

    /* access modifiers changed from: private */
    public final void zzpF() {
        if (zzb(this.zzaCs)) {
            if (zzb(this.zzaCt) || zzpH()) {
                switch (this.zzaCw) {
                    case 1:
                        break;
                    case 2:
                        this.zzaCl.zzm(this.zzaCr);
                        break;
                    default:
                        Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                        break;
                }
                zzpG();
                this.zzaCw = 0;
            } else if (this.zzaCt == null) {
            } else {
                if (this.zzaCw == 1) {
                    zzpG();
                    return;
                }
                zza(this.zzaCt);
                this.zzaCm.disconnect();
            }
        } else if (this.zzaCs != null && zzb(this.zzaCt)) {
            this.zzaCn.disconnect();
            zza(this.zzaCs);
        } else if (this.zzaCs != null && this.zzaCt != null) {
            ConnectionResult connectionResult = this.zzaCs;
            if (this.zzaCn.zzaDX < this.zzaCm.zzaDX) {
                connectionResult = this.zzaCt;
            }
            zza(connectionResult);
        }
    }

    private final void zzpG() {
        for (zzbei zzmF : this.zzaCp) {
            zzmF.zzmF();
        }
        this.zzaCp.clear();
    }

    private final boolean zzpH() {
        return this.zzaCt != null && this.zzaCt.getErrorCode() == 4;
    }

    @Nullable
    private final PendingIntent zzpI() {
        if (this.zzaCq == null) {
            return null;
        }
        return PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zzaCl), this.zzaCq.zzmH(), 134217728);
    }

    public final ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public final void connect() {
        this.zzaCw = 2;
        this.zzaCu = false;
        this.zzaCt = null;
        this.zzaCs = null;
        this.zzaCm.connect();
        this.zzaCn.connect();
    }

    public final void disconnect() {
        this.zzaCt = null;
        this.zzaCs = null;
        this.zzaCw = 0;
        this.zzaCm.disconnect();
        this.zzaCn.disconnect();
        zzpG();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.zzaCn.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.zzaCm.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return this.zzaCo.get(api.zzpd()).equals(this.zzaCn) ? zzpH() ? new ConnectionResult(4, zzpI()) : this.zzaCn.getConnectionResult(api) : this.zzaCm.getConnectionResult(api);
    }

    public final boolean isConnected() {
        boolean z = true;
        this.zzaCv.lock();
        try {
            if (!this.zzaCm.isConnected() || (!this.zzaCn.isConnected() && !zzpH() && this.zzaCw != 1)) {
                z = false;
            }
            return z;
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final boolean isConnecting() {
        this.zzaCv.lock();
        try {
            return this.zzaCw == 2;
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final boolean zza(zzbei zzbei) {
        this.zzaCv.lock();
        try {
            if ((isConnecting() || isConnected()) && !this.zzaCn.isConnected()) {
                this.zzaCp.add(zzbei);
                if (this.zzaCw == 0) {
                    this.zzaCw = 1;
                }
                this.zzaCt = null;
                this.zzaCn.connect();
                return true;
            }
            this.zzaCv.unlock();
            return false;
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbay<R, A>> T zzd(@NonNull T t) {
        if (!zzf((zzbay<? extends Result, ? extends Api.zzb>) t)) {
            return this.zzaCm.zzd(t);
        }
        if (!zzpH()) {
            return this.zzaCn.zzd(t);
        }
        t.zzr(new Status(4, (String) null, zzpI()));
        return t;
    }

    public final <A extends Api.zzb, T extends zzbay<? extends Result, A>> T zze(@NonNull T t) {
        if (!zzf((zzbay<? extends Result, ? extends Api.zzb>) t)) {
            return this.zzaCm.zze(t);
        }
        if (!zzpH()) {
            return this.zzaCn.zze(t);
        }
        t.zzr(new Status(4, (String) null, zzpI()));
        return t;
    }

    public final void zzpE() {
        this.zzaCm.zzpE();
        this.zzaCn.zzpE();
    }

    public final void zzpl() {
        this.zzaCv.lock();
        try {
            boolean isConnecting = isConnecting();
            this.zzaCn.disconnect();
            this.zzaCt = new ConnectionResult(4);
            if (isConnecting) {
                new Handler(this.zzrM).post(new zzbbl(this));
            } else {
                zzpG();
            }
        } finally {
            this.zzaCv.unlock();
        }
    }
}
