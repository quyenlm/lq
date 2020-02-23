package com.google.android.gms.gcm;

import android.support.annotation.NonNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class zzb implements ThreadFactory {
    private final AtomicInteger zzbfH = new AtomicInteger(1);

    zzb(GcmTaskService gcmTaskService) {
    }

    public final Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable, new StringBuilder(20).append("gcm-task#").append(this.zzbfH.getAndIncrement()).toString());
        thread.setPriority(4);
        return thread;
    }
}
