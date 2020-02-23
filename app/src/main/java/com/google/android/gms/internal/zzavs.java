package com.google.android.gms.internal;

import android.app.Activity;
import android.view.View;
import com.google.android.gms.R;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

public final class zzavs extends UIController {
    private final View mView;
    /* access modifiers changed from: private */
    public final WeakReference<Activity> zzavm;
    private final String zzavn;
    private final String zzavo;
    private final View.OnClickListener zzavp = new zzavt(this);

    public zzavs(View view, Activity activity) {
        this.mView = view;
        this.zzavn = activity.getString(R.string.cast_closed_captions);
        this.zzavo = activity.getString(R.string.cast_closed_captions_unavailable);
        this.zzavm = new WeakReference<>(activity);
    }

    private final void zzok() {
        boolean z;
        int i;
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession()) {
            MediaInfo mediaInfo = remoteMediaClient.getMediaInfo();
            if (mediaInfo != null) {
                List<MediaTrack> mediaTracks = mediaInfo.getMediaTracks();
                if (mediaTracks != null && !mediaTracks.isEmpty()) {
                    Iterator<MediaTrack> it = mediaTracks.iterator();
                    int i2 = 0;
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        MediaTrack next = it.next();
                        if (next.getType() == 2) {
                            i = i2 + 1;
                            if (i > 1) {
                                z = true;
                                break;
                            }
                        } else if (next.getType() == 1) {
                            z = true;
                            break;
                        } else {
                            i = i2;
                        }
                        i2 = i;
                    }
                } else {
                    z = false;
                    if (z && !remoteMediaClient.isPlayingAd()) {
                        this.mView.setEnabled(true);
                        this.mView.setContentDescription(this.zzavn);
                        return;
                    }
                }
            }
            z = false;
            this.mView.setEnabled(true);
            this.mView.setContentDescription(this.zzavn);
            return;
        }
        this.mView.setEnabled(false);
        this.mView.setContentDescription(this.zzavo);
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
