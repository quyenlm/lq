package com.tencent.mna.base.jni.entity;

public class CloudRet {
    public int errno;
    public String json;

    public CloudRet(int i, String str) {
        this.errno = i;
        this.json = str;
    }
}
