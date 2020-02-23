package com.tencent.ieg.ntv.view;

import android.support.v4.app.Fragment;
import com.tencent.ieg.ntv.utils.Logger;

public class BaseContentFragment extends Fragment {
    private static final String TAG = BaseContentFragment.class.getSimpleName();
    private boolean _isShown = false;

    public void onShow() {
        this._isShown = true;
        Logger.d(TAG, "onShow");
    }

    public void onHidden() {
        this._isShown = false;
        Logger.d(TAG, "onHidden");
    }

    public boolean isShown() {
        return this._isShown;
    }
}
