package com.tencent.imsdk.stat.api;

public interface IMStatPlatInterface {
    String[] setCrashReportChannels();

    String[] setEventReportChannels();

    String[] setExceptionReportChannels();

    String[] setPurchaseReportChannels();

    String[] setTestSpeedReportChannels();

    String[] setTrackEventReportChannels();

    String[] setTrackPageChannels();
}
