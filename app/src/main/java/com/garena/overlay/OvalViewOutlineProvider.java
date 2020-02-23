package com.garena.overlay;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

@TargetApi(21)
final class OvalViewOutlineProvider extends ViewOutlineProvider {
    OvalViewOutlineProvider() {
    }

    public void getOutline(View view, Outline outline) {
        outline.setOval(0, 0, view.getWidth(), view.getHeight());
    }
}
