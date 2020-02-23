package com.tencent.liteav.videodecoder;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp2.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: TXCVideoDecoder */
public class b implements com.tencent.liteav.basic.c.a, d {
    boolean a = true;
    boolean b = true;
    boolean c = false;
    boolean d = false;
    Surface e;
    d f;
    private int g;
    private ByteBuffer h;
    private ByteBuffer i;
    private long j;
    private boolean k = false;
    private ArrayList<com.tencent.liteav.basic.f.b> l = new ArrayList<>();
    private a m;
    private WeakReference<com.tencent.liteav.basic.c.a> n;

    public void onNotifyEvent(int i2, Bundle bundle) {
        com.tencent.liteav.basic.util.a.a(this.n, this.j, i2, bundle);
    }

    public void a(long j2) {
        this.j = j2;
    }

    public void a(d dVar) {
        this.f = dVar;
    }

    public boolean a() {
        return this.b;
    }

    public void a(com.tencent.liteav.basic.c.a aVar) {
        this.n = new WeakReference<>(aVar);
    }

    public int a(SurfaceTexture surfaceTexture, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z) {
        return a(new Surface(surfaceTexture), byteBuffer, byteBuffer2, z);
    }

    public int a(Surface surface, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z) {
        this.e = surface;
        this.h = byteBuffer;
        this.i = byteBuffer2;
        this.a = z;
        return 0;
    }

    public void a(boolean z) {
        this.b = z;
    }

    private void b(com.tencent.liteav.basic.f.b bVar) {
        boolean z = bVar.b == 0;
        Bundle bundle = new Bundle();
        bundle.putBoolean("iframe", z);
        bundle.putByteArray("nal", bVar.a);
        bundle.putLong("pts", bVar.g);
        bundle.putLong("dts", bVar.h);
        bundle.putInt("codecId", bVar.i);
        Message message = new Message();
        message.what = 101;
        message.setData(bundle);
        a aVar = this.m;
        if (aVar != null) {
            aVar.sendMessage(message);
        }
        this.g++;
    }

