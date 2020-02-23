package com.tencent.midas.oversea.business;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APUICommonMethod;
import com.tencent.midas.oversea.data.channel.APCountryInfo;
import com.tencent.midas.oversea.data.channel.GoodsItem;
import com.tencent.midas.oversea.data.mp.APMPSendInfo;
import com.tencent.midas.oversea.utils.APFindViewUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class APRegionGridContentFragment extends APAbstractContentFragment implements View.OnClickListener {
    private static boolean h;
    /* access modifiers changed from: private */
    public String a;
    private GridView b;
    private Drawable c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e;
    /* access modifiers changed from: private */
    public ArrayList<APCountryInfo> f;
    private APContentGridAdapter g = null;
    private TextView i;
    private RelativeLayout j;
    private int k = -1;

    private void a(View view) {
        this.k = 0;
        this.e = this.f.get(0).countryname;
        this.d = this.f.get(0).currency;
        String str = APPayMananger.singleton().getCurPayHub().countryCode;
        APLog.i("APRegionGridContentFragment", "country:" + str);
        if (!TextUtils.isEmpty(str)) {
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                if (this.f.get(i2).countryname.equals(str)) {
                    this.k = i2;
                    this.e = this.f.get(i2).countryname;
                    this.d = this.f.get(i2).currency;
                }
            }
        }
        d(view);
        c(view);
        b(view);
    }

    /* access modifiers changed from: private */
    public void a(View view, ArrayList<GoodsItem> arrayList) {
        this.b = (GridView) view.findViewById(APCommMethod.getId(getActivity(), "unipay_id_grid"));
        if (h) {
            if (view.getResources().getConfiguration().orientation == 2) {
                this.b.setNumColumns(4);
            } else {
                this.b.setNumColumns(3);
            }
        }
        int i2 = APPayMananger.singleton().getCurBaseRequest().resId;
        byte[] bArr = APPayMananger.singleton().getCurBaseRequest().resData;
        if (bArr != null && bArr.length != 0) {
            this.c = APUICommonMethod.getAppResDrawable(bArr);
        } else if (i2 > 0) {
            this.c = getActivity().getResources().getDrawable(i2);
        }
        if (this.c == null) {
            this.c = APCommMethod.getDrawable(getActivity(), "unipay_abroad_iconzuan");
        }
        if (this.g == null) {
            if (this.a.equals("mol_acct") || this.a.equals("mol_hf")) {
                this.g = new APContentGridAdapter(getActivity(), arrayList, this.c, this.d, false);
            } else {
                this.g = new APContentGridAdapter(getActivity(), arrayList, this.c, this.d, true);
            }
            this.b.setAdapter(this.g);
        } else if (this.a.equals("mol_acct") || this.a.equals("mol_hf")) {
            this.g.refresh(getActivity(), arrayList, this.c, this.d, false);
        } else {
            this.g.refresh(getActivity(), arrayList, this.c, this.d, true);
        }
        this.b.setOnItemClickListener(new n(this, arrayList));
    }

    private static boolean a(ArrayList<APCountryInfo> arrayList) {
        if (arrayList == null || arrayList.size() <= 1) {
            return false;
        }
        Iterator<APCountryInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            if (TextUtils.isEmpty(it.next().countryname)) {
                return false;
            }
        }
        return true;
    }

    private void b(View view) {
        this.i = (TextView) getActivity().findViewById(APCommMethod.getId(getActivity(), "unipay_id_mpinfoIdText"));
        this.j = (RelativeLayout) getActivity().findViewById(APCommMethod.getId(getActivity(), "unipay_id_mpinfoIdView"));
        String mpTitle = APMPSendInfo.getInstance().getMpTitle();
        if (!TextUtils.isEmpty(mpTitle)) {
            this.i.setText(mpTitle);
            this.j.setVisibility(0);
        } else if (!APMPSendInfo.getInstance().getIsHasFirstMPInfo() || !APDataInterface.singleton().getUserInfo().isFirstCharge) {
            this.j.setVisibility(8);
        } else {
            this.i.setText(APMPSendInfo.getInstance().getFirstMpInfo(""));
            this.j.setVisibility(0);
        }
    }

    private void c(View view) {
        a(view, APPayMananger.singleton().getCurPayHub().getCountryGoodsList(this.a, this.e));
    }

    private void d(View view) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(APCommMethod.getId(getActivity(), "unipay_id_currentRegion"));
        GridView gridView = (GridView) APFindViewUtils.findViewById(view, "unipay_id_allRegion");
        this.f = APPayMananger.singleton().getCurPayHub().getCountryList(this.a);
        if (!a(this.f)) {
            linearLayout.setVisibility(8);
            gridView.setVisibility(8);
            return;
        }
        APRegionSelectHandler aPRegionSelectHandler = new APRegionSelectHandler(linearLayout, gridView, this.f);
        aPRegionSelectHandler.setCurrentCountry(this.f.get(this.k));
        gridView.setOnItemClickListener(new o(this, aPRegionSelectHandler));
    }

    public static APRegionGridContentFragment newInstance(String str, boolean z) {
        h = z;
        APRegionGridContentFragment aPRegionGridContentFragment = new APRegionGridContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("channel_id", str);
        aPRegionGridContentFragment.setArguments(bundle);
        return aPRegionGridContentFragment;
    }

    public void onClick(View view) {
        APLog.e("APRegionG", "onClick");
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mViewRoot = layoutInflater.inflate(APCommMethod.getLayoutId(layoutInflater.getContext(), "unipay_abroad_fragment_region_grid"), viewGroup, false);
        return this.mViewRoot;
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.a = getArguments().getString("channel_id");
        if (APPayMananger.singleton().getCurPayHub() == null) {
            getActivity().finish();
            return;
        }
        this.f = APPayMananger.singleton().getCurPayHub().getCountryList(this.a);
        a(this.mViewRoot);
    }
}
