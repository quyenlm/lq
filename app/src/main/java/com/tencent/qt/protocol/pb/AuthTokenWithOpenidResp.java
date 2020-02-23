package com.tencent.qt.protocol.pb;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;
import okio.ByteString;

public final class AuthTokenWithOpenidResp extends Message {
    public static final ByteString DEFAULT_ERRMSG = ByteString.EMPTY;
    public static final Integer DEFAULT_EXPIRES = 0;
    public static final List<Integer> DEFAULT_IPLIST = Collections.emptyList();
    public static final List<Integer> DEFAULT_PORTLIST = Collections.emptyList();
    public static final ByteString DEFAULT_QT_ACCESS_TOKEN = ByteString.EMPTY;
    public static final ByteString DEFAULT_QT_AUTH = ByteString.EMPTY;
    public static final String DEFAULT_QT_OPENID = "";
    public static final ByteString DEFAULT_QT_TOKEN = ByteString.EMPTY;
    public static final String DEFAULT_QT_UUID = "";
    public static final Integer DEFAULT_RESULT = 0;
    @ProtoField(tag = 2, type = Message.Datatype.BYTES)
    public final ByteString errmsg;
    @ProtoField(tag = 9, type = Message.Datatype.UINT32)
    public final Integer expires;
    @ProtoField(label = Message.Label.REPEATED, tag = 5, type = Message.Datatype.UINT32)
    public final List<Integer> iplist;
    @ProtoField(label = Message.Label.REPEATED, tag = 6, type = Message.Datatype.UINT32)
    public final List<Integer> portlist;
    @ProtoField(tag = 10, type = Message.Datatype.BYTES)
    public final ByteString qt_access_token;
    @ProtoField(tag = 7, type = Message.Datatype.BYTES)
    public final ByteString qt_auth;
    @ProtoField(tag = 4, type = Message.Datatype.STRING)
    public final String qt_openid;
    @ProtoField(tag = 8, type = Message.Datatype.BYTES)
    public final ByteString qt_token;
    @ProtoField(tag = 3, type = Message.Datatype.STRING)
    public final String qt_uuid;
    @ProtoField(label = Message.Label.REQUIRED, tag = 1, type = Message.Datatype.UINT32)
    public final Integer result;

    public AuthTokenWithOpenidResp(Integer result2, ByteString errmsg2, String qt_uuid2, String qt_openid2, List<Integer> iplist2, List<Integer> portlist2, ByteString qt_auth2, ByteString qt_token2, Integer expires2, ByteString qt_access_token2) {
        this.result = result2;
        this.errmsg = errmsg2;
        this.qt_uuid = qt_uuid2;
        this.qt_openid = qt_openid2;
        this.iplist = immutableCopyOf(iplist2);
        this.portlist = immutableCopyOf(portlist2);
        this.qt_auth = qt_auth2;
        this.qt_token = qt_token2;
        this.expires = expires2;
        this.qt_access_token = qt_access_token2;
    }

