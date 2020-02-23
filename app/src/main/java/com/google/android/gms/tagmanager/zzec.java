package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class zzec implements zzcc {
    /* access modifiers changed from: private */
    public static final String zzagp = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", new Object[]{"gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time"});
    /* access modifiers changed from: private */
    public final Context mContext;
    private final zzee zzbFu;
    private volatile zzbe zzbFv;
    private final zzcd zzbFw;
    /* access modifiers changed from: private */
    public final String zzbFx;
    private long zzbFy;
    private final int zzbFz;
    /* access modifiers changed from: private */
    public zze zzvw;

    zzec(zzcd zzcd, Context context) {
        this(zzcd, context, "gtm_urls.db", 2000);
    }

    private zzec(zzcd zzcd, Context context, String str, int i) {
        this.mContext = context.getApplicationContext();
        this.zzbFx = str;
        this.zzbFw = zzcd;
        this.zzvw = zzi.zzrY();
        this.zzbFu = new zzee(this, this.mContext, this.zzbFx);
        this.zzbFv = new zzfv(this.mContext, new zzed(this));
        this.zzbFy = 0;
        this.zzbFz = 2000;
    }

    private final int zzBA() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase zzfg = zzfg("Error opening database for getNumStoredHits.");
        if (zzfg != null) {
            try {
                Cursor rawQuery = zzfg.rawQuery("SELECT COUNT(*) from gtm_hits", (String[]) null);
                if (rawQuery.moveToFirst()) {
                    i = (int) rawQuery.getLong(0);
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
            } catch (SQLiteException e) {
                zzdj.zzaT("Error getting numStoredHits");
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzBB() {
        /*
            r10 = this;
            r8 = 0
            r9 = 0
            java.lang.String r0 = "Error opening database for getNumStoredHits."
            android.database.sqlite.SQLiteDatabase r0 = r10.zzfg(r0)
            if (r0 != 0) goto L_0x000b
        L_0x000a:
            return r8
        L_0x000b:
            java.lang.String r1 = "gtm_hits"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x002f, all -> 0x003d }
            r3 = 0
            java.lang.String r4 = "hit_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x002f, all -> 0x003d }
            r3 = 1
            java.lang.String r4 = "hit_first_send_time"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x002f, all -> 0x003d }
            java.lang.String r3 = "hit_first_send_time=0"
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x002f, all -> 0x003d }
            int r0 = r1.getCount()     // Catch:{ SQLiteException -> 0x004d, all -> 0x0045 }
            if (r1 == 0) goto L_0x002d
            r1.close()
        L_0x002d:
            r8 = r0
            goto L_0x000a
        L_0x002f:
            r0 = move-exception
            r0 = r9
        L_0x0031:
            java.lang.String r1 = "Error getting num untried hits"
            com.google.android.gms.tagmanager.zzdj.zzaT(r1)     // Catch:{ all -> 0x0049 }
            if (r0 == 0) goto L_0x0050
            r0.close()
            r0 = r8
            goto L_0x002d
        L_0x003d:
            r0 = move-exception
            r2 = r0
        L_0x003f:
            if (r9 == 0) goto L_0x0044
            r9.close()
        L_0x0044:
            throw r2
        L_0x0045:
            r0 = move-exception
            r2 = r0
            r9 = r1
            goto L_0x003f
        L_0x0049:
            r1 = move-exception
            r2 = r1
            r9 = r0
            goto L_0x003f
        L_0x004d:
            r0 = move-exception
            r0 = r1
            goto L_0x0031
        L_0x0050:
            r0 = r8
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzec.zzBB():int");
    }

    private final List<String> zzbD(int i) {
        Cursor cursor;
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            zzdj.zzaT("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase zzfg = zzfg("Error opening database for peekHitIds.");
        if (zzfg == null) {
            return arrayList;
        }
        try {
            cursor = zzfg.query("gtm_hits", new String[]{"hit_id"}, (String) null, (String[]) null, (String) null, (String) null, String.format("%s ASC", new Object[]{"hit_id"}), Integer.toString(i));
            try {
                if (cursor.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(cursor.getLong(0)));
                    } while (cursor.moveToNext());
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                e = e;
                try {
                    String valueOf = String.valueOf(e.getMessage());
                    zzdj.zzaT(valueOf.length() != 0 ? "Error in peekHits fetching hitIds: ".concat(valueOf) : new String("Error in peekHits fetching hitIds: "));
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                }
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00cd, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ce, code lost:
        r3 = null;
        r11 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00f8, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0178, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x017b, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x017c, code lost:
        r3 = r13;
        r11 = r12;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0178 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x0015] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<com.google.android.gms.tagmanager.zzbx> zzbE(int r17) {
        /*
            r16 = this;
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>()
            java.lang.String r2 = "Error opening database for peekHits"
            r0 = r16
            android.database.sqlite.SQLiteDatabase r2 = r0.zzfg(r2)
            if (r2 != 0) goto L_0x0011
            r11 = r12
        L_0x0010:
            return r11
        L_0x0011:
            r13 = 0
            java.lang.String r3 = "gtm_hits"
            r4 = 3
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            r5 = 0
            java.lang.String r6 = "hit_id"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            r5 = 1
            java.lang.String r6 = "hit_time"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            r5 = 2
            java.lang.String r6 = "hit_first_send_time"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "%s ASC"
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            r11 = 0
            java.lang.String r14 = "hit_id"
            r10[r11] = r14     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            java.lang.String r9 = java.lang.String.format(r9, r10)     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            r10 = 40
            java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            android.database.Cursor r13 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x00cd, all -> 0x0178 }
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x017b, all -> 0x0178 }
            r11.<init>()     // Catch:{ SQLiteException -> 0x017b, all -> 0x0178 }
            boolean r3 = r13.moveToFirst()     // Catch:{ SQLiteException -> 0x0180, all -> 0x0178 }
            if (r3 == 0) goto L_0x006a
        L_0x004d:
            com.google.android.gms.tagmanager.zzbx r3 = new com.google.android.gms.tagmanager.zzbx     // Catch:{ SQLiteException -> 0x0180, all -> 0x0178 }
            r4 = 0
            long r4 = r13.getLong(r4)     // Catch:{ SQLiteException -> 0x0180, all -> 0x0178 }
            r6 = 1
            long r6 = r13.getLong(r6)     // Catch:{ SQLiteException -> 0x0180, all -> 0x0178 }
            r8 = 2
            long r8 = r13.getLong(r8)     // Catch:{ SQLiteException -> 0x0180, all -> 0x0178 }
            r3.<init>(r4, r6, r8)     // Catch:{ SQLiteException -> 0x0180, all -> 0x0178 }
            r11.add(r3)     // Catch:{ SQLiteException -> 0x0180, all -> 0x0178 }
            boolean r3 = r13.moveToNext()     // Catch:{ SQLiteException -> 0x0180, all -> 0x0178 }
            if (r3 != 0) goto L_0x004d
        L_0x006a:
            if (r13 == 0) goto L_0x006f
            r13.close()
        L_0x006f:
            r12 = 0
            java.lang.String r3 = "gtm_hits"
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0176 }
            r5 = 0
            java.lang.String r6 = "hit_id"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0176 }
            r5 = 1
            java.lang.String r6 = "hit_url"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0176 }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "%s ASC"
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ SQLiteException -> 0x0176 }
            r14 = 0
            java.lang.String r15 = "hit_id"
            r10[r14] = r15     // Catch:{ SQLiteException -> 0x0176 }
            java.lang.String r9 = java.lang.String.format(r9, r10)     // Catch:{ SQLiteException -> 0x0176 }
            r10 = 40
            java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch:{ SQLiteException -> 0x0176 }
            android.database.Cursor r3 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x0176 }
            boolean r2 = r3.moveToFirst()     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            if (r2 == 0) goto L_0x00c6
            r4 = r12
        L_0x00a2:
            r0 = r3
            android.database.sqlite.SQLiteCursor r0 = (android.database.sqlite.SQLiteCursor) r0     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            r2 = r0
            android.database.CursorWindow r2 = r2.getWindow()     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            int r2 = r2.getNumRows()     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            if (r2 <= 0) goto L_0x00fc
            java.lang.Object r2 = r11.get(r4)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            com.google.android.gms.tagmanager.zzbx r2 = (com.google.android.gms.tagmanager.zzbx) r2     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            r5 = 1
            java.lang.String r5 = r3.getString(r5)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            r2.zzfl(r5)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
        L_0x00be:
            int r2 = r4 + 1
            boolean r4 = r3.moveToNext()     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            if (r4 != 0) goto L_0x0184
        L_0x00c6:
            if (r3 == 0) goto L_0x0010
            r3.close()
            goto L_0x0010
        L_0x00cd:
            r2 = move-exception
            r3 = r13
            r11 = r12
        L_0x00d0:
            java.lang.String r4 = "Error in peekHits fetching hitIds: "
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x00f4 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x00f4 }
            int r5 = r2.length()     // Catch:{ all -> 0x00f4 }
            if (r5 == 0) goto L_0x00ee
            java.lang.String r2 = r4.concat(r2)     // Catch:{ all -> 0x00f4 }
        L_0x00e4:
            com.google.android.gms.tagmanager.zzdj.zzaT(r2)     // Catch:{ all -> 0x00f4 }
            if (r3 == 0) goto L_0x0010
            r3.close()
            goto L_0x0010
        L_0x00ee:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00f4 }
            r2.<init>(r4)     // Catch:{ all -> 0x00f4 }
            goto L_0x00e4
        L_0x00f4:
            r2 = move-exception
            r13 = r3
        L_0x00f6:
            if (r13 == 0) goto L_0x00fb
            r13.close()
        L_0x00fb:
            throw r2
        L_0x00fc:
            java.lang.String r5 = "HitString for hitId %d too large.  Hit will be deleted."
            r2 = 1
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            r7 = 0
            java.lang.Object r2 = r11.get(r4)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            com.google.android.gms.tagmanager.zzbx r2 = (com.google.android.gms.tagmanager.zzbx) r2     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            long r8 = r2.zzBm()     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            java.lang.Long r2 = java.lang.Long.valueOf(r8)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            r6[r7] = r2     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            java.lang.String r2 = java.lang.String.format(r5, r6)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            com.google.android.gms.tagmanager.zzdj.zzaT(r2)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0173 }
            goto L_0x00be
        L_0x011a:
            r2 = move-exception
            r13 = r3
        L_0x011c:
            java.lang.String r3 = "Error in peekHits fetching hit url: "
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x015e }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x015e }
            int r4 = r2.length()     // Catch:{ all -> 0x015e }
            if (r4 == 0) goto L_0x0165
            java.lang.String r2 = r3.concat(r2)     // Catch:{ all -> 0x015e }
        L_0x0130:
            com.google.android.gms.tagmanager.zzdj.zzaT(r2)     // Catch:{ all -> 0x015e }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ all -> 0x015e }
            r4.<init>()     // Catch:{ all -> 0x015e }
            r5 = 0
            r0 = r11
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ all -> 0x015e }
            r2 = r0
            int r7 = r2.size()     // Catch:{ all -> 0x015e }
            r3 = 0
            r6 = r3
        L_0x0143:
            if (r6 >= r7) goto L_0x016b
            java.lang.Object r3 = r2.get(r6)     // Catch:{ all -> 0x015e }
            int r6 = r6 + 1
            com.google.android.gms.tagmanager.zzbx r3 = (com.google.android.gms.tagmanager.zzbx) r3     // Catch:{ all -> 0x015e }
            java.lang.String r8 = r3.zzBo()     // Catch:{ all -> 0x015e }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x015e }
            if (r8 == 0) goto L_0x015a
            if (r5 != 0) goto L_0x016b
            r5 = 1
        L_0x015a:
            r4.add(r3)     // Catch:{ all -> 0x015e }
            goto L_0x0143
        L_0x015e:
            r2 = move-exception
        L_0x015f:
            if (r13 == 0) goto L_0x0164
            r13.close()
        L_0x0164:
            throw r2
        L_0x0165:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x015e }
            r2.<init>(r3)     // Catch:{ all -> 0x015e }
            goto L_0x0130
        L_0x016b:
            if (r13 == 0) goto L_0x0170
            r13.close()
        L_0x0170:
            r11 = r4
            goto L_0x0010
        L_0x0173:
            r2 = move-exception
            r13 = r3
            goto L_0x015f
        L_0x0176:
            r2 = move-exception
            goto L_0x011c
        L_0x0178:
            r2 = move-exception
            goto L_0x00f6
        L_0x017b:
            r2 = move-exception
            r3 = r13
            r11 = r12
            goto L_0x00d0
        L_0x0180:
            r2 = move-exception
            r3 = r13
            goto L_0x00d0
        L_0x0184:
            r4 = r2
            goto L_0x00a2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzec.zzbE(int):java.util.List");
    }

    private final void zzd(String[] strArr) {
        SQLiteDatabase zzfg;
        boolean z = true;
        if (strArr != null && strArr.length != 0 && (zzfg = zzfg("Error opening database for deleteHits.")) != null) {
            try {
                zzfg.delete("gtm_hits", String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                zzcd zzcd = this.zzbFw;
                if (zzBA() != 0) {
                    z = false;
                }
                zzcd.zzar(z);
            } catch (SQLiteException e) {
                zzdj.zzaT("Error deleting hits");
            }
        }
    }

    private final SQLiteDatabase zzfg(String str) {
        try {
            return this.zzbFu.getWritableDatabase();
        } catch (SQLiteException e) {
            zzdj.zzaT(str);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public final void zzh(long j, long j2) {
        SQLiteDatabase zzfg = zzfg("Error opening database for getNumStoredHits.");
        if (zzfg != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_first_send_time", Long.valueOf(j2));
            try {
                zzfg.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
            } catch (SQLiteException e) {
                zzdj.zzaT(new StringBuilder(69).append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ").append(j).toString());
                zzp(j);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zzp(long j) {
        zzd(new String[]{String.valueOf(j)});
    }

    public final void dispatch() {
        zzdj.v("GTM Dispatch running...");
        if (this.zzbFv.zzBf()) {
            List<zzbx> zzbE = zzbE(40);
            if (zzbE.isEmpty()) {
                zzdj.v("...nothing to dispatch");
                this.zzbFw.zzar(true);
                return;
            }
            this.zzbFv.zzK(zzbE);
            if (zzBB() > 0) {
                zzfo.zzBV().dispatch();
            }
        }
    }

    public final void zzb(long j, String str) {
        boolean z = true;
        long currentTimeMillis = this.zzvw.currentTimeMillis();
        if (currentTimeMillis > this.zzbFy + TimeUtils.MILLIS_IN_DAY) {
            this.zzbFy = currentTimeMillis;
            SQLiteDatabase zzfg = zzfg("Error opening database for deleteStaleHits.");
            if (zzfg != null) {
                zzfg.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.zzvw.currentTimeMillis() - 2592000000L)});
                zzcd zzcd = this.zzbFw;
                if (zzBA() != 0) {
                    z = false;
                }
                zzcd.zzar(z);
            }
        }
        int zzBA = (zzBA() - this.zzbFz) + 1;
        if (zzBA > 0) {
            List<String> zzbD = zzbD(zzBA);
            zzdj.v(new StringBuilder(51).append("Store full, deleting ").append(zzbD.size()).append(" hits to make room.").toString());
            zzd((String[]) zzbD.toArray(new String[0]));
        }
        SQLiteDatabase zzfg2 = zzfg("Error opening database for putHit");
        if (zzfg2 != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_time", Long.valueOf(j));
            contentValues.put("hit_url", str);
            contentValues.put("hit_first_send_time", 0);
            try {
                zzfg2.insert("gtm_hits", (String) null, contentValues);
                this.zzbFw.zzar(false);
            } catch (SQLiteException e) {
                zzdj.zzaT("Error storing hit");
            }
        }
    }
}
