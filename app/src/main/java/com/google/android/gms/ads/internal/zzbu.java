package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ViewSwitcher;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzahq;
import com.google.android.gms.internal.zzaix;
import com.google.android.gms.internal.zzaka;
import java.util.ArrayList;

public final class zzbu extends ViewSwitcher {
    private final zzahq zzwB;
    @Nullable
    private final zzaix zzwC;
    private boolean zzwD = true;

    public zzbu(Context context, String str, String str2, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        super(context);
        this.zzwB = new zzahq(context);
        this.zzwB.setAdUnitId(str);
        this.zzwB.zzaO(str2);
        if (context instanceof Activity) {
            this.zzwC = new zzaix((Activity) context, this, onGlobalLayoutListener, onScrollChangedListener);
        } else {
            this.zzwC = new zzaix((Activity) null, this, onGlobalLayoutListener, onScrollChangedListener);
        }
        this.zzwC.zzig();
    }

    /* access modifiers changed from: protected */
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.zzwC != null) {
            this.zzwC.onAttachedToWindow();
        }
    }

    /* access modifiers changed from: protected */
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.zzwC != null) {
            this.zzwC.onDetachedFromWindow();
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.zzwD) {
            return false;
        }
        this.zzwB.zzf(motionEvent);
        return false;
    }

    public final void removeAllViews() {
        int i = 0;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt != null && (childAt instanceof zzaka)) {
                arrayList.add((zzaka) childAt);
            }
        }
        super.removeAllViews();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zzaka) obj).destroy();
        }
    }

    public final zzahq zzcf() {
        return this.zzwB;
    }

    public final void zzcg() {
        zzafr.v("Disable position monitoring on adFrame.");
        if (this.zzwC != null) {
            this.zzwC.zzih();
        }
    }

    public final void zzch() {
        zzafr.v("Enable debug gesture detector on adFrame.");
        this.zzwD = true;
    }

    public final void zzci() {
        zzafr.v("Disable debug gesture detector on adFrame.");
        this.zzwD = false;
    }
}
