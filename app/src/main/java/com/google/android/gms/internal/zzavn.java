package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import com.google.android.gms.R;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.ReconnectionService;
import com.google.android.gms.cast.framework.media.MediaNotificationService;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.util.zzq;

public final class zzavn implements RemoteMediaClient.Listener {
    private boolean mIsAttached;
    private CastDevice zzaoX;
    private final Context zzarM;
    private final zzavb zzasB;
    /* access modifiers changed from: private */
    public RemoteMediaClient zzase;
    private final CastOptions zzauY;
    private final ComponentName zzauZ;
    private final zzavc zzava;
    private final zzavc zzavb;
    private MediaSessionCompat zzavc;
    private MediaSessionCompat.Callback zzavd;

    public zzavn(Context context, CastOptions castOptions, zzavb zzavb2) {
        this.zzarM = context;
        this.zzauY = castOptions;
        this.zzasB = zzavb2;
        if (this.zzauY.getCastMediaOptions() == null || TextUtils.isEmpty(this.zzauY.getCastMediaOptions().getExpandedControllerActivityClassName())) {
            this.zzauZ = null;
        } else {
            this.zzauZ = new ComponentName(this.zzarM, this.zzauY.getCastMediaOptions().getExpandedControllerActivityClassName());
        }
        this.zzava = new zzavc(this.zzarM);
        this.zzava.zza(new zzavo(this));
        this.zzavb = new zzavc(this.zzarM);
        this.zzavb.zza(new zzavp(this));
    }

    private final Uri zza(MediaMetadata mediaMetadata, int i) {
        WebImage onPickImage = this.zzauY.getCastMediaOptions().getImagePicker() != null ? this.zzauY.getCastMediaOptions().getImagePicker().onPickImage(mediaMetadata, i) : mediaMetadata.hasImages() ? mediaMetadata.getImages().get(0) : null;
        if (onPickImage == null) {
            return null;
        }
        return onPickImage.getUrl();
    }

