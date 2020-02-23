package com.tencent.kgvmp.e;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class b {

    public enum a {
        PATTERN1("yyyy_MM_dd_HH_mm_ss"),
        PATTERN2("yyyy-MM-dd HH:mm:ss");
        
        private String format;

        private a(String str) {
            this.format = str;
        }

        public String getFormat() {
            return this.format;
        }
    }

    public static String a() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String a(String str) {
        return new SimpleDateFormat(str, Locale.CHINA).format(new Date());
    }
}
