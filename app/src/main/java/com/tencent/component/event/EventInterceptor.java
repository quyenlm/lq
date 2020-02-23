package com.tencent.component.event;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 6)
public interface EventInterceptor {
    @PluginApi(since = 6)
    boolean intercept(Event event);
}
