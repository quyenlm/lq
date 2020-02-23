package com.tencent.tdm.gcloud.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.tdm.TDataMaster;

public class PluginReportLifecycle {
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        TDataMaster.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int i, String[] strings, int[] ints) {
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void onCreate(Activity activity, Bundle bundle) {
        TDataMaster.getInstance().initialize(activity);
    }

    public void onDestroy() {
        TDataMaster.getInstance().onDestroy();
    }

    public void onNewIntent(Intent arg0) {
    }

    public void onPause() {
        TDataMaster.getInstance().onPause();
    }

    public void onRestart() {
        TDataMaster.getInstance().onRestart();
    }

    public void onResume() {
        TDataMaster.getInstance().onResume();
    }

    public void onStart() {
        TDataMaster.getInstance().onStart();
    }

    public void onStop() {
        TDataMaster.getInstance().onStop();
    }
}
