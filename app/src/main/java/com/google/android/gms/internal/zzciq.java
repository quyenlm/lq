package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.stats.zza;

public final class zzciq implements ServiceConnection, zzf, zzg {
    final /* synthetic */ zzcid zzbua;
    /* access modifiers changed from: private */
    public volatile boolean zzbuh;
    private volatile zzcfk zzbui;

    protected zzciq(zzcid zzcid) {
        this.zzbua = zzcid;
    }

    @MainThread
    public final void onConnected(@Nullable Bundle bundle) {
        zzbo.zzcz("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                this.zzbui = null;
                this.zzbua.zzwE().zzj(new zzcit(this, (zzcfd) this.zzbui.zzrf()));
            } catch (DeadObjectException | IllegalStateException e) {
                this.zzbui = null;
                this.zzbuh = false;
            }
        }
    }

    @MainThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzbo.zzcz("MeasurementServiceConnection.onConnectionFailed");
        zzcfl zzyQ = this.zzbua.zzboe.zzyQ();
        if (zzyQ != null) {
            zzyQ.zzyz().zzj("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzbuh = false;
            this.zzbui = null;
        }
        this.zzbua.zzwE().zzj(new zzciv(this));
    }

    @MainThread
    public final void onConnectionSuspended(int i) {
        zzbo.zzcz("MeasurementServiceConnection.onConnectionSuspended");
        this.zzbua.zzwF().zzyC().log("Service connection suspended");
        this.zzbua.zzwE().zzj(new zzciu(this));
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008d A[SYNTHETIC, Splitter:B:39:0x008d] */
    @android.support.annotation.MainThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onServiceConnected(android.content.ComponentName r5, android.os.IBinder r6) {
        /*
            r4 = this;
            r1 = 0
            java.lang.String r0 = "MeasurementServiceConnection.onServiceConnected"
            com.google.android.gms.common.internal.zzbo.zzcz(r0)
            monitor-enter(r4)
            if (r6 != 0) goto L_0x001d
            r0 = 0
            r4.zzbuh = r0     // Catch:{ all -> 0x0054 }
            com.google.android.gms.internal.zzcid r0 = r4.zzbua     // Catch:{ all -> 0x0054 }
            com.google.android.gms.internal.zzcfl r0 = r0.zzwF()     // Catch:{ all -> 0x0054 }
            com.google.android.gms.internal.zzcfn r0 = r0.zzyx()     // Catch:{ all -> 0x0054 }
            java.lang.String r1 = "Service connected with null binder"
            r0.log(r1)     // Catch:{ all -> 0x0054 }
            monitor-exit(r4)     // Catch:{ all -> 0x0054 }
        L_0x001c:
            return
        L_0x001d:
            java.lang.String r0 = r6.getInterfaceDescriptor()     // Catch:{ RemoteException -> 0x006a }
            java.lang.String r2 = "com.google.android.gms.measurement.internal.IMeasurementService"
            boolean r2 = r2.equals(r0)     // Catch:{ RemoteException -> 0x006a }
            if (r2 == 0) goto L_0x007c
            if (r6 != 0) goto L_0x0057
            r0 = r1
        L_0x002c:
            com.google.android.gms.internal.zzcid r1 = r4.zzbua     // Catch:{ RemoteException -> 0x009e }
            com.google.android.gms.internal.zzcfl r1 = r1.zzwF()     // Catch:{ RemoteException -> 0x009e }
            com.google.android.gms.internal.zzcfn r1 = r1.zzyD()     // Catch:{ RemoteException -> 0x009e }
            java.lang.String r2 = "Bound to IMeasurementService interface"
            r1.log(r2)     // Catch:{ RemoteException -> 0x009e }
        L_0x003b:
            if (r0 != 0) goto L_0x008d
            r0 = 0
            r4.zzbuh = r0     // Catch:{ all -> 0x0054 }
            com.google.android.gms.common.stats.zza.zzrU()     // Catch:{ IllegalArgumentException -> 0x009c }
            com.google.android.gms.internal.zzcid r0 = r4.zzbua     // Catch:{ IllegalArgumentException -> 0x009c }
            android.content.Context r0 = r0.getContext()     // Catch:{ IllegalArgumentException -> 0x009c }
            com.google.android.gms.internal.zzcid r1 = r4.zzbua     // Catch:{ IllegalArgumentException -> 0x009c }
            com.google.android.gms.internal.zzciq r1 = r1.zzbtT     // Catch:{ IllegalArgumentException -> 0x009c }
            r0.unbindService(r1)     // Catch:{ IllegalArgumentException -> 0x009c }
        L_0x0052:
            monitor-exit(r4)     // Catch:{ all -> 0x0054 }
            goto L_0x001c
        L_0x0054:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0054 }
            throw r0
        L_0x0057:
            java.lang.String r0 = "com.google.android.gms.measurement.internal.IMeasurementService"
            android.os.IInterface r0 = r6.queryLocalInterface(r0)     // Catch:{ RemoteException -> 0x006a }
            boolean r2 = r0 instanceof com.google.android.gms.internal.zzcfd     // Catch:{ RemoteException -> 0x006a }
            if (r2 == 0) goto L_0x0064
            com.google.android.gms.internal.zzcfd r0 = (com.google.android.gms.internal.zzcfd) r0     // Catch:{ RemoteException -> 0x006a }
            goto L_0x002c
        L_0x0064:
            com.google.android.gms.internal.zzcff r0 = new com.google.android.gms.internal.zzcff     // Catch:{ RemoteException -> 0x006a }
            r0.<init>(r6)     // Catch:{ RemoteException -> 0x006a }
            goto L_0x002c
        L_0x006a:
            r0 = move-exception
            r0 = r1
        L_0x006c:
            com.google.android.gms.internal.zzcid r1 = r4.zzbua     // Catch:{ all -> 0x0054 }
            com.google.android.gms.internal.zzcfl r1 = r1.zzwF()     // Catch:{ all -> 0x0054 }
            com.google.android.gms.internal.zzcfn r1 = r1.zzyx()     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "Service connect failed to get IMeasurementService"
            r1.log(r2)     // Catch:{ all -> 0x0054 }
            goto L_0x003b
        L_0x007c:
            com.google.android.gms.internal.zzcid r2 = r4.zzbua     // Catch:{ RemoteException -> 0x006a }
            com.google.android.gms.internal.zzcfl r2 = r2.zzwF()     // Catch:{ RemoteException -> 0x006a }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ RemoteException -> 0x006a }
            java.lang.String r3 = "Got binder with a wrong descriptor"
            r2.zzj(r3, r0)     // Catch:{ RemoteException -> 0x006a }
            r0 = r1
            goto L_0x003b
        L_0x008d:
            com.google.android.gms.internal.zzcid r1 = r4.zzbua     // Catch:{ all -> 0x0054 }
            com.google.android.gms.internal.zzcgg r1 = r1.zzwE()     // Catch:{ all -> 0x0054 }
            com.google.android.gms.internal.zzcir r2 = new com.google.android.gms.internal.zzcir     // Catch:{ all -> 0x0054 }
            r2.<init>(r4, r0)     // Catch:{ all -> 0x0054 }
            r1.zzj(r2)     // Catch:{ all -> 0x0054 }
            goto L_0x0052
        L_0x009c:
            r0 = move-exception
            goto L_0x0052
        L_0x009e:
            r1 = move-exception
            goto L_0x006c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzciq.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
    }

    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        zzbo.zzcz("MeasurementServiceConnection.onServiceDisconnected");
        this.zzbua.zzwF().zzyC().log("Service disconnected");
        this.zzbua.zzwE().zzj(new zzcis(this, componentName));
    }

    @WorkerThread
    public final void zzk(Intent intent) {
        this.zzbua.zzjC();
        Context context = this.zzbua.getContext();
        zza zzrU = zza.zzrU();
        synchronized (this) {
            if (this.zzbuh) {
                this.zzbua.zzwF().zzyD().log("Connection attempt already in progress");
                return;
            }
            this.zzbuh = true;
            zzrU.zza(context, intent, this.zzbua.zzbtT, 129);
        }
    }

    @WorkerThread
    public final void zzzm() {
        this.zzbua.zzjC();
        Context context = this.zzbua.getContext();
        synchronized (this) {
            if (this.zzbuh) {
                this.zzbua.zzwF().zzyD().log("Connection attempt already in progress");
            } else if (this.zzbui != null) {
                this.zzbua.zzwF().zzyD().log("Already awaiting connection attempt");
            } else {
                this.zzbui = new zzcfk(context, Looper.getMainLooper(), this, this);
                this.zzbua.zzwF().zzyD().log("Connecting to remote service");
                this.zzbuh = true;
                this.zzbui.zzrb();
            }
        }
    }
}
