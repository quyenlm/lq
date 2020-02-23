package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;

public final class zzcua {
    private static Object zzbDk = new Object();
    private static zzcua zzbHF;
    private volatile boolean mClosed;
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Thread zzYV;
    private volatile AdvertisingIdClient.Info zzafw;
    private volatile long zzbDe;
    private volatile long zzbDf;
    private volatile long zzbDg;
    private volatile long zzbDh;
    private final Object zzbDi;
    /* access modifiers changed from: private */
    public volatile boolean zzbHD;
    private zzcud zzbHE;
    private final zze zzvw;

    private zzcua(Context context) {
        this(context, (zzcud) null, zzi.zzrY());
    }

    private zzcua(Context context, zzcud zzcud, zze zze) {
        this.zzbDe = 900000;
        this.zzbDf = NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS;
        this.zzbHD = true;
        this.mClosed = false;
        this.zzbDi = new Object();
        this.zzbHE = new zzcub(this);
        this.zzvw = zze;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        this.zzbDg = this.zzvw.currentTimeMillis();
        this.zzYV = new Thread(new zzcuc(this));
    }

    private final void zzAA() {
        if (this.zzvw.currentTimeMillis() - this.zzbDg > this.zzbDf) {
            synchronized (this.zzbDi) {
                this.zzbDi.notify();
            }
            this.zzbDg = this.zzvw.currentTimeMillis();
        }
    }

    private final void zzAB() {
        if (this.zzvw.currentTimeMillis() - this.zzbDh > 3600000) {
            this.zzafw = null;
        }
    }

    /* access modifiers changed from: private */
    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:653)
        	at java.util.ArrayList.get(ArrayList.java:429)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public final void zzAC() {
        /*
            r4 = this;
            r0 = 10
            android.os.Process.setThreadPriority(r0)
        L_0x0005:
            boolean r0 = r4.mClosed
            r0 = 0
            boolean r1 = r4.zzbHD
            if (r1 == 0) goto L_0x0012
            com.google.android.gms.internal.zzcud r0 = r4.zzbHE
            com.google.android.gms.ads.identifier.AdvertisingIdClient$Info r0 = r0.zzAD()
        L_0x0012:
            if (r0 == 0) goto L_0x0023
            r4.zzafw = r0
            com.google.android.gms.common.util.zze r0 = r4.zzvw
            long r0 = r0.currentTimeMillis()
            r4.zzbDh = r0
            java.lang.String r0 = "Obtained fresh AdvertisingId info from GmsCore."
            com.google.android.gms.internal.zzcvk.zzaS(r0)
        L_0x0023:
            monitor-enter(r4)
            r4.notifyAll()     // Catch:{ all -> 0x003e }
            monitor-exit(r4)     // Catch:{ all -> 0x003e }
            java.lang.Object r1 = r4.zzbDi     // Catch:{ InterruptedException -> 0x0037 }
            monitor-enter(r1)     // Catch:{ InterruptedException -> 0x0037 }
            java.lang.Object r0 = r4.zzbDi     // Catch:{ all -> 0x0034 }
            long r2 = r4.zzbDe     // Catch:{ all -> 0x0034 }
            r0.wait(r2)     // Catch:{ all -> 0x0034 }
            monitor-exit(r1)     // Catch:{ all -> 0x0034 }
            goto L_0x0005
        L_0x0034:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0034 }
            throw r0     // Catch:{ InterruptedException -> 0x0037 }
        L_0x0037:
            r0 = move-exception
            java.lang.String r0 = "sleep interrupted in AdvertiserDataPoller thread; continuing"
            com.google.android.gms.internal.zzcvk.zzaS(r0)
            goto L_0x0005
        L_0x003e:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003e }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcua.zzAC():void");
    }

    private final void zzAz() {
        synchronized (this) {
            try {
                zzAA();
                wait(500);
            } catch (InterruptedException e) {
            }
        }
    }

    public static zzcua zzbu(Context context) {
        if (zzbHF == null) {
            synchronized (zzbDk) {
                if (zzbHF == null) {
                    zzcua zzcua = new zzcua(context);
                    zzbHF = zzcua;
                    zzcua.zzYV.start();
                }
            }
        }
        return zzbHF;
    }

    public final boolean isLimitAdTrackingEnabled() {
        if (this.zzafw == null) {
            zzAz();
        } else {
            zzAA();
        }
        zzAB();
        if (this.zzafw == null) {
            return true;
        }
        return this.zzafw.isLimitAdTrackingEnabled();
    }

    public final String zzAy() {
        if (this.zzafw == null) {
            zzAz();
        } else {
            zzAA();
        }
        zzAB();
        if (this.zzafw == null) {
            return null;
        }
        return this.zzafw.getId();
    }
}
