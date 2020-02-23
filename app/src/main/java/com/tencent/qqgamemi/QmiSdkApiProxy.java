package com.tencent.qqgamemi;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.api.AudioSource;
import com.tencent.qqgamemi.api.RecorderPosition;
import com.tencent.qqgamemi.api.TimeStamp;
import com.tencent.qqgamemi.api.VideoQuality;
import com.tencent.qqgamemi.util.GlobalUtil;
import java.util.List;
import java.util.Map;

public class QmiSdkApiProxy {
    private static volatile QmiSdkApiProxy sInstance;
    private String TAG = "QmiSdkApiProxy";

    public static QmiSdkApiProxy getInstance() {
        if (sInstance == null) {
            synchronized (QmiSdkApiProxy.class) {
                if (sInstance == null) {
                    sInstance = new QmiSdkApiProxy();
                }
            }
        }
        return sInstance;
    }

    public QmiSdkApiProxy() {
        Context context = GlobalUtil.getGlobalContext();
        if (context != null) {
            QmiSdkApi.initQMi(context);
        } else {
            LogUtil.e(this.TAG, "initQmi is fail because context is null!");
        }
    }

    public static void initSDK(Context context, String gameEngineType) {
        QmiSdkApi.initSDK(context, gameEngineType);
    }

    public void initQmi(Context context) {
        QmiSdkApi.initQMi(context);
    }

    public void setDefaultStartPosition(float x, float y) {
        QmiSdkApi.setDefaultStartPosition(x, y);
    }

    public void startRecorder(Context context) {
        QmiSdkApi.showQMi(context);
    }

    public void stopRecorder(Context context) {
        QmiSdkApi.stopQMi(context);
    }

    public void startMomentsRecording(Context context) {
        QmiSdkApi.startMomentRecording(context);
    }

    public void endMomentsRecording() {
        QmiSdkApi.endMomentRecording();
    }

    public void generateMomentsVideo(List<TimeStamp> timeStampList, String title, Map<String, String> extraInfo) {
        String extraInfoStr = mapToString(extraInfo);
        long[] startTimeArray = null;
        long[] endTimeArray = null;
        int[] priorityArray = null;
        String[] titleArray = null;
        if (timeStampList != null && timeStampList.size() > 0) {
            int length = timeStampList.size();
            startTimeArray = new long[length];
            endTimeArray = new long[length];
            priorityArray = new int[length];
            titleArray = new String[length];
            TimeStamp[] timeStampArray = (TimeStamp[]) timeStampList.toArray(new TimeStamp[0]);
            for (int i = 0; i < length; i++) {
                startTimeArray[i] = timeStampArray[i].startTime;
                endTimeArray[i] = timeStampArray[i].endTime;
                priorityArray[i] = timeStampArray[i].priority.getCode();
                titleArray[i] = timeStampArray[i].title;
            }
        }
        QmiSdkApi.generateMomentVideo(titleArray, priorityArray, startTimeArray, endTimeArray, title, extraInfoStr);
    }

    public static void generateMomentsVideo(String[] titleArray, int[] priorityArray, long[] startTimeArray, long[] endTimeArray, String title, String extraInfoStr) {
        QmiSdkApi.generateMomentVideo(titleArray, priorityArray, startTimeArray, endTimeArray, title, extraInfoStr);
    }

    public void generateMomentsVideo(List<TimeStamp> shortVideoTime, List<TimeStamp> largeVideoTime, String title, Map<String, String> extraInfo) {
        String extraInfoStr = mapToString(extraInfo);
        long[] shortVideoStartTimeArray = null;
        long[] shortVideoEndTimeArray = null;
        int[] shortVideoPriorityArray = null;
        String[] shortVideoTitleArray = null;
        if (shortVideoTime != null && shortVideoTime.size() > 0) {
            int length = shortVideoTime.size();
            shortVideoStartTimeArray = new long[length];
            shortVideoEndTimeArray = new long[length];
            shortVideoPriorityArray = new int[length];
            shortVideoTitleArray = new String[length];
            TimeStamp[] timeStampArray = (TimeStamp[]) shortVideoTime.toArray(new TimeStamp[0]);
            for (int i = 0; i < length; i++) {
                shortVideoStartTimeArray[i] = timeStampArray[i].startTime;
                shortVideoEndTimeArray[i] = timeStampArray[i].endTime;
                shortVideoPriorityArray[i] = timeStampArray[i].priority.getCode();
                shortVideoTitleArray[i] = timeStampArray[i].title;
            }
        }
        long[] largeVideoStartTimeArray = null;
        long[] largeVideoEndTimeArray = null;
        if (largeVideoTime != null && largeVideoTime.size() > 0) {
            int length2 = largeVideoTime.size();
            largeVideoStartTimeArray = new long[length2];
            largeVideoEndTimeArray = new long[length2];
            TimeStamp[] timeStampArray2 = (TimeStamp[]) largeVideoTime.toArray(new TimeStamp[0]);
            for (int i2 = 0; i2 < length2; i2++) {
                largeVideoStartTimeArray[i2] = timeStampArray2[i2].startTime;
                largeVideoEndTimeArray[i2] = timeStampArray2[i2].endTime;
            }
        }
        QmiSdkApi.generateMomentVideo(shortVideoTitleArray, shortVideoPriorityArray, shortVideoStartTimeArray, shortVideoEndTimeArray, largeVideoStartTimeArray, largeVideoEndTimeArray, title, extraInfoStr);
    }

