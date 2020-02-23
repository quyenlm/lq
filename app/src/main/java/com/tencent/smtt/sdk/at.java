package com.tencent.smtt.sdk;

import com.tencent.component.plugin.server.PluginConstant;
import java.io.File;
import java.io.FileFilter;

class at implements FileFilter {
    final /* synthetic */ am a;

    at(am amVar) {
        this.a = amVar;
    }

    public boolean accept(File file) {
        return !file.getName().endsWith(PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX) && !file.getName().endsWith(".jar_is_first_load_dex_flag_file");
    }
}
