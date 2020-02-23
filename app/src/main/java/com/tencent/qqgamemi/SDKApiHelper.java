package com.tencent.qqgamemi;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.tencent.component.UtilitiesInitial;
import com.tencent.component.net.download.multiplex.FileDownload;
import com.tencent.component.plugin.server.PluginConstant;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.SDKDetectableCommander;
import com.tencent.qqgamemi.protocol.pbproxy.PBProxyManager;
import com.tencent.qqgamemi.util.GameMessageEventManager;

public class SDKApiHelper extends SDKDetectableCommander {
    private static volatile SDKApiHelper instance;
    /* access modifiers changed from: private */
    public String TAG = "SDKApiHelper";
    private final int TYPE_DEFAULT = 0;
    private final int TYPE_JUGEMENT = 3;
    private final int TYPE_MANUAL = 2;
    private final int TYPE_MOMENT = 1;
    /* access modifiers changed from: private */
    public volatile boolean isLoaded = false;
    /* access modifiers changed from: private */
    public volatile boolean isPluginInit = false;
    /* access modifiers changed from: private */
    public volatile boolean isSDKInit = false;
    /* access modifiers changed from: private */
    public ApplicationLifecycleCallback lifecycleCallback;
    /* access modifiers changed from: private */
    public Object mLock = new Object();

    public static SDKApiHelper getInstance() {
        if (instance == null) {
            synchronized (SDKApiHelper.class) {
                if (instance == null) {
                    instance = new SDKApiHelper();
                }
            }
        }
        return instance;
    }

    public void initSDK(final Context context) {
        runOnMainThread(new Runnable() {
            public void run() {
                if (!SDKApiHelper.this.isSDKInit) {
                    UtilitiesInitial.init(context);
                    LogManager.setLogTraceLevel(3);
                    LogUtil.i(SDKApiHelper.this.TAG, "initSDK is called:sdk-2500-2.1.1-build-201806131635");
                    SDKApiHelper.this.bugly(context);
                    PBProxyManager.getInstance().init(context);
                    FileDownload.init(context);
                    boolean unused = SDKApiHelper.this.isSDKInit = true;
                }
            }
        });
    }

