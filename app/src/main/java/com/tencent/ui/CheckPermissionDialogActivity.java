package com.tencent.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import com.tencent.component.utils.ResourceUtil;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.SDKApiHelper;

public class CheckPermissionDialogActivity extends Activity implements View.OnClickListener {
    private static final String Key = "IsFirstCheck";
    private static final String PERMISSION_RECORD_AUDIO = "android.permission.RECORD_AUDIO";
    private static final String PERMISSION_WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final int REQUEST_CODE_PERMISSION_EXTERNAl_STORAGE = 938;
    private static final int REQUEST_CODE_PERMISSION_RECORD_AUDIO = 330;
    private static final int REQUEST_CODE_PERMISSION_STORAGE_AUDIO = 4;
    private static final int REQUEST_CODE_SCREEN_CAPTURE = 1;
    private static final String TAG = "CheckPermission";
    private View contentView = null;
    private Button settingButton;
    private ViewStub viewStub;

    public void onCreate(Bundle savedInstanceState) {
        setTheme(16973841);
        super.onCreate(savedInstanceState);
        setContentView(ResourceUtil.getLayoutId("check_permission_layout"));
        this.viewStub = (ViewStub) findViewById(ResourceUtil.getId("viewStub"));
        checkStoreAndAudioPermissions();
    }

    private void setButton() {
        if (this.settingButton == null && this.contentView != null) {
            this.settingButton = (Button) this.contentView.findViewById(ResourceUtil.getId("notify_dialog_btn_go"));
            this.settingButton.setOnClickListener(this);
        }
    }

    private void refreshLayout() {
        if (this.contentView == null) {
            this.contentView = this.viewStub.inflate();
            setButton();
        }
    }

    @TargetApi(21)
    private void checkRecorderPermisson() {
        Intent permissionIntent = checkSafePermissionIntent(this);
        if (permissionIntent != null) {
            Log.i(TAG, "checkRecorderPermisson is called!");
            startActivityForResult(permissionIntent, 1);
            return;
        }
        finish();
    }

    @TargetApi(21)
    public static Intent checkSafePermissionIntent(Context context) {
        boolean isPermissionActivityExists = false;
        if (context == null) {
            return null;
        }
        Intent permissionIntent = ((MediaProjectionManager) context.getSystemService("media_projection")).createScreenCaptureIntent();
        if (permissionIntent.resolveActivityInfo(context.getPackageManager(), 0) != null) {
            isPermissionActivityExists = true;
        }
        if (!isPermissionActivityExists) {
            permissionIntent = null;
        }
        return permissionIntent;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1 == requestCode) {
            SPUtils.put(this, Key, false);
            finish();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult onRequestPermissionsResult: " + requestCode);
        switch (requestCode) {
            case 4:
            case 330:
            case REQUEST_CODE_PERMISSION_EXTERNAl_STORAGE /*938*/:
                refreshLayout();
                return;
            default:
                return;
        }
    }

    public void onClick(View view) {
        if (view.getId() == ResourceUtil.getId("notify_dialog_btn_go")) {
            checkRecorderPermisson();
        }
    }

    public static void startPermissionActivity(Context context) {
        if (context == null || Build.VERSION.SDK_INT < 21 || !((Boolean) SPUtils.get(context, Key, true)).booleanValue()) {
            return;
        }
        if (checkSafePermissionIntent(context) == null) {
            SDKApiHelper.getInstance().showDefaultWarning(context);
        } else if (!ToolUitls.getAppOps(context)) {
            if (context.getApplicationContext() == null) {
                LogUtil.e(TAG, "context.getApplicationContext() is null");
            }
            LogUtil.i(TAG, "未授权，开始授权引导");
            ResourceUtil.setContext(context);
            context.startActivity(new Intent(context, CheckPermissionDialogActivity.class));
        } else {
            LogUtil.i(TAG, "已经授权成功过，不进行授权引导");
        }
    }

    private void checkStoreAndAudioPermissions() {
        boolean hasRecordAudioPermission;
        String[] permissions = null;
        if (Build.VERSION.SDK_INT >= 23) {
            boolean hasStoragePermission = checkSelfPermission(PERMISSION_WRITE_EXTERNAL_STORAGE) == 0;
            if (checkSelfPermission(PERMISSION_RECORD_AUDIO) == 0) {
                hasRecordAudioPermission = true;
            } else {
                hasRecordAudioPermission = false;
            }
            LogUtil.i(TAG, "storage permission: " + hasStoragePermission + ", audio permission: " + hasRecordAudioPermission);
            if (!hasStoragePermission && !hasRecordAudioPermission) {
                permissions = new String[]{PERMISSION_WRITE_EXTERNAL_STORAGE, PERMISSION_RECORD_AUDIO};
                requestPermissions(permissions, 4);
                Log.i(TAG, "requestPermissions REQUEST_CODE_PERMISSION_STORAGE_AUDIO!");
            } else if (!hasStoragePermission) {
                permissions = new String[]{PERMISSION_WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, REQUEST_CODE_PERMISSION_EXTERNAl_STORAGE);
                Log.i(TAG, "requestPermissions REQUEST_CODE_PERMISSION_EXTERNAl_STORAGE!");
            } else if (!hasRecordAudioPermission) {
                permissions = new String[]{PERMISSION_RECORD_AUDIO};
                requestPermissions(permissions, 330);
                Log.i(TAG, "requestPermissions REQUEST_CODE_PERMISSION_RECORD_AUDIO!");
            }
        }
        if (permissions == null) {
            refreshLayout();
        }
    }
}
