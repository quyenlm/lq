package com.neovisionaries.ws.client;

import java.util.Timer;
import java.util.TimerTask;

abstract class PeriodicalFrameSender {
    private PayloadGenerator mGenerator;
    private long mInterval;
    private boolean mScheduled;
    private Timer mTimer;
    private final String mTimerName;
    private final WebSocket mWebSocket;

    /* access modifiers changed from: protected */
    public abstract WebSocketFrame createFrame(byte[] bArr);

    public PeriodicalFrameSender(WebSocket webSocket, String timerName, PayloadGenerator generator) {
        this.mWebSocket = webSocket;
        this.mTimerName = timerName;
        this.mGenerator = generator;
    }

    public void start() {
        setInterval(getInterval());
    }

    public void stop() {
        synchronized (this) {
            if (this.mTimer != null) {
                this.mScheduled = false;
                this.mTimer.cancel();
            }
        }
    }

    public long getInterval() {
        long j;
        synchronized (this) {
            j = this.mInterval;
        }
        return j;
    }

    public void setInterval(long interval) {
        if (interval < 0) {
            interval = 0;
        }
        synchronized (this) {
            this.mInterval = interval;
        }
        if (interval != 0 && this.mWebSocket.isOpen()) {
            synchronized (this) {
                if (this.mTimer == null) {
                    this.mTimer = new Timer(this.mTimerName);
                }
                if (!this.mScheduled) {
                    this.mScheduled = schedule(this.mTimer, new Task(), interval);
                }
            }
        }
    }

    public PayloadGenerator getPayloadGenerator() {
        PayloadGenerator payloadGenerator;
        synchronized (this) {
            payloadGenerator = this.mGenerator;
        }
        return payloadGenerator;
    }

    public void setPayloadGenerator(PayloadGenerator generator) {
        synchronized (this) {
            this.mGenerator = generator;
        }
    }

    private final class Task extends TimerTask {
        private Task() {
        }

        public void run() {
            PeriodicalFrameSender.this.doTask();
        }
    }

    /* access modifiers changed from: private */
    public void doTask() {
        synchronized (this) {
            if (this.mInterval == 0 || !this.mWebSocket.isOpen()) {
                this.mScheduled = false;
                return;
            }
            this.mWebSocket.sendFrame(createFrame());
            this.mScheduled = schedule(this.mTimer, new Task(), this.mInterval);
        }
    }

    private WebSocketFrame createFrame() {
        return createFrame(generatePayload());
    }

    private byte[] generatePayload() {
        if (this.mGenerator == null) {
            return null;
        }
        try {
            return this.mGenerator.generate();
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean schedule(Timer timer, Task task, long interval) {
        try {
            timer.schedule(task, interval);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
