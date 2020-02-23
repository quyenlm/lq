package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzayv {
    private static final zzayo zzapq = new zzayo("MetadataUtils");
    private static final String[] zzayU = {"Z", "+hh", "+hhmm", "+hh:mm"};
    private static final String zzayV;

    static {
        String valueOf = String.valueOf("yyyyMMdd'T'HHmmss");
        String valueOf2 = String.valueOf(zzayU[0]);
        zzayV = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public static String zza(Calendar calendar) {
        if (calendar == null) {
            zzapq.zzb("Calendar object cannot be null", new Object[0]);
            return null;
        }
        String str = zzayV;
        if (calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0) {
            str = "yyyyMMdd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        String format = simpleDateFormat.format(calendar.getTime());
        return format.endsWith("+0000") ? format.replace("+0000", zzayU[0]) : format;
    }

    public static void zza(List<WebImage> list, JSONObject jSONObject) {
        try {
            list.clear();
            JSONArray jSONArray = jSONObject.getJSONArray("images");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    list.add(new WebImage(jSONArray.getJSONObject(i)));
                } catch (IllegalArgumentException e) {
                }
            }
        } catch (JSONException e2) {
        }
    }

    public static void zza(JSONObject jSONObject, List<WebImage> list) {
        if (list != null && !list.isEmpty()) {
            JSONArray jSONArray = new JSONArray();
            for (WebImage json : list) {
                jSONArray.put(json.toJson());
            }
            try {
                jSONObject.put("images", jSONArray);
            } catch (JSONException e) {
            }
        }
    }

    public static Calendar zzco(String str) {
        if (TextUtils.isEmpty(str)) {
            zzapq.zzb("Input string is empty or null", new Object[0]);
            return null;
        }
        String zzcp = zzcp(str);
        if (TextUtils.isEmpty(zzcp)) {
            zzapq.zzb("Invalid date format", new Object[0]);
            return null;
        }
        String zzcq = zzcq(str);
        String str2 = "yyyyMMdd";
        if (!TextUtils.isEmpty(zzcq)) {
            String valueOf = String.valueOf(zzcp);
            zzcp = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(zzcq).length()).append(valueOf).append("T").append(zzcq).toString();
            str2 = zzcq.length() == 6 ? "yyyyMMdd'T'HHmmss" : zzayV;
        }
        Calendar instance = GregorianCalendar.getInstance();
        try {
            instance.setTime(new SimpleDateFormat(str2).parse(zzcp));
            return instance;
        } catch (ParseException e) {
            zzapq.zzb("Error parsing string: %s", e.getMessage());
            return null;
        }
    }

    private static String zzcp(String str) {
        if (TextUtils.isEmpty(str)) {
            zzapq.zzb("Input string is empty or null", new Object[0]);
            return null;
        }
        try {
            return str.substring(0, 8);
        } catch (IndexOutOfBoundsException e) {
            zzapq.zze("Error extracting the date: %s", e.getMessage());
            return null;
        }
    }

    private static String zzcq(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            zzapq.zzb("string is empty or null", new Object[0]);
            return null;
        }
        int indexOf = str.indexOf(84);
        int i = indexOf + 1;
        if (indexOf != 8) {
            zzapq.zzb("T delimeter is not found", new Object[0]);
            return null;
        }
        try {
            String substring = str.substring(i);
            if (substring.length() == 6) {
                return substring;
            }
            switch (substring.charAt(6)) {
                case '+':
                case '-':
                    int length = substring.length();
                    if (length == zzayU[1].length() + 6 || length == zzayU[2].length() + 6 || length == zzayU[3].length() + 6) {
                        z = true;
                    }
                    if (z) {
                        return substring.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)", "$1$2");
                    }
                    return null;
                case 'Z':
                    if (substring.length() != zzayU[0].length() + 6) {
                        return null;
                    }
                    String valueOf = String.valueOf(substring.substring(0, substring.length() - 1));
                    String valueOf2 = String.valueOf("+0000");
                    return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                default:
                    return null;
            }
        } catch (IndexOutOfBoundsException e) {
            zzapq.zzb("Error extracting the time substring: %s", e.getMessage());
            return null;
        }
    }
}
