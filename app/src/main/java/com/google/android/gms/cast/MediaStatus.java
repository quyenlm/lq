package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.internal.zzaye;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MediaStatus extends zza {
    public static final long COMMAND_PAUSE = 1;
    public static final long COMMAND_SEEK = 2;
    public static final long COMMAND_SET_VOLUME = 4;
    public static final long COMMAND_SKIP_BACKWARD = 32;
    public static final long COMMAND_SKIP_FORWARD = 16;
    public static final long COMMAND_TOGGLE_MUTE = 8;
    public static final Parcelable.Creator<MediaStatus> CREATOR = new zzag();
    public static final int IDLE_REASON_CANCELED = 2;
    public static final int IDLE_REASON_ERROR = 4;
    public static final int IDLE_REASON_FINISHED = 1;
    public static final int IDLE_REASON_INTERRUPTED = 3;
    public static final int IDLE_REASON_NONE = 0;
    public static final int PLAYER_STATE_BUFFERING = 4;
    public static final int PLAYER_STATE_IDLE = 1;
    public static final int PLAYER_STATE_PAUSED = 3;
    public static final int PLAYER_STATE_PLAYING = 2;
    public static final int PLAYER_STATE_UNKNOWN = 0;
    public static final int REPEAT_MODE_REPEAT_ALL = 1;
    public static final int REPEAT_MODE_REPEAT_ALL_AND_SHUFFLE = 3;
    public static final int REPEAT_MODE_REPEAT_OFF = 0;
    public static final int REPEAT_MODE_REPEAT_SINGLE = 2;
    private String zzaoC;
    private JSONObject zzaoD;
    private int zzaqA;
    private long zzaqB;
    private long zzaqC;
    private double zzaqD;
    private boolean zzaqE;
    private int zzaqF;
    private int zzaqG;
    private int zzaqH;
    private ArrayList<MediaQueueItem> zzaqI;
    private boolean zzaqJ;
    private AdBreakStatus zzaqK;
    private VideoInfo zzaqL;
    private final SparseArray<Integer> zzaqM;
    private MediaInfo zzaqg;
    private long[] zzaqu;
    private long zzaqw;
    private int zzaqx;
    private double zzaqy;
    private int zzaqz;

    MediaStatus(MediaInfo mediaInfo, long j, int i, double d, int i2, int i3, long j2, long j3, double d2, boolean z, long[] jArr, int i4, int i5, String str, int i6, List<MediaQueueItem> list, boolean z2, AdBreakStatus adBreakStatus, VideoInfo videoInfo) {
        this.zzaqI = new ArrayList<>();
        this.zzaqM = new SparseArray<>();
        this.zzaqg = mediaInfo;
        this.zzaqw = j;
        this.zzaqx = i;
        this.zzaqy = d;
        this.zzaqz = i2;
        this.zzaqA = i3;
        this.zzaqB = j2;
        this.zzaqC = j3;
        this.zzaqD = d2;
        this.zzaqE = z;
        this.zzaqu = jArr;
        this.zzaqF = i4;
        this.zzaqG = i5;
        this.zzaoC = str;
        if (this.zzaoC != null) {
            try {
                this.zzaoD = new JSONObject(this.zzaoC);
            } catch (JSONException e) {
                this.zzaoD = null;
                this.zzaoC = null;
            }
        } else {
            this.zzaoD = null;
        }
        this.zzaqH = i6;
        if (list != null && !list.isEmpty()) {
            zza((MediaQueueItem[]) list.toArray(new MediaQueueItem[list.size()]));
        }
        this.zzaqJ = z2;
        this.zzaqK = adBreakStatus;
        this.zzaqL = videoInfo;
    }

    public MediaStatus(JSONObject jSONObject) throws JSONException {
        this((MediaInfo) null, 0, 0, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 0, 0, 0, 0, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, false, (long[]) null, 0, 0, (String) null, 0, (List<MediaQueueItem>) null, false, (AdBreakStatus) null, (VideoInfo) null);
        zza(jSONObject, 0);
    }

    private final void zza(MediaQueueItem[] mediaQueueItemArr) {
        this.zzaqI.clear();
        this.zzaqM.clear();
        for (int i = 0; i < mediaQueueItemArr.length; i++) {
            MediaQueueItem mediaQueueItem = mediaQueueItemArr[i];
            this.zzaqI.add(mediaQueueItem);
            this.zzaqM.put(mediaQueueItem.getItemId(), Integer.valueOf(i));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaStatus)) {
            return false;
        }
        MediaStatus mediaStatus = (MediaStatus) obj;
        if ((this.zzaoD == null) != (mediaStatus.zzaoD == null) || this.zzaqw != mediaStatus.zzaqw || this.zzaqx != mediaStatus.zzaqx || this.zzaqy != mediaStatus.zzaqy || this.zzaqz != mediaStatus.zzaqz || this.zzaqA != mediaStatus.zzaqA || this.zzaqB != mediaStatus.zzaqB || this.zzaqD != mediaStatus.zzaqD || this.zzaqE != mediaStatus.zzaqE || this.zzaqF != mediaStatus.zzaqF || this.zzaqG != mediaStatus.zzaqG || this.zzaqH != mediaStatus.zzaqH || !Arrays.equals(this.zzaqu, mediaStatus.zzaqu) || !zzaye.zza(Long.valueOf(this.zzaqC), Long.valueOf(mediaStatus.zzaqC)) || !zzaye.zza(this.zzaqI, mediaStatus.zzaqI) || !zzaye.zza(this.zzaqg, mediaStatus.zzaqg)) {
            return false;
        }
        return (this.zzaoD == null || mediaStatus.zzaoD == null || zzo.zzc(this.zzaoD, mediaStatus.zzaoD)) && this.zzaqJ == mediaStatus.isPlayingAd();
    }

    public long[] getActiveTrackIds() {
        return this.zzaqu;
    }

    public AdBreakStatus getAdBreakStatus() {
        return this.zzaqK;
    }

    public AdBreakInfo getCurrentAdBreak() {
        if (this.zzaqK == null || this.zzaqg == null) {
            return null;
        }
        String breakId = this.zzaqK.getBreakId();
        if (TextUtils.isEmpty(breakId)) {
            return null;
        }
        List<AdBreakInfo> adBreaks = this.zzaqg.getAdBreaks();
        if (adBreaks == null || adBreaks.isEmpty()) {
            return null;
        }
        for (AdBreakInfo next : adBreaks) {
            if (breakId.equals(next.getId())) {
                return next;
            }
        }
        return null;
    }

    public AdBreakClipInfo getCurrentAdBreakClip() {
        if (this.zzaqK == null || this.zzaqg == null) {
            return null;
        }
        String breakClipId = this.zzaqK.getBreakClipId();
        if (TextUtils.isEmpty(breakClipId)) {
            return null;
        }
        List<AdBreakClipInfo> adBreakClips = this.zzaqg.getAdBreakClips();
        if (adBreakClips == null || adBreakClips.isEmpty()) {
            return null;
        }
        for (AdBreakClipInfo next : adBreakClips) {
            if (breakClipId.equals(next.getId())) {
                return next;
            }
        }
        return null;
    }

    public int getCurrentItemId() {
        return this.zzaqx;
    }

    public JSONObject getCustomData() {
        return this.zzaoD;
    }

    public int getIdleReason() {
        return this.zzaqA;
    }

    public Integer getIndexById(int i) {
        return this.zzaqM.get(i);
    }

    public MediaQueueItem getItemById(int i) {
        Integer num = this.zzaqM.get(i);
        if (num == null) {
            return null;
        }
        return this.zzaqI.get(num.intValue());
    }

    public MediaQueueItem getItemByIndex(int i) {
        if (i < 0 || i >= this.zzaqI.size()) {
            return null;
        }
        return this.zzaqI.get(i);
    }

    public int getLoadingItemId() {
        return this.zzaqF;
    }

    public MediaInfo getMediaInfo() {
        return this.zzaqg;
    }

    public double getPlaybackRate() {
        return this.zzaqy;
    }

    public int getPlayerState() {
        return this.zzaqz;
    }

    public int getPreloadedItemId() {
        return this.zzaqG;
    }

    public MediaQueueItem getQueueItem(int i) {
        return getItemByIndex(i);
    }

    public MediaQueueItem getQueueItemById(int i) {
        return getItemById(i);
    }

    public int getQueueItemCount() {
        return this.zzaqI.size();
    }

    public List<MediaQueueItem> getQueueItems() {
        return this.zzaqI;
    }

    public int getQueueRepeatMode() {
        return this.zzaqH;
    }

    public long getStreamPosition() {
        return this.zzaqB;
    }

    public double getStreamVolume() {
        return this.zzaqD;
    }

    public VideoInfo getVideoInfo() {
        return this.zzaqL;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaqg, Long.valueOf(this.zzaqw), Integer.valueOf(this.zzaqx), Double.valueOf(this.zzaqy), Integer.valueOf(this.zzaqz), Integer.valueOf(this.zzaqA), Long.valueOf(this.zzaqB), Long.valueOf(this.zzaqC), Double.valueOf(this.zzaqD), Boolean.valueOf(this.zzaqE), Integer.valueOf(Arrays.hashCode(this.zzaqu)), Integer.valueOf(this.zzaqF), Integer.valueOf(this.zzaqG), String.valueOf(this.zzaoD), Integer.valueOf(this.zzaqH), this.zzaqI, Boolean.valueOf(this.zzaqJ)});
    }

    public boolean isMediaCommandSupported(long j) {
        return (this.zzaqC & j) != 0;
    }

    public boolean isMute() {
        return this.zzaqE;
    }

    public boolean isPlayingAd() {
        return this.zzaqJ;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.zzaoC = this.zzaoD == null ? null : this.zzaoD.toString();
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) getMediaInfo(), i, false);
        zzd.zza(parcel, 3, this.zzaqw);
        zzd.zzc(parcel, 4, getCurrentItemId());
        zzd.zza(parcel, 5, getPlaybackRate());
        zzd.zzc(parcel, 6, getPlayerState());
        zzd.zzc(parcel, 7, getIdleReason());
        zzd.zza(parcel, 8, getStreamPosition());
        zzd.zza(parcel, 9, this.zzaqC);
        zzd.zza(parcel, 10, getStreamVolume());
        zzd.zza(parcel, 11, isMute());
        zzd.zza(parcel, 12, getActiveTrackIds(), false);
        zzd.zzc(parcel, 13, getLoadingItemId());
        zzd.zzc(parcel, 14, getPreloadedItemId());
        zzd.zza(parcel, 15, this.zzaoC, false);
        zzd.zzc(parcel, 16, this.zzaqH);
        zzd.zzc(parcel, 17, this.zzaqI, false);
        zzd.zza(parcel, 18, isPlayingAd());
        zzd.zza(parcel, 19, (Parcelable) getAdBreakStatus(), i, false);
        zzd.zza(parcel, 20, (Parcelable) getVideoInfo(), i, false);
        zzd.zzI(parcel, zze);
    }

    public final void zzV(boolean z) {
        this.zzaqJ = z;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0200  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0300  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zza(org.json.JSONObject r11, int r12) throws org.json.JSONException {
        /*
            r10 = this;
            r0 = 0
            java.lang.String r1 = "mediaSessionId"
            long r2 = r11.getLong(r1)
            long r4 = r10.zzaqw
            int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0010
            r10.zzaqw = r2
            r0 = 1
        L_0x0010:
            java.lang.String r1 = "playerState"
            boolean r1 = r11.has(r1)
            if (r1 == 0) goto L_0x0053
            r1 = 0
            java.lang.String r2 = "playerState"
            java.lang.String r2 = r11.getString(r2)
            java.lang.String r3 = "IDLE"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x00f8
            r1 = 1
        L_0x0028:
            int r2 = r10.zzaqz
            if (r1 == r2) goto L_0x0390
            r10.zzaqz = r1
            r2 = r0 | 2
        L_0x0030:
            r0 = 1
            if (r1 != r0) goto L_0x038d
            java.lang.String r0 = "idleReason"
            boolean r0 = r11.has(r0)
            if (r0 == 0) goto L_0x038d
            r0 = 0
            java.lang.String r1 = "idleReason"
            java.lang.String r1 = r11.getString(r1)
            java.lang.String r3 = "CANCELLED"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0119
            r0 = 2
        L_0x004b:
            int r1 = r10.zzaqA
            if (r0 == r1) goto L_0x038d
            r10.zzaqA = r0
            r0 = r2 | 2
        L_0x0053:
            java.lang.String r1 = "playbackRate"
            boolean r1 = r11.has(r1)
            if (r1 == 0) goto L_0x006b
            java.lang.String r1 = "playbackRate"
            double r2 = r11.getDouble(r1)
            double r4 = r10.zzaqy
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r1 == 0) goto L_0x006b
            r10.zzaqy = r2
            r0 = r0 | 2
        L_0x006b:
            java.lang.String r1 = "currentTime"
            boolean r1 = r11.has(r1)
            if (r1 == 0) goto L_0x008e
            r1 = r12 & 2
            if (r1 != 0) goto L_0x008e
            java.lang.String r1 = "currentTime"
            double r2 = r11.getDouble(r1)
            r4 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r2 = r2 * r4
            long r2 = (long) r2
            long r4 = r10.zzaqB
            int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x008e
            r10.zzaqB = r2
            r0 = r0 | 2
        L_0x008e:
            java.lang.String r1 = "supportedMediaCommands"
            boolean r1 = r11.has(r1)
            if (r1 == 0) goto L_0x00a6
            java.lang.String r1 = "supportedMediaCommands"
            long r2 = r11.getLong(r1)
            long r4 = r10.zzaqC
            int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x00a6
            r10.zzaqC = r2
            r0 = r0 | 2
        L_0x00a6:
            java.lang.String r1 = "volume"
            boolean r1 = r11.has(r1)
            if (r1 == 0) goto L_0x00d6
            r1 = r12 & 1
            if (r1 != 0) goto L_0x00d6
            java.lang.String r1 = "volume"
            org.json.JSONObject r1 = r11.getJSONObject(r1)
            java.lang.String r2 = "level"
            double r2 = r1.getDouble(r2)
            double r4 = r10.zzaqD
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x00c8
            r10.zzaqD = r2
            r0 = r0 | 2
        L_0x00c8:
            java.lang.String r2 = "muted"
            boolean r1 = r1.getBoolean(r2)
            boolean r2 = r10.zzaqE
            if (r1 == r2) goto L_0x00d6
            r10.zzaqE = r1
            r0 = r0 | 2
        L_0x00d6:
            r2 = 0
            r1 = 0
            java.lang.String r3 = "activeTrackIds"
            boolean r3 = r11.has(r3)
            if (r3 == 0) goto L_0x0241
            java.lang.String r1 = "activeTrackIds"
            org.json.JSONArray r4 = r11.getJSONArray(r1)
            int r5 = r4.length()
            long[] r1 = new long[r5]
            r3 = 0
        L_0x00ed:
            if (r3 >= r5) goto L_0x013a
            long r6 = r4.getLong(r3)
            r1[r3] = r6
            int r3 = r3 + 1
            goto L_0x00ed
        L_0x00f8:
            java.lang.String r3 = "PLAYING"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x0103
            r1 = 2
            goto L_0x0028
        L_0x0103:
            java.lang.String r3 = "PAUSED"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x010e
            r1 = 3
            goto L_0x0028
        L_0x010e:
            java.lang.String r3 = "BUFFERING"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0028
            r1 = 4
            goto L_0x0028
        L_0x0119:
            java.lang.String r3 = "INTERRUPTED"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0124
            r0 = 3
            goto L_0x004b
        L_0x0124:
            java.lang.String r3 = "FINISHED"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x012f
            r0 = 1
            goto L_0x004b
        L_0x012f:
            java.lang.String r3 = "ERROR"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x004b
            r0 = 4
            goto L_0x004b
        L_0x013a:
            long[] r3 = r10.zzaqu
            if (r3 != 0) goto L_0x0226
            r2 = 1
        L_0x013f:
            if (r2 == 0) goto L_0x0143
            r10.zzaqu = r1
        L_0x0143:
            if (r2 == 0) goto L_0x0149
            r10.zzaqu = r1
            r0 = r0 | 2
        L_0x0149:
            java.lang.String r1 = "customData"
            boolean r1 = r11.has(r1)
            if (r1 == 0) goto L_0x015e
            java.lang.String r1 = "customData"
            org.json.JSONObject r1 = r11.getJSONObject(r1)
            r10.zzaoD = r1
            r1 = 0
            r10.zzaoC = r1
            r0 = r0 | 2
        L_0x015e:
            java.lang.String r1 = "media"
            boolean r1 = r11.has(r1)
            if (r1 == 0) goto L_0x018f
            java.lang.String r1 = "media"
            org.json.JSONObject r1 = r11.getJSONObject(r1)
            com.google.android.gms.cast.MediaInfo r2 = new com.google.android.gms.cast.MediaInfo
            r2.<init>((org.json.JSONObject) r1)
            com.google.android.gms.cast.MediaInfo r3 = r10.zzaqg
            if (r3 == 0) goto L_0x0181
            com.google.android.gms.cast.MediaInfo r3 = r10.zzaqg
            if (r3 == 0) goto L_0x0185
            com.google.android.gms.cast.MediaInfo r3 = r10.zzaqg
            boolean r3 = r3.equals(r2)
            if (r3 != 0) goto L_0x0185
        L_0x0181:
            r10.zzaqg = r2
            r0 = r0 | 2
        L_0x0185:
            java.lang.String r2 = "metadata"
            boolean r1 = r1.has(r2)
            if (r1 == 0) goto L_0x018f
            r0 = r0 | 4
        L_0x018f:
            java.lang.String r1 = "currentItemId"
            boolean r1 = r11.has(r1)
            if (r1 == 0) goto L_0x01a5
            java.lang.String r1 = "currentItemId"
            int r1 = r11.getInt(r1)
            int r2 = r10.zzaqx
            if (r2 == r1) goto L_0x01a5
            r10.zzaqx = r1
            r0 = r0 | 2
        L_0x01a5:
            java.lang.String r1 = "preloadedItemId"
            r2 = 0
            int r1 = r11.optInt(r1, r2)
            int r2 = r10.zzaqG
            if (r2 == r1) goto L_0x01b4
            r10.zzaqG = r1
            r0 = r0 | 16
        L_0x01b4:
            java.lang.String r1 = "loadingItemId"
            r2 = 0
            int r1 = r11.optInt(r1, r2)
            int r2 = r10.zzaqF
            if (r2 == r1) goto L_0x038a
            r10.zzaqF = r1
            r0 = r0 | 2
            r1 = r0
        L_0x01c4:
            com.google.android.gms.cast.MediaInfo r0 = r10.zzaqg
            if (r0 != 0) goto L_0x0248
            r0 = -1
        L_0x01c9:
            int r2 = r10.zzaqz
            int r3 = r10.zzaqA
            int r4 = r10.zzaqF
            r5 = 1
            if (r2 == r5) goto L_0x0250
            r0 = 0
        L_0x01d3:
            if (r0 != 0) goto L_0x0361
            r2 = 0
            java.lang.String r0 = "repeatMode"
            boolean r0 = r11.has(r0)
            if (r0 == 0) goto L_0x0387
            int r0 = r10.zzaqH
            java.lang.String r3 = "repeatMode"
            java.lang.String r4 = r11.getString(r3)
            r3 = -1
            int r5 = r4.hashCode()
            switch(r5) {
                case -1118317585: goto L_0x0281;
                case -962896020: goto L_0x0276;
                case 1645938909: goto L_0x026b;
                case 1645952171: goto L_0x0261;
                default: goto L_0x01ee;
            }
        L_0x01ee:
            switch(r3) {
                case 0: goto L_0x028c;
                case 1: goto L_0x028f;
                case 2: goto L_0x0292;
                case 3: goto L_0x0295;
                default: goto L_0x01f1;
            }
        L_0x01f1:
            int r3 = r10.zzaqH
            if (r3 == r0) goto L_0x0387
            r10.zzaqH = r0
            r0 = 1
        L_0x01f8:
            java.lang.String r2 = "items"
            boolean r2 = r11.has(r2)
            if (r2 == 0) goto L_0x02fe
            java.lang.String r2 = "items"
            org.json.JSONArray r4 = r11.getJSONArray(r2)
            int r5 = r4.length()
            android.util.SparseArray r6 = new android.util.SparseArray
            r6.<init>()
            r2 = 0
        L_0x0210:
            if (r2 >= r5) goto L_0x0298
            org.json.JSONObject r3 = r4.getJSONObject(r2)
            java.lang.String r7 = "itemId"
            int r3 = r3.getInt(r7)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r6.put(r2, r3)
            int r2 = r2 + 1
            goto L_0x0210
        L_0x0226:
            long[] r3 = r10.zzaqu
            int r3 = r3.length
            if (r3 == r5) goto L_0x022e
            r2 = 1
            goto L_0x013f
        L_0x022e:
            r3 = 0
        L_0x022f:
            if (r3 >= r5) goto L_0x013f
            long[] r4 = r10.zzaqu
            r6 = r4[r3]
            r8 = r1[r3]
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 == 0) goto L_0x023e
            r2 = 1
            goto L_0x013f
        L_0x023e:
            int r3 = r3 + 1
            goto L_0x022f
        L_0x0241:
            long[] r3 = r10.zzaqu
            if (r3 == 0) goto L_0x0143
            r2 = 1
            goto L_0x0143
        L_0x0248:
            com.google.android.gms.cast.MediaInfo r0 = r10.zzaqg
            int r0 = r0.getStreamType()
            goto L_0x01c9
        L_0x0250:
            switch(r3) {
                case 1: goto L_0x0256;
                case 2: goto L_0x025b;
                case 3: goto L_0x0256;
                default: goto L_0x0253;
            }
        L_0x0253:
            r0 = 1
            goto L_0x01d3
        L_0x0256:
            if (r4 == 0) goto L_0x0253
            r0 = 0
            goto L_0x01d3
        L_0x025b:
            r2 = 2
            if (r0 != r2) goto L_0x0253
            r0 = 0
            goto L_0x01d3
        L_0x0261:
            java.lang.String r5 = "REPEAT_OFF"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x01ee
            r3 = 0
            goto L_0x01ee
        L_0x026b:
            java.lang.String r5 = "REPEAT_ALL"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x01ee
            r3 = 1
            goto L_0x01ee
        L_0x0276:
            java.lang.String r5 = "REPEAT_SINGLE"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x01ee
            r3 = 2
            goto L_0x01ee
        L_0x0281:
            java.lang.String r5 = "REPEAT_ALL_AND_SHUFFLE"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x01ee
            r3 = 3
            goto L_0x01ee
        L_0x028c:
            r0 = 0
            goto L_0x01f1
        L_0x028f:
            r0 = 1
            goto L_0x01f1
        L_0x0292:
            r0 = 2
            goto L_0x01f1
        L_0x0295:
            r0 = 3
            goto L_0x01f1
        L_0x0298:
            com.google.android.gms.cast.MediaQueueItem[] r7 = new com.google.android.gms.cast.MediaQueueItem[r5]
            r3 = 0
            r2 = r0
        L_0x029c:
            if (r3 >= r5) goto L_0x02f2
            java.lang.Object r0 = r6.get(r3)
            java.lang.Integer r0 = (java.lang.Integer) r0
            org.json.JSONObject r8 = r4.getJSONObject(r3)
            int r9 = r0.intValue()
            com.google.android.gms.cast.MediaQueueItem r9 = r10.getItemById(r9)
            if (r9 == 0) goto L_0x02cc
            boolean r8 = r9.zzm(r8)
            r2 = r2 | r8
            r7[r3] = r9
            int r0 = r0.intValue()
            java.lang.Integer r0 = r10.getIndexById(r0)
            int r0 = r0.intValue()
            if (r3 == r0) goto L_0x02f0
            r0 = 1
        L_0x02c8:
            int r3 = r3 + 1
            r2 = r0
            goto L_0x029c
        L_0x02cc:
            r2 = 1
            int r0 = r0.intValue()
            int r9 = r10.zzaqx
            if (r0 != r9) goto L_0x02e9
            com.google.android.gms.cast.MediaQueueItem$Builder r0 = new com.google.android.gms.cast.MediaQueueItem$Builder
            com.google.android.gms.cast.MediaInfo r9 = r10.zzaqg
            r0.<init>((com.google.android.gms.cast.MediaInfo) r9)
            com.google.android.gms.cast.MediaQueueItem r0 = r0.build()
            r7[r3] = r0
            r0 = r7[r3]
            r0.zzm(r8)
            r0 = r2
            goto L_0x02c8
        L_0x02e9:
            com.google.android.gms.cast.MediaQueueItem r0 = new com.google.android.gms.cast.MediaQueueItem
            r0.<init>((org.json.JSONObject) r8)
            r7[r3] = r0
        L_0x02f0:
            r0 = r2
            goto L_0x02c8
        L_0x02f2:
            java.util.ArrayList<com.google.android.gms.cast.MediaQueueItem> r0 = r10.zzaqI
            int r0 = r0.size()
            if (r0 == r5) goto L_0x0384
            r0 = 1
        L_0x02fb:
            r10.zza(r7)
        L_0x02fe:
            if (r0 == 0) goto L_0x0302
            r1 = r1 | 8
        L_0x0302:
            java.lang.String r0 = "breakStatus"
            org.json.JSONObject r0 = r11.optJSONObject(r0)
            com.google.android.gms.cast.AdBreakStatus r2 = com.google.android.gms.cast.AdBreakStatus.zzj(r0)
            com.google.android.gms.cast.AdBreakStatus r0 = r10.zzaqK
            if (r0 != 0) goto L_0x0312
            if (r2 != 0) goto L_0x031e
        L_0x0312:
            com.google.android.gms.cast.AdBreakStatus r0 = r10.zzaqK
            if (r0 == 0) goto L_0x0327
            com.google.android.gms.cast.AdBreakStatus r0 = r10.zzaqK
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0327
        L_0x031e:
            if (r2 == 0) goto L_0x0382
            r0 = 1
        L_0x0321:
            r10.zzaqJ = r0
            r10.zzaqK = r2
            r1 = r1 | 32
        L_0x0327:
            java.lang.String r0 = "videoInfo"
            org.json.JSONObject r0 = r11.optJSONObject(r0)
            com.google.android.gms.cast.VideoInfo r0 = com.google.android.gms.cast.VideoInfo.zzn(r0)
            com.google.android.gms.cast.VideoInfo r2 = r10.zzaqL
            if (r2 != 0) goto L_0x0337
            if (r0 != 0) goto L_0x0343
        L_0x0337:
            com.google.android.gms.cast.VideoInfo r2 = r10.zzaqL
            if (r2 == 0) goto L_0x0347
            com.google.android.gms.cast.VideoInfo r2 = r10.zzaqL
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L_0x0347
        L_0x0343:
            r10.zzaqL = r0
            r1 = r1 | 64
        L_0x0347:
            java.lang.String r0 = "breakInfo"
            boolean r0 = r11.has(r0)
            if (r0 == 0) goto L_0x0360
            com.google.android.gms.cast.MediaInfo r0 = r10.zzaqg
            if (r0 == 0) goto L_0x0360
            com.google.android.gms.cast.MediaInfo r0 = r10.zzaqg
            java.lang.String r2 = "breakInfo"
            org.json.JSONObject r2 = r11.getJSONObject(r2)
            r0.zzk(r2)
            r1 = r1 | 2
        L_0x0360:
            return r1
        L_0x0361:
            r0 = 0
            r10.zzaqx = r0
            r0 = 0
            r10.zzaqF = r0
            r0 = 0
            r10.zzaqG = r0
            java.util.ArrayList<com.google.android.gms.cast.MediaQueueItem> r0 = r10.zzaqI
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0302
            r0 = 0
            r10.zzaqH = r0
            java.util.ArrayList<com.google.android.gms.cast.MediaQueueItem> r0 = r10.zzaqI
            r0.clear()
            android.util.SparseArray<java.lang.Integer> r0 = r10.zzaqM
            r0.clear()
            r1 = r1 | 8
            goto L_0x0302
        L_0x0382:
            r0 = 0
            goto L_0x0321
        L_0x0384:
            r0 = r2
            goto L_0x02fb
        L_0x0387:
            r0 = r2
            goto L_0x01f8
        L_0x038a:
            r1 = r0
            goto L_0x01c4
        L_0x038d:
            r0 = r2
            goto L_0x0053
        L_0x0390:
            r2 = r0
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaStatus.zza(org.json.JSONObject, int):int");
    }

    public final long zznk() {
        return this.zzaqw;
    }
}
