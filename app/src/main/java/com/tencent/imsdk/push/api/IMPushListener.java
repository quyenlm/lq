package com.tencent.imsdk.push.api;

import com.tencent.imsdk.IMResult;

public interface IMPushListener {
    void OnDeleteTag(IMResult iMResult);

    void OnNotifactionClick(String str);

    void OnNotifactionShow(String str);

    void OnNotification(String str);

    void OnRegister(IMResult iMResult);

    void OnSetTag(IMResult iMResult);

    void OnUnregister(IMResult iMResult);
}
