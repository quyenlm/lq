package com.garena.android.gpns.network;

import com.garena.android.gpns.network.tcp.TCPPacket;

public interface NetworkRequestHandler {
    void connectAuthenticationServer();

    void connectNotificationServer();

    void connectRegionServer();

    void sendPacket(TCPPacket tCPPacket);
}
