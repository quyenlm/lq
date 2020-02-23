package com.tencent.imsdk.notice.entity;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonList;
import com.tencent.imsdk.tool.json.JsonProp;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class IMNoticeResult extends IMResult {
    @JsonList(type = "com.tencent.imsdk.notice.entity.IMNoticeInfo")
    @JsonProp(name = "noticelist")
    public List<IMNoticeInfo> noticesList;
    @JsonProp(name = "noticeNum")
    public int noticesNum;

    public IMNoticeResult() {
    }

    public IMNoticeResult(String json) throws JSONException {
        super(json);
    }

    public IMNoticeResult(JSONObject json) throws JSONException {
        super(json);
    }
}
