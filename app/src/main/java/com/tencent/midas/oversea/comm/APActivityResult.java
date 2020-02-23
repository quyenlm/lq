package com.tencent.midas.oversea.comm;

import android.content.Intent;

public class APActivityResult {
    public Intent data;
    public int requestCode;
    public int resultCode;

    public APActivityResult(int i, int i2, Intent intent) {
        this.requestCode = i;
        this.resultCode = i2;
        this.data = intent;
    }
}
