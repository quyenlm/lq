package com.tencent.kgvmp.d;

import android.content.Context;
import android.os.SystemClock;
import com.samsung.android.game.gamelib.GameServiceHelper;
import com.tencent.kgvmp.VmpCallBack;
import com.tencent.kgvmp.a.a;
import com.tencent.kgvmp.e.f;
import com.tencent.kgvmp.report.e;

public class c extends a {
    /* access modifiers changed from: private */
    public static final String a = a.a;
    private static volatile c b = null;
    private boolean c = false;
    private GameServiceHelper d = new GameServiceHelper();
    /* access modifiers changed from: private */
    public int e = -1;
    /* access modifiers changed from: private */
    public VmpCallBack f = null;

    private c() {
    }

    public static c a() {
        if (b == null) {
            synchronized (c.class) {
                if (b == null) {
                    b = new c();
                }
            }
        }
        return b;
    }

    public e a(Context context) {
        if (this.d == null) {
            f.a(a, "isAvailable: gameservicehelper is null.");
            return e.SAMSUNG2_GAME_SERVICE_HELPER_IS_NULL;
        }
        f.a(a, "isAvailable: ready to bind.");
        this.d.bind(context);
        SystemClock.sleep(1000);
        f.a(a, "isAvailable: ready to init.");
        boolean init = this.d.init();
        f.a(a, "isAvailable: gameservicehelper init." + init);
        if (!init) {
            return e.SAMSUNG2_GAME_SERVICE_HELPER_INIT_FAILED;
        }
        this.c = true;
        return e.VMP_SUCCESS;
    }

    public e a(Context context, VmpCallBack vmpCallBack) {
        if (this.d == null) {
            f.a(a, "Samsung gameservicehelper is null.");
            return e.SAMSUNG2_GAME_SERVICE_HELPER_IS_NULL;
        } else if (!this.c) {
            f.a(a, "Samsung is not available.");
            return e.SAMSUNG2_GAME_SERVICE_HELPER_INIT_FAILED;
        } else {
            this.f = vmpCallBack;
            return !this.d.registerListener(new GameServiceHelper.Listener() {
                public void resultCallBack(int i, int i2) {
                    f.a(c.a, "resultCallBack: ");
                }

                public void systemCallBack(int i) {
                    f.a(c.a, "systemCallBack: last level: " + c.this.e);
                    if (i == c.this.e) {
                        f.a(c.a, "systemCallBack: the same to last.");
                        return;
                    }
                    int unused = c.this.e = i;
                    f.a(c.a, "systemCallBack: current level: " + i);
                    if (c.this.f != null) {
                        c.this.f.notifySystemInfo(("{\"" + com.tencent.kgvmp.a.e.FREQUENCY_SIGNAL + "\":\"2\",") + "\"" + com.tencent.kgvmp.a.e.DEVICE_TEMP + "\":\"" + String.valueOf(i) + "\"}");
                    }
                }
            }) ? e.SAMSUNG2_GAME_SERVICE_HELPER_REGISTERED_FAILED : e.VMP_SUCCESS;
        }
    }

    public e a(String str) {
        if (this.c) {
            f.a(a, "samsung updateGameInfo: " + str);
            int updateGameInfo = this.d.updateGameInfo(str);
            if (updateGameInfo == -1) {
                f.a(a, "samsungsdk2 updateGameInfo faild:" + updateGameInfo);
            }
        }
        return e.VMP_SUCCESS;
    }
}
