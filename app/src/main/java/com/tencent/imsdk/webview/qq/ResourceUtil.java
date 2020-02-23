package com.tencent.imsdk.webview.qq;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import com.facebook.internal.AnalyticsEvents;
import com.tencent.imsdk.tool.etc.IMLogger;

public class ResourceUtil {
    private static final String FORMAT_TYPEFACE_NAME = "fonts/%s.ttf";
    static final String TYPEFACE_SHARE_CANCEL_NAME = "share_cancel";
    static final String TYPEFACE_SHARE_RESULT_NAME = "share_result";
    static final String TYPEFACE_WEBVIEW_TITLE_NAME = "webview_title";

    public static Typeface getTypeface(Context context, String paramString) {
        String tfName = String.format(FORMAT_TYPEFACE_NAME, new Object[]{paramString.toLowerCase()});
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(context.getAssets(), tfName);
            IMLogger.d("get TTF " + tfName + " success!");
            return tf;
        } catch (RuntimeException e) {
            IMLogger.w("no TTF found : " + tfName);
            return tf;
        }
    }

    public static int getLayoutId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, "layout", context.getPackageName());
    }

    public static int getStringId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, "string", context.getPackageName());
    }

    public static int getDrawableId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, "drawable", context.getPackageName());
    }

    public static int getStyleId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, context.getPackageName());
    }

    public static int getId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, "id", context.getPackageName());
    }

    public static int getArrayId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, "array", context.getPackageName());
    }

    public static Drawable getDrawable(Context context, String paramString) {
        if (context != null) {
            Resources resources = context.getResources();
            int resID = getDrawableId(context, paramString);
            if (resID != 0) {
                return resources.getDrawable(resID);
            }
        }
        return null;
    }

    public static int getColorId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, "color", context.getPackageName());
    }

    public static int getDimenId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, "dimen", context.getPackageName());
    }

    public static int getAnimId(Context context, String paramString) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(paramString, "anim", context.getPackageName());
    }

    public static int[] getStyleableIntArray(Context context, String name) {
        if (context == null) {
            return null;
        }
        try {
            return (int[]) Class.forName(context.getPackageName() + ".R$styleable").getDeclaredField(name).get((Object) null);
        } catch (Throwable t) {
            IMLogger.e(t.getMessage());
            return null;
        }
    }

    public static int getStyleableIntArrayIndex(Context context, String name) {
        if (context == null) {
            return 0;
        }
        try {
            return ((Integer) Class.forName(context.getPackageName() + ".R$styleable").getDeclaredField(name).get((Object) null)).intValue();
        } catch (Throwable t) {
            IMLogger.e(t.getMessage());
            return 0;
        }
    }
}
