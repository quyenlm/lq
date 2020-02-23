package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import android.os.Bundle;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONObject;

public class FBUserInfoPlugin extends BaseFBPlugin<Void, PluginResult> {
    public void onError(Exception e, Activity activity) {
        complain(activity, e.getMessage());
    }

    public void onSuccess(final Activity activity) {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object == null) {
                    FBUserInfoPlugin.this.complain(activity, response.toString());
                } else {
                    FBUserInfoPlugin.this.result(activity, object);
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,birthday,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /* access modifiers changed from: private */
    public void result(Activity activity, final JSONObject object) {
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                this.status = 0;
                this.flag = 0;
                this.message = object.toString();
                this.source = FBUserInfoPlugin.this.getId();
            }
        }, activity, getId());
    }

    /* access modifiers changed from: private */
    public void complain(Activity activity, final String response) {
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                this.status = -1;
                this.flag = -1;
                this.message = response;
                this.source = FBUserInfoPlugin.this.getId();
            }
        }, activity, getId());
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.FACEBOOK_REQUEST_ME;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FB_REQUEST_ME;
    }
}
