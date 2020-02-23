package com.tencent.imsdk.notice.api;

import com.tencent.imsdk.notice.entity.IMNoticeResult;

public abstract class IMNoticeListener {
    public void onShowNoticeCallback(IMNoticeResult result) {
    }

    public void onLoadNoticeCallback(IMNoticeResult result) {
    }
}
