package com.tencent.kgvmp.socket;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import com.tencent.kgvmp.VmpCallBack;
import com.tencent.kgvmp.d.b;
import com.tencent.kgvmp.d.c;
import com.tencent.kgvmp.d.d;
import com.tencent.kgvmp.d.e;
import com.tencent.kgvmp.e.f;
import com.tencent.kgvmp.socket.VmpService;
import java.util.HashMap;
import java.util.Map;

public class a {
    public static boolean a = false;
    protected static e b = null;
    protected static c c = null;
    protected static b d = null;
    public static d e = d.UNKOWN;
    /* access modifiers changed from: private */
    public static final String f = com.tencent.kgvmp.a.a.a;
    /* access modifiers changed from: private */
    public VmpService.b g;
    private boolean h = false;
    /* access modifiers changed from: private */
    public boolean i = false;
    private C0022a j;
    private Context k = null;

    /* renamed from: com.tencent.kgvmp.socket.a$a  reason: collision with other inner class name */
    private class C0022a implements ServiceConnection {
        private C0022a() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            f.a(a.f, "ServiceHelper: onServiceConnected. ");
            try {
                if (VmpService.b.class.isInstance(iBinder)) {
                    VmpService.b unused = a.this.g = (VmpService.b) iBinder;
                    boolean unused2 = a.this.i = true;
                }
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put(GGLiveConstants.PARAM.RESULT, a.this.i ? "0" : "1");
                    hashMap.put(IMFriendInfoExViber.TAG, "1");
                    com.tencent.kgvmp.report.f.c((HashMap<String, String>) hashMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put(GGLiveConstants.PARAM.RESULT, a.this.i ? "0" : "1");
                    hashMap2.put(IMFriendInfoExViber.TAG, "1");
                    com.tencent.kgvmp.report.f.c((HashMap<String, String>) hashMap2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                throw th;
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            f.a(a.f, "ServiceHelper: onServiceDisconnected. ");
            VmpService.b unused = a.this.g = null;
            boolean unused2 = a.this.i = false;
        }
    }

    private void b(final Context context) {
        c = c.a();
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (com.tencent.kgvmp.report.e.VMP_SUCCESS == a.c.a(context)) {
                        a.e = d.SAMSUNG2;
                        com.tencent.kgvmp.report.f.e(true);
                        f.a(a.f, "samsung2 sdk is available. ");
                    } else {
                        f.a(a.f, "samsung2 sdk is not available. ");
                    }
                    a.a = false;
                } catch (Throwable th) {
                    th.printStackTrace();
                    f.a(a.f, "samsung2 check isAvailable exception.");
                    a.a = false;
                }
            }
        }).start();
    }

    public boolean a(int i2, String str) {
        if (this.g == null || a) {
            f.a(f, "ServiceHelper: service connect failed, send string failed.");
            com.tencent.kgvmp.report.d.r.put(String.valueOf(i2), str);
            return false;
        }
        this.g.a().a(i2, str);
        return true;
    }

    public boolean a(int i2, float[] fArr) {
        if (this.g == null || a) {
            f.a(f, "ServiceHelper: service connect failed, send array failed.");
            return false;
        }
        this.g.a().a(i2, fArr);
        return true;
    }

    public boolean a(Context context) {
        Intent intent = new Intent(context, VmpService.class);
        this.j = new C0022a();
        this.h = context.bindService(intent, this.j, 1);
        String lowerCase = com.tencent.kgvmp.e.c.b().toLowerCase();
        f.a(f, "manufacture: " + String.valueOf(lowerCase));
        if (com.tencent.kgvmp.report.f.b() && lowerCase.compareTo("xiaomi") == 0) {
            b = e.a();
            if (com.tencent.kgvmp.report.e.VMP_SUCCESS == b.b()) {
                this.k = context;
                e = d.XIAOMI;
                com.tencent.kgvmp.report.f.e(true);
                f.a(f, "xiaomi sdk is available.");
            } else {
                f.a(f, "xiaomi sdk is not available.");
            }
        } else if (com.tencent.kgvmp.report.f.b() && lowerCase.compareTo("samsung") == 0) {
            a = true;
            this.k = context;
            b(this.k);
        } else if (!com.tencent.kgvmp.report.f.b() || lowerCase.compareTo("huawei") != 0) {
            f.a(f, "None sdk is support. manufacture: " + String.valueOf(lowerCase));
        } else {
            d = b.a();
            this.k = context;
            if (com.tencent.kgvmp.report.e.VMP_SUCCESS == d.b()) {
                e = d.HUAWEI2;
                com.tencent.kgvmp.report.f.e(true);
                f.a(f, "huawei sdk is available.");
            } else {
                f.a(f, "huawei sdk is not available.");
            }
        }
        return this.h;
    }

    public boolean a(VmpCallBack vmpCallBack) {
        if (e == d.XIAOMI) {
            if (b.a(this.k, vmpCallBack) == com.tencent.kgvmp.report.e.VMP_SUCCESS) {
                return true;
            }
        } else if (e == d.SAMSUNG2) {
            if (c.a(this.k, vmpCallBack) == com.tencent.kgvmp.report.e.VMP_SUCCESS) {
                return true;
            }
        } else if (e == d.HUAWEI2) {
            d.a(this.k, vmpCallBack);
            return true;
        }
        if (this.g != null) {
            this.g.a().a(vmpCallBack);
            return true;
        }
        f.a(f, "ServiceHelper: service connect failed, set callback failed.");
        return false;
    }

    public boolean a(HashMap<Object, String> hashMap) {
        if (this.g == null || a) {
            f.a(f, "ServiceHelper: service connect failed, send array failed.");
            try {
                for (Map.Entry next : hashMap.entrySet()) {
                    com.tencent.kgvmp.report.d.r.put(String.valueOf(next.getKey()), next.getValue());
                }
            } catch (Exception e2) {
                f.a(f, "ServiceHelper: save hashmap data exception. ");
            }
            return false;
        }
        this.g.a().a(hashMap);
        return true;
    }
}
