package com.tencent.midas.oversea.network.http;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.tp.a.h;

public class APIPDatabase {
    public static final String DB_IPH5TABLE = "TencentUnipayIPH5Table";
    public static final String DB_IPTABLE = "TencentUnipayIPTable";
    Context a;
    private APIPDBHelper b;
    private SQLiteDatabase c;

    public class APIPDBHelper extends SQLiteOpenHelper {
        public APIPDBHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
            super(context, str, cursorFactory, i);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            APLog.i("APIPDatabase", "onCreate");
            try {
                boolean unused = APIPDatabase.this.a(sQLiteDatabase, APIPDatabase.DB_IPTABLE);
                boolean unused2 = APIPDatabase.this.a(sQLiteDatabase, APIPDatabase.DB_IPH5TABLE);
            } catch (Exception e) {
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            APLog.i("APIPDatabase", "onUpgrade");
        }
    }

    public APIPDatabase(Context context) {
        this.a = context;
        b();
    }

    private void a() {
        try {
            if (this.b == null) {
                b();
            }
            if (this.c != null && !this.c.isOpen()) {
                this.c = this.b.getWritableDatabase();
            }
        } catch (Exception e) {
            APLog.i("APIPDatabase checkDBOpen", e.toString());
        }
    }

    /* access modifiers changed from: private */
    public boolean a(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + str + " ( " + APBaseHttpParam.IP_ACCESS + " VARCHAR(64), " + "accessTimes" + " INTEGER, " + "seqFailTimes" + " INTEGER, " + "succTimes" + " INTEGER, " + "failTimes" + " INTEGER, " + "ansTims" + " INTEGER,  " + "ipEnv" + " VARCHAR(12), " + "province" + " VARCHAR(64), " + "city" + " VARCHAR(64)" + h.b);
            return true;
        } catch (SQLException e) {
            Log.w("APIPDatabase", e.toString());
            return false;
        }
    }

    private void b() {
        try {
            if (this.b == null) {
                this.b = new APIPDBHelper(this.a, APNetCfg.getIDC() + "_" + APNetCfg.getEnv() + "_" + "TencentUnipayIPList.db", (SQLiteDatabase.CursorFactory) null, 1);
                if (this.c == null || (this.c != null && !this.c.isOpen())) {
                    this.c = this.b.getWritableDatabase();
                }
            }
        } catch (Exception e) {
            APLog.i("APIPDatabase openDB", e.toString());
        }
    }

    public void clearAll(String str) {
        try {
            a();
            this.c.delete(str, (String) null, (String[]) null);
        } catch (SQLiteException e) {
            APLog.w("deleteAccount", String.valueOf(e));
        }
    }

    public void closeDB() {
        try {
            if (this.b != null) {
                this.b.close();
                this.c.close();
            }
        } catch (Exception e) {
            APLog.i("APIPDatabase closeDB", e.toString());
        }
    }

    public void deleteIP(String str, String str2) {
        String str3 = "delete from " + str2 + " where ip = '" + str + "'";
        try {
            a();
            this.c.execSQL(str3);
        } catch (SQLiteException e) {
            APLog.w("deleteAccount", e.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0059 A[SYNTHETIC, Splitter:B:27:0x0059] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getCount(java.lang.String r11, java.lang.String r12) {
        /*
            r10 = this;
            r8 = 0
            r9 = 0
            r10.a()
            android.database.sqlite.SQLiteDatabase r0 = r10.c
            if (r0 != 0) goto L_0x000a
        L_0x0009:
            return r8
        L_0x000a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "ipEnv='"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r11)
            java.lang.String r1 = "'"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r3 = r0.toString()
            android.database.sqlite.SQLiteDatabase r0 = r10.c     // Catch:{ Exception -> 0x0040, all -> 0x0055 }
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r1 = r12
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0040, all -> 0x0055 }
            int r0 = r1.getCount()     // Catch:{ Exception -> 0x0063 }
            if (r0 <= 0) goto L_0x0067
            int r0 = r1.getCount()     // Catch:{ Exception -> 0x0063 }
        L_0x0039:
            if (r1 == 0) goto L_0x003e
            r1.close()     // Catch:{ Exception -> 0x005d }
        L_0x003e:
            r8 = r0
            goto L_0x0009
        L_0x0040:
            r0 = move-exception
            r1 = r9
        L_0x0042:
            java.lang.String r2 = "APIPDatabase"
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0061 }
            com.tencent.midas.oversea.comm.APLog.w(r2, r0)     // Catch:{ all -> 0x0061 }
            if (r1 == 0) goto L_0x0065
            r1.close()     // Catch:{ Exception -> 0x0052 }
            r0 = r8
            goto L_0x003e
        L_0x0052:
            r0 = move-exception
            r0 = r8
            goto L_0x003e
        L_0x0055:
            r0 = move-exception
            r1 = r9
        L_0x0057:
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ Exception -> 0x005f }
        L_0x005c:
            throw r0
        L_0x005d:
            r1 = move-exception
            goto L_0x003e
        L_0x005f:
            r1 = move-exception
            goto L_0x005c
        L_0x0061:
            r0 = move-exception
            goto L_0x0057
        L_0x0063:
            r0 = move-exception
            goto L_0x0042
        L_0x0065:
            r0 = r8
            goto L_0x003e
        L_0x0067:
            r0 = r8
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.network.http.APIPDatabase.getCount(java.lang.String, java.lang.String):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00aa A[SYNTHETIC, Splitter:B:28:0x00aa] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getIPStateMap(java.util.HashMap<java.lang.String, com.tencent.midas.oversea.network.http.APIPState> r10, java.lang.String r11, java.lang.String r12) {
        /*
            r9 = this;
            r8 = 0
            r9.a()     // Catch:{ Exception -> 0x0093, all -> 0x00a6 }
            android.database.sqlite.SQLiteDatabase r0 = r9.c     // Catch:{ Exception -> 0x0093, all -> 0x00a6 }
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r1 = r12
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0093, all -> 0x00a6 }
            int r0 = r1.getCount()     // Catch:{ Exception -> 0x00b4 }
            if (r0 <= 0) goto L_0x008d
            int r2 = r1.getCount()     // Catch:{ Exception -> 0x00b4 }
            r1.moveToFirst()     // Catch:{ Exception -> 0x00b4 }
            r0 = 0
        L_0x001f:
            if (r0 >= r2) goto L_0x008d
            com.tencent.midas.oversea.network.http.APIPState r3 = new com.tencent.midas.oversea.network.http.APIPState     // Catch:{ Exception -> 0x00b4 }
            r3.<init>()     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = "ip"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = r1.getString(r4)     // Catch:{ Exception -> 0x00b4 }
            r3.ip = r4     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = "accessTimes"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ Exception -> 0x00b4 }
            int r4 = r1.getInt(r4)     // Catch:{ Exception -> 0x00b4 }
            r3.accessTimes = r4     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = "seqFailTimes"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ Exception -> 0x00b4 }
            int r4 = r1.getInt(r4)     // Catch:{ Exception -> 0x00b4 }
            r3.seqFailTimes = r4     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = "succTimes"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ Exception -> 0x00b4 }
            int r4 = r1.getInt(r4)     // Catch:{ Exception -> 0x00b4 }
            r3.succTimes = r4     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = "failTimes"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ Exception -> 0x00b4 }
            int r4 = r1.getInt(r4)     // Catch:{ Exception -> 0x00b4 }
            r3.failTimes = r4     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = "ansTims"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ Exception -> 0x00b4 }
            int r4 = r1.getInt(r4)     // Catch:{ Exception -> 0x00b4 }
            r3.ansTims = r4     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = "ipEnv"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = r1.getString(r4)     // Catch:{ Exception -> 0x00b4 }
            r3.ipEnv = r4     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r4 = r3.ipEnv     // Catch:{ Exception -> 0x00b4 }
            boolean r4 = r4.equals(r11)     // Catch:{ Exception -> 0x00b4 }
            if (r4 == 0) goto L_0x0087
            java.lang.String r4 = r3.ip     // Catch:{ Exception -> 0x00b4 }
            r10.put(r4, r3)     // Catch:{ Exception -> 0x00b4 }
        L_0x0087:
            r1.moveToNext()     // Catch:{ Exception -> 0x00b4 }
            int r0 = r0 + 1
            goto L_0x001f
        L_0x008d:
            if (r1 == 0) goto L_0x0092
            r1.close()     // Catch:{ Exception -> 0x00ae }
        L_0x0092:
            return
        L_0x0093:
            r0 = move-exception
            r1 = r8
        L_0x0095:
            java.lang.String r2 = "APIPDatabase"
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00b2 }
            com.tencent.midas.oversea.comm.APLog.w(r2, r0)     // Catch:{ all -> 0x00b2 }
            if (r1 == 0) goto L_0x0092
            r1.close()     // Catch:{ Exception -> 0x00a4 }
            goto L_0x0092
        L_0x00a4:
            r0 = move-exception
            goto L_0x0092
        L_0x00a6:
            r0 = move-exception
            r1 = r8
        L_0x00a8:
            if (r1 == 0) goto L_0x00ad
            r1.close()     // Catch:{ Exception -> 0x00b0 }
        L_0x00ad:
            throw r0
        L_0x00ae:
            r0 = move-exception
            goto L_0x0092
        L_0x00b0:
            r1 = move-exception
            goto L_0x00ad
        L_0x00b2:
            r0 = move-exception
            goto L_0x00a8
        L_0x00b4:
            r0 = move-exception
            goto L_0x0095
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.network.http.APIPDatabase.getIPStateMap(java.util.HashMap, java.lang.String, java.lang.String):void");
    }

    public void insertIP(APIPState aPIPState, String str) {
        if (!isIPExist(aPIPState.ip, str)) {
            synchronized (this) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(APBaseHttpParam.IP_ACCESS, aPIPState.ip);
                contentValues.put("accessTimes", Integer.valueOf(aPIPState.accessTimes));
                contentValues.put("seqFailTimes", Integer.valueOf(aPIPState.seqFailTimes));
                contentValues.put("succTimes", Integer.valueOf(aPIPState.succTimes));
                contentValues.put("failTimes", Integer.valueOf(aPIPState.failTimes));
                contentValues.put("ansTims", Integer.valueOf(aPIPState.ansTims));
                contentValues.put("ipEnv", aPIPState.ipEnv);
                contentValues.put("province", aPIPState.province);
                contentValues.put("city", aPIPState.city);
                try {
                    a();
                    this.c.insert(str, (String) null, contentValues);
                } catch (SQLException e) {
                    APLog.w("APIPDatabase", e.toString());
                }
            }
            return;
        }
        return;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0054 A[SYNTHETIC, Splitter:B:26:0x0054] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isIPExist(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            r8 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "ip = '"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            java.lang.String r1 = "'"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r3 = r0.toString()
            r9.a()     // Catch:{ Exception -> 0x003d, all -> 0x0050 }
            android.database.sqlite.SQLiteDatabase r0 = r9.c     // Catch:{ Exception -> 0x003d, all -> 0x0050 }
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r1 = r11
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x003d, all -> 0x0050 }
            int r0 = r1.getCount()     // Catch:{ Exception -> 0x0060 }
            if (r0 <= 0) goto L_0x0036
            r0 = 1
            if (r1 == 0) goto L_0x0035
            r1.close()     // Catch:{ Exception -> 0x0058 }
        L_0x0035:
            return r0
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.close()     // Catch:{ Exception -> 0x005a }
        L_0x003b:
            r0 = 0
            goto L_0x0035
        L_0x003d:
            r0 = move-exception
            r1 = r8
        L_0x003f:
            java.lang.String r2 = "APIPDatabase"
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x005e }
            com.tencent.midas.oversea.comm.APLog.w(r2, r0)     // Catch:{ all -> 0x005e }
            if (r1 == 0) goto L_0x003b
            r1.close()     // Catch:{ Exception -> 0x004e }
            goto L_0x003b
        L_0x004e:
            r0 = move-exception
            goto L_0x003b
        L_0x0050:
            r0 = move-exception
            r1 = r8
        L_0x0052:
            if (r1 == 0) goto L_0x0057
            r1.close()     // Catch:{ Exception -> 0x005c }
        L_0x0057:
            throw r0
        L_0x0058:
            r1 = move-exception
            goto L_0x0035
        L_0x005a:
            r0 = move-exception
            goto L_0x003b
        L_0x005c:
            r1 = move-exception
            goto L_0x0057
        L_0x005e:
            r0 = move-exception
            goto L_0x0052
        L_0x0060:
            r0 = move-exception
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.network.http.APIPDatabase.isIPExist(java.lang.String, java.lang.String):boolean");
    }

    public void updateIP(APIPState aPIPState, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("accessTimes", Integer.valueOf(aPIPState.accessTimes));
        contentValues.put("seqFailTimes", Integer.valueOf(aPIPState.seqFailTimes));
        contentValues.put("succTimes", Integer.valueOf(aPIPState.succTimes));
        contentValues.put("failTimes", Integer.valueOf(aPIPState.failTimes));
        contentValues.put("ansTims", Integer.valueOf(aPIPState.ansTims));
        contentValues.put("ipEnv", aPIPState.ipEnv);
        contentValues.put("province", aPIPState.province);
        contentValues.put("city", aPIPState.city);
        try {
            a();
            this.c.update(str, contentValues, "ip=?", new String[]{aPIPState.ip});
        } catch (SQLException e) {
            APLog.w("APIPDatabase", e.toString());
        }
    }
}
