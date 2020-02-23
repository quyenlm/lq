package com.tencent.qqgamemi;

import com.tencent.component.plugin.PluginCommander;
import com.tencent.component.utils.log.LogUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class SDKCommander {
    private static ReadWriteLock mLock = new ReentrantReadWriteLock();
    private static ArrayList<QmiCommand> mPendingCmds = new ArrayList<>();
    private String TAG = "SDKCommander";

    /* access modifiers changed from: protected */
    public Object invokeQmiReadCmd(String cmd, Object args) {
        return invokeQmiReadCmd(cmd, args, (PluginCommander.ReadDataCallback) null);
    }

    /* access modifiers changed from: protected */
    public Object invokeQmiReadCmd(String cmd, Object args, PluginCommander.ReadDataCallback readDataCallback) {
        try {
            if (QmiCorePluginManager.getInstance().isPlatformInitialFinish()) {
                LogUtil.i(this.TAG, "invokeQmiReadCmd:[cmd:" + cmd + "|args:" + args + "]");
                return QmiCorePluginManager.getInstance().readDataFromPlugin(QmiCorePluginManager.RECORDER_PLUGIN_ID, cmd, args, (Object) null, readDataCallback);
            }
            boolean platformInit = false;
            mLock.writeLock().lock();
            if (!QmiCorePluginManager.getInstance().isPlatformInitialFinish()) {
                LogUtil.i(this.TAG, "add read cmd to pending list:[cmd:" + cmd + "|args:" + args + "]");
                mPendingCmds.add(new QmiCommand(cmd, args, readDataCallback));
            } else {
                platformInit = true;
            }
            mLock.writeLock().unlock();
            if (platformInit) {
                LogUtil.i(this.TAG, "invokeQmiReadCmd:[cmd:" + cmd + "|args:" + args + "] when platformInit");
                return QmiCorePluginManager.getInstance().readDataFromPlugin(QmiCorePluginManager.RECORDER_PLUGIN_ID, cmd, args, (Object) null, readDataCallback);
            }
            return null;
        } catch (Exception e) {
            LogUtil.e(this.TAG, e.getMessage());
        } catch (Throwable th) {
            mLock.writeLock().unlock();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void invokeQmiWriteCmd(String cmd, Object args) {
        try {
            if (QmiCorePluginManager.getInstance().isPlatformInitialFinish()) {
                if (!cmd.equalsIgnoreCase(SDKApiCMD.CMD_ON_UPDATE_VIDEO_FRAME)) {
                    LogUtil.i(this.TAG, "invokeQmiWriteCmd:[cmd:" + cmd + "|args:" + args + "]");
                }
                QmiCorePluginManager.getInstance().writeCommandToPlugin(QmiCorePluginManager.RECORDER_PLUGIN_ID, cmd, args);
                return;
            }
            boolean platformInit = false;
            mLock.writeLock().lock();
            if (!QmiCorePluginManager.getInstance().isPlatformInitialFinish()) {
                LogUtil.i(this.TAG, "add write cmd to pending list:[cmd:" + cmd + "|args:" + args + "]");
                if (cmd.equalsIgnoreCase(SDKApiCMD.CMD_INIT_QMI)) {
                    mPendingCmds.add(0, new QmiCommand(cmd, args));
                } else {
                    mPendingCmds.add(new QmiCommand(cmd, args));
                }
            } else {
                platformInit = true;
            }
            mLock.writeLock().unlock();
            if (platformInit) {
                QmiCorePluginManager.getInstance().deletePluginLoadingDialog();
                LogUtil.i(this.TAG, "invokeQmiWriteCmd:[cmd:" + cmd + "|args:" + args + "] when platformInit");
                QmiCorePluginManager.getInstance().writeCommandToPlugin(QmiCorePluginManager.RECORDER_PLUGIN_ID, cmd, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(this.TAG, e.getMessage());
        } catch (Throwable th) {
            mLock.writeLock().unlock();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public void sendPendingCmds() {
        try {
            mLock.writeLock().lock();
            ArrayList<QmiCommand> temp = new ArrayList<>(mPendingCmds);
            mPendingCmds.clear();
            mLock.writeLock().unlock();
            Iterator<QmiCommand> it = temp.iterator();
            while (it.hasNext()) {
                QmiCommand command = it.next();
                LogUtil.i(this.TAG, "send pending cmd:" + command.cmd + " | isReadCommand:" + command.readCommand);
                if (command.readCommand) {
                    QmiCorePluginManager.getInstance().readDataFromPlugin(QmiCorePluginManager.RECORDER_PLUGIN_ID, command.cmd, command.args, (Object) null, command.readDataCallback);
                } else {
                    QmiCorePluginManager.getInstance().writeCommandToPlugin(QmiCorePluginManager.RECORDER_PLUGIN_ID, command.cmd, command.args);
                }
            }
        } catch (Throwable th) {
            mLock.writeLock().unlock();
            throw th;
        }
    }

    private static class QmiCommand {
        Object args;
        String cmd;
        boolean readCommand;
        PluginCommander.ReadDataCallback readDataCallback;

        QmiCommand(String cmd2, Object args2) {
            this.cmd = cmd2;
            this.args = args2;
        }

        QmiCommand(String cmd2, Object args2, PluginCommander.ReadDataCallback callback) {
            this(cmd2, args2);
            this.readDataCallback = callback;
            this.readCommand = true;
        }
    }

    public <T> T readCmdSafe(String cmd, T defaultParam) {
        return readCmdSafe(cmd, (Object) null, defaultParam);
    }

    public <T> T readCmdSafe(String cmd, Object args, T defaultParam) {
        T returnObject = defaultParam;
        try {
            T object = invokeQmiReadCmd(cmd, args);
            if (object != null) {
                return object;
            }
            return returnObject;
        } catch (Exception e) {
            LogUtil.e(this.TAG, cmd + " fail :" + e.getMessage());
            return defaultParam;
        }
    }
}
