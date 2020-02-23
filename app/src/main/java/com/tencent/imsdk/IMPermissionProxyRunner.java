package com.tencent.imsdk;

import android.content.Intent;
import java.util.LinkedList;

public class IMPermissionProxyRunner {
    private boolean isProxyRunning;
    public LinkedList<IMPermissionProxyTask> tasksQueue;

    private static final class InstanceHolder {
        static final IMPermissionProxyRunner instance = new IMPermissionProxyRunner();

        private InstanceHolder() {
        }
    }

    public static IMPermissionProxyRunner getInstance() {
        return InstanceHolder.instance;
    }

    private IMPermissionProxyRunner() {
        this.isProxyRunning = false;
        this.tasksQueue = new LinkedList<>();
    }

    public void startProxyTask(IMPermissionProxyTask task) {
        this.tasksQueue.add(task);
        if (!hasProxyRunning() && task != null) {
            setProxyRunning(true);
            task.onPreProxy();
            startProxyActivity(task);
        }
    }

    private void startProxyActivity(IMPermissionProxyTask task) {
        Intent intent = new Intent(task.getContext(), IMPermissionProxyActivity.class);
        intent.setFlags(344195072);
        task.getContext().startActivity(intent);
    }

    public IMPermissionProxyTask getTask() {
        return this.tasksQueue.poll();
    }

    public void setProxyRunning(boolean isRunning) {
        this.isProxyRunning = isRunning;
    }

    public boolean hasProxyRunning() {
        return this.isProxyRunning;
    }
}
