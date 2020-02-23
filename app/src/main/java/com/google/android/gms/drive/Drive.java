package com.google.android.gms.drive;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.internal.zzblo;
import com.google.android.gms.internal.zzbmh;
import com.google.android.gms.internal.zzbmw;
import com.google.android.gms.internal.zzbnh;
import com.google.android.gms.internal.zzbnz;

public final class Drive {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Drive.API", new zze(), zzajR);
    public static final DriveApi DriveApi = new zzblo();
    public static final DrivePreferencesApi DrivePreferencesApi = new zzbnh();
    public static final Scope SCOPE_APPFOLDER = new Scope(Scopes.DRIVE_APPFOLDER);
    public static final Scope SCOPE_FILE = new Scope(Scopes.DRIVE_FILE);
    private static Scope zzaMa = new Scope("https://www.googleapis.com/auth/drive");
    private static Scope zzaMb = new Scope("https://www.googleapis.com/auth/drive.apps");
    private static Api<zzb> zzaMc = new Api<>("Drive.INTERNAL_API", new zzf(), zzajR);
    private static zzi zzaMd = new zzbmw();
    private static zzk zzaMe = new zzbnz();
    public static final Api.zzf<zzbmh> zzajR = new Api.zzf<>();

    public static abstract class zza<O extends Api.ApiOptions> extends Api.zza<zzbmh, O> {
        /* access modifiers changed from: protected */
        public abstract Bundle zza(O o);

        public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzbmh(context, looper, zzq, connectionCallbacks, onConnectionFailedListener, zza((Api.ApiOptions) obj));
        }
    }

    public static class zzb implements Api.ApiOptions.Optional {
        private final Bundle mBundle;

        public final Bundle zzsL() {
            return this.mBundle;
        }
    }

    private Drive() {
    }
}
