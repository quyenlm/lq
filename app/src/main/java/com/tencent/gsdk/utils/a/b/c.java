package com.tencent.gsdk.utils.a.b;

import java.util.Locale;

/* compiled from: EnvironmentImpl */
final class c implements d {
    private String a;

    public c(String str) {
        this.a = str;
    }

    public String a(String str) {
        return String.format(Locale.US, "%s/?cmdid=%d&appid=%s", new Object[]{this.a, 1, str});
    }

    public String b(String str) {
        return String.format(Locale.US, "%s/?cmdid=%d&appid=%s", new Object[]{this.a, 3, str});
    }

    public String c(String str) {
        return String.format(Locale.US, "%s/?cmdid=%d&appid=%s", new Object[]{this.a, 4, str});
    }
}
