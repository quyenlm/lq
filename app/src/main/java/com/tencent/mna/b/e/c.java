package com.tencent.mna.b.e;

import android.content.Context;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.mna.base.utils.n;
import com.tencent.mna.base.utils.q;

/* compiled from: RouterQuery */
public class c {

    /* compiled from: RouterQuery */
    public static class a {
        public String a = "";
        public int b = 0;
        public String c = "通畅";
        public String d = "";
        public int e = 0;
    }

    public static a a(Context context, String str, int i, int i2, double d, String str2) {
        a aVar = new a();
        try {
            aVar.a = str;
            StringBuilder sb = new StringBuilder();
            String c = q.c(context);
            int i3 = 0;
            for (int i4 = 0; i4 < i; i4++) {
                int a2 = n.a(c, 1);
                if (a2 > i2) {
                    i3++;
                }
                sb.append(a2);
                if (i4 < i - 1) {
                    sb.append(',');
                }
            }
            if (sb.length() > 0) {
                aVar.d = sb.toString();
            }
            int a3 = q.a(context);
            double d2 = i > 0 ? ((double) i3) / ((double) i) : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            String[] split = str2.split("#");
            if (split != null && split.length >= 3 && a3 > 0) {
                aVar.e = a3;
                if (a3 > 0 && a3 <= 2) {
                    aVar.c = split[0];
                    aVar.b = 1;
                } else if (a3 == 3 && d2 > d) {
                    aVar.c = split[1];
                    aVar.b = 1;
                } else if (a3 == 4 && d2 > d) {
                    aVar.c = split[2];
                    aVar.b = 1;
                }
            }
        } catch (Exception e) {
        }
        return aVar;
    }
}
