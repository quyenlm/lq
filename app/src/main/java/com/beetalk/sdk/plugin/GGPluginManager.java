package com.beetalk.sdk.plugin;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.impl.fb.FBFallbackSharePlugin;
import com.beetalk.sdk.plugin.impl.fb.FBGameMessagePlugin;
import com.beetalk.sdk.plugin.impl.fb.FBGraphItemSharePlugin;
import com.beetalk.sdk.plugin.impl.fb.FBInvitePlugin;
import com.beetalk.sdk.plugin.impl.fb.FBMessagePlugin;
import com.beetalk.sdk.plugin.impl.fb.FBShareImageToMessengerPlugin;
import com.beetalk.sdk.plugin.impl.fb.FBSharePlugin;
import com.beetalk.sdk.plugin.impl.fb.FBUserInfoPlugin;
import com.beetalk.sdk.plugin.impl.friends.GGSendBuddyRequestPlugin;
import com.beetalk.sdk.plugin.impl.friends.GetFriendInfo;
import com.beetalk.sdk.plugin.impl.friends.GetInAppFriendIDList;
import com.beetalk.sdk.plugin.impl.friends.GetUserFriendIDList;
import com.beetalk.sdk.plugin.impl.friends.LoadFriendGroupList;
import com.beetalk.sdk.plugin.impl.friends.LoadGroupFriendsInfo;
import com.beetalk.sdk.plugin.impl.friends.LoadInAppFriendGroupList;
import com.beetalk.sdk.plugin.impl.gas.GasPhotoSharePlugin;
import com.beetalk.sdk.plugin.impl.gas.GasTextSharePlugin;
import com.beetalk.sdk.plugin.impl.gas.GasUrlSharePlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveChannelInfoGetPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveChannelInfoUpdatePlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveChannelRegisterPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveChannelStreamInitPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveChannelStreamStopPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveChannelStreamVerifyPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveHeartBeatPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveLoginPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveLogoutPlugin;
import com.beetalk.sdk.plugin.impl.line.LineSharePlugin;
import com.beetalk.sdk.plugin.impl.misc.FeedbackPlugin;
import com.beetalk.sdk.plugin.impl.tag.DeleteTagPlugin;
import com.beetalk.sdk.plugin.impl.tag.SetTagsPlugin;
import com.beetalk.sdk.plugin.impl.user.GetUserInfo;
import com.beetalk.sdk.plugin.impl.vk.VKSharePlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GGPluginManager {
    private static volatile GGPluginManager ourInstance;
    /* access modifiers changed from: private */
    public HashMap<String, GGPlugin> PLUGIN_MAP = new HashMap<>();
    private final Handler handler;
    /* access modifiers changed from: private */
    public final HashMap<String, List<GGPluginCallback>> mLongTermPluginCallback = new HashMap<>();
    /* access modifiers changed from: private */
    public HashMap<String, GGPluginCallback> mOneShotPluginCallback = new HashMap<>();
    /* access modifiers changed from: private */
    public HashMap<String, Object> mPluginDatas = new HashMap<>();
    private Thread mUiThread;
    /* access modifiers changed from: private */
    public HashMap<String, Object> mUnconsumedResults = new HashMap<>();

    private GGPluginManager() {
        __initPlugins();
        this.handler = new Handler(Looper.getMainLooper());
        this.mUiThread = Looper.getMainLooper().getThread();
    }

    public static GGPluginManager getInstance() {
        if (ourInstance == null) {
            synchronized (GGPluginManager.class) {
                if (ourInstance == null) {
                    ourInstance = new GGPluginManager();
                }
            }
        }
        return ourInstance;
    }

    private void __initPlugins() {
        addPlugin(new GetUserInfo());
        addPlugin(new GetUserFriendIDList());
        addPlugin(new GetFriendInfo());
        addPlugin(new GetInAppFriendIDList());
        addPlugin(new GGSendBuddyRequestPlugin());
        addPlugin(new SetTagsPlugin());
        addPlugin(new DeleteTagPlugin());
        addPlugin(new LoadFriendGroupList());
        addPlugin(new LoadInAppFriendGroupList());
        addPlugin(new LoadGroupFriendsInfo());
        addPlugin(new GasTextSharePlugin());
        addPlugin(new GasPhotoSharePlugin());
        addPlugin(new GasUrlSharePlugin());
        addPlugin(new FBSharePlugin());
        addPlugin(new FBUserInfoPlugin());
        addPlugin(new FBInvitePlugin());
        addPlugin(new FBMessagePlugin());
        addPlugin(new FeedbackPlugin());
        addPlugin(new FBGraphItemSharePlugin());
        addPlugin(new FBFallbackSharePlugin());
        addPlugin(new FBGameMessagePlugin());
        addPlugin(new FBShareImageToMessengerPlugin());
        addPlugin(new GGLiveLoginPlugin());
        addPlugin(new GGLiveLogoutPlugin());
        addPlugin(new GGLiveChannelRegisterPlugin());
        addPlugin(new GGLiveChannelInfoGetPlugin());
        addPlugin(new GGLiveChannelInfoUpdatePlugin());
        addPlugin(new GGLiveChannelStreamInitPlugin());
        addPlugin(new GGLiveChannelStreamStopPlugin());
        addPlugin(new GGLiveChannelStreamVerifyPlugin());
        addPlugin(new GGLiveHeartBeatPlugin());
        addPlugin(new VKSharePlugin());
        addPlugin(new LineSharePlugin());
    }

    public void addPlugin(GGPlugin plugin) {
        if (this.PLUGIN_MAP.containsKey(plugin.getId())) {
            throw new ExceptionInInitializerError("Plugin Already exists for Key: " + plugin.getId());
        }
        this.PLUGIN_MAP.put(plugin.getId(), plugin);
    }

    public GGPlugin getPlugin(String key) {
        return this.PLUGIN_MAP.get(key);
    }

    public <T> void invokePlugin(Activity activity, String pluginKey, Object data, GGPluginCallback<T> pluginCallback) {
        final String str = pluginKey;
        final GGPluginCallback<T> gGPluginCallback = pluginCallback;
        final Object obj = data;
        final Activity activity2 = activity;
        runOnUiThread(new Runnable() {
            public void run() {
                BBLogger.i("invokePlugin: %s", str);
                GGPluginManager.this.mOneShotPluginCallback.put(str, gGPluginCallback);
                GGPluginManager.this.mPluginDatas.put(str, obj);
                GGPlugin plugin = GGPluginManager.this.getPlugin(str);
                if (plugin.embedInActivity()) {
                    GGPluginActivity.startWithPlugin(activity2, str, plugin.getRequestCode().intValue());
                } else {
                    GGPluginManager.this.realExecute(plugin, activity2);
                }
            }
        });
    }

    public <T> void publishResult(final T result, final Activity ggPluginActivity, final String source) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (((GGPlugin) GGPluginManager.this.PLUGIN_MAP.get(source)).embedInActivity()) {
                    ggPluginActivity.finish();
                }
                boolean oneShotResult = GGPluginManager.this.consumeInOneShotCallbacks(source, result);
                boolean longTermResult = GGPluginManager.this.consumeInLongTermCallbacks(source, result);
                if (oneShotResult || longTermResult) {
                    GGPluginManager.this.mUnconsumedResults.remove(source);
                } else {
                    GGPluginManager.this.mUnconsumedResults.put(source, result);
                }
            }
        });
    }

    public void unregister(final GGPluginCallback callback) {
        runOnUiThread(new Runnable() {
            public void run() {
                for (List<GGPluginCallback> callbackList : GGPluginManager.this.mLongTermPluginCallback.values()) {
                    callbackList.remove(callback);
                }
            }
        });
    }

    public void register(final String pluginKey, final GGPluginCallback callback) {
        runOnUiThread(new Runnable() {
            public void run() {
                List<GGPluginCallback> callbackList = (List) GGPluginManager.this.mLongTermPluginCallback.get(pluginKey);
                if (callbackList == null) {
                    callbackList = new ArrayList<>();
                    GGPluginManager.this.mLongTermPluginCallback.put(pluginKey, callbackList);
                }
                callbackList.add(callback);
                Object unconsumedResult = GGPluginManager.this.mUnconsumedResults.get(pluginKey);
                if (unconsumedResult != null) {
                    callback.onPluginResult(unconsumedResult);
                    GGPluginManager.this.mUnconsumedResults.remove(pluginKey);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void realExecute(final GGPlugin plugin, final Activity ggPluginActivity) {
        runOnUiThread(new Runnable() {
            public void run() {
                plugin.executeAction(ggPluginActivity, GGPluginManager.this.mPluginDatas.remove(plugin.getId()));
            }
        });
    }

    /* access modifiers changed from: private */
    public <T> boolean consumeInOneShotCallbacks(String pluginKey, T result) {
        GGPluginCallback callback = this.mOneShotPluginCallback.remove(pluginKey);
        if (callback == null) {
            return false;
        }
        callback.onPluginResult(result);
        return true;
    }

    /* access modifiers changed from: private */
    public <T> boolean consumeInLongTermCallbacks(String pluginKey, T result) {
        List<GGPluginCallback> callbackList = this.mLongTermPluginCallback.get(pluginKey);
        if (callbackList == null || callbackList.size() <= 0) {
            return false;
        }
        for (GGPluginCallback callback : callbackList) {
            callback.onPluginResult(result);
        }
        return true;
    }

    private void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() == this.mUiThread) {
            runnable.run();
        } else {
            this.handler.post(runnable);
        }
    }
}
