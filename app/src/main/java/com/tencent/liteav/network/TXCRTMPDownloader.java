package com.tencent.liteav.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCStreamDownloader;
import com.tencent.rtmp2.TXLiveConstants;
import java.util.Vector;

public class TXCRTMPDownloader extends TXIStreamDownloader {
    private final int MSG_EVENT;
    private final int MSG_RECONNECT;
    private final String TAG;
    private int mConnectCountQuic;
    private int mConnectCountTcp;
    private a mCurrentThread;
    private boolean mEnableNearestIP;
    private Handler mHandler;
    private boolean mHasTcpPlayUrl;
    private boolean mIsPlayRtmpAccStream;
    private String mPlayUrl;
    private boolean mQuicChannel;
    private Object mRTMPThreadLock;
    private String mServerIp;
    private HandlerThread mThread;
    private Vector<d> mVecPlayUrls;

    /* access modifiers changed from: private */
    public native TXCStreamDownloader.DownloadStats nativeGetStats(long j);

    /* access modifiers changed from: private */
    public native long nativeInitRtmpHandler(String str, boolean z, boolean z2);

    /* access modifiers changed from: private */
    public native void nativeStart(long j);

    /* access modifiers changed from: private */
    public native void nativeStop(long j);

    /* access modifiers changed from: private */
    public native void nativeUninitRtmpHandler(long j);

    public TXCRTMPDownloader() {
        this.TAG = "network.TXCRTMPDownloader";
        this.MSG_RECONNECT = 101;
        this.MSG_EVENT = 102;
        this.mPlayUrl = "";
        this.mQuicChannel = false;
        this.mServerIp = "";
        this.mCurrentThread = null;
        this.mRTMPThreadLock = null;
        this.mThread = null;
        this.mHandler = null;
        this.mIsPlayRtmpAccStream = false;
        this.mEnableNearestIP = false;
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.mRTMPThreadLock = new Object();
    }

    class a extends Thread {
        private long b = 0;
        private String c;
        private boolean d;

        a(String str, boolean z) {
            super("RTMPDownLoad");
            this.c = str;
            this.d = z;
        }

        public void run() {
            synchronized (this) {
                this.b = TXCRTMPDownloader.this.nativeInitRtmpHandler(this.c, this.d, TXCRTMPDownloader.this.mEnableMessage);
            }
            TXCRTMPDownloader.this.nativeStart(this.b);
            synchronized (this) {
                TXCRTMPDownloader.this.nativeUninitRtmpHandler(this.b);
                this.b = 0;
            }
        }

        public void a() {
            synchronized (this) {
                if (this.b != 0) {
                    TXCRTMPDownloader.this.nativeStop(this.b);
                }
            }
        }

        public TXCStreamDownloader.DownloadStats b() {
            TXCStreamDownloader.DownloadStats downloadStats = null;
            synchronized (this) {
                if (this.b != 0) {
                    downloadStats = TXCRTMPDownloader.this.nativeGetStats(this.b);
                }
            }
            return downloadStats;
        }
    }

    /* access modifiers changed from: private */
    public void startInternal() {
        if (this.mQuicChannel) {
            this.mConnectCountQuic++;
        } else {
            this.mConnectCountTcp++;
        }
        synchronized (this.mRTMPThreadLock) {
            this.mCurrentThread = new a(this.mPlayUrl, this.mQuicChannel);
            this.mCurrentThread.start();
        }
    }

    private void postReconnectMsg() {
        Message message = new Message();
        message.what = 101;
        if (this.mHandler != null) {
            this.mHandler.sendMessageDelayed(message, (long) (this.connectRetryInterval * 1000));
        }
    }

