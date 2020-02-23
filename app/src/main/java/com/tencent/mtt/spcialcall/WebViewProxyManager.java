package com.tencent.mtt.spcialcall;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.mtt.spcialcall.remote.ISpecialCallClient;
import com.tencent.mtt.spcialcall.remote.ISpecialCallService;
import com.tencent.mtt.spcialcall.sdk.WebViewClientProxy;
import com.tencent.mtt.spcialcall.sdk.WebViewProxy;
import com.tencent.smtt.sdk.TbsConfig;
import java.util.HashMap;
import org.json.JSONArray;

public class WebViewProxyManager extends ISpecialCallClient.Stub {
    private static final String TAG = "WebViewProxy";
    private static WebViewProxyManager sInstance;
    boolean mBinded;
    ServiceConnection mConnection;
    private Context mContext;
    /* access modifiers changed from: private */
    public HashMap<Long, WebViewProxy> mProxies = new HashMap<>();
    ISpecialCallService mService;

    public static WebViewProxyManager getInstance() {
        if (sInstance == null) {
            sInstance = new WebViewProxyManager();
        }
        return sInstance;
    }

    private WebViewProxyManager() {
    }

    public WebViewProxy create(Activity activity) {
        WebViewProxy proxy = new WebViewProxy(activity);
        this.mProxies.put(Long.valueOf(proxy.getId()), proxy);
        return proxy;
    }

    public boolean bindServiceIfNeed(Context context) {
        if (this.mBinded) {
            return true;
        }
        if (this.mConnection == null) {
            this.mConnection = new ServiceConnection() {
                public void onServiceDisconnected(ComponentName name) {
                    WebViewProxyManager.this.mService = null;
                    WebViewProxyManager.this.mBinded = false;
                    WebViewProxyManager.this.mProxies.clear();
                }

                public void onServiceConnected(ComponentName name, IBinder service) {
                    WebViewProxyManager.this.mService = ISpecialCallService.Stub.asInterface(service);
                    try {
                        WebViewProxyManager.this.mService.registerClient(WebViewProxyManager.this);
                    } catch (RemoteException e) {
                        Log.w(WebViewProxyManager.TAG, e);
                    }
                }
            };
        }
        this.mContext = context.getApplicationContext();
        Intent intent = new Intent();
        intent.setClassName(TbsConfig.APP_QB, "com.tencent.mtt.spcialcall.remote.SpecialCallService");
        this.mBinded = this.mContext.bindService(intent, this.mConnection, 1);
        return this.mBinded;
    }

    public void onWebViewDestroy(long viewId) {
        this.mProxies.remove(Long.valueOf(viewId));
        if (this.mProxies.isEmpty() && this.mBinded) {
            this.mContext.unbindService(this.mConnection);
            this.mBinded = false;
        }
    }

    public void loadUrlRemote(long viewId, String url) {
        if (this.mService == null) {
            Log.d(TAG, "Service not bind");
            return;
        }
        try {
            this.mService.loadUrl(viewId, url);
        } catch (Throwable e) {
            Log.w(TAG, e);
        }
    }

    public String onJsCall(long viewId, String service, String name, String args) {
        WebViewProxy proxy = this.mProxies.get(Long.valueOf(viewId));
        if (proxy == null) {
            Log.d(TAG, "No proxy for " + viewId + " was found; ignore");
            return null;
        }
        try {
            return JsUtils.exec(proxy, service, name, TextUtils.isEmpty(args) ? null : new JSONArray(args));
        } catch (Exception e) {
            Log.w(TAG, e);
            return null;
        }
    }

    public void onPageStarted(long viewId, String url) {
        WebViewProxy proxy = this.mProxies.get(Long.valueOf(viewId));
        if (proxy == null) {
            Log.d(TAG, "No proxy for " + viewId + " was found");
            return;
        }
        WebViewClientProxy client = proxy.getWebViewClient();
        if (client != null) {
            client.onPageStarted(proxy, url);
        }
    }

    public void onPageFinished(long viewId, String url) {
        WebViewProxy proxy = this.mProxies.get(Long.valueOf(viewId));
        if (proxy == null) {
            Log.d(TAG, "No proxy for " + viewId + " was found");
            return;
        }
        WebViewClientProxy client = proxy.getWebViewClient();
        if (client != null) {
            client.onPageFinished(proxy, url);
        }
    }

    public void onReceivedError(long viewId, int errorCode, String description, String failingUrl) {
        WebViewProxy proxy = this.mProxies.get(Long.valueOf(viewId));
        if (proxy == null) {
            Log.d(TAG, "No proxy for " + viewId + " was found");
            return;
        }
        WebViewClientProxy client = proxy.getWebViewClient();
        if (client != null) {
            client.onReceivedError(proxy, errorCode, description, failingUrl);
        }
    }

    public boolean shouldOverrideUrlLoading(long viewId, String url) {
        WebViewProxy proxy = this.mProxies.get(Long.valueOf(viewId));
        if (proxy == null) {
            Log.d(TAG, "No proxy for " + viewId + " was found");
            return false;
        }
        WebViewClientProxy client = proxy.getWebViewClient();
        if (client != null) {
            return client.shouldOverrideUrlLoading(proxy, url);
        }
        return false;
    }
}
