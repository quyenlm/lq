package com.garena.android.gpns.network;

import com.garena.android.gpns.network.tcp.TCPPacket;

public interface NetworkResponseListener {
    void onConnectionDropped(int i);

    void onConnectionOK(int i);

    void onPacketFailed(TCPPacket tCPPacket);

    void onResponseArrived(TCPPacket tCPPacket);

    void onUnableToConnect(int i);
}
