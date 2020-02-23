package com.tencent.mtt.spcialcall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import com.tencent.mtt.spcialcall.sdk.NinePatchUtils;

public class ThemeSwitcher {
    public static void doSwitch(View view, ExtendItem item) {
        try {
            Drawable drawable = initTransDrawable(view.getContext(), item);
            if (view instanceof ImageButton) {
                if (drawable != null) {
                    ((ImageButton) view).setImageDrawable(drawable);
                }
            } else if (view instanceof Button) {
                if (drawable != null) {
                    view.setBackgroundDrawable(drawable);
                }
                if (item.getLabel() != null) {
                    ((Button) view).setText(item.getLabel());
                }
                if (item.getTextColor() != 0) {
                    ((Button) view).setTextColor(item.getTextColor());
                }
                if (item.getTextSize() != 0) {
                    ((Button) view).setTextSize(1, (float) item.getTextSize());
                }
            } else if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            }
        } catch (Exception e) {
        }
    }

    public static Drawable initTransDrawable(Context context, ExtendItem item) {
        Drawable drawable;
        Drawable drawablePressed;
        Bitmap image = item.getImage();
        Bitmap imagePressed = item.getImagePressed();
        if (image != null) {
            byte[] ninePatchChunk = image.getNinePatchChunk();
            if (ninePatchChunk == null) {
                ninePatchChunk = item.getImageNinePatchChunk();
            }
            Rect paddingRect = new Rect();
            if (ninePatchChunk != null) {
                NinePatchUtils.readPaddingFromChunk(ninePatchChunk, paddingRect);
                drawable = new NinePatchDrawable(context.getResources(), image, ninePatchChunk, paddingRect, (String) null);
            } else {
                drawable = new BitmapDrawable(context.getResources(), image);
            }
            if (imagePressed == null) {
                return drawable;
            }
            if (imagePressed.getNinePatchChunk() != null) {
                ninePatchChunk = imagePressed.getNinePatchChunk();
            }
            if (ninePatchChunk != null) {
                drawablePressed = new NinePatchDrawable(context.getResources(), imagePressed, ninePatchChunk, paddingRect, (String) null);
            } else {
                drawablePressed = new BitmapDrawable(context.getResources(), imagePressed);
            }
            Drawable sd = new StateListDrawable();
            sd.addState(new int[]{16842910, 16842908}, drawablePressed);
            sd.addState(new int[]{16842919, 16842910}, drawablePressed);
            sd.addState(new int[]{16842908}, drawablePressed);
            sd.addState(new int[]{16842919}, drawablePressed);
            sd.addState(new int[]{16842910}, drawable);
            sd.addState(new int[0], drawable);
            return sd;
        } else if (item.getDrawableName() == null) {
            return null;
        } else {
            String defPackage = item.getThemePackage();
            if (defPackage == null) {
                defPackage = ExtraInfo.getOriPkg();
            }
            try {
                Context desContext = context.createPackageContext(defPackage, 2);
                return desContext.getResources().getDrawable(desContext.getResources().getIdentifier(item.getDrawableName(), item.getType(), defPackage));
            } catch (Exception e) {
                return null;
            }
        }
    }
}
