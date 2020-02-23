package com.tencent.component.net.download.multiplex.download;

public class DownloadSpeedData {
    public long mDataSize;
    public long mReceiveTime;

    public DownloadSpeedData(long dataSize, long receiveTime) {
        this.mDataSize = dataSize;
        this.mReceiveTime = receiveTime;
    }
}
