package com.garena.android.gpnprotocol.gpush;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class GetGPidRequest extends Message {
    public static final Long DEFAULT_OLDID = 0L;
    public static final Long DEFAULT_SIGN = 0L;
    @ProtoField(tag = 2, type = Message.Datatype.UINT64)
    public final Long OldId;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT64)
    public final Long Sign;

    public GetGPidRequest(Long Sign2, Long OldId2) {
        this.Sign = Sign2;
        this.OldId = OldId2;
    }

    private GetGPidRequest(Builder builder) {
        this(builder.Sign, builder.OldId);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GetGPidRequest)) {
            return false;
        }
        GetGPidRequest o = (GetGPidRequest) other;
        if (!equals((Object) this.Sign, (Object) o.Sign) || !equals((Object) this.OldId, (Object) o.OldId)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        int i = 0;
        int result2 = this.hashCode;
        if (result2 != 0) {
            return result2;
        }
        if (this.Sign != null) {
            result = this.Sign.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 37;
        if (this.OldId != null) {
            i = this.OldId.hashCode();
        }
        int result3 = i2 + i;
        this.hashCode = result3;
        return result3;
    }

    public static final class Builder extends Message.Builder<GetGPidRequest> {
        public Long OldId;
        public Long Sign;

        public Builder() {
        }

        public Builder(GetGPidRequest message) {
            super(message);
            if (message != null) {
                this.Sign = message.Sign;
                this.OldId = message.OldId;
            }
        }

        public Builder Sign(Long Sign2) {
            this.Sign = Sign2;
            return this;
        }

        public Builder OldId(Long OldId2) {
            this.OldId = OldId2;
            return this;
        }

        public GetGPidRequest build() {
            checkRequiredFields();
            return new GetGPidRequest(this);
        }
    }
}
