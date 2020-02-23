package com.tencent.midas.oversea.business;

import android.os.Message;
import android.view.View;
import com.tencent.midas.oversea.data.channel.APUserSelInfo;

class p implements View.OnClickListener {
    final /* synthetic */ APRegionMolpinFragment a;

    p(APRegionMolpinFragment aPRegionMolpinFragment) {
        this.a = aPRegionMolpinFragment;
    }

    public void onClick(View view) {
        Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
        APUserSelInfo aPUserSelInfo = new APUserSelInfo();
        aPUserSelInfo.channel = APPayMananger.singleton().getCurPayHub().getChannelItem(this.a.a);
        aPUserSelInfo.country = this.a.b;
        aPUserSelInfo.currency = this.a.c;
        aPUserSelInfo.srcActivity = this.a.getActivity();
        obtainMessage.obj = aPUserSelInfo;
        obtainMessage.what = 21;
        APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
    }
}
