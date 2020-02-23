package com.tencent.smtt.sdk;

import android.os.Build;
import com.tencent.component.plugin.server.PluginConstant;
import java.io.File;
import java.io.FileFilter;

final class ao implements FileFilter {
    ao() {
    }

    public boolean accept(File file) {
        String name = file.getName();
        if (name != null && !name.endsWith(".jar_is_first_load_dex_flag_file")) {
            return Build.VERSION.SDK_INT < 21 || !name.endsWith(PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX);
        }
        return false;
    }
}
