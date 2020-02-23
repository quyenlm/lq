package com.tencent.imsdk.sns.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMLoginSqlLiteHelper {
    private static final String dbFile = "imsdk.db3";
    private static final String loginTable = "imsdk_login_info";

    public static boolean isTableExists(Context context) {
        boolean z = false;
        SQLiteDatabase db = null;
        try {
            db = context.openOrCreateDatabase(dbFile, 0, (SQLiteDatabase.CursorFactory) null);
            Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'imsdk_login_info'", (String[]) null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.close();
                    z = true;
                    if (db != null) {
                        try {
                            db.close();
                        } catch (Exception e) {
                            IMLogger.e("close iMSDK db error : " + e.getMessage());
                        }
                    }
                    return z;
                }
                cursor.close();
            }
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e2) {
                    IMLogger.e("close iMSDK db error : " + e2.getMessage());
                }
            }
        } catch (Exception e3) {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e4) {
                    IMLogger.e("close iMSDK db error : " + e4.getMessage());
                }
            }
        } catch (Throwable th) {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e5) {
                    IMLogger.e("close iMSDK db error : " + e5.getMessage());
                }
            }
            throw th;
        }
        return z;
    }

    public static void SaveLoginData(Context context, IMLoginResult result, String channelKey) {
        SQLiteDatabase db = null;
        try {
            db = context.openOrCreateDatabase(dbFile, 0, (SQLiteDatabase.CursorFactory) null);
            IMLogger.d("save login data : " + channelKey);
            db.execSQL("create table if not exists imsdk_login_info (channel text primary key, login_json_str text not null)");
            ContentValues replaceVals = new ContentValues();
            replaceVals.put("channel", channelKey);
            replaceVals.put("login_json_str", result.toJSONString());
            IMLogger.d(channelKey + " replace result : " + db.replace(loginTable, (String) null, replaceVals));
        } catch (Exception e) {
            IMLogger.w("record login data error : " + e.getMessage());
        }
        if (db != null) {
            db.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0097 A[SYNTHETIC, Splitter:B:31:0x0097] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0156 A[SYNTHETIC, Splitter:B:55:0x0156] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.imsdk.sns.base.IMLoginResult GetSavedLoginData(android.content.Context r13, java.lang.String r14) {
        /*
            r0 = 0
            r5 = 0
            if (r13 != 0) goto L_0x002d
            java.lang.String r10 = "context is null"
            com.tencent.imsdk.tool.etc.IMLogger.w(r10)     // Catch:{ Exception -> 0x00f3 }
            if (r0 == 0) goto L_0x000e
            r0.close()     // Catch:{ Exception -> 0x0011 }
        L_0x000e:
            r6 = r5
            r7 = r5
        L_0x0010:
            return r7
        L_0x0011:
            r1 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "close iMSDK db error : "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r1.getMessage()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.imsdk.tool.etc.IMLogger.e(r10)
            goto L_0x000e
        L_0x002d:
            java.lang.String r10 = "imsdk.db3"
            r11 = 0
            r12 = 0
            android.database.sqlite.SQLiteDatabase r0 = r13.openOrCreateDatabase(r10, r11, r12)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r9 = "create table if not exists imsdk_login_info (channel text primary key, login_json_str text not null)"
            r0.execSQL(r9)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r10.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r11 = "select login_json_str from imsdk_login_info where channel='"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r11 = "'"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r9 = r10.toString()     // Catch:{ Exception -> 0x00f3 }
            r10 = 0
            android.database.Cursor r8 = r0.rawQuery(r9, r10)     // Catch:{ Exception -> 0x00f3 }
            if (r8 == 0) goto L_0x00dc
            int r10 = r8.getCount()     // Catch:{ Exception -> 0x00f3 }
            if (r10 <= 0) goto L_0x0092
            r8.moveToFirst()     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r10 = "login_json_str"
            int r3 = r8.getColumnIndex(r10)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r4 = r8.getString(r3)     // Catch:{ Exception -> 0x00f3 }
            com.tencent.imsdk.sns.base.IMLoginResult r6 = new com.tencent.imsdk.sns.base.IMLoginResult     // Catch:{ Exception -> 0x0179 }
            r6.<init>((java.lang.String) r4)     // Catch:{ Exception -> 0x0179 }
            if (r6 == 0) goto L_0x0091
            java.lang.String r10 = r6.channelUserId     // Catch:{ JSONException -> 0x009e }
            boolean r10 = com.tencent.imsdk.tool.etc.T.ckIsEmpty(r10)     // Catch:{ JSONException -> 0x009e }
            if (r10 == 0) goto L_0x0091
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x009e }
            r2.<init>(r4)     // Catch:{ JSONException -> 0x009e }
            java.lang.String r10 = "channelUserId"
            boolean r10 = r2.has(r10)     // Catch:{ JSONException -> 0x009e }
            if (r10 == 0) goto L_0x0091
            java.lang.String r10 = "channelUserId"
            java.lang.String r10 = r2.getString(r10)     // Catch:{ JSONException -> 0x009e }
            r6.channelUserId = r10     // Catch:{ JSONException -> 0x009e }
        L_0x0091:
            r5 = r6
        L_0x0092:
            r8.close()     // Catch:{ Exception -> 0x00f3 }
        L_0x0095:
            if (r0 == 0) goto L_0x009a
            r0.close()     // Catch:{ Exception -> 0x0136 }
        L_0x009a:
            r6 = r5
            r7 = r5
            goto L_0x0010
        L_0x009e:
            r1 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ba, all -> 0x0176 }
            r10.<init>()     // Catch:{ Exception -> 0x00ba, all -> 0x0176 }
            java.lang.String r11 = "get channelUserId error : "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00ba, all -> 0x0176 }
            java.lang.String r11 = r1.getMessage()     // Catch:{ Exception -> 0x00ba, all -> 0x0176 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00ba, all -> 0x0176 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00ba, all -> 0x0176 }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r10)     // Catch:{ Exception -> 0x00ba, all -> 0x0176 }
            goto L_0x0091
        L_0x00ba:
            r1 = move-exception
            r5 = r6
        L_0x00bc:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r10.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r11 = " get saved login data failed : "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r11 = r1.getMessage()     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00f3 }
            com.tencent.imsdk.tool.etc.IMLogger.w(r10)     // Catch:{ Exception -> 0x00f3 }
            r5 = 0
            goto L_0x0092
        L_0x00dc:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r10.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r11 = " can not get saved login data ..."
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00f3 }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r10)     // Catch:{ Exception -> 0x00f3 }
            goto L_0x0095
        L_0x00f3:
            r1 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0153 }
            r10.<init>()     // Catch:{ all -> 0x0153 }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ all -> 0x0153 }
            java.lang.String r11 = " get saved login data error : "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x0153 }
            java.lang.String r11 = r1.getMessage()     // Catch:{ all -> 0x0153 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x0153 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0153 }
            com.tencent.imsdk.tool.etc.IMLogger.w(r10)     // Catch:{ all -> 0x0153 }
            r5 = 0
            if (r0 == 0) goto L_0x009a
            r0.close()     // Catch:{ Exception -> 0x0119 }
            goto L_0x009a
        L_0x0119:
            r1 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "close iMSDK db error : "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r1.getMessage()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.imsdk.tool.etc.IMLogger.e(r10)
            goto L_0x009a
        L_0x0136:
            r1 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "close iMSDK db error : "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r1.getMessage()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.imsdk.tool.etc.IMLogger.e(r10)
            goto L_0x009a
        L_0x0153:
            r10 = move-exception
        L_0x0154:
            if (r0 == 0) goto L_0x0159
            r0.close()     // Catch:{ Exception -> 0x015a }
        L_0x0159:
            throw r10
        L_0x015a:
            r1 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "close iMSDK db error : "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = r1.getMessage()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.tencent.imsdk.tool.etc.IMLogger.e(r11)
            goto L_0x0159
        L_0x0176:
            r10 = move-exception
            r5 = r6
            goto L_0x0154
        L_0x0179:
            r1 = move-exception
            goto L_0x00bc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.sns.base.IMLoginSqlLiteHelper.GetSavedLoginData(android.content.Context, java.lang.String):com.tencent.imsdk.sns.base.IMLoginResult");
    }

    public static void CleanSavedLoginData(Context context, String channelKey) {
        SQLiteDatabase db = null;
        try {
            if (isTableExists(context)) {
                db = context.openOrCreateDatabase(dbFile, 0, (SQLiteDatabase.CursorFactory) null);
                db.delete(loginTable, "channel='" + channelKey + "'", (String[]) null);
                IMLogger.d(channelKey + " clean saved login data");
            }
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    IMLogger.e("close iMSDK db error : " + e.getMessage());
                }
            }
        } catch (Exception e2) {
            IMLogger.w(channelKey + " clean saved login data error : " + e2.getMessage());
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e3) {
                    IMLogger.e("close iMSDK db error : " + e3.getMessage());
                }
            }
        } catch (Throwable th) {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e4) {
                    IMLogger.e("close iMSDK db error : " + e4.getMessage());
                }
            }
            throw th;
        }
    }
}
