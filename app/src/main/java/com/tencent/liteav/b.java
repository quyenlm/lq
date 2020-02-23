package com.tencent.liteav;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.TextureView;
import com.appsflyer.share.Constants;
import com.google.android.gms.games.GamesStatusCodes;
import com.tencent.liteav.a.a;
import com.tencent.liteav.basic.c.a;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.d;
import com.tencent.liteav.network.TXCStreamDownloader;
import com.tencent.liteav.network.e;
import com.tencent.liteav.renderer.b;
import com.tencent.liteav.renderer.h;
import com.tencent.liteav.renderer.j;
import com.tencent.liteav.renderer.k;
import com.tencent.rtmp2.TXLiveConstants;
import com.tencent.rtmp2.TXLivePlayer;
import com.tencent.rtmp2.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import java.lang.ref.WeakReference;

/* compiled from: TXCLivePlayer */
public class b extends e implements a, d.a, e, b.a, j {
    private int A;
    private TXLivePlayer.ITXAudioRawDataListener B;
    private String C;
    /* access modifiers changed from: private */
    public boolean D;
    /* access modifiers changed from: private */
    public d e;
    /* access modifiers changed from: private */
    public com.tencent.liteav.renderer.b f;
    private TXCStreamDownloader g;
    private Handler h;
    private TextureView i;
    private Surface j;
    private boolean k;
    private boolean l;
    private int m;
    private int n;
    private int o;
    private boolean p;
    private com.tencent.liteav.a.a q;
    /* access modifiers changed from: private */
    public TXRecordCommon.ITXVideoRecordListener r;
    private a s;
    private int t;
    private int u;
    private k v;
    private k w;
    private float[] x;
    private float[] y;
    private String z;

