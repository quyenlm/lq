package com.tencent.tp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.tencent.tp.a.aa;
import com.tencent.tp.a.ac;
import com.tencent.tp.b.e;
import com.tencent.tp.b.g;
import com.tencent.tp.b.k;
import com.tencent.tp.b.n;

public class x {
    private Context a;

    private void doInvokeRootkitAppRequest() throws Exception {
        Activity currentActivity = TssSdkRuntime.getCurrentActivity();
        if (currentActivity == null) {
            q.a("TssSdkRuntime.getCurrentActivity err");
            return;
        }
        this.a = currentActivity;
        boolean isRootkitAppInstalled = isRootkitAppInstalled(currentActivity);
        boolean z = false;
        if (isRootkitAppInstalled) {
            z = isRootkitAppRunning();
        }
        if (!isRootkitAppInstalled || z) {
        }
        if (z) {
            return;
        }
        if (isRootkitAppInstalled) {
            invokeLaunchRootkitAppRequest();
        } else {
            invokeInstallRootkitAppRequest();
        }
    }

    public static void invokeForceUpdateRootkitAppRequest() {
        boolean z;
        if (!e.a()) {
            Activity currentActivity = TssSdkRuntime.getCurrentActivity();
            if (currentActivity == null) {
                q.a("TssSdkRuntime.getCurrentActivity err");
                return;
            }
            try {
                z = isRootkitAppInstalled(currentActivity);
            } catch (Exception e) {
                z = false;
            }
            if (z) {
                new n(currentActivity).a();
            } else {
                invokeRootkitAppRequest();
            }
        }
    }

    private void invokeInstallRootkitAppRequest() {
        new g(this.a).a();
    }

    private void invokeLaunchRootkitAppRequest() {
        new k(this.a).execute(new Void[0]);
    }

    public static void invokeRootkitAppRequest() {
        if (!e.a()) {
            try {
                new x().doInvokeRootkitAppRequest();
            } catch (Exception e) {
                q.a(e.toString());
            }
        }
    }

    public static void invokeRootkitIsRunningTip() {
        if (!e.a()) {
            Activity currentActivity = TssSdkRuntime.getCurrentActivity();
            if (currentActivity == null) {
                q.a("TssSdkRuntime.getCurrentActivity err");
            } else {
                new aa(currentActivity).b(ac.D);
            }
        }
    }

    public static boolean isApkInstalled(PackageManager packageManager) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(ac.c, 64);
        } catch (PackageManager.NameNotFoundException | Exception e) {
        }
        return packageInfo != null;
    }

    public static boolean isRootkitAppInstalled(Context context) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            return isApkInstalled(packageManager);
        }
        throw new Exception("Object not found");
    }

    private boolean isRootkitAppRunning() {
        return m.d() == 1;
    }
}
