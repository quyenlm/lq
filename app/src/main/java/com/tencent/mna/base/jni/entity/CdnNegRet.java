package com.tencent.mna.base.jni.entity;

import com.tencent.mna.a.a;
import com.tencent.mna.base.utils.f;

public class CdnNegRet {
    public int negErrno;
    public int proxyIp;
    public int proxyPort;
    public int token;

    public CdnNegRet(int i, int i2, int i3, int i4) {
        this.negErrno = i;
        this.proxyIp = i2;
        this.proxyPort = i3;
        this.token = i4;
    }

    public String toString() {
        return String.format(a.a, "negError:%d, proxyIp:%s, proxyPort:%d, token:%d", new Object[]{Integer.valueOf(this.negErrno), f.b(this.proxyIp), Integer.valueOf(this.proxyPort), Integer.valueOf(this.token)});
    }
}
