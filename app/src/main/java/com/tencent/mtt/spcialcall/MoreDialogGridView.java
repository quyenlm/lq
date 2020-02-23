package com.tencent.mtt.spcialcall;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tencent.mtt.common.utils.MttResources;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import java.util.ArrayList;
import java.util.Iterator;

public class MoreDialogGridView extends DialogSp implements View.OnClickListener {
    private TextView mCancel;
    LinearLayout mFrame = new LinearLayout(getContext());
    private GridView mGridView;

    public MoreDialogGridView(Context context, IWebViewClientSp pageController) {
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
        this.mFrame.setOrientation(1);
        this.mFrame.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.mFrame.setBackgroundDrawable(this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_base_9")));
        this.mGridView = new GridView(getContext());
        this.mGridView.setNumColumns(3);
        this.mGridView.setAdapter(new MoreListAdapter(this.mMoreItems));
        this.mGridView.setStretchMode(2);
        this.mCancel = createMenuBtn(new ExtendItem(0, this.mRes.getString(MttResources.getStringId("thrdcall_cancel"))));
        this.mCancel.setOnClickListener(this);
        this.mFrame.addView(this.mGridView, new LinearLayout.LayoutParams(-1, -1));
        this.mFrame.addView(this.mCancel);
        setContentView(this.mFrame);
        setPadding();
        this.mGridView.requestFocus();
    }

    private Button createMenuBtn(ExtendItem item) {
        Button btn = new Button(getContext());
        btn.setTag(item);
        btn.setText(item.getLabel());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_menu_btn_height")));
        params.gravity = 1;
        btn.setTextSize(1, 18.0f);
        params.topMargin = this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_menu_btn_inner_cancel_margin"));
        btn.setTextColor(this.mRes.getColor(MttResources.getColorId("thrdcall_white")));
        btn.setBackgroundDrawable(getStateDrawable(this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_button_cancel_normal")), this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_button_cancel_click"))));
        btn.setLayoutParams(params);
        btn.setOnClickListener(this);
        return btn;
    }

    class MoreListAdapter extends BaseAdapter {
        private ArrayList<ExtendItem> items;

        public MoreListAdapter(ArrayList<ExtendItem> shareItems) {
            this.items = shareItems;
        }

        public int getCount() {
            return this.items.size();
        }

        public Object getItem(int position) {
            return this.items.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ExtendItem curItem = this.items.get(position);
            if (convertView == null || convertView.getTag() == null) {
                int textColor = curItem.getTextColor() == 0 ? -4276546 : curItem.getTextColor();
                int textSize = curItem.getTextSize() == 0 ? 10 : curItem.getTextSize();
                CharSequence label = curItem.getLabel();
                Drawable icon = ThemeSwitcher.initTransDrawable(MoreDialogGridView.this.getContext(), curItem);
                if (icon != null) {
                    curItem.setImage(icon);
                }
                convertView = MoreDialogGridView.this.createListItem(label, icon, textColor, textSize);
                convertView.setLayoutParams(new AbsListView.LayoutParams(-1, MoreDialogGridView.this.mRes.getDimensionPixelOffset(MttResources.getDimensId("thrdcall_grid_item_height"))));
                convertView.setTag(curItem);
            } else {
                if (curItem.getImage() != null) {
                    Drawable icon2 = new BitmapDrawable(curItem.getImage());
                    int iconSize = MoreDialogGridView.this.mRes.getDimensionPixelOffset(MttResources.getDimensId("thrdcall_grid_item_icon_height"));
                    icon2.setBounds(0, 0, iconSize, iconSize);
                    ((TextView) convertView).setCompoundDrawables((Drawable) null, icon2, (Drawable) null, (Drawable) null);
                }
                ((TextView) convertView).setText(curItem.getLabel());
            }
            convertView.setId(curItem.getID());
            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MoreDialogGridView.this.onExtendItemOnClick(v);
                    MoreDialogGridView.this.dismiss();
                }
            });
            return convertView;
        }

        public class ViewHodler {
            public Drawable icon;
            public int id;
            public CharSequence label;

            public ViewHodler() {
            }
        }
    }

    public View createListItem(CharSequence label, Drawable icon, int textColor, int textSize) {
        TextView tv = new TextView(getContext());
        tv.setGravity(1);
        if (icon == null) {
            icon = this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_action_sheet_button_normal"));
        }
        int iconSize = this.mRes.getDimensionPixelOffset(MttResources.getDimensId("thrdcall_grid_item_icon_height"));
        icon.setBounds(0, 0, iconSize, iconSize);
        tv.setCompoundDrawables((Drawable) null, icon, (Drawable) null, (Drawable) null);
        tv.setCompoundDrawablePadding(this.mRes.getDimensionPixelOffset(MttResources.getDimensId("thrdcall_menu_item_icon_top_padding")));
        tv.setClickable(true);
        tv.setFocusable(true);
        tv.setTextColor(textColor);
        tv.setLayoutParams(new AbsListView.LayoutParams(-1, -1));
        tv.setText(label);
        tv.setContentDescription(label);
        tv.setTextSize(2, (float) textSize);
        return tv;
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
        dismiss();
        if (v != this.mCancel && (v.getTag() instanceof ExtendItem)) {
            onExtendItemOnClick(v);
        }
    }
}
