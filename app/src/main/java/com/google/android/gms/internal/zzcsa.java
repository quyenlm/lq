package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.HarmfulAppsData;
import com.google.android.gms.safetynet.SafeBrowsingData;
import com.google.android.gms.safetynet.SafeBrowsingThreat;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class zzcsa implements SafetyNetApi {
    private static final String TAG = zzcsa.class.getSimpleName();
    protected static SparseArray<zzcsr> zzbBM;
    protected static long zzbBN;

    static class zza implements SafetyNetApi.AttestationResult {
        private final Status mStatus;
        private final com.google.android.gms.safetynet.zza zzbBV;

        public zza(Status status, com.google.android.gms.safetynet.zza zza) {
            this.mStatus = status;
            this.zzbBV = zza;
        }

        public final String getJwsResult() {
            if (this.zzbBV == null) {
                return null;
            }
            return this.zzbBV.getJwsResult();
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static abstract class zzb extends zzcrv<SafetyNetApi.AttestationResult> {
        protected zzcrw zzbBW = new zzcsi(this);

        public zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zza(status, (com.google.android.gms.safetynet.zza) null);
        }
    }

    static abstract class zzc extends zzcrv<SafetyNetApi.VerifyAppsUserResult> {
        protected zzcrw zzbBW = new zzcsj(this);

        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zzj(status, false);
        }
    }

    static abstract class zzd extends zzcrv<SafetyNetApi.HarmfulAppsResult> {
        protected final zzcrw zzbBW = new zzcsk(this);

        public zzd(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zzg(status, (com.google.android.gms.safetynet.zzd) null);
        }
    }

    static abstract class zze extends zzcrv<SafetyNetApi.RecaptchaTokenResult> {
        protected zzcrw zzbBW = new zzcsl(this);

        public zze(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zzh(status, (com.google.android.gms.safetynet.zzf) null);
        }
    }

    static abstract class zzf extends zzcrv<SafetyNetApi.SafeBrowsingResult> {
        protected zzcrw zzbBW = new zzcsm(this);

        public zzf(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zzi(status, (SafeBrowsingData) null);
        }
    }

    static class zzg implements SafetyNetApi.HarmfulAppsResult {
        private final Status mStatus;
        private final com.google.android.gms.safetynet.zzd zzbCc;

        public zzg(Status status, com.google.android.gms.safetynet.zzd zzd) {
            this.mStatus = status;
            this.zzbCc = zzd;
        }

        public final List<HarmfulAppsData> getHarmfulAppsList() {
            return this.zzbCc == null ? Collections.emptyList() : Arrays.asList(this.zzbCc.zzbBH);
        }

        public final long getLastScanTimeMs() {
            if (this.zzbCc == null) {
                return 0;
            }
            return this.zzbCc.zzbBG;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static class zzh implements SafetyNetApi.RecaptchaTokenResult {
        private final Status mStatus;
        private final com.google.android.gms.safetynet.zzf zzbCd;

        public zzh(Status status, com.google.android.gms.safetynet.zzf zzf) {
            this.mStatus = status;
            this.zzbCd = zzf;
        }

        public final Status getStatus() {
            return this.mStatus;
        }

        public final String getTokenResult() {
            if (this.zzbCd == null) {
                return null;
            }
            return this.zzbCd.getTokenResult();
        }
    }

    static class zzi implements SafetyNetApi.SafeBrowsingResult {
        private Status mStatus;
        private String zzbBI = null;
        private final SafeBrowsingData zzbCe;

        public zzi(Status status, SafeBrowsingData safeBrowsingData) {
            this.mStatus = status;
            this.zzbCe = safeBrowsingData;
            if (this.zzbCe != null) {
                this.zzbBI = this.zzbCe.getMetadata();
            } else if (this.mStatus.isSuccess()) {
                this.mStatus = new Status(8);
            }
        }

        public final List<SafeBrowsingThreat> getDetectedThreats() {
            ArrayList arrayList = new ArrayList();
            if (this.zzbBI == null) {
                return arrayList;
            }
            try {
                JSONArray jSONArray = new JSONObject(this.zzbBI).getJSONArray("matches");
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        arrayList.add(new SafeBrowsingThreat(Integer.parseInt(jSONArray.getJSONObject(i).getString("threat_type"))));
                    } catch (NumberFormatException | JSONException e) {
                    }
                }
                return arrayList;
            } catch (JSONException e2) {
                return arrayList;
            }
        }

        public final String getMetadata() {
            return this.zzbBI;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static class zzj implements SafetyNetApi.VerifyAppsUserResult {
        private Status mStatus;
        private boolean zzzE;

        public zzj() {
        }

        public zzj(Status status, boolean z) {
            this.mStatus = status;
            this.zzzE = z;
        }

        public final Status getStatus() {
            return this.mStatus;
        }

        public final boolean isVerifyAppsEnabled() {
            if (this.mStatus == null || !this.mStatus.isSuccess()) {
                return false;
            }
            return this.zzzE;
        }
    }

    public static PendingResult<SafetyNetApi.SafeBrowsingResult> zza(GoogleApiClient googleApiClient, String str, int i, String str2, int... iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException("Null threatTypes in lookupUri");
        } else if (!TextUtils.isEmpty(str)) {
            return googleApiClient.zzd(new zzcsd(googleApiClient, iArr, i, str, str2));
        } else {
            throw new IllegalArgumentException("Null or empty uri in lookupUri");
        }
    }

    public static PendingResult<SafetyNetApi.AttestationResult> zza(GoogleApiClient googleApiClient, byte[] bArr, String str) {
        return googleApiClient.zzd(new zzcsb(googleApiClient, bArr, str));
    }

    public PendingResult<SafetyNetApi.AttestationResult> attest(GoogleApiClient googleApiClient, byte[] bArr) {
        return zza(googleApiClient, bArr, (String) null);
    }

    public PendingResult<SafetyNetApi.VerifyAppsUserResult> enableVerifyApps(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzcsf(this, googleApiClient));
    }

    public PendingResult<SafetyNetApi.VerifyAppsUserResult> isVerifyAppsEnabled(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzcse(this, googleApiClient));
    }

    public boolean isVerifyAppsEnabled(Context context) {
        GoogleApiClient build = new GoogleApiClient.Builder(context).addApi(SafetyNet.API).build();
        try {
            if (build.blockingConnect(3, TimeUnit.SECONDS).isSuccess()) {
                SafetyNetApi.VerifyAppsUserResult await = isVerifyAppsEnabled(build).await(3, TimeUnit.SECONDS);
                boolean z = await != null && await.isVerifyAppsEnabled();
            }
            if (build != null) {
                build.disconnect();
            }
            return false;
        } finally {
            if (build != null) {
                build.disconnect();
            }
        }
    }

    public PendingResult<SafetyNetApi.HarmfulAppsResult> listHarmfulApps(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzcsg(this, googleApiClient));
    }

    public PendingResult<SafetyNetApi.SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, String str, String str2, int... iArr) {
        return zza(googleApiClient, str, 1, str2, iArr);
    }

    public PendingResult<SafetyNetApi.SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, String str, int... iArr) {
        return zza(googleApiClient, str, 1, (String) null, iArr);
    }

    public PendingResult<SafetyNetApi.SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, List<Integer> list, String str) {
        return zza(googleApiClient, list, str, (String) null);
    }

    public boolean lookupUriInLocalBlacklist(String str, int... iArr) {
        if (iArr == null) {
            throw new IllegalArgumentException("Null threatTypes in lookupUri");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Null or empty uri in lookupUri");
        } else if (zzbBM == null || zzbBN == 0 || SystemClock.elapsedRealtime() - zzbBN >= 1200000) {
            return true;
        } else {
            if (zzbBM == null || zzbBM.size() == 0) {
                return true;
            }
            List<zzcsp> zzAk = new zzcss(str).zzAk();
            if (zzAk == null || zzAk.isEmpty()) {
                return true;
            }
            for (zzcsp next : zzAk) {
                int length = iArr.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        zzcsr zzcsr = zzbBM.get(iArr[i]);
                        if (zzcsr == null) {
                            return true;
                        }
                        if (zzcsr.zzr(next.zzbu(4).getBytes())) {
                            return true;
                        }
                        i++;
                    }
                }
            }
            return false;
        }
    }

    public PendingResult<SafetyNetApi.RecaptchaTokenResult> verifyWithRecaptcha(GoogleApiClient googleApiClient, String str) {
        if (!TextUtils.isEmpty(str)) {
            return googleApiClient.zzd(new zzcsh(this, googleApiClient, str));
        }
        throw new IllegalArgumentException("Null or empty site key in verifyWithRecaptcha");
    }

    public final PendingResult<SafetyNetApi.SafeBrowsingResult> zza(GoogleApiClient googleApiClient, List<Integer> list, String str, String str2) {
        if (list == null) {
            throw new IllegalArgumentException("Null threatTypes in lookupUri");
        } else if (!TextUtils.isEmpty(str)) {
            return googleApiClient.zzd(new zzcsc(this, googleApiClient, list, str, str2));
        } else {
            throw new IllegalArgumentException("Null or empty uri in lookupUri");
        }
    }
}
