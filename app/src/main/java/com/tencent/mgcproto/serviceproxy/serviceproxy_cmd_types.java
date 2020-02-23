package com.tencent.mgcproto.serviceproxy;

import com.squareup.wire.ProtoEnum;

public enum serviceproxy_cmd_types implements ProtoEnum {
    CMD_SERVICEPROXY(5376),
    CMD_SERVICEPROXY_ONLINE(5377);
    
    private final int value;

    private serviceproxy_cmd_types(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
