package com.garena.game.kgtw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class BlankActivity extends Activity {
    private Handler mHandler = new Handler();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                BlankActivity.this.startActivity(new Intent(BlankActivity.this, SGameRealActivity.class));
                BlankActivity.this.finish();
            }
        }, 1000);
    }
}
