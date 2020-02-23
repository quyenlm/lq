package com.tencent.component.net.download.multiplex.task;

public interface TaskObserver {
    void onTaskCompleted(Task task);

    void onTaskCreated(Task task);

    void onTaskExtEvent(Task task);

    void onTaskFailed(Task task);

    void onTaskProgress(Task task);

    void onTaskStarted(Task task);
}
