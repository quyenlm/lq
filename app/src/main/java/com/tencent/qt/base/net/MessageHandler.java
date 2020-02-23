package com.tencent.qt.base.net;

public interface MessageHandler {
    boolean match(int i, int i2, int i3);

    void onMessage(Request request, Message message);

    void onTimeout(Request request);
}
