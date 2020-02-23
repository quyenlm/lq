package com.tencent.qt.alg.network;

public interface NetworkFlowController {
    void onConnectionFail(int i, String str, int i2, int i3, boolean z);

    void onConnectionSuccess(int i, String str, int i2, int i3, boolean z);

    void onHostResloveFailure(int i, String str, int i2);

    void onHostResolveSuccess(int i, String str, String str2, int i2);

    void onPacketReceived(int i, int i2, int i3, int i4, int i5, int i6);

    void onPacketSended(int i, int i2, int i3, int i4, int i5);

    void onRequestFail(int i, String str, int i2, int i3, int i4, int i5, int i6, boolean z);
}
