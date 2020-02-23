package com.tencent.wetest;

import android.widget.EditText;

class b implements Runnable {
    final /* synthetic */ Robot a;
    private final /* synthetic */ EditText b;
    private final /* synthetic */ String c;

    b(Robot robot, EditText editText, String str) {
        this.a = robot;
        this.b = editText;
        this.c = str;
    }

    public void run() {
        this.b.setText(this.c);
    }
}
