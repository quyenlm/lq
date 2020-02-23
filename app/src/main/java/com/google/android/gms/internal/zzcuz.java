package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

final class zzcuz extends Thread implements zzcuy {
    private static zzcuz zzbIl;
    private volatile boolean mClosed = false;
    /* access modifiers changed from: private */
    public final Context mContext;
    private volatile boolean zzOZ = false;
    private final LinkedBlockingQueue<Runnable> zzbER = new LinkedBlockingQueue<>();
    /* access modifiers changed from: private */
    public volatile zzcvb zzbIm;
    private final zze zzvw = zzi.zzrY();

    private zzcuz(Context context) {
        super("GAThread");
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static zzcuz zzbw(Context context) {
        if (zzbIl == null) {
            zzbIl = new zzcuz(context);
        }
        return zzbIl;
    }

    public final void run() {
        while (true) {
            boolean z = this.mClosed;
            try {
                Runnable take = this.zzbER.take();
                if (!this.zzOZ) {
                    take.run();
                }
            } catch (InterruptedException e) {
                zzcvk.zzaS(e.toString());
            } catch (Throwable th) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream);
                th.printStackTrace(printStream);
                printStream.flush();
                String valueOf = String.valueOf(new String(byteArrayOutputStream.toByteArray()));
                zzcvk.e(valueOf.length() != 0 ? "Error on Google TagManager Thread: ".concat(valueOf) : new String("Error on Google TagManager Thread: "));
                zzcvk.e("Google TagManager is shutting down.");
                this.zzOZ = true;
            }
        }
    }

    public final void zzb(String str, @Nullable String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable String str4) {
        zzn(new zzcva(this, this, this.zzvw.currentTimeMillis(), str, str2, str3, map, str4));
    }

    public final void zzn(Runnable runnable) {
        this.zzbER.add(runnable);
    }
}
