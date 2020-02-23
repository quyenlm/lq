package com.tencent.component.utils.thread;

import android.os.Looper;
import android.util.Log;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.component.utils.thread.Future;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPool {
    private static final int CORE_POOL_SIZE = 4;
    public static final JobContext JOB_CONTEXT_STUB = new JobContextStub();
    private static final int KEEP_ALIVE_TIME = 10;
    private static final int MAX_POOL_SIZE = 8;
    @PluginApi(since = 4)
    public static final int MODE_CPU = 1;
    @PluginApi(since = 4)
    public static final int MODE_NETWORK = 2;
    @PluginApi(since = 4)
    public static final int MODE_NONE = 0;
    static final AtomicLong SEQ = new AtomicLong(0);
    ResourceCounter mCpuCounter;
    private final Executor mExecutor;
    ResourceCounter mNetworkCounter;

    @PluginApi(since = 4)
    public interface Job<T> {
        @PluginApi(since = 4)
        T run(JobContext jobContext);
    }

    @PluginApi(since = 4)
    public interface JobContext {
        @PluginApi(since = 4)
        boolean isCancelled();

        @PluginApi(since = 4)
        boolean setMode(int i);
    }

    private static class JobContextStub implements JobContext {
        private JobContextStub() {
        }

        public boolean isCancelled() {
            return false;
        }

        public boolean setMode(int mode) {
            return true;
        }
    }

    private static class ResourceCounter {
        public int value;

        public ResourceCounter(int v) {
            this.value = v;
        }
    }

    public enum Priority {
        LOW(1),
        NORMAL(2),
        HIGH(3);
        
        int priorityInt;

        private Priority(int priority) {
            this.priorityInt = priority;
        }
    }

    @PluginApi(since = 4)
    public ThreadPool() {
        this("thread-pool", 4, 8);
    }

    @PluginApi(since = 4)
    public ThreadPool(String name, int coreSize, int maxSize) {
        this.mCpuCounter = new ResourceCounter(2);
        this.mNetworkCounter = new ResourceCounter(2);
        coreSize = coreSize <= 0 ? 1 : coreSize;
        this.mExecutor = new ThreadPoolExecutor(coreSize, maxSize <= coreSize ? coreSize : maxSize, 10, TimeUnit.SECONDS, new PriorityBlockingQueue(), new PriorityThreadFactory(name, 10));
    }

    public ThreadPool(String name, int coreSize, int maxSize, BlockingQueue<Runnable> queue) {
        this.mCpuCounter = new ResourceCounter(2);
        this.mNetworkCounter = new ResourceCounter(2);
        coreSize = coreSize <= 0 ? 1 : coreSize;
        this.mExecutor = new ThreadPoolExecutor(coreSize, maxSize <= coreSize ? coreSize : maxSize, 10, TimeUnit.SECONDS, queue, new PriorityThreadFactory(name, 10));
    }

    @PluginApi(since = 4)
    public <T> Future<T> submit(Job<T> job, FutureListener<T> listener, Priority priority) {
        Worker<T> w = generateWorker(job, listener, priority);
        this.mExecutor.execute(w);
        return w;
    }

    @PluginApi(since = 4)
    public <T> Future<T> submit(Job<T> job, FutureListener<T> listener) {
        return submit(job, listener, Priority.NORMAL);
    }

    @PluginApi(since = 4)
    public <T> Future<T> submit(Job<T> job, Priority priority) {
        return submit(job, (FutureListener) null, priority);
    }

    @PluginApi(since = 4)
    public <T> Future<T> submit(Job<T> job) {
        return submit(job, (FutureListener) null, Priority.NORMAL);
    }

    private <T> Worker<T> generateWorker(Job<T> job, FutureListener<T> listener, Priority priority) {
        switch (priority) {
            case LOW:
                return new PriorityWorker<>(job, listener, priority.priorityInt, false);
            case NORMAL:
                return new PriorityWorker<>(job, listener, priority.priorityInt, false);
            case HIGH:
                return new PriorityWorker<>(job, listener, priority.priorityInt, true);
            default:
                return new PriorityWorker<>(job, listener, priority.priorityInt, false);
        }
    }

    private class Worker<T> implements Runnable, Future<T>, JobContext {
        private static final String TAG = "Worker";
        private Future.CancelListener mCancelListener;
        private volatile boolean mIsCancelled;
        private boolean mIsDone;
        private Job<T> mJob;
        private FutureListener<T> mListener;
        private int mMode;
        private T mResult;
        private ResourceCounter mWaitOnResource;

        public Worker(Job<T> job, FutureListener<T> listener) {
            this.mJob = job;
            this.mListener = listener;
        }

        public void run() {
            if (this.mListener != null) {
                this.mListener.onFutureBegin(this);
            }
            T result = null;
            if (setMode(1)) {
                try {
                    result = this.mJob.run(this);
                } catch (Throwable ex) {
                    LogUtil.w(TAG, "Exception in running a job", ex);
                }
            }
            synchronized (this) {
                setMode(0);
                this.mResult = result;
                this.mIsDone = true;
                notifyAll();
            }
            if (this.mListener != null) {
                this.mListener.onFutureDone(this);
            }
        }

        public synchronized void cancel() {
            if (!this.mIsCancelled) {
                this.mIsCancelled = true;
                if (this.mWaitOnResource != null) {
                    synchronized (this.mWaitOnResource) {
                        this.mWaitOnResource.notifyAll();
                    }
                }
                if (this.mCancelListener != null) {
                    this.mCancelListener.onCancel();
                }
            }
        }

        public boolean isCancelled() {
            return this.mIsCancelled;
        }

        public synchronized boolean isDone() {
            return this.mIsDone;
        }

        public synchronized T get() {
            while (!this.mIsDone) {
                try {
                    wait();
                } catch (Exception ex) {
                    Log.w(TAG, "ignore exception", ex);
                }
            }
            return this.mResult;
        }

        public void waitDone() {
            get();
        }

        public synchronized void setCancelListener(Future.CancelListener listener) {
            this.mCancelListener = listener;
            if (this.mIsCancelled && this.mCancelListener != null) {
                this.mCancelListener.onCancel();
            }
        }

        public boolean setMode(int mode) {
            ResourceCounter rc = modeToCounter(this.mMode);
            if (rc != null) {
                releaseResource(rc);
            }
            this.mMode = 0;
            ResourceCounter rc2 = modeToCounter(mode);
            if (rc2 != null) {
                if (!acquireResource(rc2)) {
                    return false;
                }
                this.mMode = mode;
            }
            return true;
        }

        private ResourceCounter modeToCounter(int mode) {
            if (mode == 1) {
                return ThreadPool.this.mCpuCounter;
            }
            if (mode == 2) {
                return ThreadPool.this.mNetworkCounter;
            }
            return null;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
            if (r2.value <= 0) goto L_0x0024;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0013, code lost:
            r2.value--;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x001a, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
            r1.mWaitOnResource = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x001e, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x001f, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r2.wait();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
            monitor-enter(r2);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean acquireResource(com.tencent.component.utils.thread.ThreadPool.ResourceCounter r2) {
            /*
                r1 = this;
            L_0x0000:
                monitor-enter(r1)
                boolean r0 = r1.mIsCancelled     // Catch:{ all -> 0x0021 }
                if (r0 == 0) goto L_0x000b
                r0 = 0
                r1.mWaitOnResource = r0     // Catch:{ all -> 0x0021 }
                r0 = 0
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
            L_0x000a:
                return r0
            L_0x000b:
                r1.mWaitOnResource = r2     // Catch:{ all -> 0x0021 }
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                monitor-enter(r2)
                int r0 = r2.value     // Catch:{ all -> 0x0029 }
                if (r0 <= 0) goto L_0x0024
                int r0 = r2.value     // Catch:{ all -> 0x0029 }
                int r0 = r0 + -1
                r2.value = r0     // Catch:{ all -> 0x0029 }
                monitor-exit(r2)     // Catch:{ all -> 0x0029 }
                monitor-enter(r1)
                r0 = 0
                r1.mWaitOnResource = r0     // Catch:{ all -> 0x002c }
                monitor-exit(r1)     // Catch:{ all -> 0x002c }
                r0 = 1
                goto L_0x000a
            L_0x0021:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                throw r0
            L_0x0024:
                r2.wait()     // Catch:{ InterruptedException -> 0x002f }
            L_0x0027:
                monitor-exit(r2)     // Catch:{ all -> 0x0029 }
                goto L_0x0000
            L_0x0029:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0029 }
                throw r0
            L_0x002c:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x002c }
                throw r0
            L_0x002f:
                r0 = move-exception
                goto L_0x0027
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.thread.ThreadPool.Worker.acquireResource(com.tencent.component.utils.thread.ThreadPool$ResourceCounter):boolean");
        }

        private void releaseResource(ResourceCounter counter) {
            synchronized (counter) {
                counter.value++;
                counter.notifyAll();
            }
        }
    }

    private class PriorityWorker<T> extends Worker<T> implements Comparable<PriorityWorker> {
        private final boolean mFilo;
        private final int mPriority;
        private final long mSeqNum = ThreadPool.SEQ.getAndIncrement();

        public PriorityWorker(Job<T> job, FutureListener<T> listener, int priority, boolean filo) {
            super(job, listener);
            this.mPriority = priority;
            this.mFilo = filo;
        }

        public int compareTo(PriorityWorker another) {
            if (this.mPriority > another.mPriority) {
                return -1;
            }
            if (this.mPriority < another.mPriority) {
                return 1;
            }
            return subCompareTo(another);
        }

        private int subCompareTo(PriorityWorker another) {
            int result = this.mSeqNum < another.mSeqNum ? -1 : this.mSeqNum > another.mSeqNum ? 1 : 0;
            return this.mFilo ? -result : result;
        }
    }

    @PluginApi(since = 4)
    public static ThreadPool getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        public static final ThreadPool INSTANCE = new ThreadPool();

        private InstanceHolder() {
        }
    }

    @PluginApi(since = 4)
    public static void runOnNonUIThread(final Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            getInstance().submit(new Job<Object>() {
                public Object run(JobContext jc) {
                    runnable.run();
                    return null;
                }
            });
        } else {
            runnable.run();
        }
    }
}
