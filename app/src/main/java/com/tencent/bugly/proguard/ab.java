package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.tencent.component.debug.FileTracerConfig;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: BUGLY */
public final class ab extends Thread {
    private static boolean a = false;
    private List<aa> b = Collections.synchronizedList(new ArrayList());
    private List<ac> c = Collections.synchronizedList(new ArrayList());

    /* compiled from: BUGLY */
    static class a {
        public static final ab a = new ab();
    }

    public static ab a(Context context) {
        return a.a;
    }

    public final void a() {
        a(new Handler(Looper.getMainLooper()), Constants.ACTIVE_THREAD_WATCHDOG);
    }

    public final void b() {
        a(new Handler(Looper.getMainLooper()));
    }

    private void a(Handler handler, long j) {
        if (handler == null) {
            x.e("addThread handler should not be null", new Object[0]);
            return;
        }
        String name = handler.getLooper().getThread().getName();
        for (int i = 0; i < this.b.size(); i++) {
            if (this.b.get(i).e().equals(handler.getLooper().getThread().getName())) {
                x.e("addThread fail ,this thread has been added in monitor queue", new Object[0]);
                return;
            }
        }
        this.b.add(new aa(handler, name, Constants.ACTIVE_THREAD_WATCHDOG));
    }

    private void a(Handler handler) {
        if (handler == null) {
            x.e("removeThread handler should not be null", new Object[0]);
            return;
        }
        for (int i = 0; i < this.b.size(); i++) {
            if (this.b.get(i).e().equals(handler.getLooper().getThread().getName())) {
                x.c("remove handler::%s", this.b.get(i));
                this.b.remove(i);
            }
        }
    }

    public final boolean c() {
        if (!isAlive()) {
            return false;
        }
        interrupt();
        a = true;
        return true;
    }

    public final boolean d() {
        if (isAlive()) {
            return false;
        }
        a = false;
        start();
        return true;
    }

    public final void a(ac acVar) {
        if (this.c.contains(acVar)) {
            x.e("addThreadMonitorListeners fail ,this threadMonitorListener has been added in monitor queue", new Object[0]);
        } else {
            this.c.add(acVar);
        }
    }

    public final void b(ac acVar) {
        this.c.remove(acVar);
    }

    public final void run() {
        setName("Bugly-ThreadMonitor");
        while (!a) {
            for (int i = 0; i < this.b.size(); i++) {
                this.b.get(i).a();
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            for (long j = 2000; j > 0 && !isInterrupted(); j = 2000 - (SystemClock.uptimeMillis() - uptimeMillis)) {
                try {
                    sleep(j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            int i2 = 0;
            for (int i3 = 0; i3 < this.b.size(); i3++) {
                i2 = Math.max(i2, this.b.get(i3).c());
            }
            if (!(i2 == 0 || i2 == 1)) {
                ArrayList arrayList = new ArrayList();
                for (int i4 = 0; i4 < this.b.size(); i4++) {
                    aa aaVar = this.b.get(i4);
                    if (aaVar.b()) {
                        arrayList.add(aaVar);
                        aaVar.a(FileTracerConfig.FOREVER);
                        x.d("to avoid upload block state repeated. as thread is blocked ,it may not be monitor until thread is unblock or this state has not been dealed with.", new Object[0]);
                    }
                }
                boolean z = false;
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    aa aaVar2 = (aa) arrayList.get(i5);
                    Thread d = aaVar2.d();
                    for (int i6 = 0; i6 < this.c.size(); i6++) {
                        if (this.c.get(i6).a(d)) {
                            z = true;
                        }
                    }
                    if (!z && aaVar2.e().contains("main")) {
                        aaVar2.f();
                        x.d("although thread is blocked ,may not be anr error,so restore handler check wait time and restart check main thread", new Object[0]);
                    }
                }
            }
        }
    }
}
