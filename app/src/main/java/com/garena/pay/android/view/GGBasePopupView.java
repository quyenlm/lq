package com.garena.pay.android.view;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import com.garena.pay.android.helper.Utils;

public class GGBasePopupView {
    public static final int STYLE_DEFAULT = 0;
    public static final int STYLE_FULL_WIDTH = 1;
    public static final int STYLE_GRID = 2;
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        public void onDismiss() {
            GGBasePopupView.this.mPopup.setBackgroundDrawable((Drawable) null);
            GGBasePopupView.this.mPopup = null;
            GGBasePopupView.this.notifyDismissListener();
        }
    };
    private DismissListener listener;
    protected PopupWindow mPopup;

    public interface DismissListener {
        void onDismissed();
    }

    public GGBasePopupView(View contentView) {
        this.mPopup = new PopupWindow(contentView, -1, -1, true);
        this.mPopup.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopup.setInputMethodMode(1);
        this.mPopup.setFocusable(true);
        this.mPopup.update();
        this.mPopup.setOnDismissListener(this.dismissListener);
    }

    public GGBasePopupView(View contentView, boolean isDropDown, boolean isCancellable) {
        this.mPopup = new PopupWindow(contentView, -1, -1, true);
        this.mPopup.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopup.setInputMethodMode(1);
        this.mPopup.setFocusable(true);
        this.mPopup.update();
        if (isCancellable) {
            this.mPopup.setTouchInterceptor(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() != 0) {
                        return true;
                    }
                    GGBasePopupView.this.dismiss();
                    return true;
                }
            });
        }
        this.mPopup.setOnDismissListener(this.dismissListener);
    }

    public GGBasePopupView(View contentView, int style) {
        if (style == 0 || style == 2) {
            this.mPopup = new PopupWindow(contentView, -2, -2, true);
        }
        if (style == 1) {
            this.mPopup = new PopupWindow(contentView, -1, -2, true);
        }
        this.mPopup.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopup.setFocusable(true);
        this.mPopup.update();
        this.mPopup.setOnDismissListener(this.dismissListener);
    }

    /* access modifiers changed from: private */
    public void notifyDismissListener() {
        if (this.listener != null) {
            this.listener.onDismissed();
        }
    }

    public void showAtCenter(View anchor) {
        if (anchor != null && Utils.isActivityContextValid(anchor.getContext())) {
            this.mPopup.showAtLocation(anchor, 17, 0, 0);
        }
    }

    public void showAtCenter(View anchor, int gravity, int x, int y) {
        if (anchor != null && Utils.isActivityContextValid(anchor.getContext())) {
            this.mPopup.showAtLocation(anchor, gravity, x, y);
        }
    }

    public void dismiss() {
        if (this.mPopup != null) {
            try {
                this.mPopup.dismiss();
            } catch (Exception e) {
            }
        }
    }

    public void successfulDismiss() {
        if (this.mPopup != null) {
            try {
                this.mPopup.setOnDismissListener((PopupWindow.OnDismissListener) null);
                this.mPopup.dismiss();
            } catch (Exception e) {
            }
        }
    }

    public boolean isShowing() {
        return this.mPopup != null && this.mPopup.isShowing();
    }

    public void setBackgroundBlack() {
        this.mPopup.setBackgroundDrawable(new ColorDrawable(-16777216));
        this.mPopup.update();
    }

    public void showAsDropDown(View anchor) {
        if (anchor != null && Utils.isActivityContextValid(anchor.getContext())) {
            this.mPopup.showAsDropDown(anchor);
        }
    }

    public void setListener(DismissListener listener2) {
        this.listener = listener2;
    }
}
