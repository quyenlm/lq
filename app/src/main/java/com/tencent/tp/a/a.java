package com.tencent.tp.a;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Timer;

public class a implements View.OnClickListener {
    public static int a = 0;
    public static int b = 1;
    public static int c = 2;
    public static int d = 3;
    protected Context e;
    protected WindowManager f = ((WindowManager) this.e.getSystemService("window"));
    protected View g = h();
    protected WindowManager.LayoutParams h = w();
    protected boolean i;
    protected Timer j;
    protected String k;
    protected String l;
    protected String m;
    protected String n;
    protected String o;
    protected String p;
    protected int q;
    protected boolean r;
    protected TextView s;
    protected Button t;
    protected Button u;
    protected C0032a v;

    /* renamed from: com.tencent.tp.a.a$a  reason: collision with other inner class name */
    public interface C0032a {
        void a(a aVar);

        void b(a aVar);

        void c(a aVar);
    }

    public a(Context context, String str, String str2, String str3, String str4, String str5, String str6, int i2, boolean z, C0032a aVar) {
        this.e = context;
        this.k = str;
        this.l = str2;
        this.m = str3;
        this.n = str4;
        this.o = str5;
        this.p = str6;
        this.q = i2;
        this.r = z;
        this.v = aVar;
    }

    private LinearLayout v() {
        LinearLayout linearLayout = new LinearLayout(this.e);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        int a2 = ae.a(this.e, 80);
        int a3 = ae.a(this.e, 40);
        int a4 = ae.a(this.e, 40);
        if (this.o == null && this.p == null) {
            a3 += ae.a(this.e, 20);
            a4 += ae.a(this.e, 40);
        }
        layoutParams.setMargins(a2, a3, a2, a4);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(1);
        linearLayout.addView(i());
        linearLayout.addView(j());
        if (!(this.o == null && this.p == null)) {
            linearLayout.addView(o());
        }
        return linearLayout;
    }

