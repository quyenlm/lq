package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.S3ResponseMetadata;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.DateUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractS3ResponseHandler<T> implements HttpResponseHandler<AmazonWebServiceResponse<T>> {
    private static final Set<String> ignoredHeaders = new HashSet();
    private static final Log log = LogFactory.getLog(S3MetadataResponseHandler.class);

    static {
        ignoredHeaders.add("Date");
        ignoredHeaders.add("Server");
        ignoredHeaders.add(Headers.REQUEST_ID);
        ignoredHeaders.add(Headers.EXTENDED_REQUEST_ID);
    }

    public boolean needsConnectionLeftOpen() {
        return false;
    }

    /* access modifiers changed from: protected */
    public AmazonWebServiceResponse<T> parseResponseMetadata(HttpResponse response) {
        AmazonWebServiceResponse<T> awsResponse = new AmazonWebServiceResponse<>();
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put(ResponseMetadata.AWS_REQUEST_ID, response.getHeaders().get(Headers.REQUEST_ID));
        metadataMap.put(S3ResponseMetadata.HOST_ID, response.getHeaders().get(Headers.EXTENDED_REQUEST_ID));
        awsResponse.setResponseMetadata(new S3ResponseMetadata(metadataMap));
        return awsResponse;
    }

    /* access modifiers changed from: protected */
    public void populateObjectMetadata(HttpResponse response, ObjectMetadata metadata) {
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            String key = header.getKey();
            if (key.startsWith(Headers.S3_USER_METADATA_PREFIX)) {
                metadata.addUserMetadata(key.substring(Headers.S3_USER_METADATA_PREFIX.length()), header.getValue());
            } else if (!ignoredHeaders.contains(key)) {
                if (key.equals("Last-Modified")) {
                    try {
                        metadata.setHeader(key, ServiceUtils.parseRfc822Date(header.getValue()));
                    } catch (Exception pe) {
                        log.warn("Unable to parse last modified date: " + header.getValue(), pe);
                    }
                } else if (key.equals("Content-Length")) {
                    try {
                        metadata.setHeader(key, Long.valueOf(Long.parseLong(header.getValue())));
                    } catch (NumberFormatException nfe) {
                        log.warn("Unable to parse content length: " + header.getValue(), nfe);
                    }
                } else if (key.equals(Headers.ETAG)) {
                    metadata.setHeader(key, ServiceUtils.removeQuotes(header.getValue()));
                } else if (key.equals(Headers.EXPIRES)) {
                    try {
                        metadata.setHttpExpiresDate(DateUtils.parseRFC822Date(header.getValue()));
                    } catch (Exception pe2) {
                        log.warn("Unable to parse http expiration date: " + header.getValue(), pe2);
                    }
                } else if (key.equals(Headers.EXPIRATION)) {
                    new ObjectExpirationHeaderHandler().handle(metadata, response);
                } else if (key.equals(Headers.RESTORE)) {
                    new ObjectRestoreHeaderHandler().handle(metadata, response);
                } else {
                    metadata.setHeader(key, header.getValue());
                }
            }
        }
    }
}
