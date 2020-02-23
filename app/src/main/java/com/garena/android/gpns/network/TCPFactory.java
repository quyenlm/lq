package com.garena.android.gpns.network;

import android.util.Pair;
import com.garena.android.gpns.network.tcp.TCPConnection;
import com.garena.android.gpns.storage.LocalStorage;
import com.garena.android.gpns.utility.CONSTANT;
import java.io.InputStream;

public final class TCPFactory {
    public static TCPConnection newConnection(int connectionId) {
        switch (connectionId) {
            case 0:
                Pair<String, Integer> authAddress = parseIPAndPort(LocalStorage.getAuthServerAddress());
                TCPConnection connection = newConnection((String) authAddress.first, ((Integer) authAddress.second).intValue(), connectionId);
                connection.setKeepConnected(false);
                return connection;
            case 1:
                Pair<String, Integer> connectionAddress = parseIPAndPort(LocalStorage.getConnectionAddress());
                TCPConnection connection2 = newConnection((String) connectionAddress.first, ((Integer) connectionAddress.second).intValue(), connectionId);
                connection2.setKeepConnected(true);
                return connection2;
            case 2:
                TCPConnection connection3 = newConnection(CONSTANT.NETWORK.SERVER_IP, CONSTANT.NETWORK.SERVER_PORT, connectionId);
                connection3.setKeepConnected(false);
                return connection3;
            default:
                return null;
        }
    }

    public static TCPConnection newConnection(String ip, int port, int id) {
        return new TCPConnection(ip, port, id);
    }

    public static TCPPacketReader newPacketReader(InputStream inputStream) {
        return new TCPPacketReader(inputStream);
    }

    private static Pair<String, Integer> parseIPAndPort(String address) {
        String[] tokens = address.split(":");
        return new Pair<>(tokens[0], Integer.valueOf(Integer.parseInt(tokens[1])));
    }

    private TCPFactory() {
    }
}
