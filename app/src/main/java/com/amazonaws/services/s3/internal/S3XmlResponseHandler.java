package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.transform.Unmarshaller;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class S3XmlResponseHandler<T> extends AbstractS3ResponseHandler<T> {
    private static final Log log = LogFactory.getLog("com.amazonaws.request");
    private Map<String, String> responseHeaders;
    private Unmarshaller<T, InputStream> responseUnmarshaller;

    public S3XmlResponseHandler(Unmarshaller<T, InputStream> responseUnmarshaller2) {
        this.responseUnmarshaller = responseUnmarshaller2;
    }

    public AmazonWebServiceResponse<T> handle(HttpResponse response) throws Exception {
        AmazonWebServiceResponse<T> awsResponse = parseResponseMetadata(response);
        this.responseHeaders = response.getHeaders();
        if (this.responseUnmarshaller != null) {
            log.trace("Beginning to parse service response XML");
            T result = this.responseUnmarshaller.unmarshall(response.getContent());
            log.trace("Done parsing service response XML");
            awsResponse.setResult(result);
        }
        return awsResponse;
    }

    public Map<String, String> getResponseHeaders() {
        return this.responseHeaders;
    }
}
