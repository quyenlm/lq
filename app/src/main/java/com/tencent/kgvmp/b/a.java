package com.tencent.kgvmp.b;

import android.content.Context;
import com.appsflyer.share.Constants;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import com.tencent.kgvmp.c.b;
import com.tencent.kgvmp.e.c;
import com.tencent.kgvmp.e.h;
import com.tencent.kgvmp.report.d;
import com.tencent.kgvmp.report.e;
import com.tencent.kgvmp.report.f;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class a {
    /* access modifiers changed from: private */
    public static final String a = com.tencent.kgvmp.a.a.a;
    private Context b;
    private String c;
    private b d;

    public a(Context context) {
        this.b = context;
    }

    private void a(e eVar, boolean z, boolean z2, boolean z3, boolean z4) {
        if (!f.d()) {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceConfigReport: report func is not open.");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(GGLiveConstants.PARAM.RESULT, eVar.getStringCode());
        hashMap.put("available", "true");
        hashMap.put("prop_match", String.valueOf(z));
        hashMap.put("package_match", String.valueOf(z2));
        hashMap.put("cpu_match", String.valueOf(z3));
        hashMap.put("gpu_match", String.valueOf(z4));
        for (String next : this.d.d.a.keySet()) {
            hashMap.put(next, h.a(next));
        }
        for (String next2 : this.d.d.b.keySet()) {
            hashMap.put(next2, h.a(next2));
        }
        Iterator<String> it = this.d.e.b.iterator();
        while (it.hasNext()) {
            String next3 = it.next();
            hashMap.put(next3, String.valueOf(c.a(next3, this.b)));
        }
        Iterator<String> it2 = this.d.e.a.iterator();
        while (it2.hasNext()) {
            String next4 = it2.next();
            hashMap.put(next4, String.valueOf(c.a(next4, this.b)));
        }
        hashMap.put("cpu", String.valueOf(c.h()));
        hashMap.put("gpu", String.valueOf(d.h()));
        hashMap.put("root", String.valueOf(c.i()));
        f.b((HashMap<String, String>) hashMap);
    }

    private e g() {
        com.tencent.kgvmp.e.f.a(a, "device_check: read device config start. ");
        String str = f.f() + Constants.URL_PATH_DELIMITER + com.tencent.kgvmp.a.a.d;
        File file = new File(str);
        if (!file.exists()) {
            com.tencent.kgvmp.e.f.a(a, "device_check: can not find local cloud file. ");
            return e.CANNT_FIND_LOCAL_CONFIG;
        } else if (!file.canRead()) {
            com.tencent.kgvmp.e.f.a(a, "device_check: can not read local cloud file. ");
            return e.CANNT_READ_LOCAL_CONFIG;
        } else {
            String str2 = null;
            try {
                str2 = com.tencent.kgvmp.e.d.a(str);
            } catch (Exception e) {
                e.printStackTrace();
                com.tencent.kgvmp.e.f.a(a, "device_check: read local config exception.");
            }
            if (str2 == null) {
                com.tencent.kgvmp.e.f.a(a, "device_check: get local config content null.");
                return e.READ_LOCAL_CONFIG_EXCEPTION;
            } else if (str2.isEmpty()) {
                com.tencent.kgvmp.e.f.a(a, "device_check: local config content is empty.");
                return e.GET_LOCAL_CONFIG_EMPTY;
            } else {
                this.c = str2;
                com.tencent.kgvmp.e.f.a(a, "device_check:readConfigFile: success. content: " + str2);
                return e.VMP_SUCCESS;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean h() {
        /*
            r7 = this;
            r2 = 0
            com.tencent.kgvmp.c.b r0 = r7.d
            com.tencent.kgvmp.c.b$c r0 = r0.d
            java.util.HashMap<java.lang.String, java.lang.String[]> r0 = r0.a
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r3 = r0.iterator()
        L_0x000f:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x006c
            java.lang.Object r0 = r3.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r0.getKey()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r0 = r0.getValue()
            java.lang.String[] r0 = (java.lang.String[]) r0
            java.lang.String r4 = com.tencent.kgvmp.e.h.a(r1)
            if (r4 != 0) goto L_0x0046
            java.lang.String r0 = a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "DeviceChecker:isPropValueMatched: get prop exception. prop: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.kgvmp.e.f.a(r0, r1)
            goto L_0x000f
        L_0x0046:
            boolean r0 = com.tencent.kgvmp.e.a.a((java.lang.String[]) r0, (java.lang.String) r4)
            if (r0 != 0) goto L_0x000f
            java.lang.String r0 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "DeviceChecker:checkDeviceMatchConfig: not matched. propKey: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r3 = " not match. "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.tencent.kgvmp.e.f.a(r0, r1)
            r0 = r2
        L_0x006b:
            return r0
        L_0x006c:
            com.tencent.kgvmp.c.b r0 = r7.d
            com.tencent.kgvmp.c.b$c r0 = r0.d
            java.util.HashMap<java.lang.String, java.lang.String[]> r0 = r0.b
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r1 = r0.iterator()
        L_0x007a:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x00e0
            java.lang.Object r0 = r1.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r0 = r0.getKey()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r3 = com.tencent.kgvmp.e.h.a(r0)
            java.lang.String r4 = a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "DeviceChecker:isPropValueMatched: exist prop: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " = "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = java.lang.String.valueOf(r3)
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.tencent.kgvmp.e.f.a(r4, r5)
            if (r3 == 0) goto L_0x00c0
            java.lang.String r4 = "null"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x007a
        L_0x00c0:
            java.lang.String r1 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "DeviceChecker:checkDeviceMatchConfig: propKey: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r3 = " not exsit."
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r0 = r0.toString()
            com.tencent.kgvmp.e.f.a(r1, r0)
            r0 = r2
            goto L_0x006b
        L_0x00e0:
            java.lang.String r0 = a
            java.lang.String r1 = "DeviceChecker:checkDeviceMatchConfig: prop all matched. "
            com.tencent.kgvmp.e.f.a(r0, r1)
            r0 = 1
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.kgvmp.b.a.h():boolean");
    }

    private boolean i() {
        String h = c.h();
        String[] strArr = this.d.f.a;
        if (h == null) {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:isCPUMatched: cpu hardware is null. result is matched.");
            return true;
        } else if (strArr.length <= 0) {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:isCPUMatched: cpu arr lenth <= 0. result is matched.");
            return true;
        } else {
            for (String contains : strArr) {
                if (h.contains(contains)) {
                    com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: cpu: " + h + " is matched. ");
                    return true;
                }
            }
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:isCPUMatched: cpu is not matched.");
            return false;
        }
    }

    private boolean j() {
        String h = d.h();
        String[] strArr = this.d.f.b;
        if (h == null) {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:isGPUMatched: gpu is null. result is matched.");
            return true;
        } else if (strArr.length <= 0) {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:isGPUMatched: gpu arr lenth <= 0. result is matched.");
            return true;
        } else {
            for (String contains : strArr) {
                if (contains.contains(h)) {
                    com.tencent.kgvmp.e.f.a(a, "DeviceChecker:isGPUMatched: gpu: " + h + " is matched.");
                    return true;
                }
            }
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: gpu is not matched.");
            return false;
        }
    }

    private boolean k() {
        ArrayList<String> arrayList = this.d.e.b;
        if (arrayList.size() > 0) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (c.a(next, this.b)) {
                    com.tencent.kgvmp.e.f.a(a, "DeviceChecker:isAppPackageInstalled: package exist, package: " + next);
                    return true;
                }
            }
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:isAppPackageInstalled: not found package in exsit. ");
        }
        ArrayList<String> arrayList2 = this.d.e.a;
        if (arrayList2.size() > 0) {
            Iterator<String> it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                if (!c.a(next2, this.b)) {
                    com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: package: " + next2 + " not exsit.");
                    return false;
                }
            }
        }
        return true;
    }

    public int a() {
        if (e.VMP_SUCCESS != b()) {
            com.tencent.kgvmp.e.f.a(a, "device_check: load device config failed. ");
            return 0;
        }
        e c2 = c();
        if (c2 == e.DEVICE_CONFIG_GET_EXCEPTION || c2 == e.DEVICE_CONFIG_AVAILABLE_IS_FALSE || c2 == e.DEVICE_IS_REAL) {
            return 0;
        }
        if (c2 == e.DEVICE_IS_NOT_REAL) {
            return 1;
        }
        return c2 == e.DEVICE_IS_UNKOWN ? 1 : 0;
    }

    public void a(HashMap<String, String> hashMap) {
        if (this.c != null || (g() == e.VMP_SUCCESS && this.c != null)) {
            try {
                JSONArray jSONArray = new JSONObject(this.c).getJSONArray("all_report");
                for (int i = 0; i < jSONArray.length(); i++) {
                    hashMap.put(jSONArray.getString(i), h.a(jSONArray.getString(i)));
                }
            } catch (Exception e) {
                com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceDataReport: check all report exception.");
            }
        } else {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceDataReport: get device config exception.");
        }
    }

    public e b() {
        e g = g();
        if (e.VMP_SUCCESS != g) {
            return g;
        }
        b bVar = new b();
        try {
            if (!bVar.a(new JSONObject(this.c))) {
                com.tencent.kgvmp.e.f.a(a, "device_check: parse json's value exception.");
                return e.PARSE_JSON_VALUE_EXCEPTION;
            }
            com.tencent.kgvmp.e.f.b(a, "device_check: parse json success.");
            this.d = bVar;
            return e.VMP_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            com.tencent.kgvmp.e.f.a(a, "device_check: file content parse exception.");
            return e.PARSE_JSON_CONFIG_EXCEPTION;
        }
    }

    public e c() {
        e eVar;
        if (this.d == null) {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: globalconfig is null.");
            return e.DEVICE_CONFIG_GET_EXCEPTION;
        } else if (!this.d.a) {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: available is false.");
            e();
            return e.DEVICE_CONFIG_AVAILABLE_IS_FALSE;
        } else {
            boolean i = c.i();
            boolean h = h();
            boolean i2 = i();
            boolean j = j();
            boolean k = k();
            if (!h && i) {
                com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: prop info is not matched and device is rooted.");
                eVar = e.DEVICE_IS_UNKOWN;
            } else if (k || !i || !h || !i2 || !j) {
                eVar = (!h || !i2 || !j || !k) ? e.DEVICE_IS_NOT_REAL : e.DEVICE_IS_REAL;
            } else {
                com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: package is not installed and device is rooted.");
                eVar = e.DEVICE_IS_UNKOWN;
            }
            a(eVar, h, k, i2, j);
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: device info match result, prop: " + String.valueOf(h));
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: device info match result, cpu: " + String.valueOf(i2));
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: device info match result, gpu: " + String.valueOf(j));
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceMatchConfig: device info match result, package: " + String.valueOf(k));
            return eVar;
        }
    }

    public void d() {
        new Thread(new Runnable() {
            public void run() {
                com.tencent.kgvmp.e.f.b(a.a, "device_check config thread start.");
                long currentTimeMillis = System.currentTimeMillis();
                HashMap hashMap = new HashMap();
                hashMap.put(IMFriendInfoExViber.TAG, "1");
                try {
                    String str = f.f() + Constants.URL_PATH_DELIMITER + com.tencent.kgvmp.a.a.d;
                    boolean c = com.tencent.kgvmp.e.d.c(f.f() + Constants.URL_PATH_DELIMITER + com.tencent.kgvmp.a.a.f);
                    if (!c) {
                        com.tencent.kgvmp.e.f.a(a.a, "device_check: not find debug file. ");
                    }
                    String str2 = com.tencent.kgvmp.a.a.k + d.g() + "&brand=" + c.b().toLowerCase() + "&model=" + c.a().toLowerCase();
                    if (c) {
                        str2 = com.tencent.kgvmp.a.a.j + d.g() + "&brand=" + c.b().toLowerCase() + "&model=" + c.a().toLowerCase();
                        hashMap.put("debug", "1");
                    }
                    com.tencent.kgvmp.e.f.b(a.a, "device_check: device config url: " + str2);
                    com.tencent.kgvmp.e.e eVar = new com.tencent.kgvmp.e.e();
                    eVar.a(5000);
                    eVar.b(5000);
                    String a2 = eVar.a(str2);
                    if (a2 == null) {
                        com.tencent.kgvmp.e.f.a(a.a, "device_check: download cloud config is null. ");
                        hashMap.put(GGLiveConstants.PARAM.RESULT, e.DOWNLOAD_CONFIG_EXCEPTION.getStringCode());
                    } else if (a2.isEmpty()) {
                        com.tencent.kgvmp.e.f.a(a.a, "device_check: download cloud config is empty. ");
                        hashMap.put(GGLiveConstants.PARAM.RESULT, e.DOWNLOAD_CONFIG_EMPTY.getStringCode());
                    } else {
                        com.tencent.kgvmp.e.f.b(a.a, "device_check: config content: " + a2);
                        try {
                            new JSONObject(a2);
                        } catch (Exception e) {
                            e.printStackTrace();
                            com.tencent.kgvmp.e.f.a(a.a, "device_check:  content is not json format.");
                            hashMap.put(GGLiveConstants.PARAM.RESULT, e.DOWNLOAD_CONFIG_CONTENT_NOT_JSON.getStringCode());
                        }
                        try {
                            com.tencent.kgvmp.e.d.a(str, a2);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            hashMap.put(GGLiveConstants.PARAM.RESULT, e.DOWNLOAD_CONFIG_SAVE_FILE_EXCEPTION.getStringCode());
                            com.tencent.kgvmp.e.f.a(a.a, "Perf_init: save config exception. ");
                        }
                    }
                    try {
                        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                        hashMap.put("run_time", String.valueOf(currentTimeMillis2));
                        com.tencent.kgvmp.e.f.b(a.a, "device_check: device config download thread run time: " + currentTimeMillis2);
                        f.d((HashMap<String, String>) hashMap);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        com.tencent.kgvmp.e.f.a(a.a, "device_check: report download result exception. ");
                    }
                } catch (Throwable th) {
                    try {
                        long currentTimeMillis3 = System.currentTimeMillis() - currentTimeMillis;
                        hashMap.put("run_time", String.valueOf(currentTimeMillis3));
                        com.tencent.kgvmp.e.f.b(a.a, "device_check: device config download thread run time: " + currentTimeMillis3);
                        f.d((HashMap<String, String>) hashMap);
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        com.tencent.kgvmp.e.f.a(a.a, "device_check: report download result exception. ");
                    }
                    throw th;
                }
            }
        }).start();
    }

    public void e() {
        if (!f.d()) {
            com.tencent.kgvmp.e.f.a(a, "DeviceChecker:checkDeviceConfigReport: report func is not open.");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(GGLiveConstants.PARAM.RESULT, e.DEVICE_CONFIG_AVAILABLE_IS_FALSE.getStringCode());
        hashMap.put("available", "false");
        for (String next : this.d.d.a.keySet()) {
            hashMap.put(next, h.a(next));
        }
        for (String next2 : this.d.d.b.keySet()) {
            hashMap.put(next2, h.a(next2));
        }
        Iterator<String> it = this.d.e.b.iterator();
        while (it.hasNext()) {
            String next3 = it.next();
            hashMap.put(next3, String.valueOf(c.a(next3, this.b)));
        }
        Iterator<String> it2 = this.d.e.a.iterator();
        while (it2.hasNext()) {
            String next4 = it2.next();
            hashMap.put(next4, String.valueOf(c.a(next4, this.b)));
        }
        hashMap.put("cpu", String.valueOf(c.h()));
        hashMap.put("gpu", String.valueOf(d.h()));
        hashMap.put("root", String.valueOf(c.i()));
        f.b((HashMap<String, String>) hashMap);
    }
}
