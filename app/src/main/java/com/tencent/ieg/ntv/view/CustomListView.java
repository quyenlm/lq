package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.ArrayList;
import java.util.List;

public class CustomListView extends ListView {
    private static final String TAG = CustomListView.class.getSimpleName();
    private ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getContext(), 0) {
        public int getCount() {
            int count = CustomListView.this.mItems == null ? 0 : CustomListView.this.mItems.size();
            CustomListView.log("mAdapter getCount count:" + count);
            return count;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            CustomListView.log("mAdapter getView position:" + position);
            return (View) CustomListView.this.mItems.get(position);
        }
    };
    /* access modifiers changed from: private */
    public List<CustomListViewItem> mItems;
    private UIListener mUIListener;

    public interface UIListener {
        void onItemClick(int i);
    }

    /* access modifiers changed from: private */
    public static void log(String msg) {
        Logger.d(TAG, msg);
    }

    public CustomListView(Context context) {
        super(context);
        initUI();
    }

    public CustomListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public CustomListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    public void setUIListener(UIListener listener) {
        this.mUIListener = listener;
    }

    public void setItemTextList(List<String> itemTextList) {
        boolean z;
        log("setItemTextList");
        this.mItems = new ArrayList();
        for (int i = 0; i < itemTextList.size(); i++) {
            CustomListViewItem item = new CustomListViewItem(getContext());
            item.setIndex(i);
            if (i == 0) {
                z = true;
            } else {
                z = false;
            }
            item.setDecorBarVisible(0, z);
            item.setText(itemTextList.get(i));
            this.mItems.add(item);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void setItemTextColor(int index, int color) {
        if (this.mItems != null && index < this.mItems.size()) {
            this.mItems.get(index).setTextColor(color);
        }
    }

    public String getItemText(int index) {
        return this.mItems.get(index).getText();
    }

    public int getCount() {
        if (this.mItems == null) {
            return 0;
        }
        return this.mItems.size();
    }

    private void initUI() {
        log("initUI");
        setDividerHeight(0);
        setAdapter(this.mAdapter);
    }

    private static class CustomListViewItem extends FrameLayout {
        private View mDecorBar1;
        private int mIndex;
        private TextView mTextView;

        public CustomListViewItem(Context context) {
            super(context);
            initUI();
        }

        public CustomListViewItem(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            initUI();
        }

        public CustomListViewItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initUI();
        }

        private void initUI() {
            LayoutInflater.from(getContext()).inflate(R.layout.ntv_custom_list_view_item, this);
            this.mDecorBar1 = findViewById(R.id.decor_bar1);
            this.mTextView = (TextView) findViewById(R.id.text);
        }

        public void setDecorBarVisible(int index, boolean visible) {
            this.mDecorBar1.setVisibility(visible ? 0 : 4);
        }

        public void setText(String text) {
            this.mTextView.setText(text);
        }

        public String getText() {
            return this.mTextView.getText().toString();
        }

        public void setTextColor(int color) {
            this.mTextView.setTextColor(color);
        }

        public int getIndex() {
            return this.mIndex;
        }

        public void setIndex(int index) {
            this.mIndex = index;
        }
    }
}
