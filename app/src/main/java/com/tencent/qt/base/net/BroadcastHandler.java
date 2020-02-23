package com.tencent.qt.base.net;

public interface BroadcastHandler {
    boolean match(int i, int i2, int i3);

    void onBroadcast(Message message);
}
