package com.tencent.tdm.defines;

public class UserInfo {
    public int AccountType;
    public int Age;
    public int Gender;
    public int Level;
    public String NickName;
    public String Region;
    public String UserID;

    public UserInfo() {
    }

    public UserInfo(String userId, String name, String region, int gender, int type, int age, int level) {
        this.UserID = userId;
        this.NickName = name;
        this.Region = region;
        this.Gender = gender;
        this.AccountType = type;
        this.Age = age;
        this.Level = level;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("TDMUserInfo{UserID=");
        stringBuffer.append(this.UserID);
        stringBuffer.append(",NickName=");
        stringBuffer.append(this.NickName);
        stringBuffer.append(",Region=");
        stringBuffer.append(this.Region);
        stringBuffer.append(",Gender=");
        stringBuffer.append(this.Gender);
        stringBuffer.append(",AccountType=");
        stringBuffer.append(this.AccountType);
        stringBuffer.append(",Age=");
        stringBuffer.append(this.Age);
        stringBuffer.append(",Level=");
        stringBuffer.append(this.Level);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
