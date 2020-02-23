package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzawc extends UIController {
    private final View mView;

    public zzawc(View view) {
        this.mView = view;
    }

    private final void zzok() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient == null || !remoteMediaClient.hasMediaSession() || !remoteMediaClient.isBuffering()) {
            this.mView.setVisibility(8);
        } else {
            this.mView.setVisibility(0);
        }
    }

    public final void onMediaStatusUpdated() {
        zzok();
    }

    public final void onSendingRemoteMediaRequest() {
        this.mView.setVisibility(0);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        zzok();
    }

    public final void onSessionEnded() {
        this.mView.setVisibility(8);
        super.onSessionEnded();
    }
}
