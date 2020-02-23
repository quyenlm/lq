package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzg;
import com.google.android.gms.common.util.zzi;
import com.tencent.qqgamemi.util.TimeUtils;
import com.tencent.tp.a.h;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

final class zzcvn implements zzcvb {
    /* access modifiers changed from: private */
    public static final String zzagp = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT UNIQUE, '%s' TEXT, '%s' TEXT);", new Object[]{"gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time", "hit_method", "hit_unique_id", "hit_headers", "hit_body"});
    /* access modifiers changed from: private */
    public static final String zzbIv = String.format("CREATE TABLE IF NOT EXISTS %s ('%s' TEXT UNIQUE);", new Object[]{"gtm_hit_unique_ids", "hit_unique_id"});
    /* access modifiers changed from: private */
    public static final String zzbIw = String.format("CREATE TRIGGER IF NOT EXISTS %s DELETE ON %s FOR EACH ROW WHEN OLD.%s NOTNULL BEGIN     INSERT OR IGNORE INTO %s (%s) VALUES (OLD.%s); END;", new Object[]{"save_unique_on_delete", "gtm_hits", "hit_unique_id", "gtm_hit_unique_ids", "hit_unique_id", "hit_unique_id"});
    /* access modifiers changed from: private */
    public static final String zzbIx = String.format("CREATE TRIGGER IF NOT EXISTS %s BEFORE INSERT ON %s FOR EACH ROW WHEN NEW.%s NOT NULL BEGIN     SELECT RAISE(ABORT, 'Duplicate unique ID.')     WHERE EXISTS (SELECT 1 FROM %s WHERE %s = NEW.%s); END;", new Object[]{"check_unique_on_insert", "gtm_hits", "hit_unique_id", "gtm_hit_unique_ids", "hit_unique_id", "hit_unique_id"});
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final String zzbFx;
    private long zzbFy;
    private final int zzbFz;
    private final zzcvc zzbIA;
    private final zzcvp zzbIy;
    private volatile zzcus zzbIz;
    /* access modifiers changed from: private */
    public zze zzvw;

    zzcvn(zzcvc zzcvc, Context context) {
        this(zzcvc, context, "gtm_urls.db", 2000);
    }

    private zzcvn(zzcvc zzcvc, Context context, String str, int i) {
        this.mContext = context.getApplicationContext();
        this.zzbFx = str;
        this.zzbIA = zzcvc;
        this.zzvw = zzi.zzrY();
        this.zzbIy = new zzcvp(this, this.mContext, this.zzbFx);
        this.zzbIz = new zzcwj(this.mContext, new zzcvo(this));
        this.zzbFy = 0;
        this.zzbFz = 2000;
    }

    private final int zzBB() {
        Cursor cursor;
        int i;
        SQLiteDatabase zzfg = zzfg("Error opening database for getNumStoredHits.");
        if (zzfg == null) {
            return 0;
        }
        try {
            cursor = zzfg.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", (String[]) null, (String) null, (String) null, (String) null);
            try {
                i = cursor.getCount();
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                e = e;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th) {
            th = th;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return i;
        try {
            String valueOf = String.valueOf(e.getMessage());
            zzcvk.zzaT(valueOf.length() != 0 ? "Error getting num untried hits: ".concat(valueOf) : new String("Error getting num untried hits: "));
            if (cursor != null) {
                cursor.close();
                i = 0;
            } else {
                i = 0;
            }
            return i;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private final List<String> zzbD(int i) {
        Cursor cursor;
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            zzcvk.zzaT("Invalid maxHits specified. Skipping");
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
                    zzcvk.zzaT(valueOf.length() != 0 ? "Error in peekHits fetching hitIds: ".concat(valueOf) : new String("Error in peekHits fetching hitIds: "));
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

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0118, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0119, code lost:
        r3 = null;
        r11 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0143, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0204, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0207, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0208, code lost:
        r3 = r13;
        r11 = r12;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0143  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0204 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x0015] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<com.google.android.gms.internal.zzcuw> zzbE(int r17) {
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
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            r5 = 0
            java.lang.String r6 = "hit_id"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            r5 = 1
            java.lang.String r6 = "hit_time"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            r5 = 2
            java.lang.String r6 = "hit_first_send_time"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "%s ASC"
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            r11 = 0
            java.lang.String r14 = "hit_id"
            r10[r11] = r14     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            java.lang.String r9 = java.lang.String.format(r9, r10)     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            r10 = 40
            java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            android.database.Cursor r13 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x0118, all -> 0x0204 }
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x0207, all -> 0x0204 }
            r11.<init>()     // Catch:{ SQLiteException -> 0x0207, all -> 0x0204 }
            boolean r3 = r13.moveToFirst()     // Catch:{ SQLiteException -> 0x020c, all -> 0x0204 }
            if (r3 == 0) goto L_0x006a
        L_0x004d:
            com.google.android.gms.internal.zzcuw r3 = new com.google.android.gms.internal.zzcuw     // Catch:{ SQLiteException -> 0x020c, all -> 0x0204 }
            r4 = 0
            long r4 = r13.getLong(r4)     // Catch:{ SQLiteException -> 0x020c, all -> 0x0204 }
            r6 = 1
            long r6 = r13.getLong(r6)     // Catch:{ SQLiteException -> 0x020c, all -> 0x0204 }
            r8 = 2
            long r8 = r13.getLong(r8)     // Catch:{ SQLiteException -> 0x020c, all -> 0x0204 }
            r3.<init>(r4, r6, r8)     // Catch:{ SQLiteException -> 0x020c, all -> 0x0204 }
            r11.add(r3)     // Catch:{ SQLiteException -> 0x020c, all -> 0x0204 }
            boolean r3 = r13.moveToNext()     // Catch:{ SQLiteException -> 0x020c, all -> 0x0204 }
            if (r3 != 0) goto L_0x004d
        L_0x006a:
            if (r13 == 0) goto L_0x006f
            r13.close()
        L_0x006f:
            r12 = 0
            java.lang.String r3 = "gtm_hits"
            r4 = 5
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0202 }
            r5 = 0
            java.lang.String r6 = "hit_id"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0202 }
            r5 = 1
            java.lang.String r6 = "hit_url"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0202 }
            r5 = 2
            java.lang.String r6 = "hit_method"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0202 }
            r5 = 3
            java.lang.String r6 = "hit_headers"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0202 }
            r5 = 4
            java.lang.String r6 = "hit_body"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0202 }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "%s ASC"
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ SQLiteException -> 0x0202 }
            r14 = 0
            java.lang.String r15 = "hit_id"
            r10[r14] = r15     // Catch:{ SQLiteException -> 0x0202 }
            java.lang.String r9 = java.lang.String.format(r9, r10)     // Catch:{ SQLiteException -> 0x0202 }
            r10 = 40
            java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch:{ SQLiteException -> 0x0202 }
            android.database.Cursor r3 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x0202 }
            boolean r2 = r3.moveToFirst()     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            if (r2 == 0) goto L_0x0174
            r5 = r12
        L_0x00b1:
            r0 = r3
            android.database.sqlite.SQLiteCursor r0 = (android.database.sqlite.SQLiteCursor) r0     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r2 = r0
            android.database.CursorWindow r2 = r2.getWindow()     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            int r2 = r2.getNumRows()     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            if (r2 <= 0) goto L_0x0188
            java.lang.Object r2 = r11.get(r5)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            com.google.android.gms.internal.zzcuw r2 = (com.google.android.gms.internal.zzcuw) r2     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r4 = 1
            java.lang.String r4 = r3.getString(r4)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r2.zzfl(r4)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            java.lang.Object r2 = r11.get(r5)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            com.google.android.gms.internal.zzcuw r2 = (com.google.android.gms.internal.zzcuw) r2     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r4 = 2
            java.lang.String r4 = r3.getString(r4)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r2.zzfD(r4)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            java.lang.Object r2 = r11.get(r5)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            com.google.android.gms.internal.zzcuw r2 = (com.google.android.gms.internal.zzcuw) r2     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r4 = 4
            java.lang.String r4 = r3.getString(r4)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r2.zzfE(r4)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r2 = 0
            r4 = 3
            java.lang.String r4 = r3.getString(r4)     // Catch:{ JSONException -> 0x0147 }
            if (r4 == 0) goto L_0x017b
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0147 }
            r7.<init>(r4)     // Catch:{ JSONException -> 0x0147 }
            org.json.JSONArray r8 = r7.names()     // Catch:{ JSONException -> 0x0147 }
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ JSONException -> 0x0147 }
            r4.<init>()     // Catch:{ JSONException -> 0x0147 }
            r2 = 0
            r6 = r2
        L_0x0101:
            int r2 = r8.length()     // Catch:{ JSONException -> 0x0147 }
            if (r6 >= r2) goto L_0x017c
            java.lang.String r9 = r8.getString(r6)     // Catch:{ JSONException -> 0x0147 }
            java.lang.Object r2 = r7.opt(r9)     // Catch:{ JSONException -> 0x0147 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ JSONException -> 0x0147 }
            r4.put(r9, r2)     // Catch:{ JSONException -> 0x0147 }
            int r2 = r6 + 1
            r6 = r2
            goto L_0x0101
        L_0x0118:
            r2 = move-exception
            r3 = r13
            r11 = r12
        L_0x011b:
            java.lang.String r4 = "Error in peekHits fetching hitIds: "
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x013f }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x013f }
            int r5 = r2.length()     // Catch:{ all -> 0x013f }
            if (r5 == 0) goto L_0x0139
            java.lang.String r2 = r4.concat(r2)     // Catch:{ all -> 0x013f }
        L_0x012f:
            com.google.android.gms.internal.zzcvk.zzaT(r2)     // Catch:{ all -> 0x013f }
            if (r3 == 0) goto L_0x0010
            r3.close()
            goto L_0x0010
        L_0x0139:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x013f }
            r2.<init>(r4)     // Catch:{ all -> 0x013f }
            goto L_0x012f
        L_0x013f:
            r2 = move-exception
            r13 = r3
        L_0x0141:
            if (r13 == 0) goto L_0x0146
            r13.close()
        L_0x0146:
            throw r2
        L_0x0147:
            r2 = move-exception
            r4 = r2
            java.lang.String r6 = "Failed to read headers for hitId %d: %s"
            r2 = 2
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r8 = 0
            java.lang.Object r2 = r11.get(r5)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            com.google.android.gms.internal.zzcuw r2 = (com.google.android.gms.internal.zzcuw) r2     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            long r12 = r2.zzBm()     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            java.lang.Long r2 = java.lang.Long.valueOf(r12)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r7[r8] = r2     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r2 = 1
            java.lang.String r4 = r4.getMessage()     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r7[r2] = r4     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            java.lang.String r2 = java.lang.String.format(r6, r7)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            com.google.android.gms.internal.zzcvk.zzaT(r2)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r2 = r5
        L_0x016e:
            boolean r4 = r3.moveToNext()     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            if (r4 != 0) goto L_0x0210
        L_0x0174:
            if (r3 == 0) goto L_0x0010
            r3.close()
            goto L_0x0010
        L_0x017b:
            r4 = r2
        L_0x017c:
            java.lang.Object r2 = r11.get(r5)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            com.google.android.gms.internal.zzcuw r2 = (com.google.android.gms.internal.zzcuw) r2     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r2.zzu(r4)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
        L_0x0185:
            int r2 = r5 + 1
            goto L_0x016e
        L_0x0188:
            java.lang.String r4 = "HitString for hitId %d too large. Hit will be deleted."
            r2 = 1
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r7 = 0
            java.lang.Object r2 = r11.get(r5)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            com.google.android.gms.internal.zzcuw r2 = (com.google.android.gms.internal.zzcuw) r2     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            long r8 = r2.zzBm()     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            java.lang.Long r2 = java.lang.Long.valueOf(r8)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            r6[r7] = r2     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            java.lang.String r2 = java.lang.String.format(r4, r6)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            com.google.android.gms.internal.zzcvk.zzaT(r2)     // Catch:{ SQLiteException -> 0x01a6, all -> 0x0200 }
            goto L_0x0185
        L_0x01a6:
            r2 = move-exception
            r13 = r3
        L_0x01a8:
            java.lang.String r3 = "Error in peekHits fetching hit url: "
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x01ea }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x01ea }
            int r4 = r2.length()     // Catch:{ all -> 0x01ea }
            if (r4 == 0) goto L_0x01f2
            java.lang.String r2 = r3.concat(r2)     // Catch:{ all -> 0x01ea }
        L_0x01bc:
            com.google.android.gms.internal.zzcvk.zzaT(r2)     // Catch:{ all -> 0x01ea }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ all -> 0x01ea }
            r4.<init>()     // Catch:{ all -> 0x01ea }
            r5 = 0
            r0 = r11
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ all -> 0x01ea }
            r2 = r0
            int r7 = r2.size()     // Catch:{ all -> 0x01ea }
            r3 = 0
            r6 = r3
        L_0x01cf:
            if (r6 >= r7) goto L_0x01f8
            java.lang.Object r3 = r2.get(r6)     // Catch:{ all -> 0x01ea }
            int r6 = r6 + 1
            com.google.android.gms.internal.zzcuw r3 = (com.google.android.gms.internal.zzcuw) r3     // Catch:{ all -> 0x01ea }
            java.lang.String r8 = r3.zzBo()     // Catch:{ all -> 0x01ea }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x01ea }
            if (r8 == 0) goto L_0x01e6
            if (r5 != 0) goto L_0x01f8
            r5 = 1
        L_0x01e6:
            r4.add(r3)     // Catch:{ all -> 0x01ea }
            goto L_0x01cf
        L_0x01ea:
            r2 = move-exception
            r3 = r13
        L_0x01ec:
            if (r3 == 0) goto L_0x01f1
            r3.close()
        L_0x01f1:
            throw r2
        L_0x01f2:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x01ea }
            r2.<init>(r3)     // Catch:{ all -> 0x01ea }
            goto L_0x01bc
        L_0x01f8:
            if (r13 == 0) goto L_0x01fd
            r13.close()
        L_0x01fd:
            r11 = r4
            goto L_0x0010
        L_0x0200:
            r2 = move-exception
            goto L_0x01ec
        L_0x0202:
            r2 = move-exception
            goto L_0x01a8
        L_0x0204:
            r2 = move-exception
            goto L_0x0141
        L_0x0207:
            r2 = move-exception
            r3 = r13
            r11 = r12
            goto L_0x011b
        L_0x020c:
            r2 = move-exception
            r3 = r13
            goto L_0x011b
        L_0x0210:
            r5 = r2
            goto L_0x00b1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcvn.zzbE(int):java.util.List");
    }

    private final void zzd(String[] strArr) {
        SQLiteDatabase zzfg;
        boolean z = true;
        if (strArr != null && strArr.length != 0 && (zzfg = zzfg("Error opening database for deleteHits.")) != null) {
            try {
                zzfg.delete("gtm_hits", String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                zzcvc zzcvc = this.zzbIA;
                if (zzfF("gtm_hits") != 0) {
                    z = false;
                }
                zzcvc.zzar(z);
            } catch (SQLiteException e) {
                String valueOf = String.valueOf(e.getMessage());
                zzcvk.zzaT(valueOf.length() != 0 ? "Error deleting hits: ".concat(valueOf) : new String("Error deleting hits: "));
            }
        }
    }

    private final int zzfF(String str) {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase zzfg = zzfg("Error opening database for getNumRecords.");
        if (zzfg != null) {
            try {
                String valueOf = String.valueOf(str);
                Cursor rawQuery = zzfg.rawQuery(valueOf.length() != 0 ? "SELECT COUNT(*) from ".concat(valueOf) : new String("SELECT COUNT(*) from "), (String[]) null);
                if (rawQuery.moveToFirst()) {
                    i = (int) rawQuery.getLong(0);
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
            } catch (SQLiteException e) {
                String valueOf2 = String.valueOf(e.getMessage());
                zzcvk.zzaT(valueOf2.length() != 0 ? "Error getting numStoredRecords: ".concat(valueOf2) : new String("Error getting numStoredRecords: "));
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

    private final SQLiteDatabase zzfg(String str) {
        try {
            return this.zzbIy.getWritableDatabase();
        } catch (SQLiteException e) {
            Context context = this.mContext;
            zzcvk.zzc(str, e);
            if (zzg.zza(context, e)) {
                zzcvk.v("Crash reported successfully.");
            } else {
                zzcvk.v("Failed to report crash");
            }
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
                String valueOf = String.valueOf(e.getMessage());
                zzcvk.zzaT(new StringBuilder(String.valueOf(valueOf).length() + 70).append("Error setting HIT_FIRST_DISPATCH_TIME for hitId ").append(j).append(": ").append(valueOf).toString());
                zzp(j);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zzp(long j) {
        zzd(new String[]{String.valueOf(j)});
    }

    public final void dispatch() {
        zzcvk.v("GTM Dispatch running...");
        if (this.zzbIz.zzBf()) {
            List<zzcuw> zzbE = zzbE(40);
            if (zzbE.isEmpty()) {
                zzcvk.v("...nothing to dispatch");
                this.zzbIA.zzar(true);
                return;
            }
            this.zzbIz.zzK(zzbE);
            if (zzBB() > 0) {
                zzcwd.zzCA().dispatch();
            }
        }
    }

    public final void zza(long j, String str, @Nullable String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable String str4) {
        long currentTimeMillis = this.zzvw.currentTimeMillis();
        if (currentTimeMillis > this.zzbFy + TimeUtils.MILLIS_IN_DAY) {
            this.zzbFy = currentTimeMillis;
            SQLiteDatabase zzfg = zzfg("Error opening database for deleteStaleHits.");
            if (zzfg != null) {
                zzcvk.v(new StringBuilder(31).append("Removed ").append(zzfg.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.zzvw.currentTimeMillis() - 2592000000L)})).append(" stale hits.").toString());
                this.zzbIA.zzar(zzfF("gtm_hits") == 0);
            }
        }
        int zzfF = (zzfF("gtm_hits") - this.zzbFz) + 1;
        if (zzfF > 0) {
            List<String> zzbD = zzbD(zzfF);
            zzcvk.v(new StringBuilder(51).append("Store full, deleting ").append(zzbD.size()).append(" hits to make room.").toString());
            zzd((String[]) zzbD.toArray(new String[0]));
        }
        SQLiteDatabase zzfg2 = zzfg("Error opening database for putHit");
        if (zzfg2 != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_time", Long.valueOf(j));
            contentValues.put("hit_url", str);
            contentValues.put("hit_first_send_time", 0);
            if (str2 == null) {
                str2 = "GET";
            }
            contentValues.put("hit_method", str2);
            contentValues.put("hit_unique_id", str3);
            contentValues.put("hit_headers", map == null ? null : new JSONObject(map).toString());
            contentValues.put("hit_body", str4);
            try {
                zzfg2.insertOrThrow("gtm_hits", (String) null, contentValues);
                zzcvk.v(new StringBuilder(String.valueOf(str).length() + 19).append("Hit stored (url = ").append(str).append(h.b).toString());
                this.zzbIA.zzar(false);
            } catch (SQLiteConstraintException e) {
                String valueOf = String.valueOf(str);
                zzcvk.v(valueOf.length() != 0 ? "Hit has already been sent: ".concat(valueOf) : new String("Hit has already been sent: "));
            } catch (SQLiteException e2) {
                String valueOf2 = String.valueOf(e2.getMessage());
                zzcvk.zzaT(valueOf2.length() != 0 ? "Error storing hit: ".concat(valueOf2) : new String("Error storing hit: "));
            }
        }
        if (zzcvs.zzCw().isPreview()) {
            zzcvk.v("Sending hits immediately under preview.");
            dispatch();
        }
    }
}
