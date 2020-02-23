package com.tencent.component.plugin;

import com.tencent.component.annotation.PluginApi;

public class PluginCommander {

    @PluginApi(since = 300)
    public interface ReadDataCallback {
        @PluginApi(since = 300)
        void onReadDataFinish(String str, Object obj);
    }

    @PluginApi(since = 300)
    public Object read(String cmd, Object args, Object defaultData, ReadDataCallback callback) {
        return defaultData;
    }

    @PluginApi(since = 300)
    public void write(String cmd, Object args) {
    }
}
