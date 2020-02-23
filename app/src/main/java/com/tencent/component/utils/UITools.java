package com.tencent.component.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.component.ComponentContext;

public class UITools {
    private static Handler mUIHandler = new Handler(Looper.getMainLooper());

    private UITools() {
    }

    public static void showToast(CharSequence text) {
        showToast(text, 0);
    }

    public static void showToast(int resId) {
        showToast(resId, 0);
    }

    public static void showToast(int resId, int duration) {
        showToast(ComponentContext.getContext().getText(resId), duration);
    }

    public static void showToast(final CharSequence text, final int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            toastInner(text, duration);
        } else {
            mUIHandler.post(new Runnable() {
                public void run() {
                    UITools.toastInner(text, duration);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void toastInner(CharSequence text, int duration) {
        Toast toast = Toast.makeText(ComponentContext.getContext(), text, duration);
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showDebugToast(CharSequence text) {
        showDebugToast(text, 0);
    }

    public static void showDebugToast(CharSequence text, int duration) {
        if (DebugUtil.isDebuggable()) {
            showToast((CharSequence) "debug版本调试信息：" + text, duration);
        }
    }

    public static void showDebugToast(int resId) {
        showDebugToast(resId, 0);
    }

    public static void showDebugToast(int resId, int duration) {
        try {
            showDebugToast(ComponentContext.getContext().getText(resId), duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
