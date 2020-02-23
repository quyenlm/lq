package com.tencent.liteav.videodecoder;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;
import com.tencent.liteav.basic.c.a;
import com.tencent.liteav.basic.f.b;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.qqgamemi.util.GlobalUtil;
import com.tencent.rtmp2.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* compiled from: TXCVideoMediaCodecDecoder */
public class c implements a {
    private MediaCodec.BufferInfo a = new MediaCodec.BufferInfo();
    private MediaCodec b = null;
    private String c = GlobalUtil.AVC_MIME_TYPE;
    private int d = 540;
    private int e = 960;
    private long f = 0;
    private long g = 0;
    private boolean h = true;
    private boolean i = false;
    private boolean j = false;
    private Surface k = null;
    private int l = 0;
    private ArrayList<b> m = new ArrayList<>();
    private d n;
    private WeakReference<a> o;

    public void setListener(d dVar) {
        this.n = dVar;
    }

    public void setNotifyListener(WeakReference<a> weakReference) {
        this.o = weakReference;
    }

    public int config(Surface surface) {
        if (surface == null) {
            return -1;
        }
        this.k = surface;
        return 0;
    }

    public void decode(b bVar) {
        boolean z = true;
        if (bVar.i != 1) {
            z = false;
        }
        a(z);
        this.m.add(bVar);
        while (!this.m.isEmpty()) {
            int size = this.m.size();
            b();
            if (size == this.m.size()) {
                return;
            }
        }
    }

    public int start(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z, boolean z2) {
        return a(byteBuffer, byteBuffer2, z2);
    }

    public void stop() {
        a();
    }

    public boolean isHevc() {
        return this.j;
    }

    private int a(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z) {
        int i2 = 1;
        int i3 = -1;
        try {
            if (this.b != null || this.k == null) {
                TXCLog.e("MediaCodecDecoder", "decode: init decoder error, can not init for decoder=" + this.b + ",surface=" + this.k);
                return -1;
            }
            this.j = z;
            if (this.j) {
                this.c = "video/hevc";
            } else {
                this.c = GlobalUtil.AVC_MIME_TYPE;
            }
            MediaFormat createVideoFormat = MediaFormat.createVideoFormat(this.c, this.d, this.e);
            if (byteBuffer != null) {
                createVideoFormat.setByteBuffer("csd-0", byteBuffer);
            }
            if (byteBuffer2 != null) {
                createVideoFormat.setByteBuffer("csd-1", byteBuffer2);
            }
            this.b = MediaCodec.createDecoderByType(this.c);
            try {
                this.b.configure(createVideoFormat, this.k, (MediaCrypto) null, 0);
                this.b.setVideoScalingMode(1);
                this.b.start();
                i2 = 4;
                TXCLog.w("MediaCodecDecoder", "decode: start decoder success, is hevc: " + this.j);
                try {
                    this.l = 0;
                    return 0;
                } catch (Exception e2) {
                    e = e2;
                    i3 = 0;
                }
            } catch (Exception e3) {
                e = e3;
                if (this.b != null) {
                    try {
                        this.b.release();
                        TXCLog.w("MediaCodecDecoder", "decode: , decoder release success");
                    } catch (Exception e4) {
                        TXCLog.e("MediaCodecDecoder", "decode: , decoder release exception: " + e.toString());
                        e4.printStackTrace();
                    } finally {
                        this.b = null;
                    }
                }
                TXCLog.e("MediaCodecDecoder", "decode: init decoder " + i2 + " step exception: " + e.toString());
                e.printStackTrace();
                f();
                return i3;
            }
        } catch (Exception e5) {
            e = e5;
            i2 = 0;
        }
    }

    private void a() {
        if (this.b != null) {
            try {
                this.b.stop();
                TXCLog.w("MediaCodecDecoder", "decode: stop decoder sucess");
                try {
                    this.b.release();
                    TXCLog.w("MediaCodecDecoder", "decode: release decoder sucess");
                } catch (Exception e2) {
                    TXCLog.e("MediaCodecDecoder", "decode: release decoder exception: " + e2.toString());
                    e2.printStackTrace();
                } finally {
                    this.b = null;
                }
            } catch (Exception e3) {
                TXCLog.e("MediaCodecDecoder", "decode: stop decoder Exception: " + e3.toString());
                e3.printStackTrace();
                try {
                    this.b.release();
                    TXCLog.w("MediaCodecDecoder", "decode: release decoder sucess");
                } catch (Exception e4) {
                    TXCLog.e("MediaCodecDecoder", "decode: release decoder exception: " + e4.toString());
                    e4.printStackTrace();
                } finally {
                    this.b = null;
                }
            } catch (Throwable th) {
                try {
                    this.b.release();
                    TXCLog.w("MediaCodecDecoder", "decode: release decoder sucess");
                } catch (Exception e5) {
                    TXCLog.e("MediaCodecDecoder", "decode: release decoder exception: " + e5.toString());
                    e5.printStackTrace();
                } finally {
                    this.b = null;
                }
                throw th;
            }
            this.m.clear();
            this.f = 0;
            this.h = true;
        }
    }

