package com.garena.overlay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import bolts.Continuation;
import bolts.Task;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.GGAppConfig;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.CacheHelper;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.garena.msdk.R;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.qqgamemi.util.GlobalUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONObject;

public class FloatDotViewUtil implements Application.ActivityLifecycleCallbacks {
    private static volatile FloatDotViewUtil _instance;
    public static boolean isRecording = false;
    /* access modifiers changed from: private */
    public static long mDeBounce = 0;
    public static boolean shouldShowFloatDot = false;
    /* access modifiers changed from: private */
    public CacheHelper cacheHelper;
    private ViewGroup decorView;
    /* access modifiers changed from: private */
    public FrameLayout.LayoutParams dotLayoutParams = new FrameLayout.LayoutParams(-2, -2);
    /* access modifiers changed from: private */
    public View dotView;
    /* access modifiers changed from: private */
    public int dotViewHeight;
    /* access modifiers changed from: private */
    public int dotViewWidth;
    private LinkedHashMap<String, FloatMenuItem> menuItemMap = new LinkedHashMap<>();
    private int orientation;
    private WeakReference<Activity> pendingActivity;
    private int previousY = -1;
    private int redDotCount = 0;
    private View redDotView;
    private boolean shouldShowRedDot = false;
    /* access modifiers changed from: private */
    public int statusBarHeight;
    /* access modifiers changed from: private */
    public Point szWindow;

    private static FloatDotViewUtil getInstance() {
        if (_instance == null) {
            synchronized (FloatDotViewUtil.class) {
                if (_instance == null) {
                    _instance = new FloatDotViewUtil();
                }
            }
        }
        return _instance;
    }

    public static void show(Activity activity) {
        shouldShowFloatDot = true;
        if (!isRecording) {
            getInstance().showFloatView(activity);
            activity.getApplication().registerActivityLifecycleCallbacks(getInstance());
        }
    }

    public static void hide(Activity activity) {
        shouldShowFloatDot = false;
        if (!isRecording) {
            getInstance().hideFloatView();
            activity.getApplication().unregisterActivityLifecycleCallbacks(getInstance());
        }
    }

    public static void viewMenuItem(FloatMenuItem item) {
        getInstance().visitMenuItem(item);
    }

    public static void restoreFloatDot() {
        getInstance().restore();
    }

    public FloatDotViewUtil() {
        this.dotLayoutParams.gravity = 51;
    }

