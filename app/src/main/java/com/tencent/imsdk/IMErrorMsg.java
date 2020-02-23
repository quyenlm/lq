package com.tencent.imsdk;

import android.content.Context;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

public class IMErrorMsg {
    private static String currentLanguage = null;
    private static Context mContext;
    private static HashMap<String, String> messageMap = new HashMap<>();

    public static void initialize(Context context) {
        mContext = context;
    }

    public static String getLanguage() {
        String lang = Locale.getDefault().getLanguage();
        return lang != null ? lang : "en";
    }

    public static void setLanguage(String language) {
        currentLanguage = language;
        IMLogger.d("IMErrorMsg currentLanguage is : " + currentLanguage);
    }

    protected static InputStream getFileStream(String file) {
        if (mContext == null) {
            return null;
        }
        try {
            return mContext.getAssets().open(file);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e2) {
            return null;
        }
    }

    protected static InputStream getFileStream() {
        String curLang = currentLanguage;
        if (curLang == null) {
            curLang = getLanguage();
        }
        InputStream inputStream = null;
        String[] dirs = {"IMSDK", "RetMsg"};
        String[] lans = {curLang.toUpperCase(), curLang.toLowerCase(), ""};
        for (int i = 0; i < dirs.length; i++) {
            for (int j = 0; j < lans.length; j++) {
                inputStream = getFileStream(dirs[i] + Constants.URL_PATH_DELIMITER + "IMSDKRetMsg" + ((lans[j] == null || lans[j].length() <= 0) ? "" : "_" + lans[j]) + ".json");
                if (inputStream != null) {
                    InputStream inputStream2 = inputStream;
                    return inputStream;
                }
            }
        }
        InputStream inputStream3 = inputStream;
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x007e A[SYNTHETIC, Splitter:B:28:0x007e] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0083 A[SYNTHETIC, Splitter:B:31:0x0083] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0088 A[SYNTHETIC, Splitter:B:34:0x0088] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0093 A[SYNTHETIC, Splitter:B:39:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x015d A[SYNTHETIC, Splitter:B:71:0x015d] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0162 A[SYNTHETIC, Splitter:B:74:0x0162] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0167 A[SYNTHETIC, Splitter:B:77:0x0167] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getMessageByCode(int r18) {
        /*
            android.content.Context r15 = mContext
            if (r15 != 0) goto L_0x000c
            java.lang.String r15 = "IMErrorMsg mContext is null, please check init first"
            com.tencent.imsdk.tool.etc.IMLogger.w(r15)
            java.lang.String r12 = ""
        L_0x000b:
            return r12
        L_0x000c:
            java.lang.String r12 = ""
            java.util.HashMap<java.lang.String, java.lang.String> r15 = messageMap
            if (r15 == 0) goto L_0x004c
            java.util.HashMap<java.lang.String, java.lang.String> r15 = messageMap
            boolean r15 = r15.isEmpty()
            if (r15 != 0) goto L_0x004c
            java.util.HashMap<java.lang.String, java.lang.String> r15 = messageMap
            java.lang.String r16 = java.lang.String.valueOf(r18)
            boolean r15 = r15.containsKey(r16)
            if (r15 == 0) goto L_0x0033
            java.util.HashMap<java.lang.String, java.lang.String> r15 = messageMap
            java.lang.String r16 = java.lang.String.valueOf(r18)
            java.lang.Object r12 = r15.get(r16)
            java.lang.String r12 = (java.lang.String) r12
            goto L_0x000b
        L_0x0033:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "IMErrorMsg message not contains code : "
            java.lang.StringBuilder r15 = r15.append(r16)
            r0 = r18
            java.lang.StringBuilder r15 = r15.append(r0)
            java.lang.String r15 = r15.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r15)
            goto L_0x000b
        L_0x004c:
            java.lang.String r9 = ""
            r4 = 0
            r1 = 0
            r5 = 0
            java.io.InputStream r4 = getFileStream()     // Catch:{ IOException -> 0x01ca }
            if (r4 == 0) goto L_0x00d8
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x01ca }
            java.lang.String r15 = "UTF-8"
            r6.<init>(r4, r15)     // Catch:{ IOException -> 0x01ca }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x01cd, all -> 0x01c3 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x01cd, all -> 0x01c3 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            r13.<init>()     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
        L_0x0068:
            java.lang.String r11 = r2.readLine()     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            if (r11 == 0) goto L_0x00c1
            r13.append(r11)     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            goto L_0x0068
        L_0x0072:
            r3 = move-exception
            r5 = r6
            r1 = r2
        L_0x0075:
            java.lang.String r15 = r3.getMessage()     // Catch:{ all -> 0x015a }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r15)     // Catch:{ all -> 0x015a }
            if (r1 == 0) goto L_0x0081
            r1.close()     // Catch:{ Exception -> 0x0120 }
        L_0x0081:
            if (r5 == 0) goto L_0x0086
            r5.close()     // Catch:{ Exception -> 0x01be }
        L_0x0086:
            if (r4 == 0) goto L_0x008b
            r4.close()     // Catch:{ Exception -> 0x013d }
        L_0x008b:
            if (r9 == 0) goto L_0x01b4
            int r15 = r9.length()
            if (r15 <= 0) goto L_0x01b4
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01aa }
            r8.<init>(r9)     // Catch:{ JSONException -> 0x01aa }
            java.util.Iterator r7 = r8.keys()     // Catch:{ JSONException -> 0x01aa }
        L_0x009c:
            boolean r15 = r7.hasNext()     // Catch:{ JSONException -> 0x01aa }
            if (r15 == 0) goto L_0x000b
            java.lang.Object r10 = r7.next()     // Catch:{ JSONException -> 0x01aa }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ JSONException -> 0x01aa }
            java.lang.String r14 = r8.getString(r10)     // Catch:{ JSONException -> 0x01aa }
            java.util.HashMap<java.lang.String, java.lang.String> r15 = messageMap     // Catch:{ JSONException -> 0x01aa }
            if (r15 == 0) goto L_0x01a3
            java.util.HashMap<java.lang.String, java.lang.String> r15 = messageMap     // Catch:{ JSONException -> 0x01aa }
            r15.put(r10, r14)     // Catch:{ JSONException -> 0x01aa }
        L_0x00b5:
            java.lang.String r15 = java.lang.String.valueOf(r18)     // Catch:{ JSONException -> 0x01aa }
            boolean r15 = r10.equals(r15)     // Catch:{ JSONException -> 0x01aa }
            if (r15 == 0) goto L_0x009c
            r12 = r14
            goto L_0x009c
        L_0x00c1:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            r15.<init>()     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            java.lang.StringBuilder r15 = r15.append(r9)     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            java.lang.String r16 = r13.toString()     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            java.lang.String r9 = r15.toString()     // Catch:{ IOException -> 0x0072, all -> 0x01c6 }
            r5 = r6
            r1 = r2
        L_0x00d8:
            if (r1 == 0) goto L_0x00dd
            r1.close()     // Catch:{ Exception -> 0x0104 }
        L_0x00dd:
            if (r5 == 0) goto L_0x00e2
            r5.close()     // Catch:{ Exception -> 0x01bb }
        L_0x00e2:
            if (r4 == 0) goto L_0x008b
            r4.close()     // Catch:{ Exception -> 0x00e8 }
            goto L_0x008b
        L_0x00e8:
            r3 = move-exception
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "close stream failed : "
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r16 = r3.getMessage()
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r15 = r15.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r15)
            goto L_0x008b
        L_0x0104:
            r3 = move-exception
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "close buffer failed : "
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r16 = r3.getMessage()
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r15 = r15.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r15)
            goto L_0x00dd
        L_0x0120:
            r3 = move-exception
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "close buffer failed : "
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r16 = r3.getMessage()
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r15 = r15.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r15)
            goto L_0x0081
        L_0x013d:
            r3 = move-exception
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "close stream failed : "
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r16 = r3.getMessage()
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r15 = r15.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r15)
            goto L_0x008b
        L_0x015a:
            r15 = move-exception
        L_0x015b:
            if (r1 == 0) goto L_0x0160
            r1.close()     // Catch:{ Exception -> 0x016b }
        L_0x0160:
            if (r5 == 0) goto L_0x0165
            r5.close()     // Catch:{ Exception -> 0x01c1 }
        L_0x0165:
            if (r4 == 0) goto L_0x016a
            r4.close()     // Catch:{ Exception -> 0x0187 }
        L_0x016a:
            throw r15
        L_0x016b:
            r3 = move-exception
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "close buffer failed : "
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r17 = r3.getMessage()
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r16 = r16.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r16)
            goto L_0x0160
        L_0x0187:
            r3 = move-exception
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "close stream failed : "
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r17 = r3.getMessage()
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r16 = r16.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r16)
            goto L_0x016a
        L_0x01a3:
            java.lang.String r15 = "messageMap is null or empty , plz check !"
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r15)     // Catch:{ JSONException -> 0x01aa }
            goto L_0x00b5
        L_0x01aa:
            r3 = move-exception
            java.lang.String r15 = r3.getMessage()
            com.tencent.imsdk.tool.etc.IMLogger.w(r15)
            goto L_0x000b
        L_0x01b4:
            java.lang.String r15 = "IMErrorMsg jsonStr is null or empty"
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r15)
            goto L_0x000b
        L_0x01bb:
            r15 = move-exception
            goto L_0x00e2
        L_0x01be:
            r15 = move-exception
            goto L_0x0086
        L_0x01c1:
            r16 = move-exception
            goto L_0x0165
        L_0x01c3:
            r15 = move-exception
            r5 = r6
            goto L_0x015b
        L_0x01c6:
            r15 = move-exception
            r5 = r6
            r1 = r2
            goto L_0x015b
        L_0x01ca:
            r3 = move-exception
            goto L_0x0075
        L_0x01cd:
            r3 = move-exception
            r5 = r6
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.IMErrorMsg.getMessageByCode(int):java.lang.String");
    }
}
