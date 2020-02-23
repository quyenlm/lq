package com.tencent.ieg.ntv.ctrl.chat;

import com.neovisionaries.ws.client.WebSocket;
import com.tencent.ieg.ntv.network.WsManager;
import com.tencent.ieg.ntv.network.WsMessage;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public class WsMsgQueue {
    private final String TAG = getClass().getSimpleName();
    private Timer _timer;
    private List<String> msgList = null;
    private int msgListMax = 3;
    private int sendCD = 1000;
    private WebSocket webs;
    private WsManager.WsStatus wstatus;

    public void addMsg(String msg) {
        if (this.msgList == null) {
            this.msgList = new LinkedList();
        }
        this.msgList.add(msg);
        if (this.msgList.size() > this.msgListMax) {
            removeMsg();
        }
    }

    public String getMsg() {
        if (this.msgList == null || this.msgList.size() <= 0) {
            return null;
        }
        return this.msgList.get(0);
    }

    public void removeMsg() {
        if (this.msgList != null && this.msgList.size() > 0) {
            this.msgList.remove(0);
        }
    }

    public void cleanMsg() {
        if (this.msgList != null) {
            this.msgList.clear();
        }
    }

    public void checkMsgQueue(WebSocket ws, WsManager.WsStatus wsStatus, String result) {
        WsMessage.LoginMsg_Res loginMsg_res;
        if (getMsg() != null) {
            this.webs = ws;
            this.wstatus = wsStatus;
            try {
                if (new JSONObject(result).getInt("type") == WsMessage.MessageType.LOGIN_RES && (loginMsg_res = WsMessage.LoginMsg_Res.parse(result)) != null && loginMsg_res.result == WsMessage.LoginResult.SUCCESS) {
                    startMsgSendTimer();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startMsgSendTimer() {
        if (this._timer != null) {
            this._timer.cancel();
        }
        this._timer = new Timer();
        this._timer.schedule(new sendMsgTimerTask(), (long) this.sendCD, (long) this.sendCD);
    }

    private class sendMsgTimerTask extends TimerTask {
        private sendMsgTimerTask() {
        }

        public void run() {
            WsMsgQueue.this.sendMsg();
        }
    }

    /* access modifiers changed from: private */
    public void sendMsg() {
        if (getMsg() == null) {
            if (this._timer != null) {
                this._timer.cancel();
            }
        } else if (this.webs != null && this.webs.isOpen() && this.wstatus == WsManager.WsStatus.CONNECT_SUCCESS) {
            Logger.d(this.TAG, "wsmodule sendMessage : " + getMsg());
            this.webs.sendText(getMsg());
            removeMsg();
        }
    }
}
