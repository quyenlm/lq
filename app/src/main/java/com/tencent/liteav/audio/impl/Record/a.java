package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* compiled from: TXCAudioBGMRecord */
public class a implements Runnable {
    private WeakReference<g> a;
    private Context b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private Thread g;
    private byte[] h;

    public synchronized void a(g gVar) {
        if (gVar == null) {
            this.a = null;
        } else {
            this.a = new WeakReference<>(gVar);
        }
    }

    public void a(Context context, int i, int i2, int i3) {
        a();
        this.b = context;
        this.c = i;
        this.d = i2;
        this.e = i3;
        this.f = true;
        this.g = new Thread(this, "AudioSysRecord Thread");
        this.g.start();
    }

    public void a() {
        this.f = false;
        long currentTimeMillis = System.currentTimeMillis();
        if (!(this.g == null || !this.g.isAlive() || Thread.currentThread().getId() == this.g.getId())) {
            try {
                this.g.join();
            } catch (Exception e2) {
                e2.printStackTrace();
                TXCLog.e("AudioCenter:TXCAudioBGMRecord", "record stop Exception: " + e2.getMessage());
            }
        }
        TXCLog.i("AudioCenter:TXCAudioBGMRecord", "stop record cost time(MS): " + (System.currentTimeMillis() - currentTimeMillis));
        this.g = null;
    }

    public boolean b() {
        return this.f;
    }

    private void a(byte[] bArr, int i, long j) {
        g gVar = null;
        synchronized (this) {
            if (this.a != null) {
                gVar = (g) this.a.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordPCM(bArr, i, j);
        } else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordPcmData:no callback");
        }
    }

    private void c() {
        g gVar = null;
        synchronized (this) {
            if (this.a != null) {
                gVar = (g) this.a.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordStart();
        } else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordStart:no callback");
        }
    }

    private void d() {
        g gVar = null;
        synchronized (this) {
            if (this.a != null) {
                gVar = (g) this.a.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordStop();
        } else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordStop:no callback");
        }
    }

    public void run() {
        if (!this.f) {
            TXCLog.w("AudioCenter:TXCAudioBGMRecord", "audio record: abandom start audio sys record thread!");
            return;
        }
        c();
        int i = this.c;
        int i2 = this.d;
        int i3 = this.e;
        int i4 = ((i2 * 1024) * i3) / 8;
        this.h = new byte[i4];
        Arrays.fill(this.h, (byte) 0);
        long j = 0;
        long currentTimeMillis = System.currentTimeMillis();
        while (this.f && !Thread.interrupted()) {
            if (((((((System.currentTimeMillis() - currentTimeMillis) * ((long) i)) * ((long) i2)) * ((long) i3)) / 8) / 1000) - j < ((long) i4)) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            } else {
                j += (long) this.h.length;
                a(this.h, this.h.length, TXCTimeUtil.getTimeTick());
            }
        }
        d();
    }
}
