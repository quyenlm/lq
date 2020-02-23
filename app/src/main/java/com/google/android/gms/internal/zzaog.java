package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.amazonaws.regions.ServiceAbbreviations;
import com.google.android.gms.analytics.zzl;

public final class zzaog extends zzamh {
    private final zzaoi zzaiA = new zzaoi(this, ServiceAbbreviations.CloudWatch, zzans.zzahT.get().longValue());
    /* access modifiers changed from: private */
    public SharedPreferences zzaix;
    private long zzaiy;
    private long zzaiz = -1;

    protected zzaog(zzamj zzamj) {
        super(zzamj);
    }

    public final void zzbz(String str) {
        zzl.zzjC();
        zzkD();
        SharedPreferences.Editor edit = this.zzaix.edit();
        if (TextUtils.isEmpty(str)) {
            edit.remove("installation_campaign");
        } else {
            edit.putString("installation_campaign", str);
        }
        if (!edit.commit()) {
            zzbr("Failed to commit campaign data");
        }
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        this.zzaix = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    public final long zzlU() {
        zzl.zzjC();
        zzkD();
        if (this.zzaiy == 0) {
            long j = this.zzaix.getLong("first_run", 0);
            if (j != 0) {
                this.zzaiy = j;
            } else {
                long currentTimeMillis = zzkq().currentTimeMillis();
                SharedPreferences.Editor edit = this.zzaix.edit();
                edit.putLong("first_run", currentTimeMillis);
                if (!edit.commit()) {
                    zzbr("Failed to commit first run time");
                }
                this.zzaiy = currentTimeMillis;
            }
        }
        return this.zzaiy;
    }

    public final zzaoo zzlV() {
        return new zzaoo(zzkq(), zzlU());
    }

    public final long zzlW() {
        zzl.zzjC();
        zzkD();
        if (this.zzaiz == -1) {
            this.zzaiz = this.zzaix.getLong("last_dispatch", 0);
        }
        return this.zzaiz;
    }

    public final void zzlX() {
        zzl.zzjC();
        zzkD();
        long currentTimeMillis = zzkq().currentTimeMillis();
        SharedPreferences.Editor edit = this.zzaix.edit();
        edit.putLong("last_dispatch", currentTimeMillis);
        edit.apply();
        this.zzaiz = currentTimeMillis;
    }

    public final String zzlY() {
        zzl.zzjC();
        zzkD();
        String string = this.zzaix.getString("installation_campaign", (String) null);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    public final zzaoi zzlZ() {
        return this.zzaiA;
    }
}
