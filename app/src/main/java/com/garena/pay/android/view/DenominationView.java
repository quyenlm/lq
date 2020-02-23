package com.garena.pay.android.view;

import android.app.Activity;
import android.view.View;
import com.garena.msdk.R;
import com.garena.pay.android.data.GGPayment;
import com.garena.pay.android.data.VirtualCurrency;
import com.garena.pay.android.helper.Utils;
import com.garena.pay.android.view.GGFullScreenPopupMenu;
import java.util.List;

public class DenominationView {
    public static DenominationCallback callback;
    static GGFullScreenPopupMenu popupMenu = null;

    public interface DenominationCallback {
        void onDenominationChosen(GGPayment.Denomination denomination);

        void onDismissed();
    }

    public static void initialize(Activity context, final List<GGPayment.Denomination> denominations, VirtualCurrency currency) {
        popupMenu = new GGFullScreenPopupMenu(context);
        popupMenu.setHeaderText(String.format(Utils.getString(context, R.string.s_picker_header_text), new Object[]{currency.getVirtualCurrencyName()}));
        int i = 0;
        for (GGPayment.Denomination denomination : denominations) {
            GGPopMenuDenominationItem item = new GGPopMenuDenominationItem();
            item.imageUrl = denomination.getIconUrl();
            item.title = denomination.getName();
            item.keyCode = Integer.valueOf(i);
            item.btnText = denomination.getPrice();
            item.isPromotion = denomination.isInPromotion();
            item.currencyIconUrl = denomination.getCurrencyIconUrl();
            if (denomination.getPromoPoints().intValue() > 0) {
                item.subTitle = context.getString(R.string.txt_bonus, new Object[]{denomination.getPromoPoints()});
            }
            popupMenu.addMenuItem(item);
            i++;
        }
        popupMenu.setCallback(new GGFullScreenPopupMenu.GGPopupFullscreenMenuCallback() {
            public void onMenuItemClick(Object keycode) {
                Integer key = (Integer) keycode;
                if (key.intValue() <= denominations.size() && DenominationView.callback != null) {
                    DenominationView.callback.onDenominationChosen((GGPayment.Denomination) denominations.get(key.intValue()));
                }
            }

            public void onDismissed() {
                DenominationView.callback.onDismissed();
            }
        });
    }

    public static void showAtCenter(View anchor) {
        popupMenu.setFooterVisibility(8);
        popupMenu.beforeShow();
        popupMenu.showAtCenter(anchor);
    }

    private static String calculateCurrencyRate(GGPayment.Denomination denomination, VirtualCurrency currency) {
        Double price = Double.valueOf(currency.getCurrencyRate().doubleValue() * ((double) denomination.getAppPoints().intValue()));
        return String.format(currency.getCurrencySymbolFormat(), new Object[]{price});
    }

    public static void destroy() {
    }

    public static void dismiss(boolean notify) {
        if (popupMenu != null) {
            popupMenu.dismiss(notify);
        }
    }

    public static void setCallback(DenominationCallback callback2) {
        callback = callback2;
    }
}
