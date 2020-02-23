package com.tencent.msdk.stat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ar extends SQLiteOpenHelper {
    /* access modifiers changed from: private */
    public String a = "";
    private Context b = null;

    public ar(Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 3);
        this.a = str;
        this.b = context.getApplicationContext();
        if (StatConfig.isDebugEnable()) {
            aj.h.i("SQLiteOpenHelper " + this.a);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x006d A[SYNTHETIC, Splitter:B:26:0x006d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.database.sqlite.SQLiteDatabase r10) {
        /*
            r9 = this;
            r8 = 0
            java.lang.String r1 = "user"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r10
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0051, all -> 0x0069 }
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch:{ Throwable -> 0x007c }
            r0.<init>()     // Catch:{ Throwable -> 0x007c }
            boolean r2 = r1.moveToNext()     // Catch:{ Throwable -> 0x007c }
            if (r2 == 0) goto L_0x0033
            r2 = 0
            java.lang.String r8 = r1.getString(r2)     // Catch:{ Throwable -> 0x007c }
            r2 = 1
            r1.getInt(r2)     // Catch:{ Throwable -> 0x007c }
            r2 = 2
            r1.getString(r2)     // Catch:{ Throwable -> 0x007c }
            r2 = 3
            r1.getLong(r2)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r2 = com.tencent.msdk.stat.common.p.b((java.lang.String) r8)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r3 = "uid"
            r0.put(r3, r2)     // Catch:{ Throwable -> 0x007c }
        L_0x0033:
            if (r8 == 0) goto L_0x0042
            java.lang.String r2 = "user"
            java.lang.String r3 = "uid=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x007c }
            r5 = 0
            r4[r5] = r8     // Catch:{ Throwable -> 0x007c }
            r10.update(r2, r0, r3, r4)     // Catch:{ Throwable -> 0x007c }
        L_0x0042:
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch:{ Throwable -> 0x0048 }
        L_0x0047:
            return
        L_0x0048:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = com.tencent.msdk.stat.aj.h
            r1.e((java.lang.Throwable) r0)
            goto L_0x0047
        L_0x0051:
            r0 = move-exception
            r1 = r8
        L_0x0053:
            com.tencent.msdk.stat.common.StatLogger r2 = com.tencent.msdk.stat.aj.h     // Catch:{ all -> 0x007a }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x007a }
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch:{ Throwable -> 0x0060 }
            goto L_0x0047
        L_0x0060:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = com.tencent.msdk.stat.aj.h
            r1.e((java.lang.Throwable) r0)
            goto L_0x0047
        L_0x0069:
            r0 = move-exception
            r1 = r8
        L_0x006b:
            if (r1 == 0) goto L_0x0070
            r1.close()     // Catch:{ Throwable -> 0x0071 }
        L_0x0070:
            throw r0
        L_0x0071:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = com.tencent.msdk.stat.aj.h
            r2.e((java.lang.Throwable) r1)
            goto L_0x0070
        L_0x007a:
            r0 = move-exception
            goto L_0x006b
        L_0x007c:
            r0 = move-exception
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.ar.a(android.database.sqlite.SQLiteDatabase):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x007c A[SYNTHETIC, Splitter:B:22:0x007c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(android.database.sqlite.SQLiteDatabase r11) {
        /*
            r10 = this;
            r8 = 0
            java.lang.String r1 = "events"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r11
            android.database.Cursor r7 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x00a7, all -> 0x00a1 }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r0.<init>()     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
        L_0x0013:
            boolean r1 = r7.moveToNext()     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            if (r1 == 0) goto L_0x0045
            r1 = 0
            long r2 = r7.getLong(r1)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r1 = 1
            java.lang.String r4 = r7.getString(r1)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r1 = 2
            int r5 = r7.getInt(r1)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r1 = 3
            int r6 = r7.getInt(r1)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            com.tencent.msdk.stat.as r1 = new com.tencent.msdk.stat.as     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r1.<init>(r2, r4, r5, r6)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r0.add(r1)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            goto L_0x0013
        L_0x0036:
            r0 = move-exception
            r1 = r7
        L_0x0038:
            com.tencent.msdk.stat.common.StatLogger r2 = com.tencent.msdk.stat.aj.h     // Catch:{ all -> 0x00a4 }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00a4 }
            if (r1 == 0) goto L_0x0044
            r1.close()     // Catch:{ Throwable -> 0x008f }
        L_0x0044:
            return
        L_0x0045:
            android.content.ContentValues r1 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r1.<init>()     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            java.util.Iterator r2 = r0.iterator()     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
        L_0x004e:
            boolean r0 = r2.hasNext()     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            if (r0 == 0) goto L_0x0080
            java.lang.Object r0 = r2.next()     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            com.tencent.msdk.stat.as r0 = (com.tencent.msdk.stat.as) r0     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            java.lang.String r3 = "content"
            java.lang.String r4 = r0.b     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            java.lang.String r4 = com.tencent.msdk.stat.common.p.b((java.lang.String) r4)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r1.put(r3, r4)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            java.lang.String r3 = "events"
            java.lang.String r4 = "event_id=?"
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r6 = 0
            long r8 = r0.a     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            java.lang.String r0 = java.lang.Long.toString(r8)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r5[r6] = r0     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            r11.update(r3, r1, r4, r5)     // Catch:{ Throwable -> 0x0036, all -> 0x0079 }
            goto L_0x004e
        L_0x0079:
            r0 = move-exception
        L_0x007a:
            if (r7 == 0) goto L_0x007f
            r7.close()     // Catch:{ Throwable -> 0x0098 }
        L_0x007f:
            throw r0
        L_0x0080:
            if (r7 == 0) goto L_0x0044
            r7.close()     // Catch:{ Throwable -> 0x0086 }
            goto L_0x0044
        L_0x0086:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = com.tencent.msdk.stat.aj.h
            r1.e((java.lang.Throwable) r0)
            goto L_0x0044
        L_0x008f:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = com.tencent.msdk.stat.aj.h
            r1.e((java.lang.Throwable) r0)
            goto L_0x0044
        L_0x0098:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = com.tencent.msdk.stat.aj.h
            r2.e((java.lang.Throwable) r1)
            goto L_0x007f
        L_0x00a1:
            r0 = move-exception
            r7 = r8
            goto L_0x007a
        L_0x00a4:
            r0 = move-exception
            r7 = r1
            goto L_0x007a
        L_0x00a7:
            r0 = move-exception
            r1 = r8
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.ar.b(android.database.sqlite.SQLiteDatabase):void");
    }

    public boolean a() {
        if (StatConfig.isDebugEnable()) {
            aj.h.w("delete " + this.a);
        }
        return this.b.deleteDatabase(this.a);
    }

    public synchronized void close() {
        super.close();
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists events(event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, content TEXT, status INTEGER, send_count INTEGER, timestamp LONG)");
        sQLiteDatabase.execSQL("create table if not exists user(uid TEXT PRIMARY KEY, user_type INTEGER, app_ver TEXT, ts INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists config(type INTEGER PRIMARY KEY NOT NULL, content TEXT, md5sum TEXT, version INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        aj.h.debug("upgrade DB from oldVersion " + i + " to newVersion " + i2);
        if (i == 1) {
            sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            a(sQLiteDatabase);
            b(sQLiteDatabase);
        }
        if (i == 2) {
            a(sQLiteDatabase);
            b(sQLiteDatabase);
        }
    }
}
