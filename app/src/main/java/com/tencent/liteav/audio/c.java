package com.tencent.liteav.audio;

import com.tencent.liteav.audio.impl.Record.f;
import com.tencent.liteav.audio.impl.TXCTraeJNI;
import com.tencent.liteav.audio.impl.e;
import com.tencent.liteav.basic.a.a;
import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXCAudioRecorder */
public class c implements e {
    public static final int a = a.e;
    public static final int b = a.f;
    public static final int c = a.h;
    public static final int d = TXEAudioDef.TXE_REVERB_TYPE_0;
    public static final int e = TXEAudioDef.TXE_AEC_NONE;
    public static final int f = TXEAudioDef.TXE_AUDIO_TYPE_AAC;
    static c g = new c();
    private static final String h = ("AudioCenter:" + c.class.getSimpleName());
    private int i = a;
    private int j = b;
    private int k = a.h;
    private int l = d;
    private boolean m = false;
    private int n = e;
    private boolean o = false;
    private boolean p = false;
    private float q = 1.0f;
    private com.tencent.liteav.audio.impl.Record.c r = null;

    private c() {
    }

    public static c a() {
        return g;
    }

    public int b() {
        if (f.a().c()) {
            if (this.n != TXEAudioDef.TXE_AEC_TRAE) {
                return this.n;
            }
            TXCLog.e(h, "audio mic has start, but aec type is trae!!" + this.n);
            return TXEAudioDef.TXE_AEC_NONE;
        } else if (!TXCTraeJNI.nativeTraeIsRecording()) {
            return TXEAudioDef.TXE_AEC_NONE;
        } else {
            if (this.n == TXEAudioDef.TXE_AEC_TRAE) {
                return this.n;
            }
            TXCLog.e(h, "trae engine has start, but aec type is not trae!!" + this.n);
            return TXEAudioDef.TXE_AEC_TRAE;
        }
    }

    public void a(int i2) {
        switch (i2) {
            case 0:
                if (this.r != null) {
                    this.r.setMute(this.m);
                    return;
                }
                return;
            case 1:
                if (this.r != null) {
                    this.r.setMute(true);
                    return;
                }
                return;
            case 2:
                if (this.r != null) {
                    this.r.setMute(true);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
