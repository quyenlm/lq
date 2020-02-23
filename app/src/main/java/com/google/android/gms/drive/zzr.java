package com.google.android.gms.drive;

import com.google.android.gms.drive.ExecutionOptions;

public final class zzr extends ExecutionOptions.Builder {
    private boolean zzaMw = true;

    public final /* synthetic */ ExecutionOptions build() {
        zzsS();
        return new zzp(this.zzaMr, this.zzaMs, this.zzaMt, this.zzaMw);
    }

    public final /* synthetic */ ExecutionOptions.Builder setConflictStrategy(int i) {
        super.setConflictStrategy(i);
        return this;
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
