package com.tencent.midas.oversea.network.http;

import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.conn.util.InetAddressUtils;

public class APBaseHttpParam {
    public static final int CONNECT_TIMEOUT = 21000;
    public static final String DOMAIN_ACCESS = "domain";
    public static final String IP_ACCESS = "ip";
    public static final int LONG_READ_TIMEOUT = 600000;
    public static final int READ_TIMEOUT = 21000;
    public static final int TRY_TIMES = 2;
    public long begTime;
    public int connectTimeout;
    public String defaultDomain;
    public String domain;
    public long endTime;
    public String httpAccessType;
    public String port;
    public int reTryTimes;
    public int readTimeout;
    public HashMap<String, String> reqParam;
    public String reqType;
    public int requestTimes;
    public String sendType;
    public String url;
    public String urlName;
    public String urlParams;

    public APBaseHttpParam() {
        this.reqType = "http://";
        this.sendType = "GET";
        this.defaultDomain = "";
        this.domain = "";
        this.port = "";
        this.urlName = "";
        this.urlParams = "";
        this.connectTimeout = 21000;
        this.readTimeout = 21000;
        this.requestTimes = 0;
        this.reTryTimes = 2;
        this.begTime = 0;
        this.endTime = 0;
        this.httpAccessType = IP_ACCESS;
        this.reqParam = new HashMap<>();
        this.domain = APAppDataInterface.singleton().getSysServerIP();
    }

    public void constructParams() {
        StringBuilder sb = new StringBuilder("");
        if (this.reqParam != null) {
            for (Map.Entry next : this.reqParam.entrySet()) {
                sb.append((String) next.getKey());
                sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                sb.append((String) next.getValue());
                sb.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                this.urlParams = sb.toString();
            }
        }
        APLog.i("APBaseHttpReq", "urlParams=" + this.urlParams);
    }

    public void constructReTryUrl() {
        if (this.requestTimes < this.reTryTimes) {
            if (this.requestTimes == this.reTryTimes - 1) {
                this.domain = this.defaultDomain;
                String str = "";
                if (isIPAddress(this.domain) && this.port.length() != 0) {
                    str = ":" + this.port;
                }
                this.url = this.reqType + this.domain + str + this.urlName;
            } else {
                this.domain = APIPList.getInstance().getRandomIP(this.domain);
                String str2 = "";
                if (isIPAddress(this.domain) && this.port.length() != 0) {
                    str2 = ":" + this.port;
                }
                this.url = this.reqType + this.domain + str2 + this.urlName;
            }
            this.requestTimes++;
        }
    }

    public void constructUrl() {
        constructParams();
        if (this.sendType.equals("GET")) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.url);
            if (!this.url.endsWith("?")) {
                stringBuffer.append("?");
            }
            stringBuffer.append(this.urlParams.toString());
            this.url = stringBuffer.toString();
        }
    }

    public boolean isIPAddress(String str) {
        return str != null && (InetAddressUtils.isIPv4Address(str) || InetAddressUtils.isIPv6Address(str));
    }

    public void setReqWithHttp() {
        this.reqType = "http://";
    }

    public void setReqWithHttps() {
        this.reqType = "https://";
    }

    public void setSendWithGet() {
        this.sendType = "GET";
    }

    public void setSendWithPost() {
        this.sendType = "POST";
    }

    public void setUrl(String str, String str2, String str3, String str4) {
        String env = APAppDataInterface.singleton().getEnv();
        String str5 = "";
        if (isIPAddress(this.domain) && this.port.length() != 0) {
            str5 = ":" + this.port;
        }
        APLog.i("APBaseHttpParam", " strEnv:" + env);
        this.defaultDomain = APNetCfg.getDomin();
        if (env.equals(APGlobalInfo.DevEnv)) {
            this.urlName = str2;
            this.url = this.reqType + this.domain + str5 + str2;
        } else if (env.equals(APGlobalInfo.TestEnv)) {
            this.urlName = str3;
            this.url = this.reqType + this.domain + str5 + str3;
        } else if (env.equals("release")) {
            this.urlName = str4;
            this.url = this.reqType + this.domain + str5 + str4;
        } else if (env.equals(APGlobalInfo.TestingEnv)) {
            this.urlName = String.format("/v1/r/%s/mobile_overseas_common", new Object[]{APAppDataInterface.singleton().getOfferid()});
            this.url = this.reqType + this.domain + str5 + this.urlName;
        }
        APLog.i("APBaseHttpParam", " url:" + this.url);
    }
}
