package com.garena.game.kgtw;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.ngame.utility.SGameUtility;
import com.tencent.tp.a.h;
import java.util.HashMap;

public class SGameActivity extends Activity {
    private static String PRE_APPLY_FOR_PERMISSIONS = "com.tencent.ngame.PreApplyForPermissions";
    /* access modifiers changed from: private */
    public boolean dialogShowing = false;
    private HashMap<String, PERMISSION_STYLE> permission_dict = new HashMap<>();
    private String[] permission_list = {"android.permission.WRITE_EXTERNAL_STORAGE"};
    private boolean showNeedView = false;

    private enum PERMISSION_STYLE {
        ALLOW,
        REFUSE,
        NERVER_SHOW
    }

    private void initBugly() {
        String buglyId = "1104811017";
        CrashReport.UserStrategy us = new CrashReport.UserStrategy(this);
        Log.d("SGameActivity", "initBugly1111");
        Log.d("SGameActivity", CrashReport.getBuglyVersion(this));
        try {
            buglyId = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData.getString("APPID_BUGLY").substring(5);
            Log.d("SGameActivity", "buglyId:" + buglyId);
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            us.setAppVersion(pinfo.versionName + h.a + pinfo.versionCode + h.b);
        } catch (Exception e) {
            Log.e("SGameActivity", "no bugly id in manifest");
            Log.e("SGameActivity", e.toString());
            e.printStackTrace();
        }
        Log.i("SGameActivity", "Apollo bugly onCreate");
        CrashReport.initCrashReport(this, buglyId, false, us);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        initBugly();
        super.onCreate(savedInstanceState);
        String permissionConfList = getStringResourceByName(PRE_APPLY_FOR_PERMISSIONS);
        Log.d("XXXX", "MFLIST:" + permissionConfList);
        if (!permissionConfList.equals(PRE_APPLY_FOR_PERMISSIONS) && permissionConfList.length() > 0) {
            this.permission_list = permissionConfList.split(",");
            Log.d("XXXX", this.permission_list.toString());
        }
        SGameUtility.setSavedIntent(getIntent());
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SGameUtility.setSavedIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        checkPermission();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.i("SGameActivity", "onWindowFocusChanged try hide system ui");
            hideSystemUI(getWindow().getDecorView());
        }
    }

    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    @TargetApi(19)
    public static void hideSystemUI(View view) {
        if (view != null && getSystemVersion() >= 19) {
            Log.i("SGameActivity", "hideSystemUI start hide system ui");
            view.setSystemUiVisibility(5894);
        }
    }

    private void updatePermissionType() {
        for (String s : this.permission_list) {
            if (ContextCompat.checkSelfPermission(this, s) == 0) {
                this.permission_dict.put(s, PERMISSION_STYLE.ALLOW);
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, s)) {
                this.permission_dict.put(s, PERMISSION_STYLE.REFUSE);
            } else {
                this.permission_dict.put(s, PERMISSION_STYLE.NERVER_SHOW);
            }
            Log.d("XXXX", s);
            Log.d("XXXX", this.permission_dict.get(s).toString());
        }
    }

    private boolean hasNeverShow() {
        int i = 0;
        Boolean res = false;
        String[] strArr = this.permission_list;
        int length = strArr.length;
        while (true) {
            if (i >= length) {
                break;
            }
            if (this.permission_dict.get(strArr[i]) == PERMISSION_STYLE.NERVER_SHOW) {
                res = true;
                break;
            }
            i++;
        }
        return res.booleanValue();
    }

    private boolean hasRefuse() {
        int i = 0;
        Boolean res = false;
        String[] strArr = this.permission_list;
        int length = strArr.length;
        while (true) {
            if (i >= length) {
                break;
            }
            if (this.permission_dict.get(strArr[i]) == PERMISSION_STYLE.REFUSE) {
                res = true;
                break;
            }
            i++;
        }
        return res.booleanValue();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!this.dialogShowing) {
                updatePermissionType();
                if (hasNeverShow()) {
                    if (!this.showNeedView) {
                        showUNeedAgreeMe();
                        this.showNeedView = true;
                        return;
                    }
                    showUNeedFixConfig();
                    return;
                } else if (hasRefuse()) {
                    if (!this.showNeedView) {
                        showUNeedAgreeMe();
                        this.showNeedView = true;
                        return;
                    }
                    showUMustAgreeMe();
                    return;
                }
            } else {
                return;
            }
        }
        Intent intent = new Intent(getApplicationContext(), SGameRealActivity.class);
        if (!(getIntent() == null || getIntent().getDataString() == null)) {
            intent.setData(Uri.parse(getIntent().getDataString()));
            intent.setAction("android.intent.action.VIEW");
        }
        finish();
        startActivity(intent);
    }

    /* access modifiers changed from: private */
    @TargetApi(23)
    public void requestPermission() {
        requestPermissions(this.permission_list, 100);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void showUNeedAgreeMe() {
        /*
            r9 = this;
            r5 = 1
            r9.dialogShowing = r5     // Catch:{ Throwable -> 0x0058 }
            r2 = 0
            android.content.pm.PackageManager r5 = r9.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0053 }
            java.lang.String r6 = r9.getPackageName()     // Catch:{ NameNotFoundException -> 0x0053 }
            r7 = 0
            android.content.pm.ApplicationInfo r3 = r5.getApplicationInfo(r6, r7)     // Catch:{ NameNotFoundException -> 0x0053 }
            android.content.pm.PackageManager r5 = r9.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0053 }
            android.graphics.drawable.Drawable r2 = r3.loadIcon(r5)     // Catch:{ NameNotFoundException -> 0x0053 }
        L_0x0019:
            android.support.v7.app.AlertDialog$Builder r0 = new android.support.v7.app.AlertDialog$Builder     // Catch:{ Throwable -> 0x0058 }
            android.content.res.Resources r5 = r9.getResources()     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r6 = "AovDialogStyle"
            java.lang.String r7 = "style"
            java.lang.String r8 = r9.getPackageName()     // Catch:{ Throwable -> 0x0058 }
            int r5 = r5.getIdentifier(r6, r7, r8)     // Catch:{ Throwable -> 0x0058 }
            r0.<init>(r9, r5)     // Catch:{ Throwable -> 0x0058 }
            if (r2 == 0) goto L_0x0033
            r0.setIcon((android.graphics.drawable.Drawable) r2)     // Catch:{ Throwable -> 0x0058 }
        L_0x0033:
            int r5 = com.garena.game.kgtw.R.string.u_need_agree_me_title     // Catch:{ Throwable -> 0x0058 }
            r0.setTitle((int) r5)     // Catch:{ Throwable -> 0x0058 }
            int r5 = com.garena.game.kgtw.R.string.u_need_agree_me     // Catch:{ Throwable -> 0x0058 }
            r0.setMessage((int) r5)     // Catch:{ Throwable -> 0x0058 }
            int r5 = com.garena.game.kgtw.R.string.submit     // Catch:{ Throwable -> 0x0058 }
            com.garena.game.kgtw.SGameActivity$1 r6 = new com.garena.game.kgtw.SGameActivity$1     // Catch:{ Throwable -> 0x0058 }
            r6.<init>()     // Catch:{ Throwable -> 0x0058 }
            r0.setPositiveButton((int) r5, (android.content.DialogInterface.OnClickListener) r6)     // Catch:{ Throwable -> 0x0058 }
            r5 = 0
            r0.setCancelable(r5)     // Catch:{ Throwable -> 0x0058 }
            android.support.v7.app.AlertDialog r4 = r0.create()     // Catch:{ Throwable -> 0x0058 }
            r4.show()     // Catch:{ Throwable -> 0x0058 }
        L_0x0052:
            return
        L_0x0053:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Throwable -> 0x0058 }
            goto L_0x0019
        L_0x0058:
            r1 = move-exception
            java.lang.String r5 = "XXXX"
            java.lang.String r6 = r1.toString()
            android.util.Log.d(r5, r6)
            goto L_0x0052
        */
        throw new UnsupportedOperationException("Method not decompiled: com.garena.game.kgtw.SGameActivity.showUNeedAgreeMe():void");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void showUMustAgreeMe() {
        /*
            r9 = this;
            r5 = 1
            r9.dialogShowing = r5     // Catch:{ Throwable -> 0x0062 }
            r2 = 0
            android.content.pm.PackageManager r5 = r9.getPackageManager()     // Catch:{ NameNotFoundException -> 0x005d }
            java.lang.String r6 = r9.getPackageName()     // Catch:{ NameNotFoundException -> 0x005d }
            r7 = 0
            android.content.pm.ApplicationInfo r3 = r5.getApplicationInfo(r6, r7)     // Catch:{ NameNotFoundException -> 0x005d }
            android.content.pm.PackageManager r5 = r9.getPackageManager()     // Catch:{ NameNotFoundException -> 0x005d }
            android.graphics.drawable.Drawable r2 = r3.loadIcon(r5)     // Catch:{ NameNotFoundException -> 0x005d }
        L_0x0019:
            android.support.v7.app.AlertDialog$Builder r0 = new android.support.v7.app.AlertDialog$Builder     // Catch:{ Throwable -> 0x0062 }
            android.content.res.Resources r5 = r9.getResources()     // Catch:{ Throwable -> 0x0062 }
            java.lang.String r6 = "AovDialogStyle"
            java.lang.String r7 = "style"
            java.lang.String r8 = r9.getPackageName()     // Catch:{ Throwable -> 0x0062 }
            int r5 = r5.getIdentifier(r6, r7, r8)     // Catch:{ Throwable -> 0x0062 }
            r0.<init>(r9, r5)     // Catch:{ Throwable -> 0x0062 }
            if (r2 == 0) goto L_0x0033
            r0.setIcon((android.graphics.drawable.Drawable) r2)     // Catch:{ Throwable -> 0x0062 }
        L_0x0033:
            int r5 = com.garena.game.kgtw.R.string.u_must_agree_me_title     // Catch:{ Throwable -> 0x0062 }
            r0.setTitle((int) r5)     // Catch:{ Throwable -> 0x0062 }
            int r5 = com.garena.game.kgtw.R.string.u_must_agree_me     // Catch:{ Throwable -> 0x0062 }
            r0.setMessage((int) r5)     // Catch:{ Throwable -> 0x0062 }
            int r5 = com.garena.game.kgtw.R.string.retry     // Catch:{ Throwable -> 0x0062 }
            com.garena.game.kgtw.SGameActivity$2 r6 = new com.garena.game.kgtw.SGameActivity$2     // Catch:{ Throwable -> 0x0062 }
            r6.<init>()     // Catch:{ Throwable -> 0x0062 }
            r0.setPositiveButton((int) r5, (android.content.DialogInterface.OnClickListener) r6)     // Catch:{ Throwable -> 0x0062 }
            int r5 = com.garena.game.kgtw.R.string.quit     // Catch:{ Throwable -> 0x0062 }
            com.garena.game.kgtw.SGameActivity$3 r6 = new com.garena.game.kgtw.SGameActivity$3     // Catch:{ Throwable -> 0x0062 }
            r6.<init>()     // Catch:{ Throwable -> 0x0062 }
            r0.setNegativeButton((int) r5, (android.content.DialogInterface.OnClickListener) r6)     // Catch:{ Throwable -> 0x0062 }
            r5 = 0
            r0.setCancelable(r5)     // Catch:{ Throwable -> 0x0062 }
            android.support.v7.app.AlertDialog r4 = r0.create()     // Catch:{ Throwable -> 0x0062 }
            r4.show()     // Catch:{ Throwable -> 0x0062 }
        L_0x005c:
            return
        L_0x005d:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Throwable -> 0x0062 }
            goto L_0x0019
        L_0x0062:
            r1 = move-exception
            java.lang.String r5 = "XXXX"
            java.lang.String r6 = r1.toString()
            android.util.Log.d(r5, r6)
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.garena.game.kgtw.SGameActivity.showUMustAgreeMe():void");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showUNeedFixConfig() {
        /*
            r9 = this;
            r5 = 1
            r9.dialogShowing = r5     // Catch:{ Throwable -> 0x0062 }
            r2 = 0
            android.content.pm.PackageManager r5 = r9.getPackageManager()     // Catch:{ NameNotFoundException -> 0x005d }
            java.lang.String r6 = r9.getPackageName()     // Catch:{ NameNotFoundException -> 0x005d }
            r7 = 0
            android.content.pm.ApplicationInfo r3 = r5.getApplicationInfo(r6, r7)     // Catch:{ NameNotFoundException -> 0x005d }
            android.content.pm.PackageManager r5 = r9.getPackageManager()     // Catch:{ NameNotFoundException -> 0x005d }
            android.graphics.drawable.Drawable r2 = r3.loadIcon(r5)     // Catch:{ NameNotFoundException -> 0x005d }
        L_0x0019:
            android.support.v7.app.AlertDialog$Builder r0 = new android.support.v7.app.AlertDialog$Builder     // Catch:{ Throwable -> 0x0062 }
            android.content.res.Resources r5 = r9.getResources()     // Catch:{ Throwable -> 0x0062 }
            java.lang.String r6 = "AovDialogStyle"
            java.lang.String r7 = "style"
            java.lang.String r8 = r9.getPackageName()     // Catch:{ Throwable -> 0x0062 }
            int r5 = r5.getIdentifier(r6, r7, r8)     // Catch:{ Throwable -> 0x0062 }
            r0.<init>(r9, r5)     // Catch:{ Throwable -> 0x0062 }
            if (r2 == 0) goto L_0x0033
            r0.setIcon((android.graphics.drawable.Drawable) r2)     // Catch:{ Throwable -> 0x0062 }
        L_0x0033:
            int r5 = com.garena.game.kgtw.R.string.u_need_fix_config_title     // Catch:{ Throwable -> 0x0062 }
            r0.setTitle((int) r5)     // Catch:{ Throwable -> 0x0062 }
            int r5 = com.garena.game.kgtw.R.string.u_need_fix_config     // Catch:{ Throwable -> 0x0062 }
            r0.setMessage((int) r5)     // Catch:{ Throwable -> 0x0062 }
            int r5 = com.garena.game.kgtw.R.string.fix     // Catch:{ Throwable -> 0x0062 }
            com.garena.game.kgtw.SGameActivity$4 r6 = new com.garena.game.kgtw.SGameActivity$4     // Catch:{ Throwable -> 0x0062 }
            r6.<init>()     // Catch:{ Throwable -> 0x0062 }
            r0.setPositiveButton((int) r5, (android.content.DialogInterface.OnClickListener) r6)     // Catch:{ Throwable -> 0x0062 }
            int r5 = com.garena.game.kgtw.R.string.quit     // Catch:{ Throwable -> 0x0062 }
            com.garena.game.kgtw.SGameActivity$5 r6 = new com.garena.game.kgtw.SGameActivity$5     // Catch:{ Throwable -> 0x0062 }
            r6.<init>()     // Catch:{ Throwable -> 0x0062 }
            r0.setNegativeButton((int) r5, (android.content.DialogInterface.OnClickListener) r6)     // Catch:{ Throwable -> 0x0062 }
            r5 = 0
            r0.setCancelable(r5)     // Catch:{ Throwable -> 0x0062 }
            android.support.v7.app.AlertDialog r4 = r0.create()     // Catch:{ Throwable -> 0x0062 }
            r4.show()     // Catch:{ Throwable -> 0x0062 }
        L_0x005c:
            return
        L_0x005d:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Throwable -> 0x0062 }
            goto L_0x0019
        L_0x0062:
            r1 = move-exception
            java.lang.String r5 = "XXXX"
            java.lang.String r6 = r1.toString()
            android.util.Log.d(r5, r6)
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.garena.game.kgtw.SGameActivity.showUNeedFixConfig():void");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (String s : permissions) {
            Log.d("XXXX", s);
        }
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            Log.d("XXXX", "" + grantResults[i]);
        }
        checkPermission();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001f, code lost:
        r2 = r0.metaData.getString(r7);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getStringResourceByName(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            android.content.pm.PackageManager r3 = r6.getPackageManager()     // Catch:{ Exception -> 0x0014 }
            java.lang.String r4 = r6.getPackageName()     // Catch:{ Exception -> 0x0014 }
            r5 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r0 = r3.getApplicationInfo(r4, r5)     // Catch:{ Exception -> 0x0014 }
        L_0x000f:
            if (r0 != 0) goto L_0x001f
            java.lang.String r2 = ""
        L_0x0013:
            return r2
        L_0x0014:
            r1 = move-exception
            java.lang.String r3 = "XXXX"
            java.lang.String r4 = r1.getLocalizedMessage()
            android.util.Log.d(r3, r4)
            goto L_0x000f
        L_0x001f:
            android.os.Bundle r3 = r0.metaData
            java.lang.String r2 = r3.getString(r7)
            if (r2 != 0) goto L_0x0013
            java.lang.String r2 = ""
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.garena.game.kgtw.SGameActivity.getStringResourceByName(java.lang.String):java.lang.String");
    }
}
