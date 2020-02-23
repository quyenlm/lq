package com.tencent.ieg.ntv.network;

import com.banalytics.BATrackerConst;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class WsMessage {

    public static class ChatMsg {
        public String admin;
        public String msg;
        public String userid;
        public String username;
    }

    public static class LoginResult {
        public static int FAIL_ACCOUNT = -2;
        public static int FAIL_TOKEN = -1;
        public static int SUCCESS = 0;
    }

    public static class MessageType {
        public static int HEART_BEAT_REQ = 9;
        public static int HEART_BEAT_RES = 10;
        public static int LOGIN_REQ = 1;
        public static int LOGIN_RES = 2;
        public static int MSG_SEND_REQ = 3;
        public static int MSG_SEND_RES = 4;
        public static int MSG_SVR_PUSH = 6;
        public static int NOTICE_SVR_PUSH = 8;
        public static int SERVER_STATUS = 12;
    }

    public static class SpeakResult {
        public static int FAIL_JY = -2;
        public static int FAIL_LM = -4;
        public static int FAIL_MG = -1;
        public static int FAIL_NU = -3;
        public static int FAIL_RS = -5;
        public static int SUCCESS = 0;
    }

    public static class LoginMsg_Req {
        public String biz;
        public String chatid;
        public String env;
        public String token;
        public int type = MessageType.LOGIN_REQ;
        public String usericon;
        public String userid;
        public String username;

        public String toString() {
            try {
                JSONObject obj = new JSONObject();
                obj.put("type", this.type);
                obj.put("token", this.token);
                obj.put("biz", this.biz);
                obj.put("env", this.env);
                obj.put(BATrackerConst.JSON_KEYS.USER_ID, this.userid);
                obj.put("username", this.username);
                obj.put("usericon", this.usericon);
                obj.put("chatid", this.chatid);
                return obj.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class LoginMsg_Res {
        public String msg;
        public int result;
        public int type;

        public static LoginMsg_Res parse(String jsonStr) {
            try {
                LoginMsg_Res mmsg = new LoginMsg_Res();
                JSONObject obj = new JSONObject(jsonStr);
                if (obj != null) {
                    mmsg.type = obj.optInt("type");
                    mmsg.result = obj.optInt(GGLiveConstants.PARAM.RESULT);
                    mmsg.msg = obj.optString("msg");
                    return mmsg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class SpeakMsg_Req {
        public String msg;
        public int type = MessageType.MSG_SEND_REQ;

        public String toString() {
            try {
                JSONObject obj = new JSONObject();
                obj.put("type", this.type);
                obj.put("msg", this.msg);
                return obj.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class SpeakMsg_Res {
        public int result;
        public int type;

        public static SpeakMsg_Res parse(String jsonStr) {
            try {
                SpeakMsg_Res mmsg = new SpeakMsg_Res();
                JSONObject obj = new JSONObject(jsonStr);
                if (obj != null) {
                    mmsg.type = obj.optInt("type");
                    mmsg.result = obj.optInt(GGLiveConstants.PARAM.RESULT);
                    return mmsg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class ServerPushChatMsg {
        public List<ChatMsg> list;
        public int type;

        public static ServerPushChatMsg parse(String jsonStr) {
            try {
                ServerPushChatMsg mmsg = new ServerPushChatMsg();
                JSONObject obj = new JSONObject(jsonStr);
                if (obj != null) {
                    mmsg.type = obj.optInt("type");
                    mmsg.list = new LinkedList();
                    JSONArray arr = obj.optJSONArray(HttpRequestParams.NOTICE_LIST);
                    if (arr == null || arr.length() <= 0) {
                        return mmsg;
                    }
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jobj = arr.getJSONObject(i);
                        ChatMsg chatMsg = new ChatMsg();
                        chatMsg.userid = jobj.optString(BATrackerConst.JSON_KEYS.USER_ID);
                        chatMsg.username = jobj.optString("username");
                        chatMsg.msg = jobj.optString("msg");
                        chatMsg.admin = jobj.optString("admin");
                        mmsg.list.add(chatMsg);
                    }
                    return mmsg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class ServerNoticeMsg {
        public String msg;
        public int type;

        public static ServerNoticeMsg parse(String jsonStr) {
            try {
                ServerNoticeMsg mmsg = new ServerNoticeMsg();
                JSONObject obj = new JSONObject(jsonStr);
                if (obj != null) {
                    mmsg.type = obj.optInt("type");
                    mmsg.msg = obj.optString("msg");
                    return mmsg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class ServerStatus {
        public int chatnum;
        public int type;

        public static ServerStatus parse(String jsonStr) {
            try {
                ServerStatus mmsg = new ServerStatus();
                JSONObject obj = new JSONObject(jsonStr);
                if (obj != null) {
                    mmsg.type = obj.optInt("type");
                    mmsg.chatnum = obj.optInt("chatnum");
                    return mmsg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
