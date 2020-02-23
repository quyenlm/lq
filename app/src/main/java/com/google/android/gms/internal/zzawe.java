package com.google.android.gms.internal;

import android.widget.TextView;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;
import java.util.ArrayList;
import java.util.List;

public final class zzawe extends UIController {
    private final TextView mView;
    private final List<String> zzavC = new ArrayList();

    public zzawe(TextView textView, List<String> list) {
        this.mView = textView;
        this.zzavC.addAll(list);
    }

    public final void onMediaStatusUpdated() {
        MediaInfo mediaInfo;
        MediaMetadata metadata;
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession() && (mediaInfo = remoteMediaClient.getMediaStatus().getMediaInfo()) != null && (metadata = mediaInfo.getMetadata()) != null) {
            for (String next : this.zzavC) {
                if (metadata.containsKey(next)) {
                    this.mView.setText(metadata.getString(next));
                    return;
                }
            }
        }
    }
}
