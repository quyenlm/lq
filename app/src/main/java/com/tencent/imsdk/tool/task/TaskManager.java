package com.tencent.imsdk.tool.task;

import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class TaskManager {
    private static volatile TaskManager instance;
    private final int PERIOD = 60000;
    private boolean started = false;
    /* access modifiers changed from: private */
    public ArrayList<BaseTask> taskList = new ArrayList<>();
    private Timer timer = null;

    public static TaskManager getInstance() {
        if (instance == null) {
            synchronized (TaskManager.class) {
                if (instance == null) {
                    instance = new TaskManager();
                }
            }
        }
        return instance;
    }

    private TaskManager() {
    }

    public void startTimer() {
        if (!this.started) {
            this.timer = new Timer();
            this.timer.schedule(new TaskDispatcher(), 0, Constants.WATCHDOG_WAKE_TIMER);
            this.started = true;
        }
    }

    public void stopTimer() {
        if (this.timer != null && this.started) {
            this.timer.cancel();
            this.timer = null;
            this.started = false;
        }
    }

    private class TaskDispatcher extends TimerTask {
        private TaskDispatcher() {
        }

        public void run() {
            IMLogger.d("TaskDispatcher run");
            synchronized (TaskManager.this.taskList) {
                Iterator it = TaskManager.this.taskList.iterator();
                while (it.hasNext()) {
                    BaseTask task = (BaseTask) it.next();
                    task.increaseNotifiedTimes();
                    if (task.getNotifiedTimes() == task.getMyInterval()) {
                        task.run();
                        task.resetNotifiedTimes();
                    }
                }
            }
        }
    }

    public int addTask(BaseTask task) {
        int result;
        synchronized (this.taskList) {
            if (this.taskList.contains(task)) {
                result = -1;
            } else {
                this.taskList.add(task);
                result = 0;
            }
        }
        if (!this.started) {
            startTimer();
        }
        return result;
    }

    public void resetNotifiedTimesByTaskName(String taskName) {
        BaseTask tempTask = getTaskByName(taskName);
        if (tempTask != null) {
            tempTask.resetNotifiedTimes();
        }
    }

    private BaseTask getTaskByName(String taskName) {
        BaseTask task;
        synchronized (this.taskList) {
            Iterator<BaseTask> it = this.taskList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    task = null;
                    break;
                }
                task = it.next();
                if (taskName.equals(task.getmTaskName())) {
                    break;
                }
            }
        }
        return task;
    }

    public Boolean removeTask(String taskName) {
        BaseTask task = getTaskByName(taskName);
        if (task == null) {
            return false;
        }
        synchronized (this.taskList) {
            this.taskList.remove(task);
        }
        return true;
    }
}
