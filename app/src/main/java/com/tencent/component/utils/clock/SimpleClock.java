package com.tencent.component.utils.clock;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 6)
public class SimpleClock extends Clock {
    private static final int CLOCK_MAX_COUNT = 32;
    private static final String CLOCK_SERVICE_NAME = "base.clock.service";
    private static Handler clockHandler;
    private static HandlerThread clockThread;
    private static SimpleClock[] clocks;
    private volatile boolean canceled;

    @PluginApi(since = 6)
    public static SimpleClock set(long interval, long delay, OnClockListener listener) {
        SimpleClock clock;
        synchronized (SimpleClock.class) {
            initClockService();
            int id = -1;
            int i = 0;
            while (true) {
                if (i >= clocks.length) {
                    break;
                } else if (clocks[i] == null) {
                    id = i;
                    break;
                } else {
                    i++;
                }
            }
            if (id < 0) {
                clock = null;
            } else {
                clock = new SimpleClock(id, interval, listener);
                clocks[id] = clock;
                prepareNextInterval(id, delay);
            }
        }
        return clock;
    }

    @PluginApi(since = 6)
    public static void cancel(SimpleClock clock) {
        if (clock != null) {
            clock.setCanceled();
            int clockId = clock.getClockId();
            if (clockId >= 0 && clockId < clocks.length) {
                synchronized (SimpleClock.class) {
                    SimpleClock theClock = clocks[clockId];
                    if (theClock != null && theClock == clock) {
                        clocks[clockId] = null;
                        clockHandler.removeMessages(clockId);
                    }
                }
            }
        }
    }

    private static void initClockService() {
        synchronized (SimpleClock.class) {
            if (clocks == null) {
                clocks = new SimpleClock[32];
            }
            if (clockThread == null) {
                clockThread = new HandlerThread(CLOCK_SERVICE_NAME);
            }
            if (!clockThread.isAlive()) {
                clockThread.start();
            }
            if (clockThread.isAlive() && clockHandler == null) {
                clockHandler = new Handler(clockThread.getLooper()) {
                    public void handleMessage(Message msg) {
                        SimpleClock.handleClockMessage(msg.what);
                    }
                };
            }
        }
    }

    /* access modifiers changed from: private */
    public static void handleClockMessage(int clockId) {
        SimpleClock clock;
        OnClockListener listener;
        if (clockId >= 0 && clockId < clocks.length && (clock = clocks[clockId]) != null && (listener = clock.getListener()) != null) {
            if (listener.onClockArrived(clock)) {
                prepareNextInterval(clockId, clock.getInterval());
            } else {
                cancel(clock);
            }
        }
    }

    private static void prepareNextInterval(int clockId, long delay) {
        if (clockHandler == null) {
            return;
        }
        if (delay > 0) {
            clockHandler.sendEmptyMessageDelayed(clockId, delay);
        } else {
            clockHandler.sendEmptyMessage(clockId);
        }
    }

    protected SimpleClock(int clockId, long interval, OnClockListener listener) {
        super(clockId, interval, listener);
    }

    @PluginApi(since = 6)
    public void cancel() {
        cancel(this);
    }

    @PluginApi(since = 6)
    public synchronized boolean isCanceled() {
        return this.canceled;
    }

    public synchronized void setCanceled() {
        this.canceled = true;
    }
}
