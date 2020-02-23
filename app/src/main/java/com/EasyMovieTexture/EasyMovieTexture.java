package com.EasyMovieTexture;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLES20;
import android.util.Log;
import android.view.Surface;
import com.android.vending.expansion.zipfile.ZipResourceFile;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EasyMovieTexture implements SurfaceTexture.OnFrameAvailableListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private static final int GL_TEXTURE_EXTERNAL_OES = 36197;
    public static ArrayList<EasyMovieTexture> m_objCtrl = new ArrayList<>();
    private MediaPlayer m_MediaPlayer = null;
    private Surface m_Surface = null;
    private SurfaceTexture m_SurfaceTexture = null;
    private Activity m_UnityActivity = null;
    private boolean m_bRockchip = true;
    private boolean m_bSplitOBB = false;
    public boolean m_bUpdate = false;
    private int m_iCurrentSeekPercent = 0;
    private int m_iCurrentSeekPosition = 0;
    MEDIAPLAYER_STATE m_iCurrentState = MEDIAPLAYER_STATE.NOT_READY;
    private int m_iErrorCode;
    private int m_iErrorCodeExtra;
    public int m_iNativeMgrID;
    private int m_iSurfaceTextureID = -1;
    private int m_iUnityTextureID = -1;
    private String m_strFileName;
    private String m_strOBBName;

    public native int GetManagerID();

    public native int InitApplication();

    public native int InitExtTexture();

    public native int InitNDK(Object obj);

    public native void QuitApplication();

    public native void RenderScene(float[] fArr, int i, int i2);

    public native void SetAssetManager(AssetManager assetManager);

    public native void SetManagerID(int i);

    public native void SetUnityTextureID(int i);

    public native void SetWindowSize(int i, int i2, int i3, boolean z);

    static {
        System.loadLibrary("BlueDoveMediaRender");
    }

    public static EasyMovieTexture GetObject(int iID) {
        for (int i = 0; i < m_objCtrl.size(); i++) {
            if (m_objCtrl.get(i).m_iNativeMgrID == iID) {
                return m_objCtrl.get(i);
            }
        }
        return null;
    }

    public void Destroy() {
        if (this.m_iSurfaceTextureID != -1) {
            GLES20.glDeleteTextures(1, new int[]{this.m_iSurfaceTextureID}, 0);
            this.m_iSurfaceTextureID = -1;
        }
        SetManagerID(this.m_iNativeMgrID);
        QuitApplication();
        m_objCtrl.remove(this);
    }

    public void UnLoad() {
        if (this.m_MediaPlayer != null) {
            if (this.m_iCurrentState != MEDIAPLAYER_STATE.NOT_READY) {
                try {
                    this.m_MediaPlayer.stop();
                    this.m_MediaPlayer.release();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                }
                this.m_MediaPlayer = null;
            } else {
                try {
                    this.m_MediaPlayer.release();
                } catch (SecurityException e3) {
                    e3.printStackTrace();
                } catch (IllegalStateException e4) {
                    e4.printStackTrace();
                }
                this.m_MediaPlayer = null;
            }
            if (this.m_Surface != null) {
                this.m_Surface.release();
                this.m_Surface = null;
            }
            if (this.m_SurfaceTexture != null) {
                this.m_SurfaceTexture.release();
                this.m_SurfaceTexture = null;
            }
            if (this.m_iSurfaceTextureID != -1) {
                GLES20.glDeleteTextures(1, new int[]{this.m_iSurfaceTextureID}, 0);
                this.m_iSurfaceTextureID = -1;
            }
        }
    }

    public boolean Load() throws SecurityException, IllegalStateException, IOException {
        UnLoad();
        this.m_iCurrentState = MEDIAPLAYER_STATE.NOT_READY;
        this.m_MediaPlayer = new MediaPlayer();
        this.m_MediaPlayer.setAudioStreamType(3);
        this.m_bUpdate = false;
        if (this.m_strFileName.contains(IMSDKFileProvider.FILE_SCHEME)) {
            File sourceFile = new File(this.m_strFileName.replace(IMSDKFileProvider.FILE_SCHEME, ""));
            if (sourceFile.exists()) {
                FileInputStream fs = new FileInputStream(sourceFile);
                this.m_MediaPlayer.setDataSource(fs.getFD());
                fs.close();
            }
        } else if (this.m_strFileName.contains("://")) {
            try {
                Map<String, String> headers = new HashMap<>();
                headers.put("rtsp_transport", "tcp");
                headers.put("max_analyze_duration", "500");
                this.m_MediaPlayer.setDataSource(this.m_UnityActivity, Uri.parse(this.m_strFileName), headers);
            } catch (IOException e) {
                Log.e("Unity", "Error m_MediaPlayer.setDataSource() : " + this.m_strFileName);
                e.printStackTrace();
                this.m_iCurrentState = MEDIAPLAYER_STATE.ERROR;
                return false;
            }
        } else if (this.m_bSplitOBB) {
            try {
                ZipResourceFile expansionFile = new ZipResourceFile(this.m_strOBBName);
                Log.e("unity", String.valueOf(this.m_strOBBName) + " " + this.m_strFileName);
                AssetFileDescriptor afd = expansionFile.getAssetFileDescriptor("assets/" + this.m_strFileName);
                ZipResourceFile.ZipEntryRO[] data = expansionFile.getAllEntries();
                for (ZipResourceFile.ZipEntryRO zipEntryRO : data) {
                    Log.e("unity", zipEntryRO.mFileName);
                }
                Log.e("unity", afd + " ");
                this.m_MediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            } catch (IOException e2) {
                this.m_iCurrentState = MEDIAPLAYER_STATE.ERROR;
                e2.printStackTrace();
                return false;
            }
        } else {
            try {
                AssetFileDescriptor afd2 = this.m_UnityActivity.getAssets().openFd(this.m_strFileName);
                this.m_MediaPlayer.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());
                afd2.close();
            } catch (IOException e3) {
                Log.e("Unity", "Error m_MediaPlayer.setDataSource() : " + this.m_strFileName);
                e3.printStackTrace();
                this.m_iCurrentState = MEDIAPLAYER_STATE.ERROR;
                return false;
            }
        }
        if (this.m_iSurfaceTextureID == -1) {
            this.m_iSurfaceTextureID = InitExtTexture();
        }
        this.m_SurfaceTexture = new SurfaceTexture(this.m_iSurfaceTextureID);
        this.m_SurfaceTexture.setOnFrameAvailableListener(this);
        this.m_Surface = new Surface(this.m_SurfaceTexture);
        this.m_MediaPlayer.setSurface(this.m_Surface);
        this.m_MediaPlayer.setOnPreparedListener(this);
        this.m_MediaPlayer.setOnCompletionListener(this);
        this.m_MediaPlayer.setOnErrorListener(this);
        this.m_MediaPlayer.prepareAsync();
        return true;
    }

    public synchronized void onFrameAvailable(SurfaceTexture surface) {
        this.m_bUpdate = true;
    }

    @TargetApi(23)
    public void SetSpeed(float fSpeed) {
        this.m_MediaPlayer.setPlaybackParams(this.m_MediaPlayer.getPlaybackParams().setSpeed(fSpeed));
    }

    public void UpdateVideoTexture() {
        if (!this.m_bUpdate || this.m_MediaPlayer == null) {
            return;
        }
        if (this.m_iCurrentState == MEDIAPLAYER_STATE.PLAYING || this.m_iCurrentState == MEDIAPLAYER_STATE.PAUSED) {
            SetManagerID(this.m_iNativeMgrID);
            boolean[] abValue = new boolean[1];
            GLES20.glGetBooleanv(2929, abValue, 0);
            GLES20.glDisable(2929);
            this.m_SurfaceTexture.updateTexImage();
            float[] mMat = new float[16];
            this.m_SurfaceTexture.getTransformMatrix(mMat);
            RenderScene(mMat, this.m_iSurfaceTextureID, this.m_iUnityTextureID);
            if (abValue[0]) {
                GLES20.glEnable(2929);
            }
        }
    }

    public void SetRockchip(boolean bValue) {
        this.m_bRockchip = bValue;
    }

    public void SetLooping(boolean bLoop) {
        if (this.m_MediaPlayer != null) {
            this.m_MediaPlayer.setLooping(bLoop);
        }
    }

    public void SetVolume(float fVolume) {
        if (this.m_MediaPlayer != null) {
            this.m_MediaPlayer.setVolume(fVolume, fVolume);
        }
    }

    public void SetSeekPosition(int iSeek) {
        if (this.m_MediaPlayer == null) {
            return;
        }
        if (this.m_iCurrentState == MEDIAPLAYER_STATE.READY || this.m_iCurrentState == MEDIAPLAYER_STATE.PLAYING || this.m_iCurrentState == MEDIAPLAYER_STATE.PAUSED) {
            this.m_MediaPlayer.seekTo(iSeek);
        }
    }

    public int GetSeekPosition() {
        if (this.m_MediaPlayer != null && (this.m_iCurrentState == MEDIAPLAYER_STATE.READY || this.m_iCurrentState == MEDIAPLAYER_STATE.PLAYING || this.m_iCurrentState == MEDIAPLAYER_STATE.PAUSED)) {
            try {
                this.m_iCurrentSeekPosition = this.m_MediaPlayer.getCurrentPosition();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
        }
        return this.m_iCurrentSeekPosition;
    }

    public int GetCurrentSeekPercent() {
        return this.m_iCurrentSeekPercent;
    }

    public void Play(int iSeek) {
        if (this.m_MediaPlayer == null) {
            return;
        }
        if (this.m_iCurrentState == MEDIAPLAYER_STATE.READY || this.m_iCurrentState == MEDIAPLAYER_STATE.PAUSED || this.m_iCurrentState == MEDIAPLAYER_STATE.END) {
            this.m_MediaPlayer.start();
            this.m_iCurrentState = MEDIAPLAYER_STATE.PLAYING;
        }
    }

    public void Reset() {
        if (this.m_MediaPlayer != null && this.m_iCurrentState == MEDIAPLAYER_STATE.PLAYING) {
            this.m_MediaPlayer.reset();
        }
        this.m_iCurrentState = MEDIAPLAYER_STATE.NOT_READY;
    }

    public void Stop() {
        if (this.m_MediaPlayer != null && this.m_iCurrentState == MEDIAPLAYER_STATE.PLAYING) {
            this.m_MediaPlayer.stop();
        }
        this.m_iCurrentState = MEDIAPLAYER_STATE.NOT_READY;
    }

    public void RePlay() {
        if (this.m_MediaPlayer != null && this.m_iCurrentState == MEDIAPLAYER_STATE.PAUSED) {
            this.m_MediaPlayer.start();
            this.m_iCurrentState = MEDIAPLAYER_STATE.PLAYING;
        }
    }

    public void Pause() {
        if (this.m_MediaPlayer != null && this.m_iCurrentState == MEDIAPLAYER_STATE.PLAYING) {
            this.m_MediaPlayer.pause();
            this.m_iCurrentState = MEDIAPLAYER_STATE.PAUSED;
        }
    }

    public int GetVideoWidth() {
        if (this.m_MediaPlayer != null) {
            return this.m_MediaPlayer.getVideoWidth();
        }
        return 0;
    }

    public int GetVideoHeight() {
        if (this.m_MediaPlayer != null) {
            return this.m_MediaPlayer.getVideoHeight();
        }
        return 0;
    }

    public boolean IsUpdateFrame() {
        if (this.m_bUpdate) {
            return true;
        }
        return false;
    }

    public void SetUnityTexture(int iTextureID) {
        this.m_iUnityTextureID = iTextureID;
        SetManagerID(this.m_iNativeMgrID);
        SetUnityTextureID(this.m_iUnityTextureID);
    }

    public void SetUnityTextureID(Object texturePtr) {
    }

    public void SetSplitOBB(boolean bValue, String strOBBName) {
        this.m_bSplitOBB = bValue;
        this.m_strOBBName = strOBBName;
    }

    public int GetDuration() {
        if (this.m_MediaPlayer != null) {
            return this.m_MediaPlayer.getDuration();
        }
        return -1;
    }

    public int InitNative(EasyMovieTexture obj) {
        this.m_iNativeMgrID = InitNDK(obj);
        m_objCtrl.add(this);
        return this.m_iNativeMgrID;
    }

    public void SetUnityActivity(Activity unityActivity) {
        SetManagerID(this.m_iNativeMgrID);
        this.m_UnityActivity = unityActivity;
        SetAssetManager(this.m_UnityActivity.getAssets());
    }

    public void NDK_SetFileName(String strFileName) {
        this.m_strFileName = strFileName;
    }

    public void InitJniManager() {
        SetManagerID(this.m_iNativeMgrID);
        InitApplication();
    }

    public int GetStatus() {
        return this.m_iCurrentState.GetValue();
    }

    public void SetNotReady() {
        this.m_iCurrentState = MEDIAPLAYER_STATE.NOT_READY;
    }

    public void SetWindowSize() {
        SetManagerID(this.m_iNativeMgrID);
        SetWindowSize(GetVideoWidth(), GetVideoHeight(), this.m_iUnityTextureID, this.m_bRockchip);
    }

    public int GetError() {
        return this.m_iErrorCode;
    }

    public int GetErrorExtra() {
        return this.m_iErrorCodeExtra;
    }

    public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
        if (arg0 != this.m_MediaPlayer) {
            return false;
        }
        switch (arg1) {
            case 1:
                break;
            case 100:
                break;
            case 200:
                break;
            default:
                String str = "Unknown error " + arg1;
                break;
        }
        this.m_iErrorCode = arg1;
        this.m_iErrorCodeExtra = arg2;
        this.m_iCurrentState = MEDIAPLAYER_STATE.ERROR;
        return true;
    }

    public void onCompletion(MediaPlayer arg0) {
        if (arg0 == this.m_MediaPlayer) {
            this.m_iCurrentState = MEDIAPLAYER_STATE.END;
        }
    }

    public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
        if (arg0 == this.m_MediaPlayer) {
            this.m_iCurrentSeekPercent = arg1;
        }
    }

    public void onPrepared(MediaPlayer arg0) {
        if (arg0 == this.m_MediaPlayer) {
            this.m_iCurrentState = MEDIAPLAYER_STATE.READY;
            SetManagerID(this.m_iNativeMgrID);
            this.m_iCurrentSeekPercent = 0;
            this.m_MediaPlayer.setOnBufferingUpdateListener(this);
        }
    }

    public enum MEDIAPLAYER_STATE {
        NOT_READY(0),
        READY(1),
        END(2),
        PLAYING(3),
        PAUSED(4),
        STOPPED(5),
        ERROR(6);
        
        private int iValue;

        private MEDIAPLAYER_STATE(int i) {
            this.iValue = i;
        }

        public int GetValue() {
            return this.iValue;
        }
    }
}
