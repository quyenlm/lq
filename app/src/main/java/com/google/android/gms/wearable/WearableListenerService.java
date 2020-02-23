package com.google.android.gms.wearable;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.zzw;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.internal.zzaa;
import com.google.android.gms.wearable.internal.zzai;
import com.google.android.gms.wearable.internal.zzdl;
import com.google.android.gms.wearable.internal.zzdx;
import com.google.android.gms.wearable.internal.zzeg;
import com.google.android.gms.wearable.internal.zzgh;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import java.util.List;

public class WearableListenerService extends Service implements CapabilityApi.CapabilityListener, ChannelApi.ChannelListener, DataApi.DataListener, MessageApi.MessageListener, NodeApi.NodeListener {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private IBinder zzaHj;
    /* access modifiers changed from: private */
    public ComponentName zzbRq;
    /* access modifiers changed from: private */
    public zzb zzbRr;
    /* access modifiers changed from: private */
    public Intent zzbRs;
    private Looper zzbRt;
    /* access modifiers changed from: private */
    public final Object zzbRu = new Object();
    /* access modifiers changed from: private */
    public boolean zzbRv;

    class zza implements ServiceConnection {
        private zza(WearableListenerService wearableListenerService) {
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    final class zzb extends Handler {
        private boolean started;
        private final zza zzbRw = new zza();

        zzb(Looper looper) {
            super(looper);
        }

        @SuppressLint({"UntrackedBindService"})
        private final synchronized void zzDW() {
            if (!this.started) {
                if (Log.isLoggable("WearableLS", 2)) {
                    String valueOf = String.valueOf(WearableListenerService.this.zzbRq);
                    Log.v("WearableLS", new StringBuilder(String.valueOf(valueOf).length() + 13).append("bindService: ").append(valueOf).toString());
                }
                WearableListenerService.this.bindService(WearableListenerService.this.zzbRs, this.zzbRw, 1);
                this.started = true;
            }
        }

        @SuppressLint({"UntrackedBindService"})
        private final synchronized void zzgk(String str) {
            if (this.started) {
                if (Log.isLoggable("WearableLS", 2)) {
                    String valueOf = String.valueOf(WearableListenerService.this.zzbRq);
                    Log.v("WearableLS", new StringBuilder(String.valueOf(str).length() + 17 + String.valueOf(valueOf).length()).append("unbindService: ").append(str).append(", ").append(valueOf).toString());
                }
                try {
                    WearableListenerService.this.unbindService(this.zzbRw);
                } catch (RuntimeException e) {
                    Log.e("WearableLS", "Exception when unbinding from local service", e);
                }
                this.started = false;
            }
            return;
        }

        public final void dispatchMessage(Message message) {
            String str;
            zzDW();
            try {
                super.dispatchMessage(message);
            } finally {
                if (!hasMessages(0)) {
                    str = "dispatch";
                    zzgk(str);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public final void quit() {
            getLooper().quit();
            zzgk("quit");
        }
    }

    final class zzc extends zzdl {
        private volatile int zzbRy;

        private zzc() {
            this.zzbRy = -1;
        }

        private final boolean zza(Runnable runnable, String str, Object obj) {
            boolean z;
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", String.format("%s: %s %s", new Object[]{str, WearableListenerService.this.zzbRq.toString(), obj}));
            }
            int callingUid = Binder.getCallingUid();
            if (callingUid == this.zzbRy) {
                z = true;
            } else if (zzgh.zzbz(WearableListenerService.this).zzgm("com.google.android.wearable.app.cn") && zzw.zzb(WearableListenerService.this, callingUid, "com.google.android.wearable.app.cn")) {
                this.zzbRy = callingUid;
                z = true;
            } else if (zzw.zzf(WearableListenerService.this, callingUid)) {
                this.zzbRy = callingUid;
                z = true;
            } else {
                Log.e("WearableLS", new StringBuilder(57).append("Caller is not GooglePlayServices; caller UID: ").append(callingUid).toString());
                z = false;
            }
            if (!z) {
                return false;
            }
            synchronized (WearableListenerService.this.zzbRu) {
                if (WearableListenerService.this.zzbRv) {
                    return false;
                }
                WearableListenerService.this.zzbRr.post(runnable);
                return true;
            }
        }

        public final void onConnectedNodes(List<zzeg> list) {
            zza(new zzp(this, list), "onConnectedNodes", list);
        }

        public final void zzS(DataHolder dataHolder) {
            zzl zzl = new zzl(this, dataHolder);
            try {
                String valueOf = String.valueOf(dataHolder);
                if (zza(zzl, "onDataItemChanged", new StringBuilder(String.valueOf(valueOf).length() + 18).append(valueOf).append(", rows=").append(dataHolder.getCount()).toString())) {
                }
            } finally {
                dataHolder.close();
            }
        }

        public final void zza(zzaa zzaa) {
            zza(new zzq(this, zzaa), "onConnectedCapabilityChanged", zzaa);
        }

        public final void zza(zzai zzai) {
            zza(new zzt(this, zzai), "onChannelEvent", zzai);
        }

        public final void zza(zzdx zzdx) {
            zza(new zzm(this, zzdx), "onMessageReceived", zzdx);
        }

        public final void zza(zzeg zzeg) {
            zza(new zzn(this, zzeg), "onPeerConnected", zzeg);
        }

        public final void zza(zzi zzi) {
            zza(new zzs(this, zzi), "onEntityUpdate", zzi);
        }

        public final void zza(zzl zzl) {
            zza(new zzr(this, zzl), "onNotificationReceived", zzl);
        }

        public final void zzb(zzeg zzeg) {
            zza(new zzo(this, zzeg), "onPeerDisconnected", zzeg);
        }
    }

    public Looper getLooper() {
        if (this.zzbRt == null) {
            HandlerThread handlerThread = new HandlerThread("WearableListenerService");
            handlerThread.start();
            this.zzbRt = handlerThread.getLooper();
        }
        return this.zzbRt;
    }

    public final IBinder onBind(Intent intent) {
        if (BIND_LISTENER_INTENT_ACTION.equals(intent.getAction())) {
            return this.zzaHj;
        }
        return null;
    }

    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
    }

    public void onChannelClosed(Channel channel, int i, int i2) {
    }

    public void onChannelOpened(Channel channel) {
    }

    public void onConnectedNodes(List<Node> list) {
    }

    public void onCreate() {
        super.onCreate();
        this.zzbRq = new ComponentName(this, getClass().getName());
        if (Log.isLoggable("WearableLS", 3)) {
            String valueOf = String.valueOf(this.zzbRq);
            Log.d("WearableLS", new StringBuilder(String.valueOf(valueOf).length() + 10).append("onCreate: ").append(valueOf).toString());
        }
        this.zzbRr = new zzb(getLooper());
        this.zzbRs = new Intent(BIND_LISTENER_INTENT_ACTION);
        this.zzbRs.setComponent(this.zzbRq);
        this.zzaHj = new zzc();
    }

    public void onDataChanged(DataEventBuffer dataEventBuffer) {
    }

    public void onDestroy() {
        if (Log.isLoggable("WearableLS", 3)) {
            String valueOf = String.valueOf(this.zzbRq);
            Log.d("WearableLS", new StringBuilder(String.valueOf(valueOf).length() + 11).append("onDestroy: ").append(valueOf).toString());
        }
        synchronized (this.zzbRu) {
            this.zzbRv = true;
            if (this.zzbRr == null) {
                String valueOf2 = String.valueOf(this.zzbRq);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf2).length() + 111).append("onDestroy: mServiceHandler not set, did you override onCreate() but forget to call super.onCreate()? component=").append(valueOf2).toString());
            }
            this.zzbRr.quit();
        }
        super.onDestroy();
    }

    public void onEntityUpdate(zzb zzb2) {
    }

    public void onInputClosed(Channel channel, int i, int i2) {
    }

    public void onMessageReceived(MessageEvent messageEvent) {
    }

    public void onNotificationReceived(zzd zzd) {
    }

    public void onOutputClosed(Channel channel, int i, int i2) {
    }

    public void onPeerConnected(Node node) {
    }

    public void onPeerDisconnected(Node node) {
    }
}
