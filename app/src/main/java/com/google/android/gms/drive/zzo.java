package com.google.android.gms.drive;

import com.google.android.gms.drive.ExecutionOptions;

public final class zzo extends ExecutionOptions.Builder {
    public final /* synthetic */ ExecutionOptions build() {
        zzsS();
        return new zzm(this.zzaMr, this.zzaMs, (String) null, (String) null, this.zzaMt, (zzn) null);
    }

    public final /* synthetic */ ExecutionOptions.Builder setConflictStrategy(int i) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ ExecutionOptions.Builder setNotifyOnCompletion(boolean z) {
        super.setNotifyOnCompletion(z);
        return this;
    }

    public final /* synthetic */ ExecutionOptions.Builder setTrackingTag(String str) {
        super.setTrackingTag(str);
        return this;
    }
}
