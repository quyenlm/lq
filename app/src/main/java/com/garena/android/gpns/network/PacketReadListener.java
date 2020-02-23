package com.garena.android.gpns.network;

import com.garena.android.gpns.network.tcp.TCPPacket;

public interface PacketReadListener {
    void onPacketRead(TCPPacket tCPPacket);

    void onReadFailed(int i);
}
