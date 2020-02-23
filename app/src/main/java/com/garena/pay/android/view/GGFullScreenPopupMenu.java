package com.garena.pay.android.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.garena.msdk.R;
import com.garena.pay.android.helper.GGDipPixelConvertor;
import com.garena.pay.android.helper.Utils;
import com.garena.pay.android.view.GGBasePopupView;
import java.util.ArrayList;
import java.util.List;

public class GGFullScreenPopupMenu {
    static final /* synthetic */ boolean $assertionsDisabled = (!GGFullScreenPopupMenu.class.desiredAssertionStatus());
    /* access modifiers changed from: private */
    public GGPopupFullscreenMenuCallback callback;
    private Activity context;
    private int footerVisibility = 0;
    private String headerText = "";
    private List<GGPopMenuItem> popMenuItems = new ArrayList();
    private ViewGroup popupDialog;
    private GGBasePopupView popupView;

    public interface GGPopupFullscreenMenuCallback {
        void onDismissed();

        void onMenuItemClick(Object obj);
    }

    public GGFullScreenPopupMenu(Activity context2) {
        this.context = context2;
    }

    public void addMenuItem(String title, int resId, String pay, Object key) {
        GGPopMenuItem item = new GGPopMenuItem();
        item.imageId = resId;
        item.title = title;
        item.keyCode = key;
        item.btnText = pay;
        this.popMenuItems.add(item);
    }

    public void addMenuItem(String title, int resId, String pay, String subTitle, Object key) {
        GGPopMenuItem item = new GGPopMenuItem();
        item.imageId = resId;
        item.title = title;
        item.keyCode = key;
        item.btnText = pay;
        this.popMenuItems.add(item);
    }

    public void addMenuItem(String title, String imageUrl, String price, Object key) {
        GGPopMenuItem item = new GGPopMenuItem();
        item.imageUrl = imageUrl;
        item.title = title;
        item.keyCode = key;
        item.btnText = price;
        this.popMenuItems.add(item);
    }

    public void addMenuItem(String title, String imageUrl, String price, String subTitle, Object key) {
        GGPopMenuItem item = new GGPopMenuItem();
        item.imageUrl = imageUrl;
        item.title = title;
        item.keyCode = key;
        item.btnText = price;
        item.subTitle = subTitle;
        this.popMenuItems.add(item);
    }

    public void addMenuItem(GGPopMenuItem item) {
        this.popMenuItems.add(item);
    }

    public void setCallback(GGPopupFullscreenMenuCallback callback2) {
        this.callback = callback2;
    }

    public void beforeShow() {
        GGDipPixelConvertor.initialize((Context) this.context);
        __buildMenuItem();
    }

    public void setFooterVisibility(int footerVisibility2) {
        this.footerVisibility = footerVisibility2;
    }

    private void __buildMenuItem() {
        GGPopupMenuItemWrapper view;
        this.popupDialog = (ViewGroup) this.context.getLayoutInflater().inflate(R.layout.msdk_picker_view, new LinearLayout(this.context));
        if ($assertionsDisabled || this.popupDialog != null) {
            LinearLayout pickerList = (LinearLayout) this.popupDialog.findViewById(R.id.picker_item_list);
            TextView textView = (TextView) this.popupDialog.findViewById(R.id.picker_header_text);
            textView.setVisibility(Utils.isNullOrEmpty(this.headerText) ? 8 : 0);
            textView.setText(this.headerText);
            this.popupDialog.findViewById(R.id.btn_footer).setVisibility(this.footerVisibility);
            for (GGPopMenuItem item : this.popMenuItems) {
                if (item instanceof GGPopMenuDenominationItem) {
                    view = new GGPopupMenuDenominationWrapper(this.context, this);
                } else {
                    view = new GGPopupMenuItemWrapper(this.context, this);
                }
                view.setInfo(item);
                pickerList.addView(view.getRootView());
            }
            return;
        }
        throw new AssertionError();
    }

    public void showAtCenter(View anchor) {
        this.popupView = new GGBasePopupView(this.popupDialog, false, false);
        this.popupView.setListener(new GGBasePopupView.DismissListener() {
            public void onDismissed() {
                GGFullScreenPopupMenu.this.callback.onDismissed();
            }
        });
        this.popupView.showAtCenter(anchor);
    }

    public void dismiss(boolean notify) {
        if (this.popupView != null && notify) {
            this.popupView.dismiss();
        } else if (this.popupView != null && !notify) {
            this.popupView.successfulDismiss();
        }
    }

    public boolean isShowing() {
        return this.popupView != null && this.popupView.isShowing();
    }

    public void setHeaderText(String headerText2) {
        this.headerText = headerText2;
    }

    public GGPopupFullscreenMenuCallback getCallback() {
        return this.callback;
    }
}
