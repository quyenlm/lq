package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.text.TextUtils;
import android.view.View;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;
import java.lang.ref.WeakReference;

public final class zzavy extends UIController {
    private final View mView;
    /* access modifiers changed from: private */
    public final WeakReference<Activity> zzavm;
    private final View.OnClickListener zzavp;
    /* access modifiers changed from: private */
    public final ComponentName zzavy;

    public zzavy(View view, Activity activity) {
        this.mView = view;
        this.zzavm = new WeakReference<>(activity);
        CastMediaOptions castMediaOptions = CastContext.getSharedInstance(activity).getCastOptions().getCastMediaOptions();
        if (castMediaOptions == null || TextUtils.isEmpty(castMediaOptions.getExpandedControllerActivityClassName())) {
            this.zzavy = null;
            this.zzavp = null;
            return;
        }
        this.zzavy = new ComponentName(activity.getApplicationContext(), castMediaOptions.getExpandedControllerActivityClassName());
        this.zzavp = new zzavz(this);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.mView.setOnClickListener(this.zzavp);
    }

    public final void onSessionEnded() {
        this.mView.setOnClickListener((View.OnClickListener) null);
        super.onSessionEnded();
    }
}
