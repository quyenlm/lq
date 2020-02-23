package com.garena.android.gpnprotocol.gpush;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class ConnectRequest extends Message {
    public static final Long DEFAULT_GPID = 0L;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT64)
    public final Long GPid;

    public ConnectRequest(Long GPid2) {
        this.GPid = GPid2;
    }

    private ConnectRequest(Builder builder) {
        this(builder.GPid);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ConnectRequest)) {
            return false;
        }
        return equals((Object) this.GPid, (Object) ((ConnectRequest) other).GPid);
    }

    public int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = this.GPid != null ? this.GPid.hashCode() : 0;
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<ConnectRequest> {
        public Long GPid;

        public Builder() {
        }

        public Builder(ConnectRequest message) {
            super(message);
            if (message != null) {
                this.GPid = message.GPid;
            }
        }

        public Builder GPid(Long GPid2) {
            this.GPid = GPid2;
            return this;
        }

        public ConnectRequest build() {
            checkRequiredFields();
            return new ConnectRequest(this);
        }
    }
}
