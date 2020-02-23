package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;

public abstract class ShareButtonBase extends FacebookButtonBase {
    private boolean enabledExplicitlySet = false;
    private int requestCode = 0;
    private ShareContent shareContent;

    /* access modifiers changed from: protected */
    public abstract FacebookDialogBase<ShareContent, Sharer.Result> getDialog();

    protected ShareButtonBase(Context context, AttributeSet attrs, int defStyleAttr, String analyticsButtonCreatedEventName, String analyticsButtonTappedEventName) {
        super(context, attrs, defStyleAttr, 0, analyticsButtonCreatedEventName, analyticsButtonTappedEventName);
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

    /* access modifiers changed from: protected */
    public void setRequestCode(int requestCode2) {
        if (FacebookSdk.isFacebookRequestCode(requestCode2)) {
            throw new IllegalArgumentException("Request code " + requestCode2 + " cannot be within the range reserved by the Facebook SDK.");
        }
        this.requestCode = requestCode2;
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<Sharer.Result> callback) {
        ShareInternalUtility.registerSharerCallback(getRequestCode(), callbackManager, callback);
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<Sharer.Result> callback, int requestCode2) {
        setRequestCode(requestCode2);
        registerCallback(callbackManager, callback);
    }

    /* access modifiers changed from: protected */
    public void configureButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.configureButton(context, attrs, defStyleAttr, defStyleRes);
        setInternalOnClickListener(getShareOnClickListener());
    }

    /* access modifiers changed from: protected */
    public boolean canShare() {
        return getDialog().canShow(getShareContent());
    }

    /* access modifiers changed from: protected */
    public View.OnClickListener getShareOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                ShareButtonBase.this.callExternalOnClickListener(v);
                ShareButtonBase.this.getDialog().show(ShareButtonBase.this.getShareContent());
            }
        };
    }

    private void internalSetEnabled(boolean enabled) {
        setEnabled(enabled);
        this.enabledExplicitlySet = false;
    }
}
