package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import java.util.HashSet;

final class zzee extends SQLiteOpenHelper {
    private /* synthetic */ zzec zzbFA;
    private boolean zzbFB;
    private long zzbFC = 0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzee(zzec zzec, Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        this.zzbFA = zzec;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x004d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean zza(java.lang.String r10, android.database.sqlite.SQLiteDatabase r11) {
        /*
            r8 = 0
            r9 = 0
            java.lang.String r1 = "SQLITE_MASTER"
            r0 = 1
            java.lang.String[] r2 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0026, all -> 0x0051 }
            r0 = 0
            java.lang.String r3 = "name"
            r2[r0] = r3     // Catch:{ SQLiteException -> 0x0026, all -> 0x0051 }
            java.lang.String r3 = "name=?"
            r0 = 1
            java.lang.String[] r4 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0026, all -> 0x0051 }
            r0 = 0
            r4[r0] = r10     // Catch:{ SQLiteException -> 0x0026, all -> 0x0051 }
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r11
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x0026, all -> 0x0051 }
            boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x0058, all -> 0x0054 }
            if (r1 == 0) goto L_0x0025
            r1.close()
        L_0x0025:
            return r0
        L_0x0026:
            r0 = move-exception
            r0 = r9
        L_0x0028:
            java.lang.String r2 = "Error querying for table "
            java.lang.String r1 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x0048 }
            int r3 = r1.length()     // Catch:{ all -> 0x0048 }
            if (r3 == 0) goto L_0x0042
            java.lang.String r1 = r2.concat(r1)     // Catch:{ all -> 0x0048 }
        L_0x0038:
            com.google.android.gms.tagmanager.zzdj.zzaT(r1)     // Catch:{ all -> 0x0048 }
            if (r0 == 0) goto L_0x0040
            r0.close()
        L_0x0040:
            r0 = r8
            goto L_0x0025
        L_0x0042:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x0048 }
            r1.<init>(r2)     // Catch:{ all -> 0x0048 }
            goto L_0x0038
        L_0x0048:
            r1 = move-exception
            r2 = r1
            r9 = r0
        L_0x004b:
            if (r9 == 0) goto L_0x0050
            r9.close()
        L_0x0050:
            throw r2
        L_0x0051:
            r0 = move-exception
            r2 = r0
            goto L_0x004b
        L_0x0054:
            r0 = move-exception
            r2 = r0
            r9 = r1
            goto L_0x004b
        L_0x0058:
            r0 = move-exception
            r0 = r1
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzee.zza(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (!this.zzbFB || this.zzbFC + 3600000 <= this.zzbFA.zzvw.currentTimeMillis()) {
            SQLiteDatabase sQLiteDatabase = null;
            this.zzbFB = true;
            this.zzbFC = this.zzbFA.zzvw.currentTimeMillis();
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                this.zzbFA.mContext.getDatabasePath(this.zzbFA.zzbFx).delete();
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
        zzbs.zzfk(sQLiteDatabase.getPath());
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    /* JADX INFO: finally extract failed */
    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (Build.VERSION.SDK_INT < 15) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", (String[]) null);
            try {
                rawQuery.moveToFirst();
            } finally {
                rawQuery.close();
            }
        }
        if (!zza("gtm_hits", sQLiteDatabase)) {
            sQLiteDatabase.execSQL(zzec.zzagp);
            return;
        }
        Cursor rawQuery2 = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", (String[]) null);
        HashSet hashSet = new HashSet();
        try {
            String[] columnNames = rawQuery2.getColumnNames();
            for (String add : columnNames) {
                hashSet.add(add);
            }
            rawQuery2.close();
            if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_time") || !hashSet.remove("hit_first_send_time")) {
                throw new SQLiteException("Database column missing");
            } else if (!hashSet.isEmpty()) {
                throw new SQLiteException("Database has extra columns");
            }
        } catch (Throwable th) {
            rawQuery2.close();
            throw th;
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
