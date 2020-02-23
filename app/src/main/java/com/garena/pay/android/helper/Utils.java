package com.garena.pay.android.helper;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.pay.android.GGPayRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.jetbrains.annotations.Nullable;

public class Utils {
    private static Random rnd = new Random();

    public static boolean isActivityContextValid(Context context) {
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                return true;
            }
            return false;
        } else if (!(context instanceof ContextWrapper)) {
            return true;
        } else {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (!(baseContext instanceof Activity) || !((Activity) baseContext).isFinishing()) {
                return true;
            }
            return false;
        }
    }

    public static int getColor(Context context, int colorId) {
        return context.getResources().getColor(colorId);
    }

    public static String nullToEmpty(@Nullable String string) {
        return string == null ? "" : string;
    }

    @Nullable
    public static String emptyToNull(@Nullable String string) {
        if (isNullOrEmpty(string)) {
            return null;
        }
        return string;
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNullOrEmpty(@Nullable CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static Bitmap getBitmap(Context context, int imageId) {
        return BitmapFactory.decodeResource(context.getResources(), imageId);
    }

    public static BitmapDrawable getBitmapDrawable(Context context, int imageId) {
        return new BitmapDrawable(getBitmap(context, imageId));
    }

    public static String getString(Activity context, int string_id) {
        return context.getResources().getString(string_id);
    }

    public static String getRandomNumber(int digCount) {
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++) {
            sb.append((char) (rnd.nextInt(10) + 48));
        }
        return sb.toString();
    }

    public static String getSignatureFingerprint(Context context) {
        Signature[] signatures;
        try {
            PackageInfo callingPackageInfo = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 64);
            if (callingPackageInfo == null || (signatures = callingPackageInfo.signatures) == null || signatures.length <= 0) {
                return null;
            }
            try {
                byte[] fingerprint = MessageDigest.getInstance("SHA1").digest(signatures[0].toByteArray());
                StringBuffer hexString = new StringBuffer();
                for (byte b : fingerprint) {
                    String appendString = Integer.toHexString(b & 255);
                    if (hexString.length() > 0) {
                        hexString.append(":");
                    }
                    if (appendString.length() == 1) {
                        hexString.append("0");
                    }
                    hexString.append(appendString);
                }
                return hexString.toString().toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String calculateAppRoleId(GGPayRequest request) {
        Integer roleId = request.getClientPaymentRequest().getRoleId();
        Integer appServerId = request.getClientPaymentRequest().getAppServerId();
        Integer clientType = 2;
        if (roleId == null) {
            roleId = 0;
        }
        if (appServerId == null) {
            appServerId = 0;
        }
        String hexString = Integer.toHexString((clientType.intValue() << 24) + (appServerId.intValue() << 16) + roleId.intValue());
        BBLogger.d("Obtained this hex String: %s", hexString);
        return hexString;
    }

    public static Map<String, String> parseQueryArgs(String url) {
        Map<String, String> map = new HashMap<>();
        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.parseUrl(url);
        for (UrlQuerySanitizer.ParameterValuePair pair : sanitizer.getParameterList()) {
            map.put(pair.mParameter, pair.mValue);
        }
        return map;
    }

    public static Map<String, String> convertBundleToMap(Bundle extras) {
        HashMap<String, String> map = new HashMap<>();
        for (String key : extras.keySet()) {
            map.put(key, extras.get(key) == null ? "" : extras.get(key).toString());
        }
        return map;
    }
}
