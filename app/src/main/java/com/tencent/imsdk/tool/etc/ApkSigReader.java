package com.tencent.imsdk.tool.etc;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Locale;

public class ApkSigReader {
    public static String getSign(Context context) {
        Iterator<PackageInfo> iter = context.getPackageManager().getInstalledPackages(64).iterator();
        String sigOrigin = "";
        while (true) {
            if (!iter.hasNext()) {
                break;
            }
            PackageInfo packageinfo = iter.next();
            if (packageinfo.packageName.equals(context.getPackageName())) {
                sigOrigin = packageinfo.signatures[0].toCharsString();
                break;
            }
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sigOrigin.getBytes(Charset.forName("UTF-8")));
            return HexUtil.bytes2HexStr(md.digest()).toLowerCase(Locale.CHINA);
        } catch (NoSuchAlgorithmException e) {
            IMLogger.e(e.getMessage());
            return "";
        }
    }

    public static String getSignaturesFromApk(Context ctx) throws IOException {
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 64);
            if (info == null) {
                return null;
            }
            String sigOrigin = new String(info.signatures[0].toByteArray(), Charset.forName("UTF-8"));
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(sigOrigin.getBytes(Charset.forName("UTF-8")));
                return HexUtil.bytes2HexStr(md.digest()).toLowerCase(Locale.CHINA);
            } catch (NoSuchAlgorithmException e) {
                IMLogger.e(e.getMessage());
                return "";
            }
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        }
    }
}
