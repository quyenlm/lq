package com.tencent.qqgamemi.mgc.connection;

public enum ConnectError {
    UNKNOW(0),
    TICKET_EXPIRED(-2),
    SERVER_SIDE_ERROR(-3),
    USER_CLOSED_CONNECTION(-4),
    KICKED_OFF(-5);
    
    private int errorCode;

    private ConnectError(int errorCode2) {
        this.errorCode = errorCode2;
    }

    public static ConnectError fromErrorCode(int errorCode2) {
        switch (errorCode2) {
            case -5:
                return KICKED_OFF;
            case -4:
                return USER_CLOSED_CONNECTION;
            case -3:
                return SERVER_SIDE_ERROR;
            case -2:
                return TICKET_EXPIRED;
            default:
                return UNKNOW;
        }
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
