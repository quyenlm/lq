package com.tencent.midas.oversea.api;

public interface IAPMidasNetCallBack {
    void MidasNetError(String str, int i, String str2);

    void MidasNetFinish(String str, String str2);

    void MidasNetStop(String str);
}
