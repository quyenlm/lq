package com.tencent.liteav.audio.impl.Play;

import android.media.MediaCodec;
import android.media.MediaFormat;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.d;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/* compiled from: TXCAudioHWDecoder */
public class a implements Runnable {
    private static final String a = ("AudioCenter:" + a.class.getSimpleName());
    private WeakReference<d> b = null;
    private MediaCodec c = null;
    private MediaCodec.BufferInfo d;
    private MediaFormat e;
    private long f = 0;
    private volatile boolean g = false;
    private Vector<com.tencent.liteav.basic.f.a> h;
    private List i;
    private Thread j = null;

    public void a(WeakReference<d> weakReference) {
        if (this.g) {
            b();
        }
        this.b = weakReference;
        this.f = 0;
        this.h = new Vector<>();
        this.i = new ArrayList();
        this.g = true;
        this.j = new Thread(this);
        this.j.setName(a);
        this.j.start();
    }

    public void a(com.tencent.liteav.basic.f.a aVar) {
        if (this.g) {
            synchronized (this.h) {
                if (this.h != null) {
                    this.h.add(aVar);
                }
            }
        }
    }

    public long a() {
        if (this.e == null) {
            return 0;
        }
        float integer = (float) this.e.getInteger("sample-rate");
        if (integer != 0.0f) {
            return (long) (((((float) this.i.size()) * 1024.0f) * 1000.0f) / integer);
        }
        return 0;
    }

