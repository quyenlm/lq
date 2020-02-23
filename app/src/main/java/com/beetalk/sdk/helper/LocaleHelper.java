package com.beetalk.sdk.helper;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import java.util.Locale;

public class LocaleHelper {
    static String DEFAULT_COUNTRY = "SG";
    static String DEFAULT_LANGUAGE = "en";
    private static final String LANGUAGE_CODE_INDONESIAN_NEW = "id";
    private static final String LANGUAGE_CODE_INDONESIAN_OLD = "in";

    public static String getDefaultDeviceCountry(Context context) {
        String phoneCountry = PhoneInfoUtil.getInstance().getPhoneCountryISO(context);
        if (TextUtils.isEmpty(phoneCountry)) {
            return getDefaultDeviceCountry();
        }
        return phoneCountry;
    }

    public static String getDefaultDeviceCountry() {
        String country = Locale.getDefault() != null ? Locale.getDefault().getCountry() : DEFAULT_COUNTRY;
        if (country != null && country.length() > 2) {
            country = country.substring(0, 2);
        }
        if (country == null || country.length() != 2) {
            return DEFAULT_COUNTRY;
        }
        return country.toUpperCase();
    }

    public static String getDefaultDeviceLanguage() {
        String language = Locale.getDefault() != null ? Locale.getDefault().getLanguage() : DEFAULT_LANGUAGE;
        if (language != null && language.length() > 2) {
            language = language.substring(0, 2);
        }
        if (language == null || language.length() != 2) {
            return DEFAULT_COUNTRY;
        }
        return language.toLowerCase();
    }

    public static String getPhoneDeviceCountry(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
        if (tMgr != null) {
            return tMgr.getSimCountryIso().toUpperCase();
        }
        return "";
    }

    public static Locale getDefaultLocale(Context context) {
        return new Locale(getDefaultDeviceLanguage(), getDefaultDeviceCountry(context));
    }

    public static Locale getDefaultLocale() {
        if (GGLoginSession.getApplicationContext() != null) {
            return getDefaultLocale(GGLoginSession.getApplicationContext());
        }
        return new Locale(getDefaultDeviceLanguage(), getDefaultDeviceCountry());
    }

    public static String getLocaleStringForServer(Locale locale) {
        String string = locale.toString();
        if (locale.getLanguage().equals(LANGUAGE_CODE_INDONESIAN_OLD)) {
            return string.replace(LANGUAGE_CODE_INDONESIAN_OLD, "id");
        }
        return string;
    }
}
