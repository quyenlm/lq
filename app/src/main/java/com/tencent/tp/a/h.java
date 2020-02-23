package com.tencent.tp.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import java.io.File;

public class h {
    public static final String a = "(";
    public static final String b = ")";
    public static final int c = a(255, 255, 255, 255);
    public static final int d = a(255, 0, 0, 255);
    public static final int e = a(6, 162, 68, 255);
    public static final int f = a(0, 0, 255, 255);

    public static float a(Context context) {
        return ae.b(context, (float) ae.a(context, 16));
    }

    public static int a(int i, int i2, int i3, int i4) {
        return ((i4 & 255) << 24) | ((i & 255) << 16) | ((i2 & 255) << 8) | (i3 & 255);
    }

    public static int a(Context context, int i) {
        return (int) ((context.getResources().getDisplayMetrics().density * ((float) (i / 2))) + 0.5f);
    }

    public static m a(Context context, String str, String str2) {
        m mVar;
        m mVar2;
        try {
            mVar2 = n.a(a(context, str), false);
            mVar = n.a(a(context, str2), false);
        } catch (Exception e2) {
            mVar = null;
            mVar2 = null;
        }
        StateListDrawable a2 = n.a(mVar2.a, mVar.a);
        m mVar3 = new m();
        mVar3.a = a2;
        mVar3.b = mVar2.b;
        mVar3.c = mVar2.c;
        return mVar3;
    }

    private static String a(Context context, String str) {
        return context.getFilesDir() + File.separator + "tss_tmp" + File.separator + str;
    }

    public static void a(View view, Drawable drawable) {
        view.setBackgroundDrawable(drawable);
    }

    public static float b(Context context) {
        return ae.b(context, (float) ae.a(context, 13));
    }

    public static m c(Context context) {
        try {
            return n.a(a(context, "bail_dlg_title.png"), true);
        } catch (Exception e2) {
            return null;
        }
    }

    public static m d(Context context) {
        try {
            return n.a(a(context, "bail_dlg_body.png"), true);
        } catch (Exception e2) {
            return null;
        }
    }

    public static m e(Context context) {
        try {
            return n.a(a(context, "bail_html_content_bg.png"), true);
        } catch (Exception e2) {
            return null;
        }
    }

    public static m f(Context context) {
        return a(context, "btn_cancel.png", "btn_cancel_pressed.png");
    }

    public static m g(Context context) {
        return a(context, "btn_ok.png", "btn_ok_pressed.png");
    }

    public static m h(Context context) {
        return a(context, "btn_gray.png", "btn_gray.png");
    }
}
