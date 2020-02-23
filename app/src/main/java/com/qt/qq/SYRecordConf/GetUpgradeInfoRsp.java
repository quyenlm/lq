package com.qt.qq.SYRecordConf;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import okio.ByteString;

public final class GetUpgradeInfoRsp extends Message {
    public static final Boolean DEFAULT_NEED_UPGRADE = false;
    public static final Integer DEFAULT_RESULT = 0;
    public static final ByteString DEFAULT_URL = ByteString.EMPTY;
    @ProtoField(tag = 2, type = Message.Datatype.BOOL)
    public final Boolean need_upgrade;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT32)
    public final Integer result;
    @ProtoField(tag = 3, type = Message.Datatype.BYTES)
    public final ByteString url;

    public GetUpgradeInfoRsp(Integer result2, Boolean need_upgrade2, ByteString url2) {
        this.result = result2;
        this.need_upgrade = need_upgrade2;
        this.url = url2;
    }

    private GetUpgradeInfoRsp(Builder builder) {
        this(builder.result, builder.need_upgrade, builder.url);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GetUpgradeInfoRsp)) {
            return false;
        }
        GetUpgradeInfoRsp o = (GetUpgradeInfoRsp) other;
        if (!equals((Object) this.result, (Object) o.result) || !equals((Object) this.need_upgrade, (Object) o.need_upgrade) || !equals((Object) this.url, (Object) o.url)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int result2 = this.hashCode;
        if (result2 != 0) {
            return result2;
        }
        int hashCode = (this.result != null ? this.result.hashCode() : 0) * 37;
        if (this.need_upgrade != null) {
            i = this.need_upgrade.hashCode();
        } else {
            i = 0;
        }
        int i3 = (hashCode + i) * 37;
        if (this.url != null) {
            i2 = this.url.hashCode();
        }
        int result3 = i3 + i2;
        this.hashCode = result3;
        return result3;
    }

    public static final class Builder extends Message.Builder<GetUpgradeInfoRsp> {
        public Boolean need_upgrade;
        public Integer result;
        public ByteString url;

        public Builder() {
        }

        public Builder(GetUpgradeInfoRsp message) {
            super(message);
            if (message != null) {
                this.result = message.result;
                this.need_upgrade = message.need_upgrade;
                this.url = message.url;
            }
        }

        public Builder result(Integer result2) {
            this.result = result2;
            return this;
        }

        public Builder need_upgrade(Boolean need_upgrade2) {
            this.need_upgrade = need_upgrade2;
            return this;
        }

        public Builder url(ByteString url2) {
            this.url = url2;
            return this;
        }

        public GetUpgradeInfoRsp build() {
            checkRequiredFields();
            return new GetUpgradeInfoRsp(this);
        }
    }
}
