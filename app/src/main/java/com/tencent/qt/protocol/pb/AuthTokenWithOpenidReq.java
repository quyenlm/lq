package com.tencent.qt.protocol.pb;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import okio.ByteString;

public final class AuthTokenWithOpenidReq extends Message {
    public static final ByteString DEFAULT_ACCESS_TOKEN = ByteString.EMPTY;
    public static final Integer DEFAULT_AREAID = 0;
    public static final Integer DEFAULT_AUTHEN_TYPE = 0;
    public static final ByteString DEFAULT_CLIENTIP = ByteString.EMPTY;
    public static final Integer DEFAULT_CLIENT_TIME = 0;
    public static final Integer DEFAULT_CLIENT_TYPE = 0;
    public static final Integer DEFAULT_GAMEID = 0;
    public static final ByteString DEFAULT_MCODE = ByteString.EMPTY;
    public static final Long DEFAULT_OPENAPPID = 0L;
    public static final ByteString DEFAULT_OPENID = ByteString.EMPTY;
    public static final ByteString DEFAULT_WXAPPID = ByteString.EMPTY;
    @ProtoField(tag = 5, type = Message.Datatype.BYTES)
    public final ByteString access_token;
    @ProtoField(tag = 4, type = Message.Datatype.UINT32)
    public final Integer areaid;
    @ProtoField(tag = 1, type = Message.Datatype.UINT32)
    public final Integer authen_type;
    @ProtoField(tag = 9, type = Message.Datatype.UINT32)
    public final Integer client_time;
    @ProtoField(tag = 3, type = Message.Datatype.UINT32)
    public final Integer client_type;
    @ProtoField(tag = 7, type = Message.Datatype.BYTES)
    public final ByteString clientip;
    @ProtoField(tag = 10, type = Message.Datatype.UINT32)
    public final Integer gameid;
    @ProtoField(tag = 8, type = Message.Datatype.BYTES)
    public final ByteString mcode;
    @ProtoField(tag = 2, type = Message.Datatype.UINT64)
    public final Long openappid;
    @ProtoField(tag = 6, type = Message.Datatype.BYTES)
    public final ByteString openid;
    @ProtoField(tag = 11, type = Message.Datatype.BYTES)
    public final ByteString wxappid;

    public AuthTokenWithOpenidReq(Integer authen_type2, Long openappid2, Integer client_type2, Integer areaid2, ByteString access_token2, ByteString openid2, ByteString clientip2, ByteString mcode2, Integer client_time2, Integer gameid2, ByteString wxappid2) {
        this.authen_type = authen_type2;
        this.openappid = openappid2;
        this.client_type = client_type2;
        this.areaid = areaid2;
        this.access_token = access_token2;
        this.openid = openid2;
        this.clientip = clientip2;
        this.mcode = mcode2;
        this.client_time = client_time2;
        this.gameid = gameid2;
        this.wxappid = wxappid2;
    }

