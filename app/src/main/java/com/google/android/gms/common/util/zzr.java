package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class zzr {
    private static String zzaJW = null;
    private static final int zzaJX = Process.myPid();

    private static String zzaD(int i) {
        BufferedReader bufferedReader;
        Throwable th;
        BufferedReader bufferedReader2;
        StrictMode.ThreadPolicy allowThreadDiskReads;
        String str = null;
        if (i > 0) {
            try {
                allowThreadDiskReads = StrictMode.allowThreadDiskReads();
                bufferedReader2 = new BufferedReader(new FileReader(new StringBuilder(25).append("/proc/").append(i).append("/cmdline").toString()));
                try {
                    StrictMode.setThreadPolicy(allowThreadDiskReads);
                    str = bufferedReader2.readLine().trim();
                    zzn.closeQuietly(bufferedReader2);
                } catch (IOException e) {
                    zzn.closeQuietly(bufferedReader2);
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    zzn.closeQuietly(bufferedReader);
                    throw th;
                }
            } catch (IOException e2) {
                bufferedReader2 = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
                zzn.closeQuietly(bufferedReader);
                throw th;
            }
        }
        return str;
    }

    public static String zzsf() {
        if (zzaJW == null) {
            zzaJW = zzaD(zzaJX);
        }
        return zzaJW;
    }
}
