package com.google.android.gms.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zza;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzbdb implements Handler.Callback {
    public static final Status zzaEc = new Status(4, "Sign-out occurred while this API call was in progress.");
    /* access modifiers changed from: private */
    public static final Status zzaEd = new Status(4, "The user must be signed in to make this API call.");
    private static zzbdb zzaEf;
    /* access modifiers changed from: private */
    public static final Object zzuF = new Object();
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    /* access modifiers changed from: private */
    public final GoogleApiAvailability zzaBd;
    /* access modifiers changed from: private */
    public final Map<zzbat<?>, zzbdd<?>> zzaCB = new ConcurrentHashMap(5, 0.75f, 1);
    /* access modifiers changed from: private */
    public long zzaDB = 120000;
    /* access modifiers changed from: private */
    public long zzaDC = Constants.ACTIVE_THREAD_WATCHDOG;
    /* access modifiers changed from: private */
    public long zzaEe = 10000;
    /* access modifiers changed from: private */
    public int zzaEg = -1;
    private final AtomicInteger zzaEh = new AtomicInteger(1);
    private final AtomicInteger zzaEi = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public zzbbw zzaEj = null;
    /* access modifiers changed from: private */
    public final Set<zzbat<?>> zzaEk = new zza();
    private final Set<zzbat<?>> zzaEl = new zza();

    private zzbdb(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.mContext = context;
        this.mHandler = new Handler(looper, this);
        this.zzaBd = googleApiAvailability;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6));
    }

    public static zzbdb zzay(Context context) {
        zzbdb zzbdb;
        synchronized (zzuF) {
            if (zzaEf == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zzaEf = new zzbdb(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            zzbdb = zzaEf;
        }
        return zzbdb;
    }

    @WorkerThread
    private final void zzc(GoogleApi<?> googleApi) {
        zzbat<?> zzph = googleApi.zzph();
        zzbdd zzbdd = this.zzaCB.get(zzph);
        if (zzbdd == null) {
            zzbdd = new zzbdd(this, googleApi);
            this.zzaCB.put(zzph, zzbdd);
        }
        if (zzbdd.zzmv()) {
            this.zzaEl.add(zzph);
        }
        zzbdd.connect();
    }

    public static zzbdb zzqk() {
        zzbdb zzbdb;
        synchronized (zzuF) {
            zzbo.zzb(zzaEf, (Object) "Must guarantee manager is non-null before using getInstance");
            zzbdb = zzaEf;
        }
        return zzbdb;
    }

    public static void zzql() {
        synchronized (zzuF) {
            if (zzaEf != null) {
                zzbdb zzbdb = zzaEf;
                zzbdb.zzaEi.incrementAndGet();
                zzbdb.mHandler.sendMessageAtFrontOfQueue(zzbdb.mHandler.obtainMessage(10));
            }
        }
    }

    @WorkerThread
    private final void zzqn() {
        for (zzbat<?> remove : this.zzaEl) {
            this.zzaCB.remove(remove).signOut();
        }
        this.zzaEl.clear();
    }

    @WorkerThread
    public final boolean handleMessage(Message message) {
        zzbdd zzbdd;
        switch (message.what) {
            case 1:
                this.zzaEe = ((Boolean) message.obj).booleanValue() ? 10000 : 300000;
                this.mHandler.removeMessages(12);
                for (zzbat<?> obtainMessage : this.zzaCB.keySet()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(12, obtainMessage), this.zzaEe);
                }
                break;
            case 2:
                zzbav zzbav = (zzbav) message.obj;
                Iterator<zzbat<?>> it = zzbav.zzpt().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else {
                        zzbat next = it.next();
                        zzbdd zzbdd2 = this.zzaCB.get(next);
                        if (zzbdd2 == null) {
                            zzbav.zza(next, new ConnectionResult(13));
                            break;
                        } else if (zzbdd2.isConnected()) {
                            zzbav.zza(next, ConnectionResult.zzazX);
                        } else if (zzbdd2.zzqu() != null) {
                            zzbav.zza(next, zzbdd2.zzqu());
                        } else {
                            zzbdd2.zza(zzbav);
                        }
                    }
                }
            case 3:
                for (zzbdd next2 : this.zzaCB.values()) {
                    next2.zzqt();
                    next2.connect();
                }
                break;
            case 4:
            case 8:
            case 13:
                zzbed zzbed = (zzbed) message.obj;
                zzbdd zzbdd3 = this.zzaCB.get(zzbed.zzaET.zzph());
                if (zzbdd3 == null) {
                    zzc(zzbed.zzaET);
                    zzbdd3 = this.zzaCB.get(zzbed.zzaET.zzph());
                }
                if (zzbdd3.zzmv() && this.zzaEi.get() != zzbed.zzaES) {
                    zzbed.zzaER.zzp(zzaEc);
                    zzbdd3.signOut();
                    break;
                } else {
                    zzbdd3.zza(zzbed.zzaER);
                    break;
                }
                break;
            case 5:
                int i = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                Iterator<zzbdd<?>> it2 = this.zzaCB.values().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        zzbdd = it2.next();
                        if (zzbdd.getInstanceId() == i) {
                        }
                    } else {
                        zzbdd = null;
                    }
                }
                if (zzbdd == null) {
                    Log.wtf("GoogleApiManager", new StringBuilder(76).append("Could not find API instance ").append(i).append(" while trying to fail enqueued calls.").toString(), new Exception());
                    break;
                } else {
                    String valueOf = String.valueOf(this.zzaBd.getErrorString(connectionResult.getErrorCode()));
                    String valueOf2 = String.valueOf(connectionResult.getErrorMessage());
                    zzbdd.zzt(new Status(17, new StringBuilder(String.valueOf(valueOf).length() + 69 + String.valueOf(valueOf2).length()).append("Error resolution was canceled by the user, original error message: ").append(valueOf).append(": ").append(valueOf2).toString()));
                    break;
                }
            case 6:
                if (this.mContext.getApplicationContext() instanceof Application) {
                    zzbaw.zza((Application) this.mContext.getApplicationContext());
                    zzbaw.zzpv().zza((zzbax) new zzbdc(this));
                    if (!zzbaw.zzpv().zzab(true)) {
                        this.zzaEe = 300000;
                        break;
                    }
                }
                break;
            case 7:
                zzc((GoogleApi<?>) (GoogleApi) message.obj);
                break;
            case 9:
                if (this.zzaCB.containsKey(message.obj)) {
                    this.zzaCB.get(message.obj).resume();
                    break;
                }
                break;
            case 10:
                zzqn();
                break;
            case 11:
                if (this.zzaCB.containsKey(message.obj)) {
                    this.zzaCB.get(message.obj).zzqd();
                    break;
                }
                break;
            case 12:
                if (this.zzaCB.containsKey(message.obj)) {
                    this.zzaCB.get(message.obj).zzqx();
                    break;
                }
                break;
            default:
                Log.w("GoogleApiManager", new StringBuilder(31).append("Unknown message id: ").append(message.what).toString());
                return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final PendingIntent zza(zzbat<?> zzbat, int i) {
        zzbdd zzbdd = this.zzaCB.get(zzbat);
        if (zzbdd == null) {
            return null;
        }
        zzctk zzqy = zzbdd.zzqy();
        if (zzqy == null) {
            return null;
        }
        return PendingIntent.getActivity(this.mContext, i, zzqy.zzmH(), 134217728);
    }

    public final <O extends Api.ApiOptions> Task<Void> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzbdy<?> zzbdy) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(13, new zzbed(new zzbar(zzbdy, taskCompletionSource), this.zzaEi.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends Api.ApiOptions> Task<Void> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzbee<Api.zzb, ?> zzbee, @NonNull zzbey<Api.zzb, ?> zzbey) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(8, new zzbed(new zzbap(new zzbef(zzbee, zzbey), taskCompletionSource), this.zzaEi.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x000f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.tasks.Task<java.lang.Void> zza(java.lang.Iterable<? extends com.google.android.gms.common.api.GoogleApi<?>> r5) {
        /*
            r4 = this;
            com.google.android.gms.internal.zzbav r1 = new com.google.android.gms.internal.zzbav
            r1.<init>(r5)
            java.util.Iterator r2 = r5.iterator()
        L_0x0009:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x003a
            java.lang.Object r0 = r2.next()
            com.google.android.gms.common.api.GoogleApi r0 = (com.google.android.gms.common.api.GoogleApi) r0
            java.util.Map<com.google.android.gms.internal.zzbat<?>, com.google.android.gms.internal.zzbdd<?>> r3 = r4.zzaCB
            com.google.android.gms.internal.zzbat r0 = r0.zzph()
            java.lang.Object r0 = r3.get(r0)
            com.google.android.gms.internal.zzbdd r0 = (com.google.android.gms.internal.zzbdd) r0
            if (r0 == 0) goto L_0x0029
            boolean r0 = r0.isConnected()
            if (r0 != 0) goto L_0x0009
        L_0x0029:
            android.os.Handler r0 = r4.mHandler
            android.os.Handler r2 = r4.mHandler
            r3 = 2
            android.os.Message r2 = r2.obtainMessage(r3, r1)
            r0.sendMessage(r2)
            com.google.android.gms.tasks.Task r0 = r1.getTask()
        L_0x0039:
            return r0
        L_0x003a:
            r1.zzpu()
            com.google.android.gms.tasks.Task r0 = r1.getTask()
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbdb.zza(java.lang.Iterable):com.google.android.gms.tasks.Task");
    }

    public final void zza(ConnectionResult connectionResult, int i) {
        if (!zzc(connectionResult, i)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(5, i, 0, connectionResult));
        }
    }

    public final <O extends Api.ApiOptions> void zza(GoogleApi<O> googleApi, int i, zzbay<? extends Result, Api.zzb> zzbay) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, new zzbed(new zzbao(i, zzbay), this.zzaEi.get(), googleApi)));
    }

    public final <O extends Api.ApiOptions, TResult> void zza(GoogleApi<O> googleApi, int i, zzbeq<Api.zzb, TResult> zzbeq, TaskCompletionSource<TResult> taskCompletionSource, zzbem zzbem) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, new zzbed(new zzbaq(i, zzbeq, taskCompletionSource, zzbem), this.zzaEi.get(), googleApi)));
    }

    public final void zza(@NonNull zzbbw zzbbw) {
        synchronized (zzuF) {
            if (this.zzaEj != zzbbw) {
                this.zzaEj = zzbbw;
                this.zzaEk.clear();
                this.zzaEk.addAll(zzbbw.zzpR());
            }
        }
    }

    public final void zzb(GoogleApi<?> googleApi) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, googleApi));
    }

    /* access modifiers changed from: package-private */
    public final void zzb(@NonNull zzbbw zzbbw) {
        synchronized (zzuF) {
            if (this.zzaEj == zzbbw) {
                this.zzaEj = null;
                this.zzaEk.clear();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzc(ConnectionResult connectionResult, int i) {
        return this.zzaBd.zza(this.mContext, connectionResult, i);
    }

    /* access modifiers changed from: package-private */
    public final void zzpl() {
        this.zzaEi.incrementAndGet();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(10));
    }

    public final void zzps() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }

    public final int zzqm() {
        return this.zzaEh.getAndIncrement();
    }
}
