package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

public final class zzaph {
    private static final String[] zzajo = {"text1", "text2", "icon", "intent_action", "intent_data", "intent_data_id", "intent_extra_data", "suggest_large_icon", "intent_activity", "thing_proto"};
    private static final Map<String, Integer> zzajp = new HashMap(zzajo.length);

    static {
        for (int i = 0; i < zzajo.length; i++) {
            zzajp.put(zzajo[i], Integer.valueOf(i));
        }
    }

    public static String zzQ(int i) {
        if (i < 0 || i >= zzajo.length) {
            return null;
        }
        return zzajo[i];
    }

    public static int zzbH(String str) {
        Integer num = zzajp.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 44).append("[").append(str).append("] is not a valid global search section name").toString());
    }

    public static int zzmk() {
        return zzajo.length;
    }
}
