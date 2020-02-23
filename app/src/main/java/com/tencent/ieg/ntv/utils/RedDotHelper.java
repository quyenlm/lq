package com.tencent.ieg.ntv.utils;

import android.content.Context;
import android.widget.ImageView;
import com.tencent.ieg.ntv.model.RedDotInfo;

public class RedDotHelper {
    private static final String TAG = RedDotHelper.class.getSimpleName();
    private static RedDotHelper ourInstance = null;
    private Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void updateRedDot(ImageView dot, RedDotInfo redDotInfo) {
        if (dot != null) {
            boolean visible = false;
            if (this.mContext != null && redDotInfo != null && redDotInfo.shouldShowUnread() && redDotInfo.checkUnreadStatus(this.mContext)) {
                visible = true;
            }
            dot.setVisibility(visible ? 0 : 8);
        }
    }

    public void clearRedDot(ImageView dot, RedDotInfo redDotInfo) {
        if (dot != null && dot.getVisibility() == 0) {
            if (!(this.mContext == null || redDotInfo == null)) {
                redDotInfo.clearUnreadStatus(this.mContext);
            }
            dot.setVisibility(8);
        }
    }

    public static RedDotHelper getInstance() {
        if (ourInstance == null) {
            ourInstance = new RedDotHelper();
        }
        return ourInstance;
    }

    private RedDotHelper() {
    }

    public void destroy() {
        ourInstance = null;
    }
}
