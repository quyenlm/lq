package com.tsf4g.tx;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.imsdk.tool.etc.APNUtil;
import com.tencent.tp.r;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Locale;

public class TXSystem {
    private boolean m_bFirstLaunch = false;
    private boolean m_bFirstLaunchChecked = false;
    private String m_szAndroidId = null;
    private String m_szBundleId = null;
    /* access modifiers changed from: private */
    public int m_szDbm = 0;
    private final String m_szDefaultUUID = "F";
    private String m_szDeviceId = null;
    private String m_szGameVersion = null;
    /* access modifiers changed from: private */
    public double m_szLatitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    /* access modifiers changed from: private */
    public double m_szLongitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private String m_szModel = null;
    private String m_szSerial = null;
    private boolean m_szSysRooted = false;
    private String m_szSysVersion = null;
    private int m_szTargetVersion = 0;
    private String m_szUdid = null;

    @TargetApi(20)
    private String GetAndroidPermission(int i) {
        switch (i) {
            case 0:
                return "android.permission.READ_CALENDAR";
            case 1:
                return "android.permission.WRITE_CALENDAR";
            case 2:
                return "android.permission.CAMERA";
            case 3:
                return "android.permission.READ_CONTACTS";
            case 4:
                return "android.permission.WRITE_CONTACTS";
            case 5:
                return "android.permission.GET_ACCOUNTS";
            case 6:
                return "android.permission.ACCESS_FINE_LOCATION";
            case 7:
                return "android.permission.ACCESS_COARSE_LOCATION";
            case 8:
                return "android.permission.RECORD_AUDIO";
            case 9:
                return r.a;
            case 10:
                return "android.permission.CALL_PHONE";
            case 11:
                return "android.permission.READ_CALL_LOG";
            case 12:
                return "android.permission.WRITE_CALL_LOG";
            case 13:
                return "com.android.voicemail.permission.ADD_VOICEMAIL";
            case 14:
                return "android.permission.USE_SIP";
            case 15:
                return "android.permission.PROCESS_OUTGOING_CALLS";
            case 16:
                return "android.permission.BODY_SENSORS";
            case 17:
                return "android.permission.SEND_SMS";
            case 18:
                return "android.permission.RECEIVE_SMS";
            case 19:
                return "android.permission.READ_SMS";
            case 20:
                return "android.permission.RECEIVE_WAP_PUSH";
            case 21:
                return "android.permission.RECEIVE_MMS";
            case 22:
                return "android.permission.READ_EXTERNAL_STORAGE";
            case 23:
                return "android.permission.WRITE_EXTERNAL_STORAGE";
            default:
                return null;
        }
    }

