package com.subao.vpn;

import android.support.annotation.Nullable;

public interface JniCallback {
    void a(int i);

    void a(int i, int i2);

    void a(int i, int i2, int i3);

    void a(int i, int i2, int i3, boolean z, String str);

    void a(int i, int i2, @Nullable String str);

    void a(int i, int i2, String str, String str2, @Nullable String str3, @Nullable String str4);

    void a(int i, String str);

    void a(int i, String str, int i2, String str2);

    void a(int i, String str, String str2);

    void a(int i, String str, String str2, String str3);

    void a(int i, String str, String str2, String str3, int i2);

    void a(String str);

    void a(String str, String str2, String str3);

    void a(boolean z);

    int b(int i);

    void b(int i, String str);

    void b(int i, String str, String str2);

    void b(int i, String str, String str2, String str3);

    void b(String str);

    void b(String str, String str2, String str3);

    void c(int i, String str);

    void d(int i, String str);

    void e(int i, String str);
}
