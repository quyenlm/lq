package com.tencent.midas.oversea.business;

import android.os.Message;
import android.view.View;
import com.tencent.midas.oversea.data.channel.APUserSelInfo;
import com.tencent.midas.oversea.data.channel.GoodsItem;

class m implements View.OnClickListener {
    final /* synthetic */ APRegionFortumoFragment a;

    m(APRegionFortumoFragment aPRegionFortumoFragment) {
        this.a = aPRegionFortumoFragment;
    }

    public void onClick(View view) {
        Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
        APUserSelInfo aPUserSelInfo = new APUserSelInfo();
        aPUserSelInfo.channel = APPayMananger.singleton().getCurPayHub().getChannelItem(this.a.a);
        aPUserSelInfo.country = this.a.b;
        aPUserSelInfo.currency = this.a.c;
        aPUserSelInfo.srcActivity = this.a.getActivity();
        aPUserSelInfo.goods = new GoodsItem();
        aPUserSelInfo.goods.currency_type = this.a.c;
        aPUserSelInfo.goods.price = "1";
        aPUserSelInfo.goods.num = "1";
        obtainMessage.obj = aPUserSelInfo;
        obtainMessage.what = 21;
        APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
    }
}