    public void a(com.tencent.liteav.basic.f.b bVar) {
        try {
            boolean z = bVar.b == 0;
            if (this.d || z) {
                if (!this.d && z) {
                    TXCLog.w("TXCVideoDecoder", "play:decode: push first i frame");
                    this.d = true;
                }
                if (!this.k && bVar.i == 1 && !this.b) {
                    TXCLog.w("TXCVideoDecoder", "play:decode: hevc decode error  ");
                    com.tencent.liteav.basic.util.a.a(this.n, (int) TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL, "h265解码失败");
                    this.k = true;
                }
                if (this.m != null) {
                    if (!this.l.isEmpty()) {
                        Iterator<com.tencent.liteav.basic.f.b> it = this.l.iterator();
                        while (it.hasNext()) {
                            b(it.next());
                        }
                    }
                    this.l.clear();
                    b(bVar);
                    return;
                }
                if (z && !this.l.isEmpty()) {
                    this.l.clear();
                }
                this.l.add(bVar);
                if (!this.k) {
                    b();
                    return;
                }
                return;
            }
            TXCLog.i("TXCVideoDecoder", "play:decode: push nal ignore p frame when not got i frame");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0084, code lost:
        r1 = new android.os.Bundle();
        r1.putInt("EVT_ID", 2008);
        r1.putLong("EVT_TIME", com.tencent.liteav.basic.util.TXCTimeUtil.getTimeTick());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x009b, code lost:
        if (r10.b == false) goto L_0x00b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009d, code lost:
        r0 = "启动硬解";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009f, code lost:
        r1.putCharSequence(com.tencent.rtmp2.TXLiveConstants.EVT_DESCRIPTION, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a6, code lost:
        if (r10.b == false) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a8, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a9, code lost:
        r1.putInt("EVT_PARAM1", r0);
        com.tencent.liteav.basic.util.a.a(r10.n, r10.j, 2008, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b6, code lost:
        r0 = "启动软解";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b9, code lost:
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int b() {
        /*
            r10 = this;
            r9 = 2008(0x7d8, float:2.814E-42)
            r0 = -1
            r8 = 0
            boolean r1 = r10.b
            if (r1 == 0) goto L_0x0014
            android.view.Surface r1 = r10.e
            if (r1 != 0) goto L_0x0014
            java.lang.String r1 = "TXCVideoDecoder"
            java.lang.String r2 = "play:decode: start decoder error when not setup surface"
            com.tencent.liteav.basic.log.TXCLog.i(r1, r2)
        L_0x0013:
            return r0
        L_0x0014:
            monitor-enter(r10)
            com.tencent.liteav.videodecoder.b$a r1 = r10.m     // Catch:{ all -> 0x0022 }
            if (r1 == 0) goto L_0x0025
            java.lang.String r1 = "TXCVideoDecoder"
            java.lang.String r2 = "play:decode: start decoder error when decoder is started"
            com.tencent.liteav.basic.log.TXCLog.e(r1, r2)     // Catch:{ all -> 0x0022 }
            monitor-exit(r10)     // Catch:{ all -> 0x0022 }
            goto L_0x0013
        L_0x0022:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0022 }
            throw r0
        L_0x0025:
            r0 = 0
            r10.g = r0     // Catch:{ all -> 0x0022 }
            r0 = 0
            r10.k = r0     // Catch:{ all -> 0x0022 }
            android.os.HandlerThread r1 = new android.os.HandlerThread     // Catch:{ all -> 0x0022 }
            java.lang.String r0 = "VDecoder"
            r1.<init>(r0)     // Catch:{ all -> 0x0022 }
            r1.start()     // Catch:{ all -> 0x0022 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0022 }
            r0.<init>()     // Catch:{ all -> 0x0022 }
            java.lang.String r2 = "VDecoder"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0022 }
            long r2 = r1.getId()     // Catch:{ all -> 0x0022 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0022 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0022 }
            r1.setName(r0)     // Catch:{ all -> 0x0022 }
            com.tencent.liteav.videodecoder.b$a r0 = new com.tencent.liteav.videodecoder.b$a     // Catch:{ all -> 0x0022 }
            android.os.Looper r1 = r1.getLooper()     // Catch:{ all -> 0x0022 }
            r0.<init>(r1)     // Catch:{ all -> 0x0022 }
            boolean r1 = r10.c     // Catch:{ all -> 0x0022 }
            boolean r2 = r10.b     // Catch:{ all -> 0x0022 }
            android.view.Surface r3 = r10.e     // Catch:{ all -> 0x0022 }
            java.nio.ByteBuffer r4 = r10.h     // Catch:{ all -> 0x0022 }
            java.nio.ByteBuffer r5 = r10.i     // Catch:{ all -> 0x0022 }
            r6 = r10
            r7 = r10
            r0.a(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0022 }
            java.lang.String r1 = "TXCVideoDecoder"
            java.lang.String r2 = "play:decode: start decode thread"
            com.tencent.liteav.basic.log.TXCLog.w(r1, r2)     // Catch:{ all -> 0x0022 }
            android.os.Message r1 = android.os.Message.obtain()     // Catch:{ all -> 0x0022 }
            r2 = 100
            r1.what = r2     // Catch:{ all -> 0x0022 }
            boolean r2 = r10.a     // Catch:{ all -> 0x0022 }
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x0022 }
            r1.obj = r2     // Catch:{ all -> 0x0022 }
            r0.sendMessage(r1)     // Catch:{ all -> 0x0022 }
            r10.m = r0     // Catch:{ all -> 0x0022 }
            monitor-exit(r10)     // Catch:{ all -> 0x0022 }
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.String r0 = "EVT_ID"
            r1.putInt(r0, r9)
            java.lang.String r0 = "EVT_TIME"
            long r2 = com.tencent.liteav.basic.util.TXCTimeUtil.getTimeTick()
            r1.putLong(r0, r2)
            java.lang.String r2 = "EVT_MSG"
            boolean r0 = r10.b
            if (r0 == 0) goto L_0x00b6
            java.lang.String r0 = "启动硬解"
        L_0x009f:
            r1.putCharSequence(r2, r0)
            java.lang.String r2 = "EVT_PARAM1"
            boolean r0 = r10.b
            if (r0 == 0) goto L_0x00b9
            r0 = 1
        L_0x00a9:
            r1.putInt(r2, r0)
            java.lang.ref.WeakReference<com.tencent.liteav.basic.c.a> r0 = r10.n
            long r2 = r10.j
            com.tencent.liteav.basic.util.a.a((java.lang.ref.WeakReference<com.tencent.liteav.basic.c.a>) r0, (long) r2, (int) r9, (android.os.Bundle) r1)
            r0 = r8
            goto L_0x0013
        L_0x00b6:
            java.lang.String r0 = "启动软解"
            goto L_0x009f
        L_0x00b9:
            r0 = 2
            goto L_0x00a9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videodecoder.b.b():int");
    }

    public void c() {
        synchronized (this) {
            if (this.m != null) {
                this.m.sendEmptyMessage(102);
            }
            this.m = null;
        }
        this.l.clear();
        this.d = false;
        this.g = 0;
    }

    public void b(boolean z) {
        int i2 = 1;
        synchronized (this) {
            this.b = z;
            this.l.clear();
            this.d = false;
            this.g = 0;
            Message obtain = Message.obtain();
            obtain.what = 103;
            obtain.arg1 = this.b ? 1 : 0;
            if (!this.a) {
                i2 = 0;
            }
            obtain.arg2 = i2;
            if (this.m != null) {
                this.m.sendMessage(obtain);
            }
        }
    }

    public void a(byte[] bArr, long j2, int i2) {
        a aVar = this.m;
        if (aVar != null && !aVar.d && aVar.a != null) {
            ((TXCVideoFfmpegDecoder) aVar.a).loadNativeData(bArr, j2, i2);
        }
    }

    public int d() {
        return this.g + this.l.size();
    }

    public void a(SurfaceTexture surfaceTexture, int i2, int i3, long j2, long j3) {
        if (this.f != null) {
            this.f.a(surfaceTexture, i2, i3, j2, j3);
        }
        if (this.g > 0) {
            this.g--;
        }
    }

    public void a(long j2, int i2, int i3, long j3, long j4) {
        if (this.f != null) {
            this.f.a(j2, i2, i3, j3, j4);
        }
        if (this.g > 0) {
            this.g--;
        }
    }

    public void a(int i2, int i3) {
        if (this.f != null) {
            this.f.a(i2, i3);
        }
    }

    public boolean e() {
        a aVar = this.m;
        if (aVar != null) {
            return aVar.a();
        }
        return false;
    }

    /* compiled from: TXCVideoDecoder */
    private static class a extends Handler {
        a a;
        d b;
        WeakReference<com.tencent.liteav.basic.c.a> c;
        boolean d;
        boolean e;
        Surface f;
        private ByteBuffer g;
        private ByteBuffer h;

        public a(Looper looper) {
            super(looper);
        }

        public void a(boolean z, boolean z2, Surface surface, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, d dVar, com.tencent.liteav.basic.c.a aVar) {
            this.e = z;
            this.d = z2;
            this.f = surface;
            this.g = byteBuffer;
            this.h = byteBuffer2;
            this.b = dVar;
            this.c = new WeakReference<>(aVar);
        }

        public void handleMessage(Message message) {
            boolean z = true;
            switch (message.what) {
                case 100:
                    a(((Boolean) message.obj).booleanValue());
                    return;
                case 101:
                    try {
                        Bundle data = message.getData();
                        a(data.getByteArray("nal"), data.getLong("pts"), data.getLong("dts"), data.getInt("codecId"));
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                case 102:
                    b();
                    return;
                case 103:
                    boolean z2 = message.arg1 == 1;
                    if (message.arg2 != 1) {
                        z = false;
                    }
                    a(z2, z);
                    return;
                default:
                    return;
            }
        }

        public boolean a() {
            if (this.a != null) {
                return this.a.isHevc();
            }
            return false;
        }

        private void a(byte[] bArr, long j, long j2, int i) {
            com.tencent.liteav.basic.f.b bVar = new com.tencent.liteav.basic.f.b();
            bVar.a = bArr;
            bVar.g = j;
            bVar.h = j2;
            bVar.i = i;
            if (this.a != null) {
                this.a.decode(bVar);
            }
        }

        private void b() {
            if (this.a != null) {
                this.a.stop();
                this.a.setListener((d) null);
                this.a.setNotifyListener((WeakReference<com.tencent.liteav.basic.c.a>) null);
                this.a = null;
            }
            Looper.myLooper().quit();
            TXCLog.w("TXCVideoDecoder", "play:decode: stop decode hwdec: " + this.d);
        }

        private void a(boolean z, boolean z2) {
            this.d = z;
            TXCLog.w("TXCVideoDecoder", "play:decode: restart decode hwdec: " + this.d);
            if (this.a != null) {
                this.a.stop();
                this.a.setListener((d) null);
                this.a.setNotifyListener((WeakReference<com.tencent.liteav.basic.c.a>) null);
                this.a = null;
            }
            a(z2);
        }

        private void a(boolean z) {
            if (this.a != null) {
                TXCLog.i("TXCVideoDecoder", "play:decode: start decode ignore hwdec: " + this.d);
                return;
            }
            if (this.d) {
                this.a = new c();
            } else {
                this.a = new TXCVideoFfmpegDecoder();
            }
            this.a.setListener(this.b);
            this.a.setNotifyListener(this.c);
            this.a.config(this.f);
            this.a.start(this.g, this.h, z, this.e);
            TXCLog.w("TXCVideoDecoder", "play:decode: start decode hwdec: " + this.d + ", hevc: " + this.e);
        }
    }
}
