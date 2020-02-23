package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzawn extends UIController {
    private final View mView;
    private final View.OnClickListener zzavp;

    public zzawn(View view, long j) {
        this.mView = view;
        this.zzavp = new zzawo(this, j);
    }

    private final void zzok() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient == null || !remoteMediaClient.hasMediaSession() || remoteMediaClient.isLiveStream() || remoteMediaClient.isPlayingAd()) {
            this.mView.setEnabled(false);
        } else {
            this.mView.setEnabled(true);
        }
    }

    public final void onMediaStatusUpdated() {
        zzok();
    }

    public final void onSendingRemoteMediaRequest() {
        this.mView.setEnabled(false);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.mView.setOnClickListener(this.zzavp);
        zzok();
    }

    public final void onSessionEnded() {
        this.mView.setOnClickListener((View.OnClickListener) null);
        super.onSessionEnded();
    }
}
