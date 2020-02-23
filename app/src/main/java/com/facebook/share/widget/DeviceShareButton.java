package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.share.DeviceShareDialog;
import com.facebook.share.R;
import com.facebook.share.model.ShareContent;

public final class DeviceShareButton extends FacebookButtonBase {
    private DeviceShareDialog dialog;
    private boolean enabledExplicitlySet;
    private int requestCode;
    private ShareContent shareContent;

    public DeviceShareButton(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public DeviceShareButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private DeviceShareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0, AnalyticsEvents.EVENT_DEVICE_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_DEVICE_SHARE_BUTTON_DID_TAP);
        this.requestCode = 0;
        this.enabledExplicitlySet = false;
        this.dialog = null;
        this.requestCode = isInEditMode() ? 0 : getDefaultRequestCode();
        internalSetEnabled(false);
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    public void setShareContent(ShareContent shareContent2) {
        this.shareContent = shareContent2;
        if (!this.enabledExplicitlySet) {
            internalSetEnabled(canShare());
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enabledExplicitlySet = true;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<DeviceShareDialog.Result> callback) {
        getDialog().registerCallback(callbackManager, callback);
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<DeviceShareDialog.Result> callback, int requestCode2) {
        setRequestCode(requestCode2);
        getDialog().registerCallback(callbackManager, callback, requestCode2);
    }

    /* access modifiers changed from: protected */
    public void configureButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.configureButton(context, attrs, defStyleAttr, defStyleRes);
        setInternalOnClickListener(getShareOnClickListener());
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
    public View.OnClickListener getShareOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                DeviceShareButton.this.callExternalOnClickListener(v);
                DeviceShareButton.this.getDialog().show(DeviceShareButton.this.getShareContent());
            }
        };
    }

    private void internalSetEnabled(boolean enabled) {
        setEnabled(enabled);
        this.enabledExplicitlySet = false;
    }

    private void setRequestCode(int requestCode2) {
        if (FacebookSdk.isFacebookRequestCode(requestCode2)) {
            throw new IllegalArgumentException("Request code " + requestCode2 + " cannot be within the range reserved by the Facebook SDK.");
        }
        this.requestCode = requestCode2;
    }

    private boolean canShare() {
        return new DeviceShareDialog(getActivity()).canShow(getShareContent());
    }

    /* access modifiers changed from: private */
    public DeviceShareDialog getDialog() {
        if (this.dialog != null) {
            return this.dialog;
        }
        if (getFragment() != null) {
            this.dialog = new DeviceShareDialog(getFragment());
        } else if (getNativeFragment() != null) {
            this.dialog = new DeviceShareDialog(getNativeFragment());
        } else {
            this.dialog = new DeviceShareDialog(getActivity());
        }
        return this.dialog;
    }
}
