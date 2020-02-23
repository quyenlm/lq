package com.tencent.qt.protocol.pb;

import com.squareup.wire.ProtoEnum;

public enum AuthTokenErrCode implements ProtoEnum {
    AuthTokenErrCode_Success(0),
    AuthTokenErrCode_UnknowAccount(1),
    AuthTokenErrCode_QQAuthFail(2),
    AuthTokenErrCode_WeChatAuthFail(3),
    AuthTokenErrCode_UuidFail(4),
    AuthTokenErrCode_InnerTimeOut(5),
    AuthTokenErrCode_InnerError(6),
    AuthTokenErrCode_PtLoginAuthFail(7);
    
    private final int value;

    private AuthTokenErrCode(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