    private void reconnect(boolean z) {
        boolean z2;
        boolean z3;
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                this.mCurrentThread.a();
                this.mCurrentThread = null;
            }
        }
        if (this.mIsRunning) {
            boolean z4 = this.mQuicChannel;
            if (this.mIsPlayRtmpAccStream) {
                if (!this.mEnableNearestIP) {
                    z2 = false;
                } else {
                    z2 = z;
                }
                if (z4) {
                    z2 = true;
                }
                if (z2) {
                    if (this.mVecPlayUrls == null || this.mVecPlayUrls.isEmpty()) {
                        z3 = false;
                    } else {
                        d dVar = this.mVecPlayUrls.get(0);
                        this.mVecPlayUrls.remove(0);
                        this.mPlayUrl = dVar.a;
                        this.mQuicChannel = dVar.b;
                        z3 = true;
                    }
                    if (!z3) {
                    }
                }
            }
            if (z4 && this.mHasTcpPlayUrl) {
                sendNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_ERROR_NET_RECONNECT);
                postReconnectMsg();
            } else if (this.connectRetryTimes < this.connectRetryLimit) {
                this.connectRetryTimes++;
                TXCLog.d("network.TXCRTMPDownloader", "reconnect retry count:" + this.connectRetryTimes + " limit:" + this.connectRetryLimit);
                sendNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_ERROR_NET_RECONNECT);
                postReconnectMsg();
            } else {
                sendNotifyEvent(12012);
            }
        }
    }

    public void sendNotifyEvent(int i, String str) {
        if (str.isEmpty()) {
            sendNotifyEvent(i);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (this.mNotifyListener != null) {
            this.mNotifyListener.onNotifyEvent(i, bundle);
        }
    }

    public void sendNotifyEvent(int i) {
        boolean z = true;
        if (i == 0 || i == 1) {
            if (i != 1) {
                z = false;
            }
            reconnect(z);
            return;
        }
        super.sendNotifyEvent(i);
    }

    public void startDownload(Vector<d> vector, boolean z, boolean z2, boolean z3) {
        if (!this.mIsRunning && vector != null && !vector.isEmpty()) {
            this.mEnableMessage = z3;
            this.mIsPlayRtmpAccStream = z;
            this.mEnableNearestIP = z2;
            this.mVecPlayUrls = vector;
            this.mHasTcpPlayUrl = false;
            int i = 0;
            while (true) {
                if (i >= this.mVecPlayUrls.size()) {
                    break;
                } else if (!this.mVecPlayUrls.elementAt(i).b) {
                    this.mHasTcpPlayUrl = true;
                    break;
                } else {
                    i++;
                }
            }
            d dVar = this.mVecPlayUrls.get(0);
            this.mVecPlayUrls.remove(0);
            this.mPlayUrl = dVar.a;
            this.mQuicChannel = dVar.b;
            this.mIsRunning = true;
            TXCLog.d("network.TXCRTMPDownloader", "start pull with url:" + this.mPlayUrl + " quic:" + (this.mQuicChannel ? "yes" : "no"));
            this.mConnectCountQuic = 0;
            this.mConnectCountTcp = 0;
            if (this.mThread == null) {
                this.mThread = new HandlerThread("RTMP_PULL");
                this.mThread.start();
            }
            this.mHandler = new Handler(this.mThread.getLooper()) {
                public void handleMessage(Message message) {
                    if (message.what == 101) {
                        TXCRTMPDownloader.this.startInternal();
                    }
                }
            };
            startInternal();
        }
    }

    public void stopDownload() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            this.mVecPlayUrls.removeAllElements();
            this.mVecPlayUrls = null;
            this.mIsPlayRtmpAccStream = false;
            this.mEnableNearestIP = false;
            TXCLog.d("network.TXCRTMPDownloader", "stop pull");
            synchronized (this.mRTMPThreadLock) {
                if (this.mCurrentThread != null) {
                    this.mCurrentThread.a();
                    this.mCurrentThread = null;
                }
            }
            if (this.mThread != null) {
                this.mThread.quit();
                this.mThread = null;
            }
            if (this.mHandler != null) {
                this.mHandler = null;
            }
        }
    }

    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        TXCStreamDownloader.DownloadStats downloadStats;
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                downloadStats = this.mCurrentThread.b();
            } else {
                downloadStats = null;
            }
        }
        return downloadStats;
    }

    public String getCurrentStreamUrl() {
        return this.mPlayUrl;
    }

    public boolean isQuicChannel() {
        return this.mQuicChannel;
    }

    public int getConnectCountQuic() {
        return this.mConnectCountQuic;
    }

    public int getConnectCountTcp() {
        return this.mConnectCountTcp;
    }
}
