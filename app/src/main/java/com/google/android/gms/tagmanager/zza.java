package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;

public final class zza {
    private static Object zzbDk = new Object();
    private static zza zzbDl;
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
    private zzd zzbDj;
    private final zze zzvw;

    private zza(Context context) {
        this(context, (zzd) null, zzi.zzrY());
    }

    private zza(Context context, zzd zzd, zze zze) {
        this.zzbDe = 900000;
        this.zzbDf = NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS;
        this.mClosed = false;
        this.zzbDi = new Object();
        this.zzbDj = new zzb(this);
        this.zzvw = zze;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        this.zzbDg = this.zzvw.currentTimeMillis();
        this.zzYV = new Thread(new zzc(this));
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
            com.google.android.gms.tagmanager.zzd r0 = r4.zzbDj
            com.google.android.gms.ads.identifier.AdvertisingIdClient$Info r0 = r0.zzAD()
            if (r0 == 0) goto L_0x001e
            r4.zzafw = r0
            com.google.android.gms.common.util.zze r0 = r4.zzvw
            long r0 = r0.currentTimeMillis()
            r4.zzbDh = r0
            java.lang.String r0 = "Obtained fresh AdvertisingId info from GmsCore."
            com.google.android.gms.tagmanager.zzdj.zzaS(r0)
        L_0x001e:
            monitor-enter(r4)
            r4.notifyAll()     // Catch:{ all -> 0x0039 }
            monitor-exit(r4)     // Catch:{ all -> 0x0039 }
            java.lang.Object r1 = r4.zzbDi     // Catch:{ InterruptedException -> 0x0032 }
            monitor-enter(r1)     // Catch:{ InterruptedException -> 0x0032 }
            java.lang.Object r0 = r4.zzbDi     // Catch:{ all -> 0x002f }
            long r2 = r4.zzbDe     // Catch:{ all -> 0x002f }
            r0.wait(r2)     // Catch:{ all -> 0x002f }
            monitor-exit(r1)     // Catch:{ all -> 0x002f }
            goto L_0x0005
        L_0x002f:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002f }
            throw r0     // Catch:{ InterruptedException -> 0x0032 }
        L_0x0032:
            r0 = move-exception
            java.lang.String r0 = "sleep interrupted in AdvertiserDataPoller thread; continuing"
            com.google.android.gms.tagmanager.zzdj.zzaS(r0)
            goto L_0x0005
        L_0x0039:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0039 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zza.zzAC():void");
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

    public static zza zzbl(Context context) {
        if (zzbDl == null) {
            synchronized (zzbDk) {
                if (zzbDl == null) {
                    zza zza = new zza(context);
                    zzbDl = zza;
                    zza.zzYV.start();
                }
            }
        }
        return zzbDl;
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
