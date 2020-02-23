package android.support.v7.media;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.util.Pair;
import android.support.v7.media.MediaRouteProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.RegisteredMediaRouteProviderWatcher;
import android.support.v7.media.RemoteControlClientCompat;
import android.support.v7.media.SystemMediaRouteProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class MediaRouter {
    public static final int AVAILABILITY_FLAG_IGNORE_DEFAULT_ROUTE = 1;
    public static final int AVAILABILITY_FLAG_REQUIRE_MATCH = 2;
    public static final int CALLBACK_FLAG_FORCE_DISCOVERY = 8;
    public static final int CALLBACK_FLAG_PERFORM_ACTIVE_SCAN = 1;
    public static final int CALLBACK_FLAG_REQUEST_DISCOVERY = 4;
    public static final int CALLBACK_FLAG_UNFILTERED_EVENTS = 2;
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MediaRouter";
    public static final int UNSELECT_REASON_DISCONNECTED = 1;
    public static final int UNSELECT_REASON_ROUTE_CHANGED = 3;
    public static final int UNSELECT_REASON_STOPPED = 2;
    public static final int UNSELECT_REASON_UNKNOWN = 0;
    static GlobalMediaRouter sGlobal;
    final ArrayList<CallbackRecord> mCallbackRecords = new ArrayList<>();
    final Context mContext;

    MediaRouter(Context context) {
        this.mContext = context;
    }

    public static MediaRouter getInstance(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        checkCallingThread();
        if (sGlobal == null) {
            sGlobal = new GlobalMediaRouter(context.getApplicationContext());
            sGlobal.start();
        }
        return sGlobal.getRouter(context);
    }

    public List<RouteInfo> getRoutes() {
        checkCallingThread();
        return sGlobal.getRoutes();
    }

    public List<ProviderInfo> getProviders() {
        checkCallingThread();
        return sGlobal.getProviders();
    }

    @NonNull
    public RouteInfo getDefaultRoute() {
        checkCallingThread();
        return sGlobal.getDefaultRoute();
    }

    public RouteInfo getBluetoothRoute() {
        checkCallingThread();
        return sGlobal.getBluetoothRoute();
    }

    @NonNull
    public RouteInfo getSelectedRoute() {
        checkCallingThread();
        return sGlobal.getSelectedRoute();
    }

    @NonNull
    public RouteInfo updateSelectedRoute(@NonNull MediaRouteSelector selector) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "updateSelectedRoute: " + selector);
        }
        RouteInfo route = sGlobal.getSelectedRoute();
        if (route.isDefaultOrBluetooth() || route.matchesSelector(selector)) {
            return route;
        }
        RouteInfo route2 = sGlobal.chooseFallbackRoute();
        sGlobal.selectRoute(route2);
        return route2;
    }

    public void selectRoute(@NonNull RouteInfo route) {
        if (route == null) {
            throw new IllegalArgumentException("route must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "selectRoute: " + route);
        }
        sGlobal.selectRoute(route);
    }

    public void unselect(int reason) {
        if (reason < 0 || reason > 3) {
            throw new IllegalArgumentException("Unsupported reason to unselect route");
        }
        checkCallingThread();
        RouteInfo fallbackRoute = sGlobal.chooseFallbackRoute();
        if (sGlobal.getSelectedRoute() != fallbackRoute) {
            sGlobal.selectRoute(fallbackRoute, reason);
        } else {
            sGlobal.selectRoute(sGlobal.getDefaultRoute(), reason);
        }
    }

    public boolean isRouteAvailable(@NonNull MediaRouteSelector selector, int flags) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        checkCallingThread();
        return sGlobal.isRouteAvailable(selector, flags);
    }

    public void addCallback(MediaRouteSelector selector, Callback callback) {
        addCallback(selector, callback, 0);
    }

    public void addCallback(@NonNull MediaRouteSelector selector, @NonNull Callback callback, int flags) {
        CallbackRecord record;
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        } else {
            checkCallingThread();
            if (DEBUG) {
                Log.d(TAG, "addCallback: selector=" + selector + ", callback=" + callback + ", flags=" + Integer.toHexString(flags));
            }
            int index = findCallbackRecord(callback);
            if (index < 0) {
                record = new CallbackRecord(this, callback);
                this.mCallbackRecords.add(record);
            } else {
                record = this.mCallbackRecords.get(index);
            }
            boolean updateNeeded = false;
            if (((record.mFlags ^ -1) & flags) != 0) {
                record.mFlags |= flags;
                updateNeeded = true;
            }
            if (!record.mSelector.contains(selector)) {
                record.mSelector = new MediaRouteSelector.Builder(record.mSelector).addSelector(selector).build();
                updateNeeded = true;
            }
            if (updateNeeded) {
                sGlobal.updateDiscoveryRequest();
            }
        }
    }

    public void removeCallback(@NonNull Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "removeCallback: callback=" + callback);
        }
        int index = findCallbackRecord(callback);
        if (index >= 0) {
            this.mCallbackRecords.remove(index);
            sGlobal.updateDiscoveryRequest();
        }
    }

    private int findCallbackRecord(Callback callback) {
        int count = this.mCallbackRecords.size();
        for (int i = 0; i < count; i++) {
            if (this.mCallbackRecords.get(i).mCallback == callback) {
                return i;
            }
        }
        return -1;
    }

    public void addProvider(@NonNull MediaRouteProvider providerInstance) {
        if (providerInstance == null) {
            throw new IllegalArgumentException("providerInstance must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "addProvider: " + providerInstance);
        }
        sGlobal.addProvider(providerInstance);
    }

    public void removeProvider(@NonNull MediaRouteProvider providerInstance) {
        if (providerInstance == null) {
            throw new IllegalArgumentException("providerInstance must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "removeProvider: " + providerInstance);
        }
        sGlobal.removeProvider(providerInstance);
    }

    public void addRemoteControlClient(@NonNull Object remoteControlClient) {
        if (remoteControlClient == null) {
            throw new IllegalArgumentException("remoteControlClient must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "addRemoteControlClient: " + remoteControlClient);
        }
        sGlobal.addRemoteControlClient(remoteControlClient);
    }

    public void removeRemoteControlClient(@NonNull Object remoteControlClient) {
        if (remoteControlClient == null) {
            throw new IllegalArgumentException("remoteControlClient must not be null");
        }
        if (DEBUG) {
            Log.d(TAG, "removeRemoteControlClient: " + remoteControlClient);
        }
        sGlobal.removeRemoteControlClient(remoteControlClient);
    }

    public void setMediaSession(Object mediaSession) {
        if (DEBUG) {
            Log.d(TAG, "addMediaSession: " + mediaSession);
        }
        sGlobal.setMediaSession(mediaSession);
    }

    public void setMediaSessionCompat(MediaSessionCompat mediaSession) {
        if (DEBUG) {
            Log.d(TAG, "addMediaSessionCompat: " + mediaSession);
        }
        sGlobal.setMediaSessionCompat(mediaSession);
    }

    public MediaSessionCompat.Token getMediaSessionToken() {
        return sGlobal.getMediaSessionToken();
    }

    static void checkCallingThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("The media router service must only be accessed on the application's main thread.");
        }
    }

    static <T> boolean equal(T a, T b) {
        return a == b || !(a == null || b == null || !a.equals(b));
    }

    public static class RouteInfo {
        static final int CHANGE_GENERAL = 1;
        static final int CHANGE_PRESENTATION_DISPLAY = 4;
        static final int CHANGE_VOLUME = 2;
        public static final int CONNECTION_STATE_CONNECTED = 2;
        public static final int CONNECTION_STATE_CONNECTING = 1;
        public static final int CONNECTION_STATE_DISCONNECTED = 0;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static final int DEVICE_TYPE_BLUETOOTH = 3;
        public static final int DEVICE_TYPE_SPEAKER = 2;
        public static final int DEVICE_TYPE_TV = 1;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static final int DEVICE_TYPE_UNKNOWN = 0;
        public static final int PLAYBACK_TYPE_LOCAL = 0;
        public static final int PLAYBACK_TYPE_REMOTE = 1;
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static final int PRESENTATION_DISPLAY_ID_NONE = -1;
        static final String SYSTEM_MEDIA_ROUTE_PROVIDER_PACKAGE_NAME = "android";
        private boolean mCanDisconnect;
        private boolean mConnecting;
        private int mConnectionState;
        private final ArrayList<IntentFilter> mControlFilters = new ArrayList<>();
        private String mDescription;
        MediaRouteDescriptor mDescriptor;
        /* access modifiers changed from: private */
        public final String mDescriptorId;
        private int mDeviceType;
        /* access modifiers changed from: private */
        public boolean mEnabled;
        private Bundle mExtras;
        private Uri mIconUri;
        private String mName;
        private int mPlaybackStream;
        private int mPlaybackType;
        private Display mPresentationDisplay;
        private int mPresentationDisplayId = -1;
        private final ProviderInfo mProvider;
        private IntentSender mSettingsIntent;
        /* access modifiers changed from: private */
        public final String mUniqueId;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;

        RouteInfo(ProviderInfo provider, String descriptorId, String uniqueId) {
            this.mProvider = provider;
            this.mDescriptorId = descriptorId;
            this.mUniqueId = uniqueId;
        }

        public ProviderInfo getProvider() {
            return this.mProvider;
        }

        @NonNull
        public String getId() {
            return this.mUniqueId;
        }

        public String getName() {
            return this.mName;
        }

        @Nullable
        public String getDescription() {
            return this.mDescription;
        }

        public Uri getIconUri() {
            return this.mIconUri;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isConnecting() {
            return this.mConnecting;
        }

        public int getConnectionState() {
            return this.mConnectionState;
        }

        public boolean isSelected() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getSelectedRoute() == this;
        }

        public boolean isDefault() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getDefaultRoute() == this;
        }

        public boolean isBluetooth() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getBluetoothRoute() == this;
        }

        public boolean isDeviceSpeaker() {
            return isDefault() && Resources.getSystem().getText(Resources.getSystem().getIdentifier("default_audio_route_name", "string", "android")).equals(this.mName);
        }

        public List<IntentFilter> getControlFilters() {
            return this.mControlFilters;
        }

        public boolean matchesSelector(@NonNull MediaRouteSelector selector) {
            if (selector == null) {
                throw new IllegalArgumentException("selector must not be null");
            }
            MediaRouter.checkCallingThread();
            return selector.matchesControlFilters(this.mControlFilters);
        }

        public boolean supportsControlCategory(@NonNull String category) {
            if (category == null) {
                throw new IllegalArgumentException("category must not be null");
            }
            MediaRouter.checkCallingThread();
            int count = this.mControlFilters.size();
            for (int i = 0; i < count; i++) {
                if (this.mControlFilters.get(i).hasCategory(category)) {
                    return true;
                }
            }
            return false;
        }

        public boolean supportsControlAction(@NonNull String category, @NonNull String action) {
            if (category == null) {
                throw new IllegalArgumentException("category must not be null");
            } else if (action == null) {
                throw new IllegalArgumentException("action must not be null");
            } else {
                MediaRouter.checkCallingThread();
                int count = this.mControlFilters.size();
                for (int i = 0; i < count; i++) {
                    IntentFilter filter = this.mControlFilters.get(i);
                    if (filter.hasCategory(category) && filter.hasAction(action)) {
                        return true;
                    }
                }
                return false;
            }
        }

        public boolean supportsControlRequest(@NonNull Intent intent) {
            if (intent == null) {
                throw new IllegalArgumentException("intent must not be null");
            }
            MediaRouter.checkCallingThread();
            ContentResolver contentResolver = MediaRouter.sGlobal.getContentResolver();
            int count = this.mControlFilters.size();
            for (int i = 0; i < count; i++) {
                if (this.mControlFilters.get(i).match(contentResolver, intent, true, MediaRouter.TAG) >= 0) {
                    return true;
                }
            }
            return false;
        }

        public void sendControlRequest(@NonNull Intent intent, @Nullable ControlRequestCallback callback) {
            if (intent == null) {
                throw new IllegalArgumentException("intent must not be null");
            }
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.sendControlRequest(this, intent, callback);
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getPlaybackStream() {
            return this.mPlaybackStream;
        }

        public int getDeviceType() {
            return this.mDeviceType;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public boolean isDefaultOrBluetooth() {
            if (isDefault() || this.mDeviceType == 3) {
                return true;
            }
            if (!isSystemMediaRouteProvider(this) || !supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO) || supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO)) {
                return false;
            }
            return true;
        }

        private static boolean isSystemMediaRouteProvider(RouteInfo route) {
            return TextUtils.equals(route.getProviderInstance().getMetadata().getPackageName(), "android");
        }

        public int getVolumeHandling() {
            return this.mVolumeHandling;
        }

        public int getVolume() {
            return this.mVolume;
        }

        public int getVolumeMax() {
            return this.mVolumeMax;
        }

        public boolean canDisconnect() {
            return this.mCanDisconnect;
        }

        public void requestSetVolume(int volume) {
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.requestSetVolume(this, Math.min(this.mVolumeMax, Math.max(0, volume)));
        }

        public void requestUpdateVolume(int delta) {
            MediaRouter.checkCallingThread();
            if (delta != 0) {
                MediaRouter.sGlobal.requestUpdateVolume(this, delta);
            }
        }

        @Nullable
        public Display getPresentationDisplay() {
            MediaRouter.checkCallingThread();
            if (this.mPresentationDisplayId >= 0 && this.mPresentationDisplay == null) {
                this.mPresentationDisplay = MediaRouter.sGlobal.getDisplay(this.mPresentationDisplayId);
            }
            return this.mPresentationDisplay;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public int getPresentationDisplayId() {
            return this.mPresentationDisplayId;
        }

        @Nullable
        public Bundle getExtras() {
            return this.mExtras;
        }

        @Nullable
        public IntentSender getSettingsIntent() {
            return this.mSettingsIntent;
        }

        public void select() {
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.selectRoute(this);
        }

        public String toString() {
            return "MediaRouter.RouteInfo{ uniqueId=" + this.mUniqueId + ", name=" + this.mName + ", description=" + this.mDescription + ", iconUri=" + this.mIconUri + ", enabled=" + this.mEnabled + ", connecting=" + this.mConnecting + ", connectionState=" + this.mConnectionState + ", canDisconnect=" + this.mCanDisconnect + ", playbackType=" + this.mPlaybackType + ", playbackStream=" + this.mPlaybackStream + ", deviceType=" + this.mDeviceType + ", volumeHandling=" + this.mVolumeHandling + ", volume=" + this.mVolume + ", volumeMax=" + this.mVolumeMax + ", presentationDisplayId=" + this.mPresentationDisplayId + ", extras=" + this.mExtras + ", settingsIntent=" + this.mSettingsIntent + ", providerPackageName=" + this.mProvider.getPackageName() + " }";
        }

        /* access modifiers changed from: package-private */
        public int maybeUpdateDescriptor(MediaRouteDescriptor descriptor) {
            if (this.mDescriptor != descriptor) {
                return updateDescriptor(descriptor);
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public int updateDescriptor(MediaRouteDescriptor descriptor) {
            int changes = 0;
            this.mDescriptor = descriptor;
            if (descriptor == null) {
                return 0;
            }
            if (!MediaRouter.equal(this.mName, descriptor.getName())) {
                this.mName = descriptor.getName();
                changes = 0 | 1;
            }
            if (!MediaRouter.equal(this.mDescription, descriptor.getDescription())) {
                this.mDescription = descriptor.getDescription();
                changes |= 1;
            }
            if (!MediaRouter.equal(this.mIconUri, descriptor.getIconUri())) {
                this.mIconUri = descriptor.getIconUri();
                changes |= 1;
            }
            if (this.mEnabled != descriptor.isEnabled()) {
                this.mEnabled = descriptor.isEnabled();
                changes |= 1;
            }
            if (this.mConnecting != descriptor.isConnecting()) {
                this.mConnecting = descriptor.isConnecting();
                changes |= 1;
            }
            if (this.mConnectionState != descriptor.getConnectionState()) {
                this.mConnectionState = descriptor.getConnectionState();
                changes |= 1;
            }
            if (!this.mControlFilters.equals(descriptor.getControlFilters())) {
                this.mControlFilters.clear();
                this.mControlFilters.addAll(descriptor.getControlFilters());
                changes |= 1;
            }
            if (this.mPlaybackType != descriptor.getPlaybackType()) {
                this.mPlaybackType = descriptor.getPlaybackType();
                changes |= 1;
            }
            if (this.mPlaybackStream != descriptor.getPlaybackStream()) {
                this.mPlaybackStream = descriptor.getPlaybackStream();
                changes |= 1;
            }
            if (this.mDeviceType != descriptor.getDeviceType()) {
                this.mDeviceType = descriptor.getDeviceType();
                changes |= 1;
            }
            if (this.mVolumeHandling != descriptor.getVolumeHandling()) {
                this.mVolumeHandling = descriptor.getVolumeHandling();
                changes |= 3;
            }
            if (this.mVolume != descriptor.getVolume()) {
                this.mVolume = descriptor.getVolume();
                changes |= 3;
            }
            if (this.mVolumeMax != descriptor.getVolumeMax()) {
                this.mVolumeMax = descriptor.getVolumeMax();
                changes |= 3;
            }
            if (this.mPresentationDisplayId != descriptor.getPresentationDisplayId()) {
                this.mPresentationDisplayId = descriptor.getPresentationDisplayId();
                this.mPresentationDisplay = null;
                changes |= 5;
            }
            if (!MediaRouter.equal(this.mExtras, descriptor.getExtras())) {
                this.mExtras = descriptor.getExtras();
                changes |= 1;
            }
            if (!MediaRouter.equal(this.mSettingsIntent, descriptor.getSettingsActivity())) {
                this.mSettingsIntent = descriptor.getSettingsActivity();
                changes |= 1;
            }
            if (this.mCanDisconnect == descriptor.canDisconnectAndKeepPlaying()) {
                return changes;
            }
            this.mCanDisconnect = descriptor.canDisconnectAndKeepPlaying();
            return changes | 5;
        }

        /* access modifiers changed from: package-private */
        public String getDescriptorId() {
            return this.mDescriptorId;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public MediaRouteProvider getProviderInstance() {
            return this.mProvider.getProviderInstance();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class RouteGroup extends RouteInfo {
        private List<RouteInfo> mRoutes = new ArrayList();

        RouteGroup(ProviderInfo provider, String descriptorId, String uniqueId) {
            super(provider, descriptorId, uniqueId);
        }

        public int getRouteCount() {
            return this.mRoutes.size();
        }

        public RouteInfo getRouteAt(int index) {
            return this.mRoutes.get(index);
        }

        public List<RouteInfo> getRoutes() {
            return this.mRoutes;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(super.toString());
            sb.append('[');
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.mRoutes.get(i));
            }
            sb.append(']');
            return sb.toString();
        }

        /* access modifiers changed from: package-private */
        public int maybeUpdateDescriptor(MediaRouteDescriptor descriptor) {
            int i = 1;
            boolean changed = false;
            if (this.mDescriptor != descriptor) {
                this.mDescriptor = descriptor;
                if (descriptor != null) {
                    List<String> groupMemberIds = descriptor.getGroupMemberIds();
                    List<RouteInfo> routes = new ArrayList<>();
                    if (groupMemberIds.size() != this.mRoutes.size()) {
                        changed = true;
                    } else {
                        changed = false;
                    }
                    for (String groupMemberId : groupMemberIds) {
                        RouteInfo groupMember = MediaRouter.sGlobal.getRoute(MediaRouter.sGlobal.getUniqueId(getProvider(), groupMemberId));
                        if (groupMember != null) {
                            routes.add(groupMember);
                            if (!changed && !this.mRoutes.contains(groupMember)) {
                                changed = true;
                            }
                        }
                    }
                    if (changed) {
                        this.mRoutes = routes;
                    }
                }
            }
            if (!changed) {
                i = 0;
            }
            return i | super.updateDescriptor(descriptor);
        }
    }

    public static final class ProviderInfo {
        private MediaRouteProviderDescriptor mDescriptor;
        private final MediaRouteProvider.ProviderMetadata mMetadata;
        /* access modifiers changed from: private */
        public final MediaRouteProvider mProviderInstance;
        private Resources mResources;
        private boolean mResourcesNotAvailable;
        /* access modifiers changed from: private */
        public final List<RouteInfo> mRoutes = new ArrayList();

        ProviderInfo(MediaRouteProvider provider) {
            this.mProviderInstance = provider;
            this.mMetadata = provider.getMetadata();
        }

        public MediaRouteProvider getProviderInstance() {
            MediaRouter.checkCallingThread();
            return this.mProviderInstance;
        }

        public String getPackageName() {
            return this.mMetadata.getPackageName();
        }

        public ComponentName getComponentName() {
            return this.mMetadata.getComponentName();
        }

        public List<RouteInfo> getRoutes() {
            MediaRouter.checkCallingThread();
            return this.mRoutes;
        }

        /* access modifiers changed from: package-private */
        public Resources getResources() {
            if (this.mResources == null && !this.mResourcesNotAvailable) {
                String packageName = getPackageName();
                Context context = MediaRouter.sGlobal.getProviderContext(packageName);
                if (context != null) {
                    this.mResources = context.getResources();
                } else {
                    Log.w(MediaRouter.TAG, "Unable to obtain resources for route provider package: " + packageName);
                    this.mResourcesNotAvailable = true;
                }
            }
            return this.mResources;
        }

        /* access modifiers changed from: package-private */
        public boolean updateDescriptor(MediaRouteProviderDescriptor descriptor) {
            if (this.mDescriptor == descriptor) {
                return false;
            }
            this.mDescriptor = descriptor;
            return true;
        }

        /* access modifiers changed from: package-private */
        public int findRouteByDescriptorId(String id) {
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (this.mRoutes.get(i).mDescriptorId.equals(id)) {
                    return i;
                }
            }
            return -1;
        }

        public String toString() {
            return "MediaRouter.RouteProviderInfo{ packageName=" + getPackageName() + " }";
        }
    }

    public static abstract class Callback {
        public void onRouteSelected(MediaRouter router, RouteInfo route) {
        }

        public void onRouteUnselected(MediaRouter router, RouteInfo route) {
        }

        public void onRouteUnselected(MediaRouter router, RouteInfo route, int reason) {
            onRouteUnselected(router, route);
        }

        public void onRouteAdded(MediaRouter router, RouteInfo route) {
        }

        public void onRouteRemoved(MediaRouter router, RouteInfo route) {
        }

        public void onRouteChanged(MediaRouter router, RouteInfo route) {
        }

        public void onRouteVolumeChanged(MediaRouter router, RouteInfo route) {
        }

        public void onRoutePresentationDisplayChanged(MediaRouter router, RouteInfo route) {
        }

        public void onProviderAdded(MediaRouter router, ProviderInfo provider) {
        }

        public void onProviderRemoved(MediaRouter router, ProviderInfo provider) {
        }

        public void onProviderChanged(MediaRouter router, ProviderInfo provider) {
        }
    }

    public static abstract class ControlRequestCallback {
        public void onResult(Bundle data) {
        }

        public void onError(String error, Bundle data) {
        }
    }

    private static final class CallbackRecord {
        public final Callback mCallback;
        public int mFlags;
        public final MediaRouter mRouter;
        public MediaRouteSelector mSelector = MediaRouteSelector.EMPTY;

        public CallbackRecord(MediaRouter router, Callback callback) {
            this.mRouter = router;
            this.mCallback = callback;
        }

        public boolean filterRouteEvent(RouteInfo route) {
            return (this.mFlags & 2) != 0 || route.matchesSelector(this.mSelector);
        }
    }

    private static final class GlobalMediaRouter implements RegisteredMediaRouteProviderWatcher.Callback, SystemMediaRouteProvider.SyncCallback {
        final Context mApplicationContext;
        private RouteInfo mBluetoothRoute;
        final CallbackHandler mCallbackHandler = new CallbackHandler();
        private MediaSessionCompat mCompatSession;
        private RouteInfo mDefaultRoute;
        private MediaRouteDiscoveryRequest mDiscoveryRequest;
        private final DisplayManagerCompat mDisplayManager;
        private final boolean mLowRam;
        private MediaSessionRecord mMediaSession;
        final RemoteControlClientCompat.PlaybackInfo mPlaybackInfo = new RemoteControlClientCompat.PlaybackInfo();
        private final ProviderCallback mProviderCallback = new ProviderCallback();
        private final ArrayList<ProviderInfo> mProviders = new ArrayList<>();
        MediaSessionCompat mRccMediaSession;
        private RegisteredMediaRouteProviderWatcher mRegisteredProviderWatcher;
        private final ArrayList<RemoteControlClientRecord> mRemoteControlClients = new ArrayList<>();
        private final Map<String, MediaRouteProvider.RouteController> mRouteControllerMap = new HashMap();
        final ArrayList<WeakReference<MediaRouter>> mRouters = new ArrayList<>();
        private final ArrayList<RouteInfo> mRoutes = new ArrayList<>();
        RouteInfo mSelectedRoute;
        private MediaRouteProvider.RouteController mSelectedRouteController;
        private MediaSessionCompat.OnActiveChangeListener mSessionActiveListener = new MediaSessionCompat.OnActiveChangeListener() {
            public void onActiveChanged() {
                if (GlobalMediaRouter.this.mRccMediaSession == null) {
                    return;
                }
                if (GlobalMediaRouter.this.mRccMediaSession.isActive()) {
                    GlobalMediaRouter.this.addRemoteControlClient(GlobalMediaRouter.this.mRccMediaSession.getRemoteControlClient());
                } else {
                    GlobalMediaRouter.this.removeRemoteControlClient(GlobalMediaRouter.this.mRccMediaSession.getRemoteControlClient());
                }
            }
        };
        final SystemMediaRouteProvider mSystemProvider;
        private final Map<Pair<String, String>, String> mUniqueIdMap = new HashMap();

        GlobalMediaRouter(Context applicationContext) {
            this.mApplicationContext = applicationContext;
            this.mDisplayManager = DisplayManagerCompat.getInstance(applicationContext);
            this.mLowRam = ActivityManagerCompat.isLowRamDevice((ActivityManager) applicationContext.getSystemService("activity"));
            this.mSystemProvider = SystemMediaRouteProvider.obtain(applicationContext, this);
            addProvider(this.mSystemProvider);
        }

        public void start() {
            this.mRegisteredProviderWatcher = new RegisteredMediaRouteProviderWatcher(this.mApplicationContext, this);
            this.mRegisteredProviderWatcher.start();
        }

        public MediaRouter getRouter(Context context) {
            int i = this.mRouters.size();
            while (true) {
                i--;
                if (i >= 0) {
                    MediaRouter router = (MediaRouter) this.mRouters.get(i).get();
                    if (router == null) {
                        this.mRouters.remove(i);
                    } else if (router.mContext == context) {
                        MediaRouter mediaRouter = router;
                        return router;
                    }
                } else {
                    MediaRouter router2 = new MediaRouter(context);
                    this.mRouters.add(new WeakReference(router2));
                    MediaRouter mediaRouter2 = router2;
                    return router2;
                }
            }
        }

        public ContentResolver getContentResolver() {
            return this.mApplicationContext.getContentResolver();
        }

        public Context getProviderContext(String packageName) {
            if (packageName.equals(SystemMediaRouteProvider.PACKAGE_NAME)) {
                return this.mApplicationContext;
            }
            try {
                return this.mApplicationContext.createPackageContext(packageName, 4);
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        }

        public Display getDisplay(int displayId) {
            return this.mDisplayManager.getDisplay(displayId);
        }

        public void sendControlRequest(RouteInfo route, Intent intent, ControlRequestCallback callback) {
            if ((route != this.mSelectedRoute || this.mSelectedRouteController == null || !this.mSelectedRouteController.onControlRequest(intent, callback)) && callback != null) {
                callback.onError((String) null, (Bundle) null);
            }
        }

        public void requestSetVolume(RouteInfo route, int volume) {
            MediaRouteProvider.RouteController controller;
            if (route == this.mSelectedRoute && this.mSelectedRouteController != null) {
                this.mSelectedRouteController.onSetVolume(volume);
            } else if (!this.mRouteControllerMap.isEmpty() && (controller = this.mRouteControllerMap.get(route.mDescriptorId)) != null) {
                controller.onSetVolume(volume);
            }
        }

        public void requestUpdateVolume(RouteInfo route, int delta) {
            if (route == this.mSelectedRoute && this.mSelectedRouteController != null) {
                this.mSelectedRouteController.onUpdateVolume(delta);
            }
        }

        public RouteInfo getRoute(String uniqueId) {
            Iterator<RouteInfo> it = this.mRoutes.iterator();
            while (it.hasNext()) {
                RouteInfo info = it.next();
                if (info.mUniqueId.equals(uniqueId)) {
                    return info;
                }
            }
            return null;
        }

        public List<RouteInfo> getRoutes() {
            return this.mRoutes;
        }

        public List<ProviderInfo> getProviders() {
            return this.mProviders;
        }

        public RouteInfo getDefaultRoute() {
            if (this.mDefaultRoute != null) {
                return this.mDefaultRoute;
            }
            throw new IllegalStateException("There is no default route.  The media router has not yet been fully initialized.");
        }

        public RouteInfo getBluetoothRoute() {
            return this.mBluetoothRoute;
        }

        public RouteInfo getSelectedRoute() {
            if (this.mSelectedRoute != null) {
                return this.mSelectedRoute;
            }
            throw new IllegalStateException("There is no currently selected route.  The media router has not yet been fully initialized.");
        }

        public void selectRoute(RouteInfo route) {
            selectRoute(route, 3);
        }

        public void selectRoute(RouteInfo route, int unselectReason) {
            if (!this.mRoutes.contains(route)) {
                Log.w(MediaRouter.TAG, "Ignoring attempt to select removed route: " + route);
            } else if (!route.mEnabled) {
                Log.w(MediaRouter.TAG, "Ignoring attempt to select disabled route: " + route);
            } else {
                setSelectedRouteInternal(route, unselectReason);
            }
        }

        public boolean isRouteAvailable(MediaRouteSelector selector, int flags) {
            if (selector.isEmpty()) {
                return false;
            }
            if ((flags & 2) == 0 && this.mLowRam) {
                return true;
            }
            int routeCount = this.mRoutes.size();
            for (int i = 0; i < routeCount; i++) {
                RouteInfo route = this.mRoutes.get(i);
                if (((flags & 1) == 0 || !route.isDefaultOrBluetooth()) && route.matchesSelector(selector)) {
                    return true;
                }
            }
            return false;
        }

        public void updateDiscoveryRequest() {
            boolean discover = false;
            boolean activeScan = false;
            MediaRouteSelector.Builder builder = new MediaRouteSelector.Builder();
            int i = this.mRouters.size();
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                MediaRouter router = (MediaRouter) this.mRouters.get(i).get();
                if (router == null) {
                    this.mRouters.remove(i);
                } else {
                    int count = router.mCallbackRecords.size();
                    for (int j = 0; j < count; j++) {
                        CallbackRecord callback = router.mCallbackRecords.get(j);
                        builder.addSelector(callback.mSelector);
                        if ((callback.mFlags & 1) != 0) {
                            activeScan = true;
                            discover = true;
                        }
                        if ((callback.mFlags & 4) != 0 && !this.mLowRam) {
                            discover = true;
                        }
                        if ((callback.mFlags & 8) != 0) {
                            discover = true;
                        }
                    }
                }
            }
            MediaRouteSelector selector = discover ? builder.build() : MediaRouteSelector.EMPTY;
            if (this.mDiscoveryRequest == null || !this.mDiscoveryRequest.getSelector().equals(selector) || this.mDiscoveryRequest.isActiveScan() != activeScan) {
                if (!selector.isEmpty() || activeScan) {
                    this.mDiscoveryRequest = new MediaRouteDiscoveryRequest(selector, activeScan);
                } else if (this.mDiscoveryRequest != null) {
                    this.mDiscoveryRequest = null;
                } else {
                    return;
                }
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Updated discovery request: " + this.mDiscoveryRequest);
                }
                if (discover && !activeScan && this.mLowRam) {
                    Log.i(MediaRouter.TAG, "Forcing passive route discovery on a low-RAM device, system performance may be affected.  Please consider using CALLBACK_FLAG_REQUEST_DISCOVERY instead of CALLBACK_FLAG_FORCE_DISCOVERY.");
                }
                int providerCount = this.mProviders.size();
                for (int i2 = 0; i2 < providerCount; i2++) {
                    this.mProviders.get(i2).mProviderInstance.setDiscoveryRequest(this.mDiscoveryRequest);
                }
            }
        }

        public void addProvider(MediaRouteProvider providerInstance) {
            if (findProviderInfo(providerInstance) < 0) {
                ProviderInfo provider = new ProviderInfo(providerInstance);
                this.mProviders.add(provider);
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Provider added: " + provider);
                }
                this.mCallbackHandler.post(513, provider);
                updateProviderContents(provider, providerInstance.getDescriptor());
                providerInstance.setCallback(this.mProviderCallback);
                providerInstance.setDiscoveryRequest(this.mDiscoveryRequest);
            }
        }

        public void removeProvider(MediaRouteProvider providerInstance) {
            int index = findProviderInfo(providerInstance);
            if (index >= 0) {
                providerInstance.setCallback((MediaRouteProvider.Callback) null);
                providerInstance.setDiscoveryRequest((MediaRouteDiscoveryRequest) null);
                ProviderInfo provider = this.mProviders.get(index);
                updateProviderContents(provider, (MediaRouteProviderDescriptor) null);
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Provider removed: " + provider);
                }
                this.mCallbackHandler.post(CallbackHandler.MSG_PROVIDER_REMOVED, provider);
                this.mProviders.remove(index);
            }
        }

        /* access modifiers changed from: package-private */
        public void updateProviderDescriptor(MediaRouteProvider providerInstance, MediaRouteProviderDescriptor descriptor) {
            int index = findProviderInfo(providerInstance);
            if (index >= 0) {
                updateProviderContents(this.mProviders.get(index), descriptor);
            }
        }

        private int findProviderInfo(MediaRouteProvider providerInstance) {
            int count = this.mProviders.size();
            for (int i = 0; i < count; i++) {
                if (this.mProviders.get(i).mProviderInstance == providerInstance) {
                    return i;
                }
            }
            return -1;
        }

        private void updateProviderContents(ProviderInfo provider, MediaRouteProviderDescriptor providerDescriptor) {
            int targetIndex;
            if (provider.updateDescriptor(providerDescriptor)) {
                int targetIndex2 = 0;
                boolean selectedRouteDescriptorChanged = false;
                if (providerDescriptor != null) {
                    if (providerDescriptor.isValid()) {
                        List<MediaRouteDescriptor> routeDescriptors = providerDescriptor.getRoutes();
                        int routeCount = routeDescriptors.size();
                        List<Pair<RouteInfo, MediaRouteDescriptor>> addedGroups = new ArrayList<>();
                        List<Pair<RouteInfo, MediaRouteDescriptor>> updatedGroups = new ArrayList<>();
                        int i = 0;
                        int targetIndex3 = 0;
                        while (i < routeCount) {
                            MediaRouteDescriptor routeDescriptor = routeDescriptors.get(i);
                            String id = routeDescriptor.getId();
                            int sourceIndex = provider.findRouteByDescriptorId(id);
                            if (sourceIndex < 0) {
                                String uniqueId = assignRouteUniqueId(provider, id);
                                boolean isGroup = routeDescriptor.getGroupMemberIds() != null;
                                RouteInfo route = isGroup ? new RouteGroup(provider, id, uniqueId) : new RouteInfo(provider, id, uniqueId);
                                targetIndex = targetIndex3 + 1;
                                provider.mRoutes.add(targetIndex3, route);
                                this.mRoutes.add(route);
                                if (isGroup) {
                                    addedGroups.add(new Pair(route, routeDescriptor));
                                } else {
                                    route.maybeUpdateDescriptor(routeDescriptor);
                                    if (MediaRouter.DEBUG) {
                                        Log.d(MediaRouter.TAG, "Route added: " + route);
                                    }
                                    this.mCallbackHandler.post(257, route);
                                }
                            } else if (sourceIndex < targetIndex3) {
                                Log.w(MediaRouter.TAG, "Ignoring route descriptor with duplicate id: " + routeDescriptor);
                                targetIndex = targetIndex3;
                            } else {
                                RouteInfo route2 = (RouteInfo) provider.mRoutes.get(sourceIndex);
                                targetIndex = targetIndex3 + 1;
                                Collections.swap(provider.mRoutes, sourceIndex, targetIndex3);
                                if (route2 instanceof RouteGroup) {
                                    updatedGroups.add(new Pair(route2, routeDescriptor));
                                } else if (updateRouteDescriptorAndNotify(route2, routeDescriptor) != 0 && route2 == this.mSelectedRoute) {
                                    selectedRouteDescriptorChanged = true;
                                }
                            }
                            i++;
                            targetIndex3 = targetIndex;
                        }
                        for (Pair<RouteInfo, MediaRouteDescriptor> pair : addedGroups) {
                            RouteInfo route3 = (RouteInfo) pair.first;
                            route3.maybeUpdateDescriptor((MediaRouteDescriptor) pair.second);
                            if (MediaRouter.DEBUG) {
                                Log.d(MediaRouter.TAG, "Route added: " + route3);
                            }
                            this.mCallbackHandler.post(257, route3);
                        }
                        for (Pair<RouteInfo, MediaRouteDescriptor> pair2 : updatedGroups) {
                            RouteInfo route4 = (RouteInfo) pair2.first;
                            if (updateRouteDescriptorAndNotify(route4, (MediaRouteDescriptor) pair2.second) != 0 && route4 == this.mSelectedRoute) {
                                selectedRouteDescriptorChanged = true;
                            }
                        }
                        targetIndex2 = targetIndex3;
                    } else {
                        Log.w(MediaRouter.TAG, "Ignoring invalid provider descriptor: " + providerDescriptor);
                    }
                }
                for (int i2 = provider.mRoutes.size() - 1; i2 >= targetIndex2; i2--) {
                    RouteInfo route5 = (RouteInfo) provider.mRoutes.get(i2);
                    route5.maybeUpdateDescriptor((MediaRouteDescriptor) null);
                    this.mRoutes.remove(route5);
                }
                updateSelectedRouteIfNeeded(selectedRouteDescriptorChanged);
                for (int i3 = provider.mRoutes.size() - 1; i3 >= targetIndex2; i3--) {
                    RouteInfo route6 = (RouteInfo) provider.mRoutes.remove(i3);
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route removed: " + route6);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_REMOVED, route6);
                }
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Provider changed: " + provider);
                }
                this.mCallbackHandler.post(CallbackHandler.MSG_PROVIDER_CHANGED, provider);
            }
        }

        private int updateRouteDescriptorAndNotify(RouteInfo route, MediaRouteDescriptor routeDescriptor) {
            int changes = route.maybeUpdateDescriptor(routeDescriptor);
            if (changes != 0) {
                if ((changes & 1) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route changed: " + route);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_CHANGED, route);
                }
                if ((changes & 2) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route volume changed: " + route);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_VOLUME_CHANGED, route);
                }
                if ((changes & 4) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route presentation display changed: " + route);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED, route);
                }
            }
            return changes;
        }

        private String assignRouteUniqueId(ProviderInfo provider, String routeDescriptorId) {
            String componentName = provider.getComponentName().flattenToShortString();
            String uniqueId = componentName + ":" + routeDescriptorId;
            if (findRouteByUniqueId(uniqueId) < 0) {
                this.mUniqueIdMap.put(new Pair(componentName, routeDescriptorId), uniqueId);
                return uniqueId;
            }
            Log.w(MediaRouter.TAG, "Either " + routeDescriptorId + " isn't unique in " + componentName + " or we're trying to assign a unique ID for an already added route");
            int i = 2;
            while (true) {
                String newUniqueId = String.format(Locale.US, "%s_%d", new Object[]{uniqueId, Integer.valueOf(i)});
                if (findRouteByUniqueId(newUniqueId) < 0) {
                    this.mUniqueIdMap.put(new Pair(componentName, routeDescriptorId), newUniqueId);
                    return newUniqueId;
                }
                i++;
            }
        }

        private int findRouteByUniqueId(String uniqueId) {
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (this.mRoutes.get(i).mUniqueId.equals(uniqueId)) {
                    return i;
                }
            }
            return -1;
        }

        /* access modifiers changed from: private */
        public String getUniqueId(ProviderInfo provider, String routeDescriptorId) {
            return this.mUniqueIdMap.get(new Pair(provider.getComponentName().flattenToShortString(), routeDescriptorId));
        }

        /* access modifiers changed from: private */
        public void updateSelectedRouteIfNeeded(boolean selectedRouteDescriptorChanged) {
            if (this.mDefaultRoute != null && !isRouteSelectable(this.mDefaultRoute)) {
                Log.i(MediaRouter.TAG, "Clearing the default route because it is no longer selectable: " + this.mDefaultRoute);
                this.mDefaultRoute = null;
            }
            if (this.mDefaultRoute == null && !this.mRoutes.isEmpty()) {
                Iterator<RouteInfo> it = this.mRoutes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RouteInfo route = it.next();
                    if (isSystemDefaultRoute(route) && isRouteSelectable(route)) {
                        this.mDefaultRoute = route;
                        Log.i(MediaRouter.TAG, "Found default route: " + this.mDefaultRoute);
                        break;
                    }
                }
            }
            if (this.mBluetoothRoute != null && !isRouteSelectable(this.mBluetoothRoute)) {
                Log.i(MediaRouter.TAG, "Clearing the bluetooth route because it is no longer selectable: " + this.mBluetoothRoute);
                this.mBluetoothRoute = null;
            }
            if (this.mBluetoothRoute == null && !this.mRoutes.isEmpty()) {
                Iterator<RouteInfo> it2 = this.mRoutes.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    RouteInfo route2 = it2.next();
                    if (isSystemLiveAudioOnlyRoute(route2) && isRouteSelectable(route2)) {
                        this.mBluetoothRoute = route2;
                        Log.i(MediaRouter.TAG, "Found bluetooth route: " + this.mBluetoothRoute);
                        break;
                    }
                }
            }
            if (this.mSelectedRoute != null && !isRouteSelectable(this.mSelectedRoute)) {
                Log.i(MediaRouter.TAG, "Unselecting the current route because it is no longer selectable: " + this.mSelectedRoute);
                setSelectedRouteInternal((RouteInfo) null, 0);
            }
            if (this.mSelectedRoute == null) {
                setSelectedRouteInternal(chooseFallbackRoute(), 0);
            } else if (selectedRouteDescriptorChanged) {
                if (this.mSelectedRoute instanceof RouteGroup) {
                    List<RouteInfo> routes = ((RouteGroup) this.mSelectedRoute).getRoutes();
                    Set idSet = new HashSet();
                    for (RouteInfo route3 : routes) {
                        idSet.add(route3.mDescriptorId);
                    }
                    Iterator<Map.Entry<String, MediaRouteProvider.RouteController>> iter = this.mRouteControllerMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry<String, MediaRouteProvider.RouteController> entry = iter.next();
                        if (!idSet.contains(entry.getKey())) {
                            MediaRouteProvider.RouteController controller = entry.getValue();
                            controller.onUnselect();
                            controller.onRelease();
                            iter.remove();
                        }
                    }
                    for (RouteInfo route4 : routes) {
                        if (!this.mRouteControllerMap.containsKey(route4.mDescriptorId)) {
                            MediaRouteProvider.RouteController controller2 = route4.getProviderInstance().onCreateRouteController(route4.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                            controller2.onSelect();
                            this.mRouteControllerMap.put(route4.mDescriptorId, controller2);
                        }
                    }
                }
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        /* access modifiers changed from: package-private */
        public RouteInfo chooseFallbackRoute() {
            Iterator<RouteInfo> it = this.mRoutes.iterator();
            while (it.hasNext()) {
                RouteInfo route = it.next();
                if (route != this.mDefaultRoute && isSystemLiveAudioOnlyRoute(route) && isRouteSelectable(route)) {
                    return route;
                }
            }
            return this.mDefaultRoute;
        }

        private boolean isSystemLiveAudioOnlyRoute(RouteInfo route) {
            return route.getProviderInstance() == this.mSystemProvider && route.supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO) && !route.supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
        }

        private boolean isRouteSelectable(RouteInfo route) {
            return route.mDescriptor != null && route.mEnabled;
        }

        private boolean isSystemDefaultRoute(RouteInfo route) {
            return route.getProviderInstance() == this.mSystemProvider && route.mDescriptorId.equals(SystemMediaRouteProvider.DEFAULT_ROUTE_ID);
        }

        private void setSelectedRouteInternal(RouteInfo route, int unselectReason) {
            if (this.mSelectedRoute != route) {
                if (this.mSelectedRoute != null) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route unselected: " + this.mSelectedRoute + " reason: " + unselectReason);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_UNSELECTED, this.mSelectedRoute, unselectReason);
                    if (this.mSelectedRouteController != null) {
                        this.mSelectedRouteController.onUnselect(unselectReason);
                        this.mSelectedRouteController.onRelease();
                        this.mSelectedRouteController = null;
                    }
                    if (!this.mRouteControllerMap.isEmpty()) {
                        for (MediaRouteProvider.RouteController controller : this.mRouteControllerMap.values()) {
                            controller.onUnselect(unselectReason);
                            controller.onRelease();
                        }
                        this.mRouteControllerMap.clear();
                    }
                }
                this.mSelectedRoute = route;
                if (this.mSelectedRoute != null) {
                    this.mSelectedRouteController = route.getProviderInstance().onCreateRouteController(route.mDescriptorId);
                    if (this.mSelectedRouteController != null) {
                        this.mSelectedRouteController.onSelect();
                    }
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route selected: " + this.mSelectedRoute);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_SELECTED, this.mSelectedRoute);
                    if (this.mSelectedRoute instanceof RouteGroup) {
                        List<RouteInfo> routes = ((RouteGroup) this.mSelectedRoute).getRoutes();
                        this.mRouteControllerMap.clear();
                        for (RouteInfo r : routes) {
                            MediaRouteProvider.RouteController controller2 = r.getProviderInstance().onCreateRouteController(r.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                            controller2.onSelect();
                            this.mRouteControllerMap.put(r.mDescriptorId, controller2);
                        }
                    }
                }
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
            r0 = r4.mProviders.get(r1);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.support.v7.media.MediaRouter.RouteInfo getSystemRouteByDescriptorId(java.lang.String r5) {
            /*
                r4 = this;
                android.support.v7.media.SystemMediaRouteProvider r3 = r4.mSystemProvider
                int r1 = r4.findProviderInfo(r3)
                if (r1 < 0) goto L_0x0021
                java.util.ArrayList<android.support.v7.media.MediaRouter$ProviderInfo> r3 = r4.mProviders
                java.lang.Object r0 = r3.get(r1)
                android.support.v7.media.MediaRouter$ProviderInfo r0 = (android.support.v7.media.MediaRouter.ProviderInfo) r0
                int r2 = r0.findRouteByDescriptorId(r5)
                if (r2 < 0) goto L_0x0021
                java.util.List r3 = r0.mRoutes
                java.lang.Object r3 = r3.get(r2)
                android.support.v7.media.MediaRouter$RouteInfo r3 = (android.support.v7.media.MediaRouter.RouteInfo) r3
            L_0x0020:
                return r3
            L_0x0021:
                r3 = 0
                goto L_0x0020
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.media.MediaRouter.GlobalMediaRouter.getSystemRouteByDescriptorId(java.lang.String):android.support.v7.media.MediaRouter$RouteInfo");
        }

        public void addRemoteControlClient(Object rcc) {
            if (findRemoteControlClientRecord(rcc) < 0) {
                this.mRemoteControlClients.add(new RemoteControlClientRecord(rcc));
            }
        }

        public void removeRemoteControlClient(Object rcc) {
            int index = findRemoteControlClientRecord(rcc);
            if (index >= 0) {
                this.mRemoteControlClients.remove(index).disconnect();
            }
        }

        public void setMediaSession(Object session) {
            setMediaSessionRecord(session != null ? new MediaSessionRecord(session) : null);
        }

        public void setMediaSessionCompat(MediaSessionCompat session) {
            MediaSessionRecord mediaSessionRecord;
            this.mCompatSession = session;
            if (Build.VERSION.SDK_INT >= 21) {
                if (session != null) {
                    mediaSessionRecord = new MediaSessionRecord(session);
                } else {
                    mediaSessionRecord = null;
                }
                setMediaSessionRecord(mediaSessionRecord);
            } else if (Build.VERSION.SDK_INT >= 14) {
                if (this.mRccMediaSession != null) {
                    removeRemoteControlClient(this.mRccMediaSession.getRemoteControlClient());
                    this.mRccMediaSession.removeOnActiveChangeListener(this.mSessionActiveListener);
                }
                this.mRccMediaSession = session;
                if (session != null) {
                    session.addOnActiveChangeListener(this.mSessionActiveListener);
                    if (session.isActive()) {
                        addRemoteControlClient(session.getRemoteControlClient());
                    }
                }
            }
        }

        private void setMediaSessionRecord(MediaSessionRecord mediaSessionRecord) {
            if (this.mMediaSession != null) {
                this.mMediaSession.clearVolumeHandling();
            }
            this.mMediaSession = mediaSessionRecord;
            if (mediaSessionRecord != null) {
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        public MediaSessionCompat.Token getMediaSessionToken() {
            if (this.mMediaSession != null) {
                return this.mMediaSession.getToken();
            }
            if (this.mCompatSession != null) {
                return this.mCompatSession.getSessionToken();
            }
            return null;
        }

        private int findRemoteControlClientRecord(Object rcc) {
            int count = this.mRemoteControlClients.size();
            for (int i = 0; i < count; i++) {
                if (this.mRemoteControlClients.get(i).getRemoteControlClient() == rcc) {
                    return i;
                }
            }
            return -1;
        }

        private void updatePlaybackInfoFromSelectedRoute() {
            if (this.mSelectedRoute != null) {
                this.mPlaybackInfo.volume = this.mSelectedRoute.getVolume();
                this.mPlaybackInfo.volumeMax = this.mSelectedRoute.getVolumeMax();
                this.mPlaybackInfo.volumeHandling = this.mSelectedRoute.getVolumeHandling();
                this.mPlaybackInfo.playbackStream = this.mSelectedRoute.getPlaybackStream();
                this.mPlaybackInfo.playbackType = this.mSelectedRoute.getPlaybackType();
                int count = this.mRemoteControlClients.size();
                for (int i = 0; i < count; i++) {
                    this.mRemoteControlClients.get(i).updatePlaybackInfo();
                }
                if (this.mMediaSession == null) {
                    return;
                }
                if (this.mSelectedRoute == getDefaultRoute()) {
                    this.mMediaSession.clearVolumeHandling();
                    return;
                }
                int controlType = 0;
                if (this.mPlaybackInfo.volumeHandling == 1) {
                    controlType = 2;
                }
                this.mMediaSession.configureVolume(controlType, this.mPlaybackInfo.volumeMax, this.mPlaybackInfo.volume);
            } else if (this.mMediaSession != null) {
                this.mMediaSession.clearVolumeHandling();
            }
        }

        private final class ProviderCallback extends MediaRouteProvider.Callback {
            ProviderCallback() {
            }

            public void onDescriptorChanged(MediaRouteProvider provider, MediaRouteProviderDescriptor descriptor) {
                GlobalMediaRouter.this.updateProviderDescriptor(provider, descriptor);
            }
        }

        private final class MediaSessionRecord {
            private int mControlType;
            private int mMaxVolume;
            private final MediaSessionCompat mMsCompat;
            private VolumeProviderCompat mVpCompat;

            public MediaSessionRecord(Object mediaSession) {
                this.mMsCompat = MediaSessionCompat.fromMediaSession(GlobalMediaRouter.this.mApplicationContext, mediaSession);
            }

            public MediaSessionRecord(MediaSessionCompat mediaSessionCompat) {
                this.mMsCompat = mediaSessionCompat;
            }

            public void configureVolume(int controlType, int max, int current) {
                if (this.mVpCompat != null && controlType == this.mControlType && max == this.mMaxVolume) {
                    this.mVpCompat.setCurrentVolume(current);
                    return;
                }
                this.mVpCompat = new VolumeProviderCompat(controlType, max, current) {
                    public void onSetVolumeTo(final int volume) {
                        GlobalMediaRouter.this.mCallbackHandler.post(new Runnable() {
                            public void run() {
                                if (GlobalMediaRouter.this.mSelectedRoute != null) {
                                    GlobalMediaRouter.this.mSelectedRoute.requestSetVolume(volume);
                                }
                            }
                        });
                    }

                    public void onAdjustVolume(final int direction) {
                        GlobalMediaRouter.this.mCallbackHandler.post(new Runnable() {
                            public void run() {
                                if (GlobalMediaRouter.this.mSelectedRoute != null) {
                                    GlobalMediaRouter.this.mSelectedRoute.requestUpdateVolume(direction);
                                }
                            }
                        });
                    }
                };
                this.mMsCompat.setPlaybackToRemote(this.mVpCompat);
            }

            public void clearVolumeHandling() {
                this.mMsCompat.setPlaybackToLocal(GlobalMediaRouter.this.mPlaybackInfo.playbackStream);
                this.mVpCompat = null;
            }

            public MediaSessionCompat.Token getToken() {
                return this.mMsCompat.getSessionToken();
            }
        }

        private final class RemoteControlClientRecord implements RemoteControlClientCompat.VolumeCallback {
            private boolean mDisconnected;
            private final RemoteControlClientCompat mRccCompat;

            public RemoteControlClientRecord(Object rcc) {
                this.mRccCompat = RemoteControlClientCompat.obtain(GlobalMediaRouter.this.mApplicationContext, rcc);
                this.mRccCompat.setVolumeCallback(this);
                updatePlaybackInfo();
            }

            public Object getRemoteControlClient() {
                return this.mRccCompat.getRemoteControlClient();
            }

            public void disconnect() {
                this.mDisconnected = true;
                this.mRccCompat.setVolumeCallback((RemoteControlClientCompat.VolumeCallback) null);
            }

            public void updatePlaybackInfo() {
                this.mRccCompat.setPlaybackInfo(GlobalMediaRouter.this.mPlaybackInfo);
            }

            public void onVolumeSetRequest(int volume) {
                if (!this.mDisconnected && GlobalMediaRouter.this.mSelectedRoute != null) {
                    GlobalMediaRouter.this.mSelectedRoute.requestSetVolume(volume);
                }
            }

            public void onVolumeUpdateRequest(int direction) {
                if (!this.mDisconnected && GlobalMediaRouter.this.mSelectedRoute != null) {
                    GlobalMediaRouter.this.mSelectedRoute.requestUpdateVolume(direction);
                }
            }
        }

        private final class CallbackHandler extends Handler {
            public static final int MSG_PROVIDER_ADDED = 513;
            public static final int MSG_PROVIDER_CHANGED = 515;
            public static final int MSG_PROVIDER_REMOVED = 514;
            public static final int MSG_ROUTE_ADDED = 257;
            public static final int MSG_ROUTE_CHANGED = 259;
            public static final int MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED = 261;
            public static final int MSG_ROUTE_REMOVED = 258;
            public static final int MSG_ROUTE_SELECTED = 262;
            public static final int MSG_ROUTE_UNSELECTED = 263;
            public static final int MSG_ROUTE_VOLUME_CHANGED = 260;
            private static final int MSG_TYPE_MASK = 65280;
            private static final int MSG_TYPE_PROVIDER = 512;
            private static final int MSG_TYPE_ROUTE = 256;
            private final ArrayList<CallbackRecord> mTempCallbackRecords = new ArrayList<>();

            CallbackHandler() {
            }

            public void post(int msg, Object obj) {
                obtainMessage(msg, obj).sendToTarget();
            }

            public void post(int msg, Object obj, int arg) {
                Message message = obtainMessage(msg, obj);
                message.arg1 = arg;
                message.sendToTarget();
            }

            public void handleMessage(Message msg) {
                int what = msg.what;
                Object obj = msg.obj;
                int arg = msg.arg1;
                if (what == 259 && GlobalMediaRouter.this.getSelectedRoute().getId().equals(((RouteInfo) obj).getId())) {
                    GlobalMediaRouter.this.updateSelectedRouteIfNeeded(true);
                }
                syncWithSystemProvider(what, obj);
                try {
                    int i = GlobalMediaRouter.this.mRouters.size();
                    while (true) {
                        i--;
                        if (i < 0) {
                            break;
                        }
                        MediaRouter router = (MediaRouter) GlobalMediaRouter.this.mRouters.get(i).get();
                        if (router == null) {
                            GlobalMediaRouter.this.mRouters.remove(i);
                        } else {
                            this.mTempCallbackRecords.addAll(router.mCallbackRecords);
                        }
                    }
                    int callbackCount = this.mTempCallbackRecords.size();
                    for (int i2 = 0; i2 < callbackCount; i2++) {
                        invokeCallback(this.mTempCallbackRecords.get(i2), what, obj, arg);
                    }
                } finally {
                    this.mTempCallbackRecords.clear();
                }
            }

            private void syncWithSystemProvider(int what, Object obj) {
                switch (what) {
                    case 257:
                        GlobalMediaRouter.this.mSystemProvider.onSyncRouteAdded((RouteInfo) obj);
                        return;
                    case MSG_ROUTE_REMOVED /*258*/:
                        GlobalMediaRouter.this.mSystemProvider.onSyncRouteRemoved((RouteInfo) obj);
                        return;
                    case MSG_ROUTE_CHANGED /*259*/:
                        GlobalMediaRouter.this.mSystemProvider.onSyncRouteChanged((RouteInfo) obj);
                        return;
                    case MSG_ROUTE_SELECTED /*262*/:
                        GlobalMediaRouter.this.mSystemProvider.onSyncRouteSelected((RouteInfo) obj);
                        return;
                    default:
                        return;
                }
            }

            private void invokeCallback(CallbackRecord record, int what, Object obj, int arg) {
                MediaRouter router = record.mRouter;
                Callback callback = record.mCallback;
                switch (65280 & what) {
                    case 256:
                        RouteInfo route = (RouteInfo) obj;
                        if (record.filterRouteEvent(route)) {
                            switch (what) {
                                case 257:
                                    callback.onRouteAdded(router, route);
                                    return;
                                case MSG_ROUTE_REMOVED /*258*/:
                                    callback.onRouteRemoved(router, route);
                                    return;
                                case MSG_ROUTE_CHANGED /*259*/:
                                    callback.onRouteChanged(router, route);
                                    return;
                                case MSG_ROUTE_VOLUME_CHANGED /*260*/:
                                    callback.onRouteVolumeChanged(router, route);
                                    return;
                                case MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /*261*/:
                                    callback.onRoutePresentationDisplayChanged(router, route);
                                    return;
                                case MSG_ROUTE_SELECTED /*262*/:
                                    callback.onRouteSelected(router, route);
                                    return;
                                case MSG_ROUTE_UNSELECTED /*263*/:
                                    callback.onRouteUnselected(router, route, arg);
                                    return;
                                default:
                                    return;
                            }
                        } else {
                            return;
                        }
                    case 512:
                        ProviderInfo provider = (ProviderInfo) obj;
                        switch (what) {
                            case 513:
                                callback.onProviderAdded(router, provider);
                                return;
                            case MSG_PROVIDER_REMOVED /*514*/:
                                callback.onProviderRemoved(router, provider);
                                return;
                            case MSG_PROVIDER_CHANGED /*515*/:
                                callback.onProviderChanged(router, provider);
                                return;
                            default:
                                return;
                        }
                    default:
                        return;
                }
            }
        }
    }
}
