package com.tencent.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class NotFocusableProgressDialog extends ProgressDialog {
    private boolean mIsSystemUiVisibilitySet = false;
    private int mSystemUiVisibility;

    public NotFocusableProgressDialog(Context context) {
        super(context);
    }

    public NotFocusableProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setSystemUiVisibility(int systemUiVisibility) {
        this.mSystemUiVisibility = systemUiVisibility;
        this.mIsSystemUiVisibilitySet = true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Window window;
        super.onCreate(savedInstanceState);
        if (this.mIsSystemUiVisibilitySet && (window = getWindow()) != null) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                decorView.setSystemUiVisibility(this.mSystemUiVisibility);
            }
            window.getAttributes().flags |= 8;
        }
    }
}
