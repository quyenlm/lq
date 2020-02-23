package com.tencent.component.plugin;

import com.tencent.component.annotation.PluginApi;

public class PluginClassLoaderInterceptor {
    @PluginApi(since = 400)
    public boolean interceptClass(String className) {
        return true;
    }
}
