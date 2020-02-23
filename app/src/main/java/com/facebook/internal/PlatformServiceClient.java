package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public abstract class PlatformServiceClient implements ServiceConnection {
    private final String applicationId;
    private final Context context;
    private final Handler handler;
    private CompletedListener listener;
    private final int protocolVersion;
    private int replyMessage;
    private int requestMessage;
    private boolean running;
    private Messenger sender;

    public interface CompletedListener {
        void completed(Bundle bundle);
    }

    /* access modifiers changed from: protected */
    public abstract void populateRequestBundle(Bundle bundle);

    public PlatformServiceClient(Context context2, int requestMessage2, int replyMessage2, int protocolVersion2, String applicationId2) {
        Context applicationContext = context2.getApplicationContext();
        this.context = applicationContext == null ? context2 : applicationContext;
        this.requestMessage = requestMessage2;
        this.replyMessage = replyMessage2;
        this.applicationId = applicationId2;
        this.protocolVersion = protocolVersion2;
        this.handler = new Handler() {
            public void handleMessage(Message message) {
                PlatformServiceClient.this.handleMessage(message);
            }
        };
    }

    public void setCompletedListener(CompletedListener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: protected */
    public Context getContext() {
        return this.context;
    }

    public boolean start() {
        Intent intent;
        if (this.running || NativeProtocol.getLatestAvailableProtocolVersionForService(this.protocolVersion) == -1 || (intent = NativeProtocol.createPlatformServiceIntent(this.context)) == null) {
            return false;
        }
        this.running = true;
        this.context.bindService(intent, this, 1);
        return true;
    }

    public void cancel() {
        this.running = false;
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        this.sender = new Messenger(service);
        sendMessage();
    }

    public void onServiceDisconnected(ComponentName name) {
        this.sender = null;
        try {
            this.context.unbindService(this);
        } catch (IllegalArgumentException e) {
        }
        callback((Bundle) null);
    }

    private void sendMessage() {
        Bundle data = new Bundle();
        data.putString(NativeProtocol.EXTRA_APPLICATION_ID, this.applicationId);
        populateRequestBundle(data);
        Message request = Message.obtain((Handler) null, this.requestMessage);
        request.arg1 = this.protocolVersion;
        request.setData(data);
        request.replyTo = new Messenger(this.handler);
        try {
            this.sender.send(request);
        } catch (RemoteException e) {
            callback((Bundle) null);
        }
    }

    /* access modifiers changed from: protected */
    public void handleMessage(Message message) {
        if (message.what == this.replyMessage) {
            Bundle extras = message.getData();
            if (extras.getString(NativeProtocol.STATUS_ERROR_TYPE) != null) {
                callback((Bundle) null);
            } else {
                callback(extras);
            }
            try {
                this.context.unbindService(this);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    private void callback(Bundle result) {
        if (this.running) {
            this.running = false;
            CompletedListener callback = this.listener;
            if (callback != null) {
                callback.completed(result);
            }
        }
    }
}
