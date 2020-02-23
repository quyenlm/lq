package com.tencent.qqgamemi;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.component.plugin.server.PluginConstant;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.language.LanguageUtil;
import com.tencent.qqgamemi.srp.aws.util.GlobalConfig;
import com.tencent.qqgamemi.util.DeviceDetectUtil;
import com.tencent.qqgamemi.util.GlobalUtil;
import java.util.Locale;

public class QmiSdkApi {
    public static final String TAG = "QmiSdkApi";
    private static int logLevel = -1;
    private static float pluginX = -1.0f;
    private static float pluginY = -1.0f;
    private static float uploadShareDialogX = 0.0f;
    private static float uploadShareDialogY = 0.0f;

    public static void initSDK(Context context) {
        SDKApiHelper.getInstance().initSDK(context);
    }

    public static void initSDK(Context context, String gameEngineType) {
        GlobalUtil.setContext(context);
        GlobalUtil.setGameEngineType(gameEngineType);
        SDKApiHelper.getInstance().initSDK(context);
    }

    public static void initQMi(Context context) {
        initQMi(context, GlobalUtil.getGameEngineType());
    }

    public static void initQMi(Context context, String gameEngineType) {
        SDKApiHelper.getInstance().initPlugin(context, gameEngineType, logLevel);
    }

    public static void setDefaultStartPosition(float x, float y) {
        pluginX = x;
        pluginY = y;
    }

    public static void showQMi(Context context) {
        showQMi(context, GlobalUtil.getGameEngineType());
    }

    public static void showQMi(Context context, String gameEngineType) {
        showQMi(context, gameEngineType, pluginX, pluginY);
    }

    public static void showQMi(Context context, float x, float y) {
        showQMi(context, GlobalUtil.getGameEngineType(), x, y);
    }

    public static void showQMi(Context context, String gameEngineType, float x, float y) {
        SDKApiHelper.getInstance().showRecorder(context, gameEngineType, x, y, logLevel);
    }

    public static void hideQMi(Context context) {
        SDKApiHelper.getInstance().writeManualFeatureCmd(SDKApiCMD.CMD_HIDE_QMI, context);
    }

    public static void stopQMi(Context context) {
        SDKApiHelper.getInstance().writeManualFeatureCmd(SDKApiCMD.CMD_STOP_QMI, context);
    }

    public static void startMomentRecording(Context context) {
        SDKApiHelper.getInstance().startMomentRecording(context, GlobalUtil.getGameEngineType(), logLevel);
    }

    public static void startMomentRecording(Context context, String gameEngineType) {
        SDKApiHelper.getInstance().startMomentRecording(context, gameEngineType, logLevel);
    }

