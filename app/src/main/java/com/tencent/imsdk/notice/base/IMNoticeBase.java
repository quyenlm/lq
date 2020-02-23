package com.tencent.imsdk.notice.base;

import android.content.Context;
import com.tencent.imsdk.notice.api.IMNoticeListener;
import com.tencent.imsdk.tool.etc.IMLogger;

public abstract class IMNoticeBase {
    public Context context;

    public abstract void init(Context context2);

    public abstract void setListener(IMNoticeListener iMNoticeListener);

    public void showNotice(String noticeId, int noticeType, String scene, String extraJson) {
        IMLogger.d("not support showNotice");
    }

    public void loadNoticeData(String noticeId, int loadDataType, String scene, int noticeType, String extraJson) {
        IMLogger.d("not support loadNoticeData");
    }

    public void loadNoticeData(String version, String language, int region, int partition, boolean isUseCache, int noticeType, String extraJson) {
        IMLogger.d("not support loadNoticeData()");
    }

    public void setUserData(String target, String extraJson) {
        IMLogger.d("not support setUserData");
    }

    public void syncUserDataToSvr(String extraJson) {
        IMLogger.d("not support syncUserDataToSvr");
    }

    public void closeNotice(String noticeId, int closeType, String extraJson) {
        IMLogger.d("not support closeNotice");
    }

    protected IMNoticeBase() {
    }

    public String toString() {
        return "IMNoticeBase{context=" + this.context + '}';
    }

    public void initialize(Context ctx) {
        this.context = ctx;
    }
}
