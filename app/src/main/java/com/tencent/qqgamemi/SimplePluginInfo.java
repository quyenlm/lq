package com.tencent.qqgamemi;

import com.tencent.component.plugin.PluginInfo;

public class SimplePluginInfo {
    public String pluginId;
    public String pluginName;
    public int version;
    public String versionName;

    public SimplePluginInfo(PluginInfo pluginInfo) {
        if (pluginInfo != null) {
            this.pluginId = pluginInfo.pluginId;
            this.pluginName = pluginInfo.pluginName;
            this.version = pluginInfo.version;
            this.versionName = pluginInfo.versionName;
        }
    }
}
