package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdb;
import com.google.android.gms.internal.zzbgb;
import com.tencent.imsdk.config.ConfigDBHelper;

public final class zze {
    private static zzbgb zzaml = new zzbgb("GoogleSignInCommon", new String[0]);

    public static GoogleSignInResult getSignInResultFromIntent(Intent intent) {
        if (intent == null || (!intent.hasExtra("googleSignInStatus") && !intent.hasExtra("googleSignInAccount"))) {
            return null;
        }
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) intent.getParcelableExtra("googleSignInAccount");
        Status status = (Status) intent.getParcelableExtra("googleSignInStatus");
        if (googleSignInAccount != null) {
            status = Status.zzaBm;
        }
        return new GoogleSignInResult(googleSignInAccount, status);
    }

    public static Intent zza(Context context, GoogleSignInOptions googleSignInOptions) {
        zzaml.zzb("GoogleSignInCommon", "getSignInIntent()");
        SignInConfiguration signInConfiguration = new SignInConfiguration(context.getPackageName(), googleSignInOptions);
        Intent intent = new Intent("com.google.android.gms.auth.GOOGLE_SIGN_IN");
        intent.setClass(context, SignInHubActivity.class);
        intent.putExtra(ConfigDBHelper.DATABASE_TABLE_NAME, signInConfiguration);
        return intent;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.common.api.OptionalPendingResult<com.google.android.gms.auth.api.signin.GoogleSignInResult> zza(com.google.android.gms.common.api.GoogleApiClient r7, android.content.Context r8, com.google.android.gms.auth.api.signin.GoogleSignInOptions r9) {
        /*
            r1 = 1
            r2 = 0
            com.google.android.gms.auth.api.signin.internal.zzy r3 = com.google.android.gms.auth.api.signin.internal.zzy.zzaj(r8)
            com.google.android.gms.internal.zzbgb r0 = zzaml
            java.lang.String r4 = "GoogleSignInCommon"
            java.lang.Object[] r5 = new java.lang.Object[r1]
            java.lang.String r6 = "getEligibleSavedSignInResult()"
            r5[r2] = r6
            r0.zzb(r4, r5)
            com.google.android.gms.common.internal.zzbo.zzu(r9)
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r4 = r3.zzmO()
            if (r4 == 0) goto L_0x0091
            android.accounts.Account r0 = r4.getAccount()
            android.accounts.Account r5 = r9.getAccount()
            if (r0 != 0) goto L_0x008c
            if (r5 != 0) goto L_0x008a
            r0 = r1
        L_0x0029:
            if (r0 == 0) goto L_0x0091
            boolean r0 = r9.zzmB()
            if (r0 != 0) goto L_0x0091
            boolean r0 = r9.isIdTokenRequested()
            if (r0 == 0) goto L_0x004b
            boolean r0 = r4.isIdTokenRequested()
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = r9.getServerClientId()
            java.lang.String r5 = r4.getServerClientId()
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x0091
        L_0x004b:
            java.util.HashSet r0 = new java.util.HashSet
            java.util.ArrayList r4 = r4.zzmA()
            r0.<init>(r4)
            java.util.HashSet r4 = new java.util.HashSet
            java.util.ArrayList r5 = r9.zzmA()
            r4.<init>(r5)
            boolean r0 = r0.containsAll(r4)
            if (r0 == 0) goto L_0x0091
            com.google.android.gms.auth.api.signin.GoogleSignInAccount r4 = r3.zzmN()
            if (r4 == 0) goto L_0x0091
            boolean r0 = r4.zzmw()
            if (r0 != 0) goto L_0x0091
            com.google.android.gms.auth.api.signin.GoogleSignInResult r0 = new com.google.android.gms.auth.api.signin.GoogleSignInResult
            com.google.android.gms.common.api.Status r5 = com.google.android.gms.common.api.Status.zzaBm
            r0.<init>(r4, r5)
        L_0x0076:
            if (r0 == 0) goto L_0x0093
            com.google.android.gms.internal.zzbgb r3 = zzaml
            java.lang.String r4 = "GoogleSignInCommon"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r5 = "Eligible saved sign in result found"
            r1[r2] = r5
            r3.zzb(r4, r1)
            com.google.android.gms.common.api.OptionalPendingResult r0 = com.google.android.gms.common.api.PendingResults.zzb(r0, r7)
        L_0x0089:
            return r0
        L_0x008a:
            r0 = r2
            goto L_0x0029
        L_0x008c:
            boolean r0 = r0.equals(r5)
            goto L_0x0029
        L_0x0091:
            r0 = 0
            goto L_0x0076
        L_0x0093:
            com.google.android.gms.internal.zzbgb r0 = zzaml
            java.lang.String r4 = "GoogleSignInCommon"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r5 = "trySilentSignIn()"
            r1[r2] = r5
            r0.zzb(r4, r1)
            com.google.android.gms.auth.api.signin.internal.zzf r0 = new com.google.android.gms.auth.api.signin.internal.zzf
            r0.<init>(r7, r3, r9)
            com.google.android.gms.internal.zzbay r1 = r7.zzd(r0)
            com.google.android.gms.internal.zzbec r0 = new com.google.android.gms.internal.zzbec
            r0.<init>(r1)
            goto L_0x0089
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.internal.zze.zza(com.google.android.gms.common.api.GoogleApiClient, android.content.Context, com.google.android.gms.auth.api.signin.GoogleSignInOptions):com.google.android.gms.common.api.OptionalPendingResult");
    }

    public static PendingResult<Status> zza(GoogleApiClient googleApiClient, Context context) {
        zzaml.zzb("GoogleSignInCommon", "Signing out");
        zzai(context);
        return googleApiClient.zze(new zzh(googleApiClient));
    }

    private static void zzai(Context context) {
        zzy.zzaj(context).zzmP();
        for (GoogleApiClient zzpl : GoogleApiClient.zzpk()) {
            zzpl.zzpl();
        }
        zzbdb.zzql();
    }

    public static PendingResult<Status> zzb(GoogleApiClient googleApiClient, Context context) {
        zzaml.zzb("GoogleSignInCommon", "Revoking access");
        zzai(context);
        return googleApiClient.zze(new zzj(googleApiClient));
    }
}
