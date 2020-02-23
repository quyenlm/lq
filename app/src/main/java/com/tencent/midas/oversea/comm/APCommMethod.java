package com.tencent.midas.oversea.comm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.facebook.internal.AnalyticsEvents;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class APCommMethod {
    public static String MaptoString(HashMap<String, String> hashMap) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry next : hashMap.entrySet()) {
            stringBuffer.append((String) next.getKey());
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            stringBuffer.append((String) next.getValue());
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    public static String MaptoString(HashMap<String, String> hashMap, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> value : hashMap.entrySet()) {
            stringBuffer.append((String) value.getValue());
            stringBuffer.append(str);
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    public static String dealString(String str) {
        String rawString = rawString(str);
        return rawString.length() <= 3 ? str : (rawString.length() <= 3 || rawString.length() > 6) ? (rawString.length() <= 6 || rawString.length() > 9) ? (rawString.length() <= 9 || rawString.length() > 12) ? str : str.substring(6, 9) + " " + str.substring(10, str.length()) : str.substring(3, 6) + " " + str.substring(7, str.length()) : str.substring(0, 3) + " " + str.substring(4, str.length());
    }

    public static String fenToYuan(String str, int i, String str2) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        if (i == 0) {
            decimalFormat.applyPattern("0");
        } else if (i == 1) {
            decimalFormat.applyPattern("0.0");
        } else if (i == 2) {
            decimalFormat.applyPattern("0.00");
        }
        try {
            float floatValue = Float.valueOf(str).floatValue();
            return str2.equalsIgnoreCase("VND") ? decimalFormat.format((double) (floatValue / 100000.0f)) + "K" : decimalFormat.format((double) (floatValue / 100.0f));
        } catch (Exception e) {
            return "";
        }
    }

    public static int getAnimId(Context context, String str) {
        return context.getResources().getIdentifier(str, "anim", context.getApplicationContext().getPackageName());
    }

    public static String getApplicationPackageName() {
        try {
            APMidasPayAPI.singleton();
            PackageManager packageManager = APMidasPayAPI.applicationContext.getPackageManager();
            APMidasPayAPI.singleton();
            return packageManager.getPackageInfo(APMidasPayAPI.applicationContext.getPackageName(), 0).packageName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getApplicationVersion() {
        try {
            APMidasPayAPI.singleton();
            PackageManager packageManager = APMidasPayAPI.applicationContext.getPackageManager();
            APMidasPayAPI.singleton();
            return packageManager.getPackageInfo(APMidasPayAPI.applicationContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getColorId(Context context, String str) {
        return context.getResources().getColor(context.getResources().getIdentifier(str, "color", context.getPackageName()));
    }

    public static Drawable getDrawable(Context context, String str) {
        return context.getResources().getDrawable(context.getResources().getIdentifier(str, "drawable", context.getPackageName()));
    }

    public static int getDrawableId(Context context, String str) {
        return context.getResources().getIdentifier(str, "drawable", context.getPackageName());
    }

    public static int getId(Context context, String str) {
        return context.getResources().getIdentifier(str, "id", context.getPackageName());
    }

    public static int getLayoutId(Context context, String str) {
        return context.getResources().getIdentifier(str, "layout", context.getPackageName());
    }

    public static String getStringId(Context context, String str) {
        return context.getResources().getString(context.getResources().getIdentifier(str, "string", context.getPackageName()));
    }

    public static int getStyleId(Context context, String str) {
        return context.getResources().getIdentifier(str, AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, context.getPackageName());
    }

    public static String getVersion() {
        return APGlobalInfo.SDK_VERSION;
    }

    public static boolean isApplicationShowing(Context context) {
        int i;
        boolean z = false;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo next : runningAppProcesses) {
                if (next.processName.equals(context.getPackageName()) && ((i = next.importance) == 200 || i == 100)) {
                    z = true;
                }
                z = z;
            }
        }
        return z;
    }

    public static String rawString(String str) {
        return str.replace(" ", "");
    }

    public static void transformStrToList(String str, List<String> list) {
        int indexOf = str.indexOf("[");
        int indexOf2 = str.indexOf("]");
        list.clear();
        if (indexOf != -1 && indexOf2 != -1 && indexOf2 > indexOf) {
            String substring = str.substring(indexOf + 1, indexOf2);
            if (substring.length() != 0) {
                for (String add : substring.split(",")) {
                    list.add(add);
                }
            }
        }
    }

    public static void transformStrToMap(String str, TreeMap<String, String> treeMap) {
        int indexOf = str.indexOf("[");
        int indexOf2 = str.indexOf("]");
        if (indexOf != -1 && indexOf2 != -1 && indexOf2 > indexOf) {
            String substring = str.substring(indexOf + 1, indexOf2);
            if (substring.length() == 0) {
                treeMap.clear();
                return;
            }
            String[] split = substring.split(",");
            int length = split.length;
            if (length > 0 && length % 2 == 0) {
                treeMap.clear();
                for (int i = 0; i < length / 2; i++) {
                    treeMap.put(split[i * 2], split[(i * 2) + 1]);
                }
            }
        }
    }

    public static void transformStrToMpInfoList(String str, List<String> list, List<String> list2) {
        int indexOf = str.indexOf("[");
        int indexOf2 = str.indexOf("]");
        if (indexOf != -1 && indexOf2 != -1 && indexOf2 > indexOf) {
            String substring = str.substring(indexOf + 1, indexOf2);
            if (substring.length() == 0) {
                list.clear();
                list2.clear();
                return;
            }
            String[] split = substring.split(",");
            int length = split.length;
            if (length > 0 && length % 2 == 0) {
                list.clear();
                list2.clear();
                for (int i = 0; i < length / 2; i++) {
                    String str2 = split[i * 2];
                    String str3 = split[(i * 2) + 1];
                    list.add(str2);
                    list2.add(str3);
                }
            }
        }
    }
}
