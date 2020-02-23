package com.tencent.component.net.download.multiplex.download;

import android.os.Process;
import com.tencent.component.net.download.multiplex.DownloaderLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class DownloadTaskManager {
    private static final String TAG = "DownloadTaskManager";
    private List<Integer> mTaskIdList = new LinkedList();
    private HashMap<Integer, DownloadTask> mTaskList = new LinkedHashMap();
    /* access modifiers changed from: private */
    public List<Worker> mWorkerList = new LinkedList();
    private int mWorkerPoolSize = 2;

    public void addTask(DownloadTask task) {
        if (task != null) {
            synchronized (this.mTaskIdList) {
                int id = task.getTaskId();
                if (!this.mTaskIdList.contains(Integer.valueOf(id))) {
                    int taskSize = this.mTaskIdList.size();
                    int index = 0;
                    while (index < taskSize && id >= this.mTaskIdList.get(index).intValue()) {
                        index++;
                    }
                    this.mTaskIdList.add(index, Integer.valueOf(id));
                    this.mTaskList.put(Integer.valueOf(id), task);
                    task.fireTaskCreatedEvent();
                    execute();
                }
            }
        }
    }

    public DownloadTask removeTask(int taskId) {
        DownloadTask task = removeTaskFromTaskList(taskId);
        if (task != null) {
            boolean cancelled = false;
            Worker w = null;
            synchronized (this.mWorkerList) {
                Iterator i$ = this.mWorkerList.iterator();
                while (true) {
                    if (!i$.hasNext()) {
                        break;
                    }
                    Worker worker = i$.next();
                    if (worker.isWorkerForTask(task)) {
                        w = worker;
                        break;
                    }
                }
            }
            if (w != null) {
                cancelled = w.cancel(task);
            }
            if (!cancelled) {
                task.cancel();
            }
        }
        return task;
    }

    private DownloadTask getEarliestHiddenTask() {
        synchronized (this.mTaskIdList) {
            int size = this.mTaskIdList.size();
            int i = 0;
            while (i < size) {
                DownloadTask task = this.mTaskList.get(this.mTaskIdList.get(i));
                if (!task.isHidden() || task.mStatus != 0) {
                    i++;
                } else {
                    DownloadTask downloadTask = task;
                    return task;
                }
            }
            return null;
        }
    }

    private DownloadTask getEarliestNotHiddenTask() {
        synchronized (this.mTaskIdList) {
            int size = this.mTaskIdList.size();
            int i = 0;
            while (i < size) {
                DownloadTask task = this.mTaskList.get(this.mTaskIdList.get(i));
                if (task.isHidden() || task.mStatus != 0) {
                    i++;
                } else {
                    DownloadTask downloadTask = task;
                    return task;
                }
            }
            return null;
        }
    }

    private DownloadTask removeTaskFromTaskList(int taskId) {
        DownloadTask remove;
        synchronized (this.mTaskIdList) {
            Integer id = Integer.valueOf(taskId);
            this.mTaskIdList.remove(id);
            remove = this.mTaskList.remove(id);
        }
        return remove;
    }

    private int getHiddenTaskCount() {
        int count = 0;
        synchronized (this.mWorkerList) {
            for (Worker w : this.mWorkerList) {
                DownloadTask t = w.getTask();
                if (t != null && t.isHidden()) {
                    count++;
                }
            }
        }
        return count;
    }

    private int getNotHiddenTaskCount() {
        int count = 0;
        synchronized (this.mWorkerList) {
            for (Worker w : this.mWorkerList) {
                DownloadTask t = w.getTask();
                if (t != null && !t.isHidden()) {
                    count++;
                }
            }
        }
        return count;
    }

    public void execute() {
        DownloadTask task;
        DownloadTask task2;
        if (getNotHiddenTaskCount() < this.mWorkerPoolSize && (task2 = getEarliestNotHiddenTask()) != null) {
            createWorker(task2);
        }
        if (getHiddenTaskCount() < this.mWorkerPoolSize && (task = getEarliestHiddenTask()) != null) {
            createWorker(task);
        }
    }

    private void createWorker(DownloadTask task) {
        if (task != null) {
            DownloaderLog.d(TAG, "Create new download task worker - " + task);
            task.mStatus = 1;
            Worker worker = new Worker(task);
            worker.setName("download_task");
            synchronized (this.mWorkerList) {
                this.mWorkerList.add(worker);
            }
            worker.start();
        }
    }

    public void setWorkerPoolSize(int newSize) {
        this.mWorkerPoolSize = newSize;
    }

    public DownloadTask getOngoingTask(int taskId) {
        DownloadTask downloadTask;
        synchronized (this.mTaskIdList) {
            downloadTask = this.mTaskList.get(Integer.valueOf(taskId));
        }
        return downloadTask;
    }

    public boolean hasTaskOngoing() {
        boolean z;
        synchronized (this.mTaskIdList) {
            z = this.mTaskList.size() > 0;
        }
        return z;
    }

    public int getOngoingTaskCount() {
        int size;
        synchronized (this.mTaskIdList) {
            size = this.mTaskList.size();
        }
        return size;
    }

    public void taskCompleted(DownloadTask task) {
        if (!task.isCanceled()) {
            DownloaderLog.d(TAG, "Worker - Task not cancelled.");
            synchronized (this.mWorkerList) {
                Worker removedWorker = null;
                Iterator i$ = this.mWorkerList.iterator();
                while (true) {
                    if (!i$.hasNext()) {
                        break;
                    }
                    Worker work = i$.next();
                    if (work.getTask() == task) {
                        removedWorker = work;
                        break;
                    }
                }
                this.mWorkerList.remove(removedWorker);
                removeTaskFromTaskList(task.getTaskId());
            }
        }
    }

    private class Worker extends Thread {
        private DownloadTask mTask;

        public Worker(DownloadTask t) {
            this.mTask = t;
        }

        public DownloadTask getTask() {
            return this.mTask;
        }

        public void run() {
            Process.setThreadPriority(10);
            this.mTask.run();
            DownloaderLog.d(DownloadTaskManager.TAG, "Worker - Task done - " + this.mTask);
        }

        public boolean isWorkerForTask(DownloadTask t) {
            if (this.mTask == null || !this.mTask.equals(t)) {
                return false;
            }
            return true;
        }

        public boolean cancel(DownloadTask t) {
            DownloaderLog.d(DownloadTaskManager.TAG, "Worker - Try to cancel task at worker - " + this.mTask);
            if (this.mTask == null || !this.mTask.equals(t)) {
                return false;
            }
            this.mTask.cancel();
            DownloaderLog.d(DownloadTaskManager.TAG, "Worker - Task cancelled.");
            synchronized (DownloadTaskManager.this.mWorkerList) {
                DownloadTaskManager.this.mWorkerList.remove(this);
            }
            return true;
        }
    }
}
