package com.tencent.imsdk;

public interface IMCallback<T> {
    void onCancel();

    void onError(IMException iMException);

    void onSuccess(T t);
}
