package com.tencent.qqgamemi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tencent.component.ComponentContext;
import com.tencent.component.utils.NetworkUtil;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.mgc.core.MGCSystemCore;
import com.tencent.qqgamemi.protocol.pbproxy.BaseProxy;
import com.tencent.qqgamemi.protocol.pbproxy.PBProxyManager;
import com.tencent.qqgamemi.util.DeviceDetectUtil;
import com.tencent.qqgamemi.util.GameMessageEventManager;
import com.tencent.qt.base.net.Request;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class SDKDetectableCommander extends SDKCommander {
    private static final String TAG = "SDKDetectableCommander";
    /* access modifiers changed from: private */
    public List<CheckSDKFeatureCallback> cacheCheckSDKFeatureCallbacks = new ArrayList();
    /* access modifiers changed from: private */
    public volatile boolean isCheckFeatureCalled = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public volatile int sdkFeatureCache = -1;
    private int sendUnityMsgCount = 0;
    volatile int videoBusId = 0;

    private interface SDKFeautureConstant {
        public static final int INGAMEAUDIO = 4;
        public static final int MAINTAINING = 8;
        public static final int MANUAL = 2;
        public static final int MOMENT = 1;
        public static final int MOMENT_MANUAL = 3;
        public static final int MOMENT_MANUAL_REPORT = 19;
        public static final int REPORT = 16;
    }

    static /* synthetic */ int access$508(SDKDetectableCommander x0) {
        int i = x0.sendUnityMsgCount;
        x0.sendUnityMsgCount = i + 1;
        return i;
    }

    public void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            this.mHandler.post(runnable);
        }
    }

    public enum SDKFeauture implements SDKFeautureConstant {
        Moment(1),
        Manual(2),
        InGameAudio(4),
        Maintaining(8),
        Report(16);
        
        private int mode;

        public static int getFilter() {
            return 31;
        }

        private SDKFeauture(int mode2) {
            this.mode = mode2;
        }

        public boolean isEnable(int sdkFeature) {
            return (this.mode & sdkFeature) == this.mode;
        }
    }

    public void writeCmdWithCheckAndFeature(final String cmd, final Object args, final int checkFeauture) {
        Context context = ComponentContext.getContext();
        if (context != null) {
            checkSDKFeature(context, new CheckSDKFeatureCallback() {
                public void check(int sdkFeature) {
                    if (!SDKFeauture.Maintaining.isEnable(sdkFeature)) {
                        if ((checkFeauture & sdkFeature) == checkFeauture) {
                            SDKDetectableCommander.this.invokeQmiWriteCmd(cmd, args);
                        }
                    }
                }
            });
        }
    }

    public void writeCmdWithCheckOrFeature(final String cmd, final Object args, final int checkFeauture) {
        Context context = ComponentContext.getContext();
        if (context != null) {
            checkSDKFeature(context, new CheckSDKFeatureCallback() {
                public void check(int sdkFeature) {
                    if (!SDKFeauture.Maintaining.isEnable(sdkFeature) && SDKDetectableCommander.this.hasBitOneValue(checkFeauture & sdkFeature)) {
                        SDKDetectableCommander.this.invokeQmiWriteCmd(cmd, args);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public boolean hasBitOneValue(int bits) {
        while (bits > 0) {
            bits &= bits - 1;
            if (bits > 0) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void notifyGameMessageEvent(final Context context, final int switchs) {
        LogUtil.i(TAG, "notifyGameMessageEvent sdkFeature :　" + switchs);
        runOnMainThread(new Runnable() {
            public void run() {
                GameMessageEventManager.getInstance(context).onCheckSupportedSDKFeatureCompletion(context, switchs);
            }
        });
    }

    /* access modifiers changed from: private */
    public void handlerSDKFeatureMessage(Context context, int sdkFeature) {
        if (this.sdkFeatureCache != sdkFeature) {
            this.sdkFeatureCache = sdkFeature;
        }
        LogUtil.i(TAG, "handlerSDKFeatureMessage sdkFeatureCache :　" + this.sdkFeatureCache);
        notifyCheckSDKFeatureCallback(sdkFeature);
        for (int i = 0; i < this.sendUnityMsgCount; i++) {
            notifyGameMessageEvent(context, this.sdkFeatureCache);
        }
        this.sendUnityMsgCount = 0;
    }

    private void notifyCheckSDKFeatureCallback(int sdkFeature) {
        Iterator<CheckSDKFeatureCallback> sListIterator = this.cacheCheckSDKFeatureCallbacks.iterator();
        while (sListIterator.hasNext()) {
            LogUtil.i(TAG, "notifyCheckSDKFeatureCallback sdkFeature :　" + sdkFeature);
            sListIterator.next().check(sdkFeature);
            sListIterator.remove();
        }
    }

    public void checkSDKFeature(final Context context, final CheckSDKFeatureCallback checkSDKFeatureCallback) {
        runOnMainThread(new Runnable() {
            public void run() {
                if (context != null) {
                    LogUtil.i(SDKDetectableCommander.TAG, "checkSDKFeature sdkFeatureCache :　" + SDKDetectableCommander.this.sdkFeatureCache + "  isCheckFeatureCalled:" + SDKDetectableCommander.this.isCheckFeatureCalled);
                    if (SDKDetectableCommander.this.isCheckFeatureCalled) {
                        LogUtil.i(SDKDetectableCommander.TAG, "checkSDKFeature sdkFeatureCache :　" + SDKDetectableCommander.this.sdkFeatureCache);
                        if (((long) SDKDetectableCommander.this.sdkFeatureCache) != -1) {
                            if (checkSDKFeatureCallback != null) {
                                checkSDKFeatureCallback.check(SDKDetectableCommander.this.sdkFeatureCache);
                            } else {
                                SDKDetectableCommander.this.notifyGameMessageEvent(context, SDKDetectableCommander.this.sdkFeatureCache);
                            }
                        } else if (checkSDKFeatureCallback != null) {
                            SDKDetectableCommander.this.cacheCheckSDKFeatureCallbacks.add(checkSDKFeatureCallback);
                        } else {
                            SDKDetectableCommander.access$508(SDKDetectableCommander.this);
                        }
                    } else {
                        boolean unused = SDKDetectableCommander.this.isCheckFeatureCalled = true;
                        if (checkSDKFeatureCallback != null) {
                            SDKDetectableCommander.this.cacheCheckSDKFeatureCallbacks.add(checkSDKFeatureCallback);
                        } else {
                            SDKDetectableCommander.access$508(SDKDetectableCommander.this);
                        }
                        LogUtil.i(SDKDetectableCommander.TAG, "no call checkSDKFeature sdkFeatureCache :　" + SDKDetectableCommander.this.sdkFeatureCache);
                        MGCSystemCore.init(context);
                        SDKDetectableCommander.this.checkSDKFeatrueInner(context);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkSDKFeatrueInner(final Context context) {
        try {
            if (!NetworkUtil.isNetworkAvailable(context)) {
                handlerSDKFeatureMessage(context, 0);
                return;
            }
            final boolean isDeviceEnable = DeviceDetectUtil.isDeviceEnable(context);
            LogUtil.i(TAG, "checkSDKFeature isDeviceEnable :　" + isDeviceEnable);
            PBProxyManager.getInstance().requestWhiteListInfo(getPluginVersionCode(QmiCorePluginManager.RECORDER_PLUGIN_ID), new BaseProxy.MessageListener() {
                public void onSuccess(Object... param) {
                    SDKDetectableCommander.this.videoBusId = param[1].intValue();
                    int sdkFeature = param[2].intValue();
                    LogUtil.i(SDKDetectableCommander.TAG, "sdkFeature is " + sdkFeature + ",videoBusId:" + SDKDetectableCommander.this.videoBusId);
                    if (!isDeviceEnable) {
                        sdkFeature = 0;
                    }
                    SDKDetectableCommander.this.handlerSDKFeatureMessage(context, sdkFeature);
                }

                public void onError(BaseProxy.ProxyStatus status, String msg) {
                    LogUtil.e(SDKDetectableCommander.TAG, "checkSDKFeature fail is " + status + ":" + msg);
                    SDKDetectableCommander.this.handlerSDKFeatureMessage(context, 0);
                }

                public void onTimeOut(Request request) {
                    LogUtil.e(SDKDetectableCommander.TAG, "checkSDKFeature onTimeOut === ");
                    SDKDetectableCommander.this.handlerSDKFeatureMessage(context, 0);
                }
            });
        } catch (Exception e) {
            handlerSDKFeatureMessage(context, 0);
            LogUtil.e(TAG, "checkSDKFeature fail : " + e.getMessage());
        }
    }

    public int getPluginVersionCode(String pluginId) {
        try {
            return QmiCorePluginManager.getInstance().getCurPluginVersion();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "getPluginVersionCode fail: " + e.getMessage());
            return 0;
        }
    }
}
