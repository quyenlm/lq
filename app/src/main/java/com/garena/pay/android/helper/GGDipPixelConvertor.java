package com.garena.pay.android.helper;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.smtt.sdk.TbsListener;
import java.lang.ref.WeakReference;

public class GGDipPixelConvertor {
    private static int DENSITY_DPI = 0;
    private static String DENSITY_NAME = null;
    private static final int DENSITY_XHIGH = 320;
    private static float FONT_SCALE_RATE;
    private static float SCREEN_DENSITY;
    private static int SCREEN_PIXEL_DENSITY;
    private static WeakReference<Context> context;
    private static Point screenSize;

    public static void initialize(Context aContext) {
        if (context == null || context.get() == null) {
            context = new WeakReference<>(aContext.getApplicationContext());
            initialize(((Context) context.get()).getResources().getDisplayMetrics());
        }
    }

    public static void initialize(DisplayMetrics displayMetrics) {
        SCREEN_DENSITY = displayMetrics.density;
        SCREEN_PIXEL_DENSITY = (int) ((((float) displayMetrics.densityDpi) / SCREEN_DENSITY) + 0.5f);
        FONT_SCALE_RATE = displayMetrics.scaledDensity;
        DENSITY_DPI = displayMetrics.densityDpi;
        switch (displayMetrics.densityDpi) {
            case TbsListener.ErrorCode.DOWNLOAD_HAS_LOCAL_TBS_ERROR:
                DENSITY_NAME = "ldpi";
                return;
            case 160:
                DENSITY_NAME = "mdpi";
                return;
            case 240:
                DENSITY_NAME = "hdpi";
                return;
            case DENSITY_XHIGH /*320*/:
                DENSITY_NAME = "xhdpi";
                return;
            default:
                if (displayMetrics.densityDpi > DENSITY_XHIGH) {
                    DENSITY_NAME = "xxhdpi";
                    return;
                } else {
                    DENSITY_NAME = "mdpi";
                    return;
                }
        }
    }

    public static int getDensityDpi() {
        return DENSITY_DPI;
    }

    public static int convertDip2Px(int nDip) {
        return (int) ((SCREEN_DENSITY * ((float) nDip)) + 0.5f);
    }

    public static int convertPx2Dip(float pxValue) {
        return (int) ((pxValue / SCREEN_DENSITY) + 0.5f);
    }

    public static float getScreenDensity() {
        return SCREEN_DENSITY;
    }

    public static int getScaleDipDensity() {
        return SCREEN_PIXEL_DENSITY;
    }

    public static float convertSP2Px(float sp) {
        return FONT_SCALE_RATE * sp;
    }

    public static int convertPx2SP(float pxValue) {
        return (int) (pxValue / FONT_SCALE_RATE);
    }

    public static String getDensityName() {
        return DENSITY_NAME;
    }

    public static int getScreenHeight() {
        if (screenSize == null) {
            initScreenSize();
        }
        return screenSize.y;
    }

    private static void initScreenSize() {
        screenSize = new Point(-1, -1);
        if (context.get() != null) {
            WindowManager manager = (WindowManager) ((Context) context.get()).getSystemService("window");
            screenSize.x = manager.getDefaultDisplay().getWidth();
            screenSize.y = manager.getDefaultDisplay().getHeight();
        }
    }

    public static int getScreenWidth() {
        if (screenSize == null) {
            initScreenSize();
        }
        return screenSize.x;
    }
}
