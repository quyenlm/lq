package com.google.android.gms.gcm;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.applinks.AppLinkData;
import com.garena.android.gpns.strategy.CompetitiveBootStrategy;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzw;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    /* access modifiers changed from: private */
    public ComponentName componentName;
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    private final Set<String> zzbfE = new HashSet();
    private int zzbfF;
    private Messenger zzbfG;
    private ExecutorService zzqF;

    @TargetApi(21)
    class zza extends Handler {
        zza(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            Messenger messenger;
            if (!zzw.zzb(GcmTaskService.this, message.sendingUid, "com.google.android.gms")) {
                Log.e("GcmTaskService", "unable to verify presence of Google Play Services");
                return;
            }
            switch (message.what) {
                case 1:
                    Bundle data = message.getData();
                    if (data != null && (messenger = message.replyTo) != null) {
                        GcmTaskService.this.zza(new zzb(data.getString(IMFriendInfoExViber.TAG), messenger, data.getBundle(AppLinkData.ARGUMENTS_EXTRAS_KEY), (List<Uri>) data.getParcelableArrayList("triggered_uris")));
                        return;
                    }
                    return;
                case 2:
                    if (Log.isLoggable("GcmTaskService", 3)) {
                        String valueOf = String.valueOf(message);
                        Log.d("GcmTaskService", new StringBuilder(String.valueOf(valueOf).length() + 45).append("ignoring unimplemented stop message for now: ").append(valueOf).toString());
                        return;
                    }
                    return;
                case 4:
                    GcmTaskService.this.onInitializeTasks();
                    return;
                default:
                    String valueOf2 = String.valueOf(message);
                    Log.e("GcmTaskService", new StringBuilder(String.valueOf(valueOf2).length() + 31).append("Unrecognized message received: ").append(valueOf2).toString());
                    return;
            }
        }
    }

    class zzb implements Runnable {
        private final Bundle mExtras;
        @Nullable
        private final Messenger mMessenger;
        private final String mTag;
        private final List<Uri> zzbfJ;
        @Nullable
        private final zzd zzbfK;

        zzb(String str, IBinder iBinder, Bundle bundle, List<Uri> list) {
            zzd zze;
            this.mTag = str;
            if (iBinder == null) {
                zze = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.gcm.INetworkTaskCallback");
                zze = queryLocalInterface instanceof zzd ? (zzd) queryLocalInterface : new zze(iBinder);
            }
            this.zzbfK = zze;
            this.mExtras = bundle;
            this.zzbfJ = list;
            this.mMessenger = null;
        }

        zzb(String str, Messenger messenger, Bundle bundle, List<Uri> list) {
            this.mTag = str;
            this.mMessenger = messenger;
            this.mExtras = bundle;
            this.zzbfJ = list;
            this.zzbfK = null;
        }

        /* access modifiers changed from: private */
        public final void zzbg(int i) {
            synchronized (GcmTaskService.this.lock) {
                try {
                    if (zzvC()) {
                        Messenger messenger = this.mMessenger;
                        Message obtain = Message.obtain();
                        obtain.what = 3;
                        obtain.arg1 = i;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(CompetitiveBootStrategy.INTENT_COMPONENT_NAME, GcmTaskService.this.componentName);
                        bundle.putString(IMFriendInfoExViber.TAG, this.mTag);
                        obtain.setData(bundle);
                        messenger.send(obtain);
                    } else {
                        this.zzbfK.zzbh(i);
                    }
                    if (!zzvC()) {
                        GcmTaskService.this.zzdp(this.mTag);
                    }
                } catch (RemoteException e) {
                    String valueOf = String.valueOf(this.mTag);
                    Log.e("GcmTaskService", valueOf.length() != 0 ? "Error reporting result of operation to scheduler for ".concat(valueOf) : new String("Error reporting result of operation to scheduler for "));
                    if (!zzvC()) {
                        GcmTaskService.this.zzdp(this.mTag);
                    }
                } catch (Throwable th) {
                    if (!zzvC()) {
                        GcmTaskService.this.zzdp(this.mTag);
                    }
                    throw th;
                }
            }
        }

        private final boolean zzvC() {
            return this.mMessenger != null;
        }

        public final void run() {
            zzbg(GcmTaskService.this.onRunTask(new TaskParams(this.mTag, this.mExtras, this.zzbfJ)));
        }
    }

    /* access modifiers changed from: private */
    public final void zza(zzb zzb2) {
        try {
            this.zzqF.execute(zzb2);
        } catch (RejectedExecutionException e) {
            Log.e("GcmTaskService", "Executor is shutdown. onDestroy was called but main looper had an unprocessed start task message. The task will be retried with backoff delay.", e);
            zzb2.zzbg(1);
        }
    }

    private final void zzbf(int i) {
        synchronized (this.lock) {
            this.zzbfF = i;
            if (this.zzbfE.isEmpty()) {
                stopSelf(this.zzbfF);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zzdp(String str) {
        synchronized (this.lock) {
            this.zzbfE.remove(str);
            if (this.zzbfE.isEmpty()) {
                stopSelf(this.zzbfF);
            }
        }
    }

    @CallSuper
    public IBinder onBind(Intent intent) {
        if (intent == null || !zzq.zzse() || !SERVICE_ACTION_EXECUTE_TASK.equals(intent.getAction())) {
            return null;
        }
        return this.zzbfG.getBinder();
    }

    @CallSuper
    public void onCreate() {
        super.onCreate();
        this.zzqF = Executors.newFixedThreadPool(2, new zzb(this));
        this.zzbfG = new Messenger(new zza(Looper.getMainLooper()));
        this.componentName = new ComponentName(this, getClass());
    }

    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        List<Runnable> shutdownNow = this.zzqF.shutdownNow();
        if (!shutdownNow.isEmpty()) {
            Log.e("GcmTaskService", new StringBuilder(79).append("Shutting down, but not all tasks are finished executing. Remaining: ").append(shutdownNow.size()).toString());
        }
    }

    public void onInitializeTasks() {
    }

    public abstract int onRunTask(TaskParams taskParams);

    @CallSuper
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            zzbf(i2);
        } else {
            try {
                intent.setExtrasClassLoader(PendingCallback.class.getClassLoader());
                String action = intent.getAction();
                if (SERVICE_ACTION_EXECUTE_TASK.equals(action)) {
                    String stringExtra = intent.getStringExtra(IMFriendInfoExViber.TAG);
                    Parcelable parcelableExtra = intent.getParcelableExtra("callback");
                    Bundle bundleExtra = intent.getBundleExtra(AppLinkData.ARGUMENTS_EXTRAS_KEY);
                    ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("triggered_uris");
                    if (!(parcelableExtra instanceof PendingCallback)) {
                        String valueOf = String.valueOf(getPackageName());
                        Log.e("GcmTaskService", new StringBuilder(String.valueOf(valueOf).length() + 47 + String.valueOf(stringExtra).length()).append(valueOf).append(" ").append(stringExtra).append(": Could not process request, invalid callback.").toString());
                    } else {
                        synchronized (this.lock) {
                            if (!this.zzbfE.add(stringExtra)) {
                                String valueOf2 = String.valueOf(getPackageName());
                                Log.w("GcmTaskService", new StringBuilder(String.valueOf(valueOf2).length() + 44 + String.valueOf(stringExtra).length()).append(valueOf2).append(" ").append(stringExtra).append(": Task already running, won't start another").toString());
                                zzbf(i2);
                            } else {
                                zza(new zzb(stringExtra, ((PendingCallback) parcelableExtra).zzaHj, bundleExtra, (List<Uri>) parcelableArrayListExtra));
                            }
                        }
                    }
                } else if (SERVICE_ACTION_INITIALIZE.equals(action)) {
                    onInitializeTasks();
                } else {
                    Log.e("GcmTaskService", new StringBuilder(String.valueOf(action).length() + 37).append("Unknown action received ").append(action).append(", terminating").toString());
                }
                zzbf(i2);
            } finally {
                zzbf(i2);
            }
        }
        return 2;
    }
}
