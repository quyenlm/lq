package com.amazonaws.mobileconnectors.s3.transferutility;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class TransferThreadPool {
    private static ExecutorService executorMainTask;
    private static ExecutorService executorPartTask;

    TransferThreadPool() {
    }

    private static synchronized void init() {
        synchronized (TransferThreadPool.class) {
            int processors = Runtime.getRuntime().availableProcessors();
            if (executorMainTask == null) {
                executorMainTask = buildExecutor(processors + 1);
            }
            if (executorPartTask == null) {
                executorPartTask = buildExecutor(processors + 1);
            }
        }
    }

    public static <T> Future<T> submitTask(Callable<T> c) {
        init();
        if (c instanceof UploadPartTask) {
            return executorPartTask.submit(c);
        }
        return executorMainTask.submit(c);
    }

    public static void closeThreadPool() {
        shutdown(executorPartTask);
        executorPartTask = null;
        shutdown(executorMainTask);
        executorMainTask = null;
    }

    private static void shutdown(ExecutorService executor) {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(250, TimeUnit.MILLISECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private static ExecutorService buildExecutor(int maxThreadsAllowed) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(maxThreadsAllowed, maxThreadsAllowed, 10, TimeUnit.SECONDS, new LinkedBlockingQueue());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }
}
