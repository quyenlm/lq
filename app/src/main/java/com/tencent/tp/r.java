package com.tencent.tp;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemClock;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.amazonaws.services.s3.internal.Constants;
import com.facebook.internal.AnalyticsEvents;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.component.net.download.multiplex.http.MttRequest;
import com.tencent.imsdk.tool.etc.APNUtil;
import com.tencent.tp.a.h;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

public final class r {
    public static final String a = "android.permission.READ_PHONE_STATE";
    private static String b = null;
    private static String c = null;

    public static String a(Context context) {
        if (b != null && b.length() > 0) {
            return b;
        }
        String o = o(context);
        if (o.toUpperCase(Locale.getDefault()).indexOf("CPUNAME") != -1) {
            return o.substring(8);
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/proc/cpuinfo"))));
            String readLine = bufferedReader.readLine();
            while (true) {
                if (readLine == null) {
                    break;
                } else if (readLine.indexOf("Processor") != -1) {
                    o = readLine.substring(readLine.indexOf(":") + 1).trim();
                    readLine = bufferedReader.readLine();
                } else if (readLine.indexOf("vendor_id") != -1) {
                    o = readLine.substring(readLine.indexOf(":") + 1).trim();
                    readLine = bufferedReader.readLine();
                } else if (readLine.indexOf("Hardware") != -1) {
                    o = o + h.a + readLine.substring(readLine.indexOf(":") + 1).trim() + h.b;
                    break;
                } else if (readLine.indexOf("model name") != -1) {
                    o = o + h.a + readLine.substring(readLine.indexOf(":") + 1).trim() + h.b;
                    break;
                } else {
                    readLine = bufferedReader.readLine();
                }
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (o == null || o.length() == 0) {
                b = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            }
            b = o;
            return o;
        } catch (Throwable th) {
            b = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    private static String a(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return "";
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            return readLine;
        } catch (Throwable th) {
            return "";
        }
    }

    public static boolean a() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String b() {
        return String.valueOf(SystemClock.elapsedRealtime());
    }

    public static String b(Context context) {
        String o = o(context);
        return o.toUpperCase(Locale.getDefault()).indexOf("CPUNAME:") != -1 ? o.toUpperCase(Locale.getDefault()).indexOf("ATOM") == -1 ? i() + h.a + h.a().i(context) + h.b : "REAL_DEVICE(" + o.substring(8) + h.b : "REAL_DEVICE(arm)";
    }

    private static String b(String str) {
        try {
            Process exec = Runtime.getRuntime().exec(str);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
            char[] cArr = new char[4096];
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                int read = bufferedReader.read(cArr);
                if (read <= 0) {
                    break;
                }
                stringBuffer.append(cArr, 0, read);
            }
            bufferedReader.close();
            StringBuffer stringBuffer2 = new StringBuffer();
            while (true) {
                int read2 = bufferedReader2.read(cArr);
                if (read2 <= 0) {
                    break;
                }
                stringBuffer2.append(cArr, 0, read2);
            }
            bufferedReader2.close();
            exec.waitFor();
            return (stringBuffer == null || stringBuffer.toString().trim().length() <= 0) ? (stringBuffer2 == null || stringBuffer2.toString().trim().length() <= 0) ? "" : stringBuffer2.toString() : stringBuffer.toString();
        } catch (Throwable th) {
            return "";
        }
    }

    public static DisplayMetrics c(Context context) {
        if (context == null) {
            return null;
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String c() {
        try {
            return String.valueOf(Build.VERSION.SDK_INT);
        } catch (Throwable th) {
            return Constants.NULL_VERSION_ID;
        }
    }

    public static int d() {
        return Integer.parseInt(c());
    }

    public static long d(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
            return memoryInfo.availMem;
        } catch (Throwable th) {
            return -1;
        }
    }

    public static String e() {
        try {
            return Locale.getDefault().getCountry();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String e(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "unknown";
            }
            if (activeNetworkInfo.getType() == 1) {
                return "wifi";
            }
            if (activeNetworkInfo.getType() != 0) {
                return "unknown";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return "unknown";
            }
            int networkType = telephonyManager.getNetworkType();
            switch (networkType) {
                case 0:
                    return "unknown";
                case 1:
                    return "GPRS";
                case 2:
                    return "EDGE";
                case 3:
                    return "UMTS";
                case 4:
                    return "CDMA";
                case 5:
                    return "CDMA - EVDO rev. 0";
                case 6:
                    return "CDMA - EVDO rev. A";
                case 7:
                    return "CDMA - 1xRTT";
                case 8:
                    return "HSDPA";
                case 9:
                    return "HSUPA";
                case 10:
                    return "HSPA";
                case 11:
                    return "iDen";
                case 12:
                    return "CDMA - EVDO rev. B";
                case 13:
                    return "LTE";
                case 14:
                    return "eHRPD";
                case 15:
                    return "HSPA+";
                default:
                    return "unknown(" + String.valueOf(networkType) + h.b;
            }
        } catch (Throwable th) {
            return "unknown";
        }
    }

    public static String f() {
        return Build.CPU_ABI + "|" + Build.CPU_ABI2;
    }

    public static String f(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            return activeNetworkInfo.getType() == 1 ? "wifi" : "" + activeNetworkInfo.getExtraInfo();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String g() {
        Field field;
        if (Build.VERSION.SDK_INT < 21) {
            return Build.CPU_ABI + "|" + Build.CPU_ABI2;
        }
        Field[] fields = Build.class.getFields();
        int i = 0;
        while (true) {
            if (i >= fields.length) {
                field = null;
                break;
            } else if (fields[i].getName().equals("SUPPORTED_ABIS")) {
                field = fields[i];
                break;
            } else {
                i++;
            }
        }
        if (field == null) {
            return Build.CPU_ABI + "|" + Build.CPU_ABI2;
        }
        try {
            Object obj = field.get(Build.class);
            if (!obj.getClass().isArray()) {
                return "";
            }
            String str = "";
            for (int i2 = 0; i2 < Array.getLength(obj); i2++) {
                str = str + Array.get(obj, i2).toString();
                if (i2 != Array.getLength(obj) - 1) {
                    str = str + "|";
                }
            }
            return str;
        } catch (Throwable th) {
            return Build.CPU_ABI + "|" + Build.CPU_ABI2;
        }
    }

    public static String g(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            return v.a(packageManager.getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray());
        } catch (Throwable th) {
            return null;
        }
    }

    public static String h() {
        return System.getProperty("os.arch");
    }

    public static String h(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName + h.a + String.valueOf(packageInfo.versionCode) + h.b;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String i() {
        if (Build.VERSION.SDK_INT < 8) {
            return null;
        }
        try {
            return Build.HARDWARE;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String i(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            if (applicationInfo != null) {
                return packageManager.getApplicationLabel(applicationInfo).toString();
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public static long j(Context context) {
        try {
            return (long) (((ActivityManager) context.getSystemService("activity")).getProcessMemoryInfo(new int[]{Process.myPid()})[0].dalvikPss / 1024);
        } catch (Throwable th) {
            return 0;
        }
    }

    public static String j() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            return null;
        }
    }

    public static float k(Context context) {
        try {
            Intent registerReceiver = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = registerReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1);
            int intExtra2 = registerReceiver.getIntExtra("scale", -1);
            if (intExtra == -1 || intExtra2 == -1) {
                return 50.0f;
            }
            return (((float) intExtra) / ((float) intExtra2)) * 100.0f;
        } catch (Throwable th) {
            return 0.0f;
        }
    }

    public static long k() {
        long j;
        long j2;
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            j2 = (long) statFs.getBlockSize();
            j = (long) statFs.getAvailableBlocks();
        } catch (Throwable th) {
            j = 0;
            j2 = 0;
        }
        return j * j2;
    }

    public static long l() {
        long j;
        long j2;
        try {
            StatFs statFs = new StatFs("/data");
            j2 = (long) statFs.getBlockSize();
            j = (long) statFs.getAvailableBlocks();
        } catch (Throwable th) {
            j = 0;
            j2 = 0;
        }
        return j * j2;
    }

    public static String l(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
        } catch (Throwable th) {
            return "UNKNOWN";
        }
    }

    public static long m(Context context) {
        return 0;
    }

    public static String m() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Throwable th) {
            return null;
        }
    }

