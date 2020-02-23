package com.tencent.mgcproto.serviceproxy;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class HelloReq extends Message {
    public static final Integer DEFAULT_CUR_TIME = 0;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT32)
    public final Integer cur_time;

    public HelloReq(Integer cur_time2) {
        this.cur_time = cur_time2;
    }

    private HelloReq(Builder builder) {
        this(builder.cur_time);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HelloReq)) {
            return false;
        }
        return equals((Object) this.cur_time, (Object) ((HelloReq) other).cur_time);
    }

    public int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = this.cur_time != null ? this.cur_time.hashCode() : 0;
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<HelloReq> {
        public Integer cur_time;

        public Builder() {
        }

        public Builder(HelloReq message) {
            super(message);
            if (message != null) {
                this.cur_time = message.cur_time;
            }
        }

        public Builder cur_time(Integer cur_time2) {
            this.cur_time = cur_time2;
            return this;
        }

        public HelloReq build() {
            checkRequiredFields();
            return new HelloReq(this);
        }
    }
}
