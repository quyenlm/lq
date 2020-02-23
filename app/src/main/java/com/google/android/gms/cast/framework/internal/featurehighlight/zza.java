package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.R;
import com.google.android.gms.internal.hl;
import com.google.android.gms.internal.hu;
import com.google.android.gms.internal.ik;

public final class zza extends ViewGroup {
    private View targetView;
    private final int[] zzasP = new int[2];
    private final Rect zzasQ = new Rect();
    private final Rect zzasR = new Rect();
    /* access modifiers changed from: private */
    public final zzl zzasS;
    private final zzj zzasT;
    private zzi zzasU;
    @Nullable
    private View zzasV;
    /* access modifiers changed from: private */
    @Nullable
    public Animator zzasW;
    private final zzk zzasX;
    private final GestureDetectorCompat zzasY;
    @Nullable
    private GestureDetectorCompat zzasZ;
    /* access modifiers changed from: private */
    public zzh zzata;
    private boolean zzatb;

    public zza(Context context) {
        super(context);
        setId(R.id.cast_featurehighlight_view);
        setWillNotDraw(false);
        this.zzasT = new zzj(context);
        this.zzasT.setCallback(this);
        this.zzasS = new zzl(context);
        this.zzasS.setCallback(this);
        this.zzasX = new zzk(this);
        this.zzasY = new GestureDetectorCompat(context, new zzb(this));
        this.zzasY.setIsLongpressEnabled(false);
        setVisibility(8);
    }

    private final void zza(Animator animator) {
        if (this.zzasW != null) {
            this.zzasW.cancel();
        }
        this.zzasW = animator;
        this.zzasW.start();
    }

    /* access modifiers changed from: private */
    public final boolean zzc(float f, float f2) {
        return this.zzasR.contains(Math.round(f), Math.round(f2));
    }

