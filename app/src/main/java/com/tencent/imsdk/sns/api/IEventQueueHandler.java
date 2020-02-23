package com.tencent.imsdk.sns.api;

public interface IEventQueueHandler {
    void onHandleMessage(Object obj);
}
