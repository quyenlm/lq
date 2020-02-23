package com.tencent.imsdk.unity.notice;

import android.app.Activity;
import android.content.Context;
import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.notice.api.IMNotice;
import com.tencent.imsdk.notice.api.IMNoticeListener;
import com.tencent.imsdk.notice.entity.IMNoticeResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;

public class NoticeHelper extends IMNotice {
    /* access modifiers changed from: private */
    public static volatile Context mCurContext = null;
    /* access modifiers changed from: private */
    public static volatile String mUnityGameObject = "Tencent.iMSDK.IMNotice";

    public static void initialize() {
        try {
            mCurContext = UnityPlayer.currentActivity;
            IMNotice.initialize(mCurContext);
        } catch (Exception e) {
            IMLogger.e("initialize get error : " + e.getMessage());
        }
    }

    public static void loadNotice(final int callbackTag, final String funcName, String version, String language, int region, int partition, boolean isUseCache, int noticeType, String extraJson) {
        try {
            IMNotice.loadNoticeData(version, language, region, partition, isUseCache, noticeType, extraJson);
            IMNotice.setListener(new IMNoticeListener() {
                public void onLoadNoticeCallback(final IMNoticeResult result) {
                    ((Activity) NoticeHelper.mCurContext).runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                IMLogger.d("loadNotice finished, calling unity : " + result.toUnityString());
                                UnityPlayer.UnitySendMessage(NoticeHelper.mUnityGameObject, funcName, callbackTag + "|" + result.toUnityString());
                            } catch (Exception e) {
                                IMLogger.e("error parse loadNotice result : " + e.getMessage());
                                UnityPlayer.UnitySendMessage(NoticeHelper.mUnityGameObject, funcName, callbackTag + "|" + "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            IMLogger.e("setListener error : " + e.getMessage());
        }
    }

    public static void loadNotice(final int callbackTag, final String funcName, String noticeId, int loadDataType, String scene, int noticeType, String extraJson) {
        try {
            IMNotice.loadNoticeData(noticeId, loadDataType, scene, noticeType, extraJson);
            IMNotice.setListener(new IMNoticeListener() {
                public void onLoadNoticeCallback(final IMNoticeResult result) {
                    ((Activity) NoticeHelper.mCurContext).runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                IMLogger.d("loadNotice finished, calling unity : " + result.toUnityString());
                                UnityPlayer.UnitySendMessage(NoticeHelper.mUnityGameObject, funcName, callbackTag + "|" + result.toUnityString());
                            } catch (Exception e) {
                                IMLogger.e("error parse loadNotice result : " + e.getMessage());
                                UnityPlayer.UnitySendMessage(NoticeHelper.mUnityGameObject, funcName, callbackTag + "|" + "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            IMLogger.e("setListener error : " + e.getMessage());
        }
    }

    public static void showNotice(final int callbackTag, final String funcName, String noticeId, int noticeType, String scene, String extraJson) {
        try {
            IMNotice.showNotice(noticeId, noticeType, scene, extraJson);
            IMNotice.setListener(new IMNoticeListener() {
                public void onLoadNoticeCallback(final IMNoticeResult result) {
                    ((Activity) NoticeHelper.mCurContext).runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                IMLogger.d("showNotice finished, calling unity : " + result.toUnityString());
                                UnityPlayer.UnitySendMessage(NoticeHelper.mUnityGameObject, funcName, callbackTag + "|" + result.toUnityString());
                            } catch (Exception e) {
                                IMLogger.e("error parse showNotice result : " + e.getMessage());
                                UnityPlayer.UnitySendMessage(NoticeHelper.mUnityGameObject, funcName, callbackTag + "|" + "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            IMLogger.e("setListener error : " + e.getMessage());
        }
    }
}
