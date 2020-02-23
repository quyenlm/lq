package com.tencent.imsdk.tool.etc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.amazonaws.services.s3.internal.Constants;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Formatter;
import java.util.Locale;
import java.util.UUID;

public final class DeviceUuidFactory {
    private static final String DECORATE_SYMBOL = "|";
    private static final String DEFAULT_EMPTY = "";
    private static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";
    private static final String IMSDK_MAC_MARK = "com.tencent.imsdk.MacMark";
    private static final String PREFS_ANDROID_ID = "android_id";
    private static final String PREFS_AUTO_GENERATE_ID = "auto_generate_id";
    private static final String PREFS_COMBINE_UNIQUE_ID = "uuid";
    private static final String PREFS_DEVICE_ID = "device_id";
    private static final String PREFS_FILE = "device_id.xml";
    private static final String PREFS_SERIES_ID = "series_id";
    private static final int STRING_BUFFER_LENGTH = 1024;
    private static volatile StringBuffer deviceIDsCache = null;
    private static volatile DeviceUuidFactory instance = null;
    private static Context mContext;
    private static String mExtra = null;
    /* access modifiers changed from: private */
    public static String mGoogleAdId = null;
    private static final Object mLock = new Object();
    private static volatile String uuid;

    private interface IMListener {
        void onNotify(String str);
    }

    public static DeviceUuidFactory getInstance(Context context) {
        if (instance == null) {
            synchronized (mLock) {
                if (instance == null) {
                    instance = new DeviceUuidFactory(context);
                }
            }
        }
        return instance;
    }

    private String md5Encrypt(String unencryptedString) {
        if (unencryptedString == null) {
            return null;
        }
        return MD5.getMD5(unencryptedString);
    }

    private String getAndroidId(Context context) {
        String androidId;
        if (context == null || (androidId = Settings.Secure.getString(context.getContentResolver(), PREFS_ANDROID_ID)) == null) {
            return "";
        }
        return androidId;
    }

