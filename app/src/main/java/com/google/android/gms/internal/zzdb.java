package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.zze;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zzdb {
    private static final String TAG = zzdb.class.getSimpleName();
    private static Object zzqQ = new Object();
    private static zze zzqS = null;
    protected Context zzqD;
    private Context zzqE;
    private ExecutorService zzqF;
    private DexClassLoader zzqG;
    private zzcw zzqH;
    private byte[] zzqI;
    private volatile AdvertisingIdClient zzqJ = null;
    private Future zzqK = null;
    /* access modifiers changed from: private */
    public volatile zzax zzqL = null;
    private Future zzqM = null;
    private zzcn zzqN;
    private GoogleApiClient zzqO = null;
    protected boolean zzqP = false;
    private boolean zzqR = false;
    protected boolean zzqT = false;
    private Map<Pair<String, String>, zzea> zzqU;
    private boolean zzqV = false;
    private volatile boolean zzqk = false;

    private zzdb(Context context) {
        this.zzqD = context;
        this.zzqE = context.getApplicationContext();
        this.zzqU = new HashMap();
    }

    /* access modifiers changed from: private */
    public final void zzM() {
        try {
            if (this.zzqJ == null && this.zzqE != null) {
                AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(this.zzqE);
                advertisingIdClient.start();
                this.zzqJ = advertisingIdClient;
            }
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException e) {
            this.zzqJ = null;
        }
    }

    @VisibleForTesting
    private final zzax zzN() {
        try {
            return zzcaq.zzn(this.zzqD, this.zzqD.getPackageName(), Integer.toString(this.zzqD.getPackageManager().getPackageInfo(this.zzqD.getPackageName(), 0).versionCode));
        } catch (Throwable th) {
            return null;
        }
    }

    public static zzdb zza(Context context, String str, String str2, boolean z) {
        File file;
        File file2;
        boolean z2 = true;
        zzdb zzdb = new zzdb(context);
        try {
            zzdb.zzqF = Executors.newCachedThreadPool();
            zzdb.zzqk = z;
            if (z) {
                zzdb.zzqK = zzdb.zzqF.submit(new zzdc(zzdb));
            }
            zzdb.zzqF.execute(new zzde(zzdb));
            try {
                zzqS = zze.zzoW();
                zzdb.zzqP = zze.zzau(zzdb.zzqD) > 0;
                if (zzqS.isGooglePlayServicesAvailable(zzdb.zzqD) != 0) {
                    z2 = false;
                }
                zzdb.zzqR = z2;
                if (zzdb.zzqD.getApplicationContext() != null) {
                    zzdb.zzqO = new GoogleApiClient.Builder(zzdb.zzqD).addApi(zzazn.API).build();
                }
            } catch (Throwable th) {
            }
            zzdb.zza(0, true);
            if (zzdg.zzS()) {
                if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFa)).booleanValue()) {
                    throw new IllegalStateException("Task Context initialization must not be called from the UI thread.");
                }
            }
            zzdb.zzqH = new zzcw((SecureRandom) null);
            zzdb.zzqI = zzdb.zzqH.zzl(str);
            File cacheDir = zzdb.zzqD.getCacheDir();
            if (cacheDir == null && (cacheDir = zzdb.zzqD.getDir("dex", 0)) == null) {
                throw new zzcy();
            }
            file = cacheDir;
            file2 = new File(String.format("%s/%s.jar", new Object[]{file, "1489418796403"}));
            if (!file2.exists()) {
                byte[] zza = zzdb.zzqH.zza(zzdb.zzqI, str2);
                file2.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(zza, 0, zza.length);
                fileOutputStream.close();
            }
            zzdb.zzb(file, "1489418796403");
            zzdb.zzqG = new DexClassLoader(file2.getAbsolutePath(), file.getAbsolutePath(), (String) null, zzdb.zzqD.getClassLoader());
            zza(file2);
            zzdb.zza(file, "1489418796403");
            zzm(String.format("%s/%s.dex", new Object[]{file, "1489418796403"}));
            zzdb.zzqN = new zzcn(zzdb);
            zzdb.zzqV = true;
            return zzdb;
        } catch (zzcx e) {
            throw new zzcy(e);
        } catch (FileNotFoundException e2) {
            throw new zzcy(e2);
        } catch (IOException e3) {
            throw new zzcy(e3);
        } catch (zzcx e4) {
            throw new zzcy(e4);
        } catch (NullPointerException e5) {
            throw new zzcy(e5);
        } catch (zzcy e6) {
        } catch (Throwable th2) {
            zza(file2);
            zzdb.zza(file, "1489418796403");
            zzm(String.format("%s/%s.dex", new Object[]{file, "1489418796403"}));
            throw th2;
        }
    }

    private static void zza(File file) {
        if (!file.exists()) {
            Log.d(TAG, String.format("File %s not found. No need for deletion", new Object[]{file.getAbsolutePath()}));
            return;
        }
        file.delete();
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x009c A[SYNTHETIC, Splitter:B:27:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a1 A[SYNTHETIC, Splitter:B:30:0x00a1] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00af A[SYNTHETIC, Splitter:B:36:0x00af] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b4 A[SYNTHETIC, Splitter:B:39:0x00b4] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zza(java.io.File r9, java.lang.String r10) {
        /*
            r8 = this;
            r2 = 0
            r7 = 2
            r6 = 1
            r4 = 0
            java.io.File r3 = new java.io.File
            java.lang.String r0 = "%s/%s.tmp"
            java.lang.Object[] r1 = new java.lang.Object[r7]
            r1[r4] = r9
            r1[r6] = r10
            java.lang.String r0 = java.lang.String.format(r0, r1)
            r3.<init>(r0)
            boolean r0 = r3.exists()
            if (r0 == 0) goto L_0x001c
        L_0x001b:
            return
        L_0x001c:
            java.io.File r5 = new java.io.File
            java.lang.String r0 = "%s/%s.dex"
            java.lang.Object[] r1 = new java.lang.Object[r7]
            r1[r4] = r9
            r1[r6] = r10
            java.lang.String r0 = java.lang.String.format(r0, r1)
            r5.<init>(r0)
            boolean r0 = r5.exists()
            if (r0 == 0) goto L_0x001b
            long r0 = r5.length()
            r6 = 0
            int r4 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x001b
            int r0 = (int) r0
            byte[] r0 = new byte[r0]
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00df, NoSuchAlgorithmException -> 0x00d6, zzcx -> 0x0097, all -> 0x00a9 }
            r1.<init>(r5)     // Catch:{ IOException -> 0x00df, NoSuchAlgorithmException -> 0x00d6, zzcx -> 0x0097, all -> 0x00a9 }
            int r4 = r1.read(r0)     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            if (r4 > 0) goto L_0x0052
            r1.close()     // Catch:{ IOException -> 0x00bb }
        L_0x004e:
            zza((java.io.File) r5)
            goto L_0x001b
        L_0x0052:
            com.google.android.gms.internal.zzbc r4 = new com.google.android.gms.internal.zzbc     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            r4.<init>()     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            java.lang.String r6 = android.os.Build.VERSION.SDK     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            r4.zzcG = r6     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            byte[] r6 = r10.getBytes()     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            r4.zzcF = r6     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            com.google.android.gms.internal.zzcw r6 = r8.zzqH     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            byte[] r7 = r8.zzqI     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            java.lang.String r0 = r6.zzc(r7, r0)     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            r4.data = r0     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            byte[] r0 = com.google.android.gms.internal.zzbv.zzb((byte[]) r0)     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            r4.zzcE = r0     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            r3.createNewFile()     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            r0.<init>(r3)     // Catch:{ IOException -> 0x00e3, NoSuchAlgorithmException -> 0x00da, zzcx -> 0x00d1, all -> 0x00c9 }
            byte[] r2 = com.google.android.gms.internal.adp.zzc(r4)     // Catch:{ IOException -> 0x00e6, NoSuchAlgorithmException -> 0x00dd, zzcx -> 0x00d4, all -> 0x00cd }
            r3 = 0
            int r4 = r2.length     // Catch:{ IOException -> 0x00e6, NoSuchAlgorithmException -> 0x00dd, zzcx -> 0x00d4, all -> 0x00cd }
            r0.write(r2, r3, r4)     // Catch:{ IOException -> 0x00e6, NoSuchAlgorithmException -> 0x00dd, zzcx -> 0x00d4, all -> 0x00cd }
            r0.close()     // Catch:{ IOException -> 0x00e6, NoSuchAlgorithmException -> 0x00dd, zzcx -> 0x00d4, all -> 0x00cd }
            r1.close()     // Catch:{ IOException -> 0x00bd }
        L_0x0090:
            r0.close()     // Catch:{ IOException -> 0x00bf }
        L_0x0093:
            zza((java.io.File) r5)
            goto L_0x001b
        L_0x0097:
            r0 = move-exception
            r0 = r2
            r1 = r2
        L_0x009a:
            if (r1 == 0) goto L_0x009f
            r1.close()     // Catch:{ IOException -> 0x00c1 }
        L_0x009f:
            if (r0 == 0) goto L_0x00a4
            r0.close()     // Catch:{ IOException -> 0x00c3 }
        L_0x00a4:
            zza((java.io.File) r5)
            goto L_0x001b
        L_0x00a9:
            r0 = move-exception
            r3 = r0
            r4 = r2
            r1 = r2
        L_0x00ad:
            if (r1 == 0) goto L_0x00b2
            r1.close()     // Catch:{ IOException -> 0x00c5 }
        L_0x00b2:
            if (r4 == 0) goto L_0x00b7
            r4.close()     // Catch:{ IOException -> 0x00c7 }
        L_0x00b7:
            zza((java.io.File) r5)
            throw r3
        L_0x00bb:
            r0 = move-exception
            goto L_0x004e
        L_0x00bd:
            r1 = move-exception
            goto L_0x0090
        L_0x00bf:
            r0 = move-exception
            goto L_0x0093
        L_0x00c1:
            r1 = move-exception
            goto L_0x009f
        L_0x00c3:
            r0 = move-exception
            goto L_0x00a4
        L_0x00c5:
            r0 = move-exception
            goto L_0x00b2
        L_0x00c7:
            r0 = move-exception
            goto L_0x00b7
        L_0x00c9:
            r0 = move-exception
            r3 = r0
            r4 = r2
            goto L_0x00ad
        L_0x00cd:
            r2 = move-exception
            r3 = r2
            r4 = r0
            goto L_0x00ad
        L_0x00d1:
            r0 = move-exception
            r0 = r2
            goto L_0x009a
        L_0x00d4:
            r2 = move-exception
            goto L_0x009a
        L_0x00d6:
            r0 = move-exception
            r0 = r2
            r1 = r2
            goto L_0x009a
        L_0x00da:
            r0 = move-exception
            r0 = r2
            goto L_0x009a
        L_0x00dd:
            r2 = move-exception
            goto L_0x009a
        L_0x00df:
            r0 = move-exception
            r0 = r2
            r1 = r2
            goto L_0x009a
        L_0x00e3:
            r0 = move-exception
            r0 = r2
            goto L_0x009a
        L_0x00e6:
            r2 = move-exception
            goto L_0x009a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdb.zza(java.io.File, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public static boolean zza(int i, zzax zzax) {
        if (i < 4) {
            if (zzax == null) {
                return true;
            }
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFd)).booleanValue() && (zzax.zzaT == null || zzax.zzaT.equals("0000000000000000000000000000000000000000000000000000000000000000"))) {
                return true;
            }
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFe)).booleanValue() && (zzax.zzbZ == null || zzax.zzbZ.zzcx == null || zzax.zzbZ.zzcx.longValue() == -2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c9 A[SYNTHETIC, Splitter:B:42:0x00c9] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ce A[SYNTHETIC, Splitter:B:45:0x00ce] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00da A[SYNTHETIC, Splitter:B:51:0x00da] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00df A[SYNTHETIC, Splitter:B:54:0x00df] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzb(java.io.File r13, java.lang.String r14) {
        /*
            r12 = this;
            r3 = 0
            r7 = 2
            r1 = 1
            r2 = 0
            java.io.File r5 = new java.io.File
            java.lang.String r0 = "%s/%s.tmp"
            java.lang.Object[] r4 = new java.lang.Object[r7]
            r4[r2] = r13
            r4[r1] = r14
            java.lang.String r0 = java.lang.String.format(r0, r4)
            r5.<init>(r0)
            boolean r0 = r5.exists()
            if (r0 != 0) goto L_0x001d
            r0 = r2
        L_0x001c:
            return r0
        L_0x001d:
            java.io.File r6 = new java.io.File
            java.lang.String r0 = "%s/%s.dex"
            java.lang.Object[] r4 = new java.lang.Object[r7]
            r4[r2] = r13
            r4[r1] = r14
            java.lang.String r0 = java.lang.String.format(r0, r4)
            r6.<init>(r0)
            boolean r0 = r6.exists()
            if (r0 == 0) goto L_0x0036
            r0 = r2
            goto L_0x001c
        L_0x0036:
            long r8 = r5.length()     // Catch:{ IOException -> 0x010d, NoSuchAlgorithmException -> 0x0102, zzcx -> 0x00c4, all -> 0x00d4 }
            r10 = 0
            int r0 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r0 > 0) goto L_0x0045
            zza((java.io.File) r5)     // Catch:{ IOException -> 0x010d, NoSuchAlgorithmException -> 0x0102, zzcx -> 0x00c4, all -> 0x00d4 }
            r0 = r2
            goto L_0x001c
        L_0x0045:
            int r0 = (int) r8     // Catch:{ IOException -> 0x010d, NoSuchAlgorithmException -> 0x0102, zzcx -> 0x00c4, all -> 0x00d4 }
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x010d, NoSuchAlgorithmException -> 0x0102, zzcx -> 0x00c4, all -> 0x00d4 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ IOException -> 0x010d, NoSuchAlgorithmException -> 0x0102, zzcx -> 0x00c4, all -> 0x00d4 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x010d, NoSuchAlgorithmException -> 0x0102, zzcx -> 0x00c4, all -> 0x00d4 }
            int r7 = r4.read(r0)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            if (r7 > 0) goto L_0x0062
            java.lang.String r0 = TAG     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            java.lang.String r1 = "Cannot read the cache data."
            android.util.Log.d(r0, r1)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            zza((java.io.File) r5)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            r4.close()     // Catch:{ IOException -> 0x00e3 }
        L_0x0060:
            r0 = r2
            goto L_0x001c
        L_0x0062:
            com.google.android.gms.internal.zzbc r7 = new com.google.android.gms.internal.zzbc     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            r7.<init>()     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            com.google.android.gms.internal.adp r0 = com.google.android.gms.internal.adp.zza(r7, r0)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            com.google.android.gms.internal.zzbc r0 = (com.google.android.gms.internal.zzbc) r0     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            java.lang.String r7 = new java.lang.String     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            byte[] r8 = r0.zzcF     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            r7.<init>(r8)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            boolean r7 = r14.equals(r7)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            if (r7 == 0) goto L_0x0096
            byte[] r7 = r0.zzcE     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            byte[] r8 = r0.data     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            byte[] r8 = com.google.android.gms.internal.zzbv.zzb((byte[]) r8)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            boolean r7 = java.util.Arrays.equals(r7, r8)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            if (r7 == 0) goto L_0x0096
            byte[] r7 = r0.zzcG     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            java.lang.String r8 = android.os.Build.VERSION.SDK     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            byte[] r8 = r8.getBytes()     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            boolean r7 = java.util.Arrays.equals(r7, r8)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            if (r7 != 0) goto L_0x009f
        L_0x0096:
            zza((java.io.File) r5)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            r4.close()     // Catch:{ IOException -> 0x00e6 }
        L_0x009c:
            r0 = r2
            goto L_0x001c
        L_0x009f:
            com.google.android.gms.internal.zzcw r5 = r12.zzqH     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            byte[] r7 = r12.zzqI     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            java.lang.String r8 = new java.lang.String     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            byte[] r0 = r0.data     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            r8.<init>(r0)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            byte[] r5 = r5.zza(r7, r8)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            r6.createNewFile()     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            r0.<init>(r6)     // Catch:{ IOException -> 0x0111, NoSuchAlgorithmException -> 0x0106, zzcx -> 0x00fb, all -> 0x00f4 }
            r3 = 0
            int r6 = r5.length     // Catch:{ IOException -> 0x0115, NoSuchAlgorithmException -> 0x010a, zzcx -> 0x00ff, all -> 0x00f8 }
            r0.write(r5, r3, r6)     // Catch:{ IOException -> 0x0115, NoSuchAlgorithmException -> 0x010a, zzcx -> 0x00ff, all -> 0x00f8 }
            r4.close()     // Catch:{ IOException -> 0x00e8 }
        L_0x00be:
            r0.close()     // Catch:{ IOException -> 0x00ea }
        L_0x00c1:
            r0 = r1
            goto L_0x001c
        L_0x00c4:
            r0 = move-exception
            r0 = r3
            r1 = r3
        L_0x00c7:
            if (r1 == 0) goto L_0x00cc
            r1.close()     // Catch:{ IOException -> 0x00ec }
        L_0x00cc:
            if (r0 == 0) goto L_0x00d1
            r0.close()     // Catch:{ IOException -> 0x00ee }
        L_0x00d1:
            r0 = r2
            goto L_0x001c
        L_0x00d4:
            r0 = move-exception
            r1 = r0
            r2 = r3
            r4 = r3
        L_0x00d8:
            if (r4 == 0) goto L_0x00dd
            r4.close()     // Catch:{ IOException -> 0x00f0 }
        L_0x00dd:
            if (r2 == 0) goto L_0x00e2
            r2.close()     // Catch:{ IOException -> 0x00f2 }
        L_0x00e2:
            throw r1
        L_0x00e3:
            r0 = move-exception
            goto L_0x0060
        L_0x00e6:
            r0 = move-exception
            goto L_0x009c
        L_0x00e8:
            r2 = move-exception
            goto L_0x00be
        L_0x00ea:
            r0 = move-exception
            goto L_0x00c1
        L_0x00ec:
            r1 = move-exception
            goto L_0x00cc
        L_0x00ee:
            r0 = move-exception
            goto L_0x00d1
        L_0x00f0:
            r0 = move-exception
            goto L_0x00dd
        L_0x00f2:
            r0 = move-exception
            goto L_0x00e2
        L_0x00f4:
            r0 = move-exception
            r1 = r0
            r2 = r3
            goto L_0x00d8
        L_0x00f8:
            r1 = move-exception
            r2 = r0
            goto L_0x00d8
        L_0x00fb:
            r0 = move-exception
            r0 = r3
            r1 = r4
            goto L_0x00c7
        L_0x00ff:
            r1 = move-exception
            r1 = r4
            goto L_0x00c7
        L_0x0102:
            r0 = move-exception
            r0 = r3
            r1 = r3
            goto L_0x00c7
        L_0x0106:
            r0 = move-exception
            r0 = r3
            r1 = r4
            goto L_0x00c7
        L_0x010a:
            r1 = move-exception
            r1 = r4
            goto L_0x00c7
        L_0x010d:
            r0 = move-exception
            r0 = r3
            r1 = r3
            goto L_0x00c7
        L_0x0111:
            r0 = move-exception
            r0 = r3
            r1 = r4
            goto L_0x00c7
        L_0x0115:
            r1 = move-exception
            r1 = r4
            goto L_0x00c7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdb.zzb(java.io.File, java.lang.String):boolean");
    }

    private static void zzm(String str) {
        zza(new File(str));
    }

    public final Context getApplicationContext() {
        return this.zzqE;
    }

    public final Context getContext() {
        return this.zzqD;
    }

    public final boolean isInitialized() {
        return this.zzqV;
    }

    public final ExecutorService zzC() {
        return this.zzqF;
    }

    public final DexClassLoader zzD() {
        return this.zzqG;
    }

    public final zzcw zzE() {
        return this.zzqH;
    }

    public final byte[] zzF() {
        return this.zzqI;
    }

    public final GoogleApiClient zzG() {
        return this.zzqO;
    }

    public final boolean zzH() {
        return this.zzqP;
    }

    public final zzcn zzI() {
        return this.zzqN;
    }

    public final boolean zzJ() {
        return this.zzqR;
    }

    public final zzax zzK() {
        return this.zzqL;
    }

    public final Future zzL() {
        return this.zzqM;
    }

    public final AdvertisingIdClient zzO() {
        if (!this.zzqk) {
            return null;
        }
        if (this.zzqJ != null) {
            return this.zzqJ;
        }
        if (this.zzqK != null) {
            try {
                this.zzqK.get(2000, TimeUnit.MILLISECONDS);
                this.zzqK = null;
            } catch (InterruptedException | ExecutionException e) {
            } catch (TimeoutException e2) {
                this.zzqK.cancel(true);
            }
        }
        return this.zzqJ;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzP() {
        /*
            r2 = this;
            java.lang.Object r1 = zzqQ     // Catch:{ Throwable -> 0x001e }
            monitor-enter(r1)     // Catch:{ Throwable -> 0x001e }
            boolean r0 = r2.zzqT     // Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
        L_0x0008:
            return
        L_0x0009:
            boolean r0 = r2.zzqR     // Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0020
            com.google.android.gms.common.api.GoogleApiClient r0 = r2.zzqO     // Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0020
            com.google.android.gms.common.api.GoogleApiClient r0 = r2.zzqO     // Catch:{ all -> 0x001b }
            r0.connect()     // Catch:{ all -> 0x001b }
            r0 = 1
            r2.zzqT = r0     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            goto L_0x0008
        L_0x001b:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            throw r0     // Catch:{ Throwable -> 0x001e }
        L_0x001e:
            r0 = move-exception
            goto L_0x0008
        L_0x0020:
            r0 = 0
            r2.zzqT = r0     // Catch:{ all -> 0x001b }
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdb.zzP():void");
    }

    public final void zzQ() {
        synchronized (zzqQ) {
            if (this.zzqT && this.zzqO != null) {
                this.zzqO.disconnect();
                this.zzqT = false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final void zza(int i, boolean z) {
        if (this.zzqR) {
            Future<?> submit = this.zzqF.submit(new zzdd(this, i, z));
            if (i == 0) {
                this.zzqM = submit;
            }
        }
    }

    public final boolean zza(String str, String str2, Class<?>... clsArr) {
        if (this.zzqU.containsKey(new Pair(str, str2))) {
            return false;
        }
        this.zzqU.put(new Pair(str, str2), new zzea(this, str, str2, clsArr));
        return true;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final zzax zzb(int i, boolean z) {
        if (i > 0 && z) {
            try {
                Thread.sleep((long) (i * 1000));
            } catch (InterruptedException e) {
            }
        }
        return zzN();
    }

    public final Method zzc(String str, String str2) {
        zzea zzea = this.zzqU.get(new Pair(str, str2));
        if (zzea == null) {
            return null;
        }
        return zzea.zzY();
    }

    public final int zzy() {
        if (this.zzqN != null) {
            return zzcn.zzy();
        }
        return Integer.MIN_VALUE;
    }
}
