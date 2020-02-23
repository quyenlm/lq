package com.qt.qq.SYRecordConf;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import okio.ByteString;

public final class GetWhiteListInfoReq extends Message {
    public static final ByteString DEFAULT_ACCESS_TOKEN = ByteString.EMPTY;
    public static final ByteString DEFAULT_CPU_VERSION = ByteString.EMPTY;
    public static final ByteString DEFAULT_GPU_VERSION = ByteString.EMPTY;
    public static final ByteString DEFAULT_OPENID = ByteString.EMPTY;
    public static final ByteString DEFAULT_OS_VERSION = ByteString.EMPTY;
    public static final ByteString DEFAULT_PHONE_TYPE = ByteString.EMPTY;
    public static final ByteString DEFAULT_PKG_NAME = ByteString.EMPTY;
    public static final ByteString DEFAULT_PLUGIN_VERSION = ByteString.EMPTY;
    public static final Long DEFAULT_QQAPPID = 0L;
    public static final ByteString DEFAULT_SDK_VERSION = ByteString.EMPTY;
    public static final Integer DEFAULT_SOURCE = 0;
    public static final ByteString DEFAULT_USER_ID = ByteString.EMPTY;
    @ProtoField(tag = 9, type = Message.Datatype.BYTES)
    public final ByteString access_token;
    @ProtoField(tag = 11, type = Message.Datatype.BYTES)
    public final ByteString cpu_version;
    @ProtoField(tag = 12, type = Message.Datatype.BYTES)
    public final ByteString gpu_version;
    @ProtoField(tag = 8, type = Message.Datatype.BYTES)
    public final ByteString openid;
    @ProtoField(tag = 3, type = Message.Datatype.BYTES)
    public final ByteString os_version;
    @ProtoField(tag = 6, type = Message.Datatype.BYTES)
    public final ByteString phone_type;
    @ProtoField(tag = 2, type = Message.Datatype.BYTES)
    public final ByteString pkg_name;
    @ProtoField(tag = 5, type = Message.Datatype.BYTES)
    public final ByteString plugin_version;
    @ProtoField(tag = 10, type = Message.Datatype.UINT64)
    public final Long qqappid;
    @ProtoField(tag = 4, type = Message.Datatype.BYTES)
    public final ByteString sdk_version;
    @ProtoField(tag = 7, type = Message.Datatype.UINT32)
    public final Integer source;
    @ProtoField(tag = 1, type = Message.Datatype.BYTES)
    public final ByteString user_id;

    public GetWhiteListInfoReq(ByteString user_id2, ByteString pkg_name2, ByteString os_version2, ByteString sdk_version2, ByteString plugin_version2, ByteString phone_type2, Integer source2, ByteString openid2, ByteString access_token2, Long qqappid2, ByteString cpu_version2, ByteString gpu_version2) {
        this.user_id = user_id2;
        this.pkg_name = pkg_name2;
        this.os_version = os_version2;
        this.sdk_version = sdk_version2;
        this.plugin_version = plugin_version2;
        this.phone_type = phone_type2;
        this.source = source2;
        this.openid = openid2;
        this.access_token = access_token2;
        this.qqappid = qqappid2;
        this.cpu_version = cpu_version2;
        this.gpu_version = gpu_version2;
    }

