package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzao;
import com.google.android.gms.common.internal.zzbo;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zzbbe<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zzaBV = new zzbbf();
    private Status mStatus;
    private boolean zzJ;
    private final Object zzaBW;
    private zzbbg<R> zzaBX;
    private WeakReference<GoogleApiClient> zzaBY;
    private final ArrayList<PendingResult.zza> zzaBZ;
    /* access modifiers changed from: private */
    public R zzaBj;
    private ResultCallback<? super R> zzaCa;
    private final AtomicReference<zzbex> zzaCb;
    private zzbbh zzaCc;
    private volatile boolean zzaCd;
    private boolean zzaCe;
    private zzao zzaCf;
    private volatile zzbes<R> zzaCg;
    private boolean zzaCh;
    private final CountDownLatch zztJ;

    @Deprecated
    zzbbe() {
        this.zzaBW = new Object();
        this.zztJ = new CountDownLatch(1);
        this.zzaBZ = new ArrayList<>();
        this.zzaCb = new AtomicReference<>();
        this.zzaCh = false;
        this.zzaBX = new zzbbg<>(Looper.getMainLooper());
        this.zzaBY = new WeakReference<>((Object) null);
    }

    @Deprecated
    protected zzbbe(Looper looper) {
        this.zzaBW = new Object();
        this.zztJ = new CountDownLatch(1);
        this.zzaBZ = new ArrayList<>();
        this.zzaCb = new AtomicReference<>();
        this.zzaCh = false;
        this.zzaBX = new zzbbg<>(looper);
        this.zzaBY = new WeakReference<>((Object) null);
    }

    protected zzbbe(GoogleApiClient googleApiClient) {
        this.zzaBW = new Object();
        this.zztJ = new CountDownLatch(1);
        this.zzaBZ = new ArrayList<>();
        this.zzaCb = new AtomicReference<>();
        this.zzaCh = false;
        this.zzaBX = new zzbbg<>(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.zzaBY = new WeakReference<>(googleApiClient);
    }

    private final R get() {
        R r;
        boolean z = true;
        synchronized (this.zzaBW) {
            if (this.zzaCd) {
                z = false;
            }
            zzbo.zza(z, (Object) "Result has already been consumed.");
            zzbo.zza(isReady(), (Object) "Result is not ready.");
            r = this.zzaBj;
            this.zzaBj = null;
            this.zzaCa = null;
            this.zzaCd = true;
        }
        zzbex andSet = this.zzaCb.getAndSet((Object) null);
        if (andSet != null) {
            andSet.zzc(this);
        }
        return r;
    }

    private final void zzb(R r) {
        this.zzaBj = r;
        this.zzaCf = null;
        this.zztJ.countDown();
        this.mStatus = this.zzaBj.getStatus();
        if (this.zzJ) {
            this.zzaCa = null;
        } else if (this.zzaCa != null) {
            this.zzaBX.removeMessages(2);
            this.zzaBX.zza(this.zzaCa, get());
        } else if (this.zzaBj instanceof Releasable) {
            this.zzaCc = new zzbbh(this, (zzbbf) null);
        }
        ArrayList arrayList = this.zzaBZ;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PendingResult.zza) obj).zzo(this.mStatus);
        }
        this.zzaBZ.clear();
    }

    public static void zzc(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String valueOf = String.valueOf(result);
                Log.w("BasePendingResult", new StringBuilder(String.valueOf(valueOf).length() + 18).append("Unable to release ").append(valueOf).toString(), e);
            }
        }
    }

    public final R await() {
        boolean z = true;
        zzbo.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread");
        zzbo.zza(!this.zzaCd, (Object) "Result has already been consumed");
        if (this.zzaCg != null) {
            z = false;
        }
        zzbo.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            this.zztJ.await();
        } catch (InterruptedException e) {
            zzs(Status.zzaBn);
        }
        zzbo.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    public final R await(long j, TimeUnit timeUnit) {
        boolean z = true;
        zzbo.zza(j <= 0 || Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread when time is greater than zero.");
        zzbo.zza(!this.zzaCd, (Object) "Result has already been consumed.");
        if (this.zzaCg != null) {
            z = false;
        }
        zzbo.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.zztJ.await(j, timeUnit)) {
                zzs(Status.zzaBp);
            }
        } catch (InterruptedException e) {
            zzs(Status.zzaBn);
        }
        zzbo.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
            r2 = this;
            java.lang.Object r1 = r2.zzaBW
            monitor-enter(r1)
            boolean r0 = r2.zzJ     // Catch:{ all -> 0x0029 }
            if (r0 != 0) goto L_0x000b
            boolean r0 = r2.zzaCd     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x000d
        L_0x000b:
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
        L_0x000c:
            return
        L_0x000d:
            com.google.android.gms.common.internal.zzao r0 = r2.zzaCf     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0016
            com.google.android.gms.common.internal.zzao r0 = r2.zzaCf     // Catch:{ RemoteException -> 0x002c }
            r0.cancel()     // Catch:{ RemoteException -> 0x002c }
        L_0x0016:
            R r0 = r2.zzaBj     // Catch:{ all -> 0x0029 }
            zzc(r0)     // Catch:{ all -> 0x0029 }
            r0 = 1
            r2.zzJ = r0     // Catch:{ all -> 0x0029 }
            com.google.android.gms.common.api.Status r0 = com.google.android.gms.common.api.Status.zzaBq     // Catch:{ all -> 0x0029 }
            com.google.android.gms.common.api.Result r0 = r2.zzb((com.google.android.gms.common.api.Status) r0)     // Catch:{ all -> 0x0029 }
            r2.zzb(r0)     // Catch:{ all -> 0x0029 }
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            goto L_0x000c
        L_0x0029:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            throw r0
        L_0x002c:
            r0 = move-exception
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbbe.cancel():void");
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.zzaBW) {
            z = this.zzJ;
        }
        return z;
    }

    public final boolean isReady() {
        return this.zztJ.getCount() == 0;
    }

    public final void setResult(R r) {
        boolean z = true;
        synchronized (this.zzaBW) {
            if (this.zzaCe || this.zzJ) {
                zzc(r);
                return;
            }
            if (isReady()) {
            }
            zzbo.zza(!isReady(), (Object) "Results have already been set");
            if (this.zzaCd) {
                z = false;
            }
            zzbo.zza(z, (Object) "Result has already been consumed");
            zzb(r);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r6) {
        /*
            r5 = this;
            r0 = 1
            r1 = 0
            java.lang.Object r3 = r5.zzaBW
            monitor-enter(r3)
            if (r6 != 0) goto L_0x000c
            r0 = 0
            r5.zzaCa = r0     // Catch:{ all -> 0x0027 }
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
        L_0x000b:
            return
        L_0x000c:
            boolean r2 = r5.zzaCd     // Catch:{ all -> 0x0027 }
            if (r2 != 0) goto L_0x002a
            r2 = r0
        L_0x0011:
            java.lang.String r4 = "Result has already been consumed."
            com.google.android.gms.common.internal.zzbo.zza((boolean) r2, (java.lang.Object) r4)     // Catch:{ all -> 0x0027 }
            com.google.android.gms.internal.zzbes<R> r2 = r5.zzaCg     // Catch:{ all -> 0x0027 }
            if (r2 != 0) goto L_0x002c
        L_0x001a:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.zzbo.zza((boolean) r0, (java.lang.Object) r1)     // Catch:{ all -> 0x0027 }
            boolean r0 = r5.isCanceled()     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x002e
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            goto L_0x000b
        L_0x0027:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            throw r0
        L_0x002a:
            r2 = r1
            goto L_0x0011
        L_0x002c:
            r0 = r1
            goto L_0x001a
        L_0x002e:
            boolean r0 = r5.isReady()     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x003f
            com.google.android.gms.internal.zzbbg<R> r0 = r5.zzaBX     // Catch:{ all -> 0x0027 }
            com.google.android.gms.common.api.Result r1 = r5.get()     // Catch:{ all -> 0x0027 }
            r0.zza(r6, r1)     // Catch:{ all -> 0x0027 }
        L_0x003d:
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            goto L_0x000b
        L_0x003f:
            r5.zzaCa = r6     // Catch:{ all -> 0x0027 }
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbbe.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r7, long r8, java.util.concurrent.TimeUnit r10) {
        /*
            r6 = this;
            r0 = 1
            r1 = 0
            java.lang.Object r3 = r6.zzaBW
            monitor-enter(r3)
            if (r7 != 0) goto L_0x000c
            r0 = 0
            r6.zzaCa = r0     // Catch:{ all -> 0x0027 }
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
        L_0x000b:
            return
        L_0x000c:
            boolean r2 = r6.zzaCd     // Catch:{ all -> 0x0027 }
            if (r2 != 0) goto L_0x002a
            r2 = r0
        L_0x0011:
            java.lang.String r4 = "Result has already been consumed."
            com.google.android.gms.common.internal.zzbo.zza((boolean) r2, (java.lang.Object) r4)     // Catch:{ all -> 0x0027 }
            com.google.android.gms.internal.zzbes<R> r2 = r6.zzaCg     // Catch:{ all -> 0x0027 }
            if (r2 != 0) goto L_0x002c
        L_0x001a:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.zzbo.zza((boolean) r0, (java.lang.Object) r1)     // Catch:{ all -> 0x0027 }
            boolean r0 = r6.isCanceled()     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x002e
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            goto L_0x000b
        L_0x0027:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            throw r0
        L_0x002a:
            r2 = r1
            goto L_0x0011
        L_0x002c:
            r0 = r1
            goto L_0x001a
        L_0x002e:
            boolean r0 = r6.isReady()     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x003f
            com.google.android.gms.internal.zzbbg<R> r0 = r6.zzaBX     // Catch:{ all -> 0x0027 }
            com.google.android.gms.common.api.Result r1 = r6.get()     // Catch:{ all -> 0x0027 }
            r0.zza(r7, r1)     // Catch:{ all -> 0x0027 }
        L_0x003d:
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            goto L_0x000b
        L_0x003f:
            r6.zzaCa = r7     // Catch:{ all -> 0x0027 }
            com.google.android.gms.internal.zzbbg<R> r0 = r6.zzaBX     // Catch:{ all -> 0x0027 }
            long r4 = r10.toMillis(r8)     // Catch:{ all -> 0x0027 }
            r1 = 2
            android.os.Message r1 = r0.obtainMessage(r1, r6)     // Catch:{ all -> 0x0027 }
            r0.sendMessageDelayed(r1, r4)     // Catch:{ all -> 0x0027 }
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbbe.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        boolean z = true;
        zzbo.zza(!this.zzaCd, (Object) "Result has already been consumed.");
        synchronized (this.zzaBW) {
            zzbo.zza(this.zzaCg == null, (Object) "Cannot call then() twice.");
            zzbo.zza(this.zzaCa == null, (Object) "Cannot call then() if callbacks are set.");
            if (this.zzJ) {
                z = false;
            }
            zzbo.zza(z, (Object) "Cannot call then() if result was canceled.");
            this.zzaCh = true;
            this.zzaCg = new zzbes<>(this.zzaBY);
            then = this.zzaCg.then(resultTransform);
            if (isReady()) {
                this.zzaBX.zza(this.zzaCg, get());
            } else {
                this.zzaCa = this.zzaCg;
            }
        }
        return then;
    }

    public final void zza(PendingResult.zza zza) {
        zzbo.zzb(zza != null, (Object) "Callback cannot be null.");
        synchronized (this.zzaBW) {
            if (isReady()) {
                zza.zzo(this.mStatus);
            } else {
                this.zzaBZ.add(zza);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(zzao zzao) {
        synchronized (this.zzaBW) {
            this.zzaCf = zzao;
        }
    }

    public final void zza(zzbex zzbex) {
        this.zzaCb.set(zzbex);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public abstract R zzb(Status status);

    public final boolean zzpB() {
        boolean isCanceled;
        synchronized (this.zzaBW) {
            if (((GoogleApiClient) this.zzaBY.get()) == null || !this.zzaCh) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public final void zzpC() {
        this.zzaCh = this.zzaCh || zzaBV.get().booleanValue();
    }

    public final Integer zzpo() {
        return null;
    }

    public final void zzs(Status status) {
        synchronized (this.zzaBW) {
            if (!isReady()) {
                setResult(zzb(status));
                this.zzaCe = true;
            }
        }
    }
}
