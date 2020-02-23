package com.tencent.tdm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tencent.tdm.defines.DBEvent;
import com.tencent.tdm.system.TXLog;
import java.util.ArrayList;

public class TXDataBase {
    private static final String DBName = "tdm.db";
    private static final String DBTable = "DataMaster";
    private static final int DBVersion = 1;
    private static final String KEY_Data = "Data";
    private static final String KEY_EventID = "EventId";
    private static final String KEY_Len = "Len";
    private static final String KEY_SrcID = "SrcId";
    private static final String PKEY_ID = "Id";
    private static final String TAG = "TXDataBase";
    private static TXDataBase instance = null;
    private static Context mContext = null;
    private static DBHelper mDBHelper = null;
    private boolean mInitialized = false;

    private native void TXDataBaseInit();

    private TXDataBase() {
    }

    public static TXDataBase getInstance() {
        if (instance == null) {
            instance = new TXDataBase();
        }
        return instance;
    }

    public void initialize(Context context) {
        if (!this.mInitialized) {
            mContext = context;
            TXDataBaseInit();
            this.mInitialized = true;
        }
    }

    public boolean createDB() {
        if (mContext == null) {
            TXLog.e(TAG, "createDB, mContext is null");
            return false;
        } else if (mDBHelper != null) {
            return true;
        } else {
            mDBHelper = new DBHelper(mContext, DBName, DBTable, 1);
            return true;
        }
    }

    public int getCount() {
        int size = -1;
        if (mDBHelper == null) {
            TXLog.e(TAG, "mDBHelper is null, please call createDB first");
            return -1;
        }
        SQLiteDatabase db = null;
        try {
            db = mDBHelper.getReadableDatabase();
        } catch (Exception e) {
            TXLog.e(TAG, "getCount, GetDB Exception");
            TXLog.i(TAG, "Exception Track: " + e);
        }
        if (db == null) {
            TXLog.e(TAG, "getCount, db is null");
            return -1;
        }
        try {
            Cursor cursor = db.rawQuery("select Id from DataMaster", (String[]) null);
            size = cursor.getCount();
            cursor.close();
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
        } catch (Exception e2) {
            TXLog.e(TAG, "getCount, Cursor Exception");
            TXLog.i(TAG, "Exception Track: " + e2);
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
        } catch (Throwable th) {
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
            throw th;
        }
        int i = size;
        return size;
    }

