package com.tencent.qt.protocol.pb;

import com.squareup.wire.ProtoEnum;

public enum ServiceCmd implements ProtoEnum {
    CMD_TICKETSVR(791);
    
    private final int value;

    private ServiceCmd(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
