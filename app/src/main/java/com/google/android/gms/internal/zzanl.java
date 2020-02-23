package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

final class zzanl implements Logger {
    private boolean zzadH;
    private int zzagX = 2;

    zzanl() {
    }

    public final void error(Exception exc) {
    }

    public final void error(String str) {
    }

    public final int getLogLevel() {
        return this.zzagX;
    }

    public final void info(String str) {
    }

    public final void setLogLevel(int i) {
        this.zzagX = i;
        if (!this.zzadH) {
            String str = zzans.zzahg.get();
            Log.i(zzans.zzahg.get(), new StringBuilder(String.valueOf(str).length() + 91).append("Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.").append(str).append(" DEBUG").toString());
            this.zzadH = true;
        }
    }

    public final void verbose(String str) {
    }

    public final void warn(String str) {
    }
}
