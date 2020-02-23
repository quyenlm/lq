package com.tencent.imsdk.api;

public class IMBaseRet {
    public static final int RET_FAIL = -1;
    public static final int RET_SUCC = 1;
    public int flag = -1;
    public String msg = "defulat error";
    public int ret = -1;

    public IMBaseRet() {
    }

    public IMBaseRet(IMBaseRet ret2) {
        if (ret2 != null) {
            this.ret = ret2.ret;
            this.flag = ret2.flag;
            this.msg = ret2.msg;
        }
    }

    public IMBaseRet(int ret2, int errorCode, String msg2) {
        this.ret = ret2;
        this.msg = msg2;
    }

    public String toString() {
        return "ret : " + this.ret + "\n" + "flag :" + this.flag + "\n" + "msg :" + this.msg + "\n";
    }
}
