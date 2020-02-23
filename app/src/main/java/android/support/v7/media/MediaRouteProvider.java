package android.support.v7.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v7.media.MediaRouter;

public abstract class MediaRouteProvider {
    static final int MSG_DELIVER_DESCRIPTOR_CHANGED = 1;
    static final int MSG_DELIVER_DISCOVERY_REQUEST_CHANGED = 2;
    private Callback mCallback;
    private final Context mContext;
    private MediaRouteProviderDescriptor mDescriptor;
    private MediaRouteDiscoveryRequest mDiscoveryRequest;
    private final ProviderHandler mHandler;
    private final ProviderMetadata mMetadata;
    private boolean mPendingDescriptorChange;
    private boolean mPendingDiscoveryRequestChange;

    public MediaRouteProvider(@NonNull Context context) {
        this(context, (ProviderMetadata) null);
    }

    MediaRouteProvider(Context context, ProviderMetadata metadata) {
        this.mHandler = new ProviderHandler();
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        this.mContext = context;
        if (metadata == null) {
            this.mMetadata = new ProviderMetadata(new ComponentName(context, getClass()));
        } else {
            this.mMetadata = metadata;
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Handler getHandler() {
        return this.mHandler;
    }

    public final ProviderMetadata getMetadata() {
        return this.mMetadata;
    }

    public final void setCallback(@Nullable Callback callback) {
        MediaRouter.checkCallingThread();
        this.mCallback = callback;
    }

    @Nullable
    public final MediaRouteDiscoveryRequest getDiscoveryRequest() {
        return this.mDiscoveryRequest;
    }

    public final void setDiscoveryRequest(MediaRouteDiscoveryRequest request) {
        MediaRouter.checkCallingThread();
        if (this.mDiscoveryRequest == request) {
            return;
        }
        if (this.mDiscoveryRequest == null || !this.mDiscoveryRequest.equals(request)) {
            this.mDiscoveryRequest = request;
            if (!this.mPendingDiscoveryRequestChange) {
                this.mPendingDiscoveryRequestChange = true;
                this.mHandler.sendEmptyMessage(2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void deliverDiscoveryRequestChanged() {
        this.mPendingDiscoveryRequestChange = false;
        onDiscoveryRequestChanged(this.mDiscoveryRequest);
    }

    public void onDiscoveryRequestChanged(@Nullable MediaRouteDiscoveryRequest request) {
    }

    @Nullable
    public final MediaRouteProviderDescriptor getDescriptor() {
        return this.mDescriptor;
    }

    public final void setDescriptor(@Nullable MediaRouteProviderDescriptor descriptor) {
        MediaRouter.checkCallingThread();
        if (this.mDescriptor != descriptor) {
            this.mDescriptor = descriptor;
            if (!this.mPendingDescriptorChange) {
                this.mPendingDescriptorChange = true;
                this.mHandler.sendEmptyMessage(1);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void deliverDescriptorChanged() {
        this.mPendingDescriptorChange = false;
        if (this.mCallback != null) {
            this.mCallback.onDescriptorChanged(this, this.mDescriptor);
        }
    }

    @Nullable
    public RouteController onCreateRouteController(@NonNull String routeId) {
        if (routeId != null) {
            return null;
        }
        throw new IllegalArgumentException("routeId cannot be null");
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public RouteController onCreateRouteController(@NonNull String routeId, @NonNull String routeGroupId) {
        if (routeId == null) {
            throw new IllegalArgumentException("routeId cannot be null");
        } else if (routeGroupId != null) {
            return onCreateRouteController(routeId);
        } else {
            throw new IllegalArgumentException("routeGroupId cannot be null");
        }
    }

    public static final class ProviderMetadata {
        private final ComponentName mComponentName;

        ProviderMetadata(ComponentName componentName) {
            if (componentName == null) {
                throw new IllegalArgumentException("componentName must not be null");
            }
            this.mComponentName = componentName;
        }

        public String getPackageName() {
            return this.mComponentName.getPackageName();
        }

        public ComponentName getComponentName() {
            return this.mComponentName;
        }

        public String toString() {
            return "ProviderMetadata{ componentName=" + this.mComponentName.flattenToShortString() + " }";
        }
    }

    public static abstract class RouteController {
        public void onRelease() {
        }

        public void onSelect() {
        }

        public void onUnselect() {
        }

        public void onUnselect(int reason) {
            onUnselect();
        }

        public void onSetVolume(int volume) {
        }

        public void onUpdateVolume(int delta) {
        }

        public boolean onControlRequest(Intent intent, @Nullable MediaRouter.ControlRequestCallback callback) {
            return false;
        }
    }

    public static abstract class Callback {
        public void onDescriptorChanged(@NonNull MediaRouteProvider provider, @Nullable MediaRouteProviderDescriptor descriptor) {
        }
    }

    private final class ProviderHandler extends Handler {
        ProviderHandler() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MediaRouteProvider.this.deliverDescriptorChanged();
                    return;
                case 2:
                    MediaRouteProvider.this.deliverDiscoveryRequestChanged();
                    return;
                default:
                    return;
            }
        }
    }
}
