package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Build;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.k;
import java.io.File;
import java.io.FileFilter;

class aq extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ Context b;
    final /* synthetic */ am c;

    aq(am amVar, Context context, Context context2) {
        this.c = amVar;
        this.a = context;
        this.b = context2;
    }

    public void run() {
        TbsLog.i("TbsInstaller", "TbsInstaller--quickDexOptForThirdPartyApp thread start");
        try {
            File file = this.a == null ? new File(TbsShareManager.getHostCorePathAppDefined()) : TbsShareManager.isThirdPartyApp(this.b) ? (TbsShareManager.c(this.b) == null || !TbsShareManager.c(this.b).contains("decouple")) ? this.c.q(this.a) : this.c.p(this.a) : this.c.q(this.a);
            File q = this.c.q(this.b);
            int i = Build.VERSION.SDK_INT;
            if (i != 19 && i < 21) {
                k.a(file, q, (FileFilter) new ar(this));
            }
            k.a(file, q, (FileFilter) new as(this));
            TbsLog.i("TbsInstaller", "TbsInstaller--quickDexOptForThirdPartyApp thread done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
