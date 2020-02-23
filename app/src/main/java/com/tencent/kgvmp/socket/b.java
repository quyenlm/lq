package com.tencent.kgvmp.socket;

import com.tencent.kgvmp.a.c;
import com.tencent.kgvmp.a.d;
import com.tencent.kgvmp.e.f;
import java.util.HashMap;
import java.util.Map;

public class b {
    public static HashMap<String, String> a = new HashMap<String, String>() {
        {
            put(c.SCENE.getKeyStr(), com.tencent.kgvmp.a.b.SCENEID.getKey());
            put(c.FPS.getKeyStr(), com.tencent.kgvmp.a.b.FPS.getKey());
            put(c.ROLE_STATUS.getKeyStr(), com.tencent.kgvmp.a.b.ROLESTATUS.getKey());
            put(c.NET_LATENCY.getKeyStr(), com.tencent.kgvmp.a.b.NETLATENCY.getKey());
            put(c.SERVER_IP.getKeyStr(), com.tencent.kgvmp.a.b.SERVERIP.getKey());
            put(c.FPS_TARGET.getKeyStr(), com.tencent.kgvmp.a.b.TARGETFPS.getKey());
            put(c.HD_MODEL.getKeyStr(), com.tencent.kgvmp.a.b.RESOLUTION.getKey());
            put(c.MODEL_LEVEL.getKeyStr(), com.tencent.kgvmp.a.b.PICQUALITY.getKey());
            put(c.USERS_COUNT.getKeyStr(), com.tencent.kgvmp.a.b.USERCOUNT.getKey());
            put(c.THREADTID.getKeyStr(), com.tencent.kgvmp.a.b.THREADTID.getKey());
            put(c.BATTERY_TEMP.getKeyStr(), (Object) null);
            put(c.MAIN_VERCODE.getKeyStr(), (Object) null);
            put(c.SUB_VERCODE.getKeyStr(), (Object) null);
            put(c.TIME_STAMP.getKeyStr(), (Object) null);
            put(c.FRAME_MISS.getKeyStr(), (Object) null);
            put(c.EFFECT_LEVEL.getKeyStr(), (Object) null);
            put(c.RECORDING.getKeyStr(), (Object) null);
            put(c.URGENT_SIGNAL.getKeyStr(), (Object) null);
        }
    };
    private static boolean b = false;
    private static boolean c = false;

    public static String a(int i, String str) {
        String a2 = a(String.valueOf(i));
        return a2 != null ? c(a2) ? "{\"" + a2 + "\":\"" + str + "\"}" : a(a2, str) : "";
    }

    public static String a(String str) {
        if (!a.containsKey(str)) {
            return str;
        }
        String str2 = a.get(str);
        if (str2 == null) {
            return null;
        }
        return str2;
    }

    private static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder("{");
        if (str.equals(com.tencent.kgvmp.a.b.SCENEID.getKey())) {
            if (str2.equals(d.LOGIN_LOAD.getSceneID()) || str2.equals(d.SCENE_LOAD.getSceneID())) {
                if (!b) {
                    sb.append("\"").append(com.tencent.kgvmp.a.b.LOADING.getKey()).append("\":").append("true,");
                    b = true;
                }
            } else if (b) {
                sb.append("\"").append(com.tencent.kgvmp.a.b.LOADING.getKey()).append("\":").append("false,");
                b = false;
            }
            if (str2.equals(d.PLAYING.getSceneID()) && !c) {
                sb.append("\"").append(com.tencent.kgvmp.a.b.BATTLE.getKey()).append("\":").append("1,");
                c = true;
            }
            if (str2.equals(d.MAIN_UI.getSceneID()) && c) {
                sb.append("\"").append(com.tencent.kgvmp.a.b.BATTLE.getKey()).append("\":").append("0,");
                c = false;
            }
        }
        sb.append("\"").append(str).append("\":").append(str2).append("}");
        return sb.toString();
    }

    public static String a(HashMap<String, String> hashMap) {
        String str = "{";
        for (Map.Entry next : hashMap.entrySet()) {
            String str2 = (String) next.getKey();
            String str3 = (String) next.getValue();
            if (b(str2)) {
                String a2 = a(str2);
                str = a2 != null ? c(a2) ? str + "\"" + a2 + "\":\"" + str3 + "\"," : str + "\"" + a2 + "\":" + str3 + "," : str;
            }
        }
        return str + "\"0\":\"0\"}";
    }

    public static boolean a(int i) {
        return (i == c.OPEN_ID.getKey() || i == c.MAP_ID.getKey() || i == -1) ? false : true;
    }

    public static boolean b(String str) {
        try {
            return a(Integer.parseInt(str));
        } catch (Exception e) {
            f.a("ContentValues", "VendorSpecialTool:checkKeyNeedSend: parse int exception.");
            return false;
        }
    }

    private static boolean c(String str) {
        int parseInt = Integer.parseInt(str);
        return parseInt == com.tencent.kgvmp.a.b.THREADTID.getKeyID() || parseInt == com.tencent.kgvmp.a.b.SERVERIP.getKeyID() || parseInt > 51;
    }
}