    public static long getSystemCurrentTimeMillis() {
        try {
            return System.currentTimeMillis();
        } catch (Exception e) {
            LogUtil.e(TAG, "getSystemCurrentTimeMillis erro:" + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public static void endMomentRecording() {
        LogUtil.i(TAG, "endMomentRecording:");
        SDKApiHelper.getInstance().writeMomentFeatureCmd(SDKApiCMD.CMD_END_MOMENT_RECORDING, (Object) null);
    }

    public static void generateMomentVideo(String[] titleArray, int[] priorityArray, long[] startTimeArray, long[] endTimeArray, String defaultGameTag, String extraInfoStr) {
        LogUtil.i(TAG, "generateMomentVideo:" + defaultGameTag + "," + extraInfoStr);
        SDKApiHelper.getInstance().writeMomentFeatureCmd(SDKApiCMD.CMD_GENERATE_MOMENT_VIDEO, new Object[]{Float.valueOf(uploadShareDialogX), Float.valueOf(uploadShareDialogY), titleArray, priorityArray, startTimeArray, endTimeArray, defaultGameTag, extraInfoStr});
    }

    public static void generateMomentVideo(String[] titleArray, int[] priorityArray, long[] shortVideosStartTimeArray, long[] shortVideosEndTimeArray, long[] largeVideosStartTimeArray, long[] largeVideoEndTimeArray, String defaultGameTag, String extraInfoStr) {
        LogUtil.i(TAG, "generateMomentVideos:" + defaultGameTag + "," + extraInfoStr);
        SDKApiHelper.getInstance().writeMomentFeatureCmd(SDKApiCMD.CMD_GENERATE_MOMENT_VIDEO, new Object[]{Float.valueOf(uploadShareDialogX), Float.valueOf(uploadShareDialogY), titleArray, priorityArray, shortVideosStartTimeArray, shortVideosEndTimeArray, largeVideosStartTimeArray, largeVideoEndTimeArray, defaultGameTag, extraInfoStr});
    }

    public static void setUploadShareDialogPosition(float x, float y) {
        uploadShareDialogX = x;
        uploadShareDialogY = y;
        LogUtil.i(TAG, "x,y:" + x + "," + y);
    }

    public static void showUploadShareVideoDialog() {
        SDKApiHelper.getInstance().writeMomentFeatureCmd(SDKApiCMD.CMD_SHOW_UPLOAD_SHARE_VIDEO_DIALOG, new Object[]{Float.valueOf(uploadShareDialogX), Float.valueOf(uploadShareDialogY)});
    }

    public static void closeGenerateMomentsVideoDialog() {
        SDKApiHelper.getInstance().writeMomentFeatureCmd(SDKApiCMD.CMD_CLOSE_UPLOAD_SHARE_VIDEO_DIALOG, (Object) null);
    }

    public static void showVideoListDialog(Context context) {
        SDKApiHelper.getInstance().showVideoListDialog(context);
    }

    public static void closeVideoListDialog() {
        SDKApiHelper.getInstance().writeManualOrMomentFeatureCmd(SDKApiCMD.CMD_CLOSE_VIDEO_LIST_DIALOG, (Object) null);
    }

    public static void setVideoQuality(Context context, int flag) {
        SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_SET_VIDEO_QUALITY, new Object[]{context, Integer.valueOf(flag)});
    }

    public static void setAudioSource(Context context, int audioSource) {
        SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_SET_AUDIO_SOURCE, new Object[]{context, Integer.valueOf(audioSource)});
    }

    public static long getMomentSourceVideoDuration() {
        return ((Long) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_GET_MOMENT_SOURCE_VIDEO_DURATION, -1L)).longValue();
    }

