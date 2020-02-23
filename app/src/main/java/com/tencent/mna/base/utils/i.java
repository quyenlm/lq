package com.tencent.mna.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.lang.reflect.Method;
import java.util.List;

/* compiled from: MobileUtil */
public final class i {
    @SuppressLint({"StaticFieldLeak"})
    private static a a;
    /* access modifiers changed from: private */
    public static int b = -1;
    /* access modifiers changed from: private */
    public static int c = 1;
    /* access modifiers changed from: private */
    public static int d = 65535;

    public static void a(Context context) {
        if (context != null) {
            if (Looper.myLooper() == null) {
                h.a("registerMobileSignalListener failed, looper is null");
                return;
            }
            try {
                if (a == null) {
                    a = new a(context);
                }
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    telephonyManager.listen(a, 256);
                }
            } catch (Exception e) {
                h.a("registerMobileSignalListener exception:" + e.getMessage());
            }
        }
    }

    public static int a() {
        return b;
    }

    public static int b() {
        return c;
    }

    public static int c() {
        return d;
    }

    /* compiled from: MobileUtil */
    static class a extends PhoneStateListener {
        private Context a;

        public a(Context context) {
            this.a = context.getApplicationContext();
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            int i;
            int i2;
            TelephonyManager telephonyManager;
            int networkType;
            super.onSignalStrengthsChanged(signalStrength);
            try {
                if (signalStrength.isGsm() || Build.VERSION.SDK_INT >= 23) {
                    Method method = SignalStrength.class.getMethod("getLevel", new Class[0]);
                    method.setAccessible(true);
                    i = ((Integer) method.invoke(signalStrength, new Object[0])).intValue();
                } else {
                    Method method2 = SignalStrength.class.getMethod("getEvdoLevel", new Class[0]);
                    method2.setAccessible(true);
                    i = ((Integer) method2.invoke(signalStrength, new Object[0])).intValue();
                }
                if (i > 4) {
                    i = 4;
                }
                int unused = i.b = i;
                Method method3 = SignalStrength.class.getMethod("getDbm", new Class[0]);
                method3.setAccessible(true);
                int unused2 = i.c = ((Integer) method3.invoke(signalStrength, new Object[0])).intValue();
                if (this.a == null || (telephonyManager = (TelephonyManager) this.a.getSystemService("phone")) == null || !((networkType = telephonyManager.getNetworkType()) == 13 || networkType == 19 || networkType == 139)) {
                    i2 = 65535;
                } else {
                    i2 = ((Integer) SignalStrength.class.getMethod("getLteRssnr", new Class[0]).invoke(signalStrength, new Object[0])).intValue();
                    if (!Build.BRAND.equalsIgnoreCase("huawei") && !Build.BRAND.equalsIgnoreCase("honor")) {
                        i2 /= 10;
                    }
                }
                int unused3 = i.d = i2;
            } catch (Throwable th) {
                h.a("MobileSignalListener exception:" + th.getMessage());
            }
        }
    }

    public static int b(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo[] allNetworkInfo = connectivityManager != null ? connectivityManager.getAllNetworkInfo() : null;
            if (allNetworkInfo != null) {
                int length = allNetworkInfo.length;
                int i = 0;
                while (i < length) {
                    NetworkInfo networkInfo = allNetworkInfo[i];
                    if (networkInfo.getType() != 0) {
                        i++;
                    } else if (!networkInfo.isAvailable()) {
                        return 0;
                    } else {
                        int a2 = k.a(networkInfo);
                        if (a2 == 0) {
                            return 4;
                        }
                        return a2;
                    }
                }
            }
        } catch (Exception e) {
            h.a("getMobileSubType exception:" + e.getMessage());
        }
        return 0;
    }

    public static String c(Context context) {
        String str;
        int i;
        int i2;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            String networkOperator = telephonyManager != null ? telephonyManager.getNetworkOperator() : null;
            if (networkOperator == null) {
                return "0_0_0_0";
            }
            String substring = networkOperator.substring(0, 3);
            String substring2 = networkOperator.substring(3);
            if (substring == null || substring2 == null) {
                h.a("cellinfo:" + "0_0_0_0");
                return "0_0_0_0";
            }
            if (Build.VERSION.SDK_INT < 26) {
                CellLocation cellLocation = telephonyManager.getCellLocation();
                if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    int lac = gsmCellLocation.getLac();
                    i = gsmCellLocation.getCid();
                    i2 = lac;
                } else {
                    if (cellLocation instanceof CdmaCellLocation) {
                        CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                        int networkId = cdmaCellLocation.getNetworkId();
                        i = cdmaCellLocation.getBaseStationId();
                        i2 = networkId;
                    }
                    i = -1;
                    i2 = -1;
                }
            } else {
                CellInfo d2 = d(context);
                if (d2 instanceof CellInfoLte) {
                    CellIdentityLte cellIdentity = ((CellInfoLte) d2).getCellIdentity();
                    i2 = cellIdentity.getTac();
                    i = cellIdentity.getCi();
                } else if (d2 instanceof CellInfoWcdma) {
                    CellIdentityWcdma cellIdentity2 = ((CellInfoWcdma) d2).getCellIdentity();
                    i2 = cellIdentity2.getLac();
                    i = cellIdentity2.getCid();
                } else if (d2 instanceof CellInfoGsm) {
                    CellIdentityGsm cellIdentity3 = ((CellInfoGsm) d2).getCellIdentity();
                    i2 = cellIdentity3.getLac();
                    i = cellIdentity3.getCid();
                } else {
                    if (d2 instanceof CellInfoCdma) {
                        CellIdentityCdma cellIdentity4 = ((CellInfoCdma) d2).getCellIdentity();
                        i2 = cellIdentity4.getNetworkId();
                        i = cellIdentity4.getBasestationId();
                    }
                    i = -1;
                    i2 = -1;
                }
            }
            str = substring + "_" + substring2 + "_" + i2 + "_" + i;
            return str;
        } catch (SecurityException e) {
            str = "0_0_0_0";
        } catch (Throwable th) {
            h.a("getPhoneCellInfo exception:" + th.getMessage());
            str = "0_0_0_0";
        }
    }

    public static CellInfo d(Context context) {
        if (context != null && Build.VERSION.SDK_INT >= 26) {
            try {
                List<CellInfo> allCellInfo = ((TelephonyManager) context.getSystemService("phone")).getAllCellInfo();
                if (allCellInfo == null) {
                    return null;
                }
                for (CellInfo next : allCellInfo) {
                    if (next.isRegistered()) {
                        return next;
                    }
                }
            } catch (SecurityException e) {
            } catch (Throwable th) {
                h.a("getRegisteredCellInfo exception:" + th.getMessage());
            }
        }
        return null;
    }

    public static int a(CellInfo cellInfo) {
        if (cellInfo == null || Build.VERSION.SDK_INT < 26) {
            return -1;
        }
        if (cellInfo instanceof CellInfoLte) {
            return ((CellInfoLte) cellInfo).getCellSignalStrength().getRsrp();
        }
        return -2;
    }

    public static int b(CellInfo cellInfo) {
        if (cellInfo == null || Build.VERSION.SDK_INT < 26) {
            return -1;
        }
        if (cellInfo instanceof CellInfoLte) {
            return ((CellInfoLte) cellInfo).getCellSignalStrength().getRsrq();
        }
        return -2;
    }
}
