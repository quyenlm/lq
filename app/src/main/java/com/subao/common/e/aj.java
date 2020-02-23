package com.subao.common.e;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.subao.common.d;
import com.subao.common.e;
import com.subao.common.f.c;
import com.subao.common.g;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/* compiled from: SubaoIdManager */
public class aj extends g<a> {
    private static final aj a = new aj();
    private c[] b;
    private String c;

    /* compiled from: SubaoIdManager */
    public interface a {
        void a(@Nullable String str);
    }

    private aj() {
    }

    public static aj b() {
        return a;
    }

    public static boolean a(@Nullable String str) {
        return str != null && str.length() == 36 && !"00000000-0000-0000-0000-000000000000".equals(str);
    }

    private static boolean d() {
        return d.a("SubaoData");
    }

    @NonNull
    private static c[] b(@NonNull Context context) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        return new c[]{com.subao.common.f.d.a(new File(externalStorageDirectory, ".sys")), com.subao.common.f.d.a(new File(new File(externalStorageDirectory, "Android"), ".sys")), com.subao.common.f.d.a(new File(new File(externalStorageDirectory, "9C52E85A-374A-4709-866F-0E708AE2B727"), ".sys")), com.subao.common.f.d.a(new File(context.getFilesDir(), ".sys"))};
    }

    @Nullable
    private static String a(@NonNull c[] cVarArr) {
        Pair<String, Integer> b2 = b(cVarArr);
        if (b2 == null) {
            d.a("SubaoData", "No SubaoId load, maybe first install");
            return null;
        }
        String str = (String) b2.first;
        if (((Integer) b2.second).intValue() == cVarArr.length) {
            return str;
        }
        a(cVarArr, str);
        return str;
    }

    @Nullable
    private static Pair<String, Integer> b(@NonNull c[] cVarArr) {
        ArrayList<Pair<String, Integer>> arrayList = new ArrayList<>(cVarArr.length);
        for (c cVar : cVarArr) {
            if (cVar != null) {
                a((List<Pair<String, Integer>>) arrayList, a(cVar));
            }
        }
        Pair<String, Integer> pair = null;
        for (Pair<String, Integer> pair2 : arrayList) {
            if (pair != null && ((Integer) pair.second).intValue() >= ((Integer) pair2.second).intValue()) {
                pair2 = pair;
            }
            pair = pair2;
        }
        return pair;
    }

    private static void a(@NonNull List<Pair<String, Integer>> list, @Nullable String str) {
        if (str != null && str.length() == 36) {
            int size = list.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                Pair pair = list.get(size);
                if (com.subao.common.n.g.a((CharSequence) str, (CharSequence) pair.first)) {
                    list.set(size, new Pair(pair.first, Integer.valueOf(((Integer) pair.second).intValue() + 1)));
                    break;
                }
                size--;
            }
            if (size < 0) {
                list.add(new Pair(str, 1));
            }
        }
    }

    @Nullable
    private static String a(@NonNull c cVar) {
        String str;
        String str2 = null;
        if (cVar.b()) {
            try {
                byte[] a2 = cVar.a(512);
                if (a2 != null) {
                    str = new String(a2);
                } else {
                    str = null;
                }
                str2 = str;
            } catch (IOException | RuntimeException e) {
            }
        }
        if (d()) {
            Log.d("SubaoData", String.format("Load SubaoId [%s] from \"%s\"", new Object[]{com.subao.common.n.g.a((Object) str2), cVar.i()}));
        }
        return str2;
    }

    private static void a(@NonNull c[] cVarArr, @Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            c(cVarArr);
            return;
        }
        boolean d = d();
        for (c cVar : cVarArr) {
            if (cVar != null) {
                boolean a2 = a(cVar, str);
                if (d) {
                    Log.d("SubaoData", "Save SubaoId to " + cVar.i() + (a2 ? " ok" : " failed"));
                }
            }
        }
    }

    private static boolean a(@NonNull c cVar, @NonNull String str) {
        OutputStream outputStream;
        Throwable th;
        OutputStream outputStream2 = null;
        try {
            OutputStream d = cVar.d();
            try {
                d.write(str.getBytes());
                e.a((Closeable) d);
                return true;
            } catch (IOException e) {
                outputStream2 = d;
                e.a((Closeable) outputStream2);
                return false;
            } catch (RuntimeException e2) {
                outputStream2 = d;
                e.a((Closeable) outputStream2);
                return false;
            } catch (Throwable th2) {
                th = th2;
                outputStream = d;
                e.a((Closeable) outputStream);
                throw th;
            }
        } catch (IOException | RuntimeException e3) {
        } catch (Throwable th3) {
            th = th3;
            outputStream = null;
            e.a((Closeable) outputStream);
            throw th;
        }
    }

    private static void c(@Nullable c[] cVarArr) {
        boolean z;
        String str;
        if (cVarArr != null) {
            for (c cVar : cVarArr) {
                if (cVar != null) {
                    try {
                        z = cVar.f();
                    } catch (RuntimeException e) {
                        z = false;
                    }
                    if (d()) {
                        Object[] objArr = new Object[2];
                        objArr[0] = cVar.i();
                        if (z) {
                            str = "OK";
                        } else {
                            str = "failed";
                        }
                        objArr[1] = str;
                        d.a("SubaoData", String.format("Delete file \"%s\" %s", objArr));
                    }
                }
            }
        }
    }

    public void a(@NonNull Context context) {
        a(context, (c[]) null);
    }

    /* access modifiers changed from: package-private */
    public void a(@NonNull Context context, @Nullable c[] cVarArr) {
        if (cVarArr == null) {
            cVarArr = b(context);
        }
        this.b = cVarArr;
        b(a(cVarArr));
    }

    public String c() {
        return this.c;
    }

    public synchronized void b(@Nullable String str) {
        if (d()) {
            Log.d("SubaoData", "set SubaoId: " + str);
        }
        if (!com.subao.common.n.g.a((CharSequence) this.c, (CharSequence) str)) {
            this.c = str;
            a(this.b, str);
            List<a> a2 = a();
            if (a2 != null) {
                for (a a3 : a2) {
                    a3.a(str);
                }
            }
        }
    }
}
