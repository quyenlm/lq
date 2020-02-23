package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.BuglyBroadcastReceiver;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.crashreport.crash.d;
import com.tencent.bugly.proguard.n;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.x;

/* compiled from: BUGLY */
public class CrashModule extends a {
    public static final int MODULE_ID = 1004;
    private static int c = 0;
    private static CrashModule e = new CrashModule();
    private long a;
    private BuglyStrategy.a b;
    private boolean d = false;

    public static CrashModule getInstance() {
        e.id = 1004;
        return e;
    }

    public synchronized boolean hasInitialized() {
        return this.d;
    }

    public synchronized void init(Context context, boolean z, BuglyStrategy buglyStrategy) {
        if (context != null) {
            if (!this.d) {
                x.a("Initializing crash module.", new Object[0]);
                n a2 = n.a();
                int i = c + 1;
                c = i;
                a2.a(1004, i);
                this.d = true;
                CrashReport.setContext(context);
                a(context, buglyStrategy);
                c a3 = c.a(1004, context, z, this.b, (o) null, (String) null);
                a3.e();
                a3.m();
                if (buglyStrategy == null || buglyStrategy.isEnableNativeCrashMonitor()) {
                    a3.g();
                } else {
                    x.a("[crash] Closed native crash monitor!", new Object[0]);
                    a3.f();
                }
                if (buglyStrategy == null || buglyStrategy.isEnableANRCrashMonitor()) {
                    a3.h();
                } else {
                    x.a("[crash] Closed ANR monitor!", new Object[0]);
                    a3.i();
                }
                a3.a(buglyStrategy != null ? buglyStrategy.getAppReportDelay() : 0);
                a3.l();
                d.a(context);
                BuglyBroadcastReceiver instance = BuglyBroadcastReceiver.getInstance();
                instance.addFilter("android.net.conn.CONNECTIVITY_CHANGE");
                instance.register(context);
                n a4 = n.a();
                int i2 = c - 1;
                c = i2;
                a4.a(1004, i2);
            }
        }
    }

    private synchronized void a(Context context, BuglyStrategy buglyStrategy) {
        if (buglyStrategy != null) {
            String libBuglySOFilePath = buglyStrategy.getLibBuglySOFilePath();
            if (!TextUtils.isEmpty(libBuglySOFilePath)) {
                a.a(context).m = libBuglySOFilePath;
                x.a("setted libBugly.so file path :%s", libBuglySOFilePath);
            }
            if (buglyStrategy.getCrashHandleCallback() != null) {
                this.b = buglyStrategy.getCrashHandleCallback();
                x.a("setted CrashHanldeCallback", new Object[0]);
            }
            if (buglyStrategy.getAppReportDelay() > 0) {
                this.a = buglyStrategy.getAppReportDelay();
                x.a("setted delay: %d", Long.valueOf(this.a));
            }
        }
    }

    public void onServerStrategyChanged(StrategyBean strategyBean) {
        c a2;
        if (strategyBean != null && (a2 = c.a()) != null) {
            a2.a(strategyBean);
        }
    }

    public String[] getTables() {
        return new String[]{"t_cr"};
    }
}
