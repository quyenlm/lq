package com.tencent.component.utils;

import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.facebook.internal.AnalyticsEvents;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.component.debug.FileTracerConfig;
import com.tencent.qqgamemi.util.TimeUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final String _BR = "<br/>";
    private static final ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat(FileTracerConfig.DEF_FOLDER_FORMAT);
        }
    };
    private static final Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    public static String subString(String str, int length) throws Exception {
        byte[] bytes = str.getBytes("Unicode");
        int n = 0;
        int i = 2;
        while (i < bytes.length && n < length) {
            if (i % 2 == 1) {
                n++;
            } else if (bytes[i] != 0) {
                n++;
            }
            i++;
        }
        if (i % 2 == 1) {
            if (bytes[i - 1] != 0) {
                i--;
            } else {
                i++;
            }
        }
        return new String(bytes, 0, i, "Unicode");
    }

    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = ' ';
            } else if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static long calculateWeiboLength(CharSequence c) {
        double d;
        double len = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        for (int i = 0; i < c.length(); i++) {
            int temp = c.charAt(i);
            if (temp <= 0 || temp >= 127) {
                d = 1.0d;
            } else {
                d = 0.5d;
            }
            len += d;
        }
        return Math.round(len);
    }

    public static String[] split(String str, String splitsign) {
        if (str == null || splitsign == null) {
            return null;
        }
        ArrayList<String> al = new ArrayList<>();
        while (true) {
            int index = str.indexOf(splitsign);
            if (index != -1) {
                al.add(str.substring(0, index));
                str = str.substring(splitsign.length() + index);
            } else {
                al.add(str);
                return (String[]) al.toArray(new String[0]);
            }
        }
    }

    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null) {
            return null;
        }
        StringBuffer bf = new StringBuffer("");
        while (true) {
            int index = source.indexOf(from);
            if (index != -1) {
                bf.append(source.substring(0, index) + to);
                source = source.substring(from.length() + index);
                int index2 = source.indexOf(from);
            } else {
                bf.append(source);
                return bf.toString();
            }
        }
    }

    public static String htmlencode(String str) {
        if (str == null) {
            return null;
        }
        return replace("\"", "&quot;", replace("<", "&lt;", str));
    }

    public static String htmldecode(String str) {
        if (str == null) {
            return null;
        }
        return replace("&quot;", "\"", replace("&lt;", "<", str));
    }

    public static String htmlshow(String str) {
        if (str == null) {
            return null;
        }
        return replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", replace("\n", _BR, replace("\r\n", _BR, replace(" ", "&nbsp;", replace("<", "&lt;", str)))));
    }

    public static String toLength(String str, int length) {
        if (str == null) {
            return null;
        }
        if (length <= 0) {
            return "";
        }
        try {
            if (str.getBytes("GBK").length <= length) {
                return str;
            }
        } catch (Exception e) {
        }
        StringBuffer buff = new StringBuffer();
        int index = 0;
        int length2 = length - 3;
        while (length2 > 0) {
            char c = str.charAt(index);
            if (c >= 128) {
                length2--;
            }
            length2--;
            buff.append(c);
            index++;
        }
        buff.append("...");
        return buff.toString();
    }

    public static String getUrlFileName(String urlString) {
        String fileName = urlString.substring(urlString.lastIndexOf(Constants.URL_PATH_DELIMITER));
        String fileName2 = fileName.substring(1, fileName.length());
        if (!fileName2.equalsIgnoreCase("")) {
            return fileName2;
        }
        Calendar c = Calendar.getInstance();
        return c.get(1) + "" + c.get(2) + "" + c.get(5) + "" + c.get(12);
    }

    public static String replaceSomeString(String str) {
        if (str == null) {
            return "";
        }
        try {
            return str.replaceAll("\r", "").replaceAll("&gt;", ">").replaceAll("&ldquo;", "“").replaceAll("&rdquo;", "”").replaceAll("&#39;", "'").replaceAll("&nbsp;", "").replaceAll("<br\\s*/>", "\n").replaceAll("&quot;", "\"").replaceAll("&lt;", "<").replaceAll("&lsquo;", "《").replaceAll("&rsquo;", "》").replaceAll("&middot;", "·").replace("&mdash;", "—").replace("&hellip;", "…").replace("&amp;", "×").replaceAll("\\s*", "").trim().replaceAll("<p>", "\n      ").replaceAll("</p>", "").replaceAll("<div.*?>", "\n      ").replaceAll("</div>", "");
        } catch (Exception e) {
            return "";
        }
    }

    public static String delHTMLTag(String htmlStr) {
        Log.v("htmlStr", htmlStr);
        try {
            return Pattern.compile("<[^>]+>", 2).matcher(Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>", 2).matcher(Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>", 2).matcher(htmlStr).replaceAll("")).replaceAll("")).replaceAll("");
        } catch (Exception e) {
            return htmlStr;
        }
    }

    public static String delSpace(String str) {
        if (str != null) {
            return str.replaceAll("\r", "").replaceAll("\n", "").replace(" ", "");
        }
        return str;
    }

    public static boolean isNotNull(String str) {
        return str != null && !"".equalsIgnoreCase(str.trim());
    }

    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        Calendar cal = Calendar.getInstance();
        if (dateFormater2.get().format(cal.getTime()).equals(dateFormater2.get().format(time))) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                return Math.max((cal.getTimeInMillis() - time.getTime()) / com.tencent.imsdk.expansion.downloader.Constants.WATCHDOG_WAKE_TIMER, 1) + "分钟前";
            }
            return hour + "小时前";
        }
        int days = (int) ((cal.getTimeInMillis() / TimeUtils.MILLIS_IN_DAY) - (time.getTime() / TimeUtils.MILLIS_IN_DAY));
        if (days == 0) {
            int hour2 = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour2 == 0) {
                return Math.max((cal.getTimeInMillis() - time.getTime()) / com.tencent.imsdk.expansion.downloader.Constants.WATCHDOG_WAKE_TIMER, 1) + "分钟前";
            }
            return hour2 + "小时前";
        } else if (days == 1) {
            return "昨天";
        } else {
            if (days == 2) {
                return "前天";
            }
            if (days > 2 && days <= 10) {
                return days + "天前";
            }
            if (days > 10) {
                return dateFormater2.get().format(time);
            }
            return "";
        }
    }

    public static String trimmy(String str) {
        if (str != null) {
            return str.replaceAll("-", "").replaceAll("\\+", "");
        }
        return "";
    }

    public static String replaceBlank(String str) {
        if (str != null) {
            return Pattern.compile("\r").matcher(str).replaceAll("");
        }
        return "";
    }

    public static boolean isToday(String sdate) {
        Date time = toDate(sdate);
        Date today = new Date();
        if (time == null || !dateFormater2.get().format(today).equals(dateFormater2.get().format(time))) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != 9 && c != 13 && c != 10) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        return emailer.matcher(email).matches();
    }

    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isHandset(String handset) {
        try {
            if (handset.substring(0, 1).equals("1") && handset != null && handset.length() == 11 && Pattern.compile("^[0123456789]+$").matcher(handset).matches()) {
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public static boolean isChinese(String str) {
        return Pattern.compile("[Α-￥]+$").matcher(str).matches();
    }

    public static boolean isNumeric(String str) {
        if (!Pattern.compile("[0-9]*").matcher(str).matches()) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String str) {
        return Pattern.compile("^[-\\+]?[\\d]*$").matcher(str).matches();
    }

    public static boolean isDouble(String str) {
        return Pattern.compile("^[-\\+]?[.\\d]*$").matcher(str).matches();
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isLenghtStrLentht(String text, int lenght) {
        if (text.length() <= lenght) {
            return true;
        }
        return false;
    }

    public static boolean isSMSStrLentht(String text) {
        if (text.length() <= 70) {
            return true;
        }
        return false;
    }

    public static boolean checkEmail(String email) {
        if (Pattern.compile("^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$").matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isShareStrLentht(String text, int lenght) {
        if (text.length() <= 120) {
            return true;
        }
        return false;
    }

    public static String getFileNameFromUrl(String url) {
        String extName;
        int index = url.lastIndexOf(63);
        if (index > 1) {
            extName = url.substring(url.lastIndexOf(46) + 1, index);
        } else {
            extName = url.substring(url.lastIndexOf(46) + 1);
        }
        return hashKeyForDisk(url) + "." + extName;
    }

    public static String hashKeyForDisk(String key) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            return bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(key.hashCode());
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] getGBKBytes(String str) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes("GBK");
        } catch (Exception e) {
            try {
                return str.getBytes("gbk");
            } catch (Exception e2) {
                return str.getBytes();
            }
        }
    }

    public static boolean startsWithIgnoreCase(String source, String prefix) {
        if (source == prefix) {
            return true;
        }
        if (source == null || prefix == null) {
            return false;
        }
        return source.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static String removeEmptyLines(String content) {
        return TextUtils.isEmpty(content) ? content : Pattern.compile("[\\r,\\n]{2,}").matcher(content).replaceAll("\n");
    }

    public static boolean isLegalContent(String content) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        Matcher matcher = Pattern.compile("\\s*").matcher(content);
        if (matcher.matches()) {
            if (matcher.end() - matcher.start() < content.length()) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static boolean isNetworkUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        if (isHttpUrl(url) || isHttpsUrl(url)) {
            return true;
        }
        return false;
    }

    public static boolean isHttpUrl(String url) {
        if (url == null || url.length() <= 6 || !url.substring(0, 7).equalsIgnoreCase("http://")) {
            return false;
        }
        return true;
    }

    public static boolean isHttpsUrl(String url) {
        if (url == null || url.length() <= 7 || !url.substring(0, 8).equalsIgnoreCase("https://")) {
            return false;
        }
        return true;
    }

    public static String toJsString(String value) {
        if (value == null) {
            return com.amazonaws.services.s3.internal.Constants.NULL_VERSION_ID;
        }
        StringBuilder out = new StringBuilder(1024);
        out.append("\"");
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            switch (c) {
                case 8:
                    out.append("\\b");
                    break;
                case 9:
                    out.append("\\t");
                    break;
                case 10:
                    out.append("\\n");
                    break;
                case 12:
                    out.append("\\f");
                    break;
                case 13:
                    out.append("\\r");
                    break;
                case '\"':
                case '/':
                case '\\':
                    out.append('\\').append(c);
                    break;
                default:
                    if (c > 31) {
                        out.append(c);
                        break;
                    } else {
                        out.append(String.format("\\u%04x", new Object[]{Integer.valueOf(c)}));
                        break;
                    }
            }
        }
        out.append("\"");
        return out.toString();
    }
}
