package com.tencent.imsdk.webview.qq;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class BottomBehaviorPortrait extends CoordinatorLayout.Behavior<LinearLayout> {
    public BottomBehaviorPortrait(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        child.setTranslationY((float) (-dependency.getTop()));
        return true;
    }
}