    public boolean insertEvent(long id, int eventId, int srcId, byte[] data, int dataLen) {
        if (mDBHelper == null) {
            TXLog.e(TAG, "mDBHelper is null, please call createDB first");
            return false;
        }
        SQLiteDatabase db = null;
        try {
            db = mDBHelper.getWritableDatabase();
        } catch (Exception e) {
            TXLog.e(TAG, "insertEvent, GetDB Exception");
            TXLog.i(TAG, "Exception Track: " + e);
        }
        if (db == null) {
            TXLog.e(TAG, "insertEvent, db is null");
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("Id", Long.valueOf(id));
        values.put(KEY_EventID, Integer.valueOf(eventId));
        values.put(KEY_SrcID, Integer.valueOf(srcId));
        values.put(KEY_Len, Integer.valueOf(dataLen));
        values.put(KEY_Data, data);
        try {
            db.insert(DBTable, (String) null, values);
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
        } catch (Exception e2) {
            TXLog.e(TAG, "insertEvent, insert Exception");
            TXLog.i(TAG, "Exception Track: " + e2);
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
        } catch (Throwable th) {
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
            throw th;
        }
        return true;
    }

    public boolean deleteTopEvent() {
        boolean ret = false;
        if (mDBHelper == null) {
            TXLog.e(TAG, "mDBHelper is null, please call createDB first");
            return false;
        }
        SQLiteDatabase db = null;
        try {
            db = mDBHelper.getWritableDatabase();
        } catch (Exception e) {
            TXLog.e(TAG, "deleteTopEvent, GetDB Exception");
            TXLog.i(TAG, "Exception Track: " + e);
        }
        if (db == null) {
            TXLog.e(TAG, "deleteTopEvent, db is null");
            return false;
        }
        try {
            Cursor cursor = db.rawQuery("select Id from DataMaster order by Id DESC limit 1", (String[]) null);
            if (cursor.moveToFirst()) {
                db.delete(DBTable, "Id=?", new String[]{String.valueOf(cursor.getLong(0))});
                ret = true;
            }
            cursor.close();
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
        } catch (Exception e2) {
            TXLog.e(TAG, "deleteTopEvent, Exception");
            TXLog.i(TAG, "Exception Track: " + e2);
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
        } catch (Throwable th) {
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
            throw th;
        }
        boolean z = ret;
        return ret;
    }

    public boolean deleteEvent(long IdValue) {
        if (mDBHelper == null) {
            TXLog.e(TAG, "mDBHelper is null, please call createDB first");
            return false;
        }
        SQLiteDatabase db = null;
        try {
            db = mDBHelper.getWritableDatabase();
        } catch (Exception e) {
            TXLog.e(TAG, "deleteEvent, GetDB Exception");
            TXLog.i(TAG, "Exception Track: " + e);
        }
        if (db == null) {
            TXLog.e(TAG, "deleteEvent, db is null");
            return false;
        }
        try {
            db.delete(DBTable, "Id=?", new String[]{String.valueOf(IdValue)});
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
        } catch (Exception e2) {
            TXLog.e(TAG, "deleteEvent, delete Exception");
            TXLog.i(TAG, "Exception Track: " + e2);
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
        } catch (Throwable th) {
            if (db != null) {
                TXLog.i(TAG, "db.close");
                db.close();
            }
            throw th;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.tdm.defines.DBEvent getTopEvent() {
        /*
            r14 = this;
            r10 = 0
            com.tencent.tdm.database.DBHelper r2 = mDBHelper
            if (r2 != 0) goto L_0x000f
            java.lang.String r2 = "TXDataBase"
            java.lang.String r3 = "mDBHelper is null, please call createDB first"
            com.tencent.tdm.system.TXLog.e(r2, r3)
            r11 = r10
            r12 = r10
        L_0x000e:
            return r12
        L_0x000f:
            r8 = 0
            com.tencent.tdm.database.DBHelper r2 = mDBHelper     // Catch:{ Exception -> 0x0022 }
            android.database.sqlite.SQLiteDatabase r8 = r2.getReadableDatabase()     // Catch:{ Exception -> 0x0022 }
        L_0x0016:
            if (r8 != 0) goto L_0x0043
            java.lang.String r2 = "TXDataBase"
            java.lang.String r3 = "getTopEvent, db is null"
            com.tencent.tdm.system.TXLog.e(r2, r3)
            r11 = r10
            r12 = r10
            goto L_0x000e
        L_0x0022:
            r9 = move-exception
            java.lang.String r2 = "TXDataBase"
            java.lang.String r3 = "getTopEvent, GetDB Exception"
            com.tencent.tdm.system.TXLog.e(r2, r3)
            java.lang.String r2 = "TXDataBase"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Exception Track: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r9)
            java.lang.String r3 = r3.toString()
            com.tencent.tdm.system.TXLog.i(r2, r3)
            goto L_0x0016
        L_0x0043:
            java.lang.String r13 = "select * from DataMaster order by Id DESC limit 1"
            r2 = 0
            android.database.Cursor r0 = r8.rawQuery(r13, r2)     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
            boolean r2 = r0.moveToFirst()     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
            if (r2 == 0) goto L_0x00c4
            com.tencent.tdm.defines.DBEvent r1 = new com.tencent.tdm.defines.DBEvent     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
            r2 = 0
            long r2 = r0.getLong(r2)     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
            r4 = 1
            int r4 = r0.getInt(r4)     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
            r5 = 2
            int r5 = r0.getInt(r5)     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
            r6 = 3
            int r6 = r0.getInt(r6)     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
            r7 = 4
            byte[] r7 = r0.getBlob(r7)     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
            r1.<init>(r2, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0081, all -> 0x00b0 }
        L_0x006e:
            r0.close()     // Catch:{ Exception -> 0x00c2 }
            if (r8 == 0) goto L_0x007e
            java.lang.String r2 = "TXDataBase"
            java.lang.String r3 = "db.close"
            com.tencent.tdm.system.TXLog.i(r2, r3)
            r8.close()
            r8 = 0
        L_0x007e:
            r11 = r1
            r12 = r1
            goto L_0x000e
        L_0x0081:
            r9 = move-exception
            r1 = r10
        L_0x0083:
            java.lang.String r2 = "TXDataBase"
            java.lang.String r3 = "getTopEvent, rawQuery Exception"
            com.tencent.tdm.system.TXLog.e(r2, r3)     // Catch:{ all -> 0x00c0 }
            java.lang.String r2 = "TXDataBase"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c0 }
            r3.<init>()     // Catch:{ all -> 0x00c0 }
            java.lang.String r4 = "Exception Track: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00c0 }
            java.lang.StringBuilder r3 = r3.append(r9)     // Catch:{ all -> 0x00c0 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00c0 }
            com.tencent.tdm.system.TXLog.i(r2, r3)     // Catch:{ all -> 0x00c0 }
            if (r8 == 0) goto L_0x007e
            java.lang.String r2 = "TXDataBase"
            java.lang.String r3 = "db.close"
            com.tencent.tdm.system.TXLog.i(r2, r3)
            r8.close()
            r8 = 0
            goto L_0x007e
        L_0x00b0:
            r2 = move-exception
            r1 = r10
        L_0x00b2:
            if (r8 == 0) goto L_0x00bf
            java.lang.String r3 = "TXDataBase"
            java.lang.String r4 = "db.close"
            com.tencent.tdm.system.TXLog.i(r3, r4)
            r8.close()
            r8 = 0
        L_0x00bf:
            throw r2
        L_0x00c0:
            r2 = move-exception
            goto L_0x00b2
        L_0x00c2:
            r9 = move-exception
            goto L_0x0083
        L_0x00c4:
            r1 = r10
            goto L_0x006e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tdm.database.TXDataBase.getTopEvent():com.tencent.tdm.defines.DBEvent");
    }

    public ArrayList<DBEvent> getEvents(int n) {
        int cnt;
        ArrayList<DBEvent> eventData = new ArrayList<>();
        if (mDBHelper == null) {
            TXLog.e(TAG, "mDBHelper is null, please call createDB first");
        } else {
            SQLiteDatabase db = null;
            try {
                db = mDBHelper.getReadableDatabase();
            } catch (Exception e) {
                TXLog.e(TAG, "getEvents, GetDB Exception");
                TXLog.i(TAG, "Exception Track: " + e);
            }
            if (db == null) {
                TXLog.e(TAG, "getEvents, db is null");
            } else {
                try {
                    Cursor cursor = db.rawQuery("select * from DataMaster order by Id DESC limit " + n, (String[]) null);
                    int size = cursor.getCount();
                    if (size <= 0) {
                        TXLog.d(TAG, "getEvents, db is empty");
                    } else {
                        if (n < size) {
                            cnt = n;
                        } else {
                            cnt = size;
                        }
                        if (cursor.moveToFirst()) {
                            for (int i = 0; i < cnt; i++) {
                                eventData.add(new DBEvent(cursor.getLong(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getBlob(4)));
                                if (!cursor.moveToNext()) {
                                    break;
                                }
                            }
                        }
                    }
                    cursor.close();
                    if (db != null) {
                        TXLog.i(TAG, "db.close");
                        db.close();
                    }
                } catch (Exception e2) {
                    TXLog.e(TAG, "getEvents, Cursor Exception");
                    TXLog.i(TAG, "Exception Track: " + e2);
                    if (db != null) {
                        TXLog.i(TAG, "db.close");
                        db.close();
                    }
                } catch (Throwable th) {
                    if (db != null) {
                        TXLog.i(TAG, "db.close");
                        db.close();
                    }
                    throw th;
                }
            }
        }
        return eventData;
    }

    public void closeDB() {
        if (mDBHelper == null) {
            TXLog.w(TAG, "mDBHelper is null!");
            return;
        }
        try {
            mDBHelper.close();
        } catch (Exception e) {
            TXLog.e(TAG, "closeDB, close Exception");
            TXLog.i(TAG, "Exception Track: " + e);
        }
    }
}
