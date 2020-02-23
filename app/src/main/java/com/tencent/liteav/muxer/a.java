package com.tencent.liteav.muxer;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.tp.a.h;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

@TargetApi(18)
/* compiled from: TXCMP4HWMuxer */
public class a {
    public static float a = 0.5f;
    public static float b = 0.8f;
    public static float c = 1.25f;
    public static float d = 2.0f;
    private int e = 2;
    private MediaMuxer f;
    private String g = null;
    private MediaFormat h = null;
    private MediaFormat i = null;
    private int j = 0;
    private int k = 0;
    private boolean l = false;
    private boolean m = false;
    private ConcurrentLinkedQueue<C0024a> n = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<C0024a> o = new ConcurrentLinkedQueue<>();
    private long p = -1;
    private long q = -1;
    private long r = -1;

    /* renamed from: com.tencent.liteav.muxer.a$a  reason: collision with other inner class name */
    /* compiled from: TXCMP4HWMuxer */
    private static class C0024a {
        ByteBuffer a;
        MediaCodec.BufferInfo b;

        public C0024a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            this.a = byteBuffer;
            this.b = bufferInfo;
        }

        public ByteBuffer a() {
            return this.a;
        }

        public MediaCodec.BufferInfo b() {
            return this.b;
        }
    }

    public synchronized void a(MediaFormat mediaFormat) {
        TXCLog.d("TXCMP4HWMuxer", "addVideoTrack:" + mediaFormat);
        this.h = mediaFormat;
        this.n.clear();
    }

    public synchronized void b(MediaFormat mediaFormat) {
        TXCLog.d("TXCMP4HWMuxer", "addAudioTrack:" + mediaFormat);
        this.i = mediaFormat;
        this.o.clear();
    }

    public synchronized boolean a() {
        boolean z;
        if (this.h != null) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public synchronized int b() {
        int i2 = 0;
        synchronized (this) {
            if (this.g == null || this.g.isEmpty()) {
                TXCLog.e("TXCMP4HWMuxer", "target path not set yet!");
                i2 = -1;
            } else if (!a()) {
                TXCLog.e("TXCMP4HWMuxer", "video track not set yet!");
                i2 = -2;
            } else if (this.f != null) {
                TXCLog.w("TXCMP4HWMuxer", "start has been called. stop must be called before start");
            } else {
                TXCLog.d("TXCMP4HWMuxer", "start");
                try {
                    this.f = new MediaMuxer(this.g, 0);
                    if (this.h != null) {
                        try {
                            this.k = this.f.addTrack(this.h);
                        } catch (IllegalArgumentException e2) {
                            TXCLog.e("TXCMP4HWMuxer", "addVideoTrack IllegalArgumentException: " + e2);
                            i2 = -5;
                        } catch (IllegalStateException e3) {
                            TXCLog.e("TXCMP4HWMuxer", "addVideoTrack IllegalStateException: " + e3);
                            i2 = -6;
                        }
                    }
                    if (this.i != null) {
                        try {
                            this.j = this.f.addTrack(this.i);
                        } catch (IllegalArgumentException e4) {
                            TXCLog.e("TXCMP4HWMuxer", "addAudioTrack IllegalArgumentException: " + e4);
                            i2 = -7;
                        } catch (IllegalStateException e5) {
                            TXCLog.e("TXCMP4HWMuxer", "addAudioTrack IllegalStateException: " + e5);
                            i2 = -8;
                        }
                    }
                    this.f.start();
                    this.p = -1;
                    this.l = true;
                    this.m = false;
                    this.q = -1;
                    this.r = -1;
                } catch (IOException e6) {
                    e6.printStackTrace();
                    TXCLog.e("TXCMP4HWMuxer", "create MediaMuxer exception:" + e6);
                    i2 = -4;
                }
            }
        }
        return i2;
    }

    public synchronized int c() {
        int i2 = 0;
        synchronized (this) {
            if (this.f != null) {
                TXCLog.d("TXCMP4HWMuxer", "stop. start flag = " + this.l + ", video key frame set = " + this.m);
                try {
                    if (this.l && this.m) {
                        this.f.stop();
                    }
                    this.f.release();
                    this.l = false;
                    this.f = null;
                    this.m = false;
                    this.n.clear();
                    this.o.clear();
                    this.h = null;
                    this.i = null;
                    this.q = -1;
                    this.r = -1;
                } catch (Exception e2) {
                    TXCLog.e("TXCMP4HWMuxer", "muxer stop/release exception: " + e2);
                    i2 = -1;
                    this.l = false;
                    this.f = null;
                    this.m = false;
                    this.n.clear();
                    this.o.clear();
                    this.h = null;
                    this.i = null;
                    this.q = -1;
                    this.r = -1;
                } catch (Throwable th) {
                    this.l = false;
                    this.f = null;
                    this.m = false;
                    this.n.clear();
                    this.o.clear();
                    this.h = null;
                    this.i = null;
                    this.q = -1;
                    this.r = -1;
                    throw th;
                }
            }
        }
        return i2;
    }

    public synchronized void a(String str) {
        this.g = str;
    }

    public synchronized void a(byte[] bArr, int i2, int i3, long j2, int i4) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i3);
        allocateDirect.put(bArr, i2, i3);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.presentationTimeUs = j2;
        bufferInfo.offset = 0;
        bufferInfo.size = i3;
        bufferInfo.flags = i4;
        a(allocateDirect, bufferInfo);
    }

    public synchronized void b(byte[] bArr, int i2, int i3, long j2, int i4) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i3);
        allocateDirect.put(bArr, i2, i3);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.presentationTimeUs = j2;
        bufferInfo.offset = 0;
        bufferInfo.size = i3;
        bufferInfo.flags = i4;
        b(allocateDirect, bufferInfo);
    }

    public synchronized void a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f == null) {
            a(true, byteBuffer, bufferInfo);
            TXCLog.w("TXCMP4HWMuxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
        } else if (this.p < 0) {
            a(true, byteBuffer, bufferInfo);
            this.p = d();
            TXCLog.d("TXCMP4HWMuxer", "first frame offset = " + this.p);
            e();
        } else {
            c(byteBuffer, bufferInfo);
        }
    }

    public synchronized void b(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f == null || this.p < 0) {
            TXCLog.w("TXCMP4HWMuxer", "cache sample before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            a(false, byteBuffer, bufferInfo);
        } else {
            d(byteBuffer, bufferInfo);
        }
    }

    private void c(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        long j2 = 0;
        long j3 = bufferInfo.presentationTimeUs - this.p;
        if (j3 < 0) {
            TXCLog.e("TXCMP4HWMuxer", "pts error! first frame offset timeus = " + this.p + ", current timeus = " + bufferInfo.presentationTimeUs);
            if (this.q > 0) {
                j2 = this.q;
            }
        } else {
            j2 = j3;
        }
        if (j2 < this.q) {
            TXCLog.w("TXCMP4HWMuxer", "video is not in chronological order. current frame's pts(" + j2 + ") smaller than pre frame's pts(" + this.q + h.b);
        } else {
            this.q = j2;
        }
        if (this.e != 2) {
            if (this.e == 3) {
                j2 = (long) (((float) j2) * b);
            } else if (this.e == 4) {
                j2 = (long) (((float) j2) * a);
            } else if (this.e == 1) {
                j2 = (long) (((float) j2) * c);
            } else if (this.e == 0) {
                j2 = (long) (((float) j2) * d);
            }
        }
        bufferInfo.presentationTimeUs = j2;
        try {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            this.f.writeSampleData(this.k, byteBuffer, bufferInfo);
            if ((bufferInfo.flags & 1) != 0) {
                this.m = true;
            }
        } catch (IllegalStateException e2) {
            TXCLog.e("TXCMP4HWMuxer", "write frame IllegalStateException: " + e2);
        } catch (IllegalArgumentException e3) {
            TXCLog.e("TXCMP4HWMuxer", "write frame IllegalArgumentException: " + e3);
        }
    }

    private void d(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        long j2 = bufferInfo.presentationTimeUs - this.p;
        if (this.p < 0 || j2 < 0) {
            TXCLog.w("TXCMP4HWMuxer", "drop sample. first frame offset timeus = " + this.p + ", current sample timeus = " + bufferInfo.presentationTimeUs);
            return;
        }
        if (j2 < this.r) {
            TXCLog.e("TXCMP4HWMuxer", "audio is not in chronological order. current audio's pts pts(" + j2 + ") must larger than pre audio's pts(" + this.r + h.b);
            j2 = this.r + 1;
        } else {
            this.r = j2;
        }
        if (this.e != 2) {
            if (this.e == 3) {
                j2 = (long) (((float) j2) * b);
            } else if (this.e == 4) {
                j2 = (long) (((float) j2) * a);
            } else if (this.e == 1) {
                j2 = (long) (((float) j2) * c);
            } else if (this.e == 0) {
                j2 = (long) (((float) j2) * d);
            }
        }
        bufferInfo.presentationTimeUs = j2;
        try {
            this.f.writeSampleData(this.j, byteBuffer, bufferInfo);
        } catch (IllegalStateException e2) {
            TXCLog.e("TXCMP4HWMuxer", "write sample IllegalStateException: " + e2);
        } catch (IllegalArgumentException e3) {
            TXCLog.e("TXCMP4HWMuxer", "write sample IllegalArgumentException: " + e3);
        }
    }

    private void a(boolean z, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (byteBuffer != null && bufferInfo != null) {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(byteBuffer.capacity());
            byteBuffer.rewind();
            if (bufferInfo.size > 0) {
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.size);
            }
            allocateDirect.rewind();
            allocateDirect.put(byteBuffer);
            MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
            bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
            C0024a aVar = new C0024a(allocateDirect, bufferInfo2);
            if (z) {
                if (this.n.size() < 200) {
                    this.n.add(aVar);
                } else {
                    TXCLog.e("TXCMP4HWMuxer", "drop video frame. video cache size is larger than 200");
                }
            } else if (this.o.size() < 300) {
                this.o.add(aVar);
            } else {
                TXCLog.e("TXCMP4HWMuxer", "drop audio frame. audio cache size is larger than 300");
            }
        }
    }

    private long d() {
        long j2;
        C0024a peek;
        if (this.n.size() > 0) {
            j2 = this.n.peek().b().presentationTimeUs;
        } else {
            j2 = 0;
        }
        if (this.o.size() <= 0 || (peek = this.o.peek()) == null || peek.b() == null) {
            return j2;
        }
        long j3 = this.o.peek().b().presentationTimeUs;
        if (j2 > j3) {
            return j3;
        }
        return j2;
    }

    private void e() {
        while (this.n.size() > 0) {
            C0024a poll = this.n.poll();
            c(poll.a(), poll.b());
        }
        while (this.o.size() > 0) {
            C0024a poll2 = this.o.poll();
            d(poll2.a(), poll2.b());
        }
    }
}
