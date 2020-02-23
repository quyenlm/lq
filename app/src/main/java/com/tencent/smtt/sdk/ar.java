package com.tencent.smtt.sdk;

import com.tencent.component.plugin.server.PluginConstant;
import java.io.File;
import java.io.FileFilter;

class ar implements FileFilter {
    final /* synthetic */ aq a;

    ar(aq aqVar) {
        this.a = aqVar;
    }

    public boolean accept(File file) {
        return file.getName().endsWith(PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX);
    }
}
