package com.tencent.tp;

public class j implements ITssJavaMethod {
    public void initialize() {
        TssSdkRuntime.initialize2();
    }

    public void invokeForceUpdateRootkitAppRequest() {
        x.invokeForceUpdateRootkitAppRequest();
    }

    public void invokeRootkitAppRequest() {
        x.invokeRootkitAppRequest();
    }

    public void invokeRootkitIsRunningTip() {
        x.invokeRootkitIsRunningTip();
    }

    public void scan() {
        TssSdkSafeScan.scan(true, false, false);
    }

    public void showMsgBoxEx() {
    }
}
