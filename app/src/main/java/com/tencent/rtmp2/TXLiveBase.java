package com.tencent.rtmp2;

import android.util.Log;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.g;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TXLiveBase {
    private static final String FILE_MD5_FFMPEG = "___md5_libtxffmpeg_md5__________";
    private static final String FILE_MD5_LITEAV = "___md5_libliteavsdk_md5_________";
    private static final String FILE_MD5_SATURN = "___md5_libsaturn_md5____________";
    private static final String FILE_MD5_TRAE = "___md5_libtraeimp_md5___________";
    private static final String TAG = "TXLiveBase";
    private static TXLiveBase instance = new TXLiveBase();
    /* access modifiers changed from: private */
    public static ITXLiveBaseListener listener = null;

    private TXLiveBase() {
    }

    public static TXLiveBase getInstance() {
        return instance;
    }

    public static void setListener(ITXLiveBaseListener iTXLiveBaseListener) {
        TXCLog.setListener(new a());
        listener = iTXLiveBaseListener;
    }

    public static void setLogLevel(int i) {
        TXCLog.setLevel(i);
    }

    public static void setConsoleEnabled(boolean z) {
        TXCLog.setConsoleEnabled(z);
    }

    public static void setAppVersion(String str) {
        TXCDRApi.txSetAppVersion(str);
        TXCCommonUtil.setAppVersion(str);
    }

    public static void setLibraryPath(String str) {
        com.tencent.liteav.basic.util.a.b(str);
    }

    public static boolean isLibraryPathValid(String str) {
        String fileMD5 = getFileMD5(str, "libliteavsdk.so");
        String fileMD52 = getFileMD5(str, "libsaturn.so");
        String fileMD53 = getFileMD5(str, "libtxffmpeg.so");
        String fileMD54 = getFileMD5(str, "libtraeimp-rtmp-armeabi.so");
        Log.e(TAG, "MD5-Check libliteavsdk = " + fileMD5 + " FILE_MD5_LITEAV = " + FILE_MD5_LITEAV);
        Log.e(TAG, "MD5-Check libsaturn = " + fileMD52 + " FILE_MD5_SATURN = " + FILE_MD5_SATURN);
        Log.e(TAG, "MD5-Check libtxffmpeg = " + fileMD53 + " FILE_MD5_FFMPEG = " + FILE_MD5_FFMPEG);
        Log.e(TAG, "MD5-Check libtraeimpl = " + fileMD54 + " FILE_MD5_TRAE = " + FILE_MD5_TRAE);
        if (fileMD5 == null || !fileMD5.equalsIgnoreCase(FILE_MD5_LITEAV) || fileMD52 == null || !fileMD52.equalsIgnoreCase(FILE_MD5_SATURN) || fileMD53 == null || !fileMD53.equalsIgnoreCase(FILE_MD5_FFMPEG) || fileMD54 == null || !fileMD54.equalsIgnoreCase(FILE_MD5_TRAE)) {
            return false;
        }
        return true;
    }

    public static String getSDKVersionStr() {
        return TXCCommonUtil.getSDKVersionStr();
    }

    private static class a implements TXCLog.a {
        private a() {
        }

        public void a(int i, String str, String str2) {
            if (TXLiveBase.listener != null) {
                TXLiveBase.listener.OnLog(i, str, str2);
            }
        }
    }

    public static void setPituLicencePath(String str) {
        TXCCommonUtil.setPituLicencePath(str);
    }

    public static String getPituSDKVersion() {
        return g.a();
    }

    private static String getFileMD5(String str, String str2) {
        MessageDigest messageDigest = null;
        File file = new File(str, str2);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read == -1) {
                    break;
                }
                messageDigest.update(bArr, 0, read);
            }
            fileInputStream.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
}
