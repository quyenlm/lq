package com.tencent.imsdk.feedback.base;

import android.content.Context;

public interface IUnreadMessage {

    public interface UnreadMessageListener {
        void getUnreadMessageCount(IMFeedbackResult iMFeedbackResult);
    }

    void getUnreadMessage(Context context, UnreadMessageListener unreadMessageListener);
}
