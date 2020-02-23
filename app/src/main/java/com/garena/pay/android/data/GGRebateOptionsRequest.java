package com.garena.pay.android.data;

import android.content.Context;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.LocaleHelper;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GGRebateOptionsRequest {
    private String appId;
    private Locale locale = LocaleHelper.getDefaultLocale();
    private String openId;
    private Integer platform;
    private int roleId = 0;
    private int serverId = 0;
    private String token;

    public GGRebateOptionsRequest(String appId2, Integer platform2, String openId2, String token2, int serverId2, int roleId2, Locale locale2) {
        this.appId = appId2;
        this.platform = platform2;
        this.openId = openId2;
        this.token = token2;
        this.serverId = serverId2;
        this.roleId = roleId2;
        this.locale = locale2;
    }

    public GGRebateOptionsRequest(String appId2, Integer platform2, String openId2, String token2, int serverId2, int roleId2) {
        this.appId = appId2;
        this.platform = platform2;
        this.openId = openId2;
        this.token = token2;
        this.serverId = serverId2;
        this.roleId = roleId2;
    }

    public String getAppId() {
        return this.appId;
    }

    public Integer getPlatform() {
        return this.platform;
    }

    public String getOpenId() {
        return this.openId;
    }

    public String getToken() {
        return this.token;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public int getServerId() {
        return this.serverId;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public Map<String, String> getParams(Context context) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", this.token);
        map.put("app_id", this.appId);
        map.put("open_id", this.openId);
        map.put("platform", String.valueOf(this.platform));
        map.put(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(2));
        map.put("app_server_id", String.valueOf(this.serverId));
        map.put("app_role_id", String.valueOf(this.roleId));
        if (this.locale != null) {
            map.put("locale", LocaleHelper.getLocaleStringForServer(this.locale));
        } else {
            map.put("locale", LocaleHelper.getLocaleStringForServer(LocaleHelper.getDefaultLocale(context)));
        }
        BBLogger.d("Rebate Options Get Request Data %s", map.toString());
        return map;
    }

    public static class GGRebateOptionsRequestBuilder {
        private String appId;
        private Locale locale;
        private String openId;
        private Integer platform;
        private int roleId = 0;
        private int serverId = 0;
        private String token;

        public GGRebateOptionsRequestBuilder setAppId(String appId2) {
            this.appId = appId2;
            return this;
        }

        public GGRebateOptionsRequestBuilder setPlatform(Integer platform2) {
            this.platform = platform2;
            return this;
        }

        public GGRebateOptionsRequestBuilder setOpenId(String openId2) {
            this.openId = openId2;
            return this;
        }

        public GGRebateOptionsRequestBuilder setToken(String token2) {
            this.token = token2;
            return this;
        }

        public GGRebateOptionsRequestBuilder setLocale(Locale locale2) {
            this.locale = locale2;
            return this;
        }

        public GGRebateOptionsRequestBuilder setServerId(int serverId2) {
            this.serverId = serverId2;
            return this;
        }

        public GGRebateOptionsRequestBuilder setRoleId(int roleId2) {
            this.roleId = roleId2;
            return this;
        }

        public GGRebateOptionsRequest Build() {
            if (this.locale == null) {
                return new GGRebateOptionsRequest(this.appId, this.platform, this.openId, this.token, this.serverId, this.roleId);
            }
            return new GGRebateOptionsRequest(this.appId, this.platform, this.openId, this.token, this.serverId, this.roleId, this.locale);
        }
    }
}
