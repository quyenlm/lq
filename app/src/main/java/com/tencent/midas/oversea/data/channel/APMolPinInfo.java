package com.tencent.midas.oversea.data.channel;

import org.json.JSONArray;

public class APMolPinInfo {
    private static String a = "";
    private static CharSequence b = "";

    /* JADX WARNING: Removed duplicated region for block: B:17:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0073 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(org.json.JSONArray r8, java.lang.String r9) {
        /*
            r2 = 0
            java.lang.String r0 = ""
            a = r0
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            r5.<init>()
            int r6 = r8.length()
            r0 = 0
        L_0x000f:
            int r1 = r8.length()
            if (r0 >= r1) goto L_0x007e
            org.json.JSONObject r1 = r8.getJSONObject(r0)     // Catch:{ JSONException -> 0x0076 }
            java.lang.String r3 = "amt"
            java.lang.String r4 = r1.getString(r3)     // Catch:{ JSONException -> 0x0076 }
            org.json.JSONObject r1 = r8.getJSONObject(r0)     // Catch:{ JSONException -> 0x0085 }
            java.lang.String r3 = "currency_type"
            java.lang.String r3 = r1.getString(r3)     // Catch:{ JSONException -> 0x0085 }
            org.json.JSONObject r1 = r8.getJSONObject(r0)     // Catch:{ JSONException -> 0x0088 }
            java.lang.String r7 = "num"
            java.lang.String r1 = r1.getString(r7)     // Catch:{ JSONException -> 0x0088 }
        L_0x0033:
            boolean r7 = android.text.TextUtils.isEmpty(r4)
            if (r7 != 0) goto L_0x0073
            boolean r7 = android.text.TextUtils.isEmpty(r3)
            if (r7 != 0) goto L_0x0073
            boolean r7 = android.text.TextUtils.isEmpty(r1)
            if (r7 != 0) goto L_0x0073
            r5.append(r4)
            java.lang.String r4 = " "
            r5.append(r4)
            r5.append(r3)
            java.lang.String r3 = " "
            r5.append(r3)
            java.lang.String r3 = "="
            r5.append(r3)
            java.lang.String r3 = " "
            r5.append(r3)
            r5.append(r1)
            java.lang.String r1 = " "
            r5.append(r1)
            r5.append(r9)
            int r1 = r6 + -1
            if (r0 == r1) goto L_0x0073
            java.lang.String r1 = "\n\n"
            r5.append(r1)
        L_0x0073:
            int r0 = r0 + 1
            goto L_0x000f
        L_0x0076:
            r1 = move-exception
            r3 = r2
            r4 = r2
        L_0x0079:
            r1.printStackTrace()
            r1 = r2
            goto L_0x0033
        L_0x007e:
            java.lang.String r0 = r5.toString()
            a = r0
            return
        L_0x0085:
            r1 = move-exception
            r3 = r2
            goto L_0x0079
        L_0x0088:
            r1 = move-exception
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.data.channel.APMolPinInfo.a(org.json.JSONArray, java.lang.String):void");
    }

    public static void anaylseMolPinInfo(JSONArray jSONArray, String str) {
        a(jSONArray, str);
        b(jSONArray, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0079  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void b(org.json.JSONArray r11, java.lang.String r12) {
        /*
            r4 = 0
            r2 = 0
            java.lang.String r0 = ""
            b = r0
            java.lang.String r0 = ""
            int r7 = r11.length()
            r1 = r2
        L_0x000d:
            int r3 = r11.length()
            if (r1 >= r3) goto L_0x00af
            org.json.JSONObject r3 = r11.getJSONObject(r1)     // Catch:{ JSONException -> 0x00a7 }
            java.lang.String r5 = "amt"
            java.lang.String r6 = r3.getString(r5)     // Catch:{ JSONException -> 0x00a7 }
            org.json.JSONObject r3 = r11.getJSONObject(r1)     // Catch:{ JSONException -> 0x00b2 }
            java.lang.String r5 = "currency_type"
            java.lang.String r5 = r3.getString(r5)     // Catch:{ JSONException -> 0x00b2 }
            org.json.JSONObject r3 = r11.getJSONObject(r1)     // Catch:{ JSONException -> 0x00b5 }
            java.lang.String r8 = "num"
            java.lang.String r3 = r3.getString(r8)     // Catch:{ JSONException -> 0x00b5 }
        L_0x0031:
            boolean r8 = android.text.TextUtils.isEmpty(r6)
            if (r8 != 0) goto L_0x00a3
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r8 != 0) goto L_0x00a3
            boolean r8 = android.text.TextUtils.isEmpty(r3)
            if (r8 != 0) goto L_0x00a3
            java.lang.StringBuffer r8 = new java.lang.StringBuffer
            r8.<init>()
            int r9 = r6.length()
            int r10 = r5.length()
            r8.append(r6)
            java.lang.String r6 = " "
            r8.append(r6)
            r8.append(r5)
            java.lang.String r5 = "\n"
            r8.append(r5)
            java.lang.String r5 = "="
            r8.append(r5)
            java.lang.String r5 = " "
            r8.append(r5)
            r8.append(r3)
            java.lang.String r3 = " "
            r8.append(r3)
            r8.append(r12)
            int r3 = r7 + -1
            if (r1 == r3) goto L_0x007e
            java.lang.String r3 = "\n\n"
            r8.append(r3)
        L_0x007e:
            android.text.SpannableString r3 = new android.text.SpannableString
            java.lang.String r5 = r8.toString()
            r3.<init>(r5)
            android.text.style.RelativeSizeSpan r5 = new android.text.style.RelativeSizeSpan
            r6 = 1065353216(0x3f800000, float:1.0)
            r5.<init>(r6)
            int r6 = r9 + r10
            int r6 = r6 + 1
            r8 = 33
            r3.setSpan(r5, r2, r6, r8)
            r5 = 2
            java.lang.CharSequence[] r5 = new java.lang.CharSequence[r5]
            r5[r2] = r0
            r0 = 1
            r5[r0] = r3
            java.lang.CharSequence r0 = android.text.TextUtils.concat(r5)
        L_0x00a3:
            int r1 = r1 + 1
            goto L_0x000d
        L_0x00a7:
            r3 = move-exception
            r5 = r4
            r6 = r4
        L_0x00aa:
            r3.printStackTrace()
            r3 = r4
            goto L_0x0031
        L_0x00af:
            b = r0
            return
        L_0x00b2:
            r3 = move-exception
            r5 = r4
            goto L_0x00aa
        L_0x00b5:
            r3 = move-exception
            goto L_0x00aa
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.data.channel.APMolPinInfo.b(org.json.JSONArray, java.lang.String):void");
    }

    public static String getMolPinInfoLand() {
        return a;
    }

    public static CharSequence getMolPinInfoPort() {
        return b;
    }
}
