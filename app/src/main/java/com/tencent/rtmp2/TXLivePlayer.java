package com.tencent.rtmp2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import com.tencent.liteav.basic.c.a;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.c;
import com.tencent.liteav.e;
import com.tencent.liteav.f;
import com.tencent.liteav.g;
import com.tencent.rtmp2.ui.TXCloudVideoView;
import com.tencent.tp.a.h;
import com.tencent.ugc.TXRecordCommon;

public class TXLivePlayer implements a {
    public static final int PLAY_TYPE_LIVE_FLV = 1;
    public static final int PLAY_TYPE_LIVE_RTMP = 0;
    public static final int PLAY_TYPE_LIVE_RTMP_ACC = 5;
    public static final int PLAY_TYPE_LOCAL_VIDEO = 6;
    public static final int PLAY_TYPE_VOD_FLV = 2;
    public static final int PLAY_TYPE_VOD_HLS = 3;
    public static final int PLAY_TYPE_VOD_MP4 = 4;
    public static final String TAG = "TXLivePlayer";
    private ITXAudioRawDataListener mAudioRawDataListener;
    private int mAudioRoute = 0;
    private boolean mAutoPlay = true;
    private TXLivePlayConfig mConfig;
    private Context mContext;
    private boolean mEnableHWDec = false;
    private boolean mIsNeedClearLastImg = true;
    private ITXLivePlayListener mListener;
    private boolean mMute = false;
    private String mPlayUrl = "";
    private e mPlayer;
    private float mRate = 1.0f;
    private int mRenderMode;
    private int mRenderRotation;
    /* access modifiers changed from: private */
    public boolean mSnapshotRunning = false;
    private Surface mSurface;
    private TXCloudVideoView mTXCloudVideoView;
    private ITXVideoRawDataListener mVideoRawDataListener = null;

    public interface ITXAudioRawDataListener {
        void onAudioInfoChanged(int i, int i2, int i3);

        void onPcmDataAvailable(byte[] bArr, long j);
    }

    public interface ITXSnapshotListener {
        void onSnapshot(Bitmap bitmap);
    }

    public interface ITXVideoRawDataListener {
        void onVideoRawDataAvailable(byte[] bArr, int i, int i2, int i3);
    }

    public TXLivePlayer(Context context) {
        TXCLog.init();
        this.mListener = null;
        this.mContext = context.getApplicationContext();
    }

    public void setConfig(TXLivePlayConfig tXLivePlayConfig) {
        TXCLog.d(TAG, "liteav_api setConfig");
        this.mConfig = tXLivePlayConfig;
        if (this.mConfig == null) {
            this.mConfig = new TXLivePlayConfig();
        }
        if (this.mPlayer != null) {
            c f = this.mPlayer.f();
            if (f == null) {
                f = new c();
            }
            f.a = this.mConfig.mCacheTime;
            f.f = this.mConfig.mAutoAdjustCacheTime;
            f.c = this.mConfig.mMinAutoAdjustCacheTime;
            f.b = this.mConfig.mMaxAutoAdjustCacheTime;
            f.d = this.mConfig.mConnectRetryCount;
            f.e = this.mConfig.mConnectRetryInterval;
            f.g = this.mConfig.mEnableAec;
            f.i = this.mConfig.mEnableNearestIP;
            f.k = this.mConfig.mRtmpChannelType;
            f.h = this.mEnableHWDec;
            f.l = this.mConfig.mCacheFolderPath;
            f.m = this.mConfig.mMaxCacheItems;
            f.j = this.mConfig.mEnableMessage;
            f.n = this.mConfig.mHeaders;
            this.mPlayer.a(f);
        }
    }

    public void setPlayerView(TXCloudVideoView tXCloudVideoView) {
        TXCLog.d(TAG, "liteav_api setPlayerView old view : " + this.mTXCloudVideoView + ", new view : " + tXCloudVideoView);
        this.mTXCloudVideoView = tXCloudVideoView;
        if (this.mPlayer != null) {
            this.mPlayer.a(tXCloudVideoView);
        }
    }

    public void setSurface(Surface surface) {
        TXCLog.d(TAG, "liteav_api setSurface old : " + this.mSurface + ", new : " + surface);
        this.mSurface = surface;
        if (this.mSurface != null) {
            enableHardwareDecode(true);
        }
        if (this.mPlayer != null) {
            this.mPlayer.a(this.mSurface);
        }
    }

