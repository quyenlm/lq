package com.tencent.liteav.videoencoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import com.google.android.gms.common.Scopes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.liteav.basic.d.d;
import com.tencent.liteav.basic.d.e;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.b;
import com.tencent.qqgamemi.util.GlobalUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;

/* compiled from: TXCHWVideoEncoder */
public class a extends b {
    private static final String a = a.class.getSimpleName();
    private ByteBuffer[] A;
    private byte[] B;
    private volatile long C;
    private long D;
    private long E;
    private int F;
    private int G;
    private boolean H;
    private int I;
    private int J;
    private long K;
    private int b;
    private long c;
    private long d;
    private long e;
    private long f;
    private int g;
    private boolean h;
    private boolean i;
    private long j;
    private long k;
    private long l;
    private long m;
    private long n;
    private long o;
    private long p;
    private MediaCodec q;
    /* access modifiers changed from: private */
    public b r;
    private Runnable s;
    private Runnable t;
    private Runnable u;
    private ArrayDeque<Long> v;
    private com.tencent.liteav.basic.d.a w;
    private Surface x;
    private boolean y;
    private boolean z;

    public a() {
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.i = true;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = null;
        this.r = null;
        this.s = new Runnable() {
            public void run() {
                a.this.c();
            }
        };
        this.t = new Runnable() {
            public void run() {
                a.this.e();
            }
        };
        this.u = new Runnable() {
            public void run() {
                a.this.d();
            }
        };
        this.v = new ArrayDeque<>(10);
        this.x = null;
        this.y = true;
        this.z = true;
        this.A = null;
        this.B = null;
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.I = 0;
        this.J = 0;
        this.K = 0;
        this.r = new b("HWVideoEncoder");
    }

    public int start(final TXSVideoEncoderParam tXSVideoEncoderParam) {
        super.start(tXSVideoEncoderParam);
        final boolean[] zArr = new boolean[1];
        if (Build.VERSION.SDK_INT < 18) {
            zArr[0] = false;
        } else {
            synchronized (this) {
                this.r.a((Runnable) new Runnable() {
                    public void run() {
                        if (a.this.mInit) {
                            a.this.e();
                        }
                        zArr[0] = a.this.a(tXSVideoEncoderParam);
                    }
                });
            }
        }
        if (!zArr[0]) {
            callDelegate(10000004);
        }
        if (zArr[0]) {
            return 0;
        }
        return 10000004;
    }

    public void stop() {
        this.z = true;
        synchronized (this) {
            this.r.a((Runnable) new Runnable() {
                public void run() {
                    if (a.this.mInit) {
                        a.this.e();
                        a.this.r.a().removeCallbacksAndMessages((Object) null);
                    }
                }
            });
        }
    }

    public void setFPS(final int i2) {
        this.r.b(new Runnable() {
            public void run() {
                a.this.b(i2);
            }
        });
    }

    public void setBitrate(final int i2) {
        this.b = i2;
        this.r.b(new Runnable() {
            public void run() {
                a.this.a(i2);
            }
        });
    }

    public long getRealFPS() {
        return this.d;
    }

    public long getRealBitrate() {
        return this.c;
    }

    public long pushVideoFrame(int i2, int i3, int i4, long j2) {
        if (this.z) {
            return 10000004;
        }
        if (this.mGLContextExternal != null) {
            if (this.mInputFilter == null) {
                this.mInputFilter = new com.tencent.liteav.basic.d.b();
                this.mInputFilter.a(true);
                this.mInputFilter.a(e.e, e.a(d.NORMAL, false, false));
                if (!this.mInputFilter.a()) {
                    this.mInputFilter = null;
                    return 0;
                }
            }
            this.mInputFilter.a(i3, i4);
            GLES20.glViewport(0, 0, i3, i4);
            this.mInputFilter.b(i2);
        }
        this.C = j2;
        this.mInputTextureID = this.mInputFilter.j();
        this.r.b(this.u);
        return 0;
    }

    @TargetApi(16)
    private MediaFormat a(int i2, int i3, int i4, int i5, int i6) {
        if (i2 == 0 || i3 == 0 || i4 == 0 || i5 == 0) {
            return null;
        }
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(GlobalUtil.AVC_MIME_TYPE, i2, i3);
        createVideoFormat.setInteger("bitrate", i4 * 1024);
        createVideoFormat.setInteger("frame-rate", i5);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("i-frame-interval", i6);
        return createVideoFormat;
    }