    /* access modifiers changed from: private */
    public void showFloatView(final Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            if (this.cacheHelper == null) {
                this.cacheHelper = CacheHelper.get(activity, GGAppConfig.OVERLAY_CONFIG);
                Task.callInBackground(new Callable<Void>() {
                    public Void call() throws Exception {
                        FloatDotViewUtil.this.initMenuItems();
                        return null;
                    }
                }).continueWith(new Continuation<Void, Void>() {
                    public Void then(Task<Void> task) throws Exception {
                        if (activity.isFinishing()) {
                            return null;
                        }
                        FloatDotViewUtil.this.showFloatView(activity);
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }
            if (this.menuItemMap != null && !this.menuItemMap.isEmpty()) {
                if (this.dotView == null) {
                    initDotView(activity);
                }
                if (this.dotView.getParent() == null) {
                    this.decorView.addView(this.dotView, this.dotLayoutParams);
                    this.redDotView.setVisibility((!this.shouldShowRedDot || this.redDotCount <= 0) ? 8 : 0);
                }
            }
        }
    }

    private void hideFloatView() {
        if (this.dotView != null) {
            this.decorView.removeView(this.dotView);
            this.dotView = null;
        }
    }

    private void initDotView(final Activity activity) {
        this.szWindow = new Point();
        ((WindowManager) activity.getSystemService("window")).getDefaultDisplay().getSize(this.szWindow);
        if (this.previousY == -1) {
            this.dotLayoutParams.topMargin = this.szWindow.y / 2;
            this.previousY = this.szWindow.y / 2;
        }
        this.statusBarHeight = (int) Math.ceil((double) (25.0f * activity.getResources().getDisplayMetrics().density));
        this.decorView = (ViewGroup) activity.getWindow().getDecorView();
        this.dotView = activity.getLayoutInflater().inflate(R.layout.msdk_floating_view, (ViewGroup) null);
        this.redDotView = this.dotView.findViewById(R.id.reddot);
        this.dotView.setFocusable(true);
        this.dotView.setFocusableInTouchMode(true);
        if (this.dotViewWidth == 0) {
            this.dotView.post(new Runnable() {
                public void run() {
                    if (FloatDotViewUtil.this.dotView != null) {
                        int unused = FloatDotViewUtil.this.dotViewWidth = FloatDotViewUtil.this.dotView.getMeasuredWidth();
                        int unused2 = FloatDotViewUtil.this.dotViewHeight = FloatDotViewUtil.this.dotView.getMeasuredHeight();
                    }
                }
            });
        }
        this.dotView.setOnTouchListener(new View.OnTouchListener() {
            private int _xDelta;
            private int _yDelta;
            private int intStartX;
            private int intStartY;

            public boolean onTouch(View v, MotionEvent event) {
                int top;
                int left;
                if (Math.abs(FloatDotViewUtil.mDeBounce - event.getEventTime()) < 250) {
                    return true;
                }
                int X = (int) event.getRawX();
                int Y = (int) event.getRawY();
                switch (event.getAction()) {
                    case 0:
                        this._xDelta = X - FloatDotViewUtil.this.dotLayoutParams.leftMargin;
                        this._yDelta = Y - FloatDotViewUtil.this.dotLayoutParams.topMargin;
                        this.intStartX = X;
                        this.intStartY = Y;
                        return true;
                    case 1:
                        int top2 = FloatDotViewUtil.this.dotLayoutParams.topMargin;
                        if (top2 < FloatDotViewUtil.this.statusBarHeight) {
                            top = FloatDotViewUtil.this.statusBarHeight;
                        } else {
                            top = Math.min(top2, FloatDotViewUtil.this.szWindow.y - FloatDotViewUtil.this.dotViewHeight);
                        }
                        FloatDotViewUtil.this.dotLayoutParams.topMargin = top;
                        if (FloatDotViewUtil.this.dotLayoutParams.leftMargin <= FloatDotViewUtil.this.szWindow.x / 2) {
                            left = 0;
                        } else {
                            left = FloatDotViewUtil.this.szWindow.x - FloatDotViewUtil.this.dotViewWidth;
                        }
                        FloatDotViewUtil.this.dotLayoutParams.leftMargin = left;
                        FloatDotViewUtil.this.dotView.setLayoutParams(FloatDotViewUtil.this.dotLayoutParams);
                        if (Math.abs(X - this.intStartX) < 10 && Math.abs(Y - this.intStartY) < 10) {
                            if (FloatDotViewUtil.mDeBounce > event.getDownTime()) {
                                return true;
                            }
                            FloatDotViewUtil.this.showFloatMenu(activity);
                            long unused = FloatDotViewUtil.mDeBounce = event.getEventTime();
                            return true;
                        }
                    case 2:
                        FloatDotViewUtil.this.dotLayoutParams.leftMargin = X - this._xDelta;
                        FloatDotViewUtil.this.dotLayoutParams.topMargin = Y - this._yDelta;
                        FloatDotViewUtil.this.dotView.setLayoutParams(FloatDotViewUtil.this.dotLayoutParams);
                        return true;
                }
                return false;
            }
        });
        int currentOrientation = activity.getResources().getConfiguration().orientation;
        if (this.orientation != currentOrientation) {
            this.orientation = currentOrientation;
            if (this.orientation == 2) {
                this.dotLayoutParams.topMargin = Math.min(this.dotLayoutParams.topMargin, this.szWindow.y - this.dotViewHeight);
                if (this.dotLayoutParams.leftMargin > 0) {
                    this.dotLayoutParams.leftMargin = this.szWindow.x - this.dotViewWidth;
                    return;
                }
                return;
            }
            this.dotLayoutParams.topMargin = Math.min(this.dotLayoutParams.topMargin, this.szWindow.y - this.dotViewHeight);
            if (this.dotLayoutParams.leftMargin > 0) {
                this.dotLayoutParams.leftMargin = this.szWindow.x - this.dotViewWidth;
            }
        }
    }

    private void visitMenuItem(final FloatMenuItem item) {
        if (item.showRedDot) {
            item.showRedDot = false;
            this.menuItemMap.put(item.url, item);
            Task.callInBackground(new Callable<Void>() {
                public Void call() throws Exception {
                    FloatDotViewUtil.this.cacheHelper.put(item.url, String.valueOf(System.currentTimeMillis() / 1000));
                    return null;
                }
            });
            this.redDotCount--;
            if (this.redDotCount == 0 && this.redDotView != null) {
                this.redDotView.setVisibility(8);
            }
        }
    }

    private void restore() {
        if (this.pendingActivity != null) {
            if (shouldShowFloatDot) {
                showFloatView((Activity) this.pendingActivity.get());
            }
            this.pendingActivity = null;
        }
    }

    public void showFloatMenu(Activity activity) {
        if (FloatingMenuActivity.active) {
            FloatingMenuActivity.menuActivity.finish();
            return;
        }
        Intent intent = new Intent(activity, FloatingMenuActivity.class).setFlags(335544320);
        ArrayList<FloatMenuItem> items = new ArrayList<>();
        items.addAll(this.menuItemMap.values());
        intent.putExtra("menus", items);
        activity.startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void initMenuItems() {
        boolean z;
        JSONObject menuConfig = this.cacheHelper.getAsJSONObject(GGAppConfig.OVERLAY_CONFIG);
        if (menuConfig != null) {
            this.shouldShowRedDot = (menuConfig.optInt(DownloadDBHelper.FLAG) & 1) > 0;
            JSONArray jsonArray = menuConfig.optJSONArray(MessengerShareContentUtility.BUTTONS);
            this.redDotCount = 0;
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        FloatMenuItem item = new FloatMenuItem(jsonArray.optJSONObject(i));
                        if (isSupported(item.url)) {
                            long currentTime = System.currentTimeMillis() / 1000;
                            if (item.reddotStarttime >= currentTime || item.reddotEndtime <= currentTime) {
                                item.showRedDot = false;
                            } else {
                                String time = this.cacheHelper.getAsString(item.url);
                                if (TextUtils.isEmpty(time)) {
                                    item.showRedDot = true;
                                } else {
                                    if (Long.parseLong(time) < item.reddotStarttime) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    item.showRedDot = z;
                                }
                            }
                            if (item.showRedDot) {
                                this.redDotCount++;
                            }
                            this.menuItemMap.put(item.url, item);
                        }
                    } catch (Exception e) {
                        BBLogger.e(e);
                    }
                }
            }
        }
    }

