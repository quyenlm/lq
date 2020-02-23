package com.tencent.imsdk.webview.qq;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

public class BottomBehaviorLand extends CoordinatorLayout.Behavior<LinearLayout> {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    /* access modifiers changed from: private */
    public static boolean isHideAnimRunning = false;
    /* access modifiers changed from: private */
    public static boolean isShowAnimRunning = false;

    public BottomBehaviorLand(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed >= 0) {
            hide(child);
        } else {
            show(child);
        }
    }

    /* access modifiers changed from: private */
    public void hide(final View view) {
        if (!isHideAnimRunning) {
            ViewPropertyAnimator animator = view.animate().translationY((float) view.getHeight()).setInterpolator(INTERPOLATOR).setDuration(200);
            animator.setListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    boolean unused = BottomBehaviorLand.isHideAnimRunning = true;
                }

                public void onAnimationEnd(Animator animator) {
                    boolean unused = BottomBehaviorLand.isHideAnimRunning = false;
                }

                public void onAnimationCancel(Animator animator) {
                    boolean unused = BottomBehaviorLand.isHideAnimRunning = false;
                    BottomBehaviorLand.this.show(view);
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            animator.start();
        }
    }

    /* access modifiers changed from: private */
    public void show(final View view) {
        if (!isShowAnimRunning) {
            ViewPropertyAnimator animator = view.animate().translationY(0.0f).setInterpolator(INTERPOLATOR).setDuration(200);
            animator.setListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    boolean unused = BottomBehaviorLand.isShowAnimRunning = true;
                }

                public void onAnimationEnd(Animator animator) {
                    boolean unused = BottomBehaviorLand.isShowAnimRunning = false;
                }

                public void onAnimationCancel(Animator animator) {
                    boolean unused = BottomBehaviorLand.isShowAnimRunning = false;
                    BottomBehaviorLand.this.hide(view);
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            animator.start();
        }
    }
}