    public void initPlugin(final Context context, final String gameEngineType, final int logLevel) {
        LogUtil.i(this.TAG, "initPlugin is called!");
        if (!this.isPluginInit && context != null) {
            checkSDKFeature(context, new CheckSDKFeatureCallback() {
                public void check(int sdkFeature) {
                    if (SDKApiHelper.this.enableRecording(sdkFeature)) {
                        synchronized (SDKApiHelper.this.mLock) {
                            if (!SDKApiHelper.this.isPluginInit) {
                                boolean unused = SDKApiHelper.this.isPluginInit = true;
                                SDKApiHelper.this.invokeQmiWriteCmd(SDKApiCMD.CMD_INIT_QMI, new Object[]{context, gameEngineType, Integer.valueOf(logLevel)});
                                SDKApiHelper.this.runOnMainThread(new Runnable() {
                                    public void run() {
                                        if (context != null) {
                                            ApplicationLifecycleCallback unused = SDKApiHelper.this.lifecycleCallback = new ApplicationLifecycleCallback(context);
                                            SDKApiHelper.this.lifecycleCallback.register();
                                        }
                                        QmiCorePluginManager.init(context);
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void bugly(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("BuglySdkInfos", 0).edit();
        editor.putString("7e13aebe82", PluginConstant.PLUGIN_PLATFROM_VERSION_NAME);
        editor.commit();
    }

    public void showUIToast(final Context context, final String msg) {
        if (context != null) {
            runOnMainThread(new Runnable() {
                public void run() {
                    Toast.makeText(context.getApplicationContext(), msg, 1).show();
                }
            });
        }
    }

    public void showRecorder(Context context, String gameEngineType, float x, float y, int logLevel) {
        LogUtil.i(this.TAG, "showRecorder is called");
        initPlugin(context, gameEngineType, logLevel);
        final Context context2 = context;
        final String str = gameEngineType;
        final float f = x;
        final float f2 = y;
        checkSDKFeature(context, new CheckSDKFeatureCallback() {
            public void check(int sdkFeature) {
                if (!SDKDetectableCommander.SDKFeauture.Manual.isEnable(sdkFeature) || SDKDetectableCommander.SDKFeauture.Maintaining.isEnable(sdkFeature)) {
                    SDKApiHelper.this.showRecorderWarning(context2, 2);
                    return;
                }
                if (!SDKApiHelper.this.isLoaded) {
                    LogUtil.i(SDKApiHelper.this.TAG, "showPluginLoadingDialog is called");
                    QmiCorePluginManager.getInstance().showPluginLoadingDialog(context2);
                    boolean unused = SDKApiHelper.this.isLoaded = true;
                }
                LogUtil.i(SDKApiHelper.this.TAG, "showRecorder is called success!");
                SDKApiHelper.this.invokeQmiWriteCmd(SDKApiCMD.CMD_START_QMI, new Object[]{context2, str, Float.valueOf(f), Float.valueOf(f2), Integer.valueOf(SDKApiHelper.this.videoBusId), Integer.valueOf(sdkFeature)});
            }
        });
    }

    public void startMomentRecording(final Context context, final String gameEngineType, int logLevel) {
        LogUtil.i(this.TAG, "startMomentRecording is called");
        initPlugin(context, gameEngineType, logLevel);
        checkSDKFeature(context, new CheckSDKFeatureCallback() {
            public void check(int sdkFeature) {
                if (!SDKDetectableCommander.SDKFeauture.Moment.isEnable(sdkFeature) || SDKDetectableCommander.SDKFeauture.Maintaining.isEnable(sdkFeature)) {
                    LogUtil.i(SDKApiHelper.this.TAG, "查询白名单startMomentRecording onFail");
                    GameMessageEventManager.getInstance(context).onStartMomentRecordingStatus(context, 0);
                    return;
                }
                LogUtil.i(SDKApiHelper.this.TAG, "startMomentRecording  onSuccess====");
                SDKApiHelper.this.invokeQmiWriteCmd(SDKApiCMD.CMD_START_MOMENT_RECORDING, new Object[]{context, gameEngineType, Integer.valueOf(SDKApiHelper.this.videoBusId), Integer.valueOf(sdkFeature)});
            }
        });
    }

    public void startJudgementRecording(final Context context, final String gameEngineType) {
        LogUtil.i(this.TAG, "startJudgementRecording is called");
        checkSDKFeature(context, new CheckSDKFeatureCallback() {
            public void check(int sdkFeature) {
                if (!SDKDetectableCommander.SDKFeauture.Report.isEnable(sdkFeature) || SDKDetectableCommander.SDKFeauture.Maintaining.isEnable(sdkFeature)) {
                    LogUtil.i(SDKApiHelper.this.TAG, "查询白名单startJudgementRecording onFail");
                    GameMessageEventManager.getInstance(context).onStartJudgementRecordingStatus(context, 0);
                    return;
                }
                LogUtil.i(SDKApiHelper.this.TAG, "startJudgementRecording  onSuccess====");
                SDKApiHelper.this.invokeQmiWriteCmd(SDKApiCMD.CMD_START_JUDGEMENT_RECORDING, new Object[]{context, gameEngineType, Integer.valueOf(SDKApiHelper.this.videoBusId), Integer.valueOf(sdkFeature)});
            }
        });
    }

    public void showVideoListDialog(Context context) {
        LogUtil.i(this.TAG, "showVideoListDialog is called ：");
        checkSDKFeature(context, new CheckSDKFeatureCallback() {
            public void check(int sdkFeature) {
                if (SDKApiHelper.this.enableRecording(sdkFeature)) {
                    LogUtil.i(SDKApiHelper.this.TAG, "showVideoListDialog onSuccess=====");
                    SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_SHOW_VIDEO_LIST_DIALOG, new Object[]{Integer.valueOf(SDKApiHelper.this.videoBusId), Integer.valueOf(sdkFeature)});
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void showRecorderWarning(Context context, int type) {
        String warn;
        switch (type) {
            case 0:
                warn = String.format("暂不支持此手机开启%s录像功能,\n待测试完善后开放！", new Object[]{""});
                break;
            case 1:
                warn = String.format("暂不支持此手机开启%s录像功能,\n待测试完善后开放！", new Object[]{QMiConfig.getInstance().getMomentsTile(context)});
                break;
            case 2:
                warn = String.format("暂不支持此手机开启%s录像功能,\n待测试完善后开放！", new Object[]{QMiConfig.getInstance().getManualTitle(context)});
                break;
            case 3:
                warn = String.format("暂不支持此手机开启%s录像功能,\n待测试完善后开放！", new Object[]{QMiConfig.getInstance().getJugementTitle(context)});
                break;
            default:
                warn = null;
                break;
        }
        showUIToast(context, warn);
    }

    public void showDefaultWarning(Context context) {
        showRecorderWarning(context, 0);
    }

    public void checkPermission(Context context) {
        GameMessageEventManager.getInstance(context).onCheckSDKPermission(context, true);
    }

    public void setLogLevel(final int level) {
        runOnMainThread(new Runnable() {
            public void run() {
                LogManager.setLogTraceLevel(level);
                SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_SET_LOG_LEVEL, Integer.valueOf(level));
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean enableRecording(int sdkFeature) {
        if (SDKDetectableCommander.SDKFeauture.Maintaining.isEnable(sdkFeature)) {
            return false;
        }
        if (SDKDetectableCommander.SDKFeauture.Moment.isEnable(sdkFeature) || SDKDetectableCommander.SDKFeauture.Manual.isEnable(sdkFeature)) {
            return true;
        }
        return false;
    }

    public void writeMomentFeatureCmd(String cmd, Object args) {
        writeCmdWithCheckAndFeature(cmd, args, 1);
    }

    public void writeManualFeatureCmd(String cmd, Object args) {
        writeCmdWithCheckAndFeature(cmd, args, 2);
    }

    public void writeReportFeatureCmd(String cmd, Object args) {
        writeCmdWithCheckAndFeature(cmd, args, 16);
    }

    public void writeManualOrMomentFeatureCmd(String cmd, Object args) {
        writeCmdWithCheckOrFeature(cmd, args, 3);
    }

    public void writeManualOrMomentOrReportFeatureCmd(String cmd, Object args) {
        writeCmdWithCheckOrFeature(cmd, args, 19);
    }
}
