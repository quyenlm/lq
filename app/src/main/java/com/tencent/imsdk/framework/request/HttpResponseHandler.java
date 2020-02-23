package com.tencent.imsdk.framework.request;

public interface HttpResponseHandler<T> {
    void onResponse(T t);
}