    /* access modifiers changed from: private */
    public final Animator zznR() {
        zzj zzj = this.zzasT;
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator duration = ObjectAnimator.ofFloat(zzj, "scale", new float[]{1.0f, 1.1f}).setDuration(500);
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(zzj, "scale", new float[]{1.1f, 1.0f}).setDuration(500);
        ObjectAnimator duration3 = ObjectAnimator.ofPropertyValuesHolder(zzj, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("pulseScale", new float[]{1.1f, 2.0f}), PropertyValuesHolder.ofFloat("pulseAlpha", new float[]{1.0f, 0.0f})}).setDuration(500);
        animatorSet.play(duration);
        animatorSet.play(duration2).with(duration3).after(duration);
        animatorSet.setInterpolator(hu.zzEi());
        animatorSet.setStartDelay(500);
        hl.zza(animatorSet, (Runnable) null);
        return animatorSet;
    }

    /* access modifiers changed from: protected */
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams;
    }

    /* access modifiers changed from: protected */
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-2, -2);
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new ViewGroup.MarginLayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public final void onDraw(Canvas canvas) {
        canvas.save();
        this.zzasS.draw(canvas);
        this.zzasT.draw(canvas);
        if (this.targetView != null) {
            if (this.targetView.getParent() != null) {
                Bitmap createBitmap = Bitmap.createBitmap(this.targetView.getWidth(), this.targetView.getHeight(), Bitmap.Config.ARGB_8888);
                this.targetView.draw(new Canvas(createBitmap));
                int color = this.zzasS.getColor();
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);
                for (int i = 0; i < createBitmap.getHeight(); i++) {
                    for (int i2 = 0; i2 < createBitmap.getWidth(); i2++) {
                        int pixel = createBitmap.getPixel(i2, i);
                        if (Color.alpha(pixel) != 0) {
                            createBitmap.setPixel(i2, i, Color.argb(Color.alpha(pixel), red, green, blue));
                        }
                    }
                }
                canvas.drawBitmap(createBitmap, (float) this.zzasQ.left, (float) this.zzasQ.top, (Paint) null);
            }
            canvas.restore();
            return;
        }
        throw new IllegalStateException("Neither target view nor drawable was set");
    }

    /* access modifiers changed from: protected */
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.targetView == null) {
            throw new IllegalStateException("Target view must be set before layout");
        }
        if (this.targetView.getParent() != null) {
            int[] iArr = this.zzasP;
            View view = this.targetView;
            getLocationInWindow(iArr);
            int i5 = iArr[0];
            int i6 = iArr[1];
            view.getLocationInWindow(iArr);
            iArr[0] = iArr[0] - i5;
            iArr[1] = iArr[1] - i6;
        }
        this.zzasQ.set(this.zzasP[0], this.zzasP[1], this.zzasP[0] + this.targetView.getWidth(), this.zzasP[1] + this.targetView.getHeight());
        this.zzasR.set(i, i2, i3, i4);
        this.zzasS.setBounds(this.zzasR);
        this.zzasT.setBounds(this.zzasR);
        this.zzasX.zza(this.zzasQ, this.zzasR);
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(resolveSize(View.MeasureSpec.getSize(i), i), resolveSize(View.MeasureSpec.getSize(i2), i2));
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.zzatb = this.zzasQ.contains((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (this.zzatb) {
            if (this.zzasZ != null) {
                this.zzasZ.onTouchEvent(motionEvent);
                if (actionMasked == 1) {
                    motionEvent = MotionEvent.obtain(motionEvent);
                    motionEvent.setAction(3);
                }
            }
            if (this.targetView.getParent() != null) {
                this.targetView.onTouchEvent(motionEvent);
            }
        } else {
            this.zzasY.onTouchEvent(motionEvent);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.zzasS || drawable == this.zzasT || drawable == null;
    }

    public final void zza(View view, @Nullable View view2, boolean z, zzh zzh) {
        this.targetView = (View) ik.zzu(view);
        this.zzasV = null;
        this.zzata = (zzh) ik.zzu(zzh);
        this.zzasZ = new GestureDetectorCompat(getContext(), new zzc(this, view, true, zzh));
        this.zzasZ.setIsLongpressEnabled(false);
        setVisibility(4);
    }

    public final void zza(zzi zzi) {
        this.zzasU = (zzi) ik.zzu(zzi);
        addView(zzi.asView(), 0);
    }

    public final void zzaa(@ColorInt int i) {
        this.zzasS.setColor(i);
    }

    public final void zzg(@Nullable Runnable runnable) {
        addOnLayoutChangeListener(new zzd(this, (Runnable) null));
    }

    public final void zzh(@Nullable Runnable runnable) {
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.zzasU.asView(), "alpha", new float[]{0.0f}).setDuration(200);
        duration.setInterpolator(hu.zzEh());
        float exactCenterX = this.zzasQ.exactCenterX() - this.zzasS.getCenterX();
        float exactCenterY = this.zzasQ.exactCenterY() - this.zzasS.getCenterY();
        zzl zzl = this.zzasS;
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scale", new float[]{0.0f});
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("alpha", new int[]{0});
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(zzl, new PropertyValuesHolder[]{ofFloat, PropertyValuesHolder.ofFloat("translationX", new float[]{0.0f, exactCenterX}), PropertyValuesHolder.ofFloat("translationY", new float[]{0.0f, exactCenterY}), ofInt});
        ofPropertyValuesHolder.setInterpolator(hu.zzEh());
        Animator duration2 = ofPropertyValuesHolder.setDuration(200);
        Animator zznS = this.zzasT.zznS();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{duration, duration2, zznS});
        animatorSet.addListener(new zzg(this, runnable));
        zza((Animator) animatorSet);
    }

    public final void zzi(@Nullable Runnable runnable) {
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.zzasU.asView(), "alpha", new float[]{0.0f}).setDuration(200);
        duration.setInterpolator(hu.zzEh());
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.zzasS, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("scale", new float[]{1.125f}), PropertyValuesHolder.ofInt("alpha", new int[]{0})});
        ofPropertyValuesHolder.setInterpolator(hu.zzEh());
        Animator duration2 = ofPropertyValuesHolder.setDuration(200);
        Animator zznS = this.zzasT.zznS();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{duration, duration2, zznS});
        animatorSet.addListener(new zzf(this, runnable));
        zza((Animator) animatorSet);
    }

    public final void zznN() {
        if (this.targetView == null) {
            throw new IllegalStateException("Target view must be set before animation");
        }
        setVisibility(0);
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.zzasU.asView(), "alpha", new float[]{0.0f, 1.0f}).setDuration(350);
        duration.setInterpolator(hu.zzEg());
        Animator zze = this.zzasS.zze(this.zzasQ.exactCenterX() - this.zzasS.getCenterX(), this.zzasQ.exactCenterY() - this.zzasS.getCenterY());
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.zzasT, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("scale", new float[]{0.0f, 1.0f}), PropertyValuesHolder.ofFloat("alpha", new float[]{0.0f, 1.0f})});
        ofPropertyValuesHolder.setInterpolator(hu.zzEg());
        Animator duration2 = ofPropertyValuesHolder.setDuration(350);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{duration, zze, duration2});
        animatorSet.addListener(new zze(this));
        zza((Animator) animatorSet);
    }

    /* access modifiers changed from: package-private */
    public final View zznO() {
        return this.zzasU.asView();
    }

    /* access modifiers changed from: package-private */
    public final zzl zznP() {
        return this.zzasS;
    }

    /* access modifiers changed from: package-private */
    public final zzj zznQ() {
        return this.zzasT;
    }
}