    public int startPlay(String str, int i) {
        TXCLog.d(TAG, "liteav_api startPlay");
        if (str == null || TextUtils.isEmpty(str)) {
            return -1;
        }
        TXCDRApi.initCrashReport(this.mContext);
        stopPlay(this.mIsNeedClearLastImg);
        this.mPlayUrl = checkPlayUrl(str, i);
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "=====  StartPlay url = " + this.mPlayUrl + " playType = " + i + " SDKVersion = " + TXCCommonUtil.getSDKID() + " , " + TXCCommonUtil.getSDKVersionStr() + "    ======");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        this.mPlayer = g.a(this.mContext, i);
        if (this.mPlayer == null) {
            return -2;
        }
        setConfig(this.mConfig);
        if (this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.clearLog();
            this.mTXCloudVideoView.setVisibility(0);
        }
        this.mPlayer.a(this.mTXCloudVideoView);
        this.mPlayer.a((a) this);
        this.mPlayer.c(this.mAutoPlay);
        if (this.mSurface != null) {
            this.mPlayer.a(this.mSurface);
        }
        this.mPlayer.a(this.mPlayUrl, i);
        this.mPlayer.b(this.mMute);
        this.mPlayer.a(this.mRate);
        this.mPlayer.b(this.mRenderRotation);
        this.mPlayer.a(this.mRenderMode);
        setAudioRoute(this.mAudioRoute);
        this.mPlayer.a(this.mAudioRawDataListener);
        setVideoRawDataListener(this.mVideoRawDataListener);
        return 0;
    }

    public int stopPlay(boolean z) {
        TXCLog.d(TAG, "liteav_api stopPlay " + z);
        if (z && this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.setVisibility(8);
        }
        if (this.mPlayer != null) {
            this.mPlayer.a(z);
        }
        this.mPlayUrl = "";
        return 0;
    }

    public boolean isPlaying() {
        if (this.mPlayer != null) {
            return this.mPlayer.c();
        }
        return false;
    }

    public void pause() {
        TXCLog.d(TAG, "liteav_api pause");
        if (this.mPlayer != null) {
            TXCLog.w(TAG, "pause play");
            this.mPlayer.a();
        }
    }

    public void resume() {
        TXCLog.d(TAG, "liteav_api resume");
        if (this.mPlayer != null) {
            TXCLog.w(TAG, "resume play");
            this.mPlayer.b();
            setAudioRoute(this.mAudioRoute);
        }
    }

    public void seek(int i) {
        TXCLog.d(TAG, "liteav_api ");
        if (this.mPlayer != null) {
            this.mPlayer.e(i);
        }
    }

    public void setPlayListener(ITXLivePlayListener iTXLivePlayListener) {
        TXCLog.d(TAG, "liteav_api setPlayListener");
        this.mListener = iTXLivePlayListener;
    }

    public void setVideoRecordListener(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        TXCLog.d(TAG, "liteav_api setVideoRecordListener");
        if (this.mPlayer != null) {
            this.mPlayer.a(iTXVideoRecordListener);
        }
    }

    public int startRecord(int i) {
        TXCLog.d(TAG, "liteav_api startRecord");
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.e(TAG, "API levl is too low (record need 18, current is" + Build.VERSION.SDK_INT + h.b);
            return -3;
        } else if (!isPlaying()) {
            TXCLog.e(TAG, "startRecord: there is no playing stream");
            return -1;
        } else if (this.mPlayer != null) {
            return this.mPlayer.c(i);
        } else {
            return -1;
        }
    }

    public int stopRecord() {
        TXCLog.d(TAG, "liteav_api stopRecord");
        if (this.mPlayer != null) {
            return this.mPlayer.e();
        }
        return -1;
    }

    public void setRenderMode(int i) {
        TXCLog.d(TAG, "liteav_api setRenderMode " + i);
        this.mRenderMode = i;
        if (this.mPlayer != null) {
            this.mPlayer.a(i);
        }
    }

    public void setRenderRotation(int i) {
        TXCLog.d(TAG, "liteav_api setRenderRotation " + i);
        this.mRenderRotation = i;
        if (this.mPlayer != null) {
            this.mPlayer.b(i);
        }
    }

    public boolean enableHardwareDecode(boolean z) {
        TXCLog.d(TAG, "liteav_api enableHardwareDecode " + z);
        if (!z && this.mSurface != null) {
            return false;
        }
        if (z) {
            if (Build.VERSION.SDK_INT < 18) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, android system build.version = " + Build.VERSION.SDK_INT + ", the minimum build.version should be 18(android 4.3 or later)");
                return false;
            } else if (isAVCDecBlacklistDevices()) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, MANUFACTURER = " + Build.MANUFACTURER + ", MODEL" + Build.MODEL);
                return false;
            }
        }
        this.mEnableHWDec = z;
        if (this.mPlayer != null) {
            c f = this.mPlayer.f();
            if (f == null) {
                f = new c();
            }
            f.h = this.mEnableHWDec;
            this.mPlayer.a(f);
        }
        return true;
    }

    public void setMute(boolean z) {
        TXCLog.d(TAG, "liteav_api setMute " + z);
        this.mMute = z;
        if (this.mPlayer != null) {
            this.mPlayer.b(z);
        }
    }

    public void setAutoPlay(boolean z) {
        TXCLog.d(TAG, "liteav_api setAutoPlay " + z);
        this.mAutoPlay = z;
    }

    public void setRate(float f) {
        TXCLog.d(TAG, "liteav_api setRate " + f);
        this.mRate = f;
        if (this.mPlayer != null) {
            this.mPlayer.a(f);
        }
    }

    public void setAudioRoute(int i) {
        TXCLog.d(TAG, "liteav_api setAudioRoute " + i);
        this.mAudioRoute = i;
        if (this.mPlayer != null) {
            this.mPlayer.a(this.mContext, this.mAudioRoute);
        }
    }

    public void onNotifyEvent(int i, Bundle bundle) {
        if (i == 15001) {
            if (this.mTXCloudVideoView != null) {
                this.mTXCloudVideoView.setLogText(bundle, (Bundle) null, 0);
            }
            if (this.mListener != null) {
                this.mListener.onNetStatus(bundle);
                return;
            }
            return;
        }
        if (this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.setLogText((Bundle) null, bundle, i);
        }
        if (this.mListener != null) {
            this.mListener.onPlayEvent(i, bundle);
        }
    }

    public boolean addVideoRawData(byte[] bArr) {
        TXCLog.d(TAG, "liteav_api addVideoRawData " + bArr);
        if (this.mPlayUrl == null || this.mPlayUrl.isEmpty()) {
            return false;
        }
        if (this.mEnableHWDec) {
            TXLog.e(TAG, "can not addVideoRawData because of hw decode has set!");
            return false;
        } else if (this.mPlayer == null) {
            TXCLog.e(TAG, "player hasn't created or not instanceof live player");
            return false;
        } else {
            this.mPlayer.a(bArr);
            return true;
        }
    }

    public void setVideoRawDataListener(final ITXVideoRawDataListener iTXVideoRawDataListener) {
        TXCLog.d(TAG, "liteav_api setVideoRawDataListener " + iTXVideoRawDataListener);
        this.mVideoRawDataListener = iTXVideoRawDataListener;
        if (this.mPlayer != null) {
            if (iTXVideoRawDataListener != null) {
                this.mPlayer.a((f) new f() {
                    public void onVideoRawDataAvailable(byte[] bArr, int i, int i2, int i3) {
                        iTXVideoRawDataListener.onVideoRawDataAvailable(bArr, i, i2, i3);
                    }
                });
            } else {
                this.mPlayer.a((f) null);
            }
        }
    }

    public void snapshot(ITXSnapshotListener iTXSnapshotListener) {
        TextureView textureView;
        TXCLog.d(TAG, "liteav_api snapshot " + iTXSnapshotListener);
        if (!this.mSnapshotRunning && iTXSnapshotListener != null) {
            this.mSnapshotRunning = true;
            if (this.mPlayer != null) {
                textureView = this.mPlayer.d();
            } else {
                textureView = null;
            }
            if (textureView != null) {
                Bitmap bitmap = textureView.getBitmap();
                if (bitmap != null) {
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), textureView.getTransform((Matrix) null), true);
                    bitmap.recycle();
                    bitmap = createBitmap;
                }
                postBitmapToMainThread(iTXSnapshotListener, bitmap);
                return;
            }
            this.mSnapshotRunning = false;
        }
    }

    public void setAudioRawDataListener(ITXAudioRawDataListener iTXAudioRawDataListener) {
        TXCLog.d(TAG, "liteav_api setAudioRawDataListener " + iTXAudioRawDataListener);
        this.mAudioRawDataListener = iTXAudioRawDataListener;
        if (this.mPlayer != null) {
            this.mPlayer.a(iTXAudioRawDataListener);
        }
    }

    private boolean isAVCDecBlacklistDevices() {
        if (!Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") || !Build.MODEL.equalsIgnoreCase("Che2-TL00")) {
            return false;
        }
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String checkPlayUrl(java.lang.String r8, int r9) {
        /*
            r7 = this;
            r0 = 0
            r1 = 6
            if (r9 == r1) goto L_0x0077
            java.lang.String r1 = "UTF-8"
            byte[] r2 = r8.getBytes(r1)     // Catch:{ Exception -> 0x0073 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0073 }
            int r1 = r2.length     // Catch:{ Exception -> 0x0073 }
            r3.<init>(r1)     // Catch:{ Exception -> 0x0073 }
            r1 = r0
        L_0x0011:
            int r0 = r2.length     // Catch:{ Exception -> 0x0073 }
            if (r1 >= r0) goto L_0x007c
            byte r0 = r2[r1]     // Catch:{ Exception -> 0x0073 }
            if (r0 >= 0) goto L_0x006b
            byte r0 = r2[r1]     // Catch:{ Exception -> 0x0073 }
            int r0 = r0 + 256
        L_0x001c:
            r4 = 32
            if (r0 <= r4) goto L_0x0054
            r4 = 127(0x7f, float:1.78E-43)
            if (r0 >= r4) goto L_0x0054
            r4 = 34
            if (r0 == r4) goto L_0x0054
            r4 = 37
            if (r0 == r4) goto L_0x0054
            r4 = 60
            if (r0 == r4) goto L_0x0054
            r4 = 62
            if (r0 == r4) goto L_0x0054
            r4 = 91
            if (r0 == r4) goto L_0x0054
            r4 = 125(0x7d, float:1.75E-43)
            if (r0 == r4) goto L_0x0054
            r4 = 92
            if (r0 == r4) goto L_0x0054
            r4 = 93
            if (r0 == r4) goto L_0x0054
            r4 = 94
            if (r0 == r4) goto L_0x0054
            r4 = 96
            if (r0 == r4) goto L_0x0054
            r4 = 123(0x7b, float:1.72E-43)
            if (r0 == r4) goto L_0x0054
            r4 = 124(0x7c, float:1.74E-43)
            if (r0 != r4) goto L_0x006e
        L_0x0054:
            java.lang.String r4 = "%%%02X"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0073 }
            r6 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0073 }
            r5[r6] = r0     // Catch:{ Exception -> 0x0073 }
            java.lang.String r0 = java.lang.String.format(r4, r5)     // Catch:{ Exception -> 0x0073 }
            r3.append(r0)     // Catch:{ Exception -> 0x0073 }
        L_0x0067:
            int r0 = r1 + 1
            r1 = r0
            goto L_0x0011
        L_0x006b:
            byte r0 = r2[r1]     // Catch:{ Exception -> 0x0073 }
            goto L_0x001c
        L_0x006e:
            char r0 = (char) r0     // Catch:{ Exception -> 0x0073 }
            r3.append(r0)     // Catch:{ Exception -> 0x0073 }
            goto L_0x0067
        L_0x0073:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0077:
            java.lang.String r0 = r8.trim()
            return r0
        L_0x007c:
            java.lang.String r8 = r3.toString()     // Catch:{ Exception -> 0x0073 }
            goto L_0x0077
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp2.TXLivePlayer.checkPlayUrl(java.lang.String, int):java.lang.String");
    }

    private void postBitmapToMainThread(final ITXSnapshotListener iTXSnapshotListener, final Bitmap bitmap) {
        if (iTXSnapshotListener != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (iTXSnapshotListener != null) {
                        iTXSnapshotListener.onSnapshot(bitmap);
                    }
                    boolean unused = TXLivePlayer.this.mSnapshotRunning = false;
                }
            });
        }
    }
}
