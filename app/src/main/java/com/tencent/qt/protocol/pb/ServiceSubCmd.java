package com.tencent.qt.protocol.pb;

import com.squareup.wire.ProtoEnum;

public enum ServiceSubCmd implements ProtoEnum {
    SUBCMD_AUTH_TOKEN(1),
    SUBCMD_SERVICEPROXY_LIST(2),
    SUBCMD_AUTH_TOKEN_WITH_OPENID(3);
    
    private final int value;

    private ServiceSubCmd(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
