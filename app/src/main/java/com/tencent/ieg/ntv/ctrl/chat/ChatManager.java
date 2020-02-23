package com.tencent.ieg.ntv.ctrl.chat;

import android.content.Context;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.event.EventChatTips;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.utils.FDCircleList;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import com.tencent.ieg.ntv.view.ChatHistoryAdapter;
import java.util.List;

public class ChatManager {
    private static final String TAG = ChatManager.class.getSimpleName();
    private static ChatManager _instance;
    private ChatHistoryAdapter _adpter;
    private int _cd_send;
    private int _chat_id;
    private int _input_max_limit;
    private long _last_send_time;
    private FDCircleList<ChatMsg> _msg_list;
    private String _suid;
    private WsChatController wsChatController;

    public static ChatManager GetInstance() {
        if (_instance == null) {
            _instance = new ChatManager();
        }
        return _instance;
    }

    public static void DestoryInstance() {
        if (_instance != null) {
            _instance.destroy();
            _instance = null;
        }
    }

    public void init(boolean moduleOpen, Context context) {
        String chatId;
        Logger.d(TAG, "init moduleOpen:" + moduleOpen);
        String chatUrl = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_URL);
        Logger.d(TAG, "chatUrl from app : " + chatUrl);
        if (chatUrl.indexOf(":") < 0) {
            chatUrl = Util.getString(NTVDefine.KCONF_STRING_CHAT_URL);
        }
        this._chat_id = TVShowManager.getInstance().getConfig(NTVDefine.KCONF_APP_CHAT_ID);
        Logger.d(TAG, "chatId from app : " + this._chat_id);
        if (this._chat_id <= 0) {
            chatId = Util.getString(NTVDefine.KCONF_STRING_CHAT_ID);
        } else {
            chatId = String.valueOf(this._chat_id);
        }
        Logger.d(TAG, "current chatId : " + chatId + ", chatUrl : " + chatUrl);
        boolean _canChatOpen = !chatUrl.isEmpty() && !chatId.isEmpty() && moduleOpen;
        Util.setMConfig(NTVDefine.KCONF_CHAT_MODULE_OPEN, _canChatOpen ? "1" : "0");
        Logger.d(TAG, "init _canChatOpen:" + _canChatOpen);
        if (_canChatOpen) {
            this._cd_send = TVShowManager.getInstance().getConfig(NTVDefine.KCONF_APP_CHAT_CD);
            if (this._cd_send <= 0) {
                this._cd_send = Util.getString(NTVDefine.KCONF_STRING_CHAT_CD) != null ? Integer.parseInt(Util.getString(NTVDefine.KCONF_STRING_CHAT_CD)) : 10000;
            }
            this._last_send_time = 0;
            int listMax = TVShowManager.getInstance().getConfig(NTVDefine.KCONF_APP_CHAT_MSGLIST_SIZE);
            if (listMax <= 0) {
                listMax = Util.getString(NTVDefine.KCONF_STRING_MSGLIST_SIZE) != null ? Integer.parseInt(Util.getString(NTVDefine.KCONF_STRING_MSGLIST_SIZE)) : 20;
            }
            this._msg_list = new FDCircleList<>(listMax);
            this._input_max_limit = TVShowManager.getInstance().getConfig(NTVDefine.KCONF_APP_CHAT_MAX_INPUT);
            if (this._input_max_limit <= 0) {
                this._input_max_limit = Util.getString(NTVDefine.KCONF_STRING_CHAT_MAX_INPUT) != null ? Integer.parseInt(Util.getString(NTVDefine.KCONF_STRING_CHAT_MAX_INPUT)) : 40;
            }
            setSelfUid(Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID));
            this.wsChatController = new WsChatController();
            this.wsChatController.init(context, chatUrl, chatId);
            return;
        }
        Logger.d(TAG, "ntvs chat module close,url:" + chatUrl + ",id:" + chatId);
    }

    private void destroy() {
        if (this.wsChatController != null) {
            this.wsChatController.destroy();
            this.wsChatController = null;
        }
        if (this._msg_list != null) {
            this._msg_list.destroy();
        }
    }

    public void setAdpter(ChatHistoryAdapter adpter) {
        this._adpter = adpter;
    }

    public void clearMsgList() {
        if (this._msg_list != null) {
            this._msg_list.clear();
        } else {
            Logger.e(TAG, "msgList is null");
        }
    }

    public void onReceiveChatMsg(String nickName, String msg, String headImgUri, String uid, String admin) {
        if (this._msg_list == null) {
            Logger.e(TAG, "msgList is null");
        } else if (this._msg_list.getNextObj() != null) {
            this._msg_list.getNextObj().uid = uid;
            this._msg_list.getNextObj().nickName = nickName;
            this._msg_list.getNextObj().msg = msg;
            this._msg_list.getNextObj().headImgUri = headImgUri;
            this._msg_list.getNextObj().admin = admin;
            this._msg_list.gotoNext();
        } else {
            ChatMsg cm = new ChatMsg();
            cm.headImgUri = headImgUri;
            cm.msg = msg;
            cm.uid = uid;
            cm.nickName = nickName;
            cm.admin = admin;
            this._msg_list.push(cm);
        }
    }

    public void sendNewMsg(String msg) {
        Logger.d(TAG, "sendNewMsg msg:" + msg);
        if (msg == null) {
            return;
        }
        if (msg != null && msg.length() == 0) {
            return;
        }
        if (this.wsChatController == null || this.wsChatController.isLoginSuc()) {
            String strBanchat = Util.getMConfig(NTVDefine.KEY_MCONF_GAME_CANSPEAK);
            if (strBanchat != null && strBanchat.toLowerCase().equals("true")) {
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_FORBID)));
            } else if (msg == null || msg.length() <= this._input_max_limit) {
                long curMSec = System.currentTimeMillis();
                Logger.d(TAG, "sendNewMsg , curms = " + curMSec + ", lastms = " + this._last_send_time + ", cd = " + this._cd_send);
                if (this._last_send_time + ((long) this._cd_send) < curMSec) {
                    this.wsChatController.sendChatMsg(msg);
                    this._last_send_time = curMSec;
                    return;
                }
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_CD_LIMIT)));
            } else {
                String txt = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_MSG_LENGTHLIMIT);
                if (txt != null) {
                    txt = txt.replace("{0}", String.valueOf(this._input_max_limit)).replace("{1}", String.valueOf(msg.length()));
                }
                EventManager.getInstance().post(5020, new EventChatTips(true, txt));
            }
        } else {
            EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_SENDMSG_USER_LOGIN_FAIL)));
        }
    }

    public ChatMsg getHistoryDataWithIdx(int idx) {
        if (this._msg_list != null) {
            return this._msg_list.getDataWithIdx(idx);
        }
        Logger.e(TAG, "msgList is null");
        return null;
    }

    public List<ChatMsg> getAllHistoryData() {
        if (this._msg_list != null) {
            return this._msg_list.getAllData();
        }
        Logger.e(TAG, "msgList is null");
        return null;
    }

    public int getHistoryCount() {
        if (this._msg_list != null) {
            return this._msg_list.count();
        }
        Logger.e(TAG, "msgList is null");
        return 0;
    }

    public void notifyChatHistoryDataSetChanged() {
        if (this._adpter != null) {
            this._adpter.notifyDataSetChanged();
        } else {
            Logger.d(TAG, "notifyChatHistoryDataSetChanged:_adapter is null");
        }
    }

    private void setSelfUid(String suid) {
        this._suid = suid;
    }

    public boolean isSelf(String uid) {
        Logger.d(TAG, "self.uid:" + this._suid + ", msg_uid:" + uid);
        return uid.equals(this._suid);
    }
}
