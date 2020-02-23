package com.tencent.mgcproto.serviceproxy;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import okio.ByteString;

public final class QTLoginReq extends Message {
    public static final ByteString DEFAULT_MACHINE_CODE = ByteString.EMPTY;
    public static final ByteString DEFAULT_OPENID = ByteString.EMPTY;
    public static final ByteString DEFAULT_QT_TOKEN = ByteString.EMPTY;
    public static final ByteString DEFAULT_UUID = ByteString.EMPTY;
    @ProtoField(tag = 4, type = Message.Datatype.BYTES)
    public final ByteString machine_code;
    @ProtoField(tag = 1, type = Message.Datatype.BYTES)
    public final ByteString openid;
    @ProtoField(tag = 3, type = Message.Datatype.BYTES)
    public final ByteString qt_token;
    @ProtoField(tag = 2, type = Message.Datatype.BYTES)
    public final ByteString uuid;

    public QTLoginReq(ByteString openid2, ByteString uuid2, ByteString qt_token2, ByteString machine_code2) {
        this.openid = openid2;
        this.uuid = uuid2;
        this.qt_token = qt_token2;
        this.machine_code = machine_code2;
    }

    private QTLoginReq(Builder builder) {
        this(builder.openid, builder.uuid, builder.qt_token, builder.machine_code);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof QTLoginReq)) {
            return false;
        }
        QTLoginReq o = (QTLoginReq) other;
        if (!equals((Object) this.openid, (Object) o.openid) || !equals((Object) this.uuid, (Object) o.uuid) || !equals((Object) this.qt_token, (Object) o.qt_token) || !equals((Object) this.machine_code, (Object) o.machine_code)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2;
        int i3 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.openid != null ? this.openid.hashCode() : 0) * 37;
        if (this.uuid != null) {
            i = this.uuid.hashCode();
        } else {
            i = 0;
        }
        int i4 = (hashCode + i) * 37;
        if (this.qt_token != null) {
            i2 = this.qt_token.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (i4 + i2) * 37;
        if (this.machine_code != null) {
            i3 = this.machine_code.hashCode();
        }
        int result2 = i5 + i3;
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<QTLoginReq> {
        public ByteString machine_code;
        public ByteString openid;
        public ByteString qt_token;
        public ByteString uuid;

        public Builder() {
        }

        public Builder(QTLoginReq message) {
            super(message);
            if (message != null) {
                this.openid = message.openid;
                this.uuid = message.uuid;
                this.qt_token = message.qt_token;
                this.machine_code = message.machine_code;
            }
        }

        public Builder openid(ByteString openid2) {
            this.openid = openid2;
            return this;
        }

        public Builder uuid(ByteString uuid2) {
            this.uuid = uuid2;
            return this;
        }

        public Builder qt_token(ByteString qt_token2) {
            this.qt_token = qt_token2;
            return this;
        }

        public Builder machine_code(ByteString machine_code2) {
            this.machine_code = machine_code2;
            return this;
        }

        public QTLoginReq build() {
            return new QTLoginReq(this);
        }
    }
}
