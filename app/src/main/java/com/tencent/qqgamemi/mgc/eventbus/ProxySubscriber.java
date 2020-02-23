package com.tencent.qqgamemi.mgc.eventbus;

public interface ProxySubscriber {
    Object getProxiedSubscriber();

    ReferenceStrength getReferenceStrength();

    void proxyUnsubscribed();
}
