package com.garena.pay.android.view;

import android.app.Activity;
import android.view.View;
import com.garena.msdk.R;
import com.garena.pay.android.GGPayRequestHandler;
import com.garena.pay.android.helper.Utils;
import com.garena.pay.android.view.GGFullScreenPopupMenu;
import java.util.HashMap;

public class GatewayView {
    public static GatewayViewCallback callback;
    private static GGFullScreenPopupMenu popupMenu;

    public interface GatewayViewCallback {
        void onDismissed();

        void onGatewayChosen(GGPayRequestHandler gGPayRequestHandler);
    }

    public static void setCallback(GatewayViewCallback callback2) {
        callback = callback2;
    }

    public static void initialize(Activity context, final HashMap<String, GGPayRequestHandler> handlerList) {
        if (popupMenu != null) {
            popupMenu.dismiss(false);
        }
        popupMenu = new GGFullScreenPopupMenu(context);
        popupMenu.setHeaderText(Utils.getString(context, R.string.txt_choose_provider));
        for (String handlerKey : handlerList.keySet()) {
            GGPayRequestHandler thisHandler = handlerList.get(handlerKey);
            String iconUri = thisHandler.getIconUri();
            int flag = thisHandler.getPaymentChannel().getFlag();
            String subTitle = "";
            if ((flag & 1) == 1) {
                subTitle = Utils.getString(context, R.string.payment_item_new_text);
            } else if ((flag & 2) == 2) {
                subTitle = Utils.getString(context, R.string.payment_item_hot_text);
            } else if ((flag & 4) == 4) {
                subTitle = Utils.getString(context, R.string.payment_item_sale_text);
            }
            if (!Utils.isNullOrEmpty(iconUri)) {
                popupMenu.addMenuItem(thisHandler.getDisplayName(), thisHandler.getIconUri(), Utils.getString(context, R.string.text_pay), subTitle, (Object) handlerKey);
            } else {
                popupMenu.addMenuItem(thisHandler.getDisplayName(), thisHandler.getImageResId(), Utils.getString(context, R.string.text_pay), subTitle, (Object) handlerKey);
            }
        }
        popupMenu.setCallback(new GGFullScreenPopupMenu.GGPopupFullscreenMenuCallback() {
            public void onMenuItemClick(Object keycode) {
                String key = (String) keycode;
                if (key != null && GatewayView.callback != null) {
                    GatewayView.callback.onGatewayChosen((GGPayRequestHandler) handlerList.get(key));
                }
            }

            public void onDismissed() {
                GatewayView.callback.onDismissed();
            }
        });
    }

    public static void destroy() {
    }

    public static void dismiss(boolean notify) {
        if (popupMenu != null) {
            popupMenu.dismiss(notify);
        }
    }

    public static boolean isShowing() {
        return popupMenu.isShowing();
    }

    public static void showAtCenter(View anchor) {
        popupMenu.setFooterVisibility(8);
        popupMenu.beforeShow();
        popupMenu.showAtCenter(anchor);
    }
}
