package com.tencent.imsdk.notice.entity;

import com.tencent.imsdk.tool.json.JsonList;
import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class IMNoticeInfo extends JsonSerializable {
    @JsonProp(name = "appId")
    public String appId;
    @JsonProp(name = "extraJson")
    public String extraJson;
    @JsonProp(name = "noticeContent")
    public String noticeContent;
    @JsonProp(name = "noticeContentType")
    public int noticeContentType;
    @JsonProp(name = "noticeContentWebUrl")
    public String noticeContentWebUrl;
    @JsonProp(name = "noticeEndTime")
    public long noticeEndTime;
    @JsonProp(name = "noticeId")
    public int noticeId;
    @JsonProp(name = "noticeLang")
    public String noticeLanguage;
    @JsonList(type = "com.tencent.imsdk.notice.entity.IMNoticePic")
    @JsonProp(name = "noticePics")
    public List<IMNoticePic> noticePics;
    @JsonProp(name = "noticeScene")
    public int noticeScene;
    @JsonProp(name = "noticeStartTime")
    public long noticeStartTime;
    @JsonProp(name = "noticeTitle")
    public String noticeTitle;
    @JsonProp(name = "noticeUpdateTime")
    public long noticeUpdateTime;
    @JsonProp(name = "noticeUrl")
    public String noticeUrl;
    @JsonProp(name = "openId")
    public String openId;
    @JsonProp(name = "screenName")
    public String screenName;
    @JsonProp(name = "stateCode")
    public String stateCode;

    public IMNoticeInfo() {
    }

    public IMNoticeInfo(String json) throws JSONException {
        super(json);
    }

    public IMNoticeInfo(JSONObject json) throws JSONException {
        super(json);
    }
}
