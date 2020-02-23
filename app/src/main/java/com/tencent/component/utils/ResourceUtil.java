package com.tencent.component.utils;

import android.content.Context;
import com.facebook.internal.AnalyticsEvents;
import com.tencent.component.ComponentContext;

public class ResourceUtil {
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static int getLayoutId(String resoureName) {
        return getIdentifier(resoureName, "layout");
    }

    public static int getStringId(String resoureName) {
        return getIdentifier(resoureName, "string");
    }

    public static int getDrawableId(String resoureName) {
        return getIdentifier(resoureName, "drawable");
    }

    public static int getStyleId(String resoureName) {
        return getIdentifier(resoureName, AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE);
    }

    public static int getStyleableId(String resoureName) {
        return getIdentifier(resoureName, "styleable");
    }

    public static int getId(String resoureName) {
        return getIdentifier(resoureName, "id");
    }

    public static int getColorId(String resoureName) {
        return getIdentifier(resoureName, "color");
    }

    public static int getAttrId(String resoureName) {
        return getIdentifier(resoureName, "attr");
    }

    public static int getAnimId(String resoureName) {
        return getIdentifier(resoureName, "anim");
    }

    public static int getDimenId(String resoureName) {
        return getIdentifier(resoureName, "dimen");
    }

    public static int getIntegerId(String resoureName) {
        return getIdentifier(resoureName, "integer");
    }

    private static int getIdentifier(String resoureName, String defType) {
        String propertyName = resoureName;
        int index = resoureName.lastIndexOf(".");
        if (index > 0) {
            propertyName = resoureName.substring(index + 1);
        }
        Context context = mContext;
        if (context == null) {
            context = ComponentContext.getContext();
        }
        return context.getResources().getIdentifier(propertyName, defType, context.getPackageName());
    }
}
