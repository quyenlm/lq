package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.internal.zzaye;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class MediaInfo extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<MediaInfo> CREATOR = new zzac();
    public static final int STREAM_TYPE_BUFFERED = 1;
    public static final int STREAM_TYPE_INVALID = -1;
    public static final int STREAM_TYPE_LIVE = 2;
    public static final int STREAM_TYPE_NONE = 0;
    public static final long UNKNOWN_DURATION = -1;
    private String zzaoC;
    private JSONObject zzaoD;
    private final String zzapX;
    private int zzapY;
    private String zzapZ;
    private MediaMetadata zzaqa;
    private long zzaqb;
    private List<MediaTrack> zzaqc;
    private TextTrackStyle zzaqd;
    private List<AdBreakInfo> zzaqe;
    private List<AdBreakClipInfo> zzaqf;

    public static class Builder {
        private final MediaInfo zzaqg;

        public Builder(String str) throws IllegalArgumentException {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Content ID cannot be empty");
            }
            this.zzaqg = new MediaInfo(str);
        }

        public MediaInfo build() throws IllegalArgumentException {
            this.zzaqg.zznj();
            return this.zzaqg;
        }

        public Builder setContentType(String str) throws IllegalArgumentException {
            this.zzaqg.setContentType(str);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zzaqg.setCustomData(jSONObject);
            return this;
        }

        public Builder setMediaTracks(List<MediaTrack> list) {
            this.zzaqg.zzy(list);
            return this;
        }

        public Builder setMetadata(MediaMetadata mediaMetadata) {
            this.zzaqg.zza(mediaMetadata);
            return this;
        }

        public Builder setStreamDuration(long j) throws IllegalArgumentException {
            this.zzaqg.zzw(j);
            return this;
        }

        public Builder setStreamType(int i) throws IllegalArgumentException {
            this.zzaqg.setStreamType(i);
            return this;
        }

        public Builder setTextTrackStyle(TextTrackStyle textTrackStyle) {
            this.zzaqg.setTextTrackStyle(textTrackStyle);
            return this;
        }
    }

    MediaInfo(String str) throws IllegalArgumentException {
        this(str, -1, (String) null, (MediaMetadata) null, -1, (List<MediaTrack>) null, (TextTrackStyle) null, (String) null, (List<AdBreakInfo>) null, (List<AdBreakClipInfo>) null);
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("content ID cannot be null or empty");
        }
    }

    MediaInfo(String str, int i, String str2, MediaMetadata mediaMetadata, long j, List<MediaTrack> list, TextTrackStyle textTrackStyle, String str3, List<AdBreakInfo> list2, List<AdBreakClipInfo> list3) {
        this.zzapX = str;
        this.zzapY = i;
        this.zzapZ = str2;
        this.zzaqa = mediaMetadata;
        this.zzaqb = j;
        this.zzaqc = list;
        this.zzaqd = textTrackStyle;
        this.zzaoC = str3;
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
        this.zzaqe = list2;
        this.zzaqf = list3;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    MediaInfo(JSONObject jSONObject) throws JSONException {
        this(jSONObject.getString("contentId"), -1, (String) null, (MediaMetadata) null, -1, (List<MediaTrack>) null, (TextTrackStyle) null, (String) null, (List<AdBreakInfo>) null, (List<AdBreakClipInfo>) null);
        String string = jSONObject.getString("streamType");
        if (IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT.equals(string)) {
            this.zzapY = 0;
        } else if ("BUFFERED".equals(string)) {
            this.zzapY = 1;
        } else if ("LIVE".equals(string)) {
            this.zzapY = 2;
        } else {
            this.zzapY = -1;
        }
        this.zzapZ = jSONObject.getString(HttpRequestParams.NOTICE_CONTENT_TYPE);
        if (jSONObject.has("metadata")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("metadata");
            this.zzaqa = new MediaMetadata(jSONObject2.getInt("metadataType"));
            this.zzaqa.zzl(jSONObject2);
        }
        this.zzaqb = -1;
        if (jSONObject.has("duration") && !jSONObject.isNull("duration")) {
            double optDouble = jSONObject.optDouble("duration", FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
            if (!Double.isNaN(optDouble) && !Double.isInfinite(optDouble)) {
                this.zzaqb = (long) (optDouble * 1000.0d);
            }
        }
        if (jSONObject.has("tracks")) {
            this.zzaqc = new ArrayList();
            JSONArray jSONArray = jSONObject.getJSONArray("tracks");
            for (int i = 0; i < jSONArray.length(); i++) {
                this.zzaqc.add(new MediaTrack(jSONArray.getJSONObject(i)));
            }
        } else {
            this.zzaqc = null;
        }
        if (jSONObject.has("textTrackStyle")) {
            JSONObject jSONObject3 = jSONObject.getJSONObject("textTrackStyle");
            TextTrackStyle textTrackStyle = new TextTrackStyle();
            textTrackStyle.zzl(jSONObject3);
            this.zzaqd = textTrackStyle;
        } else {
            this.zzaqd = null;
        }
        zzk(jSONObject);
        this.zzaoD = jSONObject.optJSONObject("customData");
    }

    /* access modifiers changed from: private */
    public final void zznj() throws IllegalArgumentException {
        if (TextUtils.isEmpty(this.zzapX)) {
            throw new IllegalArgumentException("content ID cannot be null or empty");
        } else if (TextUtils.isEmpty(this.zzapZ)) {
            throw new IllegalArgumentException("content type cannot be null or empty");
        } else if (this.zzapY == -1) {
            throw new IllegalArgumentException("a valid stream type must be specified");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaInfo)) {
            return false;
        }
        MediaInfo mediaInfo = (MediaInfo) obj;
        if ((this.zzaoD == null) == (mediaInfo.zzaoD == null)) {
            return (this.zzaoD == null || mediaInfo.zzaoD == null || zzo.zzc(this.zzaoD, mediaInfo.zzaoD)) && zzaye.zza(this.zzapX, mediaInfo.zzapX) && this.zzapY == mediaInfo.zzapY && zzaye.zza(this.zzapZ, mediaInfo.zzapZ) && zzaye.zza(this.zzaqa, mediaInfo.zzaqa) && this.zzaqb == mediaInfo.zzaqb && zzaye.zza(this.zzaqc, mediaInfo.zzaqc) && zzaye.zza(this.zzaqd, mediaInfo.zzaqd) && zzaye.zza(this.zzaqe, mediaInfo.zzaqe) && zzaye.zza(this.zzaqf, mediaInfo.zzaqf);
        }
        return false;
    }

    public final List<AdBreakClipInfo> getAdBreakClips() {
        if (this.zzaqf == null) {
            return null;
        }
        return Collections.unmodifiableList(this.zzaqf);
    }

    public final List<AdBreakInfo> getAdBreaks() {
        if (this.zzaqe == null) {
            return null;
        }
        return Collections.unmodifiableList(this.zzaqe);
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

    public final List<MediaTrack> getMediaTracks() {
        return this.zzaqc;
    }

    public final MediaMetadata getMetadata() {
        return this.zzaqa;
    }

    public final long getStreamDuration() {
        return this.zzaqb;
    }

    public final int getStreamType() {
        return this.zzapY;
    }

    public final TextTrackStyle getTextTrackStyle() {
        return this.zzaqd;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzapX, Integer.valueOf(this.zzapY), this.zzapZ, this.zzaqa, Long.valueOf(this.zzaqb), String.valueOf(this.zzaoD), this.zzaqc, this.zzaqd, this.zzaqe, this.zzaqf});
    }

    /* access modifiers changed from: package-private */
    public final void setContentType(String str) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("content type cannot be null or empty");
        }
        this.zzapZ = str;
    }

    /* access modifiers changed from: package-private */
    public final void setCustomData(JSONObject jSONObject) {
        this.zzaoD = jSONObject;
    }

    /* access modifiers changed from: package-private */
    public final void setStreamType(int i) throws IllegalArgumentException {
        if (i < -1 || i > 2) {
            throw new IllegalArgumentException("invalid stream type");
        }
        this.zzapY = i;
    }

    public final void setTextTrackStyle(TextTrackStyle textTrackStyle) {
        this.zzaqd = textTrackStyle;
    }

    public final JSONObject toJson() {
        String str;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("contentId", this.zzapX);
            switch (this.zzapY) {
                case 1:
                    str = "BUFFERED";
                    break;
                case 2:
                    str = "LIVE";
                    break;
                default:
                    str = IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT;
                    break;
            }
            jSONObject.put("streamType", str);
            if (this.zzapZ != null) {
                jSONObject.put(HttpRequestParams.NOTICE_CONTENT_TYPE, this.zzapZ);
            }
            if (this.zzaqa != null) {
                jSONObject.put("metadata", this.zzaqa.toJson());
            }
            if (this.zzaqb <= -1) {
                jSONObject.put("duration", JSONObject.NULL);
            } else {
                jSONObject.put("duration", ((double) this.zzaqb) / 1000.0d);
            }
            if (this.zzaqc != null) {
                JSONArray jSONArray = new JSONArray();
                for (MediaTrack json : this.zzaqc) {
                    jSONArray.put(json.toJson());
                }
                jSONObject.put("tracks", jSONArray);
            }
            if (this.zzaqd != null) {
                jSONObject.put("textTrackStyle", this.zzaqd.toJson());
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
        zzd.zza(parcel, 2, getContentId(), false);
        zzd.zzc(parcel, 3, getStreamType());
        zzd.zza(parcel, 4, getContentType(), false);
        zzd.zza(parcel, 5, (Parcelable) getMetadata(), i, false);
        zzd.zza(parcel, 6, getStreamDuration());
        zzd.zzc(parcel, 7, getMediaTracks(), false);
        zzd.zza(parcel, 8, (Parcelable) getTextTrackStyle(), i, false);
        zzd.zza(parcel, 9, this.zzaoC, false);
        zzd.zzc(parcel, 10, getAdBreaks(), false);
        zzd.zzc(parcel, 11, getAdBreakClips(), false);
        zzd.zzI(parcel, zze);
    }

    /* access modifiers changed from: package-private */
    public final void zza(MediaMetadata mediaMetadata) {
        this.zzaqa = mediaMetadata;
    }

    /* access modifiers changed from: package-private */
    public final void zzk(JSONObject jSONObject) throws JSONException {
        int i = 0;
        if (jSONObject.has("breaks")) {
            JSONArray jSONArray = jSONObject.getJSONArray("breaks");
            this.zzaqe = new ArrayList(jSONArray.length());
            int i2 = 0;
            while (true) {
                if (i2 < jSONArray.length()) {
                    AdBreakInfo zzi = AdBreakInfo.zzi(jSONArray.getJSONObject(i2));
                    if (zzi == null) {
                        this.zzaqe.clear();
                        break;
                    } else {
                        this.zzaqe.add(zzi);
                        i2++;
                    }
                } else {
                    break;
                }
            }
        }
        if (jSONObject.has("breakClips")) {
            JSONArray jSONArray2 = jSONObject.getJSONArray("breakClips");
            this.zzaqf = new ArrayList(jSONArray2.length());
            while (i < jSONArray2.length()) {
                AdBreakClipInfo zzh = AdBreakClipInfo.zzh(jSONArray2.getJSONObject(i));
                if (zzh != null) {
                    this.zzaqf.add(zzh);
                    i++;
                } else {
                    this.zzaqf.clear();
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzw(long j) throws IllegalArgumentException {
        if (j >= 0 || j == -1) {
            this.zzaqb = j;
            return;
        }
        throw new IllegalArgumentException("Invalid stream duration");
    }

    /* access modifiers changed from: package-private */
    public final void zzy(List<MediaTrack> list) {
        this.zzaqc = list;
    }

    public final void zzz(List<AdBreakInfo> list) {
        this.zzaqe = list;
    }
}
