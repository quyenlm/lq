package com.tencent.midas.oversea.business;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.data.channel.GoodsItem;
import com.tencent.midas.oversea.utils.APFindViewUtils;
import java.util.ArrayList;
import java.util.Currency;

public class APContentGridAdapter extends BaseAdapter {
    private ArrayList<GoodsItem> a;
    private LayoutInflater b;
    private Drawable c;
    private String d;
    private boolean e;
    private Typeface f;

    private static class a {
        TextView a;
        TextView b;
        ImageView c;

        private a() {
        }
    }

    public APContentGridAdapter(Context context, ArrayList<GoodsItem> arrayList, Drawable drawable, String str, boolean z) {
        this.a = arrayList;
        this.b = LayoutInflater.from(context);
        this.c = drawable;
        this.d = str;
        this.e = z;
        this.f = Typeface.createFromAsset(context.getAssets(), "HelveticaNeueLTPro-XBlkCn.otf");
    }

    public int getCount() {
        if (this.a == null || this.a.size() <= 0) {
            return 0;
        }
        return this.a.size();
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
            view = this.b.inflate(APCommMethod.getLayoutId(this.b.getContext(), "unipay_abroad_item_content_grid"), (ViewGroup) null, false);
            aVar = new a();
            aVar.a = (TextView) APFindViewUtils.findViewById(view, "unipay_id_grid_item_number");
            aVar.a.getPaint().setFakeBoldText(true);
            aVar.b = (TextView) APFindViewUtils.findViewById(view, "unipay_id_grid_item_price_button");
            aVar.c = (ImageView) APFindViewUtils.findViewById(view, "unipay_id_grid_item_image");
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        GoodsItem goodsItem = this.a.get(i);
        String symbol = Currency.getInstance(this.d).getSymbol();
        if (!this.e) {
            aVar.b.setText(APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_buy"));
        } else if (this.d.equals("VND")) {
            aVar.b.setText(symbol + " " + APCommMethod.fenToYuan(goodsItem.price, 0, this.d));
        } else {
            aVar.b.setText(symbol + " " + APCommMethod.fenToYuan(goodsItem.price, 2, this.d));
        }
        if (APPayMananger.singleton().getCurBaseRequest().mType.equals(APMidasBaseRequest.OFFER_TYPE_UNIONMONTH)) {
            aVar.a.setText(goodsItem.producename);
        } else {
            aVar.a.setTypeface(this.f);
            aVar.a.setText(goodsItem.num);
        }
        aVar.c.setBackgroundDrawable(this.c);
        return view;
    }

    public void refresh(Context context, ArrayList<GoodsItem> arrayList, Drawable drawable, String str, boolean z) {
        this.a = arrayList;
        this.b = LayoutInflater.from(context);
        this.c = drawable;
        this.d = str;
        this.e = z;
        notifyDataSetChanged();
    }
}
