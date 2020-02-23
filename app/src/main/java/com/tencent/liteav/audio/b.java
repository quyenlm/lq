package com.tencent.liteav.audio;

import android.content.Context;
import com.tencent.liteav.audio.impl.Play.TXAudioJitterBufferReportInfo;
import com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController;
import com.tencent.liteav.audio.impl.Play.d;
import com.tencent.liteav.audio.impl.TXCTraeJNI;
import com.tencent.liteav.audio.impl.a;
import com.tencent.liteav.audio.impl.c;
import com.tencent.liteav.audio.impl.e;
import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXCAudioPlayer */
public class b implements e {
    public static final int a = TXEAudioDef.TXE_AEC_NONE;
    public static float b = 5.0f;
    public static boolean c = true;
    public static float d = 5.0f;
    public static float e = 1.0f;
    public static boolean f = false;
    private static final String g = ("AudioCenter:" + b.class.getSimpleName());
    private TXCAudioBasePlayController h = null;
    private d i;
    private int j = a;
    private float k = b;
    private boolean l = c;
    private float m = d;
    private float n = e;
    private boolean o = false;
    private boolean p = false;
    private boolean q = f;
    private Context r;

    public synchronized int a(Context context) {
        int i2;
        if (context == null) {
            TXCLog.e(g, "invalid param, start play failed!");
            i2 = TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
        } else {
            if (a.b(this.j) != TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK) {
                TXCLog.w(g, "start player failed, with aec type " + this.j + ", invalid aec recorder has started!");
            }
            if (this.h == null || !this.h.isPlaying()) {
                this.r = context;
                c.a().a(this.r);
                c.a().a((e) this);
                if (this.h == null) {
                    if (this.j == TXEAudioDef.TXE_AEC_TRAE) {
                        this.h = new com.tencent.liteav.audio.impl.Play.c(context.getApplicationContext());
                    } else {
                        this.h = new com.tencent.liteav.audio.impl.Play.b(context.getApplicationContext());
                    }
                }
                if (this.h != null) {
                    a(this.j, this.r);
                    a(this.i);
                    a(this.k);
                    a(this.l);
                    b(this.m);
                    c(this.n);
                    c(this.o);
                    b(this.p);
                    d(this.q);
                    i2 = this.h.startPlay();
                } else {
                    TXCLog.e(g, "start play failed! controller is null!");
                    i2 = TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
                }
            } else {
                TXCLog.e(g, "play has started, can not start again!");
                i2 = TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION;
            }
        }
        return i2;
    }

    public synchronized int a() {
        int i2;
        i2 = TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        this.i = null;
        this.k = b;
        this.l = c;
        this.m = d;
        this.n = e;
        this.o = false;
        this.p = false;
        this.q = f;
        this.r = null;
        if (this.h != null) {
            i2 = this.h.stopPlay();
            this.h = null;
        }
        c.a().b((e) this);
        return i2;
    }

    public void a(boolean z, Context context) {
        a(TXEAudioDef.TXE_AEC_NONE, context);
    }

    private void a(int i2, Context context) {
        if (i2 != TXEAudioDef.TXE_AEC_TRAE || TXCTraeJNI.nativeCheckTraeEngine(context)) {
            TXCLog.i(g, "set aec type to " + i2 + ", cur type " + this.j);
            this.j = i2;
            return;
        }
        TXCLog.e(g, "set aec type failed, check trae library failed!!");
    }

    public synchronized int a(com.tencent.liteav.basic.f.a aVar) {
        int playData;
        if (this.h == null) {
            TXCLog.e(g, "play audio failed, controller not created yet!");
            playData = TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        } else {
            playData = this.h.playData(aVar);
        }
        return playData;
    }

    public void a(d dVar) {
        this.i = dVar;
        if (this.h != null) {
            this.h.setListener(dVar);
        }
    }

    public void a(float f2) {
        this.k = f2;
        if (this.h != null) {
            this.h.setCacheTime(f2);
        }
    }

    public void a(boolean z) {
        this.l = z;
        if (this.h != null) {
            this.h.enableAutojustCache(z);
        }
    }

    public void b(float f2) {
        this.m = f2;
        if (this.h != null) {
            this.h.setAutoAdjustMaxCache(f2);
        }
    }

    public void c(float f2) {
        this.n = f2;
        if (this.h != null) {
            this.h.setAutoAdjustMinCache(f2);
        }
    }

    public synchronized long b() {
        long j2;
        if (this.h != null) {
            j2 = this.h.getCacheDuration();
        } else {
            j2 = 0;
        }
        return j2;
    }

    public synchronized long c() {
        long j2;
        if (this.h != null) {
            j2 = this.h.getCurPts();
        } else {
            j2 = 0;
        }
        return j2;
    }

    public synchronized int d() {
        int i2;
        if (this.h != null) {
            i2 = this.h.getRecvJitter();
        } else {
            i2 = 0;
        }
        return i2;
    }

    public synchronized long e() {
        long j2;
        if (this.h != null) {
            j2 = this.h.getCurRecvTS();
        } else {
            j2 = 0;
        }
        return j2;
    }

    public synchronized float f() {
        float f2;
        if (this.h != null) {
            f2 = this.h.getCacheThreshold();
        } else {
            f2 = 0.0f;
        }
        return f2;
    }

    public void b(boolean z) {
        this.p = z;
        if (this.h != null) {
            this.h.enableHWAcceleration(z);
        }
    }

    public void c(boolean z) {
        this.o = z;
        if (this.h != null) {
            this.h.enableRealTimePlay(z);
        }
    }

    public void d(boolean z) {
        this.q = z;
        if (this.h != null) {
            this.h.setMute(z);
        }
    }

    public static void a(Context context, int i2) {
        TXCAudioBasePlayController.setAudioMode(context, i2);
    }

    public int g() {
        if (d.a().d()) {
            if (this.j != TXEAudioDef.TXE_AEC_TRAE) {
                return this.j;
            }
            TXCLog.e(g, "audio track has start, but aec type is trae!!" + this.j);
            return TXEAudioDef.TXE_AEC_NONE;
        } else if (!TXCTraeJNI.nativeTraeIsPlaying()) {
            return TXEAudioDef.TXE_AEC_NONE;
        } else {
            if (this.j == TXEAudioDef.TXE_AEC_TRAE) {
                return this.j;
            }
            TXCLog.e(g, "trae engine has start, but aec type is not trae!!" + this.j);
            return TXEAudioDef.TXE_AEC_TRAE;
        }
    }

    public TXAudioJitterBufferReportInfo h() {
        if (this.h != null) {
            return this.h.getReportInfo();
        }
        return null;
    }

    public void a(int i2) {
        switch (i2) {
            case 0:
                if (this.h != null) {
                    this.h.setMute(this.q);
                    return;
                }
                return;
            case 1:
                if (this.h != null) {
                    this.h.setMute(true);
                    return;
                }
                return;
            case 2:
                if (this.h != null) {
                    this.h.setMute(true);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
