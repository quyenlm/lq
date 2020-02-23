package com.tencent.mgcproto.serviceproxy;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class HelloRsp extends Message {
    public static final Integer DEFAULT_CLOSE_TCP = 0;
    public static final Integer DEFAULT_HELLO_TIME = 0;
    public static final Integer DEFAULT_RESULT = 0;
    @ProtoField(tag = 3, type = Message.Datatype.UINT32)
    public final Integer close_tcp;
    @ProtoField(tag = 2, type = Message.Datatype.UINT32)
    public final Integer hello_time;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT32)
    public final Integer result;

    public HelloRsp(Integer result2, Integer hello_time2, Integer close_tcp2) {
        this.result = result2;
        this.hello_time = hello_time2;
        this.close_tcp = close_tcp2;
    }

    private HelloRsp(Builder builder) {
        this(builder.result, builder.hello_time, builder.close_tcp);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HelloRsp)) {
            return false;
        }
        HelloRsp o = (HelloRsp) other;
        if (!equals((Object) this.result, (Object) o.result) || !equals((Object) this.hello_time, (Object) o.hello_time) || !equals((Object) this.close_tcp, (Object) o.close_tcp)) {
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
        if (this.hello_time != null) {
            i = this.hello_time.hashCode();
        } else {
            i = 0;
        }
        int i3 = (hashCode + i) * 37;
        if (this.close_tcp != null) {
            i2 = this.close_tcp.hashCode();
        }
        int result3 = i3 + i2;
        this.hashCode = result3;
        return result3;
    }

    public static final class Builder extends Message.Builder<HelloRsp> {
        public Integer close_tcp;
        public Integer hello_time;
        public Integer result;

        public Builder() {
        }

        public Builder(HelloRsp message) {
            super(message);
            if (message != null) {
                this.result = message.result;
                this.hello_time = message.hello_time;
                this.close_tcp = message.close_tcp;
            }
        }

        public Builder result(Integer result2) {
            this.result = result2;
            return this;
        }

        public Builder hello_time(Integer hello_time2) {
            this.hello_time = hello_time2;
            return this;
        }

        public Builder close_tcp(Integer close_tcp2) {
            this.close_tcp = close_tcp2;
            return this;
        }

        public HelloRsp build() {
            checkRequiredFields();
            return new HelloRsp(this);
        }
    }
}
