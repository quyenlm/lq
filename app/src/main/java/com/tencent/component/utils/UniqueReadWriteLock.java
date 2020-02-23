package com.tencent.component.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UniqueReadWriteLock<K> {
    private final ConcurrentHashMap<K, ReadWriteLock> mLockMap = new ConcurrentHashMap<>();

    public Lock readLock(K lockId) {
        return obtain(lockId).readLock();
    }

    public Lock writeLock(K lockId) {
        return obtain(lockId).writeLock();
    }

    private ReadWriteLock obtain(K lockId) {
        ReadWriteLock lock = this.mLockMap.get(lockId);
        if (lock == null) {
            synchronized (this.mLockMap) {
                try {
                    lock = this.mLockMap.get(lockId);
                    if (lock == null) {
                        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
                        try {
                            this.mLockMap.put(lockId, reentrantReadWriteLock);
                            lock = reentrantReadWriteLock;
                        } catch (Throwable th) {
                            th = th;
                            ReentrantReadWriteLock reentrantReadWriteLock2 = reentrantReadWriteLock;
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