    private final void zza(int i, MediaInfo mediaInfo) {
        PendingIntent activity;
        if (i == 0) {
            this.zzavc.setPlaybackState(new PlaybackStateCompat.Builder().setState(0, 0, 1.0f).build());
            this.zzavc.setMetadata(new MediaMetadataCompat.Builder().build());
            return;
        }
        this.zzavc.setPlaybackState(new PlaybackStateCompat.Builder().setState(i, 0, 1.0f).setActions(512).build());
        MediaSessionCompat mediaSessionCompat = this.zzavc;
        if (this.zzauZ == null) {
            activity = null;
        } else {
            Intent intent = new Intent();
            intent.setComponent(this.zzauZ);
            activity = PendingIntent.getActivity(this.zzarM, 0, intent, 134217728);
        }
        mediaSessionCompat.setSessionActivity(activity);
        MediaMetadata metadata = mediaInfo.getMetadata();
        this.zzavc.setMetadata(zzof().putString("android.media.metadata.TITLE", metadata.getString(MediaMetadata.KEY_TITLE)).putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, metadata.getString(MediaMetadata.KEY_TITLE)).putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, metadata.getString(MediaMetadata.KEY_SUBTITLE)).putLong("android.media.metadata.DURATION", mediaInfo.getStreamDuration()).build());
        Uri zza = zza(metadata, 0);
        if (zza != null) {
            this.zzava.zzm(zza);
        } else {
            zza((Bitmap) null, 0);
        }
        Uri zza2 = zza(metadata, 3);
        if (zza2 != null) {
            this.zzavb.zzm(zza2);
        } else {
            zza((Bitmap) null, 3);
        }
    }

    /* access modifiers changed from: private */
    public final void zza(Bitmap bitmap, int i) {
        if (i == 0) {
            if (bitmap != null) {
                this.zzavc.setMetadata(zzof().putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, bitmap).build());
                return;
            }
            Bitmap createBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            createBitmap.eraseColor(0);
            this.zzavc.setMetadata(zzof().putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, createBitmap).build());
        } else if (i == 3) {
            this.zzavc.setMetadata(zzof().putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bitmap).build());
        }
    }

    private final void zzoe() {
        boolean z;
        boolean z2;
        boolean z3;
        int i = 2;
        MediaStatus mediaStatus = this.zzase.getMediaStatus();
        MediaInfo mediaInfo = mediaStatus == null ? null : mediaStatus.getMediaInfo();
        MediaMetadata metadata = mediaInfo == null ? null : mediaInfo.getMetadata();
        if (mediaStatus != null && mediaInfo != null && metadata != null) {
            switch (this.zzase.getPlayerState()) {
                case 1:
                    int idleReason = mediaStatus.getIdleReason();
                    boolean z4 = this.zzase.isLiveStream() && idleReason == 2;
                    int loadingItemId = mediaStatus.getLoadingItemId();
                    boolean z5 = loadingItemId != 0 && (idleReason == 1 || idleReason == 3);
                    if (!z4) {
                        MediaQueueItem queueItemById = mediaStatus.getQueueItemById(loadingItemId);
                        if (queueItemById == null) {
                            z = z5;
                            i = 0;
                            break;
                        } else {
                            mediaInfo = queueItemById.getMedia();
                            i = 6;
                            z = z5;
                            break;
                        }
                    } else {
                        z = z5;
                        break;
                    }
                case 2:
                    z = false;
                    i = 3;
                    break;
                case 3:
                    z = false;
                    break;
                case 4:
                    z = false;
                    i = 6;
                    break;
                default:
                    z = false;
                    i = 0;
                    break;
            }
        } else {
            z = false;
            i = 0;
        }
        zza(i, mediaInfo);
        if (i == 0) {
            zzog();
            zzoh();
            return;
        }
        if (!(this.zzauY.getCastMediaOptions().getNotificationOptions() == null || this.zzase == null)) {
            Intent intent = new Intent(this.zzarM, MediaNotificationService.class);
            intent.setPackage(this.zzarM.getPackageName());
            intent.setAction(MediaNotificationService.ACTION_UPDATE_NOTIFICATION);
            intent.putExtra("extra_media_info", this.zzase.getMediaInfo());
            intent.putExtra("extra_remote_media_client_player_state", this.zzase.getPlayerState());
            intent.putExtra("extra_cast_device", this.zzaoX);
            intent.putExtra("extra_media_session_token", this.zzavc == null ? null : this.zzavc.getSessionToken());
            MediaStatus mediaStatus2 = this.zzase.getMediaStatus();
            if (mediaStatus2 != null) {
                switch (mediaStatus2.getQueueRepeatMode()) {
                    case 1:
                    case 2:
                    case 3:
                        z2 = true;
                        z3 = true;
                        break;
                    default:
                        Integer indexById = mediaStatus2.getIndexById(mediaStatus2.getCurrentItemId());
                        if (indexById == null) {
                            z2 = false;
                            z3 = false;
                            break;
                        } else {
                            z2 = indexById.intValue() > 0;
                            if (indexById.intValue() >= mediaStatus2.getQueueItemCount() - 1) {
                                z3 = false;
                                break;
                            } else {
                                z3 = true;
                                break;
                            }
                        }
                }
                intent.putExtra("extra_can_skip_next", z3);
                intent.putExtra("extra_can_skip_prev", z2);
            }
            this.zzarM.startService(intent);
        }
        if (!z && this.zzauY.getEnableReconnectionService()) {
            Intent intent2 = new Intent(this.zzarM, ReconnectionService.class);
            intent2.setPackage(this.zzarM.getPackageName());
            this.zzarM.startService(intent2);
        }
    }

    private final MediaMetadataCompat.Builder zzof() {
        MediaMetadataCompat metadata = this.zzavc.getController().getMetadata();
        return metadata == null ? new MediaMetadataCompat.Builder() : new MediaMetadataCompat.Builder(metadata);
    }

    private final void zzog() {
        if (this.zzauY.getCastMediaOptions().getNotificationOptions() != null) {
            Intent intent = new Intent(this.zzarM, MediaNotificationService.class);
            intent.setPackage(this.zzarM.getPackageName());
            intent.setAction(MediaNotificationService.ACTION_UPDATE_NOTIFICATION);
            this.zzarM.stopService(intent);
        }
    }

    private final void zzoh() {
        if (this.zzauY.getEnableReconnectionService()) {
            Intent intent = new Intent(this.zzarM, ReconnectionService.class);
            intent.setPackage(this.zzarM.getPackageName());
            this.zzarM.stopService(intent);
        }
    }

    public final void onAdBreakStatusUpdated() {
        zzoe();
    }

    public final void onMetadataUpdated() {
        zzoe();
    }

    public final void onPreloadStatusUpdated() {
        zzoe();
    }

    public final void onQueueStatusUpdated() {
        zzoe();
    }

    public final void onSendingRemoteMediaRequest() {
    }

    public final void onStatusUpdated() {
        zzoe();
    }

    public final void zza(RemoteMediaClient remoteMediaClient, CastDevice castDevice) {
        if (!this.mIsAttached && this.zzauY != null && this.zzauY.getCastMediaOptions() != null && remoteMediaClient != null && castDevice != null) {
            this.zzase = remoteMediaClient;
            this.zzase.addListener(this);
            this.zzaoX = castDevice;
            if (!zzq.zzse()) {
                ((AudioManager) this.zzarM.getSystemService("audio")).requestAudioFocus((AudioManager.OnAudioFocusChangeListener) null, 3, 3);
            }
            ComponentName componentName = new ComponentName(this.zzarM, this.zzauY.getCastMediaOptions().getMediaIntentReceiverClassName());
            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
            intent.setComponent(componentName);
            this.zzavc = new MediaSessionCompat(this.zzarM, "CastMediaSession", componentName, PendingIntent.getBroadcast(this.zzarM, 0, intent, 0));
            this.zzavc.setFlags(3);
            zza(0, (MediaInfo) null);
            if (this.zzaoX != null && !TextUtils.isEmpty(this.zzaoX.getFriendlyName())) {
                this.zzavc.setMetadata(new MediaMetadataCompat.Builder().putString("android.media.metadata.ALBUM_ARTIST", this.zzarM.getResources().getString(R.string.cast_casting_to_device, new Object[]{this.zzaoX.getFriendlyName()})).build());
            }
            this.zzavd = new zzavq(this);
            this.zzavc.setCallback(this.zzavd);
            this.zzavc.setActive(true);
            this.zzasB.setMediaSessionCompat(this.zzavc);
            this.mIsAttached = true;
            zzoe();
        }
    }

    public final void zzab(int i) {
        if (this.mIsAttached) {
            this.mIsAttached = false;
            if (this.zzase != null) {
                this.zzase.removeListener(this);
            }
            if (!zzq.zzse()) {
                ((AudioManager) this.zzarM.getSystemService("audio")).abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
            }
            this.zzasB.setMediaSessionCompat((MediaSessionCompat) null);
            if (this.zzava != null) {
                this.zzava.clear();
            }
            if (this.zzavb != null) {
                this.zzavb.clear();
            }
            if (this.zzavc != null) {
                this.zzavc.setSessionActivity((PendingIntent) null);
                this.zzavc.setCallback((MediaSessionCompat.Callback) null);
                this.zzavc.setMetadata(new MediaMetadataCompat.Builder().build());
                zza(0, (MediaInfo) null);
                this.zzavc.setActive(false);
                this.zzavc.release();
                this.zzavc = null;
            }
            this.zzase = null;
            this.zzaoX = null;
            this.zzavd = null;
            zzog();
            if (i == 0) {
                zzoh();
            }
        }
    }
}
