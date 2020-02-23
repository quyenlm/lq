package com.tencent.ieg.ntv.ctrl.chat;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventChatTips;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.network.WsManager;
import com.tencent.ieg.ntv.network.WsMessage;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public class WsChatController {
    /* access modifiers changed from: private */
    public static final String TAG = WsChatController.class.getSimpleName();
    private Timer _timer;
    private String chatid = "";
    /* access modifiers changed from: private */
    public Timer comTipsTimer = null;
    private boolean loginSuc;
    /* access modifiers changed from: private */
    public Context mContext;
    private IEventListener onComTipsEvt = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            final EventChatTips tipsEvt = (EventChatTips) event;
            ((Activity) WsChatController.this.mContext).runOnUiThread(new Runnable() {
                public void run() {
                    EventManager.getInstance().post(5021, tipsEvt);
                }
            });
            if (WsChatController.this.comTipsTimer != null) {
                WsChatController.this.comTipsTimer.cancel();
            }
            Timer unused = WsChatController.this.comTipsTimer = new Timer();
            WsChatController.this.comTipsTimer.schedule(new TimerTask() {
                public void run() {
                    ((Activity) WsChatController.this.mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            EventManager.getInstance().post(5021, new EventChatTips(false, ""));
                        }
                    });
                    WsChatController.this.comTipsTimer.cancel();
                }
            }, 3000);
        }
    };
    private WsManager.WsCallback wsCallback = new WsManager.WsCallback() {
        public void onConnected(WsManager.WsStatus status) {
            Logger.d(WsChatController.TAG, "onConnected : status = " + status);
            if (status == WsManager.WsStatus.CONNECT_SUCCESS) {
                WsChatController.this.sendLogin();
            }
        }

        public void onMessage(String msg) {
            Logger.d(WsChatController.TAG, "wsmodule onMessage : " + msg);
            if (msg != null) {
                try {
                    int type = new JSONObject(msg).optInt("type");
                    if (type == WsMessage.MessageType.LOGIN_RES) {
                        WsChatController.this.onLogin(WsMessage.LoginMsg_Res.parse(msg));
                    } else if (type == WsMessage.MessageType.MSG_SEND_RES) {
                        WsChatController.this.onSendChatMsgResult(WsMessage.SpeakMsg_Res.parse(msg));
                    } else if (type == WsMessage.MessageType.MSG_SVR_PUSH) {
                        WsChatController.this.onRecvServerChatMsg(WsMessage.ServerPushChatMsg.parse(msg));
                    } else if (type == WsMessage.MessageType.NOTICE_SVR_PUSH) {
                        WsChatController.this.onRecvServerNoticeMsg(WsMessage.ServerNoticeMsg.parse(msg));
                    } else if (type == WsMessage.MessageType.SERVER_STATUS) {
                        WsChatController.this.onRecvServerStatus(WsMessage.ServerStatus.parse(msg));
                    } else if (type == WsMessage.MessageType.HEART_BEAT_RES) {
                        Logger.d(WsChatController.TAG, "onHeartBeat msg:" + msg);
                    } else {
                        Logger.d(WsChatController.TAG, "onMessage msg:" + msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.d(WsChatController.TAG, "onMessage Exception : " + e.toString());
                }
            }
        }
    };
    private WsManager wsManager;

    public void init(Context context, String chatUrl, String _chatid) {
        Logger.d(TAG, "init chatUrl:" + chatUrl + ", chatid:" + _chatid);
        this.chatid = _chatid;
        this.mContext = context;
        this.wsManager = new WsManager();
        this.wsManager.setWsMsgQueue(new WsMsgQueue());
        this.wsManager.wsCallback = this.wsCallback;
        this.wsManager.init(chatUrl, Util.getString(NTVDefine.KCONF_STRING_CHAT_PROTOCAL), context);
        EventManager.getInstance().register(5020, this.onComTipsEvt);
    }

    /* access modifiers changed from: private */
    public void sendLogin() {
        WsMessage.LoginMsg_Req loginMsg_req = new WsMessage.LoginMsg_Req();
        loginMsg_req.token = Util.getMConfig(NTVDefine.KEY_MCONF_GAME_TICKET);
        loginMsg_req.userid = Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID);
        loginMsg_req.username = Util.getMConfig(NTVDefine.KEY_MCONF_GAME_USERNAME);
        loginMsg_req.usericon = "";
        loginMsg_req.biz = getBizByGameid(Util.getMConfig(NTVDefine.KEY_MCONF_GAME_GAMEID));
        loginMsg_req.env = Util.getString(NTVDefine.KCONF_STRING_CHAT_ENV);
        loginMsg_req.chatid = this.chatid;
        this.wsManager.sendMessage(loginMsg_req.type, loginMsg_req.toString());
    }

    private String getBizByGameid(String gameid) {
        String biz = Util.getString("NTVS_CHAT_BIZ_" + gameid);
        if (biz == null || biz.isEmpty() || biz.length() == 0) {
            return Util.getString(NTVDefine.KCONF_STRING_CHAT_BIZ);
        }
        return biz;
    }

    public boolean isLoginSuc() {
        return this.loginSuc;
    }

    /* access modifiers changed from: private */
    public void onLogin(WsMessage.LoginMsg_Res msg) {
        if (msg != null) {
            if (msg.result == WsMessage.LoginResult.SUCCESS) {
                this.loginSuc = true;
                startWsHeartBeat();
            } else if (msg.result == WsMessage.LoginResult.FAIL_TOKEN) {
                this.loginSuc = false;
                stopWsHearBeat();
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_AUTHOR_FAIL)));
            } else if (msg.result == WsMessage.LoginResult.FAIL_ACCOUNT) {
                this.loginSuc = false;
                stopWsHearBeat();
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_SENDMSG_USER_DUPLICATE)));
            }
        }
    }

    public void sendChatMsg(String msg) {
        if (this.loginSuc) {
            WsMessage.SpeakMsg_Req speakMsg_req = new WsMessage.SpeakMsg_Req();
            speakMsg_req.msg = msg;
            this.wsManager.sendMessage(speakMsg_req.type, speakMsg_req.toString());
        }
    }

    /* access modifiers changed from: private */
    public void onSendChatMsgResult(WsMessage.SpeakMsg_Res msg) {
        Logger.d(TAG, "onSendChatMsgResult in.");
        if (msg != null) {
            Logger.d(TAG, "onSendChatMsgResult result = " + msg.result);
            if (msg.result == WsMessage.SpeakResult.SUCCESS) {
                ((Activity) this.mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        EventManager.getInstance().post(5022, (Event) null);
                    }
                });
            } else if (msg.result == WsMessage.SpeakResult.FAIL_MG) {
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_WORD_BLOCK)));
            } else if (msg.result == WsMessage.SpeakResult.FAIL_JY) {
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_FORBID_BYSERVER)));
            } else if (msg.result == WsMessage.SpeakResult.FAIL_NU) {
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_SENDMSG_USER_NOT_FOUND)));
            } else if (msg.result == WsMessage.SpeakResult.FAIL_LM) {
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_CD_LIMIT)));
            } else if (msg.result == WsMessage.SpeakResult.FAIL_RS) {
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_SENDMSG_USER_LOGIN_FAIL)));
            } else {
                EventManager.getInstance().post(5020, new EventChatTips(true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_SENDMSG_COMMON_FAIL)));
            }
        }
    }

    /* access modifiers changed from: private */
    public void onRecvServerChatMsg(WsMessage.ServerPushChatMsg msg) {
        if (msg != null && msg.list != null && msg.list.size() > 0) {
            for (int i = 0; i < msg.list.size(); i++) {
                WsMessage.ChatMsg chatMsg = msg.list.get(i);
                ChatManager.GetInstance().onReceiveChatMsg(chatMsg.username, chatMsg.msg, "", chatMsg.userid, chatMsg.admin);
            }
            ((Activity) this.mContext).runOnUiThread(new Runnable() {
                public void run() {
                    ChatManager.GetInstance().notifyChatHistoryDataSetChanged();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void onRecvServerNoticeMsg(WsMessage.ServerNoticeMsg msg) {
        if (msg == null) {
        }
    }

    /* access modifiers changed from: private */
    public void onRecvServerStatus(WsMessage.ServerStatus msg) {
        if (msg == null) {
        }
    }

    /* access modifiers changed from: private */
    public void sendHearBeat() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", WsMessage.MessageType.HEART_BEAT_REQ);
            this.wsManager.sendMessage(WsMessage.MessageType.HEART_BEAT_REQ, jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startWsHeartBeat() {
        if (this._timer != null) {
            this._timer.cancel();
        }
        this._timer = new Timer();
        this._timer.schedule(new WsTimerTask(), Constants.ACTIVE_THREAD_WATCHDOG, NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS);
    }

    private void stopWsHearBeat() {
        if (this._timer != null) {
            this._timer.cancel();
        }
    }

    private class WsTimerTask extends TimerTask {
        private WsTimerTask() {
        }

        public void run() {
            WsChatController.this.sendHearBeat();
        }
    }

    public void destroy() {
        EventManager.getInstance().unregister(5020, this.onComTipsEvt);
        if (this._timer != null) {
            this._timer.cancel();
        }
        if (this.comTipsTimer != null) {
            this.comTipsTimer.cancel();
        }
        if (this.wsManager != null) {
            this.wsManager.disconnect();
        }
    }
}
