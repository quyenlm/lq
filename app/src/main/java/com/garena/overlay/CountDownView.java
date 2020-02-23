package com.garena.overlay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.garena.msdk.R;

@SuppressLint({"ViewConstructor"})
final class CountDownView extends RelativeLayout {
    private static final int COUNTDOWN_DELAY = 1000;
    ImageView countdownImageView = ((ImageView) findViewById(R.id.video_img_countdown));
    /* access modifiers changed from: private */
    public final Listener listener;

    interface Listener {
        void onCountDownComplete();
    }

    private CountDownView(Context context, Listener listener2) {
        super(context);
        this.listener = listener2;
        inflate(context, R.layout.msdk_video_countdown_view, this);
    }

    static CountDownView create(Context context, Listener listener2) {
        return new CountDownView(context, listener2);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        showCountDown();
    }

    private void showCountDown() {
        countdown(5);
    }

    /* access modifiers changed from: private */
    public void countdown(final int index) {
        if (index == 0) {
            setVisibility(8);
        } else {
            this.countdownImageView.setImageResource(getResources().getIdentifier("msdk_counting" + index, "drawable", getContext().getPackageName()));
        }
        postDelayed(new Runnable() {
            public void run() {
                if (index > 0) {
                    CountDownView.this.countdown(index - 1);
                } else {
                    CountDownView.this.listener.onCountDownComplete();
                }
            }
        }, 1000);
    }
}
