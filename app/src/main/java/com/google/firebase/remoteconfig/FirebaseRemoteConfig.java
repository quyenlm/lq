package com.google.firebase.remoteconfig;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.abe;
import com.google.android.gms.internal.abf;
import com.google.android.gms.internal.abg;
import com.google.android.gms.internal.abh;
import com.google.android.gms.internal.abi;
import com.google.android.gms.internal.abj;
import com.google.android.gms.internal.abk;
import com.google.android.gms.internal.abl;
import com.google.android.gms.internal.abm;
import com.google.android.gms.internal.abn;
import com.google.android.gms.internal.abo;
import com.google.android.gms.internal.abp;
import com.google.android.gms.internal.abq;
import com.google.android.gms.internal.in;
import com.google.android.gms.internal.zzbhb;
import com.google.android.gms.internal.zzbhg;
import com.google.android.gms.internal.zzbhh;
import com.google.android.gms.internal.zzbhs;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FirebaseRemoteConfig {
    public static final boolean DEFAULT_VALUE_FOR_BOOLEAN = false;
    public static final byte[] DEFAULT_VALUE_FOR_BYTE_ARRAY = new byte[0];
    public static final double DEFAULT_VALUE_FOR_DOUBLE = 0.0d;
    public static final long DEFAULT_VALUE_FOR_LONG = 0;
    public static final String DEFAULT_VALUE_FOR_STRING = "";
    public static final int LAST_FETCH_STATUS_FAILURE = 1;
    public static final int LAST_FETCH_STATUS_NO_FETCH_YET = 0;
    public static final int LAST_FETCH_STATUS_SUCCESS = -1;
    public static final int LAST_FETCH_STATUS_THROTTLED = 2;
    public static final int VALUE_SOURCE_DEFAULT = 1;
    public static final int VALUE_SOURCE_REMOTE = 2;
    public static final int VALUE_SOURCE_STATIC = 0;
    private static FirebaseRemoteConfig zzcns;
    private final Context mContext;
    private abh zzcnt;
    private abh zzcnu;
    private abh zzcnv;
    private abk zzcnw;
    private final ReadWriteLock zzcnx;

    private FirebaseRemoteConfig(Context context) {
        this(context, (abh) null, (abh) null, (abh) null, (abk) null);
    }

    private FirebaseRemoteConfig(Context context, abh abh, abh abh2, abh abh3, abk abk) {
        this.zzcnx = new ReentrantReadWriteLock(true);
        this.mContext = context;
        if (abk != null) {
            this.zzcnw = abk;
        } else {
            this.zzcnw = new abk();
        }
        this.zzcnw.zzaL(zzbS(this.mContext));
        if (abh != null) {
            this.zzcnt = abh;
        }
        if (abh2 != null) {
            this.zzcnu = abh2;
        }
        if (abh3 != null) {
            this.zzcnv = abh3;
        }
    }

    public static FirebaseRemoteConfig getInstance() {
        abk abk;
        if (zzcns != null) {
            return zzcns;
        }
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance == null) {
            throw new IllegalStateException("FirebaseApp has not been initialized.");
        }
        Context applicationContext = instance.getApplicationContext();
        if (zzcns == null) {
            abp zzbT = zzbT(applicationContext);
            if (zzbT == null) {
                if (Log.isLoggable("FirebaseRemoteConfig", 3)) {
                    Log.d("FirebaseRemoteConfig", "No persisted config was found. Initializing from scratch.");
                }
                zzcns = new FirebaseRemoteConfig(applicationContext);
            } else {
                if (Log.isLoggable("FirebaseRemoteConfig", 3)) {
                    Log.d("FirebaseRemoteConfig", "Initializing from persisted config.");
                }
                abh zza = zza(zzbT.zzcnX);
                abh zza2 = zza(zzbT.zzcnY);
                abh zza3 = zza(zzbT.zzcnZ);
                abn abn = zzbT.zzcoa;
                if (abn == null) {
                    abk = null;
                } else {
                    abk = new abk();
                    abk.zzce(abn.zzcnS);
                    abk.zzaJ(abn.zzcnT);
                    abk.zzaM(abn.zzcnU);
                }
                if (abk != null) {
                    abk.zzH(zza(zzbT.zzcob));
                }
                zzcns = new FirebaseRemoteConfig(applicationContext, zza, zza2, zza3, abk);
            }
        }
        return zzcns;
    }

    private final void zzKB() {
        this.zzcnx.readLock().lock();
        try {
            zzr(new abg(this.mContext, this.zzcnt, this.zzcnu, this.zzcnv, this.zzcnw));
        } finally {
            this.zzcnx.readLock().unlock();
        }
    }

    private static long zza(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    private static abh zza(abl abl) {
        if (abl == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (abo abo : abl.zzcnO) {
            String str = abo.zzbxU;
            HashMap hashMap2 = new HashMap();
            for (abm abm : abo.zzcnW) {
                hashMap2.put(abm.key, abm.zzcnR);
            }
            hashMap.put(str, hashMap2);
        }
        byte[][] bArr = abl.zzcnP;
        ArrayList arrayList = new ArrayList();
        for (byte[] add : bArr) {
            arrayList.add(add);
        }
        return new abh(hashMap, abl.timestamp, arrayList);
    }

    private static Map<String, abe> zza(abq[] abqArr) {
        HashMap hashMap = new HashMap();
        if (abqArr != null) {
            for (abq abq : abqArr) {
                hashMap.put(abq.zzbxU, new abe(abq.resourceId, abq.zzcod));
            }
        }
        return hashMap;
    }

    private final long zzbS(Context context) {
        try {
            return this.mContext.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(context.getPackageName());
            Log.e("FirebaseRemoteConfig", new StringBuilder(String.valueOf(valueOf).length() + 25).append("Package [").append(valueOf).append("] was not found!").toString());
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0060 A[SYNTHETIC, Splitter:B:31:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0071 A[SYNTHETIC, Splitter:B:38:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.gms.internal.abp zzbT(android.content.Context r5) {
        /*
            r0 = 0
            if (r5 != 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            java.lang.String r1 = "persisted_config"
            java.io.FileInputStream r2 = r5.openFileInput(r1)     // Catch:{ FileNotFoundException -> 0x0034, IOException -> 0x0055, all -> 0x006d }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            r1.<init>()     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            zza((java.io.InputStream) r2, (java.io.OutputStream) r1)     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            byte[] r1 = r1.toByteArray()     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            r3 = 0
            int r4 = r1.length     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            com.google.android.gms.internal.adg r3 = com.google.android.gms.internal.adg.zzb(r1, r3, r4)     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            com.google.android.gms.internal.abp r1 = new com.google.android.gms.internal.abp     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            r1.<init>()     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            r1.zza((com.google.android.gms.internal.adg) r3)     // Catch:{ FileNotFoundException -> 0x0083, IOException -> 0x0081 }
            if (r2 == 0) goto L_0x0029
            r2.close()     // Catch:{ IOException -> 0x002b }
        L_0x0029:
            r0 = r1
            goto L_0x0003
        L_0x002b:
            r0 = move-exception
            java.lang.String r2 = "FirebaseRemoteConfig"
            java.lang.String r3 = "Failed to close persisted config file."
            android.util.Log.e(r2, r3, r0)
            goto L_0x0029
        L_0x0034:
            r1 = move-exception
            r2 = r0
        L_0x0036:
            java.lang.String r3 = "FirebaseRemoteConfig"
            r4 = 3
            boolean r3 = android.util.Log.isLoggable(r3, r4)     // Catch:{ all -> 0x007e }
            if (r3 == 0) goto L_0x0046
            java.lang.String r3 = "FirebaseRemoteConfig"
            java.lang.String r4 = "Persisted config file was not found."
            android.util.Log.d(r3, r4, r1)     // Catch:{ all -> 0x007e }
        L_0x0046:
            if (r2 == 0) goto L_0x0003
            r2.close()     // Catch:{ IOException -> 0x004c }
            goto L_0x0003
        L_0x004c:
            r1 = move-exception
            java.lang.String r2 = "FirebaseRemoteConfig"
            java.lang.String r3 = "Failed to close persisted config file."
            android.util.Log.e(r2, r3, r1)
            goto L_0x0003
        L_0x0055:
            r1 = move-exception
            r2 = r0
        L_0x0057:
            java.lang.String r3 = "FirebaseRemoteConfig"
            java.lang.String r4 = "Cannot initialize from persisted config."
            android.util.Log.e(r3, r4, r1)     // Catch:{ all -> 0x007e }
            if (r2 == 0) goto L_0x0003
            r2.close()     // Catch:{ IOException -> 0x0064 }
            goto L_0x0003
        L_0x0064:
            r1 = move-exception
            java.lang.String r2 = "FirebaseRemoteConfig"
            java.lang.String r3 = "Failed to close persisted config file."
            android.util.Log.e(r2, r3, r1)
            goto L_0x0003
        L_0x006d:
            r1 = move-exception
            r2 = r0
        L_0x006f:
            if (r2 == 0) goto L_0x0074
            r2.close()     // Catch:{ IOException -> 0x0075 }
        L_0x0074:
            throw r1
        L_0x0075:
            r0 = move-exception
            java.lang.String r2 = "FirebaseRemoteConfig"
            java.lang.String r3 = "Failed to close persisted config file."
            android.util.Log.e(r2, r3, r0)
            goto L_0x0074
        L_0x007e:
            r0 = move-exception
            r1 = r0
            goto L_0x006f
        L_0x0081:
            r1 = move-exception
            goto L_0x0057
        L_0x0083:
            r1 = move-exception
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.remoteconfig.FirebaseRemoteConfig.zzbT(android.content.Context):com.google.android.gms.internal.abp");
    }

    private final void zzc(Map<String, Object> map, String str, boolean z) {
        if (str != null) {
            boolean z2 = map == null || map.isEmpty();
            HashMap hashMap = new HashMap();
            if (!z2) {
                for (String next : map.keySet()) {
                    Object obj = map.get(next);
                    if (obj instanceof String) {
                        hashMap.put(next, ((String) obj).getBytes(abj.UTF_8));
                    } else if (obj instanceof Long) {
                        hashMap.put(next, ((Long) obj).toString().getBytes(abj.UTF_8));
                    } else if (obj instanceof Integer) {
                        hashMap.put(next, ((Integer) obj).toString().getBytes(abj.UTF_8));
                    } else if (obj instanceof Double) {
                        hashMap.put(next, ((Double) obj).toString().getBytes(abj.UTF_8));
                    } else if (obj instanceof Float) {
                        hashMap.put(next, ((Float) obj).toString().getBytes(abj.UTF_8));
                    } else if (obj instanceof byte[]) {
                        hashMap.put(next, (byte[]) obj);
                    } else if (obj instanceof Boolean) {
                        hashMap.put(next, ((Boolean) obj).toString().getBytes(abj.UTF_8));
                    } else {
                        throw new IllegalArgumentException("The type of a default value needs to beone of String, Long, Double, Boolean, or byte[].");
                    }
                }
            }
            this.zzcnx.writeLock().lock();
            if (z2) {
                try {
                    if (this.zzcnv != null && this.zzcnv.zzhF(str)) {
                        this.zzcnv.zzh((Map<String, byte[]>) null, str);
                        this.zzcnv.setTimestamp(System.currentTimeMillis());
                    } else {
                        return;
                    }
                } finally {
                    this.zzcnx.writeLock().unlock();
                }
            } else {
                if (this.zzcnv == null) {
                    this.zzcnv = new abh(new HashMap(), System.currentTimeMillis(), (List<byte[]>) null);
                }
                this.zzcnv.zzh(hashMap, str);
                this.zzcnv.setTimestamp(System.currentTimeMillis());
            }
            if (z) {
                this.zzcnw.zzhG(str);
            }
            zzKB();
            this.zzcnx.writeLock().unlock();
        }
    }

    private static void zzr(Runnable runnable) {
        AsyncTask.SERIAL_EXECUTOR.execute(runnable);
    }

    public boolean activateFetched() {
        this.zzcnx.writeLock().lock();
        try {
            if (this.zzcnt == null) {
                return false;
            }
            if (this.zzcnu == null || this.zzcnu.getTimestamp() < this.zzcnt.getTimestamp()) {
                long timestamp = this.zzcnt.getTimestamp();
                this.zzcnu = this.zzcnt;
                this.zzcnu.setTimestamp(System.currentTimeMillis());
                this.zzcnt = new abh((Map<String, Map<String, byte[]>>) null, timestamp, (List<byte[]>) null);
                long zzKI = this.zzcnw.zzKI();
                this.zzcnw.zzaM(in.zza(zzKI, this.zzcnu.zzss()));
                zzr(new abf(this.mContext, this.zzcnu.zzss(), zzKI));
                zzKB();
                this.zzcnx.writeLock().unlock();
                return true;
            }
            this.zzcnx.writeLock().unlock();
            return false;
        } finally {
            this.zzcnx.writeLock().unlock();
        }
    }

    public Task<Void> fetch() {
        return fetch(43200);
    }

    /* JADX INFO: finally extract failed */
    public Task<Void> fetch(long j) {
        int i = Integer.MAX_VALUE;
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzcnx.readLock().lock();
        try {
            zzbhg zzbhg = new zzbhg();
            zzbhg.zzA(j);
            if (this.zzcnw.isDeveloperModeEnabled()) {
                zzbhg.zzA("_rcn_developer", "true");
            }
            zzbhg.zzaE(10300);
            if (!(this.zzcnu == null || this.zzcnu.getTimestamp() == -1)) {
                long convert = TimeUnit.SECONDS.convert(System.currentTimeMillis() - this.zzcnu.getTimestamp(), TimeUnit.MILLISECONDS);
                zzbhg.zzaG(convert < 2147483647L ? (int) convert : Integer.MAX_VALUE);
            }
            if (!(this.zzcnt == null || this.zzcnt.getTimestamp() == -1)) {
                long convert2 = TimeUnit.SECONDS.convert(System.currentTimeMillis() - this.zzcnt.getTimestamp(), TimeUnit.MILLISECONDS);
                if (convert2 < 2147483647L) {
                    i = (int) convert2;
                }
                zzbhg.zzaF(i);
            }
            zzbhb.zzaKl.zza(new zzbhs(this.mContext).zzpi(), zzbhg.zzsr()).setResultCallback(new zza(this, taskCompletionSource));
            this.zzcnx.readLock().unlock();
            return taskCompletionSource.getTask();
        } catch (Throwable th) {
            this.zzcnx.readLock().unlock();
            throw th;
        }
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, "configns:firebase");
    }

    /* JADX INFO: finally extract failed */
    public boolean getBoolean(String str, String str2) {
        if (str2 == null) {
            return false;
        }
        this.zzcnx.readLock().lock();
        try {
            if (this.zzcnu != null && this.zzcnu.zzaj(str, str2)) {
                String str3 = new String(this.zzcnu.zzak(str, str2), abj.UTF_8);
                if (abj.zzaKs.matcher(str3).matches()) {
                    this.zzcnx.readLock().unlock();
                    return true;
                } else if (abj.zzaKt.matcher(str3).matches()) {
                    this.zzcnx.readLock().unlock();
                    return false;
                }
            }
            if (this.zzcnv != null && this.zzcnv.zzaj(str, str2)) {
                String str4 = new String(this.zzcnv.zzak(str, str2), abj.UTF_8);
                if (abj.zzaKs.matcher(str4).matches()) {
                    this.zzcnx.readLock().unlock();
                    return true;
                } else if (abj.zzaKt.matcher(str4).matches()) {
                    this.zzcnx.readLock().unlock();
                    return false;
                }
            }
            this.zzcnx.readLock().unlock();
            return false;
        } catch (Throwable th) {
            this.zzcnx.readLock().unlock();
            throw th;
        }
    }

    public byte[] getByteArray(String str) {
        return getByteArray(str, "configns:firebase");
    }

    public byte[] getByteArray(String str, String str2) {
        if (str2 == null) {
            return DEFAULT_VALUE_FOR_BYTE_ARRAY;
        }
        this.zzcnx.readLock().lock();
        if (this.zzcnu == null || !this.zzcnu.zzaj(str, str2)) {
            try {
                if (this.zzcnv != null && this.zzcnv.zzaj(str, str2)) {
                    return this.zzcnv.zzak(str, str2);
                }
                byte[] bArr = DEFAULT_VALUE_FOR_BYTE_ARRAY;
                this.zzcnx.readLock().unlock();
                return bArr;
            } finally {
                this.zzcnx.readLock().unlock();
            }
        } else {
            byte[] zzak = this.zzcnu.zzak(str, str2);
            this.zzcnx.readLock().unlock();
            return zzak;
        }
    }

    public double getDouble(String str) {
        return getDouble(str, "configns:firebase");
    }

    public double getDouble(String str, String str2) {
        double d = DEFAULT_VALUE_FOR_DOUBLE;
        if (str2 != null) {
            this.zzcnx.readLock().lock();
            try {
                if (this.zzcnu != null && this.zzcnu.zzaj(str, str2)) {
                    try {
                        d = Double.valueOf(new String(this.zzcnu.zzak(str, str2), abj.UTF_8)).doubleValue();
                    } catch (NumberFormatException e) {
                    }
                }
                if (this.zzcnv != null && this.zzcnv.zzaj(str, str2)) {
                    try {
                        d = Double.valueOf(new String(this.zzcnv.zzak(str, str2), abj.UTF_8)).doubleValue();
                        this.zzcnx.readLock().unlock();
                    } catch (NumberFormatException e2) {
                    }
                }
                this.zzcnx.readLock().unlock();
            } finally {
                this.zzcnx.readLock().unlock();
            }
        }
        return d;
    }

    public FirebaseRemoteConfigInfo getInfo() {
        abi abi = new abi();
        this.zzcnx.readLock().lock();
        try {
            abi.zzaK(this.zzcnt == null ? -1 : this.zzcnt.getTimestamp());
            abi.zzce(this.zzcnw.getLastFetchStatus());
            abi.setConfigSettings(new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(this.zzcnw.isDeveloperModeEnabled()).build());
            return abi;
        } finally {
            this.zzcnx.readLock().unlock();
        }
    }

    public Set<String> getKeysByPrefix(String str) {
        return getKeysByPrefix(str, "configns:firebase");
    }

    public Set<String> getKeysByPrefix(String str, String str2) {
        Set<String> zzal;
        this.zzcnx.readLock().lock();
        try {
            if (this.zzcnu == null) {
                zzal = new TreeSet<>();
            } else {
                zzal = this.zzcnu.zzal(str, str2);
                this.zzcnx.readLock().unlock();
            }
            return zzal;
        } finally {
            this.zzcnx.readLock().unlock();
        }
    }

    public long getLong(String str) {
        return getLong(str, "configns:firebase");
    }

    public long getLong(String str, String str2) {
        long j = 0;
        if (str2 != null) {
            this.zzcnx.readLock().lock();
            try {
                if (this.zzcnu != null && this.zzcnu.zzaj(str, str2)) {
                    try {
                        j = Long.valueOf(new String(this.zzcnu.zzak(str, str2), abj.UTF_8)).longValue();
                    } catch (NumberFormatException e) {
                    }
                }
                if (this.zzcnv != null && this.zzcnv.zzaj(str, str2)) {
                    try {
                        j = Long.valueOf(new String(this.zzcnv.zzak(str, str2), abj.UTF_8)).longValue();
                        this.zzcnx.readLock().unlock();
                    } catch (NumberFormatException e2) {
                    }
                }
                this.zzcnx.readLock().unlock();
            } finally {
                this.zzcnx.readLock().unlock();
            }
        }
        return j;
    }

    public String getString(String str) {
        return getString(str, "configns:firebase");
    }

    public String getString(String str, String str2) {
        if (str2 == null) {
            return "";
        }
        this.zzcnx.readLock().lock();
        try {
            if (this.zzcnu != null && this.zzcnu.zzaj(str, str2)) {
                return new String(this.zzcnu.zzak(str, str2), abj.UTF_8);
            }
            if (this.zzcnv == null || !this.zzcnv.zzaj(str, str2)) {
                this.zzcnx.readLock().unlock();
                return "";
            }
            String str3 = new String(this.zzcnv.zzak(str, str2), abj.UTF_8);
            this.zzcnx.readLock().unlock();
            return str3;
        } finally {
            this.zzcnx.readLock().unlock();
        }
    }

    public FirebaseRemoteConfigValue getValue(String str) {
        return getValue(str, "configns:firebase");
    }

    public FirebaseRemoteConfigValue getValue(String str, String str2) {
        if (str2 == null) {
            return new abj(DEFAULT_VALUE_FOR_BYTE_ARRAY, 0);
        }
        this.zzcnx.readLock().lock();
        if (this.zzcnu == null || !this.zzcnu.zzaj(str, str2)) {
            try {
                if (this.zzcnv != null && this.zzcnv.zzaj(str, str2)) {
                    return new abj(this.zzcnv.zzak(str, str2), 1);
                }
                abj abj = new abj(DEFAULT_VALUE_FOR_BYTE_ARRAY, 0);
                this.zzcnx.readLock().unlock();
                return abj;
            } finally {
                this.zzcnx.readLock().unlock();
            }
        } else {
            abj abj2 = new abj(this.zzcnu.zzak(str, str2), 2);
            this.zzcnx.readLock().unlock();
            return abj2;
        }
    }

    public void setConfigSettings(FirebaseRemoteConfigSettings firebaseRemoteConfigSettings) {
        this.zzcnx.writeLock().lock();
        try {
            boolean isDeveloperModeEnabled = this.zzcnw.isDeveloperModeEnabled();
            boolean isDeveloperModeEnabled2 = firebaseRemoteConfigSettings == null ? false : firebaseRemoteConfigSettings.isDeveloperModeEnabled();
            this.zzcnw.zzaJ(isDeveloperModeEnabled2);
            if (isDeveloperModeEnabled != isDeveloperModeEnabled2) {
                zzKB();
            }
        } finally {
            this.zzcnx.writeLock().unlock();
        }
    }

    public void setDefaults(int i) {
        setDefaults(i, "configns:firebase");
    }

    public void setDefaults(int i, String str) {
        if (str != null) {
            this.zzcnx.readLock().lock();
            try {
                if (!(this.zzcnw == null || this.zzcnw.zzKG() == null || this.zzcnw.zzKG().get(str) == null)) {
                    abe abe = this.zzcnw.zzKG().get(str);
                    if (i == abe.zzKC() && this.zzcnw.zzKH() == abe.zzKD()) {
                        if (Log.isLoggable("FirebaseRemoteConfig", 3)) {
                            Log.d("FirebaseRemoteConfig", "Skipped setting defaults from resource file as this resource file was already applied.");
                        }
                        return;
                    }
                }
                this.zzcnx.readLock().unlock();
                HashMap hashMap = new HashMap();
                try {
                    XmlResourceParser xml = this.mContext.getResources().getXml(i);
                    String str2 = null;
                    String str3 = null;
                    String str4 = null;
                    for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                        if (eventType == 2) {
                            str4 = xml.getName();
                        } else if (eventType == 3) {
                            if (!(!"entry".equals(xml.getName()) || str3 == null || str2 == null)) {
                                hashMap.put(str3, str2);
                                str2 = null;
                                str3 = null;
                            }
                            str4 = null;
                        } else if (eventType == 4) {
                            if ("key".equals(str4)) {
                                str3 = xml.getText();
                            } else if ("value".equals(str4)) {
                                str2 = xml.getText();
                            }
                        }
                    }
                    this.zzcnw.zza(str, new abe(i, this.zzcnw.zzKH()));
                    zzc(hashMap, str, false);
                } catch (Exception e) {
                    Log.e("FirebaseRemoteConfig", "Caught exception while parsing XML resource. Skipping setDefaults.", e);
                }
            } finally {
                this.zzcnx.readLock().unlock();
            }
        } else if (Log.isLoggable("FirebaseRemoteConfig", 3)) {
            Log.d("FirebaseRemoteConfig", "namespace cannot be null for setDefaults.");
        }
    }

    public void setDefaults(Map<String, Object> map) {
        setDefaults(map, "configns:firebase");
    }

    public void setDefaults(Map<String, Object> map, String str) {
        zzc(map, str, true);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final void zza(TaskCompletionSource<Void> taskCompletionSource, zzbhh zzbhh) {
        if (zzbhh == null || zzbhh.getStatus() == null) {
            this.zzcnw.zzce(1);
            taskCompletionSource.setException(new FirebaseRemoteConfigFetchException());
            zzKB();
            return;
        }
        int statusCode = zzbhh.getStatus().getStatusCode();
        this.zzcnx.writeLock().lock();
        switch (statusCode) {
            case -6508:
            case -6506:
                this.zzcnw.zzce(-1);
                if (this.zzcnt != null && !this.zzcnt.zzKF()) {
                    Map<String, Set<String>> zzst = zzbhh.zzst();
                    HashMap hashMap = new HashMap();
                    for (String next : zzst.keySet()) {
                        HashMap hashMap2 = new HashMap();
                        for (String str : zzst.get(next)) {
                            hashMap2.put(str, zzbhh.zza(str, (byte[]) null, next));
                        }
                        hashMap.put(next, hashMap2);
                    }
                    this.zzcnt = new abh(hashMap, this.zzcnt.getTimestamp(), zzbhh.zzss());
                }
                taskCompletionSource.setResult(null);
                zzKB();
                break;
            case -6505:
                Map<String, Set<String>> zzst2 = zzbhh.zzst();
                HashMap hashMap3 = new HashMap();
                for (String next2 : zzst2.keySet()) {
                    HashMap hashMap4 = new HashMap();
                    for (String str2 : zzst2.get(next2)) {
                        hashMap4.put(str2, zzbhh.zza(str2, (byte[]) null, next2));
                    }
                    hashMap3.put(next2, hashMap4);
                }
                this.zzcnt = new abh(hashMap3, System.currentTimeMillis(), zzbhh.zzss());
                this.zzcnw.zzce(-1);
                taskCompletionSource.setResult(null);
                zzKB();
                break;
            case 6500:
            case GamesStatusCodes.STATUS_MATCH_ERROR_INACTIVE_MATCH:
            case GamesStatusCodes.STATUS_MATCH_ERROR_OUT_OF_DATE_VERSION:
            case GamesStatusCodes.STATUS_MATCH_ERROR_INVALID_MATCH_RESULTS:
                this.zzcnw.zzce(1);
                taskCompletionSource.setException(new FirebaseRemoteConfigFetchException());
                zzKB();
                break;
            case GamesStatusCodes.STATUS_MATCH_ERROR_INVALID_MATCH_STATE:
            case GamesStatusCodes.STATUS_MATCH_ERROR_LOCALLY_MODIFIED:
                this.zzcnw.zzce(2);
                taskCompletionSource.setException(new FirebaseRemoteConfigFetchThrottledException(zzbhh.getThrottleEndTimeMillis()));
                zzKB();
                break;
            default:
                try {
                    if (zzbhh.getStatus().isSuccess()) {
                        Log.w("FirebaseRemoteConfig", new StringBuilder(45).append("Unknown (successful) status code: ").append(statusCode).toString());
                    }
                    this.zzcnw.zzce(1);
                    taskCompletionSource.setException(new FirebaseRemoteConfigFetchException());
                    zzKB();
                    break;
                } catch (Throwable th) {
                    this.zzcnx.writeLock().unlock();
                    throw th;
                }
        }
        this.zzcnx.writeLock().unlock();
    }
}
