package com.tencent.imsdk;

import android.support.annotation.NonNull;

public class IMException extends Exception {
    public int errorCode = 0;
    public int imsdkRetCode = -1;
    public String imsdkRetMsg = "";
    public String retExtraJson = "{}";
    public int thirdRetCode = -1;
    public String thirdRetMsg = "";

    public IMException() {
    }

    public IMException(int code) {
        this.errorCode = code;
    }

    public String getMessage() {
        String msg = super.getMessage();
        if (msg != null) {
            String str = msg;
            return msg;
        }
        String msg2 = IMErrorDef.getErrorString(this.errorCode);
        String str2 = msg2;
        return msg2;
    }

    public String getLocalizedMessage() {
        String msg = super.getLocalizedMessage();
        if (msg != null) {
            String str = msg;
            return msg;
        }
        String msg2 = IMErrorDef.getErrorString(this.errorCode);
        String str2 = msg2;
        return msg2;
    }

    @Deprecated
    public IMException(int code, @NonNull String message) {
        super(message);
        this.errorCode = code;
    }

    public IMException(int code, int imsdkCode) {
        super(IMErrorDef.getErrorString(code));
        this.errorCode = code;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = IMErrorMsg.getMessageByCode(this.imsdkRetCode);
    }

    public IMException(int code, int imsdkCode, int thirdCode) {
        super(IMErrorDef.getErrorString(code));
        this.errorCode = code;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = IMErrorMsg.getMessageByCode(this.imsdkRetCode);
        this.thirdRetCode = thirdCode;
    }

    public IMException(int code, String message, int imsdkCode, String imsdkMsg) {
        super(message);
        this.errorCode = code;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = imsdkMsg;
    }

    public IMException(int code, int imsdkCode, int thirdCode, String thirdMsg) {
        super(IMErrorDef.getErrorString(code));
        this.errorCode = code;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
        this.thirdRetCode = thirdCode;
        this.thirdRetMsg = thirdMsg;
    }

    public IMException(int code, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        super(message);
        this.errorCode = code;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = imsdkMsg;
        this.thirdRetCode = thirdCode;
        this.thirdRetMsg = thirdMsg;
    }

    public IMException(Throwable cause) {
        super(cause);
    }

    public IMException(String message) {
        super(message);
    }

    public IMException(String message, Throwable tr) {
        super(message, tr);
    }
}
