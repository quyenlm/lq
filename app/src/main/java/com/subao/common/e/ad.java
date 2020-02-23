package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.subao.common.e.f;
import com.subao.common.e.z;
import com.subao.common.i.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: PortalMiscConfigDownloader */
public class ad extends ac {
    /* access modifiers changed from: private */
    public static String c;
    /* access modifiers changed from: private */
    public static boolean d = false;
    private final a a;
    private final b b = new b();

    /* compiled from: PortalMiscConfigDownloader */
    public interface a {
        void a(b bVar);

        void a(@Nullable String str);
    }

    ad(z.a aVar, a aVar2) {
        super(aVar);
        this.a = aVar2;
    }

    public static void a(z.a aVar, a aVar2) {
        ac.a((ac) new ad(aVar, aVar2));
    }

    /* access modifiers changed from: protected */
    public void b(aa aaVar) {
        if (this.a != null) {
            this.a.a((aaVar == null || aaVar.c == null) ? "" : new String(aaVar.c));
        }
        super.b(aaVar);
    }

    /* access modifiers changed from: protected */
    public void a(@NonNull String str, String str2) {
        this.b.a(str, str2);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return "configs/misc";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "misc-config";
    }

    /* access modifiers changed from: protected */
    public void a(boolean z) {
        super.a(z);
        d.a.a(b.a(this.b.a), b.a(this.b.b), b.a(this.b.d), b.a(this.b.c));
        com.subao.common.b.a.a(this.b.e);
        an.a(this.b.f);
        if (this.a != null) {
            this.a.a(this.b);
        }
    }

    /* compiled from: PortalMiscConfigDownloader */
    public static class b {
        /* access modifiers changed from: private */
        public int a = 100;
        /* access modifiers changed from: private */
        public int b = 1000;
        /* access modifiers changed from: private */
        public int c = 10000;
        /* access modifiers changed from: private */
        public int d = 0;
        /* access modifiers changed from: private */
        public boolean e;
        /* access modifiers changed from: private */
        public String f;
        private f.a[] g;
        private f.a[] h;
        private int i;
        private final Map<String, String> j = new HashMap(4);

        private static boolean a(String str) {
            return "1".equals(str) || "true".equalsIgnoreCase(str);
        }

        static boolean a(int i2) {
            return a(i2, System.currentTimeMillis());
        }

        static boolean a(int i2, long j2) {
            if (i2 <= 0) {
                return false;
            }
            if (i2 >= 10000 || ((int) (16777215 & j2)) % 10000 < i2) {
                return true;
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void a(String str, String str2) {
            boolean z = true;
            try {
                if ("er_tg".equals(str)) {
                    this.a = Integer.parseInt(str2);
                } else if ("er_auth".equals(str)) {
                    this.b = Integer.parseInt(str2);
                } else if ("er_was".equals(str)) {
                    this.c = Integer.parseInt(str2);
                } else if ("er_ml".equals(str)) {
                    this.d = Integer.parseInt(str2);
                } else if ("auth_http".equals(str)) {
                    this.e = a(str2);
                } else if ("acc_info_up_proto".equals(str)) {
                    this.f = str2;
                } else if ("qos_zte_primary".equals(str)) {
                    this.g = b(str2);
                } else if ("qos_zte_secondary".equals(str)) {
                    this.h = b(str2);
                } else if ("auth_cache_time".equals(str)) {
                    this.i = Integer.parseInt(str2);
                } else if ("enable_game_download".equals(str)) {
                    String unused = ad.c = str2;
                } else if ("enable_SVIP".equals(str)) {
                    if (Integer.parseInt(str2) != 1) {
                        z = false;
                    }
                    boolean unused2 = ad.d = z;
                } else {
                    this.j.put(str, str2);
                }
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }

        private static f.a[] b(String str) {
            int i2;
            f.a aVar;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            String[] split = str.split(",");
            ArrayList arrayList = new ArrayList(split.length);
            for (String str2 : split) {
                int indexOf = str2.indexOf(58);
                if (indexOf < 0) {
                    aVar = new f.a(str2, -1);
                } else {
                    try {
                        i2 = Integer.parseInt(str2.substring(indexOf + 1));
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                        i2 = -1;
                    }
                    aVar = new f.a(str2.substring(0, indexOf), i2);
                }
                arrayList.add(aVar);
            }
            return (f.a[]) arrayList.toArray(new f.a[arrayList.size()]);
        }
    }
}
