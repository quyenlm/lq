package com.garena.android.gpnprotocol.gpush;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class PushMsgAck extends Message {
    public static final List<Integer> DEFAULT_FAILLIST = Collections.emptyList();
    public static final Integer DEFAULT_MSGID = 0;
    @ProtoField(label = Message.Label.REPEATED, tag = 2, type = Message.Datatype.UINT32)
    public final List<Integer> FailList;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT32)
    public final Integer Msgid;

    public PushMsgAck(Integer Msgid2, List<Integer> FailList2) {
        this.Msgid = Msgid2;
        this.FailList = immutableCopyOf(FailList2);
    }

    private PushMsgAck(Builder builder) {
        this(builder.Msgid, builder.FailList);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PushMsgAck)) {
            return false;
        }
        PushMsgAck o = (PushMsgAck) other;
        if (!equals((Object) this.Msgid, (Object) o.Msgid) || !equals((List<?>) this.FailList, (List<?>) o.FailList)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = ((this.Msgid != null ? this.Msgid.hashCode() : 0) * 37) + (this.FailList != null ? this.FailList.hashCode() : 1);
        this.hashCode = result2;
        return result2;
    }

    public static final class Builder extends Message.Builder<PushMsgAck> {
        public List<Integer> FailList;
        public Integer Msgid;

        public Builder() {
        }

        public Builder(PushMsgAck message) {
            super(message);
            if (message != null) {
                this.Msgid = message.Msgid;
                this.FailList = PushMsgAck.copyOf(message.FailList);
            }
        }

        public Builder Msgid(Integer Msgid2) {
            this.Msgid = Msgid2;
            return this;
        }

        public Builder FailList(List<Integer> FailList2) {
            this.FailList = checkForNulls(FailList2);
            return this;
        }

        public PushMsgAck build() {
            checkRequiredFields();
            return new PushMsgAck(this);
        }
    }
}
