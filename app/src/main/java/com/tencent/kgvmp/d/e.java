package com.tencent.kgvmp.d;

import android.content.Context;
import com.tencent.kgvmp.VmpCallBack;
import com.tencent.kgvmp.e.f;
import com.xiaomi.boostersdk.GameBoosterEngineCallback;
import com.xiaomi.boostersdk.GameBoosterManager;

public class e extends a {
    /* access modifiers changed from: private */
    public static final String a = e.class.getSimpleName();
    private static volatile e b;
    private boolean c = false;

    public static e a() {
        if (b == null) {
            synchronized (e.class) {
                if (b == null) {
                    b = new e();
                }
            }
        }
        return b;
    }

    public com.tencent.kgvmp.report.e a(Context context, final VmpCallBack vmpCallBack) {
        if (!this.c) {
            f.a(a, "xiaomi register check sdk failed.");
            return com.tencent.kgvmp.report.e.XIAOMI_MOBILE_NOT_SUPPORT_SDK;
        }
        try {
            GameBoosterManager.registerThermalControlListener(context, new GameBoosterEngineCallback() {
                public void onThermalControlChanged(int i) {
                    f.a(e.a, "onThermalLevelChanged: level: " + String.valueOf(i));
                    if (i == 2) {
                        vmpCallBack.notifySystemInfo("{\"1\":\"2\"}");
                        f.a(e.a, "togamejson:" + "{\"1\":\"2\"}");
                    }
                }
            });
            return com.tencent.kgvmp.report.e.VMP_SUCCESS;
        } catch (Exception e) {
            f.a(a, "xiaomi  registergame failed.");
            return com.tencent.kgvmp.report.e.XIAOMI_MOBILE_REGISTER_FAILED;
        }
    }

    public void a(String str) {
        try {
            f.a(a, "XIAOMI updateGameInfo.");
            GameBoosterManager.updateGameInfo(str);
            f.a(a, "updateGameInfo: " + str);
        } catch (Exception e) {
            f.a(a, "updateGameInfo: failed.");
        }
    }

    public com.tencent.kgvmp.report.e b() {
        f.a(a, "checking XiaoMi sdk....");
        this.c = GameBoosterManager.checkIfSupportGameBooster();
        if (!this.c) {
            return com.tencent.kgvmp.report.e.XIAOMI_MOBILE_NOT_SUPPORT_SDK;
        }
        f.a(a, "isAvailable: XiaoMi sdk is support.");
        return com.tencent.kgvmp.report.e.VMP_SUCCESS;
    }
}
