package com.garena.android.gpns;

import android.os.Handler;
import android.os.Message;
import com.garena.android.gpns.network.NetworkResponseListener;
import com.garena.android.gpns.network.tcp.TCPPacket;
import com.garena.android.gpns.utility.AppLogger;
import com.garena.android.gpns.utility.TCPPacketFactory;

public class ServiceHandler extends Handler implements NetworkResponseListener {
    private static final int CONNECTION_DROPPED = 2;
    private static final int CONNECTION_OK = 4;
    private static final int ON_PACKET_ARRIVED = 0;
    private static final int PACKET_FAILED = 3;
    private static final int UNABLE_TO_CONNECT = 1;
    private final NetworkResponseListener mResponseListener;

    public ServiceHandler(NetworkResponseListener responseListener) {
        this.mResponseListener = responseListener;
    }

    public void onUnableToConnect(int connectionId) {
        obtainMessage(1, Integer.valueOf(connectionId)).sendToTarget();
    }

    public void onPacketFailed(TCPPacket packet) {
        obtainMessage(3, packet).sendToTarget();
    }

    public void onResponseArrived(TCPPacket packet) {
        obtainMessage(0, packet).sendToTarget();
    }

    public void onConnectionDropped(int connectionId) {
        obtainMessage(2, Integer.valueOf(connectionId)).sendToTarget();
    }

    public void onConnectionOK(int connectionId) {
        obtainMessage(4, Integer.valueOf(connectionId)).sendToTarget();
    }

    public void handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case 0:
                    if (msg.obj != null && (msg.obj instanceof TCPPacket)) {
                        TCPPacket packet = (TCPPacket) msg.obj;
                        AppLogger.i("ON_PACKET_ARRIVED : " + TCPPacketFactory.getPacketName(packet.getCommand()));
                        AppLogger.f("ON_PACKET_ARRIVED : " + TCPPacketFactory.getPacketName(packet.getCommand()));
                        this.mResponseListener.onResponseArrived(packet);
                        return;
                    }
                    return;
                case 1:
                    if (msg.obj != null && (msg.obj instanceof Integer)) {
                        int connectionId = ((Integer) msg.obj).intValue();
                        AppLogger.i("UNABLE_TO_CONNECT : " + getConnectionName(connectionId));
                        AppLogger.f("UNABLE_TO_CONNECT : " + getConnectionName(connectionId));
                        this.mResponseListener.onUnableToConnect(connectionId);
                        return;
                    }
                    return;
                case 2:
                    if (msg.obj != null && (msg.obj instanceof Integer)) {
                        int connectionId2 = ((Integer) msg.obj).intValue();
                        AppLogger.i("CONNECTION_DROPPED : " + getConnectionName(connectionId2));
                        AppLogger.f("CONNECTION_DROPPED : " + getConnectionName(connectionId2));
                        this.mResponseListener.onConnectionDropped(connectionId2);
                        return;
                    }
                    return;
                case 3:
                    if (msg.obj != null && (msg.obj instanceof TCPPacket)) {
                        TCPPacket packet2 = (TCPPacket) msg.obj;
                        AppLogger.i("PACKET_FAILED : " + TCPPacketFactory.getPacketName(packet2.getCommand()));
                        AppLogger.f("PACKET_FAILED : " + TCPPacketFactory.getPacketName(packet2.getCommand()));
                        this.mResponseListener.onPacketFailed(packet2);
                        return;
                    }
                    return;
                case 4:
                    if (msg.obj != null && (msg.obj instanceof Integer)) {
                        int connectionId3 = ((Integer) msg.obj).intValue();
                        AppLogger.i("CONNECTION_OK : " + getConnectionName(connectionId3));
                        AppLogger.f("CONNECTION_OK : " + getConnectionName(connectionId3));
                        this.mResponseListener.onConnectionOK(connectionId3);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            AppLogger.e((Throwable) e);
        }
        AppLogger.e((Throwable) e);
    }

    private String getConnectionName(int connectionId) {
        return connectionId == 0 ? "AUTH_SERVER" : "NOTIFICATION_SERVER";
    }
}
