package com.garena.android.gpns.utility;

import com.garena.android.gpnprotocol.gpush.GetGPidResponse;
import com.garena.android.gpnprotocol.gpush.GetRegionResponse;
import com.garena.android.gpnprotocol.gpush.PushMsg;
import com.squareup.wire.Message;
import com.squareup.wire.Wire;
import java.io.IOException;

public class WireUtil {
    private static final Wire WIRE = new Wire((Class<?>[]) new Class[0]);

    public static GetGPidResponse parseGetGPidResponse(byte[] data, int offset, int length) throws IOException {
        return (GetGPidResponse) WIRE.parseFrom(data, offset, length, GetGPidResponse.class);
    }

    public static PushMsg parsePushMsg(byte[] data, int offset, int length) throws IOException {
        return (PushMsg) WIRE.parseFrom(data, offset, length, PushMsg.class);
    }

    public static GetRegionResponse parseGetRegionResponse(byte[] data, int offset, int length) throws IOException {
        return (GetRegionResponse) WIRE.parseFrom(data, offset, length, GetRegionResponse.class);
    }

    public static <T extends Message> byte[] marshall(T msg) {
        return msg.toByteArray();
    }

    private WireUtil() {
    }
}
