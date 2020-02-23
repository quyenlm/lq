package com.tencent.kgvmp.d;

import android.content.Context;
import com.huawei.iaware.sdk.gamesdk.IAwareGameSdk;
import com.tencent.kgvmp.VmpCallBack;
import com.tencent.kgvmp.a.a;
import com.tencent.kgvmp.e.f;
import com.tencent.kgvmp.report.d;
import com.tencent.kgvmp.report.e;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends a {
    /* access modifiers changed from: private */
    public static final String a = a.a;
    private static volatile b b = null;
    private IAwareGameSdk c = new IAwareGameSdk();
    /* access modifiers changed from: private */
    public VmpCallBack d = null;
    /* access modifiers changed from: private */
    public int e = -1;
    private boolean f = false;

    private b() {
    }

    /* access modifiers changed from: private */
    public int a(int i) {
        switch (i) {
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                return 0;
        }
    }

    public static b a() {
        if (b == null) {
            synchronized (b.class) {
                if (b == null) {
                    b = new b();
                }
            }
        }
        return b;
    }

    /* access modifiers changed from: private */
    public int b(String str) {
        try {
            return new JSONObject(str).getInt("temperature");
        } catch (JSONException e2) {
            f.a(a, "get huawei temperature exception.");
            return -1;
        }
    }

    public e a(Context context, VmpCallBack vmpCallBack) {
        this.d = vmpCallBack;
        f.a(a, "registerGame: huawei callback set success.");
        return e.VMP_SUCCESS;
    }

    public e a(String str) {
        if (!this.f) {
            f.a(a, "updateGameInfo: huawei sdk is not registered or available.");
            return e.HUAWEI2_MOBILE_NOT_SUPPORT_SDK;
        }
        this.c.updateGameAppInfo(str);
        return e.VMP_SUCCESS;
    }

    public e b() {
        if (!this.c.registerGame(d.g(), new IAwareGameSdk.GameCallBack() {
            public void getPhoneInfo(String str) {
                try {
                    int a2 = b.this.b(str);
                    if (-1 == a2 || b.this.e == a2) {
                        f.a(b.a, "HuaWeiSdkProxy:getPhoneInfo: same to last. json: " + String.valueOf(str));
                        return;
                    }
                    int unused = b.this.e = a2;
                    if (com.tencent.kgvmp.report.f.e() && b.this.d != null) {
                        b.this.d.notifySystemInfo(("{" + "\"" + com.tencent.kgvmp.a.e.FREQUENCY_SIGNAL + "\":\"2\",") + "\"" + com.tencent.kgvmp.a.e.FREQUENCY_LEVEL + "\":\"" + String.valueOf(b.this.a(a2)) + "\"}");
                    }
                } catch (Exception e) {
                    f.a(b.a, "huawei:callback: exception.");
                }
            }
        })) {
            f.a(a, "register huawei sdk failed. It is not available.");
            this.f = false;
            return e.HUAWEI2_MOBILE_NOT_SUPPORT_SDK;
        }
        this.f = true;
        return e.VMP_SUCCESS;
    }
}
