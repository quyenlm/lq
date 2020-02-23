package com.appsflyer;

final class q implements b {

    /* renamed from: ˊ  reason: contains not printable characters */
    private b f156 = this;

    interface b {
        /* renamed from: ˊ  reason: contains not printable characters */
        Class<?> m118(String str) throws ClassNotFoundException;
    }

    enum e {
        UNITY("android_unity", "com.unity3d.player.UnityPlayer"),
        REACT_NATIVE("android_reactNative", "com.facebook.react.ReactApplication"),
        CORDOVA("android_cordova", "org.apache.cordova.CordovaActivity"),
        SEGMENT("android_segment", "com.segment.analytics.integrations.Integration"),
        COCOS2DX("android_cocos2dx", "org.cocos2dx.lib.Cocos2dxActivity"),
        DEFAULT("android_native", "android_native");
        
        /* access modifiers changed from: private */

        /* renamed from: ʻ  reason: contains not printable characters */
        public String f164;
        /* access modifiers changed from: private */

        /* renamed from: ʼ  reason: contains not printable characters */
        public String f165;

        private e(String str, String str2) {
            this.f165 = str;
            this.f164 = str2;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final String m117() {
        for (e eVar : e.values()) {
            if (m115(eVar.f164)) {
                return eVar.f165;
            }
        }
        return e.DEFAULT.f165;
    }

    q() {
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private boolean m115(String str) {
        try {
            this.f156.m118(str);
            AFLogger.afRDLog(new StringBuilder("Class: ").append(str).append(" is found.").toString());
            return true;
        } catch (ClassNotFoundException e2) {
            return false;
        } catch (Throwable th) {
            AFLogger.afErrorLog(th.getMessage(), th);
            return false;
        }
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    public final Class<?> m116(String str) throws ClassNotFoundException {
        return Class.forName(str);
    }
}