    private String getDeviceId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return telephonyManager.getDeviceId();
            }
            return null;
        } catch (SecurityException e) {
            IMLogger.w("iMSDK guest need android.permission.READ_PHONE_STATE to get unique device id");
            return null;
        } catch (Exception e2) {
            IMLogger.e("get device id error : " + e2.getMessage());
            return null;
        }
    }

    @Deprecated
    private DeviceUuidFactory(Context context, String args) {
        mContext = context;
        if (uuid == null) {
            synchronized (mLock) {
                if (uuid == null) {
                    uuid = getOldUUID();
                    if (uuid == null) {
                        SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                        String androidId = getAndroidId(context);
                        String seriesId = Build.SERIAL;
                        String autoGenerateId = null;
                        String deviceId = getDeviceId(context);
                        String androidIdMD5 = md5Encrypt(androidId);
                        String deviceIdMD5 = md5Encrypt(deviceId);
                        String seriesIdMD5 = md5Encrypt(seriesId);
                        prefs.edit().putString(PREFS_SERIES_ID, seriesIdMD5).putString("device_id", deviceIdMD5).putString(PREFS_ANDROID_ID, androidIdMD5).commit();
                        if (seriesId != null) {
                            uuid = seriesIdMD5;
                        } else if (deviceId != null) {
                            uuid = deviceIdMD5;
                        } else {
                            if (androidId != null) {
                                if (!"9774d56d682e549c".equals(androidId)) {
                                    uuid = androidIdMD5;
                                }
                            }
                            autoGenerateId = md5Encrypt(UUID.randomUUID().toString());
                            uuid = autoGenerateId;
                            prefs.edit().putString(PREFS_AUTO_GENERATE_ID, uuid).commit();
                        }
                        deviceIDsCache = new StringBuffer(1024);
                        deviceIDsCache.append(seriesIdMD5).append(",").append(deviceIdMD5).append(",").append(androidIdMD5).append(",").append(autoGenerateId);
                        IMLogger.d("androidId = " + androidId + ", deviceId = " + deviceId + " , seriesId = " + seriesId);
                        IMLogger.d("Persistence Data : " + deviceIDsCache.toString());
                    }
                }
            }
        }
    }

    private DeviceUuidFactory(Context context) {
        mContext = context;
    }

    public String getDeviceUuid() {
        if (uuid == null) {
            synchronized (mLock) {
                uuid = getPrefsDeviceId();
                if (uuid == null) {
                    String androidId = getAndroidId(mContext);
                    String seriesId = getSerial();
                    String deviceId = getDeviceId(mContext);
                    String macSeries = getMac();
                    IMLogger.d("androidId : " + androidId + ", seriesId : " + seriesId + ", deviceId : " + deviceId + ", mac :" + macSeries);
                    deviceIDsCache = new StringBuffer(1024);
                    deviceIDsCache.append(md5Encrypt(seriesId)).append(",").append(md5Encrypt(deviceId)).append(",").append(md5Encrypt(androidId)).append(",").append(md5Encrypt(macSeries));
                    uuid = md5Encrypt(androidId + seriesId + deviceId + macSeries);
                    IMLogger.d("Current uuid = " + uuid);
                }
            }
        }
        return uuid;
    }

    private String getMacAfterAndroidM(String hardware) throws SocketException {
        byte[] mac;
        String sMAC = "";
        NetworkInterface ni = NetworkInterface.getByName(hardware);
        if (!(ni == null || (mac = ni.getHardwareAddress()) == null)) {
            Formatter formatter = new Formatter();
            int i = 0;
            while (i < mac.length) {
                Locale locale = Locale.getDefault();
                Object[] objArr = new Object[2];
                objArr[0] = Byte.valueOf(mac[i]);
                objArr[1] = i < mac.length + -1 ? ":" : "";
                sMAC = formatter.format(locale, "%02X%s", objArr).toString();
                i++;
            }
        }
        return sMAC;
    }

    private String getMacAfterM() {
        String macStr = "";
        try {
            WifiInfo wifiInfo = ((WifiManager) mContext.getSystemService("wifi")).getConnectionInfo();
            if (wifiInfo.getMacAddress() != null) {
                macStr = wifiInfo.getMacAddress();
                if (DEFAULT_MAC_ADDRESS.equals(macStr)) {
                    macStr = getMacAfterAndroidM("wlan0");
                    if (T.ckIsEmpty(macStr)) {
                        macStr = getMacAfterAndroidM("eth0");
                    }
                }
            } else {
                macStr = getMacAfterAndroidM("eth0");
            }
        } catch (SecurityException ex) {
            IMLogger.w("Need ACCESS_WIFI_STATE permission : " + ex.getMessage());
        } catch (NullPointerException ex2) {
            IMLogger.w(ex2.getMessage());
        } catch (SocketException e) {
            IMLogger.w(e.getMessage());
        }
        return macStr == null ? "" : macStr;
    }

    private String getMac() {
        if (MetaDataUtil.readMetaDataFromApplication(mContext, IMSDK_MAC_MARK, false)) {
            return getMacAfterM();
        }
        try {
            WifiInfo wifiInfo = ((WifiManager) mContext.getSystemService("wifi")).getConnectionInfo();
            if (wifiInfo.getMacAddress() != null) {
                return wifiInfo.getMacAddress();
            }
            return Constants.NULL_VERSION_ID;
        } catch (SecurityException e) {
            IMLogger.w("iMSDK guest need android.permission.ACCESS_WIFI_STATE to get device mac address");
            return "";
        }
    }

    public String getPrefsDeviceId() {
        return mContext.getSharedPreferences(PREFS_FILE, 0).getString(PREFS_COMBINE_UNIQUE_ID, (String) null);
    }

    public void storePrefsDeviceId() {
        mContext.getSharedPreferences(PREFS_FILE, 0).edit().putString(PREFS_COMBINE_UNIQUE_ID, uuid).commit();
    }

    @Deprecated
    public String getOldUUID() {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_FILE, 0);
        String deviceId = prefs.getString("device_id", (String) null);
        String androidId = prefs.getString(PREFS_ANDROID_ID, (String) null);
        String seriesId = prefs.getString(PREFS_SERIES_ID, (String) null);
        String autoGenerateId = prefs.getString(PREFS_AUTO_GENERATE_ID, (String) null);
        if (deviceId == null && androidId == null && seriesId == null && autoGenerateId == null) {
            return null;
        }
        deviceIDsCache = new StringBuffer(1024);
        deviceIDsCache.append(seriesId).append(",").append(deviceId).append(",").append(androidId).append(",").append(autoGenerateId);
        IMLogger.d("Read Data : " + deviceIDsCache.toString());
        boolean isPotentialTargetHit = false;
        String potentialRetUuid = null;
        String uuid2 = Build.SERIAL;
        if (uuid2 != null) {
            isPotentialTargetHit = true;
            potentialRetUuid = uuid2;
            if (seriesId != null && md5Encrypt(uuid2).equals(seriesId)) {
                return seriesId;
            }
        }
        IMLogger.d("series Id = " + uuid2 + ", isPotentialTargetHit = " + isPotentialTargetHit + ", potentialRetUuid = " + potentialRetUuid);
        String uuid3 = getDeviceId(mContext);
        if (uuid3 != null) {
            isPotentialTargetHit = true;
            if (potentialRetUuid == null) {
                potentialRetUuid = uuid3;
            }
            if (deviceId != null && md5Encrypt(uuid3).equals(deviceId)) {
                if (seriesId == null) {
                    return deviceId;
                }
                return seriesId;
            }
        }
        IMLogger.d("device Id = " + uuid3 + ", isPotentialTargetHit = " + isPotentialTargetHit + ", potentialRetUuid = " + potentialRetUuid);
        String uuid4 = getAndroidId(mContext);
        if (uuid4 != null) {
            isPotentialTargetHit = true;
            if (potentialRetUuid == null) {
                potentialRetUuid = uuid4;
            }
            if (androidId != null && md5Encrypt(uuid4).equals(androidId)) {
                if (seriesId != null) {
                    return seriesId;
                }
                if (deviceId != null) {
                    return deviceId;
                }
                return androidId;
            }
        }
        IMLogger.d("android Id = " + uuid4 + ", isPotentialTargetHit = " + isPotentialTargetHit + ", potentialRetUuid = " + potentialRetUuid);
        if (isPotentialTargetHit) {
            return md5Encrypt(potentialRetUuid);
        }
        IMLogger.d("Read Data , AutoGenerateID = " + autoGenerateId);
        return autoGenerateId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDeviceIDs() {
        String stringBuffer;
        synchronized (mLock) {
            if (deviceIDsCache != null) {
                stringBuffer = deviceIDsCache.toString();
            } else {
                SharedPreferences prefs = mContext.getSharedPreferences(PREFS_FILE, 0);
                String deviceId = prefs.getString("device_id", (String) null);
                String androidId = prefs.getString(PREFS_ANDROID_ID, (String) null);
                String seriesId = prefs.getString(PREFS_SERIES_ID, (String) null);
                deviceIDsCache = new StringBuffer(1024);
                deviceIDsCache.append(seriesId).append(",").append(deviceId).append(",").append(androidId).append(",").append(md5Encrypt(getMac()));
                stringBuffer = deviceIDsCache.toString();
            }
        }
        return stringBuffer;
    }

    @SuppressLint({"NewApi"})
    public String getSerial() {
        if (Build.VERSION.SDK_INT <= 26) {
            return Build.SERIAL;
        }
        try {
            return Build.getSerial();
        } catch (Exception e) {
            IMLogger.w("get serial failed : " + e.getMessage());
            return "";
        }
    }

    public String getDeviceExtra() {
        String str;
        synchronized (mLock) {
            if (T.ckIsEmpty(mExtra)) {
                StringBuilder sb = new StringBuilder();
                sb.append(getAndroidId(mContext)).append(DECORATE_SYMBOL).append(getSerial()).append(DECORATE_SYMBOL).append(getDeviceId(mContext)).append(DECORATE_SYMBOL).append(getMac()).append(DECORATE_SYMBOL).append(Build.FINGERPRINT).append(DECORATE_SYMBOL).append(Build.HOST).append(DECORATE_SYMBOL).append(Build.getRadioVersion()).append(DECORATE_SYMBOL).append(Build.HARDWARE).append(DECORATE_SYMBOL).append(getGoogleAdId());
                mExtra = sb.toString();
                IMLogger.d("device extra : " + mExtra);
            }
            str = mExtra;
        }
        return str;
    }

    public void syncGoogleAdId() {
        IMLogger.d("get google ad id ...");
        getGoogleAdId();
    }

    private String getGoogleAdId() {
        String str;
        synchronized (mLock) {
            if (!T.ckIsEmpty(mGoogleAdId)) {
                str = mGoogleAdId;
            } else {
                try {
                    getGoogleAdIdAnsyc(mContext, new IMListener() {
                        public void onNotify(String result) {
                            IMLogger.d("Current google ad id = " + result);
                            String unused = DeviceUuidFactory.mGoogleAdId = result;
                        }
                    });
                } catch (Exception e) {
                    IMLogger.d("get google ad id error : " + e.getClass().getName() + ", " + e.getMessage());
                    mGoogleAdId = "";
                }
                str = mGoogleAdId;
            }
        }
        return str;
    }

    private void getGoogleAdIdAnsyc(final Context context, final IMListener listener) {
        synchronized (mLock) {
            if (T.ckIsEmpty(mGoogleAdId)) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Class cls = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
                            Object instance = cls.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
                            Object info = cls.getDeclaredMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(instance, new Object[]{context});
                            Object id = info.getClass().getMethod("getId", new Class[0]).invoke(info, new Object[0]);
                            if (id != null) {
                                listener.onNotify(id.toString());
                            } else {
                                listener.onNotify("");
                            }
                        } catch (Exception e) {
                            IMLogger.d("get google ad id failed : " + e.getClass().getName() + ", " + e.getMessage());
                            listener.onNotify("");
                        }
                    }
                }).start();
            } else {
                listener.onNotify(mGoogleAdId);
            }
        }
    }
}
