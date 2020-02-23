package com.tencent.smtt.utils;

import com.tencent.smtt.utils.k;
import java.io.File;

final class m implements k.a {
    m() {
    }

    public boolean a(File file, File file2) {
        return file.length() == file2.length() && file.lastModified() == file2.lastModified();
    }
}
