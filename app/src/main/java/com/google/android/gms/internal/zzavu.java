package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzavu extends UIController {
    private final ImagePicker zzatH;
    private final ImageHints zzauP;
    /* access modifiers changed from: private */
    public final ImageView zzavr;
    private final Bitmap zzavs;
    private final zzavc zzavt;

    public zzavu(ImageView imageView, Context context, @NonNull ImageHints imageHints, int i) {
        this.zzavr = imageView;
        this.zzauP = imageHints;
        this.zzavs = BitmapFactory.decodeResource(context.getResources(), i);
        CastMediaOptions castMediaOptions = CastContext.getSharedInstance(context).getCastOptions().getCastMediaOptions();
        this.zzatH = castMediaOptions != null ? castMediaOptions.getImagePicker() : null;
        this.zzavt = new zzavc(context.getApplicationContext());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002f, code lost:
        r0 = r4.zzatH.onPickImage(r1.getMetadata(), r4.zzauP);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzok() {
        /*
            r4 = this;
            r0 = 0
            com.google.android.gms.cast.framework.media.RemoteMediaClient r1 = r4.getRemoteMediaClient()
            if (r1 == 0) goto L_0x000d
            boolean r2 = r1.hasMediaSession()
            if (r2 != 0) goto L_0x0015
        L_0x000d:
            android.widget.ImageView r0 = r4.zzavr
            android.graphics.Bitmap r1 = r4.zzavs
            r0.setImageBitmap(r1)
        L_0x0014:
            return
        L_0x0015:
            com.google.android.gms.cast.MediaQueueItem r1 = r1.getPreloadedItem()
            if (r1 != 0) goto L_0x0025
        L_0x001b:
            if (r0 != 0) goto L_0x004e
            android.widget.ImageView r0 = r4.zzavr
            android.graphics.Bitmap r1 = r4.zzavs
            r0.setImageBitmap(r1)
            goto L_0x0014
        L_0x0025:
            com.google.android.gms.cast.MediaInfo r1 = r1.getMedia()
            if (r1 == 0) goto L_0x001b
            com.google.android.gms.cast.framework.media.ImagePicker r0 = r4.zzatH
            if (r0 == 0) goto L_0x0048
            com.google.android.gms.cast.framework.media.ImagePicker r0 = r4.zzatH
            com.google.android.gms.cast.MediaMetadata r2 = r1.getMetadata()
            com.google.android.gms.cast.framework.media.ImageHints r3 = r4.zzauP
            com.google.android.gms.common.images.WebImage r0 = r0.onPickImage((com.google.android.gms.cast.MediaMetadata) r2, (com.google.android.gms.cast.framework.media.ImageHints) r3)
            if (r0 == 0) goto L_0x0048
            android.net.Uri r2 = r0.getUrl()
            if (r2 == 0) goto L_0x0048
            android.net.Uri r0 = r0.getUrl()
            goto L_0x001b
        L_0x0048:
            r0 = 0
            android.net.Uri r0 = com.google.android.gms.cast.framework.media.MediaUtils.getImageUri(r1, r0)
            goto L_0x001b
        L_0x004e:
            com.google.android.gms.internal.zzavc r1 = r4.zzavt
            r1.zzm(r0)
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzavu.zzok():void");
    }

    public final void onMediaStatusUpdated() {
        zzok();
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.zzavt.zza(new zzavv(this));
        this.zzavr.setImageBitmap(this.zzavs);
        zzok();
    }

    public final void onSessionEnded() {
        this.zzavt.clear();
        this.zzavr.setImageBitmap(this.zzavs);
        super.onSessionEnded();
    }
}