    @TargetApi(16)
    private MediaFormat a(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        MediaFormat a2 = a(i2, i3, i4, i5, i6);
        if (a2 == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            MediaCodecInfo a3 = a(GlobalUtil.AVC_MIME_TYPE);
            if (a3 == null) {
                return a2;
            }
            MediaCodecInfo.CodecCapabilities capabilitiesForType = a3.getCapabilitiesForType(GlobalUtil.AVC_MIME_TYPE);
            MediaCodecInfo.EncoderCapabilities encoderCapabilities = capabilitiesForType.getEncoderCapabilities();
            if (encoderCapabilities.isBitrateModeSupported(i7)) {
                a2.setInteger("bitrate-mode", i7);
            } else if (encoderCapabilities.isBitrateModeSupported(2)) {
                a2.setInteger("bitrate-mode", 2);
            }
            a2.setInteger("complexity", encoderCapabilities.getComplexityRange().clamp(5).intValue());
            if (Build.VERSION.SDK_INT >= 23) {
                int i9 = 0;
                for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : capabilitiesForType.profileLevels) {
                    if (codecProfileLevel.profile <= i8 && codecProfileLevel.profile > i9) {
                        i9 = codecProfileLevel.profile;
                        a2.setInteger(Scopes.PROFILE, codecProfileLevel.profile);
                        a2.setInteger(FirebaseAnalytics.Param.LEVEL, codecProfileLevel.level);
                    }
                }
            }
        }
        return a2;
    }

    @TargetApi(16)
    private static MediaCodecInfo a(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i2 = 0; i2 < codecCount; i2++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            if (codecInfoAt.isEncoder()) {
                for (String equalsIgnoreCase : codecInfoAt.getSupportedTypes()) {
                    if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private void a(long j2) {
        this.v.add(Long.valueOf(j2));
    }

    private long a() {
        Long poll = this.v.poll();
        if (poll == null) {
            return 0;
        }
        return poll.longValue();
    }

    private boolean a(Surface surface, int i2, int i3) {
        if (surface == null) {
            return false;
        }
        EGLContext eGLContext = this.mGLContextExternal;
        if (eGLContext == null) {
            eGLContext = EGL10.EGL_NO_CONTEXT;
        }
        this.w = com.tencent.liteav.basic.d.a.a((EGLConfig) null, eGLContext, surface, i2, i3);
        if (this.w == null) {
            return false;
        }
        this.mEncodeFilter = new com.tencent.liteav.basic.d.b();
        this.mEncodeFilter.a();
        return true;
    }

    private void b() {
        if (this.mInputFilter != null) {
            this.mInputFilter.d();
            this.mInputFilter = null;
        }
        if (this.mEncodeFilter != null) {
            this.mEncodeFilter.d();
            this.mEncodeFilter = null;
        }
        if (this.w != null) {
            this.w.c();
            this.w = null;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x013b  */
    @android.annotation.TargetApi(18)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(com.tencent.liteav.videoencoder.TXSVideoEncoderParam r13) {
        /*
            r12 = this;
            r8 = 2
            r11 = 0
            r10 = 0
            r0 = 0
            r9 = 1
            r12.z = r10
            r12.y = r10
            r12.c = r0
            r12.d = r0
            r12.e = r0
            r12.f = r0
            r12.g = r10
            r12.j = r0
            r12.k = r0
            r12.l = r0
            r12.m = r0
            r12.n = r0
            r12.o = r0
            r12.p = r0
            r12.A = r11
            r12.B = r11
            r12.C = r0
            r0 = -1
            r12.F = r0
            int r0 = r13.gop
            r12.G = r0
            boolean r0 = r13.needEdit
            r12.H = r0
            java.util.ArrayDeque<java.lang.Long> r0 = r12.v
            r0.clear()
            if (r13 == 0) goto L_0x004a
            int r0 = r13.width
            if (r0 == 0) goto L_0x004a
            int r0 = r13.height
            if (r0 == 0) goto L_0x004a
            int r0 = r13.fps
            if (r0 == 0) goto L_0x004a
            int r0 = r13.gop
            if (r0 != 0) goto L_0x004d
        L_0x004a:
            r12.y = r9
        L_0x004c:
            return r10
        L_0x004d:
            boolean r0 = r13.annexb
            r12.h = r0
            boolean r0 = r13.appendSpsPps
            r12.i = r0
            int r0 = r12.b
            if (r0 != 0) goto L_0x0076
            int r0 = r13.width
            int r1 = r13.width
            int r0 = r0 * r1
            double r0 = (double) r0
            r2 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r0 = r0 * r2
            int r2 = r13.height
            int r3 = r13.height
            int r2 = r2 * r3
            double r2 = (double) r2
            double r0 = r0 + r2
            double r0 = java.lang.Math.sqrt(r0)
            r2 = 4608083138725491507(0x3ff3333333333333, double:1.2)
            double r0 = r0 * r2
            int r0 = (int) r0
            r12.b = r0
        L_0x0076:
            int r0 = r12.b
            long r0 = (long) r0
            r12.j = r0
            int r0 = r13.fps
            r12.g = r0
            int r0 = r13.gop
            r12.G = r0
            int r0 = r13.encoderMode
            switch(r0) {
                case 1: goto L_0x00de;
                case 2: goto L_0x00e0;
                case 3: goto L_0x00e2;
                default: goto L_0x0088;
            }
        L_0x0088:
            r6 = r8
        L_0x0089:
            com.tencent.liteav.basic.e.b r0 = com.tencent.liteav.basic.e.b.a()
            int r0 = r0.b()
            if (r0 != r9) goto L_0x0095
            r13.encoderProfile = r9
        L_0x0095:
            int r0 = r13.encoderProfile
            switch(r0) {
                case 1: goto L_0x009a;
                case 2: goto L_0x009a;
                case 3: goto L_0x009a;
                default: goto L_0x009a;
            }
        L_0x009a:
            r7 = 1
            int r1 = r13.width     // Catch:{ Exception -> 0x00b0 }
            int r2 = r13.height     // Catch:{ Exception -> 0x00b0 }
            int r3 = r12.b     // Catch:{ Exception -> 0x00b0 }
            int r4 = r13.fps     // Catch:{ Exception -> 0x00b0 }
            int r5 = r13.gop     // Catch:{ Exception -> 0x00b0 }
            r0 = r12
            android.media.MediaFormat r0 = r0.a(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00b0 }
            if (r0 != 0) goto L_0x00e4
            r0 = 1
            r12.y = r0     // Catch:{ Exception -> 0x00b0 }
            goto L_0x004c
        L_0x00b0:
            r0 = move-exception
            r8 = r9
        L_0x00b2:
            r0.printStackTrace()
            r0 = 5
            if (r8 < r0) goto L_0x00c1
            android.media.MediaCodec r0 = r12.q
            if (r0 == 0) goto L_0x00c1
            android.media.MediaCodec r0 = r12.q
            r0.stop()
        L_0x00c1:
            r12.q = r11
            android.view.Surface r0 = r12.x
            if (r0 == 0) goto L_0x00cc
            android.view.Surface r0 = r12.x
            r0.release()
        L_0x00cc:
            r12.x = r11
        L_0x00ce:
            android.media.MediaCodec r0 = r12.q
            if (r0 == 0) goto L_0x00da
            java.nio.ByteBuffer[] r0 = r12.A
            if (r0 == 0) goto L_0x00da
            android.view.Surface r0 = r12.x
            if (r0 != 0) goto L_0x013b
        L_0x00da:
            r12.y = r9
            goto L_0x004c
        L_0x00de:
            r6 = r8
            goto L_0x0089
        L_0x00e0:
            r6 = r9
            goto L_0x0089
        L_0x00e2:
            r6 = r10
            goto L_0x0089
        L_0x00e4:
            java.lang.String r1 = "video/avc"
            android.media.MediaCodec r1 = android.media.MediaCodec.createEncoderByType(r1)     // Catch:{ Exception -> 0x00b0 }
            r12.q = r1     // Catch:{ Exception -> 0x00b0 }
            android.media.MediaCodec r1 = r12.q     // Catch:{ Exception -> 0x010f }
            r2 = 0
            r3 = 0
            r4 = 1
            r1.configure(r0, r2, r3, r4)     // Catch:{ Exception -> 0x010f }
        L_0x00f4:
            r8 = 3
            android.media.MediaCodec r0 = r12.q     // Catch:{ Exception -> 0x010d }
            android.view.Surface r0 = r0.createInputSurface()     // Catch:{ Exception -> 0x010d }
            r12.x = r0     // Catch:{ Exception -> 0x010d }
            r8 = 4
            android.media.MediaCodec r0 = r12.q     // Catch:{ Exception -> 0x010d }
            r0.start()     // Catch:{ Exception -> 0x010d }
            r8 = 5
            android.media.MediaCodec r0 = r12.q     // Catch:{ Exception -> 0x010d }
            java.nio.ByteBuffer[] r0 = r0.getOutputBuffers()     // Catch:{ Exception -> 0x010d }
            r12.A = r0     // Catch:{ Exception -> 0x010d }
            goto L_0x00ce
        L_0x010d:
            r0 = move-exception
            goto L_0x00b2
        L_0x010f:
            r0 = move-exception
            r6 = r0
            boolean r0 = r6 instanceof java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x010d }
            if (r0 != 0) goto L_0x011f
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x010d }
            r1 = 21
            if (r0 < r1) goto L_0x013a
            boolean r0 = r6 instanceof android.media.MediaCodec.CodecException     // Catch:{ Exception -> 0x010d }
            if (r0 == 0) goto L_0x013a
        L_0x011f:
            android.media.MediaCodec r7 = r12.q     // Catch:{ Exception -> 0x010d }
            int r1 = r13.width     // Catch:{ Exception -> 0x010d }
            int r2 = r13.height     // Catch:{ Exception -> 0x010d }
            int r3 = r12.b     // Catch:{ Exception -> 0x010d }
            int r4 = r13.fps     // Catch:{ Exception -> 0x010d }
            int r5 = r13.gop     // Catch:{ Exception -> 0x010d }
            r0 = r12
            android.media.MediaFormat r0 = r0.a((int) r1, (int) r2, (int) r3, (int) r4, (int) r5)     // Catch:{ Exception -> 0x010d }
            r1 = 0
            r2 = 0
            r3 = 1
            r7.configure(r0, r1, r2, r3)     // Catch:{ Exception -> 0x010d }
            r6.printStackTrace()     // Catch:{ Exception -> 0x010d }
            goto L_0x00f4
        L_0x013a:
            throw r6     // Catch:{ Exception -> 0x010d }
        L_0x013b:
            android.view.Surface r0 = r12.x
            int r1 = r13.width
            int r2 = r13.height
            boolean r0 = r12.a(r0, r1, r2)
            if (r0 != 0) goto L_0x014b
            r12.y = r9
            goto L_0x004c
        L_0x014b:
            r12.mInit = r9
            com.tencent.liteav.basic.util.b r0 = r12.r
            java.lang.Runnable r1 = r12.s
            r2 = 1
            r0.a(r1, r2)
            r10 = r9
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videoencoder.a.a(com.tencent.liteav.videoencoder.TXSVideoEncoderParam):boolean");
    }

    /* access modifiers changed from: private */
    public void c() {
        byte[] bArr;
        byte[] bArr2;
        if (this.q != null) {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            try {
                int dequeueOutputBuffer = this.q.dequeueOutputBuffer(bufferInfo, Constants.ACTIVE_THREAD_WATCHDOG);
                if (dequeueOutputBuffer != -1) {
                    if (dequeueOutputBuffer == -3) {
                        this.A = this.q.getOutputBuffers();
                    } else if (dequeueOutputBuffer == -2) {
                        callDelegate(this.q.getOutputFormat());
                    } else if (dequeueOutputBuffer < 0) {
                        this.y = true;
                        this.r.b(this.t);
                        callDelegate(10000005);
                        return;
                    } else {
                        if (this.H) {
                            f();
                        }
                        ByteBuffer byteBuffer = this.A[dequeueOutputBuffer];
                        if (byteBuffer == null) {
                            this.y = true;
                            this.r.b(this.t);
                            callDelegate(10000005);
                        } else {
                            byte[] bArr3 = new byte[bufferInfo.size];
                            byteBuffer.position(bufferInfo.offset);
                            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                            byteBuffer.get(bArr3, 0, bufferInfo.size);
                            int length = bArr3.length;
                            if (bufferInfo.size > 5 && bArr3[0] == 0 && bArr3[1] == 0 && bArr3[2] == 0 && bArr3[3] == 0 && bArr3[4] == 0 && bArr3[5] == 0) {
                                int i2 = 3;
                                while (true) {
                                    if (i2 < bArr3.length - 4) {
                                        if (bArr3[i2] == 0 && bArr3[i2 + 1] == 0 && bArr3[i2 + 2] == 0 && bArr3[i2 + 3] == 1) {
                                            length -= i2;
                                            break;
                                        }
                                        i2++;
                                    } else {
                                        i2 = 0;
                                        break;
                                    }
                                }
                                byte[] bArr4 = new byte[length];
                                System.arraycopy(bArr3, i2, bArr4, 0, length);
                                bArr = bArr4;
                            } else {
                                bArr = bArr3;
                            }
                            if (bufferInfo.size == 0) {
                                this.y = true;
                                this.r.b(this.t);
                                callDelegate(10000005);
                            } else {
                                int i3 = 1;
                                if ((bufferInfo.flags & 2) != 2) {
                                    if ((bufferInfo.flags & 1) == 1) {
                                        i3 = 0;
                                        this.F = -1;
                                        if (this.h) {
                                            bArr2 = new byte[(this.B.length + bArr.length)];
                                            System.arraycopy(this.B, 0, bArr2, 0, this.B.length);
                                            System.arraycopy(bArr, 0, bArr2, this.B.length, bArr.length);
                                        } else {
                                            byte[] a2 = a(bArr);
                                            bArr2 = new byte[(this.B.length + a2.length)];
                                            System.arraycopy(this.B, 0, bArr2, 0, this.B.length);
                                            System.arraycopy(a2, 0, bArr2, this.B.length, a2.length);
                                        }
                                    } else if (!this.h) {
                                        bArr2 = a(bArr);
                                    } else {
                                        bArr2 = bArr;
                                    }
                                    int i4 = this.F + 1;
                                    this.F = i4;
                                    if (i4 == this.g * this.G) {
                                        f();
                                    }
                                    long a3 = a();
                                    long j2 = bufferInfo.presentationTimeUs / 1000;
                                    if (this.E == 0) {
                                        this.E = a3;
                                    }
                                    if (this.D == 0) {
                                        this.D = j2;
                                    }
                                    long j3 = j2 + (this.E - this.D);
                                    if (a3 <= this.n) {
                                        a3 = this.n + 1;
                                    }
                                    if (a3 > j3) {
                                        a3 = j3;
                                    }
                                    this.n = a3;
                                    long timeTick = TXCTimeUtil.getTimeTick();
                                    if (i3 == 0) {
                                        if (timeTick > this.e + 1000) {
                                            this.c = (long) (((((double) this.o) * 8000.0d) / ((double) (timeTick - this.e))) / 1024.0d);
                                            this.o = 0;
                                            this.e = timeTick;
                                        }
                                        this.k++;
                                        this.l = 0;
                                    } else {
                                        this.l++;
                                    }
                                    this.o += (long) bArr2.length;
                                    if (timeTick > this.f + 2000) {
                                        this.d = (long) ((((double) this.p) * 1000.0d) / ((double) (timeTick - this.f)));
                                        this.p = 0;
                                        this.f = timeTick;
                                    }
                                    this.p++;
                                    byteBuffer.position(bufferInfo.offset);
                                    if (this.i) {
                                        callDelegate(bArr2, i3, this.k, this.l, this.m, i3 == 0 ? 0 : this.l - 1, j3, j3, 0, byteBuffer, bufferInfo);
                                    } else {
                                        callDelegate(bArr3, i3, this.k, this.l, this.m, i3 == 0 ? 0 : this.l - 1, j3, j3, 0, byteBuffer, bufferInfo);
                                    }
                                    this.m++;
                                    if ((bufferInfo.flags & 4) != 0) {
                                        this.y = true;
                                        this.r.b(this.t);
                                    }
                                } else if (this.h) {
                                    this.B = (byte[]) bArr.clone();
                                } else {
                                    this.B = a((byte[]) bArr.clone());
                                }
                            }
                        }
                        try {
                            this.q.releaseOutputBuffer(dequeueOutputBuffer, false);
                        } catch (IllegalStateException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (!this.y) {
                        this.r.b(this.s);
                    }
                } else if (!this.y) {
                    this.r.a(this.s, 10);
                }
            } catch (IllegalStateException e3) {
                e3.printStackTrace();
                this.y = true;
                this.r.b(this.t);
                callDelegate(10000005);
            }
        } else if (!this.y) {
            this.r.a(this.s, 10);
        }
    }

    private byte[] a(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[(length + 20)];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            if (bArr[i2] == 0 && bArr[i2 + 1] == 0 && bArr[i2 + 2] == 1) {
                i4 = a(i2, i3, bArr2, bArr, i4);
                int i5 = i2 + 3;
                i2 = i5;
                i3 = i5;
            } else if (bArr[i2] == 0 && bArr[i2 + 1] == 0 && bArr[i2 + 2] == 0 && bArr[i2 + 3] == 1) {
                i4 = a(i2, i3, bArr2, bArr, i4);
                int i6 = i2 + 4;
                i2 = i6;
                i3 = i6;
            }
            if (i2 != length - 4 || (bArr[i2 + 1] == 0 && bArr[i2 + 2] == 0 && bArr[i2 + 3] == 1)) {
                i2++;
            }
        }
        i2 = length;
        int a2 = a(i2, i3, bArr2, bArr, i4);
        byte[] bArr3 = new byte[a2];
        System.arraycopy(bArr2, 0, bArr3, 0, a2);
        return bArr3;
    }

    private int a(int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        if (i3 <= 0 || i2 <= i3) {
            return i4;
        }
        int i5 = i2 - i3;
        ByteBuffer wrap = ByteBuffer.wrap(new byte[4]);
        wrap.asIntBuffer().put(i5);
        wrap.order(ByteOrder.BIG_ENDIAN);
        System.arraycopy(wrap.array(), 0, bArr, i4, 4);
        System.arraycopy(bArr2, i3, bArr, i4 + 4, i5);
        return i4 + i5 + 4;
    }

    /* access modifiers changed from: private */
    @TargetApi(18)
    public void d() {
        if (!this.y && this.C != 0 && this.w != null) {
            this.w.b();
            GLES20.glClear(16640);
            if (this.mGLContextExternal == null) {
                GLES20.glClearColor(0.1f, 0.3f, ((float) (this.m % 255)) / 255.0f, 1.0f);
                GLES20.glEnable(3042);
                GLES20.glBlendFunc(770, 771);
            } else {
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            }
            GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
            this.mEncodeFilter.a(this.mInputTextureID);
            GLES20.glDisable(3042);
            a(this.C);
            this.w.a();
            this.C = 0;
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        this.y = true;
        this.z = true;
        b();
        if (this.q != null) {
            try {
                if (Build.VERSION.SDK_INT >= 18) {
                    this.q.signalEndOfInputStream();
                }
                this.q.stop();
                try {
                    this.q.release();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (IllegalStateException e3) {
                e3.printStackTrace();
                try {
                    this.q.release();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    this.q.release();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                throw th;
            }
        }
        this.q = null;
        this.mInputTextureID = -1;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.mGLContextExternal = null;
        this.A = null;
        this.B = null;
        this.C = 0;
        this.mOutputWidth = 0;
        this.mOutputHeight = 0;
        this.mInit = false;
        this.v.clear();
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        if (this.j != ((long) this.b)) {
            this.j = (long) this.b;
            if (Build.VERSION.SDK_INT >= 19 && this.q != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("video-bitrate", this.b * 1024);
                this.q.setParameters(bundle);
            }
        }
    }

    private void f() {
        if (Build.VERSION.SDK_INT >= 19 && this.q != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("request-sync", 0);
            this.q.setParameters(bundle);
            TXCLog.w(a, "P帧间隔异常,强制I帧");
        }
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
    }
}
