package com.tencent.qt.base.net;

public interface VerifyHelper {
    public static final int VH_RESULT_FAIL = 1;
    public static final int VH_RESULT_FUCKOFF = 2;
    public static final int VH_RESULT_OK = 0;

    Request getSTRequest(boolean z);

    int onSTReponse(Message message);
}
