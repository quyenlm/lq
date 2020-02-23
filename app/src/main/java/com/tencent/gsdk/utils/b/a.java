package com.tencent.gsdk.utils.b;

import android.content.Context;
import android.text.TextUtils;
import java.util.Map;

/* compiled from: AbsReporter */
abstract class a implements e {
    /* access modifiers changed from: package-private */
    public abstract boolean b(int i, String str, Map<String, String> map);

    /* access modifiers changed from: package-private */
    public abstract boolean b(Context context, String str);

    a() {
    }

    public boolean a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        return b(context, str);
    }

    public boolean a(int i, String str, Map<String, String> map) {
        if (TextUtils.isEmpty(str) || map == null) {
            return false;
        }
        return b(i, str, map);
    }
}
