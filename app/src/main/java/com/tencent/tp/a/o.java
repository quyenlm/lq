package com.tencent.tp.a;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class o extends k implements View.OnClickListener {
    protected String i;
    protected String j;
    protected String k;
    protected String l;
    protected Button m;
    protected Button n;
    protected String o;
    protected String p;
    protected TextView q;
    private TextView r;
    private a s;

    public interface a {
        void a();

        void b();
    }

    public o(Context context, String str, String str2, String str3, String str4, a aVar) {
        super(context);
        this.i = str;
        this.j = str2;
        this.k = str3;
        this.l = str4;
        this.s = aVar;
    }

    public void a(String str, String str2, String str3, String str4, a aVar) {
        if (!(this.d == null || str == null)) {
            this.d.setText(str);
        }
        if (!(this.r == null || str2 == null)) {
            this.r.setText(str2);
        }
        if (!(this.m == null || str3 == null)) {
            if (str3.length() > 4) {
                this.m.setTextSize((float) i());
            }
            this.m.setText(str3);
        }
        if (this.n != null) {
            if (str4 != null) {
                this.n.setText(str4);
            } else {
                this.n.setVisibility(8);
            }
        }
        this.s = aVar;
    }

    /* access modifiers changed from: protected */
    public View c() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        int a2 = ae.a(this.a, 20);
        layoutParams.setMargins(a2, a2, a2, 0);
        layoutParams.weight = 1.0f;
        layoutParams.gravity = 17;
        TextView textView = new TextView(this.a);
        textView.setText(this.j);
        textView.setTextColor(ad.d());
        textView.setTextSize(16.0f);
        textView.setLayoutParams(layoutParams);
        textView.setMaxLines(j());
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.r = textView;
        return textView;
    }

    /* access modifiers changed from: protected */
    public View d() {
        if (this.o == null || this.p == null) {
            return null;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        int a2 = ae.a(this.a, 20);
        layoutParams.setMargins(a2, 0, a2, a2 / 2);
        TextView textView = new TextView(this.a);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(14.0f);
        textView.setText(this.o);
        textView.setGravity(17);
        textView.setTextColor(ad.h());
        textView.getPaint().setFlags(8);
        this.q = textView;
        this.q.setOnClickListener(this);
        return textView;
    }

    /* access modifiers changed from: protected */
    public View e() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = ae.a(this.a, k());
        layoutParams.height = ae.a(this.a, l());
        Button button = new Button(this.a);
        button.setText(this.k);
        button.setGravity(17);
        button.setTextColor(ad.e());
        if (this.k.length() > 4) {
            button.setTextSize((float) i());
        } else {
            button.setTextSize((float) h());
        }
        Drawable a2 = ad.a(this.a);
        if (a2 != null) {
            button.setBackgroundDrawable(a2);
        }
        button.setLayoutParams(layoutParams);
        this.m = button;
        this.m.setOnClickListener(this);
        return button;
    }

    /* access modifiers changed from: protected */
    public View f() {
        if (this.l == null) {
            return null;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = ae.a(this.a, k());
        layoutParams.height = ae.a(this.a, l());
        layoutParams.setMargins(ae.a(this.a, m()), 0, 0, 0);
        Button button = new Button(this.a);
        button.setText(this.l);
        button.setGravity(17);
        button.setTextColor(ad.e());
        if (this.l.length() > 4) {
            button.setTextSize((float) i());
        } else {
            button.setTextSize((float) h());
        }
        Drawable b = ad.b(this.a);
        if (b != null) {
            button.setBackgroundDrawable(b);
        }
        button.setLayoutParams(layoutParams);
        this.n = button;
        this.n.setOnClickListener(this);
        return button;
    }

    public void n() {
        if (!this.f) {
            this.c.addView(a(this.i), this.e);
            this.f = true;
        }
    }

    public void onClick(View view) {
        if (view != null) {
            a(view);
        }
        if (view == this.m) {
            if (!this.g) {
                a();
            }
            if (this.s != null) {
                this.s.a();
            }
        } else if (view == this.n) {
            if (!this.g) {
                a();
            }
            if (this.h) {
                a();
            }
            if (this.s != null) {
                this.s.b();
            }
        } else if (view == this.q) {
            this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.p)));
        }
    }
}
