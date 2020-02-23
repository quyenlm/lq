package com.google.android.gms.cast.framework.media.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import com.google.android.gms.internal.zzayo;

final class zzb {
    private static final zzayo zzarK = new zzayo("WidgetUtil");

    @TargetApi(17)
    public static Bitmap zza(Context context, Bitmap bitmap, float f, float f2) {
        zzarK.zzb("Begin blurring bitmap %s, original width = %d, original height = %d.", bitmap, Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()));
        int round = Math.round(((float) bitmap.getWidth()) * 0.25f);
        int round2 = Math.round(((float) bitmap.getHeight()) * 0.25f);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, round, round2, false);
        Bitmap createBitmap = Bitmap.createBitmap(round, round2, createScaledBitmap.getConfig());
        RenderScript create = RenderScript.create(context);
        Allocation createFromBitmap = Allocation.createFromBitmap(create, createScaledBitmap);
        Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, createFromBitmap.getElement());
        create2.setInput(createFromBitmap);
        create2.setRadius(7.5f);
        create2.forEach(createTyped);
        createTyped.copyTo(createBitmap);
        create.destroy();
        zzarK.zzb("End blurring bitmap %s, original width = %d, original height = %d.", createScaledBitmap, Integer.valueOf(round), Integer.valueOf(round2));
        return createBitmap;
    }

    public static Drawable zza(Context context, int i, @DrawableRes int i2) {
        return zza(context, i, i2, 16842800, 0);
    }

    private static Drawable zza(Context context, int i, @DrawableRes int i2, @AttrRes int i3, @ColorRes int i4) {
        int color;
        ColorStateList colorStateList;
        Drawable wrap = DrawableCompat.wrap(context.getResources().getDrawable(i2).mutate());
        DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_IN);
        if (i != 0) {
            colorStateList = ContextCompat.getColorStateList(context, i);
        } else {
            if (i3 != 0) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i3});
                color = obtainStyledAttributes.getColor(0, 0);
                obtainStyledAttributes.recycle();
            } else {
                color = ContextCompat.getColor(context, i4);
            }
            int[] iArr = {color, ColorUtils.setAlphaComponent(color, 128)};
            colorStateList = new ColorStateList(new int[][]{new int[]{16842910}, new int[]{-16842910}}, iArr);
        }
        DrawableCompat.setTintList(wrap, colorStateList);
        return wrap;
    }

    public static Drawable zzb(Context context, int i, @DrawableRes int i2) {
        return zza(context, i, i2, 0, 17170443);
    }
}
