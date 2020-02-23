package com.garena.android.gpnprotocol.gpush;

import com.squareup.wire.ProtoEnum;

public enum CmdType implements ProtoEnum {
    GET_GPID(1),
    CONNECT(2),
    MSG_ACK(3),
    PING(4),
    GET_REGION(5),
    RETURN_GPID(17),
    PUSH_MSG(18),
    PING_ACK(19),
    RETURN_REGION(20);
    
    private final int value;

    private CmdType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
