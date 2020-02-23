package com.tencent.wetest;

import android.view.View;

class a implements Runnable {
    final /* synthetic */ Robot a;
    private final /* synthetic */ View b;

    a(Robot robot, View view) {
        this.a = robot;
        this.b = view;
    }

    public void run() {
        this.b.performClick();
    }
}
