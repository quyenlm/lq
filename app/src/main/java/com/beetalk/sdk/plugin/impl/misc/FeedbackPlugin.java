package com.beetalk.sdk.plugin.impl.misc;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackPlugin extends GGPlugin<FeedbackData, PluginResult> {

    public static class FeedbackData {
        public String appId;
        public String feedback;
        public String openId;
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.APP_FEEDBACK;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FEEDBACK;
    }

    /* access modifiers changed from: protected */
    public void executeAction(Activity activity, FeedbackData data) {
        if (Helper.isNullOrEmpty(data.openId) || Helper.isNullOrEmpty(data.feedback) || Helper.isNullOrEmpty(data.appId)) {
            GGPluginManager.getInstance().publishResult(new PluginResult() {
                {
                    this.source = FeedbackPlugin.this.getId();
                    this.message = "Required params missing. Have you logged in?";
                    this.status = -1;
                    this.flag = -1;
                }
            }, activity, getId());
            return;
        }
        new PostFeedbackTask().execute(new FeedbackData[]{data});
    }

    /* access modifiers changed from: private */
    public JSONObject makeHttpPost(String uri, Map<String, String> data) {
        return SimpleNetworkClient.getInstance().makePostRequest(uri, data);
    }

    /* access modifiers changed from: private */
    public void onPostDone(PluginResult result) {
        GGPluginManager.getInstance().publishResult(result, (Activity) null, getId());
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean embedInActivity() {
        return false;
    }

    private class PostFeedbackTask extends AsyncTask<FeedbackData, Void, PluginResult> {
        private PostFeedbackTask() {
        }

        /* access modifiers changed from: protected */
        public PluginResult doInBackground(FeedbackData... params) {
            final FeedbackData object = params[0];
            int result = -1;
            try {
                JSONObject jsonObject = FeedbackPlugin.this.makeHttpPost(SDKConstants.getFeedbackServerUrl(), new HashMap<String, String>() {
                    {
                        put("app_id", object.appId);
                        put("open_id", object.openId);
                        put("feedback", object.feedback);
                    }
                });
                BBLogger.i("Response From Server: Feedback %s", jsonObject);
                if (jsonObject != null) {
                    result = jsonObject.getInt(GGLiveConstants.PARAM.RESULT);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (result == -1) {
                return new PluginResult() {
                    {
                        this.source = FeedbackPlugin.this.getId();
                        this.message = "Request Failed. Network Error or Server Error";
                        this.status = -1;
                        this.flag = -1;
                    }
                };
            }
            final int finalResult = result;
            return new PluginResult() {
                {
                    this.source = FeedbackPlugin.this.getId();
                    this.message = "feedback: " + object.feedback;
                    int i = finalResult;
                    this.status = i;
                    this.flag = i;
                }
            };
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(PluginResult result) {
            FeedbackPlugin.this.onPostDone(result);
        }
    }
}
