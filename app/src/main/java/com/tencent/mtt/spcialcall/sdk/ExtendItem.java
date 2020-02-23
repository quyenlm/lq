package com.tencent.mtt.spcialcall.sdk;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.tencent.tp.a.h;
import java.io.InputStream;
import java.io.Serializable;

public class ExtendItem implements Serializable {
    private static final long serialVersionUID = 5231345272635295955L;
    private String CallBackClassName;
    private String CallBackPackgeName;
    private String DesUrl;
    private String DrawableName;
    private int ID;
    private byte[] Image;
    private byte[] ImageNinePatchChunk;
    private byte[] ImagePressed;
    private CharSequence Label;
    private boolean NeedSnapshot = false;
    private int TextColor = 0;
    private int TextSize = 0;
    private String ThemePackage;
    private String Type = "drawable";

    public ExtendItem(int id) {
        this.ID = id;
    }

    public ExtendItem(int id, Drawable icon, CharSequence label) {
        if (icon != null) {
            this.Image = BitmapTools.Drawable2Bytes(icon);
        }
        this.Label = label;
        this.ID = id;
    }

    public ExtendItem(int id, CharSequence label) {
        this.Label = label;
        this.ID = id;
    }

    public ExtendItem(int id, String drawableName, CharSequence label) {
        this.DrawableName = drawableName;
        this.Label = label;
        this.ID = id;
    }

    public ExtendItem(int id, Bitmap normal, Bitmap pressed, byte[] ninePatchChunk, CharSequence label) {
        try {
            if (normal.getNinePatchChunk() != null) {
                setImageNinePatchChunk(normal.getNinePatchChunk());
            }
            this.Image = BitmapTools.Bitmap2Bytes(normal);
            if (pressed != null) {
                this.ImagePressed = BitmapTools.Bitmap2Bytes(pressed);
            }
        } catch (Exception e) {
        }
        this.Label = label;
        this.ID = id;
    }

    public ExtendItem(int id, boolean isNinePatch, InputStream inputStream, InputStream inputStreamPressed, CharSequence label) {
        Bitmap bitmap;
        Bitmap bitmapPressed;
        if (isNinePatch) {
            try {
                bitmap = NinePatchUtils.decodeFromStream(inputStream);
                setImageNinePatchChunk(bitmap.getNinePatchChunk());
            } catch (Exception e) {
            }
        } else {
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        this.Image = BitmapTools.Bitmap2Bytes(bitmap);
        if (inputStreamPressed != null) {
            if (isNinePatch) {
                bitmapPressed = NinePatchUtils.decodeFromStream(inputStreamPressed);
            } else {
                bitmapPressed = BitmapFactory.decodeStream(inputStreamPressed);
            }
            this.ImagePressed = BitmapTools.Bitmap2Bytes(bitmapPressed);
        }
        this.Label = label;
        this.ID = id;
    }

    public ExtendItem(int id, int inDensity, int inTargetDensity, InputStream inputStream, InputStream inputStreamPressed, CharSequence label) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        if (inputStream != null) {
            try {
                option.inScaled = true;
                option.inDensity = inDensity;
                option.inTargetDensity = inTargetDensity;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, new Rect(), option);
                setImageNinePatchChunk(bitmap.getNinePatchChunk());
                this.Image = BitmapTools.Bitmap2Bytes(bitmap);
            } catch (Exception e) {
            }
        }
        if (inputStreamPressed != null) {
            this.ImagePressed = BitmapTools.Bitmap2Bytes(BitmapFactory.decodeStream(inputStreamPressed, new Rect(), option));
        }
        this.Label = label;
        this.ID = id;
    }

    public Bitmap getImage() {
        return BitmapTools.Bytes2Bimap(this.Image);
    }

    public void setImage(Drawable icon) {
        this.Image = BitmapTools.Drawable2Bytes(icon);
    }

    public CharSequence getLabel() {
        return this.Label;
    }

    public void setLabel(CharSequence label) {
        this.Label = label;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public ComponentName getComponentName() {
        if (TextUtils.isEmpty(this.CallBackPackgeName) || TextUtils.isEmpty(this.CallBackClassName)) {
            return null;
        }
        return new ComponentName(this.CallBackPackgeName, this.CallBackClassName);
    }

    public void setComponentName(ComponentName componentName) {
        this.CallBackPackgeName = componentName.getPackageName();
        this.CallBackClassName = componentName.getClassName();
    }

    public String getThemePackage() {
        return this.ThemePackage;
    }

    public void setThemePackage(String themePackage, String type) {
        this.ThemePackage = themePackage;
        this.Type = type;
    }

    public String getDrawableName() {
        return this.DrawableName;
    }

    public void setDrawableName(String drawableName) {
        this.DrawableName = drawableName;
    }

    public byte[] getImageNinePatchChunk() {
        return this.ImageNinePatchChunk;
    }

    private void setImageNinePatchChunk(byte[] imageNinePatchChunk) {
        this.ImageNinePatchChunk = imageNinePatchChunk;
    }

    public Bitmap getImagePressed() {
        return BitmapTools.Bytes2Bimap(this.ImagePressed);
    }

    public void setImagePressed(byte[] imagePressed) {
        this.ImagePressed = imagePressed;
    }

    public String getType() {
        return this.Type;
    }

    public boolean isNeedSnapshot() {
        return this.NeedSnapshot;
    }

    public void setNeedSnapshot(boolean needSnapshot) {
        this.NeedSnapshot = needSnapshot;
    }

    public int getTextColor() {
        return this.TextColor;
    }

    public void setTextColor(int textColor) {
        this.TextColor = textColor;
    }

    public String getDesUrl() {
        return this.DesUrl;
    }

    public void setDesUrl(String desUrl) {
        this.DesUrl = desUrl;
    }

    public void setJsFuncOnclick(String funcName, String... params) {
        String paramsString = "";
        for (int i = 0; i < params.length; i++) {
            paramsString = String.valueOf(paramsString) + ",'" + params[i] + "'";
        }
        this.DesUrl = "javascript:" + funcName + h.a + paramsString.substring(1) + h.b;
    }

    public int getTextSize() {
        return this.TextSize;
    }

    public void setTextSize(int textSize) {
        this.TextSize = textSize;
    }
}
