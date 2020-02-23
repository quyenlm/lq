package com.garena.pay.android.view;

import android.app.Activity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.beetalk.sdk.helper.ImageLoader;
import com.garena.msdk.R;
import com.garena.pay.android.helper.GGDipPixelConvertor;
import com.garena.pay.android.helper.Utils;

public class GGPopupMenuItemWrapper {
    protected GGFullScreenPopupMenu ggFullScreenPopupMenu;
    protected Activity mContext;
    protected View m_rootView;

    public GGPopupMenuItemWrapper(Activity context, GGFullScreenPopupMenu ggFullScreenPopupMenu2) {
        this.ggFullScreenPopupMenu = ggFullScreenPopupMenu2;
        __init(context, R.layout.msdk_picker_item_view);
    }

    public GGPopupMenuItemWrapper(Activity context, GGFullScreenPopupMenu ggFullScreenPopupMenu2, int layoutResource) {
        this.ggFullScreenPopupMenu = ggFullScreenPopupMenu2;
        __init(context, layoutResource);
    }

    /* access modifiers changed from: protected */
    public void __init(Activity context, int layoutResource) {
        this.mContext = context;
        GGDipPixelConvertor.initialize(this.mContext.getResources().getDisplayMetrics());
        this.m_rootView = this.mContext.getLayoutInflater().inflate(layoutResource, (ViewGroup) null);
    }

    public void setInfo(GGPopMenuItem info) {
        if (this.m_rootView != null) {
            TextView view = (TextView) this.m_rootView.findViewById(R.id.picker_item_desc);
            Button button = (Button) this.m_rootView.findViewById(R.id.currency_amount);
            loadImageWithPicasso(info, (ImageView) this.m_rootView.findViewById(R.id.picker_item_icon));
            view.setText(info.title);
            if (!TextUtils.isEmpty(info.subTitle)) {
                String subTitle = "  " + info.subTitle;
                SpannableString subSpan = new SpannableString(subTitle);
                subSpan.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.picker_sub_text_color)), 0, subTitle.length(), 33);
                view.append(subSpan);
            }
            if (!Utils.isNullOrEmpty(info.btnText)) {
                button.setText(info.btnText);
            }
            this.m_rootView.setTag(info.keyCode);
            button.setTag(info.keyCode);
            View.OnClickListener clickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    if (!(v.getTag() == null || GGPopupMenuItemWrapper.this.ggFullScreenPopupMenu.getCallback() == null)) {
                        GGPopupMenuItemWrapper.this.ggFullScreenPopupMenu.getCallback().onMenuItemClick(v.getTag());
                    }
                    GGPopupMenuItemWrapper.this.ggFullScreenPopupMenu.dismiss(false);
                }
            };
            if (!Utils.isNullOrEmpty(button.getText())) {
                button.setOnClickListener(clickListener);
            } else {
                this.m_rootView.setOnClickListener(clickListener);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void loadImageWithPicasso(GGPopMenuItem info, ImageView icon) {
        if (Utils.isNullOrEmpty(info.imageUrl)) {
            icon.setImageResource(info.imageId);
        } else {
            ImageLoader.load(info.imageUrl).placeholder(R.drawable.msdk_default_icon).into(icon);
        }
    }

    public View getRootView() {
        return this.m_rootView;
    }
}
