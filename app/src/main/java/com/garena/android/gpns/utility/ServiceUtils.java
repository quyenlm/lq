package com.garena.android.gpns.utility;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.strategy.ServiceLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.List;

public class ServiceUtils {
    public static final String APPLICATION_ID = "com.garena.sdk.push.applicationId";

    @Deprecated
    public static ComponentName getRunningService() {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) ServiceLoader.getApplicationContext().getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (runningServices != null) {
            for (ActivityManager.RunningServiceInfo service : runningServices) {
                if (GNotificationService.class.getName().equals(service.service.getClassName()) && service.restarting == 0 && service.process.equals(CONSTANT.PROCESS_NAME)) {
                    return service.service;
                }
            }
        }
        return null;
    }

    public static void safeReleaseLock(FileLock mLock) {
        if (mLock != null) {
            try {
                mLock.release();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FileLock ensureFileLock() throws IOException {
        String sdfolder = FileUtil.getAppFolderPath();
        checkFolder(sdfolder);
        File f = new File(sdfolder, "pushservice.lock");
        if (!f.exists()) {
            f.createNewFile();
        }
        return new FileOutputStream(f).getChannel().lock();
    }

    private static void checkFolder(String strFolder) {
        File f = new File(strFolder);
        if (f.isDirectory() && f.exists()) {
            return;
        }
        if (!f.mkdirs()) {
            AppLogger.d("create folder error" + strFolder);
            return;
        }
        try {
            File file = new File(f.getPath() + File.separator + ".nomedia");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAppId(Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (info.metaData != null) {
                return Integer.valueOf(info.metaData.getInt("com.garena.sdk.push.applicationId")).toString();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}
