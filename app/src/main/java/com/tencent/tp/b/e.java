package com.tencent.tp.b;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tencent.tp.c.j;
import com.tencent.tp.q;

public class e {
    private static int c;
    protected Context a;
    protected AlertDialog b;

    public e(Context context) {
        this.a = context;
    }

    public static boolean a() {
        return c > 0;
    }

    static /* synthetic */ int e() {
        int i = c;
        c = i - 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public LinearLayout a(String str) {
        ViewGroup.LayoutParams c2 = c();
        LinearLayout linearLayout = new LinearLayout(this.a);
        linearLayout.setLayoutParams(c2);
        linearLayout.setOrientation(1);
        TextView textView = new TextView(this.a);
        textView.setText(str);
        textView.setTextColor(-1);
        textView.setTextSize(20.0f);
        textView.setLayoutParams(c2);
        textView.setPadding(15, 10, 15, 10);
        linearLayout.addView(textView);
        return linearLayout;
    }

    /* access modifiers changed from: protected */
    public void a(Context context, AlertDialog alertDialog) {
        WindowManager.LayoutParams attributes = alertDialog.getWindow().getAttributes();
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (defaultDisplay.getHeight() > defaultDisplay.getWidth()) {
            attributes.width = (int) (((float) defaultDisplay.getWidth()) * 0.9f);
        } else {
            attributes.width = (int) (((float) defaultDisplay.getWidth()) * 0.6f);
        }
        alertDialog.getWindow().setAttributes(attributes);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        c++;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.a);
        builder.setView(view);
        this.b = builder.create();
        this.b.setCancelable(false);
        this.b.show();
        this.b.setOnDismissListener(new f(this));
        a(this.a, this.b);
    }

    /* access modifiers changed from: protected */
    public void a(TextView textView) {
        textView.setTextColor(-1);
        textView.setTextSize((float) d());
        textView.setPadding(20, 10, 20, 10);
    }

    public void b() {
        if (this.b != null) {
            this.b.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void b(TextView textView) {
        textView.setTextColor(q.a(76, 140, 249, 255));
        j.a(textView);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams c() {
        return new ViewGroup.LayoutParams(-1, -2);
    }

    /* access modifiers changed from: protected */
    public int d() {
        return 15;
    }
}
