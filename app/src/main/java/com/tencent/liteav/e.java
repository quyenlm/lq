package com.tencent.liteav;

import android.content.Context;
import android.view.Surface;
import android.view.TextureView;
import com.tencent.liteav.basic.c.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp2.TXLivePlayer;
import com.tencent.rtmp2.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import java.lang.ref.WeakReference;

/* compiled from: TXIPlayer */
public abstract class e {
    protected c a = null;
    protected Context b = null;
    protected TXCloudVideoView c = null;
    protected WeakReference<a> d;

    public abstract int a(String str, int i);

    public abstract int a(boolean z);

    public abstract void a(int i);

    public abstract void b(int i);

    public abstract void b(boolean z);

    public abstract int c(int i);

    public abstract boolean c();

    public abstract int e();

    public e(Context context) {
        if (context != null) {
            this.b = context.getApplicationContext();
        }
    }

    public c f() {
        return this.a;
    }

    public void a(c cVar) {
        this.a = cVar;
        if (this.a == null) {
            this.a = new c();
        }
    }

    public void a() {
        TXCLog.w("TXIPlayer", "pause not support");
    }

    public void b() {
        TXCLog.w("TXIPlayer", "resume not support");
    }

    public void a(Surface surface) {
    }

    public void e(int i) {
        TXCLog.w("TXIPlayer", "seek not support");
    }

    public void a(TXCloudVideoView tXCloudVideoView) {
        this.c = tXCloudVideoView;
    }

    public TextureView d() {
        return null;
    }

    public void a(a aVar) {
        this.d = new WeakReference<>(aVar);
    }

    public void a(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
    }

    public void a(TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener) {
    }

    public void c(boolean z) {
        TXCLog.w("TXIPlayer", "autoPlay not implement");
    }

    public void a(float f) {
        TXCLog.w("TXIPlayer", "rate not implement");
    }

    public void a(Context context, int i) {
    }

    public boolean a(byte[] bArr) {
        return false;
    }

    public void a(f fVar) {
    }
}
