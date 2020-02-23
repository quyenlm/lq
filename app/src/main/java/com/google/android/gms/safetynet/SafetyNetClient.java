package com.google.android.gms.safetynet;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.internal.zzbas;
import com.google.android.gms.internal.zzbem;
import com.google.android.gms.internal.zzcsa;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.Task;

public class SafetyNetClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    SafetyNetClient(@NonNull Activity activity) {
        super(activity, SafetyNet.API, null, (zzbem) new zzbas());
    }

    SafetyNetClient(@NonNull Context context) {
        super(context, SafetyNet.API, null, (zzbem) new zzbas());
    }

    public Task<SafetyNetApi.AttestationResponse> attest(byte[] bArr, String str) {
        return zzbh.zza(zzcsa.zza(zzpi(), bArr, str), new SafetyNetApi.AttestationResponse());
    }

    public Task<SafetyNetApi.VerifyAppsUserResponse> enableVerifyApps() {
        return zzbh.zza(SafetyNet.SafetyNetApi.enableVerifyApps(zzpi()), new SafetyNetApi.VerifyAppsUserResponse());
    }

    public Task<Void> initSafeBrowsing() {
        return zza(new zzj(this));
    }

    public Task<SafetyNetApi.VerifyAppsUserResponse> isVerifyAppsEnabled() {
        return zzbh.zza(SafetyNet.SafetyNetApi.isVerifyAppsEnabled(zzpi()), new SafetyNetApi.VerifyAppsUserResponse());
    }

    public Task<SafetyNetApi.HarmfulAppsResponse> listHarmfulApps() {
        return zzbh.zza(SafetyNet.SafetyNetApi.listHarmfulApps(zzpi()), new SafetyNetApi.HarmfulAppsResponse());
    }

    public Task<SafetyNetApi.SafeBrowsingResponse> lookupUri(String str, String str2, int... iArr) {
        return zzbh.zza(zzcsa.zza(zzpi(), str, 3, str2, iArr), new SafetyNetApi.SafeBrowsingResponse());
    }

    public Task<Void> shutdownSafeBrowsing() {
        return zza(new zzl(this));
    }

    public Task<SafetyNetApi.RecaptchaTokenResponse> verifyWithRecaptcha(String str) {
        return zzbh.zza(SafetyNet.SafetyNetApi.verifyWithRecaptcha(zzpi(), str), new SafetyNetApi.RecaptchaTokenResponse());
    }
}