    public b(Context context) {
        super(context);
        this.e = null;
        this.f = null;
        this.g = null;
        this.k = false;
        this.l = false;
        this.m = 0;
        this.n = 0;
        this.o = 16;
        this.p = false;
        this.t = 0;
        this.u = 0;
        this.v = null;
        this.w = null;
        this.x = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};
        this.y = new float[16];
        this.z = "";
        this.C = "";
        this.D = false;
        this.h = new Handler(Looper.getMainLooper());
        this.f = new com.tencent.liteav.renderer.b();
        this.f.a((a) this);
    }

    public void a(TXCloudVideoView tXCloudVideoView) {
        TextureView videoView;
        if (!(this.c == null || this.c == tXCloudVideoView || (videoView = this.c.getVideoView()) == null)) {
            this.c.removeView(videoView);
        }
        super.a(tXCloudVideoView);
        if (this.c != null) {
            this.i = this.c.getVideoView();
            if (this.i == null) {
                this.i = new TextureView(this.c.getContext());
            }
            this.c.addVideoView(this.i);
        }
        if (this.f != null) {
            this.f.a(this.i);
        }
    }

    public void a(c cVar) {
        super.a(cVar);
        if (this.e != null) {
            this.e.a(cVar);
        }
    }

    public int a(String str, int i2) {
        if (c()) {
            TXCLog.w("TXCLivePlayer", "play: ignore start play when is playing");
            return -2;
        }
        this.z = str;
        this.A = i2;
        a(str);
        this.l = true;
        f(i2);
        int b = b(str, i2);
        if (b != 0) {
            this.l = false;
            k();
            j();
            if (this.i == null) {
                return b;
            }
            this.i.setVisibility(8);
            return b;
        }
        l();
        n();
        TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.aI);
        return b;
    }

    public int a(boolean z2) {
        if (!c()) {
            TXCLog.w("TXCLivePlayer", "play: ignore stop play when not started");
            return -2;
        }
        TXCLog.v("TXCLivePlayer", "play: stop");
        this.l = false;
        k();
        j();
        if (this.i != null && z2) {
            this.i.setVisibility(8);
        }
        m();
        o();
        return 0;
    }

    public void a() {
        a(false);
    }

    public void b() {
        a(this.z, this.A);
    }

    public boolean c() {
        return this.l;
    }

    public void a(Surface surface) {
        this.j = surface;
        if (this.e != null) {
            this.e.a(surface);
        }
    }

    public void a(int i2) {
        if (this.e != null) {
            this.e.a(i2);
        }
    }

    public void b(int i2) {
        if (this.e != null) {
            this.e.b(i2);
        }
    }

    public void b(boolean z2) {
        this.k = z2;
        if (this.e != null) {
            this.e.b(this.k);
        }
    }

    public void a(Context context, int i2) {
        d.a(context, i2);
    }

    public void a(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        this.r = iTXVideoRecordListener;
    }

    public int c(int i2) {
        if (this.p) {
            TXCLog.e("TXCLivePlayer", "startRecord: there is existing uncompleted record task");
            return -1;
        }
        this.p = true;
        this.f.a((j) this);
        this.f.a((b.a) this);
        TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.au);
        return 0;
    }

    public TextureView d() {
        return this.i;
    }

    public void a(TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener) {
        this.B = iTXAudioRawDataListener;
    }

    public int e() {
        if (!this.p) {
            TXCLog.w("TXCLivePlayer", "stopRecord: no recording task exist");
            return -1;
        }
        this.p = false;
        return 0;
    }

    public boolean a(byte[] bArr) {
        if (this.e != null) {
            return this.e.a(bArr);
        }
        return false;
    }

    public void a(f fVar) {
        if (this.e != null) {
            this.e.a(fVar);
        }
    }

    private void g() {
        if (this.q == null) {
            this.t = this.f.j();
            this.u = this.f.k();
            a.C0023a i2 = i();
            this.q = new com.tencent.liteav.a.a();
            this.q.a(i2);
            this.q.a((a.b) new a.b() {
                public void a(int i, String str, String str2, String str3) {
                    if (b.this.r != null) {
                        TXRecordCommon.TXRecordResult tXRecordResult = new TXRecordCommon.TXRecordResult();
                        if (i == 0) {
                            tXRecordResult.retCode = 0;
                        } else {
                            tXRecordResult.retCode = -1;
                        }
                        tXRecordResult.descMsg = str;
                        tXRecordResult.videoPath = str2;
                        tXRecordResult.coverPath = str3;
                        b.this.r.onRecordComplete(tXRecordResult);
                    }
                    b.this.f.a((j) null);
                    b.this.f.a((b.a) null);
                }

                public void a(long j) {
                    if (b.this.r != null) {
                        b.this.r.onRecordProgress(j);
                    }
                }
            });
        }
        if (this.v == null) {
            this.v = new k(true);
            this.v.a();
            this.v.b(this.t, this.u);
            this.v.a(this.t, this.u);
        }
        if (this.w == null) {
            this.w = new k(false);
            this.w.a();
            this.w.b(this.f.i().getWidth(), this.f.i().getHeight());
            this.w.a(this.f.i().getWidth(), this.f.i().getHeight());
            Matrix.setIdentityM(this.y, 0);
        }
    }

    private void h() {
        if (this.v != null) {
            this.v.b();
            this.v = null;
        }
        if (this.w != null) {
            this.w.b();
            this.w = null;
        }
        if (this.q != null) {
            this.q.a();
            this.q = null;
        }
    }

    private a.C0023a i() {
        int i2 = 480;
        int i3 = 640;
        if (this.t > 0 && this.u > 0) {
            i2 = this.t;
            i3 = this.u;
        }
        a.C0023a aVar = new a.C0023a();
        aVar.a = i2;
        aVar.b = i3;
        aVar.c = 20;
        aVar.d = (int) (Math.sqrt(((double) (i3 * i3)) + (((double) (i2 * i2)) * 1.0d)) * 1.2d);
        aVar.h = this.m;
        aVar.i = this.n;
        aVar.j = this.o;
        aVar.f = com.tencent.liteav.a.a.a(this.b, ".mp4");
        aVar.g = com.tencent.liteav.a.a.a(this.b, ".jpg");
        aVar.e = this.f.b();
        TXCLog.d("TXCLivePlayer", "record config: " + aVar);
        return aVar;
    }

    private void f(int i2) {
        boolean z2 = true;
        if (this.i != null) {
            this.i.setVisibility(0);
        }
        this.e = new d(this.b, 1);
        this.e.a((com.tencent.liteav.basic.c.a) this);
        this.e.a((h) this.f);
        this.e.a((d.a) this);
        this.e.a(this.a);
        this.e.setID(this.C);
        d dVar = this.e;
        if (i2 != 5) {
            z2 = false;
        }
        dVar.a(z2);
        if (this.j != null) {
            this.e.a(this.j);
        }
        this.e.b(this.k);
    }

    private void j() {
        if (this.e != null) {
            this.e.a();
            this.e.a((h) null);
            this.e.a((d.a) null);
            this.e.a((com.tencent.liteav.basic.c.a) null);
            this.e = null;
        }
    }

    private int b(String str, int i2) {
        boolean z2 = false;
        if (i2 == 0) {
            this.g = new TXCStreamDownloader(this.b, 0, 1);
        } else if (i2 == 5) {
            this.g = new TXCStreamDownloader(this.b, 0, 4);
        } else {
            this.g = new TXCStreamDownloader(this.b, 0, 0);
        }
        this.g.setID(this.C);
        this.g.setListener(this);
        this.g.setNotifyListener(this);
        this.g.setHeaders(this.a.n);
        if (i2 == 5) {
            z2 = true;
        }
        if (z2) {
            this.g.setRetryTimes(5);
            this.g.setRetryInterval(1);
        } else {
            this.g.setRetryTimes(this.a.d);
            this.g.setRetryInterval(this.a.e);
        }
        return this.g.start(str, this.a.i, this.a.k, this.a.j);
    }

    private void k() {
        if (this.g != null) {
            this.g.setListener((e) null);
            this.g.setNotifyListener((com.tencent.liteav.basic.c.a) null);
            this.g.stop();
            this.g = null;
        }
    }

    private void l() {
        this.s = new a(this.b);
        this.s.a(this.z);
        this.s.a(this.A == 5);
        this.s.b(this.C);
        this.s.a();
    }

    private void m() {
        if (this.s != null) {
            this.s.b();
            this.s = null;
        }
    }

    private void a(String str) {
        this.C = String.format("%s-%d", new Object[]{str, Long.valueOf(TXCTimeUtil.getTimeTick() % 10000)});
        if (this.e != null) {
            this.e.setID(this.C);
        }
        if (this.f != null) {
            this.f.setID(this.C);
        }
        if (this.g != null) {
            this.g.setID(this.C);
        }
        if (this.s != null) {
            this.s.b(this.C);
        }
    }

    private void n() {
        this.D = true;
        if (this.h != null) {
            this.h.postDelayed(new Runnable() {
                public void run() {
                    if (b.this.D) {
                        b.this.p();
                    }
                }
            }, 2000);
        }
    }

    private void o() {
        this.D = false;
    }

    /* access modifiers changed from: private */
    public void p() {
        int i2;
        int[] a = com.tencent.liteav.basic.util.a.a();
        String str = (a[0] / 10) + Constants.URL_PATH_DELIMITER + (a[1] / 10) + "%";
        int d = TXCStatus.d(this.C, 7102);
        int d2 = TXCStatus.d(this.C, 7101);
        String c = TXCStatus.c(this.C, 7110);
        int e2 = (int) TXCStatus.e(this.C, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE);
        Bundle bundle = new Bundle();
        if (this.f != null) {
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH, this.f.j());
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT, this.f.k());
        }
        if (this.e != null) {
            bundle.putInt(TXLiveConstants.NET_STATUS_CACHE_SIZE, (int) this.e.c());
            bundle.putInt(TXLiveConstants.NET_STATUS_CODEC_CACHE, (int) this.e.b());
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE_SIZE, (int) this.e.d());
            bundle.putInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE, this.e.e());
            bundle.putInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL, (int) this.e.f());
            bundle.putString(TXLiveConstants.NET_STATUS_AUDIO_INFO, this.e.k());
            bundle.putInt(TXLiveConstants.NET_STATUS_NET_JITTER, this.e.g());
            bundle.putInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL, (int) this.e.h());
            bundle.putFloat(TXLiveConstants.NET_STATUS_AUDIO_PLAY_SPEED, this.e.i());
            if (e2 == 0) {
                i2 = 15;
            } else {
                i2 = e2;
            }
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_GOP, (int) (((double) (((float) ((this.e.j() * 10) / i2)) / 10.0f)) + 0.5d));
        }
        bundle.putInt(TXLiveConstants.NET_STATUS_NET_SPEED, d2 + d);
        bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_FPS, e2);
        bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE, d2);
        bundle.putInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE, d);
        bundle.putCharSequence(TXLiveConstants.NET_STATUS_SERVER_IP, c);
        bundle.putCharSequence(TXLiveConstants.NET_STATUS_CPU_USAGE, str);
        com.tencent.liteav.basic.util.a.a((WeakReference<com.tencent.liteav.basic.c.a>) this.d, 15001, bundle);
        if (this.e != null) {
            this.e.l();
        }
        if (this.s != null) {
            this.s.c();
        }
        if (this.h != null && this.D) {
            this.h.postDelayed(new Runnable() {
                public void run() {
                    if (b.this.D) {
                        b.this.p();
                    }
                }
            }, 2000);
        }
    }

    public void onPullAudio(com.tencent.liteav.basic.f.a aVar) {
        if (this.l && this.e != null) {
            this.e.a(aVar);
        }
    }

    public void onPullNAL(com.tencent.liteav.basic.f.b bVar) {
        if (this.l) {
            try {
                if (this.e != null) {
                    this.e.a(bVar);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onNotifyEvent(final int i2, final Bundle bundle) {
        if (this.h != null) {
            this.h.post(new Runnable() {
                public void run() {
                    com.tencent.liteav.basic.util.a.a((WeakReference<com.tencent.liteav.basic.c.a>) b.this.d, i2, bundle);
                    if (i2 == 2103 && b.this.e != null) {
                        b.this.e.m();
                    }
                }
            });
        }
    }

    public int a(int i2, float[] fArr) {
        if (!(this.q == null || this.v == null)) {
            int b = this.v.b(i2);
            this.q.a(b, TXCTimeUtil.getTimeTick());
            this.f.a(b, this.t, this.u, false, 0);
        }
        if (this.p) {
            g();
        } else {
            h();
        }
        return i2;
    }

    public void d(int i2) {
        if (!(this.q == null || this.w == null)) {
            this.w.a(this.x);
            this.q.a(this.w.b(i2), TXCTimeUtil.getTimeTick());
            this.w.a(this.y);
            this.w.a(i2);
        }
        if (this.p) {
            g();
        } else {
            h();
        }
    }

    public void a(SurfaceTexture surfaceTexture) {
        h();
        e();
    }

    public void a(byte[] bArr, long j2) {
        if (this.q != null) {
            if (j2 <= 0) {
                j2 = TXCTimeUtil.getTimeTick();
            }
            this.q.a(bArr, j2);
        }
        if (this.B != null) {
            this.B.onPcmDataAvailable(bArr, j2);
        }
    }

    public void a(com.tencent.liteav.basic.f.a aVar) {
        TXCLog.d("TXCLivePlayer", "onPlayAudioInfoChanged, samplerate=" + aVar.a + ", channels=" + aVar.b + ", bits=" + aVar.c);
        this.m = aVar.b;
        this.n = aVar.a;
        if (aVar.c > 1) {
            this.o = aVar.c;
        }
        if (this.B != null) {
            this.B.onAudioInfoChanged(aVar.a, aVar.b, aVar.c);
        }
    }
}
