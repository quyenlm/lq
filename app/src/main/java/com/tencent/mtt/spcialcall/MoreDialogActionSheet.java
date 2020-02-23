package com.tencent.mtt.spcialcall;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import com.tencent.mtt.common.utils.MttResources;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import java.util.Iterator;

public class MoreDialogActionSheet extends DialogSp implements View.OnClickListener {
    private Button[] mBtns;
    private Button mCancel;
    private LinearLayout mFrame;

    public MoreDialogActionSheet(Context context, IWebViewClientSp pageController) {
        super(context, pageController);
        initWindow();
        addExtendItems();
        addSystemItems();
        initUI();
        switchTheme();
    }

    private void initWindow() {
        requestWindowFeature(1);
        Window window = getWindow();
        window.setWindowAnimations(MttResources.getStyleId("shareInputAnimationStyle"));
        window.setBackgroundDrawableResource(MttResources.getColorId("thrdcall_transparent"));
        window.addFlags(2);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 80;
        attributes.dimAmount = 0.5f;
        window.setAttributes(attributes);
    }

    public void initUI() {
        this.mFrame = new LinearLayout(getContext());
        this.mFrame.setBackgroundDrawable(this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_base_9")));
        setContentView(this.mFrame);
        this.mFrame.setOrientation(1);
        this.mBtns = new Button[this.mMoreItems.size()];
        for (int i = 0; i < this.mMoreItems.size(); i++) {
            this.mBtns[i] = addMenuBtn((ExtendItem) this.mMoreItems.get(i));
        }
        this.mCancel = addMenuBtn(new ExtendItem(0, this.mRes.getString(MttResources.getStringId("thrdcall_cancel"))));
        setPadding();
    }

    private Button addMenuBtn(ExtendItem item) {
        Button btn = new Button(getContext());
        btn.setTag(item);
        btn.setText(item.getLabel());
        btn.setContentDescription(item.getLabel());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_menu_btn_height")));
        params.gravity = 1;
        btn.setTextSize(1, 16.0f);
        if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_cancel")))) {
            params.topMargin = this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_menu_btn_inner_cancel_margin"));
            btn.setTextColor(this.mRes.getColor(MttResources.getColorId("thrdcall_white")));
            btn.setBackgroundDrawable(getStateDrawable(this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_button_cancel_normal")), this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_button_cancel_click"))));
        } else {
            params.bottomMargin = this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_menu_btn_inner_margin"));
            btn.setTextColor(this.mRes.getColor(MttResources.getColorId("thrdcall_menu_text_color")));
            btn.setBackgroundDrawable(getStateDrawable(this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_button_normal")), this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_button_click"))));
        }
        btn.setLayoutParams(params);
        btn.setOnClickListener(this);
        this.mFrame.addView(btn);
        return btn;
    }

    public boolean switchTheme() {
        if (ExtraInfo.getExtraThemeItem() == null || ExtraInfo.getExtraThemeItem().size() <= 0) {
            return false;
        }
        Iterator<ExtendItem> it = ExtraInfo.getExtraThemeItem().iterator();
        while (it.hasNext()) {
            ExtendItem item = it.next();
            switch (item.getID()) {
                case 20:
                    ThemeSwitcher.doSwitch(this.mFrame, item);
                    break;
                case 21:
                    for (Button btn : this.mBtns) {
                        ThemeSwitcher.doSwitch(btn, item);
                    }
                    break;
                case 22:
                    ThemeSwitcher.doSwitch(this.mCancel, item);
                    break;
            }
        }
        setPadding();
        return true;
    }

    public void setPadding() {
        int top = this.mRes.getDimensionPixelOffset(MttResources.getDimensId("thrdcall_menu_padding_top"));
        int lr = this.mRes.getDimensionPixelOffset(MttResources.getDimensId("thrdcall_menu_padding_lr"));
        this.mFrame.setPadding(lr, top, lr, this.mRes.getDimensionPixelOffset(MttResources.getDimensId("thrdcall_menu_padding_bottom")));
    }

    private StateListDrawable getStateDrawable(Drawable drawable, Drawable drawablePressed) {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{16842910, 16842908}, drawablePressed);
        sd.addState(new int[]{16842919, 16842910}, drawablePressed);
        sd.addState(new int[]{16842908}, drawablePressed);
        sd.addState(new int[]{16842919}, drawablePressed);
        sd.addState(new int[]{16842910}, drawable);
        sd.addState(new int[0], drawable);
        return sd;
    }

    public void show() {
        super.show();
        getWindow().setLayout(-1, -2);
    }

    public void onClick(View v) {
        if (v.getTag() instanceof ExtendItem) {
            onExtendItemOnClick(v);
        }
        dismiss();
    }
}
