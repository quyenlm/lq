package com.subao.common.n;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/* compiled from: AppLauncher */
public class a {
    public static boolean a(Context context, String str) {
        return a(context, str, (C0019a) null);
    }

    static boolean a(Context context, String str, C0019a aVar) {
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            Intent b2 = b(context, str);
            if (b2 == null) {
                return false;
            }
            if (aVar == null) {
                aVar = new C0019a();
            }
            if (aVar.a(b2, (b) null)) {
                return true;
            }
            context.startActivity(b2);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static Intent b(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            return packageManager.getLaunchIntentForPackage(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(Intent intent) {
        ComponentName component;
        if (intent == null || (component = intent.getComponent()) == null) {
            return null;
        }
        return String.format("%s/%s", new Object[]{component.getPackageName(), component.getClassName()});
    }

    /* renamed from: com.subao.common.n.a$a  reason: collision with other inner class name */
    /* compiled from: AppLauncher */
    static class C0019a {
        C0019a() {
        }

        public boolean a(Intent intent, b bVar) {
            String a = a.a(intent);
            if (a == null) {
                return false;
            }
            if (bVar == null) {
                bVar = new b();
            }
            bVar.a(new String[]{"am", "start", "--user", "0", a});
            try {
                bVar.a(true);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bVar.a().getInputStream()));
                boolean z = false;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        return z;
                    }
                    String lowerCase = readLine.toLowerCase(Locale.US);
                    if (lowerCase.contains("starting: intent")) {
                        z = true;
                    } else if (lowerCase.contains("error") || lowerCase.contains("exception")) {
                        z = false;
                    }
                }
            } catch (IOException | RuntimeException e) {
                return false;
            }
        }
    }

    /* compiled from: AppLauncher */
    static class b {
        private final ProcessBuilder a = new ProcessBuilder(new String[0]);

        b() {
        }

        public Process a() {
            return this.a.start();
        }

        public void a(boolean z) {
            this.a.redirectErrorStream(z);
        }

        public void a(String[] strArr) {
            this.a.command(strArr);
        }
    }
}
