package com.garena.game.kgvn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class UnityPlayerProxyActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Log.w("Unity", "UnityPlayerNativeActivity has been deprecated, please update your AndroidManifest to use UnityPlayerActivity instead");
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, UnityPlayerActivity.class);
        intent.addFlags(65536);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }
}
