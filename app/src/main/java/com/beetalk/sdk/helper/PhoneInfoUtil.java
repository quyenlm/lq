package com.beetalk.sdk.helper;

import android.content.Context;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.garena.msdk.R;
import java.util.HashMap;
import java.util.TimeZone;

public class PhoneInfoUtil {
    private static volatile PhoneInfoUtil mInstance;
    private HashMap<String, String> mTimeZoneMap;

    public static PhoneInfoUtil getInstance() {
        if (mInstance == null) {
            synchronized (PhoneInfoUtil.class) {
                if (mInstance == null) {
                    mInstance = new PhoneInfoUtil();
                }
            }
        }
        return mInstance;
    }

    private HashMap<String, String> getTimeZoneMap(Context context) {
        if (this.mTimeZoneMap == null) {
            String[] raw = context.getResources().getStringArray(R.array.regions);
            this.mTimeZoneMap = new HashMap<>();
            for (String item : raw) {
                String[] tokens = item.split(":");
                this.mTimeZoneMap.put(tokens[0], tokens[1]);
            }
        }
        return this.mTimeZoneMap;
    }

    private String getCountryISOByTimeZone(Context context) {
        if (context == null) {
            return "";
        }
        String[] zone = TimeZone.getDefault().getID().toLowerCase().split(Constants.URL_PATH_DELIMITER);
        if (zone.length == 2) {
            return getTimeZoneMap(context).get(zone[1]);
        }
        return "";
    }

    public String getPhoneCountryISO(Context context) {
        if (context == null) {
            return "";
        }
        String phoneCountry = LocaleHelper.getPhoneDeviceCountry(context);
        if (!TextUtils.isEmpty(phoneCountry)) {
            return phoneCountry;
        }
        return getCountryISOByTimeZone(context);
    }
}
