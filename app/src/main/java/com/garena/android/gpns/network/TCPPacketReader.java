package com.garena.android.gpns.network;

import com.garena.android.gpns.network.exception.ConnectionDroppedException;
import com.garena.android.gpns.network.tcp.TCPPacket;
import java.io.InputStream;

public class TCPPacketReader {
    private static final int COMMAND_FIELD_SIZE = 1;
    private static final int LENGTH_FIELD_SIZE = 4;
    private final InputStream mInputStream;

    public TCPPacketReader(InputStream inputStream) {
        this.mInputStream = inputStream;
    }

    private byte[] readNBytes(int n) throws Exception {
        byte[] buffer = new byte[n];
        int byteOffset = 0;
        int byteCount = n;
        while (true) {
            int bytesRead = this.mInputStream.read(buffer, byteOffset, byteCount);
            if (bytesRead <= 0) {
                return null;
            }
            if (bytesRead >= n) {
                return buffer;
            }
            byteOffset += bytesRead;
            byteCount = n - bytesRead;
            n = byteCount;
        }
    }

    private static int byteArrayToInt(byte[] b) {
        return (b[0] & 255) | ((b[1] & 255) << 8) | ((b[2] & 255) << 16) | ((b[3] & 255) << 24);
    }

    public TCPPacket readPacket() throws ConnectionDroppedException {
        try {
            int packetSize = byteArrayToInt(readNBytes(4));
            byte[] cmd = readNBytes(1);
            return new TCPPacket(cmd[0], readNBytes(packetSize - 1));
        } catch (Exception ex) {
            throw new ConnectionDroppedException(ex);
        }
    }
}
