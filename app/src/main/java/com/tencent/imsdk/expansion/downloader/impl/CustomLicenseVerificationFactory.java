package com.tencent.imsdk.expansion.downloader.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.imsdk.expansion.downloader.IMLogger;
import com.tencent.imsdk.expansion.downloader.IUserConfirm;
import com.tencent.imsdk.expansion.downloader.LicenseVerificationBase;
import java.lang.reflect.InvocationTargetException;

public class CustomLicenseVerificationFactory {
    public static String BASE64_PUBLIC_KEY = null;
    private static final String CUSTOM_LICENSE_VERIFICATION = "license_verification";
    private static final String CUSTOM_LICENSE_VERIFICATION_CLASSNAME = "classname_lv";
    private static final String EMPTY_STRING = "";
    public static byte[] SALT = null;
    private static IUserConfirm iUserConfirm = null;

    public static LicenseVerificationBase CreateCustomLV(DownloaderService downloaderService) {
        String className = downloaderService.getApplicationContext().getSharedPreferences(CUSTOM_LICENSE_VERIFICATION, 0).getString(CUSTOM_LICENSE_VERIFICATION_CLASSNAME, "");
        if (className == null || (className.trim().length() > 0 && !"".equals(className))) {
            try {
                LicenseVerificationBase lvb = (LicenseVerificationBase) Class.forName(className).getConstructor(new Class[]{DownloaderService.class}).newInstance(new Object[]{downloaderService});
                if (!(BASE64_PUBLIC_KEY == null || SALT == null)) {
                    lvb.setPublicKey(BASE64_PUBLIC_KEY);
                    lvb.setSALT(SALT);
                }
                if (iUserConfirm == null) {
                    return lvb;
                }
                lvb.setUserConfirmListener(iUserConfirm);
                return lvb;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (ClassNotFoundException e5) {
                e5.printStackTrace();
            }
        } else {
            IMLogger.e("You need to indicate a specific license verification which extends LicenseVerificationBase");
            return null;
        }
    }

    public static void setLicenseVerification(Context mContext, String className) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CUSTOM_LICENSE_VERIFICATION, 0);
        String classNameExist = sharedPreferences.getString(CUSTOM_LICENSE_VERIFICATION_CLASSNAME, "");
        if (classNameExist != null && !classNameExist.equals(className)) {
            sharedPreferences.edit().putString(CUSTOM_LICENSE_VERIFICATION_CLASSNAME, className).commit();
        }
    }

    public static void setLicenseVerificationByTag(Context mContext, String tagName) {
        String packageName = String.format("com.tencent.%s.expansion.downloader.%sLicenseVerification", new Object[]{tagName.toLowerCase(), tagName});
        IMLogger.d("setLicenseVerificationByTag -> packageName = " + packageName);
        setLicenseVerification(mContext, packageName);
    }

    public static void setUserConfirmListener(IUserConfirm userConfirm) {
        iUserConfirm = userConfirm;
    }
}
