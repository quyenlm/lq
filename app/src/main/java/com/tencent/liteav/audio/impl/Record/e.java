package com.tencent.liteav.audio.impl.Record;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.liteav.audio.c;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.a;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Vector;

/* compiled from: TXCAudioHWEncoder */
public class e extends Thread {
    private MediaCodec.BufferInfo a;
    private MediaCodecInfo b;
    private MediaFormat c;
    private MediaCodec d;
    private Vector<byte[]> e;
    private WeakReference<com.tencent.liteav.audio.e> f;
    private volatile boolean g = false;
    private volatile boolean h = false;
    private final Object i = new Object();
    private long j = 0;
    private int k = c.a;
    private int l = c.b;
    private int m = c.c;
    private byte[] n;

    static {
        a.d();
    }

    @TargetApi(16)
    public e() {
        super("TXAudioRecordThread");
    }

    public void a(int i2, int i3, int i4, int i5, WeakReference<com.tencent.liteav.audio.e> weakReference) {
        this.f = weakReference;
        this.a = new MediaCodec.BufferInfo();
        this.e = new Vector<>();
        this.k = i3;
        this.l = i4;
        this.m = i5;
        b();
    }

    public void a(byte[] bArr, long j2) {
        if (!(this.e == null || bArr == null)) {
            synchronized (this.e) {
                if (this.e != null) {
                    this.e.add(bArr);
                } else {
                    return;
                }
            }
        }
        synchronized (this.i) {
            this.i.notify();
        }
    }

    public void a() {
        c();
    }

    private void b() {
        int i2 = 32000;
        this.b = a("audio/mp4a-latm");
        if (this.b == null) {
            TXCLog.e("AudioCenter:TXCAudioHWEncoder", "Unable to find an appropriate codec for audio/mp4a-latm");
            return;
        }
        TXCLog.i("AudioCenter:TXCAudioHWEncoder", "selected codec: " + this.b.getName());
        if (this.k >= 32000) {
            i2 = 64000;
        }
        this.c = MediaFormat.createAudioFormat("audio/mp4a-latm", this.k, this.l);
        this.c.setInteger("bitrate", i2);
        this.c.setInteger("channel-count", this.l);
        this.c.setInteger("sample-rate", this.k);
        this.c.setInteger("aac-profile", 2);
        TXCLog.i("AudioCenter:TXCAudioHWEncoder", "format: " + this.c);
        try {
            d();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        start();
    }

    private void c() {
        this.h = true;
    }

    @TargetApi(16)
    private void d() throws IOException {
        if (this.d == null) {
            this.d = MediaCodec.createEncoderByType("audio/mp4a-latm");
            this.d.configure(this.c, (Surface) null, (MediaCrypto) null, 1);
            this.d.start();
            TXCLog.i("AudioCenter:TXCAudioHWEncoder", "prepare finishing");
            this.g = true;
        }
    }

    private void e() {
        if (this.d != null) {
            this.d.stop();
            this.d.release();
            this.d = null;
        }
        this.g = false;
    }

    public void run() {
        boolean isEmpty;
        byte[] remove;
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1024);
        while (!this.h) {
            if (this.g) {
                synchronized (this.e) {
                    isEmpty = this.e.isEmpty();
                }
                if (isEmpty) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                } else {
                    synchronized (this.e) {
                        remove = this.e.remove(0);
                    }
                    if (remove != null) {
                        try {
                            allocateDirect.clear();
                            if (remove.length > allocateDirect.capacity()) {
                                allocateDirect = ByteBuffer.allocateDirect(remove.length);
                            }
                            allocateDirect.clear();
                            allocateDirect.put(remove);
                            allocateDirect.flip();
                            a(allocateDirect, remove.length, f());
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                }
                allocateDirect = allocateDirect;
            } else {
                synchronized (this.i) {
                    try {
                        this.i.wait();
                    } catch (InterruptedException e4) {
                        e4.printStackTrace();
                    }
                }
            }
        }
        e();
        return;
    }

    private void a(ByteBuffer byteBuffer, int i2, long j2) {
        int dequeueOutputBuffer;
        if (!this.h) {
            ByteBuffer[] inputBuffers = this.d.getInputBuffers();
            int dequeueInputBuffer = this.d.dequeueInputBuffer(10000);
            if (dequeueInputBuffer >= 0) {
                ByteBuffer byteBuffer2 = inputBuffers[dequeueInputBuffer];
                byteBuffer2.clear();
                if (byteBuffer != null) {
                    byteBuffer2.put(byteBuffer);
                }
                if (i2 <= 0) {
                    TXCLog.i("AudioCenter:TXCAudioHWEncoder", "send BUFFER_FLAG_END_OF_STREAM");
                    this.d.queueInputBuffer(dequeueInputBuffer, 0, 0, j2, 4);
                } else {
                    this.d.queueInputBuffer(dequeueInputBuffer, 0, i2, j2, 0);
                }
            } else if (dequeueInputBuffer == -1) {
            }
            ByteBuffer[] outputBuffers = this.d.getOutputBuffers();
            do {
                dequeueOutputBuffer = this.d.dequeueOutputBuffer(this.a, 10000);
                if (dequeueOutputBuffer != -1) {
                    if (dequeueOutputBuffer == -3) {
                        outputBuffers = this.d.getOutputBuffers();
                        continue;
                    } else if (dequeueOutputBuffer == -2) {
                        this.d.getOutputFormat();
                        continue;
                    } else if (dequeueOutputBuffer >= 0) {
                        ByteBuffer byteBuffer3 = outputBuffers[dequeueOutputBuffer];
                        if ((this.a.flags & 2) != 0) {
                            TXCLog.d("AudioCenter:TXCAudioHWEncoder", "drain:BUFFER_FLAG_CODEC_CONFIG");
                            this.a.size = 0;
                        }
                        if (this.a.size != 0) {
                            this.a.presentationTimeUs = f();
                            this.n = new byte[byteBuffer3.limit()];
                            byteBuffer3.get(this.n);
                            b(this.n, this.a.presentationTimeUs);
                            this.j = this.a.presentationTimeUs;
                        }
                        this.d.releaseOutputBuffer(dequeueOutputBuffer, false);
                        continue;
                    } else {
                        continue;
                    }
                }
            } while (dequeueOutputBuffer >= 0);
        }
    }

    private long f() {
        long timeTick = TXCTimeUtil.getTimeTick();
        if (timeTick < this.j) {
            return timeTick + (this.j - timeTick);
        }
        return timeTick;
    }

    private static final MediaCodecInfo a(String str) {
        TXCLog.v("AudioCenter:TXCAudioHWEncoder", "selectAudioCodec:");
        int codecCount = MediaCodecList.getCodecCount();
        for (int i2 = 0; i2 < codecCount; i2++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (int i3 = 0; i3 < supportedTypes.length; i3++) {
                    TXCLog.i("AudioCenter:TXCAudioHWEncoder", "supportedType:" + codecInfoAt.getName() + ",MIME=" + supportedTypes[i3]);
                    if (supportedTypes[i3].equalsIgnoreCase(str) && 0 == 0) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private void b(byte[] bArr, long j2) {
        com.tencent.liteav.audio.e eVar;
        if (this.f != null && (eVar = (com.tencent.liteav.audio.e) this.f.get()) != null) {
            eVar.b(bArr, j2, this.k, this.l, this.m);
        }
    }
}
