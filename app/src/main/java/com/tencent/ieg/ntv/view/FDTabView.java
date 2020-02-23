package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.AttributeSet;
import com.tencent.ieg.ntv.utils.NoScrollViewPager;
import java.util.List;

public class FDTabView extends NoScrollViewPager {
    FDTabViewAdapter adapter;

    private static class FDTabViewAdapter extends FragmentPagerAdapter {
        public List<BaseContentFragment> _fragments = null;

        public FDTabViewAdapter(FragmentManager fm, List<BaseContentFragment> fragments) {
            super(fm);
            this._fragments = fragments;
        }

        public Fragment getItem(int position) {
            return this._fragments.get(position);
        }

        public int getCount() {
            return this._fragments.size();
        }
    }

    public FDTabView(Context context) {
        super(context);
    }

    public FDTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurrentItem(int item) {
        for (int i = 0; i < this.adapter.getCount(); i++) {
            if (i == item) {
                if (!this.adapter._fragments.get(i).isShown()) {
                    this.adapter._fragments.get(i).onShow();
                }
            } else if (this.adapter._fragments.get(i).isShown()) {
                this.adapter._fragments.get(i).onHidden();
            }
        }
        super.setCurrentItem(item);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        for (int i = 0; i < this.adapter.getCount(); i++) {
            if (i == item) {
                if (!this.adapter._fragments.get(i).isShown()) {
                    this.adapter._fragments.get(i).onShow();
                }
            } else if (this.adapter._fragments.get(i).isShown()) {
                this.adapter._fragments.get(i).onHidden();
            }
        }
        super.setCurrentItem(item, smoothScroll);
    }

    public boolean init(FragmentManager fm, List<BaseContentFragment> fragments) {
        this.adapter = new FDTabViewAdapter(fm, fragments);
        setAdapter(this.adapter);
        return true;
    }
}
