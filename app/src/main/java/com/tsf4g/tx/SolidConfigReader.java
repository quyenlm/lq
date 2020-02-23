package com.tsf4g.tx;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SolidConfigReader {
    private boolean m_bInited = false;
    private boolean m_bLoaded = false;
    private Context m_context;
    private String m_filename = "";
    private List<String> m_listLinedAsset = new ArrayList();

    private String getValueForKey(String str, String str2, String str3) {
        int i = 0;
        while (i < this.m_listLinedAsset.size()) {
            try {
                if (this.m_listLinedAsset.get(i).startsWith("[") && this.m_listLinedAsset.get(i).endsWith("]") && this.m_listLinedAsset.get(i).replace("[", "").replace("]", "").equals(str)) {
                    while (true) {
                        i++;
                        if (i >= this.m_listLinedAsset.size()) {
                            continue;
                            break;
                        } else if (this.m_listLinedAsset.get(i).startsWith("[") && this.m_listLinedAsset.get(i).endsWith("]")) {
                            return str3;
                        } else {
                            String[] split = this.m_listLinedAsset.get(i).split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                            if (2 == split.length && split[0].equals(str2)) {
                                return split[1];
                            }
                        }
                    }
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return str3;
            }
        }
        return str3;
    }

    private boolean parseAsset(Context context, String str) {
        String readAssetFile = readAssetFile(context, str);
        if (readAssetFile.equals("")) {
            this.m_listLinedAsset.clear();
            return false;
        }
        String[] split = readAssetFile.replace("\r\n", "\n").replace("\t", "").replace(" ", "").split("\n");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("") && !split[i].startsWith(";")) {
                this.m_listLinedAsset.add(split[i]);
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x008e A[SYNTHETIC, Splitter:B:39:0x008e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String readAssetFile(android.content.Context r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r1 = ""
            r2.<init>(r1)
            r1 = 0
            android.content.res.Resources r3 = r8.getResources()     // Catch:{ Exception -> 0x009b }
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch:{ Exception -> 0x009b }
            if (r3 != 0) goto L_0x0020
            java.lang.String r0 = ""
            if (r1 == 0) goto L_0x001a
            r1.close()     // Catch:{ Exception -> 0x001b }
        L_0x001a:
            return r0
        L_0x001b:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x001a
        L_0x0020:
            java.lang.String r4 = ""
            java.lang.String[] r4 = r3.list(r4)     // Catch:{ Exception -> 0x009b }
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch:{ Exception -> 0x009b }
            java.lang.String r5 = r7.m_filename     // Catch:{ Exception -> 0x009b }
            boolean r4 = r4.contains(r5)     // Catch:{ Exception -> 0x009b }
            if (r4 != 0) goto L_0x005f
            java.lang.String r0 = ""
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009b }
            r3.<init>()     // Catch:{ Exception -> 0x009b }
            java.lang.String r4 = "SolidConfigReader file:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x009b }
            java.lang.String r4 = r7.m_filename     // Catch:{ Exception -> 0x009b }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x009b }
            java.lang.String r4 = " not exist, please check"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x009b }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x009b }
            com.tsf4g.tx.TXLog.w(r0, r3)     // Catch:{ Exception -> 0x009b }
            java.lang.String r0 = ""
            if (r1 == 0) goto L_0x001a
            r1.close()     // Catch:{ Exception -> 0x005a }
            goto L_0x001a
        L_0x005a:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x001a
        L_0x005f:
            java.io.InputStream r1 = r3.open(r9)     // Catch:{ Exception -> 0x009b }
            if (r1 != 0) goto L_0x0072
            java.lang.String r0 = ""
            if (r1 == 0) goto L_0x001a
            r1.close()     // Catch:{ Exception -> 0x006d }
            goto L_0x001a
        L_0x006d:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x001a
        L_0x0072:
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x009b }
        L_0x0076:
            int r4 = r1.read(r3)     // Catch:{ Exception -> 0x009b }
            if (r4 <= 0) goto L_0x008c
            r5 = 64
            if (r0 >= r5) goto L_0x008c
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x009b }
            r6 = 0
            r5.<init>(r3, r6, r4)     // Catch:{ Exception -> 0x009b }
            r2.append(r5)     // Catch:{ Exception -> 0x009b }
            int r0 = r0 + 1
            goto L_0x0076
        L_0x008c:
            if (r1 == 0) goto L_0x0091
            r1.close()     // Catch:{ Exception -> 0x0096 }
        L_0x0091:
            java.lang.String r0 = r2.toString()
            goto L_0x001a
        L_0x0096:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0091
        L_0x009b:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x00aa }
            if (r1 == 0) goto L_0x0091
            r1.close()     // Catch:{ Exception -> 0x00a5 }
            goto L_0x0091
        L_0x00a5:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0091
        L_0x00aa:
            r0 = move-exception
            if (r1 == 0) goto L_0x00b0
            r1.close()     // Catch:{ Exception -> 0x00b1 }
        L_0x00b0:
            throw r0
        L_0x00b1:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00b0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tsf4g.tx.SolidConfigReader.readAssetFile(android.content.Context, java.lang.String):java.lang.String");
    }

    public void Dump() {
        if (!this.m_bLoaded) {
            TXLog.i("", "SolidConfig is Empty");
            return;
        }
        TXLog.i("", "SolidConfig FileName:" + this.m_filename);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.m_listLinedAsset.size()) {
                TXLog.i("", "SolidConfig Line" + i2 + ":" + this.m_listLinedAsset.get(i2));
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public void GetAllKeys(String str, List<String> list) {
        ApplicationInfo applicationInfo;
        if (this.m_context == null) {
            Log.e("SolidConfigReader", "SolidConfigReader not init!");
        } else if (str != null && list != null) {
            try {
                PackageManager packageManager = this.m_context.getPackageManager();
                if (packageManager != null && (applicationInfo = packageManager.getApplicationInfo(this.m_context.getPackageName(), 128)) != null && applicationInfo.metaData != null) {
                    for (String str2 : applicationInfo.metaData.keySet()) {
                        int length = str.length();
                        if (str2.length() > length && str2.substring(0, length).equals(str)) {
                            list.add(str2.substring(length + 1));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean GetBool(String str, String str2, boolean z) {
        if (!this.m_bLoaded) {
            return z;
        }
        try {
            String valueForKey = getValueForKey(str, str2, "defBoolean");
            if (valueForKey.toLowerCase(Locale.ENGLISH).equals("true")) {
                return true;
            }
            if (valueForKey.toLowerCase(Locale.ENGLISH).equals("false")) {
                return false;
            }
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }

    public int GetInt(String str, String str2, int i) {
        if (!this.m_bLoaded) {
            return i;
        }
        try {
            return Integer.parseInt(getValueForKey(str, str2, String.valueOf(i)));
        } catch (Exception e) {
            e.printStackTrace();
            return i;
        }
    }

    public String GetMetaString(Context context, String str, String str2, String str3) {
        String str4 = "";
        if (str3 != null) {
            str4 = str3;
        }
        if (str == null || !str.equals("Application")) {
            return str4;
        }
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(str2);
        } catch (Exception e) {
            e.printStackTrace();
            return str4;
        }
    }

    public String GetString(String str, String str2, String str3) {
        if (!this.m_bLoaded) {
            return str3;
        }
        try {
            return getValueForKey(str, str2, str3);
        } catch (Exception e) {
            e.printStackTrace();
            return str3;
        }
    }

    public void Init(Context context, String str) {
        if (!this.m_bInited) {
            this.m_filename = str;
            this.m_bLoaded = parseAsset(context, this.m_filename);
            this.m_bInited = true;
            this.m_context = context;
            return;
        }
        TXLog.i("", "SolidConfigReader already inited");
    }

    public boolean IsContainKey(String str, String str2) {
        ApplicationInfo applicationInfo;
        if (this.m_context == null) {
            Log.e("SolidConfigReader", "SolidConfigReader not init!");
            return false;
        } else if (str == null || str2 == null) {
            return false;
        } else {
            try {
                PackageManager packageManager = this.m_context.getPackageManager();
                if (!(packageManager == null || (applicationInfo = packageManager.getApplicationInfo(this.m_context.getPackageName(), 128)) == null || applicationInfo.metaData == null)) {
                    for (String str3 : applicationInfo.metaData.keySet()) {
                        int length = str.length();
                        if (str3.length() > length && str3.substring(0, length).equals(str) && str2.equals(str3.substring(length + 1))) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public void UnInit() {
        this.m_listLinedAsset.clear();
        this.m_bInited = false;
        this.m_bLoaded = false;
        this.m_filename = "";
    }
}
