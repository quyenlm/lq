package com.tencent.tdm.system;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.facebook.internal.AnalyticsEvents;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.tp.r;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;

public class TXSystem {
    private static final String DefaultUUID = "UUID";
    private static TXSystem instance = null;
    private static final String tag = "TXSystem";
    private long m_szMemeryAvail = 0;
    private long m_szMemoryTotal = 0;
    private int m_szScreenHeight = 0;
    private int m_szScreenwidth = 0;
    private long m_szSpaceAvail = 0;
    private long m_szSpaceTotal = 0;

    private TXSystem() {
    }

    public static TXSystem getInstance() {
        if (instance == null) {
            instance = new TXSystem();
        }
        return instance;
    }

    public String GetSysVersion() {
        try {
            return Build.VERSION.RELEASE;
        } catch (NoSuchFieldError e) {
            TXLog.e(tag, "get_sys_version failed");
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    public String GetBundleId(Context context) {
        String bundleId = null;
        try {
            bundleId = context.getPackageName();
        } catch (Exception e) {
            TXLog.e(tag, "GetBundleId Exception");
            TXLog.i(tag, "Exception Track: " + e);
        }
        if (bundleId == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return bundleId;
    }

    public String GetAppVersion(Context context) {
        String vName = null;
        int vCode = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            vName = pInfo.versionName;
            vCode = pInfo.versionCode;
        } catch (Exception e) {
            TXLog.e(tag, "GetAppVersion Exception");
            TXLog.i(tag, "Exception Track: " + e);
        }
        if (vName == null) {
            vName = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return String.format(Locale.getDefault(), "%s(%d)", new Object[]{vName, Integer.valueOf(vCode)});
    }

    public String GetApkPath(Context context) {
        String apkAbsPath = null;
        try {
            apkAbsPath = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (apkAbsPath == null) {
            return "";
        }
        return apkAbsPath;
    }

    public String GetModel() {
        try {
            return Build.MODEL;
        } catch (NoSuchFieldError e) {
            TXLog.e(tag, "get Model failed");
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    public String GetBrand() {
        try {
            return Build.BRAND;
        } catch (NoSuchFieldError e) {
            TXLog.e(tag, "get Brand failed");
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    public int GetAppList(Context context, List<String> list) {
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            TXLog.e(tag, "get PackageManager is null");
            return -1;
        } else if (list == null) {
            return -1;
        } else {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            try {
                List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);
                for (int i = 0; i < apps.size(); i++) {
                    list.add(apps.get(i).activityInfo.packageName);
                }
                TXLog.i(tag, "list size:" + list.size());
                return list.size();
            } catch (Exception e) {
                TXLog.e(tag, "get ResolveInfo is null");
                return -1;
            }
        }
    }

    @TargetApi(16)
    private void GetMemoryInfo(Context context) {
        try {
            ActivityManager aManager = (ActivityManager) context.getSystemService("activity");
            if (aManager != null) {
                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                aManager.getMemoryInfo(memoryInfo);
                if (Build.VERSION.SDK_INT >= 16) {
                    this.m_szMemoryTotal = memoryInfo.totalMem >> 20;
                } else {
                    this.m_szMemoryTotal = 0;
                }
                this.m_szMemeryAvail = memoryInfo.availMem >> 20;
            }
        } catch (Exception e) {
            TXLog.e(tag, "GetMemoryInfo failed");
            TXLog.i(tag, "Exception Track: " + e);
        }
    }

    public long GetTotalMemory(Context context) {
        if (this.m_szMemoryTotal == 0) {
            GetMemoryInfo(context);
        }
        return this.m_szMemoryTotal;
    }

    public long GetAvailMemory(Context context) {
        GetMemoryInfo(context);
        return this.m_szMemeryAvail;
    }

    @TargetApi(18)
    private void GetSpaceInfo() {
        long blockSize;
        long blockCount;
        long blockAvail;
        try {
            File file = Environment.getExternalStorageDirectory();
            if (file != null) {
                StatFs sf = new StatFs(file.getPath());
                if (Build.VERSION.SDK_INT >= 18) {
                    blockSize = sf.getBlockSizeLong();
                    blockCount = sf.getBlockCountLong();
                    blockAvail = sf.getAvailableBlocksLong();
                } else {
                    blockSize = (long) sf.getBlockSize();
                    blockCount = (long) sf.getBlockCount();
                    blockAvail = (long) sf.getAvailableBlocks();
                }
                this.m_szSpaceTotal = (blockSize * blockCount) >> 20;
                this.m_szSpaceAvail = (blockSize * blockAvail) >> 20;
            }
        } catch (Exception e) {
            TXLog.e(tag, "getStorage failed");
            TXLog.i(tag, "exception track: " + e);
        }
    }

    public long GetTotalSpace() {
        if (this.m_szSpaceTotal == 0) {
            GetSpaceInfo();
        }
        return this.m_szSpaceTotal;
    }

    public long GetAvailSpace() {
        GetSpaceInfo();
        return this.m_szSpaceAvail;
    }

    @TargetApi(21)
    public String GetCPUName() {
        String cpuModel = null;
        String cpuName = null;
        boolean isX86 = false;
        if (Build.VERSION.SDK_INT >= 21) {
            String[] abis = null;
            try {
                abis = Build.SUPPORTED_ABIS;
            } catch (NoSuchFieldError e) {
                TXLog.e(tag, "get SUPPORTED_ABIS failed");
            }
            if (abis != null && abis.length > 0) {
                isX86 = abis[0].equalsIgnoreCase("x86");
            }
        } else {
            String abi = "";
            try {
                abi = Build.CPU_ABI;
            } catch (NoSuchFieldError e2) {
                TXLog.e(tag, "get CPU_ABI failed");
            }
            isX86 = abi.equalsIgnoreCase("x86");
        }
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader localBufferedReader = new BufferedReader(fr);
            String str2 = "";
            while (true) {
                if (str2 != null) {
                    try {
                        if (str2.contains("Hardware")) {
                            cpuName = str2.split(":")[1];
                            break;
                        }
                        if (str2.contains("model name")) {
                            cpuModel = str2.split(":")[1];
                        }
                        str2 = localBufferedReader.readLine();
                    } catch (Exception e3) {
                        TXLog.e(tag, "GetCPUName, readLine Exception");
                        TXLog.i(tag, "Exception Track: " + e3);
                    }
                }
            }
            localBufferedReader.close();
            fr.close();
            break;
        } catch (Exception e4) {
            TXLog.e(tag, "GetCPUName Exception");
            TXLog.i(tag, "Exception Track: " + e4);
        }
        if (cpuName != null) {
            return cpuName;
        }
        if (!isX86 || cpuModel == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return cpuModel;
    }

    public String GetDeviceID(Context context) {
        return GetUUID(context);
    }

    public String GetUUID(Context context) {
        String imeistring = "";
        if (ContextCompat.checkSelfPermission(context, r.a) != 0) {
            TXLog.e(tag, "getDeviceID, Permission Denied. ");
        } else {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    TXLog.e(tag, "telephonyManager is null");
                } else {
                    imeistring = telephonyManager.getDeviceId();
                }
            } catch (Exception e) {
                TXLog.e(tag, "get DeviceID failed");
                TXLog.i(tag, "Exception Track: " + e);
            }
        }
        String deviceId = imeistring;
        String serial = null;
        try {
            serial = Build.SERIAL;
        } catch (NoSuchFieldError e2) {
            TXLog.e(tag, "get serial failed");
        }
        String androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
        StringBuilder builder = new StringBuilder();
        if (deviceId != null) {
            builder.append("%");
            builder.append(deviceId);
        }
        if (serial != null) {
            builder.append("%");
            builder.append(serial);
        }
        if (androidId != null) {
            builder.append("%");
            builder.append(androidId);
        }
        String uuid = builder.toString();
        if (uuid.length() == 0) {
            TXLog.e(tag, "uuid is null");
            return DefaultUUID;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (digest == null) {
                TXLog.e(tag, "digest is null, return default uuid");
                return DefaultUUID;
            }
            digest.update(uuid.getBytes());
            byte[] hash = digest.digest();
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(Integer.toHexString(b & 255));
            }
            String originUUID = hex.toString().toUpperCase(Locale.ENGLISH);
            String encryptUUID = TX.GetInstance().TXEncryptField("device_id", originUUID);
            TXLog.d(tag, "origin UUID: " + originUUID + ", encrypt UUID: " + encryptUUID);
            return encryptUUID;
        } catch (Exception e3) {
            TXLog.e(tag, "GetUUID Exception");
            TXLog.i(tag, "Exception Track: " + e3);
            return DefaultUUID;
        }
    }

    @TargetApi(17)
    private void GetScreenSize(Context context) {
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                DisplayMetrics dm = new DisplayMetrics();
                if (Build.VERSION.SDK_INT >= 17) {
                    windowManager.getDefaultDisplay().getRealMetrics(dm);
                } else {
                    windowManager.getDefaultDisplay().getMetrics(dm);
                }
                float width = (float) dm.widthPixels;
                float Height = (float) dm.heightPixels;
                if (width > Height) {
                    this.m_szScreenHeight = (int) width;
                    this.m_szScreenwidth = (int) Height;
                    return;
                }
                this.m_szScreenHeight = (int) Height;
                this.m_szScreenwidth = (int) width;
            }
        } catch (Exception e) {
            TXLog.e(tag, "get GetScreenSize failed");
            TXLog.i(tag, "Exception Track: " + e);
        }
    }

    public int GetScreenHeight(Context context) {
        if (this.m_szScreenHeight == 0) {
            GetScreenSize(context);
        }
        return this.m_szScreenHeight;
    }

    public int GetScreenWidth(Context context) {
        if (this.m_szScreenwidth == 0) {
            GetScreenSize(context);
        }
        return this.m_szScreenwidth;
    }

    public String GetMacAddress(Context context) {
        String macAddr = null;
        StringBuffer buf = new StringBuffer();
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                TXLog.w(tag, "networkInterface eth1 is null");
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface != null) {
                byte[] addr = networkInterface.getHardwareAddress();
                int length = addr.length;
                for (int i = 0; i < length; i++) {
                    buf.append(String.format("%02X:", new Object[]{Byte.valueOf(addr[i])}));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                String macAddrTmp = buf.toString();
                macAddr = TX.GetInstance().TXEncryptField("mac_addr", macAddrTmp);
                TXLog.d(tag, "origin macAddr: " + macAddrTmp + ", encrypt macAddr: " + macAddr);
            }
        } catch (Exception e) {
            TXLog.e(tag, "GetMacAdress Exception");
            TXLog.i(tag, "GetMacAdress Exception e:" + e);
        }
        if (macAddr == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return macAddr;
    }

    public NetworkType GetNetworkType(Context context) {
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connManager == null) {
                TXLog.w(tag, "NetworkStateChecker connManager is null");
                return NetworkType.NETWORK_UNKNOWN;
            }
            NetworkInfo netInfo = connManager.getActiveNetworkInfo();
            if (netInfo == null || !netInfo.isConnected()) {
                TXLog.w(tag, "NetworkStateChecker netInfo is null");
                return NetworkType.NETWORK_UNKNOWN;
            } else if (1 == netInfo.getType()) {
                return NetworkType.NETWORK_WIFI;
            } else {
                if (netInfo.getType() != 0) {
                    return NetworkType.NETWORK_UNKNOWN;
                }
                switch (netInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return NetworkType.NETWORK_2G;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return NetworkType.NETWORK_3G;
                    case 13:
                        return NetworkType.NETWORK_4G;
                    default:
                        return NetworkType.NETWORK_MOBILE;
                }
            }
        } catch (Exception e) {
            TXLog.w(tag, "check Get exception");
            TXLog.i(tag, "Exception Track: " + e);
            return NetworkType.NETWORK_UNKNOWN;
        }
    }

    public String GetSimOperator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        if (tm == null || 5 != tm.getSimState()) {
            Object obj = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
            return UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        }
        String carrierCode = tm.getSimOperator();
        String str = carrierCode;
        return carrierCode;
    }

    public String GetCommentInfo(String filename) {
        String commentInfo = "";
        try {
            File file = new File(filename);
            int fileLen = (int) file.length();
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[Math.min(fileLen, 128)];
            try {
                in.skip((long) (fileLen - buffer.length));
                int len = in.read(buffer);
                if (len > 0) {
                    commentInfo = getCommentFromBuffer(buffer, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            in.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return commentInfo;
    }

    private String getCommentFromBuffer(byte[] buffer, int len) {
        byte[] magicDirEnd = {80, 75, 5, 6};
        for (int i = len - 22; i >= 0; i--) {
            boolean isMagicStart = true;
            int k = 0;
            while (true) {
                if (k >= magicDirEnd.length) {
                    break;
                } else if (buffer[i + k] != magicDirEnd[k]) {
                    isMagicStart = false;
                    break;
                } else {
                    k++;
                }
            }
            if (isMagicStart) {
                return new String(buffer, i + 22, Math.min(buffer[i + 20] + (buffer[i + 21] * 256), (len - i) - 22));
            }
        }
        return "";
    }

    public String GetMetaString(Context context, String key) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(key);
        } catch (Exception e) {
            TXLog.e(tag, "GetMetaString exception");
            TXLog.i(tag, "Exception Track: " + e);
            return null;
        }
    }

    public boolean GetMetaBool(Context context, String key) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getBoolean(key);
        } catch (Exception e) {
            TXLog.e(tag, "GetMetaBool exception");
            TXLog.i(tag, "Exception Track: " + e);
            return false;
        }
    }
}
