package com.tencent.ieg.ntv.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.TextUtils;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.tencent.ieg.ntv.ctrl.chat.WsMsgQueue;
import com.tencent.ieg.ntv.network.WsMessage;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WsManager {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int FRAME_QUEUE_SIZE = 5;
    /* access modifiers changed from: private */
    public final String TAG = getClass().getSimpleName();
    private Context mContext;
    private Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public String mProtocal;
    private Runnable mReconnectTask = new Runnable() {
        public void run() {
            try {
                WebSocket unused = WsManager.this.ws = new WebSocketFactory().createSocket(WsManager.this.mUrl, 5000).addProtocol(WsManager.this.mProtocal).setFrameQueueSize(5).setMissingCloseFrameAllowed(false).addListener(WsManager.this.wsListener = new WsListener()).connectAsynchronously();
                WsManager.this.setStatus(WsStatus.CONNECTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    /* access modifiers changed from: private */
    public String mUrl;
    private long maxInterval = Constants.WATCHDOG_WAKE_TIMER;
    private long minInterval = 3000;
    private int reconnectCount = 0;
    /* access modifiers changed from: private */
    public WebSocket ws;
    public WsCallback wsCallback;
    /* access modifiers changed from: private */
    public WsListener wsListener;
    /* access modifiers changed from: private */
    public WsMsgQueue wsMsgQueue = null;
    /* access modifiers changed from: private */
    public WsStatus wsStatus;

    public interface WsCallback {
        void onConnected(WsStatus wsStatus);

        void onMessage(String str);
    }

    public enum WsStatus {
        CONNECT_SUCCESS,
        CONNECT_FAIL,
        CONNECTING
    }

    public void setWsMsgQueue(WsMsgQueue msgQueue) {
        this.wsMsgQueue = msgQueue;
    }

    public void init(String url, String protocal, Context context) {
        this.mUrl = url;
        this.mProtocal = protocal;
        this.mContext = context;
        try {
            if (!TextUtils.isEmpty(this.mUrl)) {
                WebSocket missingCloseFrameAllowed = new WebSocketFactory().createSocket(this.mUrl, 5000).addProtocol(this.mProtocal).setFrameQueueSize(5).setMissingCloseFrameAllowed(false);
                WsListener wsListener2 = new WsListener();
                this.wsListener = wsListener2;
                this.ws = missingCloseFrameAllowed.addListener(wsListener2).connectAsynchronously();
                setStatus(WsStatus.CONNECTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(int type, String msg) {
        Logger.d(this.TAG, "wsmodule sendMessage : " + msg);
        if (this.wsMsgQueue != null && type == WsMessage.MessageType.MSG_SEND_REQ) {
            this.wsMsgQueue.addMsg(msg);
        }
        if (this.ws == null || !this.ws.isOpen() || getWsStatus() != WsStatus.CONNECT_SUCCESS) {
            Logger.d(this.TAG, "wsmodule sendMessage fail.");
        } else if (this.wsMsgQueue == null || type != WsMessage.MessageType.MSG_SEND_REQ) {
            this.ws.sendText(msg);
        } else {
            Logger.d(this.TAG, "wsmodule sendMessage from queue : " + this.wsMsgQueue.getMsg());
            this.ws.sendText(this.wsMsgQueue.getMsg());
            this.wsMsgQueue.removeMsg();
        }
    }

    /* access modifiers changed from: private */
    public void setStatus(WsStatus status) {
        this.wsStatus = status;
    }

    /* access modifiers changed from: private */
    public WsStatus getWsStatus() {
        return this.wsStatus;
    }

    public void disconnect() {
        this.reconnectCount = 10;
        if (this.ws != null) {
            this.ws.disconnect();
        }
    }

    class WsListener extends WebSocketAdapter {
        WsListener() {
        }

        public void onTextMessage(WebSocket webSocket, String text) throws Exception {
            super.onTextMessage(webSocket, text);
            if (WsManager.this.wsCallback != null) {
                WsManager.this.wsCallback.onMessage(text);
            }
            if (WsManager.this.wsMsgQueue != null) {
                WsManager.this.wsMsgQueue.checkMsgQueue(WsManager.this.ws, WsManager.this.getWsStatus(), text);
            }
        }

        public void onConnected(WebSocket webSocket, Map<String, List<String>> headers) throws Exception {
            super.onConnected(webSocket, headers);
            Logger.d(WsManager.this.TAG, "wsmodule onConnected.");
            WsManager.this.setStatus(WsStatus.CONNECT_SUCCESS);
            WsManager.this.cancelReconnect();
            if (WsManager.this.wsCallback != null) {
                WsManager.this.wsCallback.onConnected(WsManager.this.wsStatus);
            }
        }

        public void onConnectError(WebSocket webSocket, WebSocketException exception) throws Exception {
            super.onConnectError(webSocket, exception);
            Logger.d(WsManager.this.TAG, "wsmodule onConnectError.exception : " + exception.toString());
            WsManager.this.setStatus(WsStatus.CONNECT_FAIL);
            WsManager.this.onConnectErrorHandle();
        }

        public void onDisconnected(WebSocket webSocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closeByServer) throws Exception {
            super.onDisconnected(webSocket, serverCloseFrame, clientCloseFrame, closeByServer);
            Logger.d(WsManager.this.TAG, "wsmodule onDisconnected.");
            WsManager.this.setStatus(WsStatus.CONNECT_FAIL);
            WsManager.this.onConnectErrorHandle();
        }
    }

    /* access modifiers changed from: private */
    public void onConnectErrorHandle() {
        if (this.reconnectCount > 5) {
            this.reconnectCount = 0;
            if (this.wsCallback != null) {
                this.wsCallback.onConnected(this.wsStatus);
            }
        } else if (!reconnect() && this.wsCallback != null) {
            this.wsCallback.onConnected(this.wsStatus);
        }
    }

    private boolean isNetConnect() {
        NetworkInfo networkInfo;
        ConnectivityManager connectivityM = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        if (connectivityM == null || (networkInfo = connectivityM.getActiveNetworkInfo()) == null || !networkInfo.isConnected() || networkInfo.getState() != NetworkInfo.State.CONNECTED) {
            return false;
        }
        return true;
    }

    private boolean reconnect() {
        if (!isNetConnect()) {
            this.reconnectCount = 0;
            return false;
        } else if (this.ws == null || this.ws.isOpen() || getWsStatus() == WsStatus.CONNECTING) {
            return false;
        } else {
            this.reconnectCount++;
            setStatus(WsStatus.CONNECTING);
            long reconnectTime = this.minInterval;
            if (this.reconnectCount > 3) {
                long temp = this.minInterval * ((long) (this.reconnectCount - 2));
                if (temp > this.maxInterval) {
                    reconnectTime = this.maxInterval;
                } else {
                    reconnectTime = temp;
                }
            }
            this.mHandler.postDelayed(this.mReconnectTask, reconnectTime);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void cancelReconnect() {
        this.reconnectCount = 0;
        this.mHandler.removeCallbacks(this.mReconnectTask);
    }
}
