package com.google.android.gms.tagmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;
import com.banalytics.BATrackerConst;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.Random;

public final class zzai {
    private final Context mContext;
    private final Random zzAO;
    private final String zzbDw;

    public zzai(Context context, String str) {
        this(context, str, new Random());
    }

    @VisibleForTesting
    private zzai(Context context, String str, Random random) {
        this.mContext = (Context) zzbo.zzu(context);
        this.zzbDw = (String) zzbo.zzu(str);
        this.zzAO = random;
    }

    private final SharedPreferences zzAW() {
        Context context = this.mContext;
        String valueOf = String.valueOf("_gtmContainerRefreshPolicy_");
        String valueOf2 = String.valueOf(this.zzbDw);
        return context.getSharedPreferences(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), 0);
    }

    private final long zzg(long j, long j2) {
        SharedPreferences zzAW = zzAW();
        long max = Math.max(0, zzAW.getLong("FORBIDDEN_COUNT", 0));
        return (long) (((float) (((long) ((((float) max) / ((float) ((Math.max(0, zzAW.getLong("SUCCESSFUL_COUNT", 0)) + max) + 1))) * ((float) (j2 - j)))) + j)) * this.zzAO.nextFloat());
    }

    public final long zzAS() {
        return 43200000 + zzg(7200000, 259200000);
    }

    public final long zzAT() {
        return 3600000 + zzg(BATrackerConst.TRACKER_WAKE_UP_INTERVAL, TimeUtils.MILLIS_IN_DAY);
    }

    @SuppressLint({"CommitPrefEdits"})
    public final void zzAU() {
        SharedPreferences zzAW = zzAW();
        long j = zzAW.getLong("FORBIDDEN_COUNT", 0);
        long j2 = zzAW.getLong("SUCCESSFUL_COUNT", 0);
        SharedPreferences.Editor edit = zzAW.edit();
        long min = j == 0 ? 3 : Math.min(10, 1 + j);
        long max = Math.max(0, Math.min(j2, 10 - min));
        edit.putLong("FORBIDDEN_COUNT", min);
        edit.putLong("SUCCESSFUL_COUNT", max);
        edit.apply();
    }

    @SuppressLint({"CommitPrefEdits"})
    public final void zzAV() {
        SharedPreferences zzAW = zzAW();
        long j = zzAW.getLong("SUCCESSFUL_COUNT", 0);
        long j2 = zzAW.getLong("FORBIDDEN_COUNT", 0);
        long min = Math.min(10, j + 1);
        long max = Math.max(0, Math.min(j2, 10 - min));
        SharedPreferences.Editor edit = zzAW.edit();
        edit.putLong("SUCCESSFUL_COUNT", min);
        edit.putLong("FORBIDDEN_COUNT", max);
        edit.apply();
    }
}
