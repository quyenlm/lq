package com.beetalk.sdk.helper;

import com.beetalk.sdk.networking.HttpParam;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class StringUtils {
    public static <T> String join(List<T> elements, String separator) {
        StringBuilder builder = new StringBuilder("");
        int len = elements.size();
        for (int i = 0; i < len; i++) {
            builder.append(String.valueOf(elements.get(i)));
            if (i < len - 1) {
                builder.append(separator);
            }
        }
        return builder.toString();
    }

    public static boolean isEmpty(String token) {
        return token == null || token.length() == 0;
    }

    public static boolean responseHasError(JSONObject result) {
        return result == null || result.has("error");
    }

    public static String httpParamsToString(List<HttpParam> params) throws UnsupportedEncodingException {
        if (params == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        Iterator<HttpParam> iterator = params.iterator();
        while (iterator.hasNext()) {
            HttpParam param = iterator.next();
            result.append(URLEncoder.encode(param.key, "UTF-8"));
            result.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            result.append(URLEncoder.encode(param.value, "UTF-8"));
            if (iterator.hasNext()) {
                result.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
        }
        return result.toString();
    }
}
