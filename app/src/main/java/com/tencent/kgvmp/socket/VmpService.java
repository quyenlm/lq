package com.tencent.kgvmp.socket;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.kgvmp.PerformanceAdjuster;
import com.tencent.kgvmp.VmpCallBack;
import com.tencent.kgvmp.a.e;
import com.tencent.kgvmp.e.f;
import com.tencent.kgvmp.report.BatteryInfoReceiver;
import com.tencent.kgvmp.report.c;
import com.tencent.kgvmp.report.d;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class VmpService extends Service {
    /* access modifiers changed from: private */
    public static final String a = com.tencent.kgvmp.a.a.a;
    /* access modifiers changed from: private */
    public boolean b = false;
    /* access modifiers changed from: private */
    public volatile boolean c = false;
    /* access modifiers changed from: private */
    public LocalSocket d = null;
    /* access modifiers changed from: private */
    public InputStream e = null;
    /* access modifiers changed from: private */
    public OutputStream f = null;
    /* access modifiers changed from: private */
    public PrintWriter g = null;
    private Thread h;
    private VmpCallBack i;
    /* access modifiers changed from: private */
    public c j = new c();
    private a k;

    private class a extends Handler {
        private long b = 0;

        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                if (System.currentTimeMillis() - this.b < 20) {
                    SystemClock.sleep(20);
                }
                this.b = System.currentTimeMillis();
                switch (message.what) {
                    case 1:
                        f.a(VmpService.a, "handleMessage: 1 key value .");
                        int i = message.arg2;
                        String valueOf = String.valueOf(message.obj);
                        if (d.r.size() != 0) {
                            f.a(VmpService.a, "handleMessage: 1 lost map data should send.");
                            HashMap hashMap = new HashMap();
                            for (String next : d.r.keySet()) {
                                hashMap.put(next, d.r.get(next));
                            }
                            hashMap.put(String.valueOf(i), valueOf);
                            VmpService.this.b((HashMap<String, String>) hashMap);
                            if (VmpService.this.c && !a.a) {
                                d.r.clear();
                                return;
                            }
                            return;
                        }
                        VmpService.this.b(i, valueOf);
                        return;
                    case 2:
                        f.a(VmpService.a, "handleMessage: 2 key float array.");
                        if (d.r.size() != 0) {
                            f.a(VmpService.a, "handleMessage: 2 lost map data should send.");
                            HashMap hashMap2 = new HashMap();
                            for (Map.Entry next2 : d.r.entrySet()) {
                                hashMap2.put(next2.getKey(), next2.getValue());
                            }
                            VmpService.this.b((HashMap<String, String>) hashMap2);
                            if (VmpService.this.c && !a.a) {
                                d.r.clear();
                            }
                        }
                        if (com.tencent.kgvmp.report.f.a()) {
                            VmpService.this.j.a(message.arg2, (float[]) message.obj);
                            return;
                        }
                        f.a(VmpService.a, "handleMessage: 2 report func is not open. ");
                        return;
                    case 3:
                        f.a(VmpService.a, "handleMessage: 3 hashmap.");
                        HashMap hashMap3 = (HashMap) message.obj;
                        HashMap hashMap4 = new HashMap();
                        if (d.r.size() != 0) {
                            f.a(VmpService.a, "handleMessage: 3 lost map data should send.");
                            for (String next3 : d.r.keySet()) {
                                hashMap4.put(next3, d.r.get(next3));
                            }
                            for (Map.Entry entry : hashMap3.entrySet()) {
                                hashMap4.put(String.valueOf(entry.getKey()), entry.getValue());
                            }
                            VmpService.this.b((HashMap<String, String>) hashMap4);
                            if (VmpService.this.c && !a.a) {
                                d.r.clear();
                                return;
                            }
                            return;
                        }
                        for (Object next4 : hashMap3.keySet()) {
                            hashMap4.put(String.valueOf(next4), hashMap3.get(next4));
                        }
                        VmpService.this.b((HashMap<String, String>) hashMap4);
                        return;
                    default:
                        return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
                HashMap hashMap5 = new HashMap();
                hashMap5.put("msg", String.valueOf(message.what));
                com.tencent.kgvmp.report.f.h(hashMap5);
                f.a(VmpService.a, "handleMessage: exception. msg what: " + message.what);
            }
            th.printStackTrace();
            HashMap hashMap52 = new HashMap();
            hashMap52.put("msg", String.valueOf(message.what));
            com.tencent.kgvmp.report.f.h(hashMap52);
            f.a(VmpService.a, "handleMessage: exception. msg what: " + message.what);
        }
    }

    public class b extends Binder {
        public b() {
        }

        public VmpService a() {
            return VmpService.this;
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(GGLiveConstants.PARAM.RESULT, "0");
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (this.i != null) {
                this.i.notifySystemInfo(str);
            } else {
                f.a(a, "VmpService:parseContent: mVmpCallback is null.");
                hashMap.put(GGLiveConstants.PARAM.RESULT, "1");
            }
            try {
                hashMap.put(e.FREQUENCY_SIGNAL.getKeyStr(), jSONObject.getString(e.FREQUENCY_SIGNAL.getKeyStr()));
            } catch (Exception e2) {
                f.a(a, "VmpService:parseContent: get frequency signal failed.");
            }
            try {
                hashMap.put(e.DEVICE_TEMP.getKeyStr(), jSONObject.getString(e.DEVICE_TEMP.getKeyStr()));
            } catch (Exception e3) {
                f.a(a, "VmpService:parseContent: get device temp failed.");
            }
        } catch (JSONException e4) {
            f.a(a, "VmpService:parseContent: exception");
            hashMap.put(GGLiveConstants.PARAM.RESULT, "2");
        } finally {
            com.tencent.kgvmp.report.f.f(hashMap);
        }
    }

    private void b() {
        this.h = new Thread(new Runnable() {
            public void run() {
                LocalSocket unused = VmpService.this.d = new LocalSocket();
                try {
                    VmpService.this.d.connect(new LocalSocketAddress(com.tencent.kgvmp.a.a.b));
                    boolean unused2 = VmpService.this.b = VmpService.this.d.isConnected();
                    if (!VmpService.this.b) {
                        f.a(VmpService.a, "VmpService:ConnectService: connect failed.");
                        com.tencent.kgvmp.report.f.e(VmpService.this.b);
                        boolean unused3 = VmpService.this.c = true;
                        return;
                    }
                    VmpService.this.d.setReceiveBufferSize(SDKConstants.MAX_IMG_DATA_LENGTH_BYTES);
                    VmpService.this.d.setSendBufferSize(SDKConstants.MAX_IMG_DATA_LENGTH_BYTES);
                    OutputStream unused4 = VmpService.this.f = VmpService.this.d.getOutputStream();
                    InputStream unused5 = VmpService.this.e = VmpService.this.d.getInputStream();
                    PrintWriter unused6 = VmpService.this.g = new PrintWriter(VmpService.this.f, true);
                    com.tencent.kgvmp.report.f.e(VmpService.this.b);
                    byte[] bArr = new byte[1024];
                    boolean unused7 = VmpService.this.c = true;
                    while (true) {
                        int read = VmpService.this.e.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        String str = new String(bArr, 0, read, "UTF-8");
                        f.a(VmpService.a, "VmpService:receive: " + str);
                        VmpService.this.a(str);
                    }
                    boolean unused8 = VmpService.this.c = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    boolean unused9 = VmpService.this.b = false;
                    boolean unused10 = VmpService.this.c = true;
                    f.a(VmpService.a, "VmpService:ConnectService: exception");
                }
            }
        });
        this.h.start();
    }

    /* access modifiers changed from: private */
    public void b(int i2, String str) {
        if (com.tencent.kgvmp.report.f.a()) {
            this.j.a(i2, str);
        } else {
            f.a(a, "handleMessage: 1 report data not support. ");
        }
        if (i2 != 0) {
            if (com.tencent.kgvmp.report.f.b()) {
                c(i2, str);
            } else {
                f.a(a, "handleMessage: 1 func is not open. ");
            }
        }
    }

    private void b(String str) {
        if (this.b && this.f != null) {
            try {
                this.f.write(str.getBytes());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(HashMap<String, String> hashMap) {
        if (com.tencent.kgvmp.report.f.a()) {
            for (Map.Entry next : hashMap.entrySet()) {
                this.j.a(Integer.parseInt((String) next.getKey()), (String) next.getValue());
            }
        } else {
            f.a(a, "handleMessage: report func is not open. ");
        }
        if (com.tencent.kgvmp.report.f.b()) {
            c(hashMap);
        } else {
            f.a(a, "handleMessage: func is not open. ");
        }
    }

    private void c() {
        if (this.h != null) {
            if (this.b) {
                try {
                    if (this.f != null) {
                        this.f.close();
                    }
                    if (this.e != null) {
                        this.e.close();
                    }
                    if (this.d != null) {
                        this.d.close();
                    }
                } catch (Exception e2) {
                    f.a(a, "stopLocalSocketClientReceiver: close exception.");
                }
            }
            this.h.interrupt();
            f.a(a, "VmpService: stop socket recevier. ");
        }
    }

    private void c(int i2, String str) {
        if (a.e == com.tencent.kgvmp.d.d.XIAOMI) {
            String valueOf = PerformanceAdjuster.kgtocommonmap.get(Integer.valueOf(i2)) == null ? String.valueOf(i2) : PerformanceAdjuster.kgtocommonmap.get(Integer.valueOf(i2));
            String str2 = (i2 == 4 || i2 == 5) ? "{" + "\"" + valueOf + "\":" + str + "}" : "{" + "\"" + valueOf + "\":\"" + str + "\"}";
            f.a(a, "handleMessage to manufacturer:" + str2);
            a.b.a(str2);
        } else if (a.e == com.tencent.kgvmp.d.d.SAMSUNG2) {
            String valueOf2 = PerformanceAdjuster.kgtocommonmap.get(Integer.valueOf(i2)) == null ? String.valueOf(i2) : PerformanceAdjuster.kgtocommonmap.get(Integer.valueOf(i2));
            String str3 = (i2 == 4 || i2 == 5) ? "{" + "\"" + valueOf2 + "\":" + str + "}" : "{" + "\"" + valueOf2 + "\":\"" + str + "\"}";
            f.a(a, "handleMessage to manufacturer:" + str3);
            a.c.a(str3);
        } else if (a.e == com.tencent.kgvmp.d.d.HUAWEI2) {
            String a2 = b.a(i2, str);
            f.a(a, "handleMessage to manufacturer:" + a2);
            if (!a2.isEmpty()) {
                a.d.a(a2);
            }
        }
        String str4 = "{\"" + i2 + "\":\"" + str + "\"}";
        f.a(a, "handleMessage to manufacturer:" + str4);
        b(str4);
    }

    private void c(HashMap<String, String> hashMap) {
        String str;
        String str2;
        if (a.e == com.tencent.kgvmp.d.d.XIAOMI) {
            String str3 = "{";
            for (String next : hashMap.keySet()) {
                String str4 = hashMap.get(next);
                try {
                    int parseInt = Integer.parseInt(next);
                    String valueOf = PerformanceAdjuster.kgtocommonmap.get(Integer.valueOf(parseInt)) == null ? String.valueOf(parseInt) : PerformanceAdjuster.kgtocommonmap.get(Integer.valueOf(parseInt));
                    if (parseInt == 4 || parseInt == 5) {
                        str2 = str3 + "\"" + valueOf + "\":" + str4 + ",";
                    } else if (parseInt != 0) {
                        str2 = str3 + "\"" + valueOf + "\":\"" + str4 + "\",";
                    }
                    str3 = str2;
                } catch (Exception e2) {
                    f.a(a, "send data key exception. ");
                }
            }
            String str5 = str3 + "\"0\":\"0\"}";
            f.a(a, "handleMessage to manufacturer:" + str5);
            a.b.a(str5);
        } else if (a.e == com.tencent.kgvmp.d.d.SAMSUNG2) {
            String str6 = "{";
            for (String next2 : hashMap.keySet()) {
                String str7 = hashMap.get(next2);
                try {
                    int parseInt2 = Integer.parseInt(next2);
                    String valueOf2 = PerformanceAdjuster.kgtocommonmap.get(Integer.valueOf(parseInt2)) == null ? String.valueOf(parseInt2) : PerformanceAdjuster.kgtocommonmap.get(Integer.valueOf(parseInt2));
                    if (parseInt2 == 4 || parseInt2 == 5) {
                        str = str6 + "\"" + valueOf2 + "\":" + str7 + ",";
                    } else if (parseInt2 != 0) {
                        str = str6 + "\"" + valueOf2 + "\":\"" + str7 + "\",";
                    }
                    str6 = str;
                } catch (Exception e3) {
                    f.a(a, "send data key exception. ");
                }
            }
            String str8 = str6 + "\"0\":\"0\"}";
            f.a(a, "handleMessage to manufacturer:" + str8);
            a.c.a(str8);
        } else if (a.e == com.tencent.kgvmp.d.d.HUAWEI2) {
            String a2 = b.a(hashMap);
            f.a(a, "handleMessage to manufacturer huawei: " + a2);
            a.d.a(a2);
        }
        String str9 = "{";
        for (String next3 : hashMap.keySet()) {
            String str10 = hashMap.get(next3);
            int i2 = -1;
            try {
                i2 = Integer.parseInt(next3);
            } catch (Exception e4) {
                f.a(a, "send data key exception. ");
            }
            if (i2 != 0) {
                str9 = str9 + "\"" + next3 + "\":\"" + str10 + "\",";
            }
        }
        String str11 = str9 + "\"0\":\"0\"}";
        f.a(a, "handleMessage to manufacturer:" + str11);
        b(str11);
    }

    public void a(int i2, String str) {
        f.a(a, "VmpService: send string. ");
        Message.obtain(this.k, 1, 0, i2, str).sendToTarget();
    }

    public void a(int i2, float[] fArr) {
        f.a(a, "VmpService: send array: ");
        Message.obtain(this.k, 2, 0, i2, fArr).sendToTarget();
    }

    public void a(VmpCallBack vmpCallBack) {
        this.i = vmpCallBack;
    }

    public void a(HashMap<Object, String> hashMap) {
        f.a(a, "VmpService: send map: ");
        Message.obtain(this.k, 3, 0, 0, hashMap).sendToTarget();
    }

    public IBinder onBind(Intent intent) {
        return new b();
    }

    public void onCreate() {
        super.onCreate();
        registerReceiver(new BatteryInfoReceiver(), new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        b();
        HandlerThread handlerThread = new HandlerThread("vmpss");
        handlerThread.start();
        this.k = new a(handlerThread.getLooper());
    }

    public void onDestroy() {
        super.onDestroy();
        c();
    }
}
