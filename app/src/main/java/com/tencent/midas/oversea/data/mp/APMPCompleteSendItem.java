package com.tencent.midas.oversea.data.mp;

public class APMPCompleteSendItem {
    public APMPSendItem pointSend;
    public APMPSendItem sectionSend;

    public void clear() {
        this.sectionSend = null;
        this.pointSend = null;
    }
}
