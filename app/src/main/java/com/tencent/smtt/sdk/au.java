package com.tencent.smtt.sdk;

import java.io.File;
import java.io.FileFilter;

class au implements FileFilter {
    final /* synthetic */ am a;

    au(am amVar) {
        this.a = amVar;
    }

    public boolean accept(File file) {
        return file.getName().endsWith(".jar");
    }
}
