package com.tencent.midas.oversea.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.utils.APFindViewUtils;

public class APRegionFortumoFragment extends APAbstractContentFragment {
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public String c;

    private void a(View view) {
        ((Button) APFindViewUtils.findViewById(view, "unipay_id_fortumo_btn")).setOnClickListener(new m(this));
    }

    public static APRegionFortumoFragment newInstance(String str, String str2, String str3) {
        APRegionFortumoFragment aPRegionFortumoFragment = new APRegionFortumoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("channel_name", str);
        bundle.putString("country", str2);
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, str3);
        aPRegionFortumoFragment.setArguments(bundle);
        return aPRegionFortumoFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mViewRoot = layoutInflater.inflate(APCommMethod.getLayoutId(layoutInflater.getContext(), "unipay_fortumo_view"), viewGroup, false);
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
