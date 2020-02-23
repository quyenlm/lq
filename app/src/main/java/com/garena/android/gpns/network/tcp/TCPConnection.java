package com.garena.android.gpns.network.tcp;

import com.garena.android.gpns.network.exception.CannotSendPacketException;
import com.garena.android.gpns.network.exception.UnableToConnectException;
import com.garena.android.gpns.utility.AppLogger;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPConnection {
    public static final int STATUS_CONNECTED = 0;
    public static final int STATUS_DISCONNECTED = 1;
    private final String mIP;
    private int mId = -1;
    private InputStream mInput;
    private boolean mKeepConnected = true;
    private OutputStream mOutput;
    private final int mPort;
    private Socket mSocket;
    private int mStatus;
    private TCPPacketTimer mTimer;

    public TCPConnection(String ip, int port, int id) {
        this.mIP = ip;
        this.mPort = port;
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }

    public boolean getKeepConnected() {
        return this.mKeepConnected;
    }

    public void setKeepConnected(boolean keepConnected) {
        this.mKeepConnected = keepConnected;
    }

    public void connect() throws UnableToConnectException {
        disconnect();
        try {
            this.mSocket = new Socket(this.mIP, this.mPort);
            this.mSocket.setTcpNoDelay(true);
            this.mSocket.setKeepAlive(true);
            this.mInput = this.mSocket.getInputStream();
            this.mOutput = this.mSocket.getOutputStream();
            this.mOutput.flush();
            setStatus(0);
        } catch (IOException e) {
            AppLogger.e((Throwable) e);
            disconnect();
            throw new UnableToConnectException(e);
        }
    }

    public InputStream getInputStream() {
        return this.mInput;
    }

    public void disconnect() {
        stopTimer();
        setStatus(1);
        close();
    }

    public void sendDataSynchronous(byte[] data) throws CannotSendPacketException {
        sendDataSynchronous(data, 0, data.length);
    }

    public void sendDataSynchronous(byte[] data, int offset, int length) throws CannotSendPacketException {
        try {
            this.mOutput.write(data, offset, length);
            this.mOutput.flush();
        } catch (Exception ex) {
            AppLogger.e((Throwable) ex);
            disconnect();
            throw new CannotSendPacketException(ex);
        }
    }

    public boolean isDisconnected() {
        return this.mStatus == 1;
    }

    private void setStatus(int status) {
        this.mStatus = status;
    }

    /* access modifiers changed from: protected */
    public void close() {
        close(this.mSocket);
        close((Closeable) this.mInput);
        close((Closeable) this.mOutput);
    }

    private void close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    private void stopTimer() {
        if (this.mTimer != null) {
            this.mTimer.stop();
        }
    }

    public synchronized void setTimer(TCPPacketTimer timer) {
        stopTimer();
        this.mTimer = timer;
    }

    public synchronized TCPPacketTimer getTimer() {
        return this.mTimer;
    }
}
