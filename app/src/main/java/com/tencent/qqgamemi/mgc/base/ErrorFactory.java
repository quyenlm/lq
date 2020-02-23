package com.tencent.qqgamemi.mgc.base;

import com.google.android.gms.iid.InstanceID;

public class ErrorFactory {
    private static final int CODE_BASE = -16777216;
    public static final BaseError EMPTY_DATA = new BaseError(-16777213, "NULL_OBJECT", "空数据");
    public static final BaseError NETWORK_INVALID = new BaseError(-16777214, "NETWORK_INVALID", "网络不通");
    public static final BaseError SUCCESS = new BaseError(0, "SUCCESS", "成功");
    public static final BaseError TIMEOUT = new BaseError(-16777213, InstanceID.ERROR_TIMEOUT, "网络超时");
    public static final BaseError UNKNOWN_ERROR = new BaseError(-1, "UNKNOWN_ERROR", "未知错误");

    public static BaseError fromCode(int code) {
        if (code == SUCCESS.getCode()) {
            return SUCCESS;
        }
        if (code == UNKNOWN_ERROR.getCode()) {
            return UNKNOWN_ERROR;
        }
        if (code == NETWORK_INVALID.getCode()) {
            return NETWORK_INVALID;
        }
        if (code == TIMEOUT.getCode()) {
            return TIMEOUT;
        }
        if (code == EMPTY_DATA.getCode()) {
            return EMPTY_DATA;
        }
        return null;
    }
}
