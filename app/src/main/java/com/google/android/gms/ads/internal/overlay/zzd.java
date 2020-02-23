package com.google.android.gms.ads.internal.overlay;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.Surface;
import android.view.TextureView;
import com.garena.pay.android.inappbilling.IabHelper;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzia;
import com.google.android.gms.internal.zzzn;
import com.tencent.mna.NetworkBindingListener;
import com.tencent.smtt.sdk.TbsMediaPlayer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@zzzn
@TargetApi(14)
public final class zzd extends zzy implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, TextureView.SurfaceTextureListener {
    private static final Map<Integer, String> zzOk = new HashMap();
    private final zzar zzOl;
    private final boolean zzOm;
    private int zzOn = 0;
    private int zzOo = 0;
    private MediaPlayer zzOp;
    private Uri zzOq;
    private int zzOr;
    private int zzOs;
    private int zzOt;
    private int zzOu;
    private int zzOv;
    private zzap zzOw;
    private boolean zzOx;
    private int zzOy;
    /* access modifiers changed from: private */
    public zzx zzOz;

    static {
        if (Build.VERSION.SDK_INT >= 17) {
            zzOk.put(Integer.valueOf(IabHelper.IABHELPER_SEND_INTENT_FAILED), "MEDIA_ERROR_IO");
            zzOk.put(Integer.valueOf(IabHelper.IABHELPER_MISSING_TOKEN), "MEDIA_ERROR_MALFORMED");
            zzOk.put(Integer.valueOf(IabHelper.IABHELPER_INVALID_CONSUMPTION), "MEDIA_ERROR_UNSUPPORTED");
            zzOk.put(Integer.valueOf(NetworkBindingListener.NB_PREPARE_MATCHMODE_NOTSUPPORT), "MEDIA_ERROR_TIMED_OUT");
            zzOk.put(3, "MEDIA_INFO_VIDEO_RENDERING_START");
        }
        zzOk.put(100, "MEDIA_ERROR_SERVER_DIED");
        zzOk.put(1, "MEDIA_ERROR_UNKNOWN");
        zzOk.put(1, "MEDIA_INFO_UNKNOWN");
        zzOk.put(700, "MEDIA_INFO_VIDEO_TRACK_LAGGING");
        zzOk.put(701, "MEDIA_INFO_BUFFERING_START");
        zzOk.put(702, "MEDIA_INFO_BUFFERING_END");
        zzOk.put(Integer.valueOf(TbsMediaPlayer.TbsMediaPlayerListener.MEDIA_INFO_BAD_INTERLEAVING), "MEDIA_INFO_BAD_INTERLEAVING");
        zzOk.put(Integer.valueOf(TbsMediaPlayer.TbsMediaPlayerListener.MEDIA_INFO_NOT_SEEKABLE), "MEDIA_INFO_NOT_SEEKABLE");
        zzOk.put(Integer.valueOf(TbsMediaPlayer.TbsMediaPlayerListener.MEDIA_INFO_METADATA_UPDATE), "MEDIA_INFO_METADATA_UPDATE");
        if (Build.VERSION.SDK_INT >= 19) {
            zzOk.put(Integer.valueOf(TbsMediaPlayer.TbsMediaPlayerListener.MEDIA_INFO_UNSUPPORTED_SUBTITLE), "MEDIA_INFO_UNSUPPORTED_SUBTITLE");
            zzOk.put(Integer.valueOf(TbsMediaPlayer.TbsMediaPlayerListener.MEDIA_INFO_SUBTITLE_TIMED_OUT), "MEDIA_INFO_SUBTITLE_TIMED_OUT");
        }
    }

    public zzd(Context context, boolean z, boolean z2, zzaq zzaq, zzar zzar) {
        super(context);
        setSurfaceTextureListener(this);
        this.zzOl = zzar;
        this.zzOx = z;
        this.zzOm = z2;
        this.zzOl.zza(this);
    }

