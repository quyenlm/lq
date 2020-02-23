package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;

/* compiled from: TXCAudioBaseRecord */
public abstract class b {
    protected int a = 0;
    protected int b = 0;
    protected int c = 0;
    private WeakReference<g> d = null;

    public void a(Context context, int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public synchronized void a(g gVar) {
        if (gVar == null) {
            this.d = null;
        } else {
            this.d = new WeakReference<>(gVar);
        }
    }

    /* access modifiers changed from: protected */
    public void a(byte[] bArr, int i, long j) {
        g gVar = null;
        synchronized (this) {
            if (this.d != null) {
                gVar = (g) this.d.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordPCM(bArr, i, j);
        } else {
            TXCLog.e("AudioCenter:TXCAudioBaseRecord", "onRecordPcmData:no callback");
        }
    }

    /* access modifiers changed from: protected */
    public void a() {
        g gVar = null;
        synchronized (this) {
            if (this.d != null) {
                gVar = (g) this.d.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordStart();
        } else {
            TXCLog.e("AudioCenter:TXCAudioBaseRecord", "onRecordStart:no callback");
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        g gVar = null;
        synchronized (this) {
            if (this.d != null) {
                gVar = (g) this.d.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordStop();
        } else {
            TXCLog.e("AudioCenter:TXCAudioBaseRecord", "onRecordStop:no callback");
        }
    }
}
