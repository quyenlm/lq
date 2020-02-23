package android.support.v7.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.mediarouter.R;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MediaRouteChooserDialog extends AppCompatDialog {
    static final int MSG_UPDATE_ROUTES = 1;
    static final String TAG = "MediaRouteChooserDialog";
    private static final long UPDATE_ROUTES_DELAY_MS = 300;
    private RouteAdapter mAdapter;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private final Handler mHandler;
    private long mLastUpdateTime;
    private ListView mListView;
    private final MediaRouter mRouter;
    private ArrayList<MediaRouter.RouteInfo> mRoutes;
    private MediaRouteSelector mSelector;
    private TextView mTitleView;

    public MediaRouteChooserDialog(Context context) {
        this(context, 0);
    }

    public MediaRouteChooserDialog(Context context, int theme) {
        super(MediaRouterThemeHelper.createThemedContext(context, theme), theme);
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mHandler = new Handler() {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        MediaRouteChooserDialog.this.updateRoutes((List) message.obj);
                        return;
                    default:
                        return;
                }
            }
        };
        this.mRouter = MediaRouter.getInstance(getContext());
        this.mCallback = new MediaRouterCallback();
    }

    @NonNull
    public MediaRouteSelector getRouteSelector() {
        return this.mSelector;
    }

    public void setRouteSelector(@NonNull MediaRouteSelector selector) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (!this.mSelector.equals(selector)) {
            this.mSelector = selector;
            if (this.mAttachedToWindow) {
                this.mRouter.removeCallback(this.mCallback);
                this.mRouter.addCallback(selector, this.mCallback, 1);
            }
            refreshRoutes();
        }
    }

    public void onFilterRoutes(@NonNull List<MediaRouter.RouteInfo> routes) {
        int i = routes.size();
        while (true) {
            int i2 = i;
            i = i2 - 1;
            if (i2 <= 0) {
                return;
            }
            if (!onFilterRoute(routes.get(i))) {
                routes.remove(i);
            }
        }
    }

    public boolean onFilterRoute(@NonNull MediaRouter.RouteInfo route) {
        return !route.isDefaultOrBluetooth() && route.isEnabled() && route.matchesSelector(this.mSelector);
    }

    public void setTitle(CharSequence title) {
        this.mTitleView.setText(title);
    }

    public void setTitle(int titleId) {
        this.mTitleView.setText(titleId);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mr_chooser_dialog);
        this.mRoutes = new ArrayList<>();
        this.mAdapter = new RouteAdapter(getContext(), this.mRoutes);
        this.mListView = (ListView) findViewById(R.id.mr_chooser_list);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(this.mAdapter);
        this.mListView.setEmptyView(findViewById(16908292));
        this.mTitleView = (TextView) findViewById(R.id.mr_chooser_title);
        updateLayout();
    }

    /* access modifiers changed from: package-private */
    public void updateLayout() {
        getWindow().setLayout(MediaRouteDialogHelper.getDialogWidth(getContext()), -2);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        refreshRoutes();
    }

    public void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        this.mHandler.removeMessages(1);
        super.onDetachedFromWindow();
    }

    public void refreshRoutes() {
        if (this.mAttachedToWindow) {
            ArrayList<MediaRouter.RouteInfo> routes = new ArrayList<>(this.mRouter.getRoutes());
            onFilterRoutes(routes);
            Collections.sort(routes, RouteComparator.sInstance);
            if (SystemClock.uptimeMillis() - this.mLastUpdateTime >= UPDATE_ROUTES_DELAY_MS) {
                updateRoutes(routes);
                return;
            }
            this.mHandler.removeMessages(1);
            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(1, routes), this.mLastUpdateTime + UPDATE_ROUTES_DELAY_MS);
        }
    }

    /* access modifiers changed from: package-private */
    public void updateRoutes(List<MediaRouter.RouteInfo> routes) {
        this.mLastUpdateTime = SystemClock.uptimeMillis();
        this.mRoutes.clear();
        this.mRoutes.addAll(routes);
        this.mAdapter.notifyDataSetChanged();
    }

    private final class RouteAdapter extends ArrayAdapter<MediaRouter.RouteInfo> implements AdapterView.OnItemClickListener {
        private final Drawable mDefaultIcon;
        private final LayoutInflater mInflater;
        private final Drawable mSpeakerGroupIcon;
        private final Drawable mSpeakerIcon;
        private final Drawable mTvIcon;

        public RouteAdapter(Context context, List<MediaRouter.RouteInfo> routes) {
            super(context, 0, routes);
            this.mInflater = LayoutInflater.from(context);
            TypedArray styledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.mediaRouteDefaultIconDrawable, R.attr.mediaRouteTvIconDrawable, R.attr.mediaRouteSpeakerIconDrawable, R.attr.mediaRouteSpeakerGroupIconDrawable});
            this.mDefaultIcon = styledAttributes.getDrawable(0);
            this.mTvIcon = styledAttributes.getDrawable(1);
            this.mSpeakerIcon = styledAttributes.getDrawable(2);
            this.mSpeakerGroupIcon = styledAttributes.getDrawable(3);
            styledAttributes.recycle();
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public boolean isEnabled(int position) {
            return ((MediaRouter.RouteInfo) getItem(position)).isEnabled();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            boolean isConnectedOrConnecting = true;
            View view = convertView;
            if (view == null) {
                view = this.mInflater.inflate(R.layout.mr_chooser_list_item, parent, false);
            }
            MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) getItem(position);
            TextView text1 = (TextView) view.findViewById(R.id.mr_chooser_route_name);
            TextView text2 = (TextView) view.findViewById(R.id.mr_chooser_route_desc);
            text1.setText(route.getName());
            String description = route.getDescription();
            if (!(route.getConnectionState() == 2 || route.getConnectionState() == 1)) {
                isConnectedOrConnecting = false;
            }
            if (!isConnectedOrConnecting || TextUtils.isEmpty(description)) {
                text1.setGravity(16);
                text2.setVisibility(8);
                text2.setText("");
            } else {
                text1.setGravity(80);
                text2.setVisibility(0);
                text2.setText(description);
            }
            view.setEnabled(route.isEnabled());
            ImageView iconView = (ImageView) view.findViewById(R.id.mr_chooser_route_icon);
            if (iconView != null) {
                iconView.setImageDrawable(getIconDrawable(route));
            }
            return view;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) getItem(position);
            if (route.isEnabled()) {
                route.select();
                MediaRouteChooserDialog.this.dismiss();
            }
        }

        private Drawable getIconDrawable(MediaRouter.RouteInfo route) {
            Uri iconUri = route.getIconUri();
            if (iconUri != null) {
                try {
                    Drawable drawable = Drawable.createFromStream(getContext().getContentResolver().openInputStream(iconUri), (String) null);
                    if (drawable != null) {
                        return drawable;
                    }
                } catch (IOException e) {
                    Log.w(MediaRouteChooserDialog.TAG, "Failed to load " + iconUri, e);
                }
            }
            return getDefaultIconDrawable(route);
        }

        private Drawable getDefaultIconDrawable(MediaRouter.RouteInfo route) {
            switch (route.getDeviceType()) {
                case 1:
                    return this.mTvIcon;
                case 2:
                    return this.mSpeakerIcon;
                default:
                    if (route instanceof MediaRouter.RouteGroup) {
                        return this.mSpeakerGroupIcon;
                    }
                    return this.mDefaultIcon;
            }
        }
    }

    private final class MediaRouterCallback extends MediaRouter.Callback {
        MediaRouterCallback() {
        }

        public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        public void onRouteChanged(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {
            MediaRouteChooserDialog.this.dismiss();
        }
    }

    static final class RouteComparator implements Comparator<MediaRouter.RouteInfo> {
        public static final RouteComparator sInstance = new RouteComparator();

        RouteComparator() {
        }

        public int compare(MediaRouter.RouteInfo lhs, MediaRouter.RouteInfo rhs) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }
}
