package com.amazonaws.services.s3.internal;

import android.net.http.Headers;
import com.amazonaws.Request;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.util.StringUtils;
import com.facebook.places.model.PlaceFields;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class RestUtils {
    private static final List<String> SIGNED_PARAMETERS = Arrays.asList(new String[]{"acl", "torrent", "logging", "location", "policy", "requestPayment", "versioning", "versions", "versionId", "notification", "uploadId", "uploads", "partNumber", PlaceFields.WEBSITE, "delete", "lifecycle", "tagging", "cors", APNetworkManager2.HTTP_KEY_OVERSEARESTORE, "accelerate", ResponseHeaderOverrides.RESPONSE_HEADER_CACHE_CONTROL, ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_DISPOSITION, ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_ENCODING, ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_LANGUAGE, ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_TYPE, ResponseHeaderOverrides.RESPONSE_HEADER_EXPIRES});

    public static <T> String makeS3CanonicalString(String method, String resource, Request<T> request, String expires) {
        StringBuilder buf = new StringBuilder();
        buf.append(method + "\n");
        Map<String, String> headersMap = request.getHeaders();
        SortedMap<String, String> interestingHeaders = new TreeMap<>();
        if (headersMap != null && headersMap.size() > 0) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null) {
                    String lk = StringUtils.lowerCase(key.toString());
                    if (lk.equals(Headers.CONTENT_TYPE) || lk.equals("content-md5") || lk.equals("date") || lk.startsWith(com.amazonaws.services.s3.Headers.AMAZON_PREFIX)) {
                        interestingHeaders.put(lk, value);
                    }
                }
            }
        }
        if (interestingHeaders.containsKey(com.amazonaws.services.s3.Headers.S3_ALTERNATE_DATE)) {
            interestingHeaders.put("date", "");
        }
        if (expires != null) {
            interestingHeaders.put("date", expires);
        }
        if (!interestingHeaders.containsKey(Headers.CONTENT_TYPE)) {
            interestingHeaders.put(Headers.CONTENT_TYPE, "");
        }
        if (!interestingHeaders.containsKey("content-md5")) {
            interestingHeaders.put("content-md5", "");
        }
        for (Map.Entry<String, String> parameter : request.getParameters().entrySet()) {
            if (parameter.getKey().startsWith(com.amazonaws.services.s3.Headers.AMAZON_PREFIX)) {
                interestingHeaders.put(parameter.getKey(), parameter.getValue());
            }
        }
        for (Map.Entry<String, String> entry2 : interestingHeaders.entrySet()) {
            String key2 = entry2.getKey();
            String value2 = entry2.getValue();
            if (key2.startsWith(com.amazonaws.services.s3.Headers.AMAZON_PREFIX)) {
                buf.append(key2).append(':');
                if (value2 != null) {
                    buf.append(value2);
                }
            } else if (value2 != null) {
                buf.append(value2);
            }
            buf.append("\n");
        }
        buf.append(resource);
        String[] parameterNames = (String[]) request.getParameters().keySet().toArray(new String[request.getParameters().size()]);
        Arrays.sort(parameterNames);
        char separator = '?';
        for (String parameterName : parameterNames) {
            if (SIGNED_PARAMETERS.contains(parameterName)) {
                buf.append(separator);
                buf.append(parameterName);
                String parameterValue = request.getParameters().get(parameterName);
                if (parameterValue != null) {
                    buf.append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(parameterValue);
                }
                separator = '&';
            }
        }
        return buf.toString();
    }
}
