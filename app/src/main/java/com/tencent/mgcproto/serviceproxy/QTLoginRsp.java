package com.tencent.mgcproto.serviceproxy;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class QTLoginRsp extends Message {
    public static final String DEFAULT_ERRMSG = "";
    public static final Integer DEFAULT_RESULT = 0;
    @ProtoField(tag = 2, type = Message.Datatype.STRING)
    public final String errMsg;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT32)
    public final Integer result;

    public QTLoginRsp(Integer result2, String errMsg2) {
        this.result = result2;
        this.errMsg = errMsg2;
    }

    private QTLoginRsp(Builder builder) {
        this(builder.result, builder.errMsg);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof QTLoginRsp)) {
            return false;
        }
        QTLoginRsp o = (QTLoginRsp) other;
        if (!equals((Object) this.result, (Object) o.result) || !equals((Object) this.errMsg, (Object) o.errMsg)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result2;
        int i = 0;
        int result3 = this.hashCode;
        if (result3 != 0) {
            return result3;
        }
        if (this.result != null) {
            result2 = this.result.hashCode();
        } else {
            result2 = 0;
        }
        int i2 = result2 * 37;
        if (this.errMsg != null) {
            i = this.errMsg.hashCode();
        }
        int result4 = i2 + i;
        this.hashCode = result4;
        return result4;
    }

    public static final class Builder extends Message.Builder<QTLoginRsp> {
        public String errMsg;
        public Integer result;

        public Builder() {
        }

        public Builder(QTLoginRsp message) {
            super(message);
            if (message != null) {
                this.result = message.result;
                this.errMsg = message.errMsg;
            }
        }

        public Builder result(Integer result2) {
            this.result = result2;
            return this;
        }

        public Builder errMsg(String errMsg2) {
            this.errMsg = errMsg2;
            return this;
        }

        public QTLoginRsp build() {
            checkRequiredFields();
            return new QTLoginRsp(this);
        }
    }
}
