package com.beetalk.sdk.data;

import java.util.Vector;

public class DataModel {

    public static class FriendGroup {
        public Vector<FriendIdInfo> idInfoList;
        public Vector<String> ids;
        public int platform;
    }

    public static class FriendGroupsRet {
        public int flag;
        public Vector<FriendGroup> groups;
    }

    public static class FriendIDsRet {
        public int flag;
        public Vector<FriendIdInfo> idInfoList;
        public Vector<String> ids;
        public int platform;
    }

    public static class FriendIdInfo {
        public String openId;
        public long uid;
    }

    public static class FriendsInfoRet {
        public int flag;
        public Vector<UserInfo> info;
        public int platform;
    }

    public interface GGLIVE_STREAM_STATUS {
        public static final int BANNED = 3;
        public static final int DISCONNECTED = 7;
        public static final int EXPIRED = 5;
        public static final int FINISHED = 4;
        public static final int INIT = 1;
        public static final int KICKED = 8;
        public static final int STREAMING = 2;
        public static final int UNKNOWN = 99;
        public static final int UNUPDATED = 6;
    }

    public static class GGLiveChannelStreamInitRet {
        public int flag;
        public String streamKey;
        public String streamServerAddress;
    }

    public static class GGLiveChannelStreamVerifyRet {
        public int flag;
        public int status;
        public String statusString;
    }

    public static class GGLiveGetChannelInfoRet {
        public String desc;
        public int flag;
        public String name;
    }

    public static class GGLiveHeartbeatRet {
        public int flag;
        public int numberViewer;
    }

    public static class GroupFriendsInfoRet {
        public int flag;
        public Vector<GroupUserInfo> info;
    }

    public static class GroupUserInfo extends UserInfo {
        public int platform;
    }

    public static class UserInfo {
        public int gender;
        public String iconURL;
        public String nickName;
        public String openID;
    }

    public static class UserInfoRet {
        public int flag;
        public int platform;
        public UserInfo userInfo;
    }
}
