package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.HashMap;

final class zzag extends zzae implements Handler.Callback {
    /* access modifiers changed from: private */
    public final Context mApplicationContext;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    /* access modifiers changed from: private */
    public final HashMap<zzaf, zzah> zzaHP = new HashMap<>();
    /* access modifiers changed from: private */
    public final zza zzaHQ;
    private final long zzaHR;
    /* access modifiers changed from: private */
    public final long zzaHS;

    zzag(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.zzaHQ = zza.zzrU();
        this.zzaHR = Constants.ACTIVE_THREAD_WATCHDOG;
        this.zzaHS = 300000;
    }

    public final boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                synchronized (this.zzaHP) {
                    zzaf zzaf = (zzaf) message.obj;
                    zzah zzah = this.zzaHP.get(zzaf);
                    if (zzah != null && zzah.zzrC()) {
                        if (zzah.isBound()) {
                            zzah.zzcC("GmsClientSupervisor");
                        }
                        this.zzaHP.remove(zzaf);
                    }
                }
                return true;
            case 1:
                synchronized (this.zzaHP) {
                    zzaf zzaf2 = (zzaf) message.obj;
                    zzah zzah2 = this.zzaHP.get(zzaf2);
                    if (zzah2 != null && zzah2.getState() == 3) {
                        String valueOf = String.valueOf(zzaf2);
                        Log.wtf("GmsClientSupervisor", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Timeout waiting for ServiceConnection callback ").append(valueOf).toString(), new Exception());
                        ComponentName componentName = zzah2.getComponentName();
                        if (componentName == null) {
                            componentName = zzaf2.getComponentName();
                        }
                        zzah2.onServiceDisconnected(componentName == null ? new ComponentName(zzaf2.getPackage(), "unknown") : componentName);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzaf zzaf, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        zzbo.zzb(serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.zzaHP) {
            zzah zzah = this.zzaHP.get(zzaf);
            if (zzah != null) {
                this.mHandler.removeMessages(0, zzaf);
                if (!zzah.zza(serviceConnection)) {
                    zzah.zza(serviceConnection, str);
                    switch (zzah.getState()) {
                        case 1:
                            serviceConnection.onServiceConnected(zzah.getComponentName(), zzah.getBinder());
                            break;
                        case 2:
                            zzah.zzcB(str);
                            break;
                    }
                } else {
                    String valueOf = String.valueOf(zzaf);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append(valueOf).toString());
                }
            } else {
                zzah = new zzah(this, zzaf);
                zzah.zza(serviceConnection, str);
                zzah.zzcB(str);
                this.zzaHP.put(zzaf, zzah);
            }
            isBound = zzah.isBound();
        }
        return isBound;
    }

    /* access modifiers changed from: protected */
    public final void zzb(zzaf zzaf, ServiceConnection serviceConnection, String str) {
        zzbo.zzb(serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.zzaHP) {
            zzah zzah = this.zzaHP.get(zzaf);
            if (zzah == null) {
                String valueOf = String.valueOf(zzaf);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Nonexistent connection status for service config: ").append(valueOf).toString());
            } else if (!zzah.zza(serviceConnection)) {
                String valueOf2 = String.valueOf(zzaf);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf2).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append(valueOf2).toString());
            } else {
                zzah.zzb(serviceConnection, str);
                if (zzah.zzrC()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, zzaf), this.zzaHR);
                }
            }
        }
    }
}
