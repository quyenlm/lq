package com.subao.common.intf;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.e;
import com.subao.common.n.f;
import com.subao.common.n.g;

public class UserInfo implements Parcelable, c {
    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel parcel) {
            return new UserInfo(parcel);
        }

        public UserInfo[] newArray(int i) {
            return new UserInfo[i];
        }
    };
    private final String appId;
    private final String token;
    private final String userId;

    public UserInfo(String str, String str2, String str3) {
        this.userId = str;
        this.token = str2;
        this.appId = str3;
    }

    private UserInfo(Parcel parcel) {
        this.userId = readFromParcel(parcel);
        this.token = readFromParcel(parcel);
        this.appId = readFromParcel(parcel);
    }

    private static void writeToParcel(Parcel parcel, String str) {
        if (str == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(0);
        parcel.writeString(str);
    }

    private static String readFromParcel(Parcel parcel) {
        if (-1 == parcel.readInt()) {
            return null;
        }
        return parcel.readString();
    }

    public String getUserId() {
        return this.userId;
    }

    public String getToken() {
        return this.token;
    }

    public String getAppId() {
        return this.appId;
    }

    public String toString() {
        return String.format("[UserInfo: %s, %s, %s]", new Object[]{g.a((Object) this.userId), g.a((Object) this.token), g.a((Object) this.appId)});
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserInfo)) {
            return false;
        }
        UserInfo userInfo = (UserInfo) obj;
        if (!e.a(this.userId, userInfo.userId) || !e.a(this.token, userInfo.token) || !e.a(this.appId, userInfo.appId)) {
            z = false;
        }
        return z;
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        f.a(jsonWriter, "userId", this.userId);
        f.a(jsonWriter, "token", this.token);
        f.a(jsonWriter, "appId", this.appId);
        jsonWriter.endObject();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        writeToParcel(parcel, this.userId);
        writeToParcel(parcel, this.token);
        writeToParcel(parcel, this.appId);
    }
}
