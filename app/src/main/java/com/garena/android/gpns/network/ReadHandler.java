package com.garena.android.gpns.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.garena.android.gpns.network.tcp.TCPPacket;

public class ReadHandler extends Handler implements PacketReadListener {
    private static final int ON_PACKET_READ = 0;
    private static final int ON_READ_FAILED = 1;
    private final PacketReadListener mReadListener;

    public ReadHandler(Looper looper, PacketReadListener handler) {
        super(looper);
        this.mReadListener = handler;
    }

    public void onPacketRead(TCPPacket packet) {
        obtainMessage(0, packet).sendToTarget();
    }

    public void onReadFailed(int connectionId) {
        obtainMessage(1, Integer.valueOf(connectionId)).sendToTarget();
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                if (msg.obj != null) {
                    this.mReadListener.onPacketRead((TCPPacket) msg.obj);
                    return;
                }
                return;
            case 1:
                if (msg.obj != null) {
                    this.mReadListener.onReadFailed(((Integer) msg.obj).intValue());
                    return;
                }
                return;
            default:
                return;
        }
    }
}
