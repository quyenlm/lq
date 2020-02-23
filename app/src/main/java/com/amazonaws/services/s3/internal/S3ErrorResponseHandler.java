package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.XpathUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

public class S3ErrorResponseHandler implements HttpResponseHandler<AmazonServiceException> {
    private static final Log log = LogFactory.getLog(S3ErrorResponseHandler.class);

    public AmazonServiceException handle(HttpResponse errorResponse) throws IOException {
        InputStream is = errorResponse.getContent();
        if (is == null) {
            return newAmazonS3Exception(errorResponse.getStatusText(), errorResponse);
        }
        try {
            String content = IOUtils.toString(is);
            try {
                Document document = XpathUtils.documentFrom(content);
                String message = XpathUtils.asString("Error/Message", document);
                String errorCode = XpathUtils.asString("Error/Code", document);
                String requestId = XpathUtils.asString("Error/RequestId", document);
                String extendedRequestId = XpathUtils.asString("Error/HostId", document);
                AmazonS3Exception ase = new AmazonS3Exception(message);
                int statusCode = errorResponse.getStatusCode();
                ase.setStatusCode(statusCode);
                ase.setErrorType(errorTypeOf(statusCode));
                ase.setErrorCode(errorCode);
                ase.setRequestId(requestId);
                ase.setExtendedRequestId(extendedRequestId);
                ase.setCloudFrontId(errorResponse.getHeaders().get(Headers.CLOUD_FRONT_ID));
                return ase;
            } catch (Exception ex) {
                if (log.isDebugEnabled()) {
                    log.debug("Failed in parsing the response as XML: " + content, ex);
                }
                return newAmazonS3Exception(content, errorResponse);
            }
        } catch (IOException ex2) {
            if (log.isDebugEnabled()) {
                log.debug("Failed in reading the error response", ex2);
            }
            return newAmazonS3Exception(errorResponse.getStatusText(), errorResponse);
        }
    }

    private AmazonS3Exception newAmazonS3Exception(String errmsg, HttpResponse httpResponse) {
        AmazonS3Exception ase = new AmazonS3Exception(errmsg);
        int statusCode = httpResponse.getStatusCode();
        ase.setErrorCode(statusCode + " " + httpResponse.getStatusText());
        ase.setStatusCode(statusCode);
        ase.setErrorType(errorTypeOf(statusCode));
        Map<String, String> headers = httpResponse.getHeaders();
        ase.setRequestId(headers.get(Headers.REQUEST_ID));
        ase.setExtendedRequestId(headers.get(Headers.EXTENDED_REQUEST_ID));
        ase.setCloudFrontId(headers.get(Headers.CLOUD_FRONT_ID));
        return ase;
    }

    private AmazonServiceException.ErrorType errorTypeOf(int statusCode) {
        return statusCode >= 500 ? AmazonServiceException.ErrorType.Service : AmazonServiceException.ErrorType.Client;
    }

    public boolean needsConnectionLeftOpen() {
        return false;
    }
}
