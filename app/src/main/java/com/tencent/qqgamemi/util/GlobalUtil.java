package com.tencent.qqgamemi.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.component.utils.log.LogUtil;
import java.util.Locale;

public class GlobalUtil {
    public static final String AVC_MIME_TYPE = "video/avc";
    public static final String AVC_TYPE = "ENCODER";
    public static final long RECORDPLUGINID = 9506698241L;
    private static String TAG = "GlobalUtil";
    private static Context globalContext;
    private static String sGameEngineType;

    @SuppressLint({"NewApi"})
    public static String getEncType() {
        if (Build.VERSION.SDK_INT >= 16) {
            int length = MediaCodecList.getCodecCount();
            for (int i = 0; i < length; i++) {
                MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);
                for (String mimeType : info.getSupportedTypes()) {
                    LogUtil.i(TAG, "mimeType=" + mimeType + "<<<" + info.getName());
                    if (mimeType.equals(AVC_MIME_TYPE)) {
                        String name = info.getName();
                        if (name.toUpperCase(Locale.ENGLISH).indexOf(AVC_TYPE) > 0) {
                            return name;
                        }
                    }
                }
            }
        }
        return "";
    }

    public static int getVersionCode(Context mContext, String pkgName) {
        try {
            return mContext.getPackageManager().getPackageInfo(pkgName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getCurGameNameVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    public static final int getPixFromDip(float aDipValue, Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
        return (int) (dm.density * aDipValue);
    }

    public static void setContext(Context context) {
        globalContext = context;
    }

    public static Context getGlobalContext() {
        return globalContext;
    }

    public static void setGameEngineType(String gameEngineType) {
        sGameEngineType = gameEngineType;
    }

    public static String getGameEngineType() {
        return sGameEngineType;
    }
}
