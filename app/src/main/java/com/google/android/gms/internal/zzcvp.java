package com.google.android.gms.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

final class zzcvp extends SQLiteOpenHelper {
    private boolean zzbFB;
    private long zzbFC = 0;
    private /* synthetic */ zzcvn zzbIB;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcvp(zzcvn zzcvn, Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        this.zzbIB = zzcvn;
    }

    /* JADX INFO: finally extract failed */
    private static void zza(SQLiteDatabase sQLiteDatabase, String str, List<String> list) {
        Cursor rawQuery = sQLiteDatabase.rawQuery(new StringBuilder(String.valueOf(str).length() + 22).append("SELECT * FROM ").append(str).append(" WHERE 0").toString(), (String[]) null);
        HashSet hashSet = new HashSet();
        try {
            String[] columnNames = rawQuery.getColumnNames();
            for (String add : columnNames) {
                hashSet.add(add);
            }
            rawQuery.close();
            for (String next : list) {
                if (!hashSet.remove(next)) {
                    throw new SQLiteException(String.format("Database column %s missing in table %s.", new Object[]{next, str}));
                }
            }
            if (!hashSet.isEmpty()) {
                throw new SQLiteException(String.format("Database has extra columns in table %s.", new Object[]{str}));
            }
        } catch (Throwable th) {
            rawQuery.close();
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0032  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean zza(java.lang.String r10, android.database.sqlite.SQLiteDatabase r11) {
        /*
            r8 = 0
            r9 = 0
            java.lang.String r1 = "SQLITE_MASTER"
            r0 = 1
            java.lang.String[] r2 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0026, all -> 0x002f }
            r0 = 0
            java.lang.String r3 = "name"
            r2[r0] = r3     // Catch:{ SQLiteException -> 0x0026, all -> 0x002f }
            java.lang.String r3 = "name=?"
            r0 = 1
            java.lang.String[] r4 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0026, all -> 0x002f }
            r0 = 0
            r4[r0] = r10     // Catch:{ SQLiteException -> 0x0026, all -> 0x002f }
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r11
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x0026, all -> 0x002f }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x0039, all -> 0x0036 }
            if (r1 == 0) goto L_0x0025
            r1.close()
        L_0x0025:
            return r0
        L_0x0026:
            r0 = move-exception
            r0 = r9
        L_0x0028:
            if (r0 == 0) goto L_0x002d
            r0.close()
        L_0x002d:
            r0 = r8
            goto L_0x0025
        L_0x002f:
            r0 = move-exception
        L_0x0030:
            if (r9 == 0) goto L_0x0035
            r9.close()
        L_0x0035:
            throw r0
        L_0x0036:
            r0 = move-exception
            r9 = r1
            goto L_0x0030
        L_0x0039:
            r0 = move-exception
            r0 = r1
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcvp.zza(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (!this.zzbFB || this.zzbFC + 3600000 <= this.zzbIB.zzvw.currentTimeMillis()) {
            SQLiteDatabase sQLiteDatabase = null;
            this.zzbFB = true;
            this.zzbFC = this.zzbIB.zzvw.currentTimeMillis();
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                this.zzbIB.mContext.getDatabasePath(this.zzbIB.zzbFx).delete();
            }
            if (sQLiteDatabase == null) {
                sQLiteDatabase = super.getWritableDatabase();
            }
            this.zzbFB = false;
            return sQLiteDatabase;
        }
        throw new SQLiteException("Database creation failed");
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        String path = sQLiteDatabase.getPath();
        if (zzcuv.version() >= 9) {
            File file = new File(path);
            file.setReadable(false, false);
            file.setWritable(false, false);
            file.setReadable(true, true);
            file.setWritable(true, true);
        }
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (Build.VERSION.SDK_INT < 15) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", (String[]) null);
            try {
                rawQuery.moveToFirst();
            } finally {
                rawQuery.close();
            }
        }
        if (!zza("gtm_hit_unique_ids", sQLiteDatabase)) {
            sQLiteDatabase.execSQL(zzcvn.zzbIv);
        } else {
            zza(sQLiteDatabase, "gtm_hit_unique_ids", Arrays.asList(new String[]{"hit_unique_id"}));
        }
        if (!zza("gtm_hits", sQLiteDatabase)) {
            sQLiteDatabase.execSQL(zzcvn.zzagp);
        } else {
            zza(sQLiteDatabase, "gtm_hits", Arrays.asList(new String[]{"hit_id", "hit_url", "hit_time", "hit_first_send_time", "hit_method", "hit_unique_id", "hit_headers", "hit_body"}));
        }
        sQLiteDatabase.execSQL(zzcvn.zzbIw);
        sQLiteDatabase.execSQL(zzcvn.zzbIx);
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
