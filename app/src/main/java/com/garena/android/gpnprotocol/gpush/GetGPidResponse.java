package com.garena.android.gpnprotocol.gpush;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class GetGPidResponse extends Message {
    public static final String DEFAULT_CONNSERVERADDR = "";
    public static final Long DEFAULT_GPID = 0L;
    @ProtoField(label = Message.Label.REQUIRED, tag = 2, type = Message.Datatype.STRING)
    public final String ConnServerAddr;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT64)
    public final Long GPid;

    public GetGPidResponse(Long GPid2, String ConnServerAddr2) {
        this.GPid = GPid2;
        this.ConnServerAddr = ConnServerAddr2;
    }

    private GetGPidResponse(Builder builder) {
        this(builder.GPid, builder.ConnServerAddr);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GetGPidResponse)) {
            return false;
        }
        GetGPidResponse o = (GetGPidResponse) other;
        if (!equals((Object) this.GPid, (Object) o.GPid) || !equals((Object) this.ConnServerAddr, (Object) o.ConnServerAddr)) {
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
        if (this.GPid != null) {
            result = this.GPid.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 37;
        if (this.ConnServerAddr != null) {
            i = this.ConnServerAddr.hashCode();
        }
        int result3 = i2 + i;
        this.hashCode = result3;
        return result3;
    }

    public static final class Builder extends Message.Builder<GetGPidResponse> {
        public String ConnServerAddr;
        public Long GPid;

        public Builder() {
        }

        public Builder(GetGPidResponse message) {
            super(message);
            if (message != null) {
                this.GPid = message.GPid;
                this.ConnServerAddr = message.ConnServerAddr;
            }
        }

        public Builder GPid(Long GPid2) {
            this.GPid = GPid2;
            return this;
        }

        public Builder ConnServerAddr(String ConnServerAddr2) {
            this.ConnServerAddr = ConnServerAddr2;
            return this;
        }

        public GetGPidResponse build() {
            checkRequiredFields();
            return new GetGPidResponse(this);
        }
    }
}
