package com.tencent.qqgamemi.mgc.eventbus;

public interface Subscriber<T> {
    void onEvent(T t);
}
