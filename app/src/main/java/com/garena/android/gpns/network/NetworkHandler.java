package com.garena.android.gpns.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.garena.android.gpns.network.tcp.TCPPacket;
import com.garena.android.gpns.utility.AppLogger;
import com.garena.android.gpns.utility.TCPPacketFactory;

public class NetworkHandler extends Handler implements NetworkRequestHandler {
    private static final int AUTH_WITH_SERVER = 2;
    private static final int CONNECT_REGION_SERVER = 3;
    public static final int ON_PACKET_SEND = 0;
    public static final int SETUP_CONNECTION = 1;
    private final NetworkRequestHandler mRequestHandler;

    public NetworkHandler(Looper looper, NetworkRequestHandler handler) {
        super(looper);
        this.mRequestHandler = handler;
    }

    public void connectNotificationServer() {
        obtainMessage(1, (Object) null).sendToTarget();
    }

    public void sendPacket(TCPPacket packet) {
        obtainMessage(0, packet).sendToTarget();
    }

    public void connectAuthenticationServer() {
        obtainMessage(2, (Object) null).sendToTarget();
    }

    public void connectRegionServer() {
        obtainMessage(3, (Object) null).sendToTarget();
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                if (msg.obj != null) {
                    TCPPacket packet = (TCPPacket) msg.obj;
                    AppLogger.i("ON_PACKET_SEND : " + TCPPacketFactory.getPacketName(packet.getCommand()));
                    this.mRequestHandler.sendPacket(packet);
                    return;
                }
                return;
            case 1:
                AppLogger.i("SETUP_CONNECTION");
                this.mRequestHandler.connectNotificationServer();
                return;
            case 2:
                AppLogger.i("AUTH_WITH_SERVER");
                this.mRequestHandler.connectAuthenticationServer();
                return;
            case 3:
                AppLogger.i("CONNECT_REGION_SERVER");
                this.mRequestHandler.connectRegionServer();
                return;
            default:
                return;
        }
    }
}
