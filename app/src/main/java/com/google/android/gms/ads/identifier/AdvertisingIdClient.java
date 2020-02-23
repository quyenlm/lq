package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzo;
import com.google.android.gms.internal.zzfd;
import com.google.android.gms.internal.zzfe;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@KeepForSdkWithMembers
public class AdvertisingIdClient {
    private final Context mContext;
    private Object zzsA;
    @Nullable
    private zza zzsB;
    private long zzsC;
    @Nullable
    private com.google.android.gms.common.zza zzsx;
    @Nullable
    private zzfd zzsy;
    private boolean zzsz;

    public static final class Info {
        private final String zzsI;
        private final boolean zzsJ;

        public Info(String str, boolean z) {
            this.zzsI = str;
            this.zzsJ = z;
        }

        public final String getId() {
            return this.zzsI;
        }

        public final boolean isLimitAdTrackingEnabled() {
            return this.zzsJ;
        }

        public final String toString() {
            String str = this.zzsI;
            return new StringBuilder(String.valueOf(str).length() + 7).append("{").append(str).append("}").append(this.zzsJ).toString();
        }
    }

    static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzsE;
        private long zzsF;
        CountDownLatch zzsG = new CountDownLatch(1);
        boolean zzsH = false;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzsE = new WeakReference<>(advertisingIdClient);
            this.zzsF = j;
            start();
        }

        private final void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient) this.zzsE.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzsH = true;
            }
        }

        public final void run() {
            try {
                if (!this.zzsG.await(this.zzsF, TimeUnit.MILLISECONDS)) {
                    disconnect();
                }
            } catch (InterruptedException e) {
                disconnect();
            }
        }
    }

    public AdvertisingIdClient(Context context) {
        this(context, NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS, false);
    }

    public AdvertisingIdClient(Context context, long j, boolean z) {
        this.zzsA = new Object();
        zzbo.zzu(context);
        if (z) {
            Context applicationContext = context.getApplicationContext();
            this.mContext = applicationContext != null ? applicationContext : context;
        } else {
            this.mContext = context;
        }
        this.zzsz = false;
        this.zzsC = j;
    }

    @Nullable
    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        float f = 0.0f;
        boolean z = false;
        try {
            Context remoteContext = zzo.getRemoteContext(context);
            if (remoteContext != null) {
                SharedPreferences sharedPreferences = remoteContext.getSharedPreferences("google_ads_flags", 0);
                z = sharedPreferences.getBoolean("gads:ad_id_app_context:enabled", false);
                f = sharedPreferences.getFloat("gads:ad_id_app_context:ping_ratio", 0.0f);
            }
        } catch (Exception e) {
            Log.w("AdvertisingIdClient", "Error while reading from SharedPreferences ", e);
        }
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1, z);
        try {
            advertisingIdClient.start(false);
            Info info = advertisingIdClient.getInfo();
            advertisingIdClient.zza(info, z, f, (Throwable) null);
            advertisingIdClient.finish();
            return info;
        } catch (Throwable th) {
            advertisingIdClient.finish();
            throw th;
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {
    }

    private final void start(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzbo.zzcG("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzsz) {
                finish();
            }
            this.zzsx = zzd(this.mContext);
            this.zzsy = zza(this.mContext, this.zzsx);
            this.zzsz = true;
            if (z) {
                zzaj();
            }
        }
    }

    private static zzfd zza(Context context, com.google.android.gms.common.zza zza2) throws IOException {
        try {
            return zzfe.zzc(zza2.zza(10000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    private final void zza(Info info, boolean z, float f, Throwable th) {
        if (Math.random() <= ((double) f)) {
            Bundle bundle = new Bundle();
            bundle.putString("app_context", z ? "1" : "0");
            if (info != null) {
                bundle.putString("limit_ad_tracking", info.isLimitAdTrackingEnabled() ? "1" : "0");
            }
            if (!(info == null || info.getId() == null)) {
                bundle.putString("ad_id_size", Integer.toString(info.getId().length()));
            }
            if (th != null) {
                bundle.putString("error", th.getClass().getName());
            }
            Uri.Builder buildUpon = Uri.parse("https://pagead2.googlesyndication.com/pagead/gen_204?id=gmob-apps").buildUpon();
            for (String str : bundle.keySet()) {
                buildUpon.appendQueryParameter(str, bundle.getString(str));
            }
            new zza(this, buildUpon.build().toString()).start();
        }
    }

    private final void zzaj() {
        synchronized (this.zzsA) {
            if (this.zzsB != null) {
                this.zzsB.zzsG.countDown();
                try {
                    this.zzsB.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzsC > 0) {
                this.zzsB = new zza(this, this.zzsC);
            }
        }
    }

    private static com.google.android.gms.common.zza zzd(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            switch (zze.zzoW().isGooglePlayServicesAvailable(context)) {
                case 0:
                case 2:
                    com.google.android.gms.common.zza zza2 = new com.google.android.gms.common.zza();
                    Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                    intent.setPackage("com.google.android.gms");
                    try {
                        if (com.google.android.gms.common.stats.zza.zzrU().zza(context, intent, zza2, 1)) {
                            return zza2;
                        }
                        throw new IOException("Connection failure");
                    } catch (Throwable th) {
                        throw new IOException(th);
                    }
                default:
                    throw new IOException("Google Play services not available");
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void finish() {
        /*
            r3 = this;
            java.lang.String r0 = "Calling this from your main thread can lead to deadlock"
            com.google.android.gms.common.internal.zzbo.zzcG(r0)
            monitor-enter(r3)
            android.content.Context r0 = r3.mContext     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x000e
            com.google.android.gms.common.zza r0 = r3.zzsx     // Catch:{ all -> 0x0029 }
            if (r0 != 0) goto L_0x0010
        L_0x000e:
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
        L_0x000f:
            return
        L_0x0010:
            boolean r0 = r3.zzsz     // Catch:{ IllegalArgumentException -> 0x002c, Throwable -> 0x0035 }
            if (r0 == 0) goto L_0x001e
            com.google.android.gms.common.stats.zza.zzrU()     // Catch:{ IllegalArgumentException -> 0x002c, Throwable -> 0x0035 }
            android.content.Context r0 = r3.mContext     // Catch:{ IllegalArgumentException -> 0x002c, Throwable -> 0x0035 }
            com.google.android.gms.common.zza r1 = r3.zzsx     // Catch:{ IllegalArgumentException -> 0x002c, Throwable -> 0x0035 }
            r0.unbindService(r1)     // Catch:{ IllegalArgumentException -> 0x002c, Throwable -> 0x0035 }
        L_0x001e:
            r0 = 0
            r3.zzsz = r0     // Catch:{ all -> 0x0029 }
            r0 = 0
            r3.zzsy = r0     // Catch:{ all -> 0x0029 }
            r0 = 0
            r3.zzsx = r0     // Catch:{ all -> 0x0029 }
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            goto L_0x000f
        L_0x0029:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            throw r0
        L_0x002c:
            r0 = move-exception
            java.lang.String r1 = "AdvertisingIdClient"
            java.lang.String r2 = "AdvertisingIdClient unbindService failed."
            android.util.Log.i(r1, r2, r0)     // Catch:{ all -> 0x0029 }
            goto L_0x001e
        L_0x0035:
            r0 = move-exception
            java.lang.String r1 = "AdvertisingIdClient"
            java.lang.String r2 = "AdvertisingIdClient unbindService failed."
            android.util.Log.i(r1, r2, r0)     // Catch:{ all -> 0x0029 }
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.AdvertisingIdClient.finish():void");
    }

    public Info getInfo() throws IOException {
        Info info;
        zzbo.zzcG("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzsz) {
                synchronized (this.zzsA) {
                    if (this.zzsB == null || !this.zzsB.zzsH) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    start(false);
                    if (!this.zzsz) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (RemoteException e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Exception e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzbo.zzu(this.zzsx);
            zzbo.zzu(this.zzsy);
            info = new Info(this.zzsy.getId(), this.zzsy.zzb(true));
        }
        zzaj();
        return info;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        start(true);
    }
}
