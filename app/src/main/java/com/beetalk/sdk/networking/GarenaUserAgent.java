package com.beetalk.sdk.networking;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.LocaleHelper;

public class GarenaUserAgent {
    public static final String HEADER_KEY = "user-agent";
    private static volatile String MODEL_POSTFIX = "";
    private static final String TITLE = "GarenaMSDK";
    private String mAndroidVersion;
    private String mDeviceName;
    private String mExtra;
    private String mLanguage;
    private String mRegion;
    private String mSDKVersion;

    public static void init(Context context) {
        if (context != null && ((TelephonyManager) context.getSystemService("phone")).getPhoneType() == 0) {
            MODEL_POSTFIX = "tablet";
        }
    }

    public GarenaUserAgent() {
        this((String) null);
    }

    public GarenaUserAgent(String extra) {
        this.mDeviceName = "";
        this.mAndroidVersion = "";
        this.mSDKVersion = "";
        this.mLanguage = "";
        this.mRegion = "";
        this.mExtra = "";
        generateAgent(extra);
    }

    private void generateAgent(String extra) {
        try {
            this.mSDKVersion = format(SDKConstants.VERSION.VERSION_NAME);
            this.mDeviceName = TextUtils.isEmpty(Build.MODEL) ? MODEL_POSTFIX : Build.MODEL + " " + MODEL_POSTFIX;
            this.mAndroidVersion = TextUtils.isEmpty(Build.VERSION.RELEASE) ? "Android" : "Android " + Build.VERSION.RELEASE;
            this.mLanguage = format(LocaleHelper.getDefaultDeviceLanguage());
            this.mRegion = format(LocaleHelper.getDefaultDeviceCountry());
            this.mExtra = format(extra);
        } catch (Exception e) {
            BBLogger.e(e);
        }
    }

    private String format(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }

    public String toString() {
        return String.format("%s/%s(%s;%s;%s;%s;%s)", new Object[]{TITLE, this.mSDKVersion, this.mDeviceName, this.mAndroidVersion, this.mLanguage, this.mRegion, this.mExtra});
    }
}
