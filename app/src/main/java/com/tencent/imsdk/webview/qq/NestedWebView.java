package com.tencent.imsdk.webview.qq;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.tencent.smtt.sdk.WebView;

public class NestedWebView extends WebView implements NestedScrollingChild, View.OnTouchListener {
    private NestedScrollingChildHelper mChildHelper;
    private int mLastY;
    private int mNestedOffsetY;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;

    public NestedWebView(Context context) {
        this(context, (AttributeSet) null);
    }

    public NestedWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842885);
    }

    public NestedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        setOnTouchListener(this);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        MotionEvent event = MotionEvent.obtain(ev);
        int action = MotionEventCompat.getActionMasked(event);
        if (action == 0) {
            this.mNestedOffsetY = 0;
        }
        int eventY = (int) event.getY();
        event.offsetLocation(0.0f, (float) this.mNestedOffsetY);
        switch (action) {
            case 0:
                boolean returnValue = super.onTouchEvent(event);
                this.mLastY = eventY;
                startNestedScroll(2);
                return returnValue;
            case 1:
            case 3:
                boolean returnValue2 = super.onTouchEvent(event);
                stopNestedScroll();
                return returnValue2;
            case 2:
                int deltaY = this.mLastY - eventY;
                if (dispatchNestedPreScroll(0, deltaY, this.mScrollConsumed, this.mScrollOffset)) {
                    deltaY -= this.mScrollConsumed[1];
                    this.mLastY = eventY - this.mScrollOffset[1];
                    event.offsetLocation(0.0f, (float) (-this.mScrollOffset[1]));
                    this.mNestedOffsetY += this.mScrollOffset[1];
                }
                boolean returnValue3 = super.onTouchEvent(event);
                if (!dispatchNestedScroll(0, this.mScrollOffset[1], 0, deltaY, this.mScrollOffset)) {
                    return returnValue3;
                }
                event.offsetLocation(0.0f, (float) this.mScrollOffset[1]);
                this.mNestedOffsetY += this.mScrollOffset[1];
                this.mLastY -= this.mScrollOffset[1];
                return returnValue3;
            default:
                return false;
        }
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        this.mChildHelper.setNestedScrollingEnabled(enabled);
    }

    public boolean isNestedScrollingEnabled() {
        return this.mChildHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int axes) {
        return this.mChildHelper.startNestedScroll(axes);
    }

    public void stopNestedScroll() {
        this.mChildHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.mChildHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return this.mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return this.mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return this.mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return this.mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onTouch(View v, MotionEvent event) {
        return onTouchEvent(event);
    }
}
