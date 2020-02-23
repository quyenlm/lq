package com.qt.qq.SYRecordConf;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class GetWhiteListInfoRsp extends Message {
    public static final Integer DEFAULT_BZID = 0;
    public static final Boolean DEFAULT_IN_WHITELIST = false;
    public static final Integer DEFAULT_RESULT = 0;
    public static final Long DEFAULT_SWITCH = 0L;
    @ProtoField(tag = 4, type = Message.Datatype.UINT64)
    public final Long _switch;
    @ProtoField(tag = 3, type = Message.Datatype.UINT32)
    public final Integer bzid;
    @ProtoField(tag = 2, type = Message.Datatype.BOOL)
    public final Boolean in_whitelist;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT32)
    public final Integer result;

    public GetWhiteListInfoRsp(Integer result2, Boolean in_whitelist2, Integer bzid2, Long _switch2) {
        this.result = result2;
        this.in_whitelist = in_whitelist2;
        this.bzid = bzid2;
        this._switch = _switch2;
    }

    private GetWhiteListInfoRsp(Builder builder) {
        this(builder.result, builder.in_whitelist, builder.bzid, builder._switch);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GetWhiteListInfoRsp)) {
            return false;
        }
        GetWhiteListInfoRsp o = (GetWhiteListInfoRsp) other;
        if (!equals((Object) this.result, (Object) o.result) || !equals((Object) this.in_whitelist, (Object) o.in_whitelist) || !equals((Object) this.bzid, (Object) o.bzid) || !equals((Object) this._switch, (Object) o._switch)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2;
        int i3 = 0;
        int result2 = this.hashCode;
        if (result2 != 0) {
            return result2;
        }
        int hashCode = (this.result != null ? this.result.hashCode() : 0) * 37;
        if (this.in_whitelist != null) {
            i = this.in_whitelist.hashCode();
        } else {
            i = 0;
        }
        int i4 = (hashCode + i) * 37;
        if (this.bzid != null) {
            i2 = this.bzid.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (i4 + i2) * 37;
        if (this._switch != null) {
            i3 = this._switch.hashCode();
        }
        int result3 = i5 + i3;
        this.hashCode = result3;
        return result3;
    }

    public static final class Builder extends Message.Builder<GetWhiteListInfoRsp> {
        public Long _switch;
        public Integer bzid;
        public Boolean in_whitelist;
        public Integer result;

        public Builder() {
        }

        public Builder(GetWhiteListInfoRsp message) {
            super(message);
            if (message != null) {
                this.result = message.result;
                this.in_whitelist = message.in_whitelist;
                this.bzid = message.bzid;
                this._switch = message._switch;
            }
        }

        public Builder result(Integer result2) {
            this.result = result2;
            return this;
        }

        public Builder in_whitelist(Boolean in_whitelist2) {
            this.in_whitelist = in_whitelist2;
            return this;
        }

        public Builder bzid(Integer bzid2) {
            this.bzid = bzid2;
            return this;
        }

        public Builder _switch(Long _switch2) {
            this._switch = _switch2;
            return this;
        }

        public GetWhiteListInfoRsp build() {
            checkRequiredFields();
            return new GetWhiteListInfoRsp(this);
        }
    }
}
