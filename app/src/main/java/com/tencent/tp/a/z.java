package com.tencent.tp.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tencent.tp.a.o;

public class z extends k implements View.OnClickListener {
    private l i;
    private TextView j;
    private TextView k;
    private String l;
    private o.a m;
    private Button n;

    public z(Context context) {
        super(context);
    }

    public void a(int i2) {
        if (this.i != null) {
            this.i.setProgress(i2);
        }
    }

    public void a(String str, String str2, o.a aVar) {
        if (!this.f) {
            this.l = str2;
            this.m = aVar;
            this.c.addView(a(str), this.e);
            this.f = true;
        }
    }

    public void b(String str) {
        if (this.j != null) {
            this.j.setText(str);
        }
    }

    /* access modifiers changed from: protected */
    public View c() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        LinearLayout linearLayout = new LinearLayout(this.a);
        int a = ae.a(this.a, 20);
        if (this.l != null) {
            layoutParams.setMargins(a, a, a, 0);
        } else {
            layoutParams.setMargins(a, a, a, a);
        }
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.setMargins(0, 0, 0, ae.a(this.a, 5));
        l lVar = new l(this.a);
        lVar.setLayoutParams(layoutParams2);
        lVar.a(ad.d(this.a), ad.c(this.a));
        linearLayout.addView(lVar);
        this.i = lVar;
        linearLayout.addView(n());
        return linearLayout;
    }

    public void c(String str) {
        if (this.k != null) {
            this.k.setText(str);
        }
    }

    /* access modifiers changed from: protected */
    public View e() {
        if (this.l == null) {
            return null;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = ae.a(this.a, k());
        layoutParams.height = ae.a(this.a, l());
        Button button = new Button(this.a);
        button.setText(this.l);
        button.setTextColor(ad.e());
        button.setTextSize((float) h());
        Drawable a = ad.a(this.a);
        if (a != null) {
            button.setBackgroundDrawable(a);
        }
        button.setLayoutParams(layoutParams);
        this.n = button;
        this.n.setOnClickListener(this);
        return button;
    }

    /* access modifiers changed from: protected */
    public View n() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        LinearLayout linearLayout = new LinearLayout(this.a);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(0);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        TextView textView = new TextView(this.a);
        textView.setText("0");
        textView.setTextColor(ad.g());
        textView.setTextSize(18.0f);
        textView.setLayoutParams(layoutParams2);
        linearLayout.addView(textView);
        this.j = textView;
        TextView textView2 = new TextView(this.a);
        textView2.setText("/0");
        textView2.setTextColor(ad.d());
        textView2.setTextSize(18.0f);
        textView2.setLayoutParams(layoutParams2);
        linearLayout.addView(textView2);
        this.k = textView2;
        return linearLayout;
    }

    public void onClick(View view) {
        if (view == this.n) {
            a();
            if (this.m != null) {
                this.m.a();
            }
        }
    }
}
