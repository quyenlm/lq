package com.tencent.midas.oversea.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.utils.APFindViewUtils;
import java.util.ArrayList;

public class APChannelsListAdapter extends BaseAdapter {
    private ArrayList<String> a;
    private LayoutInflater b;
    private int c;

    private static class a {
        public ImageView a;
        public TextView b;

        private a() {
        }
    }

    public APChannelsListAdapter(Context context, ArrayList<String> arrayList) {
        this.a = arrayList;
        this.b = LayoutInflater.from(context);
    }

    private int a(String str) {
        if ("mol_acct".equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdmoney");
        }
        if ("mol_pin".equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdmoney");
        }
        if ("mol_hf".equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdmoney");
        }
        if ("paymentwall".equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdpaym");
        }
        if (UnityPayHelper.GWALLET.equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdgooglew");
        }
        if ("boku".equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdboku");
        }
        if ("twmycard".equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdmycard");
        }
        if ("fortumo".equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdfortumo");
        }
        if ("testpay".equals(str)) {
            return APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_testpay");
        }
        int identifier = this.b.getContext().getResources().getIdentifier("unipay_abroad_qd_" + str, "drawable", this.b.getContext().getPackageName());
        return identifier <= 0 ? APCommMethod.getDrawableId(this.b.getContext(), "unipay_abroad_qdmol") : identifier;
    }

    public int getCount() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.b.inflate(APCommMethod.getLayoutId(this.b.getContext(), "unipay_abroad_item_channel_list"), (ViewGroup) null, false);
            aVar = new a();
            aVar.a = (ImageView) APFindViewUtils.findViewById(view, "unipay_id_channel_item_image");
            aVar.b = (TextView) APFindViewUtils.findViewById(view, "unipay_id_channel_item_text");
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        if (i == this.c) {
            view.setBackgroundColor(-13553359);
        } else {
            view.setBackgroundColor(0);
        }
        String str = this.a.get(i);
        try {
            aVar.b.setText(APPayMananger.singleton().getCurPayHub().getChannelItem(str).name);
            aVar.a.setImageResource(a(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setSelectItem(int i) {
        this.c = i;
    }
}