    private String getDeviceID(Context context) {
        if (ContextCompat.checkSelfPermission(context, r.a) != 0) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return telephonyManager == null ? "" : telephonyManager.getDeviceId();
        } catch (Exception e) {
            TXLog.e("ERROR", "get DeviceID failed: " + e.toString());
            return "";
        }
    }

    private boolean isExecutable(String str) {
        Process process = null;
        try {
            Process exec = Runtime.getRuntime().exec("ls -l " + str);
            String readLine = new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine();
            TXLog.d("TXSystem", "file info:" + readLine);
            if (readLine != null && readLine.length() >= 4) {
                char charAt = readLine.charAt(3);
                if (charAt == 's' || charAt == 'x') {
                    if (exec == null) {
                        return true;
                    }
                    exec.destroy();
                    return true;
                }
            }
            if (exec != null) {
                exec.destroy();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (process != null) {
                process.destroy();
            }
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
        return false;
    }

    public void CalculateLocaiton(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager != null) {
            AnonymousClass1 r5 = new LocationListener() {
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        double unused = TXSystem.this.m_szLatitude = location.getLatitude();
                        double unused2 = TXSystem.this.m_szLongitude = location.getLongitude();
                    }
                }

                public void onProviderDisabled(String str) {
                }

                public void onProviderEnabled(String str) {
                }

                public void onStatusChanged(String str, int i, Bundle bundle) {
                }
            };
            Criteria criteria = new Criteria();
            criteria.setAccuracy(2);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(1);
            criteria.setAltitudeRequired(true);
            locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true), Constants.ACTIVE_THREAD_WATCHDOG, 10.0f, r5);
            locationManager.removeUpdates(r5);
        }
    }

    public int CheckPermission(Context context, int i) {
        String GetAndroidPermission = GetAndroidPermission(i);
        if (context == null || GetAndroidPermission == null) {
            TXLog.e("TXSystem", "CheckPermission context or permission is null");
            return -1;
        }
        TXLog.d("TXSystem", "CheckPermission Permission: " + GetAndroidPermission);
        return ContextCompat.checkSelfPermission(context, GetAndroidPermission);
    }

    public String GetBundleId(Context context) {
        try {
            this.m_szBundleId = context.getPackageName();
            return this.m_szBundleId;
        } catch (Exception e) {
            TXLog.e("TXSystem", "GetBundleId Exception:" + e);
            return null;
        }
    }

    public String GetCurrentAPN(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            TXLog.e("Error", "ConnectivityManager is null");
            return "WIFI";
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            TXLog.e("Error", "NetworkInfo is null");
            return "WIFI";
        }
        String typeName = activeNetworkInfo.getTypeName();
        if (typeName == null) {
            TXLog.e("Error", "typeName is null");
            return "WIFI";
        } else if (typeName.toUpperCase(Locale.ENGLISH).equals("WIFI")) {
            return "WIFI";
        } else {
            String extraInfo = activeNetworkInfo.getExtraInfo();
            if (extraInfo == null) {
                TXLog.e("Error", "getExtraInfo is null");
                return "WIFI";
            }
            String lowerCase = extraInfo.toLowerCase(Locale.ENGLISH);
            return lowerCase.startsWith("cmwap") ? "CMWAP" : (lowerCase.startsWith("cmnet") || lowerCase.startsWith("epc.tmobile.com")) ? "CMNET" : lowerCase.startsWith("uniwap") ? "UNIWAP" : lowerCase.startsWith("uninet") ? "UNINET" : lowerCase.startsWith(APNUtil.ANP_NAME_WAP) ? "WAP" : lowerCase.startsWith(APNUtil.ANP_NAME_NET) ? "NET" : lowerCase.startsWith("ctwap") ? "CTWAP" : lowerCase.startsWith("ctnet") ? "CTNET" : lowerCase.startsWith(Apn.APN_3GWAP) ? "3GWAP" : lowerCase.startsWith(Apn.APN_3GNET) ? "3GNET" : lowerCase.startsWith("ctwap") ? "CTWAP" : "3G";
        }
    }

    public String GetGameVersion(Context context) {
        try {
            this.m_szGameVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionName;
            return this.m_szGameVersion;
        } catch (PackageManager.NameNotFoundException e) {
            TXLog.e("TXSystem", "GetGameVersion Exception:" + e);
            return null;
        }
    }

    public String GetICCIDInfo(Context context) {
        if (ContextCompat.checkSelfPermission(context, r.a) != 0) {
            return "N";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return telephonyManager == null ? "N" : telephonyManager.getSimSerialNumber();
        } catch (Exception e) {
            TXLog.e("ERROR", "get ICCID failed: " + e.toString());
            return "N";
        }
    }

    public double GetLatitude() {
        return this.m_szLatitude;
    }

    public String GetLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            return nextElement.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            TXLog.e("ERROR", "get local IP address failed: " + e.toString());
        }
        return "N";
    }

    public double GetLongitude() {
        return this.m_szLongitude;
    }

    public String GetModel() {
        this.m_szModel = Build.MODEL;
        return this.m_szModel != null ? this.m_szModel : "Model unknown";
    }

    public int GetSignalStrength(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        AnonymousClass2 r1 = new PhoneStateListener() {
            public void onCellLocationChanged(CellLocation cellLocation) {
                if (!(cellLocation instanceof GsmCellLocation) && (cellLocation instanceof CdmaCellLocation)) {
                }
            }

            public void onServiceStateChanged(ServiceState serviceState) {
                super.onServiceStateChanged(serviceState);
            }

            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                int unused = TXSystem.this.m_szDbm = (signalStrength.getGsmSignalStrength() * 2) - 113;
                super.onSignalStrengthsChanged(signalStrength);
            }
        };
        if (telephonyManager != null) {
            telephonyManager.listen(r1, 256);
        }
        return this.m_szDbm;
    }

    public String GetSysVersion() {
        this.m_szSysVersion = Build.VERSION.RELEASE;
        return this.m_szSysVersion != null ? this.m_szSysVersion : "SysVersion unknown";
    }

    public int GetTargetVersion(Context context) {
        try {
            this.m_szTargetVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).applicationInfo.targetSdkVersion;
            return this.m_szTargetVersion;
        } catch (PackageManager.NameNotFoundException e) {
            TXLog.e("TXSystem", "GetTargetVersion Exception:" + e);
            return 0;
        }
    }

    public String GetUdid(Context context) {
        this.m_szDeviceId = getDeviceID(context);
        this.m_szSerial = Build.SERIAL;
        this.m_szAndroidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
        StringBuilder sb = new StringBuilder();
        if (this.m_szDeviceId != null) {
            sb.append("%");
            sb.append(this.m_szDeviceId);
        }
        if (this.m_szSerial != null) {
            sb.append("%");
            sb.append(this.m_szSerial);
        }
        if (this.m_szAndroidId != null) {
            sb.append("%");
            sb.append(this.m_szAndroidId);
        }
        this.m_szUdid = sb.toString();
        if (this.m_szUdid.length() == 0) {
            return "F";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            if (instance == null) {
                return "F";
            }
            instance.update(this.m_szUdid.getBytes());
            byte[] digest = instance.digest();
            StringBuilder sb2 = new StringBuilder();
            for (byte b : digest) {
                sb2.append(Integer.toHexString(b & 255));
            }
            return sb2.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            TXLog.w("Exception", "NoSuchAlgorithmException:MD5");
            return "F";
        }
    }

    public boolean IsFirstLaunch(Context context) {
        if (this.m_bFirstLaunchChecked) {
            return this.m_bFirstLaunch;
        }
        this.m_bFirstLaunchChecked = true;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("APOLLO_STATIS", 0);
            if (sharedPreferences == null) {
                this.m_bFirstLaunch = false;
                return this.m_bFirstLaunch;
            }
            String string = sharedPreferences.getString("APP_RECORD_VERSION", "VERSION_EMPTY");
            String GetGameVersion = GetGameVersion(context);
            if (GetGameVersion.equals(string)) {
                this.m_bFirstLaunch = false;
                return this.m_bFirstLaunch;
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("APP_RECORD_VERSION", GetGameVersion);
            edit.apply();
            this.m_bFirstLaunch = true;
            return this.m_bFirstLaunch;
        } catch (Exception e) {
            e.printStackTrace();
            this.m_bFirstLaunch = false;
            return this.m_bFirstLaunch;
        }
    }

    public boolean IsSysRooted() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < strArr.length) {
            try {
                String str = strArr[i] + "su";
                File file = new File(str);
                if (file == null || !file.exists() || !isExecutable(str)) {
                    i++;
                } else {
                    TXLog.d("TXSystem", "device is Rooted");
                    this.m_szSysRooted = true;
                    return this.m_szSysRooted;
                }
            } catch (Exception e) {
                TXLog.e("TXSystem,", "IsSysRooted Exception:" + e);
            }
        }
        this.m_szSysRooted = false;
        return this.m_szSysRooted;
    }
}
