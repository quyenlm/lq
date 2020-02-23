package com.tencent.imsdk.push.entity;

import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

public class IMLocalMessage extends JsonSerializable {
    @JsonProp(name = "ActionContent")
    public String actionContent;
    @JsonProp(name = "ActionType")
    public int actionType;
    @JsonProp(name = "ApkDownloadUrl")
    public String apkDownloadUrl;
    @JsonProp(name = "BuilderId")
    public long builderId;
    @JsonProp(name = "Content")
    public String content;
    @JsonProp(name = "FireTime")
    public long fireTime;
    @JsonProp(name = "IconRes")
    public String iconRes;
    @JsonProp(name = "IsRinging")
    public boolean isRinging;
    @JsonProp(name = "IsVibrate")
    public boolean isVibrate;
    @JsonProp(name = "Light")
    public int light;
    @JsonProp(name = "RingRaw")
    public String ringRaw;
    @JsonProp(name = "StyleId")
    public int styleId;
    @JsonProp(name = "Title")
    public String title;
    @JsonProp(name = "Type")
    public int type;

    private IMLocalMessage() {
    }

    public IMLocalMessage(JSONObject json) throws JSONException {
        super(json);
    }

    public IMLocalMessage(String json) throws JSONException {
        super(json);
    }

    private IMLocalMessage(Builder builder) {
        this.type = builder.type;
        this.title = builder.title;
        this.content = builder.content;
        this.actionType = builder.actionType;
        this.actionContent = builder.actionContent;
        this.isRinging = builder.isRinging;
        this.ringRaw = builder.ringRaw;
        this.isVibrate = builder.isVibrate;
        this.light = builder.light;
        this.iconRes = builder.iconRes;
        this.apkDownloadUrl = builder.apkDownloadUrl;
        this.builderId = builder.builderId;
        this.styleId = builder.styleId;
        this.fireTime = builder.fireTimeMs;
    }

    public String toString() {
        return "IMLocalMessage{type=" + this.type + ", title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", actionType=" + this.actionType + ", actionContent='" + this.actionContent + '\'' + ", isRinging=" + this.isRinging + ", ringRaw='" + this.ringRaw + '\'' + ", isVibrate=" + this.isVibrate + ", light=" + this.light + ", iconRes='" + this.iconRes + '\'' + ", apkDownloadUrl='" + this.apkDownloadUrl + '\'' + ", builderId=" + this.builderId + ", styleId=" + this.styleId + ", fireTime=" + this.fireTime + '}';
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public String actionContent = "";
        /* access modifiers changed from: private */
        public int actionType = 1;
        /* access modifiers changed from: private */
        public String apkDownloadUrl = "";
        /* access modifiers changed from: private */
        public long builderId;
        /* access modifiers changed from: private */
        public String content;
        /* access modifiers changed from: private */
        public long fireTimeMs;
        /* access modifiers changed from: private */
        public String iconRes = "";
        /* access modifiers changed from: private */
        public boolean isRinging = true;
        /* access modifiers changed from: private */
        public boolean isVibrate = true;
        /* access modifiers changed from: private */
        public int light = 1;
        /* access modifiers changed from: private */
        public String ringRaw;
        /* access modifiers changed from: private */
        public int styleId;
        /* access modifiers changed from: private */
        public String title;
        /* access modifiers changed from: private */
        public int type = 1;

        public Builder setType(int val) {
            this.type = val;
            return this;
        }

        public Builder setTitle(String val) {
            this.title = val;
            return this;
        }

        public Builder setContent(String val) {
            this.content = val;
            return this;
        }

        public Builder setActionType(int val) {
            this.actionType = val;
            return this;
        }

        public Builder setActionContent(String val) {
            this.actionContent = val;
            return this;
        }

        public Builder setIsRinging(boolean val) {
            this.isRinging = val;
            return this;
        }

        public Builder setRingRaw(String val) {
            this.ringRaw = val;
            return this;
        }

        public Builder setIsVibrate(boolean val) {
            this.isVibrate = val;
            return this;
        }

        public Builder setLight(int val) {
            this.light = val;
            return this;
        }

        public Builder setIconRes(String val) {
            this.iconRes = val;
            return this;
        }

        public Builder setApkDownloadUrl(String val) {
            this.apkDownloadUrl = val;
            return this;
        }

        public Builder setBuilderId(long val) {
            this.builderId = val;
            return this;
        }

        public Builder setStyleId(int val) {
            this.styleId = val;
            return this;
        }

        public Builder setFireTimeMs(long val) {
            this.fireTimeMs = val;
            return this;
        }

        public IMLocalMessage build() {
            return new IMLocalMessage(this);
        }
    }
}