    public void b() {
        this.g = false;
        if (this.j != null) {
            try {
                this.j.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.j = null;
        }
    }

    public void run() {
        boolean isEmpty;
        int i2;
        ByteBuffer[] byteBufferArr;
        com.tencent.liteav.basic.f.a remove;
        int i3;
        while (this.g) {
            synchronized (this.h) {
                isEmpty = this.h.isEmpty();
            }
            if (isEmpty) {
                try {
                    Thread.sleep(10);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                if (this.c != null) {
                    try {
                        byteBufferArr = this.c.getInputBuffers();
                        i3 = 1;
                        try {
                            int dequeueInputBuffer = this.c.dequeueInputBuffer(10000);
                            if (dequeueInputBuffer >= 0) {
                                i2 = dequeueInputBuffer;
                            } else {
                                return;
                            }
                        } catch (Exception e3) {
                            e = e3;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        i3 = 0;
                        TXCLog.e(a, "Exception. step: " + i3 + ", error: " + e);
                        return;
                    }
                } else {
                    i2 = -1;
                    byteBufferArr = null;
                }
                synchronized (this.h) {
                    remove = this.h.remove(0);
                }
                if (remove.d == com.tencent.liteav.basic.a.a.k) {
                    b(remove);
                } else if (remove.d == com.tencent.liteav.basic.a.a.l) {
                    this.i.add(new Long(remove.e));
                    a(remove, byteBufferArr, i2);
                } else {
                    TXCLog.e(a, "not support audio format");
                }
            }
        }
        if (this.c != null) {
            this.c.stop();
            this.c.release();
            this.c = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00d5  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01aa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int b(com.tencent.liteav.basic.f.a r10) {
        /*
            r9 = this;
            r7 = 2
            r8 = 0
            r3 = 1
            r1 = 0
            byte[] r0 = r10.f
            int r0 = r0.length
            if (r0 == r7) goto L_0x0024
            java.lang.String r0 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "aac seq header len not equal to 2 , with len "
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = r10.f
            int r4 = r4.length
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            com.tencent.liteav.basic.log.TXCLog.w(r0, r2)
        L_0x0024:
            byte[] r0 = r10.f
            if (r0 == 0) goto L_0x01fe
            byte[] r0 = r10.f
            byte r0 = r0[r1]
            r0 = r0 & 248(0xf8, float:3.48E-43)
            int r0 = r0 >> 3
            byte[] r0 = r10.f
            byte r0 = r0[r1]
            r0 = r0 & 7
            int r0 = r0 << 1
            byte[] r2 = r10.f
            byte r2 = r2[r3]
            r2 = r2 & 128(0x80, float:1.794E-43)
            int r2 = r2 >> 7
            r0 = r0 | r2
            int r2 = com.tencent.liteav.audio.impl.a.a((int) r0)
            byte[] r0 = r10.f
            byte r0 = r0[r3]
            r0 = r0 & 120(0x78, float:1.68E-43)
            int r4 = r0 >> 3
            java.lang.String r0 = "audio/mp4a-latm"
            android.media.MediaFormat r0 = android.media.MediaFormat.createAudioFormat(r0, r2, r4)
            r9.e = r0
            android.media.MediaFormat r0 = r9.e
            java.lang.String r5 = "bitrate"
            r6 = 64000(0xfa00, float:8.9683E-41)
            r0.setInteger(r5, r6)
            android.media.MediaFormat r0 = r9.e
            java.lang.String r5 = "is-adts"
            r0.setInteger(r5, r1)
            android.media.MediaFormat r0 = r9.e
            java.lang.String r5 = "aac-profile"
            r0.setInteger(r5, r7)
            java.lang.String r0 = a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "audio decoder media format: "
            java.lang.StringBuilder r5 = r5.append(r6)
            android.media.MediaFormat r6 = r9.e
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.tencent.liteav.basic.log.TXCLog.i(r0, r5)
            java.lang.ref.WeakReference<com.tencent.liteav.audio.d> r0 = r9.b
            if (r0 == 0) goto L_0x00a5
            java.lang.ref.WeakReference<com.tencent.liteav.audio.d> r0 = r9.b
            java.lang.Object r0 = r0.get()
            com.tencent.liteav.audio.d r0 = (com.tencent.liteav.audio.d) r0
            com.tencent.liteav.basic.f.a r5 = new com.tencent.liteav.basic.f.a
            r5.<init>()
            int r6 = com.tencent.liteav.basic.a.a.h
            r5.c = r6
            r5.b = r4
            r5.a = r2
            if (r0 == 0) goto L_0x00a5
            r0.onPlayAudioInfoChanged(r5, r5)
        L_0x00a5:
            android.media.MediaCodec r0 = r9.c
            if (r0 == 0) goto L_0x00b3
            android.media.MediaCodec r0 = r9.c     // Catch:{ Exception -> 0x00e9 }
            r0.stop()     // Catch:{ Exception -> 0x00e9 }
            android.media.MediaCodec r0 = r9.c     // Catch:{ Exception -> 0x0209 }
            r0.release()     // Catch:{ Exception -> 0x0209 }
        L_0x00b3:
            java.lang.String r0 = "audio/mp4a-latm"
            android.media.MediaCodec r0 = android.media.MediaCodec.createDecoderByType(r0)     // Catch:{ IOException -> 0x010e }
            r9.c = r0     // Catch:{ IOException -> 0x010e }
        L_0x00bb:
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 21
            if (r0 < r2) goto L_0x01aa
            r0 = r1
        L_0x00c2:
            android.media.MediaCodec r2 = r9.c     // Catch:{ CodecException -> 0x012f }
            android.media.MediaFormat r4 = r9.e     // Catch:{ CodecException -> 0x012f }
            r5 = 0
            r6 = 0
            r7 = 0
            r2.configure(r4, r5, r6, r7)     // Catch:{ CodecException -> 0x012f }
            android.media.MediaCodec r2 = r9.c     // Catch:{ CodecException -> 0x0205 }
            r2.start()     // Catch:{ CodecException -> 0x0205 }
        L_0x00d1:
            android.media.MediaCodec r0 = r9.c
            if (r0 == 0) goto L_0x00e6
            android.media.MediaCodec r0 = r9.c
            java.nio.ByteBuffer[] r0 = r0.getInputBuffers()
            android.media.MediaCodec r1 = r9.c
            r2 = 10000(0x2710, double:4.9407E-320)
            int r1 = r1.dequeueInputBuffer(r2)
            r9.a(r10, r0, r1)
        L_0x00e6:
            int r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK
        L_0x00e8:
            return r0
        L_0x00e9:
            r0 = move-exception
            r2 = r1
        L_0x00eb:
            java.lang.String r4 = a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "hw audio decoder release error: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r2 = r5.append(r2)
            java.lang.String r5 = ". error: "
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.liteav.basic.log.TXCLog.e(r4, r0)
            goto L_0x00b3
        L_0x010e:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r2 = a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "createDecoderByType exception: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r4.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.liteav.basic.log.TXCLog.e(r2, r0)
            goto L_0x00bb
        L_0x012f:
            r2 = move-exception
            r4 = r1
        L_0x0131:
            java.lang.String r5 = a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "CodecException: "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r7 = ". step: "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r4 = r6.append(r4)
            java.lang.String r6 = ", mediaformat: "
            java.lang.StringBuilder r4 = r4.append(r6)
            android.media.MediaFormat r6 = r9.e
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r4 = r4.toString()
            com.tencent.liteav.basic.log.TXCLog.e(r5, r4)
            int r0 = r0 + 1
            if (r0 <= r3) goto L_0x0175
            java.lang.String r0 = a
            java.lang.String r1 = "decoder start error!"
            com.tencent.liteav.basic.log.TXCLog.e(r0, r1)
            android.media.MediaCodec r0 = r9.c
            r0.release()
            r9.c = r8
            int r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE
            goto L_0x00e8
        L_0x0175:
            boolean r4 = r2.isRecoverable()
            if (r4 == 0) goto L_0x0185
            android.media.MediaCodec r2 = r9.c     // Catch:{ Exception -> 0x0182 }
            r2.stop()     // Catch:{ Exception -> 0x0182 }
            goto L_0x00c2
        L_0x0182:
            r2 = move-exception
            goto L_0x00c2
        L_0x0185:
            boolean r2 = r2.isTransient()
            if (r2 == 0) goto L_0x0198
            r4 = 20
            java.lang.Thread.sleep(r4)     // Catch:{ InterruptedException -> 0x0192 }
            goto L_0x00c2
        L_0x0192:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00c2
        L_0x0198:
            java.lang.String r0 = a
            java.lang.String r1 = "decoder cath unrecoverable error!"
            com.tencent.liteav.basic.log.TXCLog.e(r0, r1)
            android.media.MediaCodec r0 = r9.c
            r0.release()
            r9.c = r8
            int r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE
            goto L_0x00e8
        L_0x01aa:
            r0 = r1
        L_0x01ab:
            android.media.MediaCodec r2 = r9.c     // Catch:{ IllegalStateException -> 0x0202 }
            android.media.MediaFormat r4 = r9.e     // Catch:{ IllegalStateException -> 0x0202 }
            r5 = 0
            r6 = 0
            r7 = 0
            r2.configure(r4, r5, r6, r7)     // Catch:{ IllegalStateException -> 0x0202 }
            android.media.MediaCodec r2 = r9.c     // Catch:{ IllegalStateException -> 0x01bc }
            r2.start()     // Catch:{ IllegalStateException -> 0x01bc }
            goto L_0x00d1
        L_0x01bc:
            r2 = move-exception
            r4 = r3
        L_0x01be:
            java.lang.String r5 = a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "CodecException1: "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r2 = r6.append(r2)
            java.lang.String r6 = ". step: "
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            com.tencent.liteav.basic.log.TXCLog.e(r5, r2)
            int r0 = r0 + 1
            if (r0 <= r3) goto L_0x01f6
            java.lang.String r0 = a
            java.lang.String r1 = "decoder start error!"
            com.tencent.liteav.basic.log.TXCLog.e(r0, r1)
            android.media.MediaCodec r0 = r9.c
            r0.release()
            r9.c = r8
            int r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE
            goto L_0x00e8
        L_0x01f6:
            android.media.MediaCodec r2 = r9.c     // Catch:{ Exception -> 0x01fc }
            r2.reset()     // Catch:{ Exception -> 0x01fc }
            goto L_0x01ab
        L_0x01fc:
            r2 = move-exception
            goto L_0x01ab
        L_0x01fe:
            int r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS
            goto L_0x00e8
        L_0x0202:
            r2 = move-exception
            r4 = r1
            goto L_0x01be
        L_0x0205:
            r2 = move-exception
            r4 = r3
            goto L_0x0131
        L_0x0209:
            r0 = move-exception
            r2 = r3
            goto L_0x00eb
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.audio.impl.Play.a.b(com.tencent.liteav.basic.f.a):int");
    }

    private int a(com.tencent.liteav.basic.f.a aVar, ByteBuffer[] byteBufferArr, int i2) {
        ByteBuffer[] byteBufferArr2;
        d dVar;
        if (i2 >= 0) {
            try {
                if (aVar.f != null) {
                    ByteBuffer byteBuffer = byteBufferArr[i2];
                    byteBuffer.clear();
                    byteBuffer.put(aVar.f);
                }
                if (aVar == null || aVar.f.length <= 0) {
                    this.c.queueInputBuffer(i2, 0, 0, c(), 4);
                } else {
                    this.c.queueInputBuffer(i2, 0, aVar.f.length, c(), 0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (i2 == -1) {
            return -1;
        }
        ByteBuffer[] outputBuffers = this.c.getOutputBuffers();
        if (this.d == null) {
            this.d = new MediaCodec.BufferInfo();
        }
        while (true) {
            int dequeueOutputBuffer = this.c.dequeueOutputBuffer(this.d, 10000);
            if (dequeueOutputBuffer == -3) {
                byteBufferArr2 = this.c.getOutputBuffers();
            } else {
                if (dequeueOutputBuffer == -2) {
                    this.e = this.c.getOutputFormat();
                    if (this.b != null) {
                        d dVar2 = (d) this.b.get();
                        com.tencent.liteav.basic.f.a aVar2 = new com.tencent.liteav.basic.f.a();
                        aVar2.c = com.tencent.liteav.basic.a.a.h;
                        aVar2.b = this.e.getInteger("channel-count");
                        aVar2.a = this.e.getInteger("sample-rate");
                        if (dVar2 != null) {
                            dVar2.onPlayAudioInfoChanged(aVar2, aVar2);
                        }
                        byteBufferArr2 = outputBuffers;
                    }
                } else if (dequeueOutputBuffer >= 0) {
                    ByteBuffer byteBuffer2 = outputBuffers[dequeueOutputBuffer];
                    byteBuffer2.position(this.d.offset);
                    byteBuffer2.limit(this.d.offset + this.d.size);
                    byte[] bArr = new byte[this.d.size];
                    byteBuffer2.get(bArr);
                    long longValue = ((Long) this.i.get(0)).longValue();
                    this.i.remove(0);
                    if (!(this.b == null || (dVar = (d) this.b.get()) == null)) {
                        dVar.onPlayPcmData(bArr, longValue);
                    }
                    this.c.releaseOutputBuffer(dequeueOutputBuffer, false);
                }
                byteBufferArr2 = outputBuffers;
            }
            if (dequeueOutputBuffer < 0) {
                break;
            }
            outputBuffers = byteBufferArr2;
        }
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
    }

    private long c() {
        long timeTick = TXCTimeUtil.getTimeTick();
        if (timeTick < this.f) {
            timeTick += this.f - timeTick;
        }
        this.f = timeTick;
        return timeTick;
    }
}
