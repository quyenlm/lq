package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.event.EventMainTabClick;
import com.tencent.ieg.ntv.event.EventManager;

public class MainTabItemView extends FrameLayout {
    private Button mBtn;
    private ImageView mDot;
    /* access modifiers changed from: private */
    public int mIndex;
    private View.OnClickListener onBtnClick = new View.OnClickListener() {
        public void onClick(View view) {
            EventManager.getInstance().post(1000, new EventMainTabClick(MainTabItemView.this.mIndex));
        }
    };

    public MainTabItemView(@NonNull Context context, int index) {
        super(context);
        this.mIndex = index;
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(getContext()).inflate(R.layout.main_tab_item, this);
        this.mBtn = (Button) findViewById(R.id.ntvs_main_tab_btn);
        this.mDot = (ImageView) findViewById(R.id.ntvs_main_tab_dot);
        this.mDot.setVisibility(8);
        this.mBtn.setOnClickListener(this.onBtnClick);
    }

    public void setDotVisibility(int visibility) {
        if (this.mDot != null) {
            this.mDot.setVisibility(visibility);
        }
    }

    public ImageView getDot() {
        return this.mDot;
    }

    public void setButtonLable(String label) {
        if (this.mBtn != null) {
            this.mBtn.setText(label);
        }
    }

    public String getButtonLabel() {
        if (this.mBtn != null) {
            return String.valueOf(this.mBtn.getText());
        }
        return "";
    }

    public void setSeclected(boolean selected) {
        if (this.mBtn == null) {
            return;
        }
        if (selected) {
            this.mBtn.setBackgroundResource(R.drawable.common_tab_on_light);
            this.mBtn.setTextColor(getResources().getColor(R.color.ntvs_main_tab_txt_color_selected));
            return;
        }
        this.mBtn.setBackgroundColor(0);
        this.mBtn.setTextColor(getResources().getColor(R.color.ntvs_main_tab_txt_color));
    }
}
