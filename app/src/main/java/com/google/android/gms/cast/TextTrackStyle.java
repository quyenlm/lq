package com.google.android.gms.cast;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.accessibility.CaptioningManager;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.internal.zzaye;
import com.tencent.imsdk.IMConfig;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

public final class TextTrackStyle extends zza {
    public static final int COLOR_UNSPECIFIED = 0;
    public static final Parcelable.Creator<TextTrackStyle> CREATOR = new zzbi();
    public static final float DEFAULT_FONT_SCALE = 1.0f;
    public static final int EDGE_TYPE_DEPRESSED = 4;
    public static final int EDGE_TYPE_DROP_SHADOW = 2;
    public static final int EDGE_TYPE_NONE = 0;
    public static final int EDGE_TYPE_OUTLINE = 1;
    public static final int EDGE_TYPE_RAISED = 3;
    public static final int EDGE_TYPE_UNSPECIFIED = -1;
    public static final int FONT_FAMILY_CASUAL = 4;
    public static final int FONT_FAMILY_CURSIVE = 5;
    public static final int FONT_FAMILY_MONOSPACED_SANS_SERIF = 1;
    public static final int FONT_FAMILY_MONOSPACED_SERIF = 3;
    public static final int FONT_FAMILY_SANS_SERIF = 0;
    public static final int FONT_FAMILY_SERIF = 2;
    public static final int FONT_FAMILY_SMALL_CAPITALS = 6;
    public static final int FONT_FAMILY_UNSPECIFIED = -1;
    public static final int FONT_STYLE_BOLD = 1;
    public static final int FONT_STYLE_BOLD_ITALIC = 3;
    public static final int FONT_STYLE_ITALIC = 2;
    public static final int FONT_STYLE_NORMAL = 0;
    public static final int FONT_STYLE_UNSPECIFIED = -1;
    public static final int WINDOW_TYPE_NONE = 0;
    public static final int WINDOW_TYPE_NORMAL = 1;
    public static final int WINDOW_TYPE_ROUNDED = 2;
    public static final int WINDOW_TYPE_UNSPECIFIED = -1;
    private int zzHs;
    private String zzaoC;
    private JSONObject zzaoD;
    private int zzarA;
    private int zzarB;
    private int zzarC;
    private int zzarD;
    private int zzarE;
    private String zzarF;
    private int zzarG;
    private int zzarH;
    private float zzary;
    private int zzarz;

    public TextTrackStyle() {
        this(1.0f, 0, 0, -1, 0, -1, 0, 0, (String) null, -1, -1, (String) null);
    }

    TextTrackStyle(float f, int i, int i2, int i3, int i4, int i5, int i6, int i7, String str, int i8, int i9, String str2) {
        this.zzary = f;
        this.zzarz = i;
        this.zzHs = i2;
        this.zzarA = i3;
        this.zzarB = i4;
        this.zzarC = i5;
        this.zzarD = i6;
        this.zzarE = i7;
        this.zzarF = str;
        this.zzarG = i8;
        this.zzarH = i9;
        this.zzaoC = str2;
        if (this.zzaoC != null) {
            try {
                this.zzaoD = new JSONObject(this.zzaoC);
            } catch (JSONException e) {
                this.zzaoD = null;
                this.zzaoC = null;
            }
        } else {
            this.zzaoD = null;
        }
    }

