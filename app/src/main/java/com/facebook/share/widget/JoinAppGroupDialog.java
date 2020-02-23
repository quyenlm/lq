package com.facebook.share.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class JoinAppGroupDialog extends FacebookDialogBase<String, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppGroupJoin.toRequestCode();
    private static final String JOIN_GAME_GROUP_DIALOG = "game_group_join";

    @Deprecated
    public static final class Result {
        private final Bundle data;

        private Result(Bundle bundle) {
            this.data = bundle;
        }

        public Bundle getData() {
            return this.data;
        }
    }

    @Deprecated
    public static boolean canShow() {
        return true;
    }

    @Deprecated
    public static void show(Activity activity, String groupId) {
        new JoinAppGroupDialog(activity).show(groupId);
    }

    @Deprecated
    public static void show(Fragment fragment, String groupId) {
        show(new FragmentWrapper(fragment), groupId);
    }

    @Deprecated
    public static void show(android.app.Fragment fragment, String groupId) {
        show(new FragmentWrapper(fragment), groupId);
    }

    private static void show(FragmentWrapper fragmentWrapper, String groupId) {
        new JoinAppGroupDialog(fragmentWrapper).show(groupId);
    }

    @Deprecated
    public JoinAppGroupDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public JoinAppGroupDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    @Deprecated
    public JoinAppGroupDialog(android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    private JoinAppGroupDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
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
                return ShareInternalUtility.handleActivityResult(JoinAppGroupDialog.this.getRequestCode(), resultCode, data, resultProcessor);
            }
        });
    }

    /* access modifiers changed from: protected */
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    /* access modifiers changed from: protected */
    public List<FacebookDialogBase<String, Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<String, Result>.ModeHandler> handlers = new ArrayList<>();
        handlers.add(new WebHandler());
        return handlers;
    }

    private class WebHandler extends FacebookDialogBase<String, Result>.ModeHandler {
        private WebHandler() {
            super();
        }

        public boolean canShow(String content, boolean isBestEffort) {
            return true;
        }

        public AppCall createAppCall(String content) {
            AppCall appCall = JoinAppGroupDialog.this.createBaseAppCall();
            Bundle params = new Bundle();
            params.putString("id", content);
            DialogPresenter.setupAppCallForWebDialog(appCall, JoinAppGroupDialog.JOIN_GAME_GROUP_DIALOG, params);
            return appCall;
        }
    }
}
