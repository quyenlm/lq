package com.tencent.imsdk.lbs.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;

public class IMLocationResult extends IMResult {
    @JsonProp(name = "iChannel")
    public int channelId;
    @JsonProp(name = "sCity")
    public String cityName;
    @JsonProp(name = "iCityNo")
    public int cityNumber;
    @JsonProp(name = "sCountry")
    public String countryName;
    @JsonProp(name = "iCountryNo")
    public int countryNumber;
    @JsonProp(name = "iGameId")
    public int gameId;
    @JsonProp(name = "iGuid")
    public String guid;
    @JsonProp(name = "sInnerToken")
    public String guidToken;
    @JsonProp(name = "iExpireTime")
    public long guidTokenExpire;
    @JsonProp(name = "sBirthdate")
    public String guidUserBirthday;
    @JsonProp(name = "sUserName")
    public String guidUserNick;
    @JsonProp(name = "sPictureUrl")
    public String guidUserPortrait;
    @JsonProp(name = "iGender")
    public int guidUserSex;
    @JsonProp(name = "iOpenid")
    public String openId;
    @JsonProp(name = "sProvince")
    public String provinceName;
    @JsonProp(name = "iProvinceNo")
    public int provinceNumber;

    public IMLocationResult(String result) throws JSONException {
        super(result);
    }
}
