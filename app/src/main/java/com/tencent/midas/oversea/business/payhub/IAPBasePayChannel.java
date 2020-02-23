package com.tencent.midas.oversea.business.payhub;

import android.app.Activity;
import android.os.Handler;
import com.tencent.midas.oversea.data.channel.ChannelItem;
import com.tencent.midas.oversea.data.channel.GoodsItem;

public interface IAPBasePayChannel {
    void gotoPay(Activity activity, Handler handler, ChannelItem channelItem, GoodsItem goodsItem, String str, String str2, boolean z);

    boolean hasGoodsList();
}
