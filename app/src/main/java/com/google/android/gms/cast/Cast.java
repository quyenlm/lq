package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzaxs;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzayn;
import java.io.IOException;

public final class Cast {
    public static final int ACTIVE_INPUT_STATE_NO = 0;
    public static final int ACTIVE_INPUT_STATE_UNKNOWN = -1;
    public static final int ACTIVE_INPUT_STATE_YES = 1;
    public static final Api<CastOptions> API = new Api<>("Cast.API", zzajS, zzayn.zzayp);
    public static final CastApi CastApi = new CastApi.zza();
    public static final String EXTRA_APP_NO_LONGER_RUNNING = "com.google.android.gms.cast.EXTRA_APP_NO_LONGER_RUNNING";
    public static final int MAX_MESSAGE_LENGTH = 65536;
    public static final int MAX_NAMESPACE_LENGTH = 128;
    public static final int STANDBY_STATE_NO = 0;
    public static final int STANDBY_STATE_UNKNOWN = -1;
    public static final int STANDBY_STATE_YES = 1;
    private static Api.zza<zzaxx, CastOptions> zzajS = new zze();

    public interface ApplicationConnectionResult extends Result {
        ApplicationMetadata getApplicationMetadata();

        String getApplicationStatus();

        String getSessionId();

        boolean getWasLaunched();
    }

    public static class Listener {
        public void onActiveInputStateChanged(int i) {
        }

        public void onApplicationDisconnected(int i) {
        }

        public void onApplicationMetadataChanged(ApplicationMetadata applicationMetadata) {
        }

        public void onApplicationStatusChanged() {
        }

        public void onStandbyStateChanged(int i) {
        }

        public void onVolumeChanged() {
        }
    }

    public interface MessageReceivedCallback {
        void onMessageReceived(CastDevice castDevice, String str, String str2);
    }

    static abstract class zza extends zzaxs<ApplicationConnectionResult> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public void zza(zzaxx zzaxx) throws RemoteException {
        }

        public final /* synthetic */ Result zzb(Status status) {
            return new zzm(this, status);
        }
    }

    @Deprecated
    public interface CastApi {

        public static final class zza implements CastApi {
            private final PendingResult<ApplicationConnectionResult> zza(GoogleApiClient googleApiClient, String str, String str2, zzz zzz) {
                return googleApiClient.zze(new zzi(this, googleApiClient, str, str2, (zzz) null));
            }

            public final int getActiveInputState(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzaxx) googleApiClient.zza(zzayn.zzayp)).getActiveInputState();
            }

            public final ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzaxx) googleApiClient.zza(zzayn.zzayp)).getApplicationMetadata();
            }

            public final String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzaxx) googleApiClient.zza(zzayn.zzayp)).getApplicationStatus();
            }

            public final int getStandbyState(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzaxx) googleApiClient.zza(zzayn.zzayp)).getStandbyState();
            }

            public final double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzaxx) googleApiClient.zza(zzayn.zzayp)).getVolume();
            }

            public final boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzaxx) googleApiClient.zza(zzayn.zzayp)).isMute();
            }

            public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient) {
                return zza(googleApiClient, (String) null, (String) null, (zzz) null);
            }

            public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str) {
                return zza(googleApiClient, str, (String) null, (zzz) null);
            }

            public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2) {
                return zza(googleApiClient, str, str2, (zzz) null);
            }

            public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str) {
                return googleApiClient.zze(new zzg(this, googleApiClient, str));
            }

            public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions) {
                return googleApiClient.zze(new zzh(this, googleApiClient, str, launchOptions));
            }

            @Deprecated
            public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z) {
                return launchApplication(googleApiClient, str, new LaunchOptions.Builder().setRelaunchIfRunning(z).build());
            }

            public final PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient) {
                return googleApiClient.zze(new zzj(this, googleApiClient));
            }

            public final void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str) throws IOException, IllegalArgumentException {
                try {
                    ((zzaxx) googleApiClient.zza(zzayn.zzayp)).removeMessageReceivedCallbacks(str);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public final void requestStatus(GoogleApiClient googleApiClient) throws IOException, IllegalStateException {
                try {
                    ((zzaxx) googleApiClient.zza(zzayn.zzayp)).requestStatus();
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public final PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2) {
                return googleApiClient.zze(new zzf(this, googleApiClient, str, str2));
            }

            public final void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException {
                try {
                    ((zzaxx) googleApiClient.zza(zzayn.zzayp)).setMessageReceivedCallbacks(str, messageReceivedCallback);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public final void setMute(GoogleApiClient googleApiClient, boolean z) throws IOException, IllegalStateException {
                try {
                    ((zzaxx) googleApiClient.zza(zzayn.zzayp)).setMute(z);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public final void setVolume(GoogleApiClient googleApiClient, double d) throws IOException, IllegalArgumentException, IllegalStateException {
                try {
                    ((zzaxx) googleApiClient.zza(zzayn.zzayp)).setVolume(d);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public final PendingResult<Status> stopApplication(GoogleApiClient googleApiClient) {
                return googleApiClient.zze(new zzk(this, googleApiClient));
            }

            public final PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str) {
                return googleApiClient.zze(new zzl(this, googleApiClient, str));
            }
        }

        int getActiveInputState(GoogleApiClient googleApiClient) throws IllegalStateException;

        ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException;

        String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException;

        int getStandbyState(GoogleApiClient googleApiClient) throws IllegalStateException;

        double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException;

        boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException;

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions);

        @Deprecated
        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z);

        PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient);

        void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str) throws IOException, IllegalArgumentException;

        void requestStatus(GoogleApiClient googleApiClient) throws IOException, IllegalStateException;

        PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2);

        void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException;

        void setMute(GoogleApiClient googleApiClient, boolean z) throws IOException, IllegalStateException;

        void setVolume(GoogleApiClient googleApiClient, double d) throws IOException, IllegalArgumentException, IllegalStateException;

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient);

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str);
    }

    public static final class CastOptions implements Api.ApiOptions.HasOptions {
        final Bundle extras;
        final CastDevice zzaoU;
        final Listener zzaoV;
        /* access modifiers changed from: private */
        public final int zzaoW;

        public static final class Builder {
            /* access modifiers changed from: private */
            public Bundle mExtras;
            CastDevice zzaoX;
            Listener zzaoY;
            /* access modifiers changed from: private */
            public int zzaoZ = 0;

            public Builder(CastDevice castDevice, Listener listener) {
                zzbo.zzb(castDevice, (Object) "CastDevice parameter cannot be null");
                zzbo.zzb(listener, (Object) "CastListener parameter cannot be null");
                this.zzaoX = castDevice;
                this.zzaoY = listener;
            }

            public final CastOptions build() {
                return new CastOptions(this, (zze) null);
            }

            public final Builder setVerboseLoggingEnabled(boolean z) {
                if (z) {
                    this.zzaoZ |= 1;
                } else {
                    this.zzaoZ &= -2;
                }
                return this;
            }

            public final Builder zzi(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }
        }

        private CastOptions(Builder builder) {
            this.zzaoU = builder.zzaoX;
            this.zzaoV = builder.zzaoY;
            this.zzaoW = builder.zzaoZ;
            this.extras = builder.mExtras;
        }

        /* synthetic */ CastOptions(Builder builder, zze zze) {
            this(builder);
        }

        @Deprecated
        public static Builder builder(CastDevice castDevice, Listener listener) {
            return new Builder(castDevice, listener);
        }
    }

    private Cast() {
    }
}
