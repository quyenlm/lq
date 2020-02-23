package com.unity3d.player;

import android.os.Bundle;
import android.util.Log;

public class UnityPlayerNativeActivity extends UnityPlayerActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Log.w("Unity", "UnityPlayerNativeActivity has been deprecated, please update your AndroidManifest to use UnityPlayerActivity instead");
        super.onCreate(bundle);
    }
}
