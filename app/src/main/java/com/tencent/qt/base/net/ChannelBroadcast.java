package com.tencent.qt.base.net;

public final class ChannelBroadcast extends Message {
    public static final int CB_COMMAND = 65535;
    public static final int CB_SUBCMD_BREAKDOWN = 3;
    public static final int CB_SUBCMD_CONNECTED = 1;
    public static final int CB_SUBCMD_DISCONNECTED = 2;
    private int type;

    protected ChannelBroadcast(int channelType) {
        this.type = channelType;
        this.command = 65535;
    }

    public int getChannelType() {
        return this.type;
    }
}
