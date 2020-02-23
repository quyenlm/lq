package com.tencent.midas.oversea.business;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import com.tencent.midas.oversea.data.channel.APUserSelInfo;
import com.tencent.midas.oversea.data.channel.GoodsItem;
import java.util.ArrayList;

class n implements AdapterView.OnItemClickListener {
    final /* synthetic */ ArrayList a;
    final /* synthetic */ APRegionGridContentFragment b;

    n(APRegionGridContentFragment aPRegionGridContentFragment, ArrayList arrayList) {
        this.b = aPRegionGridContentFragment;
        this.a = arrayList;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
        APUserSelInfo aPUserSelInfo = new APUserSelInfo();
        aPUserSelInfo.channel = APPayMananger.singleton().getCurPayHub().getChannelItem(this.b.a);
        aPUserSelInfo.country = this.b.e;
        aPUserSelInfo.currency = this.b.d;
        aPUserSelInfo.goods = (GoodsItem) this.a.get(i);
        aPUserSelInfo.srcActivity = this.b.getActivity();
        obtainMessage.obj = aPUserSelInfo;
        obtainMessage.what = 21;
        APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
    }
}
