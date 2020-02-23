package cu_iipsmobile;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import com.google.android.gms.drive.DriveFile;
import com.tencent.component.plugin.PluginReporter;
import com.tsf4g.tx.TXLog;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CuIIPSMobile {
    private static final String PROVIDER_NAME = ".ApolloFileprovider";
    PowerManager.WakeLock wakeLock = null;
    WifiManager.WifiLock wifiLock = null;

    public static String GetObbAbsPath(Object obj) {
        String absolutePath = ((Activity) obj).getApplicationContext().getObbDir().getAbsolutePath();
        Log.v("GetObbAbsPath", "getapkpath success, path:" + absolutePath);
        return absolutePath;
    }

    public static String getBundleId(Object obj) {
        try {
            return ((Activity) obj).getApplicationContext().getPackageName();
        } catch (Exception e) {
            Log.e("getBundleId", "GetBundleId Exception:" + e);
            return null;
        }
    }

    public static String getVersionCode(Object obj) {
        Context applicationContext = ((Activity) obj).getApplicationContext();
        try {
            return applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String GetApkAbsPath(Object obj) {
        Activity activity = (Activity) obj;
        try {
            String str = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 0).sourceDir;
            TXLog.v("GetApkAbsPath", "getapkpath success,path:" + str);
            return str;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            TXLog.v(" GetApkAbsPath", "getapkpath failed");
            return "error";
        }
    }

    public boolean acquireWakeLock(Object obj, int i) {
        Activity activity = (Activity) obj;
        if (this.wakeLock == null) {
            PowerManager powerManager = (PowerManager) activity.getSystemService("power");
            if (i == 1) {
                this.wakeLock = powerManager.newWakeLock(536870913, "PostLocationService");
            }
            if (i == 2) {
                this.wakeLock = powerManager.newWakeLock(536870918, "PostLocationService");
            }
            if (i == 3) {
                this.wakeLock = powerManager.newWakeLock(536870922, "PostLocationService");
            }
            if (i == 4) {
                this.wakeLock = powerManager.newWakeLock(536870938, "PostLocationService");
            }
            if (i == 5) {
                this.wakeLock = powerManager.newWakeLock(DriveFile.MODE_READ_WRITE, "PostLocationService");
            }
            if (this.wakeLock != null) {
                this.wakeLock.acquire();
                return true;
            }
        }
        return false;
    }

    public boolean createWifiLock(Object obj, int i) {
        TXLog.v("CuIIPSMobile::toggleWiFi", "changeobject end");
        this.wifiLock = ((WifiManager) ((Context) obj).getSystemService("wifi")).createWifiLock(i, "cu_iipsmobile_wifilock");
        return this.wifiLock != null;
    }

    public String install(String str) {
        ProcessBuilder processBuilder = new ProcessBuilder(new String[]{"pm", PluginReporter.EVENT_INSTALL, "-r", str});
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Process start = processBuilder.start();
            InputStream errorStream = start.getErrorStream();
            while (true) {
                int read = errorStream.read();
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(read);
            }
            InputStream inputStream = start.getInputStream();
            while (true) {
                int read2 = inputStream.read();
                if (read2 == -1) {
                    return new String(byteArrayOutputStream.toByteArray());
                }
                byteArrayOutputStream.write(read2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public int installAPK(String str, Object obj) {
        Activity activity = (Activity) obj;
        if (activity == null) {
            TXLog.e("CuIIPSMobile", "installAPK the activity is null");
            return -1;
        }
        Context applicationContext = activity.getApplicationContext();
        if (str == null || str.length() == 0 || !isFileExist(str)) {
            return -1;
        }
        File file = new File(str);
        if (!str.startsWith(Environment.getExternalStorageDirectory().getPath())) {
            String path = applicationContext.getFilesDir().getPath();
            String absolutePath = file.getAbsolutePath();
            while (absolutePath != null && !path.equals(absolutePath)) {
                try {
                    Runtime.getRuntime().exec("chmod 777 " + absolutePath);
                    absolutePath = new File(absolutePath).getParent();
                } catch (IOException e) {
                    e.printStackTrace();
                    return -2;
                }
            }
        }
        Intent intent = new Intent();
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        intent.setAction("android.intent.action.VIEW");
        int i = applicationContext.getApplicationInfo().targetSdkVersion;
        TXLog.d("CuIIPSMobile", "targetVersion=" + i);
        if (i < 24 || Build.VERSION.SDK_INT < 24) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            intent.addFlags(1);
            intent.setDataAndType(FileProvider.getUriForFile(applicationContext, applicationContext.getPackageName() + PROVIDER_NAME, file), "application/vnd.android.package-archive");
        }
        try {
            applicationContext.startActivity(intent);
            return 0;
        } catch (ActivityNotFoundException e2) {
            e2.printStackTrace();
            return -3;
        }
    }

    public boolean isFileExist(String str) {
        File file = new File(str);
        return file.exists() && file.isFile();
    }

    public boolean isOpenNetwork(Object obj) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ((Activity) obj).getSystemService("connectivity");
        if (connectivityManager.getActiveNetworkInfo() != null) {
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    public boolean isWifiLocked() {
        if (this.wifiLock != null) {
            return this.wifiLock.isHeld();
        }
        return false;
    }

    public boolean lockWifi() {
        if (isWifiLocked()) {
            return true;
        }
        if (this.wifiLock == null) {
            return false;
        }
        this.wifiLock.acquire();
        return true;
    }

    public boolean releaseLock() {
        if (this.wifiLock == null || !this.wifiLock.isHeld()) {
            return this.wifiLock == null;
        }
        this.wifiLock.release();
        return true;
    }

    public boolean releaseWakeLock() {
        if (this.wakeLock == null) {
            return false;
        }
        this.wakeLock.release();
        this.wakeLock = null;
        return true;
    }

    public boolean slientInstall(String str) {
        try {
            Process exec = Runtime.getRuntime().exec("su");
            OutputStream outputStream = exec.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes("chmod 777 " + str + "\n");
            dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + str);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            int waitFor = exec.waitFor();
            if (waitFor == 0) {
                return true;
            }
            return waitFor == 1 ? false : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean testnet(String str) {
        try {
            int responseCode = ((HttpURLConnection) new URL(str).openConnection()).getResponseCode();
            TXLog.v("CuIIPSMobile::testnet", "connect code" + responseCode);
            if (responseCode != 200) {
                return false;
            }
            TXLog.v("CuIIPSMobile::testnet", "testnet net is ok");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int toggleWiFi(Object obj, boolean z) {
        TXLog.v("CuIIPSMobile::toggleWiFi", "starttoggleWiFi");
        TXLog.v("CuIIPSMobile::toggleWiFi", "changeobject end");
        TXLog.v("CuIIPSMobile::toggleWiFi", "getservice");
        ((WifiManager) ((Context) obj).getSystemService("wifi")).setWifiEnabled(z);
        TXLog.v("CuIIPSMobile::toggleWiFi", "enable end");
        return 1;
    }
}
