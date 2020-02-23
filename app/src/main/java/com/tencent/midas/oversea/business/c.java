package com.tencent.midas.oversea.business;

import android.view.View;
import android.widget.AdapterView;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.data.channel.ChannelGoods;

class c implements AdapterView.OnItemClickListener {
    final /* synthetic */ APChannelsListAdapter a;
    final /* synthetic */ APMallActivity b;

    c(APMallActivity aPMallActivity, APChannelsListAdapter aPChannelsListAdapter) {
        this.b = aPMallActivity;
        this.a = aPChannelsListAdapter;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        String str = ((ChannelGoods) this.b.b.get(i)).channel.id;
        this.b.a.setSelected(false);
        this.a.setSelectItem(i);
        this.a.notifyDataSetInvalidated();
        this.b.a(str);
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_SHOW, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, str);
    }
}
