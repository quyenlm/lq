package com.tencent.liteav.videodecoder;

import android.view.Surface;
import com.tencent.liteav.basic.c.a;
import com.tencent.liteav.basic.f.b;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

public class TXCVideoFfmpegDecoder implements a {
    private boolean mFirstDec;
    private d mListener;
    private long mNativeDecoder;
    private long mNativeNotify;
    private ByteBuffer mPps;
    private byte[] mRawData;
    private ByteBuffer mSps;
    private int mVideoHeight;
    private int mVideoWidth;

    private static native void nativeClassInit();

    private native boolean nativeDecode(byte[] bArr, long j, long j2);

    private native void nativeInit(WeakReference<TXCVideoFfmpegDecoder> weakReference, boolean z);

    private native void nativeLoadRawData(byte[] bArr, long j, int i);

    private native void nativeRelease();

    public void setListener(d dVar) {
        this.mListener = dVar;
    }

    public void setNotifyListener(WeakReference<a> weakReference) {
    }

    public int config(Surface surface) {
        return 0;
    }

    public void decode(b bVar) {
        if (this.mFirstDec) {
            if (!(this.mSps == null || this.mPps == null)) {
                byte[] array = this.mSps.array();
                byte[] array2 = this.mPps.array();
                byte[] bArr = new byte[(array.length + array2.length)];
                System.arraycopy(array, 0, bArr, 0, array.length);
                System.arraycopy(array2, 0, bArr, array.length, array2.length);
                nativeDecode(bArr, bVar.g - 1, bVar.h - 1);
            }
            this.mFirstDec = false;
        }
        nativeDecode(bVar.a, bVar.g, bVar.h);
    }

    public int start(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z, boolean z2) {
        this.mSps = byteBuffer;
        this.mPps = byteBuffer2;
        this.mFirstDec = true;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        nativeInit(new WeakReference(this), z);
        return 0;
    }

    public void stop() {
        nativeRelease();
    }

    public boolean isHevc() {
        return false;
    }

    private static void postEventFromNative(WeakReference<TXCVideoFfmpegDecoder> weakReference, long j, int i, int i2, long j2, long j3) {
        d dVar;
        TXCVideoFfmpegDecoder tXCVideoFfmpegDecoder = (TXCVideoFfmpegDecoder) weakReference.get();
        if (tXCVideoFfmpegDecoder != null && (dVar = tXCVideoFfmpegDecoder.mListener) != null) {
            dVar.a(j, i, i2, j2, j3);
            if (tXCVideoFfmpegDecoder.mVideoWidth != i || tXCVideoFfmpegDecoder.mVideoHeight != i2) {
                tXCVideoFfmpegDecoder.mVideoWidth = i;
                tXCVideoFfmpegDecoder.mVideoHeight = i2;
                dVar.a(tXCVideoFfmpegDecoder.mVideoWidth, tXCVideoFfmpegDecoder.mVideoHeight);
            }
        }
    }

    public void loadNativeData(byte[] bArr, long j, int i) {
        nativeLoadRawData(bArr, j, i);
    }

    static {
        com.tencent.liteav.basic.util.a.d();
        nativeClassInit();
    }
}