    private final void zza(float f) {
        if (this.zzOp != null) {
            try {
                this.zzOp.setVolume(f, f);
            } catch (IllegalStateException e) {
            }
        } else {
            zzafr.zzaT("AdMediaPlayerView setMediaPlayerVolume() called before onPrepared().");
        }
    }

    private final void zzfE() {
        SurfaceTexture surfaceTexture;
        zzafr.v("AdMediaPlayerView init MediaPlayer");
        SurfaceTexture surfaceTexture2 = getSurfaceTexture();
        if (this.zzOq != null && surfaceTexture2 != null) {
            zzq(false);
            try {
                zzbs.zzbQ();
                this.zzOp = new MediaPlayer();
                this.zzOp.setOnBufferingUpdateListener(this);
                this.zzOp.setOnCompletionListener(this);
                this.zzOp.setOnErrorListener(this);
                this.zzOp.setOnInfoListener(this);
                this.zzOp.setOnPreparedListener(this);
                this.zzOp.setOnVideoSizeChangedListener(this);
                this.zzOt = 0;
                if (this.zzOx) {
                    this.zzOw = new zzap(getContext());
                    this.zzOw.zza(surfaceTexture2, getWidth(), getHeight());
                    this.zzOw.start();
                    surfaceTexture = this.zzOw.zzgg();
                    if (surfaceTexture == null) {
                        this.zzOw.zzgf();
                        this.zzOw = null;
                    }
                    this.zzOp.setDataSource(getContext(), this.zzOq);
                    zzbs.zzbR();
                    this.zzOp.setSurface(new Surface(surfaceTexture));
                    this.zzOp.setAudioStreamType(3);
                    this.zzOp.setScreenOnWhilePlaying(true);
                    this.zzOp.prepareAsync();
                    zzq(1);
                }
                surfaceTexture = surfaceTexture2;
                this.zzOp.setDataSource(getContext(), this.zzOq);
                zzbs.zzbR();
                this.zzOp.setSurface(new Surface(surfaceTexture));
                this.zzOp.setAudioStreamType(3);
                this.zzOp.setScreenOnWhilePlaying(true);
                this.zzOp.prepareAsync();
                zzq(1);
            } catch (IOException | IllegalArgumentException | IllegalStateException e) {
                String valueOf = String.valueOf(this.zzOq);
                zzafr.zzc(new StringBuilder(String.valueOf(valueOf).length() + 36).append("Failed to initialize MediaPlayer at ").append(valueOf).toString(), e);
                onError(this.zzOp, 1, 0);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0034 A[LOOP:0: B:9:0x0034->B:14:0x004f, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzfF() {
        /*
            r8 = this;
            boolean r0 = r8.zzOm
            if (r0 != 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            boolean r0 = r8.zzfG()
            if (r0 == 0) goto L_0x0004
            android.media.MediaPlayer r0 = r8.zzOp
            int r0 = r0.getCurrentPosition()
            if (r0 <= 0) goto L_0x0004
            int r0 = r8.zzOo
            r1 = 3
            if (r0 == r1) goto L_0x0004
            java.lang.String r0 = "AdMediaPlayerView nudging MediaPlayer"
            com.google.android.gms.internal.zzafr.v(r0)
            r0 = 0
            r8.zza((float) r0)
            android.media.MediaPlayer r0 = r8.zzOp
            r0.start()
            android.media.MediaPlayer r0 = r8.zzOp
            int r0 = r0.getCurrentPosition()
            com.google.android.gms.common.util.zze r1 = com.google.android.gms.ads.internal.zzbs.zzbF()
            long r2 = r1.currentTimeMillis()
        L_0x0034:
            boolean r1 = r8.zzfG()
            if (r1 == 0) goto L_0x0051
            android.media.MediaPlayer r1 = r8.zzOp
            int r1 = r1.getCurrentPosition()
            if (r1 != r0) goto L_0x0051
            com.google.android.gms.common.util.zze r1 = com.google.android.gms.ads.internal.zzbs.zzbF()
            long r4 = r1.currentTimeMillis()
            long r4 = r4 - r2
            r6 = 250(0xfa, double:1.235E-321)
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 <= 0) goto L_0x0034
        L_0x0051:
            android.media.MediaPlayer r0 = r8.zzOp
            r0.pause()
            r8.zzfH()
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.overlay.zzd.zzfF():void");
    }

    private final boolean zzfG() {
        return (this.zzOp == null || this.zzOn == -1 || this.zzOn == 0 || this.zzOn == 1) ? false : true;
    }

    private final void zzq(int i) {
        if (i == 3) {
            this.zzOl.zzgj();
            this.zzPq.zzgj();
        } else if (this.zzOn == 3) {
            this.zzOl.zzgk();
            this.zzPq.zzgk();
        }
        this.zzOn = i;
    }

    private final void zzq(boolean z) {
        zzafr.v("AdMediaPlayerView release");
        if (this.zzOw != null) {
            this.zzOw.zzgf();
            this.zzOw = null;
        }
        if (this.zzOp != null) {
            this.zzOp.reset();
            this.zzOp.release();
            this.zzOp = null;
            zzq(0);
            if (z) {
                this.zzOo = 0;
                this.zzOo = 0;
            }
        }
    }

    public final int getCurrentPosition() {
        if (zzfG()) {
            return this.zzOp.getCurrentPosition();
        }
        return 0;
    }

    public final int getDuration() {
        if (zzfG()) {
            return this.zzOp.getDuration();
        }
        return -1;
    }

    public final int getVideoHeight() {
        if (this.zzOp != null) {
            return this.zzOp.getVideoHeight();
        }
        return 0;
    }

    public final int getVideoWidth() {
        if (this.zzOp != null) {
            return this.zzOp.getVideoWidth();
        }
        return 0;
    }

    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        this.zzOt = i;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        zzafr.v("AdMediaPlayerView completion");
        zzq(5);
        this.zzOo = 5;
        zzagz.zzZr.post(new zzf(this));
    }

    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        String str = zzOk.get(Integer.valueOf(i));
        String str2 = zzOk.get(Integer.valueOf(i2));
        zzafr.zzaT(new StringBuilder(String.valueOf(str).length() + 38 + String.valueOf(str2).length()).append("AdMediaPlayerView MediaPlayer error: ").append(str).append(":").append(str2).toString());
        zzq(-1);
        this.zzOo = -1;
        zzagz.zzZr.post(new zzg(this, str, str2));
        return true;
    }

    public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        String str = zzOk.get(Integer.valueOf(i));
        String str2 = zzOk.get(Integer.valueOf(i2));
        zzafr.v(new StringBuilder(String.valueOf(str).length() + 37 + String.valueOf(str2).length()).append("AdMediaPlayerView MediaPlayer info: ").append(str).append(":").append(str2).toString());
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0090, code lost:
        if (r1 > r2) goto L_0x003f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r8, int r9) {
        /*
            r7 = this;
            r3 = 1073741824(0x40000000, float:2.0)
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            int r0 = r7.zzOr
            int r1 = getDefaultSize(r0, r8)
            int r0 = r7.zzOs
            int r0 = getDefaultSize(r0, r9)
            int r2 = r7.zzOr
            if (r2 <= 0) goto L_0x0092
            int r2 = r7.zzOs
            if (r2 <= 0) goto L_0x0092
            com.google.android.gms.ads.internal.overlay.zzap r2 = r7.zzOw
            if (r2 != 0) goto L_0x0092
            int r4 = android.view.View.MeasureSpec.getMode(r8)
            int r2 = android.view.View.MeasureSpec.getSize(r8)
            int r5 = android.view.View.MeasureSpec.getMode(r9)
            int r0 = android.view.View.MeasureSpec.getSize(r9)
            if (r4 != r3) goto L_0x0078
            if (r5 != r3) goto L_0x0078
            int r1 = r7.zzOr
            int r1 = r1 * r0
            int r3 = r7.zzOs
            int r3 = r3 * r2
            if (r1 >= r3) goto L_0x0069
            int r1 = r7.zzOr
            int r1 = r1 * r0
            int r2 = r7.zzOs
            int r1 = r1 / r2
            r2 = r1
        L_0x003f:
            r7.setMeasuredDimension(r2, r0)
            com.google.android.gms.ads.internal.overlay.zzap r1 = r7.zzOw
            if (r1 == 0) goto L_0x004b
            com.google.android.gms.ads.internal.overlay.zzap r1 = r7.zzOw
            r1.zzf(r2, r0)
        L_0x004b:
            int r1 = android.os.Build.VERSION.SDK_INT
            r3 = 16
            if (r1 != r3) goto L_0x0068
            int r1 = r7.zzOu
            if (r1 <= 0) goto L_0x0059
            int r1 = r7.zzOu
            if (r1 != r2) goto L_0x0061
        L_0x0059:
            int r1 = r7.zzOv
            if (r1 <= 0) goto L_0x0064
            int r1 = r7.zzOv
            if (r1 == r0) goto L_0x0064
        L_0x0061:
            r7.zzfF()
        L_0x0064:
            r7.zzOu = r2
            r7.zzOv = r0
        L_0x0068:
            return
        L_0x0069:
            int r1 = r7.zzOr
            int r1 = r1 * r0
            int r3 = r7.zzOs
            int r3 = r3 * r2
            if (r1 <= r3) goto L_0x003f
            int r0 = r7.zzOs
            int r0 = r0 * r2
            int r1 = r7.zzOr
            int r0 = r0 / r1
            goto L_0x003f
        L_0x0078:
            if (r4 != r3) goto L_0x0086
            int r1 = r7.zzOs
            int r1 = r1 * r2
            int r3 = r7.zzOr
            int r1 = r1 / r3
            if (r5 != r6) goto L_0x0084
            if (r1 > r0) goto L_0x003f
        L_0x0084:
            r0 = r1
            goto L_0x003f
        L_0x0086:
            if (r5 != r3) goto L_0x0094
            int r1 = r7.zzOr
            int r1 = r1 * r0
            int r3 = r7.zzOs
            int r1 = r1 / r3
            if (r4 != r6) goto L_0x0092
            if (r1 > r2) goto L_0x003f
        L_0x0092:
            r2 = r1
            goto L_0x003f
        L_0x0094:
            int r1 = r7.zzOr
            int r3 = r7.zzOs
            if (r5 != r6) goto L_0x00ad
            if (r3 <= r0) goto L_0x00ad
            int r1 = r7.zzOr
            int r1 = r1 * r0
            int r3 = r7.zzOs
            int r1 = r1 / r3
        L_0x00a2:
            if (r4 != r6) goto L_0x0092
            if (r1 <= r2) goto L_0x0092
            int r0 = r7.zzOs
            int r0 = r0 * r2
            int r1 = r7.zzOr
            int r0 = r0 / r1
            goto L_0x003f
        L_0x00ad:
            r0 = r3
            goto L_0x00a2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.overlay.zzd.onMeasure(int, int):void");
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        zzafr.v("AdMediaPlayerView prepared");
        zzq(2);
        this.zzOl.zzfT();
        zzagz.zzZr.post(new zze(this));
        this.zzOr = mediaPlayer.getVideoWidth();
        this.zzOs = mediaPlayer.getVideoHeight();
        if (this.zzOy != 0) {
            seekTo(this.zzOy);
        }
        zzfF();
        int i = this.zzOr;
        zzafr.zzaS(new StringBuilder(62).append("AdMediaPlayerView stream dimensions: ").append(i).append(" x ").append(this.zzOs).toString());
        if (this.zzOo == 3) {
            play();
        }
        zzfH();
    }

    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        zzafr.v("AdMediaPlayerView surface created");
        zzfE();
        zzagz.zzZr.post(new zzh(this));
    }

    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        zzafr.v("AdMediaPlayerView surface destroyed");
        if (this.zzOp != null && this.zzOy == 0) {
            this.zzOy = this.zzOp.getCurrentPosition();
        }
        if (this.zzOw != null) {
            this.zzOw.zzgf();
        }
        zzagz.zzZr.post(new zzj(this));
        zzq(true);
        return true;
    }

    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        boolean z = true;
        zzafr.v("AdMediaPlayerView surface changed");
        boolean z2 = this.zzOo == 3;
        if (!(this.zzOr == i && this.zzOs == i2)) {
            z = false;
        }
        if (this.zzOp != null && z2 && z) {
            if (this.zzOy != 0) {
                seekTo(this.zzOy);
            }
            play();
        }
        if (this.zzOw != null) {
            this.zzOw.zzf(i, i2);
        }
        zzagz.zzZr.post(new zzi(this, i, i2));
    }

    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.zzOl.zzb(this);
        this.zzPp.zza(surfaceTexture, this.zzOz);
    }

    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        zzafr.v(new StringBuilder(57).append("AdMediaPlayerView size changed: ").append(i).append(" x ").append(i2).toString());
        this.zzOr = mediaPlayer.getVideoWidth();
        this.zzOs = mediaPlayer.getVideoHeight();
        if (this.zzOr != 0 && this.zzOs != 0) {
            requestLayout();
        }
    }

    public final void pause() {
        zzafr.v("AdMediaPlayerView pause");
        if (zzfG() && this.zzOp.isPlaying()) {
            this.zzOp.pause();
            zzq(4);
            zzagz.zzZr.post(new zzl(this));
        }
        this.zzOo = 4;
    }

    public final void play() {
        zzafr.v("AdMediaPlayerView play");
        if (zzfG()) {
            this.zzOp.start();
            zzq(3);
            this.zzPp.zzfU();
            zzagz.zzZr.post(new zzk(this));
        }
        this.zzOo = 3;
    }

    public final void seekTo(int i) {
        zzafr.v(new StringBuilder(34).append("AdMediaPlayerView seek ").append(i).toString());
        if (zzfG()) {
            this.zzOp.seekTo(i);
            this.zzOy = 0;
            return;
        }
        this.zzOy = i;
    }

    public final void setVideoPath(String str) {
        Uri parse = Uri.parse(str);
        zzia zze = zzia.zze(parse);
        if (zze != null) {
            parse = Uri.parse(zze.url);
        }
        this.zzOq = parse;
        this.zzOy = 0;
        zzfE();
        requestLayout();
        invalidate();
    }

    public final void stop() {
        zzafr.v("AdMediaPlayerView stop");
        if (this.zzOp != null) {
            this.zzOp.stop();
            this.zzOp.release();
            this.zzOp = null;
            zzq(0);
            this.zzOo = 0;
        }
        this.zzOl.onStop();
    }

    public final String toString() {
        String valueOf = String.valueOf(getClass().getName());
        String valueOf2 = String.valueOf(Integer.toHexString(hashCode()));
        return new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length()).append(valueOf).append("@").append(valueOf2).toString();
    }

    public final void zza(float f, float f2) {
        if (this.zzOw != null) {
            this.zzOw.zzb(f, f2);
        }
    }

    public final void zza(zzx zzx) {
        this.zzOz = zzx;
    }

    public final String zzfD() {
        String valueOf = String.valueOf(this.zzOx ? " spherical" : "");
        return valueOf.length() != 0 ? "MediaPlayer".concat(valueOf) : new String("MediaPlayer");
    }

    public final void zzfH() {
        zza(this.zzPq.zzgm());
    }
}
