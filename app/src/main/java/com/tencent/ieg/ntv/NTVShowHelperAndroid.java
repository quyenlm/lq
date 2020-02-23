package com.tencent.ieg.ntv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.google.android.gms.drive.DriveFile;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.view.NTVShowActivity;
import com.unity3d.player.UnityPlayer;
import com.vk.sdk.api.VKApiConst;

public class NTVShowHelperAndroid {
    private static final String GAME_OBJECT_NAME = "NTVSHOW_HELPER_OBJECT";
    private static String mAreaId = "";
    private static Context mContext;
    private static String mGameId = "";
    private static String mI18NTextStr;
    private static String mIntConfigStr;
    private static boolean mIsUnity = false;
    private static String mPartitionId = "";

    private static void log(String msg) {
        Logger.d(NTVShowHelperAndroid.class.getSimpleName(), msg);
    }

    private static void Init() {
        log("Init");
        mIsUnity = true;
        mContext = UnityPlayer.currentActivity;
        TVShowManager.getInstance().init(UnityPlayer.currentActivity);
    }

    public static void Init(Activity context) {
        log("Init");
        mContext = context;
        TVShowManager.getInstance().init(context);
    }

    public static void SetLogEnable(boolean enable) {
        log("SetLogEnable enable:" + enable);
        Logger.setEnable(enable);
    }

    public static void InitI18NText(String text) {
        log("InitI18NText text:" + text);
        mI18NTextStr = text;
        TVShowManager.getInstance().initI18NText(text);
    }

    public static void InitIntCongig(String text) {
        log("InitIntCongig text:" + text);
        mIntConfigStr = text;
        TVShowManager.getInstance().initIntConfig(text);
    }

    public static void SetGameInfo(String gameid, String areaid, String partitionid) {
        log("SetGameInfo gameid:" + gameid + ", areaid:" + areaid + ", partitionid:" + partitionid);
        TVShowManager.getInstance().setGameInfo(gameid, areaid, partitionid);
        mGameId = gameid;
        mAreaId = areaid;
        mPartitionId = partitionid;
    }

    public static void ShowTVView(String uid, String token, String lang, String ticket, String username, String banchat, String firebasetoken) {
        String sfirebasetoken;
        log("ShowTVView uid:" + uid + ", token:" + token + ", lang:" + lang + ", ticket:" + ticket + ", username:" + username + ", banchat" + banchat + ", firebasetoken" + firebasetoken);
        if (firebasetoken == null) {
            sfirebasetoken = "";
        } else {
            sfirebasetoken = firebasetoken;
        }
        Intent intent = new Intent(mContext, NTVShowActivity.class);
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        intent.putExtra("enableLog", Logger.getEnable());
        intent.putExtra(GGLiveConstants.PARAM.UID, uid);
        intent.putExtra("token", token);
        intent.putExtra(VKApiConst.LANG, lang);
        intent.putExtra("ticket", ticket);
        intent.putExtra("username", username);
        intent.putExtra("banchat", banchat);
        intent.putExtra("I18NTextStr", mI18NTextStr);
        intent.putExtra("IntConfigStr", mIntConfigStr);
        intent.putExtra("gameid", mGameId);
        intent.putExtra("areaid", mAreaId);
        intent.putExtra("partitionid", mPartitionId);
        intent.putExtra("firebasetoken", sfirebasetoken);
        mContext.startActivity(intent);
    }

    public static void TryShowPopupBanner(String uid, String token, String lang, String ticket, String username, String banchat, String firebasetoken) {
        final String sfirebasetoken;
        log("TryShowPopupBanner uid:" + uid + ", token:" + token + ", lang:" + lang + ", ticket:" + ticket + ", username:" + username + ", banchat" + banchat + ", firebasetoken" + firebasetoken);
        if (firebasetoken == null) {
            sfirebasetoken = "";
        } else {
            sfirebasetoken = firebasetoken;
        }
        if (!mIsUnity) {
            TVShowManager.getInstance().showPopup(uid, token, lang, ticket, username, banchat, sfirebasetoken);
        } else if (UnityPlayer.currentActivity != null) {
            TVShowManager.getInstance().init(UnityPlayer.currentActivity);
            final String str = uid;
            final String str2 = token;
            final String str3 = lang;
            final String str4 = ticket;
            final String str5 = username;
            final String str6 = banchat;
            UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    TVShowManager.getInstance().showPopup(str, str2, str3, str4, str5, str6, sfirebasetoken);
                }
            });
        }
    }

    private static void CloseTVShow() {
        CloseTVShow(true);
    }

    public static void CloseTVShow(boolean isUnity) {
        log("CloseTVShow");
        if (isUnity) {
            UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    TVShowManager.getInstance().closeTVShow();
                    TVShowManager.destroyInstance();
                }
            });
            return;
        }
        TVShowManager.getInstance().closeTVShow();
        TVShowManager.destroyInstance();
    }

    private static void CleanUp() {
        log("CleanUp");
    }

    public static void OnNewIntent(Intent intent) {
        log("OnNewIntent");
        if (intent != null) {
            String ntvResult = intent.getStringExtra(NTVDefine.XP_RESULT_KEY);
            log("ntvResult:" + ntvResult);
            if (ntvResult != null && ntvResult.equals(NTVDefine.XP_CLOSE_CENTER)) {
                OnCloseView("1");
            }
        }
    }

    public static void OnReportEvent(String eventName) {
        log("OnReportEvent eventName:" + eventName);
        if (mIsUnity) {
            UnityPlayer.UnitySendMessage(GAME_OBJECT_NAME, "OnReportEvent", eventName);
        }
    }

    public static void OnCloseView(String typeString) {
        log("OnCloseView typeString:" + typeString);
        if (mIsUnity) {
            UnityPlayer.UnitySendMessage(GAME_OBJECT_NAME, "OnCloseView", typeString);
        }
    }

    public static void OnCheckPopup(String result) {
        log("OnCheckPopup result:" + result);
        if (mIsUnity) {
            UnityPlayer.UnitySendMessage(GAME_OBJECT_NAME, "OnCheckPopup", result);
        }
    }
}
