package com.tencent.qqgamemi.api;

import android.content.Context;
import android.util.Log;
import com.tencent.qqgamemi.CheckSDKFeatureCallback;
import com.tencent.qqgamemi.QmiSdkApi;
import com.tencent.qqgamemi.QmiSdkApiProxy;
import java.util.List;
import java.util.Map;

public class GameJoyAssistant {
    private static final String DEFAULT_GAME_ENGINE_TYPE = "cocos2d";

    public static void initialized(Context context) {
        QmiSdkApiProxy.initSDK(context, DEFAULT_GAME_ENGINE_TYPE);
    }

    public static void initialized(Context context, String gameEngineType) {
        QmiSdkApiProxy.initSDK(context, gameEngineType);
    }

    public static void setDefaultStartPosition(float x, float y) {
        QmiSdkApiProxy.getInstance().setDefaultStartPosition(x, y);
    }

    public static void startRecorder(Context context) {
        QmiSdkApiProxy.getInstance().startRecorder(context);
    }

    public static void stopRecorder(Context context) {
        QmiSdkApiProxy.getInstance().stopRecorder(context);
    }

    public static void startMomentsRecording(Context context) {
        QmiSdkApiProxy.getInstance().startMomentsRecording(context);
    }

    public static void endMomentsRecording() {
        QmiSdkApiProxy.getInstance().endMomentsRecording();
    }

    public static void generateMomentsVideo(List<TimeStamp> timeStampList, String title, Map<String, String> extraInfo) {
        QmiSdkApiProxy.getInstance().generateMomentsVideo(timeStampList, title, extraInfo);
    }

    public static void generateMomentsVideo(String[] titleArray, int[] priorityArray, long[] startTimeArray, long[] endTimeArray, String title, String extraInfoStr) {
        QmiSdkApiProxy.getInstance();
        QmiSdkApiProxy.generateMomentsVideo(titleArray, priorityArray, startTimeArray, endTimeArray, title, extraInfoStr);
    }

    public static void generateMomentsVideo(List<TimeStamp> shortVideoTime, List<TimeStamp> largeVideoTime, String title, Map<String, String> extraInfo) {
        QmiSdkApiProxy.getInstance().generateMomentsVideo(shortVideoTime, largeVideoTime, title, extraInfo);
    }

    public static void generateMomentsVideo(String[] titleArray, int[] priorityArray, long[] shortVideosStartTimeArray, long[] shortVideosEndTimeArray, long[] largeVideosStartTimeArray, long[] largeVideoEndTimeArray, String title, String extraInfoStr) {
        QmiSdkApiProxy.getInstance();
        QmiSdkApiProxy.generateMomentsVideo(titleArray, priorityArray, shortVideosStartTimeArray, shortVideosEndTimeArray, largeVideosStartTimeArray, largeVideoEndTimeArray, title, extraInfoStr);
    }

    public static void closeGenerateMomentsVideoDialog() {
        QmiSdkApiProxy.getInstance().closeGenerateMomentsVideoDialog();
    }

    public static void checkSDKFeature(Context context) {
        QmiSdkApiProxy.checkSDKFeature(context);
    }

    public static void checkSDKFeature(Context context, CheckSDKFeatureCallback checkSDKFeatureCallback) {
        QmiSdkApiProxy.checkSDKFeature(context, checkSDKFeatureCallback);
    }

    static boolean isShowed() {
        return QmiSdkApiProxy.getInstance().isShowed();
    }

    public static void showVideoListDialog(Context context) {
        QmiSdkApiProxy.getInstance().showVideoListDialog(context);
    }

    public static long getCurMomentSourceVideoDuration() {
        return QmiSdkApiProxy.getInstance().getCurMomentSourceVideoDuration();
    }

    public static void closeVideoListDialog() {
        QmiSdkApiProxy.getInstance().closeVideoListDialog();
    }

    public static int getSrpVersionCode() {
        return QmiSdkApiProxy.getSrpVersionCode();
    }

    public static void startJudgementRecording(Context context) {
        QmiSdkApiProxy.getInstance().startJudgementRecording(context);
    }

    public static void stopJudgementRecording(String userName, Map<String, String> extraInfo) {
        QmiSdkApiProxy.getInstance().stopJudgementRecording(userName, extraInfo);
    }

    public static void checkSDKPermission(Context context) {
        QmiSdkApiProxy.checkSDKPermission(context);
    }

    public static void setVideoQuality(Context context, VideoQuality quality) {
        QmiSdkApiProxy.getInstance().setVideoQuality(context, quality);
    }

    public static void setVideoQuality(Context context, int flag) {
        QmiSdkApiProxy.getInstance();
        QmiSdkApiProxy.setVideoQuality(context, flag);
    }

    public static void setRecorderAudioSource(Context context, AudioSource audioSource) {
        QmiSdkApiProxy.getInstance().setRecorderAudioSource(context, audioSource);
    }

    public static void setRecorderAudioSource(Context context, int audioSource) {
        QmiSdkApiProxy.getInstance().setRecorderAudioSource(context, audioSource);
    }

    public static boolean isRecording() {
        return QmiSdkApiProxy.getInstance().isRecording();
    }

    public static boolean isRecordingMoments() {
        return QmiSdkApiProxy.getInstance().isRecordingMoments();
    }

    public static boolean isRecordingJudgement() {
        return QmiSdkApiProxy.getInstance().isRecordingJudgement();
    }

    public static void setCurrentRecorderPosition(float x, float y) {
        QmiSdkApiProxy.getInstance().setCurrentRecorderPosition(x, y);
    }

    public static RecorderPosition currentRecorderPosition() {
        return QmiSdkApiProxy.getInstance().currentRecorderPosition();
    }

    public static void lockRecorderPosition() {
        QmiSdkApiProxy.getInstance().lockRecorderPosition();
    }

    public static void unLockRecorderPosition() {
        QmiSdkApiProxy.getInstance().unLockRecorderPosition();
    }

    public static void setDefaultUploadShareDialogPosition(float x, float y) {
        QmiSdkApiProxy.getInstance().setDefaultUploadShareDialogPosition(x, y);
    }

    public static long getSystemCurrentTimeMillis() {
        return QmiSdkApiProxy.getSystemCurrentTimeMillis();
    }

    public static double getAvailableDeviceSpaceMB() {
        return QmiSdkApiProxy.getAvailableDeviceSpaceMB();
    }

    public static void configSDK(int config) {
        QmiSdkApiProxy.configSDK(config);
    }

    public static void refreshLogin(Context context, String imsdk_ticket, long openid, int locationid, String nickName, String picUrl) {
        QmiSdkApiProxy.refreshLogin(context, imsdk_ticket, openid, locationid, nickName, picUrl);
    }

    public static void setLogLevel(int level) {
        QmiSdkApiProxy.setLogLevel(level);
    }

    public static void setAWSConfig(String bucketName, String poolID, String region, String cdnPathConf) {
        QmiSdkApiProxy.setAWSConfig(bucketName, poolID, region, cdnPathConf);
    }

    public static void setLocale(Context context, String language, String country) {
        Log.i(QmiSdkApi.TAG, "setLocale called: " + language + ", " + country);
        QmiSdkApiProxy.setLocale(context, language, country);
    }
}
