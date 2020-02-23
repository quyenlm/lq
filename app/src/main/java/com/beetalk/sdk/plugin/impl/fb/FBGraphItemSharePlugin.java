package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import android.os.AsyncTask;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import com.garena.pay.android.GGErrorCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FBGraphItemSharePlugin extends BaseFBPlugin<FBGraphShareItem, PluginResult> {

    public static class FBGraphShareItem {
        public String mAppKey;
        public List<String> mFriendIDs;
        public String mImageURL;
        public String mMessageBody;
        public String mMetaData;
        public String mObjectId;
        public String mTitle;
        public String mToken;
    }

    public void onError(final Exception e, Activity activity) {
        BBLogger.e(e);
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                int intValue = GGErrorCode.SUCCESS.getCode().intValue();
                this.status = intValue;
                this.flag = intValue;
                this.message = e != null ? e.getMessage() : "Error";
                this.source = FBGraphItemSharePlugin.this.getId();
            }
        }, activity, getId());
    }

    public void onSuccess(Activity activity) {
        new GetFBIds(activity).execute(new FBGraphShareItem[]{(FBGraphShareItem) this.mData});
    }

    /* access modifiers changed from: private */
    public void __complain(final int errorCode, final String s, Activity activity) {
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                int i = errorCode;
                this.status = i;
                this.flag = i;
                this.message = s;
                this.source = FBGraphItemSharePlugin.this.getId();
            }
        }, activity, getId());
    }

    /* access modifiers changed from: private */
    public void __complain(String s, Activity activity) {
        __complain(GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), s, activity);
    }

    /* access modifiers changed from: private */
    public void onResult(List<FBUser> fbUsers, final Activity activity) {
        if (!GameRequestDialog.canShow()) {
            __complain(GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), "Facebook App Not install", activity);
        } else if (fbUsers == null) {
            __complain(GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), "Cant get any friends, token scope issue perhaps", activity);
        } else {
            List<String> ids = new ArrayList<>();
            for (FBUser user : fbUsers) {
                ids.add(user.uid);
            }
            GameRequestDialog requestDialog = new GameRequestDialog(activity);
            requestDialog.registerCallback(this.callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
                public void onSuccess(GameRequestDialog.Result result) {
                    GGPluginManager.getInstance().publishResult(new PluginResult() {
                        {
                            int intValue = GGErrorCode.SUCCESS.getCode().intValue();
                            this.flag = intValue;
                            this.status = intValue;
                            this.message = "Successfully shared";
                            this.source = FBGraphItemSharePlugin.this.getId();
                        }
                    }, activity, FBGraphItemSharePlugin.this.getId());
                }

                public void onCancel() {
                    FBGraphItemSharePlugin.this.__complain(GGErrorCode.USER_CANCELLED.getCode().intValue(), "User Cancelled", activity);
                }

                public void onError(FacebookException error) {
                    if (error instanceof FacebookOperationCanceledException) {
                        FBGraphItemSharePlugin.this.__complain(GGErrorCode.USER_CANCELLED.getCode().intValue(), "User Cancelled", activity);
                    } else {
                        FBGraphItemSharePlugin.this.__complain(error.getMessage(), activity);
                    }
                }
            });
            requestDialog.show(new GameRequestContent.Builder().setMessage(((FBGraphShareItem) this.mData).mMessageBody).setObjectId(((FBGraphShareItem) this.mData).mObjectId).setTitle(((FBGraphShareItem) this.mData).mTitle).setData(((FBGraphShareItem) this.mData).mMetaData).setRecipients(ids).setActionType(GameRequestContent.ActionType.SEND).build());
        }
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.FACEBOOK_GRAPH_SHARE;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FB_GRAPH_SHARE;
    }

    private static class FBUser {
        String uid;

        private FBUser() {
        }
    }

    private class GetFBIds extends AsyncTask<FBGraphShareItem, Void, List<FBUser>> {
        private Activity activity;

        private GetFBIds(Activity activity2) {
            this.activity = activity2;
        }

        /* access modifiers changed from: protected */
        public List<FBUser> doInBackground(FBGraphShareItem... params) {
            final FBGraphShareItem item = params[0];
            JSONObject object = SimpleNetworkClient.getInstance().makeGetRequest(SDKConstants.getFriendsInfoFromOpenId(), new HashMap<String, String>() {
                {
                    put("access_token", GGLoginSession.getCurrentSession().getTokenValue().getAuthToken());
                    put("friends", StringUtils.join(item.mFriendIDs, ","));
                }
            });
            if (object == null || !object.has("friends")) {
                FBGraphItemSharePlugin.this.__complain(GGErrorCode.NETWORK_CONNECTION_EXCEPTION.getCode().intValue(), "Cannot Reach Server", this.activity);
            } else {
                try {
                    return getFbUsers(object);
                } catch (JSONException e) {
                    BBLogger.e(e);
                    FBGraphItemSharePlugin.this.__complain("Exception raised. Cannot Parse the Data", this.activity);
                }
            }
            return null;
        }

        private List<FBUser> getFbUsers(JSONObject object) throws JSONException {
            JSONArray friendList = object.getJSONArray("friends");
            List<FBUser> users = new ArrayList<>();
            for (int i = 0; i < friendList.length(); i++) {
                final String fb_uid = friendList.getJSONObject(i).getString(GGLiveConstants.PARAM.UID);
                users.add(new FBUser() {
                    {
                        this.uid = fb_uid;
                    }
                });
            }
            return users;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(List<FBUser> fbUsers) {
            FBGraphItemSharePlugin.this.onResult(fbUsers, this.activity);
        }
    }
}
