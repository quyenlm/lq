package com.facebook.internal;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import java.util.Iterator;
import java.util.List;

public abstract class FacebookDialogBase<CONTENT, RESULT> implements FacebookDialog<CONTENT, RESULT> {
    protected static final Object BASE_AUTOMATIC_MODE = new Object();
    private static final String TAG = "FacebookDialog";
    private final Activity activity;
    private final FragmentWrapper fragmentWrapper;
    private List<FacebookDialogBase<CONTENT, RESULT>.ModeHandler> modeHandlers;
    private int requestCode;

    /* access modifiers changed from: protected */
    public abstract AppCall createBaseAppCall();

    /* access modifiers changed from: protected */
    public abstract List<FacebookDialogBase<CONTENT, RESULT>.ModeHandler> getOrderedModeHandlers();

    /* access modifiers changed from: protected */
    public abstract void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, FacebookCallback<RESULT> facebookCallback);

    protected FacebookDialogBase(Activity activity2, int requestCode2) {
        Validate.notNull(activity2, "activity");
        this.activity = activity2;
        this.fragmentWrapper = null;
        this.requestCode = requestCode2;
    }

    protected FacebookDialogBase(FragmentWrapper fragmentWrapper2, int requestCode2) {
        Validate.notNull(fragmentWrapper2, "fragmentWrapper");
        this.fragmentWrapper = fragmentWrapper2;
        this.activity = null;
        this.requestCode = requestCode2;
        if (fragmentWrapper2.getActivity() == null) {
            throw new IllegalArgumentException("Cannot use a fragment that is not attached to an activity");
        }
    }

    public final void registerCallback(CallbackManager callbackManager, FacebookCallback<RESULT> callback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        registerCallbackImpl((CallbackManagerImpl) callbackManager, callback);
    }

    public final void registerCallback(CallbackManager callbackManager, FacebookCallback<RESULT> callback, int requestCode2) {
        setRequestCode(requestCode2);
        registerCallback(callbackManager, callback);
    }

    /* access modifiers changed from: protected */
    public void setRequestCode(int requestCode2) {
        if (FacebookSdk.isFacebookRequestCode(requestCode2)) {
            throw new IllegalArgumentException("Request code " + requestCode2 + " cannot be within the range reserved by the Facebook SDK.");
        }
        this.requestCode = requestCode2;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public boolean canShow(CONTENT content) {
        return canShowImpl(content, BASE_AUTOMATIC_MODE);
    }

    /* access modifiers changed from: protected */
    public boolean canShowImpl(CONTENT content, Object mode) {
        boolean anyModeAllowed;
        if (mode == BASE_AUTOMATIC_MODE) {
            anyModeAllowed = true;
        } else {
            anyModeAllowed = false;
        }
        for (FacebookDialogBase<CONTENT, RESULT>.ModeHandler handler : cachedModeHandlers()) {
            if ((anyModeAllowed || Utility.areObjectsEqual(handler.getMode(), mode)) && handler.canShow(content, false)) {
                return true;
            }
        }
        return false;
    }

    public void show(CONTENT content) {
        showImpl(content, BASE_AUTOMATIC_MODE);
    }

    /* access modifiers changed from: protected */
    public void showImpl(CONTENT content, Object mode) {
        AppCall appCall = createAppCallForMode(content, mode);
        if (appCall == null) {
            Log.e(TAG, "No code path should ever result in a null appCall");
            if (FacebookSdk.isDebugEnabled()) {
                throw new IllegalStateException("No code path should ever result in a null appCall");
            }
        } else if (this.fragmentWrapper != null) {
            DialogPresenter.present(appCall, this.fragmentWrapper);
        } else {
            DialogPresenter.present(appCall, this.activity);
        }
    }

    /* access modifiers changed from: protected */
    public Activity getActivityContext() {
        if (this.activity != null) {
            return this.activity;
        }
        if (this.fragmentWrapper != null) {
            return this.fragmentWrapper.getActivity();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void startActivityForResult(Intent intent, int requestCode2) {
        String error = null;
        if (this.activity != null) {
            this.activity.startActivityForResult(intent, requestCode2);
        } else if (this.fragmentWrapper == null) {
            error = "Failed to find Activity or Fragment to startActivityForResult ";
        } else if (this.fragmentWrapper.getNativeFragment() != null) {
            this.fragmentWrapper.getNativeFragment().startActivityForResult(intent, requestCode2);
        } else if (this.fragmentWrapper.getSupportFragment() != null) {
            this.fragmentWrapper.getSupportFragment().startActivityForResult(intent, requestCode2);
        } else {
            error = "Failed to find Activity or Fragment to startActivityForResult ";
        }
        if (error != null) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, 6, getClass().getName(), error);
        }
    }

    private AppCall createAppCallForMode(CONTENT content, Object mode) {
        boolean anyModeAllowed = mode == BASE_AUTOMATIC_MODE;
        AppCall appCall = null;
        Iterator<FacebookDialogBase<CONTENT, RESULT>.ModeHandler> it = cachedModeHandlers().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            FacebookDialogBase<CONTENT, RESULT>.ModeHandler handler = it.next();
            if ((anyModeAllowed || Utility.areObjectsEqual(handler.getMode(), mode)) && handler.canShow(content, true)) {
                try {
                    appCall = handler.createAppCall(content);
                    break;
                } catch (FacebookException e) {
                    appCall = createBaseAppCall();
                    DialogPresenter.setupAppCallForValidationError(appCall, e);
                }
            }
        }
        if (appCall != null) {
            return appCall;
        }
        AppCall appCall2 = createBaseAppCall();
        DialogPresenter.setupAppCallForCannotShowError(appCall2);
        return appCall2;
    }

    private List<FacebookDialogBase<CONTENT, RESULT>.ModeHandler> cachedModeHandlers() {
        if (this.modeHandlers == null) {
            this.modeHandlers = getOrderedModeHandlers();
        }
        return this.modeHandlers;
    }

    protected abstract class ModeHandler {
        public abstract boolean canShow(CONTENT content, boolean z);

        public abstract AppCall createAppCall(CONTENT content);

        protected ModeHandler() {
        }

        public Object getMode() {
            return FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
    }
}
