package com.tencent.mna.b.a.b;

import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: LRModel */
public class c {
    private Map<String, Float> a;

    c() {
    }

    /* access modifiers changed from: package-private */
    public Map<String, Float> a(String str) {
        boolean z = false;
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        String[] split = str.split(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        int length = split.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = true;
                break;
            }
            String[] split2 = split[i].split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            if (split2.length != 2) {
                break;
            }
            try {
                concurrentHashMap.put(split2[0], Float.valueOf(Float.parseFloat(split2[1])));
                i++;
            } catch (NumberFormatException e) {
            }
        }
        if (!z) {
            concurrentHashMap.clear();
        }
        return concurrentHashMap;
    }

    private Map<String, Float> a(Map<String, Float> map, int i) {
        HashMap hashMap = new HashMap();
        for (Map.Entry next : map.entrySet()) {
            String str = (String) next.getKey();
            float floatValue = ((Float) next.getValue()).floatValue();
            int indexOf = str.indexOf("GNET_");
            if (i == 4 && indexOf == -1) {
                hashMap.put(str, Float.valueOf(floatValue));
            } else if (i <= 3 && indexOf == 0) {
                hashMap.put(str.substring(indexOf + "GNET_".length()), Float.valueOf(floatValue));
            }
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    public void a(Map<String, Float> map) {
        this.a = map;
    }

    private float a(int i) {
        float f = ((float) i) / 500.0f;
        if (f > 1.0f) {
            return 1.0f;
        }
        return f;
    }

    private float b(int i) {
        float f = ((float) i) / 999.0f;
        if (f > 1.0f) {
            return 1.0f;
        }
        return f;
    }

    private Map<String, String> a(String str, int i) {
        long j;
        HashMap hashMap = new HashMap();
        long parseLong = Long.parseLong(str);
        if (i == 1) {
            if (parseLong < 1 || parseLong > ((long) 65536)) {
                return hashMap;
            }
            j = -1;
        } else if (i == 2) {
            if (parseLong < 1 || parseLong > ((long) 268435455)) {
                return hashMap;
            }
            j = parseLong % ((long) 65536);
            parseLong /= (long) 65536;
        } else if (i != 3) {
            return hashMap;
        } else {
            if (parseLong < 1 || parseLong > ((long) 268435455)) {
                return hashMap;
            }
            j = parseLong % ((long) 256);
            parseLong /= (long) 256;
        }
        hashMap.put("pcell_node", Long.toString(parseLong));
        hashMap.put("pcell_cell", Long.toString(j));
        return hashMap;
    }

    private Map<String, Float> c(long j, Map<String, Integer> map, Map<String, String> map2, Map<String, Float> map3, Vector<Integer> vector, Vector<Integer> vector2) {
        HashMap hashMap = new HashMap(map2);
        hashMap.putAll(a(j));
        int intValue = map.get("first_net").intValue();
        hashMap.put("first_net", Integer.toString(intValue));
        hashMap.putAll(a(map2.get("pcell_basestation"), intValue));
        HashMap hashMap2 = new HashMap(b((Map<String, String>) hashMap));
        for (String str : new String[]{"jumpvalues_cdnping", "jumpvalues_bridge_ping", "jumpvalues_routeping"}) {
            hashMap2.put(str, Float.valueOf(b(map.get(str).intValue())));
        }
        hashMap2.putAll(b(new LinkedList(vector2), new LinkedList(vector)));
        return hashMap2;
    }

    public Map<String, Float> a(long j, Map<String, Integer> map, Map<String, String> map2, Map<String, Float> map3, Vector<Integer> vector, Vector<Integer> vector2) {
        if (map.get("first_net").intValue() == 4) {
            return d(j, map, map2, map3, vector, vector2);
        }
        return c(j, map, map2, map3, vector, vector2);
    }

    private Map<String, Float> d(long j, Map<String, Integer> map, Map<String, String> map2, Map<String, Float> map3, Vector<Integer> vector, Vector<Integer> vector2) {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap(map2);
        hashMap2.remove("wmac");
        String str = map2.get("wmac");
        if (c(str) < 0) {
            hashMap.clear();
            return hashMap;
        }
        hashMap2.putAll(d(str));
        hashMap2.putAll(a(j));
        hashMap2.put("jumpvalues_channel_id", Integer.toString(map.get("jumpvalues_channel_id").intValue()));
        hashMap2.put("first_net", Integer.toString(map.get("first_net").intValue()));
        hashMap.putAll(b((Map<String, String>) hashMap2));
        hashMap.putAll(map3);
        hashMap.put("jumpvalues_link_speed", Float.valueOf(a(((Float) hashMap.get("jumpvalues_link_speed")).floatValue())));
        String[] strArr = {"jumpvalues_channel_id", "first_net"};
        String[] strArr2 = {"jumpvalues_cdnping", "jumpvalues_bridge_ping", "jumpvalues_routeping"};
        for (String str2 : strArr2) {
            hashMap.put(str2, Float.valueOf(b(map.get(str2).intValue())));
        }
        String[] strArr3 = {"jumpvalues_current_AP_num", "jumpvalues_neighbour_AP_num", "jumpvalues_search_AP_num"};
        for (String str3 : strArr3) {
            hashMap.put(str3, Float.valueOf(a(map.get(str3).intValue())));
        }
        HashSet hashSet = new HashSet(map.keySet());
        for (String remove : strArr) {
            hashSet.remove(remove);
        }
        for (String remove2 : strArr2) {
            hashSet.remove(remove2);
        }
        for (String remove3 : strArr3) {
            hashSet.remove(remove3);
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            String str4 = (String) it.next();
            hashMap.put(str4, Float.valueOf((float) map.get(str4).intValue()));
        }
        hashMap.putAll(b(new LinkedList(vector2), new LinkedList(vector)));
        return hashMap;
    }

    public double b(long j, Map<String, Integer> map, Map<String, String> map2, Map<String, Float> map3, Vector<Integer> vector, Vector<Integer> vector2) {
        float f;
        if (!map.containsKey("first_net")) {
            return -3001.0d;
        }
        Map<String, Float> a2 = a(j, map, map2, map3, vector, vector2);
        Map<String, Float> a3 = a(this.a, map.get("first_net").intValue());
        if (a3.size() == 0) {
            return -3002.0d;
        }
        if (a2.size() == 0) {
            return -3001.0d;
        }
        float f2 = 0.0f;
        Iterator<Map.Entry<String, Float>> it = a3.entrySet().iterator();
        while (true) {
            f = f2;
            if (!it.hasNext()) {
                break;
            }
            Map.Entry next = it.next();
            String str = (String) next.getKey();
            float floatValue = ((Float) next.getValue()).floatValue();
            if (a2.containsKey(str)) {
                f2 = (a2.get(str).floatValue() * floatValue) + f;
            } else {
                f2 = f;
            }
        }
        if (a3.containsKey("intercept")) {
            f += a3.get("intercept").floatValue();
        }
        return 1.0d / (Math.exp((double) (-f)) + 1.0d);
    }

    private float a(float f) {
        return f / 1200.0f;
    }

    private Map<String, Float> b(Map<String, String> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry next : map.entrySet()) {
            hashMap.put(b(((String) next.getKey()) + "||" + ((String) next.getValue()).toUpperCase()), Float.valueOf(1.0f));
        }
        return hashMap;
    }

    public String b(String str) {
        return str.replace('.', ':');
    }

    private Map<String, Float> b(List<Integer> list, List<Integer> list2) {
        float f;
        HashMap hashMap = new HashMap();
        Map<String, Float> g = g(list);
        Map<String, Float> g2 = g(list2);
        for (Map.Entry next : g.entrySet()) {
            String str = (String) next.getKey();
            float floatValue = ((Float) next.getValue()).floatValue();
            float floatValue2 = g2.get(str).floatValue();
            hashMap.put("sforward_" + str, Float.valueOf(floatValue));
            hashMap.put("sdirect_" + str, Float.valueOf(floatValue2));
            hashMap.put("m_o_" + str, Float.valueOf(floatValue - floatValue2));
            if (floatValue2 != 0.0f) {
                f = floatValue / floatValue2;
            } else {
                f = 0.0f;
            }
            hashMap.put("d_" + str, Float.valueOf(f));
        }
        int i = 21;
        if (list.size() < 21) {
            i = list.size();
        }
        if (list2.size() < i) {
            i = list2.size();
        }
        hashMap.put("cos_distance", Float.valueOf(a(list.subList(0, i), list.subList(0, i))));
        return hashMap;
    }

    private Map<String, Float> g(List<Integer> list) {
        HashMap hashMap = new HashMap();
        hashMap.put("mean", Float.valueOf(a(list) / 999.0f));
        hashMap.put("pingtimeout", Float.valueOf(d(list)));
        hashMap.put("amin", Float.valueOf(c(list) / 999.0f));
        hashMap.put("size", Float.valueOf(b(list) / 1200.0f));
        hashMap.put("std", Float.valueOf(e(list) / 999.0f));
        hashMap.put("score_series", Float.valueOf(f(list)));
        return hashMap;
    }

    private int c(String str) {
        if (str.length() != 17) {
            return -2001;
        }
        return 1;
    }

    private Map<String, String> d(String str) {
        String substring = str.substring(0, 8);
        String substring2 = str.substring(9);
        HashMap hashMap = new HashMap();
        hashMap.put("wmac_OUI", substring);
        hashMap.put("wmac_net_id", substring2);
        return hashMap;
    }

    public Map<String, String> a(long j) {
        HashMap hashMap = new HashMap();
        Date date = new Date(j);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(11);
        int i2 = instance.get(7);
        hashMap.put("origtime_hour_index", Integer.toString(i));
        hashMap.put("origtime_day_index", Integer.toString(i2));
        hashMap.put("origtime_minute_index", Integer.toString(instance.get(12) / 10));
        return hashMap;
    }

    public static float a(List<Integer> list, List<Integer> list2) {
        if (list.size() != list2.size()) {
            return 0.0f;
        }
        int size = list.size();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (int i = 0; i < size; i++) {
            float intValue = (float) list.get(i).intValue();
            float intValue2 = (float) list2.get(i).intValue();
            f3 += intValue * intValue2;
            f2 += intValue * intValue;
            f += intValue2 * intValue2;
        }
        if (0.0f == f || 0.0f == f2) {
            return 0.0f;
        }
        return f3 / ((float) (Math.sqrt((double) f2) * Math.sqrt((double) f)));
    }

    public static float a(List<Integer> list) {
        float f = 0.0f;
        if (list.size() == 0) {
            return 0.0f;
        }
        Iterator<Integer> it = list.iterator();
        while (true) {
            float f2 = f;
            if (!it.hasNext()) {
                return f2 / ((float) list.size());
            }
            f = ((float) it.next().intValue()) + f2;
        }
    }

    public static float b(List<Integer> list) {
        return (float) list.size();
    }

    public static float c(List<Integer> list) {
        float f = 999.0f;
        Iterator<Integer> it = list.iterator();
        while (true) {
            float f2 = f;
            if (!it.hasNext()) {
                return f2;
            }
            f = (float) it.next().intValue();
            if (f >= f2) {
                f = f2;
            }
        }
    }

    public static float d(List<Integer> list) {
        int i = 0;
        Iterator<Integer> it = list.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return ((float) i2) / ((float) list.size());
            }
            if (((float) it.next().intValue()) >= 999.0f) {
                i = i2 + 1;
            } else {
                i = i2;
            }
        }
    }

    public static float e(List<Integer> list) {
        float a2 = a(list);
        float f = 0.0f;
        if (list.size() == 0) {
            return 0.0f;
        }
        Iterator<Integer> it = list.iterator();
        while (true) {
            float f2 = f;
            if (!it.hasNext()) {
                return (float) Math.sqrt((double) (f2 / ((float) list.size())));
            }
            int intValue = it.next().intValue();
            f = ((((float) intValue) - a2) * (((float) intValue) - a2)) + f2;
        }
    }

    public static float f(List<Integer> list) {
        float[] fArr = {100.0f, 200.0f, 300.0f, 460.0f, 50000.0f};
        float[] fArr2 = {0.0f, 0.5f, 0.7f, 0.9f, 1.0f};
        int length = fArr.length;
        float f = 0.0f;
        if (list.size() == 0) {
            return 0.0f;
        }
        Iterator<Integer> it = list.iterator();
        while (true) {
            float f2 = f;
            if (it.hasNext()) {
                int intValue = it.next().intValue();
                int i = 0;
                while (true) {
                    if (i >= length) {
                        f = f2;
                        break;
                    } else if (((float) intValue) < fArr[i]) {
                        f = fArr2[i] + f2;
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                return f2 / ((float) list.size());
            }
        }
    }
}
