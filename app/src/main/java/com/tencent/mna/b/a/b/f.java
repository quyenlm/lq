package com.tencent.mna.b.a.b;

import com.tencent.mna.base.a.e;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/* compiled from: SpeedPrediction */
class f {

    /* compiled from: SpeedPrediction */
    public static class a {
        public double a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f = "";
    }

    static a a(long j, Vector<Integer> vector, Vector<Integer> vector2, int i, int i2, int i3, int i4, int i5, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        a aVar = new a();
        String[] split = str.split("_");
        if (split.length < 4) {
            aVar.a = -1003.0d;
            aVar.b = str;
        } else {
            String str8 = split[1];
            String str9 = split[2];
            String str10 = split[3];
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            HashMap hashMap3 = new HashMap();
            hashMap2.put("first_net", Integer.valueOf(i));
            hashMap2.put("jumpvalues_cdnping", Integer.valueOf(i2));
            hashMap2.put("jumpvalues_bridge_ping", Integer.valueOf(i3));
            hashMap2.put("jumpvalues_routeping", Integer.valueOf(i4));
            hashMap3.put("pcell_servicer", str8);
            hashMap3.put("pcell_protocol", str9);
            hashMap3.put("pcell_basestation", str10);
            hashMap3.put("pcell_signal", "" + i5);
            hashMap3.put("hardware_os_phone_type", str2);
            hashMap3.put("hardware_os_sys_version", str3);
            hashMap3.put("hardware_os_sys_api", str4);
            hashMap3.put("proxyip", str5);
            hashMap3.put("game_ip", str6);
            hashMap3.put("speed_ip", str7);
            aVar.b = "" + j + "_" + i + "_" + i2 + "_" + i3 + "_" + i4 + "_" + i5 + "_" + str + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5 + "_" + str6 + "_" + str7;
            a(vector, vector2, aVar);
            if (!com.tencent.mna.base.utils.f.a(str5) || !com.tencent.mna.base.utils.f.a(str6) || !com.tencent.mna.base.utils.f.a(str7)) {
                aVar.a = -1001.0d;
            } else if (i3 <= 0 || i5 < 0 || i < 0) {
                aVar.a = -1002.0d;
            } else {
                c cVar = new c();
                cVar.a(cVar.a(e.b()));
                double b = cVar.b(j, hashMap2, hashMap3, hashMap, vector2, vector);
                aVar.f = "" + b;
                aVar.a = b;
            }
        }
        return aVar;
    }

    static a a(long j, Vector<Integer> vector, Vector<Integer> vector2, int i, int i2, int i3, int i4, float f, int i5, int i6, int i7, int i8, int i9, int i10, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        a aVar = new a();
        HashMap hashMap = new HashMap();
        hashMap.put("jumpvalues_link_speed", Float.valueOf(f));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("first_net", Integer.valueOf(i));
        hashMap2.put("jumpvalues_cdnping", Integer.valueOf(i2));
        hashMap2.put("jumpvalues_bridge_ping", Integer.valueOf(i3));
        hashMap2.put("jumpvalues_routeping", Integer.valueOf(i4));
        hashMap2.put("jumpvalues_wifi_strength", Integer.valueOf(i5));
        hashMap2.put("jumpvalues_channel_id", Integer.valueOf(i6));
        hashMap2.put("jumpvalues_current_AP_num", Integer.valueOf(i7));
        hashMap2.put("jumpvalues_neighbour_AP_num", Integer.valueOf(i8));
        hashMap2.put("jumpvalues_search_AP_num", Integer.valueOf(i9));
        HashMap hashMap3 = new HashMap();
        hashMap3.put("wmac", str);
        hashMap3.put("hardware_os_phone_type", str2);
        hashMap3.put("hardware_os_sys_version", str3);
        hashMap3.put("hardware_os_sys_api", str4);
        hashMap3.put("proxyip", str5);
        hashMap3.put("game_ip", str6);
        hashMap3.put("speed_ip", str7);
        aVar.b = "" + j + "_" + f + "_" + i + "_" + i2 + "_" + i3 + "_" + i4 + "_" + i5 + "_" + i6 + "_" + i7 + "_" + i8 + "_" + i9 + "_" + str + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5 + "_" + str6 + "_" + str7;
        a(vector, vector2, aVar);
        if (!com.tencent.mna.base.utils.f.a(str5) || !com.tencent.mna.base.utils.f.a(str6) || !com.tencent.mna.base.utils.f.a(str7)) {
            aVar.a = -1001.0d;
        } else if (f < 0.0f || i3 <= 0 || i4 <= 0 || i5 < 0 || i7 < 0 || i8 < 0 || i9 < 0 || i < 0) {
            aVar.a = -1002.0d;
        } else {
            c cVar = new c();
            cVar.a(cVar.a(e.b()));
            double b = cVar.b(j, hashMap2, hashMap3, hashMap, vector2, vector);
            aVar.f = "" + b;
            aVar.a = b;
        }
        return aVar;
    }

    private static void a(Vector<Integer> vector, Vector<Integer> vector2, a aVar) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Iterator<Integer> it = vector2.iterator();
        while (it.hasNext()) {
            sb.append(it.next().intValue()).append('_');
        }
        Iterator<Integer> it2 = vector.iterator();
        while (it2.hasNext()) {
            sb2.append(it2.next().intValue()).append('_');
        }
        aVar.c = sb.toString();
        aVar.d = sb2.toString();
        aVar.e = "" + com.tencent.mna.base.a.a.P();
    }
}
