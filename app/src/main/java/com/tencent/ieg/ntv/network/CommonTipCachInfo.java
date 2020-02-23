package com.tencent.ieg.ntv.network;

public class CommonTipCachInfo {
    public boolean mShowBtn = false;
    public boolean mShowPannel = false;
    public String mTipText = "";

    public CommonTipCachInfo(boolean showPannel, boolean showBtn, String tipText) {
        this.mShowPannel = showPannel;
        this.mShowBtn = showBtn;
        this.mTipText = tipText;
    }
}
