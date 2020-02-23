package com.google.android.gms.cast.framework.media;

import android.support.annotation.NonNull;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.media.zzb;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;

public class ImagePicker {
    public static final int IMAGE_TYPE_EXPANDED_CONTROLLER_BACKGROUND = 4;
    public static final int IMAGE_TYPE_LOCK_SCREEN_BACKGROUND = 3;
    public static final int IMAGE_TYPE_MEDIA_ROUTE_CONTROLLER_DIALOG_BACKGROUND = 0;
    public static final int IMAGE_TYPE_MINI_CONTROLLER_THUMBNAIL = 2;
    public static final int IMAGE_TYPE_NOTIFICATION_THUMBNAIL = 1;
    public static final int IMAGE_TYPE_UNKNOWN = -1;
    private final zzb zzatK = new zza();

    class zza extends zzb.zza {
        private zza() {
        }

        public final WebImage onPickImage(MediaMetadata mediaMetadata, int i) {
            return ImagePicker.this.onPickImage(mediaMetadata, i);
        }

        public final WebImage zza(MediaMetadata mediaMetadata, ImageHints imageHints) {
            return ImagePicker.this.onPickImage(mediaMetadata, imageHints);
        }

        public final IObjectWrapper zznT() {
            return zzn.zzw(ImagePicker.this);
        }

        public final int zznm() {
            return 11020208;
        }
    }

    @Deprecated
    public WebImage onPickImage(MediaMetadata mediaMetadata, int i) {
        if (mediaMetadata == null || !mediaMetadata.hasImages()) {
            return null;
        }
        return mediaMetadata.getImages().get(0);
    }

    public WebImage onPickImage(MediaMetadata mediaMetadata, @NonNull ImageHints imageHints) {
        return onPickImage(mediaMetadata, imageHints.getType());
    }

    public final zzb zznU() {
        return this.zzatK;
    }
}
