package com.garena.android.gpns.utility;

import com.garena.android.gpnprotocol.gpush.ConnectRequest;
import com.garena.android.gpnprotocol.gpush.GetGPidRequest;
import com.garena.android.gpnprotocol.gpush.PushMsgAck;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.network.tcp.TCPPacket;
import com.garena.android.gpns.storage.LocalStorage;

public final class TCPPacketFactory {
    public static TCPPacket newPushMessageAckPacket(int ackId) {
        PushMsgAck.Builder builder = new PushMsgAck.Builder();
        builder.Msgid(Integer.valueOf(ackId));
        return new TCPPacket(3, WireUtil.marshall(builder.build()));
    }

    public static TCPPacket newConnectionRequestPacket(long id) {
        ConnectRequest.Builder builder = new ConnectRequest.Builder();
        builder.GPid(Long.valueOf(id));
        return new TCPPacket(2, WireUtil.marshall(builder.build()));
    }

    public static TCPPacket newAuthRequestPacket() {
        GetGPidRequest.Builder builder = new GetGPidRequest.Builder();
        builder.Sign(Long.valueOf(DeviceUtil.getDeviceId(GNotificationService.getContext())));
        builder.OldId(Long.valueOf(LocalStorage.getConnectionId()));
        GetGPidRequest request = builder.build();
        TCPPacket packet = new TCPPacket(1, WireUtil.marshall(request));
        packet.setTimed(true);
        packet.setTimeout(30000);
        AppLogger.i("auth packet: Command: " + packet.getCommand() + " sign: " + request.Sign + " oldid: " + request.OldId);
        return packet;
    }

    public static TCPPacket newRegionRequestPacket() {
        TCPPacket packet = new TCPPacket(5, new byte[0]);
        packet.setTimed(true);
        packet.setTimeout(30000);
        return packet;
    }

    public static TCPPacket newPingRequestPacket() {
        TCPPacket packet = new TCPPacket(4, new byte[0]);
        packet.setTimed(true);
        packet.setTimeout(60000);
        return packet;
    }

    public static String getPacketName(int command) {
        switch (command) {
            case 1:
                return "AUTH_REQUEST";
            case 2:
                return "CONNECTION_REQUEST";
            case 3:
                return "PUSH_ACK";
            case 4:
                return "PING_REQUEST";
            case 5:
                return "REGION_REQUEST";
            case 17:
                return "AUTH_RESPONSE";
            case 18:
                return "PUSH_NOTIFICATION";
            case 19:
                return "PING_ACK";
            case 20:
                return "REGION_RESPONSE";
            default:
                return "UNKNOWN";
        }
    }

    private TCPPacketFactory() {
    }
}
