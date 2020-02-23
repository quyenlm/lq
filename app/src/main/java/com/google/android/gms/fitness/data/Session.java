package com.google.android.gms.fitness.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.safeparcel.zze;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Session extends zza {
    public static final Parcelable.Creator<Session> CREATOR = new zzad();
    public static final String EXTRA_SESSION = "vnd.google.fitness.session";
    public static final String MIME_TYPE_PREFIX = "vnd.google.fitness.session/";
    private final String mName;
    private final long zzaTo;
    private final int zzaTp;
    private final String zzaVf;
    private final zzb zzaVg;
    private final Long zzaVh;
    private final String zzafa;
    private final long zzagZ;
    private final int zzaku;

    public static class Builder {
        /* access modifiers changed from: private */
        public String mName = null;
        /* access modifiers changed from: private */
        public long zzaTo = 0;
        /* access modifiers changed from: private */
        public int zzaTp = 4;
        /* access modifiers changed from: private */
        public String zzaVf;
        /* access modifiers changed from: private */
        public Long zzaVh;
        /* access modifiers changed from: private */
        public String zzafa;
        /* access modifiers changed from: private */
        public long zzagZ = 0;

        public Session build() {
            boolean z = false;
            zzbo.zza(this.zzagZ > 0, (Object) "Start time should be specified.");
            if (this.zzaTo == 0 || this.zzaTo > this.zzagZ) {
                z = true;
            }
            zzbo.zza(z, (Object) "End time should be later than start time.");
            if (this.zzaVf == null) {
                String str = this.mName == null ? "" : this.mName;
                this.zzaVf = new StringBuilder(String.valueOf(str).length() + 20).append(str).append(this.zzagZ).toString();
            }
            if (this.zzafa == null) {
                this.zzafa = "";
            }
            return new Session(this);
        }

        public Builder setActiveTime(long j, TimeUnit timeUnit) {
            this.zzaVh = Long.valueOf(timeUnit.toMillis(j));
            return this;
        }

        public Builder setActivity(String str) {
            this.zzaTp = com.google.android.gms.fitness.zza.zzcW(str);
            return this;
        }

        public Builder setDescription(String str) {
            zzbo.zzb(str.length() <= 1000, "Session description cannot exceed %d characters", 1000);
            this.zzafa = str;
            return this;
        }

        public Builder setEndTime(long j, TimeUnit timeUnit) {
            zzbo.zza(j >= 0, (Object) "End time should be positive.");
            this.zzaTo = timeUnit.toMillis(j);
            return this;
        }

        public Builder setIdentifier(String str) {
            zzbo.zzaf(str != null && TextUtils.getTrimmedLength(str) > 0);
            this.zzaVf = str;
            return this;
        }

        public Builder setName(String str) {
            zzbo.zzb(str.length() <= 100, "Session name cannot exceed %d characters", 100);
            this.mName = str;
            return this;
        }

        public Builder setStartTime(long j, TimeUnit timeUnit) {
            zzbo.zza(j > 0, (Object) "Start time should be positive.");
            this.zzagZ = timeUnit.toMillis(j);
            return this;
        }
    }

    Session(int i, long j, long j2, String str, String str2, String str3, int i2, zzb zzb, Long l) {
        this.zzaku = i;
        this.zzagZ = j;
        this.zzaTo = j2;
        this.mName = str;
        this.zzaVf = str2;
        this.zzafa = str3;
        this.zzaTp = i2;
        this.zzaVg = zzb;
        this.zzaVh = l;
    }

    private Session(long j, long j2, String str, String str2, String str3, int i, zzb zzb, Long l) {
        this(3, j, j2, str, str2, str3, i, (zzb) null, l);
    }

    private Session(Builder builder) {
        this(builder.zzagZ, builder.zzaTo, builder.mName, builder.zzaVf, builder.zzafa, builder.zzaTp, (zzb) null, builder.zzaVh);
    }

    public static Session extract(Intent intent) {
        if (intent == null) {
            return null;
        }
        return (Session) zze.zza(intent, EXTRA_SESSION, CREATOR);
    }

    public static String getMimeType(String str) {
        String valueOf = String.valueOf(MIME_TYPE_PREFIX);
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof Session)) {
                return false;
            }
            Session session = (Session) obj;
            if (!(this.zzagZ == session.zzagZ && this.zzaTo == session.zzaTo && zzbe.equal(this.mName, session.mName) && zzbe.equal(this.zzaVf, session.zzaVf) && zzbe.equal(this.zzafa, session.zzafa) && zzbe.equal(this.zzaVg, session.zzaVg) && this.zzaTp == session.zzaTp)) {
                return false;
            }
        }
        return true;
    }

    public long getActiveTime(TimeUnit timeUnit) {
        zzbo.zza(this.zzaVh != null, (Object) "Active time is not set");
        return timeUnit.convert(this.zzaVh.longValue(), TimeUnit.MILLISECONDS);
    }

    public String getActivity() {
        return com.google.android.gms.fitness.zza.getName(this.zzaTp);
    }

    public String getAppPackageName() {
        if (this.zzaVg == null) {
            return null;
        }
        return this.zzaVg.getPackageName();
    }

    public String getDescription() {
        return this.zzafa;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaTo, TimeUnit.MILLISECONDS);
    }

    public String getIdentifier() {
        return this.zzaVf;
    }

    public String getName() {
        return this.mName;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzagZ, TimeUnit.MILLISECONDS);
    }

    public boolean hasActiveTime() {
        return this.zzaVh != null;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzagZ), Long.valueOf(this.zzaTo), this.zzaVf});
    }

    public boolean isOngoing() {
        return this.zzaTo == 0;
    }

    public String toString() {
        return zzbe.zzt(this).zzg("startTime", Long.valueOf(this.zzagZ)).zzg(HttpRequestParams.NOTICE_END_TIME, Long.valueOf(this.zzaTo)).zzg("name", this.mName).zzg("identifier", this.zzaVf).zzg("description", this.zzafa).zzg("activity", Integer.valueOf(this.zzaTp)).zzg(ContentType.TYPE_APPLICATION, this.zzaVg).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzagZ);
        zzd.zza(parcel, 2, this.zzaTo);
        zzd.zza(parcel, 3, getName(), false);
        zzd.zza(parcel, 4, getIdentifier(), false);
        zzd.zza(parcel, 5, getDescription(), false);
        zzd.zzc(parcel, 7, this.zzaTp);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zza(parcel, 8, (Parcelable) this.zzaVg, i, false);
        zzd.zza(parcel, 9, this.zzaVh, false);
        zzd.zzI(parcel, zze);
    }
}
