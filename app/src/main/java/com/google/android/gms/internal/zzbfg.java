package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v7.widget.helper.ItemTouchHelper;

public final class zzbfg extends Drawable implements Drawable.Callback {
    private int mFrom;
    private int zzaGA;
    private boolean zzaGj;
    private int zzaGo;
    private int zzaGp;
    private int zzaGq;
    private int zzaGr;
    private int zzaGs;
    private boolean zzaGt;
    private zzbfk zzaGu;
    private Drawable zzaGv;
    private Drawable zzaGw;
    private boolean zzaGx;
    private boolean zzaGy;
    private boolean zzaGz;
    private long zzagZ;

    public zzbfg(Drawable drawable, Drawable drawable2) {
        this((zzbfk) null);
        drawable = drawable == null ? zzbfi.zzaGB : drawable;
        this.zzaGv = drawable;
        drawable.setCallback(this);
        this.zzaGu.zzaGD |= drawable.getChangingConfigurations();
        drawable2 = drawable2 == null ? zzbfi.zzaGB : drawable2;
        this.zzaGw = drawable2;
        drawable2.setCallback(this);
        this.zzaGu.zzaGD |= drawable2.getChangingConfigurations();
    }

    zzbfg(zzbfk zzbfk) {
        this.zzaGo = 0;
        this.zzaGq = 255;
        this.zzaGs = 0;
        this.zzaGj = true;
        this.zzaGu = new zzbfk(zzbfk);
    }

    private final boolean canConstantState() {
        if (!this.zzaGx) {
            this.zzaGy = (this.zzaGv.getConstantState() == null || this.zzaGw.getConstantState() == null) ? false : true;
            this.zzaGx = true;
        }
        return this.zzaGy;
    }

    public final void draw(Canvas canvas) {
        boolean z = true;
        boolean z2 = false;
        switch (this.zzaGo) {
            case 1:
                this.zzagZ = SystemClock.uptimeMillis();
                this.zzaGo = 2;
                break;
            case 2:
                if (this.zzagZ >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.zzagZ)) / ((float) this.zzaGr);
                    if (uptimeMillis < 1.0f) {
                        z = false;
                    }
                    if (z) {
                        this.zzaGo = 0;
                    }
                    this.zzaGs = (int) ((Math.min(uptimeMillis, 1.0f) * ((float) this.zzaGp)) + 0.0f);
                    break;
                }
                break;
        }
        z2 = z;
        int i = this.zzaGs;
        boolean z3 = this.zzaGj;
        Drawable drawable = this.zzaGv;
        Drawable drawable2 = this.zzaGw;
        if (z2) {
            if (!z3 || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.zzaGq) {
                drawable2.setAlpha(this.zzaGq);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z3) {
            drawable.setAlpha(this.zzaGq - i);
        }
        drawable.draw(canvas);
        if (z3) {
            drawable.setAlpha(this.zzaGq);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zzaGq);
        }
        invalidateSelf();
    }

    public final int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.zzaGu.mChangingConfigurations | this.zzaGu.zzaGD;
    }

    public final Drawable.ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.zzaGu.mChangingConfigurations = getChangingConfigurations();
        return this.zzaGu;
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zzaGv.getIntrinsicHeight(), this.zzaGw.getIntrinsicHeight());
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zzaGv.getIntrinsicWidth(), this.zzaGw.getIntrinsicWidth());
    }

    public final int getOpacity() {
        if (!this.zzaGz) {
            this.zzaGA = Drawable.resolveOpacity(this.zzaGv.getOpacity(), this.zzaGw.getOpacity());
            this.zzaGz = true;
        }
        return this.zzaGA;
    }

    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final Drawable mutate() {
        if (!this.zzaGt && super.mutate() == this) {
            if (!canConstantState()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.zzaGv.mutate();
            this.zzaGw.mutate();
            this.zzaGt = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public final void onBoundsChange(Rect rect) {
        this.zzaGv.setBounds(rect);
        this.zzaGw.setBounds(rect);
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final void setAlpha(int i) {
        if (this.zzaGs == this.zzaGq) {
            this.zzaGs = i;
        }
        this.zzaGq = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zzaGv.setColorFilter(colorFilter);
        this.zzaGw.setColorFilter(colorFilter);
    }

    public final void startTransition(int i) {
        this.mFrom = 0;
        this.zzaGp = this.zzaGq;
        this.zzaGs = 0;
        this.zzaGr = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
        this.zzaGo = 1;
        invalidateSelf();
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final Drawable zzqW() {
        return this.zzaGw;
    }
}
