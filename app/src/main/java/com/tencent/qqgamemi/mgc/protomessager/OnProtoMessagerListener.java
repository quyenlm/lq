package com.tencent.qqgamemi.mgc.protomessager;

public interface OnProtoMessagerListener<RESULT, STATUS> {
    void onError(ProtoError protoError);

    void onResult(STATUS status, RESULT result);
}
