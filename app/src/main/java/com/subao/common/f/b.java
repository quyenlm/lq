package com.subao.common.f;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.text.TextUtils;
import android.util.JsonReader;
import com.appsflyer.share.Constants;
import com.subao.common.e;
import com.subao.common.g.c;
import com.subao.common.m.d;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* compiled from: LogCatcher */
public class b {
    static C0008b a = null;
    static a b = null;
    private static final String c = (Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.URL_PATH_DELIMITER);
    private static final String d = (c + "wsds/");
    private static final String e = (d + "wsds_logs/");
    private static boolean f = false;
    private static String g = null;

    /* compiled from: LogCatcher */
    static class a extends BroadcastReceiver {
        a() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("cn.wsds.log.action.start_catch".equals(action)) {
                b.b((String) null);
            } else if ("cn.wsds.log.action.stop_catch".equals(action)) {
                b.a();
            }
        }
    }

    /* renamed from: com.subao.common.f.b$b  reason: collision with other inner class name */
    /* compiled from: LogCatcher */
    static class C0008b implements Runnable {
        Process a;
        private String b;
        private boolean c = true;
        private File d;

        C0008b(File file) {
            this.d = file;
            b();
        }

        private void b() {
            try {
                if (!this.d.exists()) {
                    this.c = this.d.mkdirs();
                }
                this.b = String.format("%s/%s_%s.log", new Object[]{this.d.getAbsolutePath(), this.d.getName(), new SimpleDateFormat("yyyyMMdd_HH_mm_ss").format(new Date())});
                b.a(this.d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            if (this.c) {
                ArrayList arrayList = new ArrayList();
                arrayList.add("logcat");
                arrayList.add("-f");
                arrayList.add(this.b);
                arrayList.add("-v");
                arrayList.add("time");
                arrayList.add("*:D");
                try {
                    this.a = Runtime.getRuntime().exec((String[]) arrayList.toArray(new String[arrayList.size()]));
                } catch (Exception e) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            try {
                if (this.a != null) {
                    this.a.destroy();
                    this.a = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void a(Context context, String str, c cVar) {
        synchronized (b.class) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    a(context);
                    if (a(cVar)) {
                        b(a(str));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return;
    }

    static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.length() > 12 ? str.substring(0, 12) : str;
    }

    static synchronized void b(String str) {
        synchronized (b.class) {
            if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(g)) {
                if (a == null) {
                    if (!TextUtils.isEmpty(str)) {
                        g = String.format("%s%s/", new Object[]{e, str});
                    }
                    a = new C0008b(new File(g));
                    d.a().execute(a);
                }
            }
        }
    }

    static void a() {
        C0008b bVar;
        synchronized (b.class) {
            bVar = a;
            a = null;
        }
        if (bVar != null) {
            bVar.a();
        }
        f = false;
    }

    static void a(Context context) {
        if (b == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("cn.wsds.log.action.start_catch");
            intentFilter.addAction("cn.wsds.log.action.stop_catch");
            b = new a();
            context.registerReceiver(b, intentFilter);
        }
    }

    public static boolean a(c cVar) {
        File a2 = a(new File(d), new File(d, "log.config"));
        f = a2 != null && a(a2, "log_action");
        if (f && cVar != null) {
            cVar.c();
        }
        return f;
    }

    public static boolean b() {
        return f;
    }

    public static File a(File file, File file2) {
        if (!file.exists() || !file.isDirectory() || !file2.exists() || !file2.isFile()) {
            return null;
        }
        return file2;
    }

    public static boolean a(File file, String str) {
        if (file == null || !file.exists() || !file.isFile() || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return a(new JsonReader(new BufferedReader(new FileReader(file), 1024)), str);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    static boolean a(JsonReader jsonReader, String str) {
        boolean z = false;
        if (jsonReader != null && !TextUtils.isEmpty(str)) {
            try {
                jsonReader.beginObject();
                while (true) {
                    if (!jsonReader.hasNext()) {
                        break;
                    } else if (str.equals(jsonReader.nextName())) {
                        z = jsonReader.nextBoolean();
                        break;
                    } else {
                        jsonReader.skipValue();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (RuntimeException e3) {
                e3.printStackTrace();
            } finally {
                e.a((Closeable) jsonReader);
            }
        }
        return z;
    }

    static void a(File file) {
        if (file != null && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    file2.delete();
                }
            }
        }
    }
}
