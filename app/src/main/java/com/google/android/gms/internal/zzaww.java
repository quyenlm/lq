package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzaww extends UIController {
    private final View mView;
    private final int zzavW;

    public zzaww(View view, int i) {
        this.mView = view;
        this.zzavW = i;
    }

    private final void zzok() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient == null || !remoteMediaClient.hasMediaSession()) {
            this.mView.setVisibility(this.zzavW);
        } else if (remoteMediaClient.getMediaStatus().getPreloadedItemId() == 0) {
            this.mView.setVisibility(this.zzavW);
        } else {
            this.mView.setVisibility(0);
        }
    }

    public final void onMediaStatusUpdated() {
        zzok();
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        zzok();
    }

    public final void onSessionEnded() {
        this.mView.setVisibility(this.zzavW);
        super.onSessionEnded();
    }
}
