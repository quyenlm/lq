package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzawr extends UIController {
    private final View mView;
    private final int zzavW;
    private final View.OnClickListener zzavp = new zzaws(this);

    public zzawr(View view, int i) {
        this.mView = view;
        this.zzavW = i;
    }

    private final void zzoo() {
        boolean z;
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession()) {
            MediaStatus mediaStatus = remoteMediaClient.getMediaStatus();
            if (mediaStatus.getQueueRepeatMode() == 0) {
                Integer indexById = mediaStatus.getIndexById(mediaStatus.getCurrentItemId());
                z = indexById != null && indexById.intValue() > 0;
            } else {
                z = true;
            }
            if (!z || remoteMediaClient.isPlayingAd()) {
                this.mView.setVisibility(this.zzavW);
                this.mView.setClickable(false);
                this.mView.setEnabled(false);
                return;
            }
            this.mView.setVisibility(0);
            this.mView.setClickable(true);
            this.mView.setEnabled(true);
        }
    }

    public final void onMediaStatusUpdated() {
        zzoo();
    }

    public final void onSendingRemoteMediaRequest() {
        this.mView.setEnabled(false);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.mView.setOnClickListener(this.zzavp);
        zzoo();
    }

    public final void onSessionEnded() {
        this.mView.setOnClickListener((View.OnClickListener) null);
        super.onSessionEnded();
    }
}
