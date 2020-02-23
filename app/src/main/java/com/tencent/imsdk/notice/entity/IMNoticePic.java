package com.tencent.imsdk.notice.entity;

import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

public class IMNoticePic extends JsonSerializable {
    @JsonProp(name = "extraJson")
    public String extraJson;
    @JsonProp(name = "noticeId")
    public int noticeId;
    @JsonProp(name = "picHash")
    public String picHash;
    @JsonProp(name = "picSize")
    public String picSize;
    @JsonProp(name = "picTitle")
    public String picTitle;
    @JsonProp(name = "picUrl")
    public String picUrl;
    @JsonProp(name = "screenDir")
    public int screenDir;

    public IMNoticePic() {
    }

    public IMNoticePic(String json) throws JSONException {
        super(json);
    }

    public IMNoticePic(JSONObject json) throws JSONException {
        super(json);
    }
}