    @TargetApi(19)
    public static TextTrackStyle fromSystemSettings(Context context) {
        TextTrackStyle textTrackStyle = new TextTrackStyle();
        if (!zzq.zzsc()) {
            return textTrackStyle;
        }
        CaptioningManager captioningManager = (CaptioningManager) context.getSystemService("captioning");
        textTrackStyle.setFontScale(captioningManager.getFontScale());
        CaptioningManager.CaptionStyle userStyle = captioningManager.getUserStyle();
        textTrackStyle.setBackgroundColor(userStyle.backgroundColor);
        textTrackStyle.setForegroundColor(userStyle.foregroundColor);
        switch (userStyle.edgeType) {
            case 1:
                textTrackStyle.setEdgeType(1);
                break;
            case 2:
                textTrackStyle.setEdgeType(2);
                break;
            default:
                textTrackStyle.setEdgeType(0);
                break;
        }
        textTrackStyle.setEdgeColor(userStyle.edgeColor);
        Typeface typeface = userStyle.getTypeface();
        if (typeface != null) {
            if (Typeface.MONOSPACE.equals(typeface)) {
                textTrackStyle.setFontGenericFamily(1);
            } else if (Typeface.SANS_SERIF.equals(typeface) || !Typeface.SERIF.equals(typeface)) {
                textTrackStyle.setFontGenericFamily(0);
            } else {
                textTrackStyle.setFontGenericFamily(2);
            }
            boolean isBold = typeface.isBold();
            boolean isItalic = typeface.isItalic();
            if (isBold && isItalic) {
                textTrackStyle.setFontStyle(3);
            } else if (isBold) {
                textTrackStyle.setFontStyle(1);
            } else if (isItalic) {
                textTrackStyle.setFontStyle(2);
            } else {
                textTrackStyle.setFontStyle(0);
            }
        }
        return textTrackStyle;
    }

    private static int zzcb(String str) {
        if (str == null || str.length() != 9 || str.charAt(0) != '#') {
            return 0;
        }
        try {
            return Color.argb(Integer.parseInt(str.substring(7, 9), 16), Integer.parseInt(str.substring(1, 3), 16), Integer.parseInt(str.substring(3, 5), 16), Integer.parseInt(str.substring(5, 7), 16));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String zzu(int i) {
        return String.format("#%02X%02X%02X%02X", new Object[]{Integer.valueOf(Color.red(i)), Integer.valueOf(Color.green(i)), Integer.valueOf(Color.blue(i)), Integer.valueOf(Color.alpha(i))});
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextTrackStyle)) {
            return false;
        }
        TextTrackStyle textTrackStyle = (TextTrackStyle) obj;
        if ((this.zzaoD == null) == (textTrackStyle.zzaoD == null)) {
            return (this.zzaoD == null || textTrackStyle.zzaoD == null || zzo.zzc(this.zzaoD, textTrackStyle.zzaoD)) && this.zzary == textTrackStyle.zzary && this.zzarz == textTrackStyle.zzarz && this.zzHs == textTrackStyle.zzHs && this.zzarA == textTrackStyle.zzarA && this.zzarB == textTrackStyle.zzarB && this.zzarC == textTrackStyle.zzarC && this.zzarE == textTrackStyle.zzarE && zzaye.zza(this.zzarF, textTrackStyle.zzarF) && this.zzarG == textTrackStyle.zzarG && this.zzarH == textTrackStyle.zzarH;
        }
        return false;
    }

    public final int getBackgroundColor() {
        return this.zzHs;
    }

    public final JSONObject getCustomData() {
        return this.zzaoD;
    }

    public final int getEdgeColor() {
        return this.zzarB;
    }

    public final int getEdgeType() {
        return this.zzarA;
    }

    public final String getFontFamily() {
        return this.zzarF;
    }

    public final int getFontGenericFamily() {
        return this.zzarG;
    }

    public final float getFontScale() {
        return this.zzary;
    }

    public final int getFontStyle() {
        return this.zzarH;
    }

    public final int getForegroundColor() {
        return this.zzarz;
    }

    public final int getWindowColor() {
        return this.zzarD;
    }

    public final int getWindowCornerRadius() {
        return this.zzarE;
    }

