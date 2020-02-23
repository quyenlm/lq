package com.google.android.gms.internal;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.common.util.zzi;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.qqgamemi.util.TimeUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzayp extends zzaxt {
    public static final String NAMESPACE = zzaye.zzcj("com.google.cast.media");
    private final List<zzayu> zzawS = new ArrayList();
    private long zzayA;
    private MediaStatus zzayB;
    private zzayq zzayC;
    private final zzayu zzayD = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayE = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayF = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayG = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayH = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayI = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayJ = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayK = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayL = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayM = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayN = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayO = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayP = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);
    private final zzayu zzayQ = new zzayu(this.zzvw, TimeUtils.MILLIS_IN_DAY);

    public zzayp(String str) {
        super(NAMESPACE, zzi.zzrY(), "MediaControlChannel", (String) null, 1000);
        this.zzawS.add(this.zzayD);
        this.zzawS.add(this.zzayE);
        this.zzawS.add(this.zzayF);
        this.zzawS.add(this.zzayG);
        this.zzawS.add(this.zzayH);
        this.zzawS.add(this.zzayI);
        this.zzawS.add(this.zzayJ);
        this.zzawS.add(this.zzayK);
        this.zzawS.add(this.zzayL);
        this.zzawS.add(this.zzayM);
        this.zzawS.add(this.zzayN);
        this.zzawS.add(this.zzayO);
        this.zzawS.add(this.zzayP);
        this.zzawS.add(this.zzayQ);
        zzoM();
    }

    private final void onMetadataUpdated() {
        if (this.zzayC != null) {
            this.zzayC.onMetadataUpdated();
        }
    }

    private final void onPreloadStatusUpdated() {
        if (this.zzayC != null) {
            this.zzayC.onPreloadStatusUpdated();
        }
    }

    private final void onQueueStatusUpdated() {
        if (this.zzayC != null) {
            this.zzayC.onQueueStatusUpdated();
        }
    }

    private final void onStatusUpdated() {
        if (this.zzayC != null) {
            this.zzayC.onStatusUpdated();
        }
    }

    private final void zza(long j, JSONObject jSONObject) throws JSONException {
        int i;
        boolean z = true;
        boolean test = this.zzayD.test(j);
        boolean z2 = this.zzayH.zzoO() && !this.zzayH.test(j);
        if ((!this.zzayI.zzoO() || this.zzayI.test(j)) && (!this.zzayJ.zzoO() || this.zzayJ.test(j))) {
            z = false;
        }
        int i2 = z2 ? 2 : 0;
        if (z) {
            i2 |= 1;
        }
        if (test || this.zzayB == null) {
            this.zzayB = new MediaStatus(jSONObject);
            this.zzayA = this.zzvw.elapsedRealtime();
            i = 127;
        } else {
            i = this.zzayB.zza(jSONObject, i2);
        }
        if ((i & 1) != 0) {
            this.zzayA = this.zzvw.elapsedRealtime();
            onStatusUpdated();
        }
        if ((i & 2) != 0) {
            this.zzayA = this.zzvw.elapsedRealtime();
            onStatusUpdated();
        }
        if ((i & 4) != 0) {
            onMetadataUpdated();
        }
        if ((i & 8) != 0) {
            onQueueStatusUpdated();
        }
        if ((i & 16) != 0) {
            onPreloadStatusUpdated();
        }
        if ((i & 32) != 0) {
            this.zzayA = this.zzvw.elapsedRealtime();
            if (this.zzayC != null) {
                this.zzayC.onAdBreakStatusUpdated();
            }
        }
        if ((i & 64) != 0) {
            this.zzayA = this.zzvw.elapsedRealtime();
            onStatusUpdated();
        }
        for (zzayu zzc : this.zzawS) {
            zzc.zzc(j, 0, (Object) null);
        }
    }

    private final long zznk() throws zzayr {
        if (this.zzayB != null) {
            return this.zzayB.zznk();
        }
        throw new zzayr();
    }

    private final void zzoM() {
        this.zzayA = 0;
        this.zzayB = null;
        for (zzayu clear : this.zzawS) {
            clear.clear();
        }
    }

    public final long getApproximateStreamPosition() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo == null || this.zzayA == 0) {
            return 0;
        }
        double playbackRate = this.zzayB.getPlaybackRate();
        long streamPosition = this.zzayB.getStreamPosition();
        int playerState = this.zzayB.getPlayerState();
        if (playbackRate == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || playerState != 2) {
            return streamPosition;
        }
        long streamDuration = mediaInfo.getStreamDuration();
        long elapsedRealtime = this.zzvw.elapsedRealtime() - this.zzayA;
        if (elapsedRealtime < 0) {
            elapsedRealtime = 0;
        }
        if (elapsedRealtime == 0) {
            return streamPosition;
        }
        long j = streamPosition + ((long) (((double) elapsedRealtime) * playbackRate));
        if (streamDuration <= 0 || j <= streamDuration) {
            streamDuration = j < 0 ? 0 : j;
        }
        return streamDuration;
    }

    public final MediaInfo getMediaInfo() {
        if (this.zzayB == null) {
            return null;
        }
        return this.zzayB.getMediaInfo();
    }

    public final MediaStatus getMediaStatus() {
        return this.zzayB;
    }

    public final long getStreamDuration() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo != null) {
            return mediaInfo.getStreamDuration();
        }
        return 0;
    }

    public final long zza(zzayt zzayt) throws IOException {
        JSONObject jSONObject = new JSONObject();
        long zzoA = zzoA();
        this.zzayK.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject.put("requestId", zzoA);
            jSONObject.put("type", "GET_STATUS");
            if (this.zzayB != null) {
                jSONObject.put("mediaSessionId", this.zzayB.zznk());
            }
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, double d, JSONObject jSONObject) throws IOException, zzayr, IllegalArgumentException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException(new StringBuilder(41).append("Volume cannot be ").append(d).toString());
        }
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayI.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", zznk());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(FirebaseAnalytics.Param.LEVEL, d);
            jSONObject2.put(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, int i, long j, MediaQueueItem[] mediaQueueItemArr, int i2, Integer num, JSONObject jSONObject) throws IllegalArgumentException, IOException, zzayr {
        if (j == -1 || j >= 0) {
            JSONObject jSONObject2 = new JSONObject();
            long zzoA = zzoA();
            this.zzayO.zza(zzoA, zzayt);
            zzZ(true);
            try {
                jSONObject2.put("requestId", zzoA);
                jSONObject2.put("type", "QUEUE_UPDATE");
                jSONObject2.put("mediaSessionId", zznk());
                if (i != 0) {
                    jSONObject2.put("currentItemId", i);
                }
                if (i2 != 0) {
                    jSONObject2.put("jump", i2);
                }
                if (mediaQueueItemArr != null && mediaQueueItemArr.length > 0) {
                    JSONArray jSONArray = new JSONArray();
                    for (int i3 = 0; i3 < mediaQueueItemArr.length; i3++) {
                        jSONArray.put(i3, mediaQueueItemArr[i3].toJson());
                    }
                    jSONObject2.put("items", jSONArray);
                }
                if (num != null) {
                    switch (num.intValue()) {
                        case 0:
                            jSONObject2.put("repeatMode", "REPEAT_OFF");
                            break;
                        case 1:
                            jSONObject2.put("repeatMode", "REPEAT_ALL");
                            break;
                        case 2:
                            jSONObject2.put("repeatMode", "REPEAT_SINGLE");
                            break;
                        case 3:
                            jSONObject2.put("repeatMode", "REPEAT_ALL_AND_SHUFFLE");
                            break;
                    }
                }
                if (j != -1) {
                    jSONObject2.put("currentTime", ((double) j) / 1000.0d);
                }
                if (jSONObject != null) {
                    jSONObject2.put("customData", jSONObject);
                }
            } catch (JSONException e) {
            }
            zza(jSONObject2.toString(), zzoA, (String) null);
            return zzoA;
        }
        throw new IllegalArgumentException(new StringBuilder(53).append("playPosition cannot be negative: ").append(j).toString());
    }

    public final long zza(zzayt zzayt, long j, int i, JSONObject jSONObject) throws IOException, zzayr {
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayH.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "SEEK");
            jSONObject2.put("mediaSessionId", zznk());
            jSONObject2.put("currentTime", ((double) j) / 1000.0d);
            if (i == 1) {
                jSONObject2.put("resumeState", "PLAYBACK_START");
            } else if (i == 2) {
                jSONObject2.put("resumeState", "PLAYBACK_PAUSE");
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, MediaInfo mediaInfo, boolean z, long j, long[] jArr, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayD.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "LOAD");
            jSONObject2.put("media", mediaInfo.toJson());
            jSONObject2.put("autoplay", z);
            jSONObject2.put("currentTime", ((double) j) / 1000.0d);
            if (jArr != null) {
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < jArr.length; i++) {
                    jSONArray.put(i, jArr[i]);
                }
                jSONObject2.put("activeTrackIds", jSONArray);
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, TextTrackStyle textTrackStyle) throws IOException, zzayr {
        JSONObject jSONObject = new JSONObject();
        long zzoA = zzoA();
        this.zzayM.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject.put("requestId", zzoA);
            jSONObject.put("type", "EDIT_TRACKS_INFO");
            if (textTrackStyle != null) {
                jSONObject.put("textTrackStyle", textTrackStyle.toJson());
            }
            jSONObject.put("mediaSessionId", zznk());
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, JSONObject jSONObject) throws IOException, zzayr {
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayE.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "PAUSE");
            jSONObject2.put("mediaSessionId", zznk());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, boolean z, JSONObject jSONObject) throws IOException, zzayr {
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayJ.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", zznk());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("muted", z);
            jSONObject2.put(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, int[] iArr, int i, JSONObject jSONObject) throws IOException, zzayr, IllegalArgumentException {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("itemIdsToReorder must not be null or empty.");
        }
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayQ.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "QUEUE_REORDER");
            jSONObject2.put("mediaSessionId", zznk());
            JSONArray jSONArray = new JSONArray();
            for (int i2 = 0; i2 < iArr.length; i2++) {
                jSONArray.put(i2, iArr[i2]);
            }
            jSONObject2.put("itemIds", jSONArray);
            if (i != 0) {
                jSONObject2.put("insertBefore", i);
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, int[] iArr, JSONObject jSONObject) throws IOException, zzayr, IllegalArgumentException {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("itemIdsToRemove must not be null or empty.");
        }
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayP.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "QUEUE_REMOVE");
            jSONObject2.put("mediaSessionId", zznk());
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < iArr.length; i++) {
                jSONArray.put(i, iArr[i]);
            }
            jSONObject2.put("itemIds", jSONArray);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, long[] jArr) throws IOException, zzayr {
        JSONObject jSONObject = new JSONObject();
        long zzoA = zzoA();
        this.zzayL.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject.put("requestId", zzoA);
            jSONObject.put("type", "EDIT_TRACKS_INFO");
            jSONObject.put("mediaSessionId", zznk());
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < jArr.length; i++) {
                jSONArray.put(i, jArr[i]);
            }
            jSONObject.put("activeTrackIds", jSONArray);
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zza(zzayt zzayt, MediaQueueItem[] mediaQueueItemArr, int i, int i2, int i3, long j, JSONObject jSONObject) throws IOException, zzayr, IllegalArgumentException {
        if (mediaQueueItemArr == null || mediaQueueItemArr.length == 0) {
            throw new IllegalArgumentException("itemsToInsert must not be null or empty.");
        } else if (i3 != -1 && (i3 < 0 || i3 >= mediaQueueItemArr.length)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "currentItemIndexInItemsToInsert %d out of range [0, %d).", new Object[]{Integer.valueOf(i3), Integer.valueOf(mediaQueueItemArr.length)}));
        } else if (j == -1 || j >= 0) {
            JSONObject jSONObject2 = new JSONObject();
            long zzoA = zzoA();
            this.zzayN.zza(zzoA, zzayt);
            zzZ(true);
            try {
                jSONObject2.put("requestId", zzoA);
                jSONObject2.put("type", "QUEUE_INSERT");
                jSONObject2.put("mediaSessionId", zznk());
                JSONArray jSONArray = new JSONArray();
                for (int i4 = 0; i4 < mediaQueueItemArr.length; i4++) {
                    jSONArray.put(i4, mediaQueueItemArr[i4].toJson());
                }
                jSONObject2.put("items", jSONArray);
                if (i != 0) {
                    jSONObject2.put("insertBefore", i);
                }
                if (i3 != -1) {
                    jSONObject2.put("currentItemIndex", i3);
                }
                if (j != -1) {
                    jSONObject2.put("currentTime", ((double) j) / 1000.0d);
                }
                if (jSONObject != null) {
                    jSONObject2.put("customData", jSONObject);
                }
            } catch (JSONException e) {
            }
            zza(jSONObject2.toString(), zzoA, (String) null);
            return zzoA;
        } else {
            throw new IllegalArgumentException(new StringBuilder(54).append("playPosition can not be negative: ").append(j).toString());
        }
    }

    public final long zza(zzayt zzayt, MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) throws IOException, IllegalArgumentException {
        if (mediaQueueItemArr == null || mediaQueueItemArr.length == 0) {
            throw new IllegalArgumentException("items must not be null or empty.");
        } else if (i < 0 || i >= mediaQueueItemArr.length) {
            throw new IllegalArgumentException(new StringBuilder(31).append("Invalid startIndex: ").append(i).toString());
        } else if (j == -1 || j >= 0) {
            JSONObject jSONObject2 = new JSONObject();
            long zzoA = zzoA();
            this.zzayD.zza(zzoA, zzayt);
            zzZ(true);
            try {
                jSONObject2.put("requestId", zzoA);
                jSONObject2.put("type", "QUEUE_LOAD");
                JSONArray jSONArray = new JSONArray();
                for (int i3 = 0; i3 < mediaQueueItemArr.length; i3++) {
                    jSONArray.put(i3, mediaQueueItemArr[i3].toJson());
                }
                jSONObject2.put("items", jSONArray);
                switch (i2) {
                    case 0:
                        jSONObject2.put("repeatMode", "REPEAT_OFF");
                        break;
                    case 1:
                        jSONObject2.put("repeatMode", "REPEAT_ALL");
                        break;
                    case 2:
                        jSONObject2.put("repeatMode", "REPEAT_SINGLE");
                        break;
                    case 3:
                        jSONObject2.put("repeatMode", "REPEAT_ALL_AND_SHUFFLE");
                        break;
                    default:
                        throw new IllegalArgumentException(new StringBuilder(32).append("Invalid repeat mode: ").append(i2).toString());
                }
                jSONObject2.put("startIndex", i);
                if (j != -1) {
                    jSONObject2.put("currentTime", ((double) j) / 1000.0d);
                }
                if (jSONObject != null) {
                    jSONObject2.put("customData", jSONObject);
                }
            } catch (JSONException e) {
            }
            zza(jSONObject2.toString(), zzoA, (String) null);
            return zzoA;
        } else {
            throw new IllegalArgumentException(new StringBuilder(54).append("playPosition can not be negative: ").append(j).toString());
        }
    }

    public final void zza(zzayq zzayq) {
        this.zzayC = zzayq;
    }

    public final long zzb(zzayt zzayt, JSONObject jSONObject) throws IOException, zzayr {
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayG.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "STOP");
            jSONObject2.put("mediaSessionId", zznk());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final long zzc(zzayt zzayt, JSONObject jSONObject) throws IOException, zzayr {
        JSONObject jSONObject2 = new JSONObject();
        long zzoA = zzoA();
        this.zzayF.zza(zzoA, zzayt);
        zzZ(true);
        try {
            jSONObject2.put("requestId", zzoA);
            jSONObject2.put("type", "PLAY");
            jSONObject2.put("mediaSessionId", zznk());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzoA, (String) null);
        return zzoA;
    }

    public final void zzc(long j, int i) {
        for (zzayu zzc : this.zzawS) {
            zzc.zzc(j, i, (Object) null);
        }
    }

    public final void zzch(String str) {
        this.zzarK.zzb("message received: %s", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("type");
            long optLong = jSONObject.optLong("requestId", -1);
            char c = 65535;
            switch (string.hashCode()) {
                case -1830647528:
                    if (string.equals("LOAD_CANCELLED")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1125000185:
                    if (string.equals("INVALID_REQUEST")) {
                        c = 4;
                        break;
                    }
                    break;
                case -262628938:
                    if (string.equals("LOAD_FAILED")) {
                        c = 2;
                        break;
                    }
                    break;
                case 431600379:
                    if (string.equals("INVALID_PLAYER_STATE")) {
                        c = 1;
                        break;
                    }
                    break;
                case 823510221:
                    if (string.equals("MEDIA_STATUS")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    JSONArray jSONArray = jSONObject.getJSONArray("status");
                    if (jSONArray.length() > 0) {
                        zza(optLong, jSONArray.getJSONObject(0));
                        return;
                    }
                    this.zzayB = null;
                    onStatusUpdated();
                    onMetadataUpdated();
                    onQueueStatusUpdated();
                    onPreloadStatusUpdated();
                    this.zzayK.zzc(optLong, 0, (Object) null);
                    return;
                case 1:
                    this.zzarK.zzf("received unexpected error: Invalid Player State.", new Object[0]);
                    JSONObject optJSONObject = jSONObject.optJSONObject("customData");
                    for (zzayu zzc : this.zzawS) {
                        zzc.zzc(optLong, 2100, optJSONObject);
                    }
                    return;
                case 2:
                    this.zzayD.zzc(optLong, 2100, jSONObject.optJSONObject("customData"));
                    return;
                case 3:
                    this.zzayD.zzc(optLong, 2101, jSONObject.optJSONObject("customData"));
                    return;
                case 4:
                    this.zzarK.zzf("received unexpected error: Invalid Request.", new Object[0]);
                    JSONObject optJSONObject2 = jSONObject.optJSONObject("customData");
                    for (zzayu zzc2 : this.zzawS) {
                        zzc2.zzc(optLong, 2100, optJSONObject2);
                    }
                    return;
                default:
                    return;
            }
        } catch (JSONException e) {
            this.zzarK.zzf("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
        }
        this.zzarK.zzf("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
    }

    public final void zzoz() {
        super.zzoz();
        zzoM();
    }

    /* access modifiers changed from: protected */
    public final boolean zzz(long j) {
        boolean z;
        for (zzayu zzd : this.zzawS) {
            zzd.zzd(j, 2102);
        }
        synchronized (zzayu.zzrl) {
            Iterator<zzayu> it = this.zzawS.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().zzoO()) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
        }
        return z;
    }
}
