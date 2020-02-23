package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.WorkerThread;
import java.util.Map;

@TargetApi(11)
final class zzcfi extends SQLiteOpenHelper {
    private /* synthetic */ zzcfh zzbqH;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcfi(zzcfh zzcfh, Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        this.zzbqH = zzcfh;
    }

    @WorkerThread
    public final SQLiteDatabase getWritableDatabase() {
        try {
            return super.getWritableDatabase();
        } catch (SQLiteException e) {
            if (Build.VERSION.SDK_INT < 11 || !(e instanceof SQLiteDatabaseLockedException)) {
                this.zzbqH.zzwF().zzyx().log("Opening the local database failed, dropping and recreating it");
                String zzxD = zzcem.zzxD();
                if (!this.zzbqH.getContext().getDatabasePath(zzxD).delete()) {
                    this.zzbqH.zzwF().zzyx().zzj("Failed to delete corrupted local db file", zzxD);
                }
                try {
                    return super.getWritableDatabase();
                } catch (SQLiteException e2) {
                    this.zzbqH.zzwF().zzyx().zzj("Failed to open local database. Events will bypass local storage", e2);
                    return null;
                }
            } else {
                throw e;
            }
        }
    }

    @WorkerThread
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzcen.zza(this.zzbqH.zzwF(), sQLiteDatabase);
    }

    @WorkerThread
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @WorkerThread
    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (Build.VERSION.SDK_INT < 15) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", (String[]) null);
            try {
                rawQuery.moveToFirst();
            } finally {
                rawQuery.close();
            }
        }
        zzcen.zza(this.zzbqH.zzwF(), sQLiteDatabase, "messages", "create table if not exists messages ( type INTEGER NOT NULL, entry BLOB NOT NULL)", "type,entry", (Map<String, String>) null);
    }

    @WorkerThread
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
