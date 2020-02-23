package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.R;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;

public final class SendButton extends ShareButtonBase {
    public SendButton(Context context) {
        super(context, (AttributeSet) null, 0, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, AnalyticsEvents.EVENT_SEND_BUTTON_DID_TAP);
    }

    public SendButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, AnalyticsEvents.EVENT_SEND_BUTTON_DID_TAP);
    }

    public SendButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, AnalyticsEvents.EVENT_SEND_BUTTON_DID_TAP);
    }

    /* access modifiers changed from: protected */
    public int getDefaultStyleResource() {
        return R.style.com_facebook_button_send;
    }

    /* access modifiers changed from: protected */
    public int getDefaultRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Message.toRequestCode();
    }

    /* access modifiers changed from: protected */
    public FacebookDialogBase<ShareContent, Sharer.Result> getDialog() {
        if (getFragment() != null) {
            return new MessageDialog(getFragment(), getRequestCode());
        }
        if (getNativeFragment() != null) {
            return new MessageDialog(getNativeFragment(), getRequestCode());
        }
        return new MessageDialog(getActivity(), getRequestCode());
    }
}
