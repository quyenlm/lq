package com.tencent.component.db.annotation;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 4)
public interface GenerationType {
    @PluginApi(since = 4)
    public static final int ASSIGN = 1;
    @PluginApi(since = 4)
    public static final int AUTO_INCREMENT = 3;
    @PluginApi(since = 4)
    public static final int UUID = 2;
}
