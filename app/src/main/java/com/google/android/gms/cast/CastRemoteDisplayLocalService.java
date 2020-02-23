package com.google.android.gms.cast;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.media.MediaRouter;
import android.text.TextUtils;
import android.view.Display;
import com.google.android.gms.R;
import com.google.android.gms.cast.CastRemoteDisplay;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.zzayo;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(19)
public abstract class CastRemoteDisplayLocalService extends Service {
    /* access modifiers changed from: private */
    public static CastRemoteDisplayLocalService zzapH;
    /* access modifiers changed from: private */
    public static final zzayo zzapq = new zzayo("CastRemoteDisplayLocalService");
    private static final int zzapr = R.id.cast_notification_id;
    /* access modifiers changed from: private */
    public static final Object zzaps = new Object();
    /* access modifiers changed from: private */
    public static AtomicBoolean zzapt = new AtomicBoolean(false);
    private Handler mHandler;
    private Notification mNotification;
    /* access modifiers changed from: private */
    public Display zzPO;
    private String zzaoM;
    private PendingIntent zzapA;
    /* access modifiers changed from: private */
    public CastDevice zzapB;
    /* access modifiers changed from: private */
    public Context zzapC;
    /* access modifiers changed from: private */
    public ServiceConnection zzapD;
    private MediaRouter zzapE;
    /* access modifiers changed from: private */
    public boolean zzapF = false;
    private final MediaRouter.Callback zzapG = new zzp(this);
    private final IBinder zzapI = new zza(this, (zzp) null);
    private GoogleApiClient zzapu;
    private CastRemoteDisplay.CastRemoteDisplaySessionCallbacks zzapv;
    private Callbacks zzapw;
    private zzb zzapx;
    private NotificationSettings zzapy;
    private boolean zzapz;

    public interface Callbacks {
        void onRemoteDisplaySessionError(Status status);

        void onRemoteDisplaySessionStarted(CastRemoteDisplayLocalService castRemoteDisplayLocalService);

        void onServiceCreated(CastRemoteDisplayLocalService castRemoteDisplayLocalService);
    }

    public static final class NotificationSettings {
        /* access modifiers changed from: private */
        public Notification mNotification;
        /* access modifiers changed from: private */
        public PendingIntent zzapQ;
        /* access modifiers changed from: private */
        public String zzapR;
        /* access modifiers changed from: private */
        public String zzapS;

        public static final class Builder {
            private NotificationSettings zzapT = new NotificationSettings((zzp) null);

            public final NotificationSettings build() {
                if (this.zzapT.mNotification != null) {
                    if (!TextUtils.isEmpty(this.zzapT.zzapR)) {
                        throw new IllegalArgumentException("notificationTitle requires using the default notification");
                    } else if (!TextUtils.isEmpty(this.zzapT.zzapS)) {
                        throw new IllegalArgumentException("notificationText requires using the default notification");
                    } else if (this.zzapT.zzapQ != null) {
                        throw new IllegalArgumentException("notificationPendingIntent requires using the default notification");
                    }
                } else if (TextUtils.isEmpty(this.zzapT.zzapR) && TextUtils.isEmpty(this.zzapT.zzapS) && this.zzapT.zzapQ == null) {
                    throw new IllegalArgumentException("At least an argument must be provided");
                }
                return this.zzapT;
            }

            public final Builder setNotification(Notification notification) {
                Notification unused = this.zzapT.mNotification = notification;
                return this;
            }

            public final Builder setNotificationPendingIntent(PendingIntent pendingIntent) {
                PendingIntent unused = this.zzapT.zzapQ = pendingIntent;
                return this;
            }

            public final Builder setNotificationText(String str) {
                String unused = this.zzapT.zzapS = str;
                return this;
            }

            public final Builder setNotificationTitle(String str) {
                String unused = this.zzapT.zzapR = str;
                return this;
            }
        }

        private NotificationSettings() {
        }

        private NotificationSettings(NotificationSettings notificationSettings) {
            this.mNotification = notificationSettings.mNotification;
            this.zzapQ = notificationSettings.zzapQ;
            this.zzapR = notificationSettings.zzapR;
            this.zzapS = notificationSettings.zzapS;
        }

