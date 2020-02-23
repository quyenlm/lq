package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.appsflyer.AppsFlyerLib;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.analytics.zzl;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.HashMap;
import java.util.Map;

final class zzamv extends zzamh {
    private boolean mStarted;
    private final zzanm zzagA;
    private final zzanm zzagB;
    private final zzaoo zzagC;
    private long zzagD;
    private boolean zzagE;
    private final zzams zzagv;
    private final zzaoe zzagw;
    private final zzaod zzagx;
    private final zzamn zzagy;
    private long zzagz = Long.MIN_VALUE;

    protected zzamv(zzamj zzamj, zzaml zzaml) {
        super(zzamj);
        zzbo.zzu(zzaml);
        this.zzagx = new zzaod(zzamj);
        this.zzagv = new zzams(zzamj);
        this.zzagw = new zzaoe(zzamj);
        this.zzagy = new zzamn(zzamj);
        this.zzagC = new zzaoo(zzkq());
        this.zzagA = new zzamw(this, zzamj);
        this.zzagB = new zzamx(this, zzamj);
    }

    private final void zza(zzamm zzamm, zzall zzall) {
        zzbo.zzu(zzamm);
        zzbo.zzu(zzall);
        zza zza = new zza(zzkp());
        zza.zzaY(zzamm.zzkL());
        zza.enableAdvertisingIdCollection(zzamm.zzkM());
        zzi zzjj = zza.zzjj();
        zzalt zzalt = (zzalt) zzjj.zzb(zzalt.class);
        zzalt.zzbj(ShareConstants.WEB_DIALOG_PARAM_DATA);
        zzalt.zzH(true);
        zzjj.zza((zzj) zzall);
        zzalo zzalo = (zzalo) zzjj.zzb(zzalo.class);
        zzalk zzalk = (zzalk) zzjj.zzb(zzalk.class);
        for (Map.Entry next : zzamm.zzdV().entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            if ("an".equals(str)) {
                zzalk.setAppName(str2);
            } else if ("av".equals(str)) {
                zzalk.setAppVersion(str2);
            } else if (AppsFlyerLib.ATTRIBUTION_ID_COLUMN_NAME.equals(str)) {
                zzalk.setAppId(str2);
            } else if ("aiid".equals(str)) {
                zzalk.setAppInstallerId(str2);
            } else if (GGLiveConstants.PARAM.UID.equals(str)) {
                zzalt.setUserId(str2);
            } else {
                zzalo.set(str, str2);
            }
        }
        zzb("Sending installation campaign to", zzamm.zzkL(), zzall);
        zzjj.zzl(zzky().zzlU());
        zzjj.zzjt();
    }

    private final boolean zzbv(String str) {
        return zzbha.zzaP(getContext()).checkCallingOrSelfPermission(str) == 0;
    }

