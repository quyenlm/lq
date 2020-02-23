package com.google.android.gms.cast.framework.media;

import android.annotation.TargetApi;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.common.util.zzq;
import java.util.Locale;

public class MediaUtils {
    private MediaUtils() {
    }

    public static Uri getImageUri(MediaInfo mediaInfo, int i) {
        MediaMetadata metadata;
        if (mediaInfo == null || (metadata = mediaInfo.getMetadata()) == null || metadata.getImages() == null || metadata.getImages().size() <= i) {
            return null;
        }
        return metadata.getImages().get(i).getUrl();
    }

    public static String getImageUrl(MediaInfo mediaInfo, int i) {
        Uri imageUri = getImageUri(mediaInfo, i);
        if (imageUri == null) {
            return null;
        }
        return imageUri.toString();
    }

    @TargetApi(21)
    public static Locale getTrackLanguage(@NonNull MediaTrack mediaTrack) {
        if (mediaTrack.getLanguage() == null) {
            return null;
        }
        if (zzq.zzse()) {
            return Locale.forLanguageTag(mediaTrack.getLanguage());
        }
        String[] split = mediaTrack.getLanguage().split("-");
        return split.length == 1 ? new Locale(split[0]) : new Locale(split[0], split[1]);
    }
}
