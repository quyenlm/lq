package com.tencent.midas.oversea.business;

import android.view.View;
import android.widget.AdapterView;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.data.channel.APCountryInfo;

class o implements AdapterView.OnItemClickListener {
    final /* synthetic */ APRegionSelectHandler a;
    final /* synthetic */ APRegionGridContentFragment b;

    o(APRegionGridContentFragment aPRegionGridContentFragment, APRegionSelectHandler aPRegionSelectHandler) {
        this.b = aPRegionGridContentFragment;
        this.a = aPRegionSelectHandler;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        String unused = this.b.e = ((APCountryInfo) this.b.f.get(i)).countryname;
        String unused2 = this.b.d = ((APCountryInfo) this.b.f.get(i)).currency;
        this.b.a(this.b.mViewRoot, APPayMananger.singleton().getCurPayHub().getCountryGoodsList(this.b.a, this.b.e));
        this.a.toggleAllRegionVisibility();
        this.a.setCurrentCountry((APCountryInfo) this.b.f.get(i));
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_COUNTRY_SELECT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.b.e);
    }
}
