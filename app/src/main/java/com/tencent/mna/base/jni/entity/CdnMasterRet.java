package com.tencent.mna.base.jni.entity;

import com.tencent.mna.a.a;
import com.tencent.mna.base.utils.f;

public class CdnMasterRet {
    public int exportIp;
    public int exportPort;
    public int masterErrno;
    public int negIp;
    public int negPort;

    public CdnMasterRet(int i, int i2, int i3, int i4, int i5) {
        this.masterErrno = i;
        this.negIp = i2;
        this.negPort = i3;
        this.exportIp = i4;
        this.exportPort = i5;
    }

    public String toString() {
        return String.format(a.a, "errno:%d, negip:%s, negport:%d, exportIp:%s, exportPort:%d", new Object[]{Integer.valueOf(this.masterErrno), f.b(this.negIp), Integer.valueOf(this.negPort), f.b(this.exportIp), Integer.valueOf(this.exportPort)});
    }
}
