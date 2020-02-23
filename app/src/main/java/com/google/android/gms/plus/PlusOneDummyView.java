package com.google.android.gms.plus;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.FrameLayout;

public class PlusOneDummyView extends FrameLayout {
    public static final String TAG = "PlusOneDummyView";

    static class zza implements zzd {
        private Context mContext;

        private zza(Context context) {
            this.mContext = context;
        }

        public final Drawable getDrawable(int i) {
            return this.mContext.getResources().getDrawable(17301508);
        }

        public final boolean isValid() {
            return true;
        }
    }

    static class zzb implements zzd {
        private Context mContext;

        private zzb(Context context) {
            this.mContext = context;
        }

        public final Drawable getDrawable(int i) {
            String str;
            try {
                Resources resources = this.mContext.createPackageContext("com.google.android.gms", 4).getResources();
                switch (i) {
                    case 0:
                        str = "ic_plusone_small";
                        break;
                    case 1:
                        str = "ic_plusone_medium";
                        break;
                    case 2:
                        str = "ic_plusone_tall";
                        break;
                    default:
                        str = "ic_plusone_standard";
                        break;
                }
                return resources.getDrawable(resources.getIdentifier(str, "drawable", "com.google.android.gms"));
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        }

        public final boolean isValid() {
            try {
                this.mContext.createPackageContext("com.google.android.gms", 4).getResources();
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }
    }

    static class zzc implements zzd {
        private Context mContext;

        private zzc(Context context) {
            this.mContext = context;
        }

        public final Drawable getDrawable(int i) {
            String str;
            switch (i) {
                case 0:
                    str = "ic_plusone_small_off_client";
                    break;
                case 1:
                    str = "ic_plusone_medium_off_client";
                    break;
                case 2:
                    str = "ic_plusone_tall_off_client";
                    break;
                default:
                    str = "ic_plusone_standard_off_client";
                    break;
            }
            return this.mContext.getResources().getDrawable(this.mContext.getResources().getIdentifier(str, "drawable", this.mContext.getPackageName()));
        }

        public final boolean isValid() {
            return (this.mContext.getResources().getIdentifier("ic_plusone_small_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_medium_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_tall_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_standard_off_client", "drawable", this.mContext.getPackageName()) == 0) ? false : true;
        }
    }

    interface zzd {
        Drawable getDrawable(int i);

        boolean isValid();
    }

    public PlusOneDummyView(Context context, int i) {
        super(context);
        int i2;
        int i3;
        Button button = new Button(context);
        button.setEnabled(false);
        zzd zzb2 = new zzb(getContext());
        zzb2 = !zzb2.isValid() ? new zzc(getContext()) : zzb2;
        button.setBackgroundDrawable((!zzb2.isValid() ? new zza(getContext()) : zzb2).getDrawable(i));
        Point point = new Point();
        switch (i) {
            case 0:
                i2 = 14;
                i3 = 24;
                break;
            case 1:
                i2 = 20;
                i3 = 32;
                break;
            case 2:
                i2 = 20;
                i3 = 50;
                break;
            default:
                i2 = 24;
                i3 = 38;
                break;
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float applyDimension = TypedValue.applyDimension(1, (float) i3, displayMetrics);
        float applyDimension2 = TypedValue.applyDimension(1, (float) i2, displayMetrics);
        point.x = (int) (((double) applyDimension) + 0.5d);
        point.y = (int) (((double) applyDimension2) + 0.5d);
        addView(button, new FrameLayout.LayoutParams(point.x, point.y, 17));
    }
}
