package com.tencent.imsdk.notice.api;

import com.tencent.imsdk.notice.entity.IMNoticeInfo;

public class IMNoticeNativeListener {
    public static native void onLoadNoticeCallback(IMNoticeInfo iMNoticeInfo);

    public static native void onShowNoticeCallback(IMNoticeInfo iMNoticeInfo);
}
