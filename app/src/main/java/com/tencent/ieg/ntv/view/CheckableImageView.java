package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.Checkable;

public class CheckableImageView extends AppCompatImageView implements Checkable {
    private static final int[] CheckedStateSet = {16842912};
    private boolean checked = false;

    public CheckableImageView(Context context) {
        super(context);
    }

    public CheckableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean b) {
        if (b != this.checked) {
            this.checked = b;
            refreshDrawableState();
        }
    }

    public void toggle() {
        setChecked(!this.checked);
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CheckedStateSet);
        }
        return drawableState;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }
}