    private boolean isSupported(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.equals(SDKConstants.FLOATING_MENU.ACTION_SCREEN_RECORD)) {
            if (Build.VERSION.SDK_INT < 21 || GGPlatform.getAppConfig().getVideoCapability() <= 0) {
                return false;
            }
            return true;
        } else if (url.equals(SDKConstants.FLOATING_MENU.ACTION_HIDE)) {
            return true;
        } else {
            if (url.startsWith(SDKConstants.FLOATING_MENU.ACTION_GARENA_DEEPLINK)) {
                return true;
            }
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return true;
            }
            return false;
        }
    }

    @SuppressLint({"NewApi"})
    private static MediaCodecInfo getMediaCodecInfo() {
        try {
            for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(0).getCodecInfos()) {
                if (mediaCodecInfo.isEncoder()) {
                    for (String equalsIgnoreCase : mediaCodecInfo.getSupportedTypes()) {
                        if (equalsIgnoreCase.equalsIgnoreCase(GlobalUtil.AVC_MIME_TYPE)) {
                            return mediaCodecInfo;
                        }
                    }
                    continue;
                }
            }
        } catch (Exception e) {
            BBLogger.e(e);
        }
        return null;
    }

    @SuppressLint({"NewApi"})
    public static boolean isSupportVideo(int displayWidth, int displayHeight) {
        int videoWidth = 480;
        try {
            MediaCodecInfo mediaCodecInfo = getMediaCodecInfo();
            if (mediaCodecInfo == null) {
                return false;
            }
            MediaCodecInfo.CodecCapabilities codecCapabilities = mediaCodecInfo.getCapabilitiesForType(GlobalUtil.AVC_MIME_TYPE);
            if (displayWidth < 480) {
                return codecCapabilities.isFormatSupported(MediaFormat.createVideoFormat(GlobalUtil.AVC_MIME_TYPE, displayHeight, displayWidth));
            }
            if (displayWidth > 480) {
                videoWidth = 720;
            }
            int videoHeight = (int) Math.floor((double) (((float) videoWidth) * (((float) displayHeight) / ((float) displayWidth))));
            if (videoHeight % 2 == 1) {
                videoHeight++;
            }
            return codecCapabilities.isFormatSupported(MediaFormat.createVideoFormat(GlobalUtil.AVC_MIME_TYPE, videoHeight, videoWidth));
        } catch (Exception e) {
            BBLogger.e(e);
            return false;
        }
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        if (shouldShowFloatDot && !(activity instanceof FloatingMenuActivity)) {
            if (isRecording) {
                this.pendingActivity = new WeakReference<>(activity);
            } else {
                showFloatView(activity);
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        hideFloatView();
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
