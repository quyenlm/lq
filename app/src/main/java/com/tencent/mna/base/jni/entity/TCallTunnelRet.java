package com.tencent.mna.base.jni.entity;

public class TCallTunnelRet {
    public int accessIp;
    public int gatewayIp;
    public int masterIp;
    public int tunnelErrno;

    public TCallTunnelRet(int i, int i2, int i3, int i4) {
        this.tunnelErrno = i;
        this.accessIp = i2;
        this.gatewayIp = i3;
        this.masterIp = i4;
    }
}
