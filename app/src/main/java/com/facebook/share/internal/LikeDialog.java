package com.facebook.share.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class LikeDialog extends FacebookDialogBase<LikeContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode();
    private static final String TAG = "LikeDialog";

    @Deprecated
    public static final class Result {
        private final Bundle bundle;

        public Result(Bundle bundle2) {
            this.bundle = bundle2;
        }

        public Bundle getData() {
            return this.bundle;
        }
    }

    @Deprecated
    public static boolean canShowNativeDialog() {
        return false;
    }

    @Deprecated
    public static boolean canShowWebFallback() {
        return false;
    }

    @Deprecated
    public LikeDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public LikeDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    @Deprecated
    public LikeDialog(android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    @Deprecated
    public LikeDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public void show(LikeContent content) {
    }

    /* access modifiers changed from: protected */
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    /* access modifiers changed from: protected */
    public List<FacebookDialogBase<LikeContent, Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<LikeContent, Result>.ModeHandler> handlers = new ArrayList<>();
        handlers.add(new NativeHandler());
        handlers.add(new WebFallbackHandler());
        return handlers;
    }

    /* access modifiers changed from: protected */
    public void registerCallbackImpl(CallbackManagerImpl callbackManager, final FacebookCallback<Result> callback) {
        final ResultProcessor resultProcessor = callback == null ? null : new ResultProcessor(callback) {
            public void onSuccess(AppCall appCall, Bundle results) {
                callback.onSuccess(new Result(results));
            }
        };
        callbackManager.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int resultCode, Intent data) {
                return ShareInternalUtility.handleActivityResult(LikeDialog.this.getRequestCode(), resultCode, data, resultProcessor);
            }
        });
    }

    private class NativeHandler extends FacebookDialogBase<LikeContent, Result>.ModeHandler {
        private NativeHandler() {
            super();
        }

        public boolean canShow(LikeContent content, boolean isBestEffort) {
            return false;
        }

        public AppCall createAppCall(final LikeContent content) {
            AppCall appCall = LikeDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider() {
                public Bundle getParameters() {
                    return LikeDialog.createParameters(content);
                }

                public Bundle getLegacyParameters() {
                    Log.e(LikeDialog.TAG, "Attempting to present the Like Dialog with an outdated Facebook app on the device");
                    return new Bundle();
                }
            }, LikeDialog.getFeature());
            return appCall;
        }
    }

    private class WebFallbackHandler extends FacebookDialogBase<LikeContent, Result>.ModeHandler {
        private WebFallbackHandler() {
            super();
        }

        public boolean canShow(LikeContent content, boolean isBestEffort) {
            return false;
        }

        public AppCall createAppCall(LikeContent content) {
            AppCall appCall = LikeDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebFallbackDialog(appCall, LikeDialog.createParameters(content), LikeDialog.getFeature());
            return appCall;
        }
    }

    /* access modifiers changed from: private */
    public static DialogFeature getFeature() {
        return LikeDialogFeature.LIKE_DIALOG;
    }

    /* access modifiers changed from: private */
    public static Bundle createParameters(LikeContent likeContent) {
        Bundle params = new Bundle();
        params.putString("object_id", likeContent.getObjectId());
        params.putString("object_type", likeContent.getObjectType());
        return params;
    }
}