    public static void setGameEngineType(String gameEngineType) {
        GlobalUtil.setGameEngineType(gameEngineType);
        SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_SET_GAMEENGINE_TYPE, gameEngineType);
    }

    public static int callMethod(int nMethodID, int nParam1, int nParam2, int nParam3, int nParam4, int nParam5) {
        return ((Integer) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_CALL_METHOD, new int[]{nMethodID, nParam1, nParam2, nParam3, nParam4, nParam5}, 0)).intValue();
    }

    public static int getVersionCode() {
        return 600;
    }

    public static String getVersionName() {
        return PluginConstant.PLUGIN_PLATFROM_VERSION_NAME;
    }

    public static int beginDraw() {
        return ((Integer) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_BEGIN_DRAW, 0)).intValue();
    }

    public static int endDraw() {
        return ((Integer) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_END_DRAW, 0)).intValue();
    }

    public static boolean isRecording() {
        return ((Boolean) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_IS_RECORDING, false)).booleanValue();
    }

    public static boolean isRecordingMoment() {
        return ((Boolean) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_IS_RECORDING_MOMENT, false)).booleanValue();
    }

    public static boolean isRecordingJudgement() {
        return ((Boolean) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_IS_JUDGEMENT_RECORDING, false)).booleanValue();
    }

    public static boolean isShowed() {
        return ((Boolean) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_IS_SHOWED, false)).booleanValue();
    }

    public static void startJudgementRecording(Context context) {
        SDKApiHelper.getInstance().startJudgementRecording(context, GlobalUtil.getGameEngineType());
    }

    public static void startJudgementRecording(Context context, String gameEngineType) {
        SDKApiHelper.getInstance().startJudgementRecording(context, gameEngineType);
    }

    public static void stopJudgementRecording(String userName, String extraInfoStr) {
        SDKApiHelper.getInstance().writeReportFeatureCmd(SDKApiCMD.CMD_STOP_JUDGEMENT_RECORDING, new Object[]{userName, extraInfoStr});
    }

    public static void onStartRecordVideo() {
        onStartRecordVideo(0);
    }

    public static void onStartRecordVideo(long ptr) {
        LogUtil.i(TAG, "onStartRecordVideo with long_ptr: " + ptr);
        SDKApiHelper.getInstance().writeManualOrMomentOrReportFeatureCmd(SDKApiCMD.CMD_ON_START_RECORD, Long.valueOf(ptr));
    }

    public static void onStopRecordVideo() {
        SDKApiHelper.getInstance().writeManualOrMomentOrReportFeatureCmd(SDKApiCMD.CMD_ON_STOP_RECORD, (Object) null);
    }

    public static void checkSDKFeature(Context context) {
        checkSDKFeature(context, (CheckSDKFeatureCallback) null);
    }

    public static void checkSDKFeature(Context context, CheckSDKFeatureCallback checkSDKFeatureCallback) {
        SDKApiHelper.getInstance().checkSDKFeature(context, checkSDKFeatureCallback);
    }

    public static void configSDK(int config) {
        SDKApiHelper.getInstance().writeManualOrMomentFeatureCmd(SDKApiCMD.CMD_CONFIG_SDK, Integer.valueOf(config));
    }

    public static void onUpdateVideoFrame() {
        SDKApiHelper.getInstance().writeManualOrMomentOrReportFeatureCmd(SDKApiCMD.CMD_ON_UPDATE_VIDEO_FRAME, (Object) null);
    }

    public static void notifyQmiService(Context context, int operation, Bundle args) {
        SDKApiHelper.getInstance().writeManualOrMomentOrReportFeatureCmd(SDKApiCMD.CMD_START_QMI_SERVICE, new Object[]{context, Integer.valueOf(operation), args});
    }

    public static int getSRPpluginVersionCode() {
        return SDKApiHelper.getInstance().getPluginVersionCode(QmiCorePluginManager.RECORDER_PLUGIN_ID);
    }

    public static void lockRecorderPosition() {
        SDKApiHelper.getInstance().writeManualFeatureCmd(SDKApiCMD.CMD_LOCK_POSITION, (Object) null);
    }

    public static void unLockRecorderPosition() {
        SDKApiHelper.getInstance().writeManualFeatureCmd(SDKApiCMD.CMD_UNLOCK_POSITION, (Object) null);
    }

    public static void setCurRecorderPosition(float x, float y) {
        SDKApiHelper.getInstance().writeManualFeatureCmd(SDKApiCMD.CMD_SET_RECORDER_POSITION, new Object[]{Float.valueOf(x), Float.valueOf(y)});
    }

    public static String getCurRecorderPosition() {
        return (String) SDKApiHelper.getInstance().readCmdSafe(SDKApiCMD.CMD_GET_RECORDER_CURPOSITION, null);
    }

    public static void checkSDKPermission(Context context) {
        SDKApiHelper.getInstance().checkPermission(context);
    }

    protected static void onBackground() {
        SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_BACKGROUD, (Object) null);
    }

    protected static void onFront() {
        SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_FRONT, (Object) null);
    }

    public static void setLogLevel(int level) {
        logLevel = level;
        Log.i(TAG, "setLogLevel: " + level);
        SDKApiHelper.getInstance().setLogLevel(level);
    }

    public static void setAWSConfig(String bucketName, String poolID, String region, String cdnPathConf) {
        Log.i(TAG, "setAWSConfig: " + bucketName + ", " + poolID + "," + region + "," + cdnPathConf);
        GlobalConfig.getInstance().setAWSConfig(bucketName, poolID, region, cdnPathConf);
    }

    public static void refreshLogin(Context context, String imsdk_ticket, long openid, int locationid, String nickName, String picUrl) {
        Log.i(TAG, "imsdk_ticket:" + imsdk_ticket + ",openid:" + openid + ",locationid:" + locationid + ",nickName:" + nickName + ",picUrl:" + picUrl);
        GlobalConfig.getInstance().setOpenId(openid);
        if (MSDKManager.getInstance().refreshMSDKTicket(imsdk_ticket, openid, locationid)) {
            SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_LOGIN, new Object[]{context, imsdk_ticket, Long.valueOf(openid), Integer.valueOf(locationid), nickName, picUrl});
        }
    }

    public static double getAvailableDeviceSpaceMB() {
        return DeviceDetectUtil.Space.getExternalAvailableSpaceInMB();
    }

    public static void setLocale(Context context, String language, String country) {
        Locale locale;
        LogUtil.i(TAG, "setLocale : " + language + " , " + country);
        if (!TextUtils.isEmpty(language) && (locale = LanguageUtil.createLocale(language, country)) != null) {
            SDKApiHelper.getInstance().invokeQmiWriteCmd(SDKApiCMD.CMD_SET_LOCALE, new Object[]{context, locale});
        }
    }
}
