package com.tencent.imsdk.webview.qq;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.tencent.imsdk.tool.etc.IMLogger;

class ShareAnimMenu {
    private ViewGroup contentContainer = null;
    private Context context;
    /* access modifiers changed from: private */
    public ViewGroup decorView;
    /* access modifiers changed from: private */
    public boolean isDismissed;
    /* access modifiers changed from: private */
    public ViewGroup rootView;

    public interface IDismissListener {
        void onDismiss();
    }

    ShareAnimMenu(Context context2, int layoutId) {
        this.context = context2;
        LayoutInflater layoutInflater = LayoutInflater.from(context2);
        this.decorView = (ViewGroup) ((Activity) context2).getWindow().getDecorView().findViewById(16908290);
        this.rootView = (ViewGroup) layoutInflater.inflate(ResourceUtil.getLayoutId(context2, "layout_share_container"), this.decorView, false);
        if (this.rootView != null) {
            this.contentContainer = (ViewGroup) this.rootView.findViewById(ResourceUtil.getId(context2, "share_view_container"));
            if (this.contentContainer != null) {
                this.contentContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -2, 80));
                LayoutInflater.from(context2).inflate(layoutId, this.contentContainer);
            }
        }
    }

    private boolean isShowing() {
        return this.decorView.findViewById(ResourceUtil.getId(this.decorView.getContext(), "share_bg_container")) != null;
    }

    /* access modifiers changed from: package-private */
    public void show() {
        if (!isShowing()) {
            this.decorView.addView(this.rootView);
            View view = this.rootView.findViewById(ResourceUtil.getId(this.decorView.getContext(), "share_bg_container"));
            if (view != null) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() != 0) {
                            return false;
                        }
                        ShareAnimMenu.this.dismiss((IDismissListener) null);
                        return false;
                    }
                });
                int resID = ResourceUtil.getAnimId(this.context, "slide_in_bottom");
                if (this.contentContainer != null) {
                    try {
                        this.contentContainer.startAnimation(AnimationUtils.loadAnimation(this.context, resID));
                    } catch (Resources.NotFoundException e) {
                        IMLogger.e("loadAnimation slide_in_bottom error!");
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dismiss(final IDismissListener listener) {
        if (!this.isDismissed) {
            Animation outAnim = null;
            try {
                outAnim = AnimationUtils.loadAnimation(this.context, ResourceUtil.getAnimId(this.context, "slide_out_bottom"));
            } catch (Resources.NotFoundException e) {
                IMLogger.e("loadAnimation slide_out_bottom error!");
            }
            if (outAnim != null) {
                outAnim.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        ShareAnimMenu.this.decorView.post(new Runnable() {
                            public void run() {
                                ShareAnimMenu.this.decorView.removeView(ShareAnimMenu.this.rootView);
                                boolean unused = ShareAnimMenu.this.isDismissed = false;
                                if (listener != null) {
                                    listener.onDismiss();
                                }
                            }
                        });
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                if (this.contentContainer != null) {
                    this.contentContainer.startAnimation(outAnim);
                    this.isDismissed = true;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public View findViewById(int id) {
        return this.contentContainer.findViewById(id);
    }
}
