package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.safeparcel.zze;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ActivityRecognitionResult extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<ActivityRecognitionResult> CREATOR = new zzb();
    private Bundle extras;
    private List<DetectedActivity> zzbhu;
    private long zzbhv;
    private long zzbhw;
    private int zzbhx;

    public ActivityRecognitionResult(DetectedActivity detectedActivity, long j, long j2) {
        this(detectedActivity, j, j2, 0, (Bundle) null);
    }

    private ActivityRecognitionResult(DetectedActivity detectedActivity, long j, long j2, int i, Bundle bundle) {
        this((List<DetectedActivity>) Collections.singletonList(detectedActivity), j, j2, 0, (Bundle) null);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long j, long j2) {
        this(list, j, j2, 0, (Bundle) null);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long j, long j2, int i, Bundle bundle) {
        boolean z = true;
        zzbo.zzb(list != null && list.size() > 0, (Object) "Must have at least 1 detected activity");
        zzbo.zzb((j <= 0 || j2 <= 0) ? false : z, (Object) "Must set times");
        this.zzbhu = list;
        this.zzbhv = j;
        this.zzbhw = j2;
        this.zzbhx = i;
        this.extras = bundle;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.location.ActivityRecognitionResult extractResult(android.content.Intent r3) {
        /*
            r1 = 0
            boolean r0 = hasResult(r3)
            if (r0 == 0) goto L_0x0029
            android.os.Bundle r0 = r3.getExtras()
            java.lang.String r2 = "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT"
            java.lang.Object r0 = r0.get(r2)
            boolean r2 = r0 instanceof byte[]
            if (r2 == 0) goto L_0x0022
            byte[] r0 = (byte[]) r0
            android.os.Parcelable$Creator<com.google.android.gms.location.ActivityRecognitionResult> r2 = CREATOR
            com.google.android.gms.common.internal.safeparcel.SafeParcelable r0 = com.google.android.gms.common.internal.safeparcel.zze.zza(r0, r2)
            com.google.android.gms.location.ActivityRecognitionResult r0 = (com.google.android.gms.location.ActivityRecognitionResult) r0
        L_0x001f:
            if (r0 == 0) goto L_0x002b
        L_0x0021:
            return r0
        L_0x0022:
            boolean r2 = r0 instanceof com.google.android.gms.location.ActivityRecognitionResult
            if (r2 == 0) goto L_0x0029
            com.google.android.gms.location.ActivityRecognitionResult r0 = (com.google.android.gms.location.ActivityRecognitionResult) r0
            goto L_0x001f
        L_0x0029:
            r0 = r1
            goto L_0x001f
        L_0x002b:
            java.util.List r0 = zzj(r3)
            if (r0 == 0) goto L_0x0037
            boolean r2 = r0.isEmpty()
            if (r2 == 0) goto L_0x0039
        L_0x0037:
            r0 = r1
            goto L_0x0021
        L_0x0039:
            int r1 = r0.size()
            int r1 = r1 + -1
            java.lang.Object r0 = r0.get(r1)
            com.google.android.gms.location.ActivityRecognitionResult r0 = (com.google.android.gms.location.ActivityRecognitionResult) r0
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.ActivityRecognitionResult.extractResult(android.content.Intent):com.google.android.gms.location.ActivityRecognitionResult");
    }

    public static boolean hasResult(Intent intent) {
        if (intent == null) {
            return false;
        }
        if (intent == null ? false : intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT")) {
            return true;
        }
        List<ActivityRecognitionResult> zzj = zzj(intent);
        return zzj != null && !zzj.isEmpty();
    }

    private static boolean zzc(Bundle bundle, Bundle bundle2) {
        if (bundle == null && bundle2 == null) {
            return true;
        }
        if ((bundle == null && bundle2 != null) || (bundle != null && bundle2 == null)) {
            return false;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            if (!bundle2.containsKey(str)) {
                return false;
            }
            if (bundle.get(str) == null) {
                if (bundle2.get(str) != null) {
                    return false;
                }
            } else if (bundle.get(str) instanceof Bundle) {
                if (!zzc(bundle.getBundle(str), bundle2.getBundle(str))) {
                    return false;
                }
            } else if (!bundle.get(str).equals(bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    private static List<ActivityRecognitionResult> zzj(Intent intent) {
        int i = 0;
        if (!(intent == null ? false : intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST"))) {
            return null;
        }
        Parcelable.Creator creator = CREATOR;
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST");
        if (arrayList == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        ArrayList arrayList3 = arrayList;
        int size = arrayList3.size();
        while (i < size) {
            Object obj = arrayList3.get(i);
            i++;
            arrayList2.add(zze.zza((byte[]) obj, creator));
        }
        return arrayList2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ActivityRecognitionResult activityRecognitionResult = (ActivityRecognitionResult) obj;
        return this.zzbhv == activityRecognitionResult.zzbhv && this.zzbhw == activityRecognitionResult.zzbhw && this.zzbhx == activityRecognitionResult.zzbhx && zzbe.equal(this.zzbhu, activityRecognitionResult.zzbhu) && zzc(this.extras, activityRecognitionResult.extras);
    }

    public int getActivityConfidence(int i) {
        for (DetectedActivity next : this.zzbhu) {
            if (next.getType() == i) {
                return next.getConfidence();
            }
        }
        return 0;
    }

    public long getElapsedRealtimeMillis() {
        return this.zzbhw;
    }

    public DetectedActivity getMostProbableActivity() {
        return this.zzbhu.get(0);
    }

    public List<DetectedActivity> getProbableActivities() {
        return this.zzbhu;
    }

    public long getTime() {
        return this.zzbhv;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzbhv), Long.valueOf(this.zzbhw), Integer.valueOf(this.zzbhx), this.zzbhu, this.extras});
    }

    public String toString() {
        String valueOf = String.valueOf(this.zzbhu);
        long j = this.zzbhv;
        return new StringBuilder(String.valueOf(valueOf).length() + 124).append("ActivityRecognitionResult [probableActivities=").append(valueOf).append(", timeMillis=").append(j).append(", elapsedRealtimeMillis=").append(this.zzbhw).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbhu, false);
        zzd.zza(parcel, 2, this.zzbhv);
        zzd.zza(parcel, 3, this.zzbhw);
        zzd.zzc(parcel, 4, this.zzbhx);
        zzd.zza(parcel, 5, this.extras, false);
        zzd.zzI(parcel, zze);
    }
}
