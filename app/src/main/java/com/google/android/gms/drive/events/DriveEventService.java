package com.google.android.gms.drive.events;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import com.google.android.gms.common.util.zzw;
import com.google.android.gms.internal.zzbng;
import com.google.android.gms.internal.zzbor;
import com.google.android.gms.internal.zzbph;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DriveEventService extends Service implements ChangeListener, CompletionListener, zzd, zzi {
    public static final String ACTION_HANDLE_EVENT = "com.google.android.gms.drive.events.HANDLE_EVENT";
    private final String mName;
    private int zzaGG;
    /* access modifiers changed from: private */
    public CountDownLatch zzaNe;
    zza zzaNf;
    boolean zzaNg;

    final class zza extends Handler {
        zza() {
        }

        /* access modifiers changed from: private */
        public final Message zzb(zzbph zzbph) {
            return obtainMessage(1, zzbph);
        }

        /* access modifiers changed from: private */
        public final Message zzta() {
            return obtainMessage(2);
        }

        public final void handleMessage(Message message) {
            zzbng.zzx("DriveEventService", new StringBuilder(38).append("handleMessage message type:").append(message.what).toString());
            switch (message.what) {
                case 1:
                    DriveEventService.this.zza((zzbph) message.obj);
                    return;
                case 2:
                    getLooper().quit();
                    return;
                default:
                    zzbng.zzy("DriveEventService", new StringBuilder(35).append("Unexpected message type:").append(message.what).toString());
                    return;
            }
        }
    }

    final class zzb extends zzbor {
        zzb() {
        }

        public final void zzc(zzbph zzbph) throws RemoteException {
            synchronized (DriveEventService.this) {
                String valueOf = String.valueOf(zzbph);
                zzbng.zzx("DriveEventService", new StringBuilder(String.valueOf(valueOf).length() + 9).append("onEvent: ").append(valueOf).toString());
                DriveEventService.this.zzsZ();
                if (DriveEventService.this.zzaNf != null) {
                    DriveEventService.this.zzaNf.sendMessage(DriveEventService.this.zzaNf.zzb(zzbph));
                } else {
                    zzbng.zzz("DriveEventService", "Receiving event before initialize is completed.");
                }
            }
        }
    }

    protected DriveEventService() {
        this("DriveEventService");
    }

    protected DriveEventService(String str) {
        this.zzaNg = false;
        this.zzaGG = -1;
        this.mName = str;
    }

    /* access modifiers changed from: private */
    public final void zza(zzbph zzbph) {
        DriveEvent zztj = zzbph.zztj();
        String valueOf = String.valueOf(zztj);
        zzbng.zzx("DriveEventService", new StringBuilder(String.valueOf(valueOf).length() + 20).append("handleEventMessage: ").append(valueOf).toString());
        try {
            switch (zztj.getType()) {
                case 1:
                    onChange((ChangeEvent) zztj);
                    return;
                case 2:
                    onCompletion((CompletionEvent) zztj);
                    return;
                case 4:
                    zza((zzb) zztj);
                    return;
                case 7:
                    String str = this.mName;
                    String valueOf2 = String.valueOf((zzr) zztj);
                    zzbng.zzy(str, new StringBuilder(String.valueOf(valueOf2).length() + 32).append("Unhandled transfer state event: ").append(valueOf2).toString());
                    return;
                default:
                    String str2 = this.mName;
                    String valueOf3 = String.valueOf(zztj);
                    zzbng.zzy(str2, new StringBuilder(String.valueOf(valueOf3).length() + 17).append("Unhandled event: ").append(valueOf3).toString());
                    return;
            }
        } catch (Exception e) {
            String str3 = this.mName;
            String valueOf4 = String.valueOf(zztj);
            zzbng.zza(str3, e, new StringBuilder(String.valueOf(valueOf4).length() + 22).append("Error handling event: ").append(valueOf4).toString());
        }
        String str32 = this.mName;
        String valueOf42 = String.valueOf(zztj);
        zzbng.zza(str32, e, new StringBuilder(String.valueOf(valueOf42).length() + 22).append("Error handling event: ").append(valueOf42).toString());
    }

    /* access modifiers changed from: private */
    public final void zzsZ() throws SecurityException {
        int callingUid = getCallingUid();
        if (callingUid != this.zzaGG) {
            if (zzw.zzf(this, callingUid)) {
                this.zzaGG = callingUid;
                return;
            }
            throw new SecurityException("Caller is not GooglePlayServices");
        }
    }

    /* access modifiers changed from: protected */
    public int getCallingUid() {
        return Binder.getCallingUid();
    }

    public final synchronized IBinder onBind(Intent intent) {
        IBinder iBinder;
        if (ACTION_HANDLE_EVENT.equals(intent.getAction())) {
            if (this.zzaNf == null && !this.zzaNg) {
                this.zzaNg = true;
                CountDownLatch countDownLatch = new CountDownLatch(1);
                this.zzaNe = new CountDownLatch(1);
                new zzh(this, countDownLatch).start();
                try {
                    if (!countDownLatch.await(Constants.ACTIVE_THREAD_WATCHDOG, TimeUnit.MILLISECONDS)) {
                        zzbng.zzz("DriveEventService", "Failed to synchronously initialize event handler.");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException("Unable to start event handler", e);
                }
            }
            iBinder = new zzb().asBinder();
        } else {
            iBinder = null;
        }
        return iBinder;
    }

    public void onChange(ChangeEvent changeEvent) {
        String str = this.mName;
        String valueOf = String.valueOf(changeEvent);
        zzbng.zzy(str, new StringBuilder(String.valueOf(valueOf).length() + 24).append("Unhandled change event: ").append(valueOf).toString());
    }

    public void onCompletion(CompletionEvent completionEvent) {
        String str = this.mName;
        String valueOf = String.valueOf(completionEvent);
        zzbng.zzy(str, new StringBuilder(String.valueOf(valueOf).length() + 28).append("Unhandled completion event: ").append(valueOf).toString());
    }

    public synchronized void onDestroy() {
        zzbng.zzx("DriveEventService", "onDestroy");
        if (this.zzaNf != null) {
            this.zzaNf.sendMessage(this.zzaNf.zzta());
            this.zzaNf = null;
            try {
                if (!this.zzaNe.await(Constants.ACTIVE_THREAD_WATCHDOG, TimeUnit.MILLISECONDS)) {
                    zzbng.zzy("DriveEventService", "Failed to synchronously quit event handler. Will quit itself");
                }
            } catch (InterruptedException e) {
            }
            this.zzaNe = null;
        }
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        return true;
    }

    public final void zza(zzb zzb2) {
        String str = this.mName;
        String valueOf = String.valueOf(zzb2);
        zzbng.zzy(str, new StringBuilder(String.valueOf(valueOf).length() + 35).append("Unhandled changes available event: ").append(valueOf).toString());
    }
}
