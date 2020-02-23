package com.tencent.mtt.common.utils;

import android.content.Context;
import com.facebook.internal.AnalyticsEvents;
import com.tencent.mtt.engine.AppEngine;

public class MttResources {
    private static Context sContext = AppEngine.getInstance().getApplicationContex();

    public static int getLayoutId(String paramString) {
        return sContext.getResources().getIdentifier(paramString, "layout", sContext.getPackageName());
    }

    public static int getStringId(String paramString) {
        return sContext.getResources().getIdentifier(paramString, "string", sContext.getPackageName());
    }

    public static int getDrawableId(String paramString) {
        try {
            return sContext.getResources().getIdentifier(paramString, "drawable", sContext.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return sContext.getResources().getIdentifier(paramString, "thrdcall_transparent", sContext.getPackageName());
        }
    }

    public static int getStyleId(String paramString) {
        return sContext.getResources().getIdentifier(paramString, AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, sContext.getPackageName());
    }

    public static int getId(String paramString) {
        return sContext.getResources().getIdentifier(paramString, "id", sContext.getPackageName());
    }

    public static int getColorId(String paramString) {
        return sContext.getResources().getIdentifier(paramString, "color", sContext.getPackageName());
    }

    public static int getDimensId(String paramString) {
        return sContext.getResources().getIdentifier(paramString, "dimen", sContext.getPackageName());
    }

    public static int getAttrId(String paramString) {
        return sContext.getResources().getIdentifier(paramString, "attr", sContext.getPackageName());
    }
}
