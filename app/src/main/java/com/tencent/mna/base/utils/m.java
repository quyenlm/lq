package com.tencent.mna.base.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/* compiled from: PhoneUtil */
public final class m {
    private static final String a = (Environment.getExternalStorageDirectory().toString() + File.separator + "mocmna");
    private static String b = "";
    private static String c = "";

    public static String a() {
        return Build.MODEL;
    }

    public static String b() {
        return Build.VERSION.RELEASE;
    }

    public static int c() {
        return Build.VERSION.SDK_INT;
    }

    public static int a(Context context) {
        if (context == null) {
            h.a("battery context null");
            return -1;
        }
        try {
            Intent registerReceiver = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (registerReceiver != null) {
                return registerReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1);
            }
            return -1;
        } catch (Exception e) {
            h.a("battery exception:" + e.getMessage());
            return -1;
        }
    }

    public static int[] b(Context context) {
        int[] iArr = {-1, 0};
        if (context == null) {
            h.d("battery context null");
        } else {
            try {
                Intent registerReceiver = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                if (registerReceiver != null) {
                    iArr[0] = registerReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1);
                    int intExtra = registerReceiver.getIntExtra("status", 1);
                    if (intExtra == 2 || intExtra == 5) {
                        iArr[1] = 1;
                    }
                }
            } catch (Exception e) {
                h.a("battery " + e.getMessage());
            }
        }
        return iArr;
    }

    public static String c(Context context) {
        String str;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return "";
            }
            str = a(MessageDigest.getInstance("SHA1").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(packageManager.getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray()))).getEncoded()));
            return str;
        } catch (Exception e) {
            str = "";
        }
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            String hexString = Integer.toHexString(bArr[i]);
            int length = hexString.length();
            if (length == 1) {
                hexString = "0" + hexString;
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            sb.append(hexString.toUpperCase());
            if (i < bArr.length - 1) {
                sb.append(':');
            }
        }
        return sb.toString();
    }

    @SuppressLint({"HardwareIds"})
    public static String a(Context context, boolean z) {
        String string;
        if (z) {
            try {
                String e = e();
                if (e != null && e.length() > 0) {
                    return e;
                }
            } catch (Exception e2) {
                h.a("getDeviceId error:" + e2.getMessage());
            }
        }
        if (!(context == null || (string = Settings.Secure.getString(context.getContentResolver(), "android_id")) == null || string.length() <= 0)) {
            return string + "_0";
        }
        return "0";
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0072 A[SYNTHETIC, Splitter:B:27:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007c A[SYNTHETIC, Splitter:B:33:0x007c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String e() {
        /*
            java.lang.String r0 = c
            if (r0 == 0) goto L_0x000f
            java.lang.String r0 = c
            int r0 = r0.length()
            if (r0 <= 0) goto L_0x000f
            java.lang.String r0 = c
        L_0x000e:
            return r0
        L_0x000f:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = a
            java.lang.String r2 = "dump"
            r0.<init>(r1, r2)
            long r2 = r0.length()
            int r1 = (int) r2
            byte[] r3 = new byte[r1]
            r2 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0058, all -> 0x0078 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x0058, all -> 0x0078 }
            int r0 = r1.read(r3)     // Catch:{ Exception -> 0x0086 }
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x0086 }
            r4 = 0
            java.lang.String r5 = "UTF-8"
            r2.<init>(r3, r4, r0, r5)     // Catch:{ Exception -> 0x0086 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0086 }
            r0.<init>(r2)     // Catch:{ Exception -> 0x0086 }
            java.lang.String r2 = "devid"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.optString(r2, r3)     // Catch:{ Exception -> 0x0086 }
            if (r0 == 0) goto L_0x0050
            int r2 = r0.length()     // Catch:{ Exception -> 0x0086 }
            if (r2 <= 0) goto L_0x0050
            c = r0     // Catch:{ Exception -> 0x0086 }
            if (r1 == 0) goto L_0x000e
            r1.close()     // Catch:{ Exception -> 0x004e }
            goto L_0x000e
        L_0x004e:
            r1 = move-exception
            goto L_0x000e
        L_0x0050:
            if (r1 == 0) goto L_0x0055
            r1.close()     // Catch:{ Exception -> 0x0080 }
        L_0x0055:
            java.lang.String r0 = ""
            goto L_0x000e
        L_0x0058:
            r0 = move-exception
            r1 = r2
        L_0x005a:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r2.<init>()     // Catch:{ all -> 0x0084 }
            java.lang.String r3 = "getDeviceIdBySdcard error:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0084 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0084 }
            com.tencent.mna.base.utils.h.a((java.lang.String) r0)     // Catch:{ all -> 0x0084 }
            if (r1 == 0) goto L_0x0055
            r1.close()     // Catch:{ Exception -> 0x0076 }
            goto L_0x0055
        L_0x0076:
            r0 = move-exception
            goto L_0x0055
        L_0x0078:
            r0 = move-exception
            r1 = r2
        L_0x007a:
            if (r1 == 0) goto L_0x007f
            r1.close()     // Catch:{ Exception -> 0x0082 }
        L_0x007f:
            throw r0
        L_0x0080:
            r0 = move-exception
            goto L_0x0055
        L_0x0082:
            r1 = move-exception
            goto L_0x007f
        L_0x0084:
            r0 = move-exception
            goto L_0x007a
        L_0x0086:
            r0 = move-exception
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.m.e():java.lang.String");
    }

    public static String d(Context context) {
        if (context == null) {
            return "nopackage";
        }
        return context.getPackageName();
    }

    /* compiled from: PhoneUtil */
    public enum a {
        ARMEABI("armeabi"),
        ARMEABI_V7A("armeabi-v7a"),
        ARM64_V8A("arm64-v8a"),
        X86("x86"),
        X86_64("x86_64"),
        MIPS("mips"),
        MIPS64("mips64"),
        UNKNOWN("unknown");
        
        private String abiName;

        private a(String str) {
            this.abiName = "unknown";
            this.abiName = str;
        }

        public String getAbiName() {
            return this.abiName;
        }

        public boolean isLP64() {
            return equals(ARM64_V8A) || equals(X86_64) || equals(MIPS64);
        }

        public static a getAbi(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            for (a aVar : values()) {
                if (aVar.getAbiName().equals(str.toLowerCase())) {
                    return aVar;
                }
            }
            return UNKNOWN;
        }
    }

    public static a d() {
        return a.getAbi(Build.CPU_ABI);
    }
}