    public final int getWindowType() {
        return this.zzarC;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.zzary), Integer.valueOf(this.zzarz), Integer.valueOf(this.zzHs), Integer.valueOf(this.zzarA), Integer.valueOf(this.zzarB), Integer.valueOf(this.zzarC), Integer.valueOf(this.zzarD), Integer.valueOf(this.zzarE), this.zzarF, Integer.valueOf(this.zzarG), Integer.valueOf(this.zzarH), String.valueOf(this.zzaoD)});
    }

    public final void setBackgroundColor(int i) {
        this.zzHs = i;
    }

    public final void setCustomData(JSONObject jSONObject) {
        this.zzaoD = jSONObject;
    }

    public final void setEdgeColor(int i) {
        this.zzarB = i;
    }

    public final void setEdgeType(int i) {
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("invalid edgeType");
        }
        this.zzarA = i;
    }

    public final void setFontFamily(String str) {
        this.zzarF = str;
    }

    public final void setFontGenericFamily(int i) {
        if (i < 0 || i > 6) {
            throw new IllegalArgumentException("invalid fontGenericFamily");
        }
        this.zzarG = i;
    }

    public final void setFontScale(float f) {
        this.zzary = f;
    }

    public final void setFontStyle(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("invalid fontStyle");
        }
        this.zzarH = i;
    }

    public final void setForegroundColor(int i) {
        this.zzarz = i;
    }

    public final void setWindowColor(int i) {
        this.zzarD = i;
    }

    public final void setWindowCornerRadius(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("invalid windowCornerRadius");
        }
        this.zzarE = i;
    }

    public final void setWindowType(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("invalid windowType");
        }
        this.zzarC = i;
    }

    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("fontScale", (double) this.zzary);
            if (this.zzarz != 0) {
                jSONObject.put("foregroundColor", zzu(this.zzarz));
            }
            if (this.zzHs != 0) {
                jSONObject.put("backgroundColor", zzu(this.zzHs));
            }
            switch (this.zzarA) {
                case 0:
                    jSONObject.put("edgeType", IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT);
                    break;
                case 1:
                    jSONObject.put("edgeType", "OUTLINE");
                    break;
                case 2:
                    jSONObject.put("edgeType", "DROP_SHADOW");
                    break;
                case 3:
                    jSONObject.put("edgeType", "RAISED");
                    break;
                case 4:
                    jSONObject.put("edgeType", "DEPRESSED");
                    break;
            }
            if (this.zzarB != 0) {
                jSONObject.put("edgeColor", zzu(this.zzarB));
            }
            switch (this.zzarC) {
                case 0:
                    jSONObject.put("windowType", IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT);
                    break;
                case 1:
                    jSONObject.put("windowType", "NORMAL");
                    break;
                case 2:
                    jSONObject.put("windowType", "ROUNDED_CORNERS");
                    break;
            }
            if (this.zzarD != 0) {
                jSONObject.put("windowColor", zzu(this.zzarD));
            }
            if (this.zzarC == 2) {
                jSONObject.put("windowRoundedCornerRadius", this.zzarE);
            }
            if (this.zzarF != null) {
                jSONObject.put("fontFamily", this.zzarF);
            }
            switch (this.zzarG) {
                case 0:
                    jSONObject.put("fontGenericFamily", "SANS_SERIF");
                    break;
                case 1:
                    jSONObject.put("fontGenericFamily", "MONOSPACED_SANS_SERIF");
                    break;
                case 2:
                    jSONObject.put("fontGenericFamily", "SERIF");
                    break;
                case 3:
                    jSONObject.put("fontGenericFamily", "MONOSPACED_SERIF");
                    break;
                case 4:
                    jSONObject.put("fontGenericFamily", "CASUAL");
                    break;
                case 5:
                    jSONObject.put("fontGenericFamily", "CURSIVE");
                    break;
                case 6:
                    jSONObject.put("fontGenericFamily", "SMALL_CAPITALS");
                    break;
            }
            switch (this.zzarH) {
                case 0:
                    jSONObject.put("fontStyle", "NORMAL");
                    break;
                case 1:
                    jSONObject.put("fontStyle", "BOLD");
                    break;
                case 2:
                    jSONObject.put("fontStyle", "ITALIC");
                    break;
                case 3:
                    jSONObject.put("fontStyle", "BOLD_ITALIC");
                    break;
            }
            if (this.zzaoD != null) {
                jSONObject.put("customData", this.zzaoD);
            }
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        this.zzaoC = this.zzaoD == null ? null : this.zzaoD.toString();
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, getFontScale());
        zzd.zzc(parcel, 3, getForegroundColor());
        zzd.zzc(parcel, 4, getBackgroundColor());
        zzd.zzc(parcel, 5, getEdgeType());
        zzd.zzc(parcel, 6, getEdgeColor());
        zzd.zzc(parcel, 7, getWindowType());
        zzd.zzc(parcel, 8, getWindowColor());
        zzd.zzc(parcel, 9, getWindowCornerRadius());
        zzd.zza(parcel, 10, getFontFamily(), false);
        zzd.zzc(parcel, 11, getFontGenericFamily());
        zzd.zzc(parcel, 12, getFontStyle());
        zzd.zza(parcel, 13, this.zzaoC, false);
        zzd.zzI(parcel, zze);
    }

    public final void zzl(JSONObject jSONObject) throws JSONException {
        this.zzary = (float) jSONObject.optDouble("fontScale", 1.0d);
        this.zzarz = zzcb(jSONObject.optString("foregroundColor"));
        this.zzHs = zzcb(jSONObject.optString("backgroundColor"));
        if (jSONObject.has("edgeType")) {
            String string = jSONObject.getString("edgeType");
            if (IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT.equals(string)) {
                this.zzarA = 0;
            } else if ("OUTLINE".equals(string)) {
                this.zzarA = 1;
            } else if ("DROP_SHADOW".equals(string)) {
                this.zzarA = 2;
            } else if ("RAISED".equals(string)) {
                this.zzarA = 3;
            } else if ("DEPRESSED".equals(string)) {
                this.zzarA = 4;
            }
        }
        this.zzarB = zzcb(jSONObject.optString("edgeColor"));
        if (jSONObject.has("windowType")) {
            String string2 = jSONObject.getString("windowType");
            if (IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT.equals(string2)) {
                this.zzarC = 0;
            } else if ("NORMAL".equals(string2)) {
                this.zzarC = 1;
            } else if ("ROUNDED_CORNERS".equals(string2)) {
                this.zzarC = 2;
            }
        }
        this.zzarD = zzcb(jSONObject.optString("windowColor"));
        if (this.zzarC == 2) {
            this.zzarE = jSONObject.optInt("windowRoundedCornerRadius", 0);
        }
        this.zzarF = jSONObject.optString("fontFamily", (String) null);
        if (jSONObject.has("fontGenericFamily")) {
            String string3 = jSONObject.getString("fontGenericFamily");
            if ("SANS_SERIF".equals(string3)) {
                this.zzarG = 0;
            } else if ("MONOSPACED_SANS_SERIF".equals(string3)) {
                this.zzarG = 1;
            } else if ("SERIF".equals(string3)) {
                this.zzarG = 2;
            } else if ("MONOSPACED_SERIF".equals(string3)) {
                this.zzarG = 3;
            } else if ("CASUAL".equals(string3)) {
                this.zzarG = 4;
            } else if ("CURSIVE".equals(string3)) {
                this.zzarG = 5;
            } else if ("SMALL_CAPITALS".equals(string3)) {
                this.zzarG = 6;
            }
        }
        if (jSONObject.has("fontStyle")) {
            String string4 = jSONObject.getString("fontStyle");
            if ("NORMAL".equals(string4)) {
                this.zzarH = 0;
            } else if ("BOLD".equals(string4)) {
                this.zzarH = 1;
            } else if ("ITALIC".equals(string4)) {
                this.zzarH = 2;
            } else if ("BOLD_ITALIC".equals(string4)) {
                this.zzarH = 3;
            }
        }
        this.zzaoD = jSONObject.optJSONObject("customData");
    }
}
