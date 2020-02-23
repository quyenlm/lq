package com.google.android.gms.cast.framework.media.uicontroller;

import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

public class UIController {
    private RemoteMediaClient zzase;

    /* access modifiers changed from: protected */
    public RemoteMediaClient getRemoteMediaClient() {
        return this.zzase;
    }

    public void onMediaStatusUpdated() {
    }

    public void onSendingRemoteMediaRequest() {
    }

    public void onSessionConnected(CastSession castSession) {
        if (castSession != null) {
            this.zzase = castSession.getRemoteMediaClient();
        } else {
            this.zzase = null;
        }
    }

    public void onSessionEnded() {
        this.zzase = null;
    }
}
