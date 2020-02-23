package com.qt.qq.SYRecordConf;

import com.squareup.wire.ProtoEnum;

public enum syrecordconf_subcmd_types implements ProtoEnum {
    SUBCMD_GET_WHITELIST_INFO(1),
    SUBCMD_GET_UPGRADE_INFO(2),
    SUBCMD_GET_WHITELIST_WITH_STATUS(3),
    SUBCMD_GET_GAME_WITH_SDK(4),
    SUBCMD_GET_UPGRADE_INFO_WITH_STATUS(5);
    
    private final int value;

    private syrecordconf_subcmd_types(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
