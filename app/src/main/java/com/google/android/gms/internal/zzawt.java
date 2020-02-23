package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.widget.TextView;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzawt extends UIController {
    private final TextView zzavZ;

    public zzawt(@NonNull TextView textView) {
        this.zzavZ = textView;
    }

    public final void onMediaStatusUpdated() {
        MediaInfo mediaInfo;
        MediaMetadata metadata;
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && (mediaInfo = remoteMediaClient.getMediaInfo()) != null && (metadata = mediaInfo.getMetadata()) != null) {
            String str = MediaMetadata.KEY_SUBTITLE;
            if (!metadata.containsKey(str)) {
                switch (metadata.getMediaType()) {
                    case 1:
                        str = MediaMetadata.KEY_STUDIO;
                        break;
                    case 2:
                        str = MediaMetadata.KEY_SERIES_TITLE;
                        break;
                    case 3:
                        if (!metadata.containsKey(MediaMetadata.KEY_ARTIST)) {
                            if (!metadata.containsKey(MediaMetadata.KEY_ALBUM_ARTIST)) {
                                if (metadata.containsKey(MediaMetadata.KEY_COMPOSER)) {
                                    str = MediaMetadata.KEY_COMPOSER;
                                    break;
                                }
                            } else {
                                str = MediaMetadata.KEY_ALBUM_ARTIST;
                                break;
                            }
                        }
                        break;
                    case 4:
                        str = MediaMetadata.KEY_ARTIST;
                        break;
                }
            }
            if (metadata.containsKey(str)) {
                this.zzavZ.setText(metadata.getString(str));
            }
        }
    }
}
