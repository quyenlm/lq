package com.tencent.gsdk.utils.b;

import android.content.Context;
import java.util.Map;

/* compiled from: BeaconReporterImpl */
final class b extends a {
    b() {
    }

    /* access modifiers changed from: package-private */
    public boolean b(Context context, String str) {
        return j.a(str) && j.a(context.getApplicationContext());
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i, String str, Map<String, String> map) {
        return j.a(str, true, 0, -1, map, true);
    }
}
