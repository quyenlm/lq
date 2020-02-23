package com.facebook.appevents.codeless;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.codeless.CodelessLoggingEventListener;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.codeless.internal.EventBinding;
import com.facebook.appevents.codeless.internal.ParameterComponent;
import com.facebook.appevents.codeless.internal.PathComponent;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CodelessMatcher {
    private static final String CURRENT_CLASS_NAME = ".";
    private static final String PARENT_CLASS_NAME = "..";
    /* access modifiers changed from: private */
    public static final String TAG = CodelessMatcher.class.getCanonicalName();
    private Set<Activity> activitiesSet = new HashSet();
    private HashMap<String, String> delegateMap = new HashMap<>();
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());
    private Set<ViewMatcher> viewMatchers = new HashSet();

    public void add(Activity activity) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new FacebookException("Can't add activity to CodelessMatcher on non-UI thread");
        }
        this.activitiesSet.add(activity);
        this.delegateMap.clear();
        startTracking();
    }

    public void remove(Activity activity) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new FacebookException("Can't remove activity from CodelessMatcher on non-UI thread");
        }
        this.activitiesSet.remove(activity);
        this.viewMatchers.clear();
        this.delegateMap.clear();
    }

    public static Bundle getParameters(EventBinding mapping, View rootView, View hostView) {
        List<ParameterComponent> parameters;
        List<MatchedView> matchedViews;
        Bundle params = new Bundle();
        if (!(mapping == null || (parameters = mapping.getViewParameters()) == null)) {
            for (ParameterComponent component : parameters) {
                if (component.value == null || component.value.length() <= 0) {
                    if (component.path.size() > 0) {
                        if (component.pathType.equals(Constants.PATH_TYPE_RELATIVE)) {
                            matchedViews = ViewMatcher.findViewByPath(mapping, hostView, component.path, 0, -1, hostView.getClass().getSimpleName());
                        } else {
                            matchedViews = ViewMatcher.findViewByPath(mapping, rootView, component.path, 0, -1, rootView.getClass().getSimpleName());
                        }
                        Iterator<MatchedView> it = matchedViews.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            MatchedView view = it.next();
                            if (view.getView() != null) {
                                String text = ViewHierarchy.getTextOfView(view.getView());
                                if (text.length() > 0) {
                                    params.putString(component.name, text);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    params.putString(component.name, component.value);
                }
            }
        }
        return params;
    }

    private void startTracking() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            matchViews();
        } else {
            this.uiThreadHandler.post(new Runnable() {
                public void run() {
                    CodelessMatcher.this.matchViews();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void matchViews() {
        for (Activity activity : this.activitiesSet) {
            this.viewMatchers.add(new ViewMatcher(activity.getWindow().getDecorView().getRootView(), this.uiThreadHandler, this.delegateMap, activity.getClass().getSimpleName()));
        }
    }

    public static class MatchedView {
        private WeakReference<View> view;
        private String viewMapKey;

        public MatchedView(View view2, String viewMapKey2) {
            this.view = new WeakReference<>(view2);
            this.viewMapKey = viewMapKey2;
        }

        @Nullable
        public View getView() {
            if (this.view == null) {
                return null;
            }
            return (View) this.view.get();
        }

        public String getViewMapKey() {
            return this.viewMapKey;
        }
    }

    protected static class ViewMatcher implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener, Runnable {
        private final String activityName;
        private HashMap<String, String> delegateMap;
        @Nullable
        private List<EventBinding> eventBindings;
        private final Handler handler;
        private WeakReference<View> rootView;

        public ViewMatcher(View rootView2, Handler handler2, HashMap<String, String> delegateMap2, String activityName2) {
            this.rootView = new WeakReference<>(rootView2);
            this.handler = handler2;
            this.delegateMap = delegateMap2;
            this.activityName = activityName2;
            this.handler.postDelayed(this, 200);
        }

        public void run() {
            View rootView2;
            FetchedAppSettings appSettings = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
            if (appSettings != null && appSettings.getCodelessEventsEnabled()) {
                this.eventBindings = EventBinding.parseArray(appSettings.getEventBindings());
                if (this.eventBindings != null && (rootView2 = (View) this.rootView.get()) != null) {
                    ViewTreeObserver observer = rootView2.getViewTreeObserver();
                    if (observer.isAlive()) {
                        observer.addOnGlobalLayoutListener(this);
                        observer.addOnScrollChangedListener(this);
                    }
                    startMatch();
                }
            }
        }

        public void onGlobalLayout() {
            startMatch();
        }

        public void onScrollChanged() {
            startMatch();
        }

        private void startMatch() {
            if (this.eventBindings != null && this.rootView.get() != null) {
                for (int i = 0; i < this.eventBindings.size(); i++) {
                    findView(this.eventBindings.get(i), (View) this.rootView.get());
                }
            }
        }

        public void findView(EventBinding mapping, View rootView2) {
            if (mapping != null && rootView2 != null) {
                if (TextUtils.isEmpty(mapping.getActivityName()) || mapping.getActivityName().equals(this.activityName)) {
                    List<PathComponent> path = mapping.getViewPath();
                    if (path.size() <= 25) {
                        for (MatchedView view : findViewByPath(mapping, rootView2, path, 0, -1, this.activityName)) {
                            attachListener(view, rootView2, mapping);
                        }
                    }
                }
            }
        }

        public static List<MatchedView> findViewByPath(EventBinding mapping, View view, List<PathComponent> path, int level, int index, String mapKey) {
            String mapKey2 = mapKey + CodelessMatcher.CURRENT_CLASS_NAME + String.valueOf(index);
            List<MatchedView> result = new ArrayList<>();
            if (view != null) {
                if (level >= path.size()) {
                    result.add(new MatchedView(view, mapKey2));
                } else {
                    PathComponent pathElement = path.get(level);
                    if (pathElement.className.equals(CodelessMatcher.PARENT_CLASS_NAME)) {
                        ViewParent parent = view.getParent();
                        if (parent instanceof ViewGroup) {
                            List<View> visibleViews = findVisibleChildren((ViewGroup) parent);
                            int childCount = visibleViews.size();
                            for (int i = 0; i < childCount; i++) {
                                result.addAll(findViewByPath(mapping, visibleViews.get(i), path, level + 1, i, mapKey2));
                            }
                        }
                    } else if (pathElement.className.equals(CodelessMatcher.CURRENT_CLASS_NAME)) {
                        result.add(new MatchedView(view, mapKey2));
                    } else if (isTheSameView(view, pathElement, index)) {
                        if (level == path.size() - 1) {
                            result.add(new MatchedView(view, mapKey2));
                        }
                    }
                }
                if (view instanceof ViewGroup) {
                    List<View> visibleViews2 = findVisibleChildren((ViewGroup) view);
                    int childCount2 = visibleViews2.size();
                    for (int i2 = 0; i2 < childCount2; i2++) {
                        result.addAll(findViewByPath(mapping, visibleViews2.get(i2), path, level + 1, i2, mapKey2));
                    }
                }
            }
            return result;
        }

        private static boolean isTheSameView(View targetView, PathComponent pathElement, int index) {
            String targetTag;
            String targetDesc;
            if (pathElement.index != -1 && index != pathElement.index) {
                return false;
            }
            if (!targetView.getClass().getCanonicalName().equals(pathElement.className)) {
                if (!pathElement.className.matches(".*android\\..*")) {
                    return false;
                }
                String[] names = pathElement.className.split("\\.");
                if (names.length <= 0) {
                    return false;
                }
                if (!targetView.getClass().getSimpleName().equals(names[names.length - 1])) {
                    return false;
                }
            }
            if ((pathElement.matchBitmask & PathComponent.MatchBitmaskType.ID.getValue()) > 0 && pathElement.id != targetView.getId()) {
                return false;
            }
            if ((pathElement.matchBitmask & PathComponent.MatchBitmaskType.TEXT.getValue()) > 0 && !pathElement.text.equals(ViewHierarchy.getTextOfView(targetView))) {
                return false;
            }
            if ((pathElement.matchBitmask & PathComponent.MatchBitmaskType.DESCRIPTION.getValue()) > 0) {
                String pathDesc = pathElement.description;
                if (targetView.getContentDescription() == null) {
                    targetDesc = "";
                } else {
                    targetDesc = String.valueOf(targetView.getContentDescription());
                }
                if (!pathDesc.equals(targetDesc)) {
                    return false;
                }
            }
            if ((pathElement.matchBitmask & PathComponent.MatchBitmaskType.HINT.getValue()) > 0 && !pathElement.hint.equals(ViewHierarchy.getHintOfView(targetView))) {
                return false;
            }
            if ((pathElement.matchBitmask & PathComponent.MatchBitmaskType.TAG.getValue()) > 0) {
                String tag = pathElement.tag;
                if (targetView.getTag() == null) {
                    targetTag = "";
                } else {
                    targetTag = String.valueOf(targetView.getTag());
                }
                if (tag.equals(targetTag)) {
                    return true;
                }
                return false;
            }
            return true;
        }

        private static List<View> findVisibleChildren(ViewGroup viewGroup) {
            List<View> visibleViews = new ArrayList<>();
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == 0) {
                    visibleViews.add(child);
                }
            }
            return visibleViews;
        }

        private void attachListener(MatchedView matchedView, View rootView2, EventBinding mapping) {
            if (mapping != null) {
                try {
                    View view = matchedView.getView();
                    if (view != null) {
                        String mapKey = matchedView.getViewMapKey();
                        View.AccessibilityDelegate existingDelegate = ViewHierarchy.getExistingDelegate(view);
                        if (this.delegateMap.containsKey(mapKey)) {
                            return;
                        }
                        if (existingDelegate == null || !(existingDelegate instanceof CodelessLoggingEventListener.AutoLoggingAccessibilityDelegate)) {
                            view.setAccessibilityDelegate(CodelessLoggingEventListener.getAccessibilityDelegate(mapping, rootView2, view));
                            this.delegateMap.put(mapKey, mapping.getEventName());
                        }
                    }
                } catch (FacebookException e) {
                    Log.e(CodelessMatcher.TAG, "Failed to attach auto logging event listener.", e);
                }
            }
        }
    }
}
