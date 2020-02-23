package com.tencent.qqgamemi.mgc.protomessager;

public enum ProtoError {
    TIMEOUT,
    FORMAT_ERROR,
    IO_ERROR,
    SEND_ERROR,
    CONNECT_FAIL,
    BUSY,
    LOGIN_CANCELED,
    OTHER_ERROR;
    
    private Exception mException;

    /* access modifiers changed from: package-private */
    public void setException(Exception e) {
        this.mException = e;
    }

    public Exception getException() {
        return this.mException;
    }

    public String toHumanEasyReadText() {
        switch (ordinal()) {
            case 0:
                return "连接超时";
            case 1:
                return "服务器出错";
            case 2:
            case 3:
            case 4:
                return "网络似乎有问题";
            case 5:
                return "正在请求，请稍等";
            case 6:
                return "您还未登录";
            case 7:
                return "未知错误";
            default:
                return null;
        }
    }
}
