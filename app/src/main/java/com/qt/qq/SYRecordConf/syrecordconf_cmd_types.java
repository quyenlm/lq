package com.qt.qq.SYRecordConf;

import com.squareup.wire.ProtoEnum;

public enum syrecordconf_cmd_types implements ProtoEnum {
    CMD_SYRECORDCONF(1124);
    
    private final int value;

    private syrecordconf_cmd_types(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
