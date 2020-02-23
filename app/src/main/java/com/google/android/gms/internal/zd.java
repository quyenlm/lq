package com.google.android.gms.internal;

import android.util.Base64;
import com.appsflyer.share.Constants;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.vk.sdk.api.VKApiConst;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class zd {
    private static final char[] zzcjC = "0123456789abcdef".toCharArray();

    public static void zzaF(boolean z) {
        zzb(z, "");
    }

    public static za<Task<Void>, DatabaseReference.CompletionListener> zzb(DatabaseReference.CompletionListener completionListener) {
        if (completionListener != null) {
            return new za<>(null, completionListener);
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        return new za<>(taskCompletionSource.getTask(), new ze(taskCompletionSource));
    }

    public static void zzb(boolean z, String str) {
        if (!z) {
            String valueOf = String.valueOf(str);
            throw new AssertionError(valueOf.length() != 0 ? "hardAssert failed: ".concat(valueOf) : new String("hardAssert failed: "));
        }
    }

    public static zb zzgX(String str) throws DatabaseException {
        String str2;
        try {
            int indexOf = str.indexOf("//");
            if (indexOf == -1) {
                throw new URISyntaxException(str, "Invalid scheme specified");
            }
            int indexOf2 = str.substring(indexOf + 2).indexOf(Constants.URL_PATH_DELIMITER);
            if (indexOf2 != -1) {
                int i = indexOf + 2 + indexOf2;
                String[] split = str.substring(i).split(Constants.URL_PATH_DELIMITER);
                StringBuilder sb = new StringBuilder();
                for (int i2 = 0; i2 < split.length; i2++) {
                    if (!split[i2].equals("")) {
                        sb.append(Constants.URL_PATH_DELIMITER);
                        sb.append(URLEncoder.encode(split[i2], "UTF-8"));
                    }
                }
                String valueOf = String.valueOf(str.substring(0, i));
                String valueOf2 = String.valueOf(sb.toString());
                str2 = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            } else {
                str2 = str;
            }
            URI uri = new URI(str2);
            String replace = uri.getPath().replace("+", " ");
            zf.zzhc(replace);
            qr qrVar = new qr(replace);
            String scheme = uri.getScheme();
            rx rxVar = new rx();
            rxVar.host = uri.getHost().toLowerCase();
            int port = uri.getPort();
            if (port != -1) {
                rxVar.secure = scheme.equals(VKApiConst.HTTPS);
                String valueOf3 = String.valueOf(rxVar.host);
                rxVar.host = new StringBuilder(String.valueOf(valueOf3).length() + 12).append(valueOf3).append(":").append(port).toString();
            } else {
                rxVar.secure = true;
            }
            rxVar.zzbxU = rxVar.host.split("\\.")[0].toLowerCase();
            rxVar.zzceq = rxVar.host;
            zb zbVar = new zb();
            zbVar.zzbZf = qrVar;
            zbVar.zzbYW = rxVar;
            return zbVar;
        } catch (URISyntaxException e) {
            throw new DatabaseException("Invalid Firebase Database url specified", e);
        } catch (UnsupportedEncodingException e2) {
            throw new DatabaseException("Failed to URLEncode the path", e2);
        }
    }

    public static String zzgY(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes("UTF-8"));
            return Base64.encodeToString(instance.digest(), 2);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Missing SHA-1 MessageDigest provider.", e);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("UTF-8 encoding is required for Firebase Database to run!");
        }
    }

    public static String zzgZ(String str) {
        String replace = str.indexOf(92) != -1 ? str.replace("\\", "\\\\") : str;
        if (str.indexOf(34) != -1) {
            replace = replace.replace("\"", "\\\"");
        }
        return new StringBuilder(String.valueOf(replace).length() + 2).append("\"").append(replace).append("\"").toString();
    }

    public static Integer zzha(String str) {
        boolean z;
        int i;
        if (str.length() > 11 || str.length() == 0) {
            return null;
        }
        if (str.charAt(0) != '-') {
            z = false;
            i = 0;
        } else if (str.length() == 1) {
            return null;
        } else {
            z = true;
            i = 1;
        }
        long j = 0;
        int i2 = i;
        while (i2 < str.length()) {
            char charAt = str.charAt(i2);
            if (charAt < '0' || charAt > '9') {
                return null;
            }
            i2++;
            j = ((long) (charAt - '0')) + (j * 10);
        }
        if (z) {
            if ((-j) < -2147483648L) {
                return null;
            }
            return Integer.valueOf((int) (-j));
        } else if (j > 2147483647L) {
            return null;
        } else {
            return Integer.valueOf((int) j);
        }
    }

    public static int zzj(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }

    public static String zzj(double d) {
        StringBuilder sb = new StringBuilder(16);
        long doubleToLongBits = Double.doubleToLongBits(d);
        for (int i = 7; i >= 0; i--) {
            int i2 = (int) ((doubleToLongBits >>> (i << 3)) & 255);
            sb.append(zzcjC[(i2 >> 4) & 15]);
            sb.append(zzcjC[i2 & 15]);
        }
        return sb.toString();
    }

    public static int zzo(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }
}