    @TargetApi(16)
    private void b() {
        int i2;
        int i3;
        if (this.b == null) {
            TXCLog.e("MediaCodecDecoder", "null decoder");
            return;
        }
        b bVar = this.m.get(0);
        if (bVar == null || bVar.a.length == 0) {
            TXCLog.e("MediaCodecDecoder", "decode: empty buffer");
            this.m.remove(0);
            return;
        }
        ByteBuffer[] inputBuffers = this.b.getInputBuffers();
        if (inputBuffers == null || inputBuffers.length == 0) {
            TXCLog.e("MediaCodecDecoder", "decode: getInputBuffers failed");
            return;
        }
        try {
            i2 = this.b.dequeueInputBuffer(10000);
        } catch (Exception e2) {
            TXCLog.e("MediaCodecDecoder", "decode: dequeueInputBuffer Exception!! " + e2);
            i2 = -10000;
        }
        if (i2 >= 0) {
            inputBuffers[i2].put(bVar.a);
            this.b.queueInputBuffer(i2, 0, bVar.a.length, bVar.g, 0);
            this.m.remove(0);
            if (this.f == 0) {
                TXCLog.w("MediaCodecDecoder", "decode: input buffer available, dequeueInputBuffer index: " + i2);
            }
        } else {
            TXCLog.w("MediaCodecDecoder", "decode: input buffer not available, dequeueInputBuffer failed");
        }
        try {
            i3 = this.b.dequeueOutputBuffer(this.a, 10000);
        } catch (Exception e3) {
            g();
            TXCLog.e("MediaCodecDecoder", "decode: dequeueOutputBuffer exception!!" + e3);
            i3 = -10000;
        }
        if (i3 >= 0) {
            a(i3, this.a.presentationTimeUs, this.a.presentationTimeUs);
            this.l = 0;
        } else if (i3 == -1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e4) {
                e4.printStackTrace();
            }
            TXCLog.d("MediaCodecDecoder", "decode: no output from decoder available when timeout");
            g();
        } else if (i3 == -3) {
            TXCLog.d("MediaCodecDecoder", "decode: output buffers changed");
        } else if (i3 == -2) {
            c();
        } else {
            TXCLog.e("MediaCodecDecoder", "decode: unexpected result from decoder.dequeueOutputBuffer: " + i3);
        }
    }

    private void a(int i2, long j2, long j3) {
        this.b.releaseOutputBuffer(i2, true);
        if ((this.a.flags & 4) != 0) {
            TXCLog.d("MediaCodecDecoder", "output EOS");
        }
        try {
            if (this.n != null) {
                this.n.a((SurfaceTexture) null, this.d, this.e, j2, j3);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        d();
    }

    private void c() {
        MediaFormat outputFormat = this.b.getOutputFormat();
        TXCLog.d("MediaCodecDecoder", "decode output format changed: " + outputFormat);
        int integer = outputFormat.getInteger("width");
        int integer2 = outputFormat.getInteger("height");
        int min = Math.min(Math.abs(outputFormat.getInteger("crop-right") - outputFormat.getInteger("crop-left")) + 1, integer);
        int min2 = Math.min(Math.abs(outputFormat.getInteger("crop-bottom") - outputFormat.getInteger("crop-top")) + 1, integer2);
        if (min != this.d || min2 != this.e) {
            this.d = min;
            this.e = min2;
            try {
                if (this.n != null) {
                    this.n.a(this.d, this.e);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            TXCLog.d("MediaCodecDecoder", "decode: video size change to w:" + min + ",h:" + min2);
        } else if (this.h) {
            this.h = false;
            if (this.n != null) {
                this.n.a(this.d, this.e);
            }
        }
    }

    private void d() {
        if (this.f == 0) {
            TXCLog.w("MediaCodecDecoder", "decode first frame sucess");
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (this.f > 0 && currentTimeMillis > this.f + 1000 && currentTimeMillis > this.g + 2000 && this.g != 0) {
            TXCLog.e("MediaCodecDecoder", "frame interval[" + (currentTimeMillis - this.f) + "] > " + 1000);
            this.g = currentTimeMillis;
        }
        if (this.g == 0) {
            this.g = currentTimeMillis;
        }
        this.f = currentTimeMillis;
    }

    private boolean e() {
        if (Build.VERSION.SDK_INT >= 21) {
            for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(1).getCodecInfos()) {
                for (String contains : mediaCodecInfo.getSupportedTypes()) {
                    if (contains.contains("video/hevc")) {
                        TXCLog.e("MediaCodecDecoder", "decode: video/hevc MediaCodecInfo: " + mediaCodecInfo.getName() + ",encoder:" + mediaCodecInfo.isEncoder());
                        return true;
                    }
                }
            }
            return false;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            int codecCount = MediaCodecList.getCodecCount();
            for (int i2 = 0; i2 < codecCount; i2++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
                for (String contains2 : codecInfoAt.getSupportedTypes()) {
                    if (contains2.contains("video/hevc")) {
                        TXCLog.e("MediaCodecDecoder", "video/hevc MediaCodecInfo: " + codecInfoAt.getName() + ",encoder:" + codecInfoAt.isEncoder());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void f() {
        if (!this.i) {
            TXCLog.w("MediaCodecDecoder", "decode hw decode error, hevc: " + this.j);
            if (this.j) {
                com.tencent.liteav.basic.util.a.a(this.o, (int) TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL, "h265解码失败");
            } else {
                com.tencent.liteav.basic.util.a.a(this.o, (int) TXLiveConstants.PLAY_WARNING_HW_ACCELERATION_FAIL, "硬解启动失败，采用软解");
            }
            this.i = true;
        }
    }

    private void g() {
        if (this.l >= 40) {
            f();
            this.l = 0;
            return;
        }
        this.l++;
    }

    private void a(boolean z) {
        if (this.j != z) {
            this.j = z;
            if (!this.i) {
                if (!this.j || e()) {
                    a();
                    a((ByteBuffer) null, (ByteBuffer) null, this.j);
                    return;
                }
                a();
                f();
            }
        }
    }
}
