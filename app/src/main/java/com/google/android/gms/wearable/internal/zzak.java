package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;

public final class zzak extends zza implements Channel {
    public static final Parcelable.Creator<zzak> CREATOR = new zzau();
    private final String mPath;
    /* access modifiers changed from: private */
    public final String zzakv;
    private final String zzbRe;

    public zzak(String str, String str2, String str3) {
        this.zzakv = (String) zzbo.zzu(str);
        this.zzbRe = (String) zzbo.zzu(str2);
        this.mPath = (String) zzbo.zzu(str3);
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener) {
        return zzb.zza(googleApiClient, new zzar(this.zzakv, new IntentFilter[]{zzez.zzgl(ChannelApi.ACTION_CHANNEL_EVENT)}), channelListener);
    }

    public final PendingResult<Status> close(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzal(this, googleApiClient));
    }

    public final PendingResult<Status> close(GoogleApiClient googleApiClient, int i) {
        return googleApiClient.zzd(new zzam(this, googleApiClient, i));
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzak)) {
            return false;
        }
        zzak zzak = (zzak) obj;
        return this.zzakv.equals(zzak.zzakv) && zzbe.equal(zzak.zzbRe, this.zzbRe) && zzbe.equal(zzak.mPath, this.mPath);
    }

    public final PendingResult<Channel.GetInputStreamResult> getInputStream(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzan(this, googleApiClient));
    }

    public final String getNodeId() {
        return this.zzbRe;
    }

    public final PendingResult<Channel.GetOutputStreamResult> getOutputStream(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzao(this, googleApiClient));
    }

    public final String getPath() {
        return this.mPath;
    }

    public final int hashCode() {
        return this.zzakv.hashCode();
    }

    public final PendingResult<Status> receiveFile(GoogleApiClient googleApiClient, Uri uri, boolean z) {
        zzbo.zzb(googleApiClient, (Object) "client is null");
        zzbo.zzb(uri, (Object) "uri is null");
        return googleApiClient.zzd(new zzap(this, googleApiClient, uri, z));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener) {
        zzbo.zzb(googleApiClient, (Object) "client is null");
        zzbo.zzb(channelListener, (Object) "listener is null");
        return googleApiClient.zzd(new zzag(googleApiClient, channelListener, this.zzakv));
    }

    public final PendingResult<Status> sendFile(GoogleApiClient googleApiClient, Uri uri) {
        return sendFile(googleApiClient, uri, 0, -1);
    }

    public final PendingResult<Status> sendFile(GoogleApiClient googleApiClient, Uri uri, long j, long j2) {
        zzbo.zzb(googleApiClient, (Object) "client is null");
        zzbo.zzb(this.zzakv, (Object) "token is null");
        zzbo.zzb(uri, (Object) "uri is null");
        zzbo.zzb(j >= 0, "startOffset is negative: %s", Long.valueOf(j));
        zzbo.zzb(j2 >= 0 || j2 == -1, "invalid length: %s", Long.valueOf(j2));
        return googleApiClient.zzd(new zzaq(this, googleApiClient, uri, j, j2));
    }

    public final String toString() {
        String str = this.zzakv;
        String str2 = this.zzbRe;
        String str3 = this.mPath;
        return new StringBuilder(String.valueOf(str).length() + 43 + String.valueOf(str2).length() + String.valueOf(str3).length()).append("ChannelImpl{, token='").append(str).append("', nodeId='").append(str2).append("', path='").append(str3).append("'}").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzakv, false);
        zzd.zza(parcel, 3, getNodeId(), false);
        zzd.zza(parcel, 4, getPath(), false);
        zzd.zzI(parcel, zze);
    }
}
