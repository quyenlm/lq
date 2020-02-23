package com.tencent.qqgamemi.mgc.eventbus;

public interface TopicSubscriber<T> {
    void onEvent(String str, T t);
}
