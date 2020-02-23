package com.facebook.internal;

import com.facebook.FacebookSdk;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

public class LockOnGetVariable<T> {
    /* access modifiers changed from: private */
    public CountDownLatch initLatch;
    /* access modifiers changed from: private */
    public T value;

    public LockOnGetVariable(T value2) {
        this.value = value2;
    }

    public LockOnGetVariable(final Callable<T> callable) {
        this.initLatch = new CountDownLatch(1);
        FacebookSdk.getExecutor().execute(new FutureTask(new Callable<Void>() {
            /* JADX INFO: finally extract failed */
            public Void call() throws Exception {
                try {
                    Object unused = LockOnGetVariable.this.value = callable.call();
                    LockOnGetVariable.this.initLatch.countDown();
                    return null;
                } catch (Throwable th) {
                    LockOnGetVariable.this.initLatch.countDown();
                    throw th;
                }
            }
        }));
    }

    public T getValue() {
        waitOnInit();
        return this.value;
    }

    private void waitOnInit() {
        if (this.initLatch != null) {
            try {
                this.initLatch.await();
            } catch (InterruptedException e) {
            }
        }
    }
}
