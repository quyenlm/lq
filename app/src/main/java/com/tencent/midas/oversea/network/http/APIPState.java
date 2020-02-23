package com.tencent.midas.oversea.network.http;

import com.tencent.midas.oversea.comm.APAppDataInterface;

public class APIPState {
    public int accessTimes = 0;
    public int ansTims = -1;
    public String city = "";
    public int failPercent = 0;
    public int failTimes = 0;
    public String ip = "";
    public String ipEnv = APAppDataInterface.singleton().getEnv();
    public String province = "";
    public int seqFailTimes = 0;
    public int succTimes = 0;
}
