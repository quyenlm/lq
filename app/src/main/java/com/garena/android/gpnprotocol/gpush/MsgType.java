package com.garena.android.gpnprotocol.gpush;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import okio.ByteString;

public final class MsgType extends Message {
    public static final Integer DEFAULT_APPID = 0;
    public static final ByteString DEFAULT_DATA = ByteString.EMPTY;
    public static final Integer DEFAULT_MSGID = 0;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT32)
    public final Integer Appid;
    @ProtoField(label = Message.Label.REQUIRED, tag = 3, type = Message.Datatype.BYTES)
    public final ByteString Data;
    @ProtoField(label = Message.Label.REQUIRED, tag = 2, type = Message.Datatype.UINT32)
    public final Integer Msgid;

    public MsgType(Integer Appid2, Integer Msgid2, ByteString Data2) {
        this.Appid = Appid2;
        this.Msgid = Msgid2;
        this.Data = Data2;
    }

    private MsgType(Builder builder) {
        this(builder.Appid, builder.Msgid, builder.Data);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MsgType)) {
            return false;
        }
        MsgType o = (MsgType) other;
        if (!equals((Object) this.Appid, (Object) o.Appid) || !equals((Object) this.Msgid, (Object) o.Msgid) || !equals((Object) this.Data, (Object) o.Data)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.Appid != null ? this.Appid.hashCode() : 0) * 37;
        if (this.Msgid != null) {
            i = this.Msgid.hashCode();
        } else {
            i = 0;
        }
        int i3 = (hashCode + i) * 37;
        if (this.Data != null) {
            i2 = this.Data.hashCode();
        }
        int result2 = i3 + i2;
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<MsgType> {
        public Integer Appid;
        public ByteString Data;
        public Integer Msgid;

        public Builder() {
        }

        public Builder(MsgType message) {
            super(message);
            if (message != null) {
                this.Appid = message.Appid;
                this.Msgid = message.Msgid;
                this.Data = message.Data;
            }
        }

        public Builder Appid(Integer Appid2) {
            this.Appid = Appid2;
            return this;
        }

        public Builder Msgid(Integer Msgid2) {
            this.Msgid = Msgid2;
            return this;
        }

        public Builder Data(ByteString Data2) {
            this.Data = Data2;
            return this;
        }

        public MsgType build() {
            checkRequiredFields();
            return new MsgType(this);
        }
    }
}
