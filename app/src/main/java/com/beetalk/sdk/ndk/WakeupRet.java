package com.beetalk.sdk.ndk;

import com.garena.pay.android.GGErrorCode;

public class WakeupRet {
    public int flag = GGErrorCode.SUCCESS.getCode().intValue();
    public String fromOpenId;
    public String gameId;
    public String gameUri;
    public String mediaTag;
    public String openId;
    public int platform;
}
