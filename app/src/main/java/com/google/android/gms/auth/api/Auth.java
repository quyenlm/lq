package com.google.android.gms.auth.api;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zzc;
import com.google.android.gms.auth.api.signin.internal.zzd;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzaqy;
import com.google.android.gms.internal.zzaqz;
import com.google.android.gms.internal.zzara;
import com.google.android.gms.internal.zzarg;
import com.google.android.gms.internal.zzaro;
import com.google.android.gms.internal.zzasn;

public final class Auth {
    public static final Api<AuthCredentialsOptions> CREDENTIALS_API = new Api<>("Auth.CREDENTIALS_API", zzakH, zzakE);
    public static final CredentialsApi CredentialsApi = new zzarg();
    public static final Api<GoogleSignInOptions> GOOGLE_SIGN_IN_API = new Api<>("Auth.GOOGLE_SIGN_IN_API", zzakJ, zzakG);
    public static final GoogleSignInApi GoogleSignInApi = new zzc();
    @KeepForSdk
    public static final Api<zzf> PROXY_API = zzd.API;
    @KeepForSdk
    public static final ProxyApi ProxyApi = new zzasn();
    public static final Api.zzf<zzaro> zzakE = new Api.zzf<>();
    private static Api.zzf<zzara> zzakF = new Api.zzf<>();
    public static final Api.zzf<zzd> zzakG = new Api.zzf<>();
    private static final Api.zza<zzaro, AuthCredentialsOptions> zzakH = new zza();
    private static final Api.zza<zzara, Api.ApiOptions.NoOptions> zzakI = new zzb();
    private static final Api.zza<zzd, GoogleSignInOptions> zzakJ = new zzc();
    private static Api<Api.ApiOptions.NoOptions> zzakK = new Api<>("Auth.ACCOUNT_STATUS_API", zzakI, zzakF);
    private static zzaqy zzakL = new zzaqz();

    public static final class AuthCredentialsOptions implements Api.ApiOptions.Optional {
        private final String zzakM;
        private final PasswordSpecification zzakN;

        public static class Builder {
            @NonNull
            private PasswordSpecification zzakN = PasswordSpecification.zzalo;
        }

        public final Bundle zzmo() {
            Bundle bundle = new Bundle();
            bundle.putString("consumer_package", this.zzakM);
            bundle.putParcelable("password_specification", this.zzakN);
            return bundle;
        }

        public final PasswordSpecification zzmr() {
            return this.zzakN;
        }
    }

    private Auth() {
    }
}
