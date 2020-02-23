package com.tencent.liteav;

import MTT.EFvrECode;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.impl.Play.TXAudioJitterBufferReportInfo;
import com.tencent.liteav.basic.b.b;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.renderer.h;
import com.tencent.liteav.renderer.i;
import com.tencent.rtmp2.TXLiveConstants;
import java.nio.ByteBuffer;

/* compiled from: TXCRenderAndDec */
public class d extends com.tencent.liteav.basic.module.a implements com.tencent.liteav.audio.d, b, com.tencent.liteav.basic.c.a, i, com.tencent.liteav.videodecoder.d {
    private Context a = null;
    private c b = null;
    private com.tencent.liteav.videodecoder.b c = null;
    private h d = null;
    private com.tencent.liteav.basic.b.a e = null;
    private com.tencent.liteav.audio.b f = null;
    private com.tencent.liteav.basic.c.a g = null;
    private boolean h = false;
    private long i = 0;
    private byte[] j = null;
    private f k = null;
    private int l;
    private boolean m = false;
    private String n;
    private final float o = com.tencent.liteav.basic.a.a.o;
    private final float p = com.tencent.liteav.basic.a.a.p;
    private final float q = com.tencent.liteav.basic.a.a.q;
    private final float r = 0.3f;
    private boolean s = false;
    private a t = null;

    /* compiled from: TXCRenderAndDec */
    public interface a {
        void a(SurfaceTexture surfaceTexture);

        void a(com.tencent.liteav.basic.f.a aVar);

        void a(byte[] bArr, long j);
    }

    public d(Context context, int i2) {
        this.a = context;
        this.l = i2;
    }

    public void a(h hVar) {
        this.d = hVar;
        if (this.d != null && this.g != null) {
            this.d.a((com.tencent.liteav.basic.c.a) this);
        }
    }

    public void a(com.tencent.liteav.basic.c.a aVar) {
        this.g = aVar;
    }

    public void a(c cVar) {
        this.b = cVar;
        q();
    }

    public void setID(String str) {
        super.setID(str);
        if (this.d != null) {
            this.d.setID(getID());
        }
    }

    public void a(a aVar) {
        this.t = aVar;
    }

    public void a(boolean z) {
        this.h = z;
        this.m = true;
        if (this.d != null) {
            this.d.a((i) this);
            this.d.g();
            this.d.setID(getID());
        }
        this.c = new com.tencent.liteav.videodecoder.b();
        this.c.a(this.i);
        this.c.a((com.tencent.liteav.videodecoder.d) this);
        this.c.a((com.tencent.liteav.basic.c.a) this);
        this.f = new com.tencent.liteav.audio.b();
        this.f.a((com.tencent.liteav.audio.d) this);
        c(this.h);
        this.f.a(this.a);
        this.e = new com.tencent.liteav.basic.b.a();
        this.e.a((b) this);
        this.e.a();
        p();
        q();
    }

    public void a() {
        this.h = false;
        if (this.c != null) {
            this.c.a((com.tencent.liteav.videodecoder.d) null);
            this.c.a((com.tencent.liteav.basic.c.a) null);
            this.c.c();
            this.c = null;
        }
        if (this.f != null) {
            this.f.a((com.tencent.liteav.audio.d) null);
            this.f.a();
            this.f = null;
        }
        if (this.e != null) {
            this.e.a((b) null);
            this.e.b();
            this.e = null;
        }
        if (this.d != null) {
            this.d.h();
            this.d.a((i) null);
        }
    }

    public void a(Surface surface) {
        if (this.c != null) {
            this.c.a(surface, (ByteBuffer) null, (ByteBuffer) null, !this.h);
            this.c.c();
            if (surface != null) {
                this.c.a(true);
                this.c.b();
            }
        }
    }

    public void a(com.tencent.liteav.basic.f.a aVar) {
        if (this.f != null) {
            this.f.a(aVar);
        } else {
            TXCLog.w("TXCRenderAndDec", "decAudio fail which audio play hasn't been created!");
        }
    }

