package com.tencent.gsdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* compiled from: DevicesInfo */
public class c {
    private static int a = -1;
    /* access modifiers changed from: private */
    public static a b = null;

    public static boolean a(Context context) {
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return activeNetworkInfo.isAvailable();
                }
            } catch (Exception e) {
                Logger.e("Check isNetworkConnected error:" + e.getMessage());
            }
        }
        return false;
    }

    public static String b(Context context) {
        switch (c(context)) {
            case 0:
                return "unknown或无网络";
            case 1:
                return "2G";
            case 2:
                return "3G";
            case 3:
                return "4G";
            case 4:
                return "wifi";
            default:
                return "unknown";
        }
    }

    public static int c(Context context) {
        ConnectivityManager connectivityManager;
        int a2;
        if (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null) {
            return 0;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            a2 = 0;
        } else if (activeNetworkInfo.getType() == 1) {
            a2 = 4;
        } else {
            a2 = activeNetworkInfo.getType() == 0 ? a(activeNetworkInfo) : 0;
        }
        return a2;
    }

    private static int a(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return 0;
        }
        switch (networkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 1;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 2;
            case 13:
                return 3;
            default:
                return 0;
        }
    }

    public static int d(Context context) {
        int i;
        if (context == null) {
            return -1;
        }
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo.getBSSID() != null) {
                i = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 5);
                return i;
            }
        } catch (Exception e) {
            Logger.w("getWifiRssi error:" + e.getMessage());
        }
        i = -1;
        return i;
    }

    public static int e(Context context) {
        int i;
        if (context == null) {
            return -1;
        }
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo.getBSSID() != null) {
                i = connectionInfo.getLinkSpeed();
                return i;
            }
        } catch (Exception e) {
            Logger.w("getWifiLinkSpeed error:" + e.getMessage());
        }
        i = -1;
        return i;
    }

    @SuppressLint({"UseSparseArrays"})
    public static int f(Context context) {
        int i;
        int i2;
        if (context == null) {
            return -1;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            HashMap hashMap = new HashMap();
            try {
                int i3 = 0;
                for (ScanResult next : wifiManager.getScanResults()) {
                    int a2 = a(next.frequency);
                    if (next.BSSID.equalsIgnoreCase(connectionInfo.getBSSID())) {
                        i2 = a2;
                    } else {
                        i2 = i3;
                    }
                    if (hashMap.containsKey(Integer.valueOf(a2))) {
                        hashMap.put(Integer.valueOf(a2), Integer.valueOf(((Integer) hashMap.get(Integer.valueOf(a2))).intValue() + 1));
                    } else {
                        hashMap.put(Integer.valueOf(a2), 1);
                    }
                    i3 = i2;
                }
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                for (Map.Entry entry : hashMap.entrySet()) {
                    int intValue = ((Integer) entry.getKey()).intValue();
                    int intValue2 = ((Integer) entry.getValue()).intValue();
                    i4 += intValue2;
                    if (i3 == intValue) {
                        i = i5;
                    } else if (intValue < i3 - 2 || intValue > i3 + 2) {
                        i = i5;
                        intValue2 = i6;
                    } else {
                        i = i5 + intValue2;
                        intValue2 = i6;
                    }
                    i5 = i;
                    i6 = intValue2;
                }
                Logger.i("信道：" + i3 + "，信道wifi数：" + i6 + "，附近的wifi数:" + i5 + ",扫描到的wifi总数：" + i4);
                return i6;
            } catch (Exception e) {
                Logger.w("getWifiSignal error:" + e.getMessage());
                return -1;
            }
        } catch (Exception e2) {
            return -1;
        }
    }

    public static int a(int i) {
        switch (i) {
            case 2412:
                return 1;
            case 2417:
                return 2;
            case 2422:
                return 3;
            case 2427:
                return 4;
            case 2432:
                return 5;
            case 2437:
                return 6;
            case 2442:
                return 7;
            case 2447:
                return 8;
            case 2452:
                return 9;
            case 2457:
                return 10;
            case 2462:
                return 11;
            case 2467:
                return 12;
            case 2472:
                return 13;
            case 2484:
                return 14;
            case 5180:
                return 36;
            case 5190:
                return 38;
            case 5200:
                return 40;
            case 5210:
                return 42;
            case 5220:
                return 44;
            case 5230:
                return 46;
            case 5240:
                return 48;
            case 5745:
                return 149;
            case 5765:
                return 153;
            case 5785:
                return 157;
            case 5805:
                return 161;
            case 5825:
                return 165;
            default:
                return -1;
        }
    }

    public static void b(int i) {
        a = i;
    }

    public static int a() {
        return a;
    }

    public static void g(final Context context) {
        Logger.d("startMobileSignalListener");
        try {
            if (b == null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        a unused = c.b = new a();
                        if (context != null) {
                            Logger.d("startMobileSignalListener 2");
                            ((TelephonyManager) context.getSystemService("phone")).listen(c.b, 256);
                        }
                    }
                });
            }
        } catch (Exception e) {
            Logger.e("startMobileSignal:" + e.getMessage());
        }
    }

    /* compiled from: DevicesInfo */
    static class a extends PhoneStateListener {
        a() {
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            int intValue;
            super.onSignalStrengthsChanged(signalStrength);
            Logger.d("startMobileSignalListener onSignalStrengthsChanged");
            if (signalStrength != null) {
                try {
                    if (signalStrength.isGsm()) {
                        Method method = SignalStrength.class.getMethod("getLevel", new Class[0]);
                        method.setAccessible(true);
                        intValue = ((Integer) method.invoke(signalStrength, new Object[0])).intValue();
                    } else {
                        Method method2 = SignalStrength.class.getMethod("getEvdoLevel", new Class[0]);
                        method2.setAccessible(true);
                        intValue = ((Integer) method2.invoke(signalStrength, new Object[0])).intValue();
                    }
                    if (intValue == 5) {
                        intValue = 4;
                    }
                    Logger.d("onSignalStrengthsChanged level is " + intValue);
                    c.b(intValue);
                } catch (Exception e) {
                    Logger.w("MobileSignalState:" + e.getMessage());
                }
            }
        }
    }
}
