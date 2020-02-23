package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzbcx implements zzbbj, zzbdp {
    private final Context mContext;
    private Api.zza<? extends zzctk, zzctl> zzaBe;
    private zzq zzaCA;
    private Map<Api<?>, Boolean> zzaCD;
    private final zze zzaCF;
    final zzbcp zzaCl;
    /* access modifiers changed from: private */
    public final Lock zzaCv;
    final Map<Api.zzc<?>, Api.zze> zzaDF;
    private final Condition zzaDS;
    private final zzbcz zzaDT;
    final Map<Api.zzc<?>, ConnectionResult> zzaDU = new HashMap();
    /* access modifiers changed from: private */
    public volatile zzbcw zzaDV;
    private ConnectionResult zzaDW = null;
    int zzaDX;
    final zzbdq zzaDY;

    public zzbcx(Context context, zzbcp zzbcp, Lock lock, Looper looper, zze zze, Map<Api.zzc<?>, Api.zze> map, zzq zzq, Map<Api<?>, Boolean> map2, Api.zza<? extends zzctk, zzctl> zza, ArrayList<zzbbi> arrayList, zzbdq zzbdq) {
        this.mContext = context;
        this.zzaCv = lock;
        this.zzaCF = zze;
        this.zzaDF = map;
        this.zzaCA = zzq;
        this.zzaCD = map2;
        this.zzaBe = zza;
        this.zzaCl = zzbcp;
        this.zzaDY = zzbdq;
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zzbbi) obj).zza(this);
        }
        this.zzaDT = new zzbcz(this, looper);
        this.zzaDS = lock.newCondition();
        this.zzaDV = new zzbco(this);
    }

    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zzaDS.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, (PendingIntent) null);
            }
        }
        return isConnected() ? ConnectionResult.zzazX : this.zzaDW != null ? this.zzaDW : new ConnectionResult(13, (PendingIntent) null);
    }

    public final ConnectionResult blockingConnect(long j, TimeUnit timeUnit) {
        connect();
        long nanos = timeUnit.toNanos(j);
        while (isConnecting()) {
            if (nanos <= 0) {
                try {
                    disconnect();
                    return new ConnectionResult(14, (PendingIntent) null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, (PendingIntent) null);
                }
            } else {
                nanos = this.zzaDS.awaitNanos(nanos);
            }
        }
        return isConnected() ? ConnectionResult.zzazX : this.zzaDW != null ? this.zzaDW : new ConnectionResult(13, (PendingIntent) null);
    }

    public final void connect() {
        this.zzaDV.connect();
    }

    public final void disconnect() {
        if (this.zzaDV.disconnect()) {
            this.zzaDU.clear();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append(str).append("mState=").println(this.zzaDV);
        for (Api next : this.zzaCD.keySet()) {
            printWriter.append(str).append(next.getName()).println(":");
            this.zzaDF.get(next.zzpd()).dump(concat, fileDescriptor, printWriter, strArr);
        }
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        Api.zzc<?> zzpd = api.zzpd();
        if (this.zzaDF.containsKey(zzpd)) {
            if (this.zzaDF.get(zzpd).isConnected()) {
                return ConnectionResult.zzazX;
            }
            if (this.zzaDU.containsKey(zzpd)) {
                return this.zzaDU.get(zzpd);
            }
        }
        return null;
    }

    public final boolean isConnected() {
        return this.zzaDV instanceof zzbca;
    }

    public final boolean isConnecting() {
        return this.zzaDV instanceof zzbcd;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zzaCv.lock();
        try {
            this.zzaDV.onConnected(bundle);
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zzaCv.lock();
        try {
            this.zzaDV.onConnectionSuspended(i);
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z) {
        this.zzaCv.lock();
        try {
            this.zzaDV.zza(connectionResult, api, z);
        } finally {
            this.zzaCv.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzbcy zzbcy) {
        this.zzaDT.sendMessage(this.zzaDT.obtainMessage(1, zzbcy));
    }

    /* access modifiers changed from: package-private */
    public final void zza(RuntimeException runtimeException) {
        this.zzaDT.sendMessage(this.zzaDT.obtainMessage(2, runtimeException));
    }

    public final boolean zza(zzbei zzbei) {
        return false;
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbay<R, A>> T zzd(@NonNull T t) {
        t.zzpC();
        return this.zzaDV.zzd(t);
    }

    public final <A extends Api.zzb, T extends zzbay<? extends Result, A>> T zze(@NonNull T t) {
        t.zzpC();
        return this.zzaDV.zze(t);
    }

    /* access modifiers changed from: package-private */
    public final void zzg(ConnectionResult connectionResult) {
        this.zzaCv.lock();
        try {
            this.zzaDW = connectionResult;
            this.zzaDV = new zzbco(this);
            this.zzaDV.begin();
            this.zzaDS.signalAll();
        } finally {
            this.zzaCv.unlock();
        }
    }

    public final void zzpE() {
        if (isConnected()) {
            ((zzbca) this.zzaDV).zzpU();
        }
    }

    public final void zzpl() {
    }

    /* access modifiers changed from: package-private */
    public final void zzqh() {
        this.zzaCv.lock();
        try {
            this.zzaDV = new zzbcd(this, this.zzaCA, this.zzaCD, this.zzaCF, this.zzaBe, this.zzaCv, this.mContext);
            this.zzaDV.begin();
            this.zzaDS.signalAll();
        } finally {
            this.zzaCv.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzqi() {
        this.zzaCv.lock();
        try {
            this.zzaCl.zzqe();
            this.zzaDV = new zzbca(this);
            this.zzaDV.begin();
            this.zzaDS.signalAll();
        } finally {
            this.zzaCv.unlock();
        }
    }
}
