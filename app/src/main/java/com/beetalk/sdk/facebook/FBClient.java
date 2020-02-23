package com.beetalk.sdk.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.beetalk.sdk.helper.BBLogger;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.internal.Utility;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.json.JSONObject;

public class FBClient {
    private static Context _appContext;
    private static String _appID;
    /* access modifiers changed from: private */
    public static CallbackManager _callbackManager;
    private static ArrayList<Runnable> asyncMyInfoTasks = new ArrayList<>();
    /* access modifiers changed from: private */
    public static final Handler sUILoop = new Handler(Looper.getMainLooper());

    public static void init(Context appContext, String appID) {
        _appContext = appContext;
        _appID = appID;
        _callbackManager = CallbackManager.Factory.create();
    }

    private static void authFacebook(Activity context, final Runnable run) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null || accessToken.isExpired()) {
            LoginManager.getInstance().logInWithReadPermissions(context, (Collection<String>) Arrays.asList(new String[]{"public_profile", "user_friends", "email"}));
            LoginManager.getInstance().registerCallback(_callbackManager, new FacebookCallback<LoginResult>() {
                public void onSuccess(LoginResult loginResult) {
                    FBClient.sUILoop.post(run);
                    FBClient.executeAsyncTask();
                }

                public void onCancel() {
                }

                public void onError(FacebookException error) {
                }
            });
            return;
        }
        sUILoop.post(run);
    }

    public static void requestUserInfo(Activity context, final FBRequestUserInfoCallback event) {
        authFacebook(context, new Runnable() {
            public void run() {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken == null) {
                    Profile.setCurrentProfile((Profile) null);
                } else {
                    Utility.getGraphMeRequestWithCacheAsync(accessToken.getToken(), new Utility.GraphMeRequestWithCacheCallback() {
                        public void onSuccess(JSONObject userInfo) {
                            String id = userInfo.optString("id");
                            if (id != null) {
                                String link = userInfo.optString("link");
                                Profile profile = new Profile(id, userInfo.optString("first_name"), userInfo.optString("middle_name"), userInfo.optString("last_name"), userInfo.optString("name"), link != null ? Uri.parse(link) : null);
                                Profile.setCurrentProfile(profile);
                                event.onSuccess(new FBUserInfo(profile));
                            }
                        }

                        public void onFailure(FacebookException error) {
                            event.onError();
                        }
                    });
                }
            }
        });
    }

    public static void sendInvitation(final Activity context, final String text) {
        authFacebook(context, new Runnable() {
            public void run() {
                GameRequestDialog gameRequestDialog = new GameRequestDialog(context);
                gameRequestDialog.registerCallback(FBClient._callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
                    public void onSuccess(GameRequestDialog.Result res) {
                    }

                    public void onCancel() {
                    }

                    public void onError(FacebookException error) {
                        if (error != null) {
                            Toast.makeText(context, error.toString(), 1).show();
                        }
                    }
                });
                gameRequestDialog.show(new GameRequestContent.Builder().setMessage(text).build());
            }
        });
    }

    /* access modifiers changed from: private */
    public static void executeAsyncTask() {
        Iterator<Runnable> it = asyncMyInfoTasks.iterator();
        while (it.hasNext()) {
            try {
                it.next().run();
            } catch (Exception e) {
                BBLogger.e(e);
            }
        }
        asyncMyInfoTasks.clear();
    }

    public static void clearSession(Context context) {
        _appContext = context;
        LoginManager.getInstance().logOut();
        if (asyncMyInfoTasks != null) {
            asyncMyInfoTasks.clear();
        }
    }

    public static void closeSession() {
        LoginManager.getInstance().logOut();
    }

    public static void onActivityDestory() {
        if (asyncMyInfoTasks != null) {
            asyncMyInfoTasks.clear();
        }
    }

    public static String getAppId() {
        return _appID;
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        _callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
