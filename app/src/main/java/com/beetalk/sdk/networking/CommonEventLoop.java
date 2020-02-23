package com.beetalk.sdk.networking;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommonEventLoop {
    private static CommonEventLoop __instance;
    private ScheduledExecutorService m_executor = Executors.newSingleThreadScheduledExecutor();

    public static CommonEventLoop getInstance() {
        if (__instance == null) {
            __instance = new CommonEventLoop();
        }
        return __instance;
    }

    public void post(Runnable call) {
        this.m_executor.execute(call);
    }

    public void cancel(Future f) {
        if (!f.isCancelled() && !f.isDone()) {
            f.cancel(true);
        }
    }

    public Future delayPost(Runnable call, int nMillSec) {
        return this.m_executor.schedule(call, (long) nMillSec, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        this.m_executor.shutdown();
    }
}
