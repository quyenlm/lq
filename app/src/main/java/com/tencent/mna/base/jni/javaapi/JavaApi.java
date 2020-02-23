package com.tencent.mna.base.jni.javaapi;

import com.tencent.mna.b.b.b;
import com.tencent.mna.base.utils.h;

public class JavaApi {
    public static int bindFdToMobile(int i) {
        try {
            b m = com.tencent.mna.b.a.b.m();
            if (m != null) {
                h.a("c2j call bindFdToMobile, fd:" + i);
                return m.a(i);
            }
        } catch (Throwable th) {
        }
        return -102;
    }

    public static int unbindFd(int i) {
        try {
            b m = com.tencent.mna.b.a.b.m();
            if (m != null) {
                h.a("c2j call unbindFd, fd:" + i);
                return m.b(i);
            }
        } catch (Throwable th) {
        }
        return -103;
    }

    public static void addSendPkg(int i, int i2, long j) {
        try {
            com.tencent.mna.base.d.b.a(i, i2, j);
        } catch (Throwable th) {
        }
    }

    public static void addPushPkg(int i, int i2) {
        try {
            com.tencent.mna.base.d.b.a(i, i2);
        } catch (Throwable th) {
        }
    }

    public static void addRecvPkg(int i, int i2, int i3, long j) {
        try {
            com.tencent.mna.base.d.b.a(i, i2, i3, j);
        } catch (Throwable th) {
        }
    }

    public static void appendFps(int i) {
        try {
            com.tencent.mna.b.a.b.a(String.valueOf(i), "", "");
            h.b("c2j call appendFps:" + i);
        } catch (Throwable th) {
        }
    }
}
