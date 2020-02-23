package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

final class zzcfw extends zzchj {
    static final Pair<String, Long> zzbri = new Pair<>("", 0L);
    /* access modifiers changed from: private */
    public SharedPreferences zzaix;
    public final zzcfz zzbrA = new zzcfz(this, "last_pause_time", 0);
    public final zzcfz zzbrB = new zzcfz(this, "time_active", 0);
    public boolean zzbrC;
    public final zzcga zzbrj = new zzcga(this, "health_monitor", zzcem.zzxK());
    public final zzcfz zzbrk = new zzcfz(this, "last_upload", 0);
    public final zzcfz zzbrl = new zzcfz(this, "last_upload_attempt", 0);
    public final zzcfz zzbrm = new zzcfz(this, "backoff", 0);
    public final zzcfz zzbrn = new zzcfz(this, "last_delete_stale", 0);
    public final zzcfz zzbro = new zzcfz(this, "midnight_offset", 0);
    public final zzcfz zzbrp = new zzcfz(this, "first_open_time", 0);
    public final zzcgb zzbrq = new zzcgb(this, "app_instance_id", (String) null);
    private String zzbrr;
    private boolean zzbrs;
    private long zzbrt;
    private String zzbru;
    private long zzbrv;
    private final Object zzbrw = new Object();
    public final zzcfz zzbrx = new zzcfz(this, "time_before_start", 10000);
    public final zzcfz zzbry = new zzcfz(this, "session_timeout", 1800000);
    public final zzcfy zzbrz = new zzcfy(this, "start_new_session", true);

    zzcfw(zzcgl zzcgl) {
        super(zzcgl);
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final SharedPreferences zzyF() {
        zzjC();
        zzkD();
        return this.zzaix;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void setMeasurementEnabled(boolean z) {
        zzjC();
        zzwF().zzyD().zzj("Setting measurementEnabled", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zzyF().edit();
        edit.putBoolean("measurement_enabled", z);
        edit.apply();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzak(boolean z) {
        zzjC();
        zzwF().zzyD().zzj("Setting useService", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zzyF().edit();
        edit.putBoolean("use_service", z);
        edit.apply();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzal(boolean z) {
        zzjC();
        return zzyF().getBoolean("measurement_enabled", z);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @NonNull
    public final Pair<String, Boolean> zzeb(String str) {
        zzjC();
        long elapsedRealtime = zzkq().elapsedRealtime();
        if (this.zzbrr != null && elapsedRealtime < this.zzbrt) {
            return new Pair<>(this.zzbrr, Boolean.valueOf(this.zzbrs));
        }
        this.zzbrt = elapsedRealtime + zzwH().zza(str, zzcfb.zzbpW);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            if (advertisingIdInfo != null) {
                this.zzbrr = advertisingIdInfo.getId();
                this.zzbrs = advertisingIdInfo.isLimitAdTrackingEnabled();
            }
            if (this.zzbrr == null) {
                this.zzbrr = "";
            }
        } catch (Throwable th) {
            zzwF().zzyC().zzj("Unable to get advertising id", th);
            this.zzbrr = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(this.zzbrr, Boolean.valueOf(this.zzbrs));
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final String zzec(String str) {
        zzjC();
        String str2 = (String) zzeb(str).first;
        MessageDigest zzbE = zzcjl.zzbE("MD5");
        if (zzbE == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzbE.digest(str2.getBytes()))});
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzed(String str) {
        zzjC();
        SharedPreferences.Editor edit = zzyF().edit();
        edit.putString("gmp_app_id", str);
        edit.apply();
    }

    /* access modifiers changed from: package-private */
    public final void zzee(String str) {
        synchronized (this.zzbrw) {
            this.zzbru = str;
            this.zzbrv = zzkq().elapsedRealtime();
        }
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        this.zzaix = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzbrC = this.zzaix.getBoolean("has_been_opened", false);
        if (!this.zzbrC) {
            SharedPreferences.Editor edit = this.zzaix.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final String zzyG() {
        zzjC();
        return zzyF().getString("gmp_app_id", (String) null);
    }

    /* access modifiers changed from: package-private */
    public final String zzyH() {
        String str;
        synchronized (this.zzbrw) {
            str = Math.abs(zzkq().elapsedRealtime() - this.zzbrv) < 1000 ? this.zzbru : null;
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final Boolean zzyI() {
        zzjC();
        if (!zzyF().contains("use_service")) {
            return null;
        }
        return Boolean.valueOf(zzyF().getBoolean("use_service", false));
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzyJ() {
        boolean z = true;
        zzjC();
        zzwF().zzyD().log("Clearing collection preferences.");
        boolean contains = zzyF().contains("measurement_enabled");
        if (contains) {
            z = zzal(true);
        }
        SharedPreferences.Editor edit = zzyF().edit();
        edit.clear();
        edit.apply();
        if (contains) {
            setMeasurementEnabled(z);
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final String zzyK() {
        zzjC();
        String string = zzyF().getString("previous_os_version", (String) null);
        zzwv().zzkD();
        String str = Build.VERSION.RELEASE;
        if (!TextUtils.isEmpty(str) && !str.equals(string)) {
            SharedPreferences.Editor edit = zzyF().edit();
            edit.putString("previous_os_version", str);
            edit.apply();
        }
        return string;
    }
}
