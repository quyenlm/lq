package com.tencent.ieg.ntv;

import android.app.Activity;
import com.tencent.ieg.ntv.ctrl.chat.ChatManager;
import com.tencent.ieg.ntv.ctrl.player.PlayerPopUpController;
import com.tencent.ieg.ntv.ctrl.player.PopupPlayerActivity;
import com.tencent.ieg.ntv.model.DataForReport;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.network.CDNFileManager;
import com.tencent.ieg.ntv.network.NetworkModule;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import com.tencent.ieg.ntv.view.NTVShowActivity;
import java.util.HashMap;
import org.json.JSONObject;

public class TVShowManager {
    private static final String TAG = TVShowManager.class.getSimpleName();
    private static TVShowManager _instance;
    private String[] _configKeySets = {NTVDefine.KCONF_APP_CHAT_MSGLIST_SIZE, NTVDefine.KCONF_APP_CHAT_CD, NTVDefine.KCONF_APP_CHAT_MAX_INPUT, NTVDefine.KCONF_APP_CHAT_ID};
    private Activity _curActivity;
    private HashMap<String, Integer> _intConfigData = new HashMap<>();
    private HashMap<String, String> _internationTextData = new HashMap<>();
    private String[] _textKeySets = {NTVDefine.KEY_NTV_SVR, NTVDefine.KEY_POPUP_CLOSE_BTN_TEXT, NTVDefine.KEY_POPUP_JUMP_BTN_TEXT, NTVDefine.KEY_LOBBY_TAB_0_NAME, NTVDefine.KEY_CHAT_INPUT_HINT, NTVDefine.KEY_LOBBY_TAB_1_NAME, NTVDefine.KEY_NOT_WIFI_TIP_TEXT, NTVDefine.KEY_NOT_WIFI_BTN_TEXT, NTVDefine.KEY_NETWORK_ERROR, NTVDefine.KEY_MATCH_STATUS_FINISH, NTVDefine.KEY_LOADING_VIDEO, NTVDefine.KEY_VIDEO_TYPE_LIVE, NTVDefine.KEY_VIDEO_TYPE_REPLAY, NTVDefine.KEY_CHAT_FORBID, NTVDefine.KEY_CHAT_FORBID_BYSERVER, NTVDefine.KEY_CHAT_CD_LIMIT, NTVDefine.KEY_CHAT_WORD_BLOCK, NTVDefine.KEY_CHAT_AUTHOR_FAIL, NTVDefine.KEY_CHAT_SENDMSG_COMMON_FAIL, NTVDefine.KEY_CHAT_SENDMSG_USER_NOT_FOUND, NTVDefine.KEY_CHAT_SENDMSG_USER_DUPLICATE, NTVDefine.KEY_CHAT_SENDMSG_USER_LOGIN_FAIL, NTVDefine.KEY_CHAT_MSG_LENGTHLIMIT, NTVDefine.KEY_CHAT_URL, NTVDefine.KEY_MATCH_STATUS_RESERVABLE, NTVDefine.KEY_MATCH_STATUS_RESERVED, NTVDefine.KEY_MATCH_STATUS_REPLAY, NTVDefine.KEY_HOLDER_BONUS_DIALOG_TITLE, NTVDefine.KEY_HOLDER_BONUS_REWARD_RECEIVE_TITLE, NTVDefine.KEY_HOLDER_BONUS_CAN_RECEIVE, NTVDefine.KEY_HOLDER_BONUS_REWARD_TITLE, NTVDefine.KEY_HOLDER_BONUS_REWARD_END_TIME, NTVDefine.KEY_HOLDER_BONUS_REWARD_TASK_DESC, NTVDefine.KEY_HOLDER_BONUS_REWARD_TASK_BTNRECEIVE, NTVDefine.KEY_HOLDER_BONUS_REWARD_TASK_RECEIVED, NTVDefine.KEY_HOLDER_BONUS_REWARD_RECEIVE_DESC, NTVDefine.KEY_HOLDER_BONUS_DIALOG_OK_BTN};

    public static TVShowManager getInstance() {
        if (_instance == null) {
            _instance = new TVShowManager();
        }
        return _instance;
    }

    public static boolean destroyInstance() {
        if (_instance == null) {
            return false;
        }
        _instance = null;
        return true;
    }

    private TVShowManager() {
    }