    public static long n(Context context) {
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
            return memoryInfo.availMem >> 20;
        } catch (Throwable th) {
            return 0;
        }
    }

    public static String n() {
        try {
            Matcher matcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(a("/proc/version"));
            return !matcher.matches() ? "Unavailable" : matcher.groupCount() < 4 ? "Unavailable" : matcher.group(1) + " " + matcher.group(3);
        } catch (Throwable th) {
            return "Unavailable";
        }
    }

    public static String o() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(j());
        stringBuffer.append(";Android ");
        stringBuffer.append(r());
        stringBuffer.append(",level ");
        stringBuffer.append(c());
        return stringBuffer.toString();
    }

    private static String o(Context context) {
        if (c != null && c.length() > 0) {
            return c;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{80, 75, 3, 4, 20, 0, 0, 0, 8, 0, 119, 79, -87, 72, -15, -113, -82, 14, -70, 6, 0, 0, -100, 20, 0, 0, 9, 0, 0, 0, 109, 121, 99, 112, 117, 105, 110, MttRequest.REQUEST_DIRECT, 111, -19, 88, 89, -116, 75, 81, 24, 62, -9, -10, 78, 93, -116, -86, 125, 107, 40, -118, -79, -43, 18, -60, 26, 6, -75, -17, 12, 17, 75, 93, -67, 53, -45, -24, -76, -109, -10, 18, -124, 32, -75, -35, 84, 19, 66, -30, 69, 66, 108, 15, -120, 8, 34, 125, 16, 65, -83, -119, 4, -119, 32, -31, -127, 88, 82, -79, -124, 32, 17, -122, -6, -50, 98, -26, -86, 74, 120, -14, 50, MttRequest.REQUEST_BROKER, -14, -35, -1, -4, -1, -1, -99, -1, -4, MttRequest.REQUEST_BROKER, -21, -36, 115, -41, -5, -90, -116, -109, 36, -119, -4, 44, 54, -4, 81, 109, -121, -115, -112, 1, -112, 74, 19, 106, -91, 117, 55, 81, 73, 9, 105, 69, 90, 18, 59, -45, 107, -127, 6, 12, 10, -31, -80, 81, -69, 84, -117, MttRequest.REQUEST_DIRECT, -124, 67, -87, -95, -2, 90, -6, -86, 28, 69, 84, 113, 114, -1, 73, 7, -48, -98, 99, 6, 12, 115, 0, -69, -16, -53, 16, -41, 29, 64, 123, 14, 85, -30, -80, -117, 62, MttRequest.REQUEST_DIRECT, 60, 55, -12, -101, -11, 8, -7, 9, -99, 80, -44, -26, 55, 19, 126, 82, -96, -40, -123, -100, 5, Byte.MAX_VALUE, 126, -1, 51, 44, -15, -5, -60, 87, -57, -115, 96, MttRequest.REQUEST_NORMAL, -97, -91, -95, 72, -97, 112, 40, -78, 60, 24, -53, APNUtil.APNTYPE_3GNET, -108, 55, -58, -90, 64, -93, 60, -67, 109, -98, -34, -45, -94, -45, 41, 31, -111, -25, 31, -101, -89, 79, -55, -45, -25, -48, -118, 27, 96, 83, -12, 62, -73, 32, 79, 47, -89, -71, 91, 116, -30, -9, -121, 67, 75, 3, -2, 80, 36, 100, -96, 30, 88, -91, -7, 53, 35, -72, -118, 41, 113, 67, APNUtil.APNTYPE_3GNET, 44, -9, 7, 42, -106, -5, -105, 105, -95, -16, 47, -122, -14, 21, 90, 76, 39, 85, -79, 80, -60, 88, 6, 71, 48, 22, -117, 68, 9, -90, 35, -96, -123, 65, 12, -22, -102, -95, -63, -68, 52, 30, -89, 109, 98, 6, 76, 17, -99, -96, -93, 112, -76, -36, 27, -113, -46, 90, -36, -48, 3, 61, 123, 10, -91, 82, -56, Byte.MIN_VALUE, -112, 122, 24, 21, -74, Byte.MAX_VALUE, 26, 2, 42, -48, 0, -88, 95, 96, -83, 100, 49, -57, 54, -79, 110, -11, Byte.MIN_VALUE, 34, -79, 62, -73, 59, -16, -74, 119, -123, 124, 32, -28, 35, 33, 31, 67, -38, 65, 124, 7, 89, 15, 65, 62, 82, -119, Byte.MIN_VALUE, -97, -87, 68, -64, 106, 42, -117, 120, 63, -71, -45, 10, 125, 30, 84, -87, -62, 106, -59, 120, 86, -48, -22, -53, -57, 57, -108, -125, 78, -86, -85, 84, -65, -51, -12, -26, 84, 119, 82, -3, 2, -45, 91, 83, -99, 62, 94, -98, -124, -102, -46, 61, 31, -109, 99, 61, -50, -51, 23, 12, 57, 119, 27, 90, 113, 38, 117, -106, 70, 75, -99, -21, 70, 69, -103, -7, 124, 118, -10, 20, 42, 27, 51, -109, 93, -124, 36, -98, -67, 3, -25, 113, 50, -15, 26, 109, -81, -115, -11, 40, -108, 99, -114, -11, 52, 79, 38, -34, -63, -126, 90, -21, 100, -30, 35, -81, -71, 82, -80, 67, 22, 39, 19, -97, -71, 69, 77, -7, 20, 83, -15, 100, -57, -45, -114, -49, 116, -91, 109, MttRequest.REQUEST_BROKER, MttRequest.REQUEST_BROKER, -53, 88, -16, -73, -19, 8, 65, -28, 108, 42, -31, -30, 17, 85, 100, -27, -122, 84, 82, -119, -19, 79, -47, 26, -51, -36, 60, -33, -42, APNUtil.APNTYPE_3GNET, 50, -87, -107, 52, 63, -34, -66, 5, 107, Byte.MAX_VALUE, -126, -73, -1, -100, 109, -56, 73, -22, -126, 76, -46, -27, -55, 32, 125, 40, -17, -82, -7, -86, -39, -104, -54, -86, -105, -104, -9, -45, -88, 109, MttRequest.REQUEST_FILE_DOWNLOAD, 124, -64, -84, -89, 37, 125, -43, 55, 40, MttRequest.REQUEST_NORMAL, -34, 92, 78, -6, -56, 73, 31, -109, 83, -43, 37, -26, 61, -13, 86, -65, APNUtil.APNTYPE_3GNET, -96, 117, 48, -25, 41, -26, 116, -43, -76, -125, -2, 17, 60, -25, 98, -1, 34, -42, 40, 89, -90, -46, MttRequest.REQUEST_FILE_DOWNLOAD, -78, -120, MttRequest.REQUEST_DIRECT, -6, 25, 109, 94, 113, -38, -58, 45, -29, -99, -90, -65, -71, 57, -67, -75, 57, -49, -107, 86, -124, -55, 109, -6, 61, -26, -12, 18, 115, 94, 47, 77, 68, -103, 59, 27, 49, 119, MttRequest.REQUEST_BROKER, -89, 35, -19, -115, -103, 44, -114, 97, 50, 81, -115, 122, -110, -104, -66, MttRequest.REQUEST_BROKER, -41, 124, 71, MttRequest.REQUEST_FILE_DOWNLOAD, 70, -41, 124, 71, -71, 56, 65, -59, 85, -33, 41, -28, 120, -6, 124, 63, -44, -45, 108, -6, -17, 36, -98, 72, -101, 111, 24, -22, 54, 57, 113, 73, 74, -68, -107, -88, 65, -122, -95, -2, 50, 19, 22, 57, -15, 86, 54, -33, 95, 124, 41, -101, -9, 63, 28, 51, -65, -104, -17, -71, -45, 46, -100, 47, 36, -13, 61, -44, 34, -42, 56, -27, 59, 66, 39, -6, -27, -9, 92, 46, 123, 17, 15, -45, -73, Byte.MAX_VALUE, -29, -70, -3, 116, 60, 43, -69, -89, 124, -89, -87, 79, 70, 114, -88, -14, -43, 60, -62, 23, MttRequest.REQUEST_FILE_DOWNLOAD, -8, APNUtil.APNTYPE_3GNET, -66, 64, -5, -66, -27, 114, -81, 90, -44, -104, -89, 8, -13, 54, -104, -59, -88, -54, -98, 37, -55, -112, 75, 70, 81, 118, -20, 119, -74, 76, -31, 5, 98, 54, -79, -79, -78, 111, 96, 75, -45, 67, 116, 35, 67, 84, -53, -17, 115, 105, 68, -113, 69, 67, 58, 59, 119, 99, -86, 86, 76, -45, 42, -125, 67, 75, -70, -60, -69, 119, -119, 55, -8, 69, -97, 22, -91, 85, 88, 112, 72, -101, -1, -4, -35, 91, 51, -117, 72, 107, 85, -87, 93, -79, -94, 108, -109, -72, -35, 69, -49, 50, 114, 106, 70, 9, 99, 29, -50, 105, 14, -123, -76, 22, -1, 59, -100, -80, -49, -89, -10, 82, -121, -70, 89, 30, -35, -88, -88, 52, 97, -93, -4, 25, -64, 28, -8, 6, 9, 95, 66, -98, -18, 112, -21, 14, -75, 52, -29, 80, -88, -65, 10, 48, -32, 111, MttRequest.REQUEST_BROKER, -15, 59, 39, 114, 63, 61, 124, 59, Byte.MIN_VALUE, 40, -4, -20, -64, -120, 28, 14, 1, 126, -40, 58, 91, -5, -13, 95, 41, 86, 64, -14, -64, 116, 25, 88, 10, -1, 64, -85, Byte.MAX_VALUE, -36, 86, -37, 22, 37, 116, -75, -12, 90, 41, 37, -78, -97, -44, -41, 34, -10, -72, 95, -14, -34, 106, -109, 71, 113, 82, 9, 29, 23, -58, -66, 19, 28, 85, -78, 112, -58, -45, 88, -119, 34, -7, 75, -90, 84, 4, 100, 99, 25, 14, 78, 37, -42, -94, -87, 53, -34, -76, -38, -68, 22, -62, -65, -22, 107, 46, 55, -125, -6, 29, -22, 56, 71, -15, -92, -58, 13, 13, MttRequest.REQUEST_NORMAL, 21, 25, -39, 118, 88, -113, -2, -98, 78, 53, -1, 115, -38, -39, -122, -83, 66, -100, -122, 116, -18, -64, -65, -117, 29, 112, 8, 114, 55, 108, -105, 33, -113, 67, 122, -80, 45, -82, 67, -22, -112, 89, -56, -11, -112, 42, 120, 116, -69, -72, 32, -113, 64, 14, -121, 124, 8, -87, 67, 118, -60, 24, -74, 65, 118, 7, -9, 12, 36, -87, 43, 117, -91, -82, -44, -107, -70, 82, 87, -2, 67, -55, -95, 20, -110, 54, -32, 89, 7, 126, 47, 112, 3, -83, Byte.MIN_VALUE, 106, -103, -65, Byte.MAX_VALUE, 52, 17, -72, 43, -13, -69, 82, 9, -48, 76, -36, 47, -66, -28, 114, 81, 5, -78, -123, -27, 14, 56, 65, -30, -9, 15, -89, -72, 83, 124, -106, -8, 61, 100, -81, 120, 71, -38, 45, -13, -9, -92, 48, -31, 114, -91, -112, 9, 33, -109, 66, -18, 98, -9, 60, 126, -121, 116, -119, -2, -38, 0, 105, -24, -19, -88, 46, 114, -67, 0, -67, -93, -48, -37, APNUtil.APNTYPE_3GNET, -7, 21, 121, 73, -28, -33, APNUtil.APNTYPE_3GNET, -115, -107, 22, -9, -42, 79, -30, 14, 115, -67, 125, -83, Byte.MAX_VALUE, 16, 38, 106, 28, 48, 23, 88, 70, 39, 109, -4, -104, 49, 67, -35, 37, -29, -89, -107, 117, 119, 15, -16, 14, MttRequest.REQUEST_DIRECT, -29, -85, 47, -58, 9, 35, 41, -113, -122, 117, 119, 63, 111, -65, 126, -76, -79, 55, 94, 17, 55, 98, -122, -74, -108, 120, 113, 1, 12, -58, -86, -120, 87, 95, 29, -119, -81, -82, 100, 18, 46, -30, -83, -48, -30, 21, -60, 27, APNUtil.APNTYPE_3GNET, -122, -87, -123, 87, -86, -62, 6, -15, -30, 110, -119, MttRequest.REQUEST_BROKER, 36, 106, 4, -67, 26, Byte.MAX_VALUE, -67, -12, -122, -12, 96, 4, -74, 88, -108, -35, 26, -67, -63, 10, -1, -78, 24, -34, 39, 107, 107, -2, 10, 29, 17, -105, -31, -118, -22, -41, 98, 49, 109, 53, -19, 53, 100, -4, -84, 87, -59, -126, -75, 42, 75, 64, -85, 12, 5, -120, -73, 60, 106, -80, 7, -17, 23, 87, 81, -30, 13, 68, 43, 43, -47, -105, -24, -65, 60, -78, -62, 75, -121, -43, 123, MttRequest.REQUEST_NORMAL, 48, 22, 15, 69, 35, -28, 111, 75, 67, -79, -82, 50, -79, 126, -37, -8, -3, -50, -33, 76, 112, MttRequest.REQUEST_NORMAL, -74, -105, 56, 78, -46, 61, 42, 56, -118, -40, 91, -19, -124, 77, MttRequest.REQUEST_DIRECT, 123, -116, 99, 111, -127, 120, -99, -59, 62, -108, -39, -34, -29, 24, -59, 116, 94, 20, -127, -18, 98, -19, 100, -74, -41, 57, 74, -84, 60, -79, -73, -6, APNUtil.APNTYPE_3GNET, -34, MttRequest.REQUEST_FILE_DOWNLOAD, 118, 70, 56, -36, -126, 87, -49, -62, 27, 36, 114, -80, -45, 125, 106, -29, -104, 97, -55, -49, 41, -72, -61, 44, -68, 42, 27, -57, 30, -39, -54, -29, -91, -44, 50, Byte.MAX_VALUE, -86, -99, -93, -75, 37, -98, 34, 100, -103, -123, -25, -74, 115, 116, 43, 48, 47, -117, 44, -68, 9, 118, -114, 77, -46, -17, -15, -54, 107, 120, -42, 111, 66, -65, -13, 86, 0, -115, -39, -102, -120, 111, 63, 14, -28, 88, Byte.MIN_VALUE, -73, 17, 112, 8, 94, -102, -98, -73, 63, -16, 82, 98, -20, 54, 113, 46, 47, -4, -127, -73, 87, -52, -99, 77, -100, -43, -21, -108, 39, 65, -49, 91, -73, -61, -106, 111, 30, -73, 59, 0, 72, -74, 121, -127, 120, 71, 45, -68, MttRequest.REQUEST_BROKER, 29, 0, -16, 92, 5, 120, -89, 89, 92, -47, -113, -101, 39, -85, 20, -32, -91, 69, -68, -66, -106, 5, 117, 90, -41, 67, -32, -94, -40, 63, 53, 52, MttRequest.REQUEST_BROKER, -127, 126, 107, 114, -85, 45, -67, -64, 123, 80, 96, 125, Byte.MAX_VALUE, 0, 80, 75, 1, 2, 63, 0, 20, 0, 0, 0, 8, 0, 119, 79, -87, 72, -15, -113, -82, 14, -70, 6, 0, 0, -100, 20, 0, 0, 9, 0, 36, 0, 0, 0, 0, 0, 0, 0, 32, 0, 0, 0, 0, 0, 0, 0, 109, 121, 99, 112, 117, 105, 110, MttRequest.REQUEST_DIRECT, 111, 10, 0, 32, 0, 0, 0, 0, 0, 1, 0, 24, 0, -50, 126, -10, 116, -106, -87, -47, 1, -39, 93, -11, 116, -106, -87, -47, 1, 62, 92, -107, Byte.MIN_VALUE, -33, -91, -47, 1, 80, 75, 5, 6, 0, 0, 0, 0, 1, 0, 1, 0, 91, 0, 0, 0, -31, 6, 0, 0, 0, 0});
        ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
        while (zipInputStream.getNextEntry() != null) {
            try {
                byte[] bArr = new byte[2048];
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(context.getFilesDir().getAbsolutePath() + "/mycpuinfo"));
                while (true) {
                    int read = zipInputStream.read(bArr, 0, 2048);
                    if (read == -1) {
                        break;
                    }
                    bufferedOutputStream.write(bArr, 0, read);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            } catch (Throwable th) {
            }
        }
        zipInputStream.close();
        byteArrayInputStream.close();
        b("chmod 755 " + context.getFilesDir().getAbsolutePath() + "/mycpuinfo\n");
        String b2 = b(context.getFilesDir().getAbsolutePath() + "/mycpuinfo\n");
        c = b2.trim();
        return b2.trim();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x004f A[SYNTHETIC, Splitter:B:10:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0054 A[SYNTHETIC, Splitter:B:13:0x0054] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String p() {
        /*
            r1 = 0
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x004a }
            java.lang.String r0 = "/proc/meminfo"
            r3.<init>(r0)     // Catch:{ Throwable -> 0x004a }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x005d }
            r0 = 8192(0x2000, float:1.14794E-41)
            r2.<init>(r3, r0)     // Catch:{ Throwable -> 0x005d }
            java.lang.String r0 = r2.readLine()     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r4 = ":\\s+"
            r5 = 2
            java.lang.String[] r0 = r0.split(r4, r5)     // Catch:{ Throwable -> 0x0060 }
            r4 = 1
            r0 = r0[r4]     // Catch:{ Throwable -> 0x0060 }
            java.util.Locale r4 = java.util.Locale.US     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r0 = r0.toLowerCase(r4)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r4 = "kb"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replace(r4, r5)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x0060 }
            long r4 = java.lang.Long.parseLong(r0)     // Catch:{ Throwable -> 0x0060 }
            r6 = 1024(0x400, double:5.06E-321)
            long r4 = r4 / r6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0060 }
            r0.<init>()     // Catch:{ Throwable -> 0x0060 }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0060 }
            r2.close()     // Catch:{ Throwable -> 0x0060 }
            r3.close()     // Catch:{ Throwable -> 0x0060 }
        L_0x0049:
            return r0
        L_0x004a:
            r0 = move-exception
            r0 = r1
            r3 = r1
        L_0x004d:
            if (r0 == 0) goto L_0x0052
            r0.close()     // Catch:{ Throwable -> 0x0059 }
        L_0x0052:
            if (r3 == 0) goto L_0x0057
            r3.close()     // Catch:{ Throwable -> 0x005b }
        L_0x0057:
            r0 = r1
            goto L_0x0049
        L_0x0059:
            r0 = move-exception
            goto L_0x0052
        L_0x005b:
            r0 = move-exception
            goto L_0x0057
        L_0x005d:
            r0 = move-exception
            r0 = r1
            goto L_0x004d
        L_0x0060:
            r0 = move-exception
            r0 = r2
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tp.r.p():java.lang.String");
    }

    public static String q() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return (((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "";
        } catch (Throwable th) {
            return null;
        }
    }

    public static String r() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable th) {
            return Constants.NULL_VERSION_ID;
        }
    }

    public static String s() {
        try {
            return Build.HARDWARE;
        } catch (Throwable th) {
            return "UNKNOWN";
        }
    }

    public static String t() {
        try {
            return s() + h.a + f() + h.b;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String u() {
        return "UNKNOWN";
    }
}
