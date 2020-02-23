package com.tencent.beacon.cover;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import com.tencent.component.debug.TraceFormat;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.tp.r;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipInputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: Utils */
public final class g {
    public static boolean a = false;
    static int b = 1;
    static int c = 2;

    public static boolean a(Context context, String str, String str2) {
        return context.getSharedPreferences("beacon_cover", 0).edit().putString(str, str2).commit();
    }

    public static String b(Context context, String str, String str2) {
        return context.getSharedPreferences("beacon_cover", 0).getString(str, str2);
    }

    public static void a(Context context) {
        context.getSharedPreferences("DENGTA_META", 0).edit().putInt("load_so", 0).commit();
    }

    public static String b(Context context) {
        if (h.a == null) {
            try {
                Object obj = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get("APPKEY_DENGTA");
                if (obj != null) {
                    h.a = obj.toString().trim();
                }
            } catch (Throwable th) {
            }
        }
        return h.a;
    }

    public static String c(Context context) {
        String str;
        if (h.b == null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                String str2 = packageInfo.versionName;
                int i = packageInfo.versionCode;
                if (str2 == null || str2.trim().length() <= 0) {
                    return String.valueOf(i);
                }
                String replace = str2.trim().replace(10, ' ').replace(13, ' ').replace("|", "%7C");
                char[] charArray = replace.toCharArray();
                int i2 = 0;
                for (char c2 : charArray) {
                    if (c2 == '.') {
                        i2++;
                    }
                }
                if (i2 < 3) {
                    a(TraceFormat.STR_INFO, "versionCode: %s", Integer.valueOf(i));
                    str = replace + "." + i;
                } else {
                    str = replace;
                }
                h.b = str;
            } catch (Exception e) {
            }
        }
        return h.b;
    }

    public static String a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            return "";
        }
    }

    public static String d(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            if (string != null) {
                return string.toLowerCase();
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String e(Context context) {
        String str;
        if (context == null) {
            a(TraceFormat.STR_ERROR, "getImei but context == null!", new Object[0]);
            return "";
        }
        try {
            if (c(context, r.a)) {
                String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                if (deviceId == null) {
                    str = "";
                } else {
                    str = deviceId.toLowerCase();
                }
            } else {
                str = "";
            }
            try {
                a(TraceFormat.STR_INFO, "IMEI:" + str, new Object[0]);
                return str;
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            str = "";
            a(TraceFormat.STR_ERROR, "getImei error!", new Object[0]);
            return str;
        }
    }

    private static boolean c(Context context, String str) {
        if (context == null) {
            return false;
        }
        boolean z = context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
        if (!z) {
            try {
                String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
                if (strArr != null) {
                    for (String equals : strArr) {
                        if (str.equals(equals)) {
                            return true;
                        }
                    }
                }
                return z;
            } catch (Throwable th) {
            }
        }
        return z;
    }

    public static String b() {
        try {
            return Build.CPU_ABI;
        } catch (Exception e) {
            return "";
        }
    }

    public static String f(Context context) {
        try {
            return b(h.a + "_" + d(context) + "_" + new Date().getTime() + "_" + ((int) (Math.random() * 2.147483647E9d)));
        } catch (Exception e) {
            return null;
        }
    }

    private static String b(String str) {
        String c2 = c(str);
        if (c2 == null) {
            return c2;
        }
        try {
            return c2.substring(8, 24);
        } catch (Exception e) {
            return c2;
        }
    }

    private static String c(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b2 : digest) {
                byte b3 = b2 & 255;
                if (b3 < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(b3));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            return str;
        }
    }

    public static String a(File file) {
        if (!file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    instance.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return a(instance.digest());
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toLowerCase();
    }

    public static long c() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean g(Context context) {
        NetworkInfo activeNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (!(connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || activeNetworkInfo.getType() != 1)) {
                return true;
            }
        } catch (Exception e) {
            a(APDataReportManager.ACCOUNTINPUT_PRE, e.getMessage(), new Object[0]);
        }
        return false;
    }

    public static void a(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
        }
    }

    public static void a(String str, String str2, Object... objArr) {
        if (str.equalsIgnoreCase(TraceFormat.STR_INFO)) {
            if (a) {
                Log.i("beaconCover", a(str2, objArr));
            }
        } else if (str.equalsIgnoreCase(TraceFormat.STR_DEBUG)) {
            if (a) {
                Log.d("beaconCover", a(str2, objArr));
            }
        } else if (str.equalsIgnoreCase(TraceFormat.STR_WARN)) {
            if (a) {
                Log.w("beaconCover", a(str2, objArr));
            }
        } else if (str.equalsIgnoreCase(TraceFormat.STR_ERROR) && a) {
            Log.e("beaconCover", a(str2, objArr));
        }
    }

    private static String a(String str, Object... objArr) {
        if (str == null) {
            return "[cover] null";
        }
        if (objArr == null || objArr.length == 0) {
            return "[cover] " + str;
        }
        return "[cover] " + String.format(Locale.US, str, objArr);
    }

    public static byte[] a(boolean z, String str, byte[] bArr) throws Exception {
        if (str == null || bArr == null) {
            return null;
        }
        for (int length = str.length(); length < 16; length++) {
            str = str + "0";
        }
        String substring = str.substring(0, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(substring.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(substring.getBytes());
        if (z) {
            instance.init(1, secretKeySpec, ivParameterSpec);
        } else {
            instance.init(2, secretKeySpec, ivParameterSpec);
        }
        return instance.doFinal(bArr);
    }

    public static String a(Context context, String str) {
        try {
            return Base64.encodeToString(d(context, str), 2);
        } catch (Exception e) {
            return "";
        }
    }

    private static byte[] d(Context context, String str) {
        byte[] bArr;
        KeyFactory instance;
        if (str == null) {
            return null;
        }
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsAxNCSLyNUCOP1QqYStE8ZeiU\nv4afaMqEmoLCKb0mUZYvYOoVN7LPMi2IVY2MRaFJvuND3glVw1RDm2VJJtjQkwUd\n3kpR9TrHAf7UQOVTpNo3Vi7pXTOqZ6bh3ZA/fs56jDCCKV6+wT/pCeu8N6vVnPrD\nz3SdHIeNeWb/woazCwIDAQAB", 0));
            if (context.getApplicationInfo().targetSdkVersion >= 28) {
                instance = KeyFactory.getInstance("RSA");
            } else {
                instance = KeyFactory.getInstance("RSA", "BC");
            }
            Cipher instance2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance2.init(1, (RSAPublicKey) instance.generatePublic(x509EncodedKeySpec));
            bArr = instance2.doFinal(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            bArr = null;
        }
        return bArr;
    }

    public static boolean a(String str, String str2) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
            ZipInputStream zipInputStream = new ZipInputStream(bufferedInputStream);
            while (zipInputStream.getNextEntry() != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
                while (true) {
                    int read = zipInputStream.read();
                    if (read == -1) {
                        break;
                    }
                    bufferedOutputStream.write(read);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            zipInputStream.close();
            bufferedInputStream.close();
            File file = new File(str);
            if (!file.exists() || !file.isFile()) {
                return true;
            }
            file.delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<a> a(String str) {
        ArrayList arrayList = new ArrayList();
        for (String split : str.split(";")) {
            String[] split2 = split.split(",");
            if (split2.length >= 6) {
                try {
                    a aVar = new a();
                    aVar.a = Integer.valueOf(split2[0]).intValue();
                    aVar.b = split2[1];
                    aVar.c = Integer.valueOf(split2[2]).intValue();
                    aVar.d = split2[3];
                    aVar.f = Integer.valueOf(split2[4]).intValue();
                    aVar.g = split2[5];
                    if (split2.length > 6) {
                        aVar.h = split2[6];
                    }
                    arrayList.add(aVar);
                } catch (Exception e) {
                }
            }
        }
        return arrayList;
    }

    public static String a(List<a> list) {
        StringBuilder sb = new StringBuilder();
        for (a next : list) {
            if (next != null) {
                sb.append(next.a).append(",");
                sb.append(next.b).append(",");
                sb.append(next.c).append(",");
                sb.append(next.d).append(",");
                sb.append(next.f).append(",");
                sb.append(next.g).append(",");
                sb.append(next.h).append(";");
            }
        }
        return sb.toString();
    }

    public static boolean a(Context context, String str, String str2, String str3, long j, byte[] bArr) {
        boolean z;
        String str4 = str2 + File.separator + str3;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            InputStream open = context.getResources().getAssets().open(str);
            if (((long) open.available()) == j) {
                FileOutputStream fileOutputStream = new FileOutputStream(str4);
                while (true) {
                    int read = open.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.close();
                z = true;
            } else {
                z = false;
            }
            try {
                open.close();
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            z = false;
            a(TraceFormat.STR_ERROR, "copy assets error.", new Object[0]);
            return z;
        }
        return z;
    }

    public static String b(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream open = context.getAssets().open(str);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open, "UTF-8"));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }
            bufferedReader.close();
            open.close();
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e2) {
            a(TraceFormat.STR_ERROR, "read string from assets file error.", new Object[0]);
        }
        return sb.toString();
    }

    public static boolean b(File file) {
        boolean z = true;
        if (file != null && file.exists()) {
            a(TraceFormat.STR_ERROR, "safeDeleteFile, try to delete path: " + file.getPath(), new Object[0]);
            z = file.delete();
            if (!z) {
                a(TraceFormat.STR_ERROR, "Failed to delete file, try to delete when exit. path: " + file.getPath(), new Object[0]);
                file.deleteOnExit();
            }
        }
        return z;
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }
}