    public void a(com.tencent.liteav.basic.f.b bVar) {
        try {
            if (this.e != null) {
                this.e.a(bVar);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(int i2) {
        if (this.d != null) {
            this.d.a(i2);
        }
    }

    public void b(int i2) {
        if (this.d != null) {
            this.d.b(i2);
        }
    }

    public void b(boolean z) {
        if (this.f != null) {
            this.f.d(z);
        }
    }

    public static void a(Context context, int i2) {
        com.tencent.liteav.audio.b.a(context, i2);
    }

    public long b() {
        if (this.f != null) {
            return this.f.b();
        }
        return 0;
    }

    public long c() {
        if (this.e != null) {
            return this.e.d();
        }
        return 0;
    }

    public long d() {
        if (this.e != null) {
            return this.e.e();
        }
        return 0;
    }

    public int e() {
        if (this.c != null) {
            return this.c.d();
        }
        return 0;
    }

    public long f() {
        if (this.e == null || this.f == null) {
            return 0;
        }
        return this.f.c() - this.e.f();
    }

    public int g() {
        if (this.f != null) {
            return this.f.d();
        }
        return 0;
    }

    public long h() {
        if (this.f == null || this.e == null) {
            return 0;
        }
        return this.f.e() - this.e.g();
    }

    public float i() {
        if (this.f != null) {
            return this.f.f();
        }
        return 0.0f;
    }

    public int j() {
        if (this.e != null) {
            return this.e.h();
        }
        return 0;
    }

    public String k() {
        int i2;
        if (this.f != null) {
            i2 = this.f.g();
        } else {
            i2 = TXEAudioDef.TXE_AEC_NONE;
        }
        return i2 + " | " + this.n;
    }

    public void l() {
        TXAudioJitterBufferReportInfo h2;
        long j2 = 0;
        if (!(this.f == null || (h2 = this.f.h()) == null)) {
            long j3 = h2.mLoadCnt == 0 ? 0 : (long) (h2.mLoadTime / h2.mLoadCnt);
            long j4 = h2.mTimeTotalCacheTimeCnt == 0 ? 0 : h2.mTimeTotalCacheTime / h2.mTimeTotalCacheTimeCnt;
            int i2 = h2.mTimeTotalJittCnt == 0 ? 0 : h2.mTimeTotalJitt / h2.mTimeTotalJittCnt;
            setStatusValue(2001, Long.valueOf(j3));
            setStatusValue(2002, Long.valueOf((long) h2.mLoadCnt));
            setStatusValue(2003, Long.valueOf((long) h2.mLoadMaxTime));
            setStatusValue(2004, Long.valueOf((long) h2.mSpeedCnt));
            setStatusValue(2005, Long.valueOf((long) h2.mNoDataCnt));
            setStatusValue(2007, Long.valueOf((long) h2.mAvgCacheTime));
            setStatusValue(2008, Long.valueOf((long) h2.mIsRealTime));
            setStatusValue(2010, Long.valueOf(j4));
            setStatusValue(2011, Long.valueOf((long) i2));
            setStatusValue(EFvrECode._ERR_FVR_IMGCVT_EXCEPTION, Long.valueOf((long) h2.mTimeDropCnt));
        }
        if (this.e != null) {
            setStatusValue(2006, Long.valueOf(d()));
            setStatusValue(6007, Long.valueOf(this.e.k()));
            setStatusValue(6008, Long.valueOf(this.e.j()));
            setStatusValue(6009, Long.valueOf(this.e.i()));
        }
        if (this.c != null) {
            if (this.c.a()) {
                j2 = 1;
            }
            setStatusValue(5002, Long.valueOf(j2));
        }
    }

    public boolean a(byte[] bArr) {
        synchronized (this) {
            this.j = bArr;
        }
        return true;
    }

    public void a(f fVar) {
        synchronized (this) {
            this.k = fVar;
        }
    }

    private void c(SurfaceTexture surfaceTexture) {
        com.tencent.liteav.videodecoder.b bVar = this.c;
        if (bVar != null) {
            bVar.a(this.b.h);
            if (surfaceTexture != null) {
                bVar.a(surfaceTexture, (ByteBuffer) null, (ByteBuffer) null, !this.h);
                bVar.b();
            }
        }
    }

    private void p() {
        c(this.d != null ? this.d.a() : null);
    }

    private void q() {
        c(this.h);
        if (this.f != null) {
            this.f.a(this.b.a);
            this.f.a(this.b.f);
            this.f.c(this.b.c);
            this.f.b(this.b.b);
            setStatusValue(2012, Long.valueOf((long) (this.b.c * 1000.0f)));
            setStatusValue(EFvrECode._ERR_FVR_NONSUPPORT, Long.valueOf((long) (this.b.b * 1000.0f)));
            setStatusValue(EFvrECode._ERR_FVR_IMGCVT_ERR, 0L);
        }
        if (this.e != null) {
            this.e.a(this.b.c);
        }
        if (this.c != null && this.c.a() && this.b.c < 0.3f && this.b.b < 0.3f) {
            this.b.h = false;
            this.c.c();
            p();
        }
    }

    private void c(boolean z) {
        float f2;
        if (z) {
            float f3 = this.b.a;
            float f4 = this.b.c;
            float f5 = this.b.b;
            if (f4 > this.p) {
                f4 = this.p;
            }
            if (f5 > this.q) {
                f5 = this.q;
            }
            if (f4 >= f5) {
                f2 = this.p;
                f5 = this.q;
            } else {
                f2 = f4;
            }
            this.b.f = true;
            this.b.a = f2;
            this.b.c = f2;
            this.b.b = f5;
            if (this.f != null) {
                this.f.a(true, this.a);
                this.f.c(true);
            }
        } else if (this.f != null) {
            if (this.b == null || !this.b.g) {
                this.f.a(false, this.a);
            } else {
                this.f.a(true, this.a);
            }
            this.f.c(false);
        }
        if (this.e != null) {
            this.e.b(z);
        }
    }

    private void a(int i2, String str) {
        com.tencent.liteav.basic.c.a aVar = this.g;
        if (aVar != null) {
            Bundle bundle = new Bundle();
            Log.i("TXCRenderAndDec", "TXCRenderAndDec notifyEvent: mUserID  " + this.i);
            bundle.putLong("EVT_USERID", this.i);
            bundle.putInt("EVT_ID", i2);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            if (str != null) {
                bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
            }
            aVar.onNotifyEvent(i2, bundle);
        }
    }

    private void r() {
        com.tencent.liteav.videodecoder.b bVar = this.c;
        if (bVar != null) {
            TXCLog.w("TXCRenderAndDec", "switch to soft decoder when hw error");
            bVar.c();
            this.b.h = false;
            c(this.h);
            p();
        }
    }

    public void m() {
        com.tencent.liteav.videodecoder.b bVar = this.c;
        if (bVar != null && bVar.e()) {
            bVar.b(true);
        }
    }

    public void onNotifyEvent(int i2, Bundle bundle) {
        if (i2 == 2106) {
            r();
        } else if (i2 == 2003 && this.m) {
            a(2004, "视频播放开始");
            this.m = false;
        }
        com.tencent.liteav.basic.c.a aVar = this.g;
        if (aVar != null) {
            aVar.onNotifyEvent(i2, bundle);
        }
    }

    public void a(SurfaceTexture surfaceTexture) {
        c(surfaceTexture);
    }

    public void b(SurfaceTexture surfaceTexture) {
        try {
            TXCLog.w("TXCRenderAndDec", "play:stop decode when surface texture release");
            if (this.c != null) {
                this.c.c();
            }
            if (this.t != null) {
                this.t.a(surfaceTexture);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public long n() {
        try {
            if (this.f != null) {
                return this.f.c();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return 0;
    }

    public void b(com.tencent.liteav.basic.f.b bVar) {
        try {
            if (this.c != null) {
                this.c.a(bVar);
            } else {
                this.e.a(bVar.g);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void c(com.tencent.liteav.basic.f.b bVar) {
        Bundle bundle = new Bundle();
        bundle.putByteArray(TXLiveConstants.EVT_GET_MSG, bVar.a);
        onNotifyEvent(2012, bundle);
    }

    public int o() {
        try {
            if (this.c != null) {
                return this.c.d();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return 0;
    }

    public void a(SurfaceTexture surfaceTexture, int i2, int i3, long j2, long j3) {
        if (this.d != null) {
            this.d.a(surfaceTexture, i2, i3);
            if (this.e != null) {
                this.e.a(j2);
            }
        }
    }

    public void a(long j2, int i2, int i3, long j3, long j4) {
        boolean z = false;
        if (!(this.k == null || this.j == null)) {
            synchronized (this) {
                byte[] bArr = this.j;
                this.j = null;
                if (!(this.k == null || bArr == null || this.c == null)) {
                    if (bArr.length <= ((i2 * i3) * 3) / 2) {
                        this.c.a(bArr, j2, bArr.length);
                        this.k.onVideoRawDataAvailable(bArr, i2, i3, (int) j3);
                        z = true;
                    } else {
                        TXCLog.e("TXCRenderAndDec", "raw data buffer length is too large");
                    }
                }
            }
        }
        if (!z) {
            if (j2 > 0 && this.d != null) {
                this.d.a(j2, i2, i3);
            }
            if (this.e != null) {
                this.e.a(j3);
            }
        }
    }

    public void a(int i2, int i3) {
        if (this.d != null) {
            this.d.b(i2, i3);
        }
        Bundle bundle = new Bundle();
        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, "分辨率改变");
        bundle.putInt("EVT_PARAM1", i2);
        bundle.putInt("EVT_PARAM2", i3);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        onNotifyEvent(2009, bundle);
    }

    public void onPlayAudioInfoChanged(com.tencent.liteav.basic.f.a aVar, com.tencent.liteav.basic.f.a aVar2) {
        if (this.t != null) {
            this.t.a(aVar2);
        }
        if (aVar != null && aVar2 != null) {
            this.n = aVar.a + "," + aVar.b + " | " + aVar2.a + "," + aVar2.b;
        }
    }

    public void onPlayPcmData(byte[] bArr, long j2) {
        if (this.t != null) {
            this.t.a(bArr, j2);
        }
    }

    public void onPlayError(int i2, String str) {
    }

    public void onPlayJitterStateNotify(int i2) {
        if (i2 == TXEAudioDef.TXE_AUDIO_JITTER_STATE_LOADING) {
            if (this.e != null) {
                this.e.a(true);
            }
            a(2007, "视频缓冲中...");
        } else if (i2 == TXEAudioDef.TXE_AUDIO_JITTER_STATE_PLAYING) {
            if (this.e != null) {
                this.e.a(false);
            }
            a(2004, "视频播放开始");
        } else if (i2 == TXEAudioDef.TXE_AUDIO_JITTER_STATE_FIRST_PLAY) {
            if (this.e != null) {
                this.e.a(false);
            }
            if (this.m) {
                a(2004, "视频播放开始");
                this.m = false;
            }
        }
    }
}
