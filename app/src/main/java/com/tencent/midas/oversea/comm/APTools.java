package com.tencent.midas.oversea.comm;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.tp.r;
import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import org.apache.http.ConnectionClosedException;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class APTools {
    public static final int OPERATOR_MOBILE = 0;
    public static final int OPERATOR_TELECOM = 1;
    public static final int OPERATOR_UNICOM = 2;
    public static final int OPERATOR_UNKOWN = -1;
    private static final String a = APTools.class.getSimpleName();
    private static HashMap<String, Object> b;
    private static int c = -1;
    private static int d = -1;
    private static long e = 0;
    private static String f;
    private static ProgressDialog g;
    public static String mBasekey = "SfjUddAdIdxnd4v4";
    public static String mKey = "";
    public static long s_screenHeight = 0;
    public static long s_screenWidth = 0;

    public static String GetActiveNetWorkName() {
        NetworkInfo activeNetworkInfo;
        APMidasPayAPI.singleton();
        ConnectivityManager connectivityManager = (ConnectivityManager) APMidasPayAPI.applicationContext.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return "";
        }
        String typeName = activeNetworkInfo.getTypeName();
        String subtypeName = activeNetworkInfo.getSubtypeName();
        return (subtypeName == null || subtypeName.length() == 0) ? typeName : subtypeName;
    }

    public static float GetDensity(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (activity == null) {
            return 0.0f;
        }
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    public static Node GetFirstElementByTagName(Element element, String str) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeName().equalsIgnoreCase(str)) {
                return item;
            }
        }
        return null;
    }

    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap, float f2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f2, f2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public static long GetScreenHeight(Activity activity) {
        if (s_screenHeight == 0) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (activity != null) {
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                s_screenHeight = (long) displayMetrics.heightPixels;
            } else {
                s_screenHeight = 0;
            }
        }
        return s_screenHeight;
    }

    public static long GetScreenWidth(Activity activity) {
        if (s_screenWidth == 0) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (activity != null) {
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                s_screenWidth = (long) displayMetrics.widthPixels;
            } else {
                s_screenHeight = 0;
            }
        }
        return s_screenWidth;
    }

    public static boolean IsNetworkAvailable() {
        NetworkInfo[] allNetworkInfo;
        APMidasPayAPI.singleton();
        ConnectivityManager connectivityManager = (ConnectivityManager) APMidasPayAPI.applicationContext.getSystemService("connectivity");
        if (connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null) {
            return false;
        }
        for (NetworkInfo state : allNetworkInfo) {
            if (state.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    private static String a(Signature signature) {
        try {
            try {
                try {
                    return byte2HexFormatted(MessageDigest.getInstance("MD5").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(signature.toByteArray()))).getEncoded()));
                } catch (NoSuchAlgorithmException e2) {
                    e2.printStackTrace();
                    return null;
                } catch (CertificateEncodingException e3) {
                    e3.printStackTrace();
                    return null;
                }
            } catch (CertificateException e4) {
                e4.printStackTrace();
                return "";
            }
        } catch (CertificateException e5) {
            e5.printStackTrace();
            return "";
        }
    }

    private static String a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[(length * 2)];
        for (int i = 0; i < length; i++) {
            byte b2 = bArr[i];
            int i2 = (b2 >> 4) & 15;
            cArr[i * 2] = (char) (i2 >= 10 ? (i2 + 97) - 10 : i2 + 48);
            byte b3 = b2 & 15;
            cArr[(i * 2) + 1] = (char) (b3 >= 10 ? (b3 + 97) - 10 : b3 + 48);
        }
        return new String(cArr);
    }

    private static boolean a(Context context) {
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 0:
                return false;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return true;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return false;
            case 8:
                return true;
            case 9:
                return true;
            case 10:
                return true;
            default:
                return false;
        }
    }

    private static Certificate[] a(JarFile jarFile, JarEntry jarEntry, byte[] bArr) {
        try {
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            do {
            } while (inputStream.read(bArr, 0, bArr.length) != -1);
            inputStream.close();
            if (jarEntry != null) {
                return jarEntry.getCertificates();
            }
            return null;
        } catch (IOException e2) {
            return null;
        }
    }

    public static String array2String(String[] strArr, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (strArr.length > 0) {
            for (int i = 0; i < strArr.length; i++) {
                stringBuffer.append(strArr[i]);
                if (i < strArr.length - 1) {
                    stringBuffer.append(str);
                }
            }
        }
        return stringBuffer.toString();
    }

    private static String b(Context context) {
        if (context == null) {
            return null;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null) {
            return null;
        }
        try {
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo == null) {
                return null;
            }
            return connectionInfo.getMacAddress();
        } catch (Exception e2) {
            APLog.w("APTools getLocalMacAddressFromWifiInfo", e2.toString());
            return "";
        }
    }

    public static String byte2HexFormatted(byte[] bArr) {
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

    public static String byteArrayToHex(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] cArr2 = new char[(bArr.length * 2)];
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            byte b2 = bArr[i];
            int i3 = i2 + 1;
            cArr2[i2] = cArr[(b2 >>> 4) & 15];
            cArr2[i3] = cArr[b2 & 15];
            i++;
            i2 = i3 + 1;
        }
        return new String(cArr2);
    }

    private static void c(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null) {
            try {
                String subscriberId = telephonyManager.getPhoneType() == 1 ? telephonyManager.getSubscriberId() : telephonyManager.getPhoneType() != 0 ? telephonyManager.getSimOperator() : null;
                if (subscriberId != null && subscriberId.length() >= 3) {
                    d = Integer.parseInt(subscriberId.substring(0, 3));
                }
                if (subscriberId != null && subscriberId.length() >= 5) {
                    c = Integer.parseInt(subscriberId.substring(3, 5));
                }
            } catch (Exception e2) {
            }
        }
    }

    public static String checkActivity(Context context) {
        String checkMidasActivity = checkMidasActivity(context);
        String checkWSJActivity = checkWSJActivity(context);
        if (!TextUtils.isEmpty(checkMidasActivity) && !TextUtils.isEmpty(checkWSJActivity)) {
            if (!TextUtils.isEmpty(checkMidasActivity)) {
                return checkMidasActivity;
            }
            if (!TextUtils.isEmpty(checkWSJActivity)) {
                return checkWSJActivity;
            }
        }
        return null;
    }

    public static boolean checkApkIsExist(Context context, String str) {
        if (str == null) {
            return false;
        }
        try {
            return new File(str).exists();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String checkMidasActivity(Context context) {
        HashMap hashMap = new HashMap();
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.name = "com.tencent.midas.proxyactivity.APMidasPayProxyActivity";
        activityInfo.exported = false;
        hashMap.put(activityInfo, false);
        ActivityInfo activityInfo2 = new ActivityInfo();
        activityInfo2.name = "com.tencent.midas.wx.APMidasWXPayActivity";
        activityInfo2.exported = true;
        hashMap.put(activityInfo2, false);
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            ActivityInfo[] activityInfoArr = packageManager.getPackageInfo(packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0).packageName, 1).activities;
            for (Map.Entry entry : hashMap.entrySet()) {
                ActivityInfo activityInfo3 = (ActivityInfo) entry.getKey();
                int i = 0;
                while (true) {
                    if (i >= activityInfoArr.length) {
                        break;
                    }
                    ActivityInfo activityInfo4 = activityInfoArr[i];
                    String str = activityInfo4.name;
                    APLog.i("checkActivity", "activityname:" + str);
                    APLog.i("checkActivity", "screenOrientation:" + activityInfo4.screenOrientation);
                    if ((!str.equals("com.tencent.midas.proxyactivity.APMidasPayProxyActivity") || activityInfo4.screenOrientation == 0 || activityInfo4.screenOrientation == 1) && (!str.equals("com.tencent.midas.proxyactivity.APMidasPayProxyActivity") || activityInfo4.configChanges == 1184 || activityInfo4.configChanges == 160)) {
                        if (activityInfo3.name.equals(str) && activityInfo3.exported == activityInfo4.exported) {
                            entry.setValue(true);
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e2) {
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry entry2 : hashMap.entrySet()) {
            ActivityInfo activityInfo5 = (ActivityInfo) entry2.getKey();
            if (!((Boolean) entry2.getValue()).booleanValue()) {
                stringBuffer.append(activityInfo5.name);
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }

    public static String checkPermission(Context context) {
        HashMap hashMap = new HashMap();
        hashMap.put("android.permission.ACCESS_NETWORK_STATE", false);
        hashMap.put("android.permission.WRITE_EXTERNAL_STORAGE", false);
        hashMap.put(r.a, false);
        hashMap.put("android.permission.ACCESS_WIFI_STATE", false);
        hashMap.put("android.permission.RESTART_PACKAGES", false);
        hashMap.put("android.permission.GET_TASKS", false);
        hashMap.put("android.permission.READ_SMS", false);
        hashMap.put("android.permission.SEND_SMS", false);
        hashMap.put("android.permission.MOUNT_UNMOUNT_FILESYSTEMS", false);
        hashMap.put("android.permission.WRITE_SETTINGS", false);
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            String[] strArr = packageManager.getPackageInfo(packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0).packageName, 4096).requestedPermissions;
            for (Map.Entry entry : hashMap.entrySet()) {
                String str = (String) entry.getKey();
                int i = 0;
                while (true) {
                    if (i >= strArr.length) {
                        break;
                    } else if (strArr[i].equals(str)) {
                        entry.setValue(true);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        } catch (Exception e2) {
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry entry2 : hashMap.entrySet()) {
            String str2 = (String) entry2.getKey();
            if (!((Boolean) entry2.getValue()).booleanValue()) {
                stringBuffer.append(str2);
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }

    public static String checkWSJActivity(Context context) {
        HashMap hashMap = new HashMap();
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.name = "com.wsj.pay.proxyactivity.APWSJPayProxyActivity";
        activityInfo.exported = false;
        hashMap.put(activityInfo, false);
        ActivityInfo activityInfo2 = new ActivityInfo();
        activityInfo2.name = "com.wsj.pay.wx.APWSJWXPayActivity";
        activityInfo2.exported = true;
        hashMap.put(activityInfo2, false);
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            ActivityInfo[] activityInfoArr = packageManager.getPackageInfo(packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0).packageName, 1).activities;
            for (Map.Entry entry : hashMap.entrySet()) {
                ActivityInfo activityInfo3 = (ActivityInfo) entry.getKey();
                int i = 0;
                while (true) {
                    if (i >= activityInfoArr.length) {
                        break;
                    }
                    ActivityInfo activityInfo4 = activityInfoArr[i];
                    String str = activityInfo4.name;
                    APLog.i("checkActivity", "activityname:" + str);
                    APLog.i("checkActivity", "screenOrientation:" + activityInfo4.screenOrientation);
                    if ((!str.equals("com.wsj.pay.proxyactivity.APWSJPayProxyActivity") || activityInfo4.screenOrientation == 0 || activityInfo4.screenOrientation == 1) && (!str.equals("com.wsj.pay.proxyactivity.APWSJPayProxyActivity") || activityInfo4.configChanges == 1184 || activityInfo4.configChanges == 160)) {
                        if (activityInfo3.name.equals(str) && activityInfo3.exported == activityInfo4.exported) {
                            entry.setValue(true);
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e2) {
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry entry2 : hashMap.entrySet()) {
            ActivityInfo activityInfo5 = (ActivityInfo) entry2.getKey();
            if (!((Boolean) entry2.getValue()).booleanValue()) {
                stringBuffer.append(activityInfo5.name);
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }

    public static void closeProgressDialog() {
        if (g != null) {
            g.dismiss();
        }
    }

    public static String collectDeviceInfo(Activity activity) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("imei=" + getDeviceId(activity));
        String b2 = b(activity);
        if (b2 == null) {
            b2 = "";
        }
        stringBuffer.append("&mac=" + b2);
        String str = Build.DEVICE;
        if (str == null) {
            str = "";
        }
        stringBuffer.append("&device=" + str);
        stringBuffer.append("&manufacturer=" + Build.MANUFACTURER);
        stringBuffer.append("&sdkVersion=" + Build.VERSION.RELEASE);
        stringBuffer.append("&showModel=" + Build.MODEL);
        stringBuffer.append("&phoneIp=" + getLocalIp());
        try {
            int heightPixels = getHeightPixels(activity);
            stringBuffer.append("&Pixel=" + heightPixels + "x" + getWeightPixels(activity));
        } catch (Exception e2) {
        }
        String availMemory2 = getAvailMemory2(activity);
        if (availMemory2 == null) {
            availMemory2 = "";
        }
        stringBuffer.append("&availableMemory=" + availMemory2);
        String totalMemory = getTotalMemory(activity);
        if (totalMemory == null) {
            totalMemory = "";
        }
        stringBuffer.append("&totalMemory=" + totalMemory);
        String[] cpuInfo = getCpuInfo();
        if (cpuInfo.length == 2) {
            stringBuffer.append("&cup=" + cpuInfo[0]);
            stringBuffer.append("&cupFrequency=" + cpuInfo[1]);
        }
        return stringBuffer.toString();
    }

    public static Bitmap createRepeater(int i, Bitmap bitmap) {
        int width = ((bitmap.getWidth() + i) - 1) / bitmap.getWidth();
        Bitmap createBitmap = Bitmap.createBitmap(i, bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        for (int i2 = 0; i2 < width; i2++) {
            canvas.drawBitmap(bitmap, (float) (bitmap.getWidth() * i2), 0.0f, (Paint) null);
        }
        return createBitmap;
    }

    private static String d(Context context) {
        int i = 0;
        File dir = context.getDir("midasplugins", 0);
        if (dir != null) {
            File[] listFiles = dir.listFiles();
            while (i < listFiles.length) {
                File file = listFiles[i];
                if (file.getName().startsWith("MidasPay")) {
                    try {
                        return file.getCanonicalPath();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return "";
                    }
                } else {
                    i++;
                }
            }
        }
        return "";
    }

    private static Signature[] e(Context context) {
        if (isApkPlugin()) {
            return f(context);
        }
        String d2 = d(context);
        if (!TextUtils.isEmpty(d2)) {
            return getSignaturesFromApk(d2);
        }
        return null;
    }

    public static void extractLibs(String str, String str2) {
        boolean z;
        String str3;
        if (!str2.endsWith(File.separator)) {
            str2 = str2 + File.separator;
        }
        String str4 = null;
        try {
            Enumeration<? extends ZipEntry> entries = new ZipFile(str).entries();
            while (true) {
                if (!entries.hasMoreElements()) {
                    str3 = null;
                    break;
                }
                String name = ((ZipEntry) entries.nextElement()).getName();
                if (!name.endsWith(File.separator) && (name.contains("lib") || name.endsWith(".so"))) {
                    File file = new File(str2 + name);
                    if (file != null) {
                        str3 = file.getParentFile().getName();
                        if (!str3.equals(Build.CPU_ABI)) {
                            if (0 == 0) {
                                if (!str3.equals("armeabi")) {
                                    if (str3.equals("armeabi-v7a")) {
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                            if (Build.CPU_ABI.equals("x86")) {
                                if (0 == 0) {
                                    if (!str3.equals("armeabi")) {
                                        if (str3.equals("armeabi-v7a")) {
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                            if (Build.CPU_ABI.equals("mips")) {
                                if (0 == 0) {
                                    if (!str3.equals("armeabi")) {
                                        if (str3.equals("armeabi-v7a")) {
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        } else {
                            str3 = Build.CPU_ABI;
                            break;
                        }
                    }
                    if (0 != 0) {
                        str3 = null;
                        break;
                    }
                }
            }
            str4 = str3;
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        APLog.i("APPluginUtils", "extractLibs end to dirToExtract:" + str4);
        try {
            ZipFile zipFile = new ZipFile(str);
            byte[] bArr = new byte[4096];
            Enumeration<? extends ZipEntry> entries2 = zipFile.entries();
            while (entries2.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries2.nextElement();
                String name2 = zipEntry.getName();
                if (!name2.endsWith(File.separator) && (name2.contains("lib") || name2.endsWith(".so"))) {
                    File file2 = new File(str2 + name2);
                    int lastIndexOf = name2.lastIndexOf(File.separator);
                    if (lastIndexOf != -1) {
                        name2 = name2.substring(lastIndexOf + 1);
                    }
                    File file3 = new File(str2 + name2);
                    while (true) {
                        if (file2 == null) {
                            z = false;
                            break;
                        } else if (file2.getName().equals(str4)) {
                            z = true;
                            break;
                        } else {
                            file2 = file2.getParentFile();
                        }
                    }
                    if (z) {
                        file3.getParentFile().mkdirs();
                        try {
                            InputStream inputStream = zipFile.getInputStream(zipEntry);
                            FileOutputStream fileOutputStream = new FileOutputStream(file3);
                            while (true) {
                                int read = inputStream.read(bArr);
                                if (read <= 0) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    private static Signature[] f(Context context) {
        for (PackageInfo next : context.getPackageManager().getInstalledPackages(64)) {
            if (next.packageName.equals(context.getPackageName())) {
                return next.signatures;
            }
        }
        return null;
    }

    public static String formatFloat(double d2, int i) {
        String str = "";
        try {
            String valueOf = String.valueOf(d2);
            if (valueOf.contains(".")) {
                String[] split = valueOf.split("\\.");
                valueOf = split[0];
                if (split.length > 1) {
                    str = split[1];
                }
                if (str.length() == 0) {
                    if (i == 0) {
                        str = "";
                    } else if (i == 1) {
                        str = "0";
                    } else if (i == 2) {
                        str = "00";
                    }
                } else if (str.length() == 1) {
                    if (i == 2) {
                        str = str + "0";
                    }
                } else if (str.length() != 2) {
                    str = str.substring(0, 1);
                }
            } else if (i == 0) {
                str = "";
            } else if (i == 1) {
                str = "0";
            } else if (i == 2) {
                str = "00";
            }
            return !str.equals("") ? valueOf + "." + str : valueOf;
        } catch (Exception e2) {
            APLog.i("formatFloat error", e2.toString());
            return "";
        }
    }

    public static String formatFloat(float f2) {
        String str;
        String str2;
        try {
            String valueOf = String.valueOf(f2);
            if (valueOf.contains(".")) {
                str2 = valueOf.split("\\.")[0];
                str = valueOf.split("\\.")[1];
                if (str.length() == 0) {
                    str = "00";
                } else if (str.length() == 1) {
                    str = str + "0";
                } else if (str.length() != 2) {
                    str = str.substring(0, 1);
                }
            } else {
                str = "00";
                str2 = valueOf;
            }
            return str2 + "." + str;
        } catch (Exception e2) {
            APLog.i("formatFloat error", e2.toString());
            return "";
        }
    }

    public static String getAppPackageName() {
        return f;
    }

    public static String getAppVersionName() {
        try {
            APMidasPayAPI.singleton();
            Context context = APMidasPayAPI.applicationContext;
            String str = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
            return (str == null || str.length() <= 0) ? "" : str;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static long getAvailMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
    }

    public static String getAvailMemory2(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null) {
            return null;
        }
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return Formatter.formatFileSize(context, memoryInfo.availMem);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00e0 A[SYNTHETIC, Splitter:B:31:0x00e0] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00e5 A[Catch:{ Exception -> 0x00e9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x010a A[SYNTHETIC, Splitter:B:41:0x010a] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x010f A[Catch:{ Exception -> 0x0113 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String[] getCpuInfo() {
        /*
            r1 = 0
            r3 = 2
            r9 = 1
            r8 = 0
            java.lang.String r2 = "/proc/cpuinfo"
            java.lang.String r0 = ""
            java.lang.String[] r4 = new java.lang.String[r3]
            java.lang.String r0 = ""
            r4[r8] = r0
            java.lang.String r0 = ""
            r4[r9] = r0
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ IOException -> 0x011f, all -> 0x0115 }
            r0.<init>(r2)     // Catch:{ IOException -> 0x011f, all -> 0x0115 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0123, all -> 0x011a }
            r5 = 8192(0x2000, float:1.14794E-41)
            r2.<init>(r0, r5)     // Catch:{ IOException -> 0x0123, all -> 0x011a }
            java.lang.String r1 = r2.readLine()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            if (r1 == 0) goto L_0x0050
            java.lang.String r5 = "\\s+"
            java.lang.String[] r5 = r1.split(r5)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r1 = r3
        L_0x002b:
            int r3 = r5.length     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            if (r1 >= r3) goto L_0x0068
            r3 = 0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r6.<init>()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r7 = 0
            r7 = r4[r7]     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r7 = r5[r1]     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r7 = " "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r4[r3] = r6     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            int r1 = r1 + 1
            goto L_0x002b
        L_0x0050:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r1.<init>()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r3 = 0
            r5 = r4[r3]     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r5 = ""
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r4[r3] = r1     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
        L_0x0068:
            java.lang.String r1 = r2.readLine()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            if (r1 == 0) goto L_0x00eb
            java.lang.String r3 = "\\s+"
            java.lang.String[] r1 = r1.split(r3)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            if (r1 == 0) goto L_0x00c4
            int r3 = r1.length     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r5 = 3
            if (r3 < r5) goto L_0x00c4
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r3.<init>()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r5 = 1
            r6 = r4[r5]     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.StringBuilder r3 = r3.append(r6)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r6 = 2
            r1 = r1[r6]     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r4[r5] = r1     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
        L_0x0093:
            if (r2 == 0) goto L_0x0098
            r2.close()     // Catch:{ Exception -> 0x0126 }
        L_0x0098:
            if (r0 == 0) goto L_0x009d
            r0.close()     // Catch:{ Exception -> 0x0126 }
        L_0x009d:
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "cpuinfo="
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r4[r8]
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = " cupinfo2="
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r4[r9]
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            return r4
        L_0x00c4:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r1.<init>()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r3 = 1
            r5 = r4[r3]     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r5 = ""
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r4[r3] = r1     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            goto L_0x0093
        L_0x00dd:
            r1 = move-exception
        L_0x00de:
            if (r2 == 0) goto L_0x00e3
            r2.close()     // Catch:{ Exception -> 0x00e9 }
        L_0x00e3:
            if (r0 == 0) goto L_0x009d
            r0.close()     // Catch:{ Exception -> 0x00e9 }
            goto L_0x009d
        L_0x00e9:
            r0 = move-exception
            goto L_0x009d
        L_0x00eb:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r1.<init>()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r3 = 1
            r5 = r4[r3]     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r5 = ""
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            r4[r3] = r1     // Catch:{ IOException -> 0x00dd, all -> 0x0104 }
            goto L_0x0093
        L_0x0104:
            r1 = move-exception
            r3 = r1
            r4 = r0
            r5 = r2
        L_0x0108:
            if (r5 == 0) goto L_0x010d
            r5.close()     // Catch:{ Exception -> 0x0113 }
        L_0x010d:
            if (r4 == 0) goto L_0x0112
            r4.close()     // Catch:{ Exception -> 0x0113 }
        L_0x0112:
            throw r3
        L_0x0113:
            r0 = move-exception
            goto L_0x0112
        L_0x0115:
            r0 = move-exception
            r3 = r0
            r4 = r1
            r5 = r1
            goto L_0x0108
        L_0x011a:
            r2 = move-exception
            r3 = r2
            r4 = r0
            r5 = r1
            goto L_0x0108
        L_0x011f:
            r0 = move-exception
            r0 = r1
            r2 = r1
            goto L_0x00de
        L_0x0123:
            r2 = move-exception
            r2 = r1
            goto L_0x00de
        L_0x0126:
            r0 = move-exception
            goto L_0x009d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.comm.APTools.getCpuInfo():java.lang.String[]");
    }

    public static String getDeviceId(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return (telephonyManager == null || TextUtils.isEmpty(telephonyManager.getDeviceId())) ? "" : telephonyManager.getDeviceId();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String getEncKey() {
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            sb.append(charArray[random.nextInt(charArray.length)]);
        }
        return sb.toString();
    }

    public static int getErrorCodeFromException(IOException iOException) {
        if (iOException instanceof CharConversionException) {
            return -20;
        }
        if (iOException instanceof MalformedInputException) {
            return -21;
        }
        if (iOException instanceof UnmappableCharacterException) {
            return -22;
        }
        if (iOException instanceof HttpResponseException) {
            return -23;
        }
        if (iOException instanceof ClosedChannelException) {
            return -24;
        }
        if (iOException instanceof ConnectionClosedException) {
            return -25;
        }
        if (iOException instanceof EOFException) {
            return -26;
        }
        if (iOException instanceof FileLockInterruptionException) {
            return -27;
        }
        if (iOException instanceof FileNotFoundException) {
            return -28;
        }
        if (iOException instanceof HttpRetryException) {
            return -29;
        }
        if (iOException instanceof ConnectTimeoutException) {
            return -7;
        }
        if (iOException instanceof SocketTimeoutException) {
            return -8;
        }
        if (iOException instanceof InvalidPropertiesFormatException) {
            return -30;
        }
        if (iOException instanceof MalformedChunkCodingException) {
            return -31;
        }
        if (iOException instanceof MalformedURLException) {
            return -3;
        }
        if (iOException instanceof NoHttpResponseException) {
            return -32;
        }
        if (iOException instanceof InvalidClassException) {
            return -33;
        }
        if (iOException instanceof InvalidObjectException) {
            return -34;
        }
        if (iOException instanceof NotActiveException) {
            return -35;
        }
        if (iOException instanceof NotSerializableException) {
            return -36;
        }
        if (iOException instanceof OptionalDataException) {
            return -37;
        }
        if (iOException instanceof StreamCorruptedException) {
            return -38;
        }
        if (iOException instanceof WriteAbortedException) {
            return -39;
        }
        if (iOException instanceof ProtocolException) {
            return -40;
        }
        if (iOException instanceof SSLHandshakeException) {
            return -41;
        }
        if (iOException instanceof SSLKeyException) {
            return -42;
        }
        if (iOException instanceof SSLPeerUnverifiedException) {
            return -43;
        }
        if (iOException instanceof SSLProtocolException) {
            return -44;
        }
        if (iOException instanceof BindException) {
            return -45;
        }
        if (iOException instanceof ConnectException) {
            return -46;
        }
        if (iOException instanceof NoRouteToHostException) {
            return -47;
        }
        if (iOException instanceof PortUnreachableException) {
            return -48;
        }
        if (iOException instanceof SyncFailedException) {
            return -49;
        }
        if (iOException instanceof UTFDataFormatException) {
            return -50;
        }
        if (iOException instanceof UnknownHostException) {
            return -51;
        }
        if (iOException instanceof UnknownServiceException) {
            return -52;
        }
        if (iOException instanceof UnsupportedEncodingException) {
            return -53;
        }
        return iOException instanceof ZipException ? -54 : -2;
    }

    public static int getHeightPixels(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static String getInstalledVersionName(Context context, String str) {
        int i = 0;
        if (str == null) {
            return "";
        }
        List<PackageInfo> installedPackages = context.getApplicationContext().getPackageManager().getInstalledPackages(0);
        while (true) {
            int i2 = i;
            if (i2 >= installedPackages.size()) {
                return "";
            }
            PackageInfo packageInfo = installedPackages.get(i2);
            if (str.equals(packageInfo.applicationInfo.packageName)) {
                return packageInfo.versionName;
            }
            i = i2 + 1;
        }
    }

    public static String getLocalIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && !nextElement.isLinkLocalAddress()) {
                            return nextElement.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (SocketException e2) {
            Log.e("WifiPreference IpAddress", e2.toString());
        }
        return null;
    }

    public static String getLocalMacAddress() {
        APMidasPayAPI.singleton();
        return ((WifiManager) APMidasPayAPI.applicationContext.getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }

    public static int getMainActivityScreenType(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
            String str = queryIntentActivities.size() == 1 ? queryIntentActivities.get(0).activityInfo.name : "";
            APLog.i("getMainActivityScreenType", "main name:" + str);
            if (TextUtils.isEmpty(str)) {
                return -1;
            }
            ActivityInfo[] activityInfoArr = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 1).activities;
            for (ActivityInfo activityInfo : activityInfoArr) {
                String str2 = activityInfo.name;
                APLog.i("getMainActivityScreenType", "activityname:" + str2);
                if (str2.equals(str)) {
                    return activityInfo.screenOrientation;
                }
            }
            return -1;
        } catch (Exception e2) {
        }
    }

    public static int getNetWorkType(Context context) {
        int i;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                i = 0;
                APLog.i("networktype", String.valueOf(i));
                return i;
            }
            String typeName = activeNetworkInfo.getTypeName();
            i = typeName.equalsIgnoreCase("WIFI") ? 4 : typeName.equalsIgnoreCase("MOBILE") ? !TextUtils.isEmpty(Proxy.getDefaultHost()) ? 1 : a(context) ? 3 : 2 : -1;
            APLog.i("networktype", String.valueOf(i));
            return i;
        } catch (Exception e2) {
            i = -1;
        }
    }

    public static String getOperator(Context context) {
        c(context);
        if (d != 460) {
            return "";
        }
        switch (c) {
            case 0:
            case 2:
            case 7:
                return "gmcc";
            case 1:
            case 6:
                return "unicom";
            case 3:
            case 5:
                return "vnet";
            default:
                return "";
        }
    }

    public static boolean getRememberPwd(Context context) {
        return context.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0).getBoolean("REMEMBERPWD", true);
    }

    public static double getScreenInch(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        float f2 = displayMetrics.xdpi;
        float f3 = displayMetrics.ydpi;
        if (!isXYDpiAvailable(f2, f3)) {
            f2 = (float) displayMetrics.densityDpi;
            f3 = (float) displayMetrics.densityDpi;
        }
        float f4 = ((float) i) / f2;
        float f5 = ((float) i2) / f3;
        return Math.sqrt((double) ((f5 * f5) + (f4 * f4)));
    }

    public static String getSign() {
        APMidasPayAPI.singleton();
        Signature[] e2 = e(APMidasPayAPI.applicationContext);
        return (e2 == null || e2.length == 0) ? "" : a(e2[0]);
    }

    public static Signature[] getSignaturesFromApk(String str) {
        Signature[] signatureArr = null;
        File file = new File(str);
        if (file.exists()) {
            try {
                JarFile jarFile = new JarFile(file);
                Certificate[] a2 = a(jarFile, jarFile.getJarEntry("AndroidManifest.xml"), new byte[8192]);
                if (a2 != null) {
                    signatureArr = new Signature[a2.length];
                    int length = a2.length;
                    int i = 0;
                    int i2 = 0;
                    while (i < length) {
                        signatureArr[i2] = new Signature(a(a2[i].getEncoded()));
                        i++;
                        i2++;
                    }
                }
            } catch (Exception e2) {
            }
        }
        return signatureArr;
    }

    public static long getTimeDiff(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime();
        } catch (ParseException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0064 A[SYNTHETIC, Splitter:B:26:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0069 A[Catch:{ Exception -> 0x006d }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0074 A[SYNTHETIC, Splitter:B:34:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0079 A[Catch:{ Exception -> 0x007d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getTotalMemory(android.content.Context r10) {
        /*
            r3 = 0
            java.lang.String r4 = "/proc/meminfo"
            r0 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ IOException -> 0x005f, all -> 0x006f }
            r2.<init>(r4)     // Catch:{ IOException -> 0x005f, all -> 0x006f }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0084, all -> 0x007f }
            r5 = 8192(0x2000, float:1.14794E-41)
            r4.<init>(r2, r5)     // Catch:{ IOException -> 0x0084, all -> 0x007f }
            java.lang.String r5 = r4.readLine()     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            if (r5 == 0) goto L_0x0050
            java.lang.String r3 = "\\s+"
            java.lang.String[] r6 = r5.split(r3)     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            int r7 = r6.length     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            r3 = 0
        L_0x001f:
            if (r3 >= r7) goto L_0x003c
            r8 = r6[r3]     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            r9.<init>()     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            java.lang.StringBuilder r8 = r9.append(r8)     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            java.lang.String r9 = "\t"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            android.util.Log.i(r5, r8)     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            int r3 = r3 + 1
            goto L_0x001f
        L_0x003c:
            if (r6 == 0) goto L_0x0050
            int r3 = r6.length     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            r5 = 2
            if (r3 < r5) goto L_0x0050
            r3 = 1
            r3 = r6[r3]     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            int r0 = r3.intValue()     // Catch:{ IOException -> 0x0087, all -> 0x0082 }
            int r0 = r0 * 1024
            long r0 = (long) r0
        L_0x0050:
            if (r4 == 0) goto L_0x0055
            r4.close()     // Catch:{ Exception -> 0x0089 }
        L_0x0055:
            if (r2 == 0) goto L_0x005a
            r2.close()     // Catch:{ Exception -> 0x0089 }
        L_0x005a:
            java.lang.String r0 = android.text.format.Formatter.formatFileSize(r10, r0)
            return r0
        L_0x005f:
            r2 = move-exception
            r2 = r3
            r4 = r3
        L_0x0062:
            if (r4 == 0) goto L_0x0067
            r4.close()     // Catch:{ Exception -> 0x006d }
        L_0x0067:
            if (r2 == 0) goto L_0x005a
            r2.close()     // Catch:{ Exception -> 0x006d }
            goto L_0x005a
        L_0x006d:
            r2 = move-exception
            goto L_0x005a
        L_0x006f:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x0072:
            if (r4 == 0) goto L_0x0077
            r4.close()     // Catch:{ Exception -> 0x007d }
        L_0x0077:
            if (r2 == 0) goto L_0x007c
            r2.close()     // Catch:{ Exception -> 0x007d }
        L_0x007c:
            throw r0
        L_0x007d:
            r1 = move-exception
            goto L_0x007c
        L_0x007f:
            r0 = move-exception
            r4 = r3
            goto L_0x0072
        L_0x0082:
            r0 = move-exception
            goto L_0x0072
        L_0x0084:
            r4 = move-exception
            r4 = r3
            goto L_0x0062
        L_0x0087:
            r3 = move-exception
            goto L_0x0062
        L_0x0089:
            r2 = move-exception
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.comm.APTools.getTotalMemory(android.content.Context):java.lang.String");
    }

    public static String getUUID() {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception e2) {
            return "";
        }
    }

    public static String getUrlParamsValue(String str, String str2) {
        String[] split = str.split("[?]");
        if (split.length <= 1 || split[1] == null) {
            return null;
        }
        for (String split2 : split[1].split("[&]")) {
            String[] split3 = split2.split("[=]");
            String str3 = (split3.length <= 1 || split3[0] == null) ? null : split3[0];
            String str4 = (split3.length <= 1 || split3[1] == null) ? null : split3[1];
            if (str3 != null && str3.compareToIgnoreCase(str2) == 0) {
                return str4;
            }
        }
        return null;
    }

    public static int getWeightPixels(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static void installApp(Context context, String str) {
        if (str != null) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(Uri.parse(IMSDKFileProvider.FILE_SCHEME + str), "application/vnd.android.package-archive");
                context.startActivity(intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0048 A[SYNTHETIC, Splitter:B:16:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004d A[Catch:{ IOException -> 0x0092 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009c A[SYNTHETIC, Splitter:B:36:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a1 A[Catch:{ IOException -> 0x00a5 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void installPaySDK(android.content.Context r7, java.lang.String r8) {
        /*
            r2 = 0
            android.content.Intent r4 = new android.content.Intent
            r4.<init>()
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            r4.addFlags(r0)
            java.lang.String r0 = "android.intent.action.VIEW"
            r4.setAction(r0)
            java.lang.String r5 = "application/vnd.android.package-archive"
            java.lang.Class r0 = r7.getClass()     // Catch:{ Exception -> 0x00af, all -> 0x0097 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00af, all -> 0x0097 }
            r1.<init>()     // Catch:{ Exception -> 0x00af, all -> 0x0097 }
            java.lang.String r3 = "/assets/"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x00af, all -> 0x0097 }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Exception -> 0x00af, all -> 0x0097 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00af, all -> 0x0097 }
            java.io.InputStream r3 = r0.getResourceAsStream(r1)     // Catch:{ Exception -> 0x00af, all -> 0x0097 }
            r0 = 0
            java.io.FileOutputStream r1 = r7.openFileOutput(r8, r0)     // Catch:{ Exception -> 0x00b3, all -> 0x00aa }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0042 }
        L_0x0036:
            int r2 = r3.read(r0)     // Catch:{ Exception -> 0x0042 }
            r6 = -1
            if (r2 == r6) goto L_0x007f
            r6 = 0
            r1.write(r0, r6, r2)     // Catch:{ Exception -> 0x0042 }
            goto L_0x0036
        L_0x0042:
            r0 = move-exception
        L_0x0043:
            r0.printStackTrace()     // Catch:{ all -> 0x00ad }
            if (r3 == 0) goto L_0x004b
            r3.close()     // Catch:{ IOException -> 0x0092 }
        L_0x004b:
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ IOException -> 0x0092 }
        L_0x0050:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.io.File r2 = r7.getFilesDir()
            java.lang.String r2 = r2.getPath()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "/"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r8)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            android.net.Uri r0 = android.net.Uri.fromFile(r0)
            r4.setDataAndType(r0, r5)
            r7.startActivity(r4)
            return
        L_0x007f:
            r1.flush()     // Catch:{ Exception -> 0x0042 }
            if (r3 == 0) goto L_0x0087
            r3.close()     // Catch:{ IOException -> 0x008d }
        L_0x0087:
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ IOException -> 0x008d }
            goto L_0x0050
        L_0x008d:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0050
        L_0x0092:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0050
        L_0x0097:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x009a:
            if (r3 == 0) goto L_0x009f
            r3.close()     // Catch:{ IOException -> 0x00a5 }
        L_0x009f:
            if (r1 == 0) goto L_0x00a4
            r1.close()     // Catch:{ IOException -> 0x00a5 }
        L_0x00a4:
            throw r0
        L_0x00a5:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00a4
        L_0x00aa:
            r0 = move-exception
            r1 = r2
            goto L_0x009a
        L_0x00ad:
            r0 = move-exception
            goto L_0x009a
        L_0x00af:
            r0 = move-exception
            r1 = r2
            r3 = r2
            goto L_0x0043
        L_0x00b3:
            r0 = move-exception
            r1 = r2
            goto L_0x0043
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.comm.APTools.installPaySDK(android.content.Context, java.lang.String):void");
    }

    public static boolean isApkPlugin() {
        APMidasPayAPI.singleton();
        return APMidasPayAPI.applicationContext.getPackageName().equals("com.tencent.unipay");
    }

    public static boolean isDynamicLoad() {
        ClassLoader classLoader = APTools.class.getClassLoader();
        APMidasPayAPI.singleton();
        return classLoader != APMidasPayAPI.applicationContext.getClassLoader();
    }

    public static boolean isFastClick() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - e < 800) {
            return true;
        }
        e = currentTimeMillis;
        return false;
    }

    public static boolean isHavedPermission(Context context, String str) {
        boolean z;
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 4096).requestedPermissions;
            int i = 0;
            z = false;
            while (i < strArr.length) {
                try {
                    if (strArr[i].equals(str)) {
                        z = true;
                    }
                    i++;
                } catch (PackageManager.NameNotFoundException e2) {
                    e = e2;
                    APLog.i("permission compare error", e.toString());
                    return z;
                }
            }
        } catch (PackageManager.NameNotFoundException e3) {
            e = e3;
            z = false;
            APLog.i("permission compare error", e.toString());
            return z;
        }
        return z;
    }

    public static boolean isInstalledApp(Context context, String str) {
        if (str == null) {
            return false;
        }
        List<PackageInfo> installedPackages = context.getApplicationContext().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            if (str.equals(installedPackages.get(i).applicationInfo.packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isServiceRunning(Context context, String str) {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPhoneNum(String str) {
        if (str.length() == 11) {
            return str.startsWith("1");
        }
        if (str.length() > 11) {
            return reverseStr(str).substring(0, 11).endsWith("1");
        }
        return false;
    }

    public static boolean isXYDpiAvailable(float f2, float f3) {
        return f2 <= 450.0f && f2 >= 100.0f && f3 <= 450.0f && f3 >= 100.0f;
    }

    public static HashMap<String, String> keyvalues2Map(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split("\\&");
                String str2 = "";
                String str3 = "";
                for (int i = 0; i < split.length; i++) {
                    try {
                        str2 = split[i].split("\\=")[0];
                        str3 = split[i].split("\\=")[1];
                    } catch (Exception e2) {
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        hashMap.put(str2, str3);
                    }
                }
            } else {
                APLog.i("keyvalue2Map", "keyvalue2Map后参数为空");
            }
        } catch (Exception e3) {
            APLog.w("keyvalue2Map", e3.toString());
        }
        return hashMap;
    }

    public static HashMap<String, String> kv2Map(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split("\\&");
                String str2 = "";
                String str3 = "";
                for (int i = 0; i < split.length; i++) {
                    try {
                        str2 = split[i].split("\\=")[0];
                        str3 = split[i].split("\\=")[1];
                    } catch (Exception e2) {
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        hashMap.put(str2, str3);
                    }
                }
            } else {
                APLog.i("kv2Map", "url后参数为空");
            }
        } catch (Exception e3) {
            APLog.w("kv2Map", e3.toString());
        }
        return hashMap;
    }

    public static Object loadDataCach(String str) {
        if (b == null) {
            b = new HashMap<>();
        }
        return b.get(str);
    }

    public static String map2UrlParams(HashMap<String, String> hashMap) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Map.Entry next : hashMap.entrySet()) {
                stringBuffer.append((String) next.getKey());
                stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                stringBuffer.append((String) next.getValue());
                stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            if (!TextUtils.isEmpty(stringBuffer)) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
        } catch (Exception e2) {
        }
        return stringBuffer.toString();
    }

    public static String readInfo(Context context, String str, String str2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getString(str2, (String) null);
        }
        return null;
    }

    public static String readInfoSDCard(String str) {
        return new APIOXml().readInfoFromXML("Midas.xml", str);
    }

    public static int reflectResouce(String str, String str2, String str3) {
        if (str == null || str2 == null || str3 == null) {
            return 0;
        }
        try {
            Class<?> cls = Class.forName(str + "$" + str2);
            return Integer.parseInt(cls.getField(str3).get(cls.newInstance()).toString());
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public static Drawable resizeImage(Bitmap bitmap, int i, int i2, int i3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        matrix.postRotate((float) i3);
        return new BitmapDrawable(Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true));
    }

    public static String reverseStr(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        return stringBuffer.reverse().toString();
    }

    public static void saveDataCach(String str, Object obj) {
        if (b == null) {
            b = new HashMap<>();
        }
        b.put(str, obj);
    }

    public static void saveInfo(Context context, String str, String str2, String str3) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(str2, str3).commit();
        }
    }

    public static void saveInfoSDCard(String str, String str2) {
        if (hasSDCard()) {
            APIOXml aPIOXml = new APIOXml();
            aPIOXml.writeToXml(aPIOXml.writeInfoToString(str, str2), "Midas.xml");
            return;
        }
        APLog.i("saveInfoSDCard", "no sdcard");
    }

    public static String screenOrient(Activity activity) {
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        activity.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        return width > height ? "landscape" : "portrait";
    }

    public static void setKey(String str) {
        APLog.i(a, "setKey:" + str);
        mKey = str;
    }

    public static void setPackageName(String str) {
        f = str;
    }

    public static String stringMD5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            return byteArrayToHex(instance.digest());
        } catch (NoSuchAlgorithmException e2) {
            return null;
        }
    }

    public static void unstallApp(Context context, String str) {
        if (str != null) {
            context.startActivity(new Intent("android.intent.action.DELETE", Uri.parse("package:" + str)));
        }
    }

    public static HashMap<String, String> url2Map(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        String str2 = str.split("\\?")[1];
        try {
            if (!TextUtils.isEmpty(str2)) {
                String[] split = str2.split("\\&");
                String str3 = "";
                String str4 = "";
                for (int i = 0; i < split.length; i++) {
                    try {
                        str3 = split[i].split("\\=")[0];
                        str4 = split[i].split("\\=")[1];
                    } catch (Exception e2) {
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        hashMap.put(str3, str4);
                    }
                }
            } else {
                APLog.i("url2Map", "url后参数为空");
            }
        } catch (Exception e3) {
            APLog.w("url2Map", e3.toString());
        }
        return hashMap;
    }

    public static String urlDecode(String str, int i) {
        String str2;
        String str3 = "";
        if (TextUtils.isEmpty(str)) {
            APLog.w("", "解码内容为空");
        } else if (i <= 0) {
            return str;
        } else {
            int i2 = 0;
            while (i2 < i) {
                try {
                    str2 = URLDecoder.decode(str, "utf-8");
                } catch (Exception e2) {
                    APLog.i("urlEncode", e2.toString());
                    str2 = str3;
                }
                i2++;
                str3 = str2;
                str = str2;
            }
        }
        return str3;
    }

    public static String urlEncode(String str, int i) {
        String str2;
        String str3 = "";
        if (TextUtils.isEmpty(str)) {
            APLog.i("urlEncode", "编码内容为空");
        } else if (i <= 0) {
            return str;
        } else {
            int i2 = 0;
            while (i2 < i) {
                try {
                    str2 = URLEncoder.encode(str, "utf-8");
                } catch (Exception e2) {
                    APLog.i("urlEncode", e2.toString());
                    str2 = str3;
                }
                i2++;
                str3 = str2;
                str = str2;
            }
        }
        return str3;
    }
}
