package com.tencent.tp.a;

import android.content.Context;

public class ae {
    public static int a(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int a(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().scaledDensity * f) + 0.5f);
    }

    public static int a(Context context, int i) {
        return (int) ((context.getResources().getDisplayMetrics().density * ((float) i)) + 0.5f);
    }

    public static float b(Context context, float f) {
        return (f / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f;
    }

    public static int b(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int b(Context context, int i) {
        return (int) ((((float) i) / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
