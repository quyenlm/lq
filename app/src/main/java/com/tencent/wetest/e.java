package com.tencent.wetest;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class e implements Runnable {
    MotionEvent a;
    View b = null;

    public e(MotionEvent motionEvent, View view) {
        this.a = motionEvent;
        this.b = view;
    }

    public void run() {
        while (true) {
            if (!U3DAutomation.c) {
                if (this.b.onTouchEvent(this.a)) {
                    break;
                }
                boolean a2 = U3DAutomation.c();
                Log.i(U3DAutomation.a, "getForward = " + a2);
                if (!a2) {
                    break;
                }
                U3DAutomation.c = true;
            } else {
                U3DAutomation.b(false);
                if (!this.b.onTouchEvent(this.a)) {
                    Log.e(U3DAutomation.a, "touch fail.");
                }
                U3DAutomation.b(true);
            }
        }
        this.a.recycle();
    }
}
