package com.subao.common.n;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import com.subao.common.e.n;
import com.subao.common.n.d;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: InfoUtils */
public class e {
    /* access modifiers changed from: private */
    public static d.a a = d.a.a;

    public static String a(Context context) {
        return null;
    }

    @Nullable
    public static String a(Context context, ApplicationInfo applicationInfo) {
        PackageManager packageManager;
        if (applicationInfo == null) {
            applicationInfo = context.getApplicationInfo();
        }
        if (applicationInfo == null || (packageManager = context.getPackageManager()) == null) {
            return null;
        }
        return applicationInfo.loadLabel(packageManager).toString();
    }

    /* access modifiers changed from: private */
    public static long b(String str, String str2, long j) {
        try {
            return a(d.a(new File(str), 1048576), str2, j);
        } catch (IOException e) {
            return j;
        }
    }

    static long a(byte[] bArr, String str, long j) {
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            byte b = bArr[i];
            if (i == 0 || b == 10) {
                if (b == 10) {
                    i++;
                }
                int i2 = i;
                while (i2 < length) {
                    int i3 = i2 - i;
                    if (bArr[i2] != str.charAt(i3)) {
                        continue;
                        break;
                    } else if (i3 == str.length() - 1) {
                        return com.subao.common.e.a(bArr, i2, bArr.length, -1);
                    } else {
                        i2++;
                    }
                }
                continue;
            }
            i++;
        }
        return j;
    }

    public static long b(Context context) {
        return a(context, Build.VERSION.SDK_INT);
    }

    static long a(Context context, int i) {
        if (i >= 16) {
            return c(context);
        }
        return a();
    }

    static long a() {
        long b = b("/proc/meminfo", "MemTotal", -1);
        if (b > 0) {
            return b * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        }
        return b;
    }

    @TargetApi(16)
    private static long c(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null) {
            return -1;
        }
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }

    /* compiled from: InfoUtils */
    public static class a {
        public static String a() {
            try {
                return a(e.a.b("/proc/cpuinfo"));
            } catch (IOException | RuntimeException e) {
                return null;
            }
        }

        private static String a(Reader reader) {
            String group;
            BufferedReader bufferedReader = new BufferedReader(reader);
            try {
                Pattern compile = Pattern.compile("Hardware\\s*:\\s*(.+)", 2);
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        throw new EOFException();
                    }
                    Matcher matcher = compile.matcher(readLine);
                    if (matcher.find() && (group = matcher.group(1)) != null) {
                        return group.trim();
                    }
                }
            } finally {
                com.subao.common.e.a((Closeable) bufferedReader);
            }
        }

        public static int b() {
            return a(Build.VERSION.SDK_INT);
        }

        static int a(int i) {
            if (i <= 10) {
                return 1;
            }
            try {
                int a = a(e.a.a("/sys/devices/system/cpu/"));
                if (a <= 1) {
                    a = 1;
                }
                return a;
            } catch (RuntimeException e) {
                return 1;
            }
        }

        private static int a(File file) {
            File[] listFiles = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    String name = file.getName();
                    if (!name.startsWith("cpu")) {
                        return false;
                    }
                    for (int i = 3; i < name.length(); i++) {
                        if (!Character.isDigit(name.charAt(i))) {
                            return false;
                        }
                    }
                    return true;
                }
            });
            if (listFiles == null) {
                return 0;
            }
            return listFiles.length;
        }

        public static long c() {
            return b(b());
        }

        static long b(int i) {
            int i2 = 0;
            long j = -1;
            while (i2 < i) {
                try {
                    long a = a(String.format(n.b, "/sys/devices/system/cpu/cpu%d/cpufreq/cpuinfo_max_freq", new Object[]{Integer.valueOf(i2)}));
                    if (a <= j) {
                        a = j;
                    }
                    i2++;
                    j = a;
                } catch (IOException | RuntimeException e) {
                    return j;
                }
            }
            if (j > 0) {
                return j;
            }
            long a2 = e.b("/proc/cpuinfo", "cpu MHz", -1);
            if (a2 != -1) {
                return 1000 * a2;
            }
            return j;
        }

        private static long a(String str) {
            InputStream inputStream = null;
            try {
                inputStream = e.a.c(str);
                byte[] bArr = new byte[128];
                return com.subao.common.e.a(bArr, 0, inputStream.read(bArr), -1);
            } finally {
                com.subao.common.e.a((Closeable) inputStream);
            }
        }
    }
}
