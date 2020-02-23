package com.tencent.component.utils.thread;

import com.tencent.component.annotation.PluginApi;

public interface FutureListener<T> {
    @PluginApi(since = 4)
    void onFutureBegin(Future<T> future);

    @PluginApi(since = 4)
    void onFutureDone(Future<T> future);
}
