package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class zzcid extends zzchj {
    /* access modifiers changed from: private */
    public final zzciq zzbtT;
    /* access modifiers changed from: private */
    public zzcfd zzbtU;
    private Boolean zzbtV;
    private final zzcer zzbtW;
    private final zzcjf zzbtX;
    private final List<Runnable> zzbtY = new ArrayList();
    private final zzcer zzbtZ;

    protected zzcid(zzcgl zzcgl) {
        super(zzcgl);
        this.zzbtX = new zzcjf(zzcgl.zzkq());
        this.zzbtT = new zzciq(this);
        this.zzbtW = new zzcie(this, zzcgl);
        this.zzbtZ = new zzcii(this, zzcgl);
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void onServiceDisconnected(ComponentName componentName) {
        super.zzjC();
        if (this.zzbtU != null) {
            this.zzbtU = null;
            super.zzwF().zzyD().zzj("Disconnected from device MeasurementService", componentName);
            super.zzjC();
            zzla();
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzkP() {
        super.zzjC();
        this.zzbtX.start();
        this.zzbtW.zzs(zzcem.zzxB());
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzkQ() {
        super.zzjC();
        if (isConnected()) {
            super.zzwF().zzyD().log("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    @WorkerThread
    private final void zzm(Runnable runnable) throws IllegalStateException {
        super.zzjC();
        if (isConnected()) {
            runnable.run();
        } else if (((long) this.zzbtY.size()) >= zzcem.zzxJ()) {
            super.zzwF().zzyx().log("Discarding data. Max runnable queue size reached");
        } else {
            this.zzbtY.add(runnable);
            this.zzbtZ.zzs(Constants.WATCHDOG_WAKE_TIMER);
            zzla();
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzzl() {
        super.zzjC();
        super.zzwF().zzyD().zzj("Processing queued up service tasks", Integer.valueOf(this.zzbtY.size()));
        for (Runnable run : this.zzbtY) {
            try {
                run.run();
            } catch (Throwable th) {
                super.zzwF().zzyx().zzj("Task exception while flushing queue", th);
            }
        }
        this.zzbtY.clear();
        this.zzbtZ.cancel();
    }

    @WorkerThread
    public final void disconnect() {
        super.zzjC();
        zzkD();
        try {
            zza.zzrU();
            super.getContext().unbindService(this.zzbtT);
        } catch (IllegalArgumentException | IllegalStateException e) {
        }
        this.zzbtU = null;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final boolean isConnected() {
        super.zzjC();
        zzkD();
        return this.zzbtU != null;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zza(zzcfd zzcfd) {
        super.zzjC();
        zzbo.zzu(zzcfd);
        this.zzbtU = zzcfd;
        zzkP();
        zzzl();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zza(zzcfd zzcfd, com.google.android.gms.common.internal.safeparcel.zza zza) {
        super.zzjC();
        super.zzwp();
        zzkD();
        zzcem.zzxE();
        ArrayList arrayList = new ArrayList();
        zzcem.zzxN();
        int i = 100;
        for (int i2 = 0; i2 < 1001 && i == 100; i2++) {
            List<com.google.android.gms.common.internal.safeparcel.zza> zzbp = super.zzwy().zzbp(100);
            if (zzbp != null) {
                arrayList.addAll(zzbp);
                i = zzbp.size();
            } else {
                i = 0;
            }
            if (zza != null && i < 100) {
                arrayList.add(zza);
            }
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                com.google.android.gms.common.internal.safeparcel.zza zza2 = (com.google.android.gms.common.internal.safeparcel.zza) obj;
                if (zza2 instanceof zzcez) {
                    try {
                        zzcfd.zza((zzcez) zza2, super.zzwu().zzdV(super.zzwF().zzyE()));
                    } catch (RemoteException e) {
                        super.zzwF().zzyx().zzj("Failed to send event to the service", e);
                    }
                } else if (zza2 instanceof zzcji) {
                    try {
                        zzcfd.zza((zzcji) zza2, super.zzwu().zzdV(super.zzwF().zzyE()));
                    } catch (RemoteException e2) {
                        super.zzwF().zzyx().zzj("Failed to send attribute to the service", e2);
                    }
                } else if (zza2 instanceof zzcek) {
                    try {
                        zzcfd.zza((zzcek) zza2, super.zzwu().zzdV(super.zzwF().zzyE()));
                    } catch (RemoteException e3) {
                        super.zzwF().zzyx().zzj("Failed to send conditional property to the service", e3);
                    }
                } else {
                    super.zzwF().zzyx().log("Discarding data. Unrecognized parcel type.");
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zza(AppMeasurement.zzb zzb) {
        super.zzjC();
        zzkD();
        zzm(new zzcih(this, zzb));
    }

    @WorkerThread
    public final void zza(AtomicReference<String> atomicReference) {
        super.zzjC();
        zzkD();
        zzm(new zzcif(this, atomicReference));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zza(AtomicReference<List<zzcek>> atomicReference, String str, String str2, String str3) {
        super.zzjC();
        zzkD();
        zzm(new zzcim(this, atomicReference, str, str2, str3));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zza(AtomicReference<List<zzcji>> atomicReference, String str, String str2, String str3, boolean z) {
        super.zzjC();
        zzkD();
        zzm(new zzcin(this, atomicReference, str, str2, str3, z));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zza(AtomicReference<List<zzcji>> atomicReference, boolean z) {
        super.zzjC();
        zzkD();
        zzm(new zzcip(this, atomicReference, z));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzb(zzcji zzcji) {
        super.zzjC();
        zzkD();
        zzcem.zzxE();
        zzm(new zzcio(this, super.zzwy().zza(zzcji), zzcji));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzc(zzcez zzcez, String str) {
        zzbo.zzu(zzcez);
        super.zzjC();
        zzkD();
        zzcem.zzxE();
        zzm(new zzcik(this, true, super.zzwy().zza(zzcez), zzcez, str));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzf(zzcek zzcek) {
        zzbo.zzu(zzcek);
        super.zzjC();
        zzkD();
        zzcem.zzxE();
        zzm(new zzcil(this, true, super.zzwy().zzc(zzcek), new zzcek(zzcek), zzcek));
    }

    public final /* bridge */ /* synthetic */ void zzjC() {
        super.zzjC();
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }

    public final /* bridge */ /* synthetic */ zze zzkq() {
        return super.zzkq();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0053, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0054, code lost:
        r6.zzbtV = java.lang.Boolean.valueOf(r0);
        super.zzwG().zzak(r6.zzbtV.booleanValue());
     */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzla() {
        /*
            r6 = this;
            r2 = 0
            r1 = 1
            super.zzjC()
            r6.zzkD()
            boolean r0 = r6.isConnected()
            if (r0 == 0) goto L_0x000f
        L_0x000e:
            return
        L_0x000f:
            java.lang.Boolean r0 = r6.zzbtV
            if (r0 != 0) goto L_0x0067
            com.google.android.gms.internal.zzcfw r0 = super.zzwG()
            java.lang.Boolean r0 = r0.zzyI()
            r6.zzbtV = r0
            java.lang.Boolean r0 = r6.zzbtV
            if (r0 != 0) goto L_0x0067
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyD()
            java.lang.String r3 = "State of service unknown"
            r0.log(r3)
            super.zzjC()
            r6.zzkD()
            com.google.android.gms.internal.zzcem.zzxE()
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyD()
            java.lang.String r3 = "Checking service availability"
            r0.log(r3)
            com.google.android.gms.common.zze r0 = com.google.android.gms.common.zze.zzoW()
            android.content.Context r3 = super.getContext()
            int r0 = r0.isGooglePlayServicesAvailable(r3)
            switch(r0) {
                case 0: goto L_0x0082;
                case 1: goto L_0x0091;
                case 2: goto L_0x00ae;
                case 3: goto L_0x00bd;
                case 9: goto L_0x00cb;
                case 18: goto L_0x009f;
                default: goto L_0x0053;
            }
        L_0x0053:
            r0 = r2
        L_0x0054:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r6.zzbtV = r0
            com.google.android.gms.internal.zzcfw r0 = super.zzwG()
            java.lang.Boolean r3 = r6.zzbtV
            boolean r3 = r3.booleanValue()
            r0.zzak(r3)
        L_0x0067:
            java.lang.Boolean r0 = r6.zzbtV
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x00da
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyD()
            java.lang.String r1 = "Using measurement service"
            r0.log(r1)
            com.google.android.gms.internal.zzciq r0 = r6.zzbtT
            r0.zzzm()
            goto L_0x000e
        L_0x0082:
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyD()
            java.lang.String r3 = "Service available"
            r0.log(r3)
            r0 = r1
            goto L_0x0054
        L_0x0091:
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyD()
            java.lang.String r3 = "Service missing"
            r0.log(r3)
            goto L_0x0053
        L_0x009f:
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyz()
            java.lang.String r3 = "Service updating"
            r0.log(r3)
            r0 = r1
            goto L_0x0054
        L_0x00ae:
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyC()
            java.lang.String r3 = "Service container out of date"
            r0.log(r3)
            r0 = r1
            goto L_0x0054
        L_0x00bd:
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyz()
            java.lang.String r3 = "Service disabled"
            r0.log(r3)
            goto L_0x0053
        L_0x00cb:
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyz()
            java.lang.String r3 = "Service invalid"
            r0.log(r3)
            goto L_0x0053
        L_0x00da:
            com.google.android.gms.internal.zzcem.zzxE()
            android.content.Context r0 = super.getContext()
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            android.content.Intent r3 = new android.content.Intent
            r3.<init>()
            android.content.Context r4 = super.getContext()
            java.lang.String r5 = "com.google.android.gms.measurement.AppMeasurementService"
            android.content.Intent r3 = r3.setClassName(r4, r5)
            r4 = 65536(0x10000, float:9.18355E-41)
            java.util.List r0 = r0.queryIntentServices(r3, r4)
            if (r0 == 0) goto L_0x0130
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0130
        L_0x0102:
            if (r1 == 0) goto L_0x0132
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyD()
            java.lang.String r1 = "Using local app measurement service"
            r0.log(r1)
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "com.google.android.gms.measurement.START"
            r0.<init>(r1)
            android.content.ComponentName r1 = new android.content.ComponentName
            android.content.Context r2 = super.getContext()
            com.google.android.gms.internal.zzcem.zzxE()
            java.lang.String r3 = "com.google.android.gms.measurement.AppMeasurementService"
            r1.<init>(r2, r3)
            r0.setComponent(r1)
            com.google.android.gms.internal.zzciq r1 = r6.zzbtT
            r1.zzk(r0)
            goto L_0x000e
        L_0x0130:
            r1 = r2
            goto L_0x0102
        L_0x0132:
            com.google.android.gms.internal.zzcfl r0 = super.zzwF()
            com.google.android.gms.internal.zzcfn r0 = r0.zzyx()
            java.lang.String r1 = "Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest"
            r0.log(r1)
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcid.zzla():void");
    }

    public final /* bridge */ /* synthetic */ zzcfj zzwA() {
        return super.zzwA();
    }

    public final /* bridge */ /* synthetic */ zzcjl zzwB() {
        return super.zzwB();
    }

    public final /* bridge */ /* synthetic */ zzcgf zzwC() {
        return super.zzwC();
    }

    public final /* bridge */ /* synthetic */ zzcja zzwD() {
        return super.zzwD();
    }

    public final /* bridge */ /* synthetic */ zzcgg zzwE() {
        return super.zzwE();
    }

    public final /* bridge */ /* synthetic */ zzcfl zzwF() {
        return super.zzwF();
    }

    public final /* bridge */ /* synthetic */ zzcfw zzwG() {
        return super.zzwG();
    }

    public final /* bridge */ /* synthetic */ zzcem zzwH() {
        return super.zzwH();
    }

    public final /* bridge */ /* synthetic */ void zzwo() {
        super.zzwo();
    }

    public final /* bridge */ /* synthetic */ void zzwp() {
        super.zzwp();
    }

    public final /* bridge */ /* synthetic */ void zzwq() {
        super.zzwq();
    }

    public final /* bridge */ /* synthetic */ zzcec zzwr() {
        return super.zzwr();
    }

    public final /* bridge */ /* synthetic */ zzcej zzws() {
        return super.zzws();
    }

    public final /* bridge */ /* synthetic */ zzchl zzwt() {
        return super.zzwt();
    }

    public final /* bridge */ /* synthetic */ zzcfg zzwu() {
        return super.zzwu();
    }

    public final /* bridge */ /* synthetic */ zzcet zzwv() {
        return super.zzwv();
    }

    public final /* bridge */ /* synthetic */ zzcid zzww() {
        return super.zzww();
    }

    public final /* bridge */ /* synthetic */ zzchz zzwx() {
        return super.zzwx();
    }

    public final /* bridge */ /* synthetic */ zzcfh zzwy() {
        return super.zzwy();
    }

    public final /* bridge */ /* synthetic */ zzcen zzwz() {
        return super.zzwz();
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzzj() {
        super.zzjC();
        zzkD();
        zzm(new zzcij(this));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzzk() {
        super.zzjC();
        zzkD();
        zzm(new zzcig(this));
    }
}
