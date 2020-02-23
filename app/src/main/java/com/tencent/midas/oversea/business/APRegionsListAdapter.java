package com.tencent.midas.oversea.business;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.data.channel.APCountryInfo;
import com.tencent.midas.oversea.utils.APFindViewUtils;
import java.util.List;

public class APRegionsListAdapter extends BaseAdapter {
    public static final String COUNTRY_PREFIX = "unipay_country_";
    public static final String FLAG_PREFIX = "unipay_abroad_flag_";
    private LayoutInflater a;
    private List<APCountryInfo> b;

    private static class a {
        public ImageView a;
        public TextView b;

        private a() {
        }
    }

    public APRegionsListAdapter(Context context, List<APCountryInfo> list) {
        this.a = LayoutInflater.from(context);
        this.b = list;
    }

    public int getCount() {
        if (this.b == null || this.b.size() <= 0) {
            return 0;
        }
        return this.b.size();
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
            view = this.a.inflate(APCommMethod.getLayoutId(this.a.getContext(), "unipay_adroad_item_all_region"), viewGroup, false);
            aVar = new a();
            aVar.a = (ImageView) APFindViewUtils.findViewById(view, "unipay_id_all_region_item_image");
            aVar.b = (TextView) APFindViewUtils.findViewById(view, "unipay_id_all_region_item_text");
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        int drawableId = APCommMethod.getDrawableId(APPayMananger.singleton().getApplicationContext(), FLAG_PREFIX + this.b.get(i).countryname.toLowerCase());
        if (drawableId > 0) {
            aVar.a.setBackgroundResource(drawableId);
            aVar.b.setText(this.b.get(i).countryname);
        } else {
            aVar.a.setVisibility(8);
            String stringId = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), COUNTRY_PREFIX + this.b.get(i).countryname.toUpperCase());
            if (TextUtils.isEmpty(stringId)) {
                stringId = this.b.get(i).countryname;
            }
            aVar.b.setText(stringId);
        }
        return view;
    }
}
