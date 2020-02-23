package com.google.android.gms.internal;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.amazonaws.services.s3.Headers;
import com.garena.android.gpns.utility.CONSTANT;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class zzcgl {
    private static volatile zzcgl zzbsm;
    private final Context mContext;
    private final boolean zzafK;
    private final zzchz zzbsA;
    private final zzcid zzbsB;
    private final zzcet zzbsC;
    private final zzchl zzbsD;
    private final zzcfg zzbsE;
    private final zzcfu zzbsF;
    private final zzcjg zzbsG;
    private final zzcej zzbsH;
    private final zzcec zzbsI;
    private boolean zzbsJ;
    private Boolean zzbsK;
    private long zzbsL;
    private FileLock zzbsM;
    private FileChannel zzbsN;
    private List<Long> zzbsO;
    private List<Runnable> zzbsP;
    private int zzbsQ;
    private int zzbsR;
    private long zzbsS = -1;
    private long zzbsT;
    private boolean zzbsU;
    private boolean zzbsV;
    private boolean zzbsW;
    private final long zzbsX = this.zzvw.currentTimeMillis();
    private final zzcem zzbsn = new zzcem(this);
    private final zzcfw zzbso;
    private final zzcfl zzbsp;
    private final zzcgg zzbsq;
    private final zzcja zzbsr;
    private final zzcgf zzbss;
    private final AppMeasurement zzbst;
    private final FirebaseAnalytics zzbsu;
    private final zzcjl zzbsv;
    private final zzcfj zzbsw;
    private final zzcen zzbsx;
    private final zzcfh zzbsy;
    private final zzcfp zzbsz;
    private final zze zzvw = zzi.zzrY();

    class zza implements zzcep {
        zzcjz zzbsZ;
        List<Long> zzbta;
        private long zzbtb;
        List<zzcjw> zztH;

        private zza() {
        }

        /* synthetic */ zza(zzcgl zzcgl, zzcgm zzcgm) {
            this();
        }

        private static long zza(zzcjw zzcjw) {
            return ((zzcjw.zzbvx.longValue() / 1000) / 60) / 60;
        }

        public final boolean zza(long j, zzcjw zzcjw) {
            zzbo.zzu(zzcjw);
            if (this.zztH == null) {
                this.zztH = new ArrayList();
            }
            if (this.zzbta == null) {
                this.zzbta = new ArrayList();
            }
            if (this.zztH.size() > 0 && zza(this.zztH.get(0)) != zza(zzcjw)) {
                return false;
            }
            long zzLV = this.zzbtb + ((long) zzcjw.zzLV());
            if (zzLV >= ((long) zzcem.zzxL())) {
                return false;
            }
            this.zzbtb = zzLV;
            this.zztH.add(zzcjw);
            this.zzbta.add(Long.valueOf(j));
            return this.zztH.size() < zzcem.zzxM();
        }

        public final void zzb(zzcjz zzcjz) {
            zzbo.zzu(zzcjz);
            this.zzbsZ = zzcjz;
        }
    }

    private zzcgl(zzchk zzchk) {
        zzcfn zzyB;
        String concat;
        zzbo.zzu(zzchk);
        this.mContext = zzchk.mContext;
        zzcfw zzcfw = new zzcfw(this);
        zzcfw.initialize();
        this.zzbso = zzcfw;
        zzcfl zzcfl = new zzcfl(this);
        zzcfl.initialize();
        this.zzbsp = zzcfl;
        zzwF().zzyB().zzj("App measurement is starting up, version", Long.valueOf(zzcem.zzwP()));
        zzcem.zzxE();
        zzwF().zzyB().log("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        zzcjl zzcjl = new zzcjl(this);
        zzcjl.initialize();
        this.zzbsv = zzcjl;
        zzcfj zzcfj = new zzcfj(this);
        zzcfj.initialize();
        this.zzbsw = zzcfj;
        zzcet zzcet = new zzcet(this);
        zzcet.initialize();
        this.zzbsC = zzcet;
        zzcfg zzcfg = new zzcfg(this);
        zzcfg.initialize();
        this.zzbsE = zzcfg;
        zzcem.zzxE();
        String zzhl = zzcfg.zzhl();
        if (zzwB().zzey(zzhl)) {
            zzyB = zzwF().zzyB();
            concat = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
        } else {
            zzyB = zzwF().zzyB();
            String valueOf = String.valueOf(zzhl);
            concat = valueOf.length() != 0 ? "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(valueOf) : new String("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ");
        }
        zzyB.log(concat);
        zzwF().zzyC().log("Debug-level message logging enabled");
        zzcen zzcen = new zzcen(this);
        zzcen.initialize();
        this.zzbsx = zzcen;
        zzcfh zzcfh = new zzcfh(this);
        zzcfh.initialize();
        this.zzbsy = zzcfh;
        zzcej zzcej = new zzcej(this);
        zzcej.initialize();
        this.zzbsH = zzcej;
        this.zzbsI = new zzcec(this);
        zzcfp zzcfp = new zzcfp(this);
        zzcfp.initialize();
        this.zzbsz = zzcfp;
        zzchz zzchz = new zzchz(this);
        zzchz.initialize();
        this.zzbsA = zzchz;
        zzcid zzcid = new zzcid(this);
        zzcid.initialize();
        this.zzbsB = zzcid;
        zzchl zzchl = new zzchl(this);
        zzchl.initialize();
        this.zzbsD = zzchl;
        zzcjg zzcjg = new zzcjg(this);
        zzcjg.initialize();
        this.zzbsG = zzcjg;
        this.zzbsF = new zzcfu(this);
        this.zzbst = new AppMeasurement(this);
        this.zzbsu = new FirebaseAnalytics(this);
        zzcja zzcja = new zzcja(this);
        zzcja.initialize();
        this.zzbsr = zzcja;
        zzcgf zzcgf = new zzcgf(this);
        zzcgf.initialize();
        this.zzbss = zzcgf;
        zzcgg zzcgg = new zzcgg(this);
        zzcgg.initialize();
        this.zzbsq = zzcgg;
        if (this.zzbsQ != this.zzbsR) {
            zzwF().zzyx().zze("Not all components initialized", Integer.valueOf(this.zzbsQ), Integer.valueOf(this.zzbsR));
        }
        this.zzafK = true;
        zzcem.zzxE();
        if (this.mContext.getApplicationContext() instanceof Application) {
            zzchl zzwt = zzwt();
            if (zzwt.getContext().getApplicationContext() instanceof Application) {
                Application application = (Application) zzwt.getContext().getApplicationContext();
                if (zzwt.zzbto == null) {
                    zzwt.zzbto = new zzchy(zzwt, (zzchm) null);
                }
                application.unregisterActivityLifecycleCallbacks(zzwt.zzbto);
                application.registerActivityLifecycleCallbacks(zzwt.zzbto);
                zzwt.zzwF().zzyD().log("Registered activity lifecycle callback");
            }
        } else {
            zzwF().zzyz().log("Application context is not an Application");
        }
        this.zzbsq.zzj(new zzcgm(this));
    }

    @WorkerThread
    private final int zza(FileChannel fileChannel) {
        zzwE().zzjC();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzwF().zzyx().log("Bad chanel to read from");
            return 0;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        try {
            fileChannel.position(0);
            int read = fileChannel.read(allocate);
            if (read == 4) {
                allocate.flip();
                return allocate.getInt();
            } else if (read == -1) {
                return 0;
            } else {
                zzwF().zzyz().zzj("Unexpected data length. Bytes read", Integer.valueOf(read));
                return 0;
            }
        } catch (IOException e) {
            zzwF().zzyx().zzj("Failed to read from channel", e);
            return 0;
        }
    }

    private final void zza(zzceu zzceu, zzceh zzceh) {
        boolean z;
        zzwE().zzjC();
        zzkD();
        zzbo.zzu(zzceu);
        zzbo.zzu(zzceh);
        zzbo.zzcF(zzceu.mAppId);
        zzbo.zzaf(zzceu.mAppId.equals(zzceh.packageName));
        zzcjz zzcjz = new zzcjz();
        zzcjz.zzbvD = 1;
        zzcjz.zzbvL = SystemMediaRouteProvider.PACKAGE_NAME;
        zzcjz.zzaH = zzceh.packageName;
        zzcjz.zzboR = zzceh.zzboR;
        zzcjz.zzbgW = zzceh.zzbgW;
        zzcjz.zzbvY = zzceh.zzboX == -2147483648L ? null : Integer.valueOf((int) zzceh.zzboX);
        zzcjz.zzbvP = Long.valueOf(zzceh.zzboS);
        zzcjz.zzboQ = zzceh.zzboQ;
        zzcjz.zzbvU = zzceh.zzboT == 0 ? null : Long.valueOf(zzceh.zzboT);
        Pair<String, Boolean> zzeb = zzwG().zzeb(zzceh.packageName);
        if (zzeb != null && !TextUtils.isEmpty((CharSequence) zzeb.first)) {
            zzcjz.zzbvR = (String) zzeb.first;
            zzcjz.zzbvS = (Boolean) zzeb.second;
        }
        zzwv().zzkD();
        zzcjz.zzbvM = Build.MODEL;
        zzwv().zzkD();
        zzcjz.zzaY = Build.VERSION.RELEASE;
        zzcjz.zzbvO = Integer.valueOf((int) zzwv().zzyq());
        zzcjz.zzbvN = zzwv().zzyr();
        zzcjz.zzbvQ = null;
        zzcjz.zzbvG = null;
        zzcjz.zzbvH = null;
        zzcjz.zzbvI = null;
        zzcjz.zzbwc = Long.valueOf(zzceh.zzboZ);
        if (isEnabled() && zzcem.zzyb()) {
            zzwu();
            zzcjz.zzbwd = null;
        }
        zzceg zzdQ = zzwz().zzdQ(zzceh.packageName);
        if (zzdQ == null) {
            zzdQ = new zzceg(this, zzceh.packageName);
            zzdQ.zzdG(zzwu().zzyu());
            zzdQ.zzdJ(zzceh.zzboY);
            zzdQ.zzdH(zzceh.zzboQ);
            zzdQ.zzdI(zzwG().zzec(zzceh.packageName));
            zzdQ.zzQ(0);
            zzdQ.zzL(0);
            zzdQ.zzM(0);
            zzdQ.setAppVersion(zzceh.zzbgW);
            zzdQ.zzN(zzceh.zzboX);
            zzdQ.zzdK(zzceh.zzboR);
            zzdQ.zzO(zzceh.zzboS);
            zzdQ.zzP(zzceh.zzboT);
            zzdQ.setMeasurementEnabled(zzceh.zzboV);
            zzdQ.zzZ(zzceh.zzboZ);
            zzwz().zza(zzdQ);
        }
        zzcjz.zzbvT = zzdQ.getAppInstanceId();
        zzcjz.zzboY = zzdQ.zzwK();
        List<zzcjk> zzdP = zzwz().zzdP(zzceh.packageName);
        zzcjz.zzbvF = new zzckb[zzdP.size()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < zzdP.size()) {
                zzckb zzckb = new zzckb();
                zzcjz.zzbvF[i2] = zzckb;
                zzckb.name = zzdP.get(i2).mName;
                zzckb.zzbwh = Long.valueOf(zzdP.get(i2).zzbuC);
                zzwB().zza(zzckb, zzdP.get(i2).mValue);
                i = i2 + 1;
            } else {
                try {
                    break;
                } catch (IOException e) {
                    zzwF().zzyx().zze("Data loss. Failed to insert raw event metadata. appId", zzcfl.zzdZ(zzcjz.zzaH), e);
                    return;
                }
            }
        }
        long zza2 = zzwz().zza(zzcjz);
        zzcen zzwz = zzwz();
        if (zzceu.zzbpF != null) {
            Iterator<String> it = zzceu.zzbpF.iterator();
            while (true) {
                if (it.hasNext()) {
                    if ("_r".equals(it.next())) {
                        z = true;
                        break;
                    }
                } else {
                    boolean zzO = zzwC().zzO(zzceu.mAppId, zzceu.mName);
                    zzceo zza3 = zzwz().zza(zzyZ(), zzceu.mAppId, false, false, false, false, false);
                    if (zzO && zza3.zzbpy < ((long) this.zzbsn.zzdM(zzceu.mAppId))) {
                        z = true;
                    }
                }
            }
        }
        z = false;
        if (zzwz.zza(zzceu, zza2, z)) {
            this.zzbsT = 0;
        }
    }

    private static void zza(zzchi zzchi) {
        if (zzchi == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static void zza(zzchj zzchj) {
        if (zzchj == null) {
            throw new IllegalStateException("Component not created");
        } else if (!zzchj.isInitialized()) {
            throw new IllegalStateException("Component not initialized");
        }
    }

    @WorkerThread
    private final boolean zza(int i, FileChannel fileChannel) {
        zzwE().zzjC();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzwF().zzyx().log("Bad chanel to read from");
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.flip();
        try {
            fileChannel.truncate(0);
            fileChannel.write(allocate);
            fileChannel.force(true);
            if (fileChannel.size() == 4) {
                return true;
            }
            zzwF().zzyx().zzj("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            return true;
        } catch (IOException e) {
            zzwF().zzyx().zzj("Failed to write to channel", e);
            return false;
        }
    }

    private final zzcjv[] zza(String str, zzckb[] zzckbArr, zzcjw[] zzcjwArr) {
        zzbo.zzcF(str);
        return zzws().zza(str, zzcjwArr, zzckbArr);
    }

    @WorkerThread
    private final void zzb(zzceg zzceg) {
        ArrayMap arrayMap = null;
        zzwE().zzjC();
        if (TextUtils.isEmpty(zzceg.getGmpAppId())) {
            zzb(zzceg.zzhl(), 204, (Throwable) null, (byte[]) null, (Map<String, List<String>>) null);
            return;
        }
        String gmpAppId = zzceg.getGmpAppId();
        String appInstanceId = zzceg.getAppInstanceId();
        Uri.Builder builder = new Uri.Builder();
        Uri.Builder encodedAuthority = builder.scheme(zzcfb.zzbpZ.get()).encodedAuthority(zzcfb.zzbqa.get());
        String valueOf = String.valueOf(gmpAppId);
        encodedAuthority.path(valueOf.length() != 0 ? "config/app/".concat(valueOf) : new String("config/app/")).appendQueryParameter("app_instance_id", appInstanceId).appendQueryParameter("platform", SystemMediaRouteProvider.PACKAGE_NAME).appendQueryParameter("gmp_version", "11020");
        String uri = builder.build().toString();
        try {
            URL url = new URL(uri);
            zzwF().zzyD().zzj("Fetching remote configuration", zzceg.zzhl());
            zzcjt zzeh = zzwC().zzeh(zzceg.zzhl());
            String zzei = zzwC().zzei(zzceg.zzhl());
            if (zzeh != null && !TextUtils.isEmpty(zzei)) {
                arrayMap = new ArrayMap();
                arrayMap.put(Headers.GET_OBJECT_IF_MODIFIED_SINCE, zzei);
            }
            this.zzbsU = true;
            zzyU().zza(zzceg.zzhl(), url, arrayMap, new zzcgp(this));
        } catch (MalformedURLException e) {
            zzwF().zzyx().zze("Failed to parse config URL. Not fetching. appId", zzcfl.zzdZ(zzceg.zzhl()), uri);
        }
    }

    public static zzcgl zzbj(Context context) {
        zzbo.zzu(context);
        zzbo.zzu(context.getApplicationContext());
        if (zzbsm == null) {
            synchronized (zzcgl.class) {
                if (zzbsm == null) {
                    zzbsm = new zzcgl(new zzchk(context));
                }
            }
        }
        return zzbsm;
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:56:0x01d4=Splitter:B:56:0x01d4, B:68:0x027b=Splitter:B:68:0x027b, B:99:0x038f=Splitter:B:99:0x038f} */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzc(com.google.android.gms.internal.zzcez r19, com.google.android.gms.internal.zzceh r20) {
        /*
            r18 = this;
            com.google.android.gms.common.internal.zzbo.zzu(r20)
            r0 = r20
            java.lang.String r2 = r0.packageName
            com.google.android.gms.common.internal.zzbo.zzcF(r2)
            long r16 = java.lang.System.nanoTime()
            com.google.android.gms.internal.zzcgg r2 = r18.zzwE()
            r2.zzjC()
            r18.zzkD()
            r0 = r20
            java.lang.String r3 = r0.packageName
            r18.zzwB()
            boolean r2 = com.google.android.gms.internal.zzcjl.zzd((com.google.android.gms.internal.zzcez) r19, (com.google.android.gms.internal.zzceh) r20)
            if (r2 != 0) goto L_0x0026
        L_0x0025:
            return
        L_0x0026:
            r0 = r20
            boolean r2 = r0.zzboV
            if (r2 != 0) goto L_0x0034
            r0 = r18
            r1 = r20
            r0.zzf(r1)
            goto L_0x0025
        L_0x0034:
            com.google.android.gms.internal.zzcgf r2 = r18.zzwC()
            r0 = r19
            java.lang.String r4 = r0.name
            boolean r2 = r2.zzN(r3, r4)
            if (r2 == 0) goto L_0x00d8
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()
            java.lang.String r4 = "Dropping blacklisted event. appId"
            java.lang.Object r5 = com.google.android.gms.internal.zzcfl.zzdZ(r3)
            com.google.android.gms.internal.zzcfj r6 = r18.zzwA()
            r0 = r19
            java.lang.String r7 = r0.name
            java.lang.String r6 = r6.zzdW(r7)
            r2.zze(r4, r5, r6)
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()
            boolean r2 = r2.zzeA(r3)
            if (r2 != 0) goto L_0x0073
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()
            boolean r2 = r2.zzeB(r3)
            if (r2 == 0) goto L_0x00d5
        L_0x0073:
            r2 = 1
            r8 = r2
        L_0x0075:
            if (r8 != 0) goto L_0x0093
            java.lang.String r2 = "_err"
            r0 = r19
            java.lang.String r4 = r0.name
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x0093
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()
            r4 = 11
            java.lang.String r5 = "_ev"
            r0 = r19
            java.lang.String r6 = r0.name
            r7 = 0
            r2.zza((java.lang.String) r3, (int) r4, (java.lang.String) r5, (java.lang.String) r6, (int) r7)
        L_0x0093:
            if (r8 == 0) goto L_0x0025
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            com.google.android.gms.internal.zzceg r2 = r2.zzdQ(r3)
            if (r2 == 0) goto L_0x0025
            long r4 = r2.zzwU()
            long r6 = r2.zzwT()
            long r4 = java.lang.Math.max(r4, r6)
            r0 = r18
            com.google.android.gms.common.util.zze r3 = r0.zzvw
            long r6 = r3.currentTimeMillis()
            long r4 = r6 - r4
            long r4 = java.lang.Math.abs(r4)
            long r6 = com.google.android.gms.internal.zzcem.zzxI()
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x0025
            com.google.android.gms.internal.zzcfl r3 = r18.zzwF()
            com.google.android.gms.internal.zzcfn r3 = r3.zzyC()
            java.lang.String r4 = "Fetching config for blacklisted app"
            r3.log(r4)
            r0 = r18
            r0.zzb((com.google.android.gms.internal.zzceg) r2)
            goto L_0x0025
        L_0x00d5:
            r2 = 0
            r8 = r2
            goto L_0x0075
        L_0x00d8:
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()
            r4 = 2
            boolean r2 = r2.zzz(r4)
            if (r2 == 0) goto L_0x00fa
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()
            com.google.android.gms.internal.zzcfn r2 = r2.zzyD()
            java.lang.String r4 = "Logging event"
            com.google.android.gms.internal.zzcfj r5 = r18.zzwA()
            r0 = r19
            java.lang.String r5 = r5.zzb((com.google.android.gms.internal.zzcez) r0)
            r2.zzj(r4, r5)
        L_0x00fa:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.beginTransaction()
            r0 = r19
            com.google.android.gms.internal.zzcew r2 = r0.zzbpM     // Catch:{ all -> 0x02b0 }
            android.os.Bundle r14 = r2.zzyt()     // Catch:{ all -> 0x02b0 }
            r0 = r18
            r1 = r20
            r0.zzf(r1)     // Catch:{ all -> 0x02b0 }
            java.lang.String r2 = "_iap"
            r0 = r19
            java.lang.String r4 = r0.name     // Catch:{ all -> 0x02b0 }
            boolean r2 = r2.equals(r4)     // Catch:{ all -> 0x02b0 }
            if (r2 != 0) goto L_0x0128
            java.lang.String r2 = "ecommerce_purchase"
            r0 = r19
            java.lang.String r4 = r0.name     // Catch:{ all -> 0x02b0 }
            boolean r2 = r2.equals(r4)     // Catch:{ all -> 0x02b0 }
            if (r2 == 0) goto L_0x021c
        L_0x0128:
            java.lang.String r2 = "currency"
            java.lang.String r2 = r14.getString(r2)     // Catch:{ all -> 0x02b0 }
            java.lang.String r4 = "ecommerce_purchase"
            r0 = r19
            java.lang.String r5 = r0.name     // Catch:{ all -> 0x02b0 }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x02b0 }
            if (r4 == 0) goto L_0x02a0
            java.lang.String r4 = "value"
            double r4 = r14.getDouble(r4)     // Catch:{ all -> 0x02b0 }
            r6 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            double r4 = r4 * r6
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 != 0) goto L_0x0159
            java.lang.String r4 = "value"
            long r4 = r14.getLong(r4)     // Catch:{ all -> 0x02b0 }
            double r4 = (double) r4     // Catch:{ all -> 0x02b0 }
            r6 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            double r4 = r4 * r6
        L_0x0159:
            r6 = 4890909195324358656(0x43e0000000000000, double:9.223372036854776E18)
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 > 0) goto L_0x027b
            r6 = -4332462841530417152(0xc3e0000000000000, double:-9.223372036854776E18)
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 < 0) goto L_0x027b
            long r4 = java.lang.Math.round(r4)     // Catch:{ all -> 0x02b0 }
            r8 = r4
        L_0x016a:
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x02b0 }
            if (r4 != 0) goto L_0x021c
            java.util.Locale r4 = java.util.Locale.US     // Catch:{ all -> 0x02b0 }
            java.lang.String r2 = r2.toUpperCase(r4)     // Catch:{ all -> 0x02b0 }
            java.lang.String r4 = "[A-Z]{3}"
            boolean r4 = r2.matches(r4)     // Catch:{ all -> 0x02b0 }
            if (r4 == 0) goto L_0x021c
            java.lang.String r4 = "_ltv_"
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x02b0 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x02b0 }
            int r5 = r2.length()     // Catch:{ all -> 0x02b0 }
            if (r5 == 0) goto L_0x02a9
            java.lang.String r5 = r4.concat(r2)     // Catch:{ all -> 0x02b0 }
        L_0x0192:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcjk r2 = r2.zzG(r3, r5)     // Catch:{ all -> 0x02b0 }
            if (r2 == 0) goto L_0x01a2
            java.lang.Object r4 = r2.mValue     // Catch:{ all -> 0x02b0 }
            boolean r4 = r4 instanceof java.lang.Long     // Catch:{ all -> 0x02b0 }
            if (r4 != 0) goto L_0x02cd
        L_0x01a2:
            com.google.android.gms.internal.zzcen r4 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            r0 = r18
            com.google.android.gms.internal.zzcem r2 = r0.zzbsn     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfc<java.lang.Integer> r6 = com.google.android.gms.internal.zzcfb.zzbqz     // Catch:{ all -> 0x02b0 }
            int r2 = r2.zzb(r3, r6)     // Catch:{ all -> 0x02b0 }
            int r2 = r2 + -1
            com.google.android.gms.common.internal.zzbo.zzcF(r3)     // Catch:{ all -> 0x02b0 }
            r4.zzjC()     // Catch:{ all -> 0x02b0 }
            r4.zzkD()     // Catch:{ all -> 0x02b0 }
            android.database.sqlite.SQLiteDatabase r6 = r4.getWritableDatabase()     // Catch:{ SQLiteException -> 0x02b9 }
            java.lang.String r7 = "delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);"
            r10 = 3
            java.lang.String[] r10 = new java.lang.String[r10]     // Catch:{ SQLiteException -> 0x02b9 }
            r11 = 0
            r10[r11] = r3     // Catch:{ SQLiteException -> 0x02b9 }
            r11 = 1
            r10[r11] = r3     // Catch:{ SQLiteException -> 0x02b9 }
            r11 = 2
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ SQLiteException -> 0x02b9 }
            r10[r11] = r2     // Catch:{ SQLiteException -> 0x02b9 }
            r6.execSQL(r7, r10)     // Catch:{ SQLiteException -> 0x02b9 }
        L_0x01d4:
            com.google.android.gms.internal.zzcjk r2 = new com.google.android.gms.internal.zzcjk     // Catch:{ all -> 0x02b0 }
            r0 = r19
            java.lang.String r4 = r0.zzbpc     // Catch:{ all -> 0x02b0 }
            r0 = r18
            com.google.android.gms.common.util.zze r6 = r0.zzvw     // Catch:{ all -> 0x02b0 }
            long r6 = r6.currentTimeMillis()     // Catch:{ all -> 0x02b0 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x02b0 }
            r2.<init>(r3, r4, r5, r6, r8)     // Catch:{ all -> 0x02b0 }
        L_0x01e9:
            com.google.android.gms.internal.zzcen r4 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            boolean r4 = r4.zza((com.google.android.gms.internal.zzcjk) r2)     // Catch:{ all -> 0x02b0 }
            if (r4 != 0) goto L_0x021c
            com.google.android.gms.internal.zzcfl r4 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ all -> 0x02b0 }
            java.lang.String r5 = "Too many unique user properties are set. Ignoring user property. appId"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r3)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfj r7 = r18.zzwA()     // Catch:{ all -> 0x02b0 }
            java.lang.String r8 = r2.mName     // Catch:{ all -> 0x02b0 }
            java.lang.String r7 = r7.zzdY(r8)     // Catch:{ all -> 0x02b0 }
            java.lang.Object r2 = r2.mValue     // Catch:{ all -> 0x02b0 }
            r4.zzd(r5, r6, r7, r2)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x02b0 }
            r4 = 9
            r5 = 0
            r6 = 0
            r7 = 0
            r2.zza((java.lang.String) r3, (int) r4, (java.lang.String) r5, (java.lang.String) r6, (int) r7)     // Catch:{ all -> 0x02b0 }
        L_0x021c:
            r0 = r19
            java.lang.String r2 = r0.name     // Catch:{ all -> 0x02b0 }
            boolean r10 = com.google.android.gms.internal.zzcjl.zzeo(r2)     // Catch:{ all -> 0x02b0 }
            java.lang.String r2 = "_err"
            r0 = r19
            java.lang.String r4 = r0.name     // Catch:{ all -> 0x02b0 }
            boolean r12 = r2.equals(r4)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r5 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            long r6 = r18.zzyZ()     // Catch:{ all -> 0x02b0 }
            r9 = 1
            r11 = 0
            r13 = 0
            r8 = r3
            com.google.android.gms.internal.zzceo r2 = r5.zza(r6, r8, r9, r10, r11, r12, r13)     // Catch:{ all -> 0x02b0 }
            long r4 = r2.zzbpv     // Catch:{ all -> 0x02b0 }
            long r6 = com.google.android.gms.internal.zzcem.zzxq()     // Catch:{ all -> 0x02b0 }
            long r4 = r4 - r6
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x02ed
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 % r6
            r6 = 1
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x026b
            com.google.android.gms.internal.zzcfl r4 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ all -> 0x02b0 }
            java.lang.String r5 = "Data loss. Too many events logged. appId, count"
            java.lang.Object r3 = com.google.android.gms.internal.zzcfl.zzdZ(r3)     // Catch:{ all -> 0x02b0 }
            long r6 = r2.zzbpv     // Catch:{ all -> 0x02b0 }
            java.lang.Long r2 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x02b0 }
            r4.zze(r5, r3, r2)     // Catch:{ all -> 0x02b0 }
        L_0x026b:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            r2.setTransactionSuccessful()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.endTransaction()
            goto L_0x0025
        L_0x027b:
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()     // Catch:{ all -> 0x02b0 }
            java.lang.String r6 = "Data lost. Currency value is too big. appId"
            java.lang.Object r3 = com.google.android.gms.internal.zzcfl.zzdZ(r3)     // Catch:{ all -> 0x02b0 }
            java.lang.Double r4 = java.lang.Double.valueOf(r4)     // Catch:{ all -> 0x02b0 }
            r2.zze(r6, r3, r4)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            r2.setTransactionSuccessful()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.endTransaction()
            goto L_0x0025
        L_0x02a0:
            java.lang.String r4 = "value"
            long r4 = r14.getLong(r4)     // Catch:{ all -> 0x02b0 }
            r8 = r4
            goto L_0x016a
        L_0x02a9:
            java.lang.String r5 = new java.lang.String     // Catch:{ all -> 0x02b0 }
            r5.<init>(r4)     // Catch:{ all -> 0x02b0 }
            goto L_0x0192
        L_0x02b0:
            r2 = move-exception
            com.google.android.gms.internal.zzcen r3 = r18.zzwz()
            r3.endTransaction()
            throw r2
        L_0x02b9:
            r2 = move-exception
            com.google.android.gms.internal.zzcfl r4 = r4.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ all -> 0x02b0 }
            java.lang.String r6 = "Error pruning currencies. appId"
            java.lang.Object r7 = com.google.android.gms.internal.zzcfl.zzdZ(r3)     // Catch:{ all -> 0x02b0 }
            r4.zze(r6, r7, r2)     // Catch:{ all -> 0x02b0 }
            goto L_0x01d4
        L_0x02cd:
            java.lang.Object r2 = r2.mValue     // Catch:{ all -> 0x02b0 }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ all -> 0x02b0 }
            long r10 = r2.longValue()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcjk r2 = new com.google.android.gms.internal.zzcjk     // Catch:{ all -> 0x02b0 }
            r0 = r19
            java.lang.String r4 = r0.zzbpc     // Catch:{ all -> 0x02b0 }
            r0 = r18
            com.google.android.gms.common.util.zze r6 = r0.zzvw     // Catch:{ all -> 0x02b0 }
            long r6 = r6.currentTimeMillis()     // Catch:{ all -> 0x02b0 }
            long r8 = r8 + r10
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x02b0 }
            r2.<init>(r3, r4, r5, r6, r8)     // Catch:{ all -> 0x02b0 }
            goto L_0x01e9
        L_0x02ed:
            if (r10 == 0) goto L_0x033c
            long r4 = r2.zzbpu     // Catch:{ all -> 0x02b0 }
            long r6 = com.google.android.gms.internal.zzcem.zzxr()     // Catch:{ all -> 0x02b0 }
            long r4 = r4 - r6
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x033c
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 % r6
            r6 = 1
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x031c
            com.google.android.gms.internal.zzcfl r4 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ all -> 0x02b0 }
            java.lang.String r5 = "Data loss. Too many public events logged. appId, count"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r3)     // Catch:{ all -> 0x02b0 }
            long r8 = r2.zzbpu     // Catch:{ all -> 0x02b0 }
            java.lang.Long r2 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x02b0 }
            r4.zze(r5, r6, r2)     // Catch:{ all -> 0x02b0 }
        L_0x031c:
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x02b0 }
            r4 = 16
            java.lang.String r5 = "_ev"
            r0 = r19
            java.lang.String r6 = r0.name     // Catch:{ all -> 0x02b0 }
            r7 = 0
            r2.zza((java.lang.String) r3, (int) r4, (java.lang.String) r5, (java.lang.String) r6, (int) r7)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            r2.setTransactionSuccessful()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.endTransaction()
            goto L_0x0025
        L_0x033c:
            if (r12 == 0) goto L_0x038f
            long r4 = r2.zzbpx     // Catch:{ all -> 0x02b0 }
            r0 = r18
            com.google.android.gms.internal.zzcem r6 = r0.zzbsn     // Catch:{ all -> 0x02b0 }
            r0 = r20
            java.lang.String r7 = r0.packageName     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfc<java.lang.Integer> r8 = com.google.android.gms.internal.zzcfb.zzbqg     // Catch:{ all -> 0x02b0 }
            int r6 = r6.zzb(r7, r8)     // Catch:{ all -> 0x02b0 }
            r7 = 1000000(0xf4240, float:1.401298E-39)
            int r6 = java.lang.Math.min(r7, r6)     // Catch:{ all -> 0x02b0 }
            r7 = 0
            int r6 = java.lang.Math.max(r7, r6)     // Catch:{ all -> 0x02b0 }
            long r6 = (long) r6     // Catch:{ all -> 0x02b0 }
            long r4 = r4 - r6
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x038f
            r6 = 1
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x037f
            com.google.android.gms.internal.zzcfl r4 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ all -> 0x02b0 }
            java.lang.String r5 = "Too many error events logged. appId, count"
            java.lang.Object r3 = com.google.android.gms.internal.zzcfl.zzdZ(r3)     // Catch:{ all -> 0x02b0 }
            long r6 = r2.zzbpx     // Catch:{ all -> 0x02b0 }
            java.lang.Long r2 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x02b0 }
            r4.zze(r5, r3, r2)     // Catch:{ all -> 0x02b0 }
        L_0x037f:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            r2.setTransactionSuccessful()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.endTransaction()
            goto L_0x0025
        L_0x038f:
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x02b0 }
            java.lang.String r4 = "_o"
            r0 = r19
            java.lang.String r5 = r0.zzbpc     // Catch:{ all -> 0x02b0 }
            r2.zza((android.os.Bundle) r14, (java.lang.String) r4, (java.lang.Object) r5)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x02b0 }
            boolean r2 = r2.zzey(r3)     // Catch:{ all -> 0x02b0 }
            if (r2 == 0) goto L_0x03c4
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x02b0 }
            java.lang.String r4 = "_dbg"
            r6 = 1
            java.lang.Long r5 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x02b0 }
            r2.zza((android.os.Bundle) r14, (java.lang.String) r4, (java.lang.Object) r5)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x02b0 }
            java.lang.String r4 = "_r"
            r6 = 1
            java.lang.Long r5 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x02b0 }
            r2.zza((android.os.Bundle) r14, (java.lang.String) r4, (java.lang.Object) r5)     // Catch:{ all -> 0x02b0 }
        L_0x03c4:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            long r4 = r2.zzdR(r3)     // Catch:{ all -> 0x02b0 }
            r6 = 0
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x03e7
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()     // Catch:{ all -> 0x02b0 }
            java.lang.String r6 = "Data lost. Too many events stored on disk, deleted. appId"
            java.lang.Object r7 = com.google.android.gms.internal.zzcfl.zzdZ(r3)     // Catch:{ all -> 0x02b0 }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x02b0 }
            r2.zze(r6, r7, r4)     // Catch:{ all -> 0x02b0 }
        L_0x03e7:
            com.google.android.gms.internal.zzceu r5 = new com.google.android.gms.internal.zzceu     // Catch:{ all -> 0x02b0 }
            r0 = r19
            java.lang.String r7 = r0.zzbpc     // Catch:{ all -> 0x02b0 }
            r0 = r19
            java.lang.String r9 = r0.name     // Catch:{ all -> 0x02b0 }
            r0 = r19
            long r10 = r0.zzbpN     // Catch:{ all -> 0x02b0 }
            r12 = 0
            r6 = r18
            r8 = r3
            r5.<init>((com.google.android.gms.internal.zzcgl) r6, (java.lang.String) r7, (java.lang.String) r8, (java.lang.String) r9, (long) r10, (long) r12, (android.os.Bundle) r14)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            java.lang.String r4 = r5.mName     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcev r2 = r2.zzE(r3, r4)     // Catch:{ all -> 0x02b0 }
            if (r2 != 0) goto L_0x04c0
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            long r6 = r2.zzdU(r3)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcem.zzxp()     // Catch:{ all -> 0x02b0 }
            r8 = 500(0x1f4, double:2.47E-321)
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 < 0) goto L_0x0452
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x02b0 }
            java.lang.String r4 = "Too many event names used, ignoring event. appId, name, supported count"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r3)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfj r7 = r18.zzwA()     // Catch:{ all -> 0x02b0 }
            java.lang.String r5 = r5.mName     // Catch:{ all -> 0x02b0 }
            java.lang.String r5 = r7.zzdW(r5)     // Catch:{ all -> 0x02b0 }
            int r7 = com.google.android.gms.internal.zzcem.zzxp()     // Catch:{ all -> 0x02b0 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x02b0 }
            r2.zzd(r4, r6, r5, r7)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x02b0 }
            r4 = 8
            r5 = 0
            r6 = 0
            r7 = 0
            r2.zza((java.lang.String) r3, (int) r4, (java.lang.String) r5, (java.lang.String) r6, (int) r7)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.endTransaction()
            goto L_0x0025
        L_0x0452:
            com.google.android.gms.internal.zzcev r7 = new com.google.android.gms.internal.zzcev     // Catch:{ all -> 0x02b0 }
            java.lang.String r9 = r5.mName     // Catch:{ all -> 0x02b0 }
            r10 = 0
            r12 = 0
            long r14 = r5.zzayS     // Catch:{ all -> 0x02b0 }
            r8 = r3
            r7.<init>(r8, r9, r10, r12, r14)     // Catch:{ all -> 0x02b0 }
        L_0x0460:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            r2.zza((com.google.android.gms.internal.zzcev) r7)     // Catch:{ all -> 0x02b0 }
            r0 = r18
            r1 = r20
            r0.zza((com.google.android.gms.internal.zzceu) r5, (com.google.android.gms.internal.zzceh) r1)     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x02b0 }
            r2.setTransactionSuccessful()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            r3 = 2
            boolean r2 = r2.zzz(r3)     // Catch:{ all -> 0x02b0 }
            if (r2 == 0) goto L_0x0495
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyD()     // Catch:{ all -> 0x02b0 }
            java.lang.String r3 = "Event recorded"
            com.google.android.gms.internal.zzcfj r4 = r18.zzwA()     // Catch:{ all -> 0x02b0 }
            java.lang.String r4 = r4.zza((com.google.android.gms.internal.zzceu) r5)     // Catch:{ all -> 0x02b0 }
            r2.zzj(r3, r4)     // Catch:{ all -> 0x02b0 }
        L_0x0495:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.endTransaction()
            r18.zzzc()
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()
            com.google.android.gms.internal.zzcfn r2 = r2.zzyD()
            java.lang.String r3 = "Background event processing time, ms"
            long r4 = java.lang.System.nanoTime()
            long r4 = r4 - r16
            r6 = 500000(0x7a120, double:2.47033E-318)
            long r4 = r4 + r6
            r6 = 1000000(0xf4240, double:4.940656E-318)
            long r4 = r4 / r6
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r2.zzj(r3, r4)
            goto L_0x0025
        L_0x04c0:
            long r6 = r2.zzbpI     // Catch:{ all -> 0x02b0 }
            r0 = r18
            com.google.android.gms.internal.zzceu r5 = r5.zza((com.google.android.gms.internal.zzcgl) r0, (long) r6)     // Catch:{ all -> 0x02b0 }
            long r6 = r5.zzayS     // Catch:{ all -> 0x02b0 }
            com.google.android.gms.internal.zzcev r7 = r2.zzab(r6)     // Catch:{ all -> 0x02b0 }
            goto L_0x0460
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcgl.zzc(com.google.android.gms.internal.zzcez, com.google.android.gms.internal.zzceh):void");
    }

    @WorkerThread
    private final zzceh zzel(String str) {
        zzceg zzdQ = zzwz().zzdQ(str);
        if (zzdQ == null || TextUtils.isEmpty(zzdQ.zzjH())) {
            zzwF().zzyC().zzj("No app data available; dropping", str);
            return null;
        }
        try {
            String str2 = zzbha.zzaP(this.mContext).getPackageInfo(str, 0).versionName;
            if (zzdQ.zzjH() != null && !zzdQ.zzjH().equals(str2)) {
                zzwF().zzyz().zzj("App version does not match; dropping. appId", zzcfl.zzdZ(str));
                return null;
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return new zzceh(str, zzdQ.getGmpAppId(), zzdQ.zzjH(), zzdQ.zzwN(), zzdQ.zzwO(), zzdQ.zzwP(), zzdQ.zzwQ(), (String) null, zzdQ.zzwR(), false, zzdQ.zzwK(), zzdQ.zzxe(), 0, 0);
    }

    @WorkerThread
    private final void zzf(zzceh zzceh) {
        boolean z = true;
        zzwE().zzjC();
        zzkD();
        zzbo.zzu(zzceh);
        zzbo.zzcF(zzceh.packageName);
        zzceg zzdQ = zzwz().zzdQ(zzceh.packageName);
        String zzec = zzwG().zzec(zzceh.packageName);
        boolean z2 = false;
        if (zzdQ == null) {
            zzdQ = new zzceg(this, zzceh.packageName);
            zzdQ.zzdG(zzwu().zzyu());
            zzdQ.zzdI(zzec);
            z2 = true;
        } else if (!zzec.equals(zzdQ.zzwJ())) {
            zzdQ.zzdI(zzec);
            zzdQ.zzdG(zzwu().zzyu());
            z2 = true;
        }
        if (!TextUtils.isEmpty(zzceh.zzboQ) && !zzceh.zzboQ.equals(zzdQ.getGmpAppId())) {
            zzdQ.zzdH(zzceh.zzboQ);
            z2 = true;
        }
        if (!TextUtils.isEmpty(zzceh.zzboY) && !zzceh.zzboY.equals(zzdQ.zzwK())) {
            zzdQ.zzdJ(zzceh.zzboY);
            z2 = true;
        }
        if (!(zzceh.zzboS == 0 || zzceh.zzboS == zzdQ.zzwP())) {
            zzdQ.zzO(zzceh.zzboS);
            z2 = true;
        }
        if (!TextUtils.isEmpty(zzceh.zzbgW) && !zzceh.zzbgW.equals(zzdQ.zzjH())) {
            zzdQ.setAppVersion(zzceh.zzbgW);
            z2 = true;
        }
        if (zzceh.zzboX != zzdQ.zzwN()) {
            zzdQ.zzN(zzceh.zzboX);
            z2 = true;
        }
        if (zzceh.zzboR != null && !zzceh.zzboR.equals(zzdQ.zzwO())) {
            zzdQ.zzdK(zzceh.zzboR);
            z2 = true;
        }
        if (zzceh.zzboT != zzdQ.zzwQ()) {
            zzdQ.zzP(zzceh.zzboT);
            z2 = true;
        }
        if (zzceh.zzboV != zzdQ.zzwR()) {
            zzdQ.setMeasurementEnabled(zzceh.zzboV);
            z2 = true;
        }
        if (!TextUtils.isEmpty(zzceh.zzboU) && !zzceh.zzboU.equals(zzdQ.zzxc())) {
            zzdQ.zzdL(zzceh.zzboU);
            z2 = true;
        }
        if (zzceh.zzboZ != zzdQ.zzxe()) {
            zzdQ.zzZ(zzceh.zzboZ);
        } else {
            z = z2;
        }
        if (z) {
            zzwz().zza(zzdQ);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0364, code lost:
        if (com.google.android.gms.internal.zzcjl.zzeC(r15.zztH.get(r14).name) != false) goto L_0x0366;
     */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x0331 A[Catch:{ IOException -> 0x0298, all -> 0x019d }] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x0335 A[Catch:{ IOException -> 0x0298, all -> 0x019d }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008d A[Catch:{ IOException -> 0x0298, all -> 0x019d }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0090 A[Catch:{ IOException -> 0x0298, all -> 0x019d }] */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x0776 A[Catch:{ IOException -> 0x0298, all -> 0x019d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzg(java.lang.String r19, long r20) {
        /*
            r18 = this;
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.beginTransaction()
            com.google.android.gms.internal.zzcgl$zza r15 = new com.google.android.gms.internal.zzcgl$zza     // Catch:{ all -> 0x019d }
            r2 = 0
            r0 = r18
            r15.<init>(r0, r2)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcen r14 = r18.zzwz()     // Catch:{ all -> 0x019d }
            r4 = 0
            r0 = r18
            long r0 = r0.zzbsS     // Catch:{ all -> 0x019d }
            r16 = r0
            com.google.android.gms.common.internal.zzbo.zzu(r15)     // Catch:{ all -> 0x019d }
            r14.zzjC()     // Catch:{ all -> 0x019d }
            r14.zzkD()     // Catch:{ all -> 0x019d }
            r3 = 0
            android.database.sqlite.SQLiteDatabase r2 = r14.getWritableDatabase()     // Catch:{ SQLiteException -> 0x078b }
            r5 = 0
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ SQLiteException -> 0x078b }
            if (r5 == 0) goto L_0x01a6
            r6 = -1
            int r5 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1))
            if (r5 == 0) goto L_0x013f
            r5 = 2
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x078b }
            r6 = 0
            java.lang.String r7 = java.lang.String.valueOf(r16)     // Catch:{ SQLiteException -> 0x078b }
            r5[r6] = r7     // Catch:{ SQLiteException -> 0x078b }
            r6 = 1
            java.lang.String r7 = java.lang.String.valueOf(r20)     // Catch:{ SQLiteException -> 0x078b }
            r5[r6] = r7     // Catch:{ SQLiteException -> 0x078b }
            r6 = r5
        L_0x0047:
            r8 = -1
            int r5 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1))
            if (r5 == 0) goto L_0x014c
            java.lang.String r5 = "rowid <= ? and "
        L_0x004f:
            java.lang.String r7 = java.lang.String.valueOf(r5)     // Catch:{ SQLiteException -> 0x078b }
            int r7 = r7.length()     // Catch:{ SQLiteException -> 0x078b }
            int r7 = r7 + 148
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x078b }
            r8.<init>(r7)     // Catch:{ SQLiteException -> 0x078b }
            java.lang.String r7 = "select app_id, metadata_fingerprint from raw_events where "
            java.lang.StringBuilder r7 = r8.append(r7)     // Catch:{ SQLiteException -> 0x078b }
            java.lang.StringBuilder r5 = r7.append(r5)     // Catch:{ SQLiteException -> 0x078b }
            java.lang.String r7 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;"
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ SQLiteException -> 0x078b }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLiteException -> 0x078b }
            android.database.Cursor r3 = r2.rawQuery(r5, r6)     // Catch:{ SQLiteException -> 0x078b }
            boolean r5 = r3.moveToFirst()     // Catch:{ SQLiteException -> 0x078b }
            if (r5 != 0) goto L_0x0150
            if (r3 == 0) goto L_0x0081
            r3.close()     // Catch:{ all -> 0x019d }
        L_0x0081:
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            if (r2 == 0) goto L_0x008d
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x019d }
            if (r2 == 0) goto L_0x0335
        L_0x008d:
            r2 = 1
        L_0x008e:
            if (r2 != 0) goto L_0x0776
            r13 = 0
            com.google.android.gms.internal.zzcjz r0 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            r16 = r0
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            int r2 = r2.size()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw[] r2 = new com.google.android.gms.internal.zzcjw[r2]     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbvE = r2     // Catch:{ all -> 0x019d }
            r12 = 0
            r2 = 0
            r14 = r2
        L_0x00a4:
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            int r2 = r2.size()     // Catch:{ all -> 0x019d }
            if (r14 >= r2) goto L_0x05be
            com.google.android.gms.internal.zzcgf r3 = r18.zzwC()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r2 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r4 = r2.zzaH     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.name     // Catch:{ all -> 0x019d }
            boolean r2 = r3.zzN(r4, r2)     // Catch:{ all -> 0x019d }
            if (r2 == 0) goto L_0x033b
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfn r3 = r2.zzyz()     // Catch:{ all -> 0x019d }
            java.lang.String r4 = "Dropping blacklisted raw event. appId"
            com.google.android.gms.internal.zzcjz r2 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.zzaH     // Catch:{ all -> 0x019d }
            java.lang.Object r5 = com.google.android.gms.internal.zzcfl.zzdZ(r2)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfj r6 = r18.zzwA()     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.name     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r6.zzdW(r2)     // Catch:{ all -> 0x019d }
            r3.zze(r4, r5, r2)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r3 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r3 = r3.zzaH     // Catch:{ all -> 0x019d }
            boolean r2 = r2.zzeA(r3)     // Catch:{ all -> 0x019d }
            if (r2 != 0) goto L_0x0107
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r3 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r3 = r3.zzaH     // Catch:{ all -> 0x019d }
            boolean r2 = r2.zzeB(r3)     // Catch:{ all -> 0x019d }
            if (r2 == 0) goto L_0x0338
        L_0x0107:
            r2 = 1
        L_0x0108:
            if (r2 != 0) goto L_0x07a1
            java.lang.String r3 = "_err"
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.name     // Catch:{ all -> 0x019d }
            boolean r2 = r3.equals(r2)     // Catch:{ all -> 0x019d }
            if (r2 != 0) goto L_0x07a1
            com.google.android.gms.internal.zzcjl r2 = r18.zzwB()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r3 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r3 = r3.zzaH     // Catch:{ all -> 0x019d }
            r4 = 11
            java.lang.String r5 = "_ev"
            java.util.List<com.google.android.gms.internal.zzcjw> r6 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r6 = r6.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r6 = (com.google.android.gms.internal.zzcjw) r6     // Catch:{ all -> 0x019d }
            java.lang.String r6 = r6.name     // Catch:{ all -> 0x019d }
            r7 = 0
            r2.zza((java.lang.String) r3, (int) r4, (java.lang.String) r5, (java.lang.String) r6, (int) r7)     // Catch:{ all -> 0x019d }
            r2 = r12
            r4 = r13
        L_0x0138:
            int r3 = r14 + 1
            r14 = r3
            r12 = r2
            r13 = r4
            goto L_0x00a4
        L_0x013f:
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x078b }
            r6 = 0
            java.lang.String r7 = java.lang.String.valueOf(r20)     // Catch:{ SQLiteException -> 0x078b }
            r5[r6] = r7     // Catch:{ SQLiteException -> 0x078b }
            r6 = r5
            goto L_0x0047
        L_0x014c:
            java.lang.String r5 = ""
            goto L_0x004f
        L_0x0150:
            r5 = 0
            java.lang.String r4 = r3.getString(r5)     // Catch:{ SQLiteException -> 0x078b }
            r5 = 1
            java.lang.String r5 = r3.getString(r5)     // Catch:{ SQLiteException -> 0x078b }
            r3.close()     // Catch:{ SQLiteException -> 0x078b }
            r13 = r5
            r11 = r3
            r12 = r4
        L_0x0160:
            java.lang.String r3 = "raw_events_metadata"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r5 = 0
            java.lang.String r6 = "metadata"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            java.lang.String r5 = "app_id = ? and metadata_fingerprint = ?"
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r7 = 0
            r6[r7] = r12     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r7 = 1
            r6[r7] = r13     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r7 = 0
            r8 = 0
            java.lang.String r9 = "rowid"
            java.lang.String r10 = "2"
            android.database.Cursor r11 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            boolean r3 = r11.moveToFirst()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            if (r3 != 0) goto L_0x0210
            com.google.android.gms.internal.zzcfl r2 = r14.zzwF()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            java.lang.String r3 = "Raw event metadata record is missing. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r2.zzj(r3, r4)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            if (r11 == 0) goto L_0x0081
            r11.close()     // Catch:{ all -> 0x019d }
            goto L_0x0081
        L_0x019d:
            r2 = move-exception
            com.google.android.gms.internal.zzcen r3 = r18.zzwz()
            r3.endTransaction()
            throw r2
        L_0x01a6:
            r6 = -1
            int r5 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1))
            if (r5 == 0) goto L_0x01f7
            r5 = 2
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x078b }
            r6 = 0
            r7 = 0
            r5[r6] = r7     // Catch:{ SQLiteException -> 0x078b }
            r6 = 1
            java.lang.String r7 = java.lang.String.valueOf(r16)     // Catch:{ SQLiteException -> 0x078b }
            r5[r6] = r7     // Catch:{ SQLiteException -> 0x078b }
            r6 = r5
        L_0x01bb:
            r8 = -1
            int r5 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1))
            if (r5 == 0) goto L_0x0200
            java.lang.String r5 = " and rowid <= ?"
        L_0x01c3:
            java.lang.String r7 = java.lang.String.valueOf(r5)     // Catch:{ SQLiteException -> 0x078b }
            int r7 = r7.length()     // Catch:{ SQLiteException -> 0x078b }
            int r7 = r7 + 84
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x078b }
            r8.<init>(r7)     // Catch:{ SQLiteException -> 0x078b }
            java.lang.String r7 = "select metadata_fingerprint from raw_events where app_id = ?"
            java.lang.StringBuilder r7 = r8.append(r7)     // Catch:{ SQLiteException -> 0x078b }
            java.lang.StringBuilder r5 = r7.append(r5)     // Catch:{ SQLiteException -> 0x078b }
            java.lang.String r7 = " order by rowid limit 1;"
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ SQLiteException -> 0x078b }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLiteException -> 0x078b }
            android.database.Cursor r3 = r2.rawQuery(r5, r6)     // Catch:{ SQLiteException -> 0x078b }
            boolean r5 = r3.moveToFirst()     // Catch:{ SQLiteException -> 0x078b }
            if (r5 != 0) goto L_0x0203
            if (r3 == 0) goto L_0x0081
            r3.close()     // Catch:{ all -> 0x019d }
            goto L_0x0081
        L_0x01f7:
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x078b }
            r6 = 0
            r7 = 0
            r5[r6] = r7     // Catch:{ SQLiteException -> 0x078b }
            r6 = r5
            goto L_0x01bb
        L_0x0200:
            java.lang.String r5 = ""
            goto L_0x01c3
        L_0x0203:
            r5 = 0
            java.lang.String r5 = r3.getString(r5)     // Catch:{ SQLiteException -> 0x078b }
            r3.close()     // Catch:{ SQLiteException -> 0x078b }
            r13 = r5
            r11 = r3
            r12 = r4
            goto L_0x0160
        L_0x0210:
            r3 = 0
            byte[] r3 = r11.getBlob(r3)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r4 = 0
            int r5 = r3.length     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            com.google.android.gms.internal.adg r3 = com.google.android.gms.internal.adg.zzb(r3, r4, r5)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            com.google.android.gms.internal.zzcjz r4 = new com.google.android.gms.internal.zzcjz     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r4.<init>()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r4.zza((com.google.android.gms.internal.adg) r3)     // Catch:{ IOException -> 0x0298 }
            boolean r3 = r11.moveToNext()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            if (r3 == 0) goto L_0x023a
            com.google.android.gms.internal.zzcfl r3 = r14.zzwF()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            com.google.android.gms.internal.zzcfn r3 = r3.zzyz()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            java.lang.String r5 = "Get multiple raw event metadata records, expected one. appId"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r3.zzj(r5, r6)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
        L_0x023a:
            r11.close()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r15.zzb(r4)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r4 = -1
            int r3 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x02b1
            java.lang.String r5 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?"
            r3 = 3
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r3 = 0
            r6[r3] = r12     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r3 = 1
            r6[r3] = r13     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r3 = 2
            java.lang.String r4 = java.lang.String.valueOf(r16)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r6[r3] = r4     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
        L_0x0258:
            java.lang.String r3 = "raw_events"
            r4 = 4
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r7 = 0
            java.lang.String r8 = "rowid"
            r4[r7] = r8     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r7 = 1
            java.lang.String r8 = "name"
            r4[r7] = r8     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r7 = 2
            java.lang.String r8 = "timestamp"
            r4[r7] = r8     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r7 = 3
            java.lang.String r8 = "data"
            r4[r7] = r8     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r7 = 0
            r8 = 0
            java.lang.String r9 = "rowid"
            r10 = 0
            android.database.Cursor r3 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            boolean r2 = r3.moveToFirst()     // Catch:{ SQLiteException -> 0x078e }
            if (r2 != 0) goto L_0x02d8
            com.google.android.gms.internal.zzcfl r2 = r14.zzwF()     // Catch:{ SQLiteException -> 0x078e }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()     // Catch:{ SQLiteException -> 0x078e }
            java.lang.String r4 = "Raw event data disappeared while in transaction. appId"
            java.lang.Object r5 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x078e }
            r2.zzj(r4, r5)     // Catch:{ SQLiteException -> 0x078e }
            if (r3 == 0) goto L_0x0081
            r3.close()     // Catch:{ all -> 0x019d }
            goto L_0x0081
        L_0x0298:
            r2 = move-exception
            com.google.android.gms.internal.zzcfl r3 = r14.zzwF()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            com.google.android.gms.internal.zzcfn r3 = r3.zzyx()     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            java.lang.String r4 = "Data loss. Failed to merge raw event metadata. appId"
            java.lang.Object r5 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r3.zze(r4, r5, r2)     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            if (r11 == 0) goto L_0x0081
            r11.close()     // Catch:{ all -> 0x019d }
            goto L_0x0081
        L_0x02b1:
            java.lang.String r5 = "app_id = ? and metadata_fingerprint = ?"
            r3 = 2
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r3 = 0
            r6[r3] = r12     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            r3 = 1
            r6[r3] = r13     // Catch:{ SQLiteException -> 0x02bd, all -> 0x0787 }
            goto L_0x0258
        L_0x02bd:
            r2 = move-exception
            r3 = r11
            r4 = r12
        L_0x02c0:
            com.google.android.gms.internal.zzcfl r5 = r14.zzwF()     // Catch:{ all -> 0x032e }
            com.google.android.gms.internal.zzcfn r5 = r5.zzyx()     // Catch:{ all -> 0x032e }
            java.lang.String r6 = "Data loss. Error selecting raw event. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r4)     // Catch:{ all -> 0x032e }
            r5.zze(r6, r4, r2)     // Catch:{ all -> 0x032e }
            if (r3 == 0) goto L_0x0081
            r3.close()     // Catch:{ all -> 0x019d }
            goto L_0x0081
        L_0x02d8:
            r2 = 0
            long r4 = r3.getLong(r2)     // Catch:{ SQLiteException -> 0x078e }
            r2 = 3
            byte[] r2 = r3.getBlob(r2)     // Catch:{ SQLiteException -> 0x078e }
            r6 = 0
            int r7 = r2.length     // Catch:{ SQLiteException -> 0x078e }
            com.google.android.gms.internal.adg r2 = com.google.android.gms.internal.adg.zzb(r2, r6, r7)     // Catch:{ SQLiteException -> 0x078e }
            com.google.android.gms.internal.zzcjw r6 = new com.google.android.gms.internal.zzcjw     // Catch:{ SQLiteException -> 0x078e }
            r6.<init>()     // Catch:{ SQLiteException -> 0x078e }
            r6.zza((com.google.android.gms.internal.adg) r2)     // Catch:{ IOException -> 0x030f }
            r2 = 1
            java.lang.String r2 = r3.getString(r2)     // Catch:{ SQLiteException -> 0x078e }
            r6.name = r2     // Catch:{ SQLiteException -> 0x078e }
            r2 = 2
            long r8 = r3.getLong(r2)     // Catch:{ SQLiteException -> 0x078e }
            java.lang.Long r2 = java.lang.Long.valueOf(r8)     // Catch:{ SQLiteException -> 0x078e }
            r6.zzbvx = r2     // Catch:{ SQLiteException -> 0x078e }
            boolean r2 = r15.zza(r4, r6)     // Catch:{ SQLiteException -> 0x078e }
            if (r2 != 0) goto L_0x0321
            if (r3 == 0) goto L_0x0081
            r3.close()     // Catch:{ all -> 0x019d }
            goto L_0x0081
        L_0x030f:
            r2 = move-exception
            com.google.android.gms.internal.zzcfl r4 = r14.zzwF()     // Catch:{ SQLiteException -> 0x078e }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ SQLiteException -> 0x078e }
            java.lang.String r5 = "Data loss. Failed to merge raw event. appId"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x078e }
            r4.zze(r5, r6, r2)     // Catch:{ SQLiteException -> 0x078e }
        L_0x0321:
            boolean r2 = r3.moveToNext()     // Catch:{ SQLiteException -> 0x078e }
            if (r2 != 0) goto L_0x02d8
            if (r3 == 0) goto L_0x0081
            r3.close()     // Catch:{ all -> 0x019d }
            goto L_0x0081
        L_0x032e:
            r2 = move-exception
        L_0x032f:
            if (r3 == 0) goto L_0x0334
            r3.close()     // Catch:{ all -> 0x019d }
        L_0x0334:
            throw r2     // Catch:{ all -> 0x019d }
        L_0x0335:
            r2 = 0
            goto L_0x008e
        L_0x0338:
            r2 = 0
            goto L_0x0108
        L_0x033b:
            com.google.android.gms.internal.zzcgf r3 = r18.zzwC()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r2 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r4 = r2.zzaH     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.name     // Catch:{ all -> 0x019d }
            boolean r17 = r3.zzO(r4, r2)     // Catch:{ all -> 0x019d }
            if (r17 != 0) goto L_0x0366
            r18.zzwB()     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.name     // Catch:{ all -> 0x019d }
            boolean r2 = com.google.android.gms.internal.zzcjl.zzeC(r2)     // Catch:{ all -> 0x019d }
            if (r2 == 0) goto L_0x05bc
        L_0x0366:
            r3 = 0
            r4 = 0
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx[] r2 = r2.zzbvw     // Catch:{ all -> 0x019d }
            if (r2 != 0) goto L_0x0381
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            r5 = 0
            com.google.android.gms.internal.zzcjx[] r5 = new com.google.android.gms.internal.zzcjx[r5]     // Catch:{ all -> 0x019d }
            r2.zzbvw = r5     // Catch:{ all -> 0x019d }
        L_0x0381:
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx[] r6 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r7 = r6.length     // Catch:{ all -> 0x019d }
            r2 = 0
            r5 = r2
        L_0x038e:
            if (r5 >= r7) goto L_0x03be
            r2 = r6[r5]     // Catch:{ all -> 0x019d }
            java.lang.String r8 = "_c"
            java.lang.String r9 = r2.name     // Catch:{ all -> 0x019d }
            boolean r8 = r8.equals(r9)     // Catch:{ all -> 0x019d }
            if (r8 == 0) goto L_0x03aa
            r8 = 1
            java.lang.Long r3 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x019d }
            r2.zzbvA = r3     // Catch:{ all -> 0x019d }
            r3 = 1
            r2 = r4
        L_0x03a6:
            int r5 = r5 + 1
            r4 = r2
            goto L_0x038e
        L_0x03aa:
            java.lang.String r8 = "_r"
            java.lang.String r9 = r2.name     // Catch:{ all -> 0x019d }
            boolean r8 = r8.equals(r9)     // Catch:{ all -> 0x019d }
            if (r8 == 0) goto L_0x079e
            r8 = 1
            java.lang.Long r4 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x019d }
            r2.zzbvA = r4     // Catch:{ all -> 0x019d }
            r2 = 1
            goto L_0x03a6
        L_0x03be:
            if (r3 != 0) goto L_0x041e
            if (r17 == 0) goto L_0x041e
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfn r3 = r2.zzyD()     // Catch:{ all -> 0x019d }
            java.lang.String r5 = "Marking event as conversion"
            com.google.android.gms.internal.zzcfj r6 = r18.zzwA()     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.name     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r6.zzdW(r2)     // Catch:{ all -> 0x019d }
            r3.zzj(r5, r2)     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx[] r3 = r2.zzbvw     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx[] r2 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r2 = r2.length     // Catch:{ all -> 0x019d }
            int r2 = r2 + 1
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r3, r2)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx[] r2 = (com.google.android.gms.internal.zzcjx[]) r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx r3 = new com.google.android.gms.internal.zzcjx     // Catch:{ all -> 0x019d }
            r3.<init>()     // Catch:{ all -> 0x019d }
            java.lang.String r5 = "_c"
            r3.name = r5     // Catch:{ all -> 0x019d }
            r6 = 1
            java.lang.Long r5 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x019d }
            r3.zzbvA = r5     // Catch:{ all -> 0x019d }
            int r5 = r2.length     // Catch:{ all -> 0x019d }
            int r5 = r5 + -1
            r2[r5] = r3     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r3 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r3 = r3.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r3 = (com.google.android.gms.internal.zzcjw) r3     // Catch:{ all -> 0x019d }
            r3.zzbvw = r2     // Catch:{ all -> 0x019d }
        L_0x041e:
            if (r4 != 0) goto L_0x047c
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfn r3 = r2.zzyD()     // Catch:{ all -> 0x019d }
            java.lang.String r4 = "Marking event as real-time"
            com.google.android.gms.internal.zzcfj r5 = r18.zzwA()     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.name     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r5.zzdW(r2)     // Catch:{ all -> 0x019d }
            r3.zzj(r4, r2)     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx[] r3 = r2.zzbvw     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx[] r2 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r2 = r2.length     // Catch:{ all -> 0x019d }
            int r2 = r2 + 1
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r3, r2)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx[] r2 = (com.google.android.gms.internal.zzcjx[]) r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjx r3 = new com.google.android.gms.internal.zzcjx     // Catch:{ all -> 0x019d }
            r3.<init>()     // Catch:{ all -> 0x019d }
            java.lang.String r4 = "_r"
            r3.name = r4     // Catch:{ all -> 0x019d }
            r4 = 1
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x019d }
            r3.zzbvA = r4     // Catch:{ all -> 0x019d }
            int r4 = r2.length     // Catch:{ all -> 0x019d }
            int r4 = r4 + -1
            r2[r4] = r3     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r3 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r3 = r3.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r3 = (com.google.android.gms.internal.zzcjw) r3     // Catch:{ all -> 0x019d }
            r3.zzbvw = r2     // Catch:{ all -> 0x019d }
        L_0x047c:
            r2 = 1
            com.google.android.gms.internal.zzcen r3 = r18.zzwz()     // Catch:{ all -> 0x019d }
            long r4 = r18.zzyZ()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r6 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r6 = r6.zzaH     // Catch:{ all -> 0x019d }
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 1
            com.google.android.gms.internal.zzceo r3 = r3.zza(r4, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x019d }
            long r4 = r3.zzbpy     // Catch:{ all -> 0x019d }
            r0 = r18
            com.google.android.gms.internal.zzcem r3 = r0.zzbsn     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r6 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r6 = r6.zzaH     // Catch:{ all -> 0x019d }
            int r3 = r3.zzdM(r6)     // Catch:{ all -> 0x019d }
            long r6 = (long) r3     // Catch:{ all -> 0x019d }
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x079b
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            r3 = 0
        L_0x04ae:
            com.google.android.gms.internal.zzcjx[] r4 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r4 = r4.length     // Catch:{ all -> 0x019d }
            if (r3 >= r4) goto L_0x04df
            java.lang.String r4 = "_r"
            com.google.android.gms.internal.zzcjx[] r5 = r2.zzbvw     // Catch:{ all -> 0x019d }
            r5 = r5[r3]     // Catch:{ all -> 0x019d }
            java.lang.String r5 = r5.name     // Catch:{ all -> 0x019d }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x019d }
            if (r4 == 0) goto L_0x0551
            com.google.android.gms.internal.zzcjx[] r4 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r4 = r4.length     // Catch:{ all -> 0x019d }
            int r4 = r4 + -1
            com.google.android.gms.internal.zzcjx[] r4 = new com.google.android.gms.internal.zzcjx[r4]     // Catch:{ all -> 0x019d }
            if (r3 <= 0) goto L_0x04d1
            com.google.android.gms.internal.zzcjx[] r5 = r2.zzbvw     // Catch:{ all -> 0x019d }
            r6 = 0
            r7 = 0
            java.lang.System.arraycopy(r5, r6, r4, r7, r3)     // Catch:{ all -> 0x019d }
        L_0x04d1:
            int r5 = r4.length     // Catch:{ all -> 0x019d }
            if (r3 >= r5) goto L_0x04dd
            com.google.android.gms.internal.zzcjx[] r5 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r6 = r3 + 1
            int r7 = r4.length     // Catch:{ all -> 0x019d }
            int r7 = r7 - r3
            java.lang.System.arraycopy(r5, r6, r4, r3, r7)     // Catch:{ all -> 0x019d }
        L_0x04dd:
            r2.zzbvw = r4     // Catch:{ all -> 0x019d }
        L_0x04df:
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.name     // Catch:{ all -> 0x019d }
            boolean r2 = com.google.android.gms.internal.zzcjl.zzeo(r2)     // Catch:{ all -> 0x019d }
            if (r2 == 0) goto L_0x05bc
            if (r17 == 0) goto L_0x05bc
            com.google.android.gms.internal.zzcen r3 = r18.zzwz()     // Catch:{ all -> 0x019d }
            long r4 = r18.zzyZ()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r2 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r6 = r2.zzaH     // Catch:{ all -> 0x019d }
            r7 = 0
            r8 = 0
            r9 = 1
            r10 = 0
            r11 = 0
            com.google.android.gms.internal.zzceo r2 = r3.zza(r4, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x019d }
            long r2 = r2.zzbpw     // Catch:{ all -> 0x019d }
            r0 = r18
            com.google.android.gms.internal.zzcem r4 = r0.zzbsn     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r5 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r5 = r5.zzaH     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfc<java.lang.Integer> r6 = com.google.android.gms.internal.zzcfb.zzbqi     // Catch:{ all -> 0x019d }
            int r4 = r4.zzb(r5, r6)     // Catch:{ all -> 0x019d }
            long r4 = (long) r4     // Catch:{ all -> 0x019d }
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x05bc
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()     // Catch:{ all -> 0x019d }
            java.lang.String r3 = "Too many conversions. Not logging as conversion. appId"
            com.google.android.gms.internal.zzcjz r4 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r4 = r4.zzaH     // Catch:{ all -> 0x019d }
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r4)     // Catch:{ all -> 0x019d }
            r2.zzj(r3, r4)     // Catch:{ all -> 0x019d }
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            r5 = 0
            r4 = 0
            com.google.android.gms.internal.zzcjx[] r7 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r8 = r7.length     // Catch:{ all -> 0x019d }
            r3 = 0
            r6 = r3
        L_0x053f:
            if (r6 >= r8) goto L_0x0562
            r3 = r7[r6]     // Catch:{ all -> 0x019d }
            java.lang.String r9 = "_c"
            java.lang.String r10 = r3.name     // Catch:{ all -> 0x019d }
            boolean r9 = r9.equals(r10)     // Catch:{ all -> 0x019d }
            if (r9 == 0) goto L_0x0555
        L_0x054d:
            int r6 = r6 + 1
            r4 = r3
            goto L_0x053f
        L_0x0551:
            int r3 = r3 + 1
            goto L_0x04ae
        L_0x0555:
            java.lang.String r9 = "_err"
            java.lang.String r3 = r3.name     // Catch:{ all -> 0x019d }
            boolean r3 = r9.equals(r3)     // Catch:{ all -> 0x019d }
            if (r3 == 0) goto L_0x0798
            r5 = 1
            r3 = r4
            goto L_0x054d
        L_0x0562:
            if (r5 == 0) goto L_0x0597
            if (r4 == 0) goto L_0x0597
            com.google.android.gms.internal.zzcjx[] r3 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r3 = r3.length     // Catch:{ all -> 0x019d }
            int r3 = r3 + -1
            com.google.android.gms.internal.zzcjx[] r7 = new com.google.android.gms.internal.zzcjx[r3]     // Catch:{ all -> 0x019d }
            r5 = 0
            com.google.android.gms.internal.zzcjx[] r8 = r2.zzbvw     // Catch:{ all -> 0x019d }
            int r9 = r8.length     // Catch:{ all -> 0x019d }
            r3 = 0
            r6 = r3
        L_0x0573:
            if (r6 >= r9) goto L_0x0581
            r10 = r8[r6]     // Catch:{ all -> 0x019d }
            if (r10 == r4) goto L_0x0795
            int r3 = r5 + 1
            r7[r5] = r10     // Catch:{ all -> 0x019d }
        L_0x057d:
            int r6 = r6 + 1
            r5 = r3
            goto L_0x0573
        L_0x0581:
            r2.zzbvw = r7     // Catch:{ all -> 0x019d }
            r4 = r13
        L_0x0584:
            r0 = r16
            com.google.android.gms.internal.zzcjw[] r5 = r0.zzbvE     // Catch:{ all -> 0x019d }
            int r3 = r12 + 1
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw r2 = (com.google.android.gms.internal.zzcjw) r2     // Catch:{ all -> 0x019d }
            r5[r12] = r2     // Catch:{ all -> 0x019d }
            r2 = r3
            goto L_0x0138
        L_0x0597:
            if (r4 == 0) goto L_0x05a7
            java.lang.String r2 = "_err"
            r4.name = r2     // Catch:{ all -> 0x019d }
            r2 = 10
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x019d }
            r4.zzbvA = r2     // Catch:{ all -> 0x019d }
            r4 = r13
            goto L_0x0584
        L_0x05a7:
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x019d }
            java.lang.String r3 = "Did not find conversion parameter. appId"
            com.google.android.gms.internal.zzcjz r4 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r4 = r4.zzaH     // Catch:{ all -> 0x019d }
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r4)     // Catch:{ all -> 0x019d }
            r2.zzj(r3, r4)     // Catch:{ all -> 0x019d }
        L_0x05bc:
            r4 = r13
            goto L_0x0584
        L_0x05be:
            java.util.List<com.google.android.gms.internal.zzcjw> r2 = r15.zztH     // Catch:{ all -> 0x019d }
            int r2 = r2.size()     // Catch:{ all -> 0x019d }
            if (r12 >= r2) goto L_0x05d4
            r0 = r16
            com.google.android.gms.internal.zzcjw[] r2 = r0.zzbvE     // Catch:{ all -> 0x019d }
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r2, r12)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjw[] r2 = (com.google.android.gms.internal.zzcjw[]) r2     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbvE = r2     // Catch:{ all -> 0x019d }
        L_0x05d4:
            com.google.android.gms.internal.zzcjz r2 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.zzaH     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r3 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzckb[] r3 = r3.zzbvF     // Catch:{ all -> 0x019d }
            r0 = r16
            com.google.android.gms.internal.zzcjw[] r4 = r0.zzbvE     // Catch:{ all -> 0x019d }
            r0 = r18
            com.google.android.gms.internal.zzcjv[] r2 = r0.zza((java.lang.String) r2, (com.google.android.gms.internal.zzckb[]) r3, (com.google.android.gms.internal.zzcjw[]) r4)     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbvX = r2     // Catch:{ all -> 0x019d }
            r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbvH = r2     // Catch:{ all -> 0x019d }
            r2 = -9223372036854775808
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbvI = r2     // Catch:{ all -> 0x019d }
            r2 = 0
        L_0x0602:
            r0 = r16
            com.google.android.gms.internal.zzcjw[] r3 = r0.zzbvE     // Catch:{ all -> 0x019d }
            int r3 = r3.length     // Catch:{ all -> 0x019d }
            if (r2 >= r3) goto L_0x0642
            r0 = r16
            com.google.android.gms.internal.zzcjw[] r3 = r0.zzbvE     // Catch:{ all -> 0x019d }
            r3 = r3[r2]     // Catch:{ all -> 0x019d }
            java.lang.Long r4 = r3.zzbvx     // Catch:{ all -> 0x019d }
            long r4 = r4.longValue()     // Catch:{ all -> 0x019d }
            r0 = r16
            java.lang.Long r6 = r0.zzbvH     // Catch:{ all -> 0x019d }
            long r6 = r6.longValue()     // Catch:{ all -> 0x019d }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x0627
            java.lang.Long r4 = r3.zzbvx     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbvH = r4     // Catch:{ all -> 0x019d }
        L_0x0627:
            java.lang.Long r4 = r3.zzbvx     // Catch:{ all -> 0x019d }
            long r4 = r4.longValue()     // Catch:{ all -> 0x019d }
            r0 = r16
            java.lang.Long r6 = r0.zzbvI     // Catch:{ all -> 0x019d }
            long r6 = r6.longValue()     // Catch:{ all -> 0x019d }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x063f
            java.lang.Long r3 = r3.zzbvx     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbvI = r3     // Catch:{ all -> 0x019d }
        L_0x063f:
            int r2 = r2 + 1
            goto L_0x0602
        L_0x0642:
            com.google.android.gms.internal.zzcjz r2 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r6 = r2.zzaH     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzceg r7 = r2.zzdQ(r6)     // Catch:{ all -> 0x019d }
            if (r7 != 0) goto L_0x06d4
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x019d }
            java.lang.String r3 = "Bundling raw events w/o app info. appId"
            com.google.android.gms.internal.zzcjz r4 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r4 = r4.zzaH     // Catch:{ all -> 0x019d }
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r4)     // Catch:{ all -> 0x019d }
            r2.zzj(r3, r4)     // Catch:{ all -> 0x019d }
        L_0x0665:
            r0 = r16
            com.google.android.gms.internal.zzcjw[] r2 = r0.zzbvE     // Catch:{ all -> 0x019d }
            int r2 = r2.length     // Catch:{ all -> 0x019d }
            if (r2 <= 0) goto L_0x069e
            com.google.android.gms.internal.zzcem.zzxE()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcgf r2 = r18.zzwC()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjz r3 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r3 = r3.zzaH     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcjt r2 = r2.zzeh(r3)     // Catch:{ all -> 0x019d }
            if (r2 == 0) goto L_0x0681
            java.lang.Long r3 = r2.zzbvl     // Catch:{ all -> 0x019d }
            if (r3 != 0) goto L_0x0757
        L_0x0681:
            com.google.android.gms.internal.zzcjz r2 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r2.zzboQ     // Catch:{ all -> 0x019d }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x019d }
            if (r2 == 0) goto L_0x0740
            r2 = -1
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbwb = r2     // Catch:{ all -> 0x019d }
        L_0x0695:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x019d }
            r0 = r16
            r2.zza((com.google.android.gms.internal.zzcjz) r0, (boolean) r13)     // Catch:{ all -> 0x019d }
        L_0x069e:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x019d }
            java.util.List<java.lang.Long> r3 = r15.zzbta     // Catch:{ all -> 0x019d }
            r2.zzG(r3)     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcen r3 = r18.zzwz()     // Catch:{ all -> 0x019d }
            android.database.sqlite.SQLiteDatabase r2 = r3.getWritableDatabase()     // Catch:{ all -> 0x019d }
            java.lang.String r4 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)"
            r5 = 2
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x075f }
            r7 = 0
            r5[r7] = r6     // Catch:{ SQLiteException -> 0x075f }
            r7 = 1
            r5[r7] = r6     // Catch:{ SQLiteException -> 0x075f }
            r2.execSQL(r4, r5)     // Catch:{ SQLiteException -> 0x075f }
        L_0x06bd:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x019d }
            r2.setTransactionSuccessful()     // Catch:{ all -> 0x019d }
            r0 = r16
            com.google.android.gms.internal.zzcjw[] r2 = r0.zzbvE     // Catch:{ all -> 0x019d }
            int r2 = r2.length     // Catch:{ all -> 0x019d }
            if (r2 <= 0) goto L_0x0773
            r2 = 1
        L_0x06cc:
            com.google.android.gms.internal.zzcen r3 = r18.zzwz()
            r3.endTransaction()
        L_0x06d3:
            return r2
        L_0x06d4:
            r0 = r16
            com.google.android.gms.internal.zzcjw[] r2 = r0.zzbvE     // Catch:{ all -> 0x019d }
            int r2 = r2.length     // Catch:{ all -> 0x019d }
            if (r2 <= 0) goto L_0x0665
            long r2 = r7.zzwM()     // Catch:{ all -> 0x019d }
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x073c
            java.lang.Long r4 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x019d }
        L_0x06e9:
            r0 = r16
            r0.zzbvK = r4     // Catch:{ all -> 0x019d }
            long r4 = r7.zzwL()     // Catch:{ all -> 0x019d }
            r8 = 0
            int r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r8 != 0) goto L_0x0792
        L_0x06f7:
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x073e
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x019d }
        L_0x0701:
            r0 = r16
            r0.zzbvJ = r2     // Catch:{ all -> 0x019d }
            r7.zzwV()     // Catch:{ all -> 0x019d }
            long r2 = r7.zzwS()     // Catch:{ all -> 0x019d }
            int r2 = (int) r2     // Catch:{ all -> 0x019d }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbvV = r2     // Catch:{ all -> 0x019d }
            r0 = r16
            java.lang.Long r2 = r0.zzbvH     // Catch:{ all -> 0x019d }
            long r2 = r2.longValue()     // Catch:{ all -> 0x019d }
            r7.zzL(r2)     // Catch:{ all -> 0x019d }
            r0 = r16
            java.lang.Long r2 = r0.zzbvI     // Catch:{ all -> 0x019d }
            long r2 = r2.longValue()     // Catch:{ all -> 0x019d }
            r7.zzM(r2)     // Catch:{ all -> 0x019d }
            java.lang.String r2 = r7.zzxd()     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzboU = r2     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x019d }
            r2.zza((com.google.android.gms.internal.zzceg) r7)     // Catch:{ all -> 0x019d }
            goto L_0x0665
        L_0x073c:
            r4 = 0
            goto L_0x06e9
        L_0x073e:
            r2 = 0
            goto L_0x0701
        L_0x0740:
            com.google.android.gms.internal.zzcfl r2 = r18.zzwF()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()     // Catch:{ all -> 0x019d }
            java.lang.String r3 = "Did not find measurement config or missing version info. appId"
            com.google.android.gms.internal.zzcjz r4 = r15.zzbsZ     // Catch:{ all -> 0x019d }
            java.lang.String r4 = r4.zzaH     // Catch:{ all -> 0x019d }
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r4)     // Catch:{ all -> 0x019d }
            r2.zzj(r3, r4)     // Catch:{ all -> 0x019d }
            goto L_0x0695
        L_0x0757:
            java.lang.Long r2 = r2.zzbvl     // Catch:{ all -> 0x019d }
            r0 = r16
            r0.zzbwb = r2     // Catch:{ all -> 0x019d }
            goto L_0x0695
        L_0x075f:
            r2 = move-exception
            com.google.android.gms.internal.zzcfl r3 = r3.zzwF()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcfn r3 = r3.zzyx()     // Catch:{ all -> 0x019d }
            java.lang.String r4 = "Failed to remove unused event metadata. appId"
            java.lang.Object r5 = com.google.android.gms.internal.zzcfl.zzdZ(r6)     // Catch:{ all -> 0x019d }
            r3.zze(r4, r5, r2)     // Catch:{ all -> 0x019d }
            goto L_0x06bd
        L_0x0773:
            r2 = 0
            goto L_0x06cc
        L_0x0776:
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()     // Catch:{ all -> 0x019d }
            r2.setTransactionSuccessful()     // Catch:{ all -> 0x019d }
            com.google.android.gms.internal.zzcen r2 = r18.zzwz()
            r2.endTransaction()
            r2 = 0
            goto L_0x06d3
        L_0x0787:
            r2 = move-exception
            r3 = r11
            goto L_0x032f
        L_0x078b:
            r2 = move-exception
            goto L_0x02c0
        L_0x078e:
            r2 = move-exception
            r4 = r12
            goto L_0x02c0
        L_0x0792:
            r2 = r4
            goto L_0x06f7
        L_0x0795:
            r3 = r5
            goto L_0x057d
        L_0x0798:
            r3 = r4
            goto L_0x054d
        L_0x079b:
            r13 = r2
            goto L_0x04df
        L_0x079e:
            r2 = r4
            goto L_0x03a6
        L_0x07a1:
            r2 = r12
            r4 = r13
            goto L_0x0138
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcgl.zzg(java.lang.String, long):boolean");
    }

    static void zzwo() {
        zzcem.zzxE();
        throw new IllegalStateException("Unexpected call on client side");
    }

    private final zzcfu zzyV() {
        if (this.zzbsF != null) {
            return this.zzbsF;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzcjg zzyW() {
        zza((zzchj) this.zzbsG);
        return this.zzbsG;
    }

    @WorkerThread
    private final boolean zzyX() {
        zzwE().zzjC();
        try {
            this.zzbsN = new RandomAccessFile(new File(this.mContext.getFilesDir(), zzcem.zzxC()), "rw").getChannel();
            this.zzbsM = this.zzbsN.tryLock();
            if (this.zzbsM != null) {
                zzwF().zzyD().log("Storage concurrent access okay");
                return true;
            }
            zzwF().zzyx().log("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            zzwF().zzyx().zzj("Failed to acquire storage lock", e);
        } catch (IOException e2) {
            zzwF().zzyx().zzj("Failed to access storage lock file", e2);
        }
    }

    private final long zzyZ() {
        long currentTimeMillis = this.zzvw.currentTimeMillis();
        zzcfw zzwG = zzwG();
        zzwG.zzkD();
        zzwG.zzjC();
        long j = zzwG.zzbro.get();
        if (j == 0) {
            j = (long) (zzwG.zzwB().zzzt().nextInt(CONSTANT.TIME.HR_24) + 1);
            zzwG.zzbro.set(j);
        }
        return ((((j + currentTimeMillis) / 1000) / 60) / 60) / 24;
    }

    private final boolean zzzb() {
        zzwE().zzjC();
        zzkD();
        return zzwz().zzyh() || !TextUtils.isEmpty(zzwz().zzyc());
    }

    @WorkerThread
    private final void zzzc() {
        long zzxR;
        long j;
        zzwE().zzjC();
        zzkD();
        if (zzzf()) {
            if (this.zzbsT > 0) {
                long abs = 3600000 - Math.abs(this.zzvw.elapsedRealtime() - this.zzbsT);
                if (abs > 0) {
                    zzwF().zzyD().zzj("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(abs));
                    zzyV().unregister();
                    zzyW().cancel();
                    return;
                }
                this.zzbsT = 0;
            }
            if (!zzyP() || !zzzb()) {
                zzwF().zzyD().log("Nothing to upload or uploading impossible");
                zzyV().unregister();
                zzyW().cancel();
                return;
            }
            long currentTimeMillis = this.zzvw.currentTimeMillis();
            long zzxX = zzcem.zzxX();
            boolean z = zzwz().zzyi() || zzwz().zzyd();
            if (z) {
                String zzya = this.zzbsn.zzya();
                zzxR = (TextUtils.isEmpty(zzya) || ".none.".equals(zzya)) ? zzcem.zzxS() : zzcem.zzxT();
            } else {
                zzxR = zzcem.zzxR();
            }
            long j2 = zzwG().zzbrk.get();
            long j3 = zzwG().zzbrl.get();
            long max = Math.max(zzwz().zzyf(), zzwz().zzyg());
            if (max != 0) {
                long abs2 = currentTimeMillis - Math.abs(max - currentTimeMillis);
                long abs3 = currentTimeMillis - Math.abs(j3 - currentTimeMillis);
                long max2 = Math.max(currentTimeMillis - Math.abs(j2 - currentTimeMillis), abs3);
                long j4 = abs2 + zzxX;
                if (z && max2 > 0) {
                    j4 = Math.min(abs2, max2) + zzxR;
                }
                if (!zzwB().zzf(max2, zzxR)) {
                    j4 = max2 + zzxR;
                }
                if (abs3 != 0 && abs3 >= abs2) {
                    int i = 0;
                    while (true) {
                        if (i >= zzcem.zzxZ()) {
                            j = 0;
                            break;
                        }
                        j4 += ((long) (1 << i)) * zzcem.zzxY();
                        if (j4 > abs3) {
                            j = j4;
                            break;
                        }
                        i++;
                    }
                } else {
                    j = j4;
                }
            } else {
                j = 0;
            }
            if (j == 0) {
                zzwF().zzyD().log("Next upload time is 0");
                zzyV().unregister();
                zzyW().cancel();
            } else if (!zzyU().zzlQ()) {
                zzwF().zzyD().log("No network");
                zzyV().zzlN();
                zzyW().cancel();
            } else {
                long j5 = zzwG().zzbrm.get();
                long zzxQ = zzcem.zzxQ();
                if (!zzwB().zzf(j5, zzxQ)) {
                    j = Math.max(j, j5 + zzxQ);
                }
                zzyV().unregister();
                long currentTimeMillis2 = j - this.zzvw.currentTimeMillis();
                if (currentTimeMillis2 <= 0) {
                    currentTimeMillis2 = zzcem.zzxU();
                    zzwG().zzbrk.set(this.zzvw.currentTimeMillis());
                }
                zzwF().zzyD().zzj("Upload scheduled in approximately ms", Long.valueOf(currentTimeMillis2));
                zzyW().zzs(currentTimeMillis2);
            }
        }
    }

    @WorkerThread
    private final boolean zzzf() {
        zzwE().zzjC();
        zzkD();
        return this.zzbsJ;
    }

    @WorkerThread
    private final void zzzg() {
        zzwE().zzjC();
        if (this.zzbsU || this.zzbsV || this.zzbsW) {
            zzwF().zzyD().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzbsU), Boolean.valueOf(this.zzbsV), Boolean.valueOf(this.zzbsW));
            return;
        }
        zzwF().zzyD().log("Stopping uploading service(s)");
        if (this.zzbsP != null) {
            for (Runnable run : this.zzbsP) {
                run.run();
            }
            this.zzbsP.clear();
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    @WorkerThread
    public final boolean isEnabled() {
        boolean z = false;
        zzwE().zzjC();
        zzkD();
        if (this.zzbsn.zzxF()) {
            return false;
        }
        Boolean zzdN = this.zzbsn.zzdN("firebase_analytics_collection_enabled");
        if (zzdN != null) {
            z = zzdN.booleanValue();
        } else if (!zzcem.zzqB()) {
            z = true;
        }
        return zzwG().zzal(z);
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void start() {
        zzwE().zzjC();
        zzwz().zzye();
        if (zzwG().zzbrk.get() == 0) {
            zzwG().zzbrk.set(this.zzvw.currentTimeMillis());
        }
        if (Long.valueOf(zzwG().zzbrp.get()).longValue() == 0) {
            zzwF().zzyD().zzj("Persisting first open", Long.valueOf(this.zzbsX));
            zzwG().zzbrp.set(this.zzbsX);
        }
        if (zzyP()) {
            zzcem.zzxE();
            if (!TextUtils.isEmpty(zzwu().getGmpAppId())) {
                String zzyG = zzwG().zzyG();
                if (zzyG == null) {
                    zzwG().zzed(zzwu().getGmpAppId());
                } else if (!zzyG.equals(zzwu().getGmpAppId())) {
                    zzwF().zzyB().log("Rechecking which service to use due to a GMP App Id change");
                    zzwG().zzyJ();
                    this.zzbsB.disconnect();
                    this.zzbsB.zzla();
                    zzwG().zzed(zzwu().getGmpAppId());
                    zzwG().zzbrp.set(this.zzbsX);
                    zzwG().zzbrq.zzef((String) null);
                }
            }
            zzwt().zzee(zzwG().zzbrq.zzyL());
            zzcem.zzxE();
            if (!TextUtils.isEmpty(zzwu().getGmpAppId())) {
                zzchl zzwt = zzwt();
                zzwt.zzjC();
                zzwt.zzwp();
                zzwt.zzkD();
                if (zzwt.zzboe.zzyP()) {
                    zzwt.zzww().zzzk();
                    String zzyK = zzwt.zzwG().zzyK();
                    if (!TextUtils.isEmpty(zzyK)) {
                        zzwt.zzwv().zzkD();
                        if (!zzyK.equals(Build.VERSION.RELEASE)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("_po", zzyK);
                            zzwt.zzd("auto", "_ou", bundle);
                        }
                    }
                }
                zzww().zza((AtomicReference<String>) new AtomicReference());
            }
        } else if (isEnabled()) {
            if (!zzwB().zzbv("android.permission.INTERNET")) {
                zzwF().zzyx().log("App is missing INTERNET permission");
            }
            if (!zzwB().zzbv("android.permission.ACCESS_NETWORK_STATE")) {
                zzwF().zzyx().log("App is missing ACCESS_NETWORK_STATE permission");
            }
            zzcem.zzxE();
            if (!zzbha.zzaP(this.mContext).zzsl()) {
                if (!zzcgc.zzj(this.mContext, false)) {
                    zzwF().zzyx().log("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzciw.zzk(this.mContext, false)) {
                    zzwF().zzyx().log("AppMeasurementService not registered/enabled");
                }
            }
            zzwF().zzyx().log("Uploading is not possible. App measurement disabled");
        }
        zzzc();
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zza(int i, Throwable th, byte[] bArr) {
        zzcen zzwz;
        zzwE().zzjC();
        zzkD();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzbsV = false;
                zzzg();
                throw th2;
            }
        }
        List<Long> list = this.zzbsO;
        this.zzbsO = null;
        if ((i == 200 || i == 204) && th == null) {
            try {
                zzwG().zzbrk.set(this.zzvw.currentTimeMillis());
                zzwG().zzbrl.set(0);
                zzzc();
                zzwF().zzyD().zze("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzwz().beginTransaction();
                try {
                    for (Long longValue : list) {
                        zzwz = zzwz();
                        long longValue2 = longValue.longValue();
                        zzwz.zzjC();
                        zzwz.zzkD();
                        if (zzwz.getWritableDatabase().delete("queue", "rowid=?", new String[]{String.valueOf(longValue2)}) != 1) {
                            throw new SQLiteException("Deleted fewer rows from queue than expected");
                        }
                    }
                    zzwz().setTransactionSuccessful();
                    zzwz().endTransaction();
                    if (!zzyU().zzlQ() || !zzzb()) {
                        this.zzbsS = -1;
                        zzzc();
                    } else {
                        zzza();
                    }
                    this.zzbsT = 0;
                } catch (SQLiteException e) {
                    zzwz.zzwF().zzyx().zzj("Failed to delete a bundle in a queue table", e);
                    throw e;
                } catch (Throwable th3) {
                    zzwz().endTransaction();
                    throw th3;
                }
            } catch (SQLiteException e2) {
                zzwF().zzyx().zzj("Database error while trying to delete uploaded bundles", e2);
                this.zzbsT = this.zzvw.elapsedRealtime();
                zzwF().zzyD().zzj("Disable upload, time", Long.valueOf(this.zzbsT));
            }
        } else {
            zzwF().zzyD().zze("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            zzwG().zzbrl.set(this.zzvw.currentTimeMillis());
            if (i == 503 || i == 429) {
                zzwG().zzbrm.set(this.zzvw.currentTimeMillis());
            }
            zzzc();
        }
        this.zzbsV = false;
        zzzg();
    }

    @WorkerThread
    public final byte[] zza(@NonNull zzcez zzcez, @Size(min = 1) String str) {
        long j;
        zzkD();
        zzwE().zzjC();
        zzwo();
        zzbo.zzu(zzcez);
        zzbo.zzcF(str);
        zzcjy zzcjy = new zzcjy();
        zzwz().beginTransaction();
        try {
            zzceg zzdQ = zzwz().zzdQ(str);
            if (zzdQ == null) {
                zzwF().zzyC().zzj("Log and bundle not available. package_name", str);
                return new byte[0];
            } else if (!zzdQ.zzwR()) {
                zzwF().zzyC().zzj("Log and bundle disabled. package_name", str);
                byte[] bArr = new byte[0];
                zzwz().endTransaction();
                return bArr;
            } else {
                zzcjz zzcjz = new zzcjz();
                zzcjy.zzbvB = new zzcjz[]{zzcjz};
                zzcjz.zzbvD = 1;
                zzcjz.zzbvL = SystemMediaRouteProvider.PACKAGE_NAME;
                zzcjz.zzaH = zzdQ.zzhl();
                zzcjz.zzboR = zzdQ.zzwO();
                zzcjz.zzbgW = zzdQ.zzjH();
                long zzwN = zzdQ.zzwN();
                zzcjz.zzbvY = zzwN == -2147483648L ? null : Integer.valueOf((int) zzwN);
                zzcjz.zzbvP = Long.valueOf(zzdQ.zzwP());
                zzcjz.zzboQ = zzdQ.getGmpAppId();
                zzcjz.zzbvU = Long.valueOf(zzdQ.zzwQ());
                if (isEnabled() && zzcem.zzyb() && this.zzbsn.zzdO(zzcjz.zzaH)) {
                    zzwu();
                    zzcjz.zzbwd = null;
                }
                Pair<String, Boolean> zzeb = zzwG().zzeb(zzdQ.zzhl());
                if (zzeb != null && !TextUtils.isEmpty((CharSequence) zzeb.first)) {
                    zzcjz.zzbvR = (String) zzeb.first;
                    zzcjz.zzbvS = (Boolean) zzeb.second;
                }
                zzwv().zzkD();
                zzcjz.zzbvM = Build.MODEL;
                zzwv().zzkD();
                zzcjz.zzaY = Build.VERSION.RELEASE;
                zzcjz.zzbvO = Integer.valueOf((int) zzwv().zzyq());
                zzcjz.zzbvN = zzwv().zzyr();
                zzcjz.zzbvT = zzdQ.getAppInstanceId();
                zzcjz.zzboY = zzdQ.zzwK();
                List<zzcjk> zzdP = zzwz().zzdP(zzdQ.zzhl());
                zzcjz.zzbvF = new zzckb[zzdP.size()];
                for (int i = 0; i < zzdP.size(); i++) {
                    zzckb zzckb = new zzckb();
                    zzcjz.zzbvF[i] = zzckb;
                    zzckb.name = zzdP.get(i).mName;
                    zzckb.zzbwh = Long.valueOf(zzdP.get(i).zzbuC);
                    zzwB().zza(zzckb, zzdP.get(i).mValue);
                }
                Bundle zzyt = zzcez.zzbpM.zzyt();
                if ("_iap".equals(zzcez.name)) {
                    zzyt.putLong("_c", 1);
                    zzwF().zzyC().log("Marking in-app purchase as real-time");
                    zzyt.putLong("_r", 1);
                }
                zzyt.putString("_o", zzcez.zzbpc);
                if (zzwB().zzey(zzcjz.zzaH)) {
                    zzwB().zza(zzyt, "_dbg", (Object) 1L);
                    zzwB().zza(zzyt, "_r", (Object) 1L);
                }
                zzcev zzE = zzwz().zzE(str, zzcez.name);
                if (zzE == null) {
                    zzwz().zza(new zzcev(str, zzcez.name, 1, 0, zzcez.zzbpN));
                    j = 0;
                } else {
                    j = zzE.zzbpI;
                    zzwz().zza(zzE.zzab(zzcez.zzbpN).zzys());
                }
                zzceu zzceu = new zzceu(this, zzcez.zzbpc, str, zzcez.name, zzcez.zzbpN, j, zzyt);
                zzcjw zzcjw = new zzcjw();
                zzcjz.zzbvE = new zzcjw[]{zzcjw};
                zzcjw.zzbvx = Long.valueOf(zzceu.zzayS);
                zzcjw.name = zzceu.mName;
                zzcjw.zzbvy = Long.valueOf(zzceu.zzbpE);
                zzcjw.zzbvw = new zzcjx[zzceu.zzbpF.size()];
                Iterator<String> it = zzceu.zzbpF.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    String next = it.next();
                    zzcjx zzcjx = new zzcjx();
                    zzcjw.zzbvw[i2] = zzcjx;
                    zzcjx.name = next;
                    zzwB().zza(zzcjx, zzceu.zzbpF.get(next));
                    i2++;
                }
                zzcjz.zzbvX = zza(zzdQ.zzhl(), zzcjz.zzbvF, zzcjz.zzbvE);
                zzcjz.zzbvH = zzcjw.zzbvx;
                zzcjz.zzbvI = zzcjw.zzbvx;
                long zzwM = zzdQ.zzwM();
                zzcjz.zzbvK = zzwM != 0 ? Long.valueOf(zzwM) : null;
                long zzwL = zzdQ.zzwL();
                if (zzwL != 0) {
                    zzwM = zzwL;
                }
                zzcjz.zzbvJ = zzwM != 0 ? Long.valueOf(zzwM) : null;
                zzdQ.zzwV();
                zzcjz.zzbvV = Integer.valueOf((int) zzdQ.zzwS());
                zzcjz.zzbvQ = Long.valueOf(zzcem.zzwP());
                zzcjz.zzbvG = Long.valueOf(this.zzvw.currentTimeMillis());
                zzcjz.zzbvW = Boolean.TRUE;
                zzdQ.zzL(zzcjz.zzbvH.longValue());
                zzdQ.zzM(zzcjz.zzbvI.longValue());
                zzwz().zza(zzdQ);
                zzwz().setTransactionSuccessful();
                zzwz().endTransaction();
                try {
                    byte[] bArr2 = new byte[zzcjy.zzLV()];
                    adh zzc = adh.zzc(bArr2, 0, bArr2.length);
                    zzcjy.zza(zzc);
                    zzc.zzLM();
                    return zzwB().zzl(bArr2);
                } catch (IOException e) {
                    zzwF().zzyx().zze("Data loss. Failed to bundle and serialize. appId", zzcfl.zzdZ(str), e);
                    return null;
                }
            }
        } finally {
            zzwz().endTransaction();
        }
    }

    public final void zzam(boolean z) {
        zzzc();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzb(zzcek zzcek, zzceh zzceh) {
        boolean z = true;
        zzbo.zzu(zzcek);
        zzbo.zzcF(zzcek.packageName);
        zzbo.zzu(zzcek.zzbpc);
        zzbo.zzu(zzcek.zzbpd);
        zzbo.zzcF(zzcek.zzbpd.name);
        zzwE().zzjC();
        zzkD();
        if (!TextUtils.isEmpty(zzceh.zzboQ)) {
            if (!zzceh.zzboV) {
                zzf(zzceh);
                return;
            }
            zzcek zzcek2 = new zzcek(zzcek);
            zzcek2.zzbpf = false;
            zzwz().beginTransaction();
            try {
                zzcek zzH = zzwz().zzH(zzcek2.packageName, zzcek2.zzbpd.name);
                if (zzH != null && !zzH.zzbpc.equals(zzcek2.zzbpc)) {
                    zzwF().zzyz().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", zzwA().zzdY(zzcek2.zzbpd.name), zzcek2.zzbpc, zzH.zzbpc);
                }
                if (zzH != null && zzH.zzbpf) {
                    zzcek2.zzbpc = zzH.zzbpc;
                    zzcek2.zzbpe = zzH.zzbpe;
                    zzcek2.zzbpi = zzH.zzbpi;
                    zzcek2.zzbpg = zzH.zzbpg;
                    zzcek2.zzbpj = zzH.zzbpj;
                    zzcek2.zzbpf = zzH.zzbpf;
                    zzcek2.zzbpd = new zzcji(zzcek2.zzbpd.name, zzH.zzbpd.zzbuy, zzcek2.zzbpd.getValue(), zzH.zzbpd.zzbpc);
                    z = false;
                } else if (TextUtils.isEmpty(zzcek2.zzbpg)) {
                    zzcek2.zzbpd = new zzcji(zzcek2.zzbpd.name, zzcek2.zzbpe, zzcek2.zzbpd.getValue(), zzcek2.zzbpd.zzbpc);
                    zzcek2.zzbpf = true;
                } else {
                    z = false;
                }
                if (zzcek2.zzbpf) {
                    zzcji zzcji = zzcek2.zzbpd;
                    zzcjk zzcjk = new zzcjk(zzcek2.packageName, zzcek2.zzbpc, zzcji.name, zzcji.zzbuy, zzcji.getValue());
                    if (zzwz().zza(zzcjk)) {
                        zzwF().zzyC().zzd("User property updated immediately", zzcek2.packageName, zzwA().zzdY(zzcjk.mName), zzcjk.mValue);
                    } else {
                        zzwF().zzyx().zzd("(2)Too many active user properties, ignoring", zzcfl.zzdZ(zzcek2.packageName), zzwA().zzdY(zzcjk.mName), zzcjk.mValue);
                    }
                    if (z && zzcek2.zzbpj != null) {
                        zzc(new zzcez(zzcek2.zzbpj, zzcek2.zzbpe), zzceh);
                    }
                }
                if (zzwz().zza(zzcek2)) {
                    zzwF().zzyC().zzd("Conditional property added", zzcek2.packageName, zzwA().zzdY(zzcek2.zzbpd.name), zzcek2.zzbpd.getValue());
                } else {
                    zzwF().zzyx().zzd("Too many conditional properties, ignoring", zzcfl.zzdZ(zzcek2.packageName), zzwA().zzdY(zzcek2.zzbpd.name), zzcek2.zzbpd.getValue());
                }
                zzwz().setTransactionSuccessful();
            } finally {
                zzwz().endTransaction();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzb(zzcez zzcez, zzceh zzceh) {
        List<zzcek> zzc;
        List<zzcek> zzc2;
        List<zzcek> zzc3;
        zzbo.zzu(zzceh);
        zzbo.zzcF(zzceh.packageName);
        zzwE().zzjC();
        zzkD();
        String str = zzceh.packageName;
        long j = zzcez.zzbpN;
        zzwB();
        if (zzcjl.zzd(zzcez, zzceh)) {
            if (!zzceh.zzboV) {
                zzf(zzceh);
                return;
            }
            zzwz().beginTransaction();
            try {
                zzcen zzwz = zzwz();
                zzbo.zzcF(str);
                zzwz.zzjC();
                zzwz.zzkD();
                if (j < 0) {
                    zzwz.zzwF().zzyz().zze("Invalid time querying timed out conditional properties", zzcfl.zzdZ(str), Long.valueOf(j));
                    zzc = Collections.emptyList();
                } else {
                    zzc = zzwz.zzc("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzcek next : zzc) {
                    if (next != null) {
                        zzwF().zzyC().zzd("User property timed out", next.packageName, zzwA().zzdY(next.zzbpd.name), next.zzbpd.getValue());
                        if (next.zzbph != null) {
                            zzc(new zzcez(next.zzbph, j), zzceh);
                        }
                        zzwz().zzI(str, next.zzbpd.name);
                    }
                }
                zzcen zzwz2 = zzwz();
                zzbo.zzcF(str);
                zzwz2.zzjC();
                zzwz2.zzkD();
                if (j < 0) {
                    zzwz2.zzwF().zzyz().zze("Invalid time querying expired conditional properties", zzcfl.zzdZ(str), Long.valueOf(j));
                    zzc2 = Collections.emptyList();
                } else {
                    zzc2 = zzwz2.zzc("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(zzc2.size());
                for (zzcek next2 : zzc2) {
                    if (next2 != null) {
                        zzwF().zzyC().zzd("User property expired", next2.packageName, zzwA().zzdY(next2.zzbpd.name), next2.zzbpd.getValue());
                        zzwz().zzF(str, next2.zzbpd.name);
                        if (next2.zzbpl != null) {
                            arrayList.add(next2.zzbpl);
                        }
                        zzwz().zzI(str, next2.zzbpd.name);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    zzc(new zzcez((zzcez) obj, j), zzceh);
                }
                zzcen zzwz3 = zzwz();
                String str2 = zzcez.name;
                zzbo.zzcF(str);
                zzbo.zzcF(str2);
                zzwz3.zzjC();
                zzwz3.zzkD();
                if (j < 0) {
                    zzwz3.zzwF().zzyz().zzd("Invalid time querying triggered conditional properties", zzcfl.zzdZ(str), zzwz3.zzwA().zzdW(str2), Long.valueOf(j));
                    zzc3 = Collections.emptyList();
                } else {
                    zzc3 = zzwz3.zzc("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                ArrayList arrayList3 = new ArrayList(zzc3.size());
                for (zzcek next3 : zzc3) {
                    if (next3 != null) {
                        zzcji zzcji = next3.zzbpd;
                        zzcjk zzcjk = new zzcjk(next3.packageName, next3.zzbpc, zzcji.name, j, zzcji.getValue());
                        if (zzwz().zza(zzcjk)) {
                            zzwF().zzyC().zzd("User property triggered", next3.packageName, zzwA().zzdY(zzcjk.mName), zzcjk.mValue);
                        } else {
                            zzwF().zzyx().zzd("Too many active user properties, ignoring", zzcfl.zzdZ(next3.packageName), zzwA().zzdY(zzcjk.mName), zzcjk.mValue);
                        }
                        if (next3.zzbpj != null) {
                            arrayList3.add(next3.zzbpj);
                        }
                        next3.zzbpd = new zzcji(zzcjk);
                        next3.zzbpf = true;
                        zzwz().zza(next3);
                    }
                }
                zzc(zzcez, zzceh);
                ArrayList arrayList4 = arrayList3;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList4.get(i2);
                    i2++;
                    zzc(new zzcez((zzcez) obj2, j), zzceh);
                }
                zzwz().setTransactionSuccessful();
            } finally {
                zzwz().endTransaction();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzb(zzcez zzcez, String str) {
        zzceg zzdQ = zzwz().zzdQ(str);
        if (zzdQ == null || TextUtils.isEmpty(zzdQ.zzjH())) {
            zzwF().zzyC().zzj("No app data available; dropping event", str);
            return;
        }
        try {
            String str2 = zzbha.zzaP(this.mContext).getPackageInfo(str, 0).versionName;
            if (zzdQ.zzjH() != null && !zzdQ.zzjH().equals(str2)) {
                zzwF().zzyz().zzj("App version does not match; dropping event. appId", zzcfl.zzdZ(str));
                return;
            }
        } catch (PackageManager.NameNotFoundException e) {
            if (!"_ui".equals(zzcez.name)) {
                zzwF().zzyz().zzj("Could not find package. appId", zzcfl.zzdZ(str));
            }
        }
        zzcez zzcez2 = zzcez;
        zzb(zzcez2, new zzceh(str, zzdQ.getGmpAppId(), zzdQ.zzjH(), zzdQ.zzwN(), zzdQ.zzwO(), zzdQ.zzwP(), zzdQ.zzwQ(), (String) null, zzdQ.zzwR(), false, zzdQ.zzwK(), zzdQ.zzxe(), 0, 0));
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzchj zzchj) {
        this.zzbsQ++;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzb(zzcji zzcji, zzceh zzceh) {
        int i = 0;
        zzwE().zzjC();
        zzkD();
        if (!TextUtils.isEmpty(zzceh.zzboQ)) {
            if (!zzceh.zzboV) {
                zzf(zzceh);
                return;
            }
            int zzes = zzwB().zzes(zzcji.name);
            if (zzes != 0) {
                zzwB();
                String zza2 = zzcjl.zza(zzcji.name, zzcem.zzxi(), true);
                if (zzcji.name != null) {
                    i = zzcji.name.length();
                }
                zzwB().zza(zzceh.packageName, zzes, "_ev", zza2, i);
                return;
            }
            int zzl = zzwB().zzl(zzcji.name, zzcji.getValue());
            if (zzl != 0) {
                zzwB();
                String zza3 = zzcjl.zza(zzcji.name, zzcem.zzxi(), true);
                Object value = zzcji.getValue();
                if (value != null && ((value instanceof String) || (value instanceof CharSequence))) {
                    i = String.valueOf(value).length();
                }
                zzwB().zza(zzceh.packageName, zzl, "_ev", zza3, i);
                return;
            }
            Object zzm = zzwB().zzm(zzcji.name, zzcji.getValue());
            if (zzm != null) {
                zzcjk zzcjk = new zzcjk(zzceh.packageName, zzcji.zzbpc, zzcji.name, zzcji.zzbuy, zzm);
                zzwF().zzyC().zze("Setting user property", zzwA().zzdY(zzcjk.mName), zzm);
                zzwz().beginTransaction();
                try {
                    zzf(zzceh);
                    boolean zza4 = zzwz().zza(zzcjk);
                    zzwz().setTransactionSuccessful();
                    if (zza4) {
                        zzwF().zzyC().zze("User property set", zzwA().zzdY(zzcjk.mName), zzcjk.mValue);
                    } else {
                        zzwF().zzyx().zze("Too many unique user properties are set. Ignoring user property", zzwA().zzdY(zzcjk.mName), zzcjk.mValue);
                        zzwB().zza(zzceh.packageName, 9, (String) null, (String) null, 0);
                    }
                } finally {
                    zzwz().endTransaction();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzb(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        boolean z = true;
        zzwE().zzjC();
        zzkD();
        zzbo.zzcF(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzbsU = false;
                zzzg();
                throw th2;
            }
        }
        zzwF().zzyD().zzj("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zzwz().beginTransaction();
        zzceg zzdQ = zzwz().zzdQ(str);
        boolean z2 = (i == 200 || i == 204 || i == 304) && th == null;
        if (zzdQ == null) {
            zzwF().zzyz().zzj("App does not exist in onConfigFetched. appId", zzcfl.zzdZ(str));
        } else if (z2 || i == 404) {
            List list = map != null ? map.get("Last-Modified") : null;
            String str2 = (list == null || list.size() <= 0) ? null : (String) list.get(0);
            if (i == 404 || i == 304) {
                if (zzwC().zzeh(str) == null && !zzwC().zzb(str, (byte[]) null, (String) null)) {
                    zzwz().endTransaction();
                    this.zzbsU = false;
                    zzzg();
                    return;
                }
            } else if (!zzwC().zzb(str, bArr, str2)) {
                zzwz().endTransaction();
                this.zzbsU = false;
                zzzg();
                return;
            }
            zzdQ.zzR(this.zzvw.currentTimeMillis());
            zzwz().zza(zzdQ);
            if (i == 404) {
                zzwF().zzyA().zzj("Config not found. Using empty config. appId", str);
            } else {
                zzwF().zzyD().zze("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
            }
            if (!zzyU().zzlQ() || !zzzb()) {
                zzzc();
            } else {
                zzza();
            }
        } else {
            zzdQ.zzS(this.zzvw.currentTimeMillis());
            zzwz().zza(zzdQ);
            zzwF().zzyD().zze("Fetching config failed. code, error", Integer.valueOf(i), th);
            zzwC().zzej(str);
            zzwG().zzbrl.set(this.zzvw.currentTimeMillis());
            if (!(i == 503 || i == 429)) {
                z = false;
            }
            if (z) {
                zzwG().zzbrm.set(this.zzvw.currentTimeMillis());
            }
            zzzc();
        }
        zzwz().setTransactionSuccessful();
        zzwz().endTransaction();
        this.zzbsU = false;
        zzzg();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzc(zzcek zzcek, zzceh zzceh) {
        zzbo.zzu(zzcek);
        zzbo.zzcF(zzcek.packageName);
        zzbo.zzu(zzcek.zzbpd);
        zzbo.zzcF(zzcek.zzbpd.name);
        zzwE().zzjC();
        zzkD();
        if (!TextUtils.isEmpty(zzceh.zzboQ)) {
            if (!zzceh.zzboV) {
                zzf(zzceh);
                return;
            }
            zzwz().beginTransaction();
            try {
                zzf(zzceh);
                zzcek zzH = zzwz().zzH(zzcek.packageName, zzcek.zzbpd.name);
                if (zzH != null) {
                    zzwF().zzyC().zze("Removing conditional user property", zzcek.packageName, zzwA().zzdY(zzcek.zzbpd.name));
                    zzwz().zzI(zzcek.packageName, zzcek.zzbpd.name);
                    if (zzH.zzbpf) {
                        zzwz().zzF(zzcek.packageName, zzcek.zzbpd.name);
                    }
                    if (zzcek.zzbpl != null) {
                        Bundle bundle = null;
                        if (zzcek.zzbpl.zzbpM != null) {
                            bundle = zzcek.zzbpl.zzbpM.zzyt();
                        }
                        zzc(zzwB().zza(zzcek.zzbpl.name, bundle, zzH.zzbpc, zzcek.zzbpl.zzbpN, true, false), zzceh);
                    }
                } else {
                    zzwF().zzyz().zze("Conditional user property doesn't exist", zzcfl.zzdZ(zzcek.packageName), zzwA().zzdY(zzcek.zzbpd.name));
                }
                zzwz().setTransactionSuccessful();
            } finally {
                zzwz().endTransaction();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzc(zzcji zzcji, zzceh zzceh) {
        zzwE().zzjC();
        zzkD();
        if (!TextUtils.isEmpty(zzceh.zzboQ)) {
            if (!zzceh.zzboV) {
                zzf(zzceh);
                return;
            }
            zzwF().zzyC().zzj("Removing user property", zzwA().zzdY(zzcji.name));
            zzwz().beginTransaction();
            try {
                zzf(zzceh);
                zzwz().zzF(zzceh.packageName, zzcji.name);
                zzwz().setTransactionSuccessful();
                zzwF().zzyC().zzj("User property removed", zzwA().zzdY(zzcji.name));
            } finally {
                zzwz().endTransaction();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzd(zzceh zzceh) {
        zzwE().zzjC();
        zzkD();
        zzbo.zzcF(zzceh.packageName);
        zzf(zzceh);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzd(zzcek zzcek) {
        zzceh zzel = zzel(zzcek.packageName);
        if (zzel != null) {
            zzb(zzcek, zzel);
        }
    }

    @WorkerThread
    public final void zze(zzceh zzceh) {
        int i;
        zzceg zzdQ;
        ApplicationInfo applicationInfo;
        zzcen zzwz;
        String zzhl;
        zzwE().zzjC();
        zzkD();
        zzbo.zzu(zzceh);
        zzbo.zzcF(zzceh.packageName);
        if (!TextUtils.isEmpty(zzceh.zzboQ)) {
            zzceg zzdQ2 = zzwz().zzdQ(zzceh.packageName);
            if (zzdQ2 != null && TextUtils.isEmpty(zzdQ2.getGmpAppId()) && !TextUtils.isEmpty(zzceh.zzboQ)) {
                zzdQ2.zzR(0);
                zzwz().zza(zzdQ2);
                zzwC().zzek(zzceh.packageName);
            }
            if (!zzceh.zzboV) {
                zzf(zzceh);
                return;
            }
            long j = zzceh.zzbpa;
            if (j == 0) {
                j = this.zzvw.currentTimeMillis();
            }
            int i2 = zzceh.zzbpb;
            if (i2 == 0 || i2 == 1) {
                i = i2;
            } else {
                zzwF().zzyz().zze("Incorrect app type, assuming installed app. appId, appType", zzcfl.zzdZ(zzceh.packageName), Integer.valueOf(i2));
                i = 0;
            }
            zzwz().beginTransaction();
            try {
                zzdQ = zzwz().zzdQ(zzceh.packageName);
                if (!(zzdQ == null || zzdQ.getGmpAppId() == null || zzdQ.getGmpAppId().equals(zzceh.zzboQ))) {
                    zzwF().zzyz().zzj("New GMP App Id passed in. Removing cached database data. appId", zzcfl.zzdZ(zzdQ.zzhl()));
                    zzwz = zzwz();
                    zzhl = zzdQ.zzhl();
                    zzwz.zzkD();
                    zzwz.zzjC();
                    zzbo.zzcF(zzhl);
                    SQLiteDatabase writableDatabase = zzwz.getWritableDatabase();
                    String[] strArr = {zzhl};
                    int delete = writableDatabase.delete("audience_filter_values", "app_id=?", strArr) + writableDatabase.delete("events", "app_id=?", strArr) + 0 + writableDatabase.delete("user_attributes", "app_id=?", strArr) + writableDatabase.delete("conditional_properties", "app_id=?", strArr) + writableDatabase.delete("apps", "app_id=?", strArr) + writableDatabase.delete("raw_events", "app_id=?", strArr) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr) + writableDatabase.delete("event_filters", "app_id=?", strArr) + writableDatabase.delete("property_filters", "app_id=?", strArr);
                    if (delete > 0) {
                        zzwz.zzwF().zzyD().zze("Deleted application data. app, records", zzhl, Integer.valueOf(delete));
                    }
                    zzdQ = null;
                }
            } catch (SQLiteException e) {
                zzwz.zzwF().zzyx().zze("Error deleting application data. appId, error", zzcfl.zzdZ(zzhl), e);
            } catch (Throwable th) {
                zzwz().endTransaction();
                throw th;
            }
            if (zzdQ != null) {
                if (zzdQ.zzjH() != null && !zzdQ.zzjH().equals(zzceh.zzbgW)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("_pv", zzdQ.zzjH());
                    zzb(new zzcez("_au", new zzcew(bundle), "auto", j), zzceh);
                }
            }
            zzf(zzceh);
            zzcev zzcev = null;
            if (i == 0) {
                zzcev = zzwz().zzE(zzceh.packageName, "_f");
            } else if (i == 1) {
                zzcev = zzwz().zzE(zzceh.packageName, "_v");
            }
            if (zzcev == null) {
                long j2 = (1 + (j / 3600000)) * 3600000;
                if (i == 0) {
                    zzb(new zzcji("_fot", j, Long.valueOf(j2), "auto"), zzceh);
                    zzwE().zzjC();
                    zzkD();
                    Bundle bundle2 = new Bundle();
                    bundle2.putLong("_c", 1);
                    bundle2.putLong("_r", 1);
                    bundle2.putLong("_uwa", 0);
                    bundle2.putLong("_pfo", 0);
                    bundle2.putLong("_sys", 0);
                    bundle2.putLong("_sysu", 0);
                    if (this.mContext.getPackageManager() == null) {
                        zzwF().zzyx().zzj("PackageManager is null, first open report might be inaccurate. appId", zzcfl.zzdZ(zzceh.packageName));
                    } else {
                        PackageInfo packageInfo = null;
                        try {
                            packageInfo = zzbha.zzaP(this.mContext).getPackageInfo(zzceh.packageName, 0);
                        } catch (PackageManager.NameNotFoundException e2) {
                            zzwF().zzyx().zze("Package info is null, first open report might be inaccurate. appId", zzcfl.zzdZ(zzceh.packageName), e2);
                        }
                        if (packageInfo != null) {
                            if (packageInfo.firstInstallTime != 0) {
                                boolean z = false;
                                if (packageInfo.firstInstallTime != packageInfo.lastUpdateTime) {
                                    bundle2.putLong("_uwa", 1);
                                } else {
                                    z = true;
                                }
                                zzb(new zzcji("_fi", j, Long.valueOf(z ? 1 : 0), "auto"), zzceh);
                            }
                        }
                        try {
                            applicationInfo = zzbha.zzaP(this.mContext).getApplicationInfo(zzceh.packageName, 0);
                        } catch (PackageManager.NameNotFoundException e3) {
                            zzwF().zzyx().zze("Application info is null, first open report might be inaccurate. appId", zzcfl.zzdZ(zzceh.packageName), e3);
                            applicationInfo = null;
                        }
                        if (applicationInfo != null) {
                            if ((applicationInfo.flags & 1) != 0) {
                                bundle2.putLong("_sys", 1);
                            }
                            if ((applicationInfo.flags & 128) != 0) {
                                bundle2.putLong("_sysu", 1);
                            }
                        }
                    }
                    zzcen zzwz2 = zzwz();
                    String str = zzceh.packageName;
                    zzbo.zzcF(str);
                    zzwz2.zzjC();
                    zzwz2.zzkD();
                    long zzL = zzwz2.zzL(str, "first_open_count");
                    if (zzL >= 0) {
                        bundle2.putLong("_pfo", zzL);
                    }
                    zzb(new zzcez("_f", new zzcew(bundle2), "auto", j), zzceh);
                } else if (i == 1) {
                    zzb(new zzcji("_fvt", j, Long.valueOf(j2), "auto"), zzceh);
                    zzwE().zzjC();
                    zzkD();
                    Bundle bundle3 = new Bundle();
                    bundle3.putLong("_c", 1);
                    bundle3.putLong("_r", 1);
                    zzb(new zzcez("_v", new zzcew(bundle3), "auto", j), zzceh);
                }
                Bundle bundle4 = new Bundle();
                bundle4.putLong("_et", 1);
                zzb(new zzcez("_e", new zzcew(bundle4), "auto", j), zzceh);
            } else if (zzceh.zzboW) {
                zzb(new zzcez("_cd", new zzcew(new Bundle()), "auto", j), zzceh);
            }
            zzwz().setTransactionSuccessful();
            zzwz().endTransaction();
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zze(zzcek zzcek) {
        zzceh zzel = zzel(zzcek.packageName);
        if (zzel != null) {
            zzc(zzcek, zzel);
        }
    }

    public final String zzem(String str) {
        try {
            return (String) zzwE().zze(new zzcgn(this, str)).get(NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            zzwF().zzyx().zze("Failed to get app instance id. appId", zzcfl.zzdZ(str), e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzkD() {
        if (!this.zzafK) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }

    public final zze zzkq() {
        return this.zzvw;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzl(Runnable runnable) {
        zzwE().zzjC();
        if (this.zzbsP == null) {
            this.zzbsP = new ArrayList();
        }
        this.zzbsP.add(runnable);
    }

    public final zzcfj zzwA() {
        zza((zzchi) this.zzbsw);
        return this.zzbsw;
    }

    public final zzcjl zzwB() {
        zza((zzchi) this.zzbsv);
        return this.zzbsv;
    }

    public final zzcgf zzwC() {
        zza((zzchj) this.zzbss);
        return this.zzbss;
    }

    public final zzcja zzwD() {
        zza((zzchj) this.zzbsr);
        return this.zzbsr;
    }

    public final zzcgg zzwE() {
        zza((zzchj) this.zzbsq);
        return this.zzbsq;
    }

    public final zzcfl zzwF() {
        zza((zzchj) this.zzbsp);
        return this.zzbsp;
    }

    public final zzcfw zzwG() {
        zza((zzchi) this.zzbso);
        return this.zzbso;
    }

    public final zzcem zzwH() {
        return this.zzbsn;
    }

    public final zzcec zzwr() {
        zza((zzchi) this.zzbsI);
        return this.zzbsI;
    }

    public final zzcej zzws() {
        zza((zzchj) this.zzbsH);
        return this.zzbsH;
    }

    public final zzchl zzwt() {
        zza((zzchj) this.zzbsD);
        return this.zzbsD;
    }

    public final zzcfg zzwu() {
        zza((zzchj) this.zzbsE);
        return this.zzbsE;
    }

    public final zzcet zzwv() {
        zza((zzchj) this.zzbsC);
        return this.zzbsC;
    }

    public final zzcid zzww() {
        zza((zzchj) this.zzbsB);
        return this.zzbsB;
    }

    public final zzchz zzwx() {
        zza((zzchj) this.zzbsA);
        return this.zzbsA;
    }

    public final zzcfh zzwy() {
        zza((zzchj) this.zzbsy);
        return this.zzbsy;
    }

    public final zzcen zzwz() {
        zza((zzchj) this.zzbsx);
        return this.zzbsx;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final boolean zzyP() {
        boolean z = false;
        zzkD();
        zzwE().zzjC();
        if (this.zzbsK == null || this.zzbsL == 0 || (this.zzbsK != null && !this.zzbsK.booleanValue() && Math.abs(this.zzvw.elapsedRealtime() - this.zzbsL) > 1000)) {
            this.zzbsL = this.zzvw.elapsedRealtime();
            zzcem.zzxE();
            if (zzwB().zzbv("android.permission.INTERNET") && zzwB().zzbv("android.permission.ACCESS_NETWORK_STATE") && (zzbha.zzaP(this.mContext).zzsl() || (zzcgc.zzj(this.mContext, false) && zzciw.zzk(this.mContext, false)))) {
                z = true;
            }
            this.zzbsK = Boolean.valueOf(z);
            if (this.zzbsK.booleanValue()) {
                this.zzbsK = Boolean.valueOf(zzwB().zzev(zzwu().getGmpAppId()));
            }
        }
        return this.zzbsK.booleanValue();
    }

    public final zzcfl zzyQ() {
        if (this.zzbsp == null || !this.zzbsp.isInitialized()) {
            return null;
        }
        return this.zzbsp;
    }

    /* access modifiers changed from: package-private */
    public final zzcgg zzyR() {
        return this.zzbsq;
    }

    public final AppMeasurement zzyS() {
        return this.zzbst;
    }

    public final FirebaseAnalytics zzyT() {
        return this.zzbsu;
    }

    public final zzcfp zzyU() {
        zza((zzchj) this.zzbsz);
        return this.zzbsz;
    }

    /* access modifiers changed from: package-private */
    public final long zzyY() {
        Long valueOf = Long.valueOf(zzwG().zzbrp.get());
        return valueOf.longValue() == 0 ? this.zzbsX : Math.min(this.zzbsX, valueOf.longValue());
    }

    @WorkerThread
    public final void zzza() {
        String zzyc;
        zzceg zzdQ;
        String str;
        List<Pair<zzcjz, Long>> list;
        String zzxO;
        boolean z = true;
        zzwE().zzjC();
        zzkD();
        this.zzbsW = true;
        try {
            zzcem.zzxE();
            Boolean zzyI = zzwG().zzyI();
            if (zzyI == null) {
                zzwF().zzyz().log("Upload data called on the client side before use of service was decided");
                this.zzbsW = false;
                zzzg();
            } else if (zzyI.booleanValue()) {
                zzwF().zzyx().log("Upload called in the client side when service should be used");
                this.zzbsW = false;
                zzzg();
            } else if (this.zzbsT > 0) {
                zzzc();
                this.zzbsW = false;
                zzzg();
            } else {
                zzwE().zzjC();
                if (this.zzbsO != null) {
                    zzwF().zzyD().log("Uploading requested multiple times");
                    this.zzbsW = false;
                    zzzg();
                } else if (!zzyU().zzlQ()) {
                    zzwF().zzyD().log("Network not connected, ignoring upload request");
                    zzzc();
                    this.zzbsW = false;
                    zzzg();
                } else {
                    long currentTimeMillis = this.zzvw.currentTimeMillis();
                    zzg((String) null, currentTimeMillis - zzcem.zzxP());
                    long j = zzwG().zzbrk.get();
                    if (j != 0) {
                        zzwF().zzyC().zzj("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(currentTimeMillis - j)));
                    }
                    zzyc = zzwz().zzyc();
                    if (!TextUtils.isEmpty(zzyc)) {
                        if (this.zzbsS == -1) {
                            this.zzbsS = zzwz().zzyj();
                        }
                        List<Pair<zzcjz, Long>> zzl = zzwz().zzl(zzyc, this.zzbsn.zzb(zzyc, zzcfb.zzbqb), Math.max(0, this.zzbsn.zzb(zzyc, zzcfb.zzbqc)));
                        if (!zzl.isEmpty()) {
                            Iterator<Pair<zzcjz, Long>> it = zzl.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    str = null;
                                    break;
                                }
                                zzcjz zzcjz = (zzcjz) it.next().first;
                                if (!TextUtils.isEmpty(zzcjz.zzbvR)) {
                                    str = zzcjz.zzbvR;
                                    break;
                                }
                            }
                            if (str != null) {
                                int i = 0;
                                while (true) {
                                    if (i >= zzl.size()) {
                                        break;
                                    }
                                    zzcjz zzcjz2 = (zzcjz) zzl.get(i).first;
                                    if (!TextUtils.isEmpty(zzcjz2.zzbvR) && !zzcjz2.zzbvR.equals(str)) {
                                        list = zzl.subList(0, i);
                                        break;
                                    }
                                    i++;
                                }
                            }
                            list = zzl;
                            zzcjy zzcjy = new zzcjy();
                            zzcjy.zzbvB = new zzcjz[list.size()];
                            ArrayList arrayList = new ArrayList(list.size());
                            boolean z2 = zzcem.zzyb() && this.zzbsn.zzdO(zzyc);
                            for (int i2 = 0; i2 < zzcjy.zzbvB.length; i2++) {
                                zzcjy.zzbvB[i2] = (zzcjz) list.get(i2).first;
                                arrayList.add((Long) list.get(i2).second);
                                zzcjy.zzbvB[i2].zzbvQ = Long.valueOf(zzcem.zzwP());
                                zzcjy.zzbvB[i2].zzbvG = Long.valueOf(currentTimeMillis);
                                zzcjy.zzbvB[i2].zzbvW = Boolean.valueOf(zzcem.zzxE());
                                if (!z2) {
                                    zzcjy.zzbvB[i2].zzbwd = null;
                                }
                            }
                            String zza2 = zzwF().zzz(2) ? zzwA().zza(zzcjy) : null;
                            byte[] zzb = zzwB().zzb(zzcjy);
                            zzxO = zzcem.zzxO();
                            URL url = new URL(zzxO);
                            if (arrayList.isEmpty()) {
                                z = false;
                            }
                            zzbo.zzaf(z);
                            if (this.zzbsO != null) {
                                zzwF().zzyx().log("Set uploading progress before finishing the previous upload");
                            } else {
                                this.zzbsO = new ArrayList(arrayList);
                            }
                            zzwG().zzbrl.set(currentTimeMillis);
                            String str2 = "?";
                            if (zzcjy.zzbvB.length > 0) {
                                str2 = zzcjy.zzbvB[0].zzaH;
                            }
                            zzwF().zzyD().zzd("Uploading data. app, uncompressed size, data", str2, Integer.valueOf(zzb.length), zza2);
                            this.zzbsV = true;
                            zzyU().zza(zzyc, url, zzb, (Map<String, String>) null, new zzcgo(this));
                        }
                    } else {
                        this.zzbsS = -1;
                        String zzaa = zzwz().zzaa(currentTimeMillis - zzcem.zzxP());
                        if (!TextUtils.isEmpty(zzaa) && (zzdQ = zzwz().zzdQ(zzaa)) != null) {
                            zzb(zzdQ);
                        }
                    }
                    this.zzbsW = false;
                    zzzg();
                }
            }
        } catch (MalformedURLException e) {
            zzwF().zzyx().zze("Failed to parse upload URL. Not uploading. appId", zzcfl.zzdZ(zzyc), zzxO);
        } catch (Throwable th) {
            this.zzbsW = false;
            zzzg();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzzd() {
        this.zzbsR++;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzze() {
        zzwE().zzjC();
        zzkD();
        if (!this.zzbsJ) {
            zzwF().zzyB().log("This instance being marked as an uploader");
            zzwE().zzjC();
            zzkD();
            if (zzzf() && zzyX()) {
                int zza2 = zza(this.zzbsN);
                int zzyv = zzwu().zzyv();
                zzwE().zzjC();
                if (zza2 > zzyv) {
                    zzwF().zzyx().zze("Panic: can't downgrade version. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzyv));
                } else if (zza2 < zzyv) {
                    if (zza(zzyv, this.zzbsN)) {
                        zzwF().zzyD().zze("Storage version upgraded. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzyv));
                    } else {
                        zzwF().zzyx().zze("Storage version upgrade failed. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzyv));
                    }
                }
            }
            this.zzbsJ = true;
            zzzc();
        }
    }
}
