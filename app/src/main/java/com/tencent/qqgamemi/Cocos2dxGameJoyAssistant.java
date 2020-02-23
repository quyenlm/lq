package com.tencent.qqgamemi;

import android.content.Context;
import com.tencent.qqgamemi.api.RecorderPosition;
import com.tencent.qqgamemi.api.TimeStamp;
import com.tencent.qqgamemi.api.VideoQuality;
import java.util.List;
import java.util.Map;

public class Cocos2dxGameJoyAssistant {
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

    public static void generateMomentsVideo(List<TimeStamp> shortVideoTime, List<TimeStamp> largeVideoTime, String title, Map<String, String> extraInfo) {
        QmiSdkApiProxy.getInstance().generateMomentsVideo(shortVideoTime, largeVideoTime, title, extraInfo);
    }

    public static void checkSDKFeature(Context context) {
        QmiSdkApiProxy.checkSDKFeature(context);
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

    public static void refreshLogin(Context context, String imsdk_ticket, long openid, int locationid, String nickName, String picUrl) {
        QmiSdkApiProxy.refreshLogin(context, imsdk_ticket, openid, locationid, nickName, picUrl);
    }

    public static void setLogLevel(int level) {
        QmiSdkApiProxy.setLogLevel(level);
    }
}
