package com.tencent.liteav.audio;

import android.media.AudioTrack;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.a;
import java.lang.ref.WeakReference;

public class TXCLiveBGMPlayer implements Runnable {
    private static final int PLAY_ERR_AUDIO_TRACK = -3;
    private static final int PLAY_ERR_AUDIO_TRACK_PLAY = -4;
    private static final int PLAY_ERR_FILE_NOTFOUND = -2;
    private static final int PLAY_ERR_OPEN = -1;
    private static final int PLAY_SUCCESS = 0;
    private static final String TAG = ("AudioCenter:" + TXCLiveBGMPlayer.class.getSimpleName());
    private static TXCLiveBGMPlayer instance;
    private int mAECType = TXEAudioDef.TXE_AEC_NONE;
    private String mFilePath = null;
    private boolean mIsPause = false;
    private boolean mIsRunning = false;
    private Thread mThread = null;
    private WeakReference<f> mWeakListener = null;

    private native int nativeGetBitsPerChannel();

    private native int nativeGetChannels();

    private native long nativeGetCurDurationMS();

    private native long nativeGetCurPtsMS();

    private native long nativeGetDurationMS(String str);

    private native int nativeGetSampleRate();

    private native void nativePause();

    private native int nativeRead(byte[] bArr, int i);

    private native void nativeResume();

    private native void nativeSetVolume(float f);

    private native boolean nativeStartPlay(String str, TXCLiveBGMPlayer tXCLiveBGMPlayer);

    private native void nativeStopPlay();

    static {
        a.d();
    }

    public static TXCLiveBGMPlayer getInstance() {
        if (instance == null) {
            synchronized (TXCLiveBGMPlayer.class) {
                if (instance == null) {
                    instance = new TXCLiveBGMPlayer();
                }
            }
        }
        return instance;
    }

    private TXCLiveBGMPlayer() {
    }

    public synchronized void setOnPlayListener(f fVar) {
        if (fVar == null) {
            this.mWeakListener = null;
        }
        this.mWeakListener = new WeakReference<>(fVar);
    }

    public boolean startPlay(String str, int i) {
        int i2 = TXEAudioDef.TXE_AEC_NONE;
        if (str == null || str.isEmpty()) {
            TXCLog.e(TAG, "start live bgm failed! invalid params!");
            return false;
        }
        stopPlay();
        this.mAECType = i2;
        this.mFilePath = str;
        this.mIsRunning = true;
        onPlayStart();
        if (!nativeStartPlay(this.mFilePath, this)) {
            onPlayEnd(-1);
            return false;
        }
        if (this.mAECType != TXEAudioDef.TXE_AEC_TRAE && this.mThread == null) {
            this.mThread = new Thread(this, "BGMPlayer");
            this.mThread.start();
        }
        TXCLog.i(TAG, "startPlay filePath = " + str);
        return true;
    }

    public void stopPlay() {
        this.mIsRunning = false;
        long currentTimeMillis = System.currentTimeMillis();
        if (!(this.mThread == null || !this.mThread.isAlive() || Thread.currentThread().getId() == this.mThread.getId())) {
            try {
                this.mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.mThread = null;
        nativeStopPlay();
        this.mIsPause = false;
        TXCLog.i(TAG, "stopBGMPlay cost(MS): " + (System.currentTimeMillis() - currentTimeMillis));
    }

    public void switchAecType(int i) {
        if (!this.mIsRunning) {
            TXCLog.w(TAG, "未开始播放BGM，不能切换AEC Type");
        } else if (this.mAECType == i) {
            TXCLog.i(TAG, "无需切换AEC Type. aecType = " + this.mAECType);
        } else {
            TXCLog.i(TAG, "切换AEC Type为 " + i);
            this.mAECType = i;
            if (this.mAECType == TXEAudioDef.TXE_AEC_TRAE) {
                if (!(this.mThread == null || !this.mThread.isAlive() || Thread.currentThread().getId() == this.mThread.getId())) {
                    try {
                        this.mThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.mThread = null;
            } else if (this.mThread == null) {
                this.mThread = new Thread(this, "BGMPlayer");
                this.mThread.start();
            }
        }
    }

    public void pause() {
        TXCLog.i(TAG, "pause");
        this.mIsPause = true;
        nativePause();
    }

    public void resume() {
        TXCLog.i(TAG, "resume");
        this.mIsPause = false;
        nativeResume();
    }

    public void setVolume(float f) {
        nativeSetVolume(f);
    }

    public long getMusicDuration(String str) {
        return nativeGetDurationMS(str);
    }

    private void onPlayStart() {
        final f fVar = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                fVar = (f) this.mWeakListener.get();
            }
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (fVar != null) {
                    fVar.a();
                }
            }
        });
    }

    private void onPlayEnd(final int i) {
        final f fVar = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                fVar = (f) this.mWeakListener.get();
            }
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (fVar != null) {
                    fVar.a(i);
                }
            }
        });
    }

    private void onPlayProgress(long j, long j2) {
        f fVar = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                fVar = (f) this.mWeakListener.get();
            }
        }
        if (fVar != null) {
            fVar.a(j, j2);
        }
    }

    private void nativeOPlayProgress(long j, long j2) {
        onPlayProgress(j, j2);
        if (j == j2) {
            onPlayEnd(0);
        }
    }

    public void run() {
        AudioTrack audioTrack;
        int i;
        int i2;
        int i3 = 3;
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mFilePath == null || this.mFilePath.isEmpty()) {
            i = -2;
            audioTrack = null;
        } else {
            int nativeGetSampleRate = nativeGetSampleRate();
            int nativeGetChannels = nativeGetChannels();
            int nativeGetBitsPerChannel = nativeGetBitsPerChannel();
            if (nativeGetChannels == 1) {
                i2 = 2;
            } else {
                i2 = 3;
            }
            if (nativeGetBitsPerChannel != 8) {
                i3 = 2;
            }
            try {
                AudioTrack audioTrack2 = new AudioTrack(3, nativeGetSampleRate, i2, i3, AudioTrack.getMinBufferSize(nativeGetSampleRate, i2, i3), 1);
                try {
                    audioTrack2.play();
                    byte[] bArr = new byte[2048];
                    while (true) {
                        if (!this.mIsRunning || Thread.interrupted() || this.mAECType == TXEAudioDef.TXE_AEC_TRAE) {
                            i = 0;
                            audioTrack = audioTrack2;
                        } else {
                            int nativeRead = nativeRead(bArr, 2048);
                            if (nativeRead < 0) {
                                onPlayProgress(nativeGetCurDurationMS(), nativeGetCurDurationMS());
                                i = 0;
                                audioTrack = audioTrack2;
                                break;
                            } else if (nativeRead != 0) {
                                audioTrack2.write(bArr, 0, nativeRead);
                                onPlayProgress(nativeGetCurPtsMS(), nativeGetCurDurationMS());
                            } else if (this.mIsPause) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    i = 0;
                    audioTrack = audioTrack2;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    TXCLog.e(TAG, "AudioTrack play Exception: " + e2.getMessage());
                    i = -4;
                    audioTrack = audioTrack2;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                TXCLog.e(TAG, "new AudioTrack Exception: " + e3.getMessage());
                i = -3;
                audioTrack = null;
            }
        }
        if (audioTrack != null) {
            try {
                audioTrack.pause();
                audioTrack.flush();
                audioTrack.stop();
                audioTrack.release();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        TXCLog.i(TAG, "Live BGM player play time: " + (System.currentTimeMillis() - currentTimeMillis));
        if (this.mIsRunning) {
            onPlayEnd(i);
        }
    }
}
