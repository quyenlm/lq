package com.tencent.imsdk.framework.config;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.imsdk.api.IMSystem;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.imsdk.stat.api.IMStat;
import com.tencent.imsdk.tool.etc.APNUtil;
import com.tencent.imsdk.tool.etc.CommonUtil;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfo {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceInfo.class.desiredAssertionStatus());
    public static final String APN_PROP_PROXY = "proxy";
    private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
    private Context mCtx;
    private PackageManager mPm;

    public DeviceInfo(Context ctx) {
        this.mCtx = ctx;
        this.mPm = ctx.getPackageManager();
    }

    public String getAPPVersion() {
        try {
            return this.mPm.getPackageInfo(this.mCtx.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            IMLogger.e(e.getMessage());
            return "";
        }
    }

    public int getVersionCode() {
        try {
            return this.mPm.getPackageInfo(this.mCtx.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getOs() {
        return SystemMediaRouteProvider.PACKAGE_NAME;
    }

    public String getResolution() {
        DisplayMetrics metrics = this.mCtx.getResources().getDisplayMetrics();
        if (metrics == null) {
            return "";
        }
        return metrics.widthPixels + "*" + metrics.heightPixels;
    }

    public String getNetwork() {
        NetworkInfo info;
        ConnectivityManager cm = (ConnectivityManager) this.mCtx.getSystemService("connectivity");
        if (cm == null || (info = cm.getActiveNetworkInfo()) == null) {
            return "";
        }
        return info.getExtraInfo().toLowerCase(Locale.CHINESE);
    }

    public String getImei() {
        TelephonyManager tm = (TelephonyManager) IMSystem.getInstance().getActivity().getSystemService("phone");
        if (tm == null) {
            return "";
        }
        String imei = tm.getDeviceId();
        if (CommonUtil.ckIsEmpty(imei)) {
            return "";
        }
        return imei;
    }

    public String getQImei() {
        String qImei = "";
        try {
            qImei = IMStat.getStatIMEI();
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
        if (CommonUtil.ckIsEmpty(qImei)) {
            return "";
        }
        return qImei;
    }

    public String getProvidersName() {
        String ProvidersName = null;
        String IMSI = ((TelephonyManager) this.mCtx.getSystemService("phone")).getSubscriberId();
        if (IMSI == null) {
            return "";
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        }
        String str = ProvidersName;
        return ProvidersName;
    }

    public String getApn() {
        String apn = "";
        ConnectivityManager cm = (ConnectivityManager) IMSystem.getInstance().getActivity().getSystemService("connectivity");
        if (cm == null) {
            String str = apn;
            return "";
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            String str2 = apn;
            return "";
        }
        String typeName = info.getTypeName();
        if (typeName == null) {
            String str3 = apn;
            return "";
        }
        IMLogger.d("typeName:" + typeName);
        if (typeName.toUpperCase(Locale.CHINA).equals("WIFI")) {
            apn = "wifi";
        } else if (info.getExtraInfo() == null) {
            String str4 = apn;
            return "";
        } else {
            String extraInfo = info.getExtraInfo().toLowerCase(Locale.CHINA);
            IMLogger.d("extraInfo:" + extraInfo);
            if (extraInfo.startsWith("cmwap")) {
                apn = "cmwap";
            } else if (extraInfo.startsWith("cmnet") || extraInfo.startsWith("epc.tmobile.com")) {
                apn = "cmnet";
            } else if (extraInfo.startsWith("uniwap")) {
                apn = "uniwap";
            } else if (extraInfo.startsWith("uninet")) {
                apn = "uninet";
            } else if (extraInfo.startsWith(APNUtil.ANP_NAME_WAP)) {
                apn = APNUtil.ANP_NAME_WAP;
            } else if (extraInfo.startsWith(APNUtil.ANP_NAME_NET)) {
                apn = APNUtil.ANP_NAME_NET;
            } else if (extraInfo.startsWith("ctwap")) {
                apn = "ctwap";
            } else if (extraInfo.startsWith("ctnet")) {
                apn = "ctnet";
            } else if (extraInfo.startsWith(Apn.APN_3GWAP)) {
                apn = Apn.APN_3GWAP;
            } else if (extraInfo.startsWith(Apn.APN_3GNET)) {
                apn = Apn.APN_3GNET;
            } else if (extraInfo.startsWith(Apn.APN_777)) {
                String proxy = getApnProxy(IMSystem.getInstance().getActivity());
                if (proxy == null || proxy.length() <= 0) {
                    apn = "cmda net";
                } else {
                    apn = "cmda wap";
                }
            }
        }
        String str5 = apn;
        return apn;
    }

    public static String getApnProxy(Context context) {
        Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, (String[]) null, (String) null, (String[]) null, (String) null);
        c.moveToFirst();
        if (c.isAfterLast()) {
            c.close();
            return null;
        }
        String strResult = c.getString(c.getColumnIndex("proxy"));
        c.close();
        return strResult;
    }

    public String getBrand() {
        return Build.BRAND;
    }

    @SuppressLint({"NewApi"})
    public String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public String getModel() {
        return Build.MODEL;
    }

    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getRAMInfo() {
        String str;
        FileReader localFileReader = null;
        try {
            FileReader localFileReader2 = new FileReader("/proc/meminfo");
            try {
                BufferedReader localBufferedReader = new BufferedReader(localFileReader2, 8192);
                String str2 = localBufferedReader.readLine();
                if (str2 == null) {
                    localBufferedReader.close();
                    str = "";
                    if (localFileReader2 != null) {
                        try {
                            localFileReader2.close();
                        } catch (Exception e) {
                            IMLogger.d("localFileReader exception = " + e.getMessage());
                        }
                    }
                    FileReader fileReader = localFileReader2;
                } else {
                    long initialMemory = ((long) Integer.valueOf(str2.split("\\s+")[1]).intValue()) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                    localBufferedReader.close();
                    str = "" + initialMemory;
                    if (localFileReader2 != null) {
                        try {
                            localFileReader2.close();
                        } catch (Exception e2) {
                            IMLogger.d("localFileReader exception = " + e2.getMessage());
                        }
                    }
                    FileReader fileReader2 = localFileReader2;
                }
            } catch (IOException e3) {
                localFileReader = localFileReader2;
            } catch (Throwable th) {
                FileReader localFileReader3 = localFileReader2;
                if (localFileReader3 != null) {
                    try {
                        localFileReader3.close();
                    } catch (Exception e4) {
                        IMLogger.d("localFileReader exception = " + e4.getMessage());
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            str = "";
            if (localFileReader != null) {
                try {
                    localFileReader.close();
                } catch (Exception e6) {
                    IMLogger.d("localFileReader exception = " + e6.getMessage());
                }
            }
            return str;
        }
        return str;
    }

    public String getROMInfo() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = (long) statFs.getBlockSize();
        return "" + (((long) statFs.getBlockCount()) * blockSize);
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a5 A[SYNTHETIC, Splitter:B:43:0x00a5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getCpuInfo() {
        /*
            r13 = this;
            r5 = -1
            r6 = 0
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch:{ IOException -> 0x00c8 }
            java.lang.String r10 = "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state"
            java.lang.String r11 = "r"
            r7.<init>(r10, r11)     // Catch:{ IOException -> 0x00c8 }
            r0 = 0
        L_0x000c:
            if (r0 != 0) goto L_0x0015
            java.lang.String r4 = r7.readLine()     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            if (r4 != 0) goto L_0x002f
            r0 = 1
        L_0x0015:
            if (r7 == 0) goto L_0x001a
            r7.close()     // Catch:{ Exception -> 0x0084 }
        L_0x001a:
            r6 = r7
        L_0x001b:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = ""
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r5)
            java.lang.String r10 = r10.toString()
            return r10
        L_0x002f:
            java.lang.String r10 = "\\s+"
            java.lang.String[] r8 = r4.split(r10)     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            boolean r10 = $assertionsDisabled     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            if (r10 != 0) goto L_0x006e
            int r10 = r8.length     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            r11 = 2
            if (r10 == r11) goto L_0x006e
            java.lang.AssertionError r10 = new java.lang.AssertionError     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            r10.<init>()     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            throw r10     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
        L_0x0043:
            r2 = move-exception
            r6 = r7
        L_0x0045:
            java.lang.String r10 = r2.getMessage()     // Catch:{ all -> 0x00a2 }
            com.tencent.imsdk.tool.etc.IMLogger.e(r10)     // Catch:{ all -> 0x00a2 }
            if (r6 == 0) goto L_0x001b
            r6.close()     // Catch:{ Exception -> 0x0052 }
            goto L_0x001b
        L_0x0052:
            r1 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = " reader close exception =  "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r1.getMessage()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r10)
            goto L_0x001b
        L_0x006e:
            r10 = 1
            r10 = r8[r10]     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            int r9 = java.lang.Integer.parseInt(r10)     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            if (r9 <= 0) goto L_0x000c
            r10 = 0
            r10 = r8[r10]     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ IOException -> 0x0043, all -> 0x00c5 }
            int r3 = r10 / 1000
            if (r3 <= r5) goto L_0x000c
            r5 = r3
            goto L_0x000c
        L_0x0084:
            r1 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = " reader close exception =  "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r1.getMessage()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r10)
            r6 = r7
            goto L_0x001b
        L_0x00a2:
            r10 = move-exception
        L_0x00a3:
            if (r6 == 0) goto L_0x00a8
            r6.close()     // Catch:{ Exception -> 0x00a9 }
        L_0x00a8:
            throw r10
        L_0x00a9:
            r1 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = " reader close exception =  "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = r1.getMessage()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r11)
            goto L_0x00a8
        L_0x00c5:
            r10 = move-exception
            r6 = r7
            goto L_0x00a3
        L_0x00c8:
            r2 = move-exception
            goto L_0x0045
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.framework.config.DeviceInfo.getCpuInfo():java.lang.String");
    }

    @SuppressLint({"NewApi"})
    public String getPhoneName() {
        String deviceName = Build.MODEL;
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        if (myDevice != null) {
            return myDevice.getName();
        }
        return deviceName;
    }

    public JSONObject getAllDeviceInfo() {
        JSONObject deviceInfo = new JSONObject();
        try {
            deviceInfo.put(HttpRequestParams.PUSH_XG_ID, getQImei());
            deviceInfo.put("qImei", getQImei());
            deviceInfo.put("appVersion", getAPPVersion());
            deviceInfo.put("appVersionCode", getVersionCode());
            deviceInfo.put("osSystem", SystemMediaRouteProvider.PACKAGE_NAME);
            deviceInfo.put("deviceResolution", getResolution());
            deviceInfo.put("deviceApn", getApn());
            deviceInfo.put("mobileService", getProvidersName());
            deviceInfo.put("deviceTradeMark", getBrand());
            deviceInfo.put("deviceManufacturer", getManufacturer());
            deviceInfo.put("deviceModel", getModel());
            deviceInfo.put("deviceImei", getImei());
            deviceInfo.put("deviceName", getModel());
            deviceInfo.put("deviceRom", getROMInfo());
            deviceInfo.put("deviceRam", getRAMInfo());
            deviceInfo.put("deviceCPU", getCpuInfo());
        } catch (JSONException e) {
            IMLogger.e(e.getMessage());
        }
        return deviceInfo;
    }
}
