package com.vk.sdk.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.Nullable;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.vk.sdk.api.VKParameters;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class VKUtil {
    static final /* synthetic */ boolean $assertionsDisabled = (!VKUtil.class.desiredAssertionStatus());

    @Nullable
    public static Map<String, String> explodeQueryString(@Nullable String queryString) {
        if (queryString == null) {
            return null;
        }
        String[] keyValuePairs = queryString.split(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        HashMap<String, String> parameters = new HashMap<>(keyValuePairs.length);
        for (String keyValueString : keyValuePairs) {
            String[] keyValueArray = keyValueString.split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            parameters.put(keyValueArray[0], keyValueArray[1]);
        }
        return parameters;
    }

    public static String fileToString(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder builder = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                builder.append(line);
            } else {
                reader.close();
                return builder.toString();
            }
        }
    }

    public static void stringToFile(String filename, String stringToWrite) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(stringToWrite);
            writer.flush();
            writer.close();
        } catch (Exception e) {
        }
    }

    public static String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(aMessageDigest & 255);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static String[] getCertificateFingerprint(Context ctx, String packageName) {
        if (ctx != null) {
            try {
                if (ctx.getPackageManager() != null) {
                    PackageInfo info = ctx.getPackageManager().getPackageInfo(packageName, 64);
                    if ($assertionsDisabled || info.signatures != null) {
                        String[] result = new String[info.signatures.length];
                        Signature[] signatureArr = info.signatures;
                        int length = signatureArr.length;
                        int i = 0;
                        int i2 = 0;
                        while (i < length) {
                            Signature signature = signatureArr[i];
                            MessageDigest md = MessageDigest.getInstance("SHA");
                            md.update(signature.toByteArray());
                            int i3 = i2 + 1;
                            result[i2] = toHex(md.digest());
                            i++;
                            i2 = i3;
                        }
                        return result;
                    }
                    throw new AssertionError();
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", new Object[]{bi});
    }

    public static Map<String, Object> mapFrom(Object... args) {
        if (args.length % 2 != 0) {
        }
        LinkedHashMap<String, Object> result = new LinkedHashMap<>(args.length / 2);
        for (int i = 0; i + 1 < args.length; i += 2) {
            if (!(args[i] == null || args[i + 1] == null || !(args[i] instanceof String))) {
                result.put(args[i], args[i + 1]);
            }
        }
        return result;
    }

    public static VKParameters paramsFrom(Object... args) {
        return new VKParameters(mapFrom(args));
    }

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            if ($assertionsDisabled || pm != null) {
                pm.getPackageInfo(uri, 1);
                return true;
            }
            throw new AssertionError();
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isIntentAvailable(Context context, String action) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(action);
        if ($assertionsDisabled || packageManager != null) {
            return packageManager.queryIntentActivities(intent, 65536).size() > 0;
        }
        throw new AssertionError();
    }

    public static String getApplicationName(Context ctx) {
        try {
            Context appContext = ctx.getApplicationContext();
            if ($assertionsDisabled || appContext != null) {
                PackageManager pm = appContext.getPackageManager();
                if ($assertionsDisabled || pm != null) {
                    ApplicationInfo ai = pm.getApplicationInfo(ctx.getPackageName(), 0);
                    return (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getHost(String url) {
        int doubleslash;
        if (url == null || url.length() == 0) {
            return "";
        }
        int doubleslash2 = url.indexOf("//");
        if (doubleslash2 == -1) {
            doubleslash = 0;
        } else {
            doubleslash = doubleslash2 + 2;
        }
        int end = url.indexOf(47, doubleslash);
        if (end < 0) {
            end = url.length();
        }
        int port = url.indexOf(58, doubleslash);
        if (port > 0 && port < end) {
            end = port;
        }
        return url.substring(doubleslash, end);
    }
}
