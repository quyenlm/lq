package com.google.android.gms.cast.framework;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzauj;
import com.google.android.gms.internal.zzaul;
import com.google.android.gms.internal.zzavn;
import com.google.android.gms.internal.zzayo;
import com.google.android.gms.internal.zzayp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CastSession extends Session {
    /* access modifiers changed from: private */
    public static final zzayo zzarK = new zzayo("CastSession");
    /* access modifiers changed from: private */
    public GoogleApiClient zzapu;
    private final Context zzarM;
    private final CastOptions zzarQ;
    /* access modifiers changed from: private */
    public final Set<Cast.Listener> zzarZ = new HashSet();
    /* access modifiers changed from: private */
    public final zzm zzasa;
    /* access modifiers changed from: private */
    public final Cast.CastApi zzasb;
    private final zzaul zzasc;
    /* access modifiers changed from: private */
    public final zzavn zzasd;
    /* access modifiers changed from: private */
    public RemoteMediaClient zzase;
    private CastDevice zzasf;
    /* access modifiers changed from: private */
    public Cast.ApplicationConnectionResult zzasg;

    class zza implements ResultCallback<Cast.ApplicationConnectionResult> {
        private String zzash;

        zza(String str) {
            this.zzash = str;
        }

        public final /* synthetic */ void onResult(@NonNull Result result) {
            Cast.ApplicationConnectionResult applicationConnectionResult = (Cast.ApplicationConnectionResult) result;
            Cast.ApplicationConnectionResult unused = CastSession.this.zzasg = applicationConnectionResult;
            try {
                if (applicationConnectionResult.getStatus().isSuccess()) {
                    CastSession.zzarK.zzb("%s() -> success result", this.zzash);
                    RemoteMediaClient unused2 = CastSession.this.zzase = new RemoteMediaClient(new zzayp((String) null), CastSession.this.zzasb);
                    try {
                        CastSession.this.zzase.zzc(CastSession.this.zzapu);
                        CastSession.this.zzase.zznX();
                        CastSession.this.zzase.requestStatus();
                        CastSession.this.zzasd.zza(CastSession.this.zzase, CastSession.this.getCastDevice());
                    } catch (IOException e) {
                        CastSession.zzarK.zza(e, "Exception when setting GoogleApiClient.", new Object[0]);
                        RemoteMediaClient unused3 = CastSession.this.zzase = null;
                    }
                    CastSession.this.zzasa.zza(applicationConnectionResult.getApplicationMetadata(), applicationConnectionResult.getApplicationStatus(), applicationConnectionResult.getSessionId(), applicationConnectionResult.getWasLaunched());
                    return;
                }
                CastSession.zzarK.zzb("%s() -> failure result", this.zzash);
                CastSession.this.zzasa.zzZ(applicationConnectionResult.getStatus().getStatusCode());
            } catch (RemoteException e2) {
                CastSession.zzarK.zzb(e2, "Unable to call %s on %s.", "methods", zzm.class.getSimpleName());
            }
        }
    }

    class zzb extends zzj {
        private zzb() {
        }

        public final void zzY(int i) {
            CastSession.this.zzY(i);
        }

        public final void zza(String str, LaunchOptions launchOptions) {
            CastSession.this.zzasb.launchApplication(CastSession.this.zzapu, str, launchOptions).setResultCallback(new zza("launchApplication"));
        }

        public final void zzcc(String str) {
            CastSession.this.zzasb.stopApplication(CastSession.this.zzapu, str);
        }

        public final void zzt(String str, String str2) {
            CastSession.this.zzasb.joinApplication(CastSession.this.zzapu, str, str2).setResultCallback(new zza("joinApplication"));
        }
    }

    class zzc extends Cast.Listener {
        private zzc() {
        }

        public final void onActiveInputStateChanged(int i) {
            for (Cast.Listener onActiveInputStateChanged : new HashSet(CastSession.this.zzarZ)) {
                onActiveInputStateChanged.onActiveInputStateChanged(i);
            }
        }

        public final void onApplicationDisconnected(int i) {
            CastSession.this.zzY(i);
            CastSession.this.notifySessionEnded(i);
            for (Cast.Listener onApplicationDisconnected : new HashSet(CastSession.this.zzarZ)) {
                onApplicationDisconnected.onApplicationDisconnected(i);
            }
        }

        public final void onApplicationMetadataChanged(ApplicationMetadata applicationMetadata) {
            for (Cast.Listener onApplicationMetadataChanged : new HashSet(CastSession.this.zzarZ)) {
                onApplicationMetadataChanged.onApplicationMetadataChanged(applicationMetadata);
            }
        }

        public final void onApplicationStatusChanged() {
            for (Cast.Listener onApplicationStatusChanged : new HashSet(CastSession.this.zzarZ)) {
                onApplicationStatusChanged.onApplicationStatusChanged();
            }
        }

        public final void onStandbyStateChanged(int i) {
            for (Cast.Listener onStandbyStateChanged : new HashSet(CastSession.this.zzarZ)) {
                onStandbyStateChanged.onStandbyStateChanged(i);
            }
        }

        public final void onVolumeChanged() {
            for (Cast.Listener onVolumeChanged : new HashSet(CastSession.this.zzarZ)) {
                onVolumeChanged.onVolumeChanged();
            }
        }
    }

    class zzd implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        private zzd() {
        }

        public final void onConnected(Bundle bundle) {
            try {
                if (CastSession.this.zzase != null) {
                    try {
                        CastSession.this.zzase.zznX();
                        CastSession.this.zzase.requestStatus();
                    } catch (IOException e) {
                        CastSession.zzarK.zza(e, "Exception when setting GoogleApiClient.", new Object[0]);
                        RemoteMediaClient unused = CastSession.this.zzase = null;
                    }
                }
                CastSession.this.zzasa.onConnected(bundle);
            } catch (RemoteException e2) {
                CastSession.zzarK.zzb(e2, "Unable to call %s on %s.", "onConnected", zzm.class.getSimpleName());
            }
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            try {
                CastSession.this.zzasa.onConnectionFailed(connectionResult);
            } catch (RemoteException e) {
                CastSession.zzarK.zzb(e, "Unable to call %s on %s.", "onConnectionFailed", zzm.class.getSimpleName());
            }
        }

        public final void onConnectionSuspended(int i) {
            try {
                CastSession.this.zzasa.onConnectionSuspended(i);
            } catch (RemoteException e) {
                CastSession.zzarK.zzb(e, "Unable to call %s on %s.", "onConnectionSuspended", zzm.class.getSimpleName());
            }
        }
    }

    public CastSession(Context context, String str, String str2, CastOptions castOptions, Cast.CastApi castApi, zzaul zzaul, zzavn zzavn) {
        super(context, str, str2);
        this.zzarM = context.getApplicationContext();
        this.zzarQ = castOptions;
        this.zzasb = castApi;
        this.zzasc = zzaul;
        this.zzasd = zzavn;
        this.zzasa = zzauj.zza(context, castOptions, zznw(), (zzi) new zzb());
    }

    /* access modifiers changed from: private */
    public final void zzY(int i) {
        this.zzasd.zzab(i);
        if (this.zzapu != null) {
            this.zzapu.disconnect();
            this.zzapu = null;
        }
        this.zzasf = null;
        if (this.zzase != null) {
            try {
                this.zzase.zzc((GoogleApiClient) null);
            } catch (IOException e) {
                zzarK.zza(e, "Exception when setting GoogleApiClient.", new Object[0]);
            }
            this.zzase = null;
        }
        this.zzasg = null;
    }

    private final void zzj(Bundle bundle) {
        boolean z = true;
        this.zzasf = CastDevice.getFromBundle(bundle);
        if (this.zzasf != null) {
            if (this.zzapu != null) {
                this.zzapu.disconnect();
                this.zzapu = null;
            }
            zzarK.zzb("Acquiring a connection to Google Play Services for %s", this.zzasf);
            zzd zzd2 = new zzd();
            Context context = this.zzarM;
            CastDevice castDevice = this.zzasf;
            CastOptions castOptions = this.zzarQ;
            zzc zzc2 = new zzc();
            Bundle bundle2 = new Bundle();
            if (castOptions == null || castOptions.getCastMediaOptions() == null || castOptions.getCastMediaOptions().getNotificationOptions() == null) {
                z = false;
            }
            bundle2.putBoolean("com.google.android.gms.cast.EXTRA_CAST_FRAMEWORK_NOTIFICATION_ENABLED", z);
            this.zzapu = new GoogleApiClient.Builder(context).addApi(Cast.API, new Cast.CastOptions.Builder(castDevice, zzc2).zzi(bundle2).build()).addConnectionCallbacks(zzd2).addOnConnectionFailedListener(zzd2).build();
            this.zzapu.connect();
        } else if (isResuming()) {
            notifyFailedToResumeSession(8);
        } else {
            notifyFailedToStartSession(8);
        }
    }

    public void addCastListener(Cast.Listener listener) {
        zzbo.zzcz("Must be called from the main thread.");
        if (listener != null) {
            this.zzarZ.add(listener);
        }
    }

    /* access modifiers changed from: protected */
    public void end(boolean z) {
        try {
            this.zzasa.zzb(z, 0);
        } catch (RemoteException e) {
            zzarK.zzb(e, "Unable to call %s on %s.", "disconnectFromDevice", zzm.class.getSimpleName());
        }
        notifySessionEnded(0);
    }

    public int getActiveInputState() throws IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            return this.zzasb.getActiveInputState(this.zzapu);
        }
        return -1;
    }

    public Cast.ApplicationConnectionResult getApplicationConnectionResult() {
        zzbo.zzcz("Must be called from the main thread.");
        return this.zzasg;
    }

    public ApplicationMetadata getApplicationMetadata() throws IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            return this.zzasb.getApplicationMetadata(this.zzapu);
        }
        return null;
    }

    public String getApplicationStatus() throws IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            return this.zzasb.getApplicationStatus(this.zzapu);
        }
        return null;
    }

    public CastDevice getCastDevice() {
        zzbo.zzcz("Must be called from the main thread.");
        return this.zzasf;
    }

    public RemoteMediaClient getRemoteMediaClient() {
        zzbo.zzcz("Must be called from the main thread.");
        return this.zzase;
    }

    public long getSessionRemainingTimeMs() {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzase == null) {
            return 0;
        }
        return this.zzase.getStreamDuration() - this.zzase.getApproximateStreamPosition();
    }

    public int getStandbyState() throws IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            return this.zzasb.getStandbyState(this.zzapu);
        }
        return -1;
    }

    public double getVolume() throws IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        return this.zzapu != null ? this.zzasb.getVolume(this.zzapu) : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    }

    public boolean isMute() throws IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            return this.zzasb.isMute(this.zzapu);
        }
        return false;
    }

    public void removeCastListener(Cast.Listener listener) {
        zzbo.zzcz("Must be called from the main thread.");
        if (listener != null) {
            this.zzarZ.remove(listener);
        }
    }

    public void removeMessageReceivedCallbacks(String str) throws IOException, IllegalArgumentException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            this.zzasb.removeMessageReceivedCallbacks(this.zzapu, str);
        }
    }

    public void requestStatus() throws IOException, IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            this.zzasb.requestStatus(this.zzapu);
        }
    }

    /* access modifiers changed from: protected */
    public void resume(Bundle bundle) {
        zzj(bundle);
    }

    public PendingResult<Status> sendMessage(String str, String str2) {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            return this.zzasb.sendMessage(this.zzapu, str, str2);
        }
        return null;
    }

    public void setMessageReceivedCallbacks(String str, Cast.MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            this.zzasb.setMessageReceivedCallbacks(this.zzapu, str, messageReceivedCallback);
        }
    }

    public void setMute(boolean z) throws IOException, IllegalStateException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            this.zzasb.setMute(this.zzapu, z);
        }
    }

    public void setVolume(double d) throws IOException {
        zzbo.zzcz("Must be called from the main thread.");
        if (this.zzapu != null) {
            this.zzasb.setVolume(this.zzapu, d);
        }
    }

    /* access modifiers changed from: protected */
    public void start(Bundle bundle) {
        zzj(bundle);
    }
}
