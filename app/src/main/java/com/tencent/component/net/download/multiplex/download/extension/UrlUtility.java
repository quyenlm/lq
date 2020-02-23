package com.tencent.component.net.download.multiplex.download.extension;

import android.net.Uri;
import android.text.TextUtils;
import com.amazonaws.services.s3.util.Mimetypes;
import com.appsflyer.share.Constants;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.component.net.download.multiplex.DownloaderLog;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.tool.etc.APNUtil;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.smtt.sdk.WebView;
import com.vk.sdk.api.model.VKApiUserFull;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UrlUtility {
    private static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*[;\\s*charset=\\s*]*([^\"]*)\\s*$", 2);
    private static final Pattern INLINE_CONTENT_DISPOSITION_PATTERN = Pattern.compile("inline;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*[;\\s*charset=\\s*]*([^\"]*)\\s*$", 2);
    public static final int INVALID = 3;
    public static final String KEY_PAYURL = "webpay_url";
    public static final int PAY = 1;
    private static String[] SUFFIX = {"gd", "com", "cn", "ac", "edu", "gov", "mil", "arpa", APNUtil.ANP_NAME_NET, "org", "biz", APNetworkManager2.HTTP_KEY_OVERSEAINFO, "pro", "name", "coop", "mobi", "int", "us", "travel", "xxx", "idv", "co", "so", VKApiUserFull.TV, "hk", "asia", "me", "cc", "tw"};
    private static final String TAG = "UrlUtility";
    private static final String URL_CODING = "utf-8";
    private static Pattern VALID_IP_ADDRESS = Pattern.compile("(\\d){1,3}\\.(\\d){1,3}\\.(\\d){1,3}\\.(\\d){1,3}(:\\d{1,4})?(/(.*))?", 2);
    private static Pattern VALID_LOCAL_URL = Pattern.compile("(.+)localhost(:)?(\\d)*/(.+)(\\.)(.+)", 2);
    private static Pattern VALID_MTT_URL = Pattern.compile("mtt://(.+)", 2);
    private static Pattern VALID_PAY_URL = Pattern.compile("(tenpay|alipay)://(.+)", 2);
    private static Pattern VALID_QB_URL = Pattern.compile("qb://(.+)", 2);
    private static Pattern VALID_URL = Pattern.compile("(.+)(\\.)(.+)[^\\w]*(.*)", 2);
    public static final int WEBPAY = 2;
    private static final String[] alipaySites = {"alipay.com", "115.124.16.81", "110.75.128.59"};

    public static boolean isAlipaySite(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        for (String host : alipaySites) {
            if (host != null && url.contains(host)) {
                return true;
            }
        }
        return false;
    }

    public static String prepareUrl(String url) {
        if (url == null || url.length() == 0 || url.charAt(0) == '#') {
            return url;
        }
        try {
            url = url.replaceAll(" ", "%20").replaceAll("&amp;", HttpRequest.HTTP_REQ_ENTITY_JOIN).replaceAll("\\|", "%7C").replaceAll("\\^", "%5E").replaceAll("<", "%3C").replaceAll(">", "%3E").replaceAll("\\{", "%7B").replaceAll("\\}", "%7D");
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
        if (!isSmsUrl(url)) {
            return escapeAllChineseChar(url);
        }
        return url;
    }

    public static URL toURL(String url) throws MalformedURLException {
        int idx;
        URL _URL = new URL(url);
        if (_URL.getPath() != null && !"".equals(_URL.getPath())) {
            return _URL;
        }
        if (!(_URL.getFile() == null || !_URL.getFile().startsWith("?") || (idx = url.indexOf(63)) == -1)) {
            _URL = new URL(url.substring(0, idx) + '/' + url.substring(idx));
        }
        if (_URL.getFile() != null && !"".equals(_URL.getFile())) {
            return _URL;
        }
        return new URL(url + Constants.URL_PATH_DELIMITER);
    }

    public static boolean isEmptyUrl(String url) {
        return url == null || url.trim().length() == 0 || url.trim().equals(Constants.URL_PATH_DELIMITER);
    }

    public static String escapeAllChineseChar(String url) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);
            if ((c < 19968 || c > 40959) && (c < 65072 || c > 65440)) {
                sb.append(c);
            } else {
                try {
                    String escapedStr = URLEncoder.encode(String.valueOf(c), "utf-8");
                    DownloaderLog.d(TAG, " : " + escapedStr);
                    sb.append(escapedStr);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String escape(String url) {
        if (url == null || url.trim().length() == 0) {
            return url;
        }
        try {
            int idx = url.indexOf(63);
            if (idx == -1) {
                return url;
            }
            String path = url.substring(0, idx + 1);
            String[] nameValues = url.substring(idx + 1).split(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            StringBuilder sb = new StringBuilder();
            sb.append(path);
            boolean hasParam = false;
            for (String nameValue : nameValues) {
                if (nameValue != null && nameValue.length() > 0) {
                    hasParam = true;
                    int idxEqual = nameValue.indexOf(61);
                    if (idxEqual != -1) {
                        sb.append(nameValue.substring(0, idxEqual + 1));
                        String value = nameValue.substring(idxEqual + 1);
                        if (value != null && value.length() > 0) {
                            int i = 0;
                            int j = 0;
                            boolean ascii = true;
                            while (i < value.length()) {
                                char c = value.charAt(i);
                                if ((c < 19968 || c > 40959) && (c < 65072 || c > 65440)) {
                                    if (!ascii) {
                                        sb.append(URLEncoder.encode(value.substring(j, i), "utf-8"));
                                    }
                                    if (c == '/') {
                                        sb.append(c);
                                    } else {
                                        sb.append(c);
                                    }
                                    ascii = true;
                                    j = i;
                                } else {
                                    ascii = false;
                                }
                                i++;
                            }
                            if (!ascii && j < i) {
                                sb.append(URLEncoder.encode(value.substring(j, i), "utf-8"));
                            }
                        }
                    } else {
                        sb.append(nameValue);
                    }
                    sb.append('&');
                }
            }
            String escapedUrl = sb.toString();
            if (hasParam && escapedUrl.charAt(escapedUrl.length() - 1) == '&') {
                escapedUrl = escapedUrl.substring(0, escapedUrl.length() - 1);
            }
            return escapedUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return url;
        }
    }

    public static boolean isJavascript(String url) {
        return url != null && url.length() > 10 && url.substring(0, 11).equalsIgnoreCase("javascript:");
    }

    public static String getJavascriptCommand(String url) {
        int idx1;
        int idx12 = url.indexOf(58);
        int idx2 = url.indexOf(59);
        if (idx12 == -1) {
            idx1 = 0;
        } else {
            idx1 = idx12 + 1;
        }
        if (idx2 == -1) {
            idx2 = url.length();
        }
        return url.substring(idx1, idx2);
    }

    public static boolean isAnchorUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.startsWith("#");
    }

    public static boolean isWxUrl(String url) {
        if (TextUtils.isEmpty(url) || !url.startsWith("mttbrowser://")) {
            return false;
        }
        return true;
    }

    public static boolean isH5QQUrl(String url) {
        if (TextUtils.isEmpty(url) || !url.startsWith("http://openmobile.qq.com/api/check?page=shareindex.html&style=9")) {
            return false;
        }
        return true;
    }

    public static boolean getQqResult(String url) {
        String url2 = deletePrefix(url);
        if (!TextUtils.isEmpty(url2)) {
            Map<String, String> param_map = new HashMap<>();
            for (String param : url2.split(HttpRequest.HTTP_REQ_ENTITY_JOIN)) {
                String[] key_value = param.split(HttpRequest.HTTP_REQ_ENTITY_MERGE, 2);
                if (key_value.length == 2) {
                    param_map.put(key_value[0], key_value[1]);
                }
            }
            if (!TextUtils.isEmpty(param_map.get(GGLiveConstants.PARAM.RESULT))) {
                return param_map.get(GGLiveConstants.PARAM.RESULT).equals("complete");
            }
        }
        return false;
    }

    public static boolean isMRVPUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url != null && url.length() > 7 && url.substring(0, 7).equalsIgnoreCase("mrvp://")) {
            return true;
        }
        if (!isQQDomain(url)) {
            return false;
        }
        String sPath = getPath(url);
        if (TextUtils.isEmpty(sPath) || !sPath.contains("mrvp://")) {
            return false;
        }
        return true;
    }

    public static boolean isSamsungUrl(String url) {
        if (TextUtils.isEmpty(url) || !url.startsWith("samsungapps://")) {
            return false;
        }
        return true;
    }

    public static boolean isSpecialUrl(String url) {
        if (url == null) {
            return false;
        }
        String lowser = url.toLowerCase();
        if (lowser.startsWith("samsungapps://") || lowser.startsWith("about:blank")) {
            return true;
        }
        return false;
    }

    public static String deleteCustomPrefix(String srcUrl) {
        String url = srcUrl;
        if (isDttpUrl(srcUrl) || isSecurityCacheUrl(srcUrl) || isSecurityFileUrl(srcUrl) || isBrokerUrl(url) || isWebkitUrl(url)) {
            return deletePrefix(srcUrl);
        }
        return url;
    }

    public static String deleteHttpPrefix(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (url.startsWith("http://")) {
            return url.substring("http://".length());
        }
        if (url.startsWith("https://")) {
            return url.substring("https://".length());
        }
        return url;
    }

    public static String getPrefix(String url) {
        int pos;
        if (TextUtils.isEmpty(url) || (pos = url.indexOf("://")) <= 0) {
            return "";
        }
        return url.substring(0, pos + 3);
    }

    public static void addStringToList(String string, Vector<String> list, int maxNum) {
        if (!TextUtils.isEmpty(string) && list != null) {
            boolean isNeed = true;
            Iterator i$ = list.iterator();
            while (i$.hasNext()) {
                if (i$.next().equalsIgnoreCase(string)) {
                    isNeed = false;
                }
            }
            if (isNeed) {
                if (maxNum > 0 && list.size() >= maxNum) {
                    list.remove(0);
                }
                list.add(string);
            }
        }
    }

    public static boolean isBrokerUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        if (url.startsWith("page:") || url.startsWith("hotpre:")) {
            return true;
        }
        return false;
    }

    public static boolean isLocalUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        if (isFileUrl(url) || isLoginUrl(url)) {
            return true;
        }
        return false;
    }

    public static boolean isReadUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.startsWith("qb://ext/read");
    }

    public static boolean isSecurityCacheUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.startsWith("security://");
    }

    public static boolean isSecurityFileUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.startsWith("securityFile://");
    }

    public static boolean isDataBase64Url(String url) {
        return url != null && url.startsWith("data:text/html; charset=utf-8;base64,");
    }

    public static boolean isHttpUrl(String url) {
        return url != null && url.length() > 6 && url.substring(0, 7).equalsIgnoreCase("http://");
    }

    public static boolean isHttpsUrl(String url) {
        return url != null && url.length() > 7 && url.substring(0, 8).equalsIgnoreCase("https://");
    }

    public static boolean isWebUrl(String url) {
        return isHttpUrl(url) || isHttpsUrl(url);
    }

    public static boolean isDataUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.substring(0, 5).equalsIgnoreCase("data:");
    }

    public static boolean isFileUrl(String url) {
        return url != null && url.length() > 6 && url.substring(0, 7).equalsIgnoreCase(IMSDKFileProvider.FILE_SCHEME);
    }

    public static boolean isLoginUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.startsWith("Login://");
    }

    public static boolean isMttLoginUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.startsWith("*");
    }

    public static boolean isMarketUrl(String url) {
        return url != null && url.length() > 8 && url.substring(0, 9).equalsIgnoreCase("market://");
    }

    public static boolean isRtspUrl(String url) {
        return url != null && url.length() > 6 && url.substring(0, 7).equalsIgnoreCase("rtsp://");
    }

    public static boolean isFtpUrl(String url) {
        return url != null && url.length() > 5 && url.substring(0, 6).equalsIgnoreCase("ftp://");
    }

    public static boolean isSmsUrl(String url) {
        return url != null && url.length() > 4 && url.substring(0, 4).equalsIgnoreCase("sms:");
    }

    public static boolean isTelUrl(String url) {
        return url != null && url.length() > 4 && url.substring(0, 4).equalsIgnoreCase(WebView.SCHEME_TEL);
    }

    public static String getTelUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (isTelUrl(url)) {
            return url;
        }
        String tmpUrl = getWtaiUrl(url);
        if (tmpUrl != null) {
            return WebView.SCHEME_TEL + tmpUrl;
        }
        return null;
    }

    public static String getMailUrl(String url) {
        if (url == null || "".equalsIgnoreCase(url) || !isMailUrl(url)) {
            return null;
        }
        return url;
    }

    public static boolean isMailUrl(String url) {
        return url != null && url.length() > 7 && url.substring(0, 7).equalsIgnoreCase(WebView.SCHEME_MAILTO);
    }

    public static boolean isWtaiUrl(String url) {
        return url != null && url.length() > 13 && url.substring(0, 13).equalsIgnoreCase("wtai://wp/mc;");
    }

    public static String getWtaiUrl(String url) {
        if (!isWtaiUrl(url)) {
            return null;
        }
        int pos = url.indexOf("?", 13);
        if (pos != -1) {
            return url.substring(13, pos);
        }
        return url.substring(13);
    }

    public static boolean isMttProtocolUrl(String url) {
        return url != null && url.length() > 5 && url.substring(0, 5).equalsIgnoreCase("qb://");
    }

    public static boolean isWapPrefix(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        int idx = url.indexOf("://");
        if (idx != -1) {
            url = url.substring(idx + 3);
        }
        if (url.startsWith("wap.") || url.contains(".3g.") || url.startsWith("3g.") || url.contains(".wap.")) {
            return true;
        }
        return false;
    }

    public static boolean isDttpUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.toLowerCase().startsWith("dttp://");
    }

    public static boolean isWebkitUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.toLowerCase().startsWith("webkit://");
    }

    public static String deletePrefix(String url) {
        int index;
        if (url == null) {
            return null;
        }
        if (url.startsWith("page://") && (index = url.indexOf("http://")) != -1) {
            return url.substring(index);
        }
        int pos = url.indexOf("://");
        if (pos >= 0) {
            return url.substring(pos + 3);
        }
        return url;
    }

    public static String stripSchemePrefix(String url) {
        int idx = url.indexOf("://");
        if (idx != -1) {
            return url.substring(idx + 3);
        }
        return url;
    }

    public static String stripAnhcor(String url) throws MalformedURLException {
        int anchorIndex = url.indexOf(35);
        if (anchorIndex != -1) {
            return url.substring(0, anchorIndex);
        }
        return url;
    }

    public static URL stripQuery(URL url) throws MalformedURLException {
        String file = url.getFile();
        int i = file.indexOf("?");
        if (i == -1) {
            return url;
        }
        return new URL(url.getProtocol(), url.getHost(), url.getPort(), file.substring(0, i));
    }

    public static boolean isDownLoadUrl(String url) {
        int dotIndex;
        int index;
        if (TextUtils.isEmpty(url) || !url.startsWith("http://")) {
            return false;
        }
        String filename = null;
        String decodedUrl = Uri.decode(url);
        if (!TextUtils.isEmpty(url)) {
            int queryIndex = decodedUrl.indexOf(63);
            if (queryIndex > 0) {
                decodedUrl = decodedUrl.substring(0, queryIndex);
            }
            if (!decodedUrl.endsWith(Constants.URL_PATH_DELIMITER) && (index = decodedUrl.lastIndexOf(47) + 1) > 0) {
                filename = decodedUrl.substring(index);
            }
        }
        if (TextUtils.isEmpty(filename) || (dotIndex = filename.lastIndexOf(46)) < 0 || !FileUtils.isValidExtensionFileName(filename.substring(dotIndex + 1))) {
            return false;
        }
        return true;
    }

    public static String getDefaultExtensionByMimeType(String mimeType) {
        String extension = null;
        if (TextUtils.isEmpty(mimeType)) {
            return null;
        }
        if (mimeType == null || !mimeType.toLowerCase().startsWith("text/")) {
            if (mimeType == null || !mimeType.toLowerCase().startsWith("image/")) {
                extension = ".bin";
            } else if (mimeType.equalsIgnoreCase("image/png")) {
                extension = ".png";
            } else if (mimeType.equalsIgnoreCase("image/jpeg")) {
                extension = ".jpeg";
            } else if (mimeType.equalsIgnoreCase("image/jpg")) {
                extension = ".jpg";
            } else if (mimeType.equalsIgnoreCase("image/gif")) {
                extension = ".gif";
            }
        } else if (mimeType.equalsIgnoreCase(Mimetypes.MIMETYPE_HTML)) {
            extension = ".html";
        } else {
            extension = ".txt";
        }
        String str = extension;
        return extension;
    }

    public static String getHost(String url) {
        String host;
        if (url == null || url.length() == 0) {
            return null;
        }
        int start = 0;
        int protoIdx = url.indexOf("://");
        if (protoIdx != -1) {
            start = protoIdx + 3;
        }
        int slashIdx = url.indexOf(47, start);
        if (slashIdx != -1) {
            host = url.substring(start, slashIdx);
        } else {
            int queryIdx = url.indexOf(63, start);
            if (queryIdx != -1) {
                host = url.substring(start, queryIdx);
            } else {
                host = url.substring(start);
            }
        }
        int colonPos = host.indexOf(":");
        if (colonPos >= 0) {
            return host.substring(0, colonPos);
        }
        return host;
    }

    public static String getPathAndQuery(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        try {
            URL urlpaser = new URL(url);
            return urlpaser.getPath() + urlpaser.getQuery();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDomain(String url) {
        String host;
        if (!TextUtils.isEmpty(url) && (host = getHost(url)) != null && !"".equals(host)) {
            return "http://" + host;
        }
        return null;
    }

    public static String getRootDomain(String url) {
        int lastIdx;
        String domainName;
        int lastIdx2;
        String mainDomainSuffix;
        String host = getHost(url);
        if (host == null || "".equals(host) || (lastIdx = host.lastIndexOf(46)) == -1) {
            return null;
        }
        String domainSuffix = host.substring(lastIdx + 1);
        String hostWithoutSuffix = host.substring(0, lastIdx);
        if (domainSuffix != null && domainSuffix.equalsIgnoreCase("cn") && (lastIdx2 = hostWithoutSuffix.lastIndexOf(46)) != -1 && (mainDomainSuffix = hostWithoutSuffix.substring(lastIdx2 + 1)) != null && mainDomainSuffix.length() > 0 && (mainDomainSuffix.equalsIgnoreCase("com") || mainDomainSuffix.equalsIgnoreCase("edu") || mainDomainSuffix.equalsIgnoreCase("gov"))) {
            domainSuffix = mainDomainSuffix.concat(String.valueOf('.')).concat(domainSuffix);
            hostWithoutSuffix = hostWithoutSuffix.substring(0, lastIdx2);
        }
        int domainIdx = hostWithoutSuffix.lastIndexOf(46);
        if (domainIdx != -1) {
            domainName = hostWithoutSuffix.substring(domainIdx + 1);
        } else {
            domainName = hostWithoutSuffix;
        }
        if (domainName == null || domainName.length() <= 0) {
            return null;
        }
        return domainName.concat(String.valueOf('.')).concat(domainSuffix);
    }

    public static boolean isQQDomain(String url) {
        String domain = getRootDomain(url);
        if (!TextUtils.isEmpty(domain)) {
            return domain.equals("qq.com");
        }
        return false;
    }

    public static String getPath(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        int start = 0;
        int protoIdx = url.indexOf("://");
        if (protoIdx != -1) {
            start = protoIdx + 3;
        }
        int slashIdx = url.indexOf(47, start);
        if (slashIdx == -1) {
            return null;
        }
        int questionIdx = url.indexOf(63, slashIdx);
        if (questionIdx != -1) {
            return url.substring(slashIdx + 1, questionIdx);
        }
        return url.substring(slashIdx + 1);
    }

    public static String getStringAfterHost(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        int start = 0;
        int protoIdx = url.indexOf("://");
        if (protoIdx != -1) {
            start = protoIdx + 3;
        }
        int slashIdx = url.indexOf(47, start);
        if (slashIdx != -1) {
            return url.substring(slashIdx + 1);
        }
        return null;
    }

    private static boolean isNativeString(String content) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        for (char c : content.toCharArray()) {
            if (((byte) ((c >> 8) & 255)) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean urlSupportedByX5CoreSp(String url) {
        return isHttpUrl(url) || isHttpsUrl(url) || url.startsWith("about:blank") || isJavascript(url);
    }

    public static boolean isCandidateUrl(String aUrl) {
        if (aUrl == null || aUrl.length() == 0 || aUrl.startsWith("data:")) {
            return false;
        }
        String url = aUrl.trim();
        Matcher validUrl = VALID_URL.matcher(url);
        Matcher validLocal = VALID_LOCAL_URL.matcher(url);
        Matcher validIp = VALID_IP_ADDRESS.matcher(url);
        Matcher validMtt = VALID_MTT_URL.matcher(url);
        Matcher validQb = VALID_QB_URL.matcher(url);
        Matcher validPay = VALID_PAY_URL.matcher(url);
        if (validUrl.find() || validLocal.find() || validIp.find() || validMtt.find() || validQb.find() || validPay.find()) {
            return true;
        }
        return false;
    }

    public static boolean isIpUrl(String aUrl) {
        if (aUrl == null || aUrl.length() == 0) {
            return false;
        }
        if (VALID_IP_ADDRESS.matcher(aUrl.trim()).find()) {
            return true;
        }
        return false;
    }

    public static boolean isInnerIP(String ipAddress) {
        if (TextUtils.isEmpty(ipAddress) || !isIpUrl(ipAddress)) {
            return false;
        }
        try {
            long ipNum = getIpNum(ipAddress);
            return isInner(ipNum, getIpNum("10.0.0.0"), getIpNum("10.255.255.255")) || isInner(ipNum, getIpNum("172.16.0.0"), getIpNum("172.31.255.255")) || isInner(ipNum, getIpNum("192.168.0.0"), getIpNum("192.168.255.255")) || ipAddress.equals("127.0.0.1") || ipAddress.equals("1.1.1.1");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return userIp >= begin && userIp <= end;
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        return (256 * ((long) Integer.parseInt(ip[0])) * 256 * 256) + (256 * ((long) Integer.parseInt(ip[1])) * 256) + (256 * ((long) Integer.parseInt(ip[2]))) + ((long) Integer.parseInt(ip[3]));
    }

    public static boolean isDeprecatedPrefix(String url) {
        String prefix = getPrefix(url);
        if (TextUtils.isEmpty(prefix)) {
            return false;
        }
        if (prefix.equals("mtt://") || prefix.equals("hotpre://") || prefix.equals("dttp://") || prefix.equals("page://") || prefix.equals("tencent://")) {
            return true;
        }
        return false;
    }

    public static boolean hasValidProtocal(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        int pos1 = url.indexOf("://");
        int pos2 = url.indexOf(46);
        if (pos1 <= 0 || pos2 <= 0 || pos1 <= pos2) {
            return url.contains("://");
        }
        return false;
    }

    public static String resolvValidUrl(String aUrl) {
        if (aUrl == null || aUrl.length() == 0) {
            return null;
        }
        String url = aUrl.trim();
        if (isJavascript(url) || isSamsungUrl(url)) {
            return url;
        }
        if (!isCandidateUrl(url)) {
            return null;
        }
        if (!hasValidProtocal(url)) {
            return "http://" + url;
        }
        return url;
    }

    public static Uri getSmsUriFromUrl(String url) {
        if (!isSmsUrl(url)) {
            return null;
        }
        String smstoUrl = url.replaceFirst("sms:", "smsto:");
        int index = smstoUrl.indexOf(63);
        if (index > -1) {
            return Uri.parse(smstoUrl.substring(0, index));
        }
        return Uri.parse(smstoUrl);
    }

    public static String getSmsTextFromUrl(String url) {
        int index;
        int i;
        if (!isSmsUrl(url) || (index = url.indexOf(63)) <= -1 || index >= url.length() - 1) {
            return null;
        }
        for (String param : url.substring(index + 1).split(HttpRequest.HTTP_REQ_ENTITY_JOIN)) {
            if (param.startsWith("body=") && (i = param.indexOf(61)) > -1 && i < param.length() - 1) {
                return param.substring(i + 1);
            }
        }
        return null;
    }

    public static boolean isAlipayUrl(String url) {
        boolean result = false;
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.toLowerCase().trim().startsWith("alipay://securitypay/?")) {
            result = true;
        }
        boolean z = result;
        return result;
    }

    public static boolean isUpPayUrl(String url) {
        if (url != null && url.toLowerCase().trim().startsWith("uppay://")) {
            return true;
        }
        return false;
    }

    public static boolean isMobileQQUrl(String url) {
        if (url.toLowerCase().trim().contains("mqqapi://")) {
            return true;
        }
        return false;
    }

    private static boolean isQbSchema(String url) {
        return url != null && url.startsWith("qb://");
    }

    public static boolean urlSupportedByX5Core(String url) {
        return isHttpUrl(url) || isHttpsUrl(url) || isQbSchema(url) || isFileUrl(url) || isFtpUrl(url) || url.startsWith("about:blank") || isJavascript(url) || isDataUrl(url);
    }

    public static String resolvValidSqlUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        return url.replaceAll("'", "''");
    }

    public static boolean isTenpayUrl(String url) {
        boolean result = false;
        if (url == null) {
            return false;
        }
        if (url.toLowerCase().trim().startsWith("tenpay://")) {
            result = true;
        }
        boolean z = result;
        return result;
    }

    public static int parsePayInfo(String payInfo, Map<String, String> map) {
        String cmd;
        Map<String, String> result = map;
        if ((payInfo.indexOf("pay") == -1 && payInfo.indexOf("webpay") == -1) || payInfo.indexOf("?") == -1 || payInfo.indexOf(HttpRequest.HTTP_REQ_ENTITY_JOIN) == -1 || payInfo.indexOf(HttpRequest.HTTP_REQ_ENTITY_MERGE) == -1) {
            cmd = "invalid";
        } else {
            cmd = payInfo.substring(payInfo.indexOf("tenpay://") + 9, payInfo.indexOf("?"));
            try {
                String[] strs = payInfo.substring(payInfo.indexOf("?") + 1).split(HttpRequest.HTTP_REQ_ENTITY_JOIN);
                for (String parm : strs) {
                    String[] values = parm.split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                    result.put(values[0], values[1]);
                }
            } catch (Exception e) {
                cmd = "invalid";
                e.printStackTrace();
            }
        }
        if (cmd.equalsIgnoreCase("pay")) {
            return 1;
        }
        return cmd.equalsIgnoreCase("webpay") ? 2 : 0;
    }

    public static boolean isPluginUrl(String url) {
        if (url == null) {
            return false;
        }
        String url2 = url.toLowerCase().trim();
        if (url2.startsWith("qb://player/") || url2.startsWith("qb://addon/") || (url2.startsWith("qb://app/") && !url2.startsWith("qb://app/id"))) {
            return true;
        }
        return false;
    }

    public static String GetPluginType(String url) {
        int posBegin = url.indexOf("qb://");
        return url.substring(posBegin + 5, url.indexOf(Constants.URL_PATH_DELIMITER, posBegin + 5));
    }

    public static boolean isNeedCanvasDrawWebSite(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        String host = getHost(url);
        if (TextUtils.isEmpty(host) || !host.equalsIgnoreCase("m.cnbeta.com")) {
            return false;
        }
        return true;
    }

    public static String getDataFromQbUrl(String url, String key) {
        String result = "";
        if (url == null) {
            String str = result;
            return result;
        }
        String[] arr$ = url.substring(url.indexOf(63) + 1).split(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        try {
            int len$ = arr$.length;
            int i$ = 0;
            while (true) {
                if (i$ >= len$) {
                    break;
                }
                String[] values = arr$[i$].split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                if (values[0].equalsIgnoreCase(key)) {
                    result = values[1];
                    break;
                }
                i$++;
            }
        } catch (Exception e) {
            result = null;
        }
        String str2 = result;
        return result;
    }

    public static String replaceValueByKey(String url, String key, String value) {
        String str;
        String str2;
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(key)) {
            return url;
        }
        StringBuilder newUrl = new StringBuilder("");
        String split = "?";
        int index = url.indexOf(63);
        if (index != -1) {
            newUrl.append(url.substring(0, index));
        } else {
            split = "";
        }
        String[] paramaters = url.substring(index + 1).split(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        int length = paramaters.length;
        for (int i = 0; i < length; i++) {
            String[] values = paramaters[i].split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            if (values[0].equalsIgnoreCase(key) && values.length == 2) {
                values[1] = value;
            }
            if (i == 0) {
                str = split;
            } else {
                str = HttpRequest.HTTP_REQ_ENTITY_JOIN;
            }
            StringBuilder append = newUrl.append(str).append(values[0]).append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            if (values.length == 2) {
                str2 = values[1];
            } else {
                str2 = "";
            }
            append.append(str2);
        }
        return newUrl.toString();
    }

    public static String decode(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String str2 = str;
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str2;
        } catch (Exception e2) {
            DownloaderLog.d(TAG, "decode url failure");
            return str2;
        }
    }

    public static boolean isDomain(String url) {
        String str = resolvValidUrl(url);
        String lowcaseStr = url.toLowerCase();
        if (str == null || url.lastIndexOf(".") <= 0) {
            return false;
        }
        for (String endsWith : SUFFIX) {
            if (lowcaseStr.endsWith(endsWith)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<String, String> getUrlParam(String url) {
        String[] nameValues;
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        int idx = url.indexOf(63);
        if (idx == -1 || (nameValues = url.substring(idx + 1).split(HttpRequest.HTTP_REQ_ENTITY_JOIN)) == null || nameValues.length <= 0) {
            return map;
        }
        for (String param : nameValues) {
            int idx2 = param.indexOf(61);
            if (idx2 != -1) {
                String key = param.substring(0, idx2);
                String value = param.substring(idx2 + 1, param.length());
                try {
                    value = URLDecoder.decode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                map.put(key, value);
            }
        }
        return map;
    }

    public static String removeSid(String url) {
        int index;
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if ((!url.contains("?sid=") && !url.contains("&sid=")) || (index = url.indexOf("sid=")) == -1) {
            return url;
        }
        String front = url.substring(0, index);
        String end = url.substring(index + 4);
        if (TextUtils.isEmpty(end) || end.indexOf(HttpRequest.HTTP_REQ_ENTITY_JOIN) <= 0) {
            return url.substring(0, index - 1);
        }
        return front + end.substring(end.indexOf(HttpRequest.HTTP_REQ_ENTITY_JOIN) + 1);
    }

    public static HashMap<String, String> getPaiLideUrlParam(String url, String start) {
        String appId;
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        int idx = url.indexOf(start);
        if (idx == -1) {
            return map;
        }
        String queryString = url.substring(start.length() + idx, url.length());
        int titleIndex = queryString.indexOf(47);
        String title = null;
        if (titleIndex != -1) {
            appId = queryString.substring(0, titleIndex);
            title = queryString.substring(titleIndex + 1, queryString.length());
        } else {
            appId = queryString;
        }
        if (!TextUtils.isEmpty(appId)) {
            String value = appId;
            try {
                value = URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            map.put("appid", value);
        }
        if (TextUtils.isEmpty(title)) {
            return map;
        }
        String value2 = title;
        try {
            value2 = URLDecoder.decode(value2, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        map.put("title", value2);
        return map;
    }

    public static String resolvValidUrlForOldVision(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (isWebkitUrl(url) || isDttpUrl(url) || isBrokerUrl(url) || isMttUrl(url)) {
            return deletePrefix(url);
        }
        return url;
    }

    public static boolean isMttUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return url.toLowerCase().startsWith("mtt://");
    }

    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en != null && en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses();
                while (true) {
                    if (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
