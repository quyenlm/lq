package com.tencent.imsdk.webview.qq;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class BottomBehaviorLand_Old extends CoordinatorLayout.Behavior<LinearLayout> {
    private static int oldTranslation = 0;
    private int currentTranslation = 0;
    private int defaultDependencyTop = -1;

    public BottomBehaviorLand_Old(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        if (this.defaultDependencyTop == -1) {
            this.defaultDependencyTop = dependency.getTop();
        }
        this.currentTranslation = (-dependency.getTop()) + this.defaultDependencyTop;
        if (this.currentTranslation > 0 && this.currentTranslation == oldTranslation) {
            this.currentTranslation = child.getHeight();
        }
        if (this.currentTranslation < 0) {
            this.currentTranslation = 0;
        }
        oldTranslation = this.currentTranslation;
        child.setTranslationY((float) this.currentTranslation);
        return true;
    }

    public int getCurrentTranslation() {
        return this.currentTranslation;
    }
}
