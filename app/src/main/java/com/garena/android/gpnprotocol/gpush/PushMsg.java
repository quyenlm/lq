package com.garena.android.gpnprotocol.gpush;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class PushMsg extends Message {
    public static final Long DEFAULT_GPID = 0L;
    public static final List<MsgType> DEFAULT_MSG = Collections.emptyList();
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT64)
    public final Long GPid;
    @ProtoField(label = Message.Label.REPEATED, messageType = MsgType.class, tag = 2)
    public final List<MsgType> Msg;

    public PushMsg(Long GPid2, List<MsgType> Msg2) {
        this.GPid = GPid2;
        this.Msg = immutableCopyOf(Msg2);
    }

    private PushMsg(Builder builder) {
        this(builder.GPid, builder.Msg);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PushMsg)) {
            return false;
        }
        PushMsg o = (PushMsg) other;
        if (!equals((Object) this.GPid, (Object) o.GPid) || !equals((List<?>) this.Msg, (List<?>) o.Msg)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = ((this.GPid != null ? this.GPid.hashCode() : 0) * 37) + (this.Msg != null ? this.Msg.hashCode() : 1);
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<PushMsg> {
        public Long GPid;
        public List<MsgType> Msg;

        public Builder() {
        }

        public Builder(PushMsg message) {
            super(message);
            if (message != null) {
                this.GPid = message.GPid;
                this.Msg = PushMsg.copyOf(message.Msg);
            }
        }

        public Builder GPid(Long GPid2) {
            this.GPid = GPid2;
            return this;
        }

        public Builder Msg(List<MsgType> Msg2) {
            this.Msg = checkForNulls(Msg2);
            return this;
        }

        public PushMsg build() {
            checkRequiredFields();
            return new PushMsg(this);
        }
    }
}
