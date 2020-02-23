package com.beetalk.sdk.plugin.impl.friends;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.share.internal.ShareConstants;
import com.garena.pay.android.GGErrorCode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GGSendBuddyRequestPlugin extends GGPlugin<SendBuddyRequestData, PluginResult> {

    public static class SendBuddyRequestData {
        public String mAppKey;
        public List<String> mFriendIDs;
        public String mImageURL;
        public String mMessageBody;
        public String mMetaData;
        public String mObjectId;
        public String mTitle;
        public String mToken;
    }

    /* access modifiers changed from: private */
    public int _sendBuddyRequest(SendBuddyRequestData requestData) {
        HashMap<String, String> postValues = new HashMap<>(3);
        postValues.put("access_token", requestData.mToken);
        postValues.put("app_key", requestData.mAppKey);
        StringBuilder strBuffer = new StringBuilder();
        Iterator<String> iterator = requestData.mFriendIDs.iterator();
        while (iterator.hasNext()) {
            strBuffer.append(iterator.next());
            if (iterator.hasNext()) {
                strBuffer.append(",");
            }
        }
        postValues.put("to_friends", strBuffer.toString());
        postValues.put("title", requestData.mTitle);
        postValues.put("message", requestData.mMessageBody);
        postValues.put("image", requestData.mImageURL);
        postValues.put("object_id", requestData.mObjectId);
        postValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, requestData.mMetaData);
        JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getServerSendRequest(), (Map<String, String>) postValues);
        if (result == null) {
            return GGErrorCode.NETWORK_CONNECTION_EXCEPTION.getCode().intValue();
        }
        if (!result.has(GGLiveConstants.PARAM.RESULT)) {
            return GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
        }
        try {
            BBLogger.i("buddy request %s", result.getString(GGLiveConstants.PARAM.RESULT));
        } catch (JSONException e) {
            BBLogger.e(e);
        }
        return GGErrorCode.SUCCESS.getCode().intValue();
    }

    /* access modifiers changed from: private */
    public void onResult(PluginResult result) {
        GGPluginManager.getInstance().publishResult(result, (Activity) null, getId());
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.APP_FRIEND_REQUEST;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.APP_FRIEND_REQUEST;
    }

    /* access modifiers changed from: protected */
    public void executeAction(Activity activity, SendBuddyRequestData data) {
        new PostInAppBuddyRequestTask().execute(new SendBuddyRequestData[]{data});
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean embedInActivity() {
        return false;
    }

    private class PostInAppBuddyRequestTask extends AsyncTask<SendBuddyRequestData, Void, PluginResult> {
        private PostInAppBuddyRequestTask() {
        }

        /* access modifiers changed from: protected */
        public PluginResult doInBackground(SendBuddyRequestData... params) {
            final int result = GGSendBuddyRequestPlugin.this._sendBuddyRequest(params[0]);
            if (result == GGErrorCode.SUCCESS.getCode().intValue()) {
                return new PluginResult() {
                    {
                        this.source = GGSendBuddyRequestPlugin.this.getId();
                        this.message = "Successfully sent";
                        this.status = 0;
                        this.flag = result;
                    }
                };
            }
            return new PluginResult() {
                {
                    this.source = GGSendBuddyRequestPlugin.this.getId();
                    this.message = "Error encountered";
                    this.status = -1;
                    this.flag = result;
                }
            };
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(PluginResult result) {
            GGSendBuddyRequestPlugin.this.onResult(result);
        }
    }
}
