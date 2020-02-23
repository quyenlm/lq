package com.tencent.qt.base.net;

public class NetworkBroadcast extends Message {
    public static final int NB_COMMAND = 65536;
    public static final int NB_SUBCMD_UNAVAILABLE = 1;

    protected NetworkBroadcast(int subcmd) {
        this.command = 65536;
        this.subcmd = subcmd;
    }
}
