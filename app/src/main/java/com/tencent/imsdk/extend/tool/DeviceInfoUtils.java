package com.tencent.imsdk.extend.tool;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.imsdk.tool.etc.APNUtil;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MD5;
import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfoUtils {

    public static class IMDeviceInfo extends JsonSerializable {
        @JsonProp(name = "androidId")
        public String androidId;
        @JsonProp(name = "apn")
        public String apn;
        @JsonProp(name = "appVersionCode")
        public int appVersionCode;
        @JsonProp(name = "appVersionName")
        public String appVersionName;
        @JsonProp(name = "brand")
        public String brand;
        @JsonProp(name = "country")
        public String country;
        @JsonProp(name = "guestId")
        public String guestId;
        @JsonProp(name = "height")
        public int height;
        @JsonProp(name = "imei")
        public String imei;
        @JsonProp(name = "language")
        public String language;
        @JsonProp(name = "mac")
        public String mac;
        @JsonProp(name = "manufacturer")
        public String manufacturer;
        @JsonProp(name = "model")
        public String model;
        @JsonProp(name = "network")
        public String network;
        @JsonProp(name = "operators")
        public String operators;
        @JsonProp(name = "osName")
        public String osName;
        @JsonProp(name = "osVersion")
        public String osVersion;
        @JsonProp(name = "packageChannelId")
        public String packageChannelId;
        @JsonProp(name = "phoneName")
        public String phoneName;
        @JsonProp(name = "seriesId")
        public String seriesId;
        @JsonProp(name = "width")
        public int width;

        public IMDeviceInfo() {
        }

        public IMDeviceInfo(JSONObject object) throws JSONException {
            super(object);
        }

        public IMDeviceInfo(String json) throws JSONException {
            super(json);
        }
    }

    public static IMDeviceInfo getInfo(Context context) {
        IMDeviceInfo deviceInfo = new IMDeviceInfo();
        deviceInfo.packageChannelId = getPackageChannelId(context);
        deviceInfo.guestId = getGuestId(context);
        deviceInfo.appVersionName = getAppVersionName(context);
        deviceInfo.appVersionCode = getAppVersionCode(context);
        deviceInfo.osName = getOSName();
        deviceInfo.osVersion = getOSVersion();
        deviceInfo.width = getWidth(context);
        deviceInfo.height = getHeight(context);
        deviceInfo.network = getNetwork(context);
        deviceInfo.imei = getIMEI(context);
        deviceInfo.operators = getOperators(context);
        deviceInfo.apn = getApn(context);
        deviceInfo.brand = getBrand();
        deviceInfo.manufacturer = getManufacturer();
        deviceInfo.model = getModel();
        deviceInfo.phoneName = getPhoneName();
        deviceInfo.language = getLanguage();
        deviceInfo.country = getCountry();
        deviceInfo.androidId = getAndroidId(context);
        deviceInfo.mac = getMac(context);
        deviceInfo.seriesId = getSeriesId();
        return deviceInfo;
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getOSName() {
        return "Android";
    }

    public static String getOSVersion() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Exception e) {
            return "";
        }
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            return displayMetrics.widthPixels;
        }
        return 0;
    }

    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            return displayMetrics.heightPixels;
        }
        return 0;
    }

    public static String getNetwork(Context context) {
        NetworkInfo networkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null || (networkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return "";
            }
            return networkInfo.getExtraInfo().toLowerCase(Locale.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        if (r1.trim().equalsIgnoreCase(com.amazonaws.services.s3.internal.Constants.NULL_VERSION_ID) == false) goto L_0x0037;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getIMEI(android.content.Context r6) {
        /*
            java.lang.String r1 = ""
            java.lang.String r4 = "phone"
            java.lang.Object r3 = r6.getSystemService(r4)     // Catch:{ Exception -> 0x0033 }
            android.telephony.TelephonyManager r3 = (android.telephony.TelephonyManager) r3     // Catch:{ Exception -> 0x0033 }
            if (r3 == 0) goto L_0x003a
            java.lang.String r1 = r3.getDeviceId()     // Catch:{ Exception -> 0x002e }
        L_0x0010:
            if (r1 == 0) goto L_0x002a
            java.lang.String r4 = r1.trim()     // Catch:{ Exception -> 0x0033 }
            java.lang.String r5 = ""
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ Exception -> 0x0033 }
            if (r4 != 0) goto L_0x002a
            java.lang.String r4 = r1.trim()     // Catch:{ Exception -> 0x0033 }
            java.lang.String r5 = "null"
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ Exception -> 0x0033 }
            if (r4 == 0) goto L_0x0037
        L_0x002a:
            java.lang.String r4 = ""
            r2 = r1
        L_0x002d:
            return r4
        L_0x002e:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0033 }
            goto L_0x0010
        L_0x0033:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0037:
            r2 = r1
            r4 = r1
            goto L_0x002d
        L_0x003a:
            java.lang.String r1 = ""
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.extend.tool.DeviceInfoUtils.getIMEI(android.content.Context):java.lang.String");
    }

    public static String getOperators(Context context) {
        String operators;
        PackageManager packageManager = context.getPackageManager();
        String imsi = "";
        try {
            imsi = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imsi == null) {
            Object obj = "";
            return "";
        }
        if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
            operators = "中国移动";
        } else if (imsi.startsWith("46001")) {
            operators = "中国联通";
        } else if (imsi.startsWith("46003")) {
            operators = "中国电信";
        } else {
            operators = "";
        }
        String str = operators;
        return operators;
    }

    public static String getApn(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "";
            }
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return "";
            }
            if (networkInfo.getTypeName().toUpperCase(Locale.CHINA).equals("WIFI")) {
                return "";
            }
            if (networkInfo.getExtraInfo() == null) {
                return "";
            }
            String extraInfo = networkInfo.getExtraInfo().toLowerCase(Locale.CHINA);
            if (extraInfo.startsWith("cmwap")) {
                return "cmwap";
            }
            if (extraInfo.startsWith("cmnet") || extraInfo.startsWith("epc.tmobile.com")) {
                return "cmnet";
            }
            if (extraInfo.startsWith("uniwap")) {
                return "uniwap";
            }
            if (extraInfo.startsWith("uninet")) {
                return "uninet";
            }
            if (extraInfo.startsWith(APNUtil.ANP_NAME_WAP)) {
                return APNUtil.ANP_NAME_WAP;
            }
            if (extraInfo.startsWith(APNUtil.ANP_NAME_NET)) {
                return APNUtil.ANP_NAME_NET;
            }
            if (extraInfo.startsWith("ctwap")) {
                return "ctwap";
            }
            if (extraInfo.startsWith(Apn.APN_3GWAP)) {
                return Apn.APN_3GWAP;
            }
            if (extraInfo.startsWith(Apn.APN_3GNET)) {
                return Apn.APN_3GNET;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            IMLogger.w(e.getMessage());
            IMLogger.w("maybe you forget to add permission : android.permission.ACCESS_NETWORK_STATE");
            return "";
        }
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getPhoneName() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return "";
        }
        try {
            return bluetoothAdapter.getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getLanguage() {
        String language = "";
        try {
            language = Locale.getDefault().getLanguage();
            IMLogger.d("language is : " + language);
            return language;
        } catch (Exception e) {
            e.printStackTrace();
            return language;
        }
    }

    public static String getCountry() {
        String country = "";
        try {
            country = Locale.getDefault().getCountry();
            IMLogger.d("country is : " + country);
            return country;
        } catch (Exception e) {
            e.printStackTrace();
            return country;
        }
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static String getSeriesId() {
        return Build.SERIAL;
    }

    public static String getMac(Context context) {
        try {
            WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            if (wifiInfo.getMacAddress() != null) {
                return wifiInfo.getMacAddress();
            }
            return Constants.NULL_VERSION_ID;
        } catch (SecurityException ex) {
            ex.printStackTrace();
            IMLogger.w("Need ACCESS_WIFI_STATE permission");
            return "";
        }
    }

    public static String getPackageChannelId(Context context) {
        try {
            File file = new File(context.getPackageCodePath());
            if (file != null) {
                return ApkUtils.readChannelId(file);
            }
            IMLogger.w("read apk file null");
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            IMLogger.e("read apk file for channelId exception : " + e.getMessage());
            return "";
        }
    }

    public static String getPackageChannelId(String filePath) {
        try {
            File file = new File(filePath);
            if (file != null) {
                return ApkUtils.readChannelId(file);
            }
            IMLogger.w("read apk file null");
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            IMLogger.e("read apk file for channelId exception : " + e.getMessage());
            return "";
        }
    }

    public static String getGuestId(Context context) {
        return DeviceUuidUtils.getDeviceUuid(context);
    }

    @Deprecated
    public static String getGuestIdOld() {
        return MD5.getMD5(Build.SERIAL + Build.HARDWARE + Build.DEVICE + Build.FINGERPRINT + Build.USER + Build.ID + Build.MANUFACTURER);
    }

    private static String getRAMInfo() {
        return null;
    }

    private static String getROMInfo() {
        return null;
    }

    private static String getCPUInfo() {
        return null;
    }
}
