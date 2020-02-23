package com.tencent.tp.a;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class af {
    AlertDialog a = null;
    private Context b;
    private String c;
    private String d;
    private String e;
    /* access modifiers changed from: private */
    public a f;
    private DialogInterface.OnDismissListener g = new ag(this);

    public interface a {
        void a(int i);
    }

    public af(Context context, String str, String str2, String str3, a aVar) {
        this.b = context;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = aVar;
    }

    private int a(Context context, int i) {
        return (int) ((context.getResources().getDisplayMetrics().density * ((float) i)) + 0.5f);
    }

    public void a() {
        AlertDialog.Builder builder = Build.VERSION.SDK_INT >= 11 ? new AlertDialog.Builder(this.b, 1) : new AlertDialog.Builder(this.b);
        builder.setCancelable(false);
        int a2 = a(this.b, 10);
        int a3 = a(this.b, 6);
        int a4 = a(this.b, 10);
        TextView textView = new TextView(this.b);
        textView.setText(this.d);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(2, 18.0f);
        textView.setBackgroundColor(Color.parseColor("#000000"));
        textView.setPadding(a2, a3, a2, a4);
        builder.setView(textView);
        builder.setNeutralButton(this.e, (DialogInterface.OnClickListener) null);
        this.a = builder.create();
        this.a.setOnDismissListener(this.g);
        this.a.setOnShowListener(new ah(this));
        this.a.show();
        b();
    }

    /* access modifiers changed from: protected */
    public void b() {
        if (this.b != null && this.a != null) {
            WindowManager.LayoutParams attributes = this.a.getWindow().getAttributes();
            Display defaultDisplay = ((WindowManager) this.b.getSystemService("window")).getDefaultDisplay();
            if (defaultDisplay.getHeight() > defaultDisplay.getWidth()) {
                attributes.width = (int) (((float) defaultDisplay.getWidth()) * 0.9f);
            } else {
                attributes.width = (int) (((float) defaultDisplay.getWidth()) * 0.6f);
            }
            this.a.getWindow().setAttributes(attributes);
            this.a.getWindow().setGravity(17);
            this.a.setCanceledOnTouchOutside(false);
        }
    }

    public void c() {
        if (this.a != null) {
            this.a.dismiss();
            this.a = null;
        }
    }
}
