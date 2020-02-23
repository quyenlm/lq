package com.google.firebase.storage;

import android.support.annotation.NonNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class zzu implements ThreadFactory {
    private final AtomicInteger zzbfH = new AtomicInteger(1);
    private final String zzcpo;

    zzu(@NonNull String str) {
        this.zzcpo = str;
    }

    public final Thread newThread(@NonNull Runnable runnable) {
        String str = this.zzcpo;
        Thread thread = new Thread(runnable, new StringBuilder(String.valueOf(str).length() + 27).append("FirebaseStorage-").append(str).append(this.zzbfH.getAndIncrement()).toString());
        thread.setDaemon(false);
        thread.setPriority(9);
        return thread;
    }
}
