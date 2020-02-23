package com.amazonaws.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpHost;

public class HttpUtils {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Pattern ENCODED_CHARACTERS_PATTERN;

    static {
        StringBuilder pattern = new StringBuilder();
        pattern.append(Pattern.quote("+")).append("|").append(Pattern.quote("*")).append("|").append(Pattern.quote("%7E")).append("|").append(Pattern.quote("%2F"));
        ENCODED_CHARACTERS_PATTERN = Pattern.compile(pattern.toString());
    }

    public static String urlEncode(String value, boolean path) {
        if (value == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, "UTF-8");
            Matcher matcher = ENCODED_CHARACTERS_PATTERN.matcher(encoded);
            StringBuffer buffer = new StringBuffer(encoded.length());
            while (matcher.find()) {
                String replacement = matcher.group(0);
                if ("+".equals(replacement)) {
                    replacement = "%20";
                } else if ("*".equals(replacement)) {
                    replacement = "%2A";
                } else if ("%7E".equals(replacement)) {
                    replacement = "~";
                } else if (path && "%2F".equals(replacement)) {
                    replacement = Constants.URL_PATH_DELIMITER;
                }
                matcher.appendReplacement(buffer, replacement);
            }
            matcher.appendTail(buffer);
            return buffer.toString();
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isUsingNonDefaultPort(URI uri) {
        String scheme = StringUtils.lowerCase(uri.getScheme());
        int port = uri.getPort();
        if (port <= 0) {
            return false;
        }
        if (scheme.equals(HttpHost.DEFAULT_SCHEME_NAME) && port == 80) {
            return false;
        }
        if (!scheme.equals(VKApiConst.HTTPS) || port != 443) {
            return true;
        }
        return false;
    }

    public static boolean usePayloadForQueryParameters(Request<?> request) {
        boolean requestHasNoPayload;
        boolean requestIsPOST = HttpMethodName.POST.equals(request.getHttpMethod());
        if (request.getContent() == null) {
            requestHasNoPayload = true;
        } else {
            requestHasNoPayload = false;
        }
        if (!requestIsPOST || !requestHasNoPayload) {
            return false;
        }
        return true;
    }

    public static String encodeParameters(Request<?> request) {
        if (request.getParameters().isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> entry : request.getParameters().entrySet()) {
                String encodedName = URLEncoder.encode(entry.getKey(), "UTF-8");
                String value = entry.getValue();
                String encodedValue = value == null ? "" : URLEncoder.encode(value, "UTF-8");
                if (!first) {
                    sb.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
                } else {
                    first = false;
                }
                sb.append(encodedName).append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(encodedValue);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String appendUri(String baseUri, String path) {
        return appendUri(baseUri, path, false);
    }

    public static String appendUri(String baseUri, String path, boolean escapeDoubleSlash) {
        String resultUri = baseUri;
        if (path != null && path.length() > 0) {
            if (path.startsWith(Constants.URL_PATH_DELIMITER)) {
                if (resultUri.endsWith(Constants.URL_PATH_DELIMITER)) {
                    resultUri = resultUri.substring(0, resultUri.length() - 1);
                }
            } else if (!resultUri.endsWith(Constants.URL_PATH_DELIMITER)) {
                resultUri = resultUri + Constants.URL_PATH_DELIMITER;
            }
            String encodedPath = urlEncode(path, true);
            if (escapeDoubleSlash) {
                encodedPath = encodedPath.replace("//", "/%2F");
            }
            return resultUri + encodedPath;
        } else if (!resultUri.endsWith(Constants.URL_PATH_DELIMITER)) {
            return resultUri + Constants.URL_PATH_DELIMITER;
        } else {
            return resultUri;
        }
    }

    public static InputStream fetchFile(URI uri, ClientConfiguration config) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setConnectTimeout(getConnectionTimeout(config));
        connection.setReadTimeout(getSocketTimeout(config));
        connection.addRequestProperty("User-Agent", getUserAgent(config));
        if (connection.getResponseCode() == 200) {
            return connection.getInputStream();
        }
        InputStream is = connection.getErrorStream();
        if (is != null) {
            is.close();
        }
        connection.disconnect();
        throw new IOException("Error fetching file from " + uri + ": " + connection.getResponseMessage());
    }

    static String getUserAgent(ClientConfiguration config) {
        String userAgent = null;
        if (config != null) {
            userAgent = config.getUserAgent();
        }
        if (userAgent == null) {
            return ClientConfiguration.DEFAULT_USER_AGENT;
        }
        if (!ClientConfiguration.DEFAULT_USER_AGENT.equals(userAgent)) {
            return userAgent + ", " + ClientConfiguration.DEFAULT_USER_AGENT;
        }
        return userAgent;
    }

    static int getConnectionTimeout(ClientConfiguration config) {
        if (config != null) {
            return config.getConnectionTimeout();
        }
        return 15000;
    }

    static int getSocketTimeout(ClientConfiguration config) {
        if (config != null) {
            return config.getSocketTimeout();
        }
        return 15000;
    }
}
