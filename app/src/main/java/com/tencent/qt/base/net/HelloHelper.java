package com.tencent.qt.base.net;

public interface HelloHelper {
    Request getHello();

    int getHelloInterval();

    boolean isHelloOK(Message message);
}
