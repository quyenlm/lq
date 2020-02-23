package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zze;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.drive.DriveFile;

public final class zzarg implements CredentialsApi {
    public final PendingResult<Status> delete(GoogleApiClient googleApiClient, Credential credential) {
        return googleApiClient.zze(new zzark(this, googleApiClient, credential));
    }

    public final PendingResult<Status> disableAutoSignIn(GoogleApiClient googleApiClient) {
        return googleApiClient.zze(new zzarl(this, googleApiClient));
    }

    public final PendingIntent getHintPickerIntent(GoogleApiClient googleApiClient, HintRequest hintRequest) {
        zzbo.zzb(googleApiClient.zza((Api<?>) Auth.CREDENTIALS_API), (Object) "Auth.CREDENTIALS_API must be added to GoogleApiClient to use this API");
        Auth.AuthCredentialsOptions zzmu = ((zzaro) googleApiClient.zza(Auth.zzakE)).zzmu();
        Context context = googleApiClient.getContext();
        zzbo.zzb(context, (Object) "context must not be null");
        zzbo.zzb(hintRequest, (Object) "request must not be null");
        PasswordSpecification zzmr = (zzmu == null || zzmu.zzmr() == null) ? PasswordSpecification.zzalo : zzmu.zzmr();
        Intent putExtra = new Intent("com.google.android.gms.auth.api.credentials.PICKER").putExtra("com.google.android.gms.credentials.hintRequestVersion", 2).putExtra("com.google.android.gms.credentials.RequestType", "Hints");
        zze.zza(zzmr, putExtra, "com.google.android.gms.credentials.PasswordSpecification");
        zze.zza(hintRequest, putExtra, "com.google.android.gms.credentials.HintRequest");
        return PendingIntent.getActivity(context, 2000, putExtra, DriveFile.MODE_READ_ONLY);
    }

    public final PendingResult<CredentialRequestResult> request(GoogleApiClient googleApiClient, CredentialRequest credentialRequest) {
        return googleApiClient.zzd(new zzarh(this, googleApiClient, credentialRequest));
    }

    public final PendingResult<Status> save(GoogleApiClient googleApiClient, Credential credential) {
        return googleApiClient.zze(new zzarj(this, googleApiClient, credential));
    }
}
