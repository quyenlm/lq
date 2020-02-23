package com.tencent.tp;

public class TssSdk {
    public static final String TSS_SDK_VERSION = "3.5.8(2018/08/14)-jar-version";

    public interface ISendDataToSvr {
        int sendDataToSvr(byte[] bArr, int i);
    }

    static {
        System.loadLibrary("tersafe");
    }

    public static String a(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        TssIOCtlResult tssIOCtlResult = new TssIOCtlResult();
        tssIOCtlResult.cmd = str;
        if (getsdkantidata(tssIOCtlResult) == 0) {
            return tssIOCtlResult.response;
        }
        return null;
    }

    public static native void forceExit();

    public static native int getsdkantidata(TssIOCtlResult tssIOCtlResult);

    public static native int hasMatchRate(int i);

    public static native void init(TssSdkInitInfo tssSdkInitInfo);

    public static native int isRookitRunning();

    public static native int isToastEnabled();

    public static native void loadConfig(Object obj);

    public static native void loadMalwareScanInfo(Object obj);

    public static native void loadMessageBoxInfo(Object obj);

    public static native void loadRootkitTipStr(Object obj);

    public static native void onruntimeinfo(byte[] bArr, int i);

    public static native void senddatatosdk(byte[] bArr, int i);

    public static native void senddatatosvr(byte[] bArr, int i);

    public static native void setcancelupdaterootkit();

    public static native void setgamestatus(TssSdkGameStatusInfo tssSdkGameStatusInfo);

    public static native void setrootkittipstate(int i);

    public static native void setsenddatatosvrcb(ISendDataToSvr iSendDataToSvr);

    public static native void setuserinfo(TssSdkUserInfo tssSdkUserInfo);

    public static native void setuserinfoex(TssSdkUserInfoEx tssSdkUserInfoEx);
}
