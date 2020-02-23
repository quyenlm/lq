package com.subao.common.e;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import com.subao.common.d;
import com.subao.common.e.z;
import com.subao.common.g.c;
import com.subao.common.n.e;
import com.subao.common.n.f;
import com.subao.common.n.g;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: ParallelConfigDownloader */
public class y extends z {
    private static boolean a;
    private final c b;

    y(z.a aVar, c cVar) {
        super(aVar);
        this.b = cVar;
    }

    public static void a(z.a aVar, c cVar, @NonNull String str) {
        if (!"827006BE-64F7-4082-B252-33ACF328A3A5".equals(str) && !"84CC36C0-839F-4D72-8D6A-4B80D45BCD1C".equals(str)) {
            a = Build.VERSION.SDK_INT >= 21;
        }
        y yVar = new y(aVar, cVar);
        aa l = yVar.l();
        yVar.b(l);
        yVar.e(l);
    }

    public static boolean d() {
        boolean z;
        synchronized (y.class) {
            z = a;
        }
        return z;
    }

    static boolean a(a aVar, int i, String str, String str2) {
        if (i <= 0) {
            i = Build.VERSION.SDK_INT;
        }
        if (i < 21) {
            d.a("SubaoParallel", "Android SDK version too low");
            return false;
        } else if (aVar == null) {
            return false;
        } else {
            if (!aVar.a()) {
                d.a("SubaoParallel", "Parallel-Accel switch off");
                return false;
            }
            if (str == null) {
                str = Build.MODEL;
            }
            if (aVar.b(str)) {
                d.a("SubaoParallel", String.format("The model '%s' matched", new Object[]{str}));
                return true;
            }
            d.a("SubaoParallel", String.format("The model '%s' is not matched, check CPU ...", new Object[]{str}));
            if (str2 == null) {
                str2 = e.a.a();
            }
            if (aVar.a(str2)) {
                d.a("SubaoParallel", String.format("The CPU '%s' matched", new Object[]{str2}));
                return true;
            }
            d.a("SubaoParallel", String.format("The CPU '%s' is not matched", new Object[]{str2}));
            return false;
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "Parallel";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return "configs/parallel";
    }

    /* access modifiers changed from: protected */
    public void a(aa aaVar) {
        super.a(aaVar);
        if (aaVar != null && aaVar.d) {
            b(aaVar);
        }
    }

    private void b(@Nullable aa aaVar) {
        int i;
        boolean a2 = a(a.a(aaVar), -1, (String) null, (String) null);
        synchronized (y.class) {
            a = a2;
        }
        c cVar = this.b;
        if (a2) {
            i = 1;
        } else {
            i = 0;
        }
        cVar.a(0, "key_enable_qpp", i);
        if (d.a("SubaoParallel")) {
            Log.d("SubaoParallel", "Now switch turn to " + (a2 ? "on" : "off"));
        }
    }

    /* compiled from: ParallelConfigDownloader */
    static class a {
        private final boolean a;
        private final List<String> b;
        private final List<String> c;

        private a(boolean z, List<String> list, List<String> list2) {
            this.a = z;
            this.b = list;
            this.c = list2;
        }

        static a a(boolean z, List<String> list, List<String> list2) {
            if (list == null || list2 == null || list.isEmpty() || list2.isEmpty()) {
                return null;
            }
            return new a(z, list, list2);
        }

        @Nullable
        public static a a(aa aaVar) {
            a aVar = null;
            if (aaVar != null && aaVar.b() >= 8) {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(aaVar.a())));
                try {
                    aVar = a(jsonReader);
                } catch (IOException | RuntimeException e) {
                    e.printStackTrace();
                } finally {
                    com.subao.common.e.a((Closeable) jsonReader);
                }
            }
            return aVar;
        }

        private static boolean a(List<String> list, String str) {
            if (list == null || TextUtils.isEmpty(str)) {
                return false;
            }
            String lowerCase = str.toLowerCase();
            for (String contains : list) {
                if (lowerCase.contains(contains)) {
                    return true;
                }
            }
            return false;
        }

        private static List<String> b() {
            return new ArrayList(128);
        }

        private static a a(JsonReader jsonReader) {
            List<String> b2 = b();
            List<String> b3 = b();
            boolean z = false;
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String nextName = jsonReader.nextName();
                    if ("model".equals(nextName)) {
                        a(f.a(jsonReader), b2);
                    } else if ("cpu".equals(nextName)) {
                        a(f.a(jsonReader), b3);
                    } else if ("switch".equals(nextName)) {
                        z = "1".equals(jsonReader.nextString());
                    } else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.endObject();
                return a(z, b2, b3);
            } catch (RuntimeException e) {
                e = e;
                throw new IOException(e.getMessage());
            } catch (AssertionError e2) {
                e = e2;
                throw new IOException(e.getMessage());
            }
        }

        static int a(String str, List<String> list) {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            StringReader stringReader = new StringReader(str.toLowerCase());
            try {
                return g.a((Reader) stringReader, list);
            } finally {
                com.subao.common.e.a((Closeable) stringReader);
            }
        }

        public boolean a() {
            return this.a;
        }

        public boolean a(String str) {
            return a(this.c, str);
        }

        public boolean b(String str) {
            return a(this.b, str);
        }

        public String toString() {
            int size;
            int i = 0;
            Locale locale = n.b;
            Object[] objArr = new Object[3];
            objArr[0] = Boolean.valueOf(this.a);
            if (this.c == null) {
                size = 0;
            } else {
                size = this.c.size();
            }
            objArr[1] = Integer.valueOf(size);
            if (this.b != null) {
                i = this.b.size();
            }
            objArr[2] = Integer.valueOf(i);
            return String.format(locale, "[enable=%b, cpu=%d, model=%d]", objArr);
        }
    }
}
