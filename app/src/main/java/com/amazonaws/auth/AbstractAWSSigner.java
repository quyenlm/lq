package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.internal.SdkDigestInputStream;
import com.amazonaws.util.Base64;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public abstract class AbstractAWSSigner implements Signer {
    /* access modifiers changed from: protected */
    public abstract void addSessionCredentials(Request<?> request, AWSSessionCredentials aWSSessionCredentials);

    /* access modifiers changed from: protected */
    public String signAndBase64Encode(String data, String key, SigningAlgorithm algorithm) throws AmazonClientException {
        return signAndBase64Encode(data.getBytes(StringUtils.UTF8), key, algorithm);
    }

    /* access modifiers changed from: protected */
    public String signAndBase64Encode(byte[] data, String key, SigningAlgorithm algorithm) throws AmazonClientException {
        try {
            return Base64.encodeAsString(sign(data, key.getBytes(StringUtils.UTF8), algorithm));
        } catch (Exception e) {
            throw new AmazonClientException("Unable to calculate a request signature: " + e.getMessage(), e);
        }
    }

    public byte[] sign(String stringData, byte[] key, SigningAlgorithm algorithm) throws AmazonClientException {
        try {
            return sign(stringData.getBytes(StringUtils.UTF8), key, algorithm);
        } catch (Exception e) {
            throw new AmazonClientException("Unable to calculate a request signature: " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] sign(byte[] data, byte[] key, SigningAlgorithm algorithm) throws AmazonClientException {
        try {
            Mac mac = Mac.getInstance(algorithm.toString());
            mac.init(new SecretKeySpec(key, algorithm.toString()));
            return mac.doFinal(data);
        } catch (Exception e) {
            throw new AmazonClientException("Unable to calculate a request signature: " + e.getMessage(), e);
        }
    }

    public byte[] hash(String text) throws AmazonClientException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StringUtils.UTF8));
            return md.digest();
        } catch (Exception e) {
            throw new AmazonClientException("Unable to compute hash while signing request: " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] hash(InputStream input) throws AmazonClientException {
        try {
            DigestInputStream digestInputStream = new SdkDigestInputStream(input, MessageDigest.getInstance("SHA-256"));
            do {
            } while (digestInputStream.read(new byte[1024]) > -1);
            return digestInputStream.getMessageDigest().digest();
        } catch (Exception e) {
            throw new AmazonClientException("Unable to compute hash while signing request: " + e.getMessage(), e);
        }
    }

    public byte[] hash(byte[] data) throws AmazonClientException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            throw new AmazonClientException("Unable to compute hash while signing request: " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedQueryString(Map<String, String> parameters) {
        SortedMap<String, String> sorted = new TreeMap<>();
        for (Map.Entry<String, String> pair : parameters.entrySet()) {
            sorted.put(HttpUtils.urlEncode(pair.getKey(), false), HttpUtils.urlEncode(pair.getValue(), false));
        }
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> pairs = sorted.entrySet().iterator();
        while (pairs.hasNext()) {
            Map.Entry<String, String> pair2 = pairs.next();
            builder.append(pair2.getKey());
            builder.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            builder.append(pair2.getValue());
            if (pairs.hasNext()) {
                builder.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
        }
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedQueryString(Request<?> request) {
        if (HttpUtils.usePayloadForQueryParameters(request)) {
            return "";
        }
        return getCanonicalizedQueryString(request.getParameters());
    }

    /* access modifiers changed from: protected */
    public byte[] getBinaryRequestPayload(Request<?> request) {
        if (!HttpUtils.usePayloadForQueryParameters(request)) {
            return getBinaryRequestPayloadWithoutQueryParams(request);
        }
        String encodedParameters = HttpUtils.encodeParameters(request);
        if (encodedParameters == null) {
            return new byte[0];
        }
        return encodedParameters.getBytes(StringUtils.UTF8);
    }

    /* access modifiers changed from: protected */
    public String getRequestPayload(Request<?> request) {
        return newString(getBinaryRequestPayload(request));
    }

    /* access modifiers changed from: protected */
    public String getRequestPayloadWithoutQueryParams(Request<?> request) {
        return newString(getBinaryRequestPayloadWithoutQueryParams(request));
    }

    /* access modifiers changed from: protected */
    public byte[] getBinaryRequestPayloadWithoutQueryParams(Request<?> request) {
        InputStream content = getBinaryRequestPayloadStreamWithoutQueryParams(request);
        try {
            content.mark(-1);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[5120];
            while (true) {
                int bytesRead = content.read(buffer);
                if (bytesRead == -1) {
                    byteArrayOutputStream.close();
                    content.reset();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new AmazonClientException("Unable to read request payload to sign request: " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public InputStream getBinaryRequestPayloadStream(Request<?> request) {
        if (!HttpUtils.usePayloadForQueryParameters(request)) {
            return getBinaryRequestPayloadStreamWithoutQueryParams(request);
        }
        String encodedParameters = HttpUtils.encodeParameters(request);
        if (encodedParameters == null) {
            return new ByteArrayInputStream(new byte[0]);
        }
        return new ByteArrayInputStream(encodedParameters.getBytes(StringUtils.UTF8));
    }

    /* access modifiers changed from: protected */
    public InputStream getBinaryRequestPayloadStreamWithoutQueryParams(Request<?> request) {
        try {
            InputStream content = request.getContent();
            if (content == null) {
                return new ByteArrayInputStream(new byte[0]);
            }
            if (content instanceof StringInputStream) {
                return content;
            }
            if (content.markSupported()) {
                return request.getContent();
            }
            throw new AmazonClientException("Unable to read request payload to sign request.");
        } catch (Exception e) {
            throw new AmazonClientException("Unable to read request payload to sign request: " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedResourcePath(String resourcePath) {
        return getCanonicalizedResourcePath(resourcePath, true);
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedResourcePath(String resourcePath, boolean urlEncode) {
        String value;
        if (resourcePath == null || resourcePath.length() == 0) {
            return Constants.URL_PATH_DELIMITER;
        }
        if (urlEncode) {
            value = HttpUtils.urlEncode(resourcePath, true);
        } else {
            value = resourcePath;
        }
        return !value.startsWith(Constants.URL_PATH_DELIMITER) ? Constants.URL_PATH_DELIMITER.concat(value) : value;
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedEndpoint(URI endpoint) {
        String endpointForStringToSign = StringUtils.lowerCase(endpoint.getHost());
        if (HttpUtils.isUsingNonDefaultPort(endpoint)) {
            return endpointForStringToSign + ":" + endpoint.getPort();
        }
        return endpointForStringToSign;
    }

    /* access modifiers changed from: protected */
    public AWSCredentials sanitizeCredentials(AWSCredentials credentials) {
        String accessKeyId;
        String secretKey;
        String token = null;
        synchronized (credentials) {
            accessKeyId = credentials.getAWSAccessKeyId();
            secretKey = credentials.getAWSSecretKey();
            if (credentials instanceof AWSSessionCredentials) {
                token = ((AWSSessionCredentials) credentials).getSessionToken();
            }
        }
        if (secretKey != null) {
            secretKey = secretKey.trim();
        }
        if (accessKeyId != null) {
            accessKeyId = accessKeyId.trim();
        }
        if (token != null) {
            token = token.trim();
        }
        if (credentials instanceof AWSSessionCredentials) {
            return new BasicSessionCredentials(accessKeyId, secretKey, token);
        }
        return new BasicAWSCredentials(accessKeyId, secretKey);
    }

    /* access modifiers changed from: protected */
    public String newString(byte[] bytes) {
        return new String(bytes, StringUtils.UTF8);
    }

    /* access modifiers changed from: protected */
    public Date getSignatureDate(int timeOffset) {
        Date dateValue = new Date();
        if (timeOffset != 0) {
            return new Date(dateValue.getTime() - ((long) (timeOffset * 1000)));
        }
        return dateValue;
    }

    /* access modifiers changed from: protected */
    public int getTimeOffset(Request<?> request) {
        int timeOffset = request.getTimeOffset();
        if (SDKGlobalConfiguration.getGlobalTimeOffset() != 0) {
            return SDKGlobalConfiguration.getGlobalTimeOffset();
        }
        return timeOffset;
    }
}
