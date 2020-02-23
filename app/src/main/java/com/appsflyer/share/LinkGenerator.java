package com.appsflyer.share;

import android.content.Context;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.ServerConfigHandler;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHost;

public class LinkGenerator {

    /* renamed from: ʻ  reason: contains not printable characters */
    private String f204;

    /* renamed from: ʼ  reason: contains not printable characters */
    private String f205;

    /* renamed from: ʽ  reason: contains not printable characters */
    private String f206;

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f207;

    /* renamed from: ˋ  reason: contains not printable characters */
    private String f208;

    /* renamed from: ˋॱ  reason: contains not printable characters */
    private String f209;

    /* renamed from: ˎ  reason: contains not printable characters */
    private String f210;

    /* renamed from: ˏ  reason: contains not printable characters */
    private String f211;

    /* renamed from: ˏॱ  reason: contains not printable characters */
    private Map<String, String> f212 = new HashMap();

    /* renamed from: ͺ  reason: contains not printable characters */
    private Map<String, String> f213 = new HashMap();

    /* renamed from: ॱ  reason: contains not printable characters */
    private String f214;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private String f215;

    /* renamed from: ᐝ  reason: contains not printable characters */
    private String f216;

    public LinkGenerator(String str) {
        this.f208 = str;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final LinkGenerator m154(String str) {
        this.f216 = str;
        return this;
    }

    public LinkGenerator setDeeplinkPath(String str) {
        this.f206 = str;
        return this;
    }

    public LinkGenerator setBaseDeeplink(String str) {
        this.f209 = str;
        return this;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final LinkGenerator m155(String str) {
        this.f205 = str;
        return this;
    }

    public LinkGenerator setChannel(String str) {
        this.f211 = str;
        return this;
    }

    public String getChannel() {
        return this.f211;
    }

    public LinkGenerator setReferrerCustomerId(String str) {
        this.f214 = str;
        return this;
    }

    public String getMediaSource() {
        return this.f208;
    }

    public Map<String, String> getParameters() {
        return this.f213;
    }

    public LinkGenerator setCampaign(String str) {
        this.f207 = str;
        return this;
    }

    public String getCampaign() {
        return this.f207;
    }

    public LinkGenerator addParameter(String str, String str2) {
        this.f213.put(str, str2);
        return this;
    }

    public LinkGenerator addParameters(Map<String, String> map) {
        if (map != null) {
            this.f213.putAll(map);
        }
        return this;
    }

    public LinkGenerator setReferrerUID(String str) {
        this.f210 = str;
        return this;
    }

    public LinkGenerator setReferrerName(String str) {
        this.f204 = str;
        return this;
    }

    public LinkGenerator setReferrerImageURL(String str) {
        this.f215 = str;
        return this;
    }

    public LinkGenerator setBaseURL(String str, String str2, String str3) {
        if (str == null || str.length() <= 0) {
            this.f205 = String.format(Constants.AF_BASE_URL_FORMAT, new Object[]{ServerConfigHandler.getUrl(Constants.APPSFLYER_DEFAULT_APP_DOMAIN), str3});
        } else {
            if (str2 == null || str2.length() < 5) {
                str2 = Constants.ONELINK_DEFAULT_DOMAIN;
            }
            this.f205 = String.format(Constants.AF_BASE_URL_FORMAT, new Object[]{str2, str});
        }
        return this;
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private StringBuilder m152() {
        StringBuilder sb = new StringBuilder();
        if (this.f205 == null || !this.f205.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            sb.append(ServerConfigHandler.getUrl(Constants.BASE_URL_APP_APPSFLYER_COM));
        } else {
            sb.append(this.f205);
        }
        if (this.f216 != null) {
            sb.append('/').append(this.f216);
        }
        this.f212.put(Constants.URL_MEDIA_SOURCE, this.f208);
        sb.append('?').append("pid=").append(m153(this.f208, "media source"));
        if (this.f210 != null) {
            this.f212.put(Constants.URL_REFERRER_UID, this.f210);
            sb.append('&').append("af_referrer_uid=").append(m153(this.f210, "referrerUID"));
        }
        if (this.f211 != null) {
            this.f212.put("af_channel", this.f211);
            sb.append('&').append("af_channel=").append(m153(this.f211, "channel"));
        }
        if (this.f214 != null) {
            this.f212.put(Constants.URL_REFERRER_CUSTOMER_ID, this.f214);
            sb.append('&').append("af_referrer_customer_id=").append(m153(this.f214, "referrerCustomerId"));
        }
        if (this.f207 != null) {
            this.f212.put("c", this.f207);
            sb.append('&').append("c=").append(m153(this.f207, FirebaseAnalytics.Param.CAMPAIGN));
        }
        if (this.f204 != null) {
            this.f212.put(Constants.URL_REFERRER_NAME, this.f204);
            sb.append('&').append("af_referrer_name=").append(m153(this.f204, "referrerName"));
        }
        if (this.f215 != null) {
            this.f212.put(Constants.URL_REFERRER_IMAGE_URL, this.f215);
            sb.append('&').append("af_referrer_image_url=").append(m153(this.f215, "referrerImageURL"));
        }
        if (this.f209 != null) {
            StringBuilder append = new StringBuilder().append(this.f209);
            append.append(this.f209.endsWith(Constants.URL_PATH_DELIMITER) ? "" : Constants.URL_PATH_DELIMITER);
            if (this.f206 != null) {
                append.append(this.f206);
            }
            this.f212.put(Constants.URL_BASE_DEEPLINK, append.toString());
            sb.append('&').append("af_dp=").append(m153(this.f209, "baseDeeplink"));
            if (this.f206 != null) {
                sb.append(this.f209.endsWith(Constants.URL_PATH_DELIMITER) ? "" : "%2F").append(m153(this.f206, "deeplinkPath"));
            }
        }
        for (String next : this.f213.keySet()) {
            if (!sb.toString().contains(new StringBuilder().append(next).append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(m153(this.f213.get(next), next)).toString())) {
                sb.append('&').append(next).append('=').append(m153(this.f213.get(next), next));
            }
        }
        return sb;
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static String m153(String str, String str2) {
        try {
            return URLEncoder.encode(str, "utf8");
        } catch (UnsupportedEncodingException e) {
            AFLogger.afInfoLog(new StringBuilder("Illegal ").append(str2).append(": ").append(str).toString());
            return "";
        } catch (Throwable th) {
            return "";
        }
    }

    public String generateLink() {
        return m152().toString();
    }

    public void generateLink(Context context, CreateOneLinkHttpTask.ResponseListener responseListener) {
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_ID);
        if (!this.f213.isEmpty()) {
            for (Map.Entry next : this.f213.entrySet()) {
                this.f212.put(next.getKey(), next.getValue());
            }
        }
        m152();
        ShareInviteHelper.generateUserInviteLink(context, string, this.f212, responseListener);
    }
}
