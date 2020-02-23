package com.garena.android.gpns.network;

import android.os.HandlerThread;
import android.os.Looper;
import com.garena.android.gpns.network.exception.CannotSendPacketException;
import com.garena.android.gpns.network.exception.UnableToConnectException;
import com.garena.android.gpns.network.tcp.TCPConnection;
import com.garena.android.gpns.network.tcp.TCPPacket;
import com.garena.android.gpns.network.tcp.TCPPacketTimer;
import com.garena.android.gpns.utility.AppLogger;
import com.garena.android.gpns.utility.TCPPacketFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkThread extends HandlerThread implements NetworkRequestHandler, TCPPacketTimer.OnTimeoutListener, PacketReadListener {
    private static final String NETWORK_THREAD = "NETWORK_THREAD";
    private final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private NetworkHandler mNetworkHandler;
    private ReadHandler mReadHandler;
    private final NetworkResponseListener mResponseHandler;
    private TCPConnection mTCPConnection;

    public NetworkThread(NetworkResponseListener responseHandler) {
        super(NETWORK_THREAD);
        this.mResponseHandler = responseHandler;
    }

    public void connectRegionServer() {
        disconnect();
        connect(2);
    }

    public void connectAuthenticationServer() {
        disconnect();
        connect(0);
    }

    public void connectNotificationServer() {
        disconnect();
        connect(1);
    }

    /* access modifiers changed from: protected */
    public void onLooperPrepared() {
        super.onLooperPrepared();
        this.mReadHandler = new ReadHandler(getLooper(), this);
    }

    public void sendPacket(TCPPacket packet) {
        try {
            if (!this.mTCPConnection.isDisconnected()) {
                this.mTCPConnection.sendDataSynchronous(packet.toByteArray());
                if (packet.isTimed()) {
                    TCPPacketTimer timer = new TCPPacketTimer(packet);
                    timer.setOnTimeoutListener(this);
                    timer.start();
                    this.mTCPConnection.setTimer(timer);
                    return;
                }
                return;
            }
            this.mResponseHandler.onPacketFailed(packet);
        } catch (CannotSendPacketException e) {
            this.mResponseHandler.onPacketFailed(packet);
        }
    }

    public void onTimeout(TCPPacket packet) {
        AppLogger.f("TIMEOUT: " + TCPPacketFactory.getPacketName(packet.getCommand()));
        this.mTCPConnection.disconnect();
        this.mResponseHandler.onPacketFailed(packet);
    }

    public void onPacketRead(TCPPacket packet) {
        if (!this.mTCPConnection.isDisconnected()) {
            if (!this.mTCPConnection.getKeepConnected()) {
                this.mTCPConnection.disconnect();
            }
            TCPPacketTimer timer = this.mTCPConnection.getTimer();
            if (timer != null && timer.isWaitingFor(packet)) {
                timer.stop();
            }
            this.mResponseHandler.onResponseArrived(packet);
        }
    }

    public void onReadFailed(int connectionId) {
        if (!this.mTCPConnection.isDisconnected()) {
            this.mTCPConnection.disconnect();
            this.mResponseHandler.onConnectionDropped(connectionId);
        }
    }

    private void connect(int connectionId) {
        this.mTCPConnection = TCPFactory.newConnection(connectionId);
        try {
            this.mTCPConnection.connect();
            initPacketReceiver();
            this.mResponseHandler.onConnectionOK(connectionId);
        } catch (UnableToConnectException e) {
            this.mResponseHandler.onUnableToConnect(connectionId);
        }
    }

    private void initPacketReceiver() {
        this.mExecutorService.submit(new ReceiverThread(this.mTCPConnection.getId(), TCPFactory.newPacketReader(this.mTCPConnection.getInputStream()), this.mReadHandler));
    }

    private void disconnect() {
        if (this.mTCPConnection != null) {
            this.mTCPConnection.disconnect();
        }
    }

    public boolean isTCPDisconnected() {
        return this.mTCPConnection != null && this.mTCPConnection.isDisconnected();
    }

    public NetworkRequestHandler getHandler() {
        Looper looper = getLooper();
        if (this.mNetworkHandler == null && looper != null) {
            this.mNetworkHandler = new NetworkHandler(looper, this);
        }
        return this.mNetworkHandler;
    }

    public boolean quit() {
        this.mTCPConnection.disconnect();
        this.mExecutorService.shutdownNow();
        this.mNetworkHandler = null;
        return super.quit();
    }
}
