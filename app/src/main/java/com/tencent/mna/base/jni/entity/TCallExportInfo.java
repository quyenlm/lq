package com.tencent.mna.base.jni.entity;

public class TCallExportInfo {
    public int isSameArea;
    public int isSameIsp;
    public int status;

    public TCallExportInfo(int i, int i2, int i3) {
        this.isSameArea = i;
        this.isSameIsp = i2;
        this.status = i3;
    }
}
