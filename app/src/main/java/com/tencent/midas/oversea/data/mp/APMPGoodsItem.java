package com.tencent.midas.oversea.data.mp;

import android.text.TextUtils;

public class APMPGoodsItem {
    public String name;
    public String num;
    public String url;

    public boolean getIsHasSend() {
        return !TextUtils.isEmpty(this.name);
    }
}
