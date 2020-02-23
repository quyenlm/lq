package com.tencent.qqgamemi.mgc.base;

public class ErrorFactoryPb extends ErrorFactory {
    public static final BaseError BUSY_NOW = new BaseError(-33554427, "BUSY_NOW", "请稍候再试");
    private static final int CODE_BASE = -33554432;
    public static final BaseError CONNECT_FAIL = new BaseError(-33554429, "CONNECT_FAIL", "连接服务器失败");
    public static final BaseError FORMAT_ERROR = new BaseError(-33554431, "FORMAT_ERROR", "协议格式错误");
    public static final BaseError IO_ERROR = new BaseError(-33554428, "IO_ERROR", "读取信息失败");
    public static final BaseError SERVER_ERROR = new BaseError(-33554430, "SERVER_ERROR", "服务器内部错误");

    public static BaseError fromCode(int code) {
        if (code == FORMAT_ERROR.getCode()) {
            return FORMAT_ERROR;
        }
        if (code == SERVER_ERROR.getCode()) {
            return SERVER_ERROR;
        }
        if (code == CONNECT_FAIL.getCode()) {
            return CONNECT_FAIL;
        }
        if (code == IO_ERROR.getCode()) {
            return IO_ERROR;
        }
        if (code == BUSY_NOW.getCode()) {
            return BUSY_NOW;
        }
        return ErrorFactory.fromCode(code);
    }
}
