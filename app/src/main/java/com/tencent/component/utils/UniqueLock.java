package com.tencent.component.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UniqueLock<K> {
    private final ConcurrentHashMap<K, Lock> mLockMap = new ConcurrentHashMap<>();

    public Lock obtain(K lockId) {
        Lock lock = this.mLockMap.get(lockId);
        if (lock == null) {
            synchronized (this.mLockMap) {
                try {
                    lock = this.mLockMap.get(lockId);
                    if (lock == null) {
                        ReentrantLock reentrantLock = new ReentrantLock();
                        try {
                            this.mLockMap.put(lockId, reentrantLock);
                            lock = reentrantLock;
                        } catch (Throwable th) {
                            th = th;
                            ReentrantLock reentrantLock2 = reentrantLock;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return lock;
    }
}
