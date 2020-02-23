package com.garena.android.gpnprotocol.gpush;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class GetRegionResponse extends Message {
    public static final String DEFAULT_REGION = "";
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.STRING)
    public final String Region;

    public GetRegionResponse(String Region2) {
        this.Region = Region2;
    }

    private GetRegionResponse(Builder builder) {
        this(builder.Region);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GetRegionResponse)) {
            return false;
        }
        return equals((Object) this.Region, (Object) ((GetRegionResponse) other).Region);
    }

    public int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = this.Region != null ? this.Region.hashCode() : 0;
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<GetRegionResponse> {
        public String Region;

        public Builder() {
        }

        public Builder(GetRegionResponse message) {
            super(message);
            if (message != null) {
                this.Region = message.Region;
            }
        }

        public Builder Region(String Region2) {
            this.Region = Region2;
            return this;
        }

        public GetRegionResponse build() {
            checkRequiredFields();
            return new GetRegionResponse(this);
        }
    }
}
