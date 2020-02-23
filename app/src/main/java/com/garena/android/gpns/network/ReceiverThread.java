package com.garena.android.gpns.network;

import com.garena.android.gpns.network.exception.ConnectionDroppedException;
import com.garena.android.gpns.utility.AppLogger;

public class ReceiverThread implements Runnable {
    private final int mConnectionId;
    private final TCPPacketReader mPacketReader;
    private final PacketReadListener mReadListener;

    public ReceiverThread(int connectionId, TCPPacketReader reader, PacketReadListener readListener) {
        this.mConnectionId = connectionId;
        this.mPacketReader = reader;
        this.mReadListener = readListener;
    }

    public void run() {
        try {
            runPacketReadingLoop();
        } catch (Exception ex) {
            AppLogger.e((Throwable) ex);
        }
    }

    private void runPacketReadingLoop() {
        while (!Thread.interrupted()) {
            try {
                this.mReadListener.onPacketRead(this.mPacketReader.readPacket());
            } catch (ConnectionDroppedException e) {
                this.mReadListener.onReadFailed(this.mConnectionId);
                return;
            }
        }
    }
}
