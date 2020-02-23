package com.google.firebase.auth;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.aad;
import com.google.android.gms.internal.aae;
import com.google.android.gms.internal.iq;
import com.google.android.gms.internal.jz;
import com.google.android.gms.internal.ke;
import com.google.android.gms.internal.kh;
import com.google.android.gms.internal.kx;
import com.google.android.gms.internal.ll;
import com.google.android.gms.internal.lp;
import com.google.android.gms.internal.lr;
import com.google.android.gms.internal.ls;
import com.google.android.gms.internal.lv;
import com.google.android.gms.internal.lw;
import com.google.android.gms.internal.ly;
import com.google.android.gms.internal.lz;
import com.google.android.gms.internal.ma;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class FirebaseAuth implements aad {
    private static FirebaseAuth zzbWf;
    private static Map<String, FirebaseAuth> zzbgQ = new ArrayMap();
    /* access modifiers changed from: private */
    public FirebaseApp zzbVZ;
    /* access modifiers changed from: private */
    public List<AuthStateListener> zzbVd;
    /* access modifiers changed from: private */
    public List<IdTokenListener> zzbWa;
    private iq zzbWb;
    /* access modifiers changed from: private */
    public FirebaseUser zzbWc;
    private ly zzbWd;
    private lz zzbWe;

    public interface AuthStateListener {
        void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth);
    }

    public interface IdTokenListener {
        void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth);
    }

    class zza implements ll {
        zza() {
        }

        public final void zza(@NonNull kx kxVar, @NonNull FirebaseUser firebaseUser) {
            zzbo.zzu(kxVar);
            zzbo.zzu(firebaseUser);
            firebaseUser.zza(kxVar);
            FirebaseAuth.this.zza(firebaseUser, kxVar, true);
        }
    }

    class zzb extends zza implements ll, lw {
        zzb() {
            super();
        }

        public final void onError(Status status) {
            if (status.getStatusCode() == 17011 || status.getStatusCode() == 17021 || status.getStatusCode() == 17005) {
                FirebaseAuth.this.signOut();
            }
        }
    }

    public FirebaseAuth(FirebaseApp firebaseApp) {
        this(firebaseApp, ke.zza(firebaseApp.getApplicationContext(), new kh(firebaseApp.getOptions().getApiKey()).zzEP()), new ly(firebaseApp.getApplicationContext(), firebaseApp.zzEr()));
    }

    private FirebaseAuth(FirebaseApp firebaseApp, iq iqVar, ly lyVar) {
        kx zzh;
        this.zzbVZ = (FirebaseApp) zzbo.zzu(firebaseApp);
        this.zzbWb = (iq) zzbo.zzu(iqVar);
        this.zzbWd = (ly) zzbo.zzu(lyVar);
        this.zzbWa = new CopyOnWriteArrayList();
        this.zzbVd = new CopyOnWriteArrayList();
        this.zzbWe = lz.zzFb();
        this.zzbWc = this.zzbWd.zzFa();
        if (this.zzbWc != null && (zzh = this.zzbWd.zzh(this.zzbWc)) != null) {
            zza(this.zzbWc, zzh, false);
        }
    }

    @Keep
    public static FirebaseAuth getInstance() {
        return zzb(FirebaseApp.getInstance());
    }

    @Keep
    public static FirebaseAuth getInstance(@NonNull FirebaseApp firebaseApp) {
        return zzb(firebaseApp);
    }

    private final void zza(@Nullable FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String valueOf = String.valueOf(firebaseUser.getUid());
            Log.d("FirebaseAuth", new StringBuilder(String.valueOf(valueOf).length() + 45).append("Notifying id token listeners about user ( ").append(valueOf).append(" ).").toString());
        } else {
            Log.d("FirebaseAuth", "Notifying id token listeners about a sign-out event.");
        }
        this.zzbWe.execute(new zzh(this, new aae(firebaseUser != null ? firebaseUser.zzEI() : null)));
    }

    private static synchronized FirebaseAuth zzb(@NonNull FirebaseApp firebaseApp) {
        FirebaseAuth firebaseAuth;
        synchronized (FirebaseAuth.class) {
            firebaseAuth = zzbgQ.get(firebaseApp.zzEr());
            if (firebaseAuth == null) {
                firebaseAuth = new lr(firebaseApp);
                firebaseApp.zza((aad) firebaseAuth);
                if (zzbWf == null) {
                    zzbWf = firebaseAuth;
                }
                zzbgQ.put(firebaseApp.zzEr(), firebaseAuth);
            }
        }
        return firebaseAuth;
    }

    private final void zzb(@Nullable FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String valueOf = String.valueOf(firebaseUser.getUid());
            Log.d("FirebaseAuth", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Notifying auth state listeners about user ( ").append(valueOf).append(" ).").toString());
        } else {
            Log.d("FirebaseAuth", "Notifying auth state listeners about a sign-out event.");
        }
        this.zzbWe.execute(new zzi(this));
    }

    public void addAuthStateListener(@NonNull AuthStateListener authStateListener) {
        this.zzbVd.add(authStateListener);
        this.zzbWe.execute(new zzg(this, authStateListener));
    }

    public void addIdTokenListener(@NonNull IdTokenListener idTokenListener) {
        this.zzbWa.add(idTokenListener);
        this.zzbWe.execute(new zzf(this, idTokenListener));
    }

    @NonNull
    public Task<Void> applyActionCode(@NonNull String str) {
        zzbo.zzcF(str);
        return this.zzbWb.zze(this.zzbVZ, str);
    }

    @NonNull
    public Task<ActionCodeResult> checkActionCode(@NonNull String str) {
        zzbo.zzcF(str);
        return this.zzbWb.zzd(this.zzbVZ, str);
    }

    @NonNull
    public Task<Void> confirmPasswordReset(@NonNull String str, @NonNull String str2) {
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        return this.zzbWb.zza(this.zzbVZ, str, str2);
    }

    @NonNull
    public Task<AuthResult> createUserWithEmailAndPassword(@NonNull String str, @NonNull String str2) {
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        return this.zzbWb.zza(this.zzbVZ, str, str2, (ll) new zza());
    }

    @NonNull
    public Task<ProviderQueryResult> fetchProvidersForEmail(@NonNull String str) {
        zzbo.zzcF(str);
        return this.zzbWb.zza(this.zzbVZ, str);
    }

    @Nullable
    public FirebaseUser getCurrentUser() {
        return this.zzbWc;
    }

    public void removeAuthStateListener(@NonNull AuthStateListener authStateListener) {
        this.zzbVd.remove(authStateListener);
    }

    public void removeIdTokenListener(@NonNull IdTokenListener idTokenListener) {
        this.zzbWa.remove(idTokenListener);
    }

    @NonNull
    public Task<Void> sendPasswordResetEmail(@NonNull String str) {
        zzbo.zzcF(str);
        return this.zzbWb.zzb(this.zzbVZ, str);
    }

    @NonNull
    public Task<AuthResult> signInAnonymously() {
        return (this.zzbWc == null || !this.zzbWc.isAnonymous()) ? this.zzbWb.zza(this.zzbVZ, (ll) new zza()) : Tasks.forResult(new lp((ls) this.zzbWc));
    }

    @NonNull
    public Task<AuthResult> signInWithCredential(@NonNull AuthCredential authCredential) {
        zzbo.zzu(authCredential);
        if (authCredential instanceof EmailAuthCredential) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
            return this.zzbWb.zzb(this.zzbVZ, emailAuthCredential.getEmail(), emailAuthCredential.getPassword(), (ll) new zza());
        } else if (!(authCredential instanceof PhoneAuthCredential)) {
            return this.zzbWb.zza(this.zzbVZ, authCredential, (ll) new zza());
        } else {
            return this.zzbWb.zza(this.zzbVZ, (PhoneAuthCredential) authCredential, (ll) new zza());
        }
    }

    @NonNull
    public Task<AuthResult> signInWithCustomToken(@NonNull String str) {
        zzbo.zzcF(str);
        return this.zzbWb.zza(this.zzbVZ, str, (ll) new zza());
    }

    @NonNull
    public Task<AuthResult> signInWithEmailAndPassword(@NonNull String str, @NonNull String str2) {
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        return this.zzbWb.zzb(this.zzbVZ, str, str2, (ll) new zza());
    }

    public void signOut() {
        zzED();
    }

    @NonNull
    public Task<String> verifyPasswordResetCode(@NonNull String str) {
        zzbo.zzcF(str);
        return this.zzbWb.zzf(this.zzbVZ, str);
    }

    public final void zzED() {
        if (this.zzbWc != null) {
            ly lyVar = this.zzbWd;
            FirebaseUser firebaseUser = this.zzbWc;
            zzbo.zzu(firebaseUser);
            lyVar.clear(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{firebaseUser.getUid()}));
            this.zzbWc = null;
        }
        this.zzbWd.clear("com.google.firebase.auth.FIREBASE_USER");
        zza((FirebaseUser) null);
        zzb((FirebaseUser) null);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r2v1, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r5v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    @NonNull
    public final Task<Void> zza(@NonNull FirebaseUser firebaseUser, @NonNull AuthCredential authCredential) {
        zzbo.zzu(firebaseUser);
        zzbo.zzu(authCredential);
        if (!EmailAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            return authCredential instanceof PhoneAuthCredential ? this.zzbWb.zzb(this.zzbVZ, firebaseUser, (PhoneAuthCredential) authCredential, (ma) new zzb()) : this.zzbWb.zza(this.zzbVZ, firebaseUser, authCredential, (ma) new zzb());
        }
        EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
        return this.zzbWb.zza(this.zzbVZ, firebaseUser, emailAuthCredential.getEmail(), emailAuthCredential.getPassword(), (ma) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    @NonNull
    public final Task<Void> zza(@NonNull FirebaseUser firebaseUser, @NonNull PhoneAuthCredential phoneAuthCredential) {
        zzbo.zzu(firebaseUser);
        zzbo.zzu(phoneAuthCredential);
        return this.zzbWb.zza(this.zzbVZ, firebaseUser, phoneAuthCredential, (ma) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    @NonNull
    public final Task<Void> zza(@NonNull FirebaseUser firebaseUser, @NonNull UserProfileChangeRequest userProfileChangeRequest) {
        zzbo.zzu(firebaseUser);
        zzbo.zzu(userProfileChangeRequest);
        return this.zzbWb.zza(this.zzbVZ, firebaseUser, userProfileChangeRequest, (ma) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    @NonNull
    public final Task<AuthResult> zza(@NonNull FirebaseUser firebaseUser, @NonNull String str) {
        zzbo.zzcF(str);
        zzbo.zzu(firebaseUser);
        return this.zzbWb.zzc(this.zzbVZ, firebaseUser, str, (ma) new zzb());
    }

    @NonNull
    public final Task<GetTokenResult> zza(@Nullable FirebaseUser firebaseUser, boolean z) {
        if (firebaseUser == null) {
            return Tasks.forException(jz.zzK(new Status(17495)));
        }
        kx zzEG = this.zzbWc.zzEG();
        return (!zzEG.isValid() || z) ? this.zzbWb.zza(this.zzbVZ, firebaseUser, zzEG.zzES(), (ll) new zzj(this)) : Tasks.forResult(new GetTokenResult(zzEG.getAccessToken()));
    }

    public final void zza(@NonNull FirebaseUser firebaseUser, @NonNull kx kxVar, boolean z) {
        boolean z2;
        boolean z3 = false;
        zzbo.zzu(firebaseUser);
        zzbo.zzu(kxVar);
        if (this.zzbWc == null) {
            z3 = true;
            z2 = true;
        } else {
            boolean z4 = !this.zzbWc.zzEG().getAccessToken().equals(kxVar.getAccessToken());
            boolean equals = this.zzbWc.getUid().equals(firebaseUser.getUid());
            z2 = !equals || z4;
            if (!equals) {
                z3 = true;
            }
        }
        zzbo.zzu(firebaseUser);
        if (this.zzbWc == null) {
            this.zzbWc = firebaseUser;
        } else {
            this.zzbWc.zzax(firebaseUser.isAnonymous());
            this.zzbWc.zzP(firebaseUser.getProviderData());
        }
        if (z) {
            this.zzbWd.zzg(this.zzbWc);
        }
        if (z2) {
            if (this.zzbWc != null) {
                this.zzbWc.zza(kxVar);
            }
            zza(this.zzbWc);
        }
        if (z3) {
            zzb(this.zzbWc);
        }
        if (z) {
            this.zzbWd.zza(firebaseUser, kxVar);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00a5  */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(@android.support.annotation.NonNull java.lang.String r7, long r8, java.util.concurrent.TimeUnit r10, @android.support.annotation.NonNull com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks r11, @android.support.annotation.Nullable android.app.Activity r12, @android.support.annotation.NonNull java.util.concurrent.Executor r13, boolean r14) {
        /*
            r6 = this;
            com.google.firebase.FirebaseApp r0 = r6.zzbVZ
            android.content.Context r0 = r0.getApplicationContext()
            com.google.android.gms.common.internal.zzbo.zzu(r0)
            com.google.android.gms.common.internal.zzbo.zzcF(r7)
            java.lang.String r1 = "phone"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            if (r0 == 0) goto L_0x0062
            java.lang.String r0 = r0.getSimCountryIso()
        L_0x001a:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x002a
            java.util.Locale r0 = java.util.Locale.getDefault()
            if (r0 == 0) goto L_0x0064
            java.lang.String r0 = r0.getCountry()
        L_0x002a:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0066
            java.lang.String r0 = "US"
        L_0x0032:
            java.lang.String r1 = android.telephony.PhoneNumberUtils.stripSeparators(r7)
            boolean r2 = com.google.android.gms.common.util.zzq.zzse()
            if (r2 == 0) goto L_0x006d
            java.lang.String r0 = android.telephony.PhoneNumberUtils.formatNumberToE164(r1, r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x00c0
            r0 = r1
        L_0x0047:
            r1 = r0
        L_0x0048:
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS
            long r2 = r0.convert(r8, r10)
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 < 0) goto L_0x005a
            r4 = 120(0x78, double:5.93E-322)
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a5
        L_0x005a:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "We only support 0-120 seconds for sms-auto-retrieval timeout"
            r0.<init>(r1)
            throw r0
        L_0x0062:
            r0 = 0
            goto L_0x001a
        L_0x0064:
            r0 = 0
            goto L_0x002a
        L_0x0066:
            java.util.Locale r1 = java.util.Locale.US
            java.lang.String r0 = r0.toUpperCase(r1)
            goto L_0x0032
        L_0x006d:
            java.lang.String r2 = "US"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0048
            if (r1 != 0) goto L_0x0079
            r0 = 0
            goto L_0x0047
        L_0x0079:
            int r0 = r1.length()
            java.lang.String r2 = "+"
            boolean r2 = r1.startsWith(r2)
            if (r2 != 0) goto L_0x00a3
            r2 = 11
            if (r0 != r2) goto L_0x0098
            java.lang.String r2 = "1"
            boolean r2 = r1.startsWith(r2)
            if (r2 == 0) goto L_0x0098
            java.lang.String r0 = "+"
            java.lang.String r0 = r0.concat(r1)
            goto L_0x0047
        L_0x0098:
            r2 = 10
            if (r0 != r2) goto L_0x00a3
            java.lang.String r0 = "+1"
            java.lang.String r0 = r0.concat(r1)
            goto L_0x0047
        L_0x00a3:
            r0 = r1
            goto L_0x0047
        L_0x00a5:
            r4 = 30
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 >= 0) goto L_0x00be
            r2 = 30
            r4 = r2
        L_0x00ae:
            com.google.android.gms.internal.lf r2 = new com.google.android.gms.internal.lf
            r2.<init>(r1, r4, r14)
            com.google.android.gms.internal.iq r0 = r6.zzbWb
            com.google.firebase.FirebaseApp r1 = r6.zzbVZ
            r3 = r11
            r4 = r12
            r5 = r13
            r0.zza((com.google.firebase.FirebaseApp) r1, (com.google.android.gms.internal.lf) r2, (com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks) r3, (android.app.Activity) r4, (java.util.concurrent.Executor) r5)
            return
        L_0x00be:
            r4 = r2
            goto L_0x00ae
        L_0x00c0:
            r1 = r0
            goto L_0x0048
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zza(java.lang.String, long, java.util.concurrent.TimeUnit, com.google.firebase.auth.PhoneAuthProvider$OnVerificationStateChangedCallbacks, android.app.Activity, java.util.concurrent.Executor, boolean):void");
    }

    @NonNull
    public final Task<GetTokenResult> zzaw(boolean z) {
        return zza(this.zzbWc, z);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r2v1, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r5v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<AuthResult> zzb(@NonNull FirebaseUser firebaseUser, @NonNull AuthCredential authCredential) {
        zzbo.zzu(firebaseUser);
        zzbo.zzu(authCredential);
        if (!EmailAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            return authCredential instanceof PhoneAuthCredential ? this.zzbWb.zzc(this.zzbVZ, firebaseUser, authCredential, (ma) new zzb()) : this.zzbWb.zzb(this.zzbVZ, firebaseUser, authCredential, (ma) new zzb());
        }
        EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
        return this.zzbWb.zzb(this.zzbVZ, firebaseUser, emailAuthCredential.getEmail(), emailAuthCredential.getPassword(), new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    @NonNull
    public final Task<Void> zzb(@NonNull FirebaseUser firebaseUser, @NonNull String str) {
        zzbo.zzu(firebaseUser);
        zzbo.zzcF(str);
        return this.zzbWb.zza(this.zzbVZ, firebaseUser, str, (ma) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    @NonNull
    public final Task<Void> zzc(@NonNull FirebaseUser firebaseUser) {
        zzbo.zzu(firebaseUser);
        return this.zzbWb.zza(this.zzbVZ, firebaseUser, (ma) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    @NonNull
    public final Task<AuthResult> zzc(@NonNull FirebaseUser firebaseUser, @NonNull AuthCredential authCredential) {
        zzbo.zzu(authCredential);
        zzbo.zzu(firebaseUser);
        return this.zzbWb.zzd(this.zzbVZ, firebaseUser, authCredential, new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.android.gms.internal.ma, com.google.firebase.auth.FirebaseAuth$zzb] */
    @NonNull
    public final Task<Void> zzc(@NonNull FirebaseUser firebaseUser, @NonNull String str) {
        zzbo.zzu(firebaseUser);
        zzbo.zzcF(str);
        return this.zzbWb.zzb(this.zzbVZ, firebaseUser, str, (ma) new zzb());
    }

    @NonNull
    public final Task<Void> zzd(@NonNull FirebaseUser firebaseUser) {
        zzbo.zzu(firebaseUser);
        return this.zzbWb.zza(firebaseUser, (lv) new zzk(this, firebaseUser));
    }

    @NonNull
    public final Task<Void> zzgp(@NonNull String str) {
        zzbo.zzcF(str);
        return this.zzbWb.zzc(this.zzbVZ, str);
    }
}
