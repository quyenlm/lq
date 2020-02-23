package com.tencent.liteav.videoencoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import com.tencent.liteav.basic.module.a;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import javax.microedition.khronos.egl.EGLContext;

/* compiled from: TXIVideoEncoder */
public class b extends a {
    protected volatile WeakReference<c> listenerWeakReference = new WeakReference<>((Object) null);
    protected com.tencent.liteav.basic.d.b mEncodeFilter;
    private boolean mEncodeFirstGOP = false;
    protected EGLContext mGLContextExternal = null;
    protected boolean mInit;
    protected com.tencent.liteav.basic.d.b mInputFilter;
    protected int mInputHeight = 0;
    protected int mInputTextureID = -1;
    protected int mInputWidth = 0;
    protected int mOutputHeight = 0;
    protected int mOutputWidth = 0;
    private long mVideoGOPEncode = 0;

    public int start(TXSVideoEncoderParam tXSVideoEncoderParam) {
        if (tXSVideoEncoderParam != null) {
            this.mOutputWidth = tXSVideoEncoderParam.width;
            this.mOutputHeight = tXSVideoEncoderParam.height;
            this.mGLContextExternal = tXSVideoEncoderParam.glContext;
        }
        this.mVideoGOPEncode = 0;
        this.mEncodeFirstGOP = false;
        return 10000002;
    }

    public void stop() {
    }

    public void setListener(c cVar) {
        this.listenerWeakReference = new WeakReference<>(cVar);
    }

    public void setFPS(int i) {
    }

    public void setBitrate(int i) {
    }

    public long getRealFPS() {
        return 0;
    }

    public long getRealBitrate() {
        return 0;
    }

    public int getVideoWidth() {
        return this.mOutputWidth;
    }

    public int getVideoHeight() {
        return this.mOutputHeight;
    }

    public long pushVideoFrame(int i, int i2, int i3, long j) {
        return 10000002;
    }

    /* access modifiers changed from: protected */
    public void callDelegate(int i) {
        callDelegate((byte[]) null, 0, 0, 0, 0, 0, 0, 0, i, (ByteBuffer) null, (MediaCodec.BufferInfo) null);
    }

    /* access modifiers changed from: protected */
    public void callDelegate(byte[] bArr, int i, long j, long j2, long j3, long j4, long j5, long j6, int i2, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        MediaCodec.BufferInfo bufferInfo2;
        c cVar;
        ByteBuffer asReadOnlyBuffer = byteBuffer == null ? null : byteBuffer.asReadOnlyBuffer();
        if (bufferInfo == null) {
            bufferInfo2 = null;
        } else {
            bufferInfo2 = new MediaCodec.BufferInfo();
        }
        if (bufferInfo2 != null) {
            bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
        }
        if (this.listenerWeakReference != null && (cVar = (c) this.listenerWeakReference.get()) != null) {
            com.tencent.liteav.basic.f.b bVar = new com.tencent.liteav.basic.f.b();
            bVar.a = bArr;
            bVar.b = i;
            bVar.c = j;
            bVar.d = j2;
            bVar.e = j3;
            bVar.f = j4;
            bVar.g = j5;
            bVar.h = j6;
            bVar.j = asReadOnlyBuffer;
            bVar.k = bufferInfo2;
            cVar.a(bVar, i2);
            setStatusValue(4002, Integer.valueOf((int) getRealBitrate()));
            setStatusValue(4001, Double.valueOf((double) getRealFPS()));
            if (i == 0) {
                if (this.mVideoGOPEncode != 0) {
                    this.mEncodeFirstGOP = true;
                    setStatusValue(4003, Long.valueOf(this.mVideoGOPEncode));
                }
                this.mVideoGOPEncode = 1;
                return;
            }
            this.mVideoGOPEncode++;
            if (!this.mEncodeFirstGOP) {
                setStatusValue(4003, Long.valueOf(this.mVideoGOPEncode));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void callDelegate(MediaFormat mediaFormat) {
        c cVar;
        if (this.listenerWeakReference != null && (cVar = (c) this.listenerWeakReference.get()) != null) {
            cVar.a(mediaFormat);
        }
    }
}
