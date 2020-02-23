package com.tencent.component.utils.thread;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 4)
public interface Future<T> {

    @PluginApi(since = 300)
    public interface CancelListener {
        @PluginApi(since = 300)
        void onCancel();
    }

    @PluginApi(since = 4)
    void cancel();

    @PluginApi(since = 4)
    T get();

    @PluginApi(since = 4)
    boolean isCancelled();

    @PluginApi(since = 4)
    boolean isDone();

    @PluginApi(since = 300)
    void setCancelListener(CancelListener cancelListener);

    @PluginApi(since = 4)
    void waitDone();
}
