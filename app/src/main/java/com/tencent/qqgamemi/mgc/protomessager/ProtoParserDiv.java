package com.tencent.qqgamemi.mgc.protomessager;

import com.tencent.qt.base.net.Request;

public abstract class ProtoParserDiv<PARAM, RESULT, STATUS> extends ProtoParser<PARAM, RESULT, STATUS> {
    /* access modifiers changed from: protected */
    public abstract byte[] buildRequestData(PARAM... paramArr);

    /* access modifiers changed from: protected */
    public abstract int getCommand();

    /* access modifiers changed from: protected */
    public abstract int getSubCmd();

    public Request buildRequest(PARAM... params) {
        return Request.createEncryptRequest(getCommand(), getSubCmd(), buildRequestData(params), getReserve(), getExtra());
    }

    /* access modifiers changed from: protected */
    public byte[] getExtra() {
        return null;
    }

    /* access modifiers changed from: protected */
    public byte[] getReserve() {
        return null;
    }
}
