package com.facebook.appevents.internal;

import android.os.Looper;
import com.facebook.internal.Utility;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppEventUtility {
    private static final String regex = "[-+]*\\d+([\\,\\.]\\d+)*([\\.\\,]\\d+)?";

    public static void assertIsNotMainThread() {
    }

    public static void assertIsMainThread() {
    }

    public static double normalizePrice(String value) {
        try {
            Matcher matcher = Pattern.compile(regex, 8).matcher(value);
            if (!matcher.find()) {
                return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            }
            return NumberFormat.getNumberInstance(Utility.getCurrentLocale()).parse(matcher.group(0)).doubleValue();
        } catch (ParseException e) {
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
    }

    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
