package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.os.Build;
import android.view.TextureView;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.rtmp2.TXLiveConstants;
import java.lang.ref.WeakReference;

/* compiled from: TXCVideoRender */
public class h extends com.tencent.liteav.basic.module.a implements TextureView.SurfaceTextureListener {
    private SurfaceTexture a;
    private long b;
    protected TextureView c;
    protected g d;
    protected int e = 0;
    protected int f = 0;
    protected int g = 0;
    protected int h = 0;
    protected i i;
    WeakReference<com.tencent.liteav.basic.c.a> j;
    private boolean k = false;
    private boolean l = false;
    private a m = new a();

    /* compiled from: TXCVideoRender */
    public static class a {
        public long a;
        public long b;
        public long c;
        public long d;
        public long e;
        public long f;
        public long g;
        public long h;
        public long i;
        public int j;
        public int k;
    }

    public void a(i iVar) {
        this.i = iVar;
    }

    public void a(com.tencent.liteav.basic.c.a aVar) {
        this.j = new WeakReference<>(aVar);
    }

    public void a(TextureView textureView) {
        b(textureView);
    }

    public void a(long j2, int i2, int i3) {
        a(i2, i3);
        b();
    }

    public void a(SurfaceTexture surfaceTexture, int i2, int i3) {
        a(i2, i3);
        b();
    }

    public void a(int i2, int i3, int i4, boolean z, int i5) {
        a(i3, i4);
    }

    public void g() {
        this.l = true;
        this.k = false;
        l();
    }

    public void h() {
        this.k = false;
        this.l = false;
    }

    public void b(int i2, int i3) {
        a(i2, i3);
    }

    public void a(int i2) {
        if (this.d != null) {
            this.d.a(i2);
        }
    }

    public void b(int i2) {
        if (this.d != null) {
            this.d.c(i2);
        }
    }

    public SurfaceTexture a() {
        return null;
    }

    public TextureView i() {
        return this.c;
    }

    public int j() {
        return this.g;
    }

    public int k() {
        return this.h;
    }

    /* access modifiers changed from: protected */
    public void a(SurfaceTexture surfaceTexture) {
    }

    /* access modifiers changed from: protected */
    public void b(SurfaceTexture surfaceTexture) {
    }

    private void b(TextureView textureView) {
        boolean z = false;
        if ((this.c == null && textureView != null) || (this.c != null && !this.c.equals(textureView))) {
            z = true;
        }
        TXCLog.w("TXCVideoRender", "play:vrender: set video view @old=" + this.c + ",new=" + textureView);
        if (z) {
            if (this.c != null && this.a == null) {
                b(this.c.getSurfaceTexture());
            }
            this.c = textureView;
            if (this.c != null) {
                this.e = this.c.getWidth();
                this.f = this.c.getHeight();
                this.d = new g(this.c);
                this.d.b(this.g, this.h);
                this.d.a(this.e, this.f);
                this.c.setSurfaceTextureListener(this);
                if (this.a != null) {
                    if (Build.VERSION.SDK_INT >= 16 && this.c.getSurfaceTexture() != this.a) {
                        this.c.setSurfaceTexture(this.a);
                    }
                } else if (this.c.isAvailable()) {
                    a(this.c.getSurfaceTexture());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i2, int i3) {
        if (this.g != i2 || this.h != i3) {
            if (this.g != i2 || this.h != i3) {
                this.g = i2;
                this.h = i3;
                if (this.d != null) {
                    this.d.b(this.g, this.h);
                }
            }
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
        TXCLog.w("TXCVideoRender", "play:vrender: texture available @" + surfaceTexture);
        this.e = i2;
        this.f = i3;
        if (this.d != null) {
            this.d.a(this.e, this.f);
        }
        if (this.a != null) {
            if (Build.VERSION.SDK_INT >= 16 && this.c.getSurfaceTexture() != this.a) {
                this.c.setSurfaceTexture(this.a);
            }
            this.a = null;
            return;
        }
        a(surfaceTexture);
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
        TXCLog.w("TXCVideoRender", "play:vrender: texture size change new:" + i2 + "," + i3 + " old:" + this.e + "," + this.f);
        this.e = i2;
        this.f = i3;
        if (this.d != null) {
            this.d.a(this.e, this.f);
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        try {
            TXCLog.w("TXCVideoRender", "play:vrender:  onSurfaceTextureDestroyed when need save texture : " + this.l);
            if (this.l) {
                this.a = surfaceTexture;
            } else {
                this.m.a = 0;
                b(surfaceTexture);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.a == null) {
            return true;
        }
        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void l() {
        this.m.a = 0;
        this.m.b = 0;
        this.m.c = 0;
        this.m.d = 0;
        this.m.e = 0;
        this.m.f = 0;
        this.m.g = 0;
        this.m.h = 0;
        this.m.i = 0;
        this.m.j = 0;
        this.m.k = 0;
        setStatusValue(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER, 0L);
        setStatusValue(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE, Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE));
        setStatusValue(GamesStatusCodes.STATUS_MULTIPLAYER_DISABLED, 0L);
        setStatusValue(6005, 0L);
        setStatusValue(6006, 0L);
        setStatusValue(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION, 0L);
    }

    private long a(long j2) {
        long timeTick = TXCTimeUtil.getTimeTick();
        if (j2 > timeTick) {
            return 0;
        }
        return timeTick - j2;
    }

    private void b() {
        if (!this.k) {
            com.tencent.liteav.basic.util.a.a(this.j, this.b, 2003, "渲染首个视频数据包(IDR)");
            setStatusValue(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER, Long.valueOf(TXCTimeUtil.getTimeTick()));
            this.k = true;
        }
        this.m.c++;
        if (this.m.a == 0) {
            this.m.a = TXCTimeUtil.getTimeTick();
        } else {
            long timeTick = TXCTimeUtil.getTimeTick() - this.m.a;
            if (timeTick >= 1000) {
                setStatusValue(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE, Double.valueOf((((double) (this.m.c - this.m.b)) * 1000.0d) / ((double) timeTick)));
                this.m.b = this.m.c;
                a aVar = this.m;
                aVar.a = timeTick + aVar.a;
            }
        }
        if (this.m.d != 0) {
            this.m.i = a(this.m.d);
            if (this.m.i > 500) {
                this.m.e++;
                setStatusValue(GamesStatusCodes.STATUS_MULTIPLAYER_DISABLED, Long.valueOf(this.m.e));
                if (this.m.i > this.m.h) {
                    this.m.h = this.m.i;
                    setStatusValue(6005, Long.valueOf(this.m.h));
                }
                this.m.g += this.m.i;
                setStatusValue(6006, Long.valueOf(this.m.g));
                TXCLog.w("TXCVideoRender", "render frame count:" + this.m.c + " block time:" + this.m.i + "> 500");
            }
            if (this.m.i > 800) {
                TXCLog.w("TXCVideoRender", "render frame count:" + this.m.c + " block time:" + this.m.i + "> 800");
            }
            if (this.m.i > 1000) {
                this.m.f++;
                setStatusValue(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION, Long.valueOf(this.m.f));
                TXCLog.w("TXCVideoRender", "render frame count:" + this.m.c + " block time:" + this.m.i + "> 1000");
                com.tencent.liteav.basic.util.a.a(this.j, this.b, (int) TXLiveConstants.PLAY_WARNING_VIDEO_PLAY_LAG, "当前视频播放出现卡顿" + this.m.i + "ms");
            }
        }
        this.m.d = TXCTimeUtil.getTimeTick();
        this.m.k = this.h;
        this.m.j = this.g;
    }
}
