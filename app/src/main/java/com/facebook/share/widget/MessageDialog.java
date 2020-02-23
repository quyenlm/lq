package com.facebook.share.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.Sharer;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.MessageDialogFeature;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphMessageDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;

public final class MessageDialog extends FacebookDialogBase<ShareContent, Sharer.Result> implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Message.toRequestCode();
    private boolean shouldFailOnDataError;

    public static void show(Activity activity, ShareContent shareContent) {
        new MessageDialog(activity).show(shareContent);
    }

    public static void show(Fragment fragment, ShareContent shareContent) {
        show(new FragmentWrapper(fragment), shareContent);
    }

    public static void show(android.app.Fragment fragment, ShareContent shareContent) {
        show(new FragmentWrapper(fragment), shareContent);
    }

    private static void show(FragmentWrapper fragmentWrapper, ShareContent shareContent) {
        new MessageDialog(fragmentWrapper).show(shareContent);
    }

    public static boolean canShow(Class<? extends ShareContent> contentType) {
        DialogFeature feature = getFeature(contentType);
        return feature != null && DialogPresenter.canPresentNativeDialogWithFeature(feature);
    }

    public MessageDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    public MessageDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    public MessageDialog(android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    private MessageDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    MessageDialog(Activity activity, int requestCode) {
        super(activity, requestCode);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(requestCode);
    }

    MessageDialog(Fragment fragment, int requestCode) {
        this(new FragmentWrapper(fragment), requestCode);
    }

    MessageDialog(android.app.Fragment fragment, int requestCode) {
        this(new FragmentWrapper(fragment), requestCode);
    }

    private MessageDialog(FragmentWrapper fragmentWrapper, int requestCode) {
        super(fragmentWrapper, requestCode);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(requestCode);
    }

    /* access modifiers changed from: protected */
    public void registerCallbackImpl(CallbackManagerImpl callbackManager, FacebookCallback<Sharer.Result> callback) {
        ShareInternalUtility.registerSharerCallback(getRequestCode(), callbackManager, callback);
    }

    public boolean getShouldFailOnDataError() {
        return this.shouldFailOnDataError;
    }

    public void setShouldFailOnDataError(boolean shouldFailOnDataError2) {
        this.shouldFailOnDataError = shouldFailOnDataError2;
    }

    /* access modifiers changed from: protected */
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    /* access modifiers changed from: protected */
    public List<FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler> handlers = new ArrayList<>();
        handlers.add(new NativeHandler());
        return handlers;
    }

    private class NativeHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private NativeHandler() {
            super();
        }

        public boolean canShow(ShareContent shareContent, boolean isBestEffort) {
            return shareContent != null && MessageDialog.canShow(shareContent.getClass());
        }

        public AppCall createAppCall(final ShareContent content) {
            ShareContentValidation.validateForMessage(content);
            final AppCall appCall = MessageDialog.this.createBaseAppCall();
            final boolean shouldFailOnDataError = MessageDialog.this.getShouldFailOnDataError();
            MessageDialog.logDialogShare(MessageDialog.this.getActivityContext(), content, appCall);
            DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider() {
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCall.getCallId(), content, shouldFailOnDataError);
                }

                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCall.getCallId(), content, shouldFailOnDataError);
                }
            }, MessageDialog.getFeature(content.getClass()));
            return appCall;
        }
    }

    /* access modifiers changed from: private */
    public static DialogFeature getFeature(Class<? extends ShareContent> type) {
        if (ShareLinkContent.class.isAssignableFrom(type)) {
            return MessageDialogFeature.MESSAGE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom(type)) {
            return MessageDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom(type)) {
            return MessageDialogFeature.VIDEO;
        }
        if (ShareOpenGraphContent.class.isAssignableFrom(type)) {
            return OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG;
        }
        if (ShareMessengerGenericTemplateContent.class.isAssignableFrom(type)) {
            return MessageDialogFeature.MESSENGER_GENERIC_TEMPLATE;
        }
        if (ShareMessengerOpenGraphMusicTemplateContent.class.isAssignableFrom(type)) {
            return MessageDialogFeature.MESSENGER_OPEN_GRAPH_MUSIC_TEMPLATE;
        }
        if (ShareMessengerMediaTemplateContent.class.isAssignableFrom(type)) {
            return MessageDialogFeature.MESSENGER_MEDIA_TEMPLATE;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static void logDialogShare(Context context, ShareContent content, AppCall appCall) {
        String contentType;
        DialogFeature dialogFeature = getFeature(content.getClass());
        if (dialogFeature == MessageDialogFeature.MESSAGE_DIALOG) {
            contentType = "status";
        } else if (dialogFeature == MessageDialogFeature.PHOTOS) {
            contentType = "photo";
        } else if (dialogFeature == MessageDialogFeature.VIDEO) {
            contentType = "video";
        } else if (dialogFeature == OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG) {
            contentType = "open_graph";
        } else if (dialogFeature == MessageDialogFeature.MESSENGER_GENERIC_TEMPLATE) {
            contentType = AnalyticsEvents.PARAMETER_SHARE_MESSENGER_GENERIC_TEMPLATE;
        } else if (dialogFeature == MessageDialogFeature.MESSENGER_MEDIA_TEMPLATE) {
            contentType = AnalyticsEvents.PARAMETER_SHARE_MESSENGER_MEDIA_TEMPLATE;
        } else if (dialogFeature == MessageDialogFeature.MESSENGER_OPEN_GRAPH_MUSIC_TEMPLATE) {
            contentType = AnalyticsEvents.PARAMETER_SHARE_MESSENGER_OPEN_GRAPH_MUSIC_TEMPLATE;
        } else {
            contentType = "unknown";
        }
        AppEventsLogger logger = AppEventsLogger.newLogger(context);
        Bundle parameters = new Bundle();
        parameters.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_TYPE, contentType);
        parameters.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_UUID, appCall.getCallId().toString());
        parameters.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_PAGE_ID, content.getPageId());
        logger.logSdkEvent(AnalyticsEvents.EVENT_SHARE_MESSENGER_DIALOG_SHOW, (Double) null, parameters);
    }
}
