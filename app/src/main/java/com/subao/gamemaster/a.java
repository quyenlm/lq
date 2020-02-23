package com.subao.gamemaster;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/* compiled from: Unity3DHelper */
class a {
    private final Class a;

    public a() {
        Class<?> cls = null;
        try {
            cls = Class.forName("com.unity3d.player.UnityPlayer");
        } catch (ClassNotFoundException e) {
            Log.w("SubaoGame", "UnityPlayer not found");
        }
        this.a = cls;
    }

    @Nullable
    public Activity a() {
        if (this.a == null) {
            return null;
        }
        try {
            Object obj = this.a.getDeclaredField("currentActivity").get((Object) null);
            if (obj instanceof Activity) {
                return (Activity) obj;
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean a(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        if (this.a == null) {
            return false;
        }
        try {
            this.a.getDeclaredMethod("UnitySendMessage", new Class[]{String.class, String.class, String.class}).invoke((Object) null, new Object[]{str, str2, str3});
            return true;
        } catch (Error | Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
