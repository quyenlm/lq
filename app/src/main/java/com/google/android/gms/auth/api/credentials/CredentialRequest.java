package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class CredentialRequest extends zza {
    public static final Parcelable.Creator<CredentialRequest> CREATOR = new zze();
    private int zzaku;
    private final boolean zzald;
    private final String[] zzale;
    private final CredentialPickerConfig zzalf;
    private final CredentialPickerConfig zzalg;
    private final boolean zzalh;
    private final String zzali;
    private final String zzalj;

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean zzald;
        /* access modifiers changed from: private */
        public String[] zzale;
        /* access modifiers changed from: private */
        public CredentialPickerConfig zzalf;
        /* access modifiers changed from: private */
        public CredentialPickerConfig zzalg;
        /* access modifiers changed from: private */
        public boolean zzalh = false;
        /* access modifiers changed from: private */
        @Nullable
        public String zzali = null;
        /* access modifiers changed from: private */
        @Nullable
        public String zzalj;

        public final CredentialRequest build() {
            if (this.zzale == null) {
                this.zzale = new String[0];
            }
            if (this.zzald || this.zzale.length != 0) {
                return new CredentialRequest(this);
            }
            throw new IllegalStateException("At least one authentication method must be specified");
        }

        public final Builder setAccountTypes(String... strArr) {
            if (strArr == null) {
                strArr = new String[0];
            }
            this.zzale = strArr;
            return this;
        }

        public final Builder setCredentialHintPickerConfig(CredentialPickerConfig credentialPickerConfig) {
            this.zzalg = credentialPickerConfig;
            return this;
        }

        public final Builder setCredentialPickerConfig(CredentialPickerConfig credentialPickerConfig) {
            this.zzalf = credentialPickerConfig;
            return this;
        }

        public final Builder setIdTokenNonce(@Nullable String str) {
            this.zzalj = str;
            return this;
        }

        public final Builder setIdTokenRequested(boolean z) {
            this.zzalh = z;
            return this;
        }

        public final Builder setPasswordLoginSupported(boolean z) {
            this.zzald = z;
            return this;
        }

        public final Builder setServerClientId(@Nullable String str) {
            this.zzali = str;
            return this;
        }

        @Deprecated
        public final Builder setSupportsPasswordLogin(boolean z) {
            return setPasswordLoginSupported(z);
        }
    }

    CredentialRequest(int i, boolean z, String[] strArr, CredentialPickerConfig credentialPickerConfig, CredentialPickerConfig credentialPickerConfig2, boolean z2, String str, String str2) {
        this.zzaku = i;
        this.zzald = z;
        this.zzale = (String[]) zzbo.zzu(strArr);
        this.zzalf = credentialPickerConfig == null ? new CredentialPickerConfig.Builder().build() : credentialPickerConfig;
        this.zzalg = credentialPickerConfig2 == null ? new CredentialPickerConfig.Builder().build() : credentialPickerConfig2;
        if (i < 3) {
            this.zzalh = true;
            this.zzali = null;
            this.zzalj = null;
            return;
        }
        this.zzalh = z2;
        this.zzali = str;
        this.zzalj = str2;
    }

    private CredentialRequest(Builder builder) {
        this(3, builder.zzald, builder.zzale, builder.zzalf, builder.zzalg, builder.zzalh, builder.zzali, builder.zzalj);
    }

    @NonNull
    public final String[] getAccountTypes() {
        return this.zzale;
    }

    @NonNull
    public final Set<String> getAccountTypesSet() {
        return new HashSet(Arrays.asList(this.zzale));
    }

    @NonNull
    public final CredentialPickerConfig getCredentialHintPickerConfig() {
        return this.zzalg;
    }

    @NonNull
    public final CredentialPickerConfig getCredentialPickerConfig() {
        return this.zzalf;
    }

    @Nullable
    public final String getIdTokenNonce() {
        return this.zzalj;
    }

    @Nullable
    public final String getServerClientId() {
        return this.zzali;
    }

    @Deprecated
    public final boolean getSupportsPasswordLogin() {
        return isPasswordLoginSupported();
    }

    public final boolean isIdTokenRequested() {
        return this.zzalh;
    }

    public final boolean isPasswordLoginSupported() {
        return this.zzald;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, isPasswordLoginSupported());
        zzd.zza(parcel, 2, getAccountTypes(), false);
        zzd.zza(parcel, 3, (Parcelable) getCredentialPickerConfig(), i, false);
        zzd.zza(parcel, 4, (Parcelable) getCredentialHintPickerConfig(), i, false);
        zzd.zza(parcel, 5, isIdTokenRequested());
        zzd.zza(parcel, 6, getServerClientId(), false);
        zzd.zza(parcel, 7, getIdTokenNonce(), false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
