package com.tencent.midas.oversea.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.data.channel.APMolPinInfo;
import com.tencent.midas.oversea.utils.APFindViewUtils;

public class APRegionMolpinFragment extends APAbstractContentFragment {
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public String c;

    private void a(View view) {
        ((Button) APFindViewUtils.findViewById(view, "unipay_id_molpin_btn")).setOnClickListener(new p(this));
        TextView textView = (TextView) APFindViewUtils.findViewById(view, "unipay_id_exchange_rate_one");
        if (getResources().getConfiguration().orientation == 2) {
            textView.setText(APMolPinInfo.getMolPinInfoLand());
        } else {
            textView.setText(APMolPinInfo.getMolPinInfoPort());
        }
    }

    public static APRegionMolpinFragment newInstance(String str, String str2, String str3) {
        APRegionMolpinFragment aPRegionMolpinFragment = new APRegionMolpinFragment();
        Bundle bundle = new Bundle();
        bundle.putString("channel_name", str);
        bundle.putString("country", str2);
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, str3);
        aPRegionMolpinFragment.setArguments(bundle);
        return aPRegionMolpinFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mViewRoot = layoutInflater.inflate(APCommMethod.getLayoutId(layoutInflater.getContext(), "unipay_molpin_view"), viewGroup, false);
        return this.mViewRoot;
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (APPayMananger.singleton().getCurPayHub() == null) {
            getActivity().finish();
            return;
        }
        this.a = getArguments().getString("channel_name");
        this.b = getArguments().getString("country");
        this.c = getArguments().getString(FirebaseAnalytics.Param.CURRENCY);
        a(this.mViewRoot);
    }
}