    public void init(Activity context) {
        setActivity(context);
        CDNFileManager.getInstance().init(context);
    }

    public void initI18NText(String jsonStr) {
        String value;
        try {
            JSONObject jo = new JSONObject(jsonStr);
            for (String key : this._textKeySets) {
                if (jo.has(key)) {
                    value = jo.getString(key);
                } else {
                    value = key;
                }
                Logger.d(TAG, "initI18NText - key:" + key + ", value:" + value);
                this._internationTextData.put(key, value);
            }
        } catch (Exception e) {
            Logger.w(TAG, "initI18NText json error");
            Logger.w(TAG, e);
        }
    }

    public void initIntConfig(String jsonStr) {
        if (jsonStr != null) {
            try {
                JSONObject jo = new JSONObject(jsonStr);
                for (String key : this._configKeySets) {
                    if (jo.has(key)) {
                        String value = String.valueOf(jo.get(key));
                        Logger.d(TAG, "InitIntCongig KEY = " + key + ", value = " + value);
                        this._intConfigData.put(key, Integer.valueOf(Integer.parseInt(value)));
                    } else {
                        Logger.d(TAG, "InitIntCongig KEY = " + key + ", value not found. ");
                    }
                }
            } catch (Exception e) {
                Logger.w(TAG, "InitIntCongig json error");
                Logger.w(TAG, e);
            }
        }
    }

    public String getI18NText(String key) {
        if (this._internationTextData.containsKey(key)) {
            return this._internationTextData.get(key);
        }
        Logger.d(TAG, "getI18NText value not found, use default. key:" + key);
        return key;
    }

    public int getConfig(String key) {
        if (this._intConfigData.containsKey(key)) {
            return this._intConfigData.get(key).intValue();
        }
        Logger.d(TAG, "getConfig value not found, use default. key:" + key);
        return 0;
    }

    public void closeTVShow() {
        closeActivity();
        NetworkModule.getInstance().destroy();
        ChatManager.DestoryInstance();
        DataForReport.getInstance().destroy();
    }

    public void setActivity(Activity activity) {
        this._curActivity = activity;
        Util.setContext(activity);
    }

    private void closeActivity() {
        if (this._curActivity == null) {
            return;
        }
        if ((this._curActivity instanceof PopupPlayerActivity) || (this._curActivity instanceof NTVShowActivity)) {
            this._curActivity.finish();
        }
    }

    public void showPopup(String uid, String token, String lang, String ticket, String username, String banchat, String firebaseToken) {
        Logger.d(TAG, "showPopup");
        if (this._curActivity != null) {
            Logger.d(TAG, "showPopup in.");
            setLanguage(lang);
            setAccountInfo(uid, token, ticket, username, banchat);
            setFirebaseToken(firebaseToken);
            NetworkModule.getInstance().init();
            PlayerPopUpController.getInstance().init(this._curActivity);
        }
    }

    public void setLanguage(String language) {
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_LANGUAGE, language);
    }

    public void setFirebaseToken(String ftoken) {
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_FIREBASETOKEN, ftoken);
    }

    public void setAccountInfo(String openid, String token, String ticket, String username, String banchat) {
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_OPENID, openid);
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_TOKEN, token);
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_TICKET, ticket);
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_USERNAME, username);
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_CANSPEAK, banchat);
    }

    public void setGameInfo(String gameid, String areaid, String partitionid) {
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_GAMEID, gameid);
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_AREAID, areaid);
        Util.setMConfig(NTVDefine.KEY_MCONF_GAME_PARTITIONID, partitionid);
    }

    public void onReportEvent(String eventName) {
        NTVShowHelperAndroid.OnReportEvent(eventName);
    }

    public void onCloseView(String type) {
        NetworkModule.getInstance().destroy();
        ChatManager.DestoryInstance();
        NTVShowHelperAndroid.OnCloseView(type);
        DataForReport.getInstance().destroy();
    }

    public void onCheckPopup(boolean popup) {
        int i = 1;
        Object[] objArr = new Object[1];
        if (!popup) {
            i = 0;
        }
        objArr[0] = Integer.valueOf(i);
        String result = String.format("{\"needsPopup\":%d}", objArr);
        Logger.d(TAG, "onCheckPopup: " + result);
        NTVShowHelperAndroid.OnCheckPopup(result);
    }
}
