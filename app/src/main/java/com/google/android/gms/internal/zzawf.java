package com.google.android.gms.internal;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.google.android.gms.R;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzawf extends UIController {
    /* access modifiers changed from: private */
    public static final zzayo zzarK = new zzayo("MuteToggleUIController");
    private final Cast.Listener zzaoY = new zzawg(this);
    /* access modifiers changed from: private */
    public final Context zzarM;
    private final String zzavD = this.zzarM.getString(R.string.cast_mute);
    private final String zzavE = this.zzarM.getString(R.string.cast_unmute);
    private final View.OnClickListener zzavp = new zzawh(this);
    private final ImageView zzavr;

    public zzawf(ImageView imageView, Context context) {
        this.zzavr = imageView;
        this.zzarM = context.getApplicationContext();
    }

    /* access modifiers changed from: private */
    public final void zzW(boolean z) {
        this.zzavr.setSelected(z);
        this.zzavr.setContentDescription(z ? this.zzavD : this.zzavE);
    }

    /* access modifiers changed from: private */
    public final void zzok() {
        CastSession currentCastSession = CastContext.getSharedInstance(this.zzarM).getSessionManager().getCurrentCastSession();
        if (currentCastSession == null || !currentCastSession.isConnected()) {
            this.zzavr.setEnabled(false);
            return;
        }
        this.zzavr.setEnabled(true);
        if (currentCastSession.isMute()) {
            zzW(false);
        } else {
            zzW(true);
        }
    }

    public final void onMediaStatusUpdated() {
        this.zzavr.setEnabled(true);
    }

    public final void onSendingRemoteMediaRequest() {
        this.zzavr.setEnabled(false);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.zzavr.setOnClickListener(this.zzavp);
        castSession.addCastListener(this.zzaoY);
        zzok();
    }

    public final void onSessionEnded() {
        this.zzavr.setOnClickListener((View.OnClickListener) null);
        CastSession currentCastSession = CastContext.getSharedInstance(this.zzarM).getSessionManager().getCurrentCastSession();
        if (currentCastSession != null) {
            currentCastSession.removeCastListener(this.zzaoY);
        }
        super.onSessionEnded();
    }
}
