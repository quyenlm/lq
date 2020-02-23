package com.tencent.smtt.sdk;

import java.io.File;
import java.io.FileFilter;

class as implements FileFilter {
    final /* synthetic */ aq a;

    as(aq aqVar) {
        this.a = aqVar;
    }

    public boolean accept(File file) {
        return file.getName().endsWith("tbs.conf");
    }
}