    private final long zzkT() {
        zzl.zzjC();
        zzkD();
        try {
            return this.zzagv.zzkT();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public final void zzkY() {
        zzb((zzanq) new zzamz(this));
    }

    /* access modifiers changed from: private */
    public final void zzkZ() {
        try {
            this.zzagv.zzkS();
            zzld();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        this.zzagB.zzs(TimeUtils.MILLIS_IN_DAY);
    }

    private final void zzla() {
        if (!this.zzagE && zzank.zzlo() && !this.zzagy.isConnected()) {
            if (this.zzagC.zzu(zzans.zzahS.get().longValue())) {
                this.zzagC.start();
                zzbo("Connecting to service");
                if (this.zzagy.connect()) {
                    zzbo("Connected to service");
                    this.zzagC.clear();
                    onServiceConnected();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e7, code lost:
        if (r12.zzagy.isConnected() == false) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e9, code lost:
        zzbo("Service connected, sending hits to the service");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f2, code lost:
        if (r8.isEmpty() != false) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f4, code lost:
        r0 = r8.get(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0101, code lost:
        if (r12.zzagy.zzb(r0) == false) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0103, code lost:
        r4 = java.lang.Math.max(r4, r0.zzlF());
        r8.remove(r0);
        zzb("Hit sent do device AnalyticsService for delivery", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        r12.zzagv.zzp(r0.zzlF());
        r3.add(java.lang.Long.valueOf(r0.zzlF()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0128, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        zze("Failed to remove hit that was send for delivery", r0);
        zzlf();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r12.zzagv.setTransactionSuccessful();
        r12.zzagv.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x013d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x013e, code lost:
        zze("Failed to commit local dispatch transaction", r0);
        zzlf();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0148, code lost:
        r0 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x014f, code lost:
        if (r12.zzagw.zzlQ() == false) goto L_0x017a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0151, code lost:
        r8 = r12.zzagw.zzu(r8);
        r9 = r8.iterator();
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0160, code lost:
        if (r9.hasNext() == false) goto L_0x0171;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0162, code lost:
        r4 = java.lang.Math.max(r4, r9.next().longValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        r12.zzagv.zzs(r8);
        r3.addAll(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0179, code lost:
        r0 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x017e, code lost:
        if (r3.isEmpty() == false) goto L_0x01b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        r12.zzagv.setTransactionSuccessful();
        r12.zzagv.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x018c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x018d, code lost:
        zze("Failed to commit local dispatch transaction", r0);
        zzlf();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0197, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        zze("Failed to remove successfully uploaded hits", r0);
        zzlf();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        r12.zzagv.setTransactionSuccessful();
        r12.zzagv.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ac, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01ad, code lost:
        zze("Failed to commit local dispatch transaction", r0);
        zzlf();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        r12.zzagv.setTransactionSuccessful();
        r12.zzagv.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01c4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01c5, code lost:
        zze("Failed to commit local dispatch transaction", r0);
        zzlf();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzlb() {
        /*
            r12 = this;
            r1 = 1
            r2 = 0
            com.google.android.gms.analytics.zzl.zzjC()
            r12.zzkD()
            java.lang.String r0 = "Dispatching a batch of local hits"
            r12.zzbo(r0)
            com.google.android.gms.internal.zzamn r0 = r12.zzagy
            boolean r0 = r0.isConnected()
            if (r0 != 0) goto L_0x0028
            r0 = r1
        L_0x0016:
            com.google.android.gms.internal.zzaoe r3 = r12.zzagw
            boolean r3 = r3.zzlQ()
            if (r3 != 0) goto L_0x002a
        L_0x001e:
            if (r0 == 0) goto L_0x002c
            if (r1 == 0) goto L_0x002c
            java.lang.String r0 = "No network or service available. Will retry later"
            r12.zzbo(r0)
        L_0x0027:
            return r2
        L_0x0028:
            r0 = r2
            goto L_0x0016
        L_0x002a:
            r1 = r2
            goto L_0x001e
        L_0x002c:
            int r0 = com.google.android.gms.internal.zzank.zzls()
            int r1 = com.google.android.gms.internal.zzank.zzlt()
            int r0 = java.lang.Math.max(r0, r1)
            long r6 = (long) r0
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r4 = 0
        L_0x0040:
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ all -> 0x01cf }
            r0.beginTransaction()     // Catch:{ all -> 0x01cf }
            r3.clear()     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x00c1 }
            java.util.List r8 = r0.zzo(r6)     // Catch:{ SQLiteException -> 0x00c1 }
            boolean r0 = r8.isEmpty()     // Catch:{ SQLiteException -> 0x00c1 }
            if (r0 == 0) goto L_0x0071
            java.lang.String r0 = "Store is empty, nothing to dispatch"
            r12.zzbo(r0)     // Catch:{ SQLiteException -> 0x00c1 }
            r12.zzlf()     // Catch:{ SQLiteException -> 0x00c1 }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x0067 }
            r0.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x0067 }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x0067 }
            r0.endTransaction()     // Catch:{ SQLiteException -> 0x0067 }
            goto L_0x0027
        L_0x0067:
            r0 = move-exception
            java.lang.String r1 = "Failed to commit local dispatch transaction"
            r12.zze(r1, r0)
            r12.zzlf()
            goto L_0x0027
        L_0x0071:
            java.lang.String r0 = "Hits loaded from store. count"
            int r1 = r8.size()     // Catch:{ SQLiteException -> 0x00c1 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ SQLiteException -> 0x00c1 }
            r12.zza(r0, r1)     // Catch:{ SQLiteException -> 0x00c1 }
            java.util.Iterator r1 = r8.iterator()     // Catch:{ all -> 0x01cf }
        L_0x0082:
            boolean r0 = r1.hasNext()     // Catch:{ all -> 0x01cf }
            if (r0 == 0) goto L_0x00e1
            java.lang.Object r0 = r1.next()     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzanx r0 = (com.google.android.gms.internal.zzanx) r0     // Catch:{ all -> 0x01cf }
            long r10 = r0.zzlF()     // Catch:{ all -> 0x01cf }
            int r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x0082
            java.lang.String r0 = "Database contains successfully uploaded hit"
            java.lang.Long r1 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x01cf }
            int r3 = r8.size()     // Catch:{ all -> 0x01cf }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x01cf }
            r12.zzd(r0, r1, r3)     // Catch:{ all -> 0x01cf }
            r12.zzlf()     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x00b6 }
            r0.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x00b6 }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x00b6 }
            r0.endTransaction()     // Catch:{ SQLiteException -> 0x00b6 }
            goto L_0x0027
        L_0x00b6:
            r0 = move-exception
            java.lang.String r1 = "Failed to commit local dispatch transaction"
            r12.zze(r1, r0)
            r12.zzlf()
            goto L_0x0027
        L_0x00c1:
            r0 = move-exception
            java.lang.String r1 = "Failed to read hits from persisted store"
            r12.zzd(r1, r0)     // Catch:{ all -> 0x01cf }
            r12.zzlf()     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x00d6 }
            r0.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x00d6 }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x00d6 }
            r0.endTransaction()     // Catch:{ SQLiteException -> 0x00d6 }
            goto L_0x0027
        L_0x00d6:
            r0 = move-exception
            java.lang.String r1 = "Failed to commit local dispatch transaction"
            r12.zze(r1, r0)
            r12.zzlf()
            goto L_0x0027
        L_0x00e1:
            com.google.android.gms.internal.zzamn r0 = r12.zzagy     // Catch:{ all -> 0x01cf }
            boolean r0 = r0.isConnected()     // Catch:{ all -> 0x01cf }
            if (r0 == 0) goto L_0x0148
            java.lang.String r0 = "Service connected, sending hits to the service"
            r12.zzbo(r0)     // Catch:{ all -> 0x01cf }
        L_0x00ee:
            boolean r0 = r8.isEmpty()     // Catch:{ all -> 0x01cf }
            if (r0 != 0) goto L_0x0148
            r0 = 0
            java.lang.Object r0 = r8.get(r0)     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzanx r0 = (com.google.android.gms.internal.zzanx) r0     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzamn r1 = r12.zzagy     // Catch:{ all -> 0x01cf }
            boolean r1 = r1.zzb((com.google.android.gms.internal.zzanx) r0)     // Catch:{ all -> 0x01cf }
            if (r1 == 0) goto L_0x0148
            long r10 = r0.zzlF()     // Catch:{ all -> 0x01cf }
            long r4 = java.lang.Math.max(r4, r10)     // Catch:{ all -> 0x01cf }
            r8.remove(r0)     // Catch:{ all -> 0x01cf }
            java.lang.String r1 = "Hit sent do device AnalyticsService for delivery"
            r12.zzb(r1, r0)     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzams r1 = r12.zzagv     // Catch:{ SQLiteException -> 0x0128 }
            long r10 = r0.zzlF()     // Catch:{ SQLiteException -> 0x0128 }
            r1.zzp(r10)     // Catch:{ SQLiteException -> 0x0128 }
            long r0 = r0.zzlF()     // Catch:{ SQLiteException -> 0x0128 }
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ SQLiteException -> 0x0128 }
            r3.add(r0)     // Catch:{ SQLiteException -> 0x0128 }
            goto L_0x00ee
        L_0x0128:
            r0 = move-exception
            java.lang.String r1 = "Failed to remove hit that was send for delivery"
            r12.zze(r1, r0)     // Catch:{ all -> 0x01cf }
            r12.zzlf()     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x013d }
            r0.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x013d }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x013d }
            r0.endTransaction()     // Catch:{ SQLiteException -> 0x013d }
            goto L_0x0027
        L_0x013d:
            r0 = move-exception
            java.lang.String r1 = "Failed to commit local dispatch transaction"
            r12.zze(r1, r0)
            r12.zzlf()
            goto L_0x0027
        L_0x0148:
            r0 = r4
            com.google.android.gms.internal.zzaoe r4 = r12.zzagw     // Catch:{ all -> 0x01cf }
            boolean r4 = r4.zzlQ()     // Catch:{ all -> 0x01cf }
            if (r4 == 0) goto L_0x017a
            com.google.android.gms.internal.zzaoe r4 = r12.zzagw     // Catch:{ all -> 0x01cf }
            java.util.List r8 = r4.zzu(r8)     // Catch:{ all -> 0x01cf }
            java.util.Iterator r9 = r8.iterator()     // Catch:{ all -> 0x01cf }
            r4 = r0
        L_0x015c:
            boolean r0 = r9.hasNext()     // Catch:{ all -> 0x01cf }
            if (r0 == 0) goto L_0x0171
            java.lang.Object r0 = r9.next()     // Catch:{ all -> 0x01cf }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ all -> 0x01cf }
            long r0 = r0.longValue()     // Catch:{ all -> 0x01cf }
            long r4 = java.lang.Math.max(r4, r0)     // Catch:{ all -> 0x01cf }
            goto L_0x015c
        L_0x0171:
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x0197 }
            r0.zzs(r8)     // Catch:{ SQLiteException -> 0x0197 }
            r3.addAll(r8)     // Catch:{ SQLiteException -> 0x0197 }
            r0 = r4
        L_0x017a:
            boolean r4 = r3.isEmpty()     // Catch:{ all -> 0x01cf }
            if (r4 == 0) goto L_0x01b7
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x018c }
            r0.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x018c }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x018c }
            r0.endTransaction()     // Catch:{ SQLiteException -> 0x018c }
            goto L_0x0027
        L_0x018c:
            r0 = move-exception
            java.lang.String r1 = "Failed to commit local dispatch transaction"
            r12.zze(r1, r0)
            r12.zzlf()
            goto L_0x0027
        L_0x0197:
            r0 = move-exception
            java.lang.String r1 = "Failed to remove successfully uploaded hits"
            r12.zze(r1, r0)     // Catch:{ all -> 0x01cf }
            r12.zzlf()     // Catch:{ all -> 0x01cf }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x01ac }
            r0.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x01ac }
            com.google.android.gms.internal.zzams r0 = r12.zzagv     // Catch:{ SQLiteException -> 0x01ac }
            r0.endTransaction()     // Catch:{ SQLiteException -> 0x01ac }
            goto L_0x0027
        L_0x01ac:
            r0 = move-exception
            java.lang.String r1 = "Failed to commit local dispatch transaction"
            r12.zze(r1, r0)
            r12.zzlf()
            goto L_0x0027
        L_0x01b7:
            com.google.android.gms.internal.zzams r4 = r12.zzagv     // Catch:{ SQLiteException -> 0x01c4 }
            r4.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x01c4 }
            com.google.android.gms.internal.zzams r4 = r12.zzagv     // Catch:{ SQLiteException -> 0x01c4 }
            r4.endTransaction()     // Catch:{ SQLiteException -> 0x01c4 }
            r4 = r0
            goto L_0x0040
        L_0x01c4:
            r0 = move-exception
            java.lang.String r1 = "Failed to commit local dispatch transaction"
            r12.zze(r1, r0)
            r12.zzlf()
            goto L_0x0027
        L_0x01cf:
            r0 = move-exception
            com.google.android.gms.internal.zzams r1 = r12.zzagv     // Catch:{ SQLiteException -> 0x01db }
            r1.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x01db }
            com.google.android.gms.internal.zzams r1 = r12.zzagv     // Catch:{ SQLiteException -> 0x01db }
            r1.endTransaction()     // Catch:{ SQLiteException -> 0x01db }
            throw r0
        L_0x01db:
            r0 = move-exception
            java.lang.String r1 = "Failed to commit local dispatch transaction"
            r12.zze(r1, r0)
            r12.zzlf()
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzamv.zzlb():boolean");
    }

    private final void zzle() {
        zzanp zzkw = zzkw();
        if (zzkw.zzlC() && !zzkw.zzbo()) {
            long zzkT = zzkT();
            if (zzkT != 0 && Math.abs(zzkq().currentTimeMillis() - zzkT) <= zzans.zzahr.get().longValue()) {
                zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzank.zzlr()));
                zzkw.schedule();
            }
        }
    }

    private final void zzlf() {
        if (this.zzagA.zzbo()) {
            zzbo("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzagA.cancel();
        zzanp zzkw = zzkw();
        if (zzkw.zzbo()) {
            zzkw.cancel();
        }
    }

    private final long zzlg() {
        if (this.zzagz != Long.MIN_VALUE) {
            return this.zzagz;
        }
        long longValue = zzans.zzahm.get().longValue();
        zzaot zzkx = zzkx();
        zzkx.zzkD();
        if (!zzkx.zzaiP) {
            return longValue;
        }
        zzaot zzkx2 = zzkx();
        zzkx2.zzkD();
        return ((long) zzkx2.zzahZ) * 1000;
    }

    private final void zzlh() {
        zzkD();
        zzl.zzjC();
        this.zzagE = true;
        this.zzagy.disconnect();
        zzld();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x005a A[LOOP:1: B:18:0x005a->B:17:?, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0040 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onServiceConnected() {
        /*
            r6 = this;
            com.google.android.gms.analytics.zzl.zzjC()
            com.google.android.gms.analytics.zzl.zzjC()
            r6.zzkD()
            boolean r0 = com.google.android.gms.internal.zzank.zzlo()
            if (r0 != 0) goto L_0x0014
            java.lang.String r0 = "Service client disabled. Can't dispatch local hits to device AnalyticsService"
            r6.zzbr(r0)
        L_0x0014:
            com.google.android.gms.internal.zzamn r0 = r6.zzagy
            boolean r0 = r0.isConnected()
            if (r0 != 0) goto L_0x0022
            java.lang.String r0 = "Service not connected"
            r6.zzbo(r0)
        L_0x0021:
            return
        L_0x0022:
            com.google.android.gms.internal.zzams r0 = r6.zzagv
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0021
            java.lang.String r0 = "Dispatching local hits to device AnalyticsService"
            r6.zzbo(r0)
        L_0x002f:
            com.google.android.gms.internal.zzams r0 = r6.zzagv     // Catch:{ SQLiteException -> 0x0044 }
            int r1 = com.google.android.gms.internal.zzank.zzls()     // Catch:{ SQLiteException -> 0x0044 }
            long r2 = (long) r1     // Catch:{ SQLiteException -> 0x0044 }
            java.util.List r1 = r0.zzo(r2)     // Catch:{ SQLiteException -> 0x0044 }
            boolean r0 = r1.isEmpty()     // Catch:{ SQLiteException -> 0x0044 }
            if (r0 == 0) goto L_0x005a
            r6.zzld()     // Catch:{ SQLiteException -> 0x0044 }
            goto L_0x0021
        L_0x0044:
            r0 = move-exception
            java.lang.String r1 = "Failed to read hits from store"
            r6.zze(r1, r0)
            r6.zzlf()
            goto L_0x0021
        L_0x004e:
            r1.remove(r0)
            com.google.android.gms.internal.zzams r2 = r6.zzagv     // Catch:{ SQLiteException -> 0x0073 }
            long r4 = r0.zzlF()     // Catch:{ SQLiteException -> 0x0073 }
            r2.zzp(r4)     // Catch:{ SQLiteException -> 0x0073 }
        L_0x005a:
            boolean r0 = r1.isEmpty()
            if (r0 != 0) goto L_0x002f
            r0 = 0
            java.lang.Object r0 = r1.get(r0)
            com.google.android.gms.internal.zzanx r0 = (com.google.android.gms.internal.zzanx) r0
            com.google.android.gms.internal.zzamn r2 = r6.zzagy
            boolean r2 = r2.zzb((com.google.android.gms.internal.zzanx) r0)
            if (r2 != 0) goto L_0x004e
            r6.zzld()
            goto L_0x0021
        L_0x0073:
            r0 = move-exception
            java.lang.String r1 = "Failed to remove hit that was send for delivery"
            r6.zze(r1, r0)
            r6.zzlf()
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzamv.onServiceConnected():void");
    }

    /* access modifiers changed from: package-private */
    public final void start() {
        zzkD();
        zzbo.zza(!this.mStarted, (Object) "Analytics backend already started");
        this.mStarted = true;
        zzkt().zzf(new zzamy(this));
    }

    public final long zza(zzamm zzamm, boolean z) {
        zzbo.zzu(zzamm);
        zzkD();
        zzl.zzjC();
        try {
            this.zzagv.beginTransaction();
            zzams zzams = this.zzagv;
            long zzkK = zzamm.zzkK();
            String zzjX = zzamm.zzjX();
            zzbo.zzcF(zzjX);
            zzams.zzkD();
            zzl.zzjC();
            int delete = zzams.getWritableDatabase().delete("properties", "app_uid=? AND cid<>?", new String[]{String.valueOf(zzkK), zzjX});
            if (delete > 0) {
                zzams.zza("Deleted property records", Integer.valueOf(delete));
            }
            long zza = this.zzagv.zza(zzamm.zzkK(), zzamm.zzjX(), zzamm.zzkL());
            zzamm.zzm(1 + zza);
            zzams zzams2 = this.zzagv;
            zzbo.zzu(zzamm);
            zzams2.zzkD();
            zzl.zzjC();
            SQLiteDatabase writableDatabase = zzams2.getWritableDatabase();
            Map<String, String> zzdV = zzamm.zzdV();
            zzbo.zzu(zzdV);
            Uri.Builder builder = new Uri.Builder();
            for (Map.Entry next : zzdV.entrySet()) {
                builder.appendQueryParameter((String) next.getKey(), (String) next.getValue());
            }
            String encodedQuery = builder.build().getEncodedQuery();
            String str = encodedQuery == null ? "" : encodedQuery;
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_uid", Long.valueOf(zzamm.zzkK()));
            contentValues.put("cid", zzamm.zzjX());
            contentValues.put("tid", zzamm.zzkL());
            contentValues.put(HttpRequestParams.AD_ID, Integer.valueOf(zzamm.zzkM() ? 1 : 0));
            contentValues.put("hits_count", Long.valueOf(zzamm.zzkN()));
            contentValues.put(NativeProtocol.WEB_DIALOG_PARAMS, str);
            try {
                if (writableDatabase.insertWithOnConflict("properties", (String) null, contentValues, 5) == -1) {
                    zzams2.zzbs("Failed to insert/update a property (got -1)");
                }
            } catch (SQLiteException e) {
                zzams2.zze("Error storing a property", e);
            }
            this.zzagv.setTransactionSuccessful();
            try {
                this.zzagv.endTransaction();
            } catch (SQLiteException e2) {
                zze("Failed to end transaction", e2);
            }
            return zza;
        } catch (SQLiteException e3) {
            zze("Failed to update Analytics property", e3);
            try {
                this.zzagv.endTransaction();
            } catch (SQLiteException e4) {
                zze("Failed to end transaction", e4);
            }
            return -1;
        } catch (Throwable th) {
            try {
                this.zzagv.endTransaction();
            } catch (SQLiteException e5) {
                zze("Failed to end transaction", e5);
            }
            throw th;
        }
    }

    public final void zza(zzanx zzanx) {
        Pair<String, Long> zzmb;
        zzbo.zzu(zzanx);
        zzl.zzjC();
        zzkD();
        if (this.zzagE) {
            zzbp("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", zzanx);
        }
        if (TextUtils.isEmpty(zzanx.zzlK()) && (zzmb = zzky().zzlZ().zzmb()) != null) {
            String str = (String) zzmb.first;
            String valueOf = String.valueOf((Long) zzmb.second);
            String sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(str).length()).append(valueOf).append(":").append(str).toString();
            HashMap hashMap = new HashMap(zzanx.zzdV());
            hashMap.put("_m", sb);
            zzanx = new zzanx(this, hashMap, zzanx.zzlG(), zzanx.zzlI(), zzanx.zzlF(), zzanx.zzlE(), zzanx.zzlH());
        }
        zzla();
        if (this.zzagy.zzb(zzanx)) {
            zzbp("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        try {
            this.zzagv.zzc(zzanx);
            zzld();
        } catch (SQLiteException e) {
            zze("Delivery failed to save hit to a database", e);
            zzkr().zza(zzanx, "deliver: failed to insert hit to database");
        }
    }

    /* access modifiers changed from: protected */
    public final void zzb(zzamm zzamm) {
        zzl.zzjC();
        zzb("Sending first hit to property", zzamm.zzkL());
        if (!zzky().zzlV().zzu(zzank.zzly())) {
            String zzlY = zzky().zzlY();
            if (!TextUtils.isEmpty(zzlY)) {
                zzall zza = zzaos.zza(zzkr(), zzlY);
                zzb("Found relevant installation campaign", zza);
                zza(zzamm, zza);
            }
        }
    }

    public final void zzb(zzanq zzanq) {
        long j = this.zzagD;
        zzl.zzjC();
        zzkD();
        long j2 = -1;
        long zzlW = zzky().zzlW();
        if (zzlW != 0) {
            j2 = Math.abs(zzkq().currentTimeMillis() - zzlW);
        }
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(j2));
        zzla();
        try {
            zzlb();
            zzky().zzlX();
            zzld();
            if (zzanq != null) {
                zzanq.zzc((Throwable) null);
            }
            if (this.zzagD != j) {
                this.zzagx.zzlP();
            }
        } catch (Throwable th) {
            zze("Local dispatch failed", th);
            zzky().zzlX();
            zzld();
            if (zzanq != null) {
                zzanq.zzc(th);
            }
        }
    }

    public final void zzbw(String str) {
        zzbo.zzcF(str);
        zzl.zzjC();
        zzall zza = zzaos.zza(zzkr(), str);
        if (zza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String zzlY = zzky().zzlY();
        if (str.equals(zzlY)) {
            zzbr("Ignoring duplicate install campaign");
        } else if (!TextUtils.isEmpty(zzlY)) {
            zzd("Ignoring multiple install campaigns. original, new", zzlY, str);
        } else {
            zzky().zzbz(str);
            if (zzky().zzlV().zzu(zzank.zzly())) {
                zzd("Campaign received too late, ignoring", zza);
                return;
            }
            zzb("Received installation campaign", zza);
            for (zzamm zza2 : this.zzagv.zzq(0)) {
                zza(zza2, zza);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        this.zzagv.initialize();
        this.zzagw.initialize();
        this.zzagy.initialize();
    }

    /* access modifiers changed from: protected */
    public final void zzkX() {
        zzkD();
        zzl.zzjC();
        Context context = zzkp().getContext();
        if (!zzaoj.zzac(context)) {
            zzbr("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzaok.zzad(context)) {
            zzbs("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzac(context)) {
            zzbr("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!CampaignTrackingService.zzad(context)) {
            zzbr("CampaignTrackingService is not registered or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzky().zzlU();
        if (!zzbv("android.permission.ACCESS_NETWORK_STATE")) {
            zzbs("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzlh();
        }
        if (!zzbv("android.permission.INTERNET")) {
            zzbs("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzlh();
        }
        if (zzaok.zzad(getContext())) {
            zzbo("AnalyticsService registered in the app manifest and enabled");
        } else {
            zzbr("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzagE && !this.zzagv.isEmpty()) {
            zzla();
        }
        zzld();
    }

    public final void zzkk() {
        zzl.zzjC();
        zzkD();
        zzbo("Delete all hits from local store");
        try {
            zzams zzams = this.zzagv;
            zzl.zzjC();
            zzams.zzkD();
            zzams.getWritableDatabase().delete("hits2", (String) null, (String[]) null);
            zzams zzams2 = this.zzagv;
            zzl.zzjC();
            zzams2.zzkD();
            zzams2.getWritableDatabase().delete("properties", (String) null, (String[]) null);
            zzld();
        } catch (SQLiteException e) {
            zzd("Failed to delete hits from store", e);
        }
        zzla();
        if (this.zzagy.zzkO()) {
            zzbo("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzko() {
        zzl.zzjC();
        this.zzagD = zzkq().currentTimeMillis();
    }

    public final void zzlc() {
        zzl.zzjC();
        zzkD();
        zzbp("Sync dispatching local hits");
        long j = this.zzagD;
        zzla();
        try {
            zzlb();
            zzky().zzlX();
            zzld();
            if (this.zzagD != j) {
                this.zzagx.zzlP();
            }
        } catch (Throwable th) {
            zze("Sync local dispatch failed", th);
            zzld();
        }
    }

    public final void zzld() {
        boolean z;
        long min;
        zzl.zzjC();
        zzkD();
        if (!(!this.zzagE && zzlg() > 0)) {
            this.zzagx.unregister();
            zzlf();
        } else if (this.zzagv.isEmpty()) {
            this.zzagx.unregister();
            zzlf();
        } else {
            if (!zzans.zzahN.get().booleanValue()) {
                this.zzagx.zzlN();
                z = this.zzagx.isConnected();
            } else {
                z = true;
            }
            if (z) {
                zzle();
                long zzlg = zzlg();
                long zzlW = zzky().zzlW();
                if (zzlW != 0) {
                    min = zzlg - Math.abs(zzkq().currentTimeMillis() - zzlW);
                    if (min <= 0) {
                        min = Math.min(zzank.zzlq(), zzlg);
                    }
                } else {
                    min = Math.min(zzank.zzlq(), zzlg);
                }
                zza("Dispatch scheduled (ms)", Long.valueOf(min));
                if (this.zzagA.zzbo()) {
                    this.zzagA.zzt(Math.max(1, min + this.zzagA.zzlz()));
                } else {
                    this.zzagA.zzs(min);
                }
            } else {
                zzlf();
                zzle();
            }
        }
    }

    public final void zzr(long j) {
        zzl.zzjC();
        zzkD();
        if (j < 0) {
            j = 0;
        }
        this.zzagz = j;
        zzld();
    }
}
