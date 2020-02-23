package com.tencent.tp.a;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tencent.qqgamemi.BuildConfig;

public class aa extends k {
    private String i;

    public aa(Context context) {
        super(context, false);
    }

    private ImageView n() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = 51;
        layoutParams.height = 56;
        ImageView imageView = new ImageView(this.a);
        imageView.setImageDrawable(ad.e(this.a));
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    private TextView o() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        int a = ae.a(this.a, 5);
        layoutParams.setMargins(a + a, 0, a, 0);
        TextView textView = new TextView(this.a);
        textView.setText(this.i);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(ad.f());
        textView.setTextSize(14.0f);
        return textView;
    }

    public void b(String str) {
        if (!this.f) {
            this.i = str;
            this.c.addView(a((String) null, true, true), this.e);
            this.f = true;
            new ai(0, BuildConfig.VERSION_CODE, true, new ab(this)).b();
        }
    }

    /* access modifiers changed from: protected */
    public View c() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        int a = ae.a(this.a, 7);
        layoutParams.setMargins(a * 2, a, a * 2, a);
        LinearLayout linearLayout = new LinearLayout(this.a);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        linearLayout.addView(n());
        linearLayout.addView(o());
        return linearLayout;
    }
}
