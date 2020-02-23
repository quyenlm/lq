package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.R;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;

public final class ShareButton extends ShareButtonBase {
    public ShareButton(Context context) {
        super(context, (AttributeSet) null, 0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    public ShareButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    public ShareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    /* access modifiers changed from: protected */
    public int getDefaultStyleResource() {
        return R.style.com_facebook_button_share;
    }

    /* access modifiers changed from: protected */
    public int getDefaultRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();
    }

    /* access modifiers changed from: protected */
    public FacebookDialogBase<ShareContent, Sharer.Result> getDialog() {
        if (getFragment() != null) {
            return new ShareDialog(getFragment(), getRequestCode());
        }
        if (getNativeFragment() != null) {
            return new ShareDialog(getNativeFragment(), getRequestCode());
        }
        return new ShareDialog(getActivity(), getRequestCode());
    }
}
