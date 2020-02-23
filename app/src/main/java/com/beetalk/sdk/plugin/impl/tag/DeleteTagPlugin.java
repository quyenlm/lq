package com.beetalk.sdk.plugin.impl.tag;

import android.app.Activity;
import android.content.Intent;
import bolts.Continuation;
import bolts.Task;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.TagsData;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.garena.android.beepost.service.BeePostAPI;
import com.garena.android.gpns.utility.DeviceUtil;
import com.garena.pay.android.GGErrorCode;
import java.io.IOException;
import java.util.concurrent.Callable;
import org.json.JSONException;

public class DeleteTagPlugin extends GGPlugin<TagsData, PluginResult> {
    private static final int SUCCESS = 0;

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.BEEPOST_DELETE_TAGS;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.BEEPOST_DELETE_TAGS_PLUGIN;
    }

    /* access modifiers changed from: protected */
    public void executeAction(final Activity activity, final TagsData data) {
        Task.callInBackground(new Callable<PluginResult>() {
            public PluginResult call() throws Exception {
                PluginResult pluginResult = new PluginResult();
                pluginResult.source = DeleteTagPlugin.this.getId();
                try {
                    pluginResult.flag = BeePostAPI.deleteTags(activity.getApplicationContext(), data.mAppId, data.mAppKey, String.valueOf(DeviceUtil.generateDeviceId(activity)), data.mTags) ? 0 : GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                } catch (IOException e) {
                    pluginResult.flag = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                    pluginResult.message = e.getMessage();
                } catch (JSONException e2) {
                    pluginResult.flag = GGErrorCode.ERROR.getCode().intValue();
                    pluginResult.message = e2.getMessage();
                }
                return pluginResult;
            }
        }).continueWith(new Continuation<PluginResult, Void>() {
            public Void then(Task<PluginResult> task) throws Exception {
                PluginResult pluginResult = task.getResult();
                if (pluginResult == null) {
                    pluginResult = new PluginResult();
                    pluginResult.source = DeleteTagPlugin.this.getId();
                    pluginResult.flag = GGErrorCode.ERROR.getCode().intValue();
                }
                GGPluginManager.getInstance().publishResult(pluginResult, activity, DeleteTagPlugin.this.getId());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean embedInActivity() {
        return false;
    }
}
