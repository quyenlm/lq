package com.garena.pay.android.data;

import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import java.util.HashMap;
import java.util.Map;

public class GGRedeemRequest {
    private String accessToken;
    private long rebateCardId = 0;
    private int roleId;
    private int serverId;

    public GGRedeemRequest(String accessToken2, int serverId2, int roleId2, long rebateCardId2) {
        this.accessToken = accessToken2;
        this.serverId = serverId2;
        this.roleId = roleId2;
        this.rebateCardId = rebateCardId2;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public int getServerId() {
        return this.serverId;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public long getRebateCardId() {
        return this.rebateCardId;
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", this.accessToken);
        params.put(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(2));
        params.put("app_server_id", String.valueOf(this.serverId));
        params.put("app_role_id", String.valueOf(this.roleId));
        if (this.rebateCardId > 0) {
            params.put(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID, String.valueOf(this.rebateCardId));
        }
        return params;
    }

    public static class GGRedeemRequestBuilder {
        private String accessToken;
        private long rebateCardId = 0;
        private int roleId;
        private int serverId;

        public GGRedeemRequestBuilder setAccessToken(String accessToken2) {
            this.accessToken = accessToken2;
            return this;
        }

        public GGRedeemRequestBuilder setServerId(int serverId2) {
            this.serverId = serverId2;
            return this;
        }

        public GGRedeemRequestBuilder setRoleId(int roleId2) {
            this.roleId = roleId2;
            return this;
        }

        public GGRedeemRequestBuilder setRebateCardId(long rebateCardId2) {
            this.rebateCardId = rebateCardId2;
            return this;
        }

        public GGRedeemRequest Build() {
            return new GGRedeemRequest(this.accessToken, this.serverId, this.roleId, this.rebateCardId);
        }
    }
}
