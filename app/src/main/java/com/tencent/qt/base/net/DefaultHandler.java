package com.tencent.qt.base.net;

public class DefaultHandler implements MessageHandler {
    public boolean match(int command, int subcmd, int seq) {
        return false;
    }

    public void onMessage(Request request, Message msg) {
    }

    public void onTimeout(Request request) {
    }
}
