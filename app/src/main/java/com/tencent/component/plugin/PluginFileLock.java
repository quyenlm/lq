package com.tencent.component.plugin;

import com.tencent.component.utils.UniqueReadWriteLock;
import java.util.concurrent.locks.Lock;

public final class PluginFileLock {
    private static UniqueReadWriteLock<String> sPluginFileLock = new UniqueReadWriteLock<>();

    private PluginFileLock() {
    }

    public static Lock readLock(String path) {
        return sPluginFileLock.readLock(path);
    }

    public static Lock writeLock(String path) {
        return sPluginFileLock.writeLock(path);
    }
}
