package ngame.support;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import com.google.android.gms.drive.DriveFile;
import java.io.File;

public class MultiDexApplication extends Application {
    private static final String MULTIDEX_PROCESS_NAME = ":ngame_multidex";
    private static final String TAG = MultiDexApplication.class.getSimpleName();

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (!isMultidexProcess()) {
            long start = System.currentTimeMillis();
            if (ApkUtil.isApkModified(this)) {
                waitForMultidex();
            }
            MultiDex.install(this);
            Log.d(TAG, "time used:" + Long.toString(System.currentTimeMillis() - start));
        }
    }

    private void waitForMultidex() {
        createMarkFile();
        Intent intent = new Intent(this, MultiDexActivity.class);
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        startActivity(intent);
        int time = 0;
        while (time < 15000) {
            if (!getMarkFile().exists()) {
                Log.d(TAG, "multidex mark file deleted, finished!");
                return;
            }
            try {
                Thread.sleep(100);
                time += 100;
            } catch (Exception e) {
                Log.w(TAG, Log.getStackTraceString(e));
            }
        }
        Log.w(TAG, "waitForMultidex time up");
    }

    private void createMarkFile() {
        File markFile = getMarkFile();
        if (!markFile.exists()) {
            try {
                markFile.createNewFile();
            } catch (Exception e) {
                Log.w(TAG, Log.getStackTraceString(e));
            }
        } else {
            Log.w(TAG, "mark file not supposed to exist");
        }
    }

    private File getMarkFile() {
        return new File(getFilesDir().getAbsolutePath(), "multidex_mark_file");
    }

    private boolean isMultidexProcess() {
        String processName = getCurProcessName();
        Log.d(TAG, "processName:" + processName);
        if (processName != null) {
            return processName.contains(MULTIDEX_PROCESS_NAME);
        }
        Log.w(TAG, "processName null");
        return false;
    }

    private String getCurProcessName() {
        try {
            int pid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo processInfo : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
                if (processInfo.pid == pid) {
                    return processInfo.processName;
                }
            }
        } catch (Exception e) {
            Log.w(TAG, Log.getStackTraceString(e));
        }
        return null;
    }
}
