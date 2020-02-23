package com.tencent.tp;

import java.io.UnsupportedEncodingException;

public class TssNativeMethodImp implements ITssNativeMethod {
    public void forceExit() {
        TssSdk.forceExit();
    }

    public int hasMatchRate(int i) {
        return TssSdk.hasMatchRate(i);
    }

    public int isRookitRunning() {
        return TssSdk.isRookitRunning();
    }

    public int isToastEnabled() {
        return TssSdk.isToastEnabled();
    }

    public void loadConfig(Object obj) {
        TssSdk.loadConfig(obj);
    }

    public void loadMalwareScanInfo(Object obj) {
        TssSdk.loadMalwareScanInfo(obj);
    }

    public void loadMessageBoxInfo(Object obj) {
        TssSdk.loadMessageBoxInfo(obj);
    }

    public void loadRootkitTipStr(Object obj) {
        TssSdk.loadRootkitTipStr(obj);
    }

    public void onRuntimeInfo(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("utf-8");
        if (bytes != null && bytes.length > 0) {
            TssSdk.onruntimeinfo(bytes, bytes.length);
        }
    }

    public void sendStringToSvr(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("utf-8");
        if (bytes != null && bytes.length > 0) {
            TssSdk.senddatatosvr(bytes, bytes.length);
        }
    }

    public void setcancelupdaterootkit() {
        TssSdk.setcancelupdaterootkit();
    }

    public void setrootkittipstate(int i) {
        TssSdk.setrootkittipstate(i);
    }
}
