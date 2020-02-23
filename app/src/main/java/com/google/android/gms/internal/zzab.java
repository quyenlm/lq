package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class zzab {
    public static boolean DEBUG = Log.isLoggable("Volley", 2);
    private static String TAG = "Volley";

    static class zza {
        public static final boolean zzai = zzab.DEBUG;
        private final List<zzac> zzaj = new ArrayList();
        private boolean zzak = false;

        zza() {
        }

        /* access modifiers changed from: protected */
        public final void finalize() throws Throwable {
            if (!this.zzak) {
                zzc("Request on the loose");
                zzab.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        public final synchronized void zza(String str, long j) {
            if (this.zzak) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.zzaj.add(new zzac(str, j, SystemClock.elapsedRealtime()));
        }

        public final synchronized void zzc(String str) {
            long j;
            this.zzak = true;
            if (this.zzaj.size() == 0) {
                j = 0;
            } else {
                j = this.zzaj.get(this.zzaj.size() - 1).time - this.zzaj.get(0).time;
            }
            if (j > 0) {
                long j2 = this.zzaj.get(0).time;
                zzab.zzb("(%-4d ms) %s", Long.valueOf(j), str);
                long j3 = j2;
                for (zzac next : this.zzaj) {
                    long j4 = next.time;
                    zzab.zzb("(+%-4d) [%2d] %s", Long.valueOf(j4 - j3), Long.valueOf(next.zzal), next.name);
                    j3 = j4;
                }
            }
        }
    }

    public static void zza(String str, Object... objArr) {
        if (DEBUG) {
            Log.v(TAG, zzd(str, objArr));
        }
    }

    public static void zza(Throwable th, String str, Object... objArr) {
        Log.e(TAG, zzd(str, objArr), th);
    }

    public static void zzb(String str, Object... objArr) {
        Log.d(TAG, zzd(str, objArr));
    }

    public static void zzc(String str, Object... objArr) {
        Log.e(TAG, zzd(str, objArr));
    }

    private static String zzd(String str, Object... objArr) {
        String str2;
        if (objArr != null) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        int i = 2;
        while (true) {
            if (i >= stackTrace.length) {
                str2 = "<unknown>";
                break;
            } else if (!stackTrace[i].getClass().equals(zzab.class)) {
                String className = stackTrace[i].getClassName();
                String substring = className.substring(className.lastIndexOf(46) + 1);
                String substring2 = substring.substring(substring.lastIndexOf(36) + 1);
                String valueOf = String.valueOf(stackTrace[i].getMethodName());
                str2 = new StringBuilder(String.valueOf(substring2).length() + 1 + String.valueOf(valueOf).length()).append(substring2).append(".").append(valueOf).toString();
                break;
            } else {
                i++;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), str2, str});
    }
}
