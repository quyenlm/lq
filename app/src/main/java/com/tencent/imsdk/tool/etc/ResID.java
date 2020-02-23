package com.tencent.imsdk.tool.etc;

import android.content.res.Resources;

public class ResID {
    public static int loadIdentifierResource(Resources r, String name, String type, String pkgName) {
        int ret = r.getIdentifier(name, type, pkgName);
        if (ret == 0) {
            new Exception(String.format("Resource %s(type=%s, pkg=%s) is not found", new Object[]{name, type, pkgName})).printStackTrace();
            System.exit(-1);
        }
        return ret;
    }
}
