package com.tencent.imsdk.unity.runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UnityPlayerProxyActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, UnityPlayerNativeActivity.class);
        intent.addFlags(65536);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }
}
