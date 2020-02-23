package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.tencent.ieg.ntv.R;

public class ChatItemView extends FrameLayout {
    public ChatItemView(Context context) {
        super(context);
        initUI();
    }

    public ChatItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public ChatItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(getContext()).inflate(R.layout.ntvs_chat_view_history_item, this);
    }
}
