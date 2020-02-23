package com.tencent.qqgamemi.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import java.util.Locale;

public class LanguageUtil {
    private static LocaleList sLocaleList = LocaleList.getDefault();

    static {
        if (Build.VERSION.SDK_INT >= 24) {
        }
    }

    public static boolean isSetValue(Context context, Locale locale) {
        Locale currentLocale;
        Configuration config = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= 24) {
            currentLocale = config.getLocales().get(0);
        } else {
            currentLocale = config.locale;
        }
        return currentLocale.equals(locale);
    }

    public static Locale getLocalePerfance() {
        return Locale.TRADITIONAL_CHINESE;
    }

    public static Locale createLocale(String language, String country) {
        if (language == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 21) {
            return country != null ? new Locale(language, country) : new Locale(language);
        }
        Locale.Builder builder = new Locale.Builder().setLanguage(language);
        if (country != null) {
            builder.setRegion(country);
        }
        return builder.build();
    }

    public static void setLocale(Context context, Locale locale) {
        if (!isSetValue(context, locale)) {
            Resources resources = context.getResources();
            Configuration configuration = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            if (Build.VERSION.SDK_INT >= 24) {
                LocaleList localeList = new LocaleList(new Locale[]{locale});
                configuration.setLocale(locale);
                configuration.setLocales(localeList);
                LocaleList.setDefault(localeList);
            } else if (Build.VERSION.SDK_INT >= 17) {
                configuration.setLocale(locale);
            } else {
                configuration.locale = locale;
            }
            resources.updateConfiguration(configuration, dm);
        }
    }
}
