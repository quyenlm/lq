package com.tencent.mna.base.utils;

import android.app.Activity;

/* compiled from: EngineUtils */
public final class d {
    public static int a() {
        try {
            Class<?> cls = Class.forName("com.unity3d.player.UnityPlayer");
            if (!(cls == null || cls.getName() == null || !cls.getName().equals("com.unity3d.player.UnityPlayer"))) {
                return 1;
            }
        } catch (Exception e) {
            h.a("UnityPlayer ClassNotFound");
        }
        try {
            Class<?> cls2 = Class.forName("com.epicgames.ue4.GameActivity");
            if (cls2 == null || cls2.getName() == null || !cls2.getName().equals("com.epicgames.ue4.GameActivity")) {
                return 0;
            }
            return 3;
        } catch (Exception e2) {
            h.a("UE4 GameActivity ClassNotFound");
        }
    }

    public static Activity b() {
        try {
            return (Activity) Class.forName("com.unity3d.player.UnityPlayer").getField("currentActivity").get(Activity.class);
        } catch (Exception e) {
            h.a("fail to get context, exception:" + e.getMessage());
            return null;
        }
    }
}
