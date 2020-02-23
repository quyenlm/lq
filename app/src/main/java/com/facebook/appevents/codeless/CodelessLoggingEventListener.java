package com.facebook.appevents.codeless;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.codeless.internal.EventBinding;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.AppEventUtility;
import java.lang.ref.WeakReference;

class CodelessLoggingEventListener {
    /* access modifiers changed from: private */
    public static final String TAG = CodelessLoggingEventListener.class.getCanonicalName();

    CodelessLoggingEventListener() {
    }

    public static AutoLoggingAccessibilityDelegate getAccessibilityDelegate(EventBinding mapping, View rootView, View hostView) {
        return new AutoLoggingAccessibilityDelegate(mapping, rootView, hostView);
    }

    public static class AutoLoggingAccessibilityDelegate extends View.AccessibilityDelegate {
        private int accessibilityEventType;
        private View.AccessibilityDelegate existingDelegate;
        private WeakReference<View> hostView;
        private EventBinding mapping;
        private WeakReference<View> rootView;

        public AutoLoggingAccessibilityDelegate(EventBinding mapping2, View rootView2, View hostView2) {
            if (mapping2 != null && rootView2 != null && hostView2 != null) {
                this.existingDelegate = ViewHierarchy.getExistingDelegate(hostView2);
                this.mapping = mapping2;
                this.hostView = new WeakReference<>(hostView2);
                this.rootView = new WeakReference<>(rootView2);
                EventBinding.ActionType type = mapping2.getType();
                switch (mapping2.getType()) {
                    case CLICK:
                        this.accessibilityEventType = 1;
                        return;
                    case SELECTED:
                        this.accessibilityEventType = 4;
                        return;
                    case TEXT_CHANGED:
                        this.accessibilityEventType = 16;
                        return;
                    default:
                        throw new FacebookException("Unsupported action type: " + type.toString());
                }
            }
        }

        public void sendAccessibilityEvent(View host, int eventType) {
            if (eventType == -1) {
                Log.e(CodelessLoggingEventListener.TAG, "Unsupported action type");
            }
            if (eventType == this.accessibilityEventType) {
                if (this.existingDelegate != null && !(this.existingDelegate instanceof AutoLoggingAccessibilityDelegate)) {
                    this.existingDelegate.sendAccessibilityEvent(host, eventType);
                }
                logEvent();
            }
        }

        private void logEvent() {
            final String eventName = this.mapping.getEventName();
            Bundle parameters = CodelessMatcher.getParameters(this.mapping, (View) this.rootView.get(), (View) this.hostView.get());
            if (parameters.containsKey(AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM)) {
                parameters.putDouble(AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM, AppEventUtility.normalizePrice(parameters.getString(AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM)));
            }
            parameters.putString(Constants.IS_CODELESS_EVENT_KEY, "1");
            final Bundle params = parameters;
            FacebookSdk.getExecutor().execute(new Runnable() {
                public void run() {
                    AppEventsLogger.newLogger(FacebookSdk.getApplicationContext()).logEvent(eventName, params);
                }
            });
        }
    }
}
