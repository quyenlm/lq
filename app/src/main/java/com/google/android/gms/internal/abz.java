package com.google.android.gms.internal;

import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.GetTokenResult;
import com.vk.sdk.api.VKApiConst;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpHost;

public final class abz {
    @Nullable
    public static String zzf(FirebaseApp firebaseApp) {
        try {
            String token = ((GetTokenResult) Tasks.await(firebaseApp.getToken(false), NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS, TimeUnit.MILLISECONDS)).getToken();
            if (!TextUtils.isEmpty(token)) {
                return token;
            }
            Log.w("StorageUtil", "no auth token for request");
            return null;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            String valueOf = String.valueOf(e);
            Log.e("StorageUtil", new StringBuilder(String.valueOf(valueOf).length() + 20).append("error getting token ").append(valueOf).toString());
        }
    }

    @Nullable
    public static Uri zzg(@NonNull FirebaseApp firebaseApp, @Nullable String str) throws UnsupportedEncodingException {
        String substring;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.toLowerCase().startsWith("gs://")) {
            String valueOf = String.valueOf(abv.zzhI(abv.zzhK(str.substring(5))));
            return Uri.parse(valueOf.length() != 0 ? "gs://".concat(valueOf) : new String("gs://"));
        }
        Uri parse = Uri.parse(str);
        String scheme = parse.getScheme();
        if (scheme == null || (!zzbe.equal(scheme.toLowerCase(), HttpHost.DEFAULT_SCHEME_NAME) && !zzbe.equal(scheme.toLowerCase(), VKApiConst.HTTPS))) {
            String valueOf2 = String.valueOf(scheme);
            Log.w("StorageUtil", valueOf2.length() != 0 ? "FirebaseStorage is unable to support the scheme:".concat(valueOf2) : new String("FirebaseStorage is unable to support the scheme:"));
            throw new IllegalArgumentException("Uri scheme");
        }
        try {
            int indexOf = parse.getAuthority().toLowerCase().indexOf(ace.zzg(firebaseApp).zzLm());
            String zzhJ = abv.zzhJ(parse.getEncodedPath());
            if (indexOf == 0 && zzhJ.startsWith(Constants.URL_PATH_DELIMITER)) {
                int indexOf2 = zzhJ.indexOf("/b/", 0);
                int indexOf3 = zzhJ.indexOf(Constants.URL_PATH_DELIMITER, indexOf2 + 3);
                int indexOf4 = zzhJ.indexOf("/o/", 0);
                if (indexOf2 == -1 || indexOf3 == -1) {
                    Log.w("StorageUtil", "Only URLs to firebasestorage.googleapis.com are supported.");
                    throw new IllegalArgumentException("Only URLs to firebasestorage.googleapis.com are supported.");
                }
                substring = zzhJ.substring(indexOf2 + 3, indexOf3);
                zzhJ = indexOf4 != -1 ? zzhJ.substring(indexOf4 + 3) : "";
            } else if (indexOf > 1) {
                substring = parse.getAuthority().substring(0, indexOf - 1);
            } else {
                Log.w("StorageUtil", "Only URLs to firebasestorage.googleapis.com are supported.");
                throw new IllegalArgumentException("Only URLs to firebasestorage.googleapis.com are supported.");
            }
            zzbo.zzh(substring, "No bucket specified");
            return new Uri.Builder().scheme("gs").authority(substring).encodedPath(zzhJ).build();
        } catch (RemoteException e) {
            throw new UnsupportedEncodingException("Could not parse Url because the Storage network layer did not load");
        }
    }

    public static long zzhL(@Nullable String str) {
        if (str == null) {
            return 0;
        }
        String replaceAll = str.replaceAll("Z$", "-0000");
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()).parse(replaceAll).getTime();
        } catch (ParseException e) {
            ParseException parseException = e;
            String valueOf = String.valueOf(replaceAll);
            Log.w("StorageUtil", valueOf.length() != 0 ? "unable to parse datetime:".concat(valueOf) : new String("unable to parse datetime:"), parseException);
            return 0;
        }
    }
}