    private AuthTokenWithOpenidResp(Builder builder) {
        this(builder.result, builder.errmsg, builder.qt_uuid, builder.qt_openid, builder.iplist, builder.portlist, builder.qt_auth, builder.qt_token, builder.expires, builder.qt_access_token);
        setBuilder(builder);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AuthTokenWithOpenidResp)) {
            return false;
        }
        AuthTokenWithOpenidResp o = (AuthTokenWithOpenidResp) other;
        if (!equals((Object) this.result, (Object) o.result) || !equals((Object) this.errmsg, (Object) o.errmsg) || !equals((Object) this.qt_uuid, (Object) o.qt_uuid) || !equals((Object) this.qt_openid, (Object) o.qt_openid) || !equals((List<?>) this.iplist, (List<?>) o.iplist) || !equals((List<?>) this.portlist, (List<?>) o.portlist) || !equals((Object) this.qt_auth, (Object) o.qt_auth) || !equals((Object) this.qt_token, (Object) o.qt_token) || !equals((Object) this.expires, (Object) o.expires) || !equals((Object) this.qt_access_token, (Object) o.qt_access_token)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = 1;
        int i9 = 0;
        int result2 = this.hashCode;
        if (result2 != 0) {
            return result2;
        }
        int hashCode = (this.result != null ? this.result.hashCode() : 0) * 37;
        if (this.errmsg != null) {
            i = this.errmsg.hashCode();
        } else {
            i = 0;
        }
        int i10 = (hashCode + i) * 37;
        if (this.qt_uuid != null) {
            i2 = this.qt_uuid.hashCode();
        } else {
            i2 = 0;
        }
        int i11 = (i10 + i2) * 37;
        if (this.qt_openid != null) {
            i3 = this.qt_openid.hashCode();
        } else {
            i3 = 0;
        }
        int i12 = (i11 + i3) * 37;
        if (this.iplist != null) {
            i4 = this.iplist.hashCode();
        } else {
            i4 = 1;
        }
        int i13 = (i12 + i4) * 37;
        if (this.portlist != null) {
            i8 = this.portlist.hashCode();
        }
        int i14 = (i13 + i8) * 37;
        if (this.qt_auth != null) {
            i5 = this.qt_auth.hashCode();
        } else {
            i5 = 0;
        }
        int i15 = (i14 + i5) * 37;
        if (this.qt_token != null) {
            i6 = this.qt_token.hashCode();
        } else {
            i6 = 0;
        }
        int i16 = (i15 + i6) * 37;
        if (this.expires != null) {
            i7 = this.expires.hashCode();
        } else {
            i7 = 0;
        }
        int i17 = (i16 + i7) * 37;
        if (this.qt_access_token != null) {
            i9 = this.qt_access_token.hashCode();
        }
        int result3 = i17 + i9;
        this.hashCode = result3;
        return result3;
    }

    public static final class Builder extends Message.Builder<AuthTokenWithOpenidResp> {
        public ByteString errmsg;
        public Integer expires;
        public List<Integer> iplist;
        public List<Integer> portlist;
        public ByteString qt_access_token;
        public ByteString qt_auth;
        public String qt_openid;
        public ByteString qt_token;
        public String qt_uuid;
        public Integer result;

        public Builder() {
        }

        public Builder(AuthTokenWithOpenidResp message) {
            super(message);
            if (message != null) {
                this.result = message.result;
                this.errmsg = message.errmsg;
                this.qt_uuid = message.qt_uuid;
                this.qt_openid = message.qt_openid;
                this.iplist = AuthTokenWithOpenidResp.copyOf(message.iplist);
                this.portlist = AuthTokenWithOpenidResp.copyOf(message.portlist);
                this.qt_auth = message.qt_auth;
                this.qt_token = message.qt_token;
                this.expires = message.expires;
                this.qt_access_token = message.qt_access_token;
            }
        }

        public Builder result(Integer result2) {
            this.result = result2;
            return this;
        }

        public Builder errmsg(ByteString errmsg2) {
            this.errmsg = errmsg2;
            return this;
        }

        public Builder qt_uuid(String qt_uuid2) {
            this.qt_uuid = qt_uuid2;
            return this;
        }

        public Builder qt_openid(String qt_openid2) {
            this.qt_openid = qt_openid2;
            return this;
        }

        public Builder iplist(List<Integer> iplist2) {
            this.iplist = checkForNulls(iplist2);
            return this;
        }

        public Builder portlist(List<Integer> portlist2) {
            this.portlist = checkForNulls(portlist2);
            return this;
        }

        public Builder qt_auth(ByteString qt_auth2) {
            this.qt_auth = qt_auth2;
            return this;
        }

        public Builder qt_token(ByteString qt_token2) {
            this.qt_token = qt_token2;
            return this;
        }

        public Builder expires(Integer expires2) {
            this.expires = expires2;
            return this;
        }

        public Builder qt_access_token(ByteString qt_access_token2) {
            this.qt_access_token = qt_access_token2;
            return this;
        }

        public AuthTokenWithOpenidResp build() {
            checkRequiredFields();
            return new AuthTokenWithOpenidResp(this);
        }
    }
}
