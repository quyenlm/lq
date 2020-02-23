package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcelable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import bolts.MeasurementEvent;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.tp.a.h;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzcen extends zzchj {
    /* access modifiers changed from: private */
    public static final Map<String, String> zzbpn;
    /* access modifiers changed from: private */
    public static final Map<String, String> zzbpo;
    /* access modifiers changed from: private */
    public static final Map<String, String> zzbpp;
    /* access modifiers changed from: private */
    public static final Map<String, String> zzbpq;
    /* access modifiers changed from: private */
    public static final Map<String, String> zzbpr;
    private final zzceq zzbps = new zzceq(this, getContext(), zzcem.zzxC());
    /* access modifiers changed from: private */
    public final zzcjf zzbpt = new zzcjf(zzkq());

    static {
        ArrayMap arrayMap = new ArrayMap(1);
        zzbpn = arrayMap;
        arrayMap.put(FirebaseAnalytics.Param.ORIGIN, "ALTER TABLE user_attributes ADD COLUMN origin TEXT;");
        ArrayMap arrayMap2 = new ArrayMap(18);
        zzbpo = arrayMap2;
        arrayMap2.put("app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;");
        zzbpo.put("app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;");
        zzbpo.put("gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;");
        zzbpo.put("dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;");
        zzbpo.put("measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;");
        zzbpo.put("last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;");
        zzbpo.put("day", "ALTER TABLE apps ADD COLUMN day INTEGER;");
        zzbpo.put("daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;");
        zzbpo.put("daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;");
        zzbpo.put("daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;");
        zzbpo.put("remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;");
        zzbpo.put("config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;");
        zzbpo.put("failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;");
        zzbpo.put("app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;");
        zzbpo.put("firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;");
        zzbpo.put("daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;");
        zzbpo.put("daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;");
        zzbpo.put("health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;");
        zzbpo.put("android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;");
        ArrayMap arrayMap3 = new ArrayMap(1);
        zzbpp = arrayMap3;
        arrayMap3.put("realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;");
        ArrayMap arrayMap4 = new ArrayMap(1);
        zzbpq = arrayMap4;
        arrayMap4.put("has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;");
        ArrayMap arrayMap5 = new ArrayMap(1);
        zzbpr = arrayMap5;
        arrayMap5.put("previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;");
    }

    zzcen(zzcgl zzcgl) {
        super(zzcgl);
    }

    @WorkerThread
    private final long zza(String str, String[] strArr, long j) {
        Cursor cursor = null;
        try {
            Cursor rawQuery = getWritableDatabase().rawQuery(str, strArr);
            if (rawQuery.moveToFirst()) {
                j = rawQuery.getLong(0);
                if (rawQuery != null) {
                    rawQuery.close();
                }
            } else if (rawQuery != null) {
                rawQuery.close();
            }
            return j;
        } catch (SQLiteException e) {
            zzwF().zzyx().zze("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        switch (type) {
            case 0:
                zzwF().zzyx().log("Loaded invalid null value from database");
                return null;
            case 1:
                return Long.valueOf(cursor.getLong(i));
            case 2:
                return Double.valueOf(cursor.getDouble(i));
            case 3:
                return cursor.getString(i);
            case 4:
                zzwF().zzyx().log("Loaded invalid blob type value, ignoring it");
                return null;
            default:
                zzwF().zzyx().zzj("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
        }
    }

    @WorkerThread
    private static void zza(ContentValues contentValues, String str, Object obj) {
        zzbo.zzcF(str);
        zzbo.zzu(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put(str, (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    static void zza(zzcfl zzcfl, SQLiteDatabase sQLiteDatabase) {
        if (zzcfl == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzcfl.zzyz().log("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzcfl.zzyz().log("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzcfl.zzyz().log("Failed to turn on database read permission for owner");
        }
        if (!file.setWritable(true, true)) {
            zzcfl.zzyz().log("Failed to turn on database write permission for owner");
        }
    }

    @WorkerThread
    static void zza(zzcfl zzcfl, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, Map<String, String> map) throws SQLiteException {
        if (zzcfl == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzcfl, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        try {
            zza(zzcfl, sQLiteDatabase, str, str3, map);
        } catch (SQLiteException e) {
            zzcfl.zzyx().zzj("Failed to verify columns on table that was just created", str);
            throw e;
        }
    }

    @WorkerThread
    private static void zza(zzcfl zzcfl, SQLiteDatabase sQLiteDatabase, String str, String str2, Map<String, String> map) throws SQLiteException {
        if (zzcfl == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Set<String> zzb = zzb(sQLiteDatabase, str);
        for (String str3 : str2.split(",")) {
            if (!zzb.remove(str3)) {
                throw new SQLiteException(new StringBuilder(String.valueOf(str).length() + 35 + String.valueOf(str3).length()).append("Table ").append(str).append(" is missing required column: ").append(str3).toString());
            }
        }
        if (map != null) {
            for (Map.Entry next : map.entrySet()) {
                if (!zzb.remove(next.getKey())) {
                    sQLiteDatabase.execSQL((String) next.getValue());
                }
            }
        }
        if (!zzb.isEmpty()) {
            zzcfl.zzyz().zze("Table has extra columns. table, columns", str, TextUtils.join(", ", zzb));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean zza(com.google.android.gms.internal.zzcfl r10, android.database.sqlite.SQLiteDatabase r11, java.lang.String r12) {
        /*
            r8 = 0
            r9 = 0
            if (r10 != 0) goto L_0x000c
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Monitor must not be null"
            r0.<init>(r1)
            throw r0
        L_0x000c:
            java.lang.String r1 = "SQLITE_MASTER"
            r0 = 1
            java.lang.String[] r2 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0030, all -> 0x0042 }
            r0 = 0
            java.lang.String r3 = "name"
            r2[r0] = r3     // Catch:{ SQLiteException -> 0x0030, all -> 0x0042 }
            java.lang.String r3 = "name=?"
            r0 = 1
            java.lang.String[] r4 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0030, all -> 0x0042 }
            r0 = 0
            r4[r0] = r12     // Catch:{ SQLiteException -> 0x0030, all -> 0x0042 }
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r11
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x0030, all -> 0x0042 }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x004c }
            if (r1 == 0) goto L_0x002f
            r1.close()
        L_0x002f:
            return r0
        L_0x0030:
            r0 = move-exception
            r1 = r9
        L_0x0032:
            com.google.android.gms.internal.zzcfn r2 = r10.zzyz()     // Catch:{ all -> 0x0049 }
            java.lang.String r3 = "Error querying for table"
            r2.zze(r3, r12, r0)     // Catch:{ all -> 0x0049 }
            if (r1 == 0) goto L_0x0040
            r1.close()
        L_0x0040:
            r0 = r8
            goto L_0x002f
        L_0x0042:
            r0 = move-exception
        L_0x0043:
            if (r9 == 0) goto L_0x0048
            r9.close()
        L_0x0048:
            throw r0
        L_0x0049:
            r0 = move-exception
            r9 = r1
            goto L_0x0043
        L_0x004c:
            r0 = move-exception
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zza(com.google.android.gms.internal.zzcfl, android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzcjn zzcjn) {
        zzkD();
        zzjC();
        zzbo.zzcF(str);
        zzbo.zzu(zzcjn);
        if (TextUtils.isEmpty(zzcjn.zzbuN)) {
            zzwF().zzyz().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzcfl.zzdZ(str), Integer.valueOf(i), String.valueOf(zzcjn.zzbuM));
            return false;
        }
        try {
            byte[] bArr = new byte[zzcjn.zzLV()];
            adh zzc = adh.zzc(bArr, 0, bArr.length);
            zzcjn.zza(zzc);
            zzc.zzLM();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzcjn.zzbuM);
            contentValues.put(MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY, zzcjn.zzbuN);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("event_filters", (String) null, contentValues, 5) == -1) {
                    zzwF().zzyx().zzj("Failed to insert event filter (got -1). appId", zzcfl.zzdZ(str));
                }
                return true;
            } catch (SQLiteException e) {
                zzwF().zzyx().zze("Error storing event filter. appId", zzcfl.zzdZ(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzwF().zzyx().zze("Configuration loss. Failed to serialize event filter. appId", zzcfl.zzdZ(str), e2);
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzcjq zzcjq) {
        zzkD();
        zzjC();
        zzbo.zzcF(str);
        zzbo.zzu(zzcjq);
        if (TextUtils.isEmpty(zzcjq.zzbvc)) {
            zzwF().zzyz().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzcfl.zzdZ(str), Integer.valueOf(i), String.valueOf(zzcjq.zzbuM));
            return false;
        }
        try {
            byte[] bArr = new byte[zzcjq.zzLV()];
            adh zzc = adh.zzc(bArr, 0, bArr.length);
            zzcjq.zza(zzc);
            zzc.zzLM();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzcjq.zzbuM);
            contentValues.put("property_name", zzcjq.zzbvc);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("property_filters", (String) null, contentValues, 5) != -1) {
                    return true;
                }
                zzwF().zzyx().zzj("Failed to insert property filter (got -1). appId", zzcfl.zzdZ(str));
                return false;
            } catch (SQLiteException e) {
                zzwF().zzyx().zze("Error storing property filter. appId", zzcfl.zzdZ(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzwF().zzyx().zze("Configuration loss. Failed to serialize property filter. appId", zzcfl.zzdZ(str), e2);
            return false;
        }
    }

    @WorkerThread
    private final long zzb(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery(str, strArr);
            if (cursor.moveToFirst()) {
                long j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            }
            throw new SQLiteException("Database returned empty set");
        } catch (SQLiteException e) {
            zzwF().zzyx().zze("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        HashSet hashSet = new HashSet();
        Cursor rawQuery = sQLiteDatabase.rawQuery(new StringBuilder(String.valueOf(str).length() + 22).append("SELECT * FROM ").append(str).append(" LIMIT 0").toString(), (String[]) null);
        try {
            Collections.addAll(hashSet, rawQuery.getColumnNames());
            return hashSet;
        } finally {
            rawQuery.close();
        }
    }

    private final boolean zzc(String str, List<Integer> list) {
        zzbo.zzcF(str);
        zzkD();
        zzjC();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            long zzb = zzb("select count(1) from audience_filter_values where app_id=?", new String[]{str});
            int max = Math.max(0, Math.min(2000, zzwH().zzb(str, zzcfb.zzbqA)));
            if (zzb <= ((long) max)) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Integer num = list.get(i);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String valueOf = String.valueOf(TextUtils.join(",", arrayList));
            String sb = new StringBuilder(String.valueOf(valueOf).length() + 2).append(h.a).append(valueOf).append(h.b).toString();
            return writableDatabase.delete("audience_filter_values", new StringBuilder(String.valueOf(sb).length() + 140).append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ").append(sb).append(" order by rowid desc limit -1 offset ?)").toString(), new String[]{str, Integer.toString(max)}) > 0;
        } catch (SQLiteException e) {
            zzwF().zzyx().zze("Database error querying filters. appId", zzcfl.zzdZ(str), e);
            return false;
        }
    }

    private final boolean zzyk() {
        return getContext().getDatabasePath(zzcem.zzxC()).exists();
    }

    @WorkerThread
    public final void beginTransaction() {
        zzkD();
        getWritableDatabase().beginTransaction();
    }

    @WorkerThread
    public final void endTransaction() {
        zzkD();
        getWritableDatabase().endTransaction();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final SQLiteDatabase getWritableDatabase() {
        zzjC();
        try {
            return this.zzbps.getWritableDatabase();
        } catch (SQLiteException e) {
            zzwF().zzyz().zzj("Error opening database", e);
            throw e;
        }
    }

    @WorkerThread
    public final void setTransactionSuccessful() {
        zzkD();
        getWritableDatabase().setTransactionSuccessful();
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x009c  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzcev zzE(java.lang.String r13, java.lang.String r14) {
        /*
            r12 = this;
            r10 = 0
            com.google.android.gms.common.internal.zzbo.zzcF(r13)
            com.google.android.gms.common.internal.zzbo.zzcF(r14)
            r12.zzjC()
            r12.zzkD()
            android.database.sqlite.SQLiteDatabase r0 = r12.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            java.lang.String r1 = "events"
            r2 = 3
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r3 = 0
            java.lang.String r4 = "lifetime_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r3 = 1
            java.lang.String r4 = "current_bundle_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r3 = 2
            java.lang.String r4 = "last_fire_timestamp"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            java.lang.String r3 = "app_id=? and name=?"
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r5 = 0
            r4[r5] = r13     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r5 = 1
            r4[r5] = r14     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r11 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            boolean r0 = r11.moveToFirst()     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            if (r0 != 0) goto L_0x0044
            if (r11 == 0) goto L_0x0042
            r11.close()
        L_0x0042:
            r1 = r10
        L_0x0043:
            return r1
        L_0x0044:
            r0 = 0
            long r4 = r11.getLong(r0)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            r0 = 1
            long r6 = r11.getLong(r0)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            r0 = 2
            long r8 = r11.getLong(r0)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            com.google.android.gms.internal.zzcev r1 = new com.google.android.gms.internal.zzcev     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            r2 = r13
            r3 = r14
            r1.<init>(r2, r3, r4, r6, r8)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            boolean r0 = r11.moveToNext()     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            if (r0 == 0) goto L_0x0071
            com.google.android.gms.internal.zzcfl r0 = r12.zzwF()     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            com.google.android.gms.internal.zzcfn r0 = r0.zzyx()     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            java.lang.String r2 = "Got multiple records for event aggregates, expected one. appId"
            java.lang.Object r3 = com.google.android.gms.internal.zzcfl.zzdZ(r13)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            r0.zzj(r2, r3)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
        L_0x0071:
            if (r11 == 0) goto L_0x0043
            r11.close()
            goto L_0x0043
        L_0x0077:
            r0 = move-exception
            r1 = r10
        L_0x0079:
            com.google.android.gms.internal.zzcfl r2 = r12.zzwF()     // Catch:{ all -> 0x00a3 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x00a3 }
            java.lang.String r3 = "Error querying events. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r13)     // Catch:{ all -> 0x00a3 }
            com.google.android.gms.internal.zzcfj r5 = r12.zzwA()     // Catch:{ all -> 0x00a3 }
            java.lang.String r5 = r5.zzdW(r14)     // Catch:{ all -> 0x00a3 }
            r2.zzd(r3, r4, r5, r0)     // Catch:{ all -> 0x00a3 }
            if (r1 == 0) goto L_0x0097
            r1.close()
        L_0x0097:
            r1 = r10
            goto L_0x0043
        L_0x0099:
            r0 = move-exception
        L_0x009a:
            if (r10 == 0) goto L_0x009f
            r10.close()
        L_0x009f:
            throw r0
        L_0x00a0:
            r0 = move-exception
            r10 = r11
            goto L_0x009a
        L_0x00a3:
            r0 = move-exception
            r10 = r1
            goto L_0x009a
        L_0x00a6:
            r0 = move-exception
            r1 = r11
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzE(java.lang.String, java.lang.String):com.google.android.gms.internal.zzcev");
    }

    @WorkerThread
    public final void zzF(String str, String str2) {
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        zzjC();
        zzkD();
        try {
            zzwF().zzyD().zzj("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzwF().zzyx().zzd("Error deleting user attribute. appId", zzcfl.zzdZ(str), zzwA().zzdY(str2), e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x009c  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzcjk zzG(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            r8 = 0
            com.google.android.gms.common.internal.zzbo.zzcF(r10)
            com.google.android.gms.common.internal.zzbo.zzcF(r11)
            r9.zzjC()
            r9.zzkD()
            android.database.sqlite.SQLiteDatabase r0 = r9.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            java.lang.String r1 = "user_attributes"
            r2 = 3
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r3 = 0
            java.lang.String r4 = "set_timestamp"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r3 = 1
            java.lang.String r4 = "value"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r3 = 2
            java.lang.String r4 = "origin"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            java.lang.String r3 = "app_id=? and name=?"
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r5 = 0
            r4[r5] = r10     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r5 = 1
            r4[r5] = r11     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r7 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x0077, all -> 0x0099 }
            boolean r0 = r7.moveToFirst()     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            if (r0 != 0) goto L_0x0044
            if (r7 == 0) goto L_0x0042
            r7.close()
        L_0x0042:
            r0 = r8
        L_0x0043:
            return r0
        L_0x0044:
            r0 = 0
            long r4 = r7.getLong(r0)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            r0 = 1
            java.lang.Object r6 = r9.zza((android.database.Cursor) r7, (int) r0)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            r0 = 2
            java.lang.String r2 = r7.getString(r0)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            com.google.android.gms.internal.zzcjk r0 = new com.google.android.gms.internal.zzcjk     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            r1 = r10
            r3 = r11
            r0.<init>(r1, r2, r3, r4, r6)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            boolean r1 = r7.moveToNext()     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            if (r1 == 0) goto L_0x0071
            com.google.android.gms.internal.zzcfl r1 = r9.zzwF()     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            com.google.android.gms.internal.zzcfn r1 = r1.zzyx()     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            java.lang.String r2 = "Got multiple records for user property, expected one. appId"
            java.lang.Object r3 = com.google.android.gms.internal.zzcfl.zzdZ(r10)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
            r1.zzj(r2, r3)     // Catch:{ SQLiteException -> 0x00a6, all -> 0x00a0 }
        L_0x0071:
            if (r7 == 0) goto L_0x0043
            r7.close()
            goto L_0x0043
        L_0x0077:
            r0 = move-exception
            r1 = r8
        L_0x0079:
            com.google.android.gms.internal.zzcfl r2 = r9.zzwF()     // Catch:{ all -> 0x00a3 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x00a3 }
            java.lang.String r3 = "Error querying user property. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r10)     // Catch:{ all -> 0x00a3 }
            com.google.android.gms.internal.zzcfj r5 = r9.zzwA()     // Catch:{ all -> 0x00a3 }
            java.lang.String r5 = r5.zzdY(r11)     // Catch:{ all -> 0x00a3 }
            r2.zzd(r3, r4, r5, r0)     // Catch:{ all -> 0x00a3 }
            if (r1 == 0) goto L_0x0097
            r1.close()
        L_0x0097:
            r0 = r8
            goto L_0x0043
        L_0x0099:
            r0 = move-exception
        L_0x009a:
            if (r8 == 0) goto L_0x009f
            r8.close()
        L_0x009f:
            throw r0
        L_0x00a0:
            r0 = move-exception
            r8 = r7
            goto L_0x009a
        L_0x00a3:
            r0 = move-exception
            r8 = r1
            goto L_0x009a
        L_0x00a6:
            r0 = move-exception
            r1 = r7
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzG(java.lang.String, java.lang.String):com.google.android.gms.internal.zzcjk");
    }

    public final void zzG(List<Long> list) {
        zzbo.zzu(list);
        zzjC();
        zzkD();
        StringBuilder sb = new StringBuilder("rowid in (");
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                break;
            }
            if (i2 != 0) {
                sb.append(",");
            }
            sb.append(list.get(i2).longValue());
            i = i2 + 1;
        }
        sb.append(h.b);
        int delete = getWritableDatabase().delete("raw_events", sb.toString(), (String[]) null);
        if (delete != list.size()) {
            zzwF().zzyx().zze("Deleted fewer rows from raw events table than expected", Integer.valueOf(delete), Integer.valueOf(list.size()));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x014d  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzcek zzH(java.lang.String r22, java.lang.String r23) {
        /*
            r21 = this;
            com.google.android.gms.common.internal.zzbo.zzcF(r22)
            com.google.android.gms.common.internal.zzbo.zzcF(r23)
            r21.zzjC()
            r21.zzkD()
            r10 = 0
            android.database.sqlite.SQLiteDatabase r2 = r21.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            java.lang.String r3 = "conditional_properties"
            r4 = 11
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 0
            java.lang.String r6 = "origin"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 1
            java.lang.String r6 = "value"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 2
            java.lang.String r6 = "active"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 3
            java.lang.String r6 = "trigger_event_name"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 4
            java.lang.String r6 = "trigger_timeout"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 5
            java.lang.String r6 = "timed_out_event"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 6
            java.lang.String r6 = "creation_timestamp"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 7
            java.lang.String r6 = "triggered_event"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 8
            java.lang.String r6 = "triggered_timestamp"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 9
            java.lang.String r6 = "time_to_live"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r5 = 10
            java.lang.String r6 = "expired_event"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            java.lang.String r5 = "app_id=? and name=?"
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r7 = 0
            r6[r7] = r22     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r7 = 1
            r6[r7] = r23     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            r7 = 0
            r8 = 0
            r9 = 0
            android.database.Cursor r20 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x0123, all -> 0x0148 }
            boolean r2 = r20.moveToFirst()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            if (r2 != 0) goto L_0x0070
            if (r20 == 0) goto L_0x006e
            r20.close()
        L_0x006e:
            r5 = 0
        L_0x006f:
            return r5
        L_0x0070:
            r2 = 0
            r0 = r20
            java.lang.String r7 = r0.getString(r2)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r2 = 1
            r0 = r21
            r1 = r20
            java.lang.Object r6 = r0.zza((android.database.Cursor) r1, (int) r2)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r2 = 2
            r0 = r20
            int r2 = r0.getInt(r2)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            if (r2 == 0) goto L_0x0120
            r11 = 1
        L_0x008a:
            r2 = 3
            r0 = r20
            java.lang.String r12 = r0.getString(r2)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r2 = 4
            r0 = r20
            long r14 = r0.getLong(r2)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcjl r2 = r21.zzwB()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r3 = 5
            r0 = r20
            byte[] r3 = r0.getBlob(r3)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            android.os.Parcelable$Creator<com.google.android.gms.internal.zzcez> r4 = com.google.android.gms.internal.zzcez.CREATOR     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            android.os.Parcelable r13 = r2.zzb((byte[]) r3, r4)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcez r13 = (com.google.android.gms.internal.zzcez) r13     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r2 = 6
            r0 = r20
            long r9 = r0.getLong(r2)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcjl r2 = r21.zzwB()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r3 = 7
            r0 = r20
            byte[] r3 = r0.getBlob(r3)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            android.os.Parcelable$Creator<com.google.android.gms.internal.zzcez> r4 = com.google.android.gms.internal.zzcez.CREATOR     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            android.os.Parcelable r16 = r2.zzb((byte[]) r3, r4)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcez r16 = (com.google.android.gms.internal.zzcez) r16     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r2 = 8
            r0 = r20
            long r4 = r0.getLong(r2)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r2 = 9
            r0 = r20
            long r17 = r0.getLong(r2)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcjl r2 = r21.zzwB()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r3 = 10
            r0 = r20
            byte[] r3 = r0.getBlob(r3)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            android.os.Parcelable$Creator<com.google.android.gms.internal.zzcez> r8 = com.google.android.gms.internal.zzcez.CREATOR     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            android.os.Parcelable r19 = r2.zzb((byte[]) r3, r8)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcez r19 = (com.google.android.gms.internal.zzcez) r19     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcji r2 = new com.google.android.gms.internal.zzcji     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r3 = r23
            r2.<init>(r3, r4, r6, r7)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcek r5 = new com.google.android.gms.internal.zzcek     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r6 = r22
            r8 = r2
            r5.<init>(r6, r7, r8, r9, r11, r12, r13, r14, r16, r17, r19)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            boolean r2 = r20.moveToNext()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            if (r2 == 0) goto L_0x0119
            com.google.android.gms.internal.zzcfl r2 = r21.zzwF()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            java.lang.String r3 = "Got multiple records for conditional property, expected one"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r22)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            com.google.android.gms.internal.zzcfj r6 = r21.zzwA()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r0 = r23
            java.lang.String r6 = r6.zzdY(r0)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
            r2.zze(r3, r4, r6)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0151 }
        L_0x0119:
            if (r20 == 0) goto L_0x006f
            r20.close()
            goto L_0x006f
        L_0x0120:
            r11 = 0
            goto L_0x008a
        L_0x0123:
            r2 = move-exception
            r3 = r10
        L_0x0125:
            com.google.android.gms.internal.zzcfl r4 = r21.zzwF()     // Catch:{ all -> 0x0153 }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ all -> 0x0153 }
            java.lang.String r5 = "Error querying conditional property"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r22)     // Catch:{ all -> 0x0153 }
            com.google.android.gms.internal.zzcfj r7 = r21.zzwA()     // Catch:{ all -> 0x0153 }
            r0 = r23
            java.lang.String r7 = r7.zzdY(r0)     // Catch:{ all -> 0x0153 }
            r4.zzd(r5, r6, r7, r2)     // Catch:{ all -> 0x0153 }
            if (r3 == 0) goto L_0x0145
            r3.close()
        L_0x0145:
            r5 = 0
            goto L_0x006f
        L_0x0148:
            r2 = move-exception
            r20 = r10
        L_0x014b:
            if (r20 == 0) goto L_0x0150
            r20.close()
        L_0x0150:
            throw r2
        L_0x0151:
            r2 = move-exception
            goto L_0x014b
        L_0x0153:
            r2 = move-exception
            r20 = r3
            goto L_0x014b
        L_0x0157:
            r2 = move-exception
            r3 = r20
            goto L_0x0125
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzH(java.lang.String, java.lang.String):com.google.android.gms.internal.zzcek");
    }

    @WorkerThread
    public final int zzI(String str, String str2) {
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        zzjC();
        zzkD();
        try {
            return getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzwF().zzyx().zzd("Error deleting conditional property", zzcfl.zzdZ(str), zzwA().zzdY(str2), e);
            return 0;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.zzcjn>> zzJ(java.lang.String r11, java.lang.String r12) {
        /*
            r10 = this;
            r9 = 0
            r10.zzkD()
            r10.zzjC()
            com.google.android.gms.common.internal.zzbo.zzcF(r11)
            com.google.android.gms.common.internal.zzbo.zzcF(r12)
            android.support.v4.util.ArrayMap r8 = new android.support.v4.util.ArrayMap
            r8.<init>()
            android.database.sqlite.SQLiteDatabase r0 = r10.getWritableDatabase()
            java.lang.String r1 = "event_filters"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r3 = 0
            java.lang.String r4 = "audience_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r3 = 1
            java.lang.String r4 = "data"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            java.lang.String r3 = "app_id=? AND event_name=?"
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r5 = 0
            r4[r5] = r11     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r5 = 1
            r4[r5] = r12     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x009a }
            if (r0 != 0) goto L_0x0047
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x009a }
            if (r1 == 0) goto L_0x0046
            r1.close()
        L_0x0046:
            return r0
        L_0x0047:
            r0 = 1
            byte[] r0 = r1.getBlob(r0)     // Catch:{ SQLiteException -> 0x009a }
            r2 = 0
            int r3 = r0.length     // Catch:{ SQLiteException -> 0x009a }
            com.google.android.gms.internal.adg r0 = com.google.android.gms.internal.adg.zzb(r0, r2, r3)     // Catch:{ SQLiteException -> 0x009a }
            com.google.android.gms.internal.zzcjn r2 = new com.google.android.gms.internal.zzcjn     // Catch:{ SQLiteException -> 0x009a }
            r2.<init>()     // Catch:{ SQLiteException -> 0x009a }
            r2.zza((com.google.android.gms.internal.adg) r0)     // Catch:{ IOException -> 0x0087 }
            r0 = 0
            int r3 = r1.getInt(r0)     // Catch:{ SQLiteException -> 0x009a }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)     // Catch:{ SQLiteException -> 0x009a }
            java.lang.Object r0 = r8.get(r0)     // Catch:{ SQLiteException -> 0x009a }
            java.util.List r0 = (java.util.List) r0     // Catch:{ SQLiteException -> 0x009a }
            if (r0 != 0) goto L_0x0077
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x009a }
            r0.<init>()     // Catch:{ SQLiteException -> 0x009a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ SQLiteException -> 0x009a }
            r8.put(r3, r0)     // Catch:{ SQLiteException -> 0x009a }
        L_0x0077:
            r0.add(r2)     // Catch:{ SQLiteException -> 0x009a }
        L_0x007a:
            boolean r0 = r1.moveToNext()     // Catch:{ SQLiteException -> 0x009a }
            if (r0 != 0) goto L_0x0047
            if (r1 == 0) goto L_0x0085
            r1.close()
        L_0x0085:
            r0 = r8
            goto L_0x0046
        L_0x0087:
            r0 = move-exception
            com.google.android.gms.internal.zzcfl r2 = r10.zzwF()     // Catch:{ SQLiteException -> 0x009a }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ SQLiteException -> 0x009a }
            java.lang.String r3 = "Failed to merge filter. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r11)     // Catch:{ SQLiteException -> 0x009a }
            r2.zze(r3, r4, r0)     // Catch:{ SQLiteException -> 0x009a }
            goto L_0x007a
        L_0x009a:
            r0 = move-exception
        L_0x009b:
            com.google.android.gms.internal.zzcfl r2 = r10.zzwF()     // Catch:{ all -> 0x00bb }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x00bb }
            java.lang.String r3 = "Database error querying filters. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r11)     // Catch:{ all -> 0x00bb }
            r2.zze(r3, r4, r0)     // Catch:{ all -> 0x00bb }
            if (r1 == 0) goto L_0x00b1
            r1.close()
        L_0x00b1:
            r0 = r9
            goto L_0x0046
        L_0x00b3:
            r0 = move-exception
            r1 = r9
        L_0x00b5:
            if (r1 == 0) goto L_0x00ba
            r1.close()
        L_0x00ba:
            throw r0
        L_0x00bb:
            r0 = move-exception
            goto L_0x00b5
        L_0x00bd:
            r0 = move-exception
            r1 = r9
            goto L_0x009b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzJ(java.lang.String, java.lang.String):java.util.Map");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.zzcjq>> zzK(java.lang.String r11, java.lang.String r12) {
        /*
            r10 = this;
            r9 = 0
            r10.zzkD()
            r10.zzjC()
            com.google.android.gms.common.internal.zzbo.zzcF(r11)
            com.google.android.gms.common.internal.zzbo.zzcF(r12)
            android.support.v4.util.ArrayMap r8 = new android.support.v4.util.ArrayMap
            r8.<init>()
            android.database.sqlite.SQLiteDatabase r0 = r10.getWritableDatabase()
            java.lang.String r1 = "property_filters"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r3 = 0
            java.lang.String r4 = "audience_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r3 = 1
            java.lang.String r4 = "data"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            java.lang.String r3 = "app_id=? AND property_name=?"
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r5 = 0
            r4[r5] = r11     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r5 = 1
            r4[r5] = r12     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x00bd, all -> 0x00b3 }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x009a }
            if (r0 != 0) goto L_0x0047
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x009a }
            if (r1 == 0) goto L_0x0046
            r1.close()
        L_0x0046:
            return r0
        L_0x0047:
            r0 = 1
            byte[] r0 = r1.getBlob(r0)     // Catch:{ SQLiteException -> 0x009a }
            r2 = 0
            int r3 = r0.length     // Catch:{ SQLiteException -> 0x009a }
            com.google.android.gms.internal.adg r0 = com.google.android.gms.internal.adg.zzb(r0, r2, r3)     // Catch:{ SQLiteException -> 0x009a }
            com.google.android.gms.internal.zzcjq r2 = new com.google.android.gms.internal.zzcjq     // Catch:{ SQLiteException -> 0x009a }
            r2.<init>()     // Catch:{ SQLiteException -> 0x009a }
            r2.zza((com.google.android.gms.internal.adg) r0)     // Catch:{ IOException -> 0x0087 }
            r0 = 0
            int r3 = r1.getInt(r0)     // Catch:{ SQLiteException -> 0x009a }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)     // Catch:{ SQLiteException -> 0x009a }
            java.lang.Object r0 = r8.get(r0)     // Catch:{ SQLiteException -> 0x009a }
            java.util.List r0 = (java.util.List) r0     // Catch:{ SQLiteException -> 0x009a }
            if (r0 != 0) goto L_0x0077
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x009a }
            r0.<init>()     // Catch:{ SQLiteException -> 0x009a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ SQLiteException -> 0x009a }
            r8.put(r3, r0)     // Catch:{ SQLiteException -> 0x009a }
        L_0x0077:
            r0.add(r2)     // Catch:{ SQLiteException -> 0x009a }
        L_0x007a:
            boolean r0 = r1.moveToNext()     // Catch:{ SQLiteException -> 0x009a }
            if (r0 != 0) goto L_0x0047
            if (r1 == 0) goto L_0x0085
            r1.close()
        L_0x0085:
            r0 = r8
            goto L_0x0046
        L_0x0087:
            r0 = move-exception
            com.google.android.gms.internal.zzcfl r2 = r10.zzwF()     // Catch:{ SQLiteException -> 0x009a }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ SQLiteException -> 0x009a }
            java.lang.String r3 = "Failed to merge filter"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r11)     // Catch:{ SQLiteException -> 0x009a }
            r2.zze(r3, r4, r0)     // Catch:{ SQLiteException -> 0x009a }
            goto L_0x007a
        L_0x009a:
            r0 = move-exception
        L_0x009b:
            com.google.android.gms.internal.zzcfl r2 = r10.zzwF()     // Catch:{ all -> 0x00bb }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x00bb }
            java.lang.String r3 = "Database error querying filters. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r11)     // Catch:{ all -> 0x00bb }
            r2.zze(r3, r4, r0)     // Catch:{ all -> 0x00bb }
            if (r1 == 0) goto L_0x00b1
            r1.close()
        L_0x00b1:
            r0 = r9
            goto L_0x0046
        L_0x00b3:
            r0 = move-exception
            r1 = r9
        L_0x00b5:
            if (r1 == 0) goto L_0x00ba
            r1.close()
        L_0x00ba:
            throw r0
        L_0x00bb:
            r0 = move-exception
            goto L_0x00b5
        L_0x00bd:
            r0 = move-exception
            r1 = r9
            goto L_0x009b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzK(java.lang.String, java.lang.String):java.util.Map");
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final long zzL(String str, String str2) {
        long j;
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        zzjC();
        zzkD();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            j = zza(new StringBuilder(String.valueOf(str2).length() + 32).append("select ").append(str2).append(" from app2 where app_id=?").toString(), new String[]{str}, -1);
            if (j == -1) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", str);
                contentValues.put("first_open_count", 0);
                contentValues.put("previous_install_count", 0);
                if (writableDatabase.insertWithOnConflict("app2", (String) null, contentValues, 5) == -1) {
                    zzwF().zzyx().zze("Failed to insert column (got -1). appId", zzcfl.zzdZ(str), str2);
                    writableDatabase.endTransaction();
                    return -1;
                }
                j = 0;
            }
            try {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("app_id", str);
                contentValues2.put(str2, Long.valueOf(1 + j));
                if (((long) writableDatabase.update("app2", contentValues2, "app_id = ?", new String[]{str})) == 0) {
                    zzwF().zzyx().zze("Failed to update column (got 0). appId", zzcfl.zzdZ(str), str2);
                    writableDatabase.endTransaction();
                    return -1;
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                return j;
            } catch (SQLiteException e) {
                e = e;
                try {
                    zzwF().zzyx().zzd("Error inserting column. appId", zzcfl.zzdZ(str), str2, e);
                    return j;
                } finally {
                    writableDatabase.endTransaction();
                }
            }
        } catch (SQLiteException e2) {
            e = e2;
            j = 0;
            zzwF().zzyx().zzd("Error inserting column. appId", zzcfl.zzdZ(str), str2, e);
            return j;
        }
    }

    public final long zza(zzcjz zzcjz) throws IOException {
        long zzn;
        zzjC();
        zzkD();
        zzbo.zzu(zzcjz);
        zzbo.zzcF(zzcjz.zzaH);
        try {
            byte[] bArr = new byte[zzcjz.zzLV()];
            adh zzc = adh.zzc(bArr, 0, bArr.length);
            zzcjz.zza(zzc);
            zzc.zzLM();
            zzcjl zzwB = zzwB();
            zzbo.zzu(bArr);
            zzwB.zzjC();
            MessageDigest zzbE = zzcjl.zzbE("MD5");
            if (zzbE == null) {
                zzwB.zzwF().zzyx().log("Failed to get MD5");
                zzn = 0;
            } else {
                zzn = zzcjl.zzn(zzbE.digest(bArr));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzcjz.zzaH);
            contentValues.put("metadata_fingerprint", Long.valueOf(zzn));
            contentValues.put("metadata", bArr);
            try {
                getWritableDatabase().insertWithOnConflict("raw_events_metadata", (String) null, contentValues, 4);
                return zzn;
            } catch (SQLiteException e) {
                zzwF().zzyx().zze("Error storing raw event metadata. appId", zzcfl.zzdZ(zzcjz.zzaH), e);
                throw e;
            }
        } catch (IOException e2) {
            zzwF().zzyx().zze("Data loss. Failed to serialize event metadata. appId", zzcfl.zzdZ(zzcjz.zzaH), e2);
            throw e2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0135  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzceo zza(long r12, java.lang.String r14, boolean r15, boolean r16, boolean r17, boolean r18, boolean r19) {
        /*
            r11 = this;
            com.google.android.gms.common.internal.zzbo.zzcF(r14)
            r11.zzjC()
            r11.zzkD()
            r0 = 1
            java.lang.String[] r10 = new java.lang.String[r0]
            r0 = 0
            r10[r0] = r14
            com.google.android.gms.internal.zzceo r8 = new com.google.android.gms.internal.zzceo
            r8.<init>()
            r9 = 0
            android.database.sqlite.SQLiteDatabase r0 = r11.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            java.lang.String r1 = "apps"
            r2 = 6
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            r3 = 0
            java.lang.String r4 = "day"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            r3 = 1
            java.lang.String r4 = "daily_events_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            r3 = 2
            java.lang.String r4 = "daily_public_events_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            r3 = 3
            java.lang.String r4 = "daily_conversions_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            r3 = 4
            java.lang.String r4 = "daily_error_events_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            r3 = 5
            java.lang.String r4 = "daily_realtime_events_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            java.lang.String r3 = "app_id=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            r5 = 0
            r4[r5] = r14     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x0116, all -> 0x0131 }
            boolean r2 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x013b }
            if (r2 != 0) goto L_0x0069
            com.google.android.gms.internal.zzcfl r0 = r11.zzwF()     // Catch:{ SQLiteException -> 0x013b }
            com.google.android.gms.internal.zzcfn r0 = r0.zzyz()     // Catch:{ SQLiteException -> 0x013b }
            java.lang.String r2 = "Not updating daily counts, app is not known. appId"
            java.lang.Object r3 = com.google.android.gms.internal.zzcfl.zzdZ(r14)     // Catch:{ SQLiteException -> 0x013b }
            r0.zzj(r2, r3)     // Catch:{ SQLiteException -> 0x013b }
            if (r1 == 0) goto L_0x0067
            r1.close()
        L_0x0067:
            r0 = r8
        L_0x0068:
            return r0
        L_0x0069:
            r2 = 0
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x013b }
            int r2 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r2 != 0) goto L_0x0095
            r2 = 1
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x013b }
            r8.zzbpv = r2     // Catch:{ SQLiteException -> 0x013b }
            r2 = 2
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x013b }
            r8.zzbpu = r2     // Catch:{ SQLiteException -> 0x013b }
            r2 = 3
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x013b }
            r8.zzbpw = r2     // Catch:{ SQLiteException -> 0x013b }
            r2 = 4
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x013b }
            r8.zzbpx = r2     // Catch:{ SQLiteException -> 0x013b }
            r2 = 5
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x013b }
            r8.zzbpy = r2     // Catch:{ SQLiteException -> 0x013b }
        L_0x0095:
            if (r15 == 0) goto L_0x009e
            long r2 = r8.zzbpv     // Catch:{ SQLiteException -> 0x013b }
            r4 = 1
            long r2 = r2 + r4
            r8.zzbpv = r2     // Catch:{ SQLiteException -> 0x013b }
        L_0x009e:
            if (r16 == 0) goto L_0x00a7
            long r2 = r8.zzbpu     // Catch:{ SQLiteException -> 0x013b }
            r4 = 1
            long r2 = r2 + r4
            r8.zzbpu = r2     // Catch:{ SQLiteException -> 0x013b }
        L_0x00a7:
            if (r17 == 0) goto L_0x00b0
            long r2 = r8.zzbpw     // Catch:{ SQLiteException -> 0x013b }
            r4 = 1
            long r2 = r2 + r4
            r8.zzbpw = r2     // Catch:{ SQLiteException -> 0x013b }
        L_0x00b0:
            if (r18 == 0) goto L_0x00b9
            long r2 = r8.zzbpx     // Catch:{ SQLiteException -> 0x013b }
            r4 = 1
            long r2 = r2 + r4
            r8.zzbpx = r2     // Catch:{ SQLiteException -> 0x013b }
        L_0x00b9:
            if (r19 == 0) goto L_0x00c2
            long r2 = r8.zzbpy     // Catch:{ SQLiteException -> 0x013b }
            r4 = 1
            long r2 = r2 + r4
            r8.zzbpy = r2     // Catch:{ SQLiteException -> 0x013b }
        L_0x00c2:
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch:{ SQLiteException -> 0x013b }
            r2.<init>()     // Catch:{ SQLiteException -> 0x013b }
            java.lang.String r3 = "day"
            java.lang.Long r4 = java.lang.Long.valueOf(r12)     // Catch:{ SQLiteException -> 0x013b }
            r2.put(r3, r4)     // Catch:{ SQLiteException -> 0x013b }
            java.lang.String r3 = "daily_public_events_count"
            long r4 = r8.zzbpu     // Catch:{ SQLiteException -> 0x013b }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ SQLiteException -> 0x013b }
            r2.put(r3, r4)     // Catch:{ SQLiteException -> 0x013b }
            java.lang.String r3 = "daily_events_count"
            long r4 = r8.zzbpv     // Catch:{ SQLiteException -> 0x013b }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ SQLiteException -> 0x013b }
            r2.put(r3, r4)     // Catch:{ SQLiteException -> 0x013b }
            java.lang.String r3 = "daily_conversions_count"
            long r4 = r8.zzbpw     // Catch:{ SQLiteException -> 0x013b }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ SQLiteException -> 0x013b }
            r2.put(r3, r4)     // Catch:{ SQLiteException -> 0x013b }
            java.lang.String r3 = "daily_error_events_count"
            long r4 = r8.zzbpx     // Catch:{ SQLiteException -> 0x013b }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ SQLiteException -> 0x013b }
            r2.put(r3, r4)     // Catch:{ SQLiteException -> 0x013b }
            java.lang.String r3 = "daily_realtime_events_count"
            long r4 = r8.zzbpy     // Catch:{ SQLiteException -> 0x013b }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ SQLiteException -> 0x013b }
            r2.put(r3, r4)     // Catch:{ SQLiteException -> 0x013b }
            java.lang.String r3 = "apps"
            java.lang.String r4 = "app_id=?"
            r0.update(r3, r2, r4, r10)     // Catch:{ SQLiteException -> 0x013b }
            if (r1 == 0) goto L_0x0113
            r1.close()
        L_0x0113:
            r0 = r8
            goto L_0x0068
        L_0x0116:
            r0 = move-exception
            r1 = r9
        L_0x0118:
            com.google.android.gms.internal.zzcfl r2 = r11.zzwF()     // Catch:{ all -> 0x0139 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x0139 }
            java.lang.String r3 = "Error updating daily counts. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r14)     // Catch:{ all -> 0x0139 }
            r2.zze(r3, r4, r0)     // Catch:{ all -> 0x0139 }
            if (r1 == 0) goto L_0x012e
            r1.close()
        L_0x012e:
            r0 = r8
            goto L_0x0068
        L_0x0131:
            r0 = move-exception
            r1 = r9
        L_0x0133:
            if (r1 == 0) goto L_0x0138
            r1.close()
        L_0x0138:
            throw r0
        L_0x0139:
            r0 = move-exception
            goto L_0x0133
        L_0x013b:
            r0 = move-exception
            goto L_0x0118
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zza(long, java.lang.String, boolean, boolean, boolean, boolean, boolean):com.google.android.gms.internal.zzceo");
    }

    @WorkerThread
    public final void zza(zzceg zzceg) {
        zzbo.zzu(zzceg);
        zzjC();
        zzkD();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzceg.zzhl());
        contentValues.put("app_instance_id", zzceg.getAppInstanceId());
        contentValues.put("gmp_app_id", zzceg.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzceg.zzwJ());
        contentValues.put("last_bundle_index", Long.valueOf(zzceg.zzwS()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzceg.zzwL()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzceg.zzwM()));
        contentValues.put("app_version", zzceg.zzjH());
        contentValues.put("app_store", zzceg.zzwO());
        contentValues.put("gmp_version", Long.valueOf(zzceg.zzwP()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzceg.zzwQ()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzceg.zzwR()));
        contentValues.put("day", Long.valueOf(zzceg.zzwW()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzceg.zzwX()));
        contentValues.put("daily_events_count", Long.valueOf(zzceg.zzwY()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzceg.zzwZ()));
        contentValues.put("config_fetched_time", Long.valueOf(zzceg.zzwT()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzceg.zzwU()));
        contentValues.put("app_version_int", Long.valueOf(zzceg.zzwN()));
        contentValues.put("firebase_instance_id", zzceg.zzwK());
        contentValues.put("daily_error_events_count", Long.valueOf(zzceg.zzxb()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzceg.zzxa()));
        contentValues.put("health_monitor_sample", zzceg.zzxc());
        contentValues.put("android_id", Long.valueOf(zzceg.zzxe()));
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (((long) writableDatabase.update("apps", contentValues, "app_id = ?", new String[]{zzceg.zzhl()})) == 0 && writableDatabase.insertWithOnConflict("apps", (String) null, contentValues, 5) == -1) {
                zzwF().zzyx().zzj("Failed to insert/update app (got -1). appId", zzcfl.zzdZ(zzceg.zzhl()));
            }
        } catch (SQLiteException e) {
            zzwF().zzyx().zze("Error storing app. appId", zzcfl.zzdZ(zzceg.zzhl()), e);
        }
    }

    @WorkerThread
    public final void zza(zzcev zzcev) {
        zzbo.zzu(zzcev);
        zzjC();
        zzkD();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcev.mAppId);
        contentValues.put("name", zzcev.mName);
        contentValues.put("lifetime_count", Long.valueOf(zzcev.zzbpG));
        contentValues.put("current_bundle_count", Long.valueOf(zzcev.zzbpH));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzcev.zzbpI));
        try {
            if (getWritableDatabase().insertWithOnConflict("events", (String) null, contentValues, 5) == -1) {
                zzwF().zzyx().zzj("Failed to insert/update event aggregates (got -1). appId", zzcfl.zzdZ(zzcev.mAppId));
            }
        } catch (SQLiteException e) {
            zzwF().zzyx().zze("Error storing event aggregates. appId", zzcfl.zzdZ(zzcev.mAppId), e);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r8 = r3.zzbuJ;
        r9 = r8.length;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00a2, code lost:
        if (r2 >= r9) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a8, code lost:
        if (r8[r2].zzbuM != null) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00aa, code lost:
        zzwF().zzyz().zze("Property filter with no ID. Audience definition ignored. appId, audienceId", com.google.android.gms.internal.zzcfl.zzdZ(r13), r3.zzbuI);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00be, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00c1, code lost:
        r8 = r3.zzbuK;
        r9 = r8.length;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00c5, code lost:
        if (r2 >= r9) goto L_0x013c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00cd, code lost:
        if (zza(r13, r7, r8[r2]) != false) goto L_0x011a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00cf, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00d0, code lost:
        if (r2 == false) goto L_0x00e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d2, code lost:
        r8 = r3.zzbuJ;
        r9 = r8.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00d6, code lost:
        if (r3 >= r9) goto L_0x00e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00de, code lost:
        if (zza(r13, r7, r8[r3]) != false) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e0, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00e1, code lost:
        if (r2 != false) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00e3, code lost:
        zzkD();
        zzjC();
        com.google.android.gms.common.internal.zzbo.zzcF(r13);
        r2 = getWritableDatabase();
        r2.delete("property_filters", "app_id=? and audience_id=?", new java.lang.String[]{r13, java.lang.String.valueOf(r7)});
        r2.delete("event_filters", "app_id=? and audience_id=?", new java.lang.String[]{r13, java.lang.String.valueOf(r7)});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x011a, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x011d, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x013c, code lost:
        r2 = true;
     */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(java.lang.String r13, com.google.android.gms.internal.zzcjm[] r14) {
        /*
            r12 = this;
            r4 = 1
            r0 = 0
            r12.zzkD()
            r12.zzjC()
            com.google.android.gms.common.internal.zzbo.zzcF(r13)
            com.google.android.gms.common.internal.zzbo.zzu(r14)
            android.database.sqlite.SQLiteDatabase r5 = r12.getWritableDatabase()
            r5.beginTransaction()
            r12.zzkD()     // Catch:{ all -> 0x0096 }
            r12.zzjC()     // Catch:{ all -> 0x0096 }
            com.google.android.gms.common.internal.zzbo.zzcF(r13)     // Catch:{ all -> 0x0096 }
            android.database.sqlite.SQLiteDatabase r1 = r12.getWritableDatabase()     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = "property_filters"
            java.lang.String r3 = "app_id=?"
            r6 = 1
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ all -> 0x0096 }
            r7 = 0
            r6[r7] = r13     // Catch:{ all -> 0x0096 }
            r1.delete(r2, r3, r6)     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = "event_filters"
            java.lang.String r3 = "app_id=?"
            r6 = 1
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ all -> 0x0096 }
            r7 = 0
            r6[r7] = r13     // Catch:{ all -> 0x0096 }
            r1.delete(r2, r3, r6)     // Catch:{ all -> 0x0096 }
            int r6 = r14.length     // Catch:{ all -> 0x0096 }
            r1 = r0
        L_0x003e:
            if (r1 >= r6) goto L_0x0120
            r3 = r14[r1]     // Catch:{ all -> 0x0096 }
            r12.zzkD()     // Catch:{ all -> 0x0096 }
            r12.zzjC()     // Catch:{ all -> 0x0096 }
            com.google.android.gms.common.internal.zzbo.zzcF(r13)     // Catch:{ all -> 0x0096 }
            com.google.android.gms.common.internal.zzbo.zzu(r3)     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.zzcjn[] r2 = r3.zzbuK     // Catch:{ all -> 0x0096 }
            com.google.android.gms.common.internal.zzbo.zzu(r2)     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.zzcjq[] r2 = r3.zzbuJ     // Catch:{ all -> 0x0096 }
            com.google.android.gms.common.internal.zzbo.zzu(r2)     // Catch:{ all -> 0x0096 }
            java.lang.Integer r2 = r3.zzbuI     // Catch:{ all -> 0x0096 }
            if (r2 != 0) goto L_0x0070
            com.google.android.gms.internal.zzcfl r2 = r12.zzwF()     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = "Audience with no ID. appId"
            java.lang.Object r7 = com.google.android.gms.internal.zzcfl.zzdZ(r13)     // Catch:{ all -> 0x0096 }
            r2.zzj(r3, r7)     // Catch:{ all -> 0x0096 }
        L_0x006d:
            int r1 = r1 + 1
            goto L_0x003e
        L_0x0070:
            java.lang.Integer r2 = r3.zzbuI     // Catch:{ all -> 0x0096 }
            int r7 = r2.intValue()     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.zzcjn[] r8 = r3.zzbuK     // Catch:{ all -> 0x0096 }
            int r9 = r8.length     // Catch:{ all -> 0x0096 }
            r2 = r0
        L_0x007a:
            if (r2 >= r9) goto L_0x009e
            r10 = r8[r2]     // Catch:{ all -> 0x0096 }
            java.lang.Integer r10 = r10.zzbuM     // Catch:{ all -> 0x0096 }
            if (r10 != 0) goto L_0x009b
            com.google.android.gms.internal.zzcfl r2 = r12.zzwF()     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()     // Catch:{ all -> 0x0096 }
            java.lang.String r7 = "Event filter with no ID. Audience definition ignored. appId, audienceId"
            java.lang.Object r8 = com.google.android.gms.internal.zzcfl.zzdZ(r13)     // Catch:{ all -> 0x0096 }
            java.lang.Integer r3 = r3.zzbuI     // Catch:{ all -> 0x0096 }
            r2.zze(r7, r8, r3)     // Catch:{ all -> 0x0096 }
            goto L_0x006d
        L_0x0096:
            r0 = move-exception
            r5.endTransaction()
            throw r0
        L_0x009b:
            int r2 = r2 + 1
            goto L_0x007a
        L_0x009e:
            com.google.android.gms.internal.zzcjq[] r8 = r3.zzbuJ     // Catch:{ all -> 0x0096 }
            int r9 = r8.length     // Catch:{ all -> 0x0096 }
            r2 = r0
        L_0x00a2:
            if (r2 >= r9) goto L_0x00c1
            r10 = r8[r2]     // Catch:{ all -> 0x0096 }
            java.lang.Integer r10 = r10.zzbuM     // Catch:{ all -> 0x0096 }
            if (r10 != 0) goto L_0x00be
            com.google.android.gms.internal.zzcfl r2 = r12.zzwF()     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyz()     // Catch:{ all -> 0x0096 }
            java.lang.String r7 = "Property filter with no ID. Audience definition ignored. appId, audienceId"
            java.lang.Object r8 = com.google.android.gms.internal.zzcfl.zzdZ(r13)     // Catch:{ all -> 0x0096 }
            java.lang.Integer r3 = r3.zzbuI     // Catch:{ all -> 0x0096 }
            r2.zze(r7, r8, r3)     // Catch:{ all -> 0x0096 }
            goto L_0x006d
        L_0x00be:
            int r2 = r2 + 1
            goto L_0x00a2
        L_0x00c1:
            com.google.android.gms.internal.zzcjn[] r8 = r3.zzbuK     // Catch:{ all -> 0x0096 }
            int r9 = r8.length     // Catch:{ all -> 0x0096 }
            r2 = r0
        L_0x00c5:
            if (r2 >= r9) goto L_0x013c
            r10 = r8[r2]     // Catch:{ all -> 0x0096 }
            boolean r10 = r12.zza((java.lang.String) r13, (int) r7, (com.google.android.gms.internal.zzcjn) r10)     // Catch:{ all -> 0x0096 }
            if (r10 != 0) goto L_0x011a
            r2 = r0
        L_0x00d0:
            if (r2 == 0) goto L_0x00e1
            com.google.android.gms.internal.zzcjq[] r8 = r3.zzbuJ     // Catch:{ all -> 0x0096 }
            int r9 = r8.length     // Catch:{ all -> 0x0096 }
            r3 = r0
        L_0x00d6:
            if (r3 >= r9) goto L_0x00e1
            r10 = r8[r3]     // Catch:{ all -> 0x0096 }
            boolean r10 = r12.zza((java.lang.String) r13, (int) r7, (com.google.android.gms.internal.zzcjq) r10)     // Catch:{ all -> 0x0096 }
            if (r10 != 0) goto L_0x011d
            r2 = r0
        L_0x00e1:
            if (r2 != 0) goto L_0x006d
            r12.zzkD()     // Catch:{ all -> 0x0096 }
            r12.zzjC()     // Catch:{ all -> 0x0096 }
            com.google.android.gms.common.internal.zzbo.zzcF(r13)     // Catch:{ all -> 0x0096 }
            android.database.sqlite.SQLiteDatabase r2 = r12.getWritableDatabase()     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = "property_filters"
            java.lang.String r8 = "app_id=? and audience_id=?"
            r9 = 2
            java.lang.String[] r9 = new java.lang.String[r9]     // Catch:{ all -> 0x0096 }
            r10 = 0
            r9[r10] = r13     // Catch:{ all -> 0x0096 }
            r10 = 1
            java.lang.String r11 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0096 }
            r9[r10] = r11     // Catch:{ all -> 0x0096 }
            r2.delete(r3, r8, r9)     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = "event_filters"
            java.lang.String r8 = "app_id=? and audience_id=?"
            r9 = 2
            java.lang.String[] r9 = new java.lang.String[r9]     // Catch:{ all -> 0x0096 }
            r10 = 0
            r9[r10] = r13     // Catch:{ all -> 0x0096 }
            r10 = 1
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0096 }
            r9[r10] = r7     // Catch:{ all -> 0x0096 }
            r2.delete(r3, r8, r9)     // Catch:{ all -> 0x0096 }
            goto L_0x006d
        L_0x011a:
            int r2 = r2 + 1
            goto L_0x00c5
        L_0x011d:
            int r3 = r3 + 1
            goto L_0x00d6
        L_0x0120:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0096 }
            r1.<init>()     // Catch:{ all -> 0x0096 }
            int r2 = r14.length     // Catch:{ all -> 0x0096 }
        L_0x0126:
            if (r0 >= r2) goto L_0x0132
            r3 = r14[r0]     // Catch:{ all -> 0x0096 }
            java.lang.Integer r3 = r3.zzbuI     // Catch:{ all -> 0x0096 }
            r1.add(r3)     // Catch:{ all -> 0x0096 }
            int r0 = r0 + 1
            goto L_0x0126
        L_0x0132:
            r12.zzc((java.lang.String) r13, (java.util.List<java.lang.Integer>) r1)     // Catch:{ all -> 0x0096 }
            r5.setTransactionSuccessful()     // Catch:{ all -> 0x0096 }
            r5.endTransaction()
            return
        L_0x013c:
            r2 = r4
            goto L_0x00d0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zza(java.lang.String, com.google.android.gms.internal.zzcjm[]):void");
    }

    @WorkerThread
    public final boolean zza(zzcek zzcek) {
        zzbo.zzu(zzcek);
        zzjC();
        zzkD();
        if (zzG(zzcek.packageName, zzcek.zzbpd.name) == null) {
            long zzb = zzb("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzcek.packageName});
            zzcem.zzxv();
            if (zzb >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcek.packageName);
        contentValues.put(FirebaseAnalytics.Param.ORIGIN, zzcek.zzbpc);
        contentValues.put("name", zzcek.zzbpd.name);
        zza(contentValues, "value", zzcek.zzbpd.getValue());
        contentValues.put("active", Boolean.valueOf(zzcek.zzbpf));
        contentValues.put("trigger_event_name", zzcek.zzbpg);
        contentValues.put("trigger_timeout", Long.valueOf(zzcek.zzbpi));
        zzwB();
        contentValues.put("timed_out_event", zzcjl.zza((Parcelable) zzcek.zzbph));
        contentValues.put("creation_timestamp", Long.valueOf(zzcek.zzbpe));
        zzwB();
        contentValues.put("triggered_event", zzcjl.zza((Parcelable) zzcek.zzbpj));
        contentValues.put("triggered_timestamp", Long.valueOf(zzcek.zzbpd.zzbuy));
        contentValues.put("time_to_live", Long.valueOf(zzcek.zzbpk));
        zzwB();
        contentValues.put("expired_event", zzcjl.zza((Parcelable) zzcek.zzbpl));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", (String) null, contentValues, 5) == -1) {
                zzwF().zzyx().zzj("Failed to insert/update conditional user property (got -1)", zzcfl.zzdZ(zzcek.packageName));
            }
        } catch (SQLiteException e) {
            zzwF().zzyx().zze("Error storing conditional user property", zzcfl.zzdZ(zzcek.packageName), e);
        }
        return true;
    }

    public final boolean zza(zzceu zzceu, long j, boolean z) {
        zzjC();
        zzkD();
        zzbo.zzu(zzceu);
        zzbo.zzcF(zzceu.mAppId);
        zzcjw zzcjw = new zzcjw();
        zzcjw.zzbvy = Long.valueOf(zzceu.zzbpE);
        zzcjw.zzbvw = new zzcjx[zzceu.zzbpF.size()];
        Iterator<String> it = zzceu.zzbpF.iterator();
        int i = 0;
        while (it.hasNext()) {
            String next = it.next();
            zzcjx zzcjx = new zzcjx();
            zzcjw.zzbvw[i] = zzcjx;
            zzcjx.name = next;
            zzwB().zza(zzcjx, zzceu.zzbpF.get(next));
            i++;
        }
        try {
            byte[] bArr = new byte[zzcjw.zzLV()];
            adh zzc = adh.zzc(bArr, 0, bArr.length);
            zzcjw.zza(zzc);
            zzc.zzLM();
            zzwF().zzyD().zze("Saving event, name, data size", zzwA().zzdW(zzceu.mName), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzceu.mAppId);
            contentValues.put("name", zzceu.mName);
            contentValues.put("timestamp", Long.valueOf(zzceu.zzayS));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
            try {
                if (getWritableDatabase().insert("raw_events", (String) null, contentValues) != -1) {
                    return true;
                }
                zzwF().zzyx().zzj("Failed to insert raw event (got -1). appId", zzcfl.zzdZ(zzceu.mAppId));
                return false;
            } catch (SQLiteException e) {
                zzwF().zzyx().zze("Error storing raw event. appId", zzcfl.zzdZ(zzceu.mAppId), e);
                return false;
            }
        } catch (IOException e2) {
            zzwF().zzyx().zze("Data loss. Failed to serialize event params/data. appId", zzcfl.zzdZ(zzceu.mAppId), e2);
            return false;
        }
    }

    @WorkerThread
    public final boolean zza(zzcjk zzcjk) {
        zzbo.zzu(zzcjk);
        zzjC();
        zzkD();
        if (zzG(zzcjk.mAppId, zzcjk.mName) == null) {
            if (zzcjl.zzeo(zzcjk.mName)) {
                long zzb = zzb("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzcjk.mAppId});
                zzcem.zzxs();
                if (zzb >= 25) {
                    return false;
                }
            } else {
                long zzb2 = zzb("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzcjk.mAppId, zzcjk.mOrigin});
                zzcem.zzxu();
                if (zzb2 >= 25) {
                    return false;
                }
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcjk.mAppId);
        contentValues.put(FirebaseAnalytics.Param.ORIGIN, zzcjk.mOrigin);
        contentValues.put("name", zzcjk.mName);
        contentValues.put("set_timestamp", Long.valueOf(zzcjk.zzbuC));
        zza(contentValues, "value", zzcjk.mValue);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", (String) null, contentValues, 5) == -1) {
                zzwF().zzyx().zzj("Failed to insert/update user property (got -1). appId", zzcfl.zzdZ(zzcjk.mAppId));
            }
        } catch (SQLiteException e) {
            zzwF().zzyx().zze("Error storing user property. appId", zzcfl.zzdZ(zzcjk.mAppId), e);
        }
        return true;
    }

    @WorkerThread
    public final boolean zza(zzcjz zzcjz, boolean z) {
        zzjC();
        zzkD();
        zzbo.zzu(zzcjz);
        zzbo.zzcF(zzcjz.zzaH);
        zzbo.zzu(zzcjz.zzbvI);
        zzye();
        long currentTimeMillis = zzkq().currentTimeMillis();
        if (zzcjz.zzbvI.longValue() < currentTimeMillis - zzcem.zzxG() || zzcjz.zzbvI.longValue() > zzcem.zzxG() + currentTimeMillis) {
            zzwF().zzyz().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzcfl.zzdZ(zzcjz.zzaH), Long.valueOf(currentTimeMillis), zzcjz.zzbvI);
        }
        try {
            byte[] bArr = new byte[zzcjz.zzLV()];
            adh zzc = adh.zzc(bArr, 0, bArr.length);
            zzcjz.zza(zzc);
            zzc.zzLM();
            byte[] zzl = zzwB().zzl(bArr);
            zzwF().zzyD().zzj("Saving bundle, size", Integer.valueOf(zzl.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzcjz.zzaH);
            contentValues.put("bundle_end_timestamp", zzcjz.zzbvI);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, zzl);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
            try {
                if (getWritableDatabase().insert("queue", (String) null, contentValues) != -1) {
                    return true;
                }
                zzwF().zzyx().zzj("Failed to insert bundle (got -1). appId", zzcfl.zzdZ(zzcjz.zzaH));
                return false;
            } catch (SQLiteException e) {
                zzwF().zzyx().zze("Error storing bundle. appId", zzcfl.zzdZ(zzcjz.zzaH), e);
                return false;
            }
        } catch (IOException e2) {
            zzwF().zzyx().zze("Data loss. Failed to serialize bundle. appId", zzcfl.zzdZ(zzcjz.zzaH), e2);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0058  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String zzaa(long r8) {
        /*
            r7 = this;
            r0 = 0
            r7.zzjC()
            r7.zzkD()
            android.database.sqlite.SQLiteDatabase r1 = r7.getWritableDatabase()     // Catch:{ SQLiteException -> 0x003f, all -> 0x0054 }
            java.lang.String r2 = "select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;"
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x003f, all -> 0x0054 }
            r4 = 0
            java.lang.String r5 = java.lang.String.valueOf(r8)     // Catch:{ SQLiteException -> 0x003f, all -> 0x0054 }
            r3[r4] = r5     // Catch:{ SQLiteException -> 0x003f, all -> 0x0054 }
            android.database.Cursor r2 = r1.rawQuery(r2, r3)     // Catch:{ SQLiteException -> 0x003f, all -> 0x0054 }
            boolean r1 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x005f }
            if (r1 != 0) goto L_0x0034
            com.google.android.gms.internal.zzcfl r1 = r7.zzwF()     // Catch:{ SQLiteException -> 0x005f }
            com.google.android.gms.internal.zzcfn r1 = r1.zzyD()     // Catch:{ SQLiteException -> 0x005f }
            java.lang.String r3 = "No expired configs for apps with pending events"
            r1.log(r3)     // Catch:{ SQLiteException -> 0x005f }
            if (r2 == 0) goto L_0x0033
            r2.close()
        L_0x0033:
            return r0
        L_0x0034:
            r1 = 0
            java.lang.String r0 = r2.getString(r1)     // Catch:{ SQLiteException -> 0x005f }
            if (r2 == 0) goto L_0x0033
            r2.close()
            goto L_0x0033
        L_0x003f:
            r1 = move-exception
            r2 = r0
        L_0x0041:
            com.google.android.gms.internal.zzcfl r3 = r7.zzwF()     // Catch:{ all -> 0x005c }
            com.google.android.gms.internal.zzcfn r3 = r3.zzyx()     // Catch:{ all -> 0x005c }
            java.lang.String r4 = "Error selecting expired configs"
            r3.zzj(r4, r1)     // Catch:{ all -> 0x005c }
            if (r2 == 0) goto L_0x0033
            r2.close()
            goto L_0x0033
        L_0x0054:
            r1 = move-exception
            r2 = r0
        L_0x0056:
            if (r2 == 0) goto L_0x005b
            r2.close()
        L_0x005b:
            throw r1
        L_0x005c:
            r0 = move-exception
            r1 = r0
            goto L_0x0056
        L_0x005f:
            r1 = move-exception
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzaa(long):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x016c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.zzcek> zzc(java.lang.String r24, java.lang.String[] r25) {
        /*
            r23 = this;
            r23.zzjC()
            r23.zzkD()
            java.util.ArrayList r20 = new java.util.ArrayList
            r20.<init>()
            r11 = 0
            android.database.sqlite.SQLiteDatabase r2 = r23.getWritableDatabase()     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            java.lang.String r3 = "conditional_properties"
            r4 = 13
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 0
            java.lang.String r6 = "app_id"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 1
            java.lang.String r6 = "origin"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 2
            java.lang.String r6 = "name"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 3
            java.lang.String r6 = "value"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 4
            java.lang.String r6 = "active"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 5
            java.lang.String r6 = "trigger_event_name"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 6
            java.lang.String r6 = "trigger_timeout"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 7
            java.lang.String r6 = "timed_out_event"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 8
            java.lang.String r6 = "creation_timestamp"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 9
            java.lang.String r6 = "triggered_event"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 10
            java.lang.String r6 = "triggered_timestamp"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 11
            java.lang.String r6 = "time_to_live"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r5 = 12
            java.lang.String r6 = "expired_event"
            r4[r5] = r6     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            r7 = 0
            r8 = 0
            java.lang.String r9 = "rowid"
            com.google.android.gms.internal.zzcem.zzxv()     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            java.lang.String r10 = "1001"
            r5 = r24
            r6 = r25
            android.database.Cursor r21 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x014d, all -> 0x0167 }
            boolean r2 = r21.moveToFirst()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            if (r2 != 0) goto L_0x007b
            if (r21 == 0) goto L_0x0078
            r21.close()
        L_0x0078:
            r2 = r20
        L_0x007a:
            return r2
        L_0x007b:
            int r2 = r20.size()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            int r3 = com.google.android.gms.internal.zzcem.zzxv()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            if (r2 < r3) goto L_0x00a2
            com.google.android.gms.internal.zzcfl r2 = r23.zzwF()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            java.lang.String r3 = "Read more than the max allowed conditional properties, ignoring extra"
            int r4 = com.google.android.gms.internal.zzcem.zzxv()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2.zzj(r3, r4)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
        L_0x009a:
            if (r21 == 0) goto L_0x009f
            r21.close()
        L_0x009f:
            r2 = r20
            goto L_0x007a
        L_0x00a2:
            r2 = 0
            r0 = r21
            java.lang.String r8 = r0.getString(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2 = 1
            r0 = r21
            java.lang.String r7 = r0.getString(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2 = 2
            r0 = r21
            java.lang.String r3 = r0.getString(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2 = 3
            r0 = r23
            r1 = r21
            java.lang.Object r6 = r0.zza((android.database.Cursor) r1, (int) r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2 = 4
            r0 = r21
            int r2 = r0.getInt(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            if (r2 == 0) goto L_0x014a
            r11 = 1
        L_0x00ca:
            r2 = 5
            r0 = r21
            java.lang.String r12 = r0.getString(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2 = 6
            r0 = r21
            long r14 = r0.getLong(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcjl r2 = r23.zzwB()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r4 = 7
            r0 = r21
            byte[] r4 = r0.getBlob(r4)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            android.os.Parcelable$Creator<com.google.android.gms.internal.zzcez> r5 = com.google.android.gms.internal.zzcez.CREATOR     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            android.os.Parcelable r13 = r2.zzb((byte[]) r4, r5)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcez r13 = (com.google.android.gms.internal.zzcez) r13     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2 = 8
            r0 = r21
            long r9 = r0.getLong(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcjl r2 = r23.zzwB()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r4 = 9
            r0 = r21
            byte[] r4 = r0.getBlob(r4)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            android.os.Parcelable$Creator<com.google.android.gms.internal.zzcez> r5 = com.google.android.gms.internal.zzcez.CREATOR     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            android.os.Parcelable r16 = r2.zzb((byte[]) r4, r5)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcez r16 = (com.google.android.gms.internal.zzcez) r16     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2 = 10
            r0 = r21
            long r4 = r0.getLong(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2 = 11
            r0 = r21
            long r17 = r0.getLong(r2)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcjl r2 = r23.zzwB()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r19 = 12
            r0 = r21
            r1 = r19
            byte[] r19 = r0.getBlob(r1)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            android.os.Parcelable$Creator<com.google.android.gms.internal.zzcez> r22 = com.google.android.gms.internal.zzcez.CREATOR     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r0 = r19
            r1 = r22
            android.os.Parcelable r19 = r2.zzb((byte[]) r0, r1)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcez r19 = (com.google.android.gms.internal.zzcez) r19     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcji r2 = new com.google.android.gms.internal.zzcji     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r2.<init>(r3, r4, r6, r7)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            com.google.android.gms.internal.zzcek r5 = new com.google.android.gms.internal.zzcek     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r6 = r8
            r8 = r2
            r5.<init>(r6, r7, r8, r9, r11, r12, r13, r14, r16, r17, r19)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            r0 = r20
            r0.add(r5)     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            boolean r2 = r21.moveToNext()     // Catch:{ SQLiteException -> 0x0176, all -> 0x0170 }
            if (r2 != 0) goto L_0x007b
            goto L_0x009a
        L_0x014a:
            r11 = 0
            goto L_0x00ca
        L_0x014d:
            r2 = move-exception
            r3 = r11
        L_0x014f:
            com.google.android.gms.internal.zzcfl r4 = r23.zzwF()     // Catch:{ all -> 0x0172 }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ all -> 0x0172 }
            java.lang.String r5 = "Error querying conditional user property value"
            r4.zzj(r5, r2)     // Catch:{ all -> 0x0172 }
            java.util.List r2 = java.util.Collections.emptyList()     // Catch:{ all -> 0x0172 }
            if (r3 == 0) goto L_0x007a
            r3.close()
            goto L_0x007a
        L_0x0167:
            r2 = move-exception
            r21 = r11
        L_0x016a:
            if (r21 == 0) goto L_0x016f
            r21.close()
        L_0x016f:
            throw r2
        L_0x0170:
            r2 = move-exception
            goto L_0x016a
        L_0x0172:
            r2 = move-exception
            r21 = r3
            goto L_0x016a
        L_0x0176:
            r2 = move-exception
            r3 = r21
            goto L_0x014f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzc(java.lang.String, java.lang.String[]):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b0  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.zzcjk> zzdP(java.lang.String r12) {
        /*
            r11 = this;
            r10 = 0
            com.google.android.gms.common.internal.zzbo.zzcF(r12)
            r11.zzjC()
            r11.zzkD()
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            android.database.sqlite.SQLiteDatabase r0 = r11.getWritableDatabase()     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            java.lang.String r1 = "user_attributes"
            r2 = 4
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            r3 = 0
            java.lang.String r4 = "name"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            r3 = 1
            java.lang.String r4 = "origin"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            r3 = 2
            java.lang.String r4 = "set_timestamp"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            r3 = 3
            java.lang.String r4 = "value"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            java.lang.String r3 = "app_id=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            r5 = 0
            r4[r5] = r12     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            r5 = 0
            r6 = 0
            java.lang.String r7 = "rowid"
            int r8 = com.google.android.gms.internal.zzcem.zzxt()     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            android.database.Cursor r7 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x00ba, all -> 0x00ad }
            boolean r0 = r7.moveToFirst()     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            if (r0 != 0) goto L_0x0051
            if (r7 == 0) goto L_0x004f
            r7.close()
        L_0x004f:
            r0 = r9
        L_0x0050:
            return r0
        L_0x0051:
            r0 = 0
            java.lang.String r3 = r7.getString(r0)     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            r0 = 1
            java.lang.String r2 = r7.getString(r0)     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            if (r2 != 0) goto L_0x005f
            java.lang.String r2 = ""
        L_0x005f:
            r0 = 2
            long r4 = r7.getLong(r0)     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            r0 = 3
            java.lang.Object r6 = r11.zza((android.database.Cursor) r7, (int) r0)     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            if (r6 != 0) goto L_0x0089
            com.google.android.gms.internal.zzcfl r0 = r11.zzwF()     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            com.google.android.gms.internal.zzcfn r0 = r0.zzyx()     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            java.lang.String r1 = "Read invalid user property value, ignoring it. appId"
            java.lang.Object r2 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            r0.zzj(r1, r2)     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
        L_0x007c:
            boolean r0 = r7.moveToNext()     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            if (r0 != 0) goto L_0x0051
            if (r7 == 0) goto L_0x0087
            r7.close()
        L_0x0087:
            r0 = r9
            goto L_0x0050
        L_0x0089:
            com.google.android.gms.internal.zzcjk r0 = new com.google.android.gms.internal.zzcjk     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            r1 = r12
            r0.<init>(r1, r2, r3, r4, r6)     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            r9.add(r0)     // Catch:{ SQLiteException -> 0x0093, all -> 0x00b4 }
            goto L_0x007c
        L_0x0093:
            r0 = move-exception
            r1 = r7
        L_0x0095:
            com.google.android.gms.internal.zzcfl r2 = r11.zzwF()     // Catch:{ all -> 0x00b7 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x00b7 }
            java.lang.String r3 = "Error querying user properties. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ all -> 0x00b7 }
            r2.zze(r3, r4, r0)     // Catch:{ all -> 0x00b7 }
            if (r1 == 0) goto L_0x00ab
            r1.close()
        L_0x00ab:
            r0 = r10
            goto L_0x0050
        L_0x00ad:
            r0 = move-exception
        L_0x00ae:
            if (r10 == 0) goto L_0x00b3
            r10.close()
        L_0x00b3:
            throw r0
        L_0x00b4:
            r0 = move-exception
            r10 = r7
            goto L_0x00ae
        L_0x00b7:
            r0 = move-exception
            r10 = r1
            goto L_0x00ae
        L_0x00ba:
            r0 = move-exception
            r1 = r10
            goto L_0x0095
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzdP(java.lang.String):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x01eb  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzceg zzdQ(java.lang.String r12) {
        /*
            r11 = this;
            r10 = 0
            r9 = 1
            r8 = 0
            com.google.android.gms.common.internal.zzbo.zzcF(r12)
            r11.zzjC()
            r11.zzkD()
            android.database.sqlite.SQLiteDatabase r0 = r11.getWritableDatabase()     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            java.lang.String r1 = "apps"
            r2 = 23
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 0
            java.lang.String r4 = "app_instance_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 1
            java.lang.String r4 = "gmp_app_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 2
            java.lang.String r4 = "resettable_device_id_hash"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 3
            java.lang.String r4 = "last_bundle_index"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 4
            java.lang.String r4 = "last_bundle_start_timestamp"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 5
            java.lang.String r4 = "last_bundle_end_timestamp"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 6
            java.lang.String r4 = "app_version"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 7
            java.lang.String r4 = "app_store"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 8
            java.lang.String r4 = "gmp_version"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 9
            java.lang.String r4 = "dev_cert_hash"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 10
            java.lang.String r4 = "measurement_enabled"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 11
            java.lang.String r4 = "day"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 12
            java.lang.String r4 = "daily_public_events_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 13
            java.lang.String r4 = "daily_events_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 14
            java.lang.String r4 = "daily_conversions_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 15
            java.lang.String r4 = "config_fetched_time"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 16
            java.lang.String r4 = "failed_config_fetch_time"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 17
            java.lang.String r4 = "app_version_int"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 18
            java.lang.String r4 = "firebase_instance_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 19
            java.lang.String r4 = "daily_error_events_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 20
            java.lang.String r4 = "daily_realtime_events_count"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 21
            java.lang.String r4 = "health_monitor_sample"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r3 = 22
            java.lang.String r4 = "android_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            java.lang.String r3 = "app_id=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r5 = 0
            r4[r5] = r12     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x01cc, all -> 0x01e7 }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x01f1 }
            if (r0 != 0) goto L_0x00b4
            if (r1 == 0) goto L_0x00b2
            r1.close()
        L_0x00b2:
            r0 = r8
        L_0x00b3:
            return r0
        L_0x00b4:
            com.google.android.gms.internal.zzceg r0 = new com.google.android.gms.internal.zzceg     // Catch:{ SQLiteException -> 0x01f1 }
            com.google.android.gms.internal.zzcgl r2 = r11.zzboe     // Catch:{ SQLiteException -> 0x01f1 }
            r0.<init>(r2, r12)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 0
            java.lang.String r2 = r1.getString(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzdG(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 1
            java.lang.String r2 = r1.getString(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzdH(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 2
            java.lang.String r2 = r1.getString(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzdI(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 3
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzQ(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 4
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzL(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 5
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzM(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 6
            java.lang.String r2 = r1.getString(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.setAppVersion(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 7
            java.lang.String r2 = r1.getString(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzdK(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 8
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzO(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 9
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzP(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 10
            boolean r2 = r1.isNull(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            if (r2 == 0) goto L_0x01b2
            r2 = r9
        L_0x0116:
            if (r2 == 0) goto L_0x01ba
            r2 = r9
        L_0x0119:
            r0.setMeasurementEnabled(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 11
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzT(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 12
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzU(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 13
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzV(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 14
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzW(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 15
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzR(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 16
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzS(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 17
            boolean r2 = r1.isNull(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            if (r2 == 0) goto L_0x01bd
            r2 = -2147483648(0xffffffff80000000, double:NaN)
        L_0x015d:
            r0.zzN(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 18
            java.lang.String r2 = r1.getString(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzdJ(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 19
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzY(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 20
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzX(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 21
            java.lang.String r2 = r1.getString(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzdL(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r2 = 22
            boolean r2 = r1.isNull(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            if (r2 == 0) goto L_0x01c5
            r2 = 0
        L_0x018e:
            r0.zzZ(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            r0.zzwI()     // Catch:{ SQLiteException -> 0x01f1 }
            boolean r2 = r1.moveToNext()     // Catch:{ SQLiteException -> 0x01f1 }
            if (r2 == 0) goto L_0x01ab
            com.google.android.gms.internal.zzcfl r2 = r11.zzwF()     // Catch:{ SQLiteException -> 0x01f1 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ SQLiteException -> 0x01f1 }
            java.lang.String r3 = "Got multiple records for app, expected one. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x01f1 }
            r2.zzj(r3, r4)     // Catch:{ SQLiteException -> 0x01f1 }
        L_0x01ab:
            if (r1 == 0) goto L_0x00b3
            r1.close()
            goto L_0x00b3
        L_0x01b2:
            r2 = 10
            int r2 = r1.getInt(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            goto L_0x0116
        L_0x01ba:
            r2 = r10
            goto L_0x0119
        L_0x01bd:
            r2 = 17
            int r2 = r1.getInt(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            long r2 = (long) r2     // Catch:{ SQLiteException -> 0x01f1 }
            goto L_0x015d
        L_0x01c5:
            r2 = 22
            long r2 = r1.getLong(r2)     // Catch:{ SQLiteException -> 0x01f1 }
            goto L_0x018e
        L_0x01cc:
            r0 = move-exception
            r1 = r8
        L_0x01ce:
            com.google.android.gms.internal.zzcfl r2 = r11.zzwF()     // Catch:{ all -> 0x01ef }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x01ef }
            java.lang.String r3 = "Error querying app. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ all -> 0x01ef }
            r2.zze(r3, r4, r0)     // Catch:{ all -> 0x01ef }
            if (r1 == 0) goto L_0x01e4
            r1.close()
        L_0x01e4:
            r0 = r8
            goto L_0x00b3
        L_0x01e7:
            r0 = move-exception
            r1 = r8
        L_0x01e9:
            if (r1 == 0) goto L_0x01ee
            r1.close()
        L_0x01ee:
            throw r0
        L_0x01ef:
            r0 = move-exception
            goto L_0x01e9
        L_0x01f1:
            r0 = move-exception
            goto L_0x01ce
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzdQ(java.lang.String):com.google.android.gms.internal.zzceg");
    }

    public final long zzdR(String str) {
        zzbo.zzcF(str);
        zzjC();
        zzkD();
        try {
            return (long) getWritableDatabase().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, String.valueOf(Math.max(0, Math.min(1000000, zzwH().zzb(str, zzcfb.zzbqk))))});
        } catch (SQLiteException e) {
            zzwF().zzyx().zze("Error deleting over the limit events. appId", zzcfl.zzdZ(str), e);
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0074  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] zzdS(java.lang.String r10) {
        /*
            r9 = this;
            r8 = 0
            com.google.android.gms.common.internal.zzbo.zzcF(r10)
            r9.zzjC()
            r9.zzkD()
            android.database.sqlite.SQLiteDatabase r0 = r9.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0056, all -> 0x0070 }
            java.lang.String r1 = "apps"
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x0056, all -> 0x0070 }
            r3 = 0
            java.lang.String r4 = "remote_config"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x0056, all -> 0x0070 }
            java.lang.String r3 = "app_id=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0056, all -> 0x0070 }
            r5 = 0
            r4[r5] = r10     // Catch:{ SQLiteException -> 0x0056, all -> 0x0070 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x0056, all -> 0x0070 }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x007a }
            if (r0 != 0) goto L_0x0034
            if (r1 == 0) goto L_0x0032
            r1.close()
        L_0x0032:
            r0 = r8
        L_0x0033:
            return r0
        L_0x0034:
            r0 = 0
            byte[] r0 = r1.getBlob(r0)     // Catch:{ SQLiteException -> 0x007a }
            boolean r2 = r1.moveToNext()     // Catch:{ SQLiteException -> 0x007a }
            if (r2 == 0) goto L_0x0050
            com.google.android.gms.internal.zzcfl r2 = r9.zzwF()     // Catch:{ SQLiteException -> 0x007a }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ SQLiteException -> 0x007a }
            java.lang.String r3 = "Got multiple records for app config, expected one. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r10)     // Catch:{ SQLiteException -> 0x007a }
            r2.zzj(r3, r4)     // Catch:{ SQLiteException -> 0x007a }
        L_0x0050:
            if (r1 == 0) goto L_0x0033
            r1.close()
            goto L_0x0033
        L_0x0056:
            r0 = move-exception
            r1 = r8
        L_0x0058:
            com.google.android.gms.internal.zzcfl r2 = r9.zzwF()     // Catch:{ all -> 0x0078 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x0078 }
            java.lang.String r3 = "Error querying remote config. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r10)     // Catch:{ all -> 0x0078 }
            r2.zze(r3, r4, r0)     // Catch:{ all -> 0x0078 }
            if (r1 == 0) goto L_0x006e
            r1.close()
        L_0x006e:
            r0 = r8
            goto L_0x0033
        L_0x0070:
            r0 = move-exception
            r1 = r8
        L_0x0072:
            if (r1 == 0) goto L_0x0077
            r1.close()
        L_0x0077:
            throw r0
        L_0x0078:
            r0 = move-exception
            goto L_0x0072
        L_0x007a:
            r0 = move-exception
            goto L_0x0058
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzdS(java.lang.String):byte[]");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x009d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.Integer, com.google.android.gms.internal.zzcka> zzdT(java.lang.String r10) {
        /*
            r9 = this;
            r8 = 0
            r9.zzkD()
            r9.zzjC()
            com.google.android.gms.common.internal.zzbo.zzcF(r10)
            android.database.sqlite.SQLiteDatabase r0 = r9.getWritableDatabase()
            java.lang.String r1 = "audience_filter_values"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x00a3, all -> 0x0099 }
            r3 = 0
            java.lang.String r4 = "audience_id"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00a3, all -> 0x0099 }
            r3 = 1
            java.lang.String r4 = "current_results"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00a3, all -> 0x0099 }
            java.lang.String r3 = "app_id=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x00a3, all -> 0x0099 }
            r5 = 0
            r4[r5] = r10     // Catch:{ SQLiteException -> 0x00a3, all -> 0x0099 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x00a3, all -> 0x0099 }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x0080 }
            if (r0 != 0) goto L_0x0039
            if (r1 == 0) goto L_0x0037
            r1.close()
        L_0x0037:
            r0 = r8
        L_0x0038:
            return r0
        L_0x0039:
            android.support.v4.util.ArrayMap r0 = new android.support.v4.util.ArrayMap     // Catch:{ SQLiteException -> 0x0080 }
            r0.<init>()     // Catch:{ SQLiteException -> 0x0080 }
        L_0x003e:
            r2 = 0
            int r2 = r1.getInt(r2)     // Catch:{ SQLiteException -> 0x0080 }
            r3 = 1
            byte[] r3 = r1.getBlob(r3)     // Catch:{ SQLiteException -> 0x0080 }
            r4 = 0
            int r5 = r3.length     // Catch:{ SQLiteException -> 0x0080 }
            com.google.android.gms.internal.adg r3 = com.google.android.gms.internal.adg.zzb(r3, r4, r5)     // Catch:{ SQLiteException -> 0x0080 }
            com.google.android.gms.internal.zzcka r4 = new com.google.android.gms.internal.zzcka     // Catch:{ SQLiteException -> 0x0080 }
            r4.<init>()     // Catch:{ SQLiteException -> 0x0080 }
            r4.zza((com.google.android.gms.internal.adg) r3)     // Catch:{ IOException -> 0x0069 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ SQLiteException -> 0x0080 }
            r0.put(r2, r4)     // Catch:{ SQLiteException -> 0x0080 }
        L_0x005d:
            boolean r2 = r1.moveToNext()     // Catch:{ SQLiteException -> 0x0080 }
            if (r2 != 0) goto L_0x003e
            if (r1 == 0) goto L_0x0038
            r1.close()
            goto L_0x0038
        L_0x0069:
            r3 = move-exception
            com.google.android.gms.internal.zzcfl r4 = r9.zzwF()     // Catch:{ SQLiteException -> 0x0080 }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ SQLiteException -> 0x0080 }
            java.lang.String r5 = "Failed to merge filter results. appId, audienceId, error"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r10)     // Catch:{ SQLiteException -> 0x0080 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ SQLiteException -> 0x0080 }
            r4.zzd(r5, r6, r2, r3)     // Catch:{ SQLiteException -> 0x0080 }
            goto L_0x005d
        L_0x0080:
            r0 = move-exception
        L_0x0081:
            com.google.android.gms.internal.zzcfl r2 = r9.zzwF()     // Catch:{ all -> 0x00a1 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x00a1 }
            java.lang.String r3 = "Database error querying filter results. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r10)     // Catch:{ all -> 0x00a1 }
            r2.zze(r3, r4, r0)     // Catch:{ all -> 0x00a1 }
            if (r1 == 0) goto L_0x0097
            r1.close()
        L_0x0097:
            r0 = r8
            goto L_0x0038
        L_0x0099:
            r0 = move-exception
            r1 = r8
        L_0x009b:
            if (r1 == 0) goto L_0x00a0
            r1.close()
        L_0x00a0:
            throw r0
        L_0x00a1:
            r0 = move-exception
            goto L_0x009b
        L_0x00a3:
            r0 = move-exception
            r1 = r8
            goto L_0x0081
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzdT(java.lang.String):java.util.Map");
    }

    public final long zzdU(String str) {
        zzbo.zzcF(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x010d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x010e, code lost:
        r10 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0116, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0117, code lost:
        r1 = r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x010d A[ExcHandler: all (th java.lang.Throwable), Splitter:B:9:0x007f] */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.zzcjk> zzh(java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            r11 = this;
            r10 = 0
            com.google.android.gms.common.internal.zzbo.zzcF(r12)
            r11.zzjC()
            r11.zzkD()
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            r1 = 3
            r0.<init>(r1)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            r0.add(r12)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String r1 = "app_id=?"
            r3.<init>(r1)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            boolean r1 = android.text.TextUtils.isEmpty(r13)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            if (r1 != 0) goto L_0x002d
            r0.add(r13)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String r1 = " and origin=?"
            r3.append(r1)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
        L_0x002d:
            boolean r1 = android.text.TextUtils.isEmpty(r14)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            if (r1 != 0) goto L_0x0045
            java.lang.String r1 = java.lang.String.valueOf(r14)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String r2 = "*"
            java.lang.String r1 = r1.concat(r2)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            r0.add(r1)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String r1 = " and name glob ?"
            r3.append(r1)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
        L_0x0045:
            int r1 = r0.size()     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.Object[] r4 = r0.toArray(r1)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String[] r4 = (java.lang.String[]) r4     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            android.database.sqlite.SQLiteDatabase r0 = r11.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String r1 = "user_attributes"
            r2 = 4
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            r5 = 0
            java.lang.String r6 = "name"
            r2[r5] = r6     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            r5 = 1
            java.lang.String r6 = "set_timestamp"
            r2[r5] = r6     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            r5 = 2
            java.lang.String r6 = "value"
            r2[r5] = r6     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            r5 = 3
            java.lang.String r6 = "origin"
            r2[r5] = r6     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String r3 = r3.toString()     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            r5 = 0
            r6 = 0
            java.lang.String r7 = "rowid"
            com.google.android.gms.internal.zzcem.zzxt()     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            java.lang.String r8 = "1001"
            android.database.Cursor r7 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x0113, all -> 0x0106 }
            boolean r0 = r7.moveToFirst()     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            if (r0 != 0) goto L_0x008d
            if (r7 == 0) goto L_0x008a
            r7.close()
        L_0x008a:
            r0 = r9
        L_0x008b:
            return r0
        L_0x008c:
            r13 = r2
        L_0x008d:
            int r0 = r9.size()     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            int r1 = com.google.android.gms.internal.zzcem.zzxt()     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            if (r0 < r1) goto L_0x00b3
            com.google.android.gms.internal.zzcfl r0 = r11.zzwF()     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            com.google.android.gms.internal.zzcfn r0 = r0.zzyx()     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            java.lang.String r1 = "Read more than the max allowed user properties, ignoring excess"
            int r2 = com.google.android.gms.internal.zzcem.zzxt()     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            r0.zzj(r1, r2)     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
        L_0x00ac:
            if (r7 == 0) goto L_0x00b1
            r7.close()
        L_0x00b1:
            r0 = r9
            goto L_0x008b
        L_0x00b3:
            r0 = 0
            java.lang.String r3 = r7.getString(r0)     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            r0 = 1
            long r4 = r7.getLong(r0)     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            r0 = 2
            java.lang.Object r6 = r11.zza((android.database.Cursor) r7, (int) r0)     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            r0 = 3
            java.lang.String r2 = r7.getString(r0)     // Catch:{ SQLiteException -> 0x0116, all -> 0x010d }
            if (r6 != 0) goto L_0x00e1
            com.google.android.gms.internal.zzcfl r0 = r11.zzwF()     // Catch:{ SQLiteException -> 0x00eb, all -> 0x010d }
            com.google.android.gms.internal.zzcfn r0 = r0.zzyx()     // Catch:{ SQLiteException -> 0x00eb, all -> 0x010d }
            java.lang.String r1 = "(2)Read invalid user property value, ignoring it"
            java.lang.Object r3 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x00eb, all -> 0x010d }
            r0.zzd(r1, r3, r2, r14)     // Catch:{ SQLiteException -> 0x00eb, all -> 0x010d }
        L_0x00da:
            boolean r0 = r7.moveToNext()     // Catch:{ SQLiteException -> 0x00eb, all -> 0x010d }
            if (r0 != 0) goto L_0x008c
            goto L_0x00ac
        L_0x00e1:
            com.google.android.gms.internal.zzcjk r0 = new com.google.android.gms.internal.zzcjk     // Catch:{ SQLiteException -> 0x00eb, all -> 0x010d }
            r1 = r12
            r0.<init>(r1, r2, r3, r4, r6)     // Catch:{ SQLiteException -> 0x00eb, all -> 0x010d }
            r9.add(r0)     // Catch:{ SQLiteException -> 0x00eb, all -> 0x010d }
            goto L_0x00da
        L_0x00eb:
            r0 = move-exception
            r1 = r7
            r13 = r2
        L_0x00ee:
            com.google.android.gms.internal.zzcfl r2 = r11.zzwF()     // Catch:{ all -> 0x0110 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x0110 }
            java.lang.String r3 = "(2)Error querying user properties"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ all -> 0x0110 }
            r2.zzd(r3, r4, r13, r0)     // Catch:{ all -> 0x0110 }
            if (r1 == 0) goto L_0x0104
            r1.close()
        L_0x0104:
            r0 = r10
            goto L_0x008b
        L_0x0106:
            r0 = move-exception
        L_0x0107:
            if (r10 == 0) goto L_0x010c
            r10.close()
        L_0x010c:
            throw r0
        L_0x010d:
            r0 = move-exception
            r10 = r7
            goto L_0x0107
        L_0x0110:
            r0 = move-exception
            r10 = r1
            goto L_0x0107
        L_0x0113:
            r0 = move-exception
            r1 = r10
            goto L_0x00ee
        L_0x0116:
            r0 = move-exception
            r1 = r7
            goto L_0x00ee
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzh(java.lang.String, java.lang.String, java.lang.String):java.util.List");
    }

    @WorkerThread
    public final List<zzcek> zzi(String str, String str2, String str3) {
        zzbo.zzcF(str);
        zzjC();
        zzkD();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            sb.append(" and name glob ?");
        }
        return zzc(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00e7  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<android.util.Pair<com.google.android.gms.internal.zzcjz, java.lang.Long>> zzl(java.lang.String r12, int r13, int r14) {
        /*
            r11 = this;
            r10 = 0
            r1 = 1
            r9 = 0
            r11.zzjC()
            r11.zzkD()
            if (r13 <= 0) goto L_0x004e
            r0 = r1
        L_0x000c:
            com.google.android.gms.common.internal.zzbo.zzaf(r0)
            if (r14 <= 0) goto L_0x0050
        L_0x0011:
            com.google.android.gms.common.internal.zzbo.zzaf(r1)
            com.google.android.gms.common.internal.zzbo.zzcF(r12)
            android.database.sqlite.SQLiteDatabase r0 = r11.getWritableDatabase()     // Catch:{ SQLiteException -> 0x00c5, all -> 0x00e3 }
            java.lang.String r1 = "queue"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x00c5, all -> 0x00e3 }
            r3 = 0
            java.lang.String r4 = "rowid"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00c5, all -> 0x00e3 }
            r3 = 1
            java.lang.String r4 = "data"
            r2[r3] = r4     // Catch:{ SQLiteException -> 0x00c5, all -> 0x00e3 }
            java.lang.String r3 = "app_id=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x00c5, all -> 0x00e3 }
            r5 = 0
            r4[r5] = r12     // Catch:{ SQLiteException -> 0x00c5, all -> 0x00e3 }
            r5 = 0
            r6 = 0
            java.lang.String r7 = "rowid"
            java.lang.String r8 = java.lang.String.valueOf(r13)     // Catch:{ SQLiteException -> 0x00c5, all -> 0x00e3 }
            android.database.Cursor r2 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x00c5, all -> 0x00e3 }
            boolean r0 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            if (r0 != 0) goto L_0x0052
            java.util.List r0 = java.util.Collections.emptyList()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            if (r2 == 0) goto L_0x004d
            r2.close()
        L_0x004d:
            return r0
        L_0x004e:
            r0 = r9
            goto L_0x000c
        L_0x0050:
            r1 = r9
            goto L_0x0011
        L_0x0052:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r0.<init>()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r3 = r9
        L_0x0058:
            r1 = 0
            long r4 = r2.getLong(r1)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r1 = 1
            byte[] r1 = r2.getBlob(r1)     // Catch:{ IOException -> 0x009d }
            com.google.android.gms.internal.zzcjl r6 = r11.zzwB()     // Catch:{ IOException -> 0x009d }
            byte[] r1 = r6.zzm(r1)     // Catch:{ IOException -> 0x009d }
            boolean r6 = r0.isEmpty()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            if (r6 != 0) goto L_0x0074
            int r6 = r1.length     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            int r6 = r6 + r3
            if (r6 > r14) goto L_0x0097
        L_0x0074:
            r6 = 0
            int r7 = r1.length     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            com.google.android.gms.internal.adg r6 = com.google.android.gms.internal.adg.zzb(r1, r6, r7)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            com.google.android.gms.internal.zzcjz r7 = new com.google.android.gms.internal.zzcjz     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r7.<init>()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r7.zza((com.google.android.gms.internal.adg) r6)     // Catch:{ IOException -> 0x00b1 }
            int r1 = r1.length     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            int r1 = r1 + r3
            java.lang.Long r3 = java.lang.Long.valueOf(r4)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            android.util.Pair r3 = android.util.Pair.create(r7, r3)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r0.add(r3)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
        L_0x008f:
            boolean r3 = r2.moveToNext()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            if (r3 == 0) goto L_0x0097
            if (r1 <= r14) goto L_0x00f3
        L_0x0097:
            if (r2 == 0) goto L_0x004d
            r2.close()
            goto L_0x004d
        L_0x009d:
            r1 = move-exception
            com.google.android.gms.internal.zzcfl r4 = r11.zzwF()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            java.lang.String r5 = "Failed to unzip queued bundle. appId"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r4.zze(r5, r6, r1)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r1 = r3
            goto L_0x008f
        L_0x00b1:
            r1 = move-exception
            com.google.android.gms.internal.zzcfl r4 = r11.zzwF()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            com.google.android.gms.internal.zzcfn r4 = r4.zzyx()     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            java.lang.String r5 = "Failed to merge queued bundle. appId"
            java.lang.Object r6 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r4.zze(r5, r6, r1)     // Catch:{ SQLiteException -> 0x00f0, all -> 0x00eb }
            r1 = r3
            goto L_0x008f
        L_0x00c5:
            r0 = move-exception
            r1 = r10
        L_0x00c7:
            com.google.android.gms.internal.zzcfl r2 = r11.zzwF()     // Catch:{ all -> 0x00ed }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x00ed }
            java.lang.String r3 = "Error querying bundles. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcfl.zzdZ(r12)     // Catch:{ all -> 0x00ed }
            r2.zze(r3, r4, r0)     // Catch:{ all -> 0x00ed }
            java.util.List r0 = java.util.Collections.emptyList()     // Catch:{ all -> 0x00ed }
            if (r1 == 0) goto L_0x004d
            r1.close()
            goto L_0x004d
        L_0x00e3:
            r0 = move-exception
            r2 = r10
        L_0x00e5:
            if (r2 == 0) goto L_0x00ea
            r2.close()
        L_0x00ea:
            throw r0
        L_0x00eb:
            r0 = move-exception
            goto L_0x00e5
        L_0x00ed:
            r0 = move-exception
            r2 = r1
            goto L_0x00e5
        L_0x00f0:
            r0 = move-exception
            r1 = r2
            goto L_0x00c7
        L_0x00f3:
            r3 = r1
            goto L_0x0058
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzl(java.lang.String, int, int):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003c  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String zzyc() {
        /*
            r5 = this;
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r5.getWritableDatabase()
            java.lang.String r2 = "select app_id from queue order by has_realtime desc, rowid asc limit 1;"
            r3 = 0
            android.database.Cursor r2 = r1.rawQuery(r2, r3)     // Catch:{ SQLiteException -> 0x0023, all -> 0x0038 }
            boolean r1 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x0043 }
            if (r1 == 0) goto L_0x001d
            r1 = 0
            java.lang.String r0 = r2.getString(r1)     // Catch:{ SQLiteException -> 0x0043 }
            if (r2 == 0) goto L_0x001c
            r2.close()
        L_0x001c:
            return r0
        L_0x001d:
            if (r2 == 0) goto L_0x001c
            r2.close()
            goto L_0x001c
        L_0x0023:
            r1 = move-exception
            r2 = r0
        L_0x0025:
            com.google.android.gms.internal.zzcfl r3 = r5.zzwF()     // Catch:{ all -> 0x0040 }
            com.google.android.gms.internal.zzcfn r3 = r3.zzyx()     // Catch:{ all -> 0x0040 }
            java.lang.String r4 = "Database error getting next bundle app id"
            r3.zzj(r4, r1)     // Catch:{ all -> 0x0040 }
            if (r2 == 0) goto L_0x001c
            r2.close()
            goto L_0x001c
        L_0x0038:
            r1 = move-exception
            r2 = r0
        L_0x003a:
            if (r2 == 0) goto L_0x003f
            r2.close()
        L_0x003f:
            throw r1
        L_0x0040:
            r0 = move-exception
            r1 = r0
            goto L_0x003a
        L_0x0043:
            r1 = move-exception
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcen.zzyc():java.lang.String");
    }

    public final boolean zzyd() {
        return zzb("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzye() {
        int delete;
        zzjC();
        zzkD();
        if (zzyk()) {
            long j = zzwG().zzbrn.get();
            long elapsedRealtime = zzkq().elapsedRealtime();
            if (Math.abs(elapsedRealtime - j) > zzcem.zzxH()) {
                zzwG().zzbrn.set(elapsedRealtime);
                zzjC();
                zzkD();
                if (zzyk() && (delete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzkq().currentTimeMillis()), String.valueOf(zzcem.zzxG())})) > 0) {
                    zzwF().zzyD().zzj("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                }
            }
        }
    }

    @WorkerThread
    public final long zzyf() {
        return zza("select max(bundle_end_timestamp) from queue", (String[]) null, 0);
    }

    @WorkerThread
    public final long zzyg() {
        return zza("select max(timestamp) from raw_events", (String[]) null, 0);
    }

    public final boolean zzyh() {
        return zzb("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean zzyi() {
        return zzb("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    public final long zzyj() {
        long j = -1;
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", (String[]) null);
            if (cursor.moveToFirst()) {
                j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
            } else if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteException e) {
            zzwF().zzyx().zzj("Error querying raw events", e);
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return j;
    }
}
