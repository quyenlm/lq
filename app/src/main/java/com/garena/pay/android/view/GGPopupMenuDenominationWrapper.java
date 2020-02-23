package com.garena.pay.android.view;

import android.app.Activity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.beetalk.sdk.helper.ImageLoader;
import com.garena.msdk.R;
import com.garena.pay.android.helper.Utils;

public class GGPopupMenuDenominationWrapper extends GGPopupMenuItemWrapper {
    public GGPopupMenuDenominationWrapper(Activity context, GGFullScreenPopupMenu ggFullScreenPopupMenu) {
        super(context, ggFullScreenPopupMenu, R.layout.msdk_picker_denomination_item_view);
    }

    public void setInfo(GGPopMenuItem info) {
        if (info instanceof GGPopMenuDenominationItem) {
            setInfo((GGPopMenuDenominationItem) info);
        } else {
            super.setInfo(info);
        }
    }

    private void setInfo(GGPopMenuDenominationItem info) {
        if (this.m_rootView != null) {
            TextView view = (TextView) this.m_rootView.findViewById(R.id.picker_item_desc);
            TextView button = (TextView) this.m_rootView.findViewById(R.id.currency_amount_button);
            LinearLayout buttonLayout = (LinearLayout) this.m_rootView.findViewById(R.id.currency_amount);
            ImageView currencyIcon = (ImageView) this.m_rootView.findViewById(R.id.current_amount_icon);
            ((ImageView) this.m_rootView.findViewById(R.id.promotionTextIcon)).setVisibility(8);
            loadImageWithPicasso(info, (ImageView) this.m_rootView.findViewById(R.id.picker_item_icon));
            view.setText(info.title);
            if (!TextUtils.isEmpty(info.subTitle)) {
                String subTitle = "\n" + info.subTitle;
                SpannableString subSpan = new SpannableString(subTitle);
                subSpan.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.picker_sub_text_color)), 0, subTitle.length(), 33);
                view.append(subSpan);
            }
            if (!Utils.isNullOrEmpty(info.btnText)) {
                button.setText(info.btnText);
            }
            if (!TextUtils.isEmpty(info.currencyIconUrl)) {
                ImageLoader.load(info.currencyIconUrl).placeholder(R.drawable.msdk_default_icon).into(currencyIcon);
            } else {
                currencyIcon.setVisibility(8);
            }
            this.m_rootView.setTag(info.keyCode);
            buttonLayout.setTag(info.keyCode);
            View.OnClickListener clickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    if (!(v.getTag() == null || GGPopupMenuDenominationWrapper.this.ggFullScreenPopupMenu.getCallback() == null)) {
                        GGPopupMenuDenominationWrapper.this.ggFullScreenPopupMenu.getCallback().onMenuItemClick(v.getTag());
                    }
                    GGPopupMenuDenominationWrapper.this.ggFullScreenPopupMenu.dismiss(false);
                }
            };
            if (!Utils.isNullOrEmpty(button.getText())) {
                buttonLayout.setOnClickListener(clickListener);
            } else {
                this.m_rootView.setOnClickListener(clickListener);
            }
        }
    }
}
