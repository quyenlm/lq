package com.tencent.kgvmp;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import com.appsflyer.share.Constants;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import com.tencent.kgvmp.a.a;
import com.tencent.kgvmp.e.f;
import com.tencent.kgvmp.report.b;
import com.tencent.kgvmp.report.d;
import com.tencent.kgvmp.report.e;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class PerformanceAdjuster {
    /* access modifiers changed from: private */
    public static final String a = a.a;
    private static int e = -1;
    public static HashMap<Integer, String> kgtocommonmap = new HashMap<>();
    /* access modifiers changed from: private */
    public String b = "com.tencent.tmgp.sgame";
    private com.tencent.kgvmp.socket.a c;
    private com.tencent.kgvmp.b.a d;
    /* access modifiers changed from: private */
    public e f = e.VMP_SUCCESS;

    /* access modifiers changed from: private */
    public void a(Context context) {
        this.b = context.getPackageName();
        d.k(this.b);
        File externalFilesDir = context.getExternalFilesDir("");
        if (externalFilesDir == null) {
            f.a(a, "Perf_init: get external dir failed.");
            this.f = e.GET_EXTERNAL_DIR_FAILED;
            return;
        }
        com.tencent.kgvmp.report.f.a(externalFilesDir.getPath());
        String str = com.tencent.kgvmp.report.f.f() + Constants.URL_PATH_DELIMITER + a.e;
        f.a(a, "Perf_init: logFilePath: " + str);
        if (!f.a(str)) {
            f.a(a, "Perf_init: not found debug log file.");
        }
        String str2 = com.tencent.kgvmp.report.f.f() + Constants.URL_PATH_DELIMITER + a.c;
        f.a(a, "Perf_init: cloudConfigFilePath: " + str2);
        com.tencent.kgvmp.c.a aVar = new com.tencent.kgvmp.c.a(str2);
        e a2 = aVar.a();
        if (a2 == e.VMP_SUCCESS) {
            aVar.f().g();
            com.tencent.kgvmp.report.f.b(aVar.b());
            com.tencent.kgvmp.report.f.a(aVar.c());
            com.tencent.kgvmp.report.f.c(aVar.d());
            com.tencent.kgvmp.report.f.d(aVar.e());
            f.a(a, "Perf_init: sdk func open status " + String.valueOf(com.tencent.kgvmp.report.f.b()));
            f.a(a, "Perf_init: report func open status " + String.valueOf(com.tencent.kgvmp.report.f.a()));
            f.a(a, "Perf_init: opt func open status " + String.valueOf(com.tencent.kgvmp.report.f.c()));
            f.a(a, "Perf_init: opt report open status " + String.valueOf(com.tencent.kgvmp.report.f.d()));
        } else {
            f.a(a, "Perf_init: load config file failed. ");
            this.f = a2;
        }
        a(str2);
        this.c = new com.tencent.kgvmp.socket.a();
        if (!this.c.a(context)) {
            f.a(a, "Perf_init: start service failed");
            this.f = e.START_SERVER_FAILED;
        }
    }

    /* access modifiers changed from: private */
    public void a(Context context, HashMap<String, String> hashMap) {
        this.d = new com.tencent.kgvmp.b.a(context);
        if (com.tencent.kgvmp.report.f.c()) {
            e = this.d.a();
        } else {
            f.a(a, "Perf_init: device check function is not open. ");
            e = 0;
        }
        if (com.tencent.kgvmp.report.f.d()) {
            this.d.a(hashMap);
        } else {
            f.a(a, "Perf_init: device report function is not open. ");
        }
        this.d.d();
    }

    private void a(final String str) {
        new Thread(new Runnable() {
            public void run() {
                HashMap hashMap = new HashMap();
                long currentTimeMillis = System.currentTimeMillis();
                hashMap.put(IMFriendInfoExViber.TAG, "0");
                try {
                    f.b(PerformanceAdjuster.a, "Perf_init: download config thread start.");
                    boolean c = com.tencent.kgvmp.e.d.c(com.tencent.kgvmp.report.f.f() + Constants.URL_PATH_DELIMITER + a.f);
                    if (!c) {
                        f.a(PerformanceAdjuster.a, "Perf_init: not find debug file. ");
                    }
                    String str = a.i + PerformanceAdjuster.this.b;
                    if (c) {
                        str = a.h + PerformanceAdjuster.this.b;
                        hashMap.put("debug", "1");
                    }
                    f.b(PerformanceAdjuster.a, "Perf_init: cloud config url: " + str);
                    com.tencent.kgvmp.e.e eVar = new com.tencent.kgvmp.e.e();
                    eVar.a(5000);
                    eVar.b(5000);
                    String a2 = eVar.a(str);
                    if (a2 == null) {
                        f.a(PerformanceAdjuster.a, "Perf_init: download cloud config is null. ");
                        hashMap.put(GGLiveConstants.PARAM.RESULT, e.DOWNLOAD_CONFIG_EXCEPTION.getStringCode());
                    } else if (a2.isEmpty()) {
                        f.a(PerformanceAdjuster.a, "Perf_init: download cloud config is empty. ");
                        hashMap.put(GGLiveConstants.PARAM.RESULT, e.DOWNLOAD_CONFIG_EMPTY.getStringCode());
                    } else {
                        f.b(PerformanceAdjuster.a, "Perf_init: config content: " + a2);
                        try {
                            new JSONObject(a2);
                        } catch (Exception e) {
                            e.printStackTrace();
                            f.a(PerformanceAdjuster.a, "Perf_init:  content is not json format.");
                            hashMap.put(GGLiveConstants.PARAM.RESULT, e.DOWNLOAD_CONFIG_CONTENT_NOT_JSON.getStringCode());
                        }
                        try {
                            com.tencent.kgvmp.e.d.a(str, a2);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            hashMap.put(GGLiveConstants.PARAM.RESULT, e.DOWNLOAD_CONFIG_SAVE_FILE_EXCEPTION.getStringCode());
                            f.a(PerformanceAdjuster.a, "Perf_init: save config exception. ");
                        }
                    }
                    try {
                        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                        hashMap.put("run_time", String.valueOf(currentTimeMillis2));
                        f.b(PerformanceAdjuster.a, "Perf_init: download config thread run time: " + currentTimeMillis2);
                        com.tencent.kgvmp.report.f.d((HashMap<String, String>) hashMap);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        f.a(PerformanceAdjuster.a, "Perf_init: report download result exception. ");
                    }
                } catch (Throwable th) {
                    try {
                        long currentTimeMillis3 = System.currentTimeMillis() - currentTimeMillis;
                        hashMap.put("run_time", String.valueOf(currentTimeMillis3));
                        f.b(PerformanceAdjuster.a, "Perf_init: download config thread run time: " + currentTimeMillis3);
                        com.tencent.kgvmp.report.f.d((HashMap<String, String>) hashMap);
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        f.a(PerformanceAdjuster.a, "Perf_init: report download result exception. ");
                    }
                    throw th;
                }
            }
        }).start();
    }

    private void b() {
        kgtocommonmap.put(4, "sceneId");
        kgtocommonmap.put(5, "fps");
        kgtocommonmap.put(51, "tid");
    }

    public String checkDeviceIsReal() {
        int i = 0;
        if (e == -1) {
            f.a(a, "Perf_checkDevice: you need init first.");
        } else if (!com.tencent.kgvmp.report.f.c()) {
            f.a(a, "Perf_checkDevice: optfunc is not open.");
        } else {
            i = e;
        }
        f.a(a, "Perf_checkDevice: check result: " + i);
        return "{\"result\":" + i + "}";
    }

    public boolean checkSdkCanWork() {
        if (com.tencent.kgvmp.report.f.b() || com.tencent.kgvmp.report.f.a()) {
            f.a(a, "checkSdkCanWork:true");
            return true;
        }
        f.a(a, "checkSdkCanWork:false");
        return false;
    }

    public int getCurrentThreadTid() {
        int i = -1;
        try {
            i = Process.myTid();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        f.a(a, "Perf_gettid: tid: " + String.valueOf(i));
        return i;
    }

    public String getOptPerfCfg() {
        return "";
    }

    public int getVersionCode() {
        return 3;
    }

    public String getVersionName() {
        return "1.0.3_72";
    }

    public void init(final Context context) {
        f.b(a, "Perf_init: start.");
        final HashMap hashMap = new HashMap();
        b();
        long currentTimeMillis = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    if (!b.b()) {
                        f.a(PerformanceAdjuster.a, "Perf_init: not found beacon.");
                    }
                    com.tencent.kgvmp.report.f.g();
                    if (context != null) {
                        PerformanceAdjuster.this.a(context);
                        PerformanceAdjuster.this.a(context, (HashMap<String, String>) hashMap);
                        return;
                    }
                    e unused = PerformanceAdjuster.this.f = e.CONTEXT_IS_NULL;
                } catch (Throwable th) {
                    th.printStackTrace();
                    f.a(PerformanceAdjuster.a, "Perf_init: init exception.");
                    e unused2 = PerformanceAdjuster.this.f = e.REALLY_INIT_EXCEPTION;
                }
            }
        });
        thread.start();
        int i = 0;
        while (true) {
            if (thread.isAlive()) {
                if (i >= 1000) {
                    thread.interrupt();
                    this.f = e.INIT_THREAD_TIMEOUT;
                    break;
                }
                SystemClock.sleep((long) 10);
                i += 10;
            }
        }
        try {
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            f.b(a, "Perf_init: init thread run time: " + currentTimeMillis2);
            hashMap.put("run_time", String.valueOf(currentTimeMillis2));
            hashMap.put(GGLiveConstants.PARAM.RESULT, this.f.getStringCode());
            com.tencent.kgvmp.report.f.a((HashMap<String, String>) hashMap);
        } catch (Exception e2) {
            e2.printStackTrace();
            f.a(a, "Perf_init: init exception at last. ");
        }
    }

    public void registerCallback(VmpCallBack vmpCallBack) {
        f.a(a, "start registercallback.");
        HashMap hashMap = new HashMap();
        if (this.c != null) {
            hashMap.put(GGLiveConstants.PARAM.RESULT, this.c.a(vmpCallBack) ? "0" : "1");
        } else {
            hashMap.put(GGLiveConstants.PARAM.RESULT, "2");
        }
        com.tencent.kgvmp.report.f.e((HashMap<String, String>) hashMap);
    }

    public void setLogAble(boolean z) {
        if (z) {
            f.a(7);
        }
    }

    public void updateGameInfo(int i, float f2) {
        updateGameInfo(i, String.valueOf((int) f2));
    }

    public void updateGameInfo(int i, String str) {
        if (this.c != null) {
            this.c.a(i, str);
            return;
        }
        f.a(a, "Perf_update: 1 sdk func is not open or ServiceHelper is null. ");
        d.r.put(String.valueOf(i), str);
    }

    public void updateGameInfo(int i, float[] fArr) {
        if (this.c != null) {
            this.c.a(i, fArr);
        } else {
            f.a(a, "Perf_update: 2 sdk func is not open or ServiceHelper is null. ");
        }
    }

    public void updateGameInfo(String str, String str2) {
        char c2 = 65535;
        switch (str.hashCode()) {
            case 70796:
                if (str.equals("GPU")) {
                    c2 = 0;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                f.a(a, "Perf_update: GPU: " + String.valueOf(str2));
                d.l(String.valueOf(str2));
                return;
            default:
                return;
        }
    }

    public void updateGameInfo(HashMap<Object, String> hashMap) {
        if (this.c != null) {
            this.c.a(hashMap);
            return;
        }
        f.a(a, "Perf_update: 3 sdk func is not open or ServiceHelper is null. ");
        try {
            for (Map.Entry next : hashMap.entrySet()) {
                d.r.put(String.valueOf(next.getKey()), next.getValue());
            }
        } catch (Exception e2) {
            f.a(a, "Perf_update: save hashmap data exception. ");
        }
    }
}
