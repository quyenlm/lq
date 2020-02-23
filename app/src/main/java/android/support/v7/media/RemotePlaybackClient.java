package android.support.v7.media;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.media.MediaRouter;
import android.util.Log;
import com.amazonaws.services.s3.internal.Constants;

public class RemotePlaybackClient {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "RemotePlaybackClient";
    private final ActionReceiver mActionReceiver;
    private final Context mContext;
    private final PendingIntent mItemStatusPendingIntent;
    private final PendingIntent mMessagePendingIntent;
    OnMessageReceivedListener mOnMessageReceivedListener;
    private final MediaRouter.RouteInfo mRoute;
    private boolean mRouteSupportsMessaging;
    private boolean mRouteSupportsQueuing;
    private boolean mRouteSupportsRemotePlayback;
    private boolean mRouteSupportsSessionManagement;
    String mSessionId;
    private final PendingIntent mSessionStatusPendingIntent;
    StatusCallback mStatusCallback;

    public interface OnMessageReceivedListener {
        void onMessageReceived(String str, Bundle bundle);
    }

    public RemotePlaybackClient(Context context, MediaRouter.RouteInfo route) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        } else if (route == null) {
            throw new IllegalArgumentException("route must not be null");
        } else {
            this.mContext = context;
            this.mRoute = route;
            IntentFilter actionFilter = new IntentFilter();
            actionFilter.addAction(ActionReceiver.ACTION_ITEM_STATUS_CHANGED);
            actionFilter.addAction(ActionReceiver.ACTION_SESSION_STATUS_CHANGED);
            actionFilter.addAction(ActionReceiver.ACTION_MESSAGE_RECEIVED);
            this.mActionReceiver = new ActionReceiver();
            context.registerReceiver(this.mActionReceiver, actionFilter);
            Intent itemStatusIntent = new Intent(ActionReceiver.ACTION_ITEM_STATUS_CHANGED);
            itemStatusIntent.setPackage(context.getPackageName());
            this.mItemStatusPendingIntent = PendingIntent.getBroadcast(context, 0, itemStatusIntent, 0);
            Intent sessionStatusIntent = new Intent(ActionReceiver.ACTION_SESSION_STATUS_CHANGED);
            sessionStatusIntent.setPackage(context.getPackageName());
            this.mSessionStatusPendingIntent = PendingIntent.getBroadcast(context, 0, sessionStatusIntent, 0);
            Intent messageIntent = new Intent(ActionReceiver.ACTION_MESSAGE_RECEIVED);
            messageIntent.setPackage(context.getPackageName());
            this.mMessagePendingIntent = PendingIntent.getBroadcast(context, 0, messageIntent, 0);
            detectFeatures();
        }
    }

    public void release() {
        this.mContext.unregisterReceiver(this.mActionReceiver);
    }

    public boolean isRemotePlaybackSupported() {
        return this.mRouteSupportsRemotePlayback;
    }

    public boolean isQueuingSupported() {
        return this.mRouteSupportsQueuing;
    }

    public boolean isSessionManagementSupported() {
        return this.mRouteSupportsSessionManagement;
    }

    public boolean isMessagingSupported() {
        return this.mRouteSupportsMessaging;
    }

    public String getSessionId() {
        return this.mSessionId;
    }

    public void setSessionId(String sessionId) {
        if (this.mSessionId == sessionId) {
            return;
        }
        if (this.mSessionId == null || !this.mSessionId.equals(sessionId)) {
            if (DEBUG) {
                Log.d(TAG, "Session id is now: " + sessionId);
            }
            this.mSessionId = sessionId;
            if (this.mStatusCallback != null) {
                this.mStatusCallback.onSessionChanged(sessionId);
            }
        }
    }

    public boolean hasSession() {
        return this.mSessionId != null;
    }

    public void setStatusCallback(StatusCallback callback) {
        this.mStatusCallback = callback;
    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener listener) {
        this.mOnMessageReceivedListener = listener;
    }

    public void play(Uri contentUri, String mimeType, Bundle metadata, long positionMillis, Bundle extras, ItemActionCallback callback) {
        playOrEnqueue(contentUri, mimeType, metadata, positionMillis, extras, callback, MediaControlIntent.ACTION_PLAY);
    }

    public void enqueue(Uri contentUri, String mimeType, Bundle metadata, long positionMillis, Bundle extras, ItemActionCallback callback) {
        playOrEnqueue(contentUri, mimeType, metadata, positionMillis, extras, callback, MediaControlIntent.ACTION_ENQUEUE);
    }

    private void playOrEnqueue(Uri contentUri, String mimeType, Bundle metadata, long positionMillis, Bundle extras, ItemActionCallback callback, String action) {
        if (contentUri == null) {
            throw new IllegalArgumentException("contentUri must not be null");
        }
        throwIfRemotePlaybackNotSupported();
        if (action.equals(MediaControlIntent.ACTION_ENQUEUE)) {
            throwIfQueuingNotSupported();
        }
        Intent intent = new Intent(action);
        intent.setDataAndType(contentUri, mimeType);
        intent.putExtra(MediaControlIntent.EXTRA_ITEM_STATUS_UPDATE_RECEIVER, this.mItemStatusPendingIntent);
        if (metadata != null) {
            intent.putExtra(MediaControlIntent.EXTRA_ITEM_METADATA, metadata);
        }
        if (positionMillis != 0) {
            intent.putExtra(MediaControlIntent.EXTRA_ITEM_CONTENT_POSITION, positionMillis);
        }
        performItemAction(intent, this.mSessionId, (String) null, extras, callback);
    }

    public void seek(String itemId, long positionMillis, Bundle extras, ItemActionCallback callback) {
        if (itemId == null) {
            throw new IllegalArgumentException("itemId must not be null");
        }
        throwIfNoCurrentSession();
        Intent intent = new Intent(MediaControlIntent.ACTION_SEEK);
        intent.putExtra(MediaControlIntent.EXTRA_ITEM_CONTENT_POSITION, positionMillis);
        performItemAction(intent, this.mSessionId, itemId, extras, callback);
    }

    public void getStatus(String itemId, Bundle extras, ItemActionCallback callback) {
        if (itemId == null) {
            throw new IllegalArgumentException("itemId must not be null");
        }
        throwIfNoCurrentSession();
        performItemAction(new Intent(MediaControlIntent.ACTION_GET_STATUS), this.mSessionId, itemId, extras, callback);
    }

    public void remove(String itemId, Bundle extras, ItemActionCallback callback) {
        if (itemId == null) {
            throw new IllegalArgumentException("itemId must not be null");
        }
        throwIfQueuingNotSupported();
        throwIfNoCurrentSession();
        performItemAction(new Intent(MediaControlIntent.ACTION_REMOVE), this.mSessionId, itemId, extras, callback);
    }

    public void pause(Bundle extras, SessionActionCallback callback) {
        throwIfNoCurrentSession();
        performSessionAction(new Intent(MediaControlIntent.ACTION_PAUSE), this.mSessionId, extras, callback);
    }

    public void resume(Bundle extras, SessionActionCallback callback) {
        throwIfNoCurrentSession();
        performSessionAction(new Intent(MediaControlIntent.ACTION_RESUME), this.mSessionId, extras, callback);
    }

    public void stop(Bundle extras, SessionActionCallback callback) {
        throwIfNoCurrentSession();
        performSessionAction(new Intent(MediaControlIntent.ACTION_STOP), this.mSessionId, extras, callback);
    }

    public void startSession(Bundle extras, SessionActionCallback callback) {
        throwIfSessionManagementNotSupported();
        Intent intent = new Intent(MediaControlIntent.ACTION_START_SESSION);
        intent.putExtra(MediaControlIntent.EXTRA_SESSION_STATUS_UPDATE_RECEIVER, this.mSessionStatusPendingIntent);
        if (this.mRouteSupportsMessaging) {
            intent.putExtra(MediaControlIntent.EXTRA_MESSAGE_RECEIVER, this.mMessagePendingIntent);
        }
        performSessionAction(intent, (String) null, extras, callback);
    }

    public void sendMessage(Bundle message, SessionActionCallback callback) {
        throwIfNoCurrentSession();
        throwIfMessageNotSupported();
        performSessionAction(new Intent(MediaControlIntent.ACTION_SEND_MESSAGE), this.mSessionId, message, callback);
    }

    public void getSessionStatus(Bundle extras, SessionActionCallback callback) {
        throwIfSessionManagementNotSupported();
        throwIfNoCurrentSession();
        performSessionAction(new Intent(MediaControlIntent.ACTION_GET_SESSION_STATUS), this.mSessionId, extras, callback);
    }

    public void endSession(Bundle extras, SessionActionCallback callback) {
        throwIfSessionManagementNotSupported();
        throwIfNoCurrentSession();
        performSessionAction(new Intent(MediaControlIntent.ACTION_END_SESSION), this.mSessionId, extras, callback);
    }

    private void performItemAction(Intent intent, String sessionId, String itemId, Bundle extras, ItemActionCallback callback) {
        intent.addCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK);
        if (sessionId != null) {
            intent.putExtra(MediaControlIntent.EXTRA_SESSION_ID, sessionId);
        }
        if (itemId != null) {
            intent.putExtra(MediaControlIntent.EXTRA_ITEM_ID, itemId);
        }
        if (extras != null) {
            intent.putExtras(extras);
        }
        logRequest(intent);
        final String str = sessionId;
        final String str2 = itemId;
        final Intent intent2 = intent;
        final ItemActionCallback itemActionCallback = callback;
        this.mRoute.sendControlRequest(intent, new MediaRouter.ControlRequestCallback() {
            public void onResult(Bundle data) {
                if (data != null) {
                    String sessionIdResult = RemotePlaybackClient.inferMissingResult(str, data.getString(MediaControlIntent.EXTRA_SESSION_ID));
                    MediaSessionStatus sessionStatus = MediaSessionStatus.fromBundle(data.getBundle(MediaControlIntent.EXTRA_SESSION_STATUS));
                    String itemIdResult = RemotePlaybackClient.inferMissingResult(str2, data.getString(MediaControlIntent.EXTRA_ITEM_ID));
                    MediaItemStatus itemStatus = MediaItemStatus.fromBundle(data.getBundle(MediaControlIntent.EXTRA_ITEM_STATUS));
                    RemotePlaybackClient.this.adoptSession(sessionIdResult);
                    if (!(sessionIdResult == null || itemIdResult == null || itemStatus == null)) {
                        if (RemotePlaybackClient.DEBUG) {
                            Log.d(RemotePlaybackClient.TAG, "Received result from " + intent2.getAction() + ": data=" + RemotePlaybackClient.bundleToString(data) + ", sessionId=" + sessionIdResult + ", sessionStatus=" + sessionStatus + ", itemId=" + itemIdResult + ", itemStatus=" + itemStatus);
                        }
                        itemActionCallback.onResult(data, sessionIdResult, sessionStatus, itemIdResult, itemStatus);
                        return;
                    }
                }
                RemotePlaybackClient.this.handleInvalidResult(intent2, itemActionCallback, data);
            }

            public void onError(String error, Bundle data) {
                RemotePlaybackClient.this.handleError(intent2, itemActionCallback, error, data);
            }
        });
    }

    private void performSessionAction(final Intent intent, final String sessionId, Bundle extras, final SessionActionCallback callback) {
        intent.addCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK);
        if (sessionId != null) {
            intent.putExtra(MediaControlIntent.EXTRA_SESSION_ID, sessionId);
        }
        if (extras != null) {
            intent.putExtras(extras);
        }
        logRequest(intent);
        this.mRoute.sendControlRequest(intent, new MediaRouter.ControlRequestCallback() {
            public void onResult(Bundle data) {
                boolean equals;
                boolean equals2;
                if (data != null) {
                    String sessionIdResult = RemotePlaybackClient.inferMissingResult(sessionId, data.getString(MediaControlIntent.EXTRA_SESSION_ID));
                    MediaSessionStatus sessionStatus = MediaSessionStatus.fromBundle(data.getBundle(MediaControlIntent.EXTRA_SESSION_STATUS));
                    RemotePlaybackClient.this.adoptSession(sessionIdResult);
                    if (sessionIdResult != null) {
                        if (RemotePlaybackClient.DEBUG) {
                            Log.d(RemotePlaybackClient.TAG, "Received result from " + intent.getAction() + ": data=" + RemotePlaybackClient.bundleToString(data) + ", sessionId=" + sessionIdResult + ", sessionStatus=" + sessionStatus);
                        }
                        try {
                            callback.onResult(data, sessionIdResult, sessionStatus);
                            if (equals && equals2) {
                                return;
                            }
                            return;
                        } finally {
                            if (intent.getAction().equals(MediaControlIntent.ACTION_END_SESSION) && sessionIdResult.equals(RemotePlaybackClient.this.mSessionId)) {
                                RemotePlaybackClient.this.setSessionId((String) null);
                            }
                        }
                    }
                }
                RemotePlaybackClient.this.handleInvalidResult(intent, callback, data);
            }

            public void onError(String error, Bundle data) {
                RemotePlaybackClient.this.handleError(intent, callback, error, data);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void adoptSession(String sessionId) {
        if (sessionId != null) {
            setSessionId(sessionId);
        }
    }

    /* access modifiers changed from: package-private */
    public void handleInvalidResult(Intent intent, ActionCallback callback, Bundle data) {
        Log.w(TAG, "Received invalid result data from " + intent.getAction() + ": data=" + bundleToString(data));
        callback.onError((String) null, 0, data);
    }

    /* access modifiers changed from: package-private */
    public void handleError(Intent intent, ActionCallback callback, String error, Bundle data) {
        int code;
        if (data != null) {
            code = data.getInt(MediaControlIntent.EXTRA_ERROR_CODE, 0);
        } else {
            code = 0;
        }
        if (DEBUG) {
            Log.w(TAG, "Received error from " + intent.getAction() + ": error=" + error + ", code=" + code + ", data=" + bundleToString(data));
        }
        callback.onError(error, code, data);
    }

    private void detectFeatures() {
        boolean z;
        boolean z2 = true;
        this.mRouteSupportsRemotePlayback = routeSupportsAction(MediaControlIntent.ACTION_PLAY) && routeSupportsAction(MediaControlIntent.ACTION_SEEK) && routeSupportsAction(MediaControlIntent.ACTION_GET_STATUS) && routeSupportsAction(MediaControlIntent.ACTION_PAUSE) && routeSupportsAction(MediaControlIntent.ACTION_RESUME) && routeSupportsAction(MediaControlIntent.ACTION_STOP);
        if (!this.mRouteSupportsRemotePlayback || !routeSupportsAction(MediaControlIntent.ACTION_ENQUEUE) || !routeSupportsAction(MediaControlIntent.ACTION_REMOVE)) {
            z = false;
        } else {
            z = true;
        }
        this.mRouteSupportsQueuing = z;
        if (!this.mRouteSupportsRemotePlayback || !routeSupportsAction(MediaControlIntent.ACTION_START_SESSION) || !routeSupportsAction(MediaControlIntent.ACTION_GET_SESSION_STATUS) || !routeSupportsAction(MediaControlIntent.ACTION_END_SESSION)) {
            z2 = false;
        }
        this.mRouteSupportsSessionManagement = z2;
        this.mRouteSupportsMessaging = doesRouteSupportMessaging();
    }

    private boolean routeSupportsAction(String action) {
        return this.mRoute.supportsControlAction(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK, action);
    }

    private boolean doesRouteSupportMessaging() {
        for (IntentFilter filter : this.mRoute.getControlFilters()) {
            if (filter.hasAction(MediaControlIntent.ACTION_SEND_MESSAGE)) {
                return true;
            }
        }
        return false;
    }

    private void throwIfRemotePlaybackNotSupported() {
        if (!this.mRouteSupportsRemotePlayback) {
            throw new UnsupportedOperationException("The route does not support remote playback.");
        }
    }

    private void throwIfQueuingNotSupported() {
        if (!this.mRouteSupportsQueuing) {
            throw new UnsupportedOperationException("The route does not support queuing.");
        }
    }

    private void throwIfSessionManagementNotSupported() {
        if (!this.mRouteSupportsSessionManagement) {
            throw new UnsupportedOperationException("The route does not support session management.");
        }
    }

    private void throwIfMessageNotSupported() {
        if (!this.mRouteSupportsMessaging) {
            throw new UnsupportedOperationException("The route does not support message.");
        }
    }

    private void throwIfNoCurrentSession() {
        if (this.mSessionId == null) {
            throw new IllegalStateException("There is no current session.");
        }
    }

    static String inferMissingResult(String request, String result) {
        if (result == null) {
            return request;
        }
        if (request == null || request.equals(result)) {
            return result;
        }
        return null;
    }

    private static void logRequest(Intent intent) {
        if (DEBUG) {
            Log.d(TAG, "Sending request: " + intent);
        }
    }

    static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return Constants.NULL_VERSION_ID;
        }
        bundle.size();
        return bundle.toString();
    }

    private final class ActionReceiver extends BroadcastReceiver {
        public static final String ACTION_ITEM_STATUS_CHANGED = "android.support.v7.media.actions.ACTION_ITEM_STATUS_CHANGED";
        public static final String ACTION_MESSAGE_RECEIVED = "android.support.v7.media.actions.ACTION_MESSAGE_RECEIVED";
        public static final String ACTION_SESSION_STATUS_CHANGED = "android.support.v7.media.actions.ACTION_SESSION_STATUS_CHANGED";

        ActionReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String sessionId = intent.getStringExtra(MediaControlIntent.EXTRA_SESSION_ID);
            if (sessionId == null || !sessionId.equals(RemotePlaybackClient.this.mSessionId)) {
                Log.w(RemotePlaybackClient.TAG, "Discarding spurious status callback with missing or invalid session id: sessionId=" + sessionId);
                return;
            }
            MediaSessionStatus sessionStatus = MediaSessionStatus.fromBundle(intent.getBundleExtra(MediaControlIntent.EXTRA_SESSION_STATUS));
            String action = intent.getAction();
            if (action.equals(ACTION_ITEM_STATUS_CHANGED)) {
                String itemId = intent.getStringExtra(MediaControlIntent.EXTRA_ITEM_ID);
                if (itemId == null) {
                    Log.w(RemotePlaybackClient.TAG, "Discarding spurious status callback with missing item id.");
                    return;
                }
                MediaItemStatus itemStatus = MediaItemStatus.fromBundle(intent.getBundleExtra(MediaControlIntent.EXTRA_ITEM_STATUS));
                if (itemStatus == null) {
                    Log.w(RemotePlaybackClient.TAG, "Discarding spurious status callback with missing item status.");
                    return;
                }
                if (RemotePlaybackClient.DEBUG) {
                    Log.d(RemotePlaybackClient.TAG, "Received item status callback: sessionId=" + sessionId + ", sessionStatus=" + sessionStatus + ", itemId=" + itemId + ", itemStatus=" + itemStatus);
                }
                if (RemotePlaybackClient.this.mStatusCallback != null) {
                    RemotePlaybackClient.this.mStatusCallback.onItemStatusChanged(intent.getExtras(), sessionId, sessionStatus, itemId, itemStatus);
                }
            } else if (action.equals(ACTION_SESSION_STATUS_CHANGED)) {
                if (sessionStatus == null) {
                    Log.w(RemotePlaybackClient.TAG, "Discarding spurious media status callback with missing session status.");
                    return;
                }
                if (RemotePlaybackClient.DEBUG) {
                    Log.d(RemotePlaybackClient.TAG, "Received session status callback: sessionId=" + sessionId + ", sessionStatus=" + sessionStatus);
                }
                if (RemotePlaybackClient.this.mStatusCallback != null) {
                    RemotePlaybackClient.this.mStatusCallback.onSessionStatusChanged(intent.getExtras(), sessionId, sessionStatus);
                }
            } else if (action.equals(ACTION_MESSAGE_RECEIVED)) {
                if (RemotePlaybackClient.DEBUG) {
                    Log.d(RemotePlaybackClient.TAG, "Received message callback: sessionId=" + sessionId);
                }
                if (RemotePlaybackClient.this.mOnMessageReceivedListener != null) {
                    RemotePlaybackClient.this.mOnMessageReceivedListener.onMessageReceived(sessionId, intent.getBundleExtra(MediaControlIntent.EXTRA_MESSAGE));
                }
            }
        }
    }

    public static abstract class StatusCallback {
        public void onItemStatusChanged(Bundle data, String sessionId, MediaSessionStatus sessionStatus, String itemId, MediaItemStatus itemStatus) {
        }

        public void onSessionStatusChanged(Bundle data, String sessionId, MediaSessionStatus sessionStatus) {
        }

        public void onSessionChanged(String sessionId) {
        }
    }

    public static abstract class ActionCallback {
        public void onError(String error, int code, Bundle data) {
        }
    }

    public static abstract class ItemActionCallback extends ActionCallback {
        public void onResult(Bundle data, String sessionId, MediaSessionStatus sessionStatus, String itemId, MediaItemStatus itemStatus) {
        }
    }

    public static abstract class SessionActionCallback extends ActionCallback {
        public void onResult(Bundle data, String sessionId, MediaSessionStatus sessionStatus) {
        }
    }
}
