package com.tencent.gsdk.utils;

/* compiled from: EngineAPI */
public class d {
    public static int a() {
        try {
            Class<?> cls = Class.forName("com.unity3d.player.UnityPlayer");
            if (!(cls == null || cls.getName() == null || !cls.getName().equals("com.unity3d.player.UnityPlayer"))) {
                return 1;
            }
        } catch (Exception e) {
            Logger.w("UnityPlayer ClassNotFound");
        }
        try {
            Class<?> cls2 = Class.forName("org.cocos2dx.lib.Cocos2dxActivity");
            if (!(cls2 == null || cls2.getName() == null || !cls2.getName().equals("org.cocos2dx.lib.Cocos2dxActivity"))) {
                return 2;
            }
        } catch (Exception e2) {
            Logger.w("Cocos2dxActivity ClassNotFound");
        }
        try {
            Class<?> cls3 = Class.forName("com.epicgames.ue4.GameActivity");
            if (cls3 == null || cls3.getName() == null || !cls3.getName().equals("com.epicgames.ue4.GameActivity")) {
                return 0;
            }
            return 3;
        } catch (Exception e3) {
            Logger.w("UE4 GameActivity ClassNotFound");
        }
    }
}
