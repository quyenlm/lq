package com.tencent.tp;

import java.io.UnsupportedEncodingException;

public interface ITssNativeMethod {
    void forceExit();

    int hasMatchRate(int i);

    int isRookitRunning();

    int isToastEnabled();

    void loadConfig(Object obj);

    void loadMalwareScanInfo(Object obj);

    void loadMessageBoxInfo(Object obj);

    void loadRootkitTipStr(Object obj);

    void onRuntimeInfo(String str) throws UnsupportedEncodingException;

    void sendStringToSvr(String str) throws UnsupportedEncodingException;

    void setcancelupdaterootkit();

    void setrootkittipstate(int i);
}
