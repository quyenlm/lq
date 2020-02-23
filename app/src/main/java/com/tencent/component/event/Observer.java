package com.tencent.component.event;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 6)
public interface Observer {
    @PluginApi(since = 6)
    void onNotify(Event event);
}
