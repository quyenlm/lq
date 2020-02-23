package com.beetalk.sdk.plugin;

import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.helper.BBLogger;
import com.tencent.ieg.ntv.ctrl.chat.ChatMsg;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PluginUtil {
    public static Vector<DataModel.FriendGroup> parseFriendGroups(JSONObject response) {
        Vector<DataModel.FriendGroup> friendGroupList = new Vector<>();
        try {
            JSONArray jsonArray = response.getJSONArray("friends_groups");
            for (int i = 0; i < jsonArray.length(); i++) {
                DataModel.FriendGroup friendGroup = new DataModel.FriendGroup();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                friendGroup.platform = jsonObject.optInt("platform");
                JSONArray jsonIdsArray = jsonObject.optJSONArray("friends");
                JSONArray jsonUidsArray = jsonObject.optJSONArray("uids");
                int openIdCount = jsonIdsArray == null ? 0 : jsonIdsArray.length();
                int uidCount = jsonUidsArray == null ? 0 : jsonUidsArray.length();
                friendGroup.ids = new Vector<>(openIdCount);
                friendGroup.idInfoList = new Vector<>(openIdCount);
                if (jsonIdsArray != null) {
                    for (int j = 0; j < openIdCount; j++) {
                        DataModel.FriendIdInfo info = new DataModel.FriendIdInfo();
                        info.openId = jsonIdsArray.getString(j);
                        if (j < uidCount) {
                            info.uid = jsonUidsArray.getLong(j);
                        }
                        friendGroup.ids.add(info.openId);
                        friendGroup.idInfoList.add(info);
                    }
                }
                friendGroupList.add(friendGroup);
            }
            return friendGroupList;
        } catch (Exception e) {
            BBLogger.e(e);
            return null;
        }
    }

    public static Vector<DataModel.GroupUserInfo> parseGroupUserInfoList(JSONObject response) {
        Vector<DataModel.GroupUserInfo> groupUserInfoList = new Vector<>();
        try {
            JSONArray jsonArray = response.getJSONArray("friends");
            for (int i = 0; i < jsonArray.length(); i++) {
                DataModel.GroupUserInfo groupUserInfo = new DataModel.GroupUserInfo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                groupUserInfo.openID = jsonObject.optString("open_id");
                groupUserInfo.platform = jsonObject.optInt("platform");
                groupUserInfo.gender = jsonObject.optInt("gender");
                groupUserInfo.nickName = jsonObject.optString(ChatMsg.KEY_NICK_NAME);
                groupUserInfo.iconURL = jsonObject.optString("icon");
                groupUserInfoList.add(groupUserInfo);
            }
            return groupUserInfoList;
        } catch (JSONException e) {
            BBLogger.e(e);
            return null;
        }
    }
}
