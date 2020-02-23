package com.tencent.tp.a;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class l extends View {
    protected Context a;
    protected Drawable b;
    protected Drawable c;
    protected int d;

    public l(Context context) {
        super(context);
        this.a = context;
    }

    public l(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = context;
    }

    /* access modifiers changed from: package-private */
    public int a(int i) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            return size;
        }
        return 500;
    }

    public void a(Drawable drawable, Drawable drawable2) {
        this.c = drawable;
        this.b = drawable2;
    }

    /* access modifiers changed from: package-private */
    public int b(int i) {
        return ae.a(this.a, 30);
    }

    public void onDraw(Canvas canvas) {
        if (this.c == null || this.b == null) {
            super.onDraw(canvas);
            return;
        }
        this.b.setBounds(0, 0, getWidth(), getHeight());
        this.b.draw(canvas);
        int width = (getWidth() * this.d) / 100;
        if (width > 0) {
            this.c.setBounds(0, 0, width, getHeight());
            this.c.draw(canvas);
        }
    }

    public void onMeasure(int i, int i2) {
        setMeasuredDimension(a(i), b(i2));
    }

    public void setProgress(int i) {
        this.d = i;
        invalidate();
    }
}
