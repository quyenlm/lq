package com.tencent.imsdk.tool.task;

import java.util.TimerTask;

public abstract class BaseTask extends TimerTask {
    private String mTaskName = "";
    private int notifiedTimes = 0;

    public abstract int getMyInterval();

    public BaseTask(String taskName) {
        setmTaskName(taskName);
    }

    public final int getNotifiedTimes() {
        return this.notifiedTimes;
    }

    public final void increaseNotifiedTimes() {
        this.notifiedTimes++;
    }

    public final void resetNotifiedTimes() {
        this.notifiedTimes = 0;
    }

    public String getmTaskName() {
        return this.mTaskName;
    }

    public void setmTaskName(String mTaskName2) {
        this.mTaskName = mTaskName2;
    }
}
