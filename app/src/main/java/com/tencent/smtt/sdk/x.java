package com.tencent.smtt.sdk;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.k;
import java.io.File;
import java.util.ArrayList;

public class x {
    public static final String a = CookieManager.LOGTAG;
    static File b;

    public static File a(Context context) {
        if (b == null && context != null) {
            b = new File(context.getDir("webview", 0), "Cookies");
        }
        if (b == null) {
            b = new File("/data/data/" + context.getPackageName() + File.separator + "app_webview" + File.separator + "Cookies");
        }
        return b;
    }

    private static String a(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + str, (String[]) null);
        int count = rawQuery.getCount();
        int columnCount = rawQuery.getColumnCount();
        StringBuilder sb = new StringBuilder();
        sb.append("raws:" + count + ",columns:" + columnCount + "\n");
        if (count <= 0 || !rawQuery.moveToFirst()) {
            return sb.toString();
        }
        do {
            sb.append("\n");
            for (int i = 0; i < columnCount; i++) {
                try {
                    sb.append(rawQuery.getString(i)).append(",");
                } catch (Exception e) {
                }
            }
            sb.append("\n");
        } while (rawQuery.moveToNext());
        return sb.toString();
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.util.ArrayList<java.lang.String>, android.database.Cursor] */
    public static ArrayList<String> a(SQLiteDatabase sQLiteDatabase) {
        ? r0 = 0;
        if (sQLiteDatabase == null) {
            return r0;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from sqlite_master where type='table'", (String[]) null);
            if (rawQuery.moveToFirst()) {
                do {
                    String string = rawQuery.getString(1);
                    rawQuery.getString(4);
                    arrayList.add(string);
                    a(sQLiteDatabase, string);
                } while (rawQuery.moveToNext());
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
        } catch (Throwable th) {
            if (r0 != 0) {
                r0.close();
            }
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
            throw th;
        }
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0054, code lost:
        if (r0 == false) goto L_0x0056;
     */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r10, com.tencent.smtt.sdk.CookieManager.a r11, java.lang.String r12, boolean r13, boolean r14) {
        /*
            r1 = 0
            r3 = 0
            r2 = 1
            if (r10 != 0) goto L_0x0006
        L_0x0005:
            return
        L_0x0006:
            com.tencent.smtt.sdk.CookieManager$a r0 = com.tencent.smtt.sdk.CookieManager.a.MODE_KEYS
            if (r11 != r0) goto L_0x0010
            boolean r0 = android.text.TextUtils.isEmpty(r12)
            if (r0 != 0) goto L_0x0005
        L_0x0010:
            java.lang.String r0 = ","
            java.lang.String[] r4 = r12.split(r0)
            if (r4 == 0) goto L_0x0005
            int r0 = r4.length
            if (r0 < r2) goto L_0x0005
            android.database.sqlite.SQLiteDatabase r5 = c(r10)
            if (r5 == 0) goto L_0x0005
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            java.lang.String r0 = "select * from cookies"
            r7 = 0
            android.database.Cursor r1 = r5.rawQuery(r0, r7)     // Catch:{ Throwable -> 0x00f4 }
            int r0 = r1.getCount()     // Catch:{ Throwable -> 0x00f4 }
            if (r0 <= 0) goto L_0x005c
            boolean r0 = r1.moveToFirst()     // Catch:{ Throwable -> 0x00f4 }
            if (r0 == 0) goto L_0x005c
        L_0x0039:
            java.lang.String r0 = "host_key"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r7 = r1.getString(r0)     // Catch:{ Throwable -> 0x00f4 }
            com.tencent.smtt.sdk.CookieManager$a r0 = com.tencent.smtt.sdk.CookieManager.a.MODE_KEYS     // Catch:{ Throwable -> 0x00f4 }
            if (r11 != r0) goto L_0x00a0
            int r8 = r4.length     // Catch:{ Throwable -> 0x00f4 }
            r0 = r3
        L_0x0049:
            if (r0 >= r8) goto L_0x0162
            r9 = r4[r0]     // Catch:{ Throwable -> 0x00f4 }
            boolean r9 = r7.equals(r9)     // Catch:{ Throwable -> 0x00f4 }
            if (r9 == 0) goto L_0x009d
            r0 = r2
        L_0x0054:
            if (r0 != 0) goto L_0x00a0
        L_0x0056:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x00f4 }
            if (r0 != 0) goto L_0x0039
        L_0x005c:
            if (r1 == 0) goto L_0x0061
            r1.close()
        L_0x0061:
            if (r5 == 0) goto L_0x006c
            boolean r0 = r5.isOpen()
            if (r0 == 0) goto L_0x006c
            r5.close()
        L_0x006c:
            boolean r0 = r6.isEmpty()
            if (r0 != 0) goto L_0x0005
            b(r10)
            java.util.Set r0 = r6.entrySet()
            java.util.Iterator r3 = r0.iterator()
        L_0x007d:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x0135
            java.lang.Object r0 = r3.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r0.getKey()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r0 = r0.getValue()
            java.lang.String r0 = (java.lang.String) r0
            com.tencent.smtt.sdk.CookieManager r4 = com.tencent.smtt.sdk.CookieManager.getInstance()
            r4.setCookie((java.lang.String) r1, (java.lang.String) r0, (boolean) r2)
            goto L_0x007d
        L_0x009d:
            int r0 = r0 + 1
            goto L_0x0049
        L_0x00a0:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00f4 }
            r0.<init>()     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r8 = "value"
            int r8 = r1.getColumnIndex(r8)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r8 = r1.getString(r8)     // Catch:{ Throwable -> 0x00f4 }
            r0.append(r8)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r8 = ";"
            java.lang.StringBuilder r8 = r0.append(r8)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r9 = "name"
            int r9 = r1.getColumnIndex(r9)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r9 = r1.getString(r9)     // Catch:{ Throwable -> 0x00f4 }
            r8.append(r9)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r8 = ";"
            java.lang.StringBuilder r8 = r0.append(r8)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r9 = "expires_utc"
            int r9 = r1.getColumnIndex(r9)     // Catch:{ Throwable -> 0x00f4 }
            int r9 = r1.getInt(r9)     // Catch:{ Throwable -> 0x00f4 }
            r8.append(r9)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r8 = ";"
            java.lang.StringBuilder r8 = r0.append(r8)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r9 = "priority"
            int r9 = r1.getColumnIndex(r9)     // Catch:{ Throwable -> 0x00f4 }
            int r9 = r1.getInt(r9)     // Catch:{ Throwable -> 0x00f4 }
            r8.append(r9)     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00f4 }
            r6.put(r7, r0)     // Catch:{ Throwable -> 0x00f4 }
            goto L_0x0056
        L_0x00f4:
            r0 = move-exception
            java.lang.String r3 = a     // Catch:{ all -> 0x0123 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0123 }
            r4.<init>()     // Catch:{ all -> 0x0123 }
            java.lang.String r7 = "getCookieDBVersion exception:"
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ all -> 0x0123 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0123 }
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ all -> 0x0123 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0123 }
            android.util.Log.e(r3, r0)     // Catch:{ all -> 0x0123 }
            if (r1 == 0) goto L_0x0116
            r1.close()
        L_0x0116:
            if (r5 == 0) goto L_0x006c
            boolean r0 = r5.isOpen()
            if (r0 == 0) goto L_0x006c
            r5.close()
            goto L_0x006c
        L_0x0123:
            r0 = move-exception
            if (r1 == 0) goto L_0x0129
            r1.close()
        L_0x0129:
            if (r5 == 0) goto L_0x0134
            boolean r1 = r5.isOpen()
            if (r1 == 0) goto L_0x0134
            r5.close()
        L_0x0134:
            throw r0
        L_0x0135:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            if (r0 < r1) goto L_0x015a
            com.tencent.smtt.sdk.CookieManager r0 = com.tencent.smtt.sdk.CookieManager.getInstance()
            r0.flush()
        L_0x0142:
            if (r13 == 0) goto L_0x0005
            android.database.sqlite.SQLiteDatabase r0 = c(r10)
            a((android.database.sqlite.SQLiteDatabase) r0)
            int r0 = d(r10)
            r1 = -1
            if (r0 == r1) goto L_0x0005
            com.tencent.smtt.sdk.CookieManager.getInstance()
            com.tencent.smtt.sdk.CookieManager.setROMCookieDBVersion(r10, r0)
            goto L_0x0005
        L_0x015a:
            com.tencent.smtt.sdk.CookieSyncManager r0 = com.tencent.smtt.sdk.CookieSyncManager.getInstance()
            r0.sync()
            goto L_0x0142
        L_0x0162:
            r0 = r3
            goto L_0x0054
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.x.a(android.content.Context, com.tencent.smtt.sdk.CookieManager$a, java.lang.String, boolean, boolean):void");
    }

    public static boolean b(Context context) {
        if (context == null) {
            return false;
        }
        k.a(a(context), false);
        return true;
    }

    public static SQLiteDatabase c(Context context) {
        File a2;
        SQLiteDatabase sQLiteDatabase = null;
        if (!(context == null || (a2 = a(context)) == null)) {
            try {
                sQLiteDatabase = SQLiteDatabase.openDatabase(a2.getAbsolutePath(), (SQLiteDatabase.CursorFactory) null, 0);
            } catch (Exception e) {
            }
            if (sQLiteDatabase == null) {
                TbsLog.i(a, "dbPath is not exist!");
            }
        }
        return sQLiteDatabase;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: android.database.Cursor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x007a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int d(android.content.Context r5) {
        /*
            r0 = 0
            r2 = 0
            java.lang.System.currentTimeMillis()
            android.database.sqlite.SQLiteDatabase r3 = c(r5)     // Catch:{ Throwable -> 0x0061, all -> 0x0075 }
            if (r3 != 0) goto L_0x001d
            r0 = -1
            if (r2 == 0) goto L_0x0011
            r2.close()
        L_0x0011:
            if (r3 == 0) goto L_0x001c
            boolean r1 = r3.isOpen()
            if (r1 == 0) goto L_0x001c
            r3.close()
        L_0x001c:
            return r0
        L_0x001d:
            java.lang.String r1 = "select * from meta"
            r4 = 0
            android.database.Cursor r1 = r3.rawQuery(r1, r4)     // Catch:{ Throwable -> 0x008e, all -> 0x0089 }
            int r2 = r1.getCount()     // Catch:{ Throwable -> 0x0091, all -> 0x008c }
            r1.getColumnCount()     // Catch:{ Throwable -> 0x0091, all -> 0x008c }
            if (r2 <= 0) goto L_0x0049
            boolean r2 = r1.moveToFirst()     // Catch:{ Throwable -> 0x0091, all -> 0x008c }
            if (r2 == 0) goto L_0x0049
        L_0x0033:
            r2 = 0
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x0091, all -> 0x008c }
            java.lang.String r4 = "version"
            boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x0091, all -> 0x008c }
            if (r2 == 0) goto L_0x005a
            r2 = 1
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x0091, all -> 0x008c }
            int r0 = java.lang.Integer.parseInt(r2)     // Catch:{ Throwable -> 0x0091, all -> 0x008c }
        L_0x0049:
            if (r1 == 0) goto L_0x004e
            r1.close()
        L_0x004e:
            if (r3 == 0) goto L_0x001c
            boolean r1 = r3.isOpen()
            if (r1 == 0) goto L_0x001c
            r3.close()
            goto L_0x001c
        L_0x005a:
            boolean r2 = r1.moveToNext()     // Catch:{ Throwable -> 0x0091, all -> 0x008c }
            if (r2 != 0) goto L_0x0033
            goto L_0x0049
        L_0x0061:
            r1 = move-exception
            r1 = r2
            r3 = r2
        L_0x0064:
            if (r1 == 0) goto L_0x0069
            r1.close()
        L_0x0069:
            if (r3 == 0) goto L_0x001c
            boolean r1 = r3.isOpen()
            if (r1 == 0) goto L_0x001c
            r3.close()
            goto L_0x001c
        L_0x0075:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x0078:
            if (r1 == 0) goto L_0x007d
            r1.close()
        L_0x007d:
            if (r3 == 0) goto L_0x0088
            boolean r1 = r3.isOpen()
            if (r1 == 0) goto L_0x0088
            r3.close()
        L_0x0088:
            throw r0
        L_0x0089:
            r0 = move-exception
            r1 = r2
            goto L_0x0078
        L_0x008c:
            r0 = move-exception
            goto L_0x0078
        L_0x008e:
            r1 = move-exception
            r1 = r2
            goto L_0x0064
        L_0x0091:
            r2 = move-exception
            goto L_0x0064
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.x.d(android.content.Context):int");
    }
}
