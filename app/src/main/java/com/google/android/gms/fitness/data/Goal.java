package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.vk.sdk.api.VKApiConst;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Goal extends zza {
    public static final Parcelable.Creator<Goal> CREATOR = new zzs();
    public static final int OBJECTIVE_TYPE_DURATION = 2;
    public static final int OBJECTIVE_TYPE_FREQUENCY = 3;
    public static final int OBJECTIVE_TYPE_METRIC = 1;
    private final int versionCode;
    private final long zzaUH;
    private final long zzaUI;
    private final List<Integer> zzaUJ;
    private final Recurrence zzaUK;
    private final int zzaUL;
    private final MetricObjective zzaUM;
    private final DurationObjective zzaUN;
    private final FrequencyObjective zzaUO;

    public static class DurationObjective extends zza {
        public static final Parcelable.Creator<DurationObjective> CREATOR = new zzp();
        private final int versionCode;
        private final long zzaUP;

        DurationObjective(int i, long j) {
            this.versionCode = i;
            this.zzaUP = j;
        }

        public DurationObjective(long j, TimeUnit timeUnit) {
            this(1, timeUnit.toNanos(j));
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (!(obj instanceof DurationObjective)) {
                    return false;
                }
                if (!(this.zzaUP == ((DurationObjective) obj).zzaUP)) {
                    return false;
                }
            }
            return true;
        }

        public long getDuration(TimeUnit timeUnit) {
            return timeUnit.convert(this.zzaUP, TimeUnit.NANOSECONDS);
        }

        public int hashCode() {
            return (int) this.zzaUP;
        }

        public String toString() {
            return zzbe.zzt(this).zzg("duration", Long.valueOf(this.zzaUP)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzd.zze(parcel);
            zzd.zza(parcel, 1, this.zzaUP);
            zzd.zzc(parcel, 1000, this.versionCode);
            zzd.zzI(parcel, zze);
        }
    }

    public static class FrequencyObjective extends zza {
        public static final Parcelable.Creator<FrequencyObjective> CREATOR = new zzr();
        private final int frequency;
        private final int versionCode;

        public FrequencyObjective(int i) {
            this(1, i);
        }

        FrequencyObjective(int i, int i2) {
            this.versionCode = i;
            this.frequency = i2;
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof FrequencyObjective) && this.frequency == ((FrequencyObjective) obj).frequency);
        }

        public int getFrequency() {
            return this.frequency;
        }

        public int hashCode() {
            return this.frequency;
        }

        public String toString() {
            return zzbe.zzt(this).zzg("frequency", Integer.valueOf(this.frequency)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzd.zze(parcel);
            zzd.zzc(parcel, 1, getFrequency());
            zzd.zzc(parcel, 1000, this.versionCode);
            zzd.zzI(parcel, zze);
        }
    }

    public static class MetricObjective extends zza {
        public static final Parcelable.Creator<MetricObjective> CREATOR = new zzx();
        private final double value;
        private final int versionCode;
        private final String zzaUQ;
        private final double zzaUR;

        MetricObjective(int i, String str, double d, double d2) {
            this.versionCode = i;
            this.zzaUQ = str;
            this.value = d;
            this.zzaUR = d2;
        }

        public MetricObjective(String str, double d) {
            this(1, str, d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (!(obj instanceof MetricObjective)) {
                    return false;
                }
                MetricObjective metricObjective = (MetricObjective) obj;
                if (!(zzbe.equal(this.zzaUQ, metricObjective.zzaUQ) && this.value == metricObjective.value && this.zzaUR == metricObjective.zzaUR)) {
                    return false;
                }
            }
            return true;
        }

        public String getDataTypeName() {
            return this.zzaUQ;
        }

        public double getValue() {
            return this.value;
        }

        public int hashCode() {
            return this.zzaUQ.hashCode();
        }

        public String toString() {
            return zzbe.zzt(this).zzg("dataTypeName", this.zzaUQ).zzg("value", Double.valueOf(this.value)).zzg("initialValue", Double.valueOf(this.zzaUR)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzd.zze(parcel);
            zzd.zza(parcel, 1, getDataTypeName(), false);
            zzd.zza(parcel, 2, getValue());
            zzd.zza(parcel, 3, this.zzaUR);
            zzd.zzc(parcel, 1000, this.versionCode);
            zzd.zzI(parcel, zze);
        }
    }

    public static class MismatchedGoalException extends IllegalStateException {
        public MismatchedGoalException(String str) {
            super(str);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ObjectiveType {
    }

    public static class Recurrence extends zza {
        public static final Parcelable.Creator<Recurrence> CREATOR = new zzab();
        public static final int UNIT_DAY = 1;
        public static final int UNIT_MONTH = 3;
        public static final int UNIT_WEEK = 2;
        private final int count;
        private final int versionCode;
        /* access modifiers changed from: private */
        public final int zzaUS;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RecurrenceUnit {
        }

        public Recurrence(int i, int i2) {
            this(1, i, i2);
        }

        Recurrence(int i, int i2, int i3) {
            this.versionCode = i;
            this.count = i2;
            zzbo.zzae(i3 > 0 && i3 <= 3);
            this.zzaUS = i3;
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (!(obj instanceof Recurrence)) {
                    return false;
                }
                Recurrence recurrence = (Recurrence) obj;
                if (!(this.count == recurrence.count && this.zzaUS == recurrence.zzaUS)) {
                    return false;
                }
            }
            return true;
        }

        public int getCount() {
            return this.count;
        }

        public int getUnit() {
            return this.zzaUS;
        }

        public int hashCode() {
            return this.zzaUS;
        }

        public String toString() {
            String str;
            zzbg zzg = zzbe.zzt(this).zzg(VKApiConst.COUNT, Integer.valueOf(this.count));
            switch (this.zzaUS) {
                case 1:
                    str = "day";
                    break;
                case 2:
                    str = "week";
                    break;
                case 3:
                    str = "month";
                    break;
                default:
                    throw new IllegalArgumentException("invalid unit value");
            }
            return zzg.zzg("unit", str).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzd.zze(parcel);
            zzd.zzc(parcel, 1, getCount());
            zzd.zzc(parcel, 2, getUnit());
            zzd.zzc(parcel, 1000, this.versionCode);
            zzd.zzI(parcel, zze);
        }
    }

    Goal(int i, long j, long j2, List<Integer> list, Recurrence recurrence, int i2, MetricObjective metricObjective, DurationObjective durationObjective, FrequencyObjective frequencyObjective) {
        this.versionCode = i;
        this.zzaUH = j;
        this.zzaUI = j2;
        this.zzaUJ = list;
        this.zzaUK = recurrence;
        this.zzaUL = i2;
        this.zzaUM = metricObjective;
        this.zzaUN = durationObjective;
        this.zzaUO = frequencyObjective;
    }

    private static String zzaW(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 1:
                return "metric";
            case 2:
                return "duration";
            case 3:
                return "frequency";
            default:
                throw new IllegalArgumentException("invalid objective type value");
        }
    }

    private final void zzaX(int i) throws MismatchedGoalException {
        if (i != this.zzaUL) {
            throw new MismatchedGoalException(String.format("%s goal does not have %s objective", new Object[]{zzaW(this.zzaUL), zzaW(i)}));
        }
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Goal)) {
                return false;
            }
            Goal goal = (Goal) obj;
            if (!(this.zzaUH == goal.zzaUH && this.zzaUI == goal.zzaUI && zzbe.equal(this.zzaUJ, goal.zzaUJ) && zzbe.equal(this.zzaUK, goal.zzaUK) && this.zzaUL == goal.zzaUL && zzbe.equal(this.zzaUM, goal.zzaUM) && zzbe.equal(this.zzaUN, goal.zzaUN) && zzbe.equal(this.zzaUO, goal.zzaUO))) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public String getActivityName() {
        if (this.zzaUJ.isEmpty() || this.zzaUJ.size() > 1) {
            return null;
        }
        return com.google.android.gms.fitness.zza.getName(this.zzaUJ.get(0).intValue());
    }

    public long getCreateTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaUH, TimeUnit.NANOSECONDS);
    }

    public DurationObjective getDurationObjective() {
        zzaX(2);
        return this.zzaUN;
    }

    public long getEndTime(Calendar calendar, TimeUnit timeUnit) {
        if (this.zzaUK == null) {
            return timeUnit.convert(this.zzaUI, TimeUnit.NANOSECONDS);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        switch (this.zzaUK.zzaUS) {
            case 1:
                instance.add(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 2:
                instance.add(4, 1);
                instance.set(7, 2);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 3:
                instance.add(2, 1);
                instance.set(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            default:
                throw new IllegalArgumentException(new StringBuilder(24).append("Invalid unit ").append(this.zzaUK.zzaUS).toString());
        }
    }

    public FrequencyObjective getFrequencyObjective() {
        zzaX(3);
        return this.zzaUO;
    }

    public MetricObjective getMetricObjective() {
        zzaX(1);
        return this.zzaUM;
    }

    public int getObjectiveType() {
        return this.zzaUL;
    }

    public Recurrence getRecurrence() {
        return this.zzaUK;
    }

    public long getStartTime(Calendar calendar, TimeUnit timeUnit) {
        if (this.zzaUK == null) {
            return timeUnit.convert(this.zzaUH, TimeUnit.NANOSECONDS);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        switch (this.zzaUK.zzaUS) {
            case 1:
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 2:
                instance.set(7, 2);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 3:
                instance.set(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            default:
                throw new IllegalArgumentException(new StringBuilder(24).append("Invalid unit ").append(this.zzaUK.zzaUS).toString());
        }
    }

    public int hashCode() {
        return this.zzaUL;
    }

    public String toString() {
        return zzbe.zzt(this).zzg("activity", getActivityName()).zzg("recurrence", this.zzaUK).zzg("metricObjective", this.zzaUM).zzg("durationObjective", this.zzaUN).zzg("frequencyObjective", this.zzaUO).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzaUH);
        zzd.zza(parcel, 2, this.zzaUI);
        zzd.zzd(parcel, 3, this.zzaUJ, false);
        zzd.zza(parcel, 4, (Parcelable) getRecurrence(), i, false);
        zzd.zzc(parcel, 5, getObjectiveType());
        zzd.zza(parcel, 6, (Parcelable) this.zzaUM, i, false);
        zzd.zza(parcel, 7, (Parcelable) this.zzaUN, i, false);
        zzd.zzc(parcel, 1000, this.versionCode);
        zzd.zza(parcel, 8, (Parcelable) this.zzaUO, i, false);
        zzd.zzI(parcel, zze);
    }
}
