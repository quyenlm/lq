package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.internal.zzaye;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class MediaTrack extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<MediaTrack> CREATOR = new zzah();
    public static final int SUBTYPE_CAPTIONS = 2;
    public static final int SUBTYPE_CHAPTERS = 4;
    public static final int SUBTYPE_DESCRIPTIONS = 3;
    public static final int SUBTYPE_METADATA = 5;
    public static final int SUBTYPE_NONE = 0;
    public static final int SUBTYPE_SUBTITLES = 1;
    public static final int SUBTYPE_UNKNOWN = -1;
    public static final int TYPE_AUDIO = 2;
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_VIDEO = 3;
    private String mName;
    private String zzaeT;
    private long zzakg;
    private int zzamr;
    private String zzaoC;
    private JSONObject zzaoD;
    private String zzapX;
    private String zzapZ;
    private int zzaqN;

    public static class Builder {
        private final MediaTrack zzaqO;

        public Builder(long j, int i) throws IllegalArgumentException {
            this.zzaqO = new MediaTrack(j, i);
        }

        public MediaTrack build() {
            return this.zzaqO;
        }

        public Builder setContentId(String str) {
            this.zzaqO.setContentId(str);
            return this;
        }

        public Builder setContentType(String str) {
            this.zzaqO.setContentType(str);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zzaqO.setCustomData(jSONObject);
            return this;
        }

        public Builder setLanguage(String str) {
            this.zzaqO.setLanguage(str);
            return this;
        }

        public Builder setLanguage(Locale locale) {
            this.zzaqO.setLanguage(zzaye.zzb(locale));
            return this;
        }

        public Builder setName(String str) {
            this.zzaqO.setName(str);
            return this;
        }

        public Builder setSubtype(int i) throws IllegalArgumentException {
            this.zzaqO.zzW(i);
            return this;
        }
    }

    MediaTrack(long j, int i) throws IllegalArgumentException {
        this(0, 0, (String) null, (String) null, (String) null, (String) null, -1, (String) null);
        this.zzakg = j;
        if (i <= 0 || i > 3) {
            throw new IllegalArgumentException(new StringBuilder(24).append("invalid type ").append(i).toString());
        }
        this.zzamr = i;
    }

    MediaTrack(long j, int i, String str, String str2, String str3, String str4, int i2, String str5) {
        this.zzakg = j;
        this.zzamr = i;
        this.zzapX = str;
        this.zzapZ = str2;
        this.mName = str3;
        this.zzaeT = str4;
        this.zzaqN = i2;
        this.zzaoC = str5;
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

    MediaTrack(JSONObject jSONObject) throws JSONException {
        this(0, 0, (String) null, (String) null, (String) null, (String) null, -1, (String) null);
        this.zzakg = jSONObject.getLong("trackId");
        String string = jSONObject.getString("type");
        if ("TEXT".equals(string)) {
            this.zzamr = 1;
        } else if ("AUDIO".equals(string)) {
            this.zzamr = 2;
        } else if (ShareConstants.VIDEO_URL.equals(string)) {
            this.zzamr = 3;
        } else {
            String valueOf = String.valueOf(string);
            throw new JSONException(valueOf.length() != 0 ? "invalid type: ".concat(valueOf) : new String("invalid type: "));
        }
        this.zzapX = jSONObject.optString("trackContentId", (String) null);
        this.zzapZ = jSONObject.optString("trackContentType", (String) null);
        this.mName = jSONObject.optString("name", (String) null);
        this.zzaeT = jSONObject.optString("language", (String) null);
        if (jSONObject.has("subtype")) {
            String string2 = jSONObject.getString("subtype");
            if ("SUBTITLES".equals(string2)) {
                this.zzaqN = 1;
            } else if ("CAPTIONS".equals(string2)) {
                this.zzaqN = 2;
            } else if ("DESCRIPTIONS".equals(string2)) {
                this.zzaqN = 3;
            } else if ("CHAPTERS".equals(string2)) {
                this.zzaqN = 4;
            } else if ("METADATA".equals(string2)) {
                this.zzaqN = 5;
            } else {
                String valueOf2 = String.valueOf(string2);
                throw new JSONException(valueOf2.length() != 0 ? "invalid subtype: ".concat(valueOf2) : new String("invalid subtype: "));
            }
        } else {
            this.zzaqN = 0;
        }
        this.zzaoD = jSONObject.optJSONObject("customData");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaTrack)) {
            return false;
        }
        MediaTrack mediaTrack = (MediaTrack) obj;
        if ((this.zzaoD == null) == (mediaTrack.zzaoD == null)) {
            return (this.zzaoD == null || mediaTrack.zzaoD == null || zzo.zzc(this.zzaoD, mediaTrack.zzaoD)) && this.zzakg == mediaTrack.zzakg && this.zzamr == mediaTrack.zzamr && zzaye.zza(this.zzapX, mediaTrack.zzapX) && zzaye.zza(this.zzapZ, mediaTrack.zzapZ) && zzaye.zza(this.mName, mediaTrack.mName) && zzaye.zza(this.zzaeT, mediaTrack.zzaeT) && this.zzaqN == mediaTrack.zzaqN;
        }
        return false;
    }

    public final String getContentId() {
        return this.zzapX;
    }

    public final String getContentType() {
        return this.zzapZ;
    }

    public final JSONObject getCustomData() {
        return this.zzaoD;
    }

    public final long getId() {
        return this.zzakg;
    }

    public final String getLanguage() {
        return this.zzaeT;
    }

    public final String getName() {
        return this.mName;
    }

    public final int getSubtype() {
        return this.zzaqN;
    }

    public final int getType() {
        return this.zzamr;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzakg), Integer.valueOf(this.zzamr), this.zzapX, this.zzapZ, this.mName, this.zzaeT, Integer.valueOf(this.zzaqN), String.valueOf(this.zzaoD)});
    }

    public final void setContentId(String str) {
        this.zzapX = str;
    }

    public final void setContentType(String str) {
        this.zzapZ = str;
    }

    /* access modifiers changed from: package-private */
    public final void setCustomData(JSONObject jSONObject) {
        this.zzaoD = jSONObject;
    }

    /* access modifiers changed from: package-private */
    public final void setLanguage(String str) {
        this.zzaeT = str;
    }

    /* access modifiers changed from: package-private */
    public final void setName(String str) {
        this.mName = str;
    }

    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("trackId", this.zzakg);
            switch (this.zzamr) {
                case 1:
                    jSONObject.put("type", "TEXT");
                    break;
                case 2:
                    jSONObject.put("type", "AUDIO");
                    break;
                case 3:
                    jSONObject.put("type", ShareConstants.VIDEO_URL);
                    break;
            }
            if (this.zzapX != null) {
                jSONObject.put("trackContentId", this.zzapX);
            }
            if (this.zzapZ != null) {
                jSONObject.put("trackContentType", this.zzapZ);
            }
            if (this.mName != null) {
                jSONObject.put("name", this.mName);
            }
            if (!TextUtils.isEmpty(this.zzaeT)) {
                jSONObject.put("language", this.zzaeT);
            }
            switch (this.zzaqN) {
                case 1:
                    jSONObject.put("subtype", "SUBTITLES");
                    break;
                case 2:
                    jSONObject.put("subtype", "CAPTIONS");
                    break;
                case 3:
                    jSONObject.put("subtype", "DESCRIPTIONS");
                    break;
                case 4:
                    jSONObject.put("subtype", "CHAPTERS");
                    break;
                case 5:
                    jSONObject.put("subtype", "METADATA");
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
        zzd.zza(parcel, 2, getId());
        zzd.zzc(parcel, 3, getType());
        zzd.zza(parcel, 4, getContentId(), false);
        zzd.zza(parcel, 5, getContentType(), false);
        zzd.zza(parcel, 6, getName(), false);
        zzd.zza(parcel, 7, getLanguage(), false);
        zzd.zzc(parcel, 8, getSubtype());
        zzd.zza(parcel, 9, this.zzaoC, false);
        zzd.zzI(parcel, zze);
    }

    /* access modifiers changed from: package-private */
    public final void zzW(int i) throws IllegalArgumentException {
        if (i < 0 || i > 5) {
            throw new IllegalArgumentException(new StringBuilder(27).append("invalid subtype ").append(i).toString());
        } else if (i == 0 || this.zzamr == 1) {
            this.zzaqN = i;
        } else {
            throw new IllegalArgumentException("subtypes are only valid for text tracks");
        }
    }
}