    public static void generateMomentsVideo(String[] titleArray, int[] priorityArray, long[] shortVideosStartTimeArray, long[] shortVideosEndTimeArray, long[] largeVideosStartTimeArray, long[] largeVideoEndTimeArray, String title, String extraInfoStr) {
        QmiSdkApi.generateMomentVideo(titleArray, priorityArray, shortVideosStartTimeArray, shortVideosEndTimeArray, largeVideosStartTimeArray, largeVideoEndTimeArray, title, extraInfoStr);
    }

    private String mapToString(Map<String, String> map) {
        int last;
        String result = null;
        if (map != null && map.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append("'");
                sb.append(entry.getValue());
                sb.append("^");
            }
            result = sb.toString();
            if (result != null && (last = result.lastIndexOf("^")) > 0) {
                result = result.substring(0, last);
            }
        }
        return result != null ? result : "";
    }

    public void showQmi(Context context) {
        QmiSdkApi.showQMi(context);
    }

    public void hideQmi(Context context) {
        QmiSdkApi.hideQMi(context);
    }

    public static void checkSDKFeature(Context context) {
        QmiSdkApi.checkSDKFeature(context, (CheckSDKFeatureCallback) null);
    }

    public static void checkSDKFeature(Context context, CheckSDKFeatureCallback checkSDKFeatureCallback) {
        QmiSdkApi.checkSDKFeature(context, checkSDKFeatureCallback);
    }

    public boolean isShowed() {
        return QmiSdkApi.isShowed();
    }

    public void showVideoListDialog(Context context) {
        QmiSdkApi.showVideoListDialog(context);
    }

    public long getCurMomentSourceVideoDuration() {
        return QmiSdkApi.getMomentSourceVideoDuration();
    }

    public void closeVideoListDialog() {
        QmiSdkApi.closeVideoListDialog();
    }

    public static int getSrpVersionCode() {
        return QmiSdkApi.getSRPpluginVersionCode();
    }

    public void startJudgementRecording(Context context) {
        QmiSdkApi.startJudgementRecording(context);
    }

    public void stopJudgementRecording(String userName, Map<String, String> extraInfo) {
        QmiSdkApi.stopJudgementRecording(userName, mapToString(extraInfo));
    }

    public static void checkSDKPermission(Context context) {
        QmiSdkApi.checkSDKPermission(context);
    }

    public void setVideoQuality(Context context, VideoQuality quality) {
        QmiSdkApi.setVideoQuality(context, quality.intValue());
    }

    public static void setVideoQuality(Context context, int flag) {
        QmiSdkApi.setVideoQuality(context, flag);
    }

    public void setRecorderAudioSource(Context context, AudioSource audioSource) {
        QmiSdkApi.setAudioSource(context, audioSource.intValue());
    }

    public void setRecorderAudioSource(Context context, int audioSource) {
        QmiSdkApi.setAudioSource(context, audioSource);
    }

    public boolean isRecording() {
        return QmiSdkApi.isRecording();
    }

    public boolean isRecordingMoments() {
        return QmiSdkApi.isRecordingMoment();
    }

    public boolean isRecordingJudgement() {
        return QmiSdkApi.isRecordingJudgement();
    }

    public void setCurrentRecorderPosition(float x, float y) {
        QmiSdkApi.setCurRecorderPosition(x, y);
    }

    public RecorderPosition currentRecorderPosition() {
        return getCurRecorderPosition();
    }

    private RecorderPosition getCurRecorderPosition() {
        String[] parts;
        String position = QmiSdkApi.getCurRecorderPosition();
        if (TextUtils.isEmpty(position)) {
            return new RecorderPosition(0.0f, 0.0f);
        }
        if (position.contains(",") && (parts = position.split(",")) != null && parts.length > 1) {
            try {
                return new RecorderPosition(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return new RecorderPosition(0.0f, 0.0f);
    }

    public void lockRecorderPosition() {
        QmiSdkApi.lockRecorderPosition();
    }

    public void unLockRecorderPosition() {
        QmiSdkApi.unLockRecorderPosition();
    }

    public void setDefaultUploadShareDialogPosition(float x, float y) {
        QmiSdkApi.setUploadShareDialogPosition(x, y);
    }

    public void closeGenerateMomentsVideoDialog() {
        QmiSdkApi.closeGenerateMomentsVideoDialog();
    }

    public static long getSystemCurrentTimeMillis() {
        return QmiSdkApi.getSystemCurrentTimeMillis();
    }

    public static double getAvailableDeviceSpaceMB() {
        return QmiSdkApi.getAvailableDeviceSpaceMB();
    }

    public static void configSDK(int config) {
        QmiSdkApi.configSDK(config);
    }

    public static void refreshLogin(Context context, String imsdk_ticket, long openid, int locationid, String nickName, String picUrl) {
        QmiSdkApi.refreshLogin(context, imsdk_ticket, openid, locationid, nickName, picUrl);
    }

    public static void setLogLevel(int level) {
        QmiSdkApi.setLogLevel(level);
    }

    public static void setAWSConfig(String bucketName, String poolID, String region, String cdnPathConf) {
        QmiSdkApi.setAWSConfig(bucketName, poolID, region, cdnPathConf);
    }

    public static void setLocale(Context context, String language, String country) {
        QmiSdkApi.setLocale(context, language, country);
    }
}
