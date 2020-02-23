package com.subao.common.intf;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.subao.common.e;
import java.util.Locale;

public class GameInformation implements Parcelable {
    public static final Parcelable.Creator<GameInformation> CREATOR = new Parcelable.Creator<GameInformation>() {
        public GameInformation createFromParcel(Parcel parcel) {
            return new GameInformation(parcel);
        }

        public GameInformation[] newArray(int i) {
            return new GameInformation[i];
        }
    };
    private static final int SERIALIZE_VERSION = 1;
    @NonNull
    private final String appLabel;
    private final boolean foreign;
    @Nullable
    private final String nodeTag;
    @NonNull
    private final String packageName;
    @Nullable
    private final String serverNameChinese;
    @Nullable
    private final String serverNameNonChinese;
    private final int uid;

    public GameInformation(int i, @NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, boolean z) {
        if (str == null) {
            throw new NullPointerException("Package name can not be null or empty");
        } else if (str.length() == 0) {
            throw new IllegalArgumentException("Package name is empty");
        } else if (str2 == null) {
            throw new NullPointerException("App label can not be null or empty");
        } else {
            this.uid = i;
            this.packageName = str;
            this.appLabel = str2;
            this.nodeTag = str3;
            this.serverNameChinese = str4;
            this.serverNameNonChinese = str5;
            this.foreign = z;
        }
    }

    protected GameInformation(Parcel parcel) {
        parcel.readInt();
        this.uid = parcel.readInt();
        this.packageName = parcel.readString();
        this.appLabel = parcel.readString();
        this.nodeTag = parcel.readString();
        this.serverNameChinese = parcel.readString();
        this.serverNameNonChinese = parcel.readString();
        this.foreign = parcel.readInt() != 0;
    }

    @Nullable
    public static GameInformation create(int i, String str, String str2, String str3, String str4, String str5, boolean z) {
        if (i <= 0 || str == null || str.length() == 0 || str2 == null) {
            return null;
        }
        return new GameInformation(i, str, str2, str3, str4, str5, z);
    }

    public int getUid() {
        return this.uid;
    }

    public boolean isForeign() {
        return this.foreign;
    }

    @NonNull
    public String getPackageName() {
        return this.packageName;
    }

    @NonNull
    public String getAppLabel() {
        return this.appLabel;
    }

    @Nullable
    public String getNodeTag() {
        return this.nodeTag;
    }

    @Nullable
    public String getServerName() {
        return getServerName((Locale) null);
    }

    @Nullable
    public String getServerName(@Nullable Locale locale) {
        String str;
        String str2;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (e.a(locale.getLanguage(), Locale.CHINESE.getLanguage())) {
            str = this.serverNameChinese;
            str2 = this.serverNameNonChinese;
        } else {
            str = this.serverNameNonChinese;
            str2 = this.serverNameChinese;
        }
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        return str;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GameInformation)) {
            return false;
        }
        GameInformation gameInformation = (GameInformation) obj;
        if (this.uid != gameInformation.uid || this.foreign != gameInformation.foreign || !e.a(this.packageName, gameInformation.packageName) || !e.a(this.appLabel, gameInformation.appLabel) || !e.a(this.nodeTag, gameInformation.nodeTag) || !e.a(this.serverNameChinese, gameInformation.serverNameChinese) || !e.a(this.serverNameNonChinese, gameInformation.serverNameNonChinese)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int makeHasCode = makeHasCode(makeHasCode(makeHasCode(makeHasCode(this.packageName.hashCode(), this.appLabel), this.nodeTag), this.serverNameChinese), this.serverNameNonChinese) | (this.uid << 16);
        if (this.foreign) {
            return makeHasCode | 1073741824;
        }
        return makeHasCode;
    }

    private static int makeHasCode(int i, String str) {
        return str == null ? i : i ^ str.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeInt(1);
        parcel.writeInt(this.uid);
        parcel.writeString(this.packageName);
        parcel.writeString(this.appLabel);
        parcel.writeString(this.nodeTag);
        parcel.writeString(this.serverNameChinese);
        parcel.writeString(this.serverNameNonChinese);
        if (!this.foreign) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
