package com.tencent.imsdk.framework.request;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import com.tencent.imsdk.api.IMSystem;

public class IMSDKServer {
    private static final int MSG_REQUEST = 0;
    private static volatile IMSDKServer instance;
    private Handler mCallHandler;
    /* access modifiers changed from: private */
    public HttpRequestManager mHttpRequestManager;

    public static IMSDKServer getInstance() {
        if (instance == null) {
            synchronized (IMSDKServer.class) {
                if (instance == null) {
                    instance = new IMSDKServer();
                }
            }
        }
        return instance;
    }

    private IMSDKServer() {
    }

    public void onCreate(Activity activtiy) {
        this.mHttpRequestManager = new HttpRequestManager();
        this.mHttpRequestManager.init(activtiy);
        this.mCallHandler = new Handler(IMSystem.getInstance().getLooper(1)) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (msg.obj != null && (msg.obj instanceof HttpRequest)) {
                            IMSDKServer.this.mHttpRequestManager.postRequest((HttpRequest) msg.obj);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public void doRequest(HttpRequest request) {
        Message msg = this.mCallHandler.obtainMessage();
        msg.what = 0;
        msg.obj = request;
        this.mCallHandler.sendMessage(msg);
    }
}
