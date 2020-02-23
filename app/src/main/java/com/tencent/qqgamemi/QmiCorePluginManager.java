package com.tencent.qqgamemi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.tencent.component.cache.sp.PreferenceUtil;
import com.tencent.component.net.download.multiplex.FileDownload;
import com.tencent.component.net.download.multiplex.download.DownloadTask;
import com.tencent.component.net.download.multiplex.task.Task;
import com.tencent.component.net.download.multiplex.task.TaskObserver;
import com.tencent.component.plugin.InstallPluginListener;
import com.tencent.component.plugin.PluginCommander;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.plugin.PluginManager;
import com.tencent.component.plugin.PluginPlatformConfig;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.ResourceUtil;
import com.tencent.component.utils.SecurityUtil;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.util.DeviceDetectUtil;
import com.tencent.tp.a.h;
import com.tencent.ui.NotFocusableProgressDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QmiCorePluginManager {
    private static final String PREFENCE_LAST_PLUGIN_DOWNLOAD_TIME = "last_core_plugin_download_";
    private static final String PREFENCE_LAST_PLUGIN_UPATE_TIME = "last_core_plugin_update_";
    public static final String RECORDER_PLUGIN_ID = "com.tencent.qqgamemi.plugin.dpsrp";
    /* access modifiers changed from: private */
    public static String TAG = "QmiCorePluginManager";
    private static volatile QmiCorePluginManager qmiPluginManager;
    private static volatile boolean sInit = false;
    /* access modifiers changed from: private */
    public volatile List<PluginInfo> cachedPluginInfos;
    /* access modifiers changed from: private */
    public volatile List<PluginInfo> cachedRecorderPluginInfos;
    /* access modifiers changed from: private */
    public volatile Context context = null;
    private int curPluginVersion = 0;
    /* access modifiers changed from: private */
    public String initErrorMsg = null;
    private boolean isDialogFinish = false;
    boolean isUpdate = false;
    /* access modifiers changed from: private */
    public Object lock = new Object();
    /* access modifiers changed from: private */
    public volatile boolean platformInitFinish = false;
    private PluginManager pluginManager = null;
    /* access modifiers changed from: private */
    public NotFocusableProgressDialog pluginProgressDialog = null;

    private QmiCorePluginManager() {
    }

    public static QmiCorePluginManager getInstance() {
        if (qmiPluginManager == null) {
            synchronized (QmiCorePluginManager.class) {
                if (qmiPluginManager == null) {
                    qmiPluginManager = new QmiCorePluginManager();
                }
            }
        }
        return qmiPluginManager;
    }

    /* access modifiers changed from: package-private */
    public void initIfNecessary(Context iContext) {
        if (iContext != null && this.context == null) {
            this.context = iContext;
            PluginPlatformConfig config = new PluginPlatformConfig();
            config.platformId = "qmi";
            config.pluginProxyReceiver = QmiPluginTreeReceiver.class;
            config.pluginTreeServiceClass = QMiService.class;
            this.pluginManager = PluginManager.getInstance(iContext, config);
            this.pluginManager.init();
            this.pluginManager.addPluginListener(new PluginManager.PluginListener() {
                public void onPluginChanged(String id, int changeFlags, int statusFlags) {
                    Log.i(QmiCorePluginManager.TAG, "changeFlags:" + changeFlags + ";statusFlags:" + statusFlags);
                }

                public void onStartCheckPluginSurvive(List<PluginInfo> list) {
                }

                public void onPlatformInitialStart() {
                    LogUtil.d(QmiCorePluginManager.TAG, "init onPlatformInitialStart");
                }

                public void onPlatformInitialFinish() {
                    LogUtil.i(QmiCorePluginManager.TAG, "init onPlatformInitialFinish");
                    synchronized (QmiCorePluginManager.this.lock) {
                        boolean unused = QmiCorePluginManager.this.platformInitFinish = true;
                    }
                    String cpu = DeviceDetectUtil.getCpuInfo();
                    String gpu = DeviceDetectUtil.getGpuInfo();
                    LogUtil.i(QmiCorePluginManager.TAG, "cpu=" + cpu + ",   gpu =" + gpu);
                    SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_GET_CPU_GPU_INFO, new Object[]{gpu, cpu});
                    QmiCorePluginManager.this.getCorePluginListIfNesscary();
                }

                public void onPluginInstalled(String id, int lastVersion, int version) {
                }

                public void onPluginUninstall(String id) {
                }

                public void onPendingInstallFinish(boolean success, boolean corePlugin, String extraInfo, String errorMsg) {
                    LogUtil.i(QmiCorePluginManager.TAG, "onPendingInstallFinish:" + success + " | extraInfo:" + extraInfo + " | errorMsg:" + errorMsg + "|corePlugin:" + corePlugin);
                }
            });
        }
    }

    public static void init(Context context2) {
        if (!sInit) {
            sInit = true;
            getInstance().initIfNecessary(context2);
        }
    }

    /* access modifiers changed from: private */
    public static void updateDownloadPluginTime(boolean update, String pluginId, Context context2) {
        SharedPreferences preferences;
        if (!TextUtils.isEmpty(pluginId) && context2 != null && (preferences = PreferenceUtil.getGlobalCachePreference(context2)) != null) {
            long now = System.currentTimeMillis();
            preferences.edit().putLong(getIntervalKey(update, pluginId), now).commit();
            LogUtil.i(TAG, "update plugin:" + pluginId + " download time --> " + now);
        }
    }

    /* access modifiers changed from: private */
    public void getCorePluginListIfNesscary() {
        boolean init;
        synchronized (this.lock) {
            init = this.platformInitFinish;
            if (!init) {
                LogUtil.i(TAG, "pending to get corePluginList[platformInitFinish:" + this.platformInitFinish + "]");
            }
        }
        if (init) {
            updatePluginList(new Runnable() {
                public void run() {
                    QmiCorePluginManager.this.getCorePluginListInner(QmiCorePluginManager.this.cachedRecorderPluginInfos);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void updatePluginList(final Runnable runnable) {
        if (this.pluginManager != null) {
            this.pluginManager.getPluginList(new PluginManager.GetPluginListCallback() {
                public void onGetPluginList(List<PluginInfo> pluginInfos) {
                    List unused = QmiCorePluginManager.this.cachedPluginInfos = pluginInfos;
                    ArrayList<PluginInfo> corePluginList = new ArrayList<>();
                    if (pluginInfos != null) {
                        for (PluginInfo pluginInfo : pluginInfos) {
                            LogUtil.i(QmiCorePluginManager.TAG, "updatePluginList get pluginInfoID : " + pluginInfo.pluginId);
                            if (pluginInfo != null && pluginInfo.pluginId.equals(QmiCorePluginManager.RECORDER_PLUGIN_ID)) {
                                corePluginList.add(pluginInfo);
                            }
                        }
                    }
                    List unused2 = QmiCorePluginManager.this.cachedRecorderPluginInfos = corePluginList;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }, false);
        }
    }

    private boolean isRecorderPluginExist() {
        HashMap<String, PluginInfo> pluginInfoMap = getCorePluginMap(this.cachedRecorderPluginInfos);
        return (pluginInfoMap == null || pluginInfoMap.get(RECORDER_PLUGIN_ID) == null) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public List<PluginInfo> getCachedPluginList() {
        return this.cachedPluginInfos;
    }

    /* access modifiers changed from: private */
    public void tryToSendPendingCmds() {
        deletePluginLoadingDialog();
        if (isRecorderPluginExist()) {
            LogUtil.i(TAG, "All core plugin exist,send pending cmds.");
            SDKApiHelper.getInstance().sendPendingCmds();
        }
    }

    private int getCurPluginVersion(List<PluginInfo> pluginInfos) {
        for (PluginInfo info : pluginInfos) {
            if (info.pluginId.equals(RECORDER_PLUGIN_ID)) {
                return info.version;
            }
        }
        return 0;
    }

    public int getCurPluginVersion() {
        if (this.cachedRecorderPluginInfos == null) {
            return 0;
        }
        return getCurPluginVersion(this.cachedRecorderPluginInfos);
    }

    /* access modifiers changed from: private */
    public void getCorePluginListInner(List<PluginInfo> pluginInfos) {
        if (pluginInfos.size() > 0) {
            this.isUpdate = true;
            tryToSendPendingCmds();
        }
    }

    private HashMap<String, PluginInfo> getCorePluginMap(List<PluginInfo> pluginInfos) {
        HashMap<String, PluginInfo> pluginInfoHashMap = new HashMap<>();
        if (pluginInfos != null) {
            for (PluginInfo pluginInfo : pluginInfos) {
                if (pluginInfo != null && pluginInfo.pluginId.equals(RECORDER_PLUGIN_ID)) {
                    pluginInfoHashMap.put(pluginInfo.pluginId, pluginInfo);
                }
            }
        }
        return pluginInfoHashMap;
    }

    public void writeCommandToPlugin(String id, String cmd, Object args) {
        if (this.pluginManager != null) {
            this.pluginManager.writeCommandToPlugin(id, cmd, args);
        }
    }

    public Object readDataFromPlugin(String id, String cmd, Object args, Object defaultValue, PluginCommander.ReadDataCallback readDataCallback) {
        if (this.pluginManager != null) {
            return this.pluginManager.readDataFromPlugin(id, cmd, args, defaultValue, readDataCallback);
        }
        return null;
    }

    public void install(String path, InstallPluginListener installPluginListener) {
        if (this.pluginManager != null) {
            this.pluginManager.install(path, installPluginListener);
        }
    }

    public void addPendingInstallPlugin(String path) {
        if (this.pluginManager != null) {
            this.pluginManager.addPendingInstallPlugin(path);
        }
    }

    public void addPendingInstallPlugin(String pluginLocation, boolean corePlugin, String extraInfo) {
        if (this.pluginManager != null) {
            this.pluginManager.addPendingInstallPlugin(pluginLocation, corePlugin, extraInfo);
        }
    }

    public boolean isPlatformInitialFinish() {
        if (this.pluginManager == null || !this.pluginManager.isPlatformInitialFinish() || !isRecorderPluginExist()) {
            return false;
        }
        return true;
    }

    private static String getIntervalKey(boolean update, String pluginId) {
        if (update) {
            return PREFENCE_LAST_PLUGIN_UPATE_TIME + pluginId;
        }
        return PREFENCE_LAST_PLUGIN_DOWNLOAD_TIME + pluginId;
    }

    public void showPluginLoadingDialog(final Context context2) {
        if (this.isDialogFinish) {
            if (this.initErrorMsg != null) {
                SDKApiHelper.getInstance().showUIToast(context2, this.initErrorMsg);
            }
        } else if (context2 != null) {
            SDKApiHelper.getInstance().runOnMainThread(new Runnable() {
                public void run() {
                    if (QmiCorePluginManager.this.pluginProgressDialog == null) {
                        ResourceUtil.setContext(context2);
                        NotFocusableProgressDialog unused = QmiCorePluginManager.this.pluginProgressDialog = new NotFocusableProgressDialog(context2, ResourceUtil.getStyleId("Qmi_plugin_loading_style"));
                        if (context2 instanceof Activity) {
                            LogUtil.i(QmiCorePluginManager.TAG, "showPluginLoadingDialog context is activity");
                            QmiCorePluginManager.this.pluginProgressDialog.setSystemUiVisibility(((Activity) context2).getWindow().getDecorView().getSystemUiVisibility());
                        } else {
                            LogUtil.i(QmiCorePluginManager.TAG, "showPluginLoadingDialog context is not activity");
                            QmiCorePluginManager.this.pluginProgressDialog.setSystemUiVisibility(1);
                        }
                        QmiCorePluginManager.this.pluginProgressDialog.setProgressStyle(0);
                        QmiCorePluginManager.this.pluginProgressDialog.setMessage(context2.getResources().getString(ResourceUtil.getStringId("qmi_loading_recorder_plugin")));
                    }
                    QmiCorePluginManager.this.pluginProgressDialog.show();
                }
            });
        }
    }

    public void deletePluginLoadingDialog() {
        LogUtil.i(TAG, "deletePluginLoadingDialog is called!");
        this.isDialogFinish = true;
        if (!(this.initErrorMsg == null || this.pluginProgressDialog == null || !this.pluginProgressDialog.isShowing())) {
            SDKApiHelper.getInstance().showUIToast(this.context, this.initErrorMsg);
        }
        SDKApiHelper.getInstance().runOnMainThread(new Runnable() {
            public void run() {
                if (QmiCorePluginManager.this.pluginProgressDialog != null) {
                    QmiCorePluginManager.this.pluginProgressDialog.cancel();
                    NotFocusableProgressDialog unused = QmiCorePluginManager.this.pluginProgressDialog = null;
                }
            }
        });
    }

    private void startDownLoad(String pkgUrl, boolean update) {
        if (TextUtils.isEmpty(pkgUrl)) {
            if (this.curPluginVersion == 0) {
                this.initErrorMsg = this.context.getResources().getString(ResourceUtil.getStringId("qmi_no_recorder_plugin"));
            }
            tryToSendPendingCmds();
            return;
        }
        DownloadTask task = FileDownload.getDownloadTask(pkgUrl);
        LogUtil.d(TAG, "开始下载========");
        if (task != null) {
            switch (task.mStatus) {
                case 4:
                case 5:
                    LogUtil.i(TAG, "resume download plugin(com.tencent.qqgamemi.plugin.dpsrp,v:),current status is " + task.mStatus + " and url is " + pkgUrl);
                    FileDownload.downloadTaskToNext(task);
                    task.addObserver(new PluginDownloadObserver(RECORDER_PLUGIN_ID, pkgUrl, update, true, this.context));
                    return;
                default:
                    FileDownload.startAllDownloadTask();
                    LogUtil.i(TAG, "plugin is downloading (com.tencent.qqgamemi.plugin.dpsrp,v:),current status is " + task.mStatus + " and url is " + pkgUrl);
                    return;
            }
        } else {
            LogUtil.i(TAG, "start download plugin(com.tencent.qqgamemi.plugin.dpsrp,v:) ,url is " + pkgUrl);
            String cacheDir = FileUtil.getCacheDir(this.context, "qmi-plugins", false);
            LogUtil.i(TAG, "cacheDir=" + cacheDir);
            if (!TextUtils.isEmpty(cacheDir)) {
                try {
                    FileDownload.startDownloadFile(pkgUrl, cacheDir, SecurityUtil.encrypt(pkgUrl), new PluginDownloadObserver(RECORDER_PLUGIN_ID, pkgUrl, update, true, this.context));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                }
            }
        }
    }

    private class PluginDownloadObserver implements TaskObserver {
        private Context mContext;
        /* access modifiers changed from: private */
        public boolean mNeedForceUpdate;
        private String mPkgUrl;
        private String mPluginId;
        private boolean mUpdate;

        public PluginDownloadObserver(String pluginId, String pkgUrl, boolean update, boolean needForceUpdate, Context context) {
            this.mPluginId = pluginId;
            this.mContext = context;
            this.mUpdate = update;
            this.mPkgUrl = pkgUrl;
            this.mNeedForceUpdate = needForceUpdate;
        }

        public void onTaskCreated(Task task) {
        }

        public void onTaskStarted(Task task) {
        }

        public void onTaskProgress(Task task) {
        }

        /* access modifiers changed from: private */
        public void startAssistant() {
            QmiCorePluginManager.this.updatePluginList(new Runnable() {
                public void run() {
                    QmiCorePluginManager.this.tryToSendPendingCmds();
                }
            });
        }

        public void onTaskCompleted(Task task) {
            DownloadTask downloadTask = (DownloadTask) task;
            String pluginPath = downloadTask.getFileFolderPath() + Constants.URL_PATH_DELIMITER + downloadTask.getFileName();
            if (!this.mUpdate) {
                LogUtil.i(QmiCorePluginManager.TAG, "auto install plugin(" + this.mPluginId + ") immediately.");
                QmiCorePluginManager.getInstance().install(pluginPath, new InstallPluginListener.Stub() {
                    public void onInstallSuccess() throws RemoteException {
                        LogUtil.d(QmiCorePluginManager.TAG, "安装完成========");
                        PluginDownloadObserver.this.startAssistant();
                    }

                    public void onInstallFailed(String failMsg) throws RemoteException {
                        LogUtil.i(QmiCorePluginManager.TAG, "install core plugin failed [msg:" + failMsg + "]");
                        SDKApiHelper.getInstance().showUIToast(QmiCorePluginManager.this.context, "安装失败！");
                        if (!PluginDownloadObserver.this.mNeedForceUpdate) {
                            PluginDownloadObserver.this.startAssistant();
                        }
                    }
                });
            } else {
                LogUtil.i(QmiCorePluginManager.TAG, "pending update plugin(" + this.mPluginId + h.b);
                QmiCorePluginManager.getInstance().addPendingInstallPlugin(pluginPath, true, this.mPluginId + "|" + this.mPkgUrl);
            }
            QmiCorePluginManager.updateDownloadPluginTime(this.mUpdate, this.mPluginId, this.mContext);
        }

        public void onTaskFailed(Task task) {
            String unused = QmiCorePluginManager.this.initErrorMsg = QmiCorePluginManager.this.context.getResources().getString(ResourceUtil.getStringId("qmi_recorder_plugin_upgrade_failed"));
            if (task != null) {
                LogUtil.e(QmiCorePluginManager.TAG, "plugin update failed(id:" + this.mPluginId + "|taskStatus:" + task.mStatus + " | url:" + task.getTaskUrl() + h.b);
                switch (task.mStatus) {
                    case 4:
                    case 5:
                        String errorMsg = null;
                        if (task instanceof DownloadTask) {
                            errorMsg = ((DownloadTask) task).getErrorDesc();
                        }
                        LogUtil.i(QmiCorePluginManager.TAG, "onTaskFailed : " + errorMsg);
                        break;
                }
            } else {
                LogUtil.e(QmiCorePluginManager.TAG, "plugin update failed(id:" + this.mPluginId + h.b);
            }
            if (!this.mNeedForceUpdate) {
                QmiCorePluginManager.this.tryToSendPendingCmds();
            }
        }

        public void onTaskExtEvent(Task task) {
        }
    }
}
