package com.google.android.gms.internal;

import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.KeyEvent;

final class zzavq extends MediaSessionCompat.Callback {
    private /* synthetic */ zzavn zzave;

    zzavq(zzavn zzavn) {
        this.zzave = zzavn;
    }

    public final boolean onMediaButtonEvent(Intent intent) {
        KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
        if (keyEvent == null) {
            return true;
        }
        if (keyEvent.getKeyCode() != 127 && keyEvent.getKeyCode() != 126) {
            return true;
        }
        this.zzave.zzase.togglePlayback();
        return true;
    }

    public final void onPause() {
        this.zzave.zzase.togglePlayback();
    }

    public final void onPlay() {
        this.zzave.zzase.togglePlayback();
    }
}
