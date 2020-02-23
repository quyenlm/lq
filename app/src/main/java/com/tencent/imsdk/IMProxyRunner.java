package com.tencent.imsdk;

import android.content.Intent;
import java.util.LinkedList;

public class IMProxyRunner {
    private boolean isProxyRunning;
    public LinkedList<IMProxyTask> tasksQueue;

    private static final class InstanceHolder {
        static final IMProxyRunner instance = new IMProxyRunner();

        private InstanceHolder() {
        }
    }

    public static IMProxyRunner getInstance() {
        return InstanceHolder.instance;
    }

    private IMProxyRunner() {
        this.isProxyRunning = false;
        this.tasksQueue = new LinkedList<>();
    }

    public void startProxyTask(IMProxyTask task) {
        this.tasksQueue.add(task);
        if (!hasProxyRunning() && task != null) {
            setProxyRunning(true);
            task.onPreProxy();
            startProxyActivity(task);
        }
    }

    private void startProxyActivity(IMProxyTask task) {
        Intent intent = new Intent(task.getContext(), IMProxyActivity.class);
        intent.setFlags(344195072);
        task.getContext().startActivity(intent);
    }

    public IMProxyTask getTask() {
        return this.tasksQueue.poll();
    }

    public void setProxyRunning(boolean isRunning) {
        this.isProxyRunning = isRunning;
    }

    public boolean hasProxyRunning() {
        return this.isProxyRunning;
    }
}
