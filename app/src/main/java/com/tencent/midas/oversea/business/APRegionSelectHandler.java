package com.tencent.midas.oversea.business;

import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.data.channel.APCountryInfo;
import com.tencent.midas.oversea.utils.APFindViewUtils;
import java.util.List;

public class APRegionSelectHandler implements View.OnClickListener {
    private LinearLayout a;
    private TextView b = ((TextView) APFindViewUtils.findViewById((View) this.a, "unipay_id_currentRegion_text"));
    private ImageView c = ((ImageView) APFindViewUtils.findViewById((View) this.a, "unipay_id_currentRegion_image"));
    private GridView d;
    private APCountryInfo e;

    public APRegionSelectHandler(LinearLayout linearLayout, GridView gridView, List<APCountryInfo> list) {
        this.a = linearLayout;
        this.a.setOnClickListener(this);
        this.a.setTag(false);
        this.d = gridView;
        this.d.setTag(false);
        this.d.setAdapter(new APRegionsListAdapter(linearLayout.getContext(), list));
    }

    public void hide() {
        this.d.setVisibility(8);
        this.a.setVisibility(8);
    }

    public void onClick(View view) {
        if (view.getId() == APCommMethod.getId(view.getContext(), "unipay_id_currentRegion")) {
            toggleAllRegionVisibility();
        }
    }

    public void setCurrentCountry(APCountryInfo aPCountryInfo) {
        this.e = aPCountryInfo;
        APPayMananger.singleton().getApplicationContext();
        try {
            int drawableId = APCommMethod.getDrawableId(APPayMananger.singleton().getApplicationContext(), APRegionsListAdapter.FLAG_PREFIX + aPCountryInfo.countryname.toLowerCase());
            if (drawableId > 0) {
                this.b.setText(this.e.countryname);
                this.c.setImageResource(drawableId);
                this.c.setVisibility(0);
                return;
            }
            this.c.setVisibility(8);
            String stringId = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), APRegionsListAdapter.COUNTRY_PREFIX + aPCountryInfo.countryname.toUpperCase());
            if (TextUtils.isEmpty(stringId)) {
                stringId = aPCountryInfo.countryname;
            }
            this.b.setText(stringId);
        } catch (Exception e2) {
            APLog.e("LogTag", e2.toString());
        }
    }

    public void toggleAllRegionVisibility() {
        Boolean bool = (Boolean) this.d.getTag();
        if (bool.booleanValue()) {
            this.d.setVisibility(8);
        } else {
            this.d.setVisibility(0);
            APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_COUNTRY_SHOW, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, (String) null);
        }
        this.d.setTag(Boolean.valueOf(!bool.booleanValue()));
    }
}
