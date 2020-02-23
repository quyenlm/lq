package com.tencent.smtt.sdk;

import java.io.File;
import java.io.FileFilter;

class av implements FileFilter {
    final /* synthetic */ am a;

    av(am amVar) {
        this.a = amVar;
    }

    public boolean accept(File file) {
        return file.getName().endsWith(".jar");
    }
}
