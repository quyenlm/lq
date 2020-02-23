package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

@zzzn
@TargetApi(19)
public final class zzyc extends zzxz {
    private Object zzQZ = new Object();
    private PopupWindow zzRa;
    private boolean zzRb = false;

    zzyc(Context context, zzafg zzafg, zzaka zzaka, zzxy zzxy) {
        super(context, zzafg, zzaka, zzxy);
    }

    private final void zzgr() {
        synchronized (this.zzQZ) {
            this.zzRb = true;
            if ((this.mContext instanceof Activity) && ((Activity) this.mContext).isDestroyed()) {
                this.zzRa = null;
            }
            if (this.zzRa != null) {
                if (this.zzRa.isShowing()) {
                    this.zzRa.dismiss();
                }
                this.zzRa = null;
            }
        }
    }

    public final void cancel() {
        zzgr();
        super.cancel();
    }

    /* access modifiers changed from: protected */
    public final void zzgq() {
        Window window = this.mContext instanceof Activity ? ((Activity) this.mContext).getWindow() : null;
        if (window != null && window.getDecorView() != null && !((Activity) this.mContext).isDestroyed()) {
            FrameLayout frameLayout = new FrameLayout(this.mContext);
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            frameLayout.addView(this.zzJH.getView(), -1, -1);
            synchronized (this.zzQZ) {
                if (!this.zzRb) {
                    this.zzRa = new PopupWindow(frameLayout, 1, 1, false);
                    this.zzRa.setOutsideTouchable(true);
                    this.zzRa.setClippingEnabled(false);
                    zzafr.zzaC("Displaying the 1x1 popup off the screen.");
                    try {
                        this.zzRa.showAtLocation(window.getDecorView(), 0, -1, -1);
                    } catch (Exception e) {
                        this.zzRa = null;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zzr(int i) {
        zzgr();
        super.zzr(i);
    }
}
