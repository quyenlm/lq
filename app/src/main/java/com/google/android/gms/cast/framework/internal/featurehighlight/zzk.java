package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.R;
import com.google.android.gms.internal.ik;

final class zzk {
    private final Rect zzatr = new Rect();
    private final int zzats;
    private final int zzatt;
    private final int zzatu;
    private final int zzatv;
    private final zza zzatw;

    zzk(zza zza) {
        this.zzatw = (zza) ik.zzu(zza);
        Resources resources = zza.getResources();
        this.zzats = resources.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_inner_radius);
        this.zzatt = resources.getDimensionPixelOffset(R.dimen.cast_libraries_material_featurehighlight_inner_margin);
        this.zzatu = resources.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_text_max_width);
        this.zzatv = resources.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_text_horizontal_offset);
    }

    private final int zza(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = i3 / 2;
        int i6 = i4 - i <= i2 - i4 ? (i4 - i5) + this.zzatv : (i4 - i5) - this.zzatv;
        return i6 - marginLayoutParams.leftMargin < i ? i + marginLayoutParams.leftMargin : (i6 + i3) + marginLayoutParams.rightMargin > i2 ? (i2 - i3) - marginLayoutParams.rightMargin : i6;
    }

    private final void zza(View view, int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.min((i - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin, this.zzatu), 1073741824), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
    }

    /* access modifiers changed from: package-private */
    public final void zza(Rect rect, Rect rect2) {
        boolean z = false;
        View zznO = this.zzatw.zznO();
        if (rect.isEmpty() || rect2.isEmpty()) {
            zznO.layout(0, 0, 0, 0);
        } else {
            int centerY = rect.centerY();
            int centerX = rect.centerX();
            if (centerY < rect2.centerY()) {
                z = true;
            }
            int max = Math.max(this.zzats * 2, rect.height());
            int i = this.zzatt + (max / 2) + centerY;
            if (z) {
                zza(zznO, rect2.width(), rect2.bottom - i);
                int zza = zza(zznO, rect2.left, rect2.right, zznO.getMeasuredWidth(), centerX);
                zznO.layout(zza, i, zznO.getMeasuredWidth() + zza, zznO.getMeasuredHeight() + i);
            } else {
                int i2 = (centerY - (max / 2)) - this.zzatt;
                zza(zznO, rect2.width(), i2 - rect2.top);
                int zza2 = zza(zznO, rect2.left, rect2.right, zznO.getMeasuredWidth(), centerX);
                zznO.layout(zza2, i2 - zznO.getMeasuredHeight(), zznO.getMeasuredWidth() + zza2, i2);
            }
        }
        this.zzatr.set(zznO.getLeft(), zznO.getTop(), zznO.getRight(), zznO.getBottom());
        this.zzatw.zznP().zzb(rect, this.zzatr);
        this.zzatw.zznQ().zzc(rect);
    }
}
