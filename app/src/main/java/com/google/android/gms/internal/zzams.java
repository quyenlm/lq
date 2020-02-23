package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzl;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzm;
import com.tencent.qqgamemi.util.TimeUtils;
import com.tencent.tp.a.h;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class zzams extends zzamh implements Closeable {
    /* access modifiers changed from: private */
    public static final String zzagp = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{"hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id"});
    private static final String zzagq = String.format("SELECT MAX(%s) FROM %s WHERE 1;", new Object[]{"hit_time", "hits2"});
    private final zzamt zzagr;
    private final zzaoo zzags = new zzaoo(zzkq());
    /* access modifiers changed from: private */
    public final zzaoo zzagt = new zzaoo(zzkq());

    zzams(zzamj zzamj) {
        super(zzamj);
        this.zzagr = new zzamt(this, zzamj.getContext(), "google_analytics_v4.db");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zza(java.lang.String r4, java.lang.String[] r5, long r6) {
        /*
            r3 = this;
            android.database.sqlite.SQLiteDatabase r0 = r3.getWritableDatabase()
            r1 = 0
            android.database.Cursor r2 = r0.rawQuery(r4, r5)     // Catch:{ SQLiteException -> 0x0022 }
            boolean r0 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x0033, all -> 0x0030 }
            if (r0 == 0) goto L_0x001a
            r0 = 0
            long r0 = r2.getLong(r0)     // Catch:{ SQLiteException -> 0x0033, all -> 0x0030 }
            if (r2 == 0) goto L_0x0019
            r2.close()
        L_0x0019:
            return r0
        L_0x001a:
            if (r2 == 0) goto L_0x001f
            r2.close()
        L_0x001f:
            r0 = 0
            goto L_0x0019
        L_0x0022:
            r0 = move-exception
        L_0x0023:
            java.lang.String r2 = "Database error"
            r3.zzd(r2, r4, r0)     // Catch:{ all -> 0x0029 }
            throw r0     // Catch:{ all -> 0x0029 }
        L_0x0029:
            r0 = move-exception
        L_0x002a:
            if (r1 == 0) goto L_0x002f
            r1.close()
        L_0x002f:
            throw r0
        L_0x0030:
            r0 = move-exception
            r1 = r2
            goto L_0x002a
        L_0x0033:
            r0 = move-exception
            r1 = r2
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzams.zza(java.lang.String, java.lang.String[], long):long");
    }

    private final long zzb(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery(str, (String[]) null);
            if (cursor.moveToFirst()) {
                long j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            }
            throw new SQLiteException("Database returned empty set");
        } catch (SQLiteException e) {
            zzd("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private final Map<String, String> zzbt(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith("?")) {
                String valueOf = String.valueOf(str);
                str = valueOf.length() != 0 ? "?".concat(valueOf) : new String("?");
            }
            return zzm.zza(new URI(str), "UTF-8");
        } catch (URISyntaxException e) {
            zze("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    private final Map<String, String> zzbu(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            String valueOf = String.valueOf(str);
            return zzm.zza(new URI(valueOf.length() != 0 ? "?".concat(valueOf) : new String("?")), "UTF-8");
        } catch (URISyntaxException e) {
            zze("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    private final long zzkN() {
        zzl.zzjC();
        zzkD();
        return zzb("SELECT COUNT(*) FROM hits2", (String[]) null);
    }

    /* access modifiers changed from: private */
    public static String zzkU() {
        return "google_analytics_v4.db";
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x006e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<java.lang.Long> zzn(long r14) {
        /*
            r13 = this;
            r10 = 0
            com.google.android.gms.analytics.zzl.zzjC()
            r13.zzkD()
            r0 = 0
            int r0 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x0012
            java.util.List r0 = java.util.Collections.emptyList()
        L_0x0011:
            return r0
        L_0x0012:
            android.database.sqlite.SQLiteDatabase r0 = r13.getWritableDatabase()
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.lang.String r1 = "hits2"
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x005e, all -> 0x006b }
            r3 = 0
            java.lang.String r4 = "hit_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x005e, all -> 0x006b }
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.String r7 = "%s ASC"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ SQLiteException -> 0x005e, all -> 0x006b }
            r11 = 0
            java.lang.String r12 = "hit_id"
            r8[r11] = r12     // Catch:{ SQLiteException -> 0x005e, all -> 0x006b }
            java.lang.String r7 = java.lang.String.format(r7, r8)     // Catch:{ SQLiteException -> 0x005e, all -> 0x006b }
            java.lang.String r8 = java.lang.Long.toString(r14)     // Catch:{ SQLiteException -> 0x005e, all -> 0x006b }
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x005e, all -> 0x006b }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x0075 }
            if (r0 == 0) goto L_0x0057
        L_0x0045:
            r0 = 0
            long r2 = r1.getLong(r0)     // Catch:{ SQLiteException -> 0x0075 }
            java.lang.Long r0 = java.lang.Long.valueOf(r2)     // Catch:{ SQLiteException -> 0x0075 }
            r9.add(r0)     // Catch:{ SQLiteException -> 0x0075 }
            boolean r0 = r1.moveToNext()     // Catch:{ SQLiteException -> 0x0075 }
            if (r0 != 0) goto L_0x0045
        L_0x0057:
            if (r1 == 0) goto L_0x005c
            r1.close()
        L_0x005c:
            r0 = r9
            goto L_0x0011
        L_0x005e:
            r0 = move-exception
            r1 = r10
        L_0x0060:
            java.lang.String r2 = "Error selecting hit ids"
            r13.zzd(r2, r0)     // Catch:{ all -> 0x0072 }
            if (r1 == 0) goto L_0x005c
            r1.close()
            goto L_0x005c
        L_0x006b:
            r0 = move-exception
        L_0x006c:
            if (r10 == 0) goto L_0x0071
            r10.close()
        L_0x0071:
            throw r0
        L_0x0072:
            r0 = move-exception
            r10 = r1
            goto L_0x006c
        L_0x0075:
            r0 = move-exception
            goto L_0x0060
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzams.zzn(long):java.util.List");
    }

    public final void beginTransaction() {
        zzkD();
        getWritableDatabase().beginTransaction();
    }

    public final void close() {
        try {
            this.zzagr.close();
        } catch (SQLiteException e) {
            zze("Sql error closing database", e);
        } catch (IllegalStateException e2) {
            zze("Error closing database", e2);
        }
    }

    public final void endTransaction() {
        zzkD();
        getWritableDatabase().endTransaction();
    }

    /* access modifiers changed from: package-private */
    public final SQLiteDatabase getWritableDatabase() {
        try {
            return this.zzagr.getWritableDatabase();
        } catch (SQLiteException e) {
            zzd("Error opening database", e);
            throw e;
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isEmpty() {
        return zzkN() == 0;
    }

    public final void setTransactionSuccessful() {
        zzkD();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(long j, String str, String str2) {
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        zzkD();
        zzl.zzjC();
        return zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(j), str, str2}, 0);
    }

    public final void zzc(zzanx zzanx) {
        zzbo.zzu(zzanx);
        zzl.zzjC();
        zzkD();
        zzbo.zzu(zzanx);
        Uri.Builder builder = new Uri.Builder();
        for (Map.Entry next : zzanx.zzdV().entrySet()) {
            String str = (String) next.getKey();
            if (!"ht".equals(str) && !"qt".equals(str) && !"AppUID".equals(str)) {
                builder.appendQueryParameter(str, (String) next.getValue());
            }
        }
        String encodedQuery = builder.build().getEncodedQuery();
        String str2 = encodedQuery == null ? "" : encodedQuery;
        if (str2.length() > 8192) {
            zzkr().zza(zzanx, "Hit length exceeds the maximum allowed size");
            return;
        }
        int intValue = zzans.zzahj.get().intValue();
        long zzkN = zzkN();
        if (zzkN > ((long) (intValue - 1))) {
            List<Long> zzn = zzn((zzkN - ((long) intValue)) + 1);
            zzd("Store full, deleting hits to make room, count", Integer.valueOf(zzn.size()));
            zzs(zzn);
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", str2);
        contentValues.put("hit_time", Long.valueOf(zzanx.zzlG()));
        contentValues.put("hit_app_id", Integer.valueOf(zzanx.zzlE()));
        contentValues.put("hit_url", zzanx.zzlI() ? zzank.zzlu() : zzank.zzlv());
        try {
            long insert = writableDatabase.insert("hits2", (String) null, contentValues);
            if (insert == -1) {
                zzbs("Failed to insert a hit (got -1)");
            } else {
                zzb("Hit saved to database. db-id, hit", Long.valueOf(insert), zzanx);
            }
        } catch (SQLiteException e) {
            zze("Error storing a hit", e);
        }
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }

    public final int zzkS() {
        zzl.zzjC();
        zzkD();
        if (!this.zzags.zzu(TimeUtils.MILLIS_IN_DAY)) {
            return 0;
        }
        this.zzags.start();
        zzbo("Deleting stale hits (if any)");
        int delete = getWritableDatabase().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzkq().currentTimeMillis() - 2592000000L)});
        zza("Deleted stale hits, count", Integer.valueOf(delete));
        return delete;
    }

    public final long zzkT() {
        zzl.zzjC();
        zzkD();
        return zza(zzagq, (String[]) null, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009e, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a4, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a5, code lost:
        r1 = r9;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a2 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0019] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.zzanx> zzo(long r14) {
        /*
            r13 = this;
            r0 = 1
            r1 = 0
            r9 = 0
            r2 = 0
            int r2 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r2 < 0) goto L_0x008f
        L_0x0009:
            com.google.android.gms.common.internal.zzbo.zzaf(r0)
            com.google.android.gms.analytics.zzl.zzjC()
            r13.zzkD()
            android.database.sqlite.SQLiteDatabase r0 = r13.getWritableDatabase()
            java.lang.String r1 = "hits2"
            r2 = 5
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            r3 = 0
            java.lang.String r4 = "hit_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            r3 = 1
            java.lang.String r4 = "hit_time"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            r3 = 2
            java.lang.String r4 = "hit_string"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            r3 = 3
            java.lang.String r4 = "hit_url"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            r3 = 4
            java.lang.String r4 = "hit_app_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.String r7 = "%s ASC"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            r10 = 0
            java.lang.String r11 = "hit_id"
            r8[r10] = r11     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            java.lang.String r7 = java.lang.String.format(r7, r8)     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            java.lang.String r8 = java.lang.Long.toString(r14)     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            android.database.Cursor r9 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x0092, all -> 0x00a2 }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            r10.<init>()     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            boolean r0 = r9.moveToFirst()     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            if (r0 == 0) goto L_0x0089
        L_0x0059:
            r0 = 0
            long r6 = r9.getLong(r0)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            r0 = 1
            long r3 = r9.getLong(r0)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            r0 = 2
            java.lang.String r0 = r9.getString(r0)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            r1 = 3
            java.lang.String r1 = r9.getString(r1)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            r2 = 4
            int r8 = r9.getInt(r2)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            java.util.Map r2 = r13.zzbt(r0)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            boolean r5 = com.google.android.gms.internal.zzaos.zzbF(r1)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            com.google.android.gms.internal.zzanx r0 = new com.google.android.gms.internal.zzanx     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            r1 = r13
            r0.<init>(r1, r2, r3, r5, r6, r8)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            r10.add(r0)     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            boolean r0 = r9.moveToNext()     // Catch:{ SQLiteException -> 0x00a4, all -> 0x00a2 }
            if (r0 != 0) goto L_0x0059
        L_0x0089:
            if (r9 == 0) goto L_0x008e
            r9.close()
        L_0x008e:
            return r10
        L_0x008f:
            r0 = r1
            goto L_0x0009
        L_0x0092:
            r0 = move-exception
            r1 = r9
        L_0x0094:
            java.lang.String r2 = "Error loading hits from the database"
            r13.zze(r2, r0)     // Catch:{ all -> 0x009a }
            throw r0     // Catch:{ all -> 0x009a }
        L_0x009a:
            r0 = move-exception
            r9 = r1
        L_0x009c:
            if (r9 == 0) goto L_0x00a1
            r9.close()
        L_0x00a1:
            throw r0
        L_0x00a2:
            r0 = move-exception
            goto L_0x009c
        L_0x00a4:
            r0 = move-exception
            r1 = r9
            goto L_0x0094
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzams.zzo(long):java.util.List");
    }

    public final void zzp(long j) {
        zzl.zzjC();
        zzkD();
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Long.valueOf(j));
        zza("Deleting hit, id", Long.valueOf(j));
        zzs(arrayList);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b7, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bb, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00bd, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00be, code lost:
        r1 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00bb A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x000c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.zzamm> zzq(long r13) {
        /*
            r12 = this;
            r12.zzkD()
            com.google.android.gms.analytics.zzl.zzjC()
            android.database.sqlite.SQLiteDatabase r0 = r12.getWritableDatabase()
            r9 = 0
            r1 = 5
            java.lang.String[] r2 = new java.lang.String[r1]     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            r1 = 0
            java.lang.String r3 = "cid"
            r2[r1] = r3     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            r1 = 1
            java.lang.String r3 = "tid"
            r2[r1] = r3     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            r1 = 2
            java.lang.String r3 = "adid"
            r2[r1] = r3     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            r1 = 3
            java.lang.String r3 = "hits_count"
            r2[r1] = r3     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            r1 = 4
            java.lang.String r3 = "params"
            r2[r1] = r3     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            com.google.android.gms.internal.zzant<java.lang.Integer> r1 = com.google.android.gms.internal.zzans.zzahl     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            java.lang.Object r1 = r1.get()     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            int r10 = r1.intValue()     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            java.lang.String r8 = java.lang.String.valueOf(r10)     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            java.lang.String r3 = "app_uid=?"
            r1 = 1
            java.lang.String[] r4 = new java.lang.String[r1]     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            r1 = 0
            java.lang.String r5 = "0"
            r4[r1] = r5     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            java.lang.String r1 = "properties"
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r9 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00bb }
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            r11.<init>()     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            boolean r0 = r9.moveToFirst()     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            if (r0 == 0) goto L_0x008d
        L_0x0055:
            r0 = 0
            java.lang.String r3 = r9.getString(r0)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            r0 = 1
            java.lang.String r4 = r9.getString(r0)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            r0 = 2
            int r0 = r9.getInt(r0)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            if (r0 == 0) goto L_0x009e
            r5 = 1
        L_0x0067:
            r0 = 3
            int r0 = r9.getInt(r0)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            long r6 = (long) r0     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            r0 = 4
            java.lang.String r0 = r9.getString(r0)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            java.util.Map r8 = r12.zzbu(r0)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            if (r0 != 0) goto L_0x0082
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            if (r0 == 0) goto L_0x00a0
        L_0x0082:
            java.lang.String r0 = "Read property with empty client id or tracker id"
            r12.zzc(r0, r3, r4)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
        L_0x0087:
            boolean r0 = r9.moveToNext()     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            if (r0 != 0) goto L_0x0055
        L_0x008d:
            int r0 = r11.size()     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            if (r0 < r10) goto L_0x0098
            java.lang.String r0 = "Sending hits to too many properties. Campaign report might be incorrect"
            r12.zzbr(r0)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
        L_0x0098:
            if (r9 == 0) goto L_0x009d
            r9.close()
        L_0x009d:
            return r11
        L_0x009e:
            r5 = 0
            goto L_0x0067
        L_0x00a0:
            com.google.android.gms.internal.zzamm r0 = new com.google.android.gms.internal.zzamm     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            r1 = 0
            r0.<init>(r1, r3, r4, r5, r6, r8)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            r11.add(r0)     // Catch:{ SQLiteException -> 0x00ab, all -> 0x00bb }
            goto L_0x0087
        L_0x00ab:
            r0 = move-exception
            r1 = r9
        L_0x00ad:
            java.lang.String r2 = "Error loading hits from the database"
            r12.zze(r2, r0)     // Catch:{ all -> 0x00b3 }
            throw r0     // Catch:{ all -> 0x00b3 }
        L_0x00b3:
            r0 = move-exception
            r9 = r1
        L_0x00b5:
            if (r9 == 0) goto L_0x00ba
            r9.close()
        L_0x00ba:
            throw r0
        L_0x00bb:
            r0 = move-exception
            goto L_0x00b5
        L_0x00bd:
            r0 = move-exception
            r1 = r9
            goto L_0x00ad
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzams.zzq(long):java.util.List");
    }

    public final void zzs(List<Long> list) {
        zzbo.zzu(list);
        zzl.zzjC();
        zzkD();
        if (!list.isEmpty()) {
            StringBuilder sb = new StringBuilder("hit_id");
            sb.append(" in (");
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < list.size()) {
                    Long l = list.get(i2);
                    if (l != null && l.longValue() != 0) {
                        if (i2 > 0) {
                            sb.append(",");
                        }
                        sb.append(l);
                        i = i2 + 1;
                    }
                } else {
                    sb.append(h.b);
                    String sb2 = sb.toString();
                    try {
                        SQLiteDatabase writableDatabase = getWritableDatabase();
                        zza("Deleting dispatched hits. count", Integer.valueOf(list.size()));
                        int delete = writableDatabase.delete("hits2", sb2, (String[]) null);
                        if (delete != list.size()) {
                            zzb("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(delete), sb2);
                            return;
                        }
                        return;
                    } catch (SQLiteException e) {
                        zze("Error deleting hits", e);
                        throw e;
                    }
                }
            }
            throw new SQLiteException("Invalid hit id");
        }
    }
}
