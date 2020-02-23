package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzavw extends UIController {
    private final ImagePicker zzatH;
    private final ImageHints zzauP;
    /* access modifiers changed from: private */
    public final ImageView zzavr;
    private final zzavc zzavt;
    private final Bitmap zzavv;
    /* access modifiers changed from: private */
    public final View zzavw;

    public zzavw(ImageView imageView, Context context, @NonNull ImageHints imageHints, int i, View view) {
        ImagePicker imagePicker = null;
        this.zzavr = imageView;
        this.zzauP = imageHints;
        this.zzavv = i != 0 ? BitmapFactory.decodeResource(context.getResources(), i) : null;
        this.zzavw = view;
        CastMediaOptions castMediaOptions = CastContext.getSharedInstance(context).getCastOptions().getCastMediaOptions();
        this.zzatH = castMediaOptions != null ? castMediaOptions.getImagePicker() : imagePicker;
        this.zzavt = new zzavc(context.getApplicationContext());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        r1 = r4.zzatH.onPickImage(r0.getMetadata(), r4.zzauP);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzok() {
        /*
            r4 = this;
            com.google.android.gms.cast.framework.media.RemoteMediaClient r0 = r4.getRemoteMediaClient()
            if (r0 == 0) goto L_0x000c
            boolean r1 = r0.hasMediaSession()
            if (r1 != 0) goto L_0x0010
        L_0x000c:
            r4.zzol()
        L_0x000f:
            return
        L_0x0010:
            com.google.android.gms.cast.MediaInfo r0 = r0.getMediaInfo()
            if (r0 != 0) goto L_0x001d
            r0 = 0
        L_0x0017:
            if (r0 != 0) goto L_0x0040
            r4.zzol()
            goto L_0x000f
        L_0x001d:
            com.google.android.gms.cast.framework.media.ImagePicker r1 = r4.zzatH
            if (r1 == 0) goto L_0x003a
            com.google.android.gms.cast.framework.media.ImagePicker r1 = r4.zzatH
            com.google.android.gms.cast.MediaMetadata r2 = r0.getMetadata()
            com.google.android.gms.cast.framework.media.ImageHints r3 = r4.zzauP
            com.google.android.gms.common.images.WebImage r1 = r1.onPickImage((com.google.android.gms.cast.MediaMetadata) r2, (com.google.android.gms.cast.framework.media.ImageHints) r3)
            if (r1 == 0) goto L_0x003a
            android.net.Uri r2 = r1.getUrl()
            if (r2 == 0) goto L_0x003a
            android.net.Uri r0 = r1.getUrl()
            goto L_0x0017
        L_0x003a:
            r1 = 0
            android.net.Uri r0 = com.google.android.gms.cast.framework.media.MediaUtils.getImageUri(r0, r1)
            goto L_0x0017
        L_0x0040:
            com.google.android.gms.internal.zzavc r1 = r4.zzavt
            r1.zzm(r0)
            goto L_0x000f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzavw.zzok():void");
    }

    private final void zzol() {
        if (this.zzavw != null) {
            this.zzavw.setVisibility(0);
            this.zzavr.setVisibility(4);
        }
        if (this.zzavv != null) {
            this.zzavr.setImageBitmap(this.zzavv);
        }
    }

    public final void onMediaStatusUpdated() {
        zzok();
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.zzavt.zza(new zzavx(this));
        zzol();
        zzok();
    }

    public final void onSessionEnded() {
        this.zzavt.clear();
        zzol();
        super.onSessionEnded();
    }
}
