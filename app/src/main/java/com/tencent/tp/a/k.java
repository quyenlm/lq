package com.tencent.tp.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class k {
    protected Context a;
    protected View b;
    protected WindowManager c;
    protected TextView d;
    protected WindowManager.LayoutParams e;
    protected boolean f;
    protected boolean g;
    protected boolean h;

    public k(Context context) {
        this.a = context;
        a(true);
    }

    public k(Context context, boolean z) {
        this.a = context;
        a(z);
    }

    private WindowManager.LayoutParams d(boolean z) {
        int a2 = ae.a(this.a);
        int b2 = ae.b(this.a);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 9999, 8, -2);
        layoutParams.gravity = 51;
        layoutParams.width = a2;
        layoutParams.height = b2;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.type = 1003;
        if (z) {
            layoutParams.flags = 0;
        } else {
            layoutParams.flags = 56;
        }
        return layoutParams;
    }

    private boolean n() {
        return ae.a(this.a) > ae.b(this.a);
    }

    private View o() {
        LinearLayout linearLayout = new LinearLayout(this.a);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        int a2 = ae.a(this.a, 20);
        layoutParams.setMargins(a2, a2 / 2, a2, a2 / 2);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setGravity(17);
        View e2 = e();
        if (e2 != null) {
            linearLayout.addView(e2);
        }
        View f2 = f();
        if (f2 != null) {
            linearLayout.addView(f2);
        }
        if (e2 == null && f2 == null) {
            return null;
        }
        return linearLayout;
    }

    /* access modifiers changed from: protected */
    public LinearLayout a(String str) {
        return a(str, false, false);
    }

    /* access modifiers changed from: protected */
    public LinearLayout a(String str, boolean z, boolean z2) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        LinearLayout linearLayout = new LinearLayout(this.a);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(17);
        linearLayout.setBackgroundDrawable(ad.a());
        if (z2) {
            linearLayout.setBackgroundColor(ad.a(0, 0, 0, 0));
        }
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(z ? -2 : -1, -2);
        int a2 = ae.a(this.a, 20);
        int i = n() ? a2 * 3 : a2;
        layoutParams2.setMargins(i, a2, i, a2);
        LinearLayout linearLayout2 = new LinearLayout(this.a);
        linearLayout2.setLayoutParams(layoutParams2);
        linearLayout2.setGravity(17);
        Drawable b2 = ad.b();
        if (b2 != null) {
            linearLayout2.setBackgroundDrawable(b2);
        }
        linearLayout2.setOrientation(1);
        linearLayout.addView(linearLayout2);
        if (str != null) {
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
            LinearLayout linearLayout3 = new LinearLayout(this.a);
            layoutParams3.setMargins(1, 1, 1, 1);
            linearLayout3.setLayoutParams(layoutParams3);
            Drawable c2 = ad.c();
            if (c2 != null) {
                linearLayout3.setBackgroundDrawable(c2);
            }
            linearLayout3.setGravity(17);
            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
            int a3 = ae.a(this.a, 13);
            layoutParams4.setMargins(0, a3, 0, a3);
            TextView textView = new TextView(this.a);
            textView.setText(str);
            textView.setTextColor(-1);
            textView.setTextSize((float) h());
            textView.setLayoutParams(layoutParams4);
            linearLayout3.addView(textView);
            this.d = textView;
            linearLayout2.addView(linearLayout3);
        }
        View c3 = c();
        if (c3 != null) {
            linearLayout2.addView(c3);
        }
        View o = o();
        if (o != null) {
            linearLayout2.addView(o);
        }
        View d2 = d();
        if (d2 != null) {
            linearLayout2.addView(d2);
        }
        this.b = linearLayout;
        return linearLayout;
    }

    public void a() {
        if (this.f && this.b != null) {
            this.c.removeView(this.b);
            this.f = false;
        }
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        int a2 = ae.a(this.a);
        int width = view.getWidth();
        int height = view.getHeight();
        int b2 = ae.b(this.a, a2);
        int b3 = ae.b(this.a, width);
        Log.d("tpsafe", "screen:" + a2 + " screen:" + b2 + " width:" + b3 + " height:" + ae.b(this.a, height));
    }

    /* access modifiers changed from: protected */
    public void a(boolean z) {
        if (this.c == null) {
            this.c = (WindowManager) this.a.getSystemService("window");
            this.e = d(z);
        }
    }

    public void b(boolean z) {
        this.g = z;
    }

    public boolean b() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public View c() {
        return null;
    }

    public void c(boolean z) {
        this.h = z;
    }

    /* access modifiers changed from: protected */
    public View d() {
        return null;
    }

    /* access modifiers changed from: protected */
    public View e() {
        return null;
    }

    /* access modifiers changed from: protected */
    public View f() {
        return null;
    }

    /* access modifiers changed from: protected */
    public WindowManager.LayoutParams g() {
        return new WindowManager.LayoutParams(-1, -2);
    }

    /* access modifiers changed from: protected */
    public int h() {
        return 22;
    }

    /* access modifiers changed from: protected */
    public int i() {
        return 18;
    }

    /* access modifiers changed from: protected */
    public int j() {
        int b2 = ae.b(this.a, ae.a(this.a));
        if (b2 > 400) {
            return 5;
        }
        return b2 > 300 ? 7 : 6;
    }

    /* access modifiers changed from: protected */
    public int k() {
        int b2 = ae.b(this.a, ae.a(this.a));
        if (b2 > 400) {
            return 147;
        }
        if (b2 > 350) {
            return 130;
        }
        return b2 > 300 ? 110 : 100;
    }

    /* access modifiers changed from: protected */
    public int l() {
        return 45;
    }

    /* access modifiers changed from: protected */
    public int m() {
        return ae.b(this.a, ae.a(this.a)) > 400 ? 20 : 15;
    }
}
