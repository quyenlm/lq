package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.internal.zzayv;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class MediaMetadata extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Parcelable.Creator<MediaMetadata> CREATOR = new zzad();
    public static final String KEY_ALBUM_ARTIST = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
    public static final String KEY_ALBUM_TITLE = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
    public static final String KEY_ARTIST = "com.google.android.gms.cast.metadata.ARTIST";
    public static final String KEY_BROADCAST_DATE = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
    public static final String KEY_COMPOSER = "com.google.android.gms.cast.metadata.COMPOSER";
    public static final String KEY_CREATION_DATE = "com.google.android.gms.cast.metadata.CREATION_DATE";
    public static final String KEY_DISC_NUMBER = "com.google.android.gms.cast.metadata.DISC_NUMBER";
    public static final String KEY_EPISODE_NUMBER = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
    public static final String KEY_HEIGHT = "com.google.android.gms.cast.metadata.HEIGHT";
    public static final String KEY_LOCATION_LATITUDE = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
    public static final String KEY_LOCATION_LONGITUDE = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
    public static final String KEY_LOCATION_NAME = "com.google.android.gms.cast.metadata.LOCATION_NAME";
    public static final String KEY_RELEASE_DATE = "com.google.android.gms.cast.metadata.RELEASE_DATE";
    public static final String KEY_SEASON_NUMBER = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
    public static final String KEY_SERIES_TITLE = "com.google.android.gms.cast.metadata.SERIES_TITLE";
    public static final String KEY_STUDIO = "com.google.android.gms.cast.metadata.STUDIO";
    public static final String KEY_SUBTITLE = "com.google.android.gms.cast.metadata.SUBTITLE";
    public static final String KEY_TITLE = "com.google.android.gms.cast.metadata.TITLE";
    public static final String KEY_TRACK_NUMBER = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
    public static final String KEY_WIDTH = "com.google.android.gms.cast.metadata.WIDTH";
    public static final int MEDIA_TYPE_GENERIC = 0;
    public static final int MEDIA_TYPE_MOVIE = 1;
    public static final int MEDIA_TYPE_MUSIC_TRACK = 3;
    public static final int MEDIA_TYPE_PHOTO = 4;
    public static final int MEDIA_TYPE_TV_SHOW = 2;
    public static final int MEDIA_TYPE_USER = 100;
    private static final String[] zzaqh = {null, "String", "int", "double", "ISO-8601 date String"};
    private static final zza zzaqi = new zza().zzb(KEY_CREATION_DATE, "creationDateTime", 4).zzb(KEY_RELEASE_DATE, "releaseDate", 4).zzb(KEY_BROADCAST_DATE, "originalAirdate", 4).zzb(KEY_TITLE, "title", 1).zzb(KEY_SUBTITLE, MessengerShareContentUtility.SUBTITLE, 1).zzb(KEY_ARTIST, "artist", 1).zzb(KEY_ALBUM_ARTIST, "albumArtist", 1).zzb(KEY_ALBUM_TITLE, "albumName", 1).zzb(KEY_COMPOSER, "composer", 1).zzb(KEY_DISC_NUMBER, "discNumber", 2).zzb(KEY_TRACK_NUMBER, "trackNumber", 2).zzb(KEY_SEASON_NUMBER, "season", 2).zzb(KEY_EPISODE_NUMBER, "episode", 2).zzb(KEY_SERIES_TITLE, "seriesTitle", 1).zzb(KEY_STUDIO, "studio", 1).zzb(KEY_WIDTH, "width", 2).zzb(KEY_HEIGHT, "height", 2).zzb(KEY_LOCATION_NAME, "location", 1).zzb(KEY_LOCATION_LATITUDE, "latitude", 3).zzb(KEY_LOCATION_LONGITUDE, "longitude", 3);
    private final List<WebImage> zzHC;
    private Bundle zzaqj;
    private int zzaqk;

    static class zza {
        private final Map<String, String> zzaql = new HashMap();
        private final Map<String, String> zzaqm = new HashMap();
        private final Map<String, Integer> zzaqn = new HashMap();

        public final zza zzb(String str, String str2, int i) {
            this.zzaql.put(str, str2);
            this.zzaqm.put(str2, str);
            this.zzaqn.put(str, Integer.valueOf(i));
            return this;
        }

        public final String zzbY(String str) {
            return this.zzaql.get(str);
        }

        public final String zzbZ(String str) {
            return this.zzaqm.get(str);
        }

        public final int zzca(String str) {
            Integer num = this.zzaqn.get(str);
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }
    }

    public MediaMetadata() {
        this(0);
    }

    public MediaMetadata(int i) {
        this(new ArrayList(), new Bundle(), i);
    }

    MediaMetadata(List<WebImage> list, Bundle bundle, int i) {
        this.zzHC = list;
        this.zzaqj = bundle;
        this.zzaqk = i;
    }

    private final void zza(JSONObject jSONObject, String... strArr) {
        try {
            for (String str : strArr) {
                if (this.zzaqj.containsKey(str)) {
                    switch (zzaqi.zzca(str)) {
                        case 1:
                        case 4:
                            jSONObject.put(zzaqi.zzbY(str), this.zzaqj.getString(str));
                            break;
                        case 2:
                            jSONObject.put(zzaqi.zzbY(str), this.zzaqj.getInt(str));
                            break;
                        case 3:
                            jSONObject.put(zzaqi.zzbY(str), this.zzaqj.getDouble(str));
                            break;
                    }
                }
            }
            for (String str2 : this.zzaqj.keySet()) {
                if (!str2.startsWith("com.google.")) {
                    Object obj = this.zzaqj.get(str2);
                    if (obj instanceof String) {
                        jSONObject.put(str2, obj);
                    } else if (obj instanceof Integer) {
                        jSONObject.put(str2, obj);
                    } else if (obj instanceof Double) {
                        jSONObject.put(str2, obj);
                    }
                }
            }
        } catch (JSONException e) {
        }
    }

    private final void zzb(JSONObject jSONObject, String... strArr) {
        HashSet hashSet = new HashSet(Arrays.asList(strArr));
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                if (!"metadataType".equals(next)) {
                    String zzbZ = zzaqi.zzbZ(next);
                    if (zzbZ == null) {
                        Object obj = jSONObject.get(next);
                        if (obj instanceof String) {
                            this.zzaqj.putString(next, (String) obj);
                        } else if (obj instanceof Integer) {
                            this.zzaqj.putInt(next, ((Integer) obj).intValue());
                        } else if (obj instanceof Double) {
                            this.zzaqj.putDouble(next, ((Double) obj).doubleValue());
                        }
                    } else if (hashSet.contains(zzbZ)) {
                        try {
                            Object obj2 = jSONObject.get(next);
                            if (obj2 != null) {
                                switch (zzaqi.zzca(zzbZ)) {
                                    case 1:
                                        if (!(obj2 instanceof String)) {
                                            break;
                                        } else {
                                            this.zzaqj.putString(zzbZ, (String) obj2);
                                            break;
                                        }
                                    case 2:
                                        if (!(obj2 instanceof Integer)) {
                                            break;
                                        } else {
                                            this.zzaqj.putInt(zzbZ, ((Integer) obj2).intValue());
                                            break;
                                        }
                                    case 3:
                                        if (!(obj2 instanceof Double)) {
                                            break;
                                        } else {
                                            this.zzaqj.putDouble(zzbZ, ((Double) obj2).doubleValue());
                                            break;
                                        }
                                    case 4:
                                        if ((obj2 instanceof String) && zzayv.zzco((String) obj2) != null) {
                                            this.zzaqj.putString(zzbZ, (String) obj2);
                                            break;
                                        }
                                }
                            }
                        } catch (JSONException e) {
                        }
                    }
                }
            }
        } catch (JSONException e2) {
        }
    }

    private final boolean zzb(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if ((obj instanceof Bundle) && (obj2 instanceof Bundle) && !zzb((Bundle) obj, (Bundle) obj2)) {
                return false;
            }
            if (obj == null) {
                if (obj2 != null || !bundle2.containsKey(str)) {
                    return false;
                }
            } else if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    private static void zzf(String str, int i) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("null and empty keys are not allowed");
        }
        int zzca = zzaqi.zzca(str);
        if (zzca != i && zzca != 0) {
            String valueOf = String.valueOf(zzaqh[i]);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(valueOf).length()).append("Value for ").append(str).append(" must be a ").append(valueOf).toString());
        }
    }

    public void addImage(WebImage webImage) {
        this.zzHC.add(webImage);
    }

    public void clear() {
        this.zzaqj.clear();
        this.zzHC.clear();
    }

    public void clearImages() {
        this.zzHC.clear();
    }

    public boolean containsKey(String str) {
        return this.zzaqj.containsKey(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaMetadata)) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) obj;
        return zzb(this.zzaqj, mediaMetadata.zzaqj) && this.zzHC.equals(mediaMetadata.zzHC);
    }

    public Calendar getDate(String str) {
        zzf(str, 4);
        String string = this.zzaqj.getString(str);
        if (string != null) {
            return zzayv.zzco(string);
        }
        return null;
    }

    public String getDateAsString(String str) {
        zzf(str, 4);
        return this.zzaqj.getString(str);
    }

    public double getDouble(String str) {
        zzf(str, 3);
        return this.zzaqj.getDouble(str);
    }

    public List<WebImage> getImages() {
        return this.zzHC;
    }

    public int getInt(String str) {
        zzf(str, 2);
        return this.zzaqj.getInt(str);
    }

    public int getMediaType() {
        return this.zzaqk;
    }

    public String getString(String str) {
        zzf(str, 1);
        return this.zzaqj.getString(str);
    }

    public boolean hasImages() {
        return this.zzHC != null && !this.zzHC.isEmpty();
    }

    public int hashCode() {
        int i = 17;
        Iterator it = this.zzaqj.keySet().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return (i2 * 31) + this.zzHC.hashCode();
            }
            i = this.zzaqj.get((String) it.next()).hashCode() + (i2 * 31);
        }
    }

    public Set<String> keySet() {
        return this.zzaqj.keySet();
    }

    public void putDate(String str, Calendar calendar) {
        zzf(str, 4);
        this.zzaqj.putString(str, zzayv.zza(calendar));
    }

    public void putDouble(String str, double d) {
        zzf(str, 3);
        this.zzaqj.putDouble(str, d);
    }

    public void putInt(String str, int i) {
        zzf(str, 2);
        this.zzaqj.putInt(str, i);
    }

    public void putString(String str, String str2) {
        zzf(str, 1);
        this.zzaqj.putString(str, str2);
    }

    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("metadataType", this.zzaqk);
        } catch (JSONException e) {
        }
        zzayv.zza(jSONObject, this.zzHC);
        switch (this.zzaqk) {
            case 0:
                zza(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_SUBTITLE, KEY_RELEASE_DATE);
                break;
            case 1:
                zza(jSONObject, KEY_TITLE, KEY_STUDIO, KEY_SUBTITLE, KEY_RELEASE_DATE);
                break;
            case 2:
                zza(jSONObject, KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE);
                break;
            case 3:
                zza(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_ALBUM_TITLE, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE);
                break;
            case 4:
                zza(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE);
                break;
            default:
                zza(jSONObject, new String[0]);
                break;
        }
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, getImages(), false);
        zzd.zza(parcel, 3, this.zzaqj, false);
        zzd.zzc(parcel, 4, getMediaType());
        zzd.zzI(parcel, zze);
    }

    public final void zzl(JSONObject jSONObject) {
        clear();
        this.zzaqk = 0;
        try {
            this.zzaqk = jSONObject.getInt("metadataType");
        } catch (JSONException e) {
        }
        zzayv.zza(this.zzHC, jSONObject);
        switch (this.zzaqk) {
            case 0:
                zzb(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_SUBTITLE, KEY_RELEASE_DATE);
                return;
            case 1:
                zzb(jSONObject, KEY_TITLE, KEY_STUDIO, KEY_SUBTITLE, KEY_RELEASE_DATE);
                return;
            case 2:
                zzb(jSONObject, KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE);
                return;
            case 3:
                zzb(jSONObject, KEY_TITLE, KEY_ALBUM_TITLE, KEY_ARTIST, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE);
                return;
            case 4:
                zzb(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE);
                return;
            default:
                zzb(jSONObject, new String[0]);
                return;
        }
    }
}
