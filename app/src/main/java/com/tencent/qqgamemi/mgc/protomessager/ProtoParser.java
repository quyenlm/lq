package com.tencent.qqgamemi.mgc.protomessager;

import com.squareup.wire.Wire;
import com.tencent.qqgamemi.mgc.pb.ProtoUtils;
import com.tencent.qt.base.net.Request;
import java.io.IOException;
import okio.ByteString;

public abstract class ProtoParser<PARAM, RESULT, STATUS> {
    static final String LOG_TAG = "ProtoMessager";
    public STATUS mStatus;

    public abstract Request buildRequest(PARAM... paramArr);

    public abstract RESULT parseResponse(byte[] bArr) throws IOException;

    /* access modifiers changed from: protected */
    public void setStatus(STATUS status) {
        this.mStatus = status;
    }

    public STATUS getStatus() {
        return this.mStatus;
    }

    /* access modifiers changed from: protected */
    public ByteString encodeString(String value) {
        return ProtoUtils.encodeString(value);
    }

    /* access modifiers changed from: protected */
    public String decodeString(ByteString stream) {
        return ProtoUtils.decodeString(stream);
    }

    /* access modifiers changed from: protected */
    public Wire getWire() {
        return ProtoUtils.getWire();
    }
}
