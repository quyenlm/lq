package com.beetalk.sdk.plugin.impl.gglive;

public class GGLiveAuthToken {
    public final TokenData refreshToken;
    public final TokenData sessionToken;
    public final long uid;

    public GGLiveAuthToken(String sessionKey, int sessionKeyExpiryTime, long refreshTokenId, String refreshToken2, int refreshTokenExpiryTime, long uid2) {
        this.sessionToken = new TokenData(sessionKey, sessionKeyExpiryTime);
        this.refreshToken = new TokenData(refreshTokenId, refreshToken2, refreshTokenExpiryTime);
        this.uid = uid2;
    }

    public static class TokenData {
        public final int expiryTime;
        public final long id;
        public final String token;

        public TokenData(String token2, int expiryTime2) {
            this(0, token2, expiryTime2);
        }

        public TokenData(long id2, String token2, int expiryTime2) {
            this.id = id2;
            this.token = token2;
            this.expiryTime = expiryTime2;
        }

        public boolean isExpired() {
            return ((int) (System.currentTimeMillis() / 1000)) >= this.expiryTime;
        }
    }
}
