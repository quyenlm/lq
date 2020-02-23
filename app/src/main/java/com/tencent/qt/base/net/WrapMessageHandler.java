package com.tencent.qt.base.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class WrapMessageHandler implements MessageHandler {
    private static final int MSG_RESPONSE = 1;
    private static final int MSG_TIMEOUT = 2;
    final Handler mHandler;
    final MessageHandler mMsgHandler;

    public WrapMessageHandler(MessageHandler msgHandler, Looper looper) {
        this.mMsgHandler = msgHandler;
        this.mHandler = new LooperHandler(looper, this);
    }

    public boolean match(int command, int subcmd, int seq) {
        if (this.mMsgHandler == null) {
            return false;
        }
        return this.mMsgHandler.match(command, subcmd, seq);
    }

    public void onMessage(Request request, Message msg) {
        ResponseData data = new ResponseData();
        data.request = request;
        data.message = msg;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, data));
        PLog.i(NetworkEngine.TAG, String.format("r %04x,%02x,%d", new Object[]{Integer.valueOf(msg.command), Integer.valueOf(msg.subcmd), Integer.valueOf(msg.sequenceNumber)}), new Object[0]);
    }

    public void onTimeout(Request request) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, request));
        PLog.i(NetworkEngine.TAG, String.format("t %04x,%02x,%d", new Object[]{Integer.valueOf(request.command), Integer.valueOf(request.subcmd), Integer.valueOf(request.sequenceNumber)}), new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void onChildMessage(Request request, Message msg) {
        if (this.mMsgHandler != null) {
            this.mMsgHandler.onMessage(request, msg);
        }
    }

    /* access modifiers changed from: protected */
    public void onChildTimeout(Request request) {
        if (this.mMsgHandler != null) {
            this.mMsgHandler.onTimeout(request);
        }
    }

    static class ResponseData {
        Message message;
        Request request;

        ResponseData() {
        }
    }

    private static class LooperHandler extends Handler {
        WrapMessageHandler mParent;

        public LooperHandler(Looper looper, WrapMessageHandler parent) {
            super(looper);
            this.mParent = parent;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ResponseData data = (ResponseData) msg.obj;
                    if (data != null) {
                        this.mParent.onChildMessage(data.request, data.message);
                        return;
                    }
                    return;
                case 2:
                    Request req = (Request) msg.obj;
                    if (req != null) {
                        this.mParent.onChildTimeout(req);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
