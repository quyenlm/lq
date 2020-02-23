package com.tencent.tp.a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;

public class n {
    public static Drawable a(byte[] bArr) {
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        if (decodeByteArray == null) {
            return null;
        }
        byte[] ninePatchChunk = decodeByteArray.getNinePatchChunk();
        return (ninePatchChunk == null || ninePatchChunk.length <= 0 || !NinePatch.isNinePatchChunk(ninePatchChunk)) ? new BitmapDrawable(decodeByteArray) : new NinePatchDrawable(decodeByteArray, ninePatchChunk, new Rect(), (String) null);
    }

    public static StateListDrawable a(Drawable drawable, Drawable drawable2) {
        if (drawable == null || drawable2 == null) {
            return null;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919, 16842910}, drawable2);
        stateListDrawable.addState(new int[]{16842910, 16842908}, (Drawable) null);
        stateListDrawable.addState(new int[]{16842910}, drawable);
        stateListDrawable.addState(new int[]{16842908}, (Drawable) null);
        stateListDrawable.addState(new int[]{16842909}, (Drawable) null);
        return stateListDrawable;
    }

    public static m a(String str, boolean z) {
        m mVar = new m();
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        if (decodeFile == null) {
            return null;
        }
        mVar.b = decodeFile.getWidth();
        mVar.c = decodeFile.getHeight();
        byte[] ninePatchChunk = decodeFile.getNinePatchChunk();
        if (!z || ninePatchChunk == null || ninePatchChunk.length == 0 || !NinePatch.isNinePatchChunk(ninePatchChunk)) {
            mVar.a = new BitmapDrawable(decodeFile);
        } else {
            mVar.a = new NinePatchDrawable(decodeFile, ninePatchChunk, new Rect(), (String) null);
            mVar.c -= 2;
        }
        return mVar;
    }
}
