package com.tencent.midas.oversea.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class APHeightWrapContentListView extends ListView {
    private int a = 4;

    public APHeightWrapContentListView(Context context) {
        super(context);
    }

    public APHeightWrapContentListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public APHeightWrapContentListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void a() {
        int count;
        ListAdapter adapter = getAdapter();
        if (adapter != null && (count = adapter.getCount()) > 0) {
            if (count >= this.a) {
                count = this.a;
            }
            int i = 0;
            for (int i2 = 0; i2 < count; i2++) {
                View view = adapter.getView(i2, (View) null, this);
                view.measure(0, 0);
                i += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = ((count - 1) * getDividerHeight()) + i;
            setLayoutParams(layoutParams);
        }
    }

    public void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        a();
    }

    public void setHeightMaxItem(int i) {
        if (i > 0) {
            this.a = 4;
        }
    }
}