    private WindowManager.LayoutParams w() {
        int a2 = ae.a(this.e);
        int b2 = ae.b(this.e);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 9999, 8, -2);
        layoutParams.gravity = 51;
        layoutParams.width = a2;
        layoutParams.height = b2;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.type = 1003;
        layoutParams.flags = 0;
        return layoutParams;
    }

    /* access modifiers changed from: protected */
    public Button a(boolean z) {
        if (z) {
            if (this.o == null) {
                return null;
            }
        } else if (this.p == null) {
            return null;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        int a2 = ae.a(this.e, 5);
        layoutParams.setMargins(a2 * 2, a2, a2 * 2, a2);
        m h2 = z ? this.r ? h.h(this.e) : h.g(this.e) : h.f(this.e);
        if (!(h2 == null || h2.b == 0 || h2.c == 0)) {
            layoutParams.width = h.a(this.e, h2.b);
            layoutParams.height = h.a(this.e, h2.c);
        }
        Button button = new Button(this.e);
        button.setLayoutParams(layoutParams);
        button.setText(z ? s() : t());
        if (h2 != null) {
            h.a((View) button, h2.a);
        }
        button.setTextSize(h.b(this.e));
        button.setTextColor(h.a(112, 44, 7, 255));
        button.setTypeface(Typeface.defaultFromStyle(1));
        return button;
    }

    public String a() {
        return this.k;
    }

    public void b() {
        if (!this.i && this.f != null && this.g != null && this.h != null) {
            this.f.addView(this.g, this.h);
            this.i = true;
        }
    }

    public void c() {
        if (!(!this.i || this.f == null || this.g == null)) {
            this.f.removeView(this.g);
            this.i = false;
        }
        e();
    }

    /* access modifiers changed from: protected */
    public void d() {
        if (this.j != null) {
            this.j.cancel();
        }
        this.j = new Timer();
        this.j.schedule(new b(this), 0, 1000);
    }

    /* access modifiers changed from: protected */
    public void e() {
        if (this.j != null) {
            this.j.cancel();
            this.j = null;
        }
    }

    /* access modifiers changed from: protected */
    public void f() {
    }

    /* access modifiers changed from: protected */
    public boolean g() {
        return ae.a(this.e) > ae.b(this.e);
    }

    /* access modifiers changed from: protected */
    public LinearLayout h() {
        LinearLayout linearLayout = new LinearLayout(this.e);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setOrientation(1);
        linearLayout.setGravity(17);
        linearLayout.setBackgroundColor(h.a(0, 0, 0, 128));
        LinearLayout v2 = v();
        if (v2 != null) {
            linearLayout.addView(v2);
        }
        return linearLayout;
    }

    /* access modifiers changed from: protected */
    public View i() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        m c2 = h.c(this.e);
        if (c2 != null && c2.c > 0) {
            layoutParams.height = h.a(this.e, c2.c);
        }
        layoutParams.weight = 0.0f;
        TextView textView = new TextView(this.e);
        textView.setText(this.l);
        textView.setGravity(17);
        textView.setLayoutParams(layoutParams);
        if (c2 != null) {
            h.a((View) textView, c2.a);
        }
        textView.setTextColor(h.c);
        textView.setTextSize(h.a(this.e));
        textView.setTypeface(Typeface.defaultFromStyle(1));
        return textView;
    }

    /* access modifiers changed from: protected */
    public View j() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.weight = 1.0f;
        LinearLayout linearLayout = new LinearLayout(this.e);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(1);
        m d2 = h.d(this.e);
        if (d2 != null) {
            h.a((View) linearLayout, d2.a);
        }
        linearLayout.addView(l());
        if (this.n != null) {
            linearLayout.addView(m());
        }
        int i2 = 0;
        if (this.e.getResources().getDisplayMetrics().density < 1.8f) {
            i2 = 3;
        }
        int a2 = ae.a(this.e, i2 + 12);
        linearLayout.setPadding(a2, ae.a(this.e, 6), a2, ae.a(this.e, i2 + i2 + 12));
        return linearLayout;
    }

    /* access modifiers changed from: protected */
    public View k() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        TextView textView = new TextView(this.e);
        textView.setText(Html.fromHtml(this.m));
        textView.setTextSize(h.b(this.e));
        textView.setLayoutParams(layoutParams);
        int a2 = ae.a(this.e, 8);
        textView.setPadding(a2, ae.a(this.e, 5), a2, a2);
        return textView;
    }

    /* access modifiers changed from: protected */
    public View l() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0);
        layoutParams.weight = 1.0f;
        ScrollView scrollView = new ScrollView(this.e);
        scrollView.setLayoutParams(layoutParams);
        scrollView.addView(k());
        scrollView.setVerticalFadingEdgeEnabled(false);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setFadingEdgeLength(0);
        m e2 = h.e(this.e);
        if (e2 != null) {
            h.a((View) scrollView, e2.a);
        }
        return scrollView;
    }

    /* access modifiers changed from: protected */
    public View m() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.weight = 0.0f;
        int a2 = ae.a(this.e, 5);
        float b2 = h.b(this.e);
        layoutParams.setMargins(a2, a2, a2, 0);
        layoutParams.height = ae.a(this.e, 4.0f + b2);
        TextView textView = new TextView(this.e);
        textView.setText(r());
        textView.setLayoutParams(layoutParams);
        textView.setGravity(17);
        textView.setTextColor(n());
        textView.setTextSize(b2);
        textView.setTypeface(Typeface.defaultFromStyle(1));
        this.s = textView;
        return textView;
    }

    /* access modifiers changed from: protected */
    public int n() {
        return h.d;
    }

    /* access modifiers changed from: protected */
    public View o() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.weight = 0.0f;
        LinearLayout linearLayout = new LinearLayout(this.e);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        Button p2 = p();
        if (p2 != null) {
            p2.setOnClickListener(this);
            this.t = p2;
            linearLayout.addView(p2);
        }
        Button q2 = q();
        if (q2 != null) {
            q2.setOnClickListener(this);
            this.u = q2;
            linearLayout.addView(q2);
        }
        return linearLayout;
    }

    public void onClick(View view) {
        if (view != null) {
            if (this.t == view) {
                if (!this.r) {
                    this.v.a(this);
                }
            } else if (this.u == view) {
                this.v.b(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Button p() {
        return a(true);
    }

    /* access modifiers changed from: protected */
    public Button q() {
        return a(false);
    }

    /* access modifiers changed from: protected */
    public String r() {
        return this.n;
    }

    /* access modifiers changed from: protected */
    public String s() {
        return this.o;
    }

    /* access modifiers changed from: protected */
    public String t() {
        return this.p;
    }

    /* access modifiers changed from: protected */
    public WindowManager.LayoutParams u() {
        return new WindowManager.LayoutParams(-1, -2);
    }
}
