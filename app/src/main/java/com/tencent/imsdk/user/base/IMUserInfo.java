package com.tencent.imsdk.user.base;

import com.tencent.imsdk.tool.json.JsonList;
import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class IMUserInfo extends JsonSerializable {
    @JsonProp(name = "sAddress")
    public String address;
    @JsonProp(name = "cBirthdate")
    public String birthday;
    @JsonProp(name = "iCityNo")
    public int city;
    @JsonProp(name = "iCountryNo")
    public int country;
    @JsonProp(name = "iCtime")
    public long createTime;
    @JsonProp(name = "sEmail")
    public String email;
    @JsonProp(name = "iGuid")
    public String guid;
    @JsonProp(name = "sIp")
    public String ip;
    @JsonProp(name = "sMobile")
    public String mobile;
    @JsonProp(name = "sUserName")
    public String nick;
    @JsonProp(name = "sPhone")
    public String phone;
    @JsonProp(name = "sPictureUrl")
    public String portrait;
    @JsonList(type = "java.lang.String")
    @JsonProp(name = "AHeaderImg")
    public List<String> sampleImgList;
    @JsonProp(name = "iGender")
    public int sex;
    @JsonProp(name = "iUpTime")
    public long updateTime;

    public IMUserInfo() {
    }

    public IMUserInfo(JSONObject object) throws JSONException {
        super(object);
    }

    public IMUserInfo(String json) throws JSONException {
        super(json);
    }
}
