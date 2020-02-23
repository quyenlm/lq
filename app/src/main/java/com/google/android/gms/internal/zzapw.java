package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.vk.sdk.api.VKApiConst;
import java.util.List;
import org.apache.http.HttpHost;

public final class zzapw implements AppIndexApi {
    private static final String TAG = zzapw.class.getSimpleName();

    static abstract class zzb<T extends Result> extends zzbay<T, zzapu> {
        public zzb(GoogleApiClient googleApiClient) {
            super((Api<?>) zzaou.zzaiT, googleApiClient);
        }

        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Result) obj);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
            zza((zzapp) ((zzapu) zzb).zzrf());
        }

        /* access modifiers changed from: protected */
        public abstract void zza(zzapp zzapp) throws RemoteException;
    }

    public static abstract class zzc<T extends Result> extends zzb<Status> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return status;
        }
    }

    public static final class zzd extends zzapt<Status> {
        public zzd(zzbaz<Status> zzbaz) {
            super(zzbaz);
        }

        public final void zza(Status status) {
            this.zzajL.setResult(status);
        }
    }

    @Deprecated
    static final class zza implements AppIndexApi.ActionResult {
        private zzapw zzajN;
        private PendingResult<Status> zzajO;
        private Action zzajP;

        zza(zzapw zzapw, PendingResult<Status> pendingResult, Action action) {
            this.zzajN = zzapw;
            this.zzajO = pendingResult;
            this.zzajP = action;
        }

        public final PendingResult<Status> end(GoogleApiClient googleApiClient) {
            String packageName = googleApiClient.getContext().getPackageName();
            zzapl zza = zzapv.zza(this.zzajP, System.currentTimeMillis(), packageName, 2);
            return this.zzajN.zza(googleApiClient, zza);
        }

        public final PendingResult<Status> getPendingResult() {
            return this.zzajO;
        }
    }

    public static Intent zza(String str, Uri uri) {
        zzb(str, uri);
        if (zzk(uri)) {
            return new Intent("android.intent.action.VIEW", uri);
        }
        if (zzl(uri)) {
            return new Intent("android.intent.action.VIEW", zzj(uri));
        }
        String valueOf = String.valueOf(uri);
        throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 70).append("appIndexingUri is neither an HTTP(S) URL nor an \"android-app://\" URL: ").append(valueOf).toString());
    }

    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, Action action, int i) {
        return zza(googleApiClient, zzapv.zza(action, System.currentTimeMillis(), googleApiClient.getContext().getPackageName(), i));
    }

    private static void zzb(String str, Uri uri) {
        if (zzk(uri)) {
            if (uri.getHost().isEmpty()) {
                String valueOf = String.valueOf(uri);
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 98).append("AppIndex: The web URL must have a host (follow the format http(s)://<host>/<path>). Provided URI: ").append(valueOf).toString());
            }
        } else if (!zzl(uri)) {
            String valueOf2 = String.valueOf(uri);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf2).length() + 176).append("AppIndex: The URI scheme must either be 'http(s)' or 'android-app'. If the latter, it must follow the format 'android-app://<package_name>/<scheme>/<host_path>'. Provided URI: ").append(valueOf2).toString());
        } else if (str == null || str.equals(uri.getHost())) {
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments.isEmpty() || pathSegments.get(0).isEmpty()) {
                String valueOf3 = String.valueOf(uri);
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf3).length() + 128).append("AppIndex: The app URI scheme must exist and follow the format android-app://<package_name>/<scheme>/<host_path>). Provided URI: ").append(valueOf3).toString());
            }
        } else {
            String valueOf4 = String.valueOf(uri);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf4).length() + 150).append("AppIndex: The android-app URI host must match the package name and follow the format android-app://<package_name>/<scheme>/<host_path>. Provided URI: ").append(valueOf4).toString());
        }
    }

    private static Uri zzj(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(pathSegments.get(0));
        if (pathSegments.size() > 1) {
            builder.authority(pathSegments.get(1));
            int i = 2;
            while (true) {
                int i2 = i;
                if (i2 >= pathSegments.size()) {
                    break;
                }
                builder.appendPath(pathSegments.get(i2));
                i = i2 + 1;
            }
        } else {
            String str = TAG;
            String valueOf = String.valueOf(uri);
            Log.e(str, new StringBuilder(String.valueOf(valueOf).length() + 88).append("The app URI must have the format: android-app://<package_name>/<scheme>/<path>. But got ").append(valueOf).toString());
        }
        builder.encodedQuery(uri.getEncodedQuery());
        builder.encodedFragment(uri.getEncodedFragment());
        return builder.build();
    }

    private static boolean zzk(Uri uri) {
        String scheme = uri.getScheme();
        return HttpHost.DEFAULT_SCHEME_NAME.equals(scheme) || VKApiConst.HTTPS.equals(scheme);
    }

    private static boolean zzl(Uri uri) {
        return "android-app".equals(uri.getScheme());
    }

    public final AppIndexApi.ActionResult action(GoogleApiClient googleApiClient, Action action) {
        return new zza(this, zza(googleApiClient, action, 1), action);
    }

    public final PendingResult<Status> end(GoogleApiClient googleApiClient, Action action) {
        return zza(googleApiClient, action, 2);
    }

    public final PendingResult<Status> start(GoogleApiClient googleApiClient, Action action) {
        return zza(googleApiClient, action, 1);
    }

    public final PendingResult<Status> view(GoogleApiClient googleApiClient, Activity activity, Intent intent, String str, Uri uri, List<AppIndexApi.AppIndexingLink> list) {
        String packageName = googleApiClient.getContext().getPackageName();
        if (list != null) {
            for (AppIndexApi.AppIndexingLink appIndexingLink : list) {
                zzb((String) null, appIndexingLink.appIndexingUrl);
            }
        }
        return zza(googleApiClient, new zzapl(packageName, intent, str, uri, (String) null, list, 1));
    }

    public final PendingResult<Status> view(GoogleApiClient googleApiClient, Activity activity, Uri uri, String str, Uri uri2, List<AppIndexApi.AppIndexingLink> list) {
        String packageName = googleApiClient.getContext().getPackageName();
        zzb(packageName, uri);
        return view(googleApiClient, activity, zza(packageName, uri), str, uri2, list);
    }

    public final PendingResult<Status> viewEnd(GoogleApiClient googleApiClient, Activity activity, Intent intent) {
        return zza(googleApiClient, new zzapm().zza(zzapl.zza(googleApiClient.getContext().getPackageName(), intent)).zzv(System.currentTimeMillis()).zzS(0).zzT(2).zzmm());
    }

    public final PendingResult<Status> viewEnd(GoogleApiClient googleApiClient, Activity activity, Uri uri) {
        return viewEnd(googleApiClient, activity, zza(googleApiClient.getContext().getPackageName(), uri));
    }

    public final PendingResult<Status> zza(GoogleApiClient googleApiClient, zzapl... zzaplArr) {
        return googleApiClient.zzd(new zzapx(this, googleApiClient, zzaplArr));
    }
}