    private AuthTokenWithOpenidReq(Builder builder) {
        this(builder.authen_type, builder.openappid, builder.client_type, builder.areaid, builder.access_token, builder.openid, builder.clientip, builder.mcode, builder.client_time, builder.gameid, builder.wxappid);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AuthTokenWithOpenidReq)) {
            return false;
        }
        AuthTokenWithOpenidReq o = (AuthTokenWithOpenidReq) other;
        if (!equals((Object) this.authen_type, (Object) o.authen_type) || !equals((Object) this.openappid, (Object) o.openappid) || !equals((Object) this.client_type, (Object) o.client_type) || !equals((Object) this.areaid, (Object) o.areaid) || !equals((Object) this.access_token, (Object) o.access_token) || !equals((Object) this.openid, (Object) o.openid) || !equals((Object) this.clientip, (Object) o.clientip) || !equals((Object) this.mcode, (Object) o.mcode) || !equals((Object) this.client_time, (Object) o.client_time) || !equals((Object) this.gameid, (Object) o.gameid) || !equals((Object) this.wxappid, (Object) o.wxappid)) {
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
        int i10 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.authen_type != null ? this.authen_type.hashCode() : 0) * 37;
        if (this.openappid != null) {
            i = this.openappid.hashCode();
        } else {
            i = 0;
        }
        int i11 = (hashCode + i) * 37;
        if (this.client_type != null) {
            i2 = this.client_type.hashCode();
        } else {
            i2 = 0;
        }
        int i12 = (i11 + i2) * 37;
        if (this.areaid != null) {
            i3 = this.areaid.hashCode();
        } else {
            i3 = 0;
        }
        int i13 = (i12 + i3) * 37;
        if (this.access_token != null) {
            i4 = this.access_token.hashCode();
        } else {
            i4 = 0;
        }
        int i14 = (i13 + i4) * 37;
        if (this.openid != null) {
            i5 = this.openid.hashCode();
        } else {
            i5 = 0;
        }
        int i15 = (i14 + i5) * 37;
        if (this.clientip != null) {
            i6 = this.clientip.hashCode();
        } else {
            i6 = 0;
        }
        int i16 = (i15 + i6) * 37;
        if (this.mcode != null) {
            i7 = this.mcode.hashCode();
        } else {
            i7 = 0;
        }
        int i17 = (i16 + i7) * 37;
        if (this.client_time != null) {
            i8 = this.client_time.hashCode();
        } else {
            i8 = 0;
        }
        int i18 = (i17 + i8) * 37;
        if (this.gameid != null) {
            i9 = this.gameid.hashCode();
        } else {
            i9 = 0;
        }
        int i19 = (i18 + i9) * 37;
        if (this.wxappid != null) {
            i10 = this.wxappid.hashCode();
        }
        int result2 = i19 + i10;
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<AuthTokenWithOpenidReq> {
        public ByteString access_token;
        public Integer areaid;
        public Integer authen_type;
        public Integer client_time;
        public Integer client_type;
        public ByteString clientip;
        public Integer gameid;
        public ByteString mcode;
        public Long openappid;
        public ByteString openid;
        public ByteString wxappid;

        public Builder() {
        }

        public Builder(AuthTokenWithOpenidReq message) {
            super(message);
            if (message != null) {
                this.authen_type = message.authen_type;
                this.openappid = message.openappid;
                this.client_type = message.client_type;
                this.areaid = message.areaid;
                this.access_token = message.access_token;
                this.openid = message.openid;
                this.clientip = message.clientip;
                this.mcode = message.mcode;
                this.client_time = message.client_time;
                this.gameid = message.gameid;
                this.wxappid = message.wxappid;
            }
        }

        public Builder authen_type(Integer authen_type2) {
            this.authen_type = authen_type2;
            return this;
        }

        public Builder openappid(Long openappid2) {
            this.openappid = openappid2;
            return this;
        }

        public Builder client_type(Integer client_type2) {
            this.client_type = client_type2;
            return this;
        }

        public Builder areaid(Integer areaid2) {
            this.areaid = areaid2;
            return this;
        }

        public Builder access_token(ByteString access_token2) {
            this.access_token = access_token2;
            return this;
        }

        public Builder openid(ByteString openid2) {
            this.openid = openid2;
            return this;
        }

        public Builder clientip(ByteString clientip2) {
            this.clientip = clientip2;
            return this;
        }

        public Builder mcode(ByteString mcode2) {
            this.mcode = mcode2;
            return this;
        }

        public Builder client_time(Integer client_time2) {
            this.client_time = client_time2;
            return this;
        }

        public Builder gameid(Integer gameid2) {
            this.gameid = gameid2;
            return this;
        }

        public Builder wxappid(ByteString wxappid2) {
            this.wxappid = wxappid2;
            return this;
        }

        public AuthTokenWithOpenidReq build() {
            return new AuthTokenWithOpenidReq(this);
        }
    }
}
