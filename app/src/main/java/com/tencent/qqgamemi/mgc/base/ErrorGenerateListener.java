package com.tencent.qqgamemi.mgc.base;

public interface ErrorGenerateListener<DATA> extends GenerateListener<DATA> {
    void onError(BaseError baseError);
}
