package com.tencent.qqgamemi.mgc.connection;

import com.tencent.qt.alg.network.NetworkFlowController;

public class ProtocolFlowMonitor implements NetworkFlowController {
    private String mIdentity = "";

    public void setIdentity(String identity) {
        this.mIdentity = identity;
    }

    public void onPacketReceived(int type, int command, int subcmd, int seq, int len, int elapsed) {
    }

    public void onPacketSended(int type, int command, int subcmd, int seq, int len) {
    }

    public void onRequestFail(int type, String host, int port, int command, int subcmd, int seq, int err, boolean isLogined) {
    }

    public void onConnectionSuccess(int type, String host, int port, int elapsed, boolean isRetry) {
    }

    public void onConnectionFail(int type, String host, int port, int err, boolean isRetry) {
    }

    public void onHostResolveSuccess(int type, String host, String ip, int elapsed) {
    }

    public void onHostResloveFailure(int type, String host, int error) {
    }
}