    private GetWhiteListInfoReq(Builder builder) {
        this(builder.user_id, builder.pkg_name, builder.os_version, builder.sdk_version, builder.plugin_version, builder.phone_type, builder.source, builder.openid, builder.access_token, builder.qqappid, builder.cpu_version, builder.gpu_version);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GetWhiteListInfoReq)) {
            return false;
        }
        GetWhiteListInfoReq o = (GetWhiteListInfoReq) other;
        if (!equals((Object) this.user_id, (Object) o.user_id) || !equals((Object) this.pkg_name, (Object) o.pkg_name) || !equals((Object) this.os_version, (Object) o.os_version) || !equals((Object) this.sdk_version, (Object) o.sdk_version) || !equals((Object) this.plugin_version, (Object) o.plugin_version) || !equals((Object) this.phone_type, (Object) o.phone_type) || !equals((Object) this.source, (Object) o.source) || !equals((Object) this.openid, (Object) o.openid) || !equals((Object) this.access_token, (Object) o.access_token) || !equals((Object) this.qqappid, (Object) o.qqappid) || !equals((Object) this.cpu_version, (Object) o.cpu_version) || !equals((Object) this.gpu_version, (Object) o.gpu_version)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.user_id != null ? this.user_id.hashCode() : 0) * 37;
        if (this.pkg_name != null) {
            i = this.pkg_name.hashCode();
        } else {
            i = 0;
        }
        int i12 = (hashCode + i) * 37;
        if (this.os_version != null) {
            i2 = this.os_version.hashCode();
        } else {
            i2 = 0;
        }
        int i13 = (i12 + i2) * 37;
        if (this.sdk_version != null) {
            i3 = this.sdk_version.hashCode();
        } else {
            i3 = 0;
        }
        int i14 = (i13 + i3) * 37;
        if (this.plugin_version != null) {
            i4 = this.plugin_version.hashCode();
        } else {
            i4 = 0;
        }
        int i15 = (i14 + i4) * 37;
        if (this.phone_type != null) {
            i5 = this.phone_type.hashCode();
        } else {
            i5 = 0;
        }
        int i16 = (i15 + i5) * 37;
        if (this.source != null) {
            i6 = this.source.hashCode();
        } else {
            i6 = 0;
        }
        int i17 = (i16 + i6) * 37;
        if (this.openid != null) {
            i7 = this.openid.hashCode();
        } else {
            i7 = 0;
        }
        int i18 = (i17 + i7) * 37;
        if (this.access_token != null) {
            i8 = this.access_token.hashCode();
        } else {
            i8 = 0;
        }
        int i19 = (i18 + i8) * 37;
        if (this.qqappid != null) {
            i9 = this.qqappid.hashCode();
        } else {
            i9 = 0;
        }
        int i20 = (i19 + i9) * 37;
        if (this.cpu_version != null) {
            i10 = this.cpu_version.hashCode();
        } else {
            i10 = 0;
        }
        int i21 = (i20 + i10) * 37;
        if (this.gpu_version != null) {
            i11 = this.gpu_version.hashCode();
        }
        int result2 = i21 + i11;
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<GetWhiteListInfoReq> {
        public ByteString access_token;
        public ByteString cpu_version;
        public ByteString gpu_version;
        public ByteString openid;
        public ByteString os_version;
        public ByteString phone_type;
        public ByteString pkg_name;
        public ByteString plugin_version;
        public Long qqappid;
        public ByteString sdk_version;
        public Integer source;
        public ByteString user_id;

        public Builder() {
        }

        public Builder(GetWhiteListInfoReq message) {
            super(message);
            if (message != null) {
                this.user_id = message.user_id;
                this.pkg_name = message.pkg_name;
                this.os_version = message.os_version;
                this.sdk_version = message.sdk_version;
                this.plugin_version = message.plugin_version;
                this.phone_type = message.phone_type;
                this.source = message.source;
                this.openid = message.openid;
                this.access_token = message.access_token;
                this.qqappid = message.qqappid;
                this.cpu_version = message.cpu_version;
                this.gpu_version = message.gpu_version;
            }
        }

        public Builder user_id(ByteString user_id2) {
            this.user_id = user_id2;
            return this;
        }

        public Builder pkg_name(ByteString pkg_name2) {
            this.pkg_name = pkg_name2;
            return this;
        }

        public Builder os_version(ByteString os_version2) {
            this.os_version = os_version2;
            return this;
        }

        public Builder sdk_version(ByteString sdk_version2) {
            this.sdk_version = sdk_version2;
            return this;
        }

        public Builder plugin_version(ByteString plugin_version2) {
            this.plugin_version = plugin_version2;
            return this;
        }

        public Builder phone_type(ByteString phone_type2) {
            this.phone_type = phone_type2;
            return this;
        }

        public Builder source(Integer source2) {
            this.source = source2;
            return this;
        }

        public Builder openid(ByteString openid2) {
            this.openid = openid2;
            return this;
        }

        public Builder access_token(ByteString access_token2) {
            this.access_token = access_token2;
            return this;
        }

        public Builder qqappid(Long qqappid2) {
            this.qqappid = qqappid2;
            return this;
        }

        public Builder cpu_version(ByteString cpu_version2) {
            this.cpu_version = cpu_version2;
            return this;
        }

        public Builder gpu_version(ByteString gpu_version2) {
            this.gpu_version = gpu_version2;
            return this;
        }

        public GetWhiteListInfoReq build() {
            return new GetWhiteListInfoReq(this);
        }
    }
}