        /* synthetic */ NotificationSettings(NotificationSettings notificationSettings, zzp zzp) {
            this(notificationSettings);
        }

        /* synthetic */ NotificationSettings(zzp zzp) {
            this();
        }
    }

    public static class Options {
        @CastRemoteDisplay.Configuration
        int zzapn = 2;

        public int getConfigPreset() {
            return this.zzapn;
        }

        public void setConfigPreset(@CastRemoteDisplay.Configuration int i) {
            this.zzapn = i;
        }
    }

    class zza extends Binder {
        private zza() {
        }

        /* synthetic */ zza(CastRemoteDisplayLocalService castRemoteDisplayLocalService, zzp zzp) {
            this();
        }
    }

    static final class zzb extends BroadcastReceiver {
        private zzb() {
        }

        /* synthetic */ zzb(zzp zzp) {
            this();
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT")) {
                CastRemoteDisplayLocalService.zzapq.zzb("disconnecting", new Object[0]);
                CastRemoteDisplayLocalService.stopService();
            }
        }
    }

    public static CastRemoteDisplayLocalService getInstance() {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService;
        synchronized (zzaps) {
            castRemoteDisplayLocalService = zzapH;
        }
        return castRemoteDisplayLocalService;
    }

    protected static void setDebugEnabled() {
        zzapq.zzaa(true);
    }

    public static void startService(Context context, Class<? extends CastRemoteDisplayLocalService> cls, String str, CastDevice castDevice, NotificationSettings notificationSettings, Callbacks callbacks) {
        startServiceWithOptions(context, cls, str, castDevice, new Options(), notificationSettings, callbacks);
    }

    public static void startServiceWithOptions(@NonNull Context context, @NonNull Class<? extends CastRemoteDisplayLocalService> cls, @NonNull String str, @NonNull CastDevice castDevice, @NonNull Options options, @NonNull NotificationSettings notificationSettings, @NonNull Callbacks callbacks) {
        zzapq.zzb("Starting Service", new Object[0]);
        synchronized (zzaps) {
            if (zzapH != null) {
                zzapq.zzf("An existing service had not been stopped before starting one", new Object[0]);
                zzR(true);
            }
        }
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context, cls), 128);
            if (serviceInfo == null || !serviceInfo.exported) {
                zzbo.zzb(context, (Object) "activityContext is required.");
                zzbo.zzb(cls, (Object) "serviceClass is required.");
                zzbo.zzb(str, (Object) "applicationId is required.");
                zzbo.zzb(castDevice, (Object) "device is required.");
                zzbo.zzb(options, (Object) "options is required.");
                zzbo.zzb(notificationSettings, (Object) "notificationSettings is required.");
                zzbo.zzb(callbacks, (Object) "callbacks is required.");
                if (notificationSettings.mNotification == null && notificationSettings.zzapQ == null) {
                    throw new IllegalArgumentException("notificationSettings: Either the notification or the notificationPendingIntent must be provided");
                } else if (zzapt.getAndSet(true)) {
                    zzapq.zzc("Service is already being started, startService has been called twice", new Object[0]);
                } else {
                    Intent intent = new Intent(context, cls);
                    context.startService(intent);
                    context.bindService(intent, new zzs(str, castDevice, options, notificationSettings, context, callbacks), 64);
                }
            } else {
                throw new IllegalStateException("The service must not be exported, verify the manifest configuration");
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("Service not found, did you forget to configure it in the manifest?");
        }
    }

    public static void stopService() {
        zzR(false);
    }

    /* access modifiers changed from: private */
    public final void zzQ(boolean z) {
        zzbp("Stopping Service");
        zzbo.zzcz("stopServiceInstanceInternal must be called on the main thread");
        if (!z && this.zzapE != null) {
            zzbp("Setting default route");
            this.zzapE.selectRoute(this.zzapE.getDefaultRoute());
        }
        if (this.zzapx != null) {
            zzbp("Unregistering notification receiver");
            unregisterReceiver(this.zzapx);
        }
        zzbp("stopRemoteDisplaySession");
        zzbp("stopRemoteDisplay");
        if (this.zzapu == null || !this.zzapu.isConnected()) {
            zzapq.zzc("Unable to stop the remote display as the API client is not ready", new Object[0]);
        } else {
            CastRemoteDisplay.CastRemoteDisplayApi.stopRemoteDisplay(this.zzapu).setResultCallback(new zzx(this));
        }
        onDismissPresentation();
        zzbp("Stopping the remote display Service");
        stopForeground(true);
        stopSelf();
        if (this.zzapE != null) {
            zzbo.zzcz("CastRemoteDisplayLocalService calls must be done on the main thread");
            zzbp("removeMediaRouterCallback");
            this.zzapE.removeCallback(this.zzapG);
        }
        if (this.zzapu != null) {
            this.zzapu.disconnect();
            this.zzapu = null;
        }
        if (!(this.zzapC == null || this.zzapD == null)) {
            try {
                this.zzapC.unbindService(this.zzapD);
            } catch (IllegalArgumentException e) {
                zzbp("No need to unbind service, already unbound");
            }
            this.zzapD = null;
            this.zzapC = null;
        }
        this.zzaoM = null;
        this.zzapu = null;
        this.mNotification = null;
        this.zzPO = null;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        if (r0.mHandler == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0034, code lost:
        if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        r0.mHandler.post(new com.google.android.gms.cast.zzt(r0, r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
        r0.zzQ(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void zzR(boolean r4) {
        /*
            r3 = 0
            com.google.android.gms.internal.zzayo r0 = zzapq
            java.lang.String r1 = "Stopping Service"
            java.lang.Object[] r2 = new java.lang.Object[r3]
            r0.zzb(r1, r2)
            java.util.concurrent.atomic.AtomicBoolean r0 = zzapt
            r0.set(r3)
            java.lang.Object r1 = zzaps
            monitor-enter(r1)
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = zzapH     // Catch:{ all -> 0x0041 }
            if (r0 != 0) goto L_0x0022
            com.google.android.gms.internal.zzayo r0 = zzapq     // Catch:{ all -> 0x0041 }
            java.lang.String r2 = "Service is already being stopped"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0041 }
            r0.zzc(r2, r3)     // Catch:{ all -> 0x0041 }
            monitor-exit(r1)     // Catch:{ all -> 0x0041 }
        L_0x0021:
            return
        L_0x0022:
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = zzapH     // Catch:{ all -> 0x0041 }
            r2 = 0
            zzapH = r2     // Catch:{ all -> 0x0041 }
            monitor-exit(r1)     // Catch:{ all -> 0x0041 }
            android.os.Handler r1 = r0.mHandler
            if (r1 == 0) goto L_0x0021
            android.os.Looper r1 = android.os.Looper.myLooper()
            android.os.Looper r2 = android.os.Looper.getMainLooper()
            if (r1 == r2) goto L_0x0044
            android.os.Handler r1 = r0.mHandler
            com.google.android.gms.cast.zzt r2 = new com.google.android.gms.cast.zzt
            r2.<init>(r0, r4)
            r1.post(r2)
            goto L_0x0021
        L_0x0041:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0041 }
            throw r0
        L_0x0044:
            r0.zzQ(r4)
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastRemoteDisplayLocalService.zzR(boolean):void");
    }

    private final Notification zzS(boolean z) {
        int i;
        int i2;
        zzbp("createDefaultNotification");
        String zzc = this.zzapy.zzapR;
        String zzd = this.zzapy.zzapS;
        if (z) {
            i = R.string.cast_notification_connected_message;
            i2 = R.drawable.cast_ic_notification_on;
        } else {
            i = R.string.cast_notification_connecting_message;
            i2 = R.drawable.cast_ic_notification_connecting;
        }
        NotificationCompat.Builder ongoing = new NotificationCompat.Builder(this).setContentTitle(TextUtils.isEmpty(zzc) ? (String) getPackageManager().getApplicationLabel(getApplicationInfo()) : zzc).setContentText(TextUtils.isEmpty(zzd) ? getString(i, new Object[]{this.zzapB.getFriendlyName()}) : zzd).setContentIntent(this.zzapy.zzapQ).setSmallIcon(i2).setOngoing(true);
        String string = getString(R.string.cast_notification_disconnect);
        if (this.zzapA == null) {
            Intent intent = new Intent("com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT");
            intent.setPackage(this.zzapC.getPackageName());
            this.zzapA = PendingIntent.getBroadcast(this, 0, intent, DriveFile.MODE_READ_ONLY);
        }
        return ongoing.addAction(17301560, string, this.zzapA).build();
    }

    /* access modifiers changed from: private */
    public final void zza(Display display) {
        this.zzPO = display;
        if (this.zzapz) {
            this.mNotification = zzS(true);
            startForeground(zzapr, this.mNotification);
        }
        if (this.zzapw != null) {
            this.zzapw.onRemoteDisplaySessionStarted(this);
            this.zzapw = null;
        }
        onCreatePresentation(this.zzPO);
    }

    /* access modifiers changed from: private */
    public final void zza(NotificationSettings notificationSettings) {
        zzbo.zzcz("updateNotificationSettingsInternal must be called on the main thread");
        if (this.zzapy == null) {
            throw new IllegalStateException("No current notification settings to update");
        }
        if (!this.zzapz) {
            zzbo.zzb(notificationSettings.mNotification, (Object) "notification is required.");
            this.mNotification = notificationSettings.mNotification;
            Notification unused = this.zzapy.mNotification = this.mNotification;
        } else if (notificationSettings.mNotification != null) {
            throw new IllegalStateException("Current mode is default notification, notification attribute must not be provided");
        } else {
            if (notificationSettings.zzapQ != null) {
                PendingIntent unused2 = this.zzapy.zzapQ = notificationSettings.zzapQ;
            }
            if (!TextUtils.isEmpty(notificationSettings.zzapR)) {
                String unused3 = this.zzapy.zzapR = notificationSettings.zzapR;
            }
            if (!TextUtils.isEmpty(notificationSettings.zzapS)) {
                String unused4 = this.zzapy.zzapS = notificationSettings.zzapS;
            }
            this.mNotification = zzS(true);
        }
        startForeground(zzapr, this.mNotification);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0084, code lost:
        if (com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings.zzb(r7.zzapy) != null) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0086, code lost:
        r7.zzapz = true;
        r7.mNotification = zzS(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x008e, code lost:
        startForeground(zzapr, r7.mNotification);
        r0 = new com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplayOptions.Builder(r9, r7.zzapv);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x009c, code lost:
        if (r10 == null) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x009e, code lost:
        r0.setConfigPreset(r10.zzapn);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00a3, code lost:
        r7.zzapu = new com.google.android.gms.common.api.GoogleApiClient.Builder(r7, new com.google.android.gms.cast.zzy(r7), new com.google.android.gms.cast.zzq(r7)).addApi(com.google.android.gms.cast.CastRemoteDisplay.API, r0.build()).build();
        r7.zzapu.connect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00c9, code lost:
        if (r7.zzapw == null) goto L_0x00d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00cb, code lost:
        r7.zzapw.onServiceCreated(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00d6, code lost:
        r7.zzapz = false;
        r7.mNotification = com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings.zzb(r7.zzapy);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        r7.zzapw = r14;
        r7.zzaoM = r8;
        r7.zzapB = r9;
        r7.zzapC = r12;
        r7.zzapD = r13;
        r7.zzapE = android.support.v7.media.MediaRouter.getInstance(getApplicationContext());
        r2 = new android.support.v7.media.MediaRouteSelector.Builder().addControlCategory(com.google.android.gms.cast.CastMediaControlIntent.categoryForCast(r7.zzaoM)).build();
        zzbp("addMediaRouterCallback");
        r7.zzapE.addCallback(r2, r7.zzapG, 4);
        r7.zzapv = new com.google.android.gms.cast.zzv(r7);
        r7.mNotification = com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings.zzb(r11);
        r7.zzapx = new com.google.android.gms.cast.CastRemoteDisplayLocalService.zzb((com.google.android.gms.cast.zzp) null);
        registerReceiver(r7.zzapx, new android.content.IntentFilter("com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT"));
        r7.zzapy = new com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings(r11, (com.google.android.gms.cast.zzp) null);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(java.lang.String r8, com.google.android.gms.cast.CastDevice r9, com.google.android.gms.cast.CastRemoteDisplayLocalService.Options r10, com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings r11, android.content.Context r12, android.content.ServiceConnection r13, com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks r14) {
        /*
            r7 = this;
            r6 = 0
            r1 = 1
            r0 = 0
            java.lang.String r2 = "startRemoteDisplaySession"
            r7.zzbp(r2)
            java.lang.String r2 = "Starting the Cast Remote Display must be done on the main thread"
            com.google.android.gms.common.internal.zzbo.zzcz(r2)
            java.lang.Object r2 = zzaps
            monitor-enter(r2)
            com.google.android.gms.cast.CastRemoteDisplayLocalService r3 = zzapH     // Catch:{ all -> 0x00d3 }
            if (r3 == 0) goto L_0x0020
            com.google.android.gms.internal.zzayo r1 = zzapq     // Catch:{ all -> 0x00d3 }
            java.lang.String r3 = "An existing service had not been stopped before starting one"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00d3 }
            r1.zzf(r3, r4)     // Catch:{ all -> 0x00d3 }
            monitor-exit(r2)     // Catch:{ all -> 0x00d3 }
        L_0x001f:
            return r0
        L_0x0020:
            zzapH = r7     // Catch:{ all -> 0x00d3 }
            monitor-exit(r2)     // Catch:{ all -> 0x00d3 }
            r7.zzapw = r14
            r7.zzaoM = r8
            r7.zzapB = r9
            r7.zzapC = r12
            r7.zzapD = r13
            android.content.Context r2 = r7.getApplicationContext()
            android.support.v7.media.MediaRouter r2 = android.support.v7.media.MediaRouter.getInstance(r2)
            r7.zzapE = r2
            android.support.v7.media.MediaRouteSelector$Builder r2 = new android.support.v7.media.MediaRouteSelector$Builder
            r2.<init>()
            java.lang.String r3 = r7.zzaoM
            java.lang.String r3 = com.google.android.gms.cast.CastMediaControlIntent.categoryForCast((java.lang.String) r3)
            android.support.v7.media.MediaRouteSelector$Builder r2 = r2.addControlCategory(r3)
            android.support.v7.media.MediaRouteSelector r2 = r2.build()
            java.lang.String r3 = "addMediaRouterCallback"
            r7.zzbp(r3)
            android.support.v7.media.MediaRouter r3 = r7.zzapE
            android.support.v7.media.MediaRouter$Callback r4 = r7.zzapG
            r5 = 4
            r3.addCallback(r2, r4, r5)
            com.google.android.gms.cast.zzv r2 = new com.google.android.gms.cast.zzv
            r2.<init>(r7)
            r7.zzapv = r2
            android.app.Notification r2 = r11.mNotification
            r7.mNotification = r2
            com.google.android.gms.cast.CastRemoteDisplayLocalService$zzb r2 = new com.google.android.gms.cast.CastRemoteDisplayLocalService$zzb
            r2.<init>(r6)
            r7.zzapx = r2
            com.google.android.gms.cast.CastRemoteDisplayLocalService$zzb r2 = r7.zzapx
            android.content.IntentFilter r3 = new android.content.IntentFilter
            java.lang.String r4 = "com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT"
            r3.<init>(r4)
            r7.registerReceiver(r2, r3)
            com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings r2 = new com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings
            r2.<init>(r11, r6)
            r7.zzapy = r2
            com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings r2 = r7.zzapy
            android.app.Notification r2 = r2.mNotification
            if (r2 != 0) goto L_0x00d6
            r7.zzapz = r1
            android.app.Notification r0 = r7.zzS(r0)
            r7.mNotification = r0
        L_0x008e:
            int r0 = zzapr
            android.app.Notification r2 = r7.mNotification
            r7.startForeground(r0, r2)
            com.google.android.gms.cast.CastRemoteDisplay$CastRemoteDisplayOptions$Builder r0 = new com.google.android.gms.cast.CastRemoteDisplay$CastRemoteDisplayOptions$Builder
            com.google.android.gms.cast.CastRemoteDisplay$CastRemoteDisplaySessionCallbacks r2 = r7.zzapv
            r0.<init>(r9, r2)
            if (r10 == 0) goto L_0x00a3
            int r2 = r10.zzapn
            r0.setConfigPreset(r2)
        L_0x00a3:
            com.google.android.gms.cast.zzy r2 = new com.google.android.gms.cast.zzy
            r2.<init>(r7)
            com.google.android.gms.cast.zzq r3 = new com.google.android.gms.cast.zzq
            r3.<init>(r7)
            com.google.android.gms.common.api.GoogleApiClient$Builder r4 = new com.google.android.gms.common.api.GoogleApiClient$Builder
            r4.<init>(r7, r2, r3)
            com.google.android.gms.common.api.Api<com.google.android.gms.cast.CastRemoteDisplay$CastRemoteDisplayOptions> r2 = com.google.android.gms.cast.CastRemoteDisplay.API
            com.google.android.gms.cast.CastRemoteDisplay$CastRemoteDisplayOptions r0 = r0.build()
            com.google.android.gms.common.api.GoogleApiClient$Builder r0 = r4.addApi(r2, r0)
            com.google.android.gms.common.api.GoogleApiClient r0 = r0.build()
            r7.zzapu = r0
            com.google.android.gms.common.api.GoogleApiClient r0 = r7.zzapu
            r0.connect()
            com.google.android.gms.cast.CastRemoteDisplayLocalService$Callbacks r0 = r7.zzapw
            if (r0 == 0) goto L_0x00d0
            com.google.android.gms.cast.CastRemoteDisplayLocalService$Callbacks r0 = r7.zzapw
            r0.onServiceCreated(r7)
        L_0x00d0:
            r0 = r1
            goto L_0x001f
        L_0x00d3:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00d3 }
            throw r0
        L_0x00d6:
            r7.zzapz = r0
            com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings r0 = r7.zzapy
            android.app.Notification r0 = r0.mNotification
            r7.mNotification = r0
            goto L_0x008e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastRemoteDisplayLocalService.zza(java.lang.String, com.google.android.gms.cast.CastDevice, com.google.android.gms.cast.CastRemoteDisplayLocalService$Options, com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings, android.content.Context, android.content.ServiceConnection, com.google.android.gms.cast.CastRemoteDisplayLocalService$Callbacks):boolean");
    }

    /* access modifiers changed from: private */
    public final void zzbp(String str) {
        zzapq.zzb("[Instance: %s] %s", this, str);
    }

    /* access modifiers changed from: private */
    public final void zzbs(String str) {
        zzapq.zzc("[Instance: %s] %s", this, str);
    }

    /* access modifiers changed from: private */
    public final void zznd() {
        zzbp("startRemoteDisplay");
        if (this.zzapu == null || !this.zzapu.isConnected()) {
            zzapq.zzc("Unable to start the remote display as the API client is not ready", new Object[0]);
        } else {
            CastRemoteDisplay.CastRemoteDisplayApi.startRemoteDisplay(this.zzapu, this.zzaoM).setResultCallback(new zzw(this));
        }
    }

    /* access modifiers changed from: private */
    public final void zzne() {
        if (this.zzapw != null) {
            this.zzapw.onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_SERVICE_CREATION_FAILED));
            this.zzapw = null;
        }
        stopService();
    }

    /* access modifiers changed from: protected */
    public Display getDisplay() {
        return this.zzPO;
    }

    public IBinder onBind(Intent intent) {
        zzbp("onBind");
        return this.zzapI;
    }

    public void onCreate() {
        zzbp("onCreate");
        super.onCreate();
        this.mHandler = new Handler(getMainLooper());
        this.mHandler.postDelayed(new zzr(this), 100);
    }

    public abstract void onCreatePresentation(Display display);

    public abstract void onDismissPresentation();

    public int onStartCommand(Intent intent, int i, int i2) {
        zzbp("onStartCommand");
        this.zzapF = true;
        return 2;
    }

    public void updateNotificationSettings(NotificationSettings notificationSettings) {
        zzbo.zzb(notificationSettings, (Object) "notificationSettings is required.");
        zzbo.zzb(this.mHandler, (Object) "Service is not ready yet.");
        this.mHandler.post(new zzu(this, notificationSettings));
    }
}
